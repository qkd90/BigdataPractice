package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.tb.DelicacyService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbDelicacy;
import com.data.spider.service.pojo.baidu.Destination.BaiduPoiSuggestion;
import com.data.spider.service.pojo.baidu.Dining.BaiduDining;
import com.data.spider.service.pojo.baidu.Poi.BaiduPoiList;
import com.data.spider.util.*;
import com.data.spider.service.baidu.BaiduDestinationService;
import com.data.spider.service.baidu.BaiduDiningService;
import com.data.spider.service.baidu.BaiduPoiService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 百度美食餐厅抓取
 */
public class BaiduDiningProcess extends BaseSpiderProcess {

    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
    private DelicacyService delicacyService = SpringContextHolder.getBean("delicacyService");
    public BaiduDiningProcess(Datatask datatask) {
        super(datatask);

    }

    @Override
    public Datatask call() throws Exception {

        String cityName = datatask.getName();//在url字段中存着城市名称
        Long cityId = Long.parseLong(datatask.getUrl());//在url字段中存着城市名称
        boolean b = getBaiduDinings(cityName, cityId);
        System.out.println("SUCCESS:" + b);
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

    private boolean getBaiduDinings(String cityName,Long cityId) {
        String sid = getSid(cityName);//根据城市名在百度旅游中搜索，获得sid
        BaiduDining dining = getDinings(sid);
        if(dining==null)
            return false;
        BaiduPoiList poiList = getPoiList(sid);
        if(dining==null)
            return false;
        if (dining != null) {
            BaiduDining.Food[] foods = dining.data.food;
            if(foods==null)
                return false;
            for (BaiduDining.Food food : foods) {
                List<TbDelicacy> delicacyExist = FindExists(food,cityId);
                if(delicacyExist.size()==0){
                    SaveDining(cityId, poiList, food);
                }
            }

        } else {
            return false;
        }
        return true;
    }

    private List<TbDelicacy> FindExists(BaiduDining.Food food,long cityId) {
        Criteria<TbDelicacy> c = new Criteria<TbDelicacy>(TbDelicacy.class);
        c.eq("foodName", food.key);
        c.eq("cityId", cityId);
        return delicacyService.gets(1,c);
    }

    private void SaveDining(Long cityId, BaiduPoiList poiList, BaiduDining.Food food) {
        TbDelicacy delicacy = new TbDelicacy();
        delicacy.setCityId(cityId);
        delicacy.setFoodName(food.key);
        delicacy.setIntroduction(food.desc);
        try {
//            delicacy.setFoodPicture(food.pic_url);
            String address = downLoadPic(food.pic_url,food.key);
            delicacy.setFoodPicture(address);
        }catch (Exception e){
            System.out.println(e);
        }

        if(food.stores!=null && food.stores.length >0){
            String poid = food.stores[0].poid;
            if(poiList!=null && poiList.data!=null )
            for (BaiduPoiList.Store store:poiList.data.stores){
                if (store.poid.equals(poid)){
                    delicacy.setPrice((double)store.price);
                    delicacy.setRecommendReson(store.reason);
                }
            }
        }
        delicacyService.save(delicacy);
    }

    private String downLoadPic(String pic_url,String delicacyName) {
//        byte[] bytes = downloadBytes(pic_url);
//        if(bytes==null)
//            return null;
//        String address = QiniuUtil.UploadToQiniu(delicacyName, pic_url, bytes,"delicacy");
        String address = QiniuUtil.UploadToQiniu( pic_url,"delicacy",delicacyName);
        return address;
    }
//
    private BaiduDining getDinings(String sid) {
        BaiduDiningService diningService = new BaiduDiningService();
        return diningService.getDinings(sid);
    }

    private BaiduPoiList getPoiList(String sid) {
        BaiduPoiService poiService = new BaiduPoiService();
        return poiService.getPoiList(sid);
    }

    private String getSid(String cityName) {
        String sid = "";
        BaiduDestinationService destinationService = new BaiduDestinationService();
        BaiduPoiSuggestion poiSuggestion = destinationService.getPoiSug(cityName);
        if (poiSuggestion != null) {
            sid = poiSuggestion.getData().getSuglist().get(0).getSid();
        }
        return sid;
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
