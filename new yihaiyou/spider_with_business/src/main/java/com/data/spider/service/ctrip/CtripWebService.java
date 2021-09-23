package com.data.spider.service.ctrip;

import com.data.spider.service.pojo.ctrip.CtripRegions;
import com.data.spider.service.pojo.ctrip.WebSearchResult;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/12/14.
 * 携程网页中的结果，
 * search:搜索返回SearchResult类型，其中包含汽车站、火车站、机场、酒店等结果
 * ；
 */
public class CtripWebService {
    private static String searchApi = "http://you.ctrip.com/SearchSite/Service/Tip2" +
            "?Jsoncallback=jQuery171027422445081174374_1450087835356" +
            "&keyword=%s&_=1450088009554";
    private static String regionsApi = "http://hotels.ctrip.com/domestic/tool/AjaxCityZoneNew.aspx?city=%d";
    private static String archiveApi = "http://web.archive.org/web/http://you.ctrip.com/sight/%s%d/%d.html";

    public WebSearchResult search(String keyword) {
        String url = String.format(searchApi, keyword);
        String result = getResponse(url);
        if (result == null) {
            return null;
        } else {
            result = result.substring(0, result.length() - 1);
            result = result.replaceAll("jQuery\\d+_\\d+\\(", "");

        }

        return new Gson().fromJson(result, WebSearchResult.class);
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

    public static String getAdviceTime(String cityPy, int cityId, int ctripId) {

        String html = getResponse(String.format(archiveApi, cityPy, cityId, ctripId));
        Pattern p = Pattern.compile(
                "(建议\\d+.*)[\r\n]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        if (m.find()) {
            return m.group(1).replace("建议", "");
        }
        return null;

    }


    public static CtripRegions getCtripRegions(int cityId) {
        String json = getResponse(String.format(regionsApi, cityId));
        if (json.length() < 23)
            return null;
        return new Gson().fromJson(json.substring(23, json.length()), CtripRegions.class);
    }

    public static List<CtripRegions.RegionEntity> getRegions(int cityId) {
        List<CtripRegions.RegionEntity> regionEntityList = new ArrayList<CtripRegions.RegionEntity>();
        CtripRegions ctripRegions = getCtripRegions(cityId);
        if (ctripRegions == null)
            return null;
        if (ctripRegions.getABCDE() != null) {
            regionEntityList.addAll(ctripRegions.getABCDE());
        }
        if (ctripRegions.getFGHJK() != null) {
            regionEntityList.addAll(ctripRegions.getFGHJK());
        }
        if (ctripRegions.getQRSTW() != null) {
            regionEntityList.addAll(ctripRegions.getQRSTW());
        }
        if (ctripRegions.getXYZ() != null) {
            regionEntityList.addAll(ctripRegions.getXYZ());
        }
        return regionEntityList;
    }


    public static void main(String[] args) {


    }
}
