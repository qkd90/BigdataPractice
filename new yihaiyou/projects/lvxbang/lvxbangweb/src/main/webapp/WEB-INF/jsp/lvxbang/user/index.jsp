<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/12/16
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>个人中心</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
    <link href="/js/lib/webuploader/webuploader.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_grxx" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>


    <div class=" cl mc_nr">
        <div class="w1000 mc_nr_bg">
            <div class="m_left fl">
                <a href="javascript:;" class="checked not" div_class="my_message"><i></i>我的信息</a>
                <a href="javascript:;" class=" not" div_class="change_password"><i></i>修改密码</a>
            </div>

            <%--<div class="m_right fr">--%>
            <%--<p class="title ff_yh">我的头像</p>--%>
            <%--<div class="cl member_toux">--%>
            <%--<a href="javaScript:;" onclick="path.click()" class="file disB">--%>
            <%--<img src="${user.head}" alt="" id="head-pic"/>--%>
            <%--</a>--%>
            <%--<input type="file" id="head" style="display:none" onchange="User.uploadHead()">--%>
            <%--<a href="javaScript:;" class="mt20 but oval4 fs14 b" >上传头像</a>--%>
            <%--</div>--%>
            <div class="m_right fr my_message" div_class="my_message" >
                <p class="title ff_yh">我的头像</p>
                <div class="cl member_toux">
                    <a href="javaScript:;" onclick="" class="file disB" style="cursor: default">
                        <c:choose>
                            <c:when test="${user.head != null && user.head != ''}">
                                <c:choose>
                                    <c:when test="${fn: startsWith(user.head, 'http')}">
                                        <img src="${user.head}" alt="" id="change_avatar" style="width: 100%; height: 100%"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${user.head}" id="change_avatar"
                                             style="width: 100%; height: 100%"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/toux.PNG" id="change_avatar" style="width: 100%; height: 100%"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <form id="headForm" action="/lvxbang/user/updateFace.jhtml" method="post"
                          enctype="multipart/form-data">
                        <input type="file" id="path" name="userFace" style="display:none" onchange="User.previewHead()">
                    </form>
                    <p id="avatar_upload_tip" style="color: #666; margin-top: 10px; display: none">正在上传,请稍候...</p>
                    <a href="javaScript:;" class="mt20 but oval4 fs14 b" onclick="path.click()">更换头像</a>
                </div>
                <p class="title ff_yh">我的信息</p>
                <uL class="member_gr_ul cl">
                    <li>
                        <label class="fl"><span class="Orange mr5">*</span>姓名：</label>
                        <div class="fl nr oval4">
                            <input type="text" value="${user.userName}" class="input" id="name"></div>
                    </li>
                    <li>
                        <label class="fl">昵称：</label>
                        <div class="fl nr oval4">
                            <input type="text" value="${user.nickName}" class="input" id="nickName"></div>
                    </li>
                    <li>
                        <label class="fl">性别：</label>
                        <input id="gender" type="hidden" value="${user.gender}"/>
                        <div class="fl">
                            <p class="fl mr30">
                                <span class="fl radio mt10 mr5 gender" id="gender-male"></span>
                                <span class="fl disB">男</span>
                            </p>
                            <p class="fl mr30">
                                <span class="fl radio mt10 mr5 gender" id="gender-female"></span>
                                <span class="fl disB ">女</span>
                            </p>
                            <p class="fl mr30">
                                <span class="fl radio mt10 mr5 gender" id="gender-secret"></span>
                                <span class="fl disB ">保密</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <label class="fl">生日：</label>
                        <div class="fl nr oval4">
                        <input type="text" class="input" onclick="WdatePicker()" id="birthday"
                               value="<fmt:formatDate value='${user.birthday}' pattern="yyyy-MM-dd"/>" >
                            </div>
                        <%--<div class="fl nr sr2"><input type="text" placeholder="年" value="" class="input"></div>--%>
                        <%--<div class="fl nr sr"><input type="text" placeholder="月" value="" class="input"></div>--%>
                        <%--<div class="fl nr sr"><input type="text" placeholder="日" value="" class="input"></div>--%>
                    </li>
                    <%--<li>--%>
                    <%--<label class="fl">积分：</label>--%>
                    <%--<div class="fl">7级/VIP会员</div>--%>
                    <%--</li>--%>
                    <li>
                        <label class="fl"><span class="Orange mr5">*</span>手机：</label>
                        <div class="fl nr oval4"><input type="text" placeholder="" value="${user.telephone}" class="input"
                                                        id="tel"></div>
                    </li>
                    <li>
                        <label class="fl">邮箱：</label>
                        <div class="fl nr oval4"><input type="text" placeholder="" value="${user.email}" class="input"
                                                        id="email"></div>
                    </li>
                    <li>
                        <label class="fl">&nbsp;</label>
                        <div class="fl">
                            <a href="javaScript:;" class="but fs14 b oval4" onclick="User.updateInfo()">保存</a>
                        </div>
                    </li>
                </uL>


            </div>




            <div class="m_right fr change_password" style="display: none;">

                <p class="title ff_yh" style="">修改密码</p>
                <uL class="member_gr_ul cl">
                    <li>
                        <label class="fl" style="width:85px;"><span class="Orange mr5">*</span>当前密码：</label>
                        <div class="fl nr oval4">
                            <input type="password" value="" class="input" id="now_password"></div>
                    </li>
                     <li>
                        <label class="fl" style="width:85px;"><span class="Orange mr5">*</span>新设密码：</label>
                        <div class="fl nr oval4">
                            <input type="password" value="" class="input" id="new_password"></div>
                    </li>
                     <li>
                        <label class="fl" style="width:85px;"><span class="Orange mr5">*</span>重复密码：</label>
                        <div class="fl nr oval4">
                            <input type="password" value="" class="input" id="again_password"></div>
                    </li>
                    <li>
                        <label class="fl" style="width:85px;">&nbsp;</label>
                        <div class="fl">
                            <a href="javaScript:;" class="but fs14 b oval4" onclick="User.changePassword();">保存设置</a>
                        </div>
                    </li>
                </uL>


            </div>

            <p class="cl"></p>
        </div>
    </div>

</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/jquery-form.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/index.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //radio
        $(".member_gr_ul .radio").click(function () {
            $(this).parents(".member_gr_ul").find(".radio").removeClass('checked');
            $(this).addClass('checked');
        });
    });

</script>