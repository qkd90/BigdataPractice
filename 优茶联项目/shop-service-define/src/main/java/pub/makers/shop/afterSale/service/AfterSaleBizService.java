package pub.makers.shop.afterSale.service;

import pub.makers.shop.afterSale.vo.AfterSaleApply;

/**
 * Created by dy on 2017/4/15.
 */
public interface AfterSaleBizService {

	
	/**
	 * 申请售后
	 * @param apply
	 * orderType 订单类型
	 * asType 售后类型
	 * returnReason 售后理由
	 * attachment 附件
	 * orderListIds 订单商品ids
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void applyAfterSale(AfterSaleApply apply);

	/**
	 * 修改申请
	 * flowId 售后流程id
	 * returnReason 售后理由
	 * attachment 附件
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void updateAfterSale(AfterSaleApply apply);

	/**
	 * flowId 售后流程id
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void cancelApply(AfterSaleApply apply);
	
	/**
	 * 同意售后申请
	 * 对于退款，则为同意退款，流程跳转到财务
	 * 对于退货/换货，则为同意退换货，需要用户提交运单号
	 * flowId 售后流程id
	 * returnAmount 同意退款时填写的允许退款金额
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void agreeApply(AfterSaleApply apply);
	
	
	/**
	 * 拒绝售后申请
	 * flowId 售后流程id
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void rejectApply(AfterSaleApply apply);
	
	
	/**
	 * 财务执行退款操作
	 * flowId 售后流程id
	 * returnAmount 退款金额
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void refund(AfterSaleApply apply);
	

	/**
	 * 修改用户物流信息
	 * flowId 售后流程id
	 * freightNo 客户运单号
	 * freightCompany 客户物流信息
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void editUserShipping(AfterSaleApply apply);

	/**
	 * 后台修改用户物流信息
	 * flowId 售后流程id
	 * freightNo 客户运单号
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void editUserShippingManager(AfterSaleApply apply);

	/**
	 * 接受用户快递的商品信息
	 * flowId 售后流程id
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void acceptShipping(AfterSaleApply apply);
	
	
	/**
	 * 对商品进行换货
	 * flowId 售后流程id
	 * freightNo 客服换货运单号
	 * freightCompany 客服物流信息
	 * operDesc 操作备注
	 * operManId 操作人id
	 * operManType 操作人类型
	 */
	void changeGood(AfterSaleApply apply);
}
