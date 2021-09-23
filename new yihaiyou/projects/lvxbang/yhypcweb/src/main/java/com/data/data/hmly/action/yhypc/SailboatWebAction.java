package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.yhypc.vo.TicketPriceResponse;
import com.data.data.hmly.action.yhypc.vo.TicketResponse;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketPriceTypeExtendService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/12/27.
 */
public class SailboatWebAction extends FrameBaseAction {

    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketPriceTypeExtendService ticketPriceTypeExtendService;
    @Resource
    private CommentService commentService;
    @Resource
    private MemberService memberService;

    private TicketSearchRequest ticketSearchRequest = new TicketSearchRequest();
    private Comment comment = new Comment();
    private List<ScenicInfo> scenicInfos = new ArrayList<ScenicInfo>();
    private Map<String, Object> map = new HashMap<String, Object>();
    private int pageIndex = 0;
    private int pageSize = 20;
    private Long ticketPriceId;
    private Long ticketId;

    public Result index() {
        setAttribute(YhyConstants.YHY_SAILBOAT_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_SAILBOAT_INDEX));
        return dispatch();
    }
    public Result detail() {

        if (ticketId == null) {
            return dispatch404();
        }

        Ticket ticket = ticketService.loadTicket(ticketId);

        if (ticket == null) {
            return dispatch404();
        }

        if (ticket.getStatus() != ProductStatus.UP) {
            return dispatch404();
        }
        setAttribute(YhyConstants.YHY_SAILBOAT_DETAIL_KEY, FileUtil.loadHTML(YhyConstants.YHY_SAILBOAT_DETAIL + ticketId));
        setAttribute(YhyConstants.YHY_SAILBOAT_HEAD_KEY, FileUtil.loadHTML(YhyConstants.YHY_SAILBOAT_HEAD + ticketId));
        return dispatch();
    }
    public Result getTotalPage() {
        Long result = ticketService.countFromSolr(ticketSearchRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result getPriceTypeInfo() {
        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        List<TicketPriceTypeExtend> typeExtends = ticketPriceTypeExtendService.getListByPriceId(ticketPriceId);
        map.put("priceTypeExtendList", typeExtends);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));
    }


    public Result getTotalCommentPage() {
        Long result = commentService.countMyComment(comment);
        return json(JSONArray.fromObject(result));
    }
    public Result getCommentList() {
        if (comment.getTargetId() == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
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
        if (ticketId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        Comment comment = new Comment();
        comment.setTargetId(ticketId);
        Integer commentAvgScore = commentService.getAvgScore(comment);
        Long commentCount = commentService.countMyComment(comment);
        map.put("commentAvgScore", commentAvgScore == 0 ? 0 : commentAvgScore.floatValue() / 20);
        map.put("commentCount", commentCount);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map));
    }

    public Result getTicketPriceList() {
        Page page = new Page(pageIndex, pageSize);
        if (ticketId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        List<TicketPrice> ticketPrices = ticketPriceService.findMinTicketPriceByTicketId(ticketId, page);
        List<TicketPriceResponse> ticketPriceResponses = Lists.transform(ticketPrices, new Function<TicketPrice, TicketPriceResponse>() {
            @Override
            public TicketPriceResponse apply(TicketPrice ticketPrice) {
                return new TicketPriceResponse(ticketPrice);
            }
        });
        map.put("ticketPrices", ticketPriceResponses);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));
    }

    public Result getScenicList() {
        Page page = new Page(pageIndex, pageSize);
        List<TicketSolrEntity> ticketSolrEntities = ticketService.listFromSolr(ticketSearchRequest, page);
        List<TicketResponse> ticketResponses = Lists.newArrayList();
        Page page1 = new Page(1, 100);
        for (TicketSolrEntity ticketSolrEntity : ticketSolrEntities) {
            TicketResponse ticketResponse = new TicketResponse(ticketSolrEntity);
            List<TicketPrice> ticketPrices = ticketPriceService.findMinTicketPriceByTicketId(ticketResponse.getId(), page1);
            List<TicketPriceResponse> ticketPriceResponses = Lists.transform(ticketPrices, new Function<TicketPrice, TicketPriceResponse>() {
                @Override
                public TicketPriceResponse apply(TicketPrice ticketPrice) {
                    return new TicketPriceResponse(ticketPrice);
                }
            });
            ticketResponse.setPriceList(ticketPriceResponses);

            ticketResponses.add(ticketResponse);

        }
        map.put("page", page);
        map.put("ticketResponses", ticketResponses);
        return json(JSONObject.fromObject(map));
    }

//    /yhypc/sailboat/list.jhtml
    public Result list() {
        //登船地点
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setScenicType(ScenicInfoType.sailboat);
        TbArea tbArea = new TbArea();
        tbArea.setId(3502L);
        scenicInfo.setCity(tbArea);
        scenicInfo.setStatus(1);
        scenicInfos = scenicInfoService.list(scenicInfo, null);
        return dispatch();
    }

    public TicketSearchRequest getTicketSearchRequest() {
        return ticketSearchRequest;
    }

    public void setTicketSearchRequest(TicketSearchRequest ticketSearchRequest) {
        this.ticketSearchRequest = ticketSearchRequest;
    }

    public List<ScenicInfo> getScenicInfos() {
        return scenicInfos;
    }

    public void setScenicInfos(List<ScenicInfo> scenicInfos) {
        this.scenicInfos = scenicInfos;
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
