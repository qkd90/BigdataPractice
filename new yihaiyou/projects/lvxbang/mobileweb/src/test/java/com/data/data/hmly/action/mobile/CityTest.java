package com.data.data.hmly.action.mobile;

import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class CityTest extends BaseTest {
    /**
     * 3.1 城市列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        request.setParameter("keyword", "");
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/city/list.jhtml");
        proxy.execute();
    }

    /**
     * 3.2 热门目的地
     *
     * @throws Exception
     */
    public void testHotCity() throws Exception {
        ActionProxy proxy = getActionProxy("/city/hotCity.jhtml");
        CityWebAction cityWebAction = (CityWebAction) proxy.getAction();
        String r = proxy.execute();
        System.out.println(r);
        System.out.println(cityWebAction.keyword);
    }
}
