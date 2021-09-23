package com.zuipin.init;

import javax.servlet.ServletContext;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2013-9-23
 * @功能说明：全局环境初始化接口
 */
public interface ContextInit {
    /**
     * 初始化工作
     */
    public void init(ServletContext context);
    
    /**
     * 销毁
     */
    public void destroy();
}
