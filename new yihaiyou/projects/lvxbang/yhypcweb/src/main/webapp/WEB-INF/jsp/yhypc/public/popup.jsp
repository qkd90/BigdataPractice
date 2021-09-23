<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2017/1/18
  Time: 14:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--弹窗-->
<div class="popup_shadow" id="popup_modal" style="display: none;">
    <div class="collapse" id="popup_modelBody">
        <div id="loginComputer" class="login-computer dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <div class="logo"></div>
            <div class="login-telemail">
                <div class="telemail-header clearfix"><i class="i-left"></i><span>欢迎使用一海游</span><i class="i-right"></i></div>
                <div class="telemail-body">
                    <form id="login_form" enctype="multipart/form-data" action="">
                    <div class="telemail-input-wrap">
                        <span class="namespan">用户名</span>
                        <input type="text" name="account" id="account" placeholder="用户名" autocomplete="off">
                        <p class="tips login-tips"></p>
                        <span class="passwordspan">密码</span>
                        <input type="password" id="pwd" placeholder="密码">
                        <input type="hidden" name="password">
                        <p class="tips login-tips"></p>
                        <div class="account-signin-group">
                            <input type="text" class="checkinput" name="vcode" placeholder="验证码" autocomplete="off">
                            <span class="vcode_span"><img class="vcode_img" src="/image/dologin_vcode.jsp"></span>
                            <a class="vcode_a vcode_login_a">换一张</a>
                        </div>
                        <p class="tips login-tips"></p>
                        <img id="eyeicon" src="/image/icon-passwordeye.png">
                    </div>
                    <div class="telemail-tips clearfix">
                        <label class="clearfix">
                            <input type="checkbox" name="autoLogin" id="auto_login" value="true"><i class="radiomask"></i><span>自动登录</span>
                        </label>
                        <a id="jumpToPwdforgot">忘记密码？</a>
                    </div>
                    <div class="next-box"><a id="doLoginBtn">登录</a></div>
                    </form>
                </div>
            </div>
            <div class="login-computer-footer clearfix">
                <span>还没有账号的点击<a id="jumpToRegister">注册</a></span>
                <a id="jumpToWechat"></a>
            </div>
        </div>
        <%--//--%>
        <div id="loginWeichart" class="login-weichart dg" style="display: none">
            <input type="hidden" id="jsid" value="${session.id}">
            <div class="clearfix">
                <a class="close-btn" href="javascript:;"></a>
            </div>
            <%--<h4>微信登录</h4>--%>
            <div class="weichartcode-wrap" id="wx_code_ctn"></div>
            <%--<p>请使用微信扫描二维码登录</p>--%>
            <%--<div><a class="yhylink" href="#">'一海游'</a></div>--%>
            <div class="clearfix"><a id="jumpToPC"></a></div>
        </div>
        <div id="passwordForget" class="password-forget" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <form id="startForgetPwdForm" enctype="multipart/form-data" action="">
            <h4>忘记密码</h4>
            <span class="forget-namespan">用户名</span>
            <input type="text" placeholder="用户名" class="username-input" name="account" autocomplete="off">
            <p class="tips username-tips"></p>
            <div class="passwordforget-group clearfix">
                <span class="forget-imgcode">图片验证码</span>
                <input type="text" name="vcode" placeholder="图片验证码" autocomplete="off" class="forget-imgcode-input">
                <span class="vcode_span"><img class="vcode_img" src="/image/login_vcode.jsp"></span><a class="vcode_a" href="javascript:;">换一张</a>
            </div>
            <p class="tips username-tips"></p>
            <div class="next-box"><a class="passwordforget-nextbtn" id="startForgetPwd">下一步</a></div>
            </form>
        </div>
        <div id="forgetPwdCheck" class="account-check dg" style="display: none">
            <form id="forgetPwdCheckForm" enctype="multipart/form-data" action="">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>账号验证</h4>
            <p>验证码已发送至: <span class="send-mobile"></span></p>
            <p>请在<span class="code-timeout"></span>s内完成验证</p>
            <input type="text" name="smsCode" class="smsCode" placeholder="验证码">
            <p class="tips time-tips resend-timeout">后重新获取</p>
            <p class="tips time-tips smscode-msg"></p>
            <input type="hidden" name="fpcCode">
            <div class="next-box"><a id="doCheckForgetPwd">下一步</a></div>
            </form>
        </div>
        <div id="resetPwd" class="account-signin reset-pwd dg" style="display: none">
            <form id="resetPwd_form" enctype="multipart/form-data" action="">
                <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
                <h4>重置密码</h4>
                <input type="password" name="password" placeholder="新密码" autocomplete="off">
                <p class="tips input-tips"></p>
                <input type="password" name="cfmPwd" placeholder="确认新密码" autocomplete="off">
                <p class="tips input-tips"></p>
                <input type="hidden" name="fpcCode">
                <p class="tips input-tips"></p>
                <div class="next-box"><a id="doResetPwd">确认重置</a></div>
            </form>
        </div>
        <div id="resetPwdSuccess" class="account-success dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>操作成功</h4>
            <p class="success-ico"></p>
            <p class="success-msg">密码重置成功!</p>
            <div class="next-box">
                <div class="btns">
                    <a class="backToLogin">去登录</a>
                    <a class="closeDgBtn">关闭</a>
                </div>
            </div>
        </div>
        <div id="changePwdSuccess" class="account-success dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>操作成功</h4>
            <p class="success-ico"></p>
            <p class="success-msg">密码修改成功!</p>
            <div class="next-box">
                <div class="btns">
                    <a class="closeDgBtn">关闭</a>
                </div>
            </div>
        </div>
        <div id="commonSuccess" class="account-success dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>操作成功</h4>
            <p class="success-ico"></p>
            <p class="success-msg">恭喜, 操作成功!</p>
            <div class="next-box">
                <div class="btns">
                    <a class="closeDgBtn">关闭</a>
                </div>
            </div>
        </div>
        <div id="realnameCheck" class="realname-check dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>实名查询</h4>
            <input type="text" placeholder="姓名" class="realName">
            <p class="tips input-tips">请输入正确的姓名</p>
            <input type="text" placeholder="身份证" class="realIdNumber">
            <input type="text" placeholder="手机号" class="realTelephone">
            <input type="text" placeholder="邮箱" class="realEmail">
            <div class="next-box"><a class="submitButton">查询</a></div>
        </div>
        <div id="ferryLogin" class="realname-check dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>轮渡账号登录</h4>
            <input type="text" placeholder="账号" class="ferryName" readonly>
            <input type="password" placeholder="密码" class="ferryPassword">
            <div class="next-box"><a class="submitButton">登录</a></div>
        </div>
        <div id="ferryRegister" class="realname-check dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>轮渡账号注册</h4>
            <input type="text" placeholder="账号" class="ferryName">
            <input type="password" placeholder="密码" class="ferryPassword">
            <input type="password" placeholder="确认密码" class="ferryCheckPassword">
            <input type="text" placeholder="银行卡号" class="ferryBankNo">
            <div class="attention"><span>*</span>检测到您还未注册过轮渡账号，系统为您生成轮渡账号，请设置密码和完善银行卡信息</div>
            <div class="next-box"><a class="submitButton">注册</a></div>
        </div>
        <div id="doRealname" class="realname-check dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>实名认证</h4>
            <input type="text" placeholder="姓名" class="realName">
            <p class="tips input-tips">请输入正确的姓名</p>
            <input type="text" placeholder="身份证" class="realIdNumber">
            <input type="text" placeholder="手机号" class="realTelephone">
            <input type="text" placeholder="银行卡号" class="realBankNo">
            <div class="attention"><span>*</span>检测到您还未绑定轮渡账号，请输入信息查询是否有轮渡账号</div>
            <div class="next-box"><a class="submitButton">认证</a></div>
        </div>
        <div id="accountSignin" class="account-signin dg" style="display: none">
            <form id="register_form" enctype="multipart/form-data" action="">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>账号注册 <span class="login_r">登录</span></h4>
            <span class="username">用户名</span>
            <input type="text" class="account" name="account" placeholder="用户名" autocomplete="off">
            <p class="tips input-tips"></p>
            <span class="phoneNum">手机号</span>
            <input type="text" class="mobile" name="mobile" placeholder="手机号" autocomplete="off">
            <p class="tips input-tips"></p>
            <span class="apassword">密码</span>
            <input type="password" class="pwd pwd_a" id="reg_pwd" placeholder="密码">
            <input type="hidden" id="reg_pwd_md5" name="password">
            <p class="tips input-tips"></p>
            <span class="bpassword">确认密码</span>
            <input type="password" class="pwd pwd_b" id="reg_cfm_pwd" placeholder="确认密码">
            <p class="tips input-tips"></p>
            <div class="account-signin-group">
                <span class="checkcode">验证码</span>
                <input type="text" class="checkinput" name="vcode" placeholder="验证码" autocomplete="off">
                <span class="vcode_span"><img class="vcode_img" src="/image/login_vcode.jsp"></span>
                <a class="vcode_a">换一张</a>
            </div>
            <p class="tips input-tips"></p>
            <div class="next-box"><a id="preRegister">下一步，验证</a></div>
            </form>
        </div>
        <div id="accountCheck" class="account-check dg" style="display: none">
            <form id="accountRegCheckForm" enctype="multipart/form-data" action="">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>账号验证</h4>
            <p>验证码已发送至: <span class="send-mobile"></span></p>
            <p>请在<span class="code-timeout"></span>s内完成验证</p>
            <input type="text" name="smsCode" class="smsCode" placeholder="验证码">
            <p class="tips time-tips resend-timeout">后重新获取</p>
            <p class="tips time-tips smscode-msg"></p>
            <div class="next-box"><a id="doRegisterBtn">完成注册</a></div>
            </form>
        </div>
        <div id="accountRegSuccess" class="account-success dg" style="display: none">
            <div class="clearfix"><a class="close-btn" href="javascript:;"></a></div>
            <h4>注册成功</h4>
            <p class="success-ico"></p>
            <p class="success-msg">恭喜! 注册成功!</p>
            <div class="next-box">
                <div class="btns">
                    <a class="backToLogin">去登录</a>
                    <a class="closeDgBtn">关闭</a>
                </div>
            </div>
        </div>

        <div id="addTourist" class="setpswordBox" style="display: none">
            <div class="with_header">新增常用游客<span class="closebtn" id="psword_close"></span></div>
            <div class="with_body">
                <form id="tourist_form" enctype="multipart/form-data" action="">
                    <input type="hidden" name="tourist.id">
                    <div class="newUser">
                        <ul>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>姓   名</span>
                                <input type="text" name="tourist.name">
                            </li>
                            <li>
                                <span class="user_l">性   别</span>
                                <input type="hidden" name="tourist.gender" value="male">
                                <div class="checksel" id="sexSel"><span data-sex-value="male" class="checksex">男</span><span data-sex-value="female">女</span></div>
                            </li>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>证件类型</span>
                                <input type="hidden" name="tourist.idType" value="IDCARD">
                                <div class="checksel" id="idTypeSel"><span data-idType-value="IDCARD" class="checksex">身份证</span><span data-idType-value="PASSPORT">护照</span><span data-idType-value="STUDENTCARD">学生证</span><span data-idType-value="REMNANTSOLDIER">残军证</span></div>
                            </li>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>证件号码</span>
                                <input type="text" name="tourist.idNumber">
                            </li>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>旅客类型</span>
                                <input type="hidden" name="tourist.peopleType" value="ADULT">
                                <div class="checksel" id="peopleTypeSel"><span data-peopleType-value="ADULT" class="checksex">成人</span><span data-peopleType-value="KID">儿童</span></div>
                            </li>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>手机号码</span>
                                <input type="text" name="tourist.tel">
                            </li>
                            <li>
                                <span class="user_l">出生年月</span>
                                <div class="birth">
                                    <input id="birthDateSel" class="bir" type="text" name="birthday">
                                </div>
                            </li>
                            <li>
                                <span class="user_l"><label class="inevitable">*</label>电子邮件</span>
                                <input type="text" name="tourist.email">
                                <span class="advice">建议您填写电话和邮箱，便于您的朋友获得订单相关信息</span>
                            </li>
                            <li>
                                <span class="user_l">通讯地址</span>
                                <input type="text" name="tourist.address">
                            </li>
                            <li>
                                <span class="user_l dian"><i></i></span>
                                <div class="button"><span class="save" id="saveTbTN">保存</span><span class="save cancle" id="cancelTBTtn">取消</span><span id="save_msg" class="save-msg"></span></div>
                            </li>
                        </ul>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript" src="/js/public/popup.js"></script>
<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>