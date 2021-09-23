<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/3
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>找回密码</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body class="FY_password">

<!--head-->
<div class="head">
    <div class="w1000 posiR">
        <i class="posiA"></i>
        <a href="${INDEX_PATH}" target="_blank" class="logo fl"></a>
        <div class="navigation fl fs14">
            <a href="${INDEX_PATH}">首页</a>
            <a href="${PLAN_PATH}" >私人订制</a>
            <a href="/tour/self/index.html" >自助自驾</a>
            <a href="/tour/group/index.html" >跟团游</a>
            <a href="${RECOMMENDPLAN_PATH}" >游记</a>
            <a href="${SCENIC_PATH}" >景点</a>
            <a href="${HOTEL_PATH}" >酒店</a>
        </div>
        <p class="cl"></p>
    </div>
    <p class="cl"></p>
</div>
<!--head  end-->

<div class="main">
    <p class="error"></p>
    <div class="login posiR">
        <div class="login_fl fl">
            <div class="login_div mt10">
                <ul class="login_ul">
                    <li class="login_li user">
                        <i></i>
                        <input type="text" value="" class="border_bottom" placeholder="手机号码" id="account"/>
                        <em></em>
                    </li>
                    <li class="login_li sms">
                        <p><input type="text" value="" placeholder="短信验证码" id="smsCode"/></p>
                        <em></em>
                        <a href="#" class="sms_a textC" onclick="Account.forgotPasswordSms()">免费获取验证短信</a>
                    </li>
                    <li class="login_li sms">
                        <p><input name="validateVlue" id="validateVlue_ADMIN" placeholder="验证码" value="" type="text"  class="reginput ui-txt03"  size="14" maxlength="4" reg="(\S)" tip="请输入验证码" /></p>
                        <em></em>
                        <img src="/images/checkNum.jsp" style="width: 100px;padding-right: 108px;border: white;background: white;" id="imgyz_ADMIN" onclick="imgClick('imgyz_ADMIN');" class="sms_a"/>
                    </li>
                    <li class="login_li">
                        <i></i>
                        <input type="password" value="" class="border_bottom" placeholder="新密码" id="password"/>
                        <em></em>
                    </li>
                    <li class="ff_yh">
                        <input type="button" value="确认修改" class="login_but" onclick="Account.forgotPassword()"/>
                    </li>

                </ul>
                <p class="cl"></p>
            </div>
        </div>
        <div class="login_fr fl">
            <img src="/images/log_ico.jpg"/>
        </div>
        <p class="cl"></p>
    </div>
</div>

<!--foot-->
<div class="foot">Copyright 2011-2013 版权所有 厦门浩茫连宇信息技术有限公司闽ICP备 14006003号</div>
<!--foot end-->
<div id="msg_box" class="mask"></div>
</body>
</html>
<script src="/js/lib/jquery.js" type="text/javascript"></script>
<script src="/js/lib/jquery.enplaceholder.js" type="text/javascript"></script>
<script src="/js/lib/jquery.lazyload.js" type="text/javascript"></script>
<script src="/js/lib/common_util.js" type="text/javascript"></script>
<script src="/js/lvxbang/account/account_util.js" type="text/javascript"></script>
<jsp:include page="/WEB-INF/jsp/lvxbang/popup/popup.jsp"></jsp:include>
<script>
    $(document).ready(function () {

        function imgClick(eleId){
            document.getElementById(eleId).src="/images/checkNum.jsp?"+ new Date().getTime();
        }

        //输入时横线加深
        $('.border_bottom').focus(function () {
            $(this).parent().css('border-bottom', '1px solid #bbbbbb');
        }).blur(function () {
            $(this).parent().css('border-bottom', '1px solid #eef0f5');
        });

//    //焦点移开时判断是否为空
//    $(document.body).delegate('.login_li input', 'blur', function () {
//
//      var val = $(this).val();
//      var placeholder = $(this).attr("placeholder");
//      if (val != "" && val != "null") {
//        $(this).next("em").hide();
//        $(".error").animate({"height": "0px", "opacity": 1}, 0);
//      } else {
//        $(this).next("em").show();
//        $(".error").text(placeholder).animate({"height": "60px", "opacity": 1}, 400);
//      }
//    });
//    //焦点移开时判断是否为空
        $(document.body).delegate('.login_li input', 'blur', function () {
            console.log(1);
            var val = $(this).val();
            var placeholder = $(this).attr("placeholder");
            if (val != "" && val != "null") {
                $(this).next("em").hide();
            } else {
                $(this).next("em").show();
                $(".error").text(placeholder).animate({"height": "60px", "opacity": 1}, 400);
            }
        });


        //提交判断
        $('.login_but').click(function () {


            var emptyCount = 0;
            var emptyIndex = -1;
            $('.login_li input').each(function (i) {
                var value = $(this).val();
                if (value == '') {
                    emptyCount++;
                    $(this).next("em").show();
                    emptyIndex = i;
                } else {
                    $(this).next("em").hide();
                }
            });


            if (emptyCount > 2) {
                $(".error").text("请输入手机号和密码").animate({"height": "60px", "opacity": 1}, 400);
            } else if (emptyCount > 0) {


                if (emptyIndex == 0) {
                    $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
                } else {
                    $(".error").text("请输入验证码和密码").animate({"height": "60px", "opacity": 1}, 400);

                }
            } else {
                //提交动作写在这里
                Account.forgotPassword();
            }
        });

    });
</script>
