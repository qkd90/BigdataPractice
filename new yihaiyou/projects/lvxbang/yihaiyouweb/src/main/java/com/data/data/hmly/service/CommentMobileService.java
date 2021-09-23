package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.CommentResponse;
import com.data.data.hmly.action.yihaiyou.response.ReplyCommentResponse;
import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.comment.entity.enums.commentScoreTypeStatus;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-11-09,0009.
 */
@Service
public class CommentMobileService {
    @Resource
    private CommentService commentService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private CommentScoreTypeService commentScoreTypeService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private TicketService ticketService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private OrderService orderService;

    public void save(Integer score, Long orderDetailId, Member user, Comment comment) {
        if (orderDetailId == null || orderDetailId < 1) {
            return;
        }
        OrderDetail orderDetail = orderDetailService.get(orderDetailId);
        if (orderDetail == null || orderDetail.getHasComment()) {
            return;
        }
        if (score != null) {
            CommentScoreType commentScoreType = new CommentScoreType();
            commentScoreType.setStatus(commentScoreTypeStatus.USE);
            commentScoreType.setTargetType(comment.getType());
            commentScoreType.setName("总体评价");
            List<CommentScoreType> list = commentScoreTypeService.list(commentScoreType, null);
            if (!list.isEmpty()) {
                CommentScoreType scoreType = list.get(0);
                CommentScore commentScore = new CommentScore();
                commentScore.setCommentScoreType(scoreType);
                commentScore.setScore(score * 20);
                comment.setCommentScores(Lists.newArrayList(commentScore));
                switch (comment.getType()) {
                    case scenic:case sailboat:case huanguyou:
                        Ticket ticket = (Ticket) orderDetail.getProduct();
                        scenicInfoService.addComment(ticket.getScenicInfo().getId(), ticket.getId(), score * 20);
                        break;
                    case hotel:
                        hotelService.addComment(orderDetail.getProduct().getId(), score * 20);
                        break;
                    case cruiseship:
                        break;
                }
            }
        }
        comment.setUser(user);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        comment.setOrderNo(orderDetail.getOrder().getOrderNo());
        comment.setReplyStatus(0);
        comment.setOrderId(orderDetail.getOrder().getId());
        comment.setOrderDetailId(orderDetail.getId());
        commentService.saveComment(comment);

        orderDetail.setHasComment(true);
        orderDetailService.update(orderDetail);

        Boolean hasComment = false;
        Order order = orderDetail.getOrder();
        for (OrderDetail detail : order.getOrderDetails()) {
            hasComment = hasComment || detail.getHasComment();
        }
        if (hasComment) {
            order.setHasComment(true);
            orderService.update(order);
        }
    }

    public List<CommentResponse> personalList(Member user, Page page) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> commentList = commentService.list(comment, page);
        List<CommentResponse> responseList = Lists.transform(commentList, new Function<Comment, CommentResponse>() {
            @Override
            public CommentResponse apply(Comment input) {
                return commentToResponse(input);
            }
        });
        responseList.removeAll(Collections.singleton(null));
        return responseList;
    }

    public List<CommentResponse> productComment(ProductType productType, Long targetId, Page page) {
        Comment comment = new Comment();
        comment.setType(productType);
        comment.setTargetId(targetId);
        List<Comment> commentList = commentService.list(comment, page);
        List<CommentResponse> responseList = Lists.transform(commentList, new Function<Comment, CommentResponse>() {
            @Override
            public CommentResponse apply(Comment input) {
                return commentToResponse(input);
            }
        });
        responseList.removeAll(Collections.singleton(null));
        return responseList;
    }

    private CommentResponse commentToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setTargetId(comment.getTargetId());
        response.setProductType(comment.getType());
        response.setContent(comment.getContent());
        response.setUserName(comment.getUser().getNickName());
        response.setCommentDate(comment.getCreateTimeStr());
        if (!comment.getCommentScores().isEmpty()) {
            response.setScore(comment.getCommentScores().get(0).getScore());
        }
        String name = "";
        String cover = "";
        switch (comment.getType()) {
            case sailboat:
                Ticket ticket = ticketService.loadTicket(comment.getTargetId());
                if (ticket != null) {
                    response.setTargetId(ticket.getId());
                    name = ticket.getName();
                    Productimage productimage = productimageService.findCover(ticket.getId(), null, ProductType.scenic);
                    if (productimage != null) {
                        cover = productimage.getPath();
                    }
                } else {
                    return null;
                }
                break;
            case scenic:
                ScenicInfo scenicInfo = scenicInfoService.get(comment.getTargetId());
                if (scenicInfo != null) {
                    name = scenicInfo.getName();
                    cover = scenicInfo.getCover();
                } else {
                    return null;
                }
                break;
            case hotel:
                Hotel hotel = hotelService.get(comment.getTargetId());
                if (hotel != null) {
                    name = hotel.getName();
                    Productimage productimage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
                    if (productimage != null) {
                        cover = productimage.getPath();
                    }
                } else {
                    return null;
                }
                break;
            case recplan:
                RecommendPlan recommendPlan = recommendPlanService.get(comment.getTargetId());
                if (recommendPlan != null) {
                    name = recommendPlan.getPlanName();
                    cover = recommendPlan.getCoverPath();
                } else {
                    return null;
                }
                break;
            case cruiseship:
                CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(comment.getTargetId());
                if (cruiseShipDate != null)  {
                    CruiseShip cruiseship = cruiseShipDate.getCruiseShip();
                    name = cruiseship.getName();
                    cover = cruiseship.getCoverImage();
                } else {
                    return null;
                }
                break;
            default:
                break;
        }
        response.setTargetName(name);
        response.setTargetCover(cover(cover));
        if (!comment.getCommentPhotos().isEmpty()) {
            response.setImageList(Lists.transform(comment.getCommentPhotos(), new Function<CommentPhoto, String>() {
                @Override
                public String apply(CommentPhoto input) {
                    return input.getImagePath();
                }
            }));
        }

        if (comment.getComments() != null && !comment.getComments().isEmpty()) {
            response.setReply(new ReplyCommentResponse(comment.getComments().get(0)));
        }
        return response;
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        }
        return QiniuUtil.URL + cover;
    }

    public void save(Comment comment, Member loginUser) {
        comment.setUser(loginUser);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        commentService.saveComment(comment);
    }

}
