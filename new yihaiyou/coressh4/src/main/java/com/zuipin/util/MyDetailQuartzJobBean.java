package com.zuipin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * @author Kingsley
 * @version Revision 2.0.0
 * @版权：象屿商城 版权所有 (c) 2012
 * @email:wxlong@xiangyu.cn
 * @see:
 * @创建日期：2013-6-8
 * @功能说明：集群定时器互斥
 */
public class MyDetailQuartzJobBean extends QuartzJobBean {

    private String quartzJob;
    private String targetMethod;
    private final static Log logger = LogFactory.getLog(MyDetailQuartzJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext ct) throws JobExecutionException {
        // TODO Auto-generated method stub
        try {

            logger.info("execute [" + quartzJob + "] at once>>>>>>");
            Object otargetObject = SpringContextHolder.getBean(quartzJob);
            Method m = null;
            try {
                m = otargetObject.getClass().getMethod(targetMethod, new Class[]{});

                m.invoke(otargetObject, new Object[]{});
            } catch (SecurityException e) {
                logger.error(e);
            } catch (NoSuchMethodException e) {
                logger.error(e);
            }

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }


    public void setQuartzJob(String quartzJob) {
        this.quartzJob = quartzJob;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

}
