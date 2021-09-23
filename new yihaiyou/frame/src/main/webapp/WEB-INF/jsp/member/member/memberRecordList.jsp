<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>会员消费记录详情</title>

</head>
<body>
<table id="memberRecordTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[15,30,50],rownumbers:true,singleSelect:false,url:'/member/memberRecord/searchByMemberId.jhtml?member.id=${member.id}',toolbar:'#memberRecordTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'sale.saleNo',formatter:function(value,row){if(row.sale!=null){return row.sale.saleNo;}else{return value;}}" width="150" align="center">单据编号</th>
			<th data-options="field:'member.realName',formatter:function(value,row){if(row.member!=null){return row.member.realName;}else{return value;}}" width="120" align="center">会员姓名</th>
			<th data-options="field:'sale.createTime',formatter:function(value,row){if(row.sale!=null){return row.sale.createTime;}else{return value;}}" width="150" align="center">消费日期</th>
			<th data-options="field:'product.productName',formatter:function(value,row){if(row.product!=null){return row.product.productName;}else{return value;}}" width="100" align="center">商品名称</th>
			<th data-options="field:'productNum',formatter:function(value){if(value!=null){return value.toFixed(4);}}" width="100" align="center">销售数量</th>
			<th data-options="field:'product.productUnit',formatter:function(value,row){if(row.product!=null){return row.product.productUnit;}else{return value;}}" width="100" align="center">商品单位</th>
			<th data-options="field:'discountPrice',formatter:function(value){if(value!=null){return value.toFixed(2)+' 元';}}" width="100" align="center">折后金额</th>
		</tr>
	</thead>
</table>
<div id="memberRecordTool" style="padding: 5px; height: auto">
	单据编号：<input type="text" id="recordInfo_saleNo" style="width: 180px;line-height:20px;border:1px solid #ccc"></input>
	消费日期：<select id="recordInfo_saleTime" onchange="changeState(this.value);">
			<option value="全部">全部</option>
			<option value="今天">今天</option>
			<option value="本周">本周</option>
			<option value="本月">本月</option>
			<option value="自定义">自定义</option>
		   </select>
	<input id="recordInfo_startTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<input id="recordInfo_zhi" value="至" style="width:11px;border:0px;display: none;" readonly="readonly"/>
	<input id="recordInfo_endTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		 onclick="memberRecordQuery();">查询</a>
	<%-- <br/>
	<a href="javascript:void(0)" 
		onclick="refreshRecords()" class="easyui-linkbutton"  >刷新</a>
	<table style="width: 50%;line-height:30px;float: right;border: 1px solid #ccc;color: red">
		<tr>
			<td><strong>合计</strong></td>
			<td style="width:80px;">数量合计：</td>
			<td><span id="totalqty">0</span>件</td>
			<td style="width:80px;">金额合计：</td>
			<td><span id="totalamount">0</span>元</td>
		</tr>
	</table> --%>
</div>


<script type="text/javascript">
	
	//选择消费日期时，当值为自定义时，弹出隐藏框
	function changeState(val){
		if(val=='自定义'){
			$("#recordInfo_startTime").show();
			$("#recordInfo_endTime").show();
			$("#recordInfo_zhi").show();
			
		}else{
			$("#recordInfo_startTime").hide();
			$("#recordInfo_endTime").hide();
			$("#recordInfo_zhi").hide();
		}
	}
	//刷新纪录表
	function refreshRecords(){
		$('#memberRecordTable').datagrid('reload');
	}

	// 查询条件
	function memberRecordQuery() {
		$('#memberRecordTable').datagrid("load", {
			'memberRecord.sale.saleNo' : $('#recordInfo_saleNo').val(),
			'memberRecord.saleTime' : $("select[id=recordInfo_saleTime]").find("option:selected").val(),
			'memberRecord.startTime' : $('#recordInfo_startTime').val(),
			'memberRecord.endTime' : $('#recordInfo_endTime').val()
		});
	}
	

</script>

</body>
</html>