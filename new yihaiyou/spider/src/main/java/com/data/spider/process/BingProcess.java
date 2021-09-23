package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.bing.BingRoutesService;
import com.data.spider.service.bing.pojo.BingRoutesResult;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbDis;
import com.data.spider.service.tb.TbDisService;
import com.data.spider.util.BaseSpiderProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 高德交通信息（两点距离时间花费）
 */
public class BingProcess extends BaseSpiderProcess {

    private TbDisService tbDisService = SpringContextHolder.getBean("tbDisService");

    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
    private List<String> keys = new ArrayList<String>();
    private static ObjectMapper om = new ObjectMapper();
    private static final String MODE_DRIVE = "driving";

    public BingProcess(Datatask datatask) {
        super(datatask);
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String[] keySpi = propertiesManager.getString("BING_API_KEYS").split(",");
        for (String key : keySpi) {
            keys.add(key);
        }
        KeyMutex.instance.setKeys(keys);

    }

    @Override
    public Datatask call() throws Exception {
        int index = KeyMutex.instance.getDayKey();
        String key = keys.get(index);
        System.err.println(index + "key:" + key);
        long id = datatask.getId();
        boolean b = getBingInfo(key, id);
        if (b) {
            datatask.setStatus(DatataskStatus.SUCCESSED);
        } else {
            datatask.setStatus(DatataskStatus.FAILED);
        }
        if (datatask.getMadeby() == MakeBy.DB) {
            datataskService.updateTask(datatask);
        }
        return datatask;
    }

    private boolean getBingInfo(String key, long id) {
        List<TbDis> diss = tbDisService.getDis(id);

        TbDis dis = diss.get(0);
        if (dis.getTaxi_cost() != null) {
            return true;
        }
        dis.setOpt_status(-1);
        tbDisService.updateDis(dis);
//        return false;
        BingRoutesResult result = BingRoutesService.getBingRoutesWithBaiduCoordinate(dis.getS_lat(), dis.getS_lng(), dis.getE_lat(),
                dis.getE_lng(), key);
        if (result == null || result.getResourceSets() == null) {
            return false;
        }
        BingRoutesResult.ResourceSetsEntity.ResourcesEntity route1 = result.getResourceSets().get(0).getResources().get(0);
        System.out.println("距离:" + route1.getTravelDistance() + route1.getDistanceUnit());

        if (route1 == null || result.getResourceSets() == null || result.getResourceSets().get(0).getResources() == null) {
            return false;
        }

//        dis.setTaxi_cost(route1.getRouteLegs().get(0).getCost());
        dis.setTaxi_dis((int) (route1.getTravelDistance() * ("Kilometer".equals(route1.getDistanceUnit()) ? 1000 : 1)));
        dis.setTaxi_time(route1.getTravelDurationTraffic() * ("Second".equals(route1.getDurationUnit()) ? 1 : 60));
        dis.setOpt_status(1);
        tbDisService.updateDis(dis);
        return true;


    }


    @Override
    public String getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Semaphore getMutex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getXmlName() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {

    }

}
