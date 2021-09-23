package com.data.hmly.service.translation.util;

/**
 * Created by Sane on 16/4/22.
 */
public class CoordinateUtil {

    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static double getBaiduLng(double google_lat, double google_lng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(google_lng * google_lng + google_lat * google_lat) + 0.00002 * Math.sin(google_lat * xpi);
        double theta = Math.atan2(google_lat, google_lng) + 0.000003 * Math.cos(google_lng * xpi);
        return z * Math.cos(theta) + 0.0065;
    }

    public static double getBaiduLat(double google_lat, double google_lng) {
        double xpi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(google_lng * google_lng + google_lat * google_lat) + 0.00002 * Math.sin(google_lat * xpi);
        double theta = Math.atan2(google_lat, google_lng) + 0.000003 * Math.cos(google_lng * xpi);
        return z * Math.sin(theta) + 0.006;
    }


    public static double getGoogleLng(double baidu_lat, double baidu_lng) {
        double x = baidu_lat - 0.0065, y = baidu_lng - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return z * Math.cos(theta);
    }

    public static double getGoogleLat(double baidu_lat, double baidu_lng) {
        double x = baidu_lat - 0.0065, y = baidu_lng - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return z * Math.sin(theta);
    }
}
