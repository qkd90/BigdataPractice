<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站点管理</title>
<%@ include file="../../common/common141.jsp"%>
<link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/scemanage/scemanage.css"/>
<script type="text/javascript" src="/js/scemanager/validateUtil.js"></script>
<script type="text/javascript" src="/js/scemanager/easy_excel.js"></script>
<script type="text/javascript" src="/js/scemanager/validatecode.js"></script>
	<style type="text/css">
		.calender_class {
			border: #999 1px solid;
			width: 117px;
			margin-left: -4px;
			height: 22px;
			background: url("/jquery-easyui-1.4.1/themes/icons/date.png") no-repeat scroll right center transparent;
			border-radius: 5px 5px 5px 5px;
			border: 1px solid #95B8E7;
		}
	</style>
</head>
<body style="background-color: white;">
	<div class="easyui-layout" data-options="fit:true"
         style="width:100%;height:100%;">
        	<!--查询区域 始 -->
			<div id="tb" style="padding: 10px;">
				<form action="" id="searchform">
					<div style="float: left;margin-right: 15px;">
						<input class="easyui-textbox"  id="ticNameid" style="width:250px;" data-options="prompt:'门票名称'" >
						<input class="easyui-textbox"  id="orderNum" style="width:150px;" data-options="prompt:'订单编号'" >
						<%--<input class="easyui-textbox"  id="orderUser" style="width:150px;" data-options="prompt:'订单用户'" >--%>
			        	<input type="hidden" id="hidden_used"  value="">
						<input id="com_used" class="easyui-combobox" style="width:100px;" data-options="
                            valueField: 'id',
                            textField: 'value',
                            prompt:'是否验证',
                            data: [{
                                    id: '1',
                                    value: '已验证'
                                },{
                                    id: '0',
                                    value: '未验证'
                                }
                                ,{
                                    id: '-1',
                                    value: '验证码无效'
                                }]" />

						<input class="easyui-datetimebox"  id="seaStartTime" style="width:150px;" data-options="prompt:'订单时间始'" >
						—
						<input class="easyui-datetimebox"  id="seaEndTime" style="width:150px;" data-options="prompt:'订单时间末'" >
					</div >
					<div style="margin-left: 10px;">
						<a href="javascript:void(0)" class="easyui-linkbutton"  id="search"  onclick="ValidateCode.doSearch()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="ValidateCode.clearForm()">重置</a>
					</div>
					<div style="text-align:left;margin-top: 5px;">
						<a id="addbtn1" href="#" onclick="ValidateCode.validatePerCode();"   class="easyui-linkbutton" >验证景点门票</a>
						<a id="addbtn2" href="#" onclick="ValidateCode.Save_Excel();"   class="easyui-linkbutton" >excel表格导出</a>
					</div>
				</form>

			</div>
			<!--查询区域 终  -->
        <div data-options="region:'center',border:false">
        	<!-- 数据表格 始 -->
			<table id="dg"></table>
			<!-- 数据表格 终-->
        </div>
    </div>
	
	
	
	<div class="easyui-dialog" id="validate_dialog" closed="true"  onClose="ValidateCode.clearForm()"  style="width:300px;top: 40px;">
	
		<form action="" id="ff">
			<div style="padding:10px;">
				<div style="margin-top: 10px;">
					<div style="float: left;">
						<label>验证码:</label>
						<input class="easyui-numberbox" id="input_validate" data-options="validType:'maxLength[5]'" style="width: 60px;"/>
					</div>
					<div>
						<label>&nbsp;&nbsp;票数:</label>
						<input class="easyui-numberbox" id="input_count" data-options="min:1,precision:0" style="width: 30px;"/>
						<label style="color: red;">(不填为全验)</label>
					</div>
					<div style=" margin-left: 100px;margin-top: 10px;">
						<a id="btn" onclick="ValidateCode.validate()" href="#" class="easyui-linkbutton" >提交</a>
					</div>
				</div>
			</div>
		</form>
	</div>

	<div class="easyui-dialog" id="detail_dialog" closed="true"  onClose="ValidateCode.clearForm()"  style="width:400px;top: 200px;">
		<form>
			<table id="td_detail" style="margin: 20px;border-bottom: 1px solid #DEDEDD;border-top: 1px solid #DEDEDD;width: 95%;margin: 10px 10px 50px 10px;">

			</table>
		</form>
	</div>
	<div id="editPanel2" class="easyui-dialog" title="门票验证详情"
		 data-options="resizable:false,modal:true,closed:true" style="padding:10px;">
		<div style="margin-bottom: 5px;">
			<label>
				产品名称：
			</label>
			<label id="proName">
			</label>
		</div>
		<div id="dg_validateInfo" style="width: 99%;height: 90%"></div>
	</div>
	<div id="editPanel3" class="easyui-dialog" title="验证记录导出<span style='color:red;font-size:12px;font-weight: 100;'>（日期范围不能超过三个月）</span>"
		 data-options="resizable:false,modal:true,closed:true" style="padding:10px;">
		<div style="padding: 5px;">
			<label style="float: left; margin-right: 10px;">起始时间：</label>
			<div><input class="calender_class" id="startTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endTime\',{M:-3})}',maxDate:'#F{$dp.$D(\'endTime\')}'})"></div>
		</div>
		<div style="padding: 5px;">
			<label style="float: left; margin-right: 10px;">截至时间：</label>
			<div><input class="calender_class" id="endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}', maxDate:'%y-%M-%d'})"></div>
		</div>

	</div>
</body>
</html>