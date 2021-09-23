package com.data.spider.service.ctrip;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.spider.service.pojo.ctrip.Airport;
import com.data.spider.service.pojo.ctrip.CommentResultInfo;
import com.data.spider.service.pojo.ctrip.CtripImages;
import com.data.spider.service.pojo.ctrip.District;
import com.data.spider.service.pojo.ctrip.DistrictList;
import com.data.spider.service.pojo.ctrip.H5ScheduleDetail;
import com.data.spider.service.pojo.ctrip.H5ScheduleList;
import com.data.spider.service.pojo.ctrip.Image;
import com.data.spider.service.pojo.ctrip.PoiTip;
import com.data.spider.service.pojo.ctrip.RankDestInfo;
import com.data.spider.service.pojo.ctrip.Result;
import com.data.spider.service.pojo.ctrip.ScenicDetail;
import com.data.spider.service.pojo.ctrip.ScenicList;
import com.data.spider.service.pojo.ctrip.ScenicMoreInfo;
import com.data.spider.service.pojo.ctrip.SearchResult;
import com.data.spider.service.pojo.ctrip.Trainstation;
import com.data.spider.service.pojo.ctrip.TravelDetail;
import com.data.spider.service.pojo.ctrip.TravelList;
import com.data.spider.service.pojo.ctrip.TravelReplyList;
import com.data.spider.util.HttpClientUtils;
import com.data.spider.util.QiniuUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/9/28.
 * 携程app中的结果，
 * search:搜索返回SearchResult类型，其中包含汽车站、火车站、机场、酒店等结果
 * airport/trainstation:机场、火车站
 * ；
 */
public class CtripService {

    public static String searchApi = "http://m.ctrip.com/restapi/h5api/searchapp/search?" +
            "lon=-180.0&voice=f&source=globalapp68&fromHistory=t&keyword=%s" +
            "&clientID=32001048310005536757&action=autocomplete&client-system=android&districtid=0&lat=-180.0&cityid=0";
    public static String poiSearchApi = "http://m.ctrip.com/restapi/soa2/10200/PoiTip";
    public static String districtSearchPostParam = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.8.1\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.8.1\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"TipType\": [\"DISTRICT\"],\n" +  //, "SIGHT", "SHOP", "ENTERTAINMENT", "RESTAURANT"
            "\t\"Keyword\": \"%s\",\n" +
            "\t\"Count\": 5\n" +
            "}";
    public static String airportApi = "http://m.ctrip.com/restapi/soa2/10159/json/GetAirportGuideInfo";
    public static String airportPostParam = "{\"PoiId\":\"%s\",\"Lon\":0,\"Lat\":0,\"head\":{\"cid\":\"32001048310005536757\"" +
            ",\"ctok\":\"\",\"cver\":\"609.000\",\"lang\":\"01\",\"sid\":\"8080\",\"syscode\":\"32\",\"auth\":null}," +
            "\"contentType\":\"json\"}";

    public static String trainStationApi = "http://m.ctrip.com/restapi/soa2/10159/json/GetTrainStationGuideV660";
    public static String trainStationPostParam = "{\"PoiId\":\"%s\",\"Lon\":0,\"Lat\":0,\"head\":{\"cid\":\"32001048310005536757\"" +
            ",\"ctok\":\"\",\"cver\":\"609.000\",\"lang\":\"01\",\"sid\":\"8080\",\"syscode\":\"32\",\"auth\":null}," +
            "\"contentType\":\"json\"}";

    public static String h5ScheduleListApi = "http://m.ctrip.com/restapi/soa2/10120/json/GetH5ScheduleList?format=json";
    public static String h5ScheduleListParam = "{\"DistrictId\":\"%d\",\"CoverImageSize\":{\"Width\":600,\"Height\":339}," +
            "\"PagingParameter\":{\"PageSize\":20,\"PageIndex\":%d},\"head\":{\"cid\":\"32001048310005536757\",\"ctok\":" +
            "\"351858059049938\",\"cver\":\"609.002\",\"lang\":\"01\",\"sid\":\"8040\",\"syscode\":\"32\",\"auth\":null}," +
            "\"contentType\":\"json\"}";

    public static String h5ScheduleDetailApi = "http://m.ctrip.com/restapi/soa2/10120/json/GetH5ScheduleDetail?format=json";
    public static String h5ScheduleDetailParam = "{\"ClientDistrictId\":2,\"DistrictId\":\"61\",\"ScheduleInfoId\":\"%d\"," +
            "\"FrontCoverImageSize\":{\"Width\":640,\"Height\":320},\"PoiCoverImageSize\":{\"Width\":640,\"Height\":266}," +
            "\"head\":{\"cid\":\"32001048310005536757\",\"ctok\":\"351858059049938\",\"cver\":\"609.002\",\"lang\":\"01\",\"sid\":" +
            "\"8040\",\"syscode\":\"32\",\"auth\":null},\"contentType\":\"json\"}";

    public static String listApi = "http://m.ctrip.com/restapi/soa2/10159/GetSightListForAPP620";
    //    public static String intro_api = "http://m.ctrip.com/restapi/soa2/10159/json/GetMoreIntroductionForApp670";
    public static String detailApi = "http://m.ctrip.com/restapi/soa2/10159/GetSightDetailAggregate";
    public static String listParam = "{\n" +
            "\t\"OnlySightHotel\": false,\n" +
            "\t\"SortType\": 1,\n" +
            "\t\"OnlyPlay\": false,\n" +
            "\t\"OnlySale\": false,\n" +
            "\t\"Count\": 20,\n" +
            "\t\"DistrictId\": %d,\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.9.2\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.2\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"PageIndex\": %d\n" +
            "}";
    public static String detailParam = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.9.2\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.2\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"SightId\": %s\n" +
            "}";

    public static String districtListApi = "http://m.ctrip.com/restapi/soa2/10318/GetDistrictListForApp";
    public static String districtListPostJson = "{\n" +
            "\t\"QueryType\": \"world\",\n" +
            "\t\"DistrictType\": \"String\",\n" +
            "\t\"Count\": 5000,\n" +
            "\t\"OrderType\": \"Distance_Asc\",\n" +
            "\t\"ImageSizes\": [\"C_320_320\"],\n" +
            "\t\"RootDistrictId\": %d,\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.9.4\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.4\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"Coord\": {\n" +
            "\t\t\"Lat\": -180.0,\n" +
            "\t\t\"Lng\": -180.0\n" +
            "\t}\n" +
            "}";

    public static String rankDestInfoApi = "http://m.ctrip.com/restapi/soa2/10159/GetRankDestInfoByParentId";
    public static String rankDestInfoPostJson = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.9.4\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.4\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"StartIndex\": \"0\",\n" +
            "\t\"ParentDistrictId\": \"%d\",\n" +
            "\t\"Count\": \"5000\"\n" +
            "}";


    public static String districtHomeApi = "http://m.ctrip.com/restapi/soa2/10159/GetDistrictHomePageInfoInTravel";
    public static String districtHomePostJson = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"1.8.1\",\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"携程攻略\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.8.1\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920,17\"\n" +
            "\t\t}],\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"55559833\",\n" +
            "\t\t\"syscode\": \"app_android\"\n" +
            "\t},\n" +
            "\t\"DistrictId\": %d\n" +
            "}";
    public static String moreInfoApi = "http://m.ctrip.com/restapi/soa2/10159/json/gettraffictipphonesiteforapp670";

    public static String appCommonPostJson = "{\n" +
            "\t\"BusinessId\": \"%s\",\n" +
            "\t\"BusinessType\": 11,\n" +
            "\t\"head\": {\n" +
            "\t\t\"cid\": \"32001020110014225766\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"609.002\",\n" +
            "\t\t\"lang\": \"01\",\n" +
            "\t\t\"sid\": \"8080\",\n" +
            "\t\t\"syscode\": \"32\",\n" +
            "\t\t\"auth\": null\n" +
            "\t},\n" +
            "\t\"contentType\": \"json\"\n" +
            "}";
    public static String imageApi = "http://m.ctrip.com/restapi/soa2/10011/json/GetPoiImageList";

    public static String imagePostJson = "{\n" +
            "\t\"PoiType\": \"SIGHT\",\n" +
            "\t\"Start\": %s,\n" +
            "\t\"Count\": 18,\n" +
            "\t\"ImageSize\": [\"R_300_10000\"],\n" +
            "\t\"BusinessId\": \"%s\",\n" +
            "\t\"head\": {\n" +
            "\t\t\"cid\": \"32001048310005536757\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"608.002\",\n" +
            "\t\t\"lang\": \"01\",\n" +
            "\t\t\"sid\": \"8080\",\n" +
//            "\t\t\"syscode\": \"32\",\n" +
//            "\t\t\"auth\": null\n" +
            "\t\t\"syscode\": \"32\"\n" +
            "\t},\n" +
            "\t\"contentType\": \"json\"\n" +
            "}\n";


    public static String travelDetailForMobileApi = "http://m.ctrip.com/restapi/soa2/10129/GetTravelDetailForMobile.json";
    public static String travelDetailForMobilePostJson = "{\n" +
            "\t\"NoNeedContent\": false,\n" +
            "\t\"PictureWidth\": 640,\n" +
            "\t\"Replaced\": false,\n" +
            "\t\"head\": {\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"??虹????荤??\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.3\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"iPhone7,2, iOS 9.2\"\n" +
            "\t\t}]\n" +
            "\t},\n" +
            "\t\"Ids\": [%d],\n" +
            "\t\"PictureHeight\": 640,\n" +
            "\t\"CtripUid\": null\n" +
            "}";
    public static String travelListForMobileApi = "http://m.ctrip.com/restapi/soa2/10129/GetTravelListForMobile.json";
    public static String travelListForMobilePostJson = "{\n" +
            "\t\"TravelMonth\": [],\n" +
            "\t\"DistrictId\": %d ,\n" +
            "\t\"OrderByField\": 1,\n" +
            "\t\"PhotoHeight\": 0,\n" +
            "\t\"TagIds\": [],\n" +
            "\t\"CtripUid\": \"\",\n" +
            "\t\"PhotoWidth\": 0,\n" +
            "\t\"head\": {\n" +
            "\t\t\"extension\": [{\n" +
            "\t\t\t\"name\": \"gs_app_name\",\n" +
            "\t\t\t\"value\": \"��虹����荤��\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_version\",\n" +
            "\t\t\t\"value\": \"1.9.3\"\n" +
            "\t\t}, {\n" +
            "\t\t\t\"name\": \"gs_app_device\",\n" +
            "\t\t\t\"value\": \"iPhone7,2, iOS 9.2\"\n" +
            "\t\t}]\n" +
            "\t},\n" +
            "\t\"TravelDaysMin\": -1,\n" +
            "\t\"KeyWord\": \"\",\n" +
            "\t\"PageIndex\": %d ,\n" +
            "\t\"CompanionTypeList\": [],\n" +
            "\t\"TravelDaysMax\": -1,\n" +
            "\t\"PoiId\": 0,\n" +
            "\t\"PageSize\": 20,\n" +
            "\t\"RecommendTypeList\": [],\n" +
            "\t\"OrderByType\": 0\n" +
            "}";
    public static String travelCommentListForMobileApi = "http://m.ctrip.com/restapi/soa2/10129/GetTravelReplyListByUid";
    public static String travelCommentListForMobilePostJson = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"\",\n" +
            "\t\t\"extension\": [],\n" +
            "\t\t\"gs_app_device\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920 17\",\n" +
            "\t\t\"gs_app_name\": \"gs_android\",\n" +
            "\t\t\"gs_app_version\": \"1.0\",\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"\",\n" +
            "\t\t\"syscode\": \"\"\n" +
            "\t},\n" +
            "\t\"PageIndex\": %d,\n" +
            "\t\"PageSize\": 20,\n" +
            "\t\"Id\": %d\n" +
            "}";
    public static String commentListApi = "http://m.ctrip.com/restapi/soa2/10159/GetCommentResultInfo";
    public static String commentListPostJson = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"auth\": \"\",\n" +
            "\t\t\"cid\": \"\",\n" +
            "\t\t\"ctok\": \"\",\n" +
            "\t\t\"cver\": \"\",\n" +
            "\t\t\"extension\": [],\n" +
            "\t\t\"gs_app_device\": \"Sony Xperia Z - 4.2.2 - API 17 - 1080x1920 17\",\n" +
            "\t\t\"gs_app_name\": \"gs_android\",\n" +
            "\t\t\"gs_app_version\": \"1.0\",\n" +
            "\t\t\"lang\": \"\",\n" +
            "\t\t\"sid\": \"\",\n" +
            "\t\t\"syscode\": \"\"\n" +
            "\t},\n" +
            "\t\"CommentResultInfoEntity\": {\n" +
            "\t\t\"Uid\": \"\",\n" +
            "\t\t\"BusinessType\": 11,\n" +
            "\t\t\"PageIndex\": %d,\n" +
            "\t\t\"PageSize\": 20,\n" +
            "\t\t\"PoiId\": %d,\n" +
            "\t\t\"SortType\": 3,\n" +
            "\t\t\"StarType\": 0,\n" +
            "\t\t\"TouristType\": 0,\n" +
            "\t\t\"BusinessId\": %d,\n" +
            "\t\t\"UserId\": 0\n" +
            "\t}\n" +
            "}";

    public SearchResult search(String keyword) {
        String url = String.format(searchApi, keyword);
        String result = getResponse(url);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, SearchResult.class);
    }

    public PoiTip getPoiTip(String name) {
        String postJson = String.format(districtSearchPostParam, name);
        String result = postResponse(poiSearchApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, PoiTip.class);
    }

    public Airport getAirport(String poi) {
        String postJson = String.format(airportPostParam, poi);
        String result = postResponse(airportApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, Airport.class);
    }

    public List<H5ScheduleList.ResultsEntity> getH5ScheduleAll(int districtId) {
        int pageIndex = 1;
        List<H5ScheduleList.ResultsEntity> results = new ArrayList<H5ScheduleList.ResultsEntity>();
        do {
            H5ScheduleList h5ScheduleList = getH5ScheduleList(districtId, pageIndex);
            if (h5ScheduleList.isIsOriginQueryNoItem())
                return null;
            if (!h5ScheduleList.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            for (H5ScheduleList.ResultsEntity result : h5ScheduleList.getResults()) {
                results.add(result);
            }
            if (h5ScheduleList.getItemCount() == results.size() || h5ScheduleList.getPageCount() == pageIndex)
                break;
        } while (pageIndex++ < 100);
        return results;
    }


    public List<TravelList.ResultEntity> getTravelAll(int districtId) {
        int pageIndex = 1;
        List<TravelList.ResultEntity> results = new ArrayList<TravelList.ResultEntity>();
        do {
            TravelList travelList = getTravelList(districtId, pageIndex);
            if (!travelList.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            for (TravelList.ResultEntity result : travelList.getResult()) {
                results.add(result);
            }
            if (travelList.getTotalCount() == results.size())
                break;
        } while (pageIndex++ < 100);
        return results;
    }

    public List<TravelReplyList.ResultEntity> getTravelReplyAll(int travelId) {
        int pageIndex = 1;
        List<TravelReplyList.ResultEntity> results = new ArrayList<TravelReplyList.ResultEntity>();
        do {
            TravelReplyList travelReplyList = getTravelReplyList(pageIndex, travelId);
            if (!travelReplyList.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            for (TravelReplyList.ResultEntity result : travelReplyList.getResult()) {
                results.add(result);
            }
            if (travelReplyList.getTotalCount() == results.size())
                break;
        } while (pageIndex++ < 100);
        return results;
    }

    public List<Comment> getTravelReplyLimitTime(int ctripId, Date after, long lvxbangId) {
        int pageIndex = 1;
        long timeStamp = after.getTime();
        List<Comment> results = new ArrayList<Comment>();
        do {
            TravelReplyList commentResultInfo = getTravelReplyList(ctripId, pageIndex);
            if (!commentResultInfo.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            for (TravelReplyList.ResultEntity commentInfoEntity : commentResultInfo.getResult()) {
                long publishTime = Long.parseLong(commentInfoEntity.getReplyDate().substring(6, 19));
                if (timeStamp > publishTime) {
                    continue;
                }
                Comment comment = new Comment();
                comment.setContent(commentInfoEntity.getContent());
                comment.setTargetId(lvxbangId);
                comment.setType(ProductType.recplan);
                comment.setCreateTime(new Date(publishTime));
                comment.setStatus(CommentStatus.NORMAL);
                comment.setNickName(commentInfoEntity.getNickname());
                results.add(comment);
            }

            if (commentResultInfo.getTotalCount() < pageIndex * 20
                    || commentResultInfo.getTotalCount() <= results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    private TravelReplyList getTravelReplyList(int travelId, int pageIndex) {
        String postJson = String.format(travelCommentListForMobilePostJson, pageIndex, travelId);
        String result = postResponse(travelCommentListForMobileApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, TravelReplyList.class);
    }

    public List<TravelList.ResultEntity> getType1TravelAll(int districtId) {
        int pageIndex = 1;
        List<TravelList.ResultEntity> results = new ArrayList<TravelList.ResultEntity>();
        do {
            TravelList travelList = getTravelList(districtId, pageIndex);
            if (!travelList.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            if (travelList.getTotalCount() == 0 || travelList.getResult().size() == 0) {
                break;
            }
            for (TravelList.ResultEntity result : travelList.getResult()) {
                if (result.getTravelType() == 1) {
                    results.add(result);
                }
            }
            if (travelList.getTotalCount() == results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    public H5ScheduleList getH5ScheduleList(int districtId, int pageIndex) {
        String postJson = String.format(h5ScheduleListParam, districtId, pageIndex);
        String result = postResponse(h5ScheduleListApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, H5ScheduleList.class);
    }

    public H5ScheduleDetail getH5ScheduleDetail(int scheduleInfoId) {
        String postJson = String.format(h5ScheduleDetailParam, scheduleInfoId);
        String result = postResponse(h5ScheduleDetailApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, H5ScheduleDetail.class);
    }

    public Trainstation getTrainstation(String poi) {
        String postJson = String.format(trainStationPostParam, poi);
        String result = postResponse(trainStationApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, Trainstation.class);
    }


    public String getResponse(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        try {
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String postResponse(String url, String postJson) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        try {
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(postJson, "utf-8"));
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getScenicTraffic(String ctripId) {
        String result = appPostForResponse(moreInfoApi, appCommonPostJson, ctripId);
        return new Gson().fromJson(result, ScenicMoreInfo.class).getTraffic();
    }

    public static ScenicMoreInfo getScenicMoreInfo(String ctripId) {
        String result = appPostForResponse(moreInfoApi, appCommonPostJson, ctripId);
        return new Gson().fromJson(result, ScenicMoreInfo.class);
    }

    public static ScenicDetail getScenicDetail(String ctripId) {
        String result = appPostForResponse(detailApi, detailParam, ctripId);
        return new Gson().fromJson(result, ScenicDetail.class);
    }


    public List<ScenicList.SightListEntity> getScenicAll(int districtId) {
        int pageIndex = 1;
        List<ScenicList.SightListEntity> results = new ArrayList<ScenicList.SightListEntity>();
        do {
            ScenicList scenicList = getScenicList(districtId, pageIndex);
            if (!scenicList.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            results.addAll(scenicList.getSightList());
//            for (ScenicList.ResultEntity result : ) {
//            }
            if (scenicList.getTotalCount() <= results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    public ScenicList getScenicList(int districtId, int pageIndex) {
        String postJson = String.format(listParam, districtId, pageIndex);
        String result = postResponse(listApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, ScenicList.class);
    }


    public List<Comment> getCommentLimitTime(int poid, int businessId, Date after, long lvxbangId) {
        int pageIndex = 1;
        long timeStamp = after.getTime();
        List<Comment> results = new ArrayList<Comment>();
        do {
            CommentResultInfo commentResultInfo = getCommentList(poid, pageIndex, businessId);
            if (!commentResultInfo.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            for (CommentResultInfo.CommentResultEntity.CommentInfoEntity commentInfoEntity : commentResultInfo.getCommentResult().getCommentInfo()) {
                long publishTime = Long.parseLong(commentInfoEntity.getPublishTime().substring(6, 19));
                if (timeStamp > publishTime) {
                    continue;
                }
                Comment comment = new Comment();
                comment.setContent(commentInfoEntity.getContent());
                comment.setTargetId(lvxbangId);
                comment.setType(ProductType.scenic);
                comment.setCreateTime(new Date(publishTime));
                comment.setStatus(CommentStatus.NORMAL);
                comment.setNickName(commentInfoEntity.getUserInfoModel().getUserNick());
                int score = (int) (commentInfoEntity.getTotalStar() * 20);
                List<CommentScore> scores = new ArrayList<CommentScore>();
                for (long i = 9; i < 13; i++) {
                    CommentScore commentScore = new CommentScore();
                    commentScore.setScore(score);
                    CommentScoreType scoreType = new CommentScoreType();
                    scoreType.setId(i);
                    commentScore.setCommentScoreType(scoreType);
                    scores.add(commentScore);
                }
                List<CommentPhoto> photos = new ArrayList<CommentPhoto>();
                for (CommentResultInfo.CommentResultEntity.CommentInfoEntity.ImagesEntity imagesEntity : commentInfoEntity.getImages()) {
                    CommentPhoto photo = new CommentPhoto();
                    String address;
                    try {
                        address = QiniuUtil.UploadToQiniu(imagesEntity.getPhotoPath(), "comment", String.valueOf(comment.getId()));
                    } catch (Exception e) {
                        address = photo.getImagePath();
                        e.printStackTrace();
                    }
                    photo.setImagePath(address);
                    photos.add(photo);
                }
                comment.setCommentScores(scores);
                comment.setCommentPhotos(photos);
                results.add(comment);
            }

            if (commentResultInfo.getCommentResult().getTotalCount() < pageIndex * 20
                    || commentResultInfo.getCommentResult().getTotalCount() <= results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    public List<CommentResultInfo.CommentResultEntity.CommentInfoEntity> getCommentAll(int poid, int businessId) {
        int pageIndex = 1;
        List<CommentResultInfo.CommentResultEntity.CommentInfoEntity> results = new ArrayList<CommentResultInfo.CommentResultEntity.CommentInfoEntity>();
        do {
            CommentResultInfo commentResultInfo = getCommentList(poid, pageIndex, businessId);
            if (!commentResultInfo.getResponseStatus().getAck().endsWith("Success")) {
                break;
            }
            results.addAll(commentResultInfo.getCommentResult().getCommentInfo());
            if (commentResultInfo.getCommentResult().getTotalCount() <= results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }

    public CommentResultInfo getCommentList(int poid, int pageIndex, int businessId) {
        String postJson = String.format(commentListPostJson, pageIndex, poid, businessId);
        String result = postResponse(commentListApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, CommentResultInfo.class);
    }

    public static String appPostForResponse(String api, String postJson, String ctripId) {
        String result = "";
        HttpPost httpPost = new HttpPost(api);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        String nvps = String.format(postJson, ctripId);
        try {
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(nvps, "utf-8"));
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Image> getCtripImagesById(String ctripId, String imageStart) {
        List<Image> images = new ArrayList<Image>();
        String postJson = String.format(imagePostJson, imageStart, ctripId);
        String result = postForResponse(imageApi, postJson);
        CtripImages ctripImages = getEntityFromResponse(result);
        for (Result r : ctripImages.Result) {
            images.add(r.Image);
        }
        return images;
    }

    public static TravelDetail getTravelDetail(int ctripId) {
        String postJson = String.format(travelDetailForMobilePostJson, ctripId);
        String result = postForResponse(travelDetailForMobileApi, postJson);
        TravelDetail travelDetail = new Gson().fromJson(result.replaceAll("[^\\u0000-\\uFFFF]", "")
                , TravelDetail.class);
        return travelDetail;
    }

    public static TravelList getTravelList(int ctripCityId, int pageIndex) {
        String postJson = String.format(travelListForMobilePostJson, ctripCityId, pageIndex);
        String result = postForResponse(travelListForMobileApi, postJson);
        TravelList travelDetail = new Gson().fromJson(result, TravelList.class);
        return travelDetail;
    }

    public static CtripImages getEntityFromResponse(String result) {
        Gson gson = new Gson();
        return gson.fromJson(result, CtripImages.class);
    }

    public static String postForResponse(String api, String postJson) {
        String result = "";
        HttpPost httpPost = new HttpPost(api);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();

        try {
            httpPost.setEntity(new StringEntity(postJson, "utf-8"));
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


//    public static CtripService ctripService = new CtripService();
//    public static void main(String[] args) {
//        System.out.println(h5ScheduleDetailParam);
////        int ctripId = 21;
////        List<H5ScheduleList.ResultsEntity> results = ctripService.getH5ScheduleAll(ctripId);
////        System.out.println(new Gson().toJson(results));
//        H5ScheduleDetail detail = ctripService.getH5ScheduleDetail(503146);
//        System.out.println(new Gson().toJson(detail));
//    }


    public static double getBaiduLng(double googleLat, double googleLng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(googleLng * googleLng + googleLat * googleLat) + 0.00002 * Math.sin(googleLat * xpi);
        double theta = Math.atan2(googleLat, googleLng) + 0.000003 * Math.cos(googleLng * xpi);
        return z * Math.cos(theta) + 0.0065;
    }

    public static double getBaiduLat(double googleLat, double googleLng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(googleLng * googleLng + googleLat * googleLat) + 0.00002 * Math.sin(googleLat * xpi);
        double theta = Math.atan2(googleLat, googleLng) + 0.000003 * Math.cos(googleLng * xpi);
        return z * Math.sin(theta) + 0.006;
    }


    public District getDistrict(int ctripId) {
        String postJson = String.format(districtHomePostJson, ctripId);
        String result = postResponse(districtHomeApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, District.class);
    }

    public DistrictList getDistrictList(int ctripId) {
        String postJson = String.format(districtListPostJson, ctripId);
        String result = postResponse(districtListApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, DistrictList.class);
    }


    public RankDestInfo getRankDestInfo(int ctripId) {
        String postJson = String.format(rankDestInfoPostJson, ctripId);
        String result = postResponse(rankDestInfoApi, postJson);
        if (result == null) {
            return null;
        }
        return new Gson().fromJson(result, RankDestInfo.class);
    }


    public Map<String, String> getNoteImagesSize(int ctripId) {
        //,"BaseUrl":"http://p.chanyouji.cn/1443018144/A7B62454-F643-46EB-A4E9-A7A4890F568A.jpg","Url":"http://p.chanyouji.cn/1443018144/A7B62454-F643-46EB-A4E9-A7A4890F568A.jpg?imageView/2/w/640/h/320","SmallUrl":"http://p.chanyouji.cn/1443018144/A7B62454-F643-46EB-A4E9-A7A4890F568A.jpg?imageView/2/w/200/h/150","DynamicUrl":"http://p.chanyouji.cn/1443018144/A7B62454-F643-46EB-A4E9-A7A4890F568A.jpg?imageView/2/w/640/h/640","UserUrl":"http://you.ctrip.com/members/97120F66F6EE458E9775D15505C82C85","UserHeadPhoto":"http://a.chanyouji.cn/493049/1427009219.jpg","Witdh":1600,"Height":1600
        String postJson = String.format(travelDetailForMobilePostJson, ctripId);
        String json = postForResponse(travelDetailForMobileApi, postJson);
        Map<String, String> imagesSize = new HashMap<String, String>();
        Pattern p = Pattern.compile(
//                "\\{\"type\":\"image\",\"content\":\\{[^\\{}]+\"o_width\":(\\d+),\"o_height\":(\\d+),[^\\{}]+,\"oimgurl\":\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);
                ",\"BaseUrl\":\"([^\"]+)\".*?,\"Witdh\":(\\d+),\"Height\":(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(json);
        while (m.find()) {
            imagesSize.put(m.group(1).replace("\\", ""), m.group(2) + "__" + m.group(3));
        }
        return imagesSize;
    }
}
