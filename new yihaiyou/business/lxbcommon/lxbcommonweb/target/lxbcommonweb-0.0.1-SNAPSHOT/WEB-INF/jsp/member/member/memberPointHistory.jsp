<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>会员积分记录历史</title>
<%@ include file="../../common/common141.jsp"%>
</head>
<body>
<table id="memberPointHistoryTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[15,30,50],rownumbers:true,singleSelect:false,url:'/pointHistory/pointHistory/searchMemberPointHistory.jhtml?memberId=${member.id}',toolbar:'#memberPointHistoryTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'memberName',formatter:function(value,row){if(row.memberId!=null){return row.memberId.nickName;}else{return value;}}" width="100" align="center">会员名</th>
			<th data-options="field:'saleId',formatter:function(value,row){if(row.relate_saleId!=null){return row.relate_saleId.saleNo;}else{return value;}}" width="120" align="center">订单号</th>
			<th data-options="field:'saleTime',formatter:function(value,row){if(row.relate_saleId!=null){return row.relate_saleId.saleTime;}else{return value;}}" width="120" align="center">订单时间</th>
			<th data-options="field:'saleCount',formatter:function(value,row){if(row.relate_saleId!=null){return row.relate_saleId.saleCount;}else{return value;}}" width="60" align="center">订单数量</th>
			<th data-options="field:'closureCost',formatter:function(value,row){if(row.relate_saleId!=null){return row.relate_saleId.closureCost;}else{return value;}}" width="80" align="center">订单结算金额</th>
			<th data-options="field:'point',formatter:function(value,row){return row.point;}" width="120" align="center">订单赚取积分</th>
		</tr>
	</thead>
</table>
<div id="memberPointHistoryTool" style="padding: 5px; height: auto">
	消费日期：<select id="pointHistory_saleTime" onchange="changeState(this.value);">
			<option value="全部">全部</option>
			<option value="今天">今天</option>
			<option value="本周">本周</option>
			<option value="本月">本月</option>
			<option value="自定义">自定义</option>
		   </select>
	<input id="pointHistory_startTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<input id="pointHistory_zhi" value="至" style="width:11px;border:0px;display: none;" readonly="readonly"/>
	<input id="pointHistory_endTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="memberPointHistoryQuery();">查询</a>
	
</div>
<script type="text/javascript">
	
</script>

</body>
</html>