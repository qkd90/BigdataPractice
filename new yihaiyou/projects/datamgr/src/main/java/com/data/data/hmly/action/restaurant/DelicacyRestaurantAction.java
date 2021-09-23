package com.data.data.hmly.action.restaurant;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.AjaxList;
import com.data.data.hmly.service.base.CtripRestaurant;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.HttpClientUtils;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.restaurant.DelicacyMgrService;
import com.data.data.hmly.service.restaurant.DelicacyResMgrService;
import com.data.data.hmly.service.restaurant.RestaurantMgrService;
import com.data.data.hmly.service.restaurant.RestaurantService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyRestaurant;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.data.data.hmly.service.restaurant.entity.RestaurantExtend;
import com.data.data.hmly.service.restaurant.entity.RestaurantGeoInfo;
import com.data.data.hmly.service.scenic.DataCityService;
import com.data.data.hmly.service.scenic.entity.DataCity;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZZL on 2015/11/30.
 */
public class DelicacyRestaurantAction extends FrameBaseAction implements ModelDriven<Restaurant> {

    @Resource
    private DelicacyMgrService delicacyMgrService;
    @Resource
    private RestaurantMgrService restaurantMgrService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private DelicacyResMgrService delicacyResMgrService;
    @Resource
    private DataCityService dataCityService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private LvXBangBuildService lvXBangBuildService;

    private Restaurant restaurant = new Restaurant();

    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<Restaurant> result = restaurantMgrService.page(Restaurant.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("city", "extend", "geoInfo")));
    }

    public Result toList() {
        return dispatch("/WEB-INF/jsp/restaurant/restaurantList.jsp");
    }

    public Result getSimilar() {
        final HttpServletRequest request = getRequest();
        Long cityId = Long.parseLong(request.getParameter("cityId"));
        Long delicacyId = Long.parseLong(request.getParameter("delicacyId"));
        String keyword = request.getParameter("keyword");
        try {
            keyword = URLDecoder.decode(keyword, "UTF-8");
            int districtId = 0;
            DataCity dataCity = dataCityService.selById(cityId);
            if (dataCity != null) {
                districtId = dataCity.getCtripCityId();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("delicacyId", delicacyId);
            List<Long> ress = delicacyResMgrService.listResIds(map);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", ress);
            List<Restaurant> restaurants = restaurantMgrService.list(Restaurant.class, paramMap);
            request.setAttribute("relatieds", restaurants);
            request.setAttribute("cityId", cityId);
            request.setAttribute("delicacyId", delicacyId);
            request.setAttribute("districtId", districtId);
            request.setAttribute("keyword", keyword);
            return dispatch("/WEB-INF/jsp/restaurant/restaurantSimilar.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result getDiySimilar() {
        final HttpServletRequest request = getRequest();
        Long cityId = Long.parseLong(request.getParameter("cityId"));
        Long delicacyId = Long.parseLong(request.getParameter("delicacyId"));
        String keyword = request.getParameter("keyword");
        try {
            keyword = URLDecoder.decode(keyword, "UTF-8");
            int districtId = 0;
            DataCity dataCity = dataCityService.selById(cityId);
            if (dataCity != null) {
                districtId = dataCity.getCtripCityId();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("delicacyId", delicacyId);
            List<Long> ress = delicacyResMgrService.listResIds(map);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", ress);
            List<Restaurant> restaurants = restaurantMgrService.list(Restaurant.class, paramMap);
            request.setAttribute("relatieds", restaurants);
            request.setAttribute("cityId", cityId);
            request.setAttribute("delicacyId", delicacyId);
            request.setAttribute("districtId", districtId);
            request.setAttribute("keyword", keyword);
            return dispatch("/WEB-INF/jsp/restaurant/restaurantDiySimilar.jsp");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Result changeStatus() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("resId")) && StringUtils.isNotBlank(request.getParameter("status"))) {
            Long resId = Long.parseLong(request.getParameter("resId"));
            Integer status = Integer.parseInt(request.getParameter("status"));
            Restaurant restaurant = restaurantMgrService.selById(resId);
            restaurant.setStatus(status);
            restaurantMgrService.update(restaurant);
            delicacyResMgrService.updateDelRes(restaurant);
            result.put("msg", "success");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("msg", "error : no match parameter");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result add() {
        final HttpServletRequest request = getRequest();
        Restaurant restaurant = new Restaurant();
        request.setAttribute("restaurant", restaurant);
        return dispatch("/WEB-INF/jsp/restaurant/restaurantEdit.jsp");
    }

    public Result edit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        Restaurant restaurant = restaurantMgrService.selById(id);
        request.setAttribute("restaurant", restaurant);
        return dispatch("/WEB-INF/jsp/restaurant/restaurantEdit.jsp");
    }

    public Result saveRestaurant() throws IOException {
        final HttpServletRequest request = getRequest();
        if (restaurant.getId() != null && restaurant.getId().longValue() > 0L) {
            createRestaurant(request);
            restaurantMgrService.update(restaurant);
            // build 和 index
            delicacyResMgrService.updateDelRes(restaurant);
            return json(JSONObject.fromObject(ActionResult.createSuccess()));
        } else {
            restaurantMgrService.insert(restaurant);
            createRestaurant(request);
            restaurantMgrService.update(restaurant);
            // build 和 index
            delicacyResMgrService.updateDelRes(restaurant);
            return json(JSONObject.fromObject(ActionResult.createSuccess()));
        }
    }

    public Restaurant createRestaurant(final HttpServletRequest request) {
        Long cityCode = Long.parseLong(request.getParameter("cityCode"));
        TbArea tbArea = tbAreaService.getArea(cityCode);
        restaurant.setCity(tbArea);
        restaurant.setExtend(getRestaurantExtend(request));
        restaurant.setGeoInfo(getRestaurantGeoInfo(request));
        return restaurant;
    }

    public RestaurantExtend getRestaurantExtend(final HttpServletRequest request) {
        RestaurantExtend restaurantExtend = new RestaurantExtend();
        restaurantExtend.setRestaurant(restaurant);
        restaurantExtend.setAddress(request.getParameter("address"));
        restaurantExtend.setTelephone(request.getParameter("telephone"));
        restaurantExtend.setShopHours(request.getParameter("shopHours"));
        return restaurantExtend;
    }

    public RestaurantGeoInfo getRestaurantGeoInfo(final HttpServletRequest request) {
        RestaurantGeoInfo restaurantGeoInfo = new RestaurantGeoInfo();
        restaurantGeoInfo.setRestaurant(restaurant);
        restaurantGeoInfo.setBaiduLat(request.getParameter("baiduLat") == null
                || request.getParameter("baiduLat").equals("") ? 0 : Double.parseDouble(request.getParameter("baiduLat")));
        restaurantGeoInfo.setBaiduLat(request.getParameter("baiduLng") == null
                || request.getParameter("baiduLng").equals("") ? 0 : Double.parseDouble(request.getParameter("baiduLng")));
        restaurantGeoInfo.setBaiduLat(request.getParameter("googleLat") == null
                || request.getParameter("googleLat").equals("") ? 0 : Double.parseDouble(request.getParameter("googleLat")));
        restaurantGeoInfo.setBaiduLat(request.getParameter("googleLng") == null
                || request.getParameter("googleLng").equals("") ? 0 : Double.parseDouble(request.getParameter("googleLng")));
        return restaurantGeoInfo;
    }

    public Result saveRes() throws IOException {
        final HttpServletRequest request = getRequest();
        String relatieds = request.getParameter("relatieds");
        String ctripIds = request.getParameter("ctripIds");
        String diyIds = request.getParameter("diyIds");
        Long delicacyId = Long.parseLong(request.getParameter("delicacyId"));
        String cityId = request.getParameter("cityId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("delicacyId", delicacyId);
        List<DelicacyRestaurant> ress = delicacyResMgrService.list(DelicacyRestaurant.class, map);
        List<Long> relatiedList = new ArrayList<Long>();
        if (relatieds != null) {
            String[] relatiedArr = relatieds.split(",");
            for (String relatied : relatiedArr) {
                relatiedList.add(Long.parseLong(relatied));
            }
        }
        //清除被取消勾选的
        for (DelicacyRestaurant res : ress) {
            if (!relatiedList.contains(res.getRestaurant().getId()))
                delicacyResMgrService.del(res.getId(), DelicacyRestaurant.class);
        }
        if (diyIds != null) {
            String[] diyIdArr = diyIds.split(",");
            for (String diyId : diyIdArr) {
                if (!relatiedList.contains(Long.parseLong(diyId))) {
                    //插入关系
                    DelicacyRestaurant res = new DelicacyRestaurant();
                    Delicacy delicacy = delicacyMgrService.info(delicacyId);
                    res.setDelicacy(delicacy);
                    Restaurant restaurant = restaurantMgrService.selById(Long.parseLong(diyId));
                    res.setRestaurant(restaurant);
                    delicacyResMgrService.insert(res);
                }
            }
        }
        List<String> names = new ArrayList<String>();
        if (ctripIds != null) {
            String[] ctripIdArr = ctripIds.split(",");
            for (String ctripId : ctripIdArr) {
                Restaurant restaurant = restaurantMgrService.selBySourceId("ctrip_" + ctripId);
                //插入新餐厅
                if (restaurant == null) {
                    restaurant = getCtripRestaurant(ctripId, cityId);
                    restaurantMgrService.insert(restaurant);
                }
                if (restaurant == null) {
                    continue;
                }
                names.add(restaurant.getName());
                //插入关系
                DelicacyRestaurant res = new DelicacyRestaurant();
                Delicacy delicacy = delicacyMgrService.info(delicacyId);
                res.setDelicacy(delicacy);
                res.setRestaurant(restaurant);
                delicacyResMgrService.insert(res);
            }
        }
        ResultModel<String> result = new ResultModel<String>();
        int count = 10;
        result.setTotal(count);
        if (count > 0) {
            result.setRows(names);
        }
        return json(JSONObject.fromObject(result));

    }

    private Restaurant getCtripRestaurant(String ctripId, String cityId) {
        String ctrip_api = "http://m.ctrip.com/restapi/soa2/10332/GetRestaurantPageViewModel";
        String ctrip_param = "{\"RestaurantId\":\"" + ctripId + "\",\"ImageQuality\":\"0\",\"CurrentDestId\":21,\"Lon\":0,\"Lat\":0,\"" +
                "head\":{\"cid\":\"32001048310005536757\",\"ctok\":\"\",\"cver\":\"608.002\",\"lang\":\"01\",\"sid\":\"8080\"," +
                "\"syscode\":\"32\",\"auth\":null},\"contentType\":\"json\"}";

        Restaurant restaurant = new Restaurant();
        RestaurantGeoInfo restaurantGeoInfo = new RestaurantGeoInfo();
        RestaurantExtend restaurantExtend = new RestaurantExtend();
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response;
        HttpPost httpPost = new HttpPost(ctrip_api);
//        //抓包检查调试
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        try {
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("X-Requested-With", "ctrip.android.view");
            httpPost.setEntity(new StringEntity(ctrip_param, "utf-8"));
            response = httpClient.execute(httpPost);
            String ctripJson = EntityUtils.toString(response.getEntity(), "utf-8");
            CtripRestaurant ctripRestaurant = new Gson().fromJson(ctripJson, CtripRestaurant.class);
            TbArea tbArea = tbAreaService.getArea(Long.parseLong(cityId));
            restaurant.setCity(tbArea);
            restaurant.setName(ctripRestaurant.RestaurantViewModel.RestaurantName);
            restaurant.setPrice(ctripRestaurant.RestaurantViewModel.AveragePrice);
            double lat = ctripRestaurant.RestaurantViewModel.Lat;
            double lon = ctripRestaurant.RestaurantViewModel.Lon;
            restaurantGeoInfo.setBaiduLng(getBaiduLng(lat, lon));
            restaurantGeoInfo.setBaiduLat(getBaiduLat(lat, lon));
            restaurantExtend.setAddress(ctripRestaurant.RestaurantViewModel.Address);
            if (ctripRestaurant.RestaurantViewModel.BookTelList != null) {
                String tel = "";
                for (String t : ctripRestaurant.RestaurantViewModel.BookTelList) {
                    tel += t + " ";
                }
                restaurantExtend.setTelephone(tel);
            }
            restaurantExtend.setShopHours(ctripRestaurant.RestaurantViewModel.OpenTime);
            if (ctripRestaurant.RestaurantViewModel.Labels != null) {
                String feature = "";
                for (CtripRestaurant.Label l : ctripRestaurant.RestaurantViewModel.Labels) {
                    feature += l.Name + " ";
                }
                restaurant.setFeature(feature);
            }
            restaurant.setCover(UploadToQiniu(ctripRestaurant.RestaurantViewModel.ImageUrl));
//            restaurant.setResComment(ctripRestaurant.RestaurantViewModel.Description);
            restaurant.setScore(Double.parseDouble(String.valueOf(ctripRestaurant.RestaurantViewModel.GoodCommentRate)));
            restaurant.setSourceId("ctrip_" + ctripId);
            return restaurant;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String UploadToQiniu(String imageUrl) {
        byte[] bytes = downloadBytes(imageUrl);
        String filename = imageUrl;
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "restaurant/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(bytes, path);
        return path;
    }

    private byte[] downloadBytes(String sourceUrl) {
        HttpGet httpGet = new HttpGet(sourceUrl);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        try {
            response = httpClient.execute(httpGet);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private double getBaiduLng(double google_lat, double google_lng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(google_lng * google_lng + google_lat * google_lat) + 0.00002 * Math.sin(google_lat * xpi);
        double theta = Math.atan2(google_lat, google_lng) + 0.000003 * Math.cos(google_lng * xpi);
        return z * Math.cos(theta) + 0.0065;
    }

    private double getBaiduLat(double google_lat, double google_lng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(google_lng * google_lng + google_lat * google_lat) + 0.00002 * Math.sin(google_lat * xpi);
        double theta = Math.atan2(google_lat, google_lng) + 0.000003 * Math.cos(google_lng * xpi);
        return z * Math.sin(theta) + 0.006;
    }


    public Result upload() throws IOException {
        final HttpServletRequest request = getRequest();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        String restaurantName = request.getParameter("restaurantName");
        if (restaurantName == null || file == null || file.getSize() > 10000000) {
            return json(JSONObject.fromObject(AjaxList.createFail()));
        }
        String filename = file.getOriginalFilename();
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "delicacy/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(file.getBytes(), path);
        return json(JSONObject.fromObject(AjaxList.createSuccess(path)));
    }

    @Override
    public Restaurant getModel() {
        return restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
