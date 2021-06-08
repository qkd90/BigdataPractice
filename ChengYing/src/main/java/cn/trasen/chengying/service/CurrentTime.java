package cn.trasen.chengying.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rq
 */
public class  CurrentTime {
    public static String currentTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return sdf.format(d);
    }
}
