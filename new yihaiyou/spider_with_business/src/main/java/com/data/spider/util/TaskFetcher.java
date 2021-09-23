package com.data.spider.util;

import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public enum TaskFetcher {
    instance;
    private static ApplicationContext ac;
    private final static LinkedBlockingQueue<Datatask> tasks = new LinkedBlockingQueue<Datatask>();
    private final static LinkedBlockingQueue<Datatask> runtimetasks = new LinkedBlockingQueue<Datatask>();
    private DatataskService datataskService = SpringContextHolder.getBean("datataskService");
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private Log log = LogFactory.getLog(TaskFetcher.class);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private TaskFetcher() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
                // TODO Auto-generated method stub
                log.info(String.format("TaskSize %d Mutex %d Actived %d Queue %d", tasks.size(), Spider.mutex.getQueueLength(),
                        Spider.executorService.getActiveCount(), Spider.executorService.getQueue().size()));
                if (tasks.isEmpty()) {
                    fetchTask(); ////当队列中没有任务的时候,去取新的任务
                }
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

    public void fetchTask() {
//        Datatask newTask = new Datatask();
//        newTask.setClassname("fafaew");
//        datataskService.saveTask(newTask);


        List<Datatask> addtasks = datataskService.updateTasks();
        log.info(String.format("============= %s ================", addtasks.size()));
        for (Datatask datatask : addtasks) {
            try {
                tasks.put(datatask);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage(), e);
            }
        }
    }

    public Datatask getTask() {
        if (tasks.isEmpty()) {
            fetchTask();////当队列中没有任务的时候,去取新的任务
        }
        try {
            Datatask task = tasks.take();
            log.info(task.toString());
            return task;
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public void addTask(Datatask task) {
        // TODO Auto-generated method stub
        tasks.add(task);
    }

}
