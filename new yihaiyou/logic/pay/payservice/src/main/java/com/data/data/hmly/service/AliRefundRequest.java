package com.data.data.hmly.service;

import com.data.data.hmly.service.pay.entity.AliRefundGatheringEndity;
import com.data.data.hmly.service.pay.util.UtilDate;

import java.util.List;

/**
 * Created by dy on 2016/5/23.
 */
public class AliRefundRequest {


    //付款当天日期
    private String refundDate;              //必填，格式：年[4位]月[2位]日[2位]，如：20100801

    //批次号
    private String batchNo;                 //必填，退款批次号(batch_no)，必填(时间格式是yyyyMMddHHmmss+数字或者字母)

    //付款笔数
    private Integer batchNum;                //注意：退款笔数(batch_num)，必填(值为您退款的笔数,取值1~1000间的整数)

    //付款详细数据
    private String detailData;              //必填，退款详细数据(WIDdetail_data)，必填(支付宝交易号^退款金额^备注)多笔请用#隔开


    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public String getDetailData() {
        return detailData;
    }

    public void setDetailData(String detailData) {
        this.detailData = detailData;
    }

    public String doFmtGatherings(List<AliRefundGatheringEndity> gatheringEndities) {

        StringBuffer sb = new StringBuffer();
        if (gatheringEndities.isEmpty()) {
            return null;
        }
//        交易退款数据集的格式为：原付款支付宝交易号^退款总金额^退款理由；
//        多条退款记录格式：原付款支付宝交易号^退款总金额^退款理由#.......#原付款支付宝交易号^退款总金额^退款理由
        for (int i = 0; i < gatheringEndities.size(); i++) {
            AliRefundGatheringEndity endity = gatheringEndities.get(i);
            sb.append(endity.getTradeNo());
            sb.append("^");
            sb.append(endity.getRefundFee());
            sb.append("^");
            sb.append(endity.getRefundDesc());
            if (i < (gatheringEndities.size() - 1)) {
                sb.append("#");
            }
        }
        return sb.toString();
    }
}
