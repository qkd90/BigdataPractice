<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="orderId" value="${order.id}">
<input type="hidden" id="orderType" value="${order.orderType}">
<input type="hidden" id="waitSeconds" value="${order.waitSeconds}">
<div class="payBox">
  <div class="recharge">
	<span class="total">用户余额：
		<span class="rmb">¥</span><span class="num" id="balance" data-balance="<fmt:formatNumber value="${user.balance}" pattern="###.##"/>"><fmt:formatNumber value="${user.balance}" pattern="###.##"/></span>
	</span>
    <%--<span class="recha">充值</span>--%>
    <span class="balance_disable">去付款</span>
  </div>
  <div class="payList">
    <ul class="clearfix">
      <li class="wechat pay_active wechatActive">微信支付</li>
      <li class="zhifubao">支付宝支付</li>
      <li class="bank t_indent">银行卡支付</li>
      <li class="blank"></li>
    </ul>
  </div>
  <div class="payContain wechatPay active">
    <p class="img" id="wechat_qrcode"></p>
    <p class="paystyle">微信支付</p>
    <p class="at_word">提示：点击“下一步”后，请打开手机微信的“扫一扫”，扫描二维码</p>
    <div class="bypicture"></div>
    <span class="next"><a>下一步</a></span>
    <%--<p>返回重新修改</p>--%>
  </div>
  <div class="payContain zhifubaopay">
    <p class="img" id="zhifubao_qrcode"></p>
    <p class="paystyle">支付宝支付</p>
    <p class="at_word">提示：点击“下一步”后，请打开手机支付宝的“扫一扫”，扫描二维码</p>
    <div class="bypicture"></div>
    <span class="next"><a>下一步</a></span>
    <%--<p>返回重新修改</p>--%>
  </div>
  <div class="payContain bankpay">
    <p class="img"></p>
    <p class="paystyle">银行卡支付</p>
    <p class="at_word">提示：点击“下一步”后，填写银行卡号等信息</p>
    <div class="bypicture"></div>
    <span class="next"><a>下一步</a></span>
    <%--<p>返回重新修改</p>--%>
  </div>
</div>
<div class="windowPayShadow"></div>
<div class="payPasswordBox">
  <span class="closebtn"></span>
  <h3>支付密码</h3>
  <div class="attenContain">
    <input class="paykey" type="password">
  </div>
  <div class="btn">确认</div>
</div>
<div class="payPassConfirm">
  <span class="closebtn"></span>
  <h3>提示</h3>
  <div class="attenContain"><p>还未设置支付密码，是否前往设置？</p></div>
  <div class="btn_double"><a href="/yhypc/personal/myWallet.jhtml?fn=openSetPayPwdBox" target="_blank" class="yes">确认</a><span class="no">取消</span></div>
</div>