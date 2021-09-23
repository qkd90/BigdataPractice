package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PresellActivity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;		
	
	/** 订单业务类型(商城/采购) */
	private String orderBizType;		
	
	/** 活动名称 */
	private String name;		
	
	/** 预售说明 */
	private String presellDesc;		
	
	/** 大促标签 */
	private String tag1;		
	
	/** 促销标签 */
	private String tag2;		
	
	/** 大促标签启用 */
	private String tag1Valid;		
	
	/** 促销标签启用 */
	private String tag2Valid;		
	
	/** 预售模式(定金模式/全款模式) */
	private String presellType;		
	
	/** 预售时间开始 */
	private Date presellStart;		
	
	/** 预售时间结束 */
	private Date presellEnd;		
	
	/** 尾款时间开始 */
	private Date paymentStart;		
	
	/** 预计发货时间 */
	private Date paymentEnd;		
	
	/** 预计发货时间 */
	private Date shipTime;		
	
	/** 预售协议 */
	private String presellAgreement;		
	
	/** 活动备注 */
	private String memo;		
	
	/** 是否限购 */
	private String limitFlg;		
	
	/** 限购数量 */
	private Integer limitNum;

	/** 限购数量单位 */
	private String limitUnit;
	
	/** 活动结束提醒 */
	private String activityNotifyFlag;		
	
	/** 活动结束提醒时间 */
	private Date activityNotifyTime;		
	
	/** 活动结束提醒手机 */
	private String activityNotifyPhone;		
	
	/** 尾款支付提醒 */
	private String paymenNotifyFlag;		
	
	/** 尾款支付提醒时间 */
	private Date paymenNotifyTime;
	
	/** 预计发货提醒 */
	private String shipNotifyFlag;		
	
	/** 预计发货提醒时间 */
	private Date shipNotifyTime;		
	
	/** 创建人 */
	private String createBy;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;

	/** 距尾款支付结束还有几小时*/
	private Integer endOfPaymentHaveTime;

	private List<PresellGood> presellGoodList;

	public Integer getEndOfPaymentHaveTime() {
		return endOfPaymentHaveTime;
	}

	public void setEndOfPaymentHaveTime(Integer endOfPaymentHaveTime) {
		this.endOfPaymentHaveTime = endOfPaymentHaveTime;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setOrderBizType(String orderBizType){
		this.orderBizType = orderBizType;
	}
	
	public String getOrderBizType(){
		return orderBizType;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setPresellDesc(String presellDesc){
		this.presellDesc = presellDesc;
	}
	
	public String getPresellDesc(){
		return presellDesc;
	}
	
	public void setTag1(String tag1){
		this.tag1 = tag1;
	}
	
	public String getTag1(){
		return tag1;
	}
	
	public void setTag2(String tag2){
		this.tag2 = tag2;
	}
	
	public String getTag2(){
		return tag2;
	}
	
	public void setTag1Valid(String tag1Valid){
		this.tag1Valid = tag1Valid;
	}
	
	public String getTag1Valid(){
		return tag1Valid;
	}
	
	public void setTag2Valid(String tag2Valid){
		this.tag2Valid = tag2Valid;
	}
	
	public String getTag2Valid(){
		return tag2Valid;
	}
	
	public void setPresellType(String presellType){
		this.presellType = presellType;
	}
	
	public String getPresellType(){
		return presellType;
	}
	
	public void setPresellStart(Date presellStart){
		this.presellStart = presellStart;
	}
	
	public Date getPresellStart(){
		return presellStart;
	}
	
	public void setPresellEnd(Date presellEnd){
		this.presellEnd = presellEnd;
	}
	
	public Date getPresellEnd(){
		return presellEnd;
	}
	
	public void setPaymentStart(Date paymentStart){
		this.paymentStart = paymentStart;
	}
	
	public Date getPaymentStart(){
		return paymentStart;
	}
	
	public void setPaymentEnd(Date paymentEnd){
		this.paymentEnd = paymentEnd;
	}
	
	public Date getPaymentEnd(){
		return paymentEnd;
	}
	
	public void setShipTime(Date shipTime){
		this.shipTime = shipTime;
	}
	
	public Date getShipTime(){
		return shipTime;
	}
	
	public void setPresellAgreement(String presellAgreement){
		this.presellAgreement = presellAgreement;
	}
	
	public String getPresellAgreement(){
		return presellAgreement;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setLimitFlg(String limitFlg){
		this.limitFlg = limitFlg;
	}
	
	public String getLimitFlg(){
		return limitFlg;
	}
	
	public void setLimitNum(Integer limitNum){
		this.limitNum = limitNum;
	}
	
	public Integer getLimitNum(){
		return limitNum;
	}
	
	public void setActivityNotifyFlag(String activityNotifyFlag){
		this.activityNotifyFlag = activityNotifyFlag;
	}
	
	public String getActivityNotifyFlag(){
		return activityNotifyFlag;
	}
	
	public void setActivityNotifyTime(Date activityNotifyTime){
		this.activityNotifyTime = activityNotifyTime;
	}
	
	public Date getActivityNotifyTime(){
		return activityNotifyTime;
	}
	
	public void setActivityNotifyPhone(String activityNotifyPhone){
		this.activityNotifyPhone = activityNotifyPhone;
	}
	
	public String getActivityNotifyPhone(){
		return activityNotifyPhone;
	}
	
	public void setPaymenNotifyFlag(String paymenNotifyFlag){
		this.paymenNotifyFlag = paymenNotifyFlag;
	}
	
	public String getPaymenNotifyFlag(){
		return paymenNotifyFlag;
	}
	
	public void setPaymenNotifyTime(Date paymenNotifyTime){
		this.paymenNotifyTime = paymenNotifyTime;
	}
	
	public Date getPaymenNotifyTime(){
		return paymenNotifyTime;
	}
	
	public void setShipNotifyFlag(String shipNotifyFlag){
		this.shipNotifyFlag = shipNotifyFlag;
	}
	
	public String getShipNotifyFlag(){
		return shipNotifyFlag;
	}
	
	public void setShipNotifyTime(Date shipNotifyTime){
		this.shipNotifyTime = shipNotifyTime;
	}
	
	public Date getShipNotifyTime(){
		return shipNotifyTime;
	}
	
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	
	public String getCreateBy(){
		return createBy;
	}
	
	public void setIsValid(String isValid){
		this.isValid = isValid;
	}
	
	public String getIsValid(){
		return isValid;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	
	public String getDelFlag(){
		return delFlag;
	}
	
	public void setDateCreated(Date dateCreated){
		this.dateCreated = dateCreated;
	}
	
	public Date getDateCreated(){
		return dateCreated;
	}
	
	public void setLastUpdated(Date lastUpdated){
		this.lastUpdated = lastUpdated;
	}
	
	public Date getLastUpdated(){
		return lastUpdated;
	}

	public List<PresellGood> getPresellGoodList() {
		return presellGoodList;
	}

	public void setPresellGoodList(List<PresellGood> presellGoodList) {
		this.presellGoodList = presellGoodList;
	}

	public String getLimitUnit() {
		return limitUnit;
	}

	public void setLimitUnit(String limitUnit) {
		this.limitUnit = limitUnit;
	}
}
