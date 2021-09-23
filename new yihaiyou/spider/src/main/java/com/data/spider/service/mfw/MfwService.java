package com.data.spider.service.mfw;

import com.data.spider.service.pojo.mfw.MddSearchResult;
import com.data.spider.service.pojo.mfw.MfwSdata;
import com.data.spider.service.pojo.mfw.PoiDetailResult;
import com.data.spider.service.pojo.mfw.PoiListResult;
import com.data.spider.service.pojo.mfw.TravelnoteDetail;
import com.data.spider.service.pojo.mfw.TravelnoteList;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/10/21.
 */
public class MfwService {

    private static String encoding = "UTF-8";
    private static String HTTP_METHOD_POST = "POST";
    private static String HTTP_METHOD_GET = "GET";
    private static String POI_IMAGE_API = "http://m.mafengwo.cn/nb/travelguide/poi_image.php";
    private static String POI_SEARCH_API = "http://mapi.mafengwo.cn/travelguide/sdata/data";
    private static String POI_WEB_API = "http://www.mafengwo.cn/poi/%d.html";
    private static String TRAVELNOTE_LIST_API = "http://m.mafengwo.cn/nb/travelguide/travelnote.php";
    //    private static String TRAVELNOTE_DETAIL_API = "http://mapi.mafengwo.cn/info/%d";
    private static String TRAVELNOTE_DETAIL_API = "http://mapi.mafengwo.cn/ginfo/%d";
    private static String MDD_SEARCH_API = "http://mapi.mafengwo.cn/travelguide/sdata/suggestions/mdds/10132";
    private static String POI_LIST_API = "http://mapi.mafengwo.cn/travelguide/poi/pois/mdds/%d";
    private static String POI_DETAIL_API = "http://mapi.mafengwo.cn/travelguide/poi/%d";

    public static String getAdviceTime(int mfwId) {
        String html = getResponse(String.format(POI_WEB_API, mfwId));
        Pattern p = Pattern.compile(
                "用时参考</span>\\n.*<p>(.*?)</p>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static String getAdviceTimeFromApp(int mfwId) {
        String html = getResponse(String.format(POI_WEB_API, mfwId));
        Pattern p = Pattern.compile(
                "用时参考</span>\\n.*<p>(.*?)</p>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static String getResponse(String url) {
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

    public static int getSearchResult(String name, String mdd) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("keyword", name);
            String url = MfwOAuthService.getRequestUrl(POI_SEARCH_API, HTTP_METHOD_GET, params);
//            System.out.println(url);
            String json = getResponse(url);
            MfwSdata sdata = new Gson().fromJson(json, MfwSdata.class);
            if (sdata == null || sdata.getData().getList() == null || sdata.getData().getList().get(0).getPois() == null) {
                return 0;
            }
            MfwSdata.DataEntity.ListEntity.PoisEntity poi = sdata.getData().getList().get(0).getPois().get(0);
            if (poi.getMdd().getName().equals(mdd)) {
                return poi.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static TravelnoteList getTravelnodes(String mddid, int index) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mddid", mddid);
        params.put("travelnote_type", "1");
        params.put("start", String.valueOf(index));
        params.put("type", "1");
        String url = MfwOAuthService.getRequestUrl(TRAVELNOTE_LIST_API, HTTP_METHOD_GET, params);
//            System.out.println(url);
        String json = getResponse(url);
        TravelnoteList travelnoteList = new Gson().fromJson(json, TravelnoteList.class);
        if (travelnoteList == null || travelnoteList.getData().getList() == null) {
            return null;
        }
        return travelnoteList;
    }

    public List<TravelnoteList.DataEntity.ListEntity> getTravelnodeAll(String mddid) {
        System.out.println("get travelNode:" + mddid);
        int pageIndex = 0;
        List<TravelnoteList.DataEntity.ListEntity> results = new ArrayList<TravelnoteList.DataEntity.ListEntity>();
        do {
            TravelnoteList travelnodes = getTravelnodes(mddid, pageIndex * 23);
            if (travelnodes.getData().getList() == null) {
                break;
            }
            if (travelnodes.getData().getTotal() == 0) {
                break;
            }
            for (TravelnoteList.DataEntity.ListEntity result : travelnodes.getData().getList()) {
                results.add(result);
            }
            if (travelnodes.getData().getTotal() < results.size() || travelnodes.getData().getHas_more() == 0)
                break;
        } while (pageIndex++ < 100);
        return results;
    }


    public TravelnoteDetail getTravelnoteDetail(int noteid) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_ver", "6.4.1");
        params.put("channel_id", "MFW");
        String url = MfwOAuthService.getRequestUrl(String.format(TRAVELNOTE_DETAIL_API, noteid), HTTP_METHOD_GET, params);
        String json = getResponse(url);
        json = json.replace("\"type\":\"container\",\"content\"", "\"type\":\"container\",\"container\"").replace("{\"type\":\"image\",\"content\"", "{\"type\":\"image\",\"image\"");
        TravelnoteDetail travelnoteDetail = new Gson().fromJson(json, TravelnoteDetail.class);
        if (travelnoteDetail == null || travelnoteDetail.getData().getContent() == null) {
            return null;
        }

        System.out.println(url);
        return travelnoteDetail;
    }

    public Map<String, String> getNoteImagesSize(int noteid) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_ver", "6.4.1");
        params.put("channel_id", "MFW");
        String url = MfwOAuthService.getRequestUrl(String.format(TRAVELNOTE_DETAIL_API, noteid), HTTP_METHOD_GET, params);
        String json = getResponse(url);
        Map<String, String> imagesSize = new HashMap<String, String>();
        Pattern p = Pattern.compile(
                "\\{\"type\":\"image\",\"content\":\\{[^\\{}]+\"o_width\":(\\d+),\"o_height\":(\\d+),[^\\{}]+,\"oimgurl\":\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(json);
        while (m.find()) {
            imagesSize.put(m.group(3).replace("\\", ""), m.group(1) + "__" + m.group(2));
        }
        return imagesSize;
    }

    public MddSearchResult searchMdd(String keyword) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("keyword", keyword);
        String url = MfwOAuthService.getRequestUrl(MDD_SEARCH_API, HTTP_METHOD_GET, params);
        String json = getResponse(url);
        MddSearchResult mdds = new Gson().fromJson(json, MddSearchResult.class);
        if (mdds == null || mdds.getData().getList() == null) {
            return null;
        }
        return mdds;
    }


    public PoiListResult getPoiList(int areaId, int start) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("start", String.valueOf(start));
        params.put("length", "15");
        params.put("type_id", "3");
        String url = MfwOAuthService.getRequestUrl(String.format(POI_LIST_API, areaId), HTTP_METHOD_GET, params);
        String json = getResponse(url);
        PoiListResult mdds = new Gson().fromJson(json, PoiListResult.class);
        if (mdds == null || mdds.getData().getList() == null) {
            return null;
        }
        return mdds;
    }

    public PoiDetailResult getPoiDetail(int poiId) {
        Map<String, String> params = new HashMap<String, String>();
        String url = MfwOAuthService.getRequestUrl(String.format(POI_DETAIL_API, poiId), HTTP_METHOD_GET, params);
        String json = getResponse(url);
        PoiDetailResult mdds = new Gson().fromJson(json, PoiDetailResult.class);
        if (mdds == null || mdds.getData() == null) {
            return null;
        }
        return mdds;
    }
}
