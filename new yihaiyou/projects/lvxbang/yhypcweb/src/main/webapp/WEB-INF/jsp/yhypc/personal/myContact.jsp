<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/personal/personal_head.css">
	<link rel="stylesheet" type="text/css" href="/css/personal/personal_Mycontact.css">
    <link rel="stylesheet" href="/css/public/pager.css">
	<title>我的常用联系人-一海游</title>
</head>
<body data-page-obj="MyContact" data-page-class="pc-mycontact" class="judgebottom">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
            <%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
				<div class="createbox clearfix"><span class="createMarch"><label>＋</label>常用联系人</span></div>
				<div class="contact_header">
					<span class="index">序号</span>
					<span class="name">姓名</span>
					<span class="sex">性别</span>
					<span class="carid">证件号码</span>
					<span class="cartype">证件类型</span>
					<span class="phonenum">手机号码</span>
					<span class="tratype">旅客类型</span>
					<span class="tratype">操作</span>
				</div>
				<div id="tourist_content"></div>
                <div class="paging m-pager"></div>
			</div>
			<%--<div class="shadow"></div>--%>
			<%--<div class="setpswordBox">--%>
				<%--<div class="with_header">新增常用游客<span class="closebtn" id="psword_close"></span>--%>
				<%--</div>--%>
				<%--<div class="with_body">--%>
                    <%--<form id="tourist_form" enctype="multipart/form-data" action="">--%>
                        <%--<input type="hidden" name="tourist.id">--%>
                        <%--<div class="newUser">--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>姓   名</span>--%>
                                    <%--<input type="text" name="tourist.name">--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l">性   别</span>--%>
                                    <%--<input type="hidden" name="tourist.gender">--%>
                                    <%--<div class="checksel" id="sexSel"><span data-sex-value="male">男</span><span data-sex-value="female">女</span></div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>证件类型</span>--%>
                                    <%--<input type="hidden" name="tourist.idType">--%>
                                    <%--<div class="checksel" id="idTypeSel"><span data-idType-value="IDCARD">身份证</span><span data-idType-value="PASSPORT">护照</span><span data-idType-value="STUDENTCARD">学生证</span></div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>证件号码</span>--%>
                                    <%--<input type="text" name="tourist.idNumber">--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>旅客类型</span>--%>
                                    <%--<input type="hidden" name="tourist.peopleType">--%>
                                    <%--<div class="checksel" id="peopleTypeSel"><span data-peopleType-value="ADULT">成人</span><span data-peopleType-value="KID">儿童</span></div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>手机号码</span>--%>
                                    <%--<input type="text" name="tourist.tel">--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l">出生年月</span>--%>
                                    <%--<div class="birth">--%>
                                        <%--<input id="birthDateSel" class="bir" type="text" name="birthday">--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l"><label class="inevitable">*</label>电子邮件</span>--%>
                                    <%--<input type="text" name="tourist.email">--%>
                                    <%--<span class="advice">建议您填写电话和邮箱，便于您的朋友获得订单相关信息</span>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l">通讯地址</span>--%>
                                    <%--<input type="text" name="tourist.address">--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span class="user_l dian"><i></i></span>--%>
                                    <%--<div class="button"><span class="save" id="saveTbTN">保存</span><span class="save cancle" id="cancelTBTtn">取消</span><span id="save_msg" class="save-msg"></span></div>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</form>--%>
				<%--</div>--%>
			<%--</div>--%>
            <%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script type="text/javascript" src="/js/public/tourist.js"></script>
<script type="text/javascript" src="/js/personal/personal_Mycontact.js"></script>
<script type="text/html" id="tourist_item">
    <div class="contact_header contact_body">
        <span class="index">{{index}}</span>
        <span class="name">{{name}}</span>
        <span class="sex">{{gender}}</span>
        <span class="carid">{{idNumber}}</span>
        <span class="cartype">{{idType}}</span>
        <span class="phonenum">{{tel}}</span>
        <span class="tratype">{{type}}</span>
        <span class="operate"><label class="edi" data-t-id="{{id}}">编辑</label><label class="del" data-t-id="{{id}}">删除</label></span>
    </div>
</script>
</html>