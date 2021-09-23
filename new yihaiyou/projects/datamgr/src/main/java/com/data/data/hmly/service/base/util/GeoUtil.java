package com.data.data.hmly.service.base.util;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Created by Sane on 15/12/2.
 */
public class GeoUtil {

    /**
     * @param area
     * @param x
     * @param y
     * @return pointInArea
     */
    public static boolean pointInArea(GeneralPath area, double x, double y) {
        Point2D.Double xy = new Point2D.Double(x, y);
        return area.contains(xy);
    }

    /**
     * @param points "[118.093825,24.517004],[118.083476,24.501222],[118.089369,24.484912],[118.103167,24.481755],[118.128607,24.481887],[118.144561,24.491752],[118.146286,24.501222],[118.139962,24.510823],[118.138381,24.51753],[118.107766,24.527393],"
     * @return GeneralPath
     */
    public static GeneralPath getGeneralPath(String points){
        String[] pointsItemStr = points.split("],");
        GeneralPath path = new GeneralPath();
        Point2D.Double first = null;
        for (int i = 0; i < pointsItemStr.length; i++) {
            String[] item = pointsItemStr[i].replace("[", "").replace("]", "").split(",");
            Point2D.Double point = null;
            try {
                point = new Point2D.Double(Double.parseDouble(item[0]), Double.parseDouble(item[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i > 0) {
                path.lineTo(point.getX(), point.getY());
            } else {
                path.moveTo(point.getX(), point.getY());
                first = point;
            }
        }
        path.lineTo(first.getX(), first.getY());
        return path;
    }

}
