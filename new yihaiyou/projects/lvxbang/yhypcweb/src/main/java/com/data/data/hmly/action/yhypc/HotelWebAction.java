package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.CommentWebService;
import com.data.data.hmly.service.HotelWebService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.util.HTMLFilterUtils;
import com.data.data.hmly.service.hotel.HotelCityBrandService;
import com.data.data.hmly.service.hotel.HotelCityServiceService;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRegionService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelCityBrand;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/12/27.
 */
public class HotelWebAction extends JxmallAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private HotelSearchRequest searchRequest = new HotelSearchRequest();
    private Hotel hotel;
    private Long hotelId;
    private Integer pageIndex;
    private Integer pageSize;
    private String startDate;
    private String endDate;
    private String searchWord;
    private Long regionId;
    private Integer star;
    private Integer commentCount;

    @Resource
    private HotelService hotelService;
    @Resource
    private HotelWebService hotelWebService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private HotelCityBrandService hotelCityBrandService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CommentWebService commentWebService;



    public Result index() {
        setAttribute(YhyConstants.YHY_HOTEL_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_HOTEL_INDEX));
        return dispatch();
    }

    public Result detail() {
        if (hotelId != null) {
            hotel = hotelWebService.findAllDetailById(hotelId);
            commentCount = commentWebService.commentCount(hotel.getCommentList());
            if (hotel != null) {
                return dispatch();
            } else {
                return redirect("/yhypc/hotel/index.jhtml");
            }
        } else {
            return redirect("/yhypc/hotel/index.jhtml");
        }
    }

    public Result list() {
        try {
            if (StringUtils.hasText(searchWord)) {
                searchWord = URLDecoder.decode(searchWord, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dispatch();
    }

    public Result getListFilterData() {
        List<HotelRegion> hotelRegions = hotelRegionService.listByCity("350200");
        List<HotelCityBrand> hotelCityBrands = hotelCityBrandService.listByCity(350200);
        List<HotelCityService> hotelCityServices = hotelCityServiceService.listByCity(350200);
        result.put("hotelRegions", hotelRegions);
        result.put("hotelCityBrands", hotelCityBrands);
        result.put("hotelCityServices", hotelCityServices);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result countList() {
        Long count = hotelService.countFromSolr(searchRequest);
        return json(JSONArray.fromObject(count));
    }

    public Result getHotelList() {
        Page page = new Page(pageIndex, pageSize);
        if (searchRequest.getPriceRange() == null || searchRequest.getPriceRange().size() == 0) {
            searchRequest.setPriceRange(new ArrayList<Float>(Arrays.asList(0.001F)));
        }
        List<HotelSolrEntity> hotelSolrEntityList = hotelService.listFromSolr(searchRequest, page);
        for (HotelSolrEntity solrEntity : hotelSolrEntityList) {
            List<String> regionNameList = new ArrayList<String>();
            List<String> regionList = solrEntity.getRegion();
            if (regionList != null) {
                for (String rid : regionList) {
                    String regionName = hotelRegionService.getRegionNameById(Long.parseLong(rid));
                    if (StringUtils.hasText(regionName)) {
                        regionNameList.add(regionName);
                    }
                }
                solrEntity.setRegionNameList(regionNameList);
            }
        }
        return json(JSONArray.fromObject(hotelSolrEntityList));
    }

    public Result getHotelPrice() {
        if (hotelId != null) {
            HotelPrice condition = new HotelPrice();
            Hotel hotel = new Hotel();
            hotel.setId(hotelId);
            condition.setHotel(hotel);
            condition.setPriceStatusList(Arrays.asList(PriceStatus.UP, PriceStatus.GUARANTEE));
            if (StringUtils.hasText(startDate)) {
                Date start = DateUtils.getDate(startDate, "yyyy-MM-dd");
                condition.setStart(start);
            }
            if (StringUtils.hasText(endDate)) {
                Date end = DateUtils.getDate(endDate, "yyyy-MM-dd");
                condition.setEnd(end);
            }
            List<HotelPrice> hotelPrices = hotelPriceService.list(condition, null, null, "showOrder", "asc");
            for (HotelPrice hotelPrice : hotelPrices) {
                hotelPrice.setRoomDescription(HTMLFilterUtils.doHTMLFilter(hotelPrice.getRoomDescription()));
                Productimage productimage = productimageService.findCover(hotel.getId(), hotelPrice.getId(), ProductType.hotel);
                if (productimage != null) {
                    hotelPrice.setCover(productimage.getPath());
                }
                if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
                    hotelPrice.setStatusStr("担保");
                } else if (PriceStatus.UP.equals(hotelPrice.getStatus())) {
                    hotelPrice.setStatusStr(ProductSource.ELONG.equals(hotelPrice.getHotel().getSource()) ? "到付" : "预付");
                }
            }
            result.put("data", hotelPrices);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getNearByHotel() {
        final HttpServletRequest request = getRequest();
        String lngStr = request.getParameter("lng");
        String latStr = request.getParameter("lat");
        String disStr = request.getParameter("dis");
        if (StringUtils.hasText(lngStr) && StringUtils.hasText(latStr)) {
            Double lng = Double.parseDouble(lngStr);
            Double lat = Double.parseDouble(latStr);
            Float dis = Float.parseFloat(disStr);
            StringBuilder query = new StringBuilder();
            query.append("type:").append(SolrType.hotel);
            query.append(" AND ").append("price:{0 TO ").append(Integer.MAX_VALUE).append("]");
            List<HotelSolrEntity> hotelSolrEntities = hotelService.findNearByHotel(query.toString(), lng, lat, dis, new Page(0, 7));
            result.put("data", hotelSolrEntities.subList(1, 7));
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public HotelSearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(HotelSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = StringUtils.htmlEncode(startDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = StringUtils.htmlEncode(endDate);
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = StringUtils.htmlEncode(searchWord);
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
