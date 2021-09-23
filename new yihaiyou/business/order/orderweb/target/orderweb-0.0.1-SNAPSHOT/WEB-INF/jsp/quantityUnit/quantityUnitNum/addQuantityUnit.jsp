<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商维护</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<script type="text/javascript" src="/js/quantityUnitNum/addQuantityUnit.js"></script>
	
	<style type="text/css">
		.td {
			float: left;
			margin-right: 15px;
		}
		.tr {
			margin-top: 5px;
			margin-bottom: 10px;
		}
		
	</style>
</head>
<body style="margin:0;padding:15px;background-color: white;">

	<div>
		<div class="tr">
			<div class="td">
				<label style="font-size: 16px;">公司串码：</label>
			</div>
			<div class="td" style="float: none;">
				<input type="text" id="ipt-unitIdentityCode" class="easyui-textbox" data-options="prompt:'请输入公司串码'" style="height:25px; width: 200px;">
			</div>
		</div>
		<div id="dealerUnitUnitDetail" style="display: none; width:100%;height:250px;padding:5px;">
			<div class="easyui-panel" title="查询结果" data-options="border:false" style="width:90%;height:200px;padding:0px;"
				 data-options="footer:'#ft'">
				<div style="margin-top: 5px; ">
					<div style="float: left; margin-right: 10px;">
						<label style="font-size: 16px;">公司名称：</label>
					</div>
					<div>
						<input type="hidden" id="dealurId" value="">
						<label id="dealurName" style="font-size: 16px;"></label>
					</div>
				</div>
				<div style="margin-top: 5px; ">
					<div style="float: left; margin-right: 10px;">
						<label style="font-size: 16px;">公司串码：</label>
					</div>
					<div>
						<label id="dealurIdentityCode" style="font-size: 16px;"></label>
					</div>
				</div>
				<div style="margin-top: 5px;">
					<div style="float: left; margin-right: 10px;">
						<label style="font-size: 16px;">拱量条件设置：</label>
					</div>
					<div>
						<input type="text" id="ipt-conditionNums" class="easyui-numberbox" data-options="prompt:'请输入大于0的数值', min:0, max:999999999" style="height:25px; width: 150px;">
					</div>
				</div>
			</div>
			<div id="ft" style="text-align: right;">
				<a href="#" class="easyui-linkbutton" onclick="AddQuntityUnit.addDealurUnit()" >确定添加</a>
				<a href="#" class="easyui-linkbutton" onclick="AddQuntityUnit.cancelAddDealurUnit()" >关闭</a>
			</div>
		</div>
	</div>




</body>
</html>
