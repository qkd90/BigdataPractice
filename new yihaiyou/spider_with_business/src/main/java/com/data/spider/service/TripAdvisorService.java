package com.data.spider.service;

import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.spider.service.pojo.tripAdvisor.Attractions;
import com.data.spider.service.pojo.tripAdvisor.Location;
import com.data.spider.util.HttpsUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sane on 16/2/29.
 */
public class TripAdvisorService {
    private String locationApi = "https://api.tripadvisor.cn/api/internal/1.8/location/%d?abtr=67&currency=RMB&dieroll=67&lang=zh_CN&show_seo_terms=true";
    //https://api.tripadvisor.cn/api/internal/1.8/location/1017022?abtr=67&currency=RMB&dieroll=67&lang=zh_CN&show_seo_terms=true
    private String attractionsApi = "https://api.daodao.com/api/internal/1.8/zh_CN/location/%d/attractions?limit=50&sort=popularity&prefersaves=true&include_rollups=true&dieroll=25&offset=%d&lang=zh_CN&currency=RMB";
    private String subAttractionsApi = "https://api.daodao.com/api/internal/1.8/zh_CN/location/%d/attractions?limit=50&sort=popularity&prefersaves=true&subcategory=%d&include_rollups=true&subtype=%d&offset=%d&dieroll=25&lang=zh_CN&currency=RMB";

    /**
     * 获得某个地点(可能是景点)的详情.
     *
     * @param locationId
     * @return Location
     */
    public Location getLocationDetail(int locationId) {
        Map<String, String> properties = getRequestProperties();
        String result = HttpsUtil.Send("GET", String.format(locationApi, locationId), null, properties);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, Location.class);
    }

    /**
     * 获取某个城市的所有景点
     *
     * @param tripAdvisorCity tripAdvisor的城市id
     * @return List<Attractions.DataEntity>
     */
    public List<Attractions.DataEntity> getScenicAll(int tripAdvisorCity) {
        int pageIndex = 0;
        List<Attractions.DataEntity> results = new ArrayList<Attractions.DataEntity>();
        do {
            Attractions attractions = getAttractions(tripAdvisorCity, pageIndex * 50);
            if (attractions == null)
                break;
            if (attractions.getData().size() == 0 || Integer.parseInt(attractions.getPaging().getTotal_results()) == 0) {
                break;
            }
            for (Attractions.DataEntity result : attractions.getData()) {
                if (result.getCategory().getKey().equals("attraction")) {
                    results.add(result);
                } else if (result.getCategory().getKey().equals("rollup")) {
                    List<Attractions.DataEntity.SubtypeEntity> subTypes = result.getSubtype();
                    for (int i = 0; i < subTypes.size(); i++) {
                        Attractions.DataEntity.SubtypeEntity subtype = subTypes.get(i);
                        int pageIndex2 = 0;
                        Attractions subList = getSubAttractions(tripAdvisorCity, Integer.
                                parseInt(result.getSubcategory().get(i).getKey()), Integer.parseInt(subtype.getKey()), pageIndex2 * 50);
                        if (subList.getData().size() == 0 || Integer.parseInt(subList.getPaging().getTotal_results()) == 0) {
                            break;
                        }
                        for (Attractions.DataEntity subResult : subList.getData()) {
                            if (subResult.getCategory().getKey().equals("attraction")) {
                                results.add(subResult);
                            }
                        }
                    }

                }
            }
            if (Integer.parseInt(attractions.getPaging().getTotal_results()) == results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    /**
     * 获取某个城市的某个下标之后的五十条内的景点
     *
     * @param tripAdvisorCity tripAdvisor的城市id
     * @param offset          下标
     * @return Attractions
     */
    public Attractions getAttractions(int tripAdvisorCity, int offset) {
        Map<String, String> properties = getRequestProperties();
        String result = HttpsUtil.Send("GET", String.format(attractionsApi, tripAdvisorCity, offset), null, properties);
        if (result == null || result.startsWith("{\n" +
                "  \"errors\"")) {
            return null;
        }
        return new Gson().fromJson(result, Attractions.class);
    }

    /**
     * 获取某个类别的下一级别景点
     *
     * @param tripAdvisorCity tripAdvisor的城市id
     * @param subCategory     类别
     * @param subType         类别
     * @param offset          下标
     * @return Attractions
     */
    public Attractions getSubAttractions(int tripAdvisorCity, int subCategory, int subType, int offset) {
        Map<String, String> properties = getRequestProperties();
        String result = HttpsUtil.Send("GET", String.format(subAttractionsApi, tripAdvisorCity, subCategory, subType, offset), null, properties);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, Attractions.class);
    }

    /**
     * https访问tripAdvisor的接口的时候,需要在http header中加入这些属性才行
     * 这些属性在以后有可能会失效,如果失效,可以再次抓包获得.
     *
     * @return Map<String, String>
     */
    private Map<String, String> getRequestProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("X-TripAdvisor-Unique", "%1%enc%3AxmEBEputYsKKrzFLd3woHU0TrIfZcW6o1BoV%2BerWzwmkwFceuXk9cQ%3D%3D");
        properties.put("X-TripAdvisor-UUID", "40FA2582-82A1-4D2F-8CD2-60EFFB1E1507");
        properties.put("X-TripAdvisor-API-Key", "3c7beec8-846d-4377-be03-71cae6145fdc");
        return properties;
    }

    /**
     * 通过location的值,填充scenicInfo
     *
     * @param detail
     * @param scenicInfo
     * @param city
     * @return
     */
    public ScenicInfo getScenicDetail(Location detail, ScenicInfo scenicInfo, AreaRelation city) {

        scenicInfo.setName(detail.getName());
        if (detail.getRating() != null) {
            scenicInfo.setStar((int) Float.parseFloat(detail.getRating()));
//        scenicInfo.setLevel((int) Float.parseFloat(detail.getRating()));
            scenicInfo.setScore((int) Float.parseFloat(detail.getRating()));
        }
        if (detail.getRanking_position() != null) {
            scenicInfo.setRanking(Integer.parseInt(detail.getRanking_position()));
        }
//        scenicInfo.setPrice(Integer.parseInt(detail.getRanking_denominator()));
//        scenicInfo.setTicket(Integer.parseInt(detail.getRanking_denominator()));
        scenicInfo.setIntro(detail.getDescription());
        if (detail.getPhoto() != null && detail.getPhoto().getImages() != null && detail.getPhoto().getImages().getLarge() != null)
            scenicInfo.setCover(detail.getPhoto().getImages().getLarge().getUrl());
//        scenicInfo.setFather(Integer.parseInt(detail.getRanking_denominator()));
        scenicInfo.setCityCode(city.getId().toString());
        scenicInfo.setCityId(city.getId().intValue());
//        scenicInfo.setHpl(detail.getSightDetailAggregate().getRating() / 5 * 100 + "%");
//        scenicInfo.setCommentNum(detail.getSightDetailAggregate().getCommentCount());
//        String img = QiniuUtil.UploadToQiniu(detail.getSightDetailAggregate().getImageCoverUrl().replace("_C_200_200", ""), "gallery/" + scenicInfo.getId() == null ? "" : String.valueOf(scenicInfo.getId()));
//        img = "http://7u2inn.com2.z0.glb.qiniucdn.com/" + img.replace(" ", "%20");
//        try {
//            String json = HttpUtil.HttpGetString(img + "?imageInfo");
//            if (!json.contains("error")) {
//                qiniuImageInfo imageInfo = new Gson().fromJson(json, qiniuImageInfo.class);
//                if (imageInfo.getWidth() == 640 && imageInfo.getHeight() == 320) {
//                    System.out.println(scenicInfo.getId() + "\t~~~" + img);
//                    scenicInfo.setCoverLarge(null);
//                } else {
//                    scenicInfo.setCoverLarge(img);
//                }
//            } else {
//                System.out.println(scenicInfo.getId() + "\t--- " + json);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        scenicInfo.setStatus(99);
//        scenicInfo.setDataSource("ctrip_app");
//        scenicInfo.setDataSourceId(detail.getSightDetailAggregate().getSightId());


        return scenicInfo;
    }

    /**
     * 通过location的值,填充scenicOther
     *
     * @param detail
     * @return
     */
    public ScenicOther getTbScenicOther(Location detail) {
        ScenicOther scenicOther = new ScenicOther();
        scenicOther.setAddress(detail.getAddress());
        scenicOther.setTelephone(detail.getPhone());
//        scenicOther.setHow(detail.getAddress());
        if (detail.getHours() != null && detail.getHours().getWeek_ranges() != null && detail.getHours().getWeek_ranges().size() > 0 && detail.getHours().getWeek_ranges().get(0).size() > 0)
            scenicOther.setOpenTime(detail.getHours().getWeek_ranges().get(0).get(0).getOpen_time() / 60 + ":00");
//        scenicOther.setAdviceTime(detail.getAddress());
//        scenicOther.setAdviceTimeDesc(detail.getAddress());
        scenicOther.setDescription(detail.getDescription());
        scenicOther.setTicketDesc(detail.getPrice());
//        scenicOther.setNotice(detail.getAddress());
//        scenicOther.setTrafficGuide(detail.getAddress());
        scenicOther.setWebsite(detail.getWebsite());
//        scenicOther.setCustom(detail.getAddress());
        if (detail.getReviews() != null && detail.getReviews().size() > 0)
            scenicOther.setRecommendReason(detail.getReviews().get(0).getSummary());
        scenicOther.setSource("tripAdvisor");
        scenicOther.setSourceId(detail.getLocation_id());
//        scenicOther.setCtripId(detail.getAddress());
        return scenicOther;
    }
}
