package com.data.data.hmly.service;

import com.data.data.hmly.action.yhypc.response.CommentResponse;
import com.data.data.hmly.action.yhypc.vo.CommentVo;
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
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-03-07,0007.
 */
@Service
public class CommentWebService {
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
    private OrderService orderService;
    @Resource
    private TicketService ticketService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private MemberService memberService;

    public void save(Integer score, OrderDetail orderDetail, Member user, Comment comment) {
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

        Boolean hasComment = true;
        Order order = orderDetail.getOrder();
        for (OrderDetail detail : order.getOrderDetails()) {
            hasComment = hasComment && detail.getHasComment();
        }
        if (hasComment) {
            order.setHasComment(true);
            orderService.update(order);
        }
    }

    public CommentResponse commentToResponse(Comment comment) {
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
        return response;
    }

    public List<Comment> getWebComments(Comment condition, Page page) {
        return commentService.getCommentList(condition, page);
    }


    /**
     * 构建一海游PC端前台评论数据
     * @param comments
     * @return
     */
    public List<CommentVo> buildCommentData(List<Comment> comments) {
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return -Long.compare(c1.getCreateTime().getTime(), c2.getCreateTime().getTime());
            }
        });

        List<CommentVo> commentVos = new ArrayList<CommentVo>();
        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getComments();
            if (comment.getUser() != null && UserType.USER.equals(comment.getUser().getUserType()) && comment.getRepliedId() == null) {
                CommentVo commentVo = new CommentVo();
                commentVo.setId(comment.getId());
                commentVo.setContent(comment.getContent());
                Member member = memberService.get(comment.getUser().getId());
                if (StringUtils.hasText(comment.getNickName())) {
                    commentVo.setNickName(comment.getNickName());
                } else {
                    commentVo.setNickName(member.getNickName());
                }
                if (StringUtils.hasText(member.getHead())) {
                    commentVo.setAvatar(member.getHead());
                }
                commentVo.setCreateTime(DateUtils.format(comment.getCreateTime(), "yyyy-MM-dd"));
                if (!comment.getCommentScores().isEmpty()) {
                    commentVo.setScore((comment.getCommentScores().get(0).getScore()) / 20);
                }
                if (replyComments != null) {
                    List<CommentVo> replyCommentVos = new ArrayList<CommentVo>();
                    for (Comment replyComment : replyComments) {
                        if (replyComment.getUser() != null) {
                            CommentVo replyCommentVo = new CommentVo();
                            replyCommentVo.setId(replyComment.getId());
                            replyCommentVo.setContent(replyComment.getContent());
                            Member replyMember = memberService.get(replyComment.getUser().getId());
                            if (replyMember == null) {
                                replyMember = new Member();
                                replyMember.setNickName("商家回复");
                            }
                            if (StringUtils.hasText(replyMember.getNickName())) {
                                replyCommentVo.setNickName(replyComment.getNickName());
                            } else {
                                replyCommentVo.setNickName(replyMember.getNickName());
                            }
                            replyCommentVo.setCreateTime(DateUtils.format(replyComment.getCreateTime(), "yyyy-MM-dd"));
                            if (StringUtils.hasText(replyMember.getHead())) {
                                replyCommentVo.setAvatar(replyComment.getHead());
                            }
                            replyCommentVos.add(replyCommentVo);
                        }
                    }
                    commentVo.setReplyCommentVos(replyCommentVos);
                }
                commentVos.add(commentVo);
            }
        }
        return commentVos;
    }

    /**
     * 获取有效评论数量(不含回复)
     * @param comments
     * @return
     */
    public Integer commentCount(List<Comment> comments) {
        Integer commentCount = 0;
        for (Comment comment : comments) {
            if (comment.getRepliedId() == null)
                commentCount++;
        }
        return commentCount;
    }

    /**
     * 根据评论列表计算好评数量
     * @param comments
     * @return
     */
    public Integer goodRateCount(List<Comment> comments) {
        Integer goodRateCount = 0;
        for (Comment comment : comments) {
            List<CommentScore> commentScores = comment.getCommentScores();
            for (CommentScore commentScore : commentScores) {
                if ("总体评价".equals(commentScore.getCommentScoreType().getName())) {
                    if (commentScore.getScore() > 80) {
                        goodRateCount++;
                    }
                    break;
                }
            }
        }

        return goodRateCount;
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
}
