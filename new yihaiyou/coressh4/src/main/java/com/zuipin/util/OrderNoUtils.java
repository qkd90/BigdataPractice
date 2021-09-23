package com.zuipin.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Kingsley
 * @date 2012-5-18
 * @version $Revision$
 * 生成订单号,订单组成 日期 + 自动增长的序列号.
 */
public enum OrderNoUtils {
    
    NumIncrement;
    
    private AtomicLong               index = new AtomicLong(0);
    
    private SimpleDateFormat         sdf   = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private ScheduledFuture<?>       future;
    private ScheduledExecutorService service;
    
    private OrderNoUtils() {
        
        service = Executors.newScheduledThreadPool(1);
        
        future = service.scheduleWithFixedDelay(new Runnable() {
            
            @Override
            public void run() {
                // System.out.println("=================");
                // index.getAndSet(0);
            }
        }, 0, 2, TimeUnit.MINUTES);
        
    }
    
    public void shutdown() {
        future.cancel(true);
        service.shutdownNow();
    }
    
    public String getOrderNum() {
        return new StringBuilder(sdf.format(new Date())).append(index.getAndIncrement()).toString();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        final FileWriter writer = new FileWriter(new File("D:/a.txt"));
        ExecutorService service = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100000; i++) {
            service.submit(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String num = OrderNoUtils.NumIncrement.getOrderNum() + System.getProperty("line.separator");
                        writer.write(num);
                        // System.out.print(num);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        OrderNoUtils.NumIncrement.shutdown();
        service.awaitTermination(100, TimeUnit.SECONDS);
        writer.close();
    }
}
