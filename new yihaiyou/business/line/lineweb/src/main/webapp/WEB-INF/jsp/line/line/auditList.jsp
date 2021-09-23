<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路审核</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script>
<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
<script type="text/javascript" src="/js/line/line/auditList.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">
					<input id="qry_lineType" style="width:120px;"/>
					<input id="qry_productAttr" style="width:120px;"/>
					<select class="easyui-combobox" id="qry_customType" style="width:130px;">
						<option value="">自定义分类（不限）</option>
						<c:forEach items="${linecategorgs}" var="categorg">
							<option value="${categorg.id}">${categorg.name}</option>
						</c:forEach>
					</select>
					<input id="qry_source" style="width:120px;"/>
					<input id="qry_buypay" style="width:120px;"/>
					<input id="qry_lineStatus" style="width:120px;"/>
					<input id="qry_keyword" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
					<a href="javascript:void(0)" id="search" class="easyui-linkbutton"  onclick="auditList.doSearch()">查询</a>
				</form>
			</div>
			<div style="padding:2px 5px;">
		        <a href="javascript:void(0)" onclick="auditList.doBatchThrough()" class="easyui-linkbutton" >通过</a>
		        <a href="javascript:void(0)" onclick="auditList.doBatchUnThrough()" class="easyui-linkbutton" >不通过</a>
				<a href="javascript:void(0)" onclick="auditList.doBatchShow()" class="easyui-linkbutton" >上架</a>
				<a href="javascript:void(0)" onclick="auditList.doBatchHide();" class="easyui-linkbutton" >下架</a>
			</div>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>	

	<!-- 审核原因窗口 -->
	<div id="reasonDg" title="审核原因" class="easyui-dialog" style="width:300px;height:180px;padding:5px 5px 5px 5px"
	          closed="true" modal="true" >
	    <table>
	    	<tr>
	    		<td>
					<input id="lineId" name="lineId" type="hidden"/>
	    			<input class="easyui-textbox" id="reason" name="reason" data-options="multiline:true,validType:'maxLength[150]'" style="height:100px;width:276px;"></input>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="auditList.doUnThroughDg()" style="margin-top: 5px;">确认</a>
	    		</td>
	    	</tr>
	    </table>
	</div>
</body>
</html>