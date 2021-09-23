package com.data.data.hmly.service.weixinh5;

import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.weixinh5.dao.ProductActivitieDao;
import com.data.data.hmly.service.weixinh5.entity.Activities;
import com.data.data.hmly.service.weixinh5.entity.ProductActivity;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/2/17.
 */
@Service
public class ProductActivitieService {

    @Resource
    private ProductActivitieDao productActivitieDao;


    /**
     * 保存商品活动关联信息
     * @param productActivity
     */
    public void save(ProductActivity productActivity) {
        productActivitieDao.save(productActivity);
    }

    /**
     * 全选保存活动商品关联信息
     * @param activities
     * @param lineList
     */
    public void saveAll(Activities activities, List<Line> lineList) {
        List<ProductActivity> productActivityList = new ArrayList<ProductActivity>();
        productActivityList = productActivitieDao.getHasLineActivity(activities, lineList);
        for (Line line : lineList) {
            for (ProductActivity productActivity : productActivityList) {
                if (productActivity.getLine() == line) {
                    productActivitieDao.delete(productActivity);
                }
            }
            ProductActivity newPa = new ProductActivity();
            newPa.setLine(line);
            newPa.setActivitie(activities);
            productActivitieDao.save(newPa);
        }

    }
    /**
     * 全选保存活动商品关联信息
     * @param activities
     * @param lineList
     */
    public void cancelAll(Activities activities, List<Line> lineList) {
        List<ProductActivity> productActivityList = new ArrayList<ProductActivity>();
        productActivityList = productActivitieDao.getHasLineActivity(activities, lineList);
        productActivitieDao.deleteAll(productActivityList);
    }


    /**
     * 通过活动删除所有商品活动关联信息
     * @param activities
     */
    public void deleteByActivity(Activities activities) {
        productActivitieDao.deleteAll(findProductActivityByProduct(activities));
    }

    /**
     * 通过活动找到商品活动关联信息
     * @param activities
     * @return
     */
    public List<ProductActivity> findProductActivityByProduct(Activities activities) {

        Criteria<ProductActivity> criteria = new Criteria<ProductActivity>(ProductActivity.class);

        criteria.eq("activitie", activities);

        return productActivitieDao.findByCriteria(criteria);

    }



    /**
     * 删除单个活动商品关联信息
     * @param line
     */
    public void deleteByProduct(Line line, Activities activities) {
        productActivitieDao.delete(findProductActivityByProduct(line, activities));
    }

    /**
     * 通过商品line找到活动商品关联信息
     * @param line
     * @return
     */
    public ProductActivity findProductActivityByProduct(Line line, Activities activities) {
        Criteria<ProductActivity> criteria = new Criteria<ProductActivity>(ProductActivity.class);

        criteria.eq("line", line);
        criteria.eq("activitie", activities);

        return productActivitieDao.findUniqueByCriteria(criteria);
    }


    /**
     * 取出已经添加优惠券或限时抢购的产品
     * @param activities
     * @return
     */
    public List<Line> findProductByAid(Activities activities) {

        Criteria<ProductActivity> criteria = new Criteria<ProductActivity>(ProductActivity.class);

        criteria.eq("activitie", activities);

        List<ProductActivity> pActivityList = productActivitieDao.findByCriteria(criteria);

        return getLineByProductActivitiList(pActivityList);
    }


    public List<Line> getLineByProductActivitiList(List<ProductActivity> pActivityList) {
        List<Line> lineList = new ArrayList<Line>();

        for (ProductActivity pa : pActivityList) {
            lineList.add(pa.getLine());
        }
        return lineList;
    }

}
