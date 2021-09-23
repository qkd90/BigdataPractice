package test;

import com.data.data.hmly.action.yihaiyou.util.RSAUtil;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ctriphotel.base.XMLUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * Created by huangpeijie on 2016-11-10,0010.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class Test {
    private String ferryPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANYWlRW3wNZg0ZK3RHPMqFez+GnxmGHSbmnjflbv5XFEi3PUie3ip0u3gkBXKindxIIZKRuY1XqQKqLHlDOPyz74Z/6Pf1OW5qDtjn8ap2XnuIcpAGgvSw/sj087hAU6872fVhpcAEjVMzaBdFzSAvq3bWyvGRMvuBWJGjws/c9vAgMBAAECgYBYkcUh1ACCcFGjmhBGvA+VIQ6PffTl23H0erpk6yCIKKxswixB7zf5GjVXuvfllCrdC9223hbLeuM4rsrAPif95d/RItkhMbaxTTr7XdhL4CU+d/VkrHmMjmpd+KfvJIHNVQzR/wyY/y6uxFVInPRlyS9kmlnI63Xls8iov2cjAQJBAP2yTzK9RbGkUAb8GWxjrWKnUm06LiqOopkhLGnlTNKlsxLn9grYnV02ZyWqgTaWp9VnFUp9F8M1xvjdOBYYtoECQQDYCDUq5uV8Q/VyT23b7dbcsmv3klPfMVPmJk6tMZxjky+s/n/nwhVQsW9tk6IVnIvcExvh+d7XOUzR1nPL9u3vAkBr2OU6GKQMBGHQ1lKodSc8DQ1JSbZeeQw+NJAA5G1oDvC1VM6Phc4/1eS7amRLpHfsjEnMxKjZX38aRFCXdCIBAkBhnUvQ5+Y4AUkt7sAYSV71+FUa1+64AkUD0LlqPumIgOBhDdpjsWoKPTK5U6VAmK0Fs0i9EjfkDkss4fcO6bPXAkEArrGj1LSy1brA5MEIQT+vWZmpd17OeYylbYpFTnORhyKZc5JpnARxveX/5ljbOUTJ/76xKYuokUDorxma0Gq5FA==";
    private String ferryPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWFpUVt8DWYNGSt0RzzKhXs/hp8Zhh0m5p435W7+VxRItz1Int4qdLt4JAVyop3cSCGSkbmNV6kCqix5Qzj8s++Gf+j39Tluag7Y5/Gqdl57iHKQBoL0sP7I9PO4QFOvO9n1YaXABI1TM2gXRc0gL6t21srxkTL7gViRo8LP3PbwIDAQAB";
    String name = "yihaiyou";
    String password = "yihaiyou";
    String username = "tester";
    String pass = "888888";

    @org.junit.Test
    public void test() {
        String content = "<member>\n" +
                "  <name>tester</name>\n" +
                "  <trueName>测试</trueName>\n" +
                "  <email>ym@jfits.com</email>\n" +
                "  <mobile>05922592527</mobile>\n" +
                "  <idTypeName>二代身份证</idTypeName>\n" +
                "  <idnumber>********</idnumber>\n" +
                "  <address> </address>\n" +
                "  <registerTime>2014-10-18 10:32:44</registerTime>\n" +
                "  <state>实名认证</state>\n" +
                "</member>";
        System.out.println("content: " + content);
        String checknum = getCheckNum(content);
        System.out.println("checknum: " + checknum);
        verifyTransResponse(content, checknum);
    }

    @org.junit.Test
    public void test1() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", username);
        params.put("password", MD5.Encode(pass));
        params.put("checknum", getCheckNum(username));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/checkMember.do", params, name, password);
        System.out.println(result);
//        Map<String, Object> map = XMLUtil.readStringXmlOut(result);
//        if ("0".equals(map.get("code").toString())) {
//            String checknum = map.get("checknum").toString();
//            String content = result.substring(result.indexOf("<content>") + 9, result.indexOf("</content>"));
//            verifyTransResponse(content, checknum);
//        }
    }

    @org.junit.Test
    public void test2() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("startTime", "20161101");
        params.put("endTime", "20161111");
        params.put("page", "1");
        params.put("checknum", getCheckNum("1"));
//        String result = HttpUtils.post("http://120.24.86.172/wx/today/third/getMembers.do", params, name, password);
//        System.out.println(result);
    }

    @org.junit.Test
    public void test3() {
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> personal = Maps.newLinkedHashMap();
        personal.put("trueName", "测试");
        personal.put("idTypeName", "二代身份证");
        personal.put("idnumber", "********");
        personal.put("mobile", "05922592527");
        personal.put("bankNo", "");
        contentMap.put("person", personal);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/queryMember.do", params, name, password);
        System.out.println(result);
    }

    @org.junit.Test
    public void test1111() {
        try {
            String checkString = "<person><trueName>测试</trueName><idTypeName>二代身份证</idTypeName><idnumber>********</idnumber><mobile>05922592527</mobile><bankNo></bankNo></person>";
            String val1 = new String(Base64.encodeBase64(checkString.getBytes()), "UTF-8");
            RSAUtil sso = new RSAUtil();
            String checkNum = sso.Sign(val1, ferryPrivateKey);

            PostMethod filePost = new PostMethod("http://120.24.86.172/wx/today/third/checkPerson.do") {
                public String getResponseCharSet(){
                    return "UTF-8";
                }

                public String getRequestCharSet() {
                    return "UTF-8";
                }

            };
            filePost.addParameter("content", checkString);
            filePost.addParameter("checknum", checkNum);

            filePost.setDoAuthentication(true);
            HttpClient client = new HttpClient();
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials("yihaiyou", "yihaiyou");
            client.getState().setCredentials(AuthScope.ANY, creds);
            client.getState().setAuthenticationPreemptive(true);
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String str = new String(filePost.getResponseBody(), "utf8");
                System.out.println(str);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test4() {
        Map<String, Object> params = Maps.newLinkedHashMap();
        Map<String, Object> contentMap = Maps.newLinkedHashMap();
        Map<String, Object> personal = Maps.newLinkedHashMap();
        personal.put("trueName", "测试");
        personal.put("idTypeName", "二代身份证");
        personal.put("idnumber", "********");
        personal.put("mobile", "05922592527");
        personal.put("bankNo", "");
        contentMap.put("person", personal);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("name", username);
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/checkPerson.do", params, name, password);
        System.out.println(result);
    }

    @org.junit.Test
    public void test5() {
        Map<String, Object> params = Maps.newHashMap();
        String date = String.valueOf(System.currentTimeMillis());
        params.put("time", date);
        params.put("checknum", getCheckNum(date));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/getFlightLine.do", params, name, password);
        System.out.println(result);
        Map<String, Object> map = XMLUtil.readStringXmlOut(result);
        if ("0".equals(map.get("code").toString())) {
            String checknum = map.get("checknum").toString();
            String content = result.substring(result.indexOf("<content>") + 9, result.indexOf("</content>"));
            verifyTransResponse(content, checknum);
        }
    }

    @org.junit.Test
    public void test6() {
        Map<String, Object> params = Maps.newHashMap();
        String date = DateUtils.formatShortDate(new Date());
        String flightLineId = "2BB589AD46C04BCE88B74AEBC387F1D3";
        params.put("flightLineId", flightLineId);
        params.put("date", date);
        params.put("checknum", getCheckNum(date + ":" + flightLineId));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/getDailyFlight.do", params, name, password);
        System.out.println(result);
        Map<String, Object> map = XMLUtil.readStringXmlOut(result);
        if ("0".equals(map.get("code").toString())) {
            String checknum = map.get("checknum").toString();
            String content = result.substring(result.indexOf("<content>") + 9, result.indexOf("</content>"));
            verifyTransResponse(content, checknum);
        }
    }

    @org.junit.Test
    public void test7() {
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> contentMap = Maps.newHashMap();
        Map<String, Object> saleOrder = Maps.newHashMap();
        saleOrder.put("sno", "");
        saleOrder.put("ebusinessOrder", "");
        saleOrder.put("dailyFlightGid", "7664D6C43B2C425CBA0C6CA5026BF15E");
        saleOrder.put("flightNumber", "DS0710");
        saleOrder.put("departTime", "2016-11-16  07:10");
        saleOrder.put("seat", "330");
        saleOrder.put("amount", "35.0");
        saleOrder.put("username", username);
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> item = Maps.newHashMap();
        Map<String, Object> ticket = Maps.newHashMap();
        ticket.put("id", "");
        ticket.put("name", "");
        ticket.put("price", "");
        ticket.put("number", "");
        item.put("ticket", ticket);
        Map<String, Object> personal = Maps.newHashMap();
        personal.put("idTypeName", "");
        personal.put("name", "");
        personal.put("idnumber", "");
        personal.put("molile", "");
        item.put("personal", personal);
        list.add(item);
        saleOrder.put("list", list);
        contentMap.put("SaleOrder", saleOrder);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/saveSaleOrder.do", params, name, password);
        System.out.println(result);
    }

    @org.junit.Test
    public void test10() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", username);
        params.put("checknum", getCheckNum(username));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/getContacts.do", params, name, password);
        System.out.println(result);
    }

    @org.junit.Test
    public void test11() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", username);
        Map<String, Object> contentMap = Maps.newHashMap();
        Map<String, Object> personal = Maps.newHashMap();
        personal.put("trueName", "测试2");
        personal.put("idTypeName", "其它证件");
        personal.put("idnumber", "2222222222222222222222");
        personal.put("mobile", "11111111111");
        contentMap.put("personal", personal);
        StringBuffer content = new StringBuffer();
        mapToXMLTest2(contentMap, content);
        params.put("content", content.toString());
        params.put("checknum", getCheckNum(content.toString()));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/addContacts.do", params, name, password);
        System.out.println(result);
    }

    @org.junit.Test
    public void test12() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("startTime", "20161101");
        params.put("endTime", "20161111");
        String date = String.valueOf(System.currentTimeMillis());
        params.put("time", date);
        params.put("checknum", getCheckNum(date));
        String result = HttpUtils.postHttpBasic("http://120.24.86.172/wx/today/third/getSaleOrder.do", params, name, password);
        System.out.println(result);
        Map<String, Object> map = XMLUtil.readStringXmlOut(result);
        if ("0".equals(map.get("code").toString())) {
            String checknum = map.get("checknum").toString();
            String content = map.get("content").toString();
            Boolean flag = verifyTransResponse(content, checknum);
            System.out.println(flag);
        }
    }

    /**
     * 描述：验证
     */
    private boolean verifyTransResponse(String checkString, String checkNum) {
        if (StringUtils.isBlank(checkString)) {
            return false;
        }
        RSAUtil sso = new RSAUtil();
        boolean status = false;
        try {
            String val = new String(Base64.encodeBase64(checkString.getBytes()), "UTF-8");
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
    private String getCheckNum(String content) {
        RSAUtil sso = new RSAUtil();
        String sign = "";
        try {
            String val = new String(org.apache.commons.codec.binary.Base64.encodeBase64(content.getBytes()), "UTF-8");
            sign = sso.Sign(val, ferryPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    private void mapToXMLTest2(Map map, StringBuffer sb) {
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
}
