package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.request.TicketSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/8/2.
 */
public class SailboatYachtWebAction extends BaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private TicketSearchRequest ticketSearchRequest = new TicketSearchRequest();
    private Integer page = 1;
    private Integer pageSize = 10;

    private Ticket ticket = new Ticket();
    private Comment comment = new Comment();

    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private CommentService commentService;


    public Result sailyachtCommentInfo() {
        List<Comment> commentList = Lists.newArrayList();
        if (comment.getType() != null) {
            comment.setStatus(CommentStatus.NORMAL);
            commentList = commentService.list(comment, null);

            Map<String, Object> tempMap = new HashMap<String, Object>();
            Integer fiveStar = 0;
            Integer fourStar = 0;
            Integer threeStar = 0;
            Integer twoStar = 0;
            Integer oneStar = 0;
            Integer zeoStar = 0;
            Integer totalScore = 0;
            Integer avgScore = 0;
            Float resultScore = 0F;
            for (Comment c : commentList) {
                List<CommentScore> commentScores = c.getCommentScores();
                for (CommentScore cs : commentScores) {
                    if (cs.getScore() != null && cs.getScore()/20F <=5 && cs.getScore()/20F > 4) {
                        fiveStar++;
                    } else if (cs.getScore() != null && cs.getScore()/20F <=4 && cs.getScore()/20F > 3) {
                        fourStar++;
                    } else if (cs.getScore() != null && cs.getScore()/20F <=3 && cs.getScore()/20F > 2) {
                        threeStar++;
                    } else if (cs.getScore() != null && cs.getScore()/20F <=2 && cs.getScore()/20F > 1) {
                        twoStar++;
                    } else if (cs.getScore() != null && cs.getScore()/20F <=1 && cs.getScore()/20F > 0) {
                        oneStar++;
                    } else {
                        zeoStar++;
                    }
                    if (cs.getScore() != null) {
                        totalScore += cs.getScore();
                    }

                }
            }
            if (totalScore > 0) {
                avgScore = totalScore/commentList.size();
                resultScore = avgScore/20F;
            }

            result.put("fiveStar", fiveStar);
            result.put("fourStar", fourStar);
            result.put("threeStar", threeStar);
            result.put("twoStar", twoStar);
            result.put("oneStar", oneStar);
            result.put("zeoStar", zeoStar);
            result.put("avgScore", avgScore);
            result.put("totalCount", commentList.size());
            result.put("resultScore", resultScore);
            result.put("success", true);
            return json(JSONObject.fromObject(result));
        } else {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

    }

    public Result sailyachtCommentList() {
        Page pageInfo = new Page(page, pageSize);
        List<Comment> commentList = Lists.newArrayList();
        if (comment.getPriceId() != null  && comment.getType() != null) {
            List<Comment> tempList = Lists.newArrayList();
            comment.setStatus(CommentStatus.NORMAL);
            commentList = commentService.list(comment, pageInfo);
            for (Comment c : commentList) {
                if (c.getPriceId().equals(comment.getPriceId())) {
                    TicketPrice ticketPrice = ticketPriceService.getPrice(comment.getPriceId());
                    c.setTargetName(ticketPrice.getTicketName());
                }
                tempList.add(c);
            }

            commentList.clear();
            commentList = tempList;
        } else if (comment.getTargetIdList() != null && comment.getType() != null) {
            List<Comment> tempList = Lists.newArrayList();
            comment.setStatus(CommentStatus.NORMAL);
            commentList = commentService.list(comment, pageInfo);
            for (Comment c : commentList) {
                for (Long targetId : comment.getTargetIdList()) {
                    if (c.getTargetId().equals(targetId)) {
                        TicketPrice ticketPrice = ticketPriceService.getPrice(targetId);
                        c.setTargetName(ticketPrice.getName());
                    }
                }
                tempList.add(c);
            }
            commentList.clear();
            commentList = tempList;

        } else {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }

        /*if (ticketPrice != null) {
            result.put("ticketTypeName", ticketPrice.getName());
            result.put("ticketName", ticketPrice.getTicket().getName());
        }*/
        result.put("success", true);
        result.put("commentList", commentList);
        result.put("totalCount", pageInfo.getTotalCount());
        if (commentList.size() < pageInfo.getTotalCount()) {
            result.put("nomore", false);
        } else {
            result.put("nomore", true);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("user", "commentPhotos", "comments", "commentScores");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result sailboatList() {
        Page pageInfo = new Page(page, pageSize);
        List<TicketSolrEntity> ticketSolrEntities = ticketService.listFromSolr(ticketSearchRequest, pageInfo);
        if (ticketSolrEntities.isEmpty()) {
            result.put("success", false);
            result.put("nomore", true);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        List<TicketSolrEntity> tempTicketList = Lists.newArrayList();
        for (TicketSolrEntity ticketSolrEntity : ticketSolrEntities) {
//            TicketMinData minDatePrice = ticketPriceService.findTicketMinPrice(ticketSolrEntity.getId(), new Date(), null, null);
//
//            Float minPriPrice = minDatePrice.getMinPriPrice();
//            Float minRebate = minDatePrice.getMinRebate();
//            if (minPriPrice == null) {
//                minPriPrice = 0f;
//            }
//            if (minRebate == null) {
//                minRebate = 0f;
//            }
            Float minPrice = ticketDatepriceService.findMinPriceByTicketId(ticketSolrEntity.getId(), new Date(), null, "priPrice");
            ticketSolrEntity.setDisCountPrice(minPrice);
            tempTicketList.add(ticketSolrEntity);
        }

        if (pageInfo.getPageIndex() >= pageInfo.getPageCount()) {

            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }

        result.put("success", true);
        result.put("resultList", tempTicketList);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }



    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public TicketSearchRequest getTicketSearchRequest() {
        return ticketSearchRequest;
    }

    public void setTicketSearchRequest(TicketSearchRequest ticketSearchRequest) {
        this.ticketSearchRequest = ticketSearchRequest;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
