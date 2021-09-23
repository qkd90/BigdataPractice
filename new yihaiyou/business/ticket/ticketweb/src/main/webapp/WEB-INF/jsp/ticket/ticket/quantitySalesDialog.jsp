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
    <link href="/css/iconfont/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/ticket/quantitySalesDialog.js"></script>

    <base href="<%=basePath%>">
    <title>用户管理</title>

</head>
<body style="background-color: white;">
<style type="text/css">
    .q_header {
        width: 100%;
        border-bottom: 1px dotted #ccc;
        border-left: 1px dotted #ccc;
        border-right: 1px dotted #ccc;
        height: 25px;
        background-color: #F4F4F4;
    }
    .q_row {
        width: 100%;
        border: 1px dotted #ccc;
        height: 25px;
    }
    .q_table {
        text-align: center;
        width: 100%;
        border: 1px;
    }
    .q_table tr td{
        width: 30px;
        border: 1px dotted #ccc;
        height: 25px;
    }
    .col_btn {
        color: #FB3C02;
        cursor: pointer;
    }
    .input_quantity {
        width: 80px;
        height: 23px;
        text-align: center;
        border-radius: 5px 5px 5px 5px;
        border: 1px solid #95B8E7;
    }
</style>

    <div style="padding: 15px;">
        <form id="quantity_form_id">
            <input type="hidden" id="hpt_ticketId" name="ticketPriceId" value="${ticketPrice.id}">
            <input type="hidden" id="hpt_quantitySalesId" name="quantitySalesId" value="${quantitySales.id}">
            <div style="font-size: 16px;font-weight: 700; margin-bottom: 10px;">
                <label>产品名称：</label>
                <label>${ticketPrice.ticket.name}</label>
            </div>
            <div style="font-size: 16px;font-weight: 700; margin-bottom: 10px;">
                <label>票种类型：</label>
                <label>${ticketPrice.name}</label>
            </div>
            <div style="font-size: 16px;font-weight: 700; margin-bottom: 10px;">
                <label>拱量对象：</label>
                <input id="q_flag" type="hidden" value="${quantitySales.flag}">
                <label><input type="radio" name="flag" value="global">全局</label>
                <label><input type="radio" name="flag" checked="checked" value="parts">公司</label>
            </div>
            <div style="font-size: 16px;font-weight: 700; margin-bottom: 10px;">
                <label>拱量时间期间：</label>
                <label>
                    <input class="easyui-datetimebox" name="q_startTime"
                           data-options="showSeconds:false,editable:false" style="width:140px" value="${quantitySales.startTime}">
                    <span style="margin-left: 5px;margin-right: 5px;">-</span>
                    <input class="easyui-datetimebox" name="q_endTime"
                           data-options="showSeconds:false,editable:false" style="width:140px" value="${quantitySales.endTime}">

                <%--<input  class="easyui-combobox" id="validity" style="width: 120px;"--%>
                            <%--data-options="--%>
                                    <%--prompt:'请选择拱量时间',--%>
                                    <%--<%--'no_limit','twelve','six','three','one'--%>
                                    <%--editable:false,--%>
                                    <%--valueField: 'id',--%>
                                    <%--textField: 'value',--%>
                                    <%--data: [--%>
                                    <%--{--%>
                                        <%--id: 'one',--%>
                                        <%--value: '近一个月',--%>
                                        <%--selected:true--%>
                                    <%--},{--%>
                                        <%--id: 'three',--%>
                                        <%--value: '近三个月'--%>
                                    <%--},{--%>
                                        <%--id: 'six',--%>
                                        <%--value: '近半年'--%>
                                    <%--},{--%>
                                        <%--id: 'twelve',--%>
                                        <%--value: '近一年'--%>
                                    <%--},{--%>
                                        <%--id: 'no_limit',--%>
                                        <%--value: '无限制'--%>
                                    <%--}]"  />--%>
                </label>
            </div>
            <div style="font-size: 16px;font-weight: 700; margin-bottom: 10px;">
                <label>拱量方式：</label>
                <input id="q_type" type="hidden" value="${quantitySales.type}">
                <label><input type="radio" name="type" checked="checked" value="money">优惠金额</label>
                <%--<label><input type="radio" name="type" checked="checked" value="percent">折价比</label>--%>
            </div>
            <div>
                <div id="q_center" style="width: 330px;height: 190px;">
                    <input type="hidden" id="hipt_rowsJsonStr" name="quantitySalesStr">
                </div>
            </div>
        </form>
    </div>
    <div style="width: 100%; height: 30px; position: absolute; bottom: 0px;">
        <div style="float:right;">
            <a href="javascript:void(0)"  style="width: 100px;"  class="easyui-linkbutton"  onclick="QuantitySales.saveQuantitySales()">保存</a>
            <a href="javascript:void(0)"  style="width: 100px;"  class="easyui-linkbutton"  onclick="QuantitySales.cancelQuantitySales()">关闭</a>
        </div>
    </div>

    <div id="quantity_dialog" class="easyui-dialog"
         data-options="closed:true," style="left:0px;top:10px;width: 350px; height: 300px;">
        <div style="padding:0px;">
            <table class="q_table">
                <thead>
                <tr class="q_header">
                    <td>数量</td>
                    <td>优惠金额</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody class="q_tbody">
                </tbody>
            </table>
            <%--<div id="quantity_dg"></div>--%>
        </div>
    </div>
</body>
</html>