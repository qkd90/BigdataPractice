<%--
  Created by IntelliJ IDEA.
  User: HMLY
  Date: 2016/1/14
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="msg_box" class="mask"></div>
<%--<!--登录-->--%>
<div class="cq_login  denglux">
    <div class="login posiR">
        <p class="error"></p>
        <i class="close"></i>

        <div class="login_fl fl">
            <div class="login_title fs14">
                <p class="title fl ff_yh">欢迎回来</p>
                <span class="fr disB">还没有账号？<a href="javaScript:to_register_popup();">立即注册</a></span>
            </div>
            <div class="login_div mt10">
                <ul class="login_ul">
                    <li class="login_li user login_lil">
                        <i></i>
                        <input type="text" value="" class="border_bottom" placeholder="请输入手机号" id="login_account"/>
                        <em></em>
                    </li>
                    <li class="login_li login_lil">
                        <i></i>
                        <input type="password" value="" class="border_bottom" placeholder="请输入密码" id="login_password" onkeydown="KeyDown()"/>
                        <em></em>
                    </li>
                    <li class="ff_yh">
                        <input type="button" value="登录" class="login_but" onclick="toLogin();"/>
                    </li>
                    <li class="security">
                        <p class="posiR fl fs14">
                            <%--<input type="checkbox" class="checkbox"/>--%>
                                <span class="checkbox fl mr10" style="padding-left: 0px;"><i></i></span>
                            记住帐号</p>
                        <a href="${INDEX_PATH}/lvxbang/login/forgotPassword.jhtml" class="fr">忘记密码？</a>
                    </li>
                </ul>
                <p class="cl"></p>
            </div>
        </div>
        <div class="login_fr fr textC">
            <p class="name textC">第三方账号登录</p>

            <p class="ico">
                <a href="#" class="xl"><s></s></a>
                <a href="#" class="wx"><s></s></a>
                <a href="#" class="qq"><s></s></a>
            </p>
        </div>
        <p class="cl"></p>
    </div>
</div>

<!--注册-->
<div class="cq_login  zhuchex">
    <div class="login posiR">
        <p class="error"></p>
        <i class="close"></i>

        <div class="login_fl fl">
            <div class="login_title fs14">
                <p class="title fl ff_yh">新用户注册</p>
                <span class="fr disB">已有账号？<a href="javaScript:to_login_popup();">马上登录</a></span>
            </div>
            <div class="login_div mt10">
                <ul class="login_ul">
                    <li class="login_li user login_lir">
                        <i></i>
                        <input type="text" value="" class="border_bottom" placeholder="手机号码" id="register_account"/>
                        <em></em>
                    </li>
                    <li class="login_li sms login_lir">
                        <p><input type="text" value="" placeholder="短信验证码" id="smsCode"/></p>
                        <em></em>
                        <a href="#" class="sms_a textC" onclick="Account.sendSms()">免费获取验证短信</a>
                    </li>
                    <li class="login_li sms login_lir">
                        <p><input name="validateVlue" id="validateVlue_ADMIN" placeholder="验证码" value="" type="text"  class="reginput ui-txt03"  size="14" maxlength="4" reg="(\S)" tip="请输入验证码" /></p>
                        <img src="/images/checkNum.jsp" style="width: 100px;padding-right: 108px;border: white;background: white;" id="imgyz_ADMIN" onclick="imgClick('imgyz_ADMIN');" class="sms_a"/>
                    </li>
                    <li class="login_li login_lir">
                        <i></i>
                        <input type="password" value="" class="border_bottom" placeholder="设置密码" id="register_password"/>
                        <em></em>
                    </li>
                    <li class="security dy">
                        <p class="posiR fl fs14">
                            <%--<input type="checkbox" class="checkbox checkbox_r"/>--%>
                                <span style="padding-left: 0px;" class="checkbox checkbox_r fl mr10 checked"><i></i></span>
                            我已经阅读并接受<a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=5" target="_blank" class="fr">《旅行帮用户协议》</a>
                        </p>
                        <em></em>
                    </li>
                    <li class="ff_yh">
                        <input type="button" value="注册" class="login_but" onclick="toRegister();"/>
                    </li>

                </ul>
                <p class="cl h30"></p>
            </div>
        </div>
        <div class="login_fr fr textC">
            <p class="name textC">第三方账号登录</p>

            <p class="ico">
                <a href="#" class="xl"><s></s></a>
                <a href="#" class="wx"><s></s></a>
                <a href="#" class="qq"><s></s></a>
            </p>
        </div>
        <p class="cl"></p>
    </div>
</div>


<input type="hidden" id="to_order_url"/>
<!--头部刷新-->
<script type="text/html" id="tpl-has-user-item">
    <div class="Haslogged fr" id="loginStatus">
        <a class="type  fl posiR" href="${INDEX_PATH}/lvxbang/message/system.jhtml" id="myMessage">消息</a>
        <a href="${INDEX_PATH}/lvxbang/user/index.jhtml" class="name fl" id="userMessage"
           value="{{userName}}">个人中心：{{userName}}
        </a>
        <a href="javascript:exitDelete();" class="fr but exit">退出</a>
    </div>
</script>
<script src="/js/lvxbang/popup/login_register_popup.js" type="text/javascript"></script>