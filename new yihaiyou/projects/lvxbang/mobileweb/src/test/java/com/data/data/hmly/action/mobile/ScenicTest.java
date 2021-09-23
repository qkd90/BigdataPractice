package com.data.data.hmly.action.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class ScenicTest extends BaseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 4.1 景点主题
     *
     * @throws Exception
     */
    public void testTheme() throws Exception {
        ActionProxy proxy = getActionProxy("/scenic/theme.jhtml");
        proxy.execute();
    }

    /**
     * 4.2 景点列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "");
        List<Long> cityIds = Lists.newArrayList(new Long(350200));
        map.put("cityIds", cityIds);
        map.put("theme", "");
        map.put("orderColumn", "ranking");
        map.put("orderType", "asc");
        request.setParameter("json", mapper.writeValueAsString(map));
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/scenic/list.jhtml");
        proxy.execute();
    }

    /**
     * 4.3 景点详情
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("id", "1059640");
        ActionProxy proxy = getActionProxy("/scenic/detail.jhtml");
        proxy.execute();
    }
}
