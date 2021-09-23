package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteWebAction extends LxbAction {
    private static final long serialVersionUID = 900341852078393539L;
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private PlanService planService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private LineService lineService;
    private OtherFavorite otherFavorite;

    private Integer page = 1;
    private Integer rows = 10;

    // 返回数据
    Map<String, Object> map = new HashMap<String, Object>();
    private int allCount;
    private int planCount;
    private int recplanCount;
    private int scenicCount;
    private int hotelCount;
    private int delicacyCount;
    private List<Long> ids;


    /**
     * 添加收藏
     *
     * @return
     * @author caiys
     * @date 2015年12月24日 下午5:49:10
     */
    @AjaxCheck
    public Result addFavorite() {
        User user = getLoginUser();
        // 检查是否已经收藏
        ProductType type = otherFavorite.getFavoriteType();
        Long id = otherFavorite.getFavoriteId();
        boolean exists = otherFavoriteService.checkExists(type, id, user.getId());
        if (exists) {
            map.put("success", false);
            map.put("errorMsg", "你已经收藏过该商品");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        otherFavorite.setUserId(user.getId());
        otherFavorite.setCreateTime(new Date());
        otherFavorite.setDeleteFlag(false);

        Integer collectNum = 0;
        if (type.equals(ProductType.scenic)) {
            ScenicInfo scenicInfo = scenicInfoService.addCollect(id);
            collectNum = scenicInfo.getScenicOther().getCollectNum();
            otherFavorite.completeFavorite(scenicInfo);
        } else if (type.equals(ProductType.hotel)) {
            Hotel hotel = hotelService.addCollect(id);
            collectNum = hotel.getExtend().getCollectNum();
            otherFavorite.completeFavorite(hotel);
        } else if (type.equals(ProductType.delicacy)) {
            Delicacy delicacy = delicacyService.addCollect(id);
            collectNum = delicacy.getExtend().getCollectNum();
            otherFavorite.completeFavorite(delicacy);
        } else if (type.equals(ProductType.plan)) {
            Plan plan = planService.addCollect(id);
            collectNum = plan.getPlanStatistic().getCollectNum();
            otherFavorite.completeFavorite(plan);
        } else if (type.equals(ProductType.recplan)) {
            RecommendPlan recommendPlan = recommendPlanService.addCollect(id);
            collectNum = recommendPlan.getCollectNum();
            otherFavorite.completeFavorite(recommendPlan);
        } else if (type.equals(ProductType.line)) {
            Line line = lineService.addCollect(id);
            collectNum = line.getCollectSum();
            List<Line> list = lineService.completeLinePrice(Lists.newArrayList(line));
            if (!list.isEmpty()) {
                otherFavorite.completeFavorite(list.get(0));
            }
        }
        otherFavoriteService.doAddOtherFavorite(otherFavorite);

        map.put("success", true);
        map.put("errorMsg", "");
        map.put("collectNum", collectNum);
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }


    /**
     * 收藏夹页面
     *
     * @return
     * @author caiys
     * @date 2016年1月3日 下午2:07:30
     */
    public Result favorites() {
        User user = getLoginUser();
//        User user = new User();
//        user.setId(2L);
        List<Object> list = otherFavoriteService.groupByFavoriteType(user.getId());
        for (Object obj : list) {
            Object[] array = (Object[]) obj;
            if (array[1] == null) {
                continue;
            }
            if (ProductType.plan.toString().equals(array[0])) {
                planCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.recplan.toString().equals(array[0])) {
                recplanCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.scenic.toString().equals(array[0])) {
                scenicCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.hotel.toString().equals(array[0])) {
                hotelCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.delicacy.toString().equals(array[0])) {
                delicacyCount = ((BigInteger) array[1]).intValue();
            }
        }
        allCount = planCount + recplanCount + scenicCount + hotelCount + delicacyCount;

        return dispatch();
    }

    /**
     * 分页显示收藏夹
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:34:10
     */
    public Result page() {
        User user = getLoginUser();
        // 请求参数
        String favoriteType = (String) getParameter("favoriteType");
        String title = (String) getParameter("title");

        OtherFavorite of = new OtherFavorite();
        of.setTitle(title);
        of.setDeleteFlag(false);
        of.setUserId(user.getId());
        Page pageInfo = new Page(page, rows);
        List<OtherFavorite> list = new ArrayList<OtherFavorite>();
        if (StringUtils.isNotBlank(favoriteType)) {
            of.setFavoriteType(ProductType.valueOf(favoriteType));
        }
        list = otherFavoriteService.findOtherFavoriteList(of, pageInfo);
//		User user = new User();
//		user.setId(2L);
        pageInfo.setData(list);
        JSONObject json = JSONObject.fromObject(pageInfo);
        return json(json);
    }

    /**
     * 批量清除收藏夹
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:55:13
     */
    @AjaxCheck
    public Result batchClearFavorite() {
        User user = getLoginUser();
        // 请求参数
        String favoriteTypeStr = (String) getParameter("favoriteType");
        String favoriteIdStr = (String) getParameter("favoriteId");    // 不传删除对应类型的收藏

        ProductType favoriteType = null;
        if (StringUtils.isNotBlank(favoriteTypeStr)) {
            favoriteType = ProductType.valueOf(favoriteTypeStr);
        }
        Long favoriteId = null;
        if (StringUtils.isNotBlank(favoriteIdStr)) {
            favoriteId = Long.valueOf(favoriteIdStr);
        }

        Long userId = null;
        userId = user.getId();
        otherFavoriteService.doClearFavoriteBy(favoriteType, userId, favoriteId);

        Integer collectNum = 0;
        if (favoriteId != null) {
            if (favoriteType.equals(ProductType.scenic)) {
                collectNum = scenicInfoService.deleteCollect(favoriteId);
            } else if (favoriteType.equals(ProductType.hotel)) {
                collectNum = hotelService.deleteCollect(favoriteId);
            } else if (favoriteType.equals(ProductType.delicacy)) {
                collectNum = delicacyService.deleteCollect(favoriteId);
            } else if (favoriteType.equals(ProductType.plan)) {
                collectNum = planService.deleteCollect(favoriteId);
            } else if (favoriteType.equals(ProductType.recplan)) {
                collectNum = recommendPlanService.deleteCollect(favoriteId);
            } else if (favoriteType.equals(ProductType.line)) {
                collectNum = lineService.deleteCollect(favoriteId);
            }
        }

        map.put("success", true);
        map.put("errorMsg", "");
        map.put("collectNum", collectNum);
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 根据标识清除收藏夹
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:55:13
     */
    @AjaxCheck
    public Result clearFavorite() {
        // 请求参数
        String ids = (String) getParameter("ids");

        User user = getLoginUser();
//		User user = new User();
//		user.setId(2L);
        otherFavoriteService.doClearFavoriteBy(ids, user.getId());
        map.put("success", true);
        map.put("errorMsg", "");
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 检查是否已经收藏过
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:55:13
     */
    @AjaxCheck
    public Result checkExists() {
        User user = getLoginUser(false);
        // 请求参数
        String favoriteTypeStr = (String) getParameter("favoriteType");
        String favoriteId = (String) getParameter("favoriteId");

        ProductType favoriteType = null;
        if (StringUtils.isNotBlank(favoriteTypeStr)) {
            favoriteType = ProductType.valueOf(favoriteTypeStr);
        }
        Long id = Long.valueOf(favoriteId);
        boolean exists = false;
        if (user != null) {
            Long userId = null;
            userId = user.getId();
            exists = otherFavoriteService.checkExists(favoriteType, id, userId);
        }
        Integer collectNum = 0;
        if (favoriteType.equals(ProductType.scenic)) {
            collectNum = scenicInfoService.getCollectNum(id);
        } else if (favoriteType.equals(ProductType.hotel)) {
            collectNum = hotelService.getCollectNum(id);
        } else if (favoriteType.equals(ProductType.delicacy)) {
            collectNum = delicacyService.getCollectNum(id);
        } else if (favoriteType.equals(ProductType.plan)) {
            collectNum = planService.getCollectNum(id);
        } else if (favoriteType.equals(ProductType.recplan)) {
            collectNum = recommendPlanService.getCollectNum(id);
        } else if (favoriteType.equals(ProductType.line)) {
            collectNum = lineService.getCollectNum(id);
        }
        map.put("success", true);
        map.put("errorMsg", "");
        map.put("exists", exists);
        map.put("collectNum", collectNum);
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 批量验证收藏
     *
     * @return
     * @author huangpeijie
     * @date 2016-01-07
     */
    @AjaxCheck
    public Result batchCheckExists() {
        User user = getLoginUser();

        String favoriteTypeStr = (String) getParameter("favoriteType");

        ProductType favoriteType = null;
        if (StringUtils.isNotBlank(favoriteTypeStr)) {
            favoriteType = ProductType.valueOf(favoriteTypeStr);
        }

        List<OtherFavorite> favoriteList = otherFavoriteService.findOtherFavoriteBy(favoriteType, user.getId());
        List<Long> favoriteIds = new ArrayList<Long>();
        for (OtherFavorite favorite : favoriteList) {
            if (ids.contains(favorite.getFavoriteId())) {
                favoriteIds.add(favorite.getFavoriteId());
            }
        }
        map.put("success", true);
        map.put("errorMsg", "");
        map.put("ids", favoriteIds);
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    public OtherFavorite getOtherFavorite() {
        return otherFavorite;
    }

    public void setOtherFavorite(OtherFavorite otherFavorite) {
        this.otherFavorite = otherFavorite;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }


    public int getAllCount() {
        return allCount;
    }


    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }


    public int getPlanCount() {
        return planCount;
    }


    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }


    public int getRecplanCount() {
        return recplanCount;
    }


    public void setRecplanCount(int recplanCount) {
        this.recplanCount = recplanCount;
    }


    public int getScenicCount() {
        return scenicCount;
    }


    public void setScenicCount(int scenicCount) {
        this.scenicCount = scenicCount;
    }


    public int getHotelCount() {
        return hotelCount;
    }


    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }


    public int getDelicacyCount() {
        return delicacyCount;
    }


    public void setDelicacyCount(int delicacyCount) {
        this.delicacyCount = delicacyCount;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
