package com.data.data.hmly.service.line;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.dao.LineDao;
import com.data.data.hmly.service.line.dao.LineacrosscitysDao;
import com.data.data.hmly.service.line.dao.LinedaysDao;
import com.data.data.hmly.service.line.dao.LinedaysplanDao;
import com.data.data.hmly.service.line.dao.LineexplainDao;
import com.data.data.hmly.service.line.dao.LineplaytitleDao;
import com.data.data.hmly.service.line.dao.LinetypepricedateDao;
import com.data.data.hmly.service.line.entity.*;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.line.entity.enums.ProductAttr;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class LineexplainService {
	@Resource
	private LineexplainDao lineexplainDao;
	@Resource
	private LineplaytitleDao lineplaytitleDao;
	@Resource
	private LinedaysDao linedaysDao;
	@Resource
	private LinedaysplanDao linedaysplanDao;
	@Resource
	private LineacrosscitysDao lineacrosscitysDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private LinetypepricedateDao linetypepricedateDao;

	@Resource
	private LineImagesService planInfoImagesService;

	@Resource
	private LineDaysPlanInfoService planInfoService;

	@Resource
	private LinedaysProductPriceService linedaysProductPriceService;

	@Resource
	private LinedaysplanService linedaysplanService;

	public void save(Lineexplain lineexplain) {
		lineexplainDao.save(lineexplain);
	}

	public void update(Lineexplain lineexplain) {
		lineexplainDao.update(lineexplain);
	}


	public Linedays parasLineDaysObject(JSONObject dayObject, Lineexplain lineexplain, SysUser sysUser) {
		Linedays linedays = new Linedays();
		if (dayObject.get("id") == null) {
			linedays.setLineId(lineexplain.getLineId());
			linedays.setLineexplain(lineexplain);
			linedays.setCreateTime(new Date());
			linedays.setUserId(sysUser.getId());
		} else {
			linedays = linedaysDao.load(Long.parseLong(dayObject.get("id").toString()));
		}

		if (dayObject.get("lineDay") != null) {
			String lineDay = (String) dayObject.get("lineDay");
			linedays.setLineDay(Integer.parseInt(lineDay));
		}
		if (dayObject.get("dayDesc") != null) {
			linedays.setDayDesc((String) dayObject.get("dayDesc"));
		}
		if (dayObject.get("arrange") != null) {
			linedays.setArrange((String) dayObject.get("arrange"));
		}
		if (dayObject.get("meals") != null) {
			linedays.setMeals((String) dayObject.get("meals"));
		}
		if (dayObject.get("hotelName") != null) {
			linedays.setHotelName((String) dayObject.get("hotelName"));
		}
		return linedays;
	}

	public void saveLineexplain(Lineexplain lineexplain, SysUser sysUser, String planDetail, Line line) {
		// 保存行程说明
		lineexplainDao.save(lineexplain);
		if (lineexplain.getId() != null) {	// 修改时先删除行程标签、行程天、天内行程、途径城市
			linedaysplanDao.delByLineId(lineexplain.getLineId());
			planInfoService.delByLineId(lineexplain.getLineId());
			planInfoImagesService.delByLineId(lineexplain.getLineId(), LineImageType.detail);
		}
		if (StringUtils.isNotBlank(planDetail)) {
			JSONArray days = JSONArray.fromObject(planDetail);
			if (!days.isEmpty()) {
				List<Long> linedayIds = new ArrayList<Long>();
				for (Object tempO: days) {
					JSONObject tempDayObject = JSONObject.fromObject(tempO);
					if (tempDayObject.get("id") != null) {
						linedayIds.add(Long.parseLong(tempDayObject.get("id").toString()));
					}
				}
				linedaysDao.delNotExistIdList(linedayIds, lineexplain.getLineId());
				linedaysProductPriceService.delByNotExistDayIdList(linedayIds, lineexplain.getLineId());
				for (Object o : days) {
					Linedays linedays = new Linedays();
					JSONObject dayObject = JSONObject.fromObject(o);
					linedays = parasLineDaysObject(dayObject, lineexplain, sysUser);
					linedaysDao.saveOrUpdate(linedays, linedays.getId()); //保存天

					if (line.getProductAttr() == ProductAttr.ziyou) {
						continue;
					}

					if (dayObject.get("timeList") != null) {
						JSONArray planInfos = JSONArray.fromObject(dayObject.get("timeList"));
//						List<Linedaysplan> linedaysplanList = new ArrayList<Linedaysplan>();
						if (!planInfos.isEmpty()) {
							for (Object lineSpan : planInfos) {
								JSONObject daysPlanJson = JSONObject.fromObject(lineSpan);
								Linedaysplan linedaysplan = new Linedaysplan();
								if (daysPlanJson.get("timeNode") != null) {
									linedaysplan.setTimeNode((String) daysPlanJson.get("timeNode"));
								}
								if (daysPlanJson.get("timeDesc") != null) {
									linedaysplan.setTimeDesc((String) daysPlanJson.get("timeDesc"));
								}
								linedaysplan.setLinedays(linedays);
								linedaysplan.setLineId(lineexplain.getLineId());
								linedaysplan.setCreateTime(new Date());
								linedaysplan.setUserId(sysUser.getId());
								//linedaysplanList.add(linedaysplan);
								linedaysplanDao.save(linedaysplan);	//保存时间节点
								if (daysPlanJson.get("timeInfoList") != null) {
									JSONArray timeInfoJsonArr = JSONArray.fromObject(daysPlanJson.get("timeInfoList"));
									if (!timeInfoJsonArr.isEmpty()) {
										List<LineDaysPlanInfo> lineDaysPlanInfos = new ArrayList<LineDaysPlanInfo>();
										for (Object timeInfo : timeInfoJsonArr) {
											LineDaysPlanInfo lineDaysPlanInfo = new LineDaysPlanInfo();
											JSONObject timeInfoJson = JSONObject.fromObject(timeInfo);
											if (timeInfoJson.get("littleTitle") != null) {
												lineDaysPlanInfo.setLittleTitle((String) timeInfoJson.get("littleTitle"));
											}
											if (timeInfoJson.get("titleDesc") != null) {
												lineDaysPlanInfo.setTitleDesc((String) timeInfoJson.get("titleDesc"));
											}
											lineDaysPlanInfo.setLinedays(linedays);
											lineDaysPlanInfo.setLinedaysplan(linedaysplan);
											lineDaysPlanInfo.setLineId(lineexplain.getLineId());
											lineDaysPlanInfos.add(lineDaysPlanInfo);

											planInfoService.save(lineDaysPlanInfo);		//保存时间节点下详情内容组

											if (timeInfoJson.get("imageList") != null) {
												JSONArray planImageJsonArr = JSONArray.fromObject(timeInfoJson.get("imageList"));
												if (!planImageJsonArr.isEmpty()) {
													List<LineImages> daysPlanInfoImageses = new ArrayList<LineImages>();
													for (Object imageObject : planImageJsonArr) {
														JSONObject imageJson = JSONObject.fromObject(imageObject);
														LineImages daysPlanInfoImages = new LineImages();
														if (imageJson.get("imageUrl") != null) {
															daysPlanInfoImages.setImageUrl((String) imageJson.get("imageUrl"));
														}
														if (imageJson.get("imageDesc") != null) {
															daysPlanInfoImages.setImageDesc((String) imageJson.get("imageDesc"));
														}
														daysPlanInfoImages.setLinedaysplan(linedaysplan);
														daysPlanInfoImages.setLineDaysPlanInfo(lineDaysPlanInfo);
														daysPlanInfoImages.setLineId(lineexplain.getLineId());
														daysPlanInfoImages.setLineImageType(LineImageType.detail);
														//daysPlanInfoImageses.add(daysPlanInfoImages);
														planInfoImagesService.save(daysPlanInfoImages);    //保存时间节点下详情图片组
													}
													//planInfoImagesService.saveAll(daysPlanInfoImageses);	//保存时间节点下详情图片组
												}
											}
										}

									}
								}
							}

						}
					}

				}
			}
		}
	}

	
	/**
	 * 保存行程内容：行程说明、行程标签、行程天、天内行程、途径城市
	 * @author caiys
	 * @date 2015年10月22日 下午8:04:44
	 * @param lineexplain
	 * @param lineplaytitles
	 * @param linedays
	 */
	public void saveLineexplain(Lineexplain lineexplain, List<Lineplaytitle> lineplaytitles, List<Linedays> linedays) {
		// 是否需要更新状态
		boolean isUpdateStatus = false;
		if (lineexplain.getId() == null) {
			isUpdateStatus = true;
		}



		// 保存行程说明
		lineexplainDao.save(lineexplain);
		if (lineexplain.getId() != null) {	// 修改时先删除行程标签、行程天、天内行程、途径城市
			lineplaytitleDao.delByLineId(lineexplain.getLineId());
			linedaysplanDao.delByLineId(lineexplain.getLineId());
			linedaysDao.delByLineId(lineexplain.getLineId());
			lineacrosscitysDao.delByLineId(lineexplain.getLineId());
		}
		
		// 保存行程标签
		if (lineplaytitles != null && lineplaytitles.size() > 0) {
			lineplaytitleDao.save(lineplaytitles);
		}
		// 保存行程天
		Integer countDayPlan = 0;
		Set<Long> cityIdSet = new LinkedHashSet<Long>();
		if (lineplaytitles != null) {
			if (linedays != null) {
				for (Linedays lineday : linedays) {
					lineday.setLineId(lineexplain.getLineId());
					lineday.setLineexplain(lineexplain);
					if (lineday.getMeal() != null && lineday.getMeal().size() > 0) {
						StringBuilder sb = new StringBuilder();
						for (String m : lineday.getMeal()) {
							sb.append(m).append(",");
						}
						sb.deleteCharAt(sb.length()-1);
						lineday.setMeals(sb.toString());
					}
					lineday.setUserId(lineexplain.getUserId());
					lineday.setCreateTime(lineexplain.getCreateTime());
					linedaysDao.save(lineday);
					// 保存天内行程
					if (lineday.getDayPlan() != null && lineday.getDayPlan().size() > 0) {
						countDayPlan = countDayPlan + lineday.getDayPlan().size();
						int index = 1;
						for (int i = 0; i < lineday.getDayPlan().size(); i++) {
							Linedaysplan linedaysplan = lineday.getDayPlan().get(i);
							if (linedaysplan != null && linedaysplan.getTypeId() != null && linedaysplan.getType() != null) {		// 过滤添加未选择的行程
								linedaysplan.setLineId(lineexplain.getLineId());
								linedaysplan.setLinedays(lineday);
								linedaysplan.setShowOrder(index);
								//linedaysplan.setType("scenic");
								linedaysplan.setUserId(lineexplain.getUserId());
								linedaysplan.setCreateTime(lineexplain.getCreateTime());
								linedaysplanDao.save(linedaysplan);
								cityIdSet.add(linedaysplan.getCityId());
								index++;
							}
						}
					}
				}
			}
		}
		
		Line line = lineDao.load(lineexplain.getLineId());
		// 保存途径城市
		Iterator<Long> its = cityIdSet.iterator();
		while (its.hasNext()) {
			Long cityId = its.next();
			/*Line line = new Line();
			line.setId(lineexplain.getLineId());*/
			TbArea area = new TbArea();
			area.setId(cityId);
			Lineacrosscitys lineacrosscity = new Lineacrosscitys();
			lineacrosscity.setLine(line);
			lineacrosscity.setTbArea(area);
			lineacrosscitysDao.save(lineacrosscity);
		}

		// 如果是新增，最后一步更新线路状态
		if (isUpdateStatus) {
			// 校验报价和天内行程
			Date[] dates = DateUtils.getRangeDate();
			Integer countPrice = linetypepricedateDao.checkCount(lineexplain.getLineId(), null, dates[0], dates[1]);
//			Integer countDayPlan = linedaysplanDao.checkCount(lineexplain.getLineId(), null);
			if (countPrice > 0 && countDayPlan > 0) {
				line.setLineStatus(LineStatus.show);
			}
		}
		// 更新游玩天数
		if (linedays != null) {
			line.setPlayDay(linedays.size());
		}
		lineDao.update(line);

	}
	
	/**
	 * 查询线路说明
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param lineexplain
	 * @return
	 */
	public List<Lineexplain> findLineexplain(Lineexplain lineexplain) {
		return lineexplainDao.findLineexplain(lineexplain);
	}
	
	/**
	 * @author caiys
	 * @date 2015年10月26日 上午9:12:29
	 * @param id
	 * @return
	 */
	public Lineexplain loadLineexplain(Long id) {
		return lineexplainDao.load(id);
	}

	public Lineexplain getByLine(Long lineId) {
		Criteria<Lineexplain> criteria = new Criteria<Lineexplain>(Lineexplain.class);
		criteria.eq("lineId", lineId);
		return lineexplainDao.findUniqueByCriteria(criteria);
	}
}
