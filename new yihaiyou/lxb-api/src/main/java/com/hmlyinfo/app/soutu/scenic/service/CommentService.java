package com.hmlyinfo.app.soutu.scenic.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.Comment;
import com.hmlyinfo.app.soutu.scenic.domain.CommentGood;
import com.hmlyinfo.app.soutu.scenic.domain.CommentImage;
import com.hmlyinfo.app.soutu.scenic.mapper.CommentGoodMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.CommentImageMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.CommentMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ColumnGenerator;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class CommentService extends BaseService<Comment, Long>{

	@Autowired
	private CommentMapper<Comment> mapper;
	@Autowired
	private CommentImageMapper<CommentImage> commentImageMapper;
	@Autowired
	private CommentGoodMapper<CommentGood> commentGoodMapper;
	
	@Override
	public BaseMapper<Comment> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 根据2个主键取得一个comment
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public Comment info(long commentId, long userId)
	{	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", commentId);
		paramMap.put("commentUserId", userId);
		mapper.list(paramMap);
		return ListUtil.getSingle(mapper.list(paramMap));
	}
	
	/**
	 * 根据景点id取景点评论
	 *  <li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public List<Map<String, Object>> getComment(Map<String, Object> paramMap){
		Validate.notNull(paramMap.get("scenicId"), ErrorCode.ERROR_51001);
				
		List<Map<String, Object>> commentMapList = new ArrayList<Map<String, Object>>();
		
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("id", "commentId");
		columnMapping.put("userId", "commentUserId");
		
		//取得评论信息
		List<Comment> commentList = list(paramMap);
		if(commentList.isEmpty()){
			List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
			return resList;
		}
				
		//取得当前用户点赞信息
		Map<String, Object> userGoodMap = new HashMap<String, Object>();
		userGoodMap.put("userId", MemberService.getCurrentUserId());
		List<CommentGood> commentGoodList = commentGoodMapper.list(userGoodMap);
				
		//把当前用户的点赞信息和评论信息join起来
		try {
			commentMapList = ListUtil.listJoin(commentList, commentGoodList, columnMapping, null, new ColumnGenerator(){
				@Override
				public Map<String, Object> generateColumns(Map<String, Object> leftMap, Map<String, Object> rightMap) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if(rightMap != null){
						resultMap.put("ifCanGood", false);
					} else{
						resultMap.put("ifCanGood", true);
					}
					return resultMap;
				}
			});
		
		
			//拼评论图片地址in查询字符串
			String commentUserIdList = new String();
			String commentIdList = new String();
			for(int i = 0 ; i < commentList.size(); ++i){
				if(i == 0){
					commentUserIdList = Long.valueOf(commentList.get(i).getUserId()).toString();
					commentIdList = Long.valueOf(commentList.get(i).getId()).toString();
				}else{
					commentUserIdList = commentUserIdList + ", " + commentList.get(i).getUserId();
					commentIdList = commentIdList + ", " + commentList.get(i).getId();
				}
			}
			Map<String, Object> commentImageParamMap = new HashMap<String, Object>();
			commentImageParamMap.put("commentUserIdList", commentUserIdList );
			commentImageParamMap.put("commentIdList", commentIdList );
			List<CommentImage> commentImageList = commentImageMapper.listIn(commentImageParamMap);
		
			//把评论信息和图片信息结合起来
		
			commentMapList = ListUtil.listJoinOne2N(commentMapList, commentImageList, columnMapping, new ColumnGenerator(){
				@Override
				public Map<String, Object> generateColumns(Map<String, Object> leftMap, Map<String, Object> rightMap) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("large", rightMap.get("addressLarge"));
					resultMap.put("medium", "addressMedium");
					resultMap.put("small", rightMap.get("addressSmall"));
					return resultMap;
				}
			}, "imageList");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return commentMapList;
	}

	/**
	 * 根据评论id取评论图片
	 *  <li>必选：评论id{commentId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public List<Map<String, String>> getImages(Map<String, Object> paramMap){
		

		Validate.notNull(paramMap.get("commentId"), ErrorCode.ERROR_51001);
		Validate.notNull(paramMap.get("commentUserId"), ErrorCode.ERROR_51001);

		
		List<CommentImage> commentImage = commentImageMapper.list(paramMap);
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for(int i = 0; i<commentImage.size(); ++i)
		{
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("large", commentImage.get(i).getAddressLarge());
			resultMap.put("medium", commentImage.get(i).getAddressMedium());
			resultMap.put("small", commentImage.get(i).getAddressSmall());
			resultList.add(resultMap);
		}
		
		return resultList;
		
	}
	
	/**
	 * 根据当前用户id和评论id查询是否还可以点赞。
	 * <ul>
	 *  <li>必选：评论id{commentId}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public boolean ifCanGood(Map<String, Object> paramMap){
		
		Validate.notNull(paramMap.get("commentId"), ErrorCode.ERROR_51001);
		Validate.notNull(paramMap.get("commentUserId"), ErrorCode.ERROR_51001);

		paramMap.put("userId", MemberService.getCurrentUserId());
		
		List<CommentGood> commentGood = commentGoodMapper.list(paramMap);
		
		return commentGood.size() == 0;
		
	}
	
	/**
	 * 用户点赞。
	 * <ul>
	 *  <li>必选：评论id{commentId}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@Transactional
		public void userGood(long commentId, long commentUserId){
		Comment comment = info(commentId, commentUserId);
		Validate.notNull(comment, ErrorCode.ERROR_52005);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("commentId", commentId);
		paramMap.put("commentUserId", commentUserId);
		
		//取得点赞表并验证
		List<CommentGood> commentGoodList = commentGoodMapper.list(paramMap);
		Validate.isTrue(commentGoodList.size() == 0, ErrorCode.ERROR_52003);
		
		CommentGood newCommentGood = new CommentGood();
		newCommentGood.setCommentId(commentId);
		newCommentGood.setCommentUserId(commentUserId);
		
		Long userId = MemberService.getCurrentUserId();
		newCommentGood.setUserId(userId);
		
		commentGoodMapper.insert(newCommentGood);
		
		mapper.updateCommentGood(comment);
	}
	
	/**
	 * 插入评论
	 * <ul>
	 * 	<li>必选scenicId</li>
	 * </ul>
	 * 
	 * @return
	 */
	public Comment newCom(Comment comment){
		//0:未审核,1:审核通过,2:审核未通过
		comment.setStatus(0);
		
		Long userId = MemberService.getCurrentUserId();
		
		comment.setUserId(userId);
		
		insert(comment);
		
		return comment;
	}
	
	/**
	 * 新增图片插入数据库
	 * <ul>
	 * 	<li>必选File图片文件</li>
	 * 	<li>commentId</li>
	 * 	<li>userId</li>
	 * </ul>
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String newImage(String fileName, long commentId) throws IOException{
		
		Long userId = MemberService.getCurrentUserId();
		
		//将返回的url插入到数据库中
		CommentImage imageurl = new CommentImage();
		imageurl.setAddressLarge(fileName);
		imageurl.setCommentId(commentId);
		imageurl.setCommentUserId(userId);
		
		commentImageMapper.insert(imageurl);
		
		return fileName;
	}
	

	/**
	 * 删除评论
	 * <ul>
	 * 	<li>commentId</li>
	 * </ul>
	 * 
	 * @return
	 * @throws IOException 
	 */
	public boolean delCom(Map<String, Object> paramMap){
		
		String commentId = paramMap.get("commentId").toString();
		
		mapper.del(commentId);
		
		return true;
	}
	
}
