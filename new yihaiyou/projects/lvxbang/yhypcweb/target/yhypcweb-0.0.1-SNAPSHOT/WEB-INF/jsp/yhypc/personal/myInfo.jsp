<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/lib/webuploader/webuploader.css">
    <link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" type="text/css" href="/css/personal/personal_Personalmessage.css">
	<title>个人信息-一海游</title>
</head>
<body data-page-obj="MyInfo" data-page-class="pc-myinfo" class="judgebottom">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
				<h3>我的头像</h3>
				<div class="setface">
					<p class="p_top"><span class="tx">头像</span><span class="picBtn" id="avatarUpBtn">选择图片</span>仅支持jpg，gif，png格式图片，且文件小于2M</p>
					<div class="facebox"></div>
					<p class="p_bottom"><span class="saveface" id="savefaceBtn">保存</span></p>
                    <p id="up_msg" class="up-msg"></p>
				</div>
				<div class="usermessage">
                    <form id="myinfo_form" enctype="multipart/form-data" action="">
					<ul>
						<li>
							<span class="user_l"><label>*</label>昵称：</span>
                            <input type="text" name="member.nickName" value="<c:out escapeXml='true' value='${member.nickName}'/>">

						</li>
						<c:if test="${LOGIN_USER.thirdPartyUserType != 'weixin'}">
						<li>
							<span class="user_l">密码：</span>
							<div class="password">******<span id="revise">修改 >></span></div>
						</li>
                        </c:if>
						<li>
							<span class="user_l">性别：</span>
							<div class="sex"><span class="male" data-gender-value="male">男</span><span class="female" data-gender-value="female">女</span></div>
                            <input type="hidden" name="member.gender" value="<c:out escapeXml='true' value='${member.gender}'/>">
						</li>
						<li>
							<span class="user_l">邮箱：</span>
							<input type="text" name="member.email" value="<c:out escapeXml='true' value='${member.email}'/>">
						</li>
						<li>
							<span class="user_l"><label>*</label>手机号码：</span>
							<input type="text" name="member.telephone" value="<c:out escapeXml='true' value='${member.telephone}'/>">
						</li>
						<li>
							<span class="user_l">真实姓名：</span>
                            <input type="text" name="member.userName" value="<c:out escapeXml='true' value='${member.userName}'/>">
						</li>
                        <li>
                            <span class="user_l">证件号码：</span>
                            <input type="text" name="member.idNumber" value="<c:out escapeXml='true' value='${member.idNumber}'/>">
                        </li>
						<li>
							<span class="user_l">银行卡号</span>
                            <input type="text" name="member.bankNo" value="<c:out escapeXml='true' value='${member.bankNo}'/>">
						</li>
						<%--<li>--%>
							<%--<span class="user_l dian">·</span>--%>
							<%--<div class="button"><span class="realname">去实名认证</span></div>--%>
						<%--</li>--%>
						<li>
							<span class="user_l dian">·</span>
							<div class="button"><span class="save" id="saveMyInfoBtn">保存</span></div>
                            <p id="save_msg" class="save-msg"></p>
						</li>
					</ul>
                    </form>
				</div>
			</div>
			<div class="shadow"></div>
			<div class="setpswordBox">
				<div class="with_header"><span class="closebtn" id="psword_close"></span></div>
				<div class="with_body">
                    <form id="change_pwd_form" enctype="multipart/form-data" action="">
					<span class="title">修改密码</span>
					<input class="pri_count" type="password" id="changeOldPwd"  placeholder="请输入旧密码">
					<input type="hidden" name="oldPwd">
					<span class="attention"></span>
					<input class="pri_count" type="password" id="changeNewPwd" placeholder="请输入新密码">
					<input type="hidden" name="newPwd">
                    <span class="attention"></span>
                    <input class="pri_count" type="password" id="changeCfmPwd" placeholder="请确认新密码">
                    <input type="hidden" name="cfmPwd">
					<span class="attention msg"></span>
					<span class="submit" id="changPwdBtn">确认</span>
                    </form>
				</div>
			</div>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="/js/personal/personal_Personalmessage.js"></script>
</html>