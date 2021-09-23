<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>第二步</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript">
        var FG_DOMAIN = '<s:property value="fgDomain"/>';
    </script>
    <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
    <link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css'/>
    <link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet'/>
    <link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print'/>
    <script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
    <script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
    <script src='/fullcalendar-2.4.0/lang-all.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
    <script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
    <script type="text/javascript" src="/js/line/line/viewStep2.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px;">
        <input id="priceId" name="priceId" type="hidden" value="<s:property value="linetypeprice.id"/>"/>
        <input id="productId" name="productId" type="hidden" value="<s:property value="productId"/>"/>
        <div id="typePriceDate"></div>
        <table>
            <%--<tr>--%>
            <%--<td class="label"><font color="red">*&nbsp;</font>价格类型名称:</td>--%>
            <%--<td>--%>
            <%--<input class="easyui-textbox" id="quoteName" name="quoteName" value="<s:property value="linetypeprice.quoteName"/>" style="width:200px;" required="true" data-options="validType:'maxLength[50]'">--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>费用说明:</td>
                <td>
                    <textarea id="quoteDescK" name="quoteDesc"
                              style="width:680px; height:120px; visibility: hidden;"><s:property
                            value="linetypeprice.quoteDesc"/></textarea>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>线路报价:</td>
                <td>
                    <div class="price-set" style="display: none">
                        <div>
                            <span><font color="red">*</font>指定时间段：</span>
                            ${dateStartStr }
                            <input id="dateStart" type="text" class="Wdate"
                                   onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',maxDate:'#F{$dp.$D(\'dateEnd\')}'})"
                                   style="width:100px;" value="${dateStartStr }"/>
                            <span>至</span>
                            ${dateEndStr }
                            <input id="dateEnd" type="text" class="Wdate"
                                   onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateStart\')}',maxDate:'%y-{%M+6}-%ld'})"
                                   style="width:100px;" value="${dateEndStr }"/>
                            <a href="javascript:void(0)" onclick="editStep2.doAddPriceDate()"
                               class="easyui-linkbutton line-btn" data-options=""
                               style="margin-left: 50px;">添加</a>
                            <!--<a href="javascript:void(0)" onclick="editStep2.doClearPriceDate()" class="easyui-linkbutton line-btn" data-options="" style="margin-left: 10px;">清除所有报价</a>-->
                        </div>
                        <div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value=""/><span class="ck-label">天天发团</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="1"/><span class="ck-label">周一</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="2"/><span class="ck-label">周二</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="3"/><span class="ck-label">周三</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="4"/><span class="ck-label">周四</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="5"/><span class="ck-label">周五</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="6"/><span class="ck-label">周六</span></div>
                            <div class="ck-div"><input disabled="disabled" type="checkbox" class="left-block"
                                                       name="weekday"
                                                       value="0"/><span class="ck-label">周日</span></div>
                        </div>
                        <div class="clr-float">
                            <fieldset class="fieldset">
                                <legend><font color="red">*</font>成人</legend>
                                <span>分销价：</span>
                                <input disabled="disabled" class="easyui-numberbox" id="discountPrice"
                                       name="discountPrice"
                                       style="width:80px;" data-options="min:0,precision:2">
                                <span>佣金：</span>
                                <input disabled="disabled" class="easyui-numberbox" id="rebate" name="rebate"
                                       style="width:80px;" data-options="min:0,precision:2">
                            </fieldset>
                            <fieldset class="fieldset">
                                <legend>儿童</legend>
                                <span>分销价：</span>
                                <input disabled="disabled" class="easyui-numberbox" id="childPrice" name="childPrice"
                                       style="width:80px;" data-options="min:0,precision:2">
                                <span>佣金：</span>
                                <input disabled="disabled" class="easyui-numberbox" id="childRebate" name="childRebate"
                                       style="width:80px;" data-options="min:0,precision:2">
                            </fieldset>
                        </div>
                    </div>
                    <div style="width: 680px;margin-top: 10px;">
                        <div id='calendar'></div>
                    </div>
                    <%--<div style="width: 680px;">--%>
                    <%--<div id='calendar2'></div>--%>
                    <%--</div>--%>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
        <div style="text-align:left;padding:5px;height:30px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="history.back()">返回</a>
        </div>
    </div>
</div>

</body>
</html>
