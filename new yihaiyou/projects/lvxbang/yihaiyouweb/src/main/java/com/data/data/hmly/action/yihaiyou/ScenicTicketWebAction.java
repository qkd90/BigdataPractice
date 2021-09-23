package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.LineListResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketPriceResponse;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
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
import java.util.Map;
import java.util.Set;

/**
 * Created by caiys on 2016/7/18.
 */
public class ScenicTicketWebAction extends BaseAction {
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketExplainService ticketExplainService;
    @Resource
    private CommentService commentService;
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;

    @Resource
    private ProductimageService productimageService;

    private Integer pageNo;
    private Integer pageSize;

    /**
     * 景点概要信息
     *
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
     * 获取门票信息
     *
     * @return
     */
    @AjaxCheck
    public Result getTicketInfo() {
        String scenicIdStr = (String) getParameter("ticketId");
        if (!StringUtils.isNotBlank(scenicIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        Ticket ticket = ticketService.loadTicket(Long.parseLong(scenicIdStr));

        if (ticket == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        result.put("telephone", ticket.getTelephone());

        ScenicInfo scenicInfo = ticket.getScenicInfo();

        TicketExplain explain = ticketExplainService.findExplainByTicketId(ticket.getId());


        result.put("ticketId", ticket.getId());
        Productimage image = productimageService.findCover(ticket.getId(), null, ProductType.scenic);
        if (image != null) {
            result.put("productImg", image.getPath());
        }
        result.put("ticketName", ticket.getName());
        result.put("address", ticket.getAddress());


        if (scenicInfo != null) {
            result.put("scenicId", scenicInfo.getId());
            result.put("scenicName", scenicInfo.getName());
            result.put("openTime", scenicInfo.getScenicOther().getOpenTime());
            // 获取满意度
        }

        Comment comment = new Comment();
        comment.setTargetId(ticket.getId());
        comment.setType(ProductType.sailboat);
        comment.setStatus(CommentStatus.NORMAL);
        result.put("productScore", commentService.getAvgScore(comment));

        if (explain != null) {
            result.put("privilege", explain.getPrivilege());
            result.put("enterDesc", explain.getEnterDesc());
            result.put("openTime", explain.getOpenTime());
            result.put("tips", explain.getTips());
            result.put("proInfo", explain.getProInfo());
        }

        Productimage productimage = new Productimage();
        productimage.setProduct(ticket);
        List<Productimage> productimageList = productimageService.findProductimage(productimage, null);

        result.put("imageCount", productimageList.size());

        boolean favorite = false;
        Member member = getLoginUser();
        if (member != null) {
            favorite = otherFavoriteService.checkExists(ProductType.sailboat, Long.valueOf(scenicIdStr), member.getId());
        }
        result.put("favorite", favorite);
        result.put("ticketType", ticket.getTicketType());

        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    /**
     * 门票价格列表信息
     *
     * @return
     */
    @AjaxCheck
    public Result ticketTypeList() {
        String ticketIdStr = (String) getParameter("ticketId");
        if (!StringUtils.isNotBlank(ticketIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
//        List<TicketPrice> ticketPriceList = ticketPriceService.findTicketList(Long.parseLong(ticketIdStr), new Page(1, 10));
        List<TicketPrice> ticketPriceList = ticketPriceService.findAvailablePriceList(Long.parseLong(ticketIdStr), new Page(1, 10), new Date(), "showOrder", "asc");
        if (ticketPriceList == null) {
            ticketPriceList = Lists.newArrayList();
        }
        List<TicketPrice> tempTicketPriceList = Lists.newArrayList();

        for (TicketPrice ticketPrice : ticketPriceList) {
            TicketPrice tempTicketPrice = ticketPrice;
            TicketDateprice dateprice = ticketDatepriceService.findFirstAvailableByPriceType(ticketPrice, new Page(1, 1));
            if (dateprice != null) {
                tempTicketPrice.setMinDiscountPrice(dateprice.getPriPrice());
                tempTicketPrice.setPriceDate(dateprice.getHuiDate());
            }
            tempTicketPriceList.add(tempTicketPrice);
        }
        JSONArray jsonArray = JSONArray.fromObject(tempTicketPriceList, JsonFilter.getIncludeConfig());
        result.put("ticketTypeList", jsonArray);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result ticketPriceDetail() {
        String priceIdStr = (String) getParameter("priceId");
        if (StringUtils.isBlank(priceIdStr)) {
            result.put("success", false);
            return jsonResult(result);
        }
        TicketPrice ticketPrice = ticketPriceService.getPrice(Long.valueOf(priceIdStr));
        if (ticketPrice == null) {
            result.put("success", false);
            return jsonResult(result);
        }
        Map<String, Date> dateMap = ticketDatepriceService.getFirstAndEndDate(ticketPrice);
        TicketPriceResponse response = new TicketPriceResponse(ticketPrice);
        Productimage image = productimageService.findCover(ticketPrice.getTicket().getId(), ticketPrice.getId(), ProductType.scenic);
        if (image != null) {
            response.setCover(image.getPath());
        }

        Comment comment = new Comment();
        comment.setPriceId(ticketPrice.getId());
        comment.setType(ProductType.sailboat);
        comment.setStatus(CommentStatus.NORMAL);
        response.setCommentCount(commentService.countMyComment(comment));
        response.setScore(commentService.getAvgScore(comment));

        Productimage productimage = new Productimage();
        productimage.setProduct(ticketPrice.getTicket());
        productimage.setTargetId(ticketPrice.getId());
        List<Productimage> productimageList = productimageService.findProductimage(productimage, null);

        Date start = dateMap.get("start");
        Date end = dateMap.get("end");
        if (start != null && end != null) {
            response.setStartDate(DateUtils.formatShortDate(start));
            response.setEndDate(DateUtils.formatShortDate(end));
        }
        result.put("success", true);
        result.put("ticketPrice", response);
        result.put("imageCount", productimageList.size());
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    /**
     * 门票列表信息
     *
     * @return
     */
    @AjaxCheck
    public Result scenicTicketList() {
        String scenicIdStr = (String) getParameter("scenicId");
        // 查询门票列表
        List<TicketPrice> ticketPriceList = ticketPriceService.findTicketPriceBy(Long.valueOf(scenicIdStr));
        for (TicketPrice ticketPrice : ticketPriceList) {
            List<TicketDateprice> datepriceList = ticketDatepriceService.findTypePriceDate(ticketPrice.getId(), new Date(), null, 1, "priPrice", "asc");
            if (!datepriceList.isEmpty()) {
                ticketPrice.setMinDiscountPrice(datepriceList.get(0).getPriPrice());
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
     *
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

    @AjaxCheck
    public Result ticketProductDesc() {
        String ticketIdStr = (String) getParameter("ticketId");
        if (!StringUtils.isNotBlank(ticketIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        TicketExplain ticketExplain = ticketExplainService.findExplainByTicketId(Long.parseLong(ticketIdStr));
        result.put("ticketExplain", ticketExplain);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 景点评论
     *
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
     *
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
