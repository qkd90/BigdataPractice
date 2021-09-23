package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.amap.AmapDirectionService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.amap.AmapDirection;
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
public class AmapDisProcess extends BaseSpiderProcess {

    private TbDisService tbDisService = SpringContextHolder.getBean("tbDisService");

    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
    private List<String> keys = new ArrayList<String>();
    private static ObjectMapper om = new ObjectMapper();
    private static final String MODE_DRIVE = "driving";

    public AmapDisProcess(Datatask datatask) {
        super(datatask);
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String[] keySpi = propertiesManager.getString("AMAP_API_KEYS").split(",");
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
        boolean b = getAmapInfo(key, id);
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

    private boolean getAmapInfo(String key, long id) {
        List<TbDis> diss = tbDisService.getDis(id);

        TbDis dis = diss.get(0);
        if (dis.getTaxi_cost() != null) {
            return true;
        }
        dis.setOpt_status(-1);
        tbDisService.updateDis(dis);
//        return false;
        //TODO:将景点的百度坐标转换为高德坐标
        AmapDirection result = AmapDirectionService.getAmapTaxiDirectionWithBaiduCoordinate(MODE_DRIVE, key, dis.getS_lat(), dis.getS_lng(), dis.getE_lat(),
                dis.getE_lng());
        if (result == null) {
            return false;
        }
        if (result.getRoute() == null || result.getRoute().getPaths() == null) {
            return false;
        }
        AmapDirection.RouteEntity.PathsEntity path = result.getRoute().getPaths().get(0);
        String taxiDis = path.getDistance();
        String taxiCost = result.getRoute().getTaxi_cost();
        String taxiTime = path.getDuration();
        dis.setTaxi_cost(Integer.parseInt(taxiCost));
        dis.setTaxi_dis(Integer.parseInt(taxiDis));
        dis.setTaxi_time(Integer.parseInt(taxiTime));
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
