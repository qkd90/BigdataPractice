package com.data.spider.service.tripAdvisor;

import com.data.spider.util.HttpsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 16/2/16.
 */
public class TripAdvisorService {
    private static String worldApi = "https://cn.tripadvisor.com/AllLocations-g1-Places-World.html";

    public Map<String, String> getWorldPlaces() {
        return getPlacesFromPage(worldApi);
    }

    public Map<String, String> getPlacesFromPage(String url) {
        Map<String, String> links = new HashMap<String, String>();
        try {
            String html = HttpsUtil.Send("GET", url, null);
            //<a href="/AllLocations-g660183-Places-Antarctic_Peninsula.html">Antarctic Peninsula的地点</a>
            Pattern p = Pattern.compile("<a href=\"(/AllLocations.*?\\.html)\">(.*?的地点)</a>");
            Matcher m = p.matcher(html);
            while (m.find()) {
//                System.out.println(m.group(1));
                links.put(m.group(1), m.group(2).replace("的地点", ""));
            }

            //
            Pattern p1 = Pattern.compile("<td style=\"width: 25%; padding-bottom:  2px; \">\n" +
                    "<a href=\"(/Tourism.*?\\.html)\">(.*?)</a>\n" +
                    "</td>");
            Matcher m1 = p1.matcher(html);
            while (m1.find()) {
//                System.out.println(m.group(1));
                links.put(m1.group(1), m1.group(2).replace("的地点", ""));
            }
            return links;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getRegionsFromPage(String url) {
        Map<String, String> links = new HashMap<String, String>();
        try {
            String html = HttpsUtil.Send("GET", url, null);
            //<a href="/Tourism-g1581025-Tongliang_County-Vacations.html">铜梁县</a>
            Pattern p = Pattern.compile("<a href=\"(/Tourism.*?\\.html)\">(.*?)</a>");
            Matcher m = p.matcher(html);
            while (m.find()) {
//                System.out.println(m.group(1));
                links.put(m.group(1), m.group(2));
            }
            return links;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
