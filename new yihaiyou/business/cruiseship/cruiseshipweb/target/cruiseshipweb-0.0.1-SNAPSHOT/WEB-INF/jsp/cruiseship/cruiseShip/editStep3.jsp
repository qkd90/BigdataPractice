<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第三步</title>
<%@ include file="../../common/common141.jsp"%>
    <script type="text/javascript">
        var FG_DOMAIN = '<s:property value="fgDomain"/>';
    </script>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseShip/addWizard.css">
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
	<script type="text/javascript" src="/js/cruiseship/cruiseShip/editStep3.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
        <form id="editForm" method="post">
		<input id="productId" name="productId" type="hidden" value="<s:property value="productId"/>"/>
        <div id="planDiv">
            <s:iterator value="cruiseShipPlans" status="status" var="plan">
            <table class="plan" id="plan_<s:property value="day"/>">
                <tr>
                    <td class="label dayLabel">第<span class="orange-bold"><s:property value="day"/></span>天:<input name="day" type="hidden" value="<s:property value="day"/>"/></td>
                    <td>
                        <input name="dayDesc" value="<s:property value="dayDesc"/>" style="width:360px;">
                        <span class="tip">最多<span class="green-bold">100</span>个字符</span>
                    </td>
                    <td width="80">
                        <a href="javascript:void(0)" class="delPlan" onClick="editStep3.doPlanDel(this)">删除行程</a>
                    </td>
                </tr>
                <tr>
                    <td class="label">停靠港口</td>
                    <td colspan="">
                        <input style="width:360px;" name="stopPort" value="<s:property value="stopPort"/>">
                    </td>
                </tr>
                <tr>
                    <td class="label"></td>
                    <td colspan="2">
                        <span style="line-height: 18px;">到达时间:</span>
                        <input style="width:100px;" name="arriveTime" value="<s:property value="arriveTime"/>">
                        <span style="line-height: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离开时间:</span>
                        <input style="width:100px;" name="leaveTime" value="<s:property value="leaveTime"/>">
                    </td>
                </tr>
                <tr>
                    <td class="label"><span style="color: red">*&nbsp;</span>行程安排:</td>
                    <td colspan="2">
                        <textarea name="arrange" style="width:600px; height:120px;" value=""><s:property value="arrange"/></textarea>
                        <span class="tip">已输入<span class="green-bold">0</span>个字符</span>
                    </td>
                </tr>
                <tr>
                    <td class="label"></td>
                    <td colspan="2">
                        <span style="line-height: 18px;">早餐:</span>
                        <input style="width:100px;" name="breakfast" value="<s:property value="breakfast"/>">
                        <span style="line-height: 18px;">&nbsp;&nbsp;午餐:</span>
                        <input style="width:100px;" name="lunch" value="<s:property value="lunch"/>">
                        <span style="line-height: 18px;">&nbsp;&nbsp;晚餐:</span>
                        <input style="width:100px;" name="supper" value="<s:property value="supper"/>">
                    </td>
                </tr>
                <tr>
                    <td class="label">住宿:</td>
                    <td colspan="2">
                        <input name="hotelName" value="<s:property value="hotelName"/>" style="width:360px;">
                    </td>
                </tr>
                <tr>
                    <td class="label"></td>
                    <td colspan="2"></td>
                </tr>
            </table>
            </s:iterator>
        </div>
        </form>
        <div style="text-align:left;margin:20px 20px 20px 180px;height:30px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep3.doPlanAdd()" style="width: 200px;">添加行程</a>
        </div>
		<div style="text-align:left;margin:20px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep3.nextGuide(4)">保存，下一步</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep3.nextGuide(5)">编辑完成</a>
		</div>
	</div>
</div>

</body>
</html>
