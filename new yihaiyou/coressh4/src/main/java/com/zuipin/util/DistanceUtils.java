package com.zuipin.util;

public class DistanceUtils {

	private static double	EARTH_RADIUS	= 6371.393; // 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000);
		return s;
	}

	public static void main(String[] args) {
		double lat1 = Double.parseDouble("24.45234");
		double lng1 = Double.parseDouble("118.073672");
		double lat2 = Double.parseDouble("24.750797");
		double lng2 = Double.parseDouble("118.163818");
		double start = System.currentTimeMillis();
		System.out.println(DistanceUtils.getDistance(lat1, lng1, lat2, lng2));
		System.out.println(System.currentTimeMillis() - start);
	}

}
