<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/3
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>

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
            <div class="login_title fs14">
                <p class="title fl ff_yh">欢迎回来</p>
                <span class="fr disB">还没有账号？<a href="${INDEX_PATH}/lvxbang/register/registerr.jhtml">立即注册</a></span>
            </div>
            <div class="login_div mt10">
                <ul class="login_ul">
                    <li class="login_li user">
                        <i></i>
                        <input type="text" value="" class="border_bottom" placeholder="请输入手机号/邮箱" id="account" autocomplete="Off"/>
                        <em></em>
                    </li>
                    <li class="login_li">
                        <i></i>
                        <input type="password" value="" class="border_bottom"  placeholder="请输入密码" id="password" onkeydown="KeyDown()" autocomplete="Off"/>
                        <em></em>
                    </li>
                    <li class="ff_yh">
                        <input type="button" value="登录" class="login_but"/>
                    </li>
                    <li class="security">
                        <p class="posiR fl fs14">
                            <%--<input type="checkbox" class="checkbox"/>--%>
                                <span class="checkbox fl mr10"><i></i></span>记住帐号</p>
                        <a href="${INDEX_PATH}/lvxbang/login/forgotPassword.jhtml" class="fr">忘记密码？</a>
                    </li>
                </ul>
                <p class="cl"></p>
            </div>
        </div>
        <div class="login_fr fr">
            <p class="name ff_yh textC">第三方账号登录</p>

            <p class="ico">
                <a  class="xl"><i></i>新浪微博登录</a>
                <a class="wx"><i></i>微信账号登录</a>
                <a class="qq"><i></i>QQ账号登录</a>
            </p>
        </div>
        <p class="cl"></p>
    </div>
</div>

<!--foot-->
<div class="foot">Copyright 2011-2013 版权所有 厦门浩茫连宇信息技术有限公司闽ICP备 14006003号</div>
<!--foot end-->
</body>
</html>
<script src="/js/lib/jquery.js" type="text/javascript"></script>
<script src="/js/lib/jquery.enplaceholder.js" type="text/javascript"></script>
<script src="/js/lib/jquery.lazyload.js" type="text/javascript"></script>
<script src="/js/lib/common_util.js" type="text/javascript"></script>
<script src="/js/lvxbang/account/account_util.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //输入时横线加深
        $('.border_bottom').focus(function(){
            $(this).parent().css('border-bottom','1px solid #bbbbbb');
        }).blur(function(){
            $(this).parent().css('border-bottom','1px solid #eef0f5');
        });
        //记住账号样式
        $(".login .checkbox").click(function(){
            if($(this).hasClass('checked')){
                $(this).removeClass("checked")
            }else{
                $(this).addClass("checked")
            }
        });


        //焦点移开时判断是否为空
        $(document.body).delegate('.login_li input', 'blur', function () {

            var val = $(this).val();
            var placeholder = $(this).attr("placeholder");
            if (val != "" && val != "null") {
                $(this).next("em").hide();
                $(".error").animate({"height": "0px", "opacity": 1}, 0);
            } else {
                $(this).next("em").show();
                $(".error").text(placeholder).animate({"height": "60px", "opacity": 1}, 400);
            }
        });

        //提交判断
        $('.login_but').click(function () {
            var emptyCount = 0;
            var emptyIndex = -1;
            var account;
            var password;
            var index = 0;

            $('.login_li input').each(function (i) {
                var value = $(this).val();

                if (index == 0) {
                    account = value;
                    index++;
                } else {
                    password = value;
                }
                if (value == '') {
                    emptyCount++;
                    $(this).next("em").show();
                    emptyIndex = i;
                } else {
                    $(this).next("em").hide();
                }
            });

            if (emptyCount > 1) {
                $(".error").text("请输入账号和密码").animate({"height": "60px", "opacity": 1}, 400);
            } else if (emptyCount > 0) {
                if (emptyIndex == 0) {
                    $(".error").text("请输入手机号/邮箱").animate({"height": "60px", "opacity": 1}, 400);
                } else {
                    $(".error").text("请输入密码").animate({"height": "60px", "opacity": 1}, 400);
                }
            } else {
                $(".error").animate({"height": "0px", "opacity": 0}, 400);

                var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
                if (!account.match(mobile) && !isEmail(account)) {
                    //
                    $(".error").text("账号格式不正确，请重新输入").animate({"height":"60px","opacity":1},400);
                    return;
                }

                //提交动作写在这里
                Account.login();
            }


        });

    });
</script>

<script>
    function KeyDown() {
        if (event.keyCode == 13) {
            event.returnValue = false;
            event.cancel = true;
            Account.login();
        }
    }
</script>
