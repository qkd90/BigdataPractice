<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/4
  Time: 09:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>支付</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">

</head>
<body>
<input type="hidden" value="0" id="page_reload"/>
<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <div class="Pay w1000 cl">
        <p class="price b fs14">签约付款<em class="more_bold">查看合同条款</em></p>
        <div class="contract">
            <div>
                <div>
                    <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin:0 auto; width:100%">
                        <tbody>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:23.8pt; margin:5pt 0pt; text-align:center">
                                    <span style="font-family:宋体; font-size:16pt; font-weight:bold">国家版国内旅游合同</span>
                                </p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt; text-align:right">
                                    <span style="font-family:宋体; font-size:7.5pt">合同编号：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:137px;height:24px;"/></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt"><br />
                                    <span style="font-family:宋体; font-size:7.5pt">旅游者：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">等</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:58px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">人（名单可附页，需旅行社和旅游者代表签名、盖章确认）。</span><br />
                                    <span style="font-family:宋体; font-size:7.5pt">旅行社：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:262px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">。</span><br />
                                    <span style="font-family:宋体; font-size:7.5pt">旅行社业务经营许可证编号：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:137px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">。</span></p></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div>
                    <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin:0 auto; width:100%">
                        <tbody>
                        <tr>
                            <td style="vertical-align:middle"><p style="line-height:18pt; margin:5pt 0pt; text-align:center"><span style="font-family:宋体; font-size:18pt; font-weight:bold">协议条款</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">线路行程时间</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">出发时间</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:53px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">年</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">月</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">日</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">时</span>
                                    <span style="font-family:宋体; font-size:7.5pt"> </span>
                                    <span style="font-family:宋体; font-size:7.5pt">，结束时间</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:58px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">年</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">月</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">日</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">时</span>
                                    <span style="font-family:宋体; font-size:7.5pt">共</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">天，住宿</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:34px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">夜（含行程中交通工具上过夜）。</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十一条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">旅游费用及支付（以人民币为计算单位）</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">成人：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:53px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">元</span>
                                    <span style="font-family:Simsun; font-size:7.5pt">/</span>
                                    <span style="font-family:宋体; font-size:7.5pt">人；儿童：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:53px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">元</span>
                                    <span style="font-family:Simsun; font-size:7.5pt">/</span>
                                    <span style="font-family:宋体; font-size:7.5pt">人；其中，导游服务费：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">元</span>
                                    <span style="font-family:Simsun; font-size:7.5pt">/</span>
                                    <span style="font-family:宋体; font-size:7.5pt">人</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">旅游费用合计：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">元。</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">旅游费用支付方式：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:317px;height:24px"/></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">旅游费用支付的时间：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:437px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">。</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十二条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">人生意外伤害保险</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">1</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、旅行社提示旅游者购买人身意外伤害保险；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">2</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、旅游者可以做以下选择：</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <input type="checkbox" disabled checked="checked" style="border: 1px solid #999;width: 21px;height: 14px;"/>
                                    <span style="font-family:宋体; font-size:7.5pt">委托旅行社购买（旅行社不具有保险兼业代理资格的，不得勾选此项）：保险产品名称</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">（投保的相关信息以实际保单为准）；</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十三条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">成团人数与不成团的约定</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">最低成团人数：</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:77px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">人。</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">如不能成团，旅游者是否同意按下列方式解决：</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">1</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="同意" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">（同意或者不同意，打勾无效）旅行社委托</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:137px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">旅行社履行合同；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">2</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="同意" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">（同意或者不同意，打勾无效）延期出团；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">3</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="同意" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">（同意或者不同意，打勾无效）改变其他线路出团。</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:Simsun; font-size:7.5pt">4</span>
                                    <span style="font-family:宋体; font-size:7.5pt">、</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="同意" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">（同意或者不同意，打勾无效）解除合同。</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十四条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">拼团约定</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">旅游者</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="同意" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">（同意或者不同意，打勾无效）采用拼团方式至</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                    <span style="font-family:宋体; font-size:7.5pt">旅行社成团。</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十五条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">自愿购物和参加另行付费旅游项目约定</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">1、旅游者可以自主决定是否参加旅行社安排的购物活动、另行付费旅游项目；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">2、旅行社可以在不以不合理的低价组织旅游活动、不诱骗旅游者、不获取回扣等不正当利益，且不影响其他旅游者行程安排的前提下，按照平等自愿、诚实信用的原则，与旅游者协商一致达成购物活动、另行付费旅游项目协议；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">3、购物活动、另行付费旅游项目安排应不与《行程单》冲突；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">4、地接社及其从业人员在行程中安排购物活动、另行付费旅游项目的，责任由订立本合同的旅行社承担；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">5、购物活动、另行付费旅游项目具体约定见《自愿购物活动补充协议》（附件3）、《自愿参加另行付费旅游项目补充协议》（附件4）。</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十六条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">争议的解决方式</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">本合同履行过程中发生争议，由双方协商解决；亦可向合同签订地的旅游质监执法机构、消费者协会、有关的调解组织等有关部门或者机构申请调解。协商或者调解不成的，按下列第</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:46px;height:24px" value="1" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">种方式解决：</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">1、提交</span>
                                    <input type="text" style="text-align: center;border: 1px solid #999;width:173px;height:24px" value="旅游质监执法机构" disabled/>
                                    <span style="font-family:宋体; font-size:7.5pt">仲裁委员会仲裁；</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">2、依法向人民法院起诉。</span></p></td>
                        </tr>
                        <tr>
                            <td style="vertical-align:middle">
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">第二十七条</span>
                                    <span style="font-family:宋体; font-size:7.5pt">其他约定事项</span></p>
                                <p style="line-height:13.35pt; margin:0pt">
                                    <span style="font-family:宋体; font-size:7.5pt">未尽事宜，经旅游者和旅行社双方协商一致，可以列入补充条款。（如合同空间不够，可以另附纸张，由双方签字或者盖章确认。）</span></p>
                                <ol type="1" style="margin:0pt; padding-left:0pt;">
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        1.	签约人代理其他旅游者签约的或签约人与旅游者（即出游人）不一致的，签约人保证已得到全体旅游者的授权签署本合同（包括附件、补充协议），并保证将上述材料约定事项向全体旅游者作出必要说明（特别是本合同涉及“温馨提醒”、“免责条款”等相关的内容），如签约人未履行上述义务或因没有代理权限导致旅游者与 旅行社发生纠纷的，由签约人承担全部赔偿责任。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        2.	旅游者同意按照旅行社提供的参考行程签约，实际航班、车次以行前说明会或出团通知的行程为准
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        3.	《补充条款》、《出团通知》及所附《行程单》、《健康证明》、《孕妇出游须知及健康申明》作为本合同附件。旅行社已将《安全须知》、《安全保障卡》发 放给旅游者，旅游者应仔细阅读并遵照执行，旅行社不再留存。确认后如有变化，旅行社需及时用传真、短信、电话或电子邮件方式告知旅游者。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        4.	旅游者应准时到达集合地点，迟到半小时以上的视为放弃，但旅游者仍可以自行加入后面的行程，所放弃行程部分已发生的费用不予退还
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        5.	旅行社均特别提醒，请旅游者在出行前做一次必要的身体检查，如存在下列健康问题或80岁以上老人，请勿报名参团出游，如隐瞒参团出游发生事故，责任自负： 传染性疾病患者、心血管疾病患者如高血压等、脑血管疾病患者如脑栓塞等、呼吸系统疾病患者、精神病患者如癫痫病人等、严重贫血病患者、大中型手术的恢复期 病患者、孕妇及行动不便者。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        6.	70周岁以上老人需签订《健康证明》且有家属或朋友陪同方可出游；未成年人需由父母或由父母指定的临时监护人陪同，如同行人员中有未成年人无父母陪同出游的，同行的成年出游人均视为未成年人的临时监护人，负责全程看管、照顾未成年人，如隐瞒参团发生事故，责任自负。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        7.	在水上（包括江河、湖海、水库）游览或者活动时，旅游者要注意乘船安全。不可单独前往深水水域或者危险河道，选择水下游泳时，应做好安全保障措施，携带救生设备助游；旅游者经过危险地段（如陡峭、狭窄、湿滑的道路等）要注意安全，不可拥挤。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        8.	海拔3000米以上的的高原地带，气压低，空气含氧量少，易导致人体缺氧，引起高原不良反应，请旅游者避免剧烈运动和情绪兴奋。16周岁以下及60周岁以上者，患有贫血、糖尿病、慢性肺病、较严重心血管疾病、精神病及孕妇等人士不宜进入高原旅游。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        9.	高风险娱乐项目，如草地摩托、雪上摩托、骑马、快艇、漂流、攀岩、滑雪、潜水、蹦极等，请旅游者根据自身情况选择参加，仔细阅读景区提示，在景区指定区域内开展活动，注意人身安全。酒后禁止参加高风险娱乐项目。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        10.	旅游者在出发前或行程中因身体不适而终止旅游或变更行程的，或因身体原因造成损害的，由此造成的损失由旅游者自行承担。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        11.	在游览中请听从导游和当地相关管理人员安排，听从有关人员指导，否则责任由旅游者自行承担。旅游者在行程中发现自身权益受到侵害，应及时向领队、导游以及旅行社紧急联系人提出，因没有及时提出而造成的损失由旅游者自负。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        12.	在自行安排活动期间，旅游者应选择自己能够控制风险的活动项目，并对自己的安全负责，旅行社已尽到警示及事后协助义务的，旅行社不承担责任。自行安排活动 期间包括旅行社安排的在旅游行程中独立的自由活动期间、旅游者不参加旅游行程的活动期间以及旅游者经导游或者领队同意暂时离队的个人活动期间等。旅游期间 及自行安排活动期间请注意人身和财产安全。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        13.	旅行社提示和推荐旅游者根据个人意愿和需要购买个人旅游意外保险，保险产品和保险发票均由保险公司提供。根据保险公司的规定，三大年龄段（未成年人、成年人、老年人）承保、赔付保险金额是不同的，其中未成年人、老年人保险赔付金额较低，敬请留意。对于高风险娱乐项目，旅行社在此特别提醒，建议旅游者投保高风险意外险。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        14.	若旅游行程内含有购物店，旅游者有权选择是否参加，如旅游者不愿参加，可选择预订其他旅游产品线路，或届时直接向导游或领队提出，由旅行社和旅游者另行协商安排。旅游行程中的自由活动期间内，旅行社将会视旅游者需求及实际情况推荐部分自费项目，如旅游者自愿参加，应与旅行社另行签署书面协议，并不得影响同团其他客人在此期间的活动安排。请旅游者购物时慎重、把握好质量与价格，主动向商家索要发票及其他购物凭证，因商品质量问题需旅行社协助索赔时，须凭发票及其他有效购物凭证。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        15.	在保证不减少行程的前提下，旅行社可依据具体情况调整行程。因不可抗力等不可归责于旅行社的原因导致合同无法履行或旅游行程需变更的，双方互不承担违约责任，合同无法履行的，旅游者或旅行社均有权要求解除合同，旅行社返还尚未实际发生的费用；变更旅游行程的，在征得旅游者同意后，旅行社有权请求旅游者分担因此增加的旅游费用，或旅游者有权请求旅行社退还因此减少的费用。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        16.	如安排住宿时出现单男单女，旅行社尽量安排同性别客人拼房或加床，如以上2种方式无法安排，旅游者需要支付单房差。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        17.	若旅游者在出游前未能按约及时付清全部团费的，旅行社有权解除合同，旅游者应承担旅行社所有已经实际发生的费用。
                                    </li>
                                    <li style="font-family:微软雅黑; font-size:8.5pt; font-weight:bold; line-height:13.35pt; margin:5pt 0pt 5pt 35.91pt; padding-left:0.09pt; text-indent:0pt;">
                                        <span>18.双方特别约定，旅游者出发前取消行程的，按下列标准向旅行社支付业务损失费，</span>
                                        <p style="line-height:13.35pt;margin: 0;font-family: 微软雅黑; font-size:8.5pt; font-weight:bold;">
                                            （1）出发前7 日至4 日，支付旅游费用总额50%；
                                        </p>
                                        <p style="line-height:13.35pt;margin: 0;font-family: 微软雅黑; font-size:8.5pt; font-weight:bold;">
                                            （2）出发前3 日至1 日，支付旅游费用总额60%；
                                        </p>
                                        <p style="line-height:13.35pt;margin: 0;font-family: 微软雅黑; font-size:8.5pt; font-weight:bold;">
                                            （3）出发当日，支付旅游费用总额80%。
                                        </p>
                                        <p style="line-height:13.35pt;margin: 0;font-family: 微软雅黑; font-size:8.5pt; font-weight:bold;">
                                            如行程中包含火车票，旅游者取票后要求解除合同的，旅游者应持证件自行办理退票，旅行社在收到相应火车票退款后为旅游者办理解除合同的退款手续；如因旅游者原因未退票成功，实际损失由旅游者自行承担。
                                        </p>
                                        <p style="line-height:13.35pt;margin: 0;font-family: 微软雅黑; font-size:8.5pt; font-weight:bold;">
                                            本条款约定与第十二条第1款、第十五条第1款约定有冲突的，双方同意以本款约定为准。
                                        </p>
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        19.	若选择信用卡支付，则旅游者已授权旅行社从本人指定的信用卡中扣款，以支付本合同所有相关服务内容的款项。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        20.	旅游者明确知晓且同意，因爆款产品资源特殊，旅游者付款后无法更改、取消、退款。如此款约定与合同中其他条款约定不一致的，以此款约定为准。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        21.	如需开具发票，请旅游者归来后两个月内向旅行社索要。发票开具后，若办理退款，需先退还原发票，并保持发票兑奖联完好（如有兑奖联）。旅行社可接受旅游 者、或除旅游者之外的其他人员、单位对本合同付款，并且旅行社按照国家有关法规要求对实际付款方开具发票；若实际付款方为受托付款的，请提供相关证明文件，由旅行社对委托付款方开具发票。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        22.	旅游者同意旅行社选用以下组团方式：本社组团、联合组团、散客拼团。同意由旅行社选择并委托有资质的接待旅行社完成旅游活动。接待社名称：
                                        <input type="text" style="text-align: center;border: 1px solid #999;width:317px;height:24px"/>
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        23.	发生争议时，双方协商解决；协商不成的，按照法律的规定，双方约定由被告住所地的人民法院来管辖。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        24.	游客自行购买___/__保险，保险费用__/__人乘以__/___元=____/___元，发票由保险公司提供。<br/>
                                        签约付款后，电子保单将上传至签约人会员中心对应订单中，您可登录会员中心查看。
                                    </li>
                                    <li style="font-family:Simsun; font-size:7.5pt; line-height:13.35pt; margin:5pt 0pt 5pt 30.5pt; padding-left:5.5pt; text-indent:0pt">
                                        25.	备注内容:
                                    </li>
                                    </ol>
                                    <p style="line-height:14pt; margin:0pt">
                                        <span style="font-family:微软雅黑; font-size:14pt; font-weight:bold">请旅游者（旅游者）务必关注本条款的全部内容。签字代表您已明确知晓上述条款并愿意接受与遵守！</span></p>
                                    <p style="line-height:13.35pt; margin:0pt">
                                        <span style="font-family:宋体; font-size:7.5pt">旅游者名单如下，旅游者需仔细核对旅游者信息，确保信息准确无误，旅行社将按照以下信息预订，若因信息有误产生损失由旅游者自行承担。</span></p>
                                    <div style="text-align:center">
                                        <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin:0 auto; width:98%">
                                            <tbody>
                                            <tr>
                                                <td style="background-color:#cccccc; border-bottom-color:#333333; border-bottom-style:solid; border-bottom-width:0.75pt; border-left-color:#333333; border-left-style:solid; border-left-width:0.75pt; border-right-color:#333333; border-right-style:solid; border-right-width:0.75pt; border-top-color:#333333; border-top-style:solid; border-top-width:0.75pt; padding:0.68pt 2.28pt; vertical-align:middle"><p style="margin:0pt; text-align:center"><span style="font-family:宋体; font-size:12pt; font-weight:bold">姓 名</span></p></td>
                                                <td style="background-color:#cccccc; border-bottom-color:#333333; border-bottom-style:solid; border-bottom-width:0.75pt; border-left-color:#333333; border-left-style:solid; border-left-width:0.75pt; border-right-color:#333333; border-right-style:solid; border-right-width:0.75pt; border-top-color:#333333; border-top-style:solid; border-top-width:0.75pt; padding:0.68pt 2.28pt; vertical-align:middle"><p style="margin:0pt; text-align:center"><span style="font-family:宋体; font-size:12pt; font-weight:bold">性别</span></p></td>
                                                <td style="background-color:#cccccc; border-bottom-color:#333333; border-bottom-style:solid; border-bottom-width:0.75pt; border-left-color:#333333; border-left-style:solid; border-left-width:0.75pt; border-right-color:#333333; border-right-style:solid; border-right-width:0.75pt; border-top-color:#333333; border-top-style:solid; border-top-width:0.75pt; padding:0.68pt 2.28pt; vertical-align:middle"><p style="margin:0pt; text-align:center"><span style="font-family:宋体; font-size:12pt; font-weight:bold">证件类型</span></p></td>
                                                <td style="background-color:#cccccc; border-bottom-color:#333333; border-bottom-style:solid; border-bottom-width:0.75pt; border-left-color:#333333; border-left-style:solid; border-left-width:0.75pt; border-right-color:#333333; border-right-style:solid; border-right-width:0.75pt; border-top-color:#333333; border-top-style:solid; border-top-width:0.75pt; padding:0.68pt 2.28pt; vertical-align:middle"><p style="margin:0pt; text-align:center"><span style="font-family:宋体; font-size:12pt; font-weight:bold">证件号</span></p></td>
                                                <td style="background-color:#cccccc; border-bottom-color:#333333; border-bottom-style:solid; border-bottom-width:0.75pt; border-left-color:#333333; border-left-style:solid; border-left-width:0.75pt; border-right-color:#333333; border-right-style:solid; border-right-width:0.75pt; border-top-color:#333333; border-top-style:solid; border-top-width:0.75pt; padding:0.68pt 2.28pt; vertical-align:middle"><p style="margin:0pt; text-align:center"><span style="font-family:宋体; font-size:12pt; font-weight:bold">出生日期</span></p></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div><p style="line-height:13.35pt; margin:0pt"></p></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p style="margin:0pt"><br /><br /><span style="font-family:宋体; font-size:12pt"></span></p>
                <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-left:0pt; width:100%">
                    <tbody>
                    <tr>
                        <td style="vertical-align:top; width:40%">
                            <p style="line-height:13.35pt; margin:0pt">
                                <span style="font-family:宋体; font-size:7.5pt; font-weight:bold">旅游者：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:118px;height:24px"/>
                            </p></td>
                        <td style="vertical-align:top; width:40%">
                            <p style="line-height:13.35pt; margin:0pt">
                                <span style="font-family:宋体; font-size:7.5pt; font-weight:bold">旅行社经办人：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:137px;height:24px"/>
                            </p></td>
                    </tr>
                    </tbody>
                </table>
                <p style="margin:0pt"><br /><span style="font-family:宋体; font-size:12pt"></span></p>
                <p style="line-height:13.35pt; margin:0pt"><span style="font-family:宋体; font-size:7.5pt">第二十八条　合同效力</span></p>
                <p style="line-height:13.35pt; margin:0pt">
                    <span style="font-family:宋体; font-size:7.5pt">本合同一式 2 份，双方各持 1 份，具有同等法律效力，自双方当事人签字或者盖章之日起生效。</span></p>
                <p style="margin:0pt"><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><span style="font-family:宋体; font-size:12pt"></span></p>
                <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-left:0pt; width:100%">
                    <tbody>
                    <tr>
                        <td style="vertical-align:top; width:40%">
                            <p style="line-height:13.35pt; margin:0pt">
                                <span style="font-family:宋体; font-size:7.5pt; font-weight:bold">旅游者代表（签名）：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:118px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">证件号码：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:118px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">住址：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">联系电话：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">传真：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">邮编：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">电子信箱：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">签约日期：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:166px;height:24px"/>
                            </p></td>
                        <td style="vertical-align:top">
                            <p style="line-height:13.35pt; margin:0pt">
                                <span style="font-family:宋体; font-size:7.5pt; font-weight:bold">旅行社（盖章）：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:262px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">签约代表签字（盖章）：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:137px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">营业地址：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:406px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">客服联系人：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">联系电话：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">传真：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">电子信箱：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                <br /><span style="font-family:宋体; font-size:7.5pt; font-weight:bold">签约日期：</span>
                                <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                            </p></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="vertical-align:middle">
                            <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-left:0pt">
                                <tbody>
                                <tr>
                                    <td style="padding:0.75pt; vertical-align:middle; width:32%"><p style="margin:0pt; text-align:right"><span style="font-family:宋体; font-size:12pt">签约地点：</span></p></td>
                                    <td style="padding:0.75pt; vertical-align:middle"><p style="margin:0pt">
                                        <input type="text" style="text-align: center;border: 1px solid #999;width:377px;height:24px"/>
                                    </p></td>
                                </tr>
                                <tr>
                                    <td style="padding:0.75pt; vertical-align:middle; width:32%"><p style="margin:0pt; text-align:right"><span style="font-family:宋体; font-size:12pt">旅行社监督、投诉电话：</span></p></td>
                                    <td style="padding:0.75pt; vertical-align:middle"><p style="margin:0pt">
                                        <input type="text" style="text-align: center;border: 1px solid #999;width:377px;height:24px"/>
                                    </p></td>
                                </tr>
                                </tbody>
                            </table><p style="line-height:13.35pt; margin:0pt"></p></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="vertical-align:middle">
                            <table cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-left:0pt">
                                <tbody>
                                <tr>
                                    <td style="padding:0.75pt 0.75pt 0.75pt 10.6pt; vertical-align:middle">
                                        <p style="margin:0pt">
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:77px;height:24px"/>
                                            <span style="font-family:宋体; font-size:12pt">省</span>
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:77px;height:24px"/>
                                            <span style="font-family:宋体; font-size:12pt">市旅游质监执法机构：</span><br />
                                            <span style="font-family:宋体; font-size:12pt">投诉电话：</span>
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                            <span style="font-family:宋体; font-size:12pt">&nbsp;</span>
                                            <span style="font-family:宋体; font-size:12pt">电子邮箱：</span>
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                            <br /><span style="font-family:宋体; font-size:12pt">地 址：</span>
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:406px;height:24px"/>
                                            <span style="font-family:宋体; font-size:12pt">&nbsp;</span>
                                            <span style="font-family:宋体; font-size:12pt">邮 编：</span>
                                            <input type="text" style="text-align: center;border: 1px solid #999;width:197px;height:24px"/>
                                        </p></td>
                                </tr>
                                </tbody>
                            </table><p style="line-height:13.35pt; margin:0pt"></p></td>
                    </tr>
                    <tr style="height:0pt">
                        <td style="width:136pt; border:none"></td>
                        <td style="width:279.3pt; border:none"></td>
                    </tr>
                    </tbody>
                </table>
                <p style="margin:0pt; orphans:0; text-align:justify; widows:0"><span style="font-family:Calibri; font-size:10.5pt">&nbsp;</span></p>
            </div>
        </div>
        <input id="orderId" type="hidden" value="${order.id}"/>
        <p class="xq fs14">订单信息：${order.name}
            <%--<a href="/lvxbang/order/hotelOrderDetail.jhtml?orderId=${order.id}" style="color:#74C7A1;border-bottom: 1px solid #74C7A1">详情</a>--%>
        </p>

        <p class="price2 fs14">需 支 付：<b class="fs16 ff_yh">￥<em><fmt:formatNumber type="number" pattern="###.#">${order.price}</fmt:formatNumber></em></b></p>

        <c:if test="${order.status == 'WAIT'}">
        <div class="Pay_div posiR mb15">
            <ul class="mailTab posiA fs16">
                <li class="checked" id="wechat-pay">微信支付</li>
                <li id="ali-pay">支付宝</li>
                <li>储蓄卡快捷</li>
            </ul>
            <div class="mailTablePlan textC ">
                <img src="/images/wxzf.jpg" style="margin-top:55px;"/>
                <%--<input id="wechat-code" type="hidden" value="${order.wechatCode}"/>--%>
                <%--<div id="code"></div>--%>
            </div>
            <div class="mailTablePlan textC disn">
                <img src="/images/zfb.jpg" style="margin-top:112px;"/>
            </div>
            <div class="mailTablePlan disn">
                <div class="DebitCard">
                        <%--<ul>--%>
                        <%--<li class="title">--%>
                        <%--<label class="DebitCard_l"> </label>--%>

                        <%--<div class="fl">--%>
                        <%--使用储蓄卡支付<b>${order.price}</b>元--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l">储蓄卡卡号</label>--%>

                        <%--<div class="DebitCard_d">--%>
                        <%--<input type="text" placeholder="请输入储蓄卡卡号" value="" class="input">--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l">姓名</label>--%>

                        <%--<div class="DebitCard_d">--%>
                        <%--<input type="text" placeholder="请输入储蓄卡的中文开户姓名" value="" class="input">--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l">姓名</label>--%>

                        <%--<div class="DebitCard_d">--%>
                        <%--<div class="sfz fl posiR">--%>
                        <%--<p class="name">身份证</p><i></i>--%>

                        <%--<p class="sfzp disn">--%>
                        <%--<a href="javaScript:;">身份2证</a>--%>
                        <%--<a href="javaScript:;">身份证3</a>--%>
                        <%--<a href="javaScript:;">身份证4</a>--%>
                        <%--</p>--%>
                        <%--</div>--%>
                        <%--<div class="DebitCard_d2 fl">--%>
                        <%--<input type="text" placeholder="请输入储蓄卡的中文开户姓名" value="" class="input">--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l">银行预留手机号</label>--%>

                        <%--<div class="DebitCard_d3">--%>
                        <%--<input type="text" placeholder="请输入手机号" value="" class="input">--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l">短信验证码</label>--%>

                        <%--<div class="DebitCard_d4 posiR">--%>
                        <%--<input type="text" placeholder="请输入验证码" value="" class="input">--%>
                        <%--<a href="#" class="b">获取验证吗</a>--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label class="DebitCard_l"></label>--%>

                        <%--<div class="DebitCard_d5 title posiR"><input type="checkbox" class="checkbox"/>记住此卡，下次填写更方便--%>
                        <%--</div>--%>
                        <%--</li>--%>
                        <%--</ul>--%>
                    <p class="link fs13 cl textR"><a href="#" class="b">储蓄卡网上银行</a>跳转到网上银行页面支付</p>
                </div>
                <form action="">

                </form>
            </div>
        </div>
        <a href="#" class="but  fs14 oval4 fr c" onclick="LxbPay.payOrder()">下一步</a>
        </c:if>
        <c:if test="${order.status != 'WAIT'}">
            <div class="Pay_div posiR mb15" style="border-top: 3px solid #c2ecd9;">
                <div style="margin-top: 130px;text-align: center;font-size: 25px;">
                    <span style="line-height: 50px;font-weight: bold;">订单生成中......客服尽快为您查询，给您反馈。</span><br/>
                    <span>如有疑问，请致电400-0131-770咨询。</span>
                </div>
            </div>
        </c:if>
        <p class="cl"></p>
    </div>
</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lvxbang/pay/pay.js" type="text/javascript"></script>

<script>
    $(document).ready(function () {
        //搜索框
        $(".categories .input").click(function () {
            $(this).next(".categories_div").show();
        });

        $(".categories_div li").click(function () {
            var label = $("label", this).text();
            $(this).closest(".categories").children(".input").val(label);
        });

        $('.categories  .input').on('click', function (event) {
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
        $(document).on("click", function () {
            $(".categories_div").hide();
        });

//选项卡
        $(".mailTab li").click(function () {
            $(this).addClass("checked").siblings().removeClass("checked");
            $(this).closest("div").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
        })

        //身份证
        $(".sfz .name,sfz i").click(function () {
            $(".sfzp").show();
        });

        $(".sfzp a").click(function () {
            var label = $(this).text();
            $(".sfzp").hide();
            $(".sfz .name").text(label);
        });


    });
</script>

