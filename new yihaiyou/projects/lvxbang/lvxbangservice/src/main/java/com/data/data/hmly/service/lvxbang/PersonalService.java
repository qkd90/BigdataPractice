package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.lvxbang.response.PersonalCommentResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalPlanResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalRecplanResponse;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 16/1/18.
 */

@Service
public class PersonalService {

    @Resource
    private AreaService areaService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private RecommendPlanService recommendPlanService;

    /**
     * 我的行程
     *
     * @param planList
     * @param started
     * @return
     */
    public List<PersonalPlanResponse> processPersonalPlan(List<Plan> planList, Boolean started) {
        List<PersonalPlanResponse> personalPlanResponseList = new ArrayList<PersonalPlanResponse>();
        for (Plan plan : planList) {
            PersonalPlanResponse personalPlanResponse = planToResonse(plan);
            personalPlanResponse.setStarted(plan.getCompleteFlag());
            personalPlanResponseList.add(personalPlanResponse);
        }
        return personalPlanResponseList;
    }

    public PersonalPlanResponse planToResonse(Plan plan) {
        PersonalPlanResponse personalPlanResponse = new PersonalPlanResponse();
        personalPlanResponse.setId(plan.getId());
        personalPlanResponse.setName(plan.getName());
        personalPlanResponse.setCover(plan.getCoverPath());
        personalPlanResponse.setDays(plan.getPlanDays());
        personalPlanResponse.setRecplanId(plan.getSourceId());
        // TODO cost
        // personalPlanResponse.setCost();
        personalPlanResponse.setDate(formatDate(plan.getStartTime()));

        List<String> citys = new ArrayList<String>();
        for (PlanDay planDay : plan.getPlanDayList()) {
            if (!citys.contains(planDay.getCity().getName())) {
                citys.add(planDay.getCity().getName());
            }
        }
        personalPlanResponse.setCitys(citys);
        return personalPlanResponse;
    }


    /**
     * 我的游记
     *
     * @param recommendPlanList
     * @param started
     * @return
     */
    public List<PersonalRecplanResponse> processPersonalRecplan(List<RecommendPlan> recommendPlanList, Boolean started) {
        // 处理推荐行程城市
        String cityIds = "";
        for (RecommendPlan recommendPlan : recommendPlanList) {
            cityIds += recommendPlan.getCityIds() + ",";
        }
        List<TbArea> tbAreaList = areaService.getByIds(cityIds);
        Map<String, String> cityMap = new HashMap<String, String>();
        for (TbArea tbArea : tbAreaList) {
            cityMap.put(tbArea.getId().toString(), tbArea.getName());
        }
        // 推荐行程其它数据
        List<PersonalRecplanResponse> personalRecplanResponseList = new ArrayList<PersonalRecplanResponse>();
        for (RecommendPlan recommendPlan : recommendPlanList) {
            cityIds += recommendPlan.getCityIds() + ",";
            PersonalRecplanResponse personalRecplanResponse = recplanToResponse(recommendPlan, cityMap);
            personalRecplanResponse.setStarted(started);
            personalRecplanResponseList.add(personalRecplanResponse);
        }
        return personalRecplanResponseList;
    }

    public PersonalRecplanResponse recplanToResponse(RecommendPlan recommendPlan, Map<String, String> cityMap) {
        //
        PersonalRecplanResponse personalRecplanResponse = new PersonalRecplanResponse();
        personalRecplanResponse.setId(recommendPlan.getId());
        personalRecplanResponse.setName(recommendPlan.getPlanName());
        personalRecplanResponse.setCover(recommendPlan.getCoverPath());
        personalRecplanResponse.setDays(recommendPlan.getDays());
        // TODO cost
        // personalPlanResponse.setCost();
        personalRecplanResponse.setDate(formatDate(recommendPlan.getCreateTime()));
        personalRecplanResponse.setBrowsingNum(recommendPlan.getViewNum());
        personalRecplanResponse.setColloctNum(recommendPlan.getCollectNum());
        personalRecplanResponse.setQuoteNum(recommendPlan.getQuoteNum());

        List<String> citys = new ArrayList<String>();
        String idsString = recommendPlan.getCityIds();
        String[] idList = {};
        if ( !StringUtils.isEmpty(idsString)) {
            idList = idsString.split(",");
        }
        for (String idString : idList) {
            if (cityMap.get(idString) != null && !citys.contains(cityMap.get(idString))) {
                citys.add(cityMap.get(idString));
            }
        }
        personalRecplanResponse.setCitys(citys);

        return personalRecplanResponse;
    }


    /**
     * 我的评论
     *
     * @param commentList
     * @return
     */
    public List<PersonalCommentResponse> processCommentResponse(List<Comment> commentList) {
        //
        List<PersonalCommentResponse> personalCommentResponseList = new ArrayList<PersonalCommentResponse>();
        for (Comment comment : commentList) {
            personalCommentResponseList.add(commentToResponse(comment));
        }
        return personalCommentResponseList;
    }

    public PersonalCommentResponse commentToResponse(Comment comment) {
        PersonalCommentResponse personalCommentResponse = new PersonalCommentResponse();
        personalCommentResponse.setId(comment.getId());
        personalCommentResponse.setTime(formatTotalDate(comment.getCreateTime()));
        personalCommentResponse.setProductType(comment.getType());
        personalCommentResponse.setContent(comment.getContent());

        String name = "";
        String cover = "";
        String detail = "";
        //scenic("门票"), hotel("酒店"), delicacy("美食"), recplan("游记");
        switch (comment.getType()) {
            case scenic:
                ScenicInfo scenicInfo = scenicInfoService.get(comment.getTargetId());
                if (scenicInfo != null) {
                    name = scenicInfo.getName();
                    cover = scenicInfo.getCover();
                    detail = "/lvxbang/scenic/detail.jhtml?scenicId=" + comment.getTargetId();
                } else {
                    name = "该景点已被下架";
                    cover = "";
                    detail = "";
                }


                break;
            case hotel:
                Hotel hotel = hotelService.get(comment.getTargetId());
                if (hotel != null) {
                    name = hotel.getName();
                    cover = hotel.getCover();
                    detail = "/lvxbang/hotel/detail.jhtml?hotelId=" + comment.getTargetId();
                } else {
                    name = "该酒店已被下架";
                    cover = "";
                    detail = "";
                }

                break;
            case delicacy:
                Delicacy delicacy = delicacyService.get(comment.getTargetId());
                if ( delicacy != null) {
                    name = delicacy.getName();
                    cover = delicacy.getCover();
                    detail = "/lvxbang/delicacy/detail.jhtml?delicacyId=" + comment.getTargetId();
                } else {
                    name = "该美食已被下架";
                    cover = "";
                    detail = "";
                }


                break;
            case recplan:
                RecommendPlan recommendPlan = recommendPlanService.get(comment.getTargetId());
                if ( recommendPlan != null) {
                    name = recommendPlan.getPlanName();
                    cover = recommendPlan.getCoverPath();
                    detail = "/lvxbang/recommendPlan/detail.jhtml?recplanId=" + comment.getTargetId();
                } else {
                    name = "该游记已被下架";
                    cover = "";
                    detail = "";
                }

                break;
            default:
                break;
        }
        personalCommentResponse.setDetail(detail);
        personalCommentResponse.setTargetName(name);
        personalCommentResponse.setTargetCover(cover);
        return personalCommentResponse;
    }


    private String formatDate(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

    private String formatTotalDate(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormater.format(date);
    }
}
