package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.entity.BankInfo;
import com.data.data.hmly.service.pay.entity.CmbPayLog;
import com.data.data.hmly.service.pay.entity.XmlPacket;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huangpeijie on 2017-02-24,0024.
 */
@Service
public class CmbPayService {
    @Resource
    private BalanceService balanceService;
    @Resource
    private UserService userService;
    @Resource
    private CmbPayLogService cmbPayLogService;
    @Resource
    private MemberService memberService;

    private static String CMB_LOGIN_NAME;
    private static String CMB_BANK_NO;
    private static String CMB_BANK_AREA_CODE;
    private static String CMB_BANK_AREA_NAME;
    private static String CMB_SERVICE_URL;

    static {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        CMB_LOGIN_NAME = propertiesManager.getString("CMB_LOGIN_NAME");
        CMB_BANK_NO = propertiesManager.getString("CMB_BANK_NO");
        CMB_BANK_AREA_CODE = propertiesManager.getString("CMB_BANK_AREA_CODE");
        CMB_BANK_AREA_NAME = propertiesManager.getString("CMB_BANK_AREA_NAME");
        CMB_SERVICE_URL = propertiesManager.getString("CMB_SERVICE_URL");
    }

    /**
     * 生成请求报文
     *
     * @return
     */
    private String getRequestStr(BankInfo bankInfo, Order order) {
        // 构造支付的请求报文
        XmlPacket xmlPkt = new XmlPacket("Payment", CMB_LOGIN_NAME);
        Map mpPodInfo = new Properties();
        mpPodInfo.put("BUSCOD", "N02031");                      //*业务类别
        mpPodInfo.put("BUSMOD", "00001");                       //*业务模式编号
        xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
        Map mpPayInfo = new Properties();
        mpPayInfo.put("YURREF", order.getOrderNo());             //*业务参考号
//        mpPayInfo.put("EPTDAT", "20070605");                    //期望日
//        mpPayInfo.put("EPTTIM", "");                            //期望时间
        mpPayInfo.put("DBTACC", CMB_BANK_NO);               //*付方帐号
        mpPayInfo.put("DBTBBK", CMB_BANK_AREA_CODE);                          //*付方开户地区代码
        mpPayInfo.put("C_DBTBBK", CMB_BANK_AREA_NAME);                       //*付方开户地区 付方开户地区和付方开户地区代码不能同时为空
        mpPayInfo.put("TRSAMT", order.getPrice().toString());                        //*交易金额
        mpPayInfo.put("CCYNBR", "10");                          //*币种代码
        mpPayInfo.put("C_CCYNBR", "人民币");                     //*币种名称 币种代码和币种名称不能同时为 币种暂时只支持10-人民币
        mpPayInfo.put("STLCHN", "N");                           //*结算方式代码
        mpPayInfo.put("C_STLCHN", "普通");                       //*结算方式 N：普通 F：快速 只对跨行交易有效；同行都是实时到账。
        mpPayInfo.put("NUSAGE", order.getName());                         //*用途
//        mpPayInfo.put("BUSNAR", "测试");                         //业务摘要 用于企业付款时填写说明或者备注。
        mpPayInfo.put("CRTACC", bankInfo.getBankNo());               //*收方帐号
        mpPayInfo.put("CRTNAM", bankInfo.getHolderName());                  //*收方帐户名
//        mpPayInfo.put("BRDNBR", "68");                          //收方行号
//        mpPayInfo.put("BNKFLG", "Y");                           //*系统内外标志 Y：招行；N：非招行；
        mpPayInfo.put("CRTBNK", bankInfo.getBankName());                     //*收方开户行 BNKFLG为空时，将依据收方开户行CRTBNK进行判断。
//        mpPayInfo.put("CTYCOD", "123");                         //城市代码 行内支付填写，如果为空跟据收方省份和收方城市自动获取代码
        mpPayInfo.put("CRTPVC", bankInfo.getProvince());                       //*收方省份
        mpPayInfo.put("CRTCTY", bankInfo.getCity());                       //*收方城市
//        mpPayInfo.put("NTFCH1", "135451@111.com");              //收方电子邮件 用于交易成功后邮件通知。
//        mpPayInfo.put("NTFCH2", "13124072979");                  //收方移动电话 用于交易成功后短信通知。
//        mpPayInfo.put("CRTSQN", "APP060928001255");             //收方编号 用于标识收款方的编号。非受限收方模式下可重复。
//        mpPayInfo.put("TRSTYP", "100001");                      //业务种类
        xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);
        return xmlPkt.toXmlString();
    }

    /**
     * 连接前置机，发送请求报文，获得返回报文
     *
     * @param data
     * @return
     * @throws MalformedURLException
     */
    private String sendRequest(String data) {
        String result = "";
        try {
            URL url;
            url = new URL(CMB_SERVICE_URL);

            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os;
            os = conn.getOutputStream();
            os.write(data.toString().getBytes("gbk"));
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), "gbk"));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println(result);
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理返回的结果
     *
     * @param result
     */
    private Map<String, Object> processResult(String result) {
        Map<String, Object> resultMap = Maps.newHashMap();
        if (StringUtils.isBlank(result)) {
            resultMap.put("success", false);
            resultMap.put("message", "请求失败");
            return resultMap;
        }
        XmlPacket pktRsp = XmlPacket.valueOf(result);
        if (pktRsp == null) {
            resultMap.put("success", false);
            resultMap.put("message", "响应报文解析失败");
            return resultMap;
        }
        String sRetCod = pktRsp.getRETCOD();
        switch (sRetCod) {
            case "0":
                Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
                String sREQSTS = (String) propPayResult.get("REQSTS");
                String sRTNFLG = (String) propPayResult.get("RTNFLG");
                if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
                    resultMap.put("success", false);
                    resultMap.put("message", "支付失败：" + propPayResult.get("ERRTXT"));
                } else {
                    resultMap.put("success", true);
                    resultMap.put("message", "支付已被银行受理（支付状态：" + sREQSTS + "）");

                }
                break;
            case "-9":
                resultMap.put("success", false);
                resultMap.put("message", "支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
                break;
            default:
                resultMap.put("success", false);
                resultMap.put("message", "支付失败：" + pktRsp.getERRMSG());
                break;
        }
        return resultMap;
    }

    /**
     * 提现
     * @param bankInfo
     * @param order
     * @return Boolean success 提现是否成功；String message 结果信息
     */
    public Map<String, Object> doWithdrawRequest(BankInfo bankInfo, Order order) {
        // 生成请求报文
        String data = getRequestStr(bankInfo, order);

        // 连接前置机，发送请求报文，获得返回报文
        String result = sendRequest(data);

        // 处理返回的结果
        Map<String, Object> resultMap = processResult(result);
        if ((Boolean) resultMap.get("success")) {
            order.setStatus(OrderStatus.SUCCESS);
        } else {
            order.setStatus(OrderStatus.FAILED);
        }
        balanceService.savePayResult(order,AccountType.withdraw);
        saveWithdrawResult(bankInfo, order, resultMap);
        return resultMap;
    }

    private void saveWithdrawResult(BankInfo bankInfo, Order order, Map<String, Object> resultMap) {
        CmbPayLog cmbPayLog = new CmbPayLog();
        User user = userService.get(order.getUser().getId());
        cmbPayLog.setOrderNo(order.getOrderNo());
        cmbPayLog.setUser(user);
        cmbPayLog.setPrice(order.getPrice());
        cmbPayLog.setBalance(user.getBalance());
        cmbPayLog.setBankNo(bankInfo.getBankNo());
        cmbPayLog.setHolderName(bankInfo.getHolderName());
        cmbPayLog.setBankName(bankInfo.getBankName());
        cmbPayLog.setProvince(bankInfo.getProvince());
        cmbPayLog.setCity(bankInfo.getCity());
        cmbPayLog.setSuccess((Boolean) resultMap.get("success"));
        cmbPayLog.setMessage((String) resultMap.get("message"));
        cmbPayLog.setCreateTime(new Date());
        cmbPayLogService.save(cmbPayLog);
    }
}
