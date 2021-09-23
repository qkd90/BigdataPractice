package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.line.LineImagesService;
import com.data.data.hmly.service.line.LineDaysPlanInfoService;
import com.data.data.hmly.service.line.LinedaysService;
import com.data.data.hmly.service.line.LinedaysplanService;
import com.data.data.hmly.service.line.entity.LineDaysPlanInfo;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.Linedaysplan;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class LinedaysAction extends FrameBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -45931220270373068L;

	@Resource
	private LinedaysplanService linedaysplanService;
	@Resource
	private LinedaysService linedaysService;

	@Resource
	private LineDaysPlanInfoService lineDaysPlanInfoService;

	@Resource
	private LineImagesService lineImagesService;


	private Linedays linedays = new Linedays();



	private Long productId;
	private Long dayId;
	private Long timeId;

	private Long timeInfoId;

	public Result getLineDaysByLineId() {
		List<Linedays> linedaysList = new ArrayList<Linedays>();
		if (productId != null) {
			linedays.setLineId(productId);
			linedaysList = linedaysService.list(linedays, null);
		}
		return datagrid(linedaysList);
	}

	public Result getLinePlanDaysBydayId() {

		List<Linedaysplan> linedaysplanList = new ArrayList<Linedaysplan>();
		if (dayId != null) {
			linedaysplanList = linedaysplanService.getLinePlanDaysBydayId(dayId);
		}
		return datagrid(linedaysplanList);
	}

	public Result getLinePlanDaysInfoByTimeId() {
		List<LineDaysPlanInfo> linedaysplanInfoList = new ArrayList<LineDaysPlanInfo>();

		if (timeId != null) {
			linedaysplanInfoList = lineDaysPlanInfoService.getLinePlanDaysInfosByTimeId(timeId);
		}
		return datagrid(linedaysplanInfoList);
	}


	public Result getLinePlanDaysInfoImagesByTimeInfoId() {
		List<LineImages> lineImageses = new ArrayList<LineImages>();

		if (timeInfoId != null) {
			lineImageses = lineImagesService.getDaysInfoImagesByTimeInfoId(timeInfoId, LineImageType.detail);
		}
		return datagrid(lineImageses);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Linedays getLinedays() {
		return linedays;
	}

	public void setLinedays(Linedays linedays) {
		this.linedays = linedays;
	}

	public Long getDayId() {
		return dayId;
	}

	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}

	public Long getTimeId() {
		return timeId;
	}

	public void setTimeId(Long timeId) {
		this.timeId = timeId;
	}

	public Long getTimeInfoId() {
		return timeInfoId;
	}

	public void setTimeInfoId(Long timeInfoId) {
		this.timeInfoId = timeInfoId;
	}
}
