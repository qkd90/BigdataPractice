package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/3/30.
 */
@Component
public class JszxOrderQuartz {


    @Resource
    private JszxOrderService jszxOrderService;

    @Resource
    private JszxOrderDetailService jszxOrderDetailService;

    @Resource
    private ProductValidateCodeService productValidateCodeService;

    @Resource
    private BalanceService balanceService;


    public void doAndUpdateJszxOrder() {

        List<JszxOrder> jszxOrders = jszxOrderService.getJszxOrderListByNoCanceled(SourceType.JSZX, JszxOrderStatus.CANCELED);

        for (JszxOrder jszxOrder : jszxOrders) {
            Date startTime = DateUtils.getStartDay(new Date(), 0);
            Date endTime = DateUtils.getEndDay(new Date(), 0);

            JszxOrderDetail searchOrderDetail = new JszxOrderDetail();
            searchOrderDetail.setStartTime(startTime);
            searchOrderDetail.setEndTime(endTime);
            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOrderDetailListByDate(jszxOrder, searchOrderDetail);

            List<JszxOrderDetail> jszxDetailList = new ArrayList<JszxOrderDetail>();

            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {
                if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                    jszxDetailList.add(jszxOrderDetail);
                }
            }


            if (jszxOrder.getProType() == ProductType.line) {
                for (JszxOrderDetail jszxOrderDetail : jszxDetailList) {
                    jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.USED);
                    jszxOrderDetailService.update(jszxOrderDetail);
                }

            } else {

                for (JszxOrderDetail jszxOrderDetail : jszxDetailList) {

                    Integer orderInitCount = jszxOrderDetail.getCount();
                    Integer orderRestCound = jszxOrderDetail.getRestCount();
                    Integer orderRefundCound = jszxOrderDetail.getRefundCount();

                    ProductValidateCode productValidateCode = new ProductValidateCode();
                    productValidateCode.setTicketNo(jszxOrderDetail.getTicketNo());
                    productValidateCode.setOrderId(jszxOrder.getId());
                    productValidateCode = productValidateCodeService.getValidateByProductCode(productValidateCode);
                    if (productValidateCode != null) {


//                        Float validInitCountFloat = productValidateCode.getOrderInitCount().floatValue();
//                        Float validRestCountFloat = productValidateCode.getOrderCount().floatValue();
//                        Float validRefundCountFloat = productValidateCode.getRefundCount().floatValue();
//
//                        Integer validInitCount = productValidateCode.getOrderInitCount();
//                        Integer validRestCount = productValidateCode.getOrderCount();
//                        Integer validRefundCount = productValidateCode.getRefundCount();
//
//
//                        Float restPrice = jszxOrderDetail.getActualPay();
//
//                        Float validCountFloat = validInitCountFloat - (validRestCountFloat + validRefundCountFloat); //浮点型已验票数量
//
//                        Integer validCount = validInitCount - (validRestCount + validRefundCount);  //整数型已验票数量
//
//                        jszxOrderDetail.setRefundCount(jszxOrderDetail.getRefundCount() + jszxOrderDetail.getRestCount());  //把剩余的票数加上已退款的数量
//                        jszxOrderDetail.setRestCount(0);    //剩余票数清空
//                        Float updatePrice = restPrice * (validRestCountFloat / (validRestCountFloat + validCountFloat) );   //清算剩余票数的金额
//                        jszxOrderDetail.setActualPay(jszxOrderDetail.getActualPay() - updatePrice); //结算实际支付金额
//
//
//                        productValidateCode.setRefundCount(productValidateCode.getRefundCount() + productValidateCode.getOrderCount());
//                        productValidateCode.setOrderCount(0);

                        productValidateCode.syncUsed(jszxOrderDetail.getEndTime());



                        if (productValidateCode.getUsed() == 1) {
                            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.USED);
                        } else {
                            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);

                        }

                        productValidateCodeService.update(productValidateCode);
                        jszxOrderDetailService.update(jszxOrderDetail);
//                        jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice() - updatePrice);
//                        updateTicketJszxOrder(jszxOrder, jszxOrderDetail);  //更新订单金额状态
//
//                        //更新经销商和供应商账户的金额
//                        balanceService.updateBalance(updatePrice.doubleValue(), AccountType.refund, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), jszxOrder.getUser().getId(), jszxOrder.getOrderNo(), jszxOrder.getId());

                    }
                }
            }
        }

    }


    /**
     * 判断订单下的票号类型是否都被取消
     * @param jszxOrder
     */
    public void updateTicketJszxOrder(JszxOrder jszxOrder, JszxOrderDetail jszxOrderDetail) {
        int refundSize = 0;
        if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
            refundSize = jszxOrderDetail.getRefundCount();       //被取消的数量
        }
        //判断被取消的个数是否与总票数相等
        if (refundSize == jszxOrderDetail.getCount()) {
            //相等，则订单设为取消状态
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
        }

    }


}
