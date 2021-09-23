<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">

<title>会员消费记录管理</title>
<%@ include file="../../common/common141.jsp"%>


</head>
<body>
<table id="memberRecordsTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'/member/memberRecord/searchList.jhtml',toolbar:'#memberRecordsTool',pagination:true">
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
			<c:if test="${session.loginuser.roleid=='1'}">
				<th data-options="field:'shopSimpleName'" width="80" align="center">所属店铺</th>
			</c:if>
		</tr>
	</thead>
</table>
<div id="memberRecordsTool" style="padding: 5px; height: auto">
	单据编号：<input type="text" id="record_saleNo" style="width: 180px;line-height:20px;border:1px solid #ccc"></input>
	会员姓名：<input type="text" id="record_memberName" style="width: 150px;line-height:20px;border:1px solid #ccc"></input> 
	手机号码：<input type="text" id="record_memberPhone" style="width: 150px;line-height:20px;border:1px solid #ccc"></input>
	消费日期：<select id="record_saleTime" onchange="changeRecordState(this.value);">
			<option value="全部">全部</option>
			<option value="今天">今天</option>
			<option value="本周">本周</option>
			<option value="本月">本月</option>
			<option value="自定义">自定义</option>
		   </select>
	<input id="startTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<input id="zhi" value="至" style="width:11px;border:0px;display: none;" readonly="readonly"/>
	<input id="endTime" onclick="WdatePicker();" style="line-height:22px;border:1px solid #ccc;display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton"
		 onclick="memberRecordsQuery();">查询</a>
	<br/>
	<!-- <a href="javascript:void(0)" 
		onclick="deletemembers()" class="easyui-linkbutton"  >删除</a> -->
	<a href="javascript:void(0)" class="easyui-linkbutton" 
		 onclick="reloadMemberRecordsTable();">刷新</a>
	<table style="width: 50%;line-height:30px;float: right;border: 1px solid #ccc;color: red">
		<tr>
			<td><strong>合计</strong></td>
			<td style="width:80px;">数量合计：</td>
			<td><span id="totalqty">${totalqty}</span>件</td>
			<td style="width:80px;">金额合计：</td>
			<td><span id="totalamount">${totalamount}</span>元</td>
		</tr>
	</table>
</div>
<script type="text/javascript">

	//选择消费日期时，当值为自定义时，弹出隐藏框
	function changeRecordState(val){
		if(val=='自定义'){
			$("#startTime").show();
			$("#endTime").show();
			$("#zhi").show();
			
		}else{
			$("#startTime").hide();
			$("#endTime").hide();
			$("#zhi").hide();
		}
	}
	
	// 批量删除
	function deletemembers() {
		var rows = $('#memberTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的会员信息！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager.confirm('温馨提示', '确定要删除选中的会员信息吗？', function(r) {
				if (r) {
					$.get('/member/member/deleteMembers.jhtml', {
						'ids' : ids
					}, function(result) {
						if (result.success) {
							$.messager.show({ 
								title : '温馨提示',
								msg : '成功删除会员信息！'
							});
							$('#memberTable').datagrid("reload");
							$('#memberTable').datagrid('clearSelections');
						} else {
							$.messager.show({ 
								title : '温馨提示',
								msg : result.errorMsg
							});
						}
					}, 'json');
				}
			});
		}
	}
	
	//刷新
	function reloadMemberRecordsTable(){
		$('#memberRecordsTable').datagrid("reload");
		$('#memberRecordsTable').datagrid('clearSelections');
	}
	

	// 查询条件
	function memberRecordsQuery() {
		$('#memberRecordsTable').datagrid("load", {
			'memberRecord.sale.saleNo' : $('#record_saleNo').val(),
			'memberRecord.member.realName' : $('#record_memberName').val(),
			'memberRecord.member.phone' : $('#record_memberPhone').val(),
			'memberRecord.saleTime' : $("select[id=record_saleTime]").find("option:selected").val(),
			'memberRecord.startTime' : $('#startTime').val(),
			'memberRecord.endTime' : $('#endTime').val()
		});
	}
	

</script>
</body>
</html>