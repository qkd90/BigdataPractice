package com.data.data.hmly.action.lvxbang.sitemap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by huangpeijie on 2016-04-01,0001.
 */
public class SiteMapTimerListener implements ServletContextListener {
    private Timer timer = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Calendar calendar = Calendar.getInstance();

        //每天2点执行
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date date = calendar.getTime(); //第一次执行定时任务的时间

        //如果第一次执行定时任务的时间小于当前的时间
        //此时要在第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }

        timer = new Timer(true);
        sce.getServletContext().log("定时器已启动");

        //SiteMap生成
        //间隔时间1天生成一次
        timer.schedule(new SiteMap(sce.getServletContext()), date, 1 * 24 * 60 * 60 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
        sce.getServletContext().log("定时器销毁");
    }

    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
