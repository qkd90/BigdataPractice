package com.data.spider.service.baidu;

import com.data.spider.service.pojo.baidu.Destination.BaiduDestinationDetail;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.util.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/9/17.
 * 获取百度旅游中搜索地名返回的结果(包含sid、名称等信息)。
 */
public class BaiduDestinationService {

    private static final String poiSugApi = "http://lvyou.baidu.com/destination/app/poisug?" +
            "apiv=v3&lv_map=0&word=%s" +
            "&m=ac3ef160b0f8f6f88e9996079b5eedee&netTpye=wifi&LVCODE=%s&T=%d" +
            "&locEnabled=YES&locType=GPS";

    private static final String DestinationDetailApi = "http://lvyou.baidu.com/destination/app/detail?" +
            "apiv=v1&sid=%s" +
            "&mods[]=abs&mods[]=pictravels&mods[]=new_geography_history&mods[]=map_album" +
            "&m=ac3ef160b0f8f6f88e9996079b5eedee&netTpye=wifi&LVCODE=%s&T=%d" +
            "&locEnabled=YES&locType=GPS";

    public static BaiduPoiSuggestion getPoiSug(String q) {
        long timeStamp = System.currentTimeMillis() / 1000;
        String url = String.format(poiSugApi, q, BaiduLVCodeService.getLvCode(timeStamp), timeStamp);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            BaiduPoiSuggestion baiduPoiSuggestion = new Gson().fromJson(baiduStr, BaiduPoiSuggestion.class);
            return baiduPoiSuggestion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BaiduDestinationDetail getDestinationDetail(String sid) {
        long timeStamp = System.currentTimeMillis() / 1000;
        String url = String.format(DestinationDetailApi, sid, BaiduLVCodeService.getLvCode(timeStamp), timeStamp);
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpResponse response;
        try {
            response = httpClient.execute(new HttpGet(url));
            String baiduStr = EntityUtils.toString(response.getEntity(), "utf-8");
            BaiduDestinationDetail baiduDestinationDetail = new Gson().fromJson(baiduStr, BaiduDestinationDetail.class);
            return baiduDestinationDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }


    public static void main(String[] args) {
        BaiduPoiSuggestion poiSuggestion = new BaiduDestinationService().getPoiSug("厦门");
        if (poiSuggestion != null) {
            for (BaiduPoiSuggestion.DataEntity.SuglistEntity suglist : poiSuggestion.getData().getSuglist()) {

                System.out.println(suglist.getSname() + "\t" + suglist.getSid());
                if (suglist.getScene_layer().equals("4")) {
                    BaiduDestinationDetail destinationDetail = getDestinationDetail(suglist.getSid());
                    if (destinationDetail.getData().getNew_geography_history() == null)
                        continue;
                    for (BaiduDestinationDetail.DataEntity.NewGeographyHistoryEntity.ListEntity listEntity : destinationDetail.getData().getNew_geography_history().getList()) {
                        System.out.println(listEntity.getKey());
                        System.out.println(listEntity.getDesc());
                        System.out.println("");
                    }
                }

            }
        }
    }

}
