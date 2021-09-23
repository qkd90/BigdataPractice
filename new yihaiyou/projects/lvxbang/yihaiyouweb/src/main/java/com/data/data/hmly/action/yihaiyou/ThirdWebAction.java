package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.pay.util.Base64;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by huangpeijie on 2016-12-28,0028.
 */
public class ThirdWebAction extends BaseAction {
    public static final String ferryUsername = "yihaiyou";
    public static final String ferryPassword = "yihaiyou";

    @NotNeedLogin
    public Result checkMember() {
        Document document = DocumentHelper.createDocument();
        Element result = document.addElement("result");
        Element code = result.addElement("code");
        Element message = result.addElement("message");
        Element checknumEle = result.addElement("checknum");
        Element contentEle = result.addElement("content");

        String authorization = getRequest().getHeader("authorization");
        if (StringUtils.isBlank(authorization) || authorization.split(" ").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String userAndPass = new String(Base64.decode(authorization.split(" ")[1]));
        if (StringUtils.isBlank(userAndPass) || userAndPass.split(":").length < 2) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }
        String username = userAndPass.split(":")[0];
        String password = userAndPass.split(":")[1];
        if (!ferryUsername.equals(username) || !ferryPassword.equals(password)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }

        if (getParameter("name") == null) {
            code.setText("-2");
            message.setText("参数错误");
            return text(document.asXML());
        }
        String name = getParameter("name").toString();
        String pass = null;
        if (getParameter("password") != null) {
            pass = getParameter("password").toString();
        }
        String checknum = getParameter("checknum").toString();
        checknumEle.setText("null");
        contentEle.setText("");
        if (!FerryUtil.verifyTransResponse(name, checknum)) {
            code.setText("-1");
            message.setText("验证信息错误");
            return text(document.asXML());
        }

        Member member = memberService.findByAccount(name);
        if (StringUtils.isBlank(pass)) {
            if (member == null) {
                code.setText("0");
                message.setText("用户不存在");
                return text(document.asXML());
            } else {
                code.setText("-3");
                message.setText("用户已存在");
                return text(document.asXML());
            }
        }
        if (member == null) {
            code.setText("-4");
            message.setText("用户不存在");
            return text(document.asXML());
        }
        pass = StringUtils.upperCase(pass);
        if (!pass.equals(member.getPassword())) {
            code.setText("-5");
            message.setText("密码错误");
            return text(document.asXML());
        }

        Element nameEle = contentEle.addElement("name");
        Element trueName = contentEle.addElement("trueName");
        Element email = contentEle.addElement("email");
        Element mobile = contentEle.addElement("mobile");
        Element idTypeName = contentEle.addElement("idTypeName");
        Element idnumber = contentEle.addElement("idnumber");
        Element registerTime = contentEle.addElement("registerTime");

        nameEle.setText(member.getAccount());
        trueName.setText(member.getNickName());
        email.setText(member.getEmail());
        mobile.setText(member.getTelephone());
        idTypeName.setText("身份证");
        idnumber.setText(member.getIdNumber());
        registerTime.setText(DateUtils.formatDate(member.getCreatedTime()));

        checknumEle.setText(FerryUtil.getCheckNum(contentEle.asXML()));
        code.setText("0");
        message.setText("登录成功");
        return text(document.asXML());
    }
}
