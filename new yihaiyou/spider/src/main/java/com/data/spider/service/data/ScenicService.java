package com.data.spider.service.data;

import com.data.spider.service.dao.RegionDao;
import com.data.spider.service.dao.ScenicAreaDao;
import com.data.spider.service.dao.ScenicDao;
import com.data.spider.service.dao.ScenicInfoDao;
import com.data.spider.service.pojo.Region;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.service.pojo.ScenicArea;
import com.data.spider.service.pojo.ScenicInfo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class ScenicService {

    @Autowired
    private ScenicDao scenicDao;
    @Autowired
    private ScenicInfoDao scenicInfoDao;
    @Autowired
    private ScenicAreaDao scenicAreaDao;
    @Autowired
    private RegionDao regionDao;

    public void saveAll(List<Scenic> scenics) {
        scenicDao.save(scenics);
    }

    public void save(Scenic scenic) {
        scenicDao.save(scenic);
    }

    public void update(Scenic dis) {
        scenicDao.update(dis);
    }

    public List<Scenic> gets(int size) {
        Page page = new Page(1, size);
        Criteria<Scenic> c = new Criteria<Scenic>(Scenic.class);
        c.eq("status", -100);
        List<Scenic> dis = scenicDao.findByCriteriaWithOutCount(c, page); // 数据量大时，使用
        // count会执行缓慢
        return dis;
    }

    public List<Scenic> gets(int size, Criteria<Scenic> c) {
        Page page = new Page(1, size);
        List<Scenic> dis = scenicDao.findByCriteriaWithOutCount(c, page); // 数据量大时，使用
        // count会执行缓慢
        return dis;
    }

    public void saveScenicArea(String cityCode) {
        Map<Region, GeneralPath> map = new HashMap<Region, GeneralPath>();
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        Criteria<Region> c2 = new Criteria<Region>(Region.class);
        c.like("city_code", cityCode + "%");
        c.eq("status", 1);
        c2.eq("cityCode", Long.parseLong(cityCode + "00"));
        List<ScenicInfo> scenics = scenicInfoDao.findByCriteria(c);
        List<Region> areas = regionDao.findByCriteria(c2);
        for (Region area : areas) {
            System.out.println(area.getId() + "\t" + area.getName() + "\t" + area.getPoints());
            String[] pointsItemStr = area.getPoints().split("],");
            GeneralPath path = new GeneralPath();
            Point2D.Double first = null;
            for (int i = 0; i < pointsItemStr.length; i++) {
                String[] item = pointsItemStr[i].replace("[", "").replace("]", "").split(",");
                if (item[0].isEmpty() || item[1].isEmpty()) {

                    System.out.println(pointsItemStr[i] + "\t" + area.getId() + "\t" + area.getName() + "\t");
                    continue;
                }
                Point2D.Double point = new Point2D.Double(Double.parseDouble(item[0]), Double.parseDouble(item[1]));
                if (i > 0) {
                    path.lineTo(point.getX(), point.getY());
                } else {
                    path.moveTo(point.getX(), point.getY());
                    first = point;
                }
            }
            if (first == null)
                continue;
            path.lineTo(first.getX(), first.getY());
            map.put(area, path);
        }
        for (ScenicInfo scenic : scenics) {
            Point2D.Double point = new Point2D.Double(scenic.getLongitude(), scenic.getLatitude());
            for (Entry<Region, GeneralPath> entry : map.entrySet()) {
                if (entry.getValue().contains(point)) {
                    ScenicArea scenicArea = new ScenicArea();
                    Region area = entry.getKey();
                    scenicArea.setAreaId(area.getId());
                    scenicArea.setScenicId(scenic.getId());
                    scenicArea.setScenicName(scenic.getName());
                    scenicArea.setAreaName(area.getName());
                    scenicArea.setRanking(scenic.getOrder_num());
                    scenicAreaDao.save(scenicArea);
                    break;
                }
            }
        }
    }
}
