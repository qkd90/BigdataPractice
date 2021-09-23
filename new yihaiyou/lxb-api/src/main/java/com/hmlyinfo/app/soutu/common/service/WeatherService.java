package com.hmlyinfo.app.soutu.common.service;

import com.hmlyinfo.app.soutu.common.XMLUtil;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/8/14.
 */
@Service
public class WeatherService {

    private static String SINA_WEATHER = "http://php.weather.sina.com.cn/xml.php?password=DJOYnieT8234jlsK";
    @Autowired
    private CityService cityService;

    @SuppressWarnings("unchecked")
    public String getWeather(String name, Map<String,Object> scenic, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR);
        if (day > 4) {
            return null;
        }
        try {
            //直接使用目的地名称获取天气，如果获取不到，改用对应城市
            String weatherResult = HttpUtil.postStrFromUrl(SINA_WEATHER + "&city=" + URLEncoder.encode(name, "GBK") + "&day=" + day, Collections.<String, String>emptyMap());
            Map<String, Object> map = XMLUtil.readStringXmlOut(weatherResult);
            if (!map.isEmpty()) {
                return renderWeather(calendar, map);
            }
            List<City> cityList = cityService.list(Collections.singletonMap("cityCode", scenic.get("cityCode")));
            if (cityList.isEmpty()) {
                return null;
            }
            String cityName = cityList.get(0).getName();
            if (cityName.contains("市")) {
                cityName = cityName.substring(0, cityName.indexOf("市"));
            }
            weatherResult = HttpUtil.postStrFromUrl(SINA_WEATHER + "&city=" + URLEncoder.encode(cityName, "GBK") + "&day=" + day, Collections.<String, String>emptyMap());
            map = XMLUtil.readStringXmlOut(weatherResult);
            if (!map.isEmpty()) {
                return renderWeather(calendar, map);
            }
            cityName = cityName.substring(0, 2);
            weatherResult = HttpUtil.postStrFromUrl(SINA_WEATHER + "&city=" + URLEncoder.encode(cityName, "GBK") + "&day=" + day, Collections.<String, String>emptyMap());
            map = XMLUtil.readStringXmlOut(weatherResult);
            if (!map.isEmpty()) {
                return renderWeather(calendar, map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String renderWeather(Calendar calendar, Map<String, Object> map) {
        Map<String, String> weather = (Map<String, String>) map.get("Weather");
        StringBuilder b = new StringBuilder();
        b.append(calendar.get(Calendar.MONTH) + 1).append("月").append(calendar.get(Calendar.DAY_OF_MONTH)).append("日天气：");
        if (StringUtil.isEmpty(weather.get("status2")) || weather.get("status2").equals(weather.get("status1"))) {
            b.append(encode(weather.get("status1")));
        } else {
            b.append(encode(weather.get("status1"))).append("转").append(encode(weather.get("status2")));
        }
        b.append("，");
        b.append(encode(weather.get("temperature1"))).append("℃").append("~").append(encode(weather.get("temperature2"))).append("℃");
        return b.toString();
    }

    public String encode(String str) {
        StringBuilder b = new StringBuilder();
        for (char c : str.toCharArray()) {
            b.append("=").append(Integer.toHexString((int) c));
        }
        return qpDecoding(b.toString());
    }

    public final String qpDecoding(String str) {
        if (str == null) {
            return "";
        }

        try {
            byte[] bytes = str.getBytes("US-ASCII");
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if (b != 95) {
                    bytes[i] = b;
                } else {
                    bytes[i] = 32;
                }
            }
            if (bytes == null) {
                return "";
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i];
                if (b == '=') {
                    try {
                        int u = Character.digit((char) bytes[++i], 16);
                        int l = Character.digit((char) bytes[++i], 16);
                        if (u == -1 || l == -1) {
                            continue;
                        }
                        buffer.write((char) ((u << 4) + l));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.write(b);
                }
            }
            return new String(buffer.toByteArray(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
