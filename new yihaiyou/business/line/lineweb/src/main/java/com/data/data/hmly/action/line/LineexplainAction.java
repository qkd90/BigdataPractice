package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Lineplaytitle;
import com.data.data.hmly.service.line.entity.enums.ChildStandardType;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineexplainAction extends FrameBaseAction {
	private static final long serialVersionUID = 8750753385473654676L;
	@Resource
	private LineexplainService			lineexplainService;

	@Resource
	private LineService lineService;

	private Long 						lineexplainId;
	private Long 						productId;
	private List<Long> 					playTitleId;
	private String 						defineTag;
	private String						lineLightPoint;
	private String 						orderKnow;
	private String						orderContext;
	private String 						tip;
	private String						tipContext;
	private String 						planDetail;
	private Integer						days;
	private List<Linedays>				linedays;

	private String						receiveStandard;
	private String						accrossScenic;
	private String						tripNotice;
	private String						specialLimit;
	private String						signWay;
	private String						payWay;
	private String						breachTip;
	private Integer						tripAgeMin;
	private Integer						tripAgeMax;
	private String						shoppingDesc;
	private String						remark;

    private String childStandardType; // 儿童价标准类型,
    private String childStartNum; // 儿童（年龄、身高）数值始,
    private String childEndNum; // 儿童（年龄、身高）数值止,
    private String childBed; // 儿童占床情况：不占床、-----,
    private String childOtherRemark; // 儿童交通、用餐等说明,
    private String heightChildStartNum; // 儿童（年龄、身高）数值始,
    private String heightChildEndNum; // 儿童（年龄、身高）数值止,
    private String heightChildBed; // 儿童占床情况：不占床、-----,
    private String heightChildOtherRemark; // 儿童交通、用餐等说明,
    private String childLongRemark; // 儿童长文本说明，当childStandardType为desc时使用,
	
	// 返回数据
	Map<String, Object>					map					= new HashMap<String, Object>();
	
	@AjaxCheck
	public Result saveLineexplain() {

		SysUser sysUser = getLoginUser();

		Lineexplain lineexplain = new Lineexplain();
		if (lineexplainId != null) {	// 修改
			lineexplain = lineexplainService.loadLineexplain(lineexplainId);
		} else {	// 新增
			lineexplain.setUserId(getLoginUser().getId());
			lineexplain.setCreateTime(new Date());

		}
		Line line = lineService.loadLine(productId);

		if (days != null) {
			line.setPlayDay(days);
		}
		lineexplain.setId(lineexplainId);
		lineexplain.setLineId(productId);
		lineexplain.setDefineTag(defineTag);
		lineexplain.setLineLightPoint(lineLightPoint);
		lineexplain.setOrderKnow(orderKnow);
		lineexplain.setOrderContext(orderContext);
		lineexplain.setTip(tip);
		lineexplain.setTipContext(tipContext);
        lineexplain.setReceiveStandard(receiveStandard);
        lineexplain.setAccrossScenic(accrossScenic);
        lineexplain.setTripNotice(tripNotice);
        lineexplain.setSpecialLimit(specialLimit);
        lineexplain.setSignWay(signWay);
        lineexplain.setPayWay(payWay);
        lineexplain.setBreachTip(breachTip);
        lineexplain.setTripAgeMin(tripAgeMin);
        lineexplain.setTripAgeMax(tripAgeMax);
		lineexplain.setShoppingDesc(shoppingDesc);
		lineexplain.setRemark(remark);
        ChildStandardType cst = ChildStandardType.none;
        if (StringUtils.isNotBlank(childStandardType)) {
            cst = ChildStandardType.valueOf(childStandardType);
        }
        fillChildProperty(cst, lineexplain);
		List<Lineplaytitle> lineplaytitles = new ArrayList<Lineplaytitle>();
		if (playTitleId != null) {
			for (Long titleId : playTitleId) {
				Lineplaytitle title = new Lineplaytitle();
				title.setLineId(productId);
				title.setPlayTitleId(titleId);
				title.setUserId(getLoginUser().getId());
				title.setCreateTime(new Date());
				lineplaytitles.add(title);
			}
		}

		lineService.update(line);
		lineexplainService.saveLineexplain(lineexplain, sysUser, planDetail, line);
//		lineexplainService.saveLineexplain(lineexplain, lineplaytitles, linedays);
		map.put("id", lineexplain.getId());
		simpleResult(map, true, "");
		return jsonResult(map);
	}

    private void fillChildProperty(ChildStandardType cst, Lineexplain lineexplain) {
        lineexplain.setChildStandardType(cst);
        lineexplain.setChildStartNum(null);
        lineexplain.setChildEndNum(null);
        lineexplain.setChildBed(null);
        lineexplain.setChildOtherRemark(null);
        lineexplain.setChildLongRemark(null);
        if (cst == ChildStandardType.age) {
            lineexplain.setChildStartNum(handleNum(childStartNum));
            lineexplain.setChildEndNum(handleNum(childEndNum));
            lineexplain.setChildBed(childBed);
            lineexplain.setChildOtherRemark(childOtherRemark);
        } else if (cst == ChildStandardType.height) {
            lineexplain.setChildStartNum(handleNum(heightChildStartNum));
            lineexplain.setChildEndNum(handleNum(heightChildEndNum));
            lineexplain.setChildBed(heightChildBed);
            lineexplain.setChildOtherRemark(heightChildOtherRemark);
        } else if (cst == ChildStandardType.desc) {
            lineexplain.setChildLongRemark(childLongRemark);
        }
    }

    /**
     * 截取小数点后面为0的数值
     * @param numStr
     * @return
     */
    private String handleNum(String numStr) {
        if (StringUtils.isNotBlank(numStr)) {
            Pattern p = Pattern.compile("\\d+\\.?\\d*");
            Matcher m = p.matcher(numStr);
            boolean b = m.matches();
            if (b) {
                return numStr;
            }
        }
        return null;
    }

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getDefineTag() {
		return defineTag;
	}

	public void setDefineTag(String defineTag) {
		this.defineTag = defineTag;
	}

	public String getLineLightPoint() {
		return lineLightPoint;
	}

	public void setLineLightPoint(String lineLightPoint) {
		this.lineLightPoint = lineLightPoint;
	}

	public String getOrderKnow() {
		return orderKnow;
	}

	public void setOrderKnow(String orderKnow) {
		this.orderKnow = orderKnow;
	}

	public String getOrderContext() {
		return orderContext;
	}

	public void setOrderContext(String orderContext) {
		this.orderContext = orderContext;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTipContext() {
		return tipContext;
	}

	public void setTipContext(String tipContext) {
		this.tipContext = tipContext;
	}

	public List<Long> getPlayTitleId() {
		return playTitleId;
	}

	public void setPlayTitleId(List<Long> playTitleId) {
		this.playTitleId = playTitleId;
	}

	public List<Linedays> getLinedays() {
		return linedays;
	}

	public void setLinedays(List<Linedays> linedays) {
		this.linedays = linedays;
	}

	public Long getLineexplainId() {
		return lineexplainId;
	}

	public void setLineexplainId(Long lineexplainId) {
		this.lineexplainId = lineexplainId;
	}

	public String getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getTripAgeMax() {
		return tripAgeMax;
	}

	public void setTripAgeMax(Integer tripAgeMax) {
		this.tripAgeMax = tripAgeMax;
	}

	public Integer getTripAgeMin() {
		return tripAgeMin;
	}

	public void setTripAgeMin(Integer tripAgeMin) {
		this.tripAgeMin = tripAgeMin;
	}

	public String getBreachTip() {
		return breachTip;
	}

	public void setBreachTip(String breachTip) {
		this.breachTip = breachTip;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
	}

	public String getSpecialLimit() {
		return specialLimit;
	}

	public void setSpecialLimit(String specialLimit) {
		this.specialLimit = specialLimit;
	}

	public String getTripNotice() {
		return tripNotice;
	}

	public void setTripNotice(String tripNotice) {
		this.tripNotice = tripNotice;
	}

	public String getAccrossScenic() {
		return accrossScenic;
	}

	public void setAccrossScenic(String accrossScenic) {
		this.accrossScenic = accrossScenic;
	}

	public String getReceiveStandard() {
		return receiveStandard;
	}

	public void setReceiveStandard(String receiveStandard) {
		this.receiveStandard = receiveStandard;
	}

    public String getChildStandardType() {
        return childStandardType;
    }

    public void setChildStandardType(String childStandardType) {
        this.childStandardType = childStandardType;
    }

    public String getChildStartNum() {
        return childStartNum;
    }

    public void setChildStartNum(String childStartNum) {
        this.childStartNum = childStartNum;
    }

    public String getChildEndNum() {
        return childEndNum;
    }

    public void setChildEndNum(String childEndNum) {
        this.childEndNum = childEndNum;
    }

    public String getChildBed() {
        return childBed;
    }

    public void setChildBed(String childBed) {
        this.childBed = childBed;
    }

    public String getChildOtherRemark() {
        return childOtherRemark;
    }

    public void setChildOtherRemark(String childOtherRemark) {
        this.childOtherRemark = childOtherRemark;
    }

    public String getChildLongRemark() {
        return childLongRemark;
    }

    public void setChildLongRemark(String childLongRemark) {
        this.childLongRemark = childLongRemark;
    }

    public String getHeightChildStartNum() {
        return heightChildStartNum;
    }

    public void setHeightChildStartNum(String heightChildStartNum) {
        this.heightChildStartNum = heightChildStartNum;
    }

    public String getHeightChildEndNum() {
        return heightChildEndNum;
    }

    public void setHeightChildEndNum(String heightChildEndNum) {
        this.heightChildEndNum = heightChildEndNum;
    }

    public String getHeightChildBed() {
        return heightChildBed;
    }

    public void setHeightChildBed(String heightChildBed) {
        this.heightChildBed = heightChildBed;
    }

    public String getHeightChildOtherRemark() {
        return heightChildOtherRemark;
    }

    public void setHeightChildOtherRemark(String heightChildOtherRemark) {
        this.heightChildOtherRemark = heightChildOtherRemark;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShoppingDesc() {
		return shoppingDesc;
	}

	public void setShoppingDesc(String shoppingDesc) {
		this.shoppingDesc = shoppingDesc;
	}
}
