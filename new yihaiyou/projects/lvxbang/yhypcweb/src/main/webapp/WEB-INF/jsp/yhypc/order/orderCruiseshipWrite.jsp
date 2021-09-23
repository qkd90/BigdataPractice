
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.data.data.hmly.action.yhypc.vo.CruiseshipOrderRoomRequest" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en"><head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>舱房详情</title>
    <link rel="stylesheet" href="/css/cruiseship/cssresets.css">
    <link rel="stylesheet" href="/css/cruiseship/new-public.css">
    <link rel="stylesheet" href="/css/cruiseship/customer_information.css">
</head>
<body>
<%@include file="../../yhypc/public/nav_header.jsp"%>

<%--<s:iterator value="cruiseshipOrderRoomRequests" var="cruiseshipOrderRoomRequest">

    <input id="children" name="" type="hidden" value="cruiseshipOrderRoomRequest.childNum"/>
    <input id="adult" name="" type="hidden" value="cruiseshipOrderRoomRequest.adultNum"/>
    <input id="price" name="" type="hidden" value="${cruiseshipOrderRoomRequest.price}"/>
    <input id="roomName" class="nameOfRoom" name="" type="hidden" value="${cruiseshipOrderRoomRequest.roomName}"/>
    <input id="totalPrice" name="" type="hidden" value="cruiseshipOrderRoomRequest.totalPrice"/>
    <input id="roomId" class="roomId" name="roomId" type="hidden" value="${cruiseshipOrderRoomRequest.id}"/>
    <input id="roomNum" name="roomNum" type="hidden" value="${cruiseshipOrderRoomRequest.roomNum}"/>
</s:iterator>--%>


<input id="dateId" name="dateId" type="hidden" value="${dateId}"/>


<div class="wrap-box">
    <div class="wrap">
        <div class="wrap-header clearfix">
            <div class="header-group active pull-left">
                <div class="header-content">
                    <i></i>
                    <span>选择舱房</span>
                </div>
            </div>
            <div class="header-group active pull-left">
                <div class="header-content">
                    <i></i>
                    <span>填写信息</span>
                </div>
            </div>
            <div class="header-group pull-left">
                <div class="header-content">
                    <i></i>
                    <span>支付并提交</span>
                </div>
            </div>
        </div>
        <div class="product-title">
            <h4 class="shipName" name=${cruiseShip.routeName}${cruiseShip.name}>${cruiseShip.routeName}${cruiseShip.name}</h4>
            <p class="playDate" name=${cruiseShip.startDate} endDate=${cruiseShip.endDate}>${cruiseShip.startDate} 至 ${cruiseShip.endDate}</p>
        </div>
        <div class="information clearfix">
            <div class="information-left pull-left">
                <div class="information-contact">
                    <h5>联系人信息</h5>
                    <div class="contact-group clearfix">
                        <label class="pull-left"><mark>*</mark>联系人：</label>
                        <input class="pull-left" type="text" name="username" id="userName">
                    </div>
                    <div class="contact-group clearfix">
                        <label class="pull-left"><mark>*</mark>Email：</label>
                        <input class="pull-left" type="text" name="email">
                        <span class="pull-left">用于接收旅游协议，产品确认单，出行指南等。</span>
                    </div>
                    <div class="contact-group clearfix">
                        <label class="pull-left"><mark>*</mark>手机号码：</label>
                        <input class="pull-left" type="text" name="username" id="phone">
                        <span class="pull-left">用于接收订单提醒短信，便于您及时了解订单状态。</span>
                    </div>
                </div>
                <div class="information-person">
                    <h5>出行旅客信息<span>请确保您填写的信息真实准确，否则会影响您顺利上船。</span></h5>
                    <%
                        List<CruiseshipOrderRoomRequest> cruiseshipOrderRoomRequests = (List<CruiseshipOrderRoomRequest>) request.getAttribute("cruiseshipOrderRoomRequests");
                        for (CruiseshipOrderRoomRequest cruiseshipOrderRoomRequest: cruiseshipOrderRoomRequests) {
                    %>
                    <div class="cabin-category detail" roomName=<%=cruiseshipOrderRoomRequest.getRoomName()%> price=<%=cruiseshipOrderRoomRequest.getPrice()%> dateId=<%=cruiseshipOrderRoomRequest.getDateId()%> roomId=<%=cruiseshipOrderRoomRequest.getId()%>><%=cruiseshipOrderRoomRequest.getRoomName()%></div>
                    <div class="orderDetails" roomName=<%=cruiseshipOrderRoomRequest.getRoomName()%> roomId=<%=cruiseshipOrderRoomRequest.getId()%> dateId=<%=cruiseshipOrderRoomRequest.getDateId()%> price=<%=cruiseshipOrderRoomRequest.getPrice()%>>
                    <%
                        for(int i = 0; i < cruiseshipOrderRoomRequest.getAdultNum(); i++) {
                    %>
                        <div class="person-group"name="成人">
                        <div class="group-header">
                            <span>成人</span>
                        </div>
                        <div class="group-input">
                            <div class="input-group chinese-name clearfix">
                                <label class="input-label pull-left"><mark>*</mark>中文姓名：</label>
                                <input class="pull-left peopleName" type="text" name="chinese-name01" placeholder="证件上的姓名，中文或拼音" >
                            </div>
                            <div class="input-group english-name clearfix">
                                <label class="input-label pull-left"><mark>*</mark>英文姓名：</label>
                                <input class="pull-left" type="text" name="last-name01" placeholder="姓/Last Name">
                                <input class="pull-left" type="text" name="first-name01" placeholder="名/First Name">
                            </div>
                            <div class="input-group sex clearfix">
                                <label class="input-label pull-left"><mark>*</mark>性别：</label>
                                <div class="input-radio pull-left clearfix">
                                    <label class="active pull-left">
                                        <input type="radio" name="sex01" value="男" checked="">
                                        <i></i>男
                                    </label>
                                    <label class="pull-left">
                                        <input type="radio" name="sex01" value="女">
                                        <i></i>女
                                    </label>
                                </div>
                            </div>
                            <div class="input-group birthday clearfix">
                                <label class="input-label pull-left"><mark>*</mark>出生日期：</label>
                                <input class="pull-left" type="text" name="birthday01" placeholder="1990-01-01">
                            </div>
                        </div>
                    </div>
                    <%
                        }
                        for(int i = 0; i < cruiseshipOrderRoomRequest.getChildNum(); i++) {
                    %>
                    <div class="person-group" name="儿童">
                        <div class="group-header">
                            <span>儿童</span>
                        </div>
                        <div class="group-input">
                            <div class="input-group chinese-name clearfix">
                                <label class="input-label pull-left"><mark>*</mark>中文姓名：</label>
                                <input class="pull-left peopleName" type="text" name="chinese-name01" placeholder="证件上的姓名，中文或拼音" >
                            </div>
                            <div class="input-group english-name clearfix">
                                <label class="input-label pull-left"><mark>*</mark>英文姓名：</label>
                                <input class="pull-left" type="text" name="last-name01" placeholder="姓/Last Name">
                                <input class="pull-left" type="text" name="first-name01" placeholder="名/First Name">
                            </div>
                            <div class="input-group sex clearfix">
                                <label class="input-label pull-left"><mark>*</mark>性别：</label>
                                <div class="input-radio pull-left clearfix">
                                    <label class="active pull-left">
                                        <input type="radio" name="sex01" value="男" checked="">
                                        <i></i>男
                                    </label>
                                    <label class="pull-left">
                                        <input type="radio" name="sex01" value="女">
                                        <i></i>女
                                    </label>
                                </div>
                            </div>
                            <div class="input-group birthday clearfix">
                                <label class="input-label pull-left"><mark>*</mark>出生日期：</label>
                                <input class="pull-left" type="text" name="birthday01" placeholder="1990-01-01">
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    </div>
                    <%
                        }
                    %>

                </div>
                <div class="information-bill">
                    <h5>发票及配送</h5>
                    <div class="bill clearfix">
                        <label class="bill-label pull-left">是否需要发票：</label>
                        <div class="input-radio pull-left clearfix">
                            <label class="active pull-left">
                                <input type="radio" name="billisNeed" value="0">
                                <i></i>否
                            </label>
                            <label class="pull-left">
                                <input type="radio" name="billisNeed" value="1">
                                <i></i>是
                            </label>
                        </div>
                    </div>
                </div>
                <div class="information-limit">
                    <h5>预订限制</h5>
                    <div class="limit-txt">
                        70周岁以上出行人需确保身体健康适宜旅游。本次旅游活动中，若由此造成任何人身以为及不良后果，均由出行人承担全部责任。<br>
                        1、根据天海邮轮公司的规定，乘坐邮轮旅行的婴儿必须在邮轮启航第一天时至少满6个月。<br>
                        2、天海邮轮公司规定，将不接受在航程开始时或航程进行中，会进入或已进入怀孕第24周的孕妇游客的预订申请。未超过24周的孕妇报名此行程，请提供医生开具的允许登船的证明并填写健康问讯表，允许登船证明请随身携带，健康问讯表填写签字后登船当天交予带团领队。 <br>
                        3、天海邮轮公司规定，邮轮启航当天18周岁以下的乘客为未成年人，必须确保每个船舱中，至少有1位乘客的年龄在18周岁以上；被监护人尽可能与监护人入住同一船舱，否则船方将实际情况保留限制未成年人登船的权利。若未成年人不随其父母一起登船出行，必须要提供以下资料：<br>
                        1）其父母及随行监护人必须填写“授权声明信与随行监护人承诺”，请打印该附件并签字携带。<br>
                        2）未成年人的出生证复印件或有父母和孩子信息页面的户口簿复印件。<br>
                        3）如果陪同出行的成年人非未成年人的父母，而是其法定监护人，则必须出示相关的“法定监护证明”。<br>
                        4）以上所有文件请未成年游客随身携带，办理登船手续时必须出示，否则船方可能拒绝该人登船。
                    </div>
                </div>
                <div class="information-pay clearfix">
                    <a href="#" class="pay-prev pull-left"> &lt; 上一步 </a>
                    <div class="pay-next pull-right">
                        <label class="pay-price totalPrice" name="${totalPrice}">
                            总计：<span><i>¥</i>${totalPrice}</span>
                        </label>
                        <a href="javascript:;" class="pay-btn" onclick="customerInformation.saveCustomerInfo()">去支付</a>
                    </div>
                </div>
                <div class="information-contract">
                    <div class="contract-header clearfix">
                        <h5 class="pull-left">旅游合同</h5>
                        <label class="contract-agree active pull-right">
                            <input type="checkbox" name="contractIsAgree" value="1" checked="">
                            <i></i>我已阅读并同意以下条款
                        </label>
                    </div>
                    <div class="contract-txt">
                        第一条 特别提示<br>
                        上海携程国际旅行社有限公司（以下简称上海携程）、北京携程国际旅行社有限公司（以下简称北京携程）、广州携程国际旅行社有限公司（以下简称广州携程）、成都携程国际旅行社有限公司（以下简称成都携程）、深圳携程国际旅行社有限公司（以下简称深圳携程）、上海携程国际旅行社有限公司南京分公司（以下简称南京携程）、上海携程国际旅行社有限公司杭州分公司（以下简称杭州携程）、成都携程国际旅行社有限公司重庆分公司（以下简称重庆携程），为携程计算机技术上海有限公司（以下简称携程）的战略合作伙伴，上海携程、北京携程、广州携程、成都携程充分借用携程旅行网的散客服务预订平台，联合其在深圳、杭州、南京、武汉、厦门、青岛、沈阳、重庆等城市的有出境资质的合作旅行社，在携程网络平台上推出全方位的海外中高端休闲旅游度假旅行产品。<br>
                        上海携程国际旅行社有限公司<br>
                        企业类型：有限责任公司（国内合资）<br>
                        国际旅行社业务许可证编号：L-SH-CJ00025<br>
                        经营范围：入境旅游业务，国内旅游业务；旅游规划及开发，旅游产品开发；出境旅游业务。<br>
                        保险兼业代理业务许可证机构编码：31011073900651502<br>
                        法定代表人：范敏<br>
                        上海市旅游局质量监督所电话：021-64393615<br>
                        北京携程国际旅行社有限公司<br>
                        企业类型：有限责任公司<br>
                        第一条 特别提示<br>
                        上海携程国际旅行社有限公司（以下简称上海携程）、北京携程国际旅行社有限公司（以下简称北京携程）、广州携程国际旅行社有限公司（以下简称广州携程）、成都携程国际旅行社有限公司（以下简称成都携程）、深圳携程国际旅行社有限公司（以下简称深圳携程）、上海携程国际旅行社有限公司南京分公司（以下简称南京携程）、上海携程国际旅行社有限公司杭州分公司（以下简称杭州携程）、成都携程国际旅行社有限公司重庆分公司（以下简称重庆携程），为携程计算机技术上海有限公司（以下简称携程）的战略合作伙伴，上海携程、北京携程、广州携程、成都携程充分借用携程旅行网的散客服务预订平台，联合其在深圳、杭州、南京、武汉、厦门、青岛、沈阳、重庆等城市的有出境资质的合作旅行社，在携程网络平台上推出全方位的海外中高端休闲旅游度假旅行产品。<br>
                        上海携程国际旅行社有限公司<br>
                        企业类型：有限责任公司（国内合资）<br>
                        国际旅行社业务许可证编号：L-SH-CJ00025<br>
                        经营范围：入境旅游业务，国内旅游业务；旅游规划及开发，旅游产品开发；出境旅游业务。<br>
                        保险兼业代理业务许可证机构编码：31011073900651502<br>
                        法定代表人：范敏<br>
                        上海市旅游局质量监督所电话：021-64393615<br>
                        北京携程国际旅行社有限公司<br>
                        企业类型：有限责任公司<br>
                        第一条 特别提示<br>
                        上海携程国际旅行社有限公司（以下简称上海携程）、北京携程国际旅行社有限公司（以下简称北京携程）、广州携程国际旅行社有限公司（以下简称广州携程）、成都携程国际旅行社有限公司（以下简称成都携程）、深圳携程国际旅行社有限公司（以下简称深圳携程）、上海携程国际旅行社有限公司南京分公司（以下简称南京携程）、上海携程国际旅行社有限公司杭州分公司（以下简称杭州携程）、成都携程国际旅行社有限公司重庆分公司（以下简称重庆携程），为携程计算机技术上海有限公司（以下简称携程）的战略合作伙伴，上海携程、北京携程、广州携程、成都携程充分借用携程旅行网的散客服务预订平台，联合其在深圳、杭州、南京、武汉、厦门、青岛、沈阳、重庆等城市的有出境资质的合作旅行社，在携程网络平台上推出全方位的海外中高端休闲旅游度假旅行产品。<br>
                        上海携程国际旅行社有限公司<br>
                        企业类型：有限责任公司（国内合资）<br>
                        国际旅行社业务许可证编号：L-SH-CJ00025<br>
                        经营范围：入境旅游业务，国内旅游业务；旅游规划及开发，旅游产品开发；出境旅游业务。<br>
                        保险兼业代理业务许可证机构编码：31011073900651502<br>
                        法定代表人：范敏<br>
                        上海市旅游局质量监督所电话：021-64393615<br>
                        北京携程国际旅行社有限公司<br>
                        企业类型：有限责任公司<br>
                    </div>
                </div>
            </div>
            <div class="information-right pull-right">
                <div class="information-order">
                    <div class="order-header clearfix">
                        <span class="pull-left">总计：</span>
                        <span class="price pull-right"><sub>¥</sub>${totalPrice}</span>
                    </div>
                    <table cellspacing="0">
                        <thead>
                        <tr>
                            <th class="th01">舱房及包含产品</th>
                            <%--<th class="th02"></th>
                            <th class="th03">¥2345</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="cruiseshipOrderRoomRequests" var="cruiseshipOrderRoomRequest">
                        <tr>
                            <td>${cruiseshipOrderRoomRequest.roomName}${cruiseshipOrderRoomRequest.adultNum}成人${cruiseshipOrderRoomRequest.childNum}儿童)</td>
                            <td class="text-center roomNumb" number=${cruiseshipOrderRoomRequest.roomNum}>${cruiseshipOrderRoomRequest.roomNum}间</td>
                            <td class="eachOfPrice" price="${cruiseshipOrderRoomRequest.price}">¥${cruiseshipOrderRoomRequest.price}/人</td>
                        </tr>
                        </s:iterator>
                        <%--<tr>
                            <td>A线休闲购物旅游</td>
                            <td class="text-center">3份</td>
                            <td>已含</td>
                        </tr>--%>
                        <%--<tr>
                            <td>立减优惠</td>
                            <td class="text-center">3份</td>
                            <td>-2345</td>
                        </tr>--%>
                        </tbody>
                    </table>
                </div>
                <div class="order-operation clearfix">
                    <a href="#" class="operation-contact pull-left"><i></i>联系客服</a>
                    <a href="#" class="operation-save pull-right" onclick="customerInformation.saveCustomerInfo()"><i></i>保存订单</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/cruiseship/jquery.min.js"></script>
<script type="text/javascript" src="/js/cruiseship/customer_information.js"></script>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</body></html>
<%@include file="../../yhypc/public/footer.jsp"%>