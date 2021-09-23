<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript">
        var FG_DOMAIN = '<s:property value="fgDomain"/>';
    </script>
    <link href="/css/ticket/form.css" rel="stylesheet" type="text/css">
    <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
    <script type="text/javascript" src="/js/sailboat/ticketUtil.js"></script>
    <script type="text/javascript" src="/js/sailboat/ticketView.js"></script>
    <base href="<%=basePath%>">
    <title>${ticket.name}</title>
</head>
<body>
<input type="hidden" id="ticket_area" name="cityId" value="${ticket.cityId}"/>
<input id="ticket_agentId" type="hidden" value="${ticket.agent}"/>
<input id="ticketId" type="hidden" name="ticketId" value="${ticket.id}"/>
<style type="text/css">
    .text {
        font-size: 13px;
        color: rgb(2, 48, 97);
    }

    .first {
        font-weight: 900;
        /*color:grey;*/
    }
    .second {
        font-size: 12px;
        /*color:grey;*/
    }

    .calender_class {
        padding-right: 20px;
        border: #999 1px solid;
        width: 56px;
        display: block;
        margin-left: -4px;
        height: 24px;
        background: url("/jquery-easyui-1.4.1/themes/icons/date.png") no-repeat scroll center center transparent;
    }
</style>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',split:true">
        <a class="easyui-linkbutton" style="position: fixed;top: 10px; right: 20px;"
           data-options="iconCls:'icon-back'" onclick="TicketView.tabClose();">返回</a>
        <!--表单区域开始-->
        <input type="hidden" id="type_ticket" value="${ticket.ticketType}">
        <input type="hidden" id="ticket_agent" value="${ticket.agent}"/>
        <div class="row">
            <div class="first">船票类别：</div>
            <div class="second">
                <a href="javascript:;" class="radio_tag " id="yachtTicket">游艇船票</a>
                <a href="javascript:;" class="radio_tag " id="boatTicket">帆船船票</a>
            </div>
        </div>

        <div class="row" id="div_jd_name">
            <div class="first">
                船票名称：
            </div>
            <div id="div1_sce" class="second">
                ${ticket.ticketName}
            </div>
        </div>
        <div class="row" id="div_jd_area">
            <div class="first">
                所属地区：
            </div>
            <div class="second">
                <span id="span_area1"></span>

            </div>
        </div>
        <div class="row" id="div_jd_aji">
            <div class="first">
                等级：
            </div>
            <div class="second">
                <span id="sce_span_aji"></span>
                <div id="sce_div_aji">
                    ${ticket.sceAji}
                </div>
            </div>
        </div>
        <div class="row" id="div_jd_address">
            <div class="first">
                地址：
            </div>
            <div class="second">
                <span id="sce_span_address"></span>
                <div id="sce_div_address">
                    ${ticket.address}
                </div>
            </div>
        </div>

        <!--演出类别选中后展现 -->
        <div class="row" id="div_yc_name" style="display: none">
            <div class="first">
                演出名称：
            </div>
            <div class="second">
                ${ticket.ticketName}
            </div>
        </div>
        <div class="row" id="div_yc_area" style="display: none">
            <div class="first">
                所属地区：
            </div>
            <div class="second">
                <span id="span_area2"></span>
            </div>
        </div>
        <div class="row" id="div_yc_time" style="display: none">
            <div class="first">
                演出时间：
            </div>
            <div class="second">
                ${ticket.startTime}
            </div>
        </div>
        <div class="row" id="div_yc_address" style="display: none">
            <div class="first">
                演出地点：
            </div>
            <div class="second">
                ${ticket.address}
            </div>
        </div>

        <!--船票类别选中后展现 -->
        <div class="row" id="div_cz_area" style="display: none">
            <div class="first">
                所属地区：
            </div>
            <div class="second">
                <span id="span_area3"></span>
            </div>
        </div>
        <div class="row" id="div_cz_name" style="display: none">
            <div class="first">
                船只名称：
            </div>
            <div class="second">
                ${ticket.ticketName}
            </div>
        </div>
        <div class="row" id="div_cz_time" style="display: none">
            <div class="first">
                出发日期：
            </div>
            <div class="second">
                ${ticket.startTime}
            </div>
        </div>
        <div class="row_hd">以下自定义内容只显示在自己网店内：</div>
        <div class="accordion_child1">
            <div class="first">产品标题：</div>
            <div class="second">
                ${ticket.name}
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">自定义分类：</div>
            <div class="second">
                <input type="hidden" value="${ticket.category}" id="categoryId">
                <span id="ticket_category"></span>
                <%--<select disabled="disabled" class="easyui-combobox" data-options="prompt:'自定义分类'" style="width: 193px"--%>
                <%--id="search_classify" name="ticket.category" value="">--%>
                <%--<!-- 								<option>自定义分类</option> -->--%>
                <%--</select>--%>
                <%--${ticket.category}--%>
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                游客需提前：
            </div>
            <div class="second">
                ${ticket.preOrderDay}天预定,指定购买日期后${ticket.validOrderDay}天内有效
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">描述图：</div>
            <div class="second">
                <%--F:\idea\workspace\data\projects\fengxiao\src\main\webapp\images\no_img.jpg--%>
                <%--<img src="\images\no_img.jpg" width="200" height="150" style="border-radius: 5px;"/>--%>
                <img src="${ticket.ticketImgUrl}" width="200" height="150" style="border-radius: 5px;"/>
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                温馨提示：
            </div>
            <div class="second" id="div_kindRuyuan">
                ${ticketExplain.enterDesc }
            </div>
        </div>
        <div class="accordion_child1" style="display: none;">
            <div class="first">
                退改规则：
            </div>
            <div class="second" id="div_kindTuigai">
                ${ticketExplain.rule}
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                特惠政策：
            </div>
            <div class="second" id="div_kindprivilege">
                ${ticketExplain.privilege }
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">产品详情：</div>
            <div class="second">
                <%--${ticketExplain.proInfo}--%>
                <textarea readonly="true" disabled="disabled" id="kindXiangqing" name="proInfo"
                          style="width: 700px; height: 300px; ">${ticketExplain.proInfo}</textarea>
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">排序：</div>
            <div class="second">
                ${ticket.showOrder}
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                船票票报价：
            </div>
            <div class="second">
                <table id="qryResult" style="width:650px;height:300px"></table>
            </div>
            <%--<div id="ticketNo_list" style="margin-left: 115px;padding-top: 5px;width:620px;height:300px;">--%>
            <%--<table id="dg_ticketList"></table>--%>
            <%--</div>--%>
            <div id="sel_startTime" class="easyui-dialog" title="价格日历" style="width:350px;height:450px;"
                 data-options="resizable:true,modal:true,closed:true">
                <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"
                        style="width:100%;height:95%;"></iframe>
                <input type="hidden" id="hipt_startTime_index"/>
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                付款方式：
            </div>
            <div class="second">
                <c:if test='${ticket.payway.equals("offlinepay")}'>线下支付</c:if>
                <c:if test='${ticket.payway.equals("scenicpay")}'>景区现付</c:if>
                <c:if test='${ticket.payway.equals("allpay")}'>全额支付</c:if>
            </div>
        </div>
        <div class="accordion_child1">
            <div class="first">
                是否确认：
            </div>
            <div class="second">
                <c:if test='${ticket.orderConfirm.equals("confirm")}'>是</c:if>
                <c:if test='${ticket.orderConfirm.equals("noconfirm")}'>否</c:if>
            </div>
        </div>

        <div style="margin-left: 115px;">
            <!--<a href="javascript:;" class="easyui-linkbutton" id="add_pic_toEditor"
               style="width: 130px;" onclick="TicketView.tabClose()">完成</a>-->
        </div>
    </div>
</div>
</div>
</body>
</html>