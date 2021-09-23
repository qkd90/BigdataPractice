package com.data.data.hmly.service.order.util;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ctriphotel.base.XMLUtil;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangpeijie on 2016-12-20,0020.
 */
public class FerryUtil {
    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private static String ferryUrl;
    private static String ferryPrivateKey;
    private static String ferryPublicKey;
    public static final String ferryUsername = "yihaiyou";
    public static final String ferryPassword = "yihaiyou";

    static {
        ferryUrl = propertiesManager.getString("ferryUrl");
        String ferryPrivateKeyPath = propertiesManager.getString("ferryPrivateKeyPath");
        String ferryPublicKeyPath = propertiesManager.getString("ferryPublicKeyPath");
        try {
            ferryPrivateKey = FileUtils.readFileToString(new File(ferryPrivateKeyPath), "utf-8");
            ferryPublicKey = FileUtils.readFileToString(new File(ferryPublicKeyPath), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户列表
     *
     * @param start
     * @param end
     * @param page
     * @return
     */
    public static Map<String, Object> getMembers(Date start, Date end, Integer page) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("startTime", DateUtils.format(start, "yyyyMMdd"));
        params.put("endTime", DateUtils.format(end, "yyyyMMdd"));
        params.put("page", page.toString());
        params.put("checknum", getCheckNum(page.toString()));
        return post(FerryUrl.GET_MEMBERS, params);
    }

    /**
     * 航线查询
     *
     * @return
     */
    public static Map<String, Object> getFlightLine() {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        String date = String.valueOf(System.currentTimeMillis());
        params.put("time", date);
        params.put("checknum", getCheckNum(date));
        Map<String, Object> resultMap = post(FerryUrl.GET_FLIGHT_LINE, params);
        if (!"0".equals(resultMap.get("code").toString())) {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
            return result;
        }
        result.put("success", true);
        result.put("flightLine", ((Map) ((Map) resultMap.get("content")).get("list")).get("flightLine"));
        return result;
    }

    /**
     * 航班查询
     *
     * @param date
     * @param flightLineId
     * @return
     */
    public static Map<String, Object> getDailyFlight(Date date, String flightLineId) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("flightLineId", flightLineId);
        String dateStr = DateUtils.formatShortDate(date);
        map.put("date", dateStr);
        map.put("checknum", getCheckNum(dateStr + ":" + flightLineId));
        return post(FerryUrl.GET_DAILY_FLIGHT, map);
    }

    /**
     * 实名查询
     *
     * @param ferryMember
     * @return
     */
    public static Map<String, Object> queryReal(FerryMember ferryMember) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> person = Maps.newLinkedHashMap();
        person.put("trueName", ferryMember.getTrueName());
        person.put("idTypeName", ferryMember.getIdTypeName().getDescription());
        person.put("idnumber", ferryMember.getIdnumber());
        person.put("mobile", ferryMember.getMobile());
        person.put("bankNo", ferryMember.getBankNo());
        contentMap.put("person", person);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        Map<String, Object> resultMap = post(FerryUrl.QUERY_MEMBER, params);
        if ("0".equals(resultMap.get("code"))) {
            Map<String, Object> member = (Map<String, Object>) ((Map) resultMap.get("content")).get("member");
            if (ferryMember.getTrueName().equals(member.get("trueName"))) {
                result.put("success", true);
                result.put("hasMember", true);
                result.put("isReal", "实名认证".equals(member.get("state")));
                result.put("name", member.get("name"));
            } else {
                result.put("success", false);
                result.put("errMsg", "姓名和身份证不匹配");
            }
        } else if ("-1".equals(resultMap.get("code"))) {
            result.put("success", true);
            result.put("hasMember", false);
            result.put("isReal", false);
        } else {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
        }
        return result;
    }

    /**
     * 登录
     *
     * @param ferryMember
     * @return
     */
    public static Map<String, Object> ferryLogin(FerryMember ferryMember) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", ferryMember.getName());
        params.put("password", ferryMember.getPassword());
        params.put("checknum", getCheckNum(ferryMember.getName()));
        Map<String, Object> resultMap = post(FerryUrl.CHECK_MEMBER, params);
        if ("0".equals(resultMap.get("code"))) {
            result.put("success", true);
            result.put("member", ((Map) resultMap.get("content")).get("member"));
        } else {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
        }
        return result;
    }

    public static Map<String, Object> doRealname(FerryMember ferryMember) {
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> person = Maps.newLinkedHashMap();
        person.put("trueName", ferryMember.getTrueName());
        person.put("idTypeName", ferryMember.getIdTypeName().getDescription());
        person.put("idnumber", ferryMember.getIdnumber());
        person.put("mobile", ferryMember.getMobile());
        person.put("bankNo", ferryMember.getBankNo());
        contentMap.put("person", person);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("name", ferryMember.getName());
        params.put("content", content.toString());
//        params.put("url", url);
//        params.put("type", type);
        params.put("checknum", getCheckNum(content.toString()));
        return post(FerryUrl.CHECK_PERSON, params);
    }

    /**
     * 实名认证
     *
     * @param ferryMember //     * @param url 回调地址
     *                    //     * @param type 页面类型（mobile:手机版，pc:pc版）
     * @return
     */
    public static Map<String, Object> doRealname(FerryMember ferryMember, String url, String type) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> person = Maps.newLinkedHashMap();
        person.put("trueName", ferryMember.getTrueName());
        person.put("idTypeName", ferryMember.getIdTypeName().getDescription());
        person.put("idnumber", ferryMember.getIdnumber());
        person.put("mobile", ferryMember.getMobile());
        contentMap.put("person", person);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
//        params.put("name", ferryMember.getName());
        params.put("content", content.toString());
        params.put("url", url);
        params.put("type", type);
        params.put("checknum", getCheckNum(content.toString()));
        String resultStr = HttpUtils.postHttpBasic(ferryUrl + FerryUrl.CHECK_PERSON_HTML, params, ferryUsername, ferryPassword);
        String code = resultStr.substring(resultStr.indexOf("<code>") + "<code>".length(), resultStr.indexOf("</code>"));
        if ("0".equals(code)) {
            result.put("success", true);
            result.put("url", resultStr.substring(resultStr.indexOf("<content>") + "<content>".length(), resultStr.indexOf("</content>")));
        } else {
            result.put("success", false);
            result.put("errMsg", resultStr.substring(resultStr.indexOf("<message>") + "<message>".length(), resultStr.indexOf("</message>")));
        }
        return result;
    }

    public static Map<String, Object> realnameFerry(FerryMember ferryMember) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> person = Maps.newLinkedHashMap();
        person.put("trueName", ferryMember.getTrueName());
        person.put("idTypeName", ferryMember.getIdTypeName().getDescription());
        person.put("idnumber", ferryMember.getIdnumber());
        person.put("mobile", ferryMember.getMobile());
        contentMap.put("person", person);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("name", ferryMember.getName());
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        Map<String, Object> resultMap = post(FerryUrl.CHECK_PERSON, params);
        if ("0".equals(resultMap.get("code"))) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
        }
        return result;
    }

    /**
     * 账号检查
     *
     * @param name
     * @return
     */
    public static Map<String, Object> checkFerryMember(String name) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", name);
        params.put("checknum", getCheckNum(name));
        return post(FerryUrl.CHECK_MEMBER, params);
    }

//    /**
//     * 注册
//     *
//     * @param ferryMember
//     * @return
//     */
//    public static Map<String, Object> ferryRegister(FerryMember ferryMember) {
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("name", ferryMember.getName());
//        params.put("password", ferryMember.getPassword());
//        params.put("checknum", getCheckNum(ferryMember.getName() + ":" + ferryMember.getPassword()));
//        return post(FerryUrl.SAVE_MEMBER, params);
//    }

    /**
     * 注册
     *
     * @param ferryMember
     * @return
     */
    public static Map<String, Object> ferryRegister(FerryMember ferryMember) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> member = Maps.newLinkedHashMap();
        member.put("name", ferryMember.getName());
        member.put("trueName", ferryMember.getTrueName());
        member.put("idTypeName", ferryMember.getIdTypeName().getDescription());
        member.put("idnumber", ferryMember.getIdnumber());
        member.put("mobile", ferryMember.getMobile());
        member.put("email", ferryMember.getEmail());
        member.put("state", "实名认证");
        contentMap.put("member", member);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("password", ferryMember.getPassword());
        params.put("checknum", getCheckNum(content.toString()));
        Map<String, Object> resultMap = post(FerryUrl.SAVE_MEMBER_ALL, params);
        if ("0".equals(resultMap.get("code"))) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
        }
        return result;
    }

    /**
     * 添加旅客
     *
     * @param order
     * @return
     */
    public static Map<String, Object> addContacts(FerryOrder order) {
        Map<String, Object> result = Maps.newHashMap();
        for (FerryOrderItem orderItem : order.getFerryOrderItemList()) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("name", order.getUser().getFerryMember().getName());
            Map<String, Object> contentMap = Maps.newHashMap();
            Map<String, Object> person = Maps.newHashMap();
            person.put("trueName", orderItem.getName());
            person.put("idTypeName", orderItem.getIdTypeName().getDescription());
            person.put("idnumber", orderItem.getIdnumber());
            person.put("mobile", orderItem.getMobile());
            contentMap.put("person", person);
            StringBuffer content = new StringBuffer();
            mapToXMLTest2(contentMap, content);
            params.put("content", content.toString());
            params.put("checknum", getCheckNum(content.toString()));
            Map<String, Object> resultMap = post(FerryUrl.ADD_CONTACTS, params);
            if (!"0".equals(resultMap.get("code").toString())) {
                result.put("success", false);
                result.put("errMsg", resultMap.get("message"));
                return result;
            }
        }
        result.put("success", true);
        return result;
    }

    /**
     * 支付结果通知
     *
     * @param orderNo 轮渡订单号
     * @param isPayed 是否支付成功
     */
    public static Map<String, Object> payNotify(String orderNo, Boolean isPayed) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        String state;
        if (isPayed) {
            state = "1";
        } else {
            state = "0";
        }
        params.put("sno", orderNo);
        params.put("state", state);
        params.put("checknum", getCheckNum(orderNo + ":" + state));
        Map<String, Object> resultMap = post(FerryUrl.UPDATE_SALE_ORDER, params);
        if (!"0".equals(resultMap.get("code").toString())) {
            result.put("success", false);
            result.put("errMsg", resultMap.get("message"));
        } else {
            result.put("success", true);
        }
        return result;
    }

    /**
     * 退票申请
     *
     * @param sno 轮渡订单号
     * @return
     */
    public static Map<String, Object> returnSaleOrder(String sno) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("sno", sno);
        params.put("checknum", getCheckNum(sno));
        Map<String, Object> resultMap = post(FerryUrl.RETURN_SALE_ORDER, params);
        if (!"0".equals(resultMap.get("code").toString())) {
            result.put("success", false);
            result.put("errorMsg", resultMap.get("message"));
            return result;
        }
        Map<String, Object> order = (Map<String, Object>) ((Map) resultMap.get("content")).get("saleOrder");
        result.put("success", true);
        result.put("returnAmount", Float.valueOf(order.get("returnAmount").toString()));
        result.put("poundageAmount", Float.valueOf(order.get("poundageAmount").toString()));
        result.put("poundageDescribe", order.get("poundageDescribe").toString());
        return result;
    }

    /**
     * 轮渡用户游客查询
     *
     * @param name 轮渡用户名
     * @return
     */
    public static Map<String, Object> getContacts(String name) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", name);
        params.put("checknum", getCheckNum(name));
        Map<String, Object> resultMap = post(FerryUrl.GET_CONTACTS, params);
        if (!"0".equals(resultMap.get("code"))) {
            result.put("success", false);
            result.put("errorMsg", resultMap.get("message"));
            return result;
        }
        result.put("success", true);
        result.put("list", castList(((Map) resultMap.get("content")).get("list")));
        return result;
    }

    /**
     * 订单查询
     * 订单号不为空时，通过订单号精确查询
     *
     * @param start
     * @param end
     * @param sno   轮渡订单号
     * @return
     */
    public static Map<String, Object> getSaleOrder(Date start, Date end, String sno) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("startTime", DateUtils.format(start, "yyyyMMdd"));
        params.put("endTime", DateUtils.format(end, "yyyyMMdd"));
        params.put("sno", StringUtils.isBlank(sno) ? "" : sno);
        String date = String.valueOf(System.currentTimeMillis());
        params.put("time", date);
        params.put("checknum", getCheckNum(date));
        Map<String, Object> resultMap = post(FerryUrl.GET_SALE_ORDER, params);
        if (!"0".equals(resultMap.get("code").toString())) {
            result.put("success", false);
            result.put("errorMsg", resultMap.get("message"));
            return result;
        }
        result.put("success", true);
        if (((Map) resultMap.get("content")).get("list") != null) {
            result.put("list", castList(((Map) resultMap.get("content")).get("list")));
        } else {
            result.put("list", Lists.newArrayList(((Map) resultMap.get("content")).get("SaleOrder")));
        }
        return result;
    }

    /**
     * 对账统计信息
     *
     * @param start
     * @param end
     * @return
     */
    public static Map<String, Object> getOrderCollect(Date start, Date end) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("startTime", DateUtils.format(start, "yyyyMMdd"));
        params.put("endTime", DateUtils.format(end, "yyyyMMdd"));
        String date = String.valueOf(System.currentTimeMillis());
        params.put("time", date);
        params.put("checknum", getCheckNum(date));
        Map<String, Object> resultMap = post(FerryUrl.GET_ORDER_COLLECT, params);
        if (!"0".equals(resultMap.get("code").toString())) {
            result.put("success", false);
            result.put("errorMsg", resultMap.get("message"));
            return result;
        }
        result.put("success", true);
        result.putAll((Map) ((Map) ((Map) resultMap.get("content")).get("list")).get("collect"));
        return result;
    }

    public static Map<String, Object> post(String url, Map<String, Object> params) {
        String resultStr = HttpUtils.postHttpBasic(ferryUrl + url, params, ferryUsername, ferryPassword);
        return XMLUtil.readStringXmlOut(resultStr);
    }

    /**
     * 描述：验证
     */
    public static boolean verifyTransResponse(String checkString, String checkNum) {
        if (StringUtils.isBlank(checkString)) {
            return false;
        }
        RSAUtil sso = new RSAUtil();
        boolean status = false;
        try {
            String val = new String(Base64.encodeBase64(checkString.getBytes("utf-8")), "UTF-8");
            status = sso.Verify(checkNum, val, ferryPublicKey);
            System.out.println("验证status=" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 描述：签名
     */
    public static String getCheckNum(String content) {
        RSAUtil sso = new RSAUtil();
        String sign = "";
        try {
            String val = new String(Base64.encodeBase64(content.getBytes("utf-8")), "UTF-8");
            sign = sso.Sign(val, ferryPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static void mapToXMLTest2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXMLTest2(hm, sb);
                }
                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXMLTest2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + ">" + value + "</" + key + ">");
                }

            }

        }
    }

    public static List castList(Object object) {
        List flightList = Lists.newArrayList();
        if (object instanceof String) {
            return flightList;
        }
        Object list = ((Map) object).values().toArray()[0];
        if (list instanceof List) {
            flightList.addAll((List) list);
        } else if (list instanceof Map) {
            flightList.add(list);
        }
        return flightList;
    }
}
