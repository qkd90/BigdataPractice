package com.hmlyinfo.app.soutu.delicacy.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy.Cuisine;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy.Taste;
import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyGood;
import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyPicture;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.mapper.DelicacyMapper;
import com.hmlyinfo.app.soutu.delicacy.mapper.DelicacyPictureMapper;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class DelicacyService extends BaseService<Delicacy, Long> {

	@Autowired
	private DelicacyMapper<Delicacy>				mapper;
	@Autowired
	private DelicacyPictureMapper<DelicacyPicture>	pictureMapper;
	@Autowired
	private RestaurantMapper<Restaurant>			restMapper;
	@Autowired
	private DelicacyGoodService						goodService;

	@Override
	public BaseMapper<Delicacy> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	public List<Delicacy> listPrice(Map<String, Object> paramMap) {
		return mapper.listPrice(paramMap);
	}

	/**
	 * 系统根据查询条件列出美食列表
	 * <ul>
	 * <li>必选:城市id{cityId}</li>
	 * <li>可选:当地人好评数{localNum}</li>
	 * <li>可选:游客好评数{touristNum}</li>
	 * <li>可选: 美食名{foodName}</li>
	 * <li>可选:菜系{create_time}</li>
	 * <li>可选:口味{create_time}</li>
	 * <li>可选:价格{create_time}</li>
	 * </ul>
	 * 
	 * @return DelicacyList
	 * @throws UnsupportedEncodingException
	 */
	public List<Delicacy> getDelicacy(Map<String, Object> paramMap) throws UnsupportedEncodingException {

		if (paramMap.get("price") != null) {
			String priceStr = paramMap.get("price").toString();
			String[] priceStrArray = priceStr.split(",");

			paramMap.put("lowPrice", priceStrArray[0]);

			if (priceStrArray.length > 1) {
				paramMap.put("highPrice", priceStrArray[1]);
			}
		}

		if (paramMap.get("cuisine") != null) {
			paramMap.put("cuisine", Cuisine.nameOf(Integer.parseInt(paramMap.get("cuisine").toString())));
		}

		if (paramMap.get("taste") != null) {
			paramMap.put("taste", Taste.nameOf(Integer.parseInt(paramMap.get("taste").toString())));
		}

		return mapper.list(paramMap);
	}

	/**
	 * 系统通过美食id返回更多美食图片
	 * <ul>
	 * <li>必选:美食id{delicacyId}</li>
	 * </ul>
	 * 
	 * @return Pictures
	 */
	public Map<String, Object> getPicture(Map<String, Object> paramMap) {

		List<DelicacyPicture> pictList = pictureMapper.list(paramMap);
		for (DelicacyPicture pict : pictList) {
			paramMap.put(pict.getId().toString(), pict.getFoodPicture());
		}

		return paramMap;
	}

	/**
	 * 系统通过美食ID查询餐厅
	 * <ul>
	 * <li>必选:美食id{delicacyId}</li>
	 * </ul>
	 * 
	 * @return Restaurant list
	 */
	public List<Restaurant> getRest(Map<String, Object> paramMap) {

		return restMapper.list(paramMap);
	}

	/**
	 * 美食分享数
	 * <ul>
	 * <li>必选:美食id{delicacyId}</li>
	 * </ul>
	 */
	public void addShareNum(Map<String, Object> paramMap) {
		Delicacy delicacy = mapper.selById(paramMap.get("delicacyId").toString());
		delicacy.setShareNum(delicacy.getShareNum() + 1);
		mapper.update(delicacy);
	}

	/**
	 * 美食点赞
	 * <ul>
	 * <li>必选:美食id{delicacyId}</li>
	 * </ul>
	 * 
	 * @return flag=true表示成功，flag=false表示失败
	 */
	public Map<String, Object> addGood(Map<String, Object> paramMap) {
		paramMap.put("userId", MemberService.getCurrentUserId());
		List<DelicacyGood> delGoodList = goodService.list(paramMap);
		if (delGoodList.isEmpty()) {
			addDelicacyGood(paramMap);
			paramMap.put("flag", true);
		} else {
			paramMap.put("flag", false);
		}
		return paramMap;
	}

	public void addDelicacyGood(Map<String, Object> paramMap) {
		DelicacyGood delicacyGood = new DelicacyGood();
		delicacyGood.setDelicacyId(Long.valueOf(paramMap.get("delicacyId").toString()));
		delicacyGood.setUserId(MemberService.getCurrentUserId());
		goodService.insert(delicacyGood);
		paramMap.put("goodNum", true);
		paramMap.put("id", paramMap.get("delicacyId"));
		mapper.updateAllNum(paramMap);
	}
}
