package spider;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PointInAreaUtils {

	public static Boolean pointInArea(Point2D.Double checkPoint, ArrayList<Point2D.Double> points) {
		GeneralPath p = new GeneralPath();
		if (points.size() < 3) {
			return false;
		}
		Point2D.Double first = points.get(0);
		p.moveTo(first.x, first.y);

		for (Point2D.Double d : points) {
			p.lineTo(d.x, d.y);
		}

		p.lineTo(first.x, first.y);

		p.closePath();

		return p.contains(checkPoint);
	}

}
