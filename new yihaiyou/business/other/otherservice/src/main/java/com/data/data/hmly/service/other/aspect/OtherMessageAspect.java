package com.data.data.hmly.service.other.aspect;

import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.other.OtherMessageService;
import com.data.data.hmly.service.other.entity.OtherMessage;
import com.data.data.hmly.service.other.enums.MsgType;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.util.MsgTemplateUtil;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Service
public class OtherMessageAspect {
    //	private final Log log = LogFactory.getLog(this.getClass());
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private WechatDataTextService wechatDataTextService;
    @Resource
    private OtherMessageService otherMessageService;
    @Resource
    private CommentService commentService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private PlanService planService;
    @Resource
    private HotelService hotelService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private RecommendPlanService recommendPlanService;

    /**
     * 下单时发送消息
     *
     * @param joinPoint
     * @author caiys
     * @date 2015年12月24日 上午10:39:40
     */
    @After("execution (* com.data.data.hmly.service.order.dao.OrderDao.sendMessage(..))")
    public void doSendOrderMessage(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            Order order = (Order) objs[0];
            String title = "小帮通知";
            StringBuilder content = new StringBuilder();
            content.append("您于").append(DateUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm"))
                    .append("购买的“").append(ellipsisString(order.getName(), 25)).append("”订单待支付。");
            Long fromUserId = null;
            Long toUserId = order.getUser().getId();
            doBuilderMessage(MsgType.order, title, content.toString(), fromUserId, toUserId, null, null, order.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回复时发送消息
     *
     * @param joinPoint
     * @author caiys
     * @date 2015年12月24日 上午10:39:40
     */
    @After("execution (* com.data.data.hmly.service.comment.CommentService.saveComment(..))")
    public void doSendReplyMessage(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            Comment comment = (Comment) objs[0];
            if (comment.getUser() != null && comment.getRepliedId() != null) {    // 当前用户不为空，且是回复评论
                Comment repliedComment = commentService.getComment(comment.getRepliedId());
                String commentTarget = null;
                if (ProductType.delicacy.equals(comment.getType())) {
                    Delicacy delicacy = delicacyService.get(comment.getTargetId());
                    commentTarget = delicacy.getName();
                } else if (ProductType.hotel.equals(comment.getType())) {
                    Hotel hotel = hotelService.get(comment.getTargetId());
                    commentTarget = hotel.getName();
                } else if (ProductType.plan.equals(comment.getType())) {
                    Plan plan = planService.get(comment.getTargetId());
                    commentTarget = plan.getName();
                } else if (ProductType.recplan.equals(comment.getType())) {
                    RecommendPlan recommendPlan = recommendPlanService.get(comment.getTargetId());
                    commentTarget = recommendPlan.getPlanName();
                } else if (ProductType.scenic.equals(comment.getType())) {
                    ScenicInfo scenicInfo = scenicInfoService.get(comment.getTargetId());
                    commentTarget = scenicInfo.getName();
                }
                if (StringUtils.isNotBlank(commentTarget)) {
                    String title = commentTarget;
                    String content = ellipsisString(repliedComment.getContent(), 25);
                    Long fromUserId = comment.getUser().getId();
                    Long toUserId = repliedComment.getUser().getId();
                    doBuilderMessage(MsgType.comment, title, content.toString(), fromUserId, toUserId, comment.getTargetId(), comment.getType(), comment.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台回复旅行帮前台意见反馈
     * @param joinPoint
     * @author zzl
     * @date 2015-05-04
     */
    @After("execution(* com.data.data.hmly.service.lxbcommon.FeedbackService.doReplyFeedback(..))")
    public void doSendFeedBackReplyMsg(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            Feedback feedback = (Feedback) objs[0];
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("feedback", feedback);
            // 意见反馈有反馈者, 有回复者, 有回复内容
            if (feedback.getCreator() != null
                    && feedback.getReplier() != null
                    && StringUtils.isNotBlank(feedback.getReplyContent())) {
                String templateName = propertiesManager.getString("LVXBANG_REPLY_FEEDBACK_MSG_TLE");
                String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
                String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
                String[] contentArr = msgContent.split("@title");
                String title = contentArr[0];
                String cotent = contentArr[1];
                Long fromUserId = null;
                Long toUserId = feedback.getCreator().getId();
                doBuilderMessage(MsgType.notify, title, cotent, fromUserId, toUserId, null, null, feedback.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param msgType
     * @param title
     * @param content
     * @param fromUserId
     * @param toUserId
     * @author caiys
     * @date 2015年12月25日 上午11:21:37
     */
    private void doBuilderMessage(MsgType msgType, String title, String content, Long fromUserId, Long toUserId, Long commentTargetId,
                                  ProductType commentTargetType, Long messageId) {
        OtherMessage otherMessage = new OtherMessage();
        otherMessage.setMsgType(msgType);
        otherMessage.setTitle(title);
        otherMessage.setContent(content);
        if (fromUserId != null) {
            Member fromUser = new Member();
            fromUser.setId(fromUserId);
            otherMessage.setFromUser(fromUser);
        }
        Member toUser = new Member();
        toUser.setId(toUserId);
        otherMessage.setToUser(toUser);
        otherMessage.setCreateTime(new Date());
        otherMessage.setReadFlag(false);
        otherMessage.setDeleteFlag(false);
        otherMessage.setCommentTargetId(commentTargetId);
        otherMessage.setCommentTargetType(commentTargetType);
        otherMessage.setMessageId(messageId);
        otherMessageService.doCreateOtherMessage(otherMessage);
    }

    /**
     * 字符过长显示省略号
     *
     * @param original
     * @param wordNumber
     * @return
     * @author caiys
     * @date 2015年12月25日 上午11:43:32
     */
    private String ellipsisString(String original, int wordNumber) {
        if (StringUtils.isNotBlank(original)) {
            if (original.length() <= wordNumber) {
                return original;
            } else {
                String ellipsisStr = original.substring(0, wordNumber);
                return ellipsisStr + "...";
            }
        }
        return "";
    }

}
