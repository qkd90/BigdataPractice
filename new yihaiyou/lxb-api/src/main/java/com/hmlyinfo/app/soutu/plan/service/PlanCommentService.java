package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanComment;
import com.hmlyinfo.app.soutu.plan.domain.PlanCommentReply;
import com.hmlyinfo.app.soutu.plan.mapper.PlanCommentMapper;
import com.hmlyinfo.app.soutu.plan.mapper.PlanCommentReplyMapper;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Service
public class PlanCommentService extends BaseService<PlanComment, Long> {

	@Autowired
	private PlanCommentMapper<PlanComment> planComMapper;
	@Autowired
	private PlanCommentReplyMapper<PlanCommentReply> planComReplyMapper;
	@Autowired
	private PlanService planService;

	@Override
	public BaseMapper<PlanComment> getMapper() {
		return planComMapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	/**
	 * 查询行程评论
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * </ul>
	 *
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchPlanCom(Map<String, Object> paramMap) {
		return (List) planComMapper.list(paramMap);
	}

	public ActionResult countPlanCom(Map<String, Object> paramMap) {
		int counts = planComMapper.count(paramMap);
		ActionResult result = new ActionResult();
		ResultList<Object> resultList = new ResultList<Object>();
		resultList.setCounts(counts);
		result.setResultList(resultList);
		return result;
	}


	/**
	 * 增加一条行程评论
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:评论内容{content}</li>
	 * </ul>
	 *
	 * @return planComment
	 * @throws java.io.UnsupportedEncodingException
	 */
	@Transactional
	public PlanComment addPlanCom(Map<String, Object> paramMap) throws UnsupportedEncodingException {

		long planId = Long.valueOf(paramMap.get("planId").toString());
		String content = paramMap.get("content").toString();
		long userId = MemberService.getCurrentUserId();

		PlanComment planCom = new PlanComment();
		planCom.setPlanId(planId);
		planCom.setUserId(userId);
		planCom.setContent(content);
		insert(planCom);
		addCommentNum(paramMap);

		return planCom;
	}

	/**
	 * 评论数+1方法
	 * <ul>
	 * <li>必选:行程ID{planId}</li>
	 * </ul>
	 *
	 * @return plan
	 */
	public Plan addCommentNum(Map<String, Object> paramMap) {
		Plan plan = planService.info(Long.valueOf(paramMap.get("planId").toString()));
		int commentNum = plan.getCommentNum();
		plan.setCommentNum(commentNum + 1);
		planService.update(plan);
		return plan;
	}

	/**
	 * 删除一条行程评论
	 * <ul>
	 * <li>必选:行程评论id{id}</li>
	 * </ul>
	 *
	 */
	@Transactional
	public void delPlanCom(Map<String, Object> paramMap) {
		String id = paramMap.get("id").toString();
		PlanComment planComment = planComMapper.selById(id);
		Validate.dataAuthorityCheck(planComment);
		paramMap.remove("id");
		paramMap.put("commentId", id);
		List<PlanCommentReply> planComReply = planComReplyMapper.list(paramMap);
		if (planComReply != null) {
			for (PlanCommentReply planReply : planComReply) {
				planComReplyMapper.del(planReply.getId().toString());
			}
		}
		planComMapper.del(id);
		paramMap.put("planId", planComment.getPlanId());
		delCommentNum(paramMap);
	}

	/**
	 * 评论数-1方法
	 * <ul>
	 * <li>必选:行程ID{planId}</li>
	 * </ul>
	 *
	 * @return plan
	 */
	public Plan delCommentNum(Map<String, Object> paramMap) {
		Plan plan = planService.info(Long.valueOf(paramMap.get("planId").toString()));
		int commentNum = plan.getCommentNum();
		plan.setCommentNum(commentNum - 1);
		planService.update(plan);
		return plan;
	}

	/**
	 * 增加一条行程评论回复
	 * <ul>
	 * <li>必选:行程评论id{commentId}</li>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:回复内容{userReturn}</li>
	 * </ul>
	 *
	 * @return PlanCommentReply
	 * @throws UnsupportedEncodingException
	 */
	public PlanCommentReply addPlanComReply(Map<String, Object> paramMap) throws UnsupportedEncodingException {

		long planId = Long.valueOf(paramMap.get("planId").toString());
		long commentId = Long.valueOf(paramMap.get("commentId").toString());
		String userReturn = paramMap.get("content").toString();

		long userId = MemberService.getCurrentUserId();

		PlanCommentReply ComReply = new PlanCommentReply();

		ComReply.setPlanId(planId);
		ComReply.setCommentId(commentId);
		ComReply.setUserId(userId);
		ComReply.setContent(userReturn);
		planComReplyMapper.insert(ComReply);

		return ComReply;
	}


	/**
	 * 查询行程评论回复
	 * <ul>
	 * <li>必选:行程评论id{commentId}</li>
	 * </ul>
	 *
	 * @return List
	 */
	public List<Map<String, Object>> searchReply(Map<String, Object> paramMap) {
		return planComReplyMapper.listWithUser(paramMap);
	}

	/**
	 * 删除一条行程评论回复
	 * <ul>
	 * <li>必选:行程评论回复id{id}</li>
	 * </ul>
	 *
	 */
	public void delPlanComReply(Map<String, Object> paramMap) {
		String id = paramMap.get("id").toString();
		PlanCommentReply uscComment = planComReplyMapper.selById(id);
		Validate.dataAuthorityCheck(uscComment);
		planComReplyMapper.del(id);
	}
}
