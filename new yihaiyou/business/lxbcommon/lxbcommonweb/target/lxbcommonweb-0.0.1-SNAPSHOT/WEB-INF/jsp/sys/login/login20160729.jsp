<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>后台管理-登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="../../common/common141.jsp"%>
<link href="/css/sys/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/sys/login.js"></script>
</HEAD>
<BODY class="body"  onkeydown ="tokeydown()" onLoad="moveTo(0,0);resizeTo(screen.width,screen.height);"  style="background:url(/images/loginTest.png) repeat-x #0697cc">
	 <div class="loginb">
        <div class="loginBox" id="dianzhang">
            <!-- <div class="loginTop">
                <ul>
                    <li class="leftItem"><a href="javascript:void(0)" class="select"></a></li>
                </ul>
            </div> -->
            <div class="loginCon">
            	<div style="display: none;">
            		<form name="WfStaffAction" id="WfStaffAction" method="post" >
            			<input name="account" id="account" />
            			<input name="password" id="password" />
            			<input name="userAccount" id="userAccount" />
            			<input name="userType" id="userType" />
            			<input name="validateVlue" id="validateVlue" />
            		</form>
            	</div>
            	<s:if test='json!=null&&json!=""'>
                	<div class="errorBox" id="validatetd_ADMIN" style="margin: 5px;margin-bottom: -25px;"><span><s:property value="json"/></span><i class="ui-ico-error01"></i></div>
            	</s:if>
            	<s:else>
            		<div class="errorBox fn-hide" id="validatetd_ADMIN" style="margin: 5px;margin-bottom: -25px;"><span></span><i class="ui-ico-error01"></i></div>
            	</s:else>
                <div class="g-mt30 inputItem inputItem_ADMIN" >
                	<input type="text" placeholder="用户名" name="account"  id="account_ADMIN" class="ui-txt02" />
                	<i class="ui-ico-customer"></i>
                </div>
                <div class="g-mt25 inputItem">
                	<input type="password" placeholder="密码" name="password" id="password_ADMIN" class="ui-txt02"
                		onpaste="return false;" oncopy="return false;"/>
                	<i class="ui-ico-password"></i>
                </div>
                <div class="g-mt25 inputItem">
                    <input name="validateVlue" id="validateVlue_ADMIN" placeholder="验证码" value="" type="text"  class="reginput ui-txt03"  size="14" maxlength="4" reg="(\S)" tip="请输入验证码" />
					<img src="/images/checkNum.jsp" id="imgyz_ADMIN" onclick="imgClick('imgyz_ADMIN');" style="position:absolute;left:130px;top:1px;height:21px;"/>
                </div>
                <div class="g-mt30 inputItem"><button class="ui-btn-logins" onclick="vsubform('ADMIN')">登录</button></div>
                <div class="g-mt20 inputItem g-ac g-pb20"><a href="http://www.lvxbang.com" style="color:green;" target="_blank" class="g-c-51a">技术支持：旅行帮</a></div>
            </div>
        </div>
    </div>
</BODY>
</HTML>
