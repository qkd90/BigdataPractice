package com.data.data.hmly.service.scenic;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.build.LvXBangBuildService;
import com.data.data.hmly.service.scenic.dao.ScenicInfoDao;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.hmly.service.translation.util.CoordinateUtil;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/11/26.
 */
@Service
public class ScenicInfoMgrService extends BaseService<ScenicInfo> {

    @Resource
    private ScenicInfoDao scenicDao;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private LvXBangBuildService lvXBangBuildService;
    @Resource
    private LabelItemService labelItemService;

    @Override
    public DataAccess<ScenicInfo> getDao() {
        return scenicDao;
    }

    public ScenicInfo info(Long id) {
        return scenicDao.load(id);
    }

    /**
     * 自动构建静态页面和自动索引
     *
     * @param scenicInfo
     */
    public void buildAndIndex(ScenicInfo scenicInfo) {
        // build 和 index
        lvXBangBuildService.buildOneScenic(scenicInfo.getId());
        lvXBangBuildService.buildScenicIndex();
        scenicInfoService.indexScenicInfo(scenicInfo);
    }

    public void indexScenicInfo(List<ScenicInfo> scenicInfos) {
        for (ScenicInfo scenicInfo : scenicInfos) {
            scenicInfoService.indexScenicInfo(scenicInfo);
        }
    }


    public void submit(long id) {
        ScenicInfo s = scenicDao.load(id);
        s.setStatus(2);
        scenicDao.update(s);
    }

    public List<Map<String, String>> listName(Map<String, Object> paramMap) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c = makeCriteria(paramMap, c);
        List<ScenicInfo> scenicInfos = scenicDao.findByCriteria(c);
        if (scenicInfos != null && scenicInfos.size() > 0) {
            for (ScenicInfo info : scenicInfos) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("scenicId", Long.toString(info.getId()));
                itemMap.put("scenicName", info.getName());
//                itemMap.put("fatherName", info.getFather().getName());
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    public List<Map<String, String>> listName3(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c = makeCriteria(paramMap, c);
        List<ScenicInfo> scenicInfos = scenicDao.findByCriteria(c);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        if (scenicInfos != null && scenicInfos.size() > 0) {
            for (ScenicInfo info : scenicInfos) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("scenicId", Long.toString(info.getId()));
                itemMap.put("scenicName", info.getName());
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    public List<Map<String, String>> listName2(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c.eq("status", 1);
        c = makeCriteria(paramMap, c);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        List<ScenicInfo> scenicInfos = scenicDao.findByCriteria(c);
//        paramMap.put("name", paramMap);
        if (scenicInfos != null && scenicInfos.size() > 0) {
            for (ScenicInfo info : scenicInfos) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("scenicId", Long.toString(info.getId()));
                itemMap.put("scenicName", info.getName());
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    public void del(String id) {
        scenicDao.delete(id, ScenicInfo.class);
    }

//    public List<ScenicOther> listRelation(Map<String, Object> paramMap) {
//        List<ScenicOther> scenicInfos = scenicDao.listRelation(paramMap);
//        return scenicInfos;
//    }

    public void updateWithDataScenic(ScenicInfo scenicUpdate) {
        scenicDao.update(scenicUpdate);
    }

    public ScenicInfo selById(Long id) {
        return scenicDao.load(id);
    }

    public List<Map<String, String>> listCoordinates(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c = makeCriteria(paramMap, c);
        c.createCriteria("scenicGeoinfo", "sg");
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        List<ScenicInfo> scenicInfos = scenicDao.findByCriteria(c);
        if (scenicInfos != null && scenicInfos.size() > 0) {
            for (ScenicInfo info : scenicInfos) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("scenicId", Long.toString(info.getId()));
                itemMap.put("scenicName", info.getName());
                itemMap.put("address", info.getScenicOther().getAddress());
                itemMap.put("longitude", info.getScenicGeoinfo().getBaiduLng().toString());
                itemMap.put("latitude", info.getScenicGeoinfo().getBaiduLat().toString());
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    public List<Map<String, String>> listGoogleCoordinates(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c = makeCriteria(paramMap, c);
        c.createCriteria("scenicGeoinfo", "sg");
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        List<ScenicInfo> scenicInfos = scenicDao.findByCriteria(c);
        if (scenicInfos != null && scenicInfos.size() > 0) {
            for (ScenicInfo info : scenicInfos) {
                Map<String, String> itemMap = new HashMap<String, String>();
                itemMap.put("scenicId", Long.toString(info.getId()));
                itemMap.put("scenicName", info.getName());
                itemMap.put("address", info.getScenicOther().getAddress());
                ScenicGeoinfo scenicGeoinfo = info.getScenicGeoinfo();
                if (info.getScenicGeoinfo().getGoogleLng() != null
                        && info.getScenicGeoinfo().getGoogleLat() != null) {
                    itemMap.put("longitude", scenicGeoinfo.getGoogleLng().toString());
                    itemMap.put("latitude", scenicGeoinfo.getGoogleLat().toString());
                } else {
                    Double gLng = CoordinateUtil.getGoogleLng(scenicGeoinfo.getBaiduLng(), scenicGeoinfo.getBaiduLat());
                    Double gLat = CoordinateUtil.getGoogleLat(scenicGeoinfo.getBaiduLng(), scenicGeoinfo.getBaiduLat());
                    itemMap.put("longitude", gLng.toString());
                    itemMap.put("latitude", gLat.toString());
                }
                resultList.add(itemMap);
            }
        }
        return resultList;
    }

    public List<ScenicInfo> selSeclist(Map<String, Object> map) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c.like("cityCode", map.get("cityCodeStr").toString());
        c.like("name", map.get("name").toString());
        c.eq("status", 1);
        return scenicDao.findByCriteria(c);
    }

    public void orderDown(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c.gt("ranking", Integer.parseInt(paramMap.get("oldOrder").toString()));
        c.lt("ranking", Integer.parseInt(paramMap.get("newOrder").toString() + 1));
        c = makeCriteria(paramMap, c);
        List<ScenicInfo> resultList = scenicDao.findByCriteria(c);
        for (ScenicInfo s : resultList) {
            s.setRanking(s.getRanking() - 1);
        }
        scenicDao.updateAll(resultList);
    }

    public void orderRise(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c.gt("ranking", Integer.parseInt(paramMap.get("newOrder").toString()) - 1);
        c.lt("ranking", Integer.parseInt(paramMap.get("oldOrder").toString()));
        c = makeCriteria(paramMap, c);
        List<ScenicInfo> resultList = scenicDao.findByCriteria(c);
        for (ScenicInfo s : resultList) {
            s.setRanking(s.getRanking() + 1);
        }
        scenicDao.updateAll(resultList);
    }

    public void updateOrder(Map<String, Object> paramMap) {
        ScenicInfo s = scenicDao.load(Long.parseLong(paramMap.get("id").toString()));
        s.setRanking(Integer.parseInt(paramMap.get("newOrder").toString()));
        scenicDao.update(s);
    }

    public ScenicInfo selByCtripId(String ctripId) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        DetachedCriteria dc = c.createCriteria("scenicOther", "se");
        dc.add(Restrictions.eq("ctripId", ctripId));
        List<ScenicInfo> resultList = scenicDao.findByCriteria(c);
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    public List<ScenicInfo> listByMap(Map<String, Object> paramMap) {
        Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
        c = makeCriteria(paramMap, c);
        return scenicDao.findByCriteria(c);
    }

    /**
     * 根据动态条件,动态构建hibernate Criteria标准查询
     *
     * @param paramMap
     * @return
     */
    @Override
    public Criteria<ScenicInfo> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicInfo> c) {
        c.orderBy("id", "asc");
        c.ne("status", -100);
        DetachedCriteria dcArea = c.createCriteria("city", "a");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name"))) {
            c.like("name", "%" + paramMap.get("name") + "%");
        }
        if (paramMap.containsKey("star") && !"".equals(paramMap.get("star"))) {
            c.eq("star", paramMap.get("star"));
        }
        if (paramMap.containsKey("level") && !"".equals(paramMap.get("level"))) {
            c.eq("level", paramMap.get("level"));
        }
        if (paramMap.containsKey("score") && !"".equals(paramMap.get("score"))) {
            c.eq("score", paramMap.get("score"));
        }
        if (paramMap.containsKey("ranking") && !"".equals(paramMap.get("ranking"))) {
            c.eq("ranking", paramMap.get("ranking"));
        }
        if (paramMap.containsKey("rankingLimit") && !"".equals(paramMap.get("rankingLimit"))) {
            c.le("ranking", Integer.parseInt(paramMap.get("rankingLimit").toString()));
        }
        if (paramMap.containsKey("price") && !"".equals(paramMap.get("price"))) {
            c.eq("price", paramMap.get("price"));
        }
        if (paramMap.containsKey("ticket") && !"".equals(paramMap.get("ticket"))) {
            c.eq("ticket", paramMap.get("ticket"));
        }
        if (paramMap.containsKey("intro") && !"".equals(paramMap.get("intro"))) {
            c.eq("intro", paramMap.get("intro"));
        }
        if (paramMap.containsKey("cover") && !"".equals(paramMap.get("cover"))) {
            c.eq("cover", paramMap.get("cover"));
        }
        if (paramMap.containsKey("father") && !"".equals(paramMap.get("father"))) {
            c.eq("father", paramMap.get("father"));
        }
        if (paramMap.containsKey("isThreeLevel") && !"".equals(paramMap.get("isThreeLevel"))) {
            c.eq("isThreeLevel", paramMap.get("isThreeLevel"));
        }
        if (paramMap.containsKey("cityCode") && !"".equals(paramMap.get("cityCode"))) {
            dcArea.add(Restrictions.like("cityCode", paramMap.get("cityCode") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dcArea.add(Restrictions.sqlRestriction("length(a2_.city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dcArea.add(Restrictions.sqlRestriction("length(a2_.city_code) = 7"));
            }
        }
        if (paramMap.containsKey("noArea") && Integer.parseInt(paramMap.get("noArea").toString()) == 1) {
            dcArea.add(Restrictions.like("cityCode", "%00"));
        }
        if (paramMap.containsKey("isShow") && !"".equals(paramMap.get("isShow"))) {
            c.eq("isShow", paramMap.get("isShow"));
        }
        if (paramMap.containsKey("status") && !"".equals(paramMap.get("status"))) {
            c.eq("status", Integer.parseInt(paramMap.get("status").toString()));
        }
        if (paramMap.containsKey("hasTaxi") && !"".equals(paramMap.get("hasTaxi"))) {
            c.eq("hasTaxi", paramMap.get("hasTaxi"));
        }
        if (paramMap.containsKey("hasBus") && !"".equals(paramMap.get("hasBus"))) {
            c.eq("hasBus", paramMap.get("hasBus"));
        }
        if (paramMap.containsKey("createTime") && !"".equals(paramMap.get("createTime"))) {
            c.eq("createTime", paramMap.get("createTime"));
        }
        if (paramMap.containsKey("modifyTime") && !"".equals(paramMap.get("modifyTime"))) {
            c.eq("modifyTime", paramMap.get("modifyTime"));
        }
        if (paramMap.containsKey("createdBy") && !"".equals(paramMap.get("createdBy"))) {
            c.eq("createdBy", paramMap.get("createdBy"));
        }
        if (paramMap.containsKey("modifiedBy") && !"".equals(paramMap.get("modifiedBy"))) {
            c.eq("modifiedBy", paramMap.get("modifiedBy"));
        }
        if (paramMap.containsKey("scenicType")) {
            ScenicInfoType scenicType = ScenicInfoType.valueOf(paramMap.get("scenicType").toString());
            c.eq("scenicType", scenicType);
        }
        if (paramMap.containsKey("orderStart") && paramMap.containsKey("orderEnd")
                && !"".equals(paramMap.get("orderStart"))
                && !"".equals(paramMap.get("orderEnd"))
                && Integer.parseInt(paramMap.get("orderStart").toString()) >= 0
                && Integer.parseInt(paramMap.get("orderEnd").toString()) > 0) {
            c.gt("ranking", Integer.parseInt(paramMap.get("orderStart").toString()));
            c.lt("ranking", Integer.parseInt(paramMap.get("orderEnd").toString()));
        }
        if (paramMap.containsKey("ids")) {
            String[] ids = (String[]) paramMap.get("ids");
            if (ids.length > 0) {
                c.in("id", ids);
            }
        }
        if (paramMap.containsKey("needCoverImage")) {
            c.isNotNull("cover");
            c.ne("cover", "");
        }
        if (paramMap.containsKey("notNeedCoverImage")) {
            c.or(Restrictions.isNull("cover"), Restrictions.eq("cover", ""));
        }
        if (paramMap.containsKey("hasFatherScenic") && Integer.parseInt(paramMap.get("hasFatherScenic").toString()) == 1) {
            c.isNotNull("father");
        }
        if (paramMap.containsKey("hasFatherScenic") && Integer.parseInt(paramMap.get("hasFatherScenic").toString()) == -1) {
            c.isNull("father");
        }
        DetachedCriteria dc = null;
        if (paramMap.containsKey("perfection") && StringUtils.isNotBlank((String) paramMap.get("perfection"))) {
            c.or(Restrictions.isNull("ticket"), Restrictions.eq("ticket", ""));
            if (dc == null) {   // 需要时再做关联
                dc = c.createCriteria("scenicOther", "se");
            }
            dc.add(Restrictions.or(Restrictions.eq("adviceTime", 0), Restrictions.isNull("adviceTime"),
                    Restrictions.eq("openTime", ""), Restrictions.isNull("openTime")));
        }
        if (paramMap.containsKey("hasAdvicehours") && Integer.parseInt(paramMap.get("hasAdvicehours").toString()) == 1) {
            if (dc == null) {   // 需要时再做关联
                dc = c.createCriteria("scenicOther", "se");
            }
            dc.add(Restrictions.and(Restrictions.ne("adviceTime", 0), Restrictions.isNotNull("adviceTime")));
        }
        if (paramMap.containsKey("hasAdvicehours") && Integer.parseInt(paramMap.get("hasAdvicehours").toString()) == -1) {
            if (dc == null) {   // 需要时再做关联
                dc = c.createCriteria("scenicOther", "se");
            }
            dc.add(Restrictions.or(Restrictions.eq("adviceTime", 0), Restrictions.isNull("adviceTime")));
        }
        if (paramMap.containsKey("hasAddress") && Integer.parseInt(paramMap.get("hasAddress").toString()) == -1) {
            if (dc == null) {   // 需要时再做关联
                dc = c.createCriteria("scenicOther", "se");
            }
            dc.add(Restrictions.or(Restrictions.eq("address", ""), Restrictions.isNull("address")));
        }
        if (paramMap.containsKey("hasAddressAndHours") && Integer.parseInt(paramMap.get("hasAddressAndHours").toString()) == -1) {
            if (dc == null) {   // 需要时再做关联
                dc = c.createCriteria("scenicOther", "se");
            }
            dc.add(Restrictions.and(Restrictions.or(Restrictions.eq("address", ""), Restrictions.isNull("address")), Restrictions.or(Restrictions.eq("adviceTime", 0), Restrictions.isNull("adviceTime"))));
        }
        return c;
    }

    public void delLabelItems(ScenicInfo scenicInfo) {
        labelItemService.delItemListByTargetIdId(scenicInfo.getId(), TargetType.SCENIC);
    }
}

