package com.data.data.hmly.action.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class UserTest extends BaseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private String json = "";

    /**
     * 10.1 登录
     *
     * @throws Exception
     */
    public void testLogin() throws Exception {
        request.setParameter("account", "13124072979");
        request.setParameter("password", "123456");
        ActionProxy proxy = getActionProxy("/user/login.jhtml");
        proxy.execute();
    }

    /**
     * 10.2 验证用户
     *
     * @throws Exception
     */
    public void testCheckUser() throws Exception {
        request.setParameter("account", "13124072979");
        ActionProxy proxy = getActionProxy("/user/checkUser.jhtml");
        proxy.execute();
    }

    /**
     * 10.3 注册
     *
     * @throws Exception
     */
    public void testRegister() throws Exception {
        request.setParameter("account", "13124072978");
        request.setParameter("password", "123456");
        request.setParameter("smsCode", "318165");
        ActionProxy proxy = getActionProxy("/user/register.jhtml");
        proxy.execute();
    }

    /**
     * 10.5 忘记密码
     *
     * @throws Exception
     */
    public void testForgotPassword() throws Exception {
        request.setParameter("account", "13124072979");
        ActionProxy proxy = getActionProxy("/user/forgotPassword.jhtml");
        proxy.execute();
    }

    /**
     * 10.6 修改密码
     *
     * @throws Exception
     */
    public void testUpdatePassword() throws Exception {
        request.setParameter("account", "13124072979");
        request.setParameter("password", "123456");
        request.setParameter("smsCode", "914412");
        ActionProxy proxy = getActionProxy("/user/updatePassword.jhtml");
        proxy.execute();
    }

    /**
     * 10.7 发送短信验证码
     *
     * @throws Exception
     */
    public void testSendSmsCode() throws Exception {
        request.setParameter("account", "13124072979");
        ActionProxy proxy = getActionProxy("/user/sendSmsCode.jhtml");
        proxy.execute();
    }

    /**
     * 10.8 验证短信验证码
     *
     * @throws Exception
     */
    public void testCheckSmsCode() throws Exception {
        request.setParameter("account", "13124072979");
        request.setParameter("smsCode", "650794");
        ActionProxy proxy = getActionProxy("/user/checkSmsCode.jhtml");
        proxy.execute();
    }

    /**
     * 10.9 查询个人信息
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("id", "151");
        ActionProxy proxy = getActionProxy("/user/detail.jhtml");
        proxy.execute();
    }

    /**
     * 10.10 修改个人信息
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 151);
        map.put("head", "");
        map.put("nickName", "新用户");
        map.put("gender", "male");
        map.put("telephone", "13124072979");
        map.put("email", "641231092@qq.com");
        json = mapper.writeValueAsString(map);
        request.setParameter("json", json);
        ActionProxy proxy = getActionProxy("/user/update.jhtml");
        proxy.execute();
    }

    /**
     * 10.11 意见反馈
     *
     * @throws Exception
     */
    public void testFeedback() throws Exception {
        request.setParameter("id", "151");
        request.setParameter("content", "2222222222");
        ActionProxy proxy = getActionProxy("/user/feedback.jhtml");
        proxy.execute();
    }

    @Test
    public void test() throws Exception {
       String regex = "([0-9.]+)([\u4E00-\u9FA5]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher("35.9吨");
        if (m.find()) {
            System.out.println(m.group(1));
        }
    }
}
