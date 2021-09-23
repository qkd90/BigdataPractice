package com.data.data.hmly.service.nctripticket.test;

import com.data.data.hmly.service.ctripcommon.enums.CtripTicketIcode;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by caiys on 2016/1/27.
 */
//@Ignore
public class CtripTicketApiServiceTest extends TestCase {
    final CtripTicketApiService service = new CtripTicketApiService();

    /**
     * 门票景点ID列表查询
     */
    public void testGetScenicSpotIdList() throws Exception {
       // String uuid = UUID.randomUUID().toString();
        /*JSONObject paramJson = new JSONObject();
        paramJson.put("action", CtripTicketIcode.SCENICSPOT_ID);*/
        List<Long> idList = service.doGetScenicSpotIdList();
        System.out.println("正常");
    }

    /**
     * 门票景点详情（门票资源）
     */
    public void testGetScenicSpotInfo() throws Exception {
//        String uuid = UUID.randomUUID().toString();
 //       List<Long> scenicSpotIDList = new ArrayList<Long>();
//        scenicSpotIDList.add(3757L);
//        scenicSpotIDList.add(1785962L);
//        scenicSpotIDList.add(1479393L);
//        scenicSpotIDList.add(1479941L);
        service.doGetScenicSpotInfo();
    }

    public void testGetResourcePriceCalendar() throws Exception {
//        String uuid = UUID.randomUUID().toString();
        List<Long> resourceIDList = Lists.newArrayList(209L, 212L,10000364L,10000983L,10000963L,10000364L,369L,368L,485L,487L,367L,6038L,6400L,366L,356L);
//        resourceIDList.add(0L);
//        resourceIDList.add(0L);
        Date startDate = DateUtils.date(2017, 7, 1);
        Date endDate = DateUtils.date(2017, 7, 31);
        service.doGetResourcePriceCalendar(resourceIDList, startDate, endDate);
    }

}
