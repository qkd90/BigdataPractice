package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.mobile.response.LineListResponse;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by caiys on 2016/7/18.
 */
public class ScenicTicketWebAction extends MobileBaseAction {
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private CommentService commentService;
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;

    private Integer pageNo;
    private Integer pageSize;

    /**
     * 景点概要信息
     * @return
     */
    @AjaxCheck
    public Result scenicTicketInfo() throws LoginException {
        String scenicIdStr = (String) getParameter("scenicId");
        String favoriteStr = (String) getParameter("favorite");
        // 查询景点概要信息
        ScenicInfo scenicInfo = scenicInfoService.get(Long.valueOf(scenicIdStr));
        result.put("scenicId", scenicIdStr);
        result.put("scenicName", scenicInfo.getName());
        result.put("scenicCover", scenicInfo.getCover());
        result.put("openTime", scenicInfo.getScenicOther().getOpenTime());
        result.put("address", scenicInfo.getScenicOther().getAddress());
        result.put("productRemark", scenicInfo.getScenicOther().getHow());
        result.put("telephone", scenicInfo.getScenicOther().getTelephone());
        result.put("trafficGuide", scenicInfo.getScenicOther().getTrafficGuide());
        // 获取满意度
        result.put("satisfaction", scenicInfo.getScore());
        // 查询是否收藏
        boolean favorite = false;
        if (StringUtils.isNotBlank(favoriteStr)) {
            Member member = getLoginUser();
            if (member != null) {
                favorite = otherFavoriteService.checkExists(ProductType.scenic, Long.valueOf(scenicIdStr), member.getId());
            }
        }
        result.put("favorite", favorite);

        // 查询门票列表
//        List<TicketPrice> ticketPriceList = ticketPriceService.findTicketPriceBy(Long.valueOf(scenicIdStr));
//        JSONArray jsonArray = JSONArray.fromObject(ticketPriceList, JsonFilter.getIncludeConfig());
//        result.put("ticketList", jsonArray);

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 门票列表信息
     * @return
     */
    @AjaxCheck
    public Result scenicTicketList() {
        String scenicIdStr = (String) getParameter("scenicId");
        // 查询门票列表
        List<TicketPrice> ticketPriceList = ticketPriceService.findTicketPriceBy(Long.valueOf(scenicIdStr));
        for (TicketPrice ticketPrice : ticketPriceList) {
            List<TicketDateprice> datepriceList = ticketDatepriceService.findTypePriceDate(ticketPrice.getId(), new Date(), null, 1);
            if (!datepriceList.isEmpty()) {
                ticketPrice.setRebate(datepriceList.get(0).getRebate());
            }
        }
        Set<Long> ticketIds = Sets.newHashSet();
        for (TicketPrice ticketPrice : ticketPriceList) {
            ticketIds.add(ticketPrice.getTicket().getId());
        }
        Set<Line> lines = Sets.newHashSet();
        for (Long ticketId : ticketIds) {
            List<Line> lineList = linedaysProductPriceService.listLine(ticketId, ProductType.scenic, null);
            lines.addAll(lineList);
        }
        List<LineListResponse> lineResponses = Lists.newArrayList();
        for (Line line : lines) {
            if (line.getPrice() > 0) {
                LineListResponse response = new LineListResponse();
                response.setId(line.getId());
                response.setName(line.getName());
                response.setAppendTitle(line.getAppendTitle());
                response.setMinPrice(line.getPrice());
                lineResponses.add(response);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(ticketPriceList, JsonFilter.getIncludeConfig());
        result.put("ticketList", jsonArray);
        result.put("lineList", lineResponses);

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 景点详情
     * @return
     */
    @AjaxCheck
    public Result scenicDesc() {
        String scenicIdStr = (String) getParameter("scenicId");
        // 查询景点详情
        ScenicInfo scenicInfo = scenicInfoService.get(Long.valueOf(scenicIdStr));
        result.put("descripton", scenicInfo.getScenicOther().getDescription());

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 景点评论
     * @return
     */
    @AjaxCheck
    public Result scenicCommentList() {
        String scenicIdStr = (String) getParameter("scenicId");

        // 获取评论列表、点评数
        Page page = new Page(pageNo, pageSize);
        Comment comment = new Comment();
        comment.setType(ProductType.scenic);
        comment.setTargetId(Long.valueOf(scenicIdStr));
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> list = commentService.list(comment, page);
        for (Comment c : list) {
            if (c.getUser() != null) {
                c.setNickName(c.getUser().getNickName());
            }
            // 获取最高评分和最低评分
            List<CommentScore> scores = c.getCommentScores();
            for (CommentScore s : scores) {
                if (c.getMaxScore() == null || (s.getScore() != null && s.getScore() > c.getMaxScore())) {
                    c.setMaxScore(s.getScore());
                }
                if (c.getMinScore() == null || (s.getScore() != null && s.getScore() < c.getMinScore())) {
                    c.setMinScore(s.getScore());
                }
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(list, JsonFilter.getIncludeConfig());
        result.put("commentList", jsonArray);
        result.put("commentCount", page.getTotalCount());
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 票型说明
     * @return
     */
    @AjaxCheck
    public Result ticketAddinfoDetail() {
        String ticketPriceIdStr = (String) getParameter("ticketPriceId");

        TicketPrice ticketPrice = ticketPriceService.findById(Long.valueOf(ticketPriceIdStr));
        // 判断是否来自携程，如果不是直接取预订须知内容，如果是取值携程附加信息
        List<TicketPriceAddInfo> addinfoDetailList = ticketPriceService.findTicketPriceAddInfoList(ticketPrice);
        result.put("addinfoDetailList", addinfoDetailList);

        result.put("success", true);
        return json(JSONObject.fromObject(result));
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
}
