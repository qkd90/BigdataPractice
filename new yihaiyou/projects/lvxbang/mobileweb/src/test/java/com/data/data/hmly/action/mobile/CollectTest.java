package com.data.data.hmly.action.mobile;

import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;


/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class CollectTest extends BaseTest {
    /**
     * 9.1 添加收藏
     *
     * @throws Exception
     */
    public void testSave() throws Exception {
        request.setParameter("userId", "100722");
        request.setParameter("targetId", "148011");
        request.setParameter("targetType", "recplan");
        ActionProxy proxy = getActionProxy("/collect/save.jhtml");
        proxy.execute();
    }

    /**
     * 9.2 验证收藏
     *
     * @throws Exception
     */
    public void testCheck() throws Exception {
        request.setParameter("userId", "100722");
        request.setParameter("targetId", "14801");
        request.setParameter("targetType", "recplan");
        ActionProxy proxy = getActionProxy("/collect/check.jhtml");
        proxy.execute();
    }

    /**
     * 9.3 收藏列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        request.setParameter("userId", "100722");
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/collect/list.jhtml");
        proxy.execute();
    }

    /**
     * 9.4 删除收藏
     *
     * @throws Exception
     */
    public void testDelete() throws Exception {
        request.setParameter("userId", "100722");
        request.setParameter("targetId", "148011");
        request.setParameter("targetType", "recplan");
        ActionProxy proxy = getActionProxy("/collect/delete.jhtml");
        proxy.execute();
    }
}
