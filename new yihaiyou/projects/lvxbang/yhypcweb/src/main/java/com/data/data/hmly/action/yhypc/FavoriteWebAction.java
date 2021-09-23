package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.HTMLFilterUtils;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
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
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/1/18.
 */
public class FavoriteWebAction extends YhyAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private Integer pageNo;
    private Integer pageSize;

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
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private LineService lineService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private HotelPriceService hotelPriceService;

    private ProductType favoriteType;
    private Long favoriteId;

    public Result countMyFav() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        OtherFavorite otherFavorite = new OtherFavorite();
        String favoriteType = (String) getParameter("favoriteType");
        if (StringUtils.hasText(favoriteType)) {
            otherFavorite.setFavoriteType(ProductType.valueOf(favoriteType));
        }
        otherFavorite.setUserId(loginUser.getId());
        otherFavorite.setDeleteFlag(false);
        Long count = otherFavoriteService.countOtherFavorite(otherFavorite);
        result.put("success", true);
        result.put("count", count);
        return json(JSONObject.fromObject(result));
    }

    public Result getMyFav() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Page page = new Page(pageNo, pageSize);
        OtherFavorite favorite = new OtherFavorite();
        String favoriteType = (String) getParameter("favoriteType");
        if (StringUtils.hasText(favoriteType)) {
            favorite.setFavoriteType(ProductType.valueOf(favoriteType));
        }
        favorite.setUserId(loginUser.getId());
        favorite.setDeleteFlag(false);
        List<OtherFavorite> otherFavorites = otherFavoriteService.findOtherFavoriteList(favorite, page);
        for (OtherFavorite otherFavorite : otherFavorites) {
            Integer score = otherFavorite.getScore();
            if (score != null && score > 0) {
                otherFavorite.setScore((score / 100) * 5);
            } else {
                otherFavorite.setScore(5);
            }
            // 过滤html标签等数据
            otherFavorite.setContent(HTMLFilterUtils.doHTMLFilter(otherFavorite.getContent()));
        }
        result.put("success", true);
        result.put("data", otherFavorites);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result doCheckFavorite() {
        User member = getLoginUser();
        if (member == null) {
            result.put("favorite", false);
            result.put("success", true);
            return json(JSONObject.fromObject(result));
        }
        boolean favorite = false;
        if (member != null) {
            favorite = otherFavoriteService.checkExists(favoriteType, favoriteId, member.getId());
        }
        result.put("favorite", favorite);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }


    /**
     * 收藏、取消收藏，
     * 不存在则收藏，存在则取消收藏
     *
     * @return
     */
    @AjaxCheck
//    @NeedLogin
    public Result favorite() throws LoginException {
        User member = getLoginUser();
        if (member == null) {
            result.put("favorite", false);
            result.put("success", true);
            return json(JSONObject.fromObject(result));
        }
        boolean favorite = otherFavoriteService.checkExists(favoriteType, favoriteId, member.getId());
        if (favorite) { // 取消收藏
            if (ProductType.scenic.equals(favoriteType)) {
                scenicInfoService.deleteCollect(favoriteId);
            } else if (ProductType.hotel.equals(favoriteType)) {
                hotelService.deleteCollect(favoriteId);
            } else if (ProductType.sailboat.equals(favoriteType)) {
                Ticket ticket = ticketService.loadTicket(favoriteId);
            } else if (ProductType.delicacy.equals(favoriteType)) {
                delicacyService.deleteCollect(favoriteId);
            } else if (ProductType.cruiseship.equals(favoriteType)) {
                cruiseShipDateService.deleteCollect(favoriteId);
            } else if (ProductType.plan.equals(favoriteType)) {
                planService.deleteCollect(favoriteId);
            } else if (ProductType.recplan.equals(favoriteType)) {
                recommendPlanService.deleteCollect(favoriteId);
            } else if (ProductType.line.equals(favoriteType)) {
                lineService.deleteCollect(favoriteId);
            } else {
                result.put("success", true);
                result.put("errorMsg", "未知收藏类型");
                return json(JSONObject.fromObject(result));
            }
            otherFavoriteService.doClearFavoriteBy(favoriteType, member.getId(), favoriteId);
            favorite = false;
        } else {    // 收藏
            // 收藏信息
            OtherFavorite otherFavorite = new OtherFavorite();
            otherFavorite.setUserId(member.getId());
            otherFavorite.setCreateTime(new Date());
            otherFavorite.setDeleteFlag(false);
            otherFavorite.setFavoriteId(favoriteId);
            otherFavorite.setFavoriteType(favoriteType);
            // 不同类型收藏
            if (ProductType.scenic.equals(favoriteType)) {
                ScenicInfo scenicInfo = scenicInfoService.addCollect(favoriteId);
                otherFavorite.completeFavorite(scenicInfo);
            } else if (ProductType.sailboat.equals(favoriteType)) {
                Ticket ticket = ticketService.loadTicket(favoriteId);
                otherFavorite.completeFavorite(ticket);
                if (otherFavorite.getPrice() == null || otherFavorite.getPrice() <= 0) {
                    Float minPrice = ticketDatepriceService.findMinPriceByTicketId(ticket.getId(), new Date(), null, "priPrice");
                    otherFavorite.setPrice(minPrice != null ? minPrice.doubleValue() : 0D);
                }
            } else if (ProductType.hotel.equals(favoriteType)) {
                Hotel hotel = hotelService.addCollect(favoriteId);
                otherFavorite.completeFavorite(hotel);
                if (otherFavorite.getPrice() == null || otherFavorite.getPrice() <= 0) {
                    Float minPrice = hotelPriceService.findHotelMinPrice(hotel);
                    otherFavorite.setPrice(minPrice != null ? minPrice.doubleValue() : 0D);
                }
            } else if (ProductType.delicacy.equals(favoriteType)) {
                Delicacy delicacy = delicacyService.addCollect(favoriteId);
                otherFavorite.completeFavorite(delicacy);
            } else if (ProductType.plan.equals(favoriteType)) {
                Plan plan = planService.addCollect(favoriteId);
                otherFavorite.completeFavorite(plan);
            } else if (ProductType.cruiseship.equals(favoriteType)) {
                CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(favoriteId);
                otherFavorite.completeFavorite(cruiseShipDate);
                cruiseShipDateService.doAddCollect(favoriteId);
                if (otherFavorite.getPrice() == null || otherFavorite.getPrice() <= 0) {
                    CruiseShipDate minCruiseShipDate = cruiseShipDateService.getMinPriceDate(cruiseShipDate.getCruiseShip(), new Date());
                    Float minPrice = minCruiseShipDate != null ? minCruiseShipDate.getMinDiscountPrice() : Float.valueOf(0F);
                    otherFavorite.setPrice(minPrice.doubleValue());
                }
            } else if (ProductType.recplan.equals(favoriteType)) {
                RecommendPlan recommendPlan = recommendPlanService.addCollect(favoriteId);
                otherFavorite.completeFavorite(recommendPlan);
            } else if (ProductType.line.equals(favoriteType)) {
                Line line = lineService.addCollect(favoriteId);
                List<Line> list = lineService.completeLinePrice(Lists.newArrayList(line));
                if (!list.isEmpty()) {
                    otherFavorite.completeFavorite(list.get(0));
                }
            } else {
                result.put("success", true);
                result.put("errorMsg", "未知收藏类型");
                return json(JSONObject.fromObject(result));
            }

            if (!StringUtils.hasText(otherFavorite.getImgPath())) {
                otherFavorite.setImgPath(otherFavoriteService.getImgPath(otherFavorite.getFavoriteId()));
            }
            otherFavoriteService.doAddOtherFavorite(otherFavorite);
            favorite = true;
        }

        result.put("favorite", favorite);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public ProductType getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(ProductType favoriteType) {
        this.favoriteType = favoriteType;
    }

}
