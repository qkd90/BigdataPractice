package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.scenic.domain.Note;
import com.hmlyinfo.app.soutu.scenic.domain.NoteDay;
import com.hmlyinfo.app.soutu.scenic.domain.NoteGood;
import com.hmlyinfo.app.soutu.scenic.domain.NoteImage;
import com.hmlyinfo.app.soutu.scenic.domain.NoteScenic;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteDayMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteGoodMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteImageMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteScenicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService extends BaseService<Note, Long>{

	@Autowired
	private NoteMapper<Note> mapper;
	@Autowired
	private NoteDayMapper<NoteDay> dayMapper;
	@Autowired
	private NoteScenicMapper<NoteScenic> scenicMapper;
	@Autowired
	private NoteImageMapper<NoteImage> imgMapper;
    @Autowired
    private ScenicInfoService scenicInfoService;
    @Autowired
    private CtripHotelService hotelService;
    @Autowired
    private RestaurantService resService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteCommentService noteCommentService;
    @Autowired
    private NoteGoodMapper<NoteGood> noteGoodMapper;


    @Override
	public BaseMapper<Note> getMapper()
	{
		return mapper;
	}

	@Override
	public String getKey()
	{
		return "id";
	}

    @SuppressWarnings("unchecked")
	public Note detail(Map<String,Object> params)
	{
		// 查询游记详情
		Note note = mapper.selById(params.get("id").toString());
        User user = userService.info(note.getUserId());
        note.setUser(user);
        Validate.notNull(note, ErrorCode.ERROR_55001);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("noteId", note.getId());
        //将获取DayList方法提出来   update by林则金
        note.setDayList(getDayListByNoteId(paramMap));
        Map<String, Object> commentParams = new HashMap<String, Object>();
        commentParams.put("noteId", note.getId());
        int commentCount = noteCommentService.count(commentParams);
        note.setCommentCount(commentCount);
        return note;
	}

    @Transactional
    public void addComment(Map<String, Object> params) {
        noteCommentService.insert(params);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", params.get("noteImageId"));
        paramMap.put("commentCount", true);
        imgMapper.updateNum(paramMap);
    }

    public void deleteComment(String id) {
        Map<String,Object> noteComment = (Map<String,Object>)noteCommentService.info(Long.valueOf(id));
        Validate.dataAuthorityCheck(noteComment);
        noteCommentService.del(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", noteComment.get("noteImageId"));
        paramMap.put("commentCount", false);
        imgMapper.updateNum(paramMap);
    }

    public List<Map<String,Object>> listComment(Map<String,Object> params) {
        params.put("needUser", true);
        params.put("needPic", true);
        return (List)noteCommentService.list(params);
    }

    public List<Map<String,Object>> listMainComment(Map<String,Object> params) {
        params.put("needUser", true);
        params.put("onlyMain", true);
        return (List)noteCommentService.list(params);
    }

    @Transactional
    public void addGood(Long noteImageId) {
        Long userId = MemberService.getCurrentUserId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("noteImageId", noteImageId);
        List<NoteGood> noteGoods = noteGoodMapper.list(params);
        Validate.isTrue(noteGoods.isEmpty(), ErrorCode.ERROR_55002);
        NoteGood noteGood = new NoteGood();
        noteGood.setUserId(userId);
        noteGood.setNoteImageId(noteImageId);
        noteGoodMapper.insert(noteGood);
        params.clear();
        params.put("id", noteImageId);
        params.put("goodCount", true);
        imgMapper.updateNum(params);
    }

    
    /**
     * add by 林则金
     * 根据NoteId获取DayList数据
     * @param paramMap
     * @return
     */
    public List<NoteDay> getDayListByNoteId(Map<String, Object> paramMap)
    {
        // 查询游记天
		List<NoteDay> dayList = dayMapper.list(paramMap);
        // 查询游记景点
        Map<Long, Boolean> commentMap = new HashMap<Long, Boolean>();
        if (paramMap.get("userId") != null) {
            paramMap.put("userId", paramMap.get("userId"));
            List<NoteGood> noteGoods = noteGoodMapper.list(paramMap);
            for (NoteGood noteGood : noteGoods) {
                commentMap.put(noteGood.getNoteImageId(), true);
            }
            paramMap.remove("userId");
        }
        for (NoteDay day : dayList) {
            paramMap.put("notedayId", day.getId());
            List<NoteScenic> scenicList = scenicMapper.list(paramMap);
            // 查询游记图片
            for (NoteScenic scenic : scenicList) {

            	TripType scenicType = TripType.valueOf(scenic.getScenicType());

            	String sname = "";
            	if (scenicType == TripType.SCENIC)
            	{
            		Map<String, Object> scenicInfo = (Map<String, Object>) scenicInfoService.info(scenic.getScenicId());
            		if (scenicInfo != null)
            		{
            			sname = scenicInfo.get("name").toString();
            		}
            	}
            	else if (scenicType == TripType.HOTEL)
            	{
            		CtripHotel hotel = hotelService.info(scenic.getScenicId());
            		if (hotel != null)
            		{
            			sname = hotel.getHotelName();
            		}
            	}
            	else if (scenicType == TripType.RESTAURANT)
            	{
            		Restaurant res = resService.info(scenic.getScenicId());
            		if (res != null)
            		{
            			sname = res.getResName();
            		}
            	}
            	// 需要判断景点的类型
                scenic.setScenicName(sname);
                paramMap.put("noteScenicId", scenic.getId());
                List<NoteImage> imageList = imgMapper.list(paramMap);
                for (NoteImage noteImage : imageList) {
                    if (commentMap.get(noteImage.getId()) != null) {
                        noteImage.setPraised(true);
                    }
                }
                scenic.setImageList(imageList);
            }
            day.setScenicList(scenicList);
        }
        return dayList;
    }
}
