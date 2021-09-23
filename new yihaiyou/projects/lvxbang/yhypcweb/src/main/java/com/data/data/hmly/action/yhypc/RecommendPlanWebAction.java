package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.yhypc.vo.RecommendPlanResponse;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/1/13.
 */
public class RecommendPlanWebAction extends FrameBaseAction {


    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private CommentService commentService;
    @Resource
    private MemberService memberService;

    private int pageIndex = 0;
    private int pageSize = 10;
    private RecommendPlanSearchRequest recommendPlanSearchRequest = new RecommendPlanSearchRequest();
    private Map<String, Object> map = new HashMap<String, Object>();
    private Long recommendPlanId;


    public Result getTotalCommentPage() {
        Comment comment = new Comment();
        comment.setTargetId(recommendPlanId);
        Long result = commentService.countMyComment(comment);
        return json(JSONArray.fromObject(result));
    }

    public Result getCommentList() {
        if (recommendPlanId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        Comment comment = new Comment();
        comment.setTargetId(recommendPlanId);
        Page page = new Page(pageIndex, pageSize);
        List<Comment> commentList = commentService.getCommentList(comment, page);
        List<Comment> tempCommentList = Lists.newArrayList();
        for (Comment tempComment : commentList) {
            Member user = memberService.get(tempComment.getUser().getId());
            Float totalScore = 0F;
            List<CommentScore> commentScores = tempComment.getCommentScores();
            for (CommentScore commentScore : commentScores) {
                totalScore += commentScore.getScore().floatValue();
            }
            tempComment.setAvgScore(totalScore==0F ? 0F: totalScore / 20);
            tempComment.setUserName(user.getNickName());
            if (StringUtils.isNotBlank(user.getHead()) && !user.getHead().startsWith("http")) {
                tempComment.setHead(QiniuUtil.URL + user.getHead());
            } else {
                tempComment.setHead(user.getHead());
            }
            tempCommentList.add(tempComment);
        }
        map.put("commentList", tempCommentList);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("comments")));
    }


    public Result getCommentInfo() {
        if (recommendPlanId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        Comment comment = new Comment();
        comment.setTargetId(recommendPlanId);
        Integer commentAvgScore = commentService.getAvgScore(comment);
        Long commentCount = commentService.countMyComment(comment);
        map.put("commentAvgScore", commentAvgScore == 0 ? 0 : commentAvgScore.floatValue() / 20);
        map.put("commentCount", commentCount);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map));
    }


    public Result detail() {
        if (recommendPlanId == null) {
            return dispatch404();
        }
        RecommendPlan recommendPlan = recommendPlanService.get(recommendPlanId);
        if (recommendPlan.getStatus() != 2) {
            return dispatch500();
        }
        setAttribute(YhyConstants.YHY_RECOMMEND_PLAN_DETAIL_KEY, FileUtil.loadHTML(YhyConstants.YHY_RECOMMEND_PLAN_DETAIL + recommendPlanId));
        setAttribute(YhyConstants.YHY_RECOMMEND_PLAN_HEAD_KEY, FileUtil.loadHTML(YhyConstants.YHY_RECOMMEND_PLAN_HEAD + recommendPlanId));

        Integer viewNum = recommendPlan.getViewNum();
        recommendPlan.setViewNum(viewNum + 1);
        recommendPlanService.update(recommendPlan);
        return dispatch();
    }


    public Result index() {
        setAttribute(YhyConstants.YHY_RECOMMEND_PLAN_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_RECOMMEND_PLAN_INDEX));
        return dispatch();
    }

    public Result getTotalPage() {
        TbArea area = tbAreaService.getArea(350200L);
        List<TbArea> areaList = area.getChilds();
        areaList.add(area);
        List<Long> cityIds = Lists.transform(areaList, new Function<TbArea, Long>() {
            @Override
            public Long apply(TbArea tbArea) {
                return tbArea.getId();
            }
        });
        recommendPlanSearchRequest.setCityIds(cityIds);
        Long result = recommendPlanService.countFromSolr(recommendPlanSearchRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result getRecommendPlanList() {
        Page page = new Page(pageIndex, pageSize);

        TbArea area = tbAreaService.getArea(350200L);
        List<TbArea> areaList = area.getChilds();
        areaList.add(area);
        List<Long> cityIds = Lists.transform(areaList, new Function<TbArea, Long>() {
            @Override
            public Long apply(TbArea tbArea) {
                return tbArea.getId();
            }
        });
        recommendPlanSearchRequest.setCityIds(cityIds);
        List<RecommendPlanSolrEntity> recommendPlanSolrEntities = recommendPlanService.listFromSolr(recommendPlanSearchRequest, page);
        List<RecommendPlanResponse> recommendPlanResponseList = Lists.transform(recommendPlanSolrEntities, new Function<RecommendPlanSolrEntity, RecommendPlanResponse>() {
            @Override
            public RecommendPlanResponse apply(RecommendPlanSolrEntity recommendPlanSolrEntity) {
                return new RecommendPlanResponse(recommendPlanSolrEntity);
            }
        });
        map.put("page", page);
        map.put("recommendPlanResponseList", recommendPlanResponseList);
        return json(JSONObject.fromObject(map));
    }

    public Result list() {
        recommendPlanSearchRequest.setName(StringUtils.htmlEncode(recommendPlanSearchRequest.getName()));
        return dispatch();
    }

    public RecommendPlanSearchRequest getRecommendPlanSearchRequest() {
        return recommendPlanSearchRequest;
    }

    public void setRecommendPlanSearchRequest(RecommendPlanSearchRequest recommendPlanSearchRequest) {
        this.recommendPlanSearchRequest = recommendPlanSearchRequest;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getRecommendPlanId() {
        return recommendPlanId;
    }

    public void setRecommendPlanId(Long recommendPlanId) {
        this.recommendPlanId = recommendPlanId;
    }
}
