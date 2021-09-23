package com.taobao;

import com.taobao.core.AlipayNotify;
import com.taobao.core.AlipaySubmit;
import com.taobao.util.UtilDate;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dy on 2016/5/23.
 */
public class AliPayService {

    static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    /**
     * @param batchNum      注意：退款笔数(batch_num)，必填(值为您退款的笔数,取值1~1000间的整数)
     * @param detailData    必填，退款详细数据(WIDdetail_data)，必填(支付宝交易号^退款金额^备注)多笔请用#隔开
     * @return
     */
    public static String doAliRefund(String batchNum, String detailData) {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", propertiesManager.getString("PARTNER"));
        sParaTemp.put("_input_charset", propertiesManager.getString("INPUT_CHARSET"));
        sParaTemp.put("notify_url", propertiesManager.getString("REFUND_NOTIFY_URL"));
        sParaTemp.put("seller_user_id", propertiesManager.getString("PARTNER"));
        sParaTemp.put("refund_date", UtilDate.getDateFormatter());
        sParaTemp.put("batch_no", UtilDate.getOrderNum());
        sParaTemp.put("batch_num", batchNum);
        sParaTemp.put("detail_data", detailData);
        AlipaySubmit alipaySubmit = new AlipaySubmit();
        //建立请求
        String sHtmlText = alipaySubmit.buildRequest(sParaTemp, "post", "确认");
        return sHtmlText;
    }


    public static Map<String, String> doRefundBackParam(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> returnMap = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //批次号
        try {
            returnMap.put("batchNo", new String(request.getParameter("batch_no").getBytes("ISO-8859-1"), "UTF-8"));
            returnMap.put("successNum", new String(request.getParameter("success_num").getBytes("ISO-8859-1"), "UTF-8"));
            returnMap.put("resultDetails", new String(request.getParameter("result_details").getBytes("ISO-8859-1"), "UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        AlipayNotify notify = new AlipayNotify();
        if (notify.verify(params)) { //验证成功
            returnMap.put("refundFlag", "success"); //请不要修改或删除
        } else { //验证失败
            returnMap.put("refundFlag", "fail");
            returnMap.put("retrunMsg", "验证失败！");
        }

        return returnMap;
    }


}
