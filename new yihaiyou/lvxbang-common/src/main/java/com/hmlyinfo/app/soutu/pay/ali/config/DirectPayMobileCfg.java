package com.hmlyinfo.app.soutu.pay.ali.config;

/* *
 *类名：DirectPayMobileCfg
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class DirectPayMobileCfg {
	// 商户的私钥
	// 如果签名方式设置为“0001”时，请设置该参数
	public static final String PRIVATE_KEY = "";

	// 支付宝的公钥
	// 如果签名方式设置为“0001”时，请设置该参数
	public static final String ALI_PUBLIC_KEY = "";

	// 签名方式，选择项：0001(RSA)、MD5
	public static final String SIGN_TYPE = "MD5";
	// 无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA

	//支付宝网关地址
	public static final String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

	//授权接口
	public static final String DIRECT_PAY_CREATE_SERVICE = "alipay.wap.trade.create.direct";

	//交易接口
	public static final String DIRECT_PAY_EXECUTE_SERVICE = "alipay.wap.auth.authAndExecute";

	//返回格式
	//不需要修改
	public static final String FORMAT = "xml";

	//返回格式
	//不需要修改
	public static final String VERSION = "2.0";
}
