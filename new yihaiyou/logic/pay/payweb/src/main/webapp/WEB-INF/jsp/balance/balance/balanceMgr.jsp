<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户余额管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/balance/balanceMgr.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
		<!-- 表格工具条 始 -->
		<div id="tb">
			<div style="padding:2px 5px;">
				<form action="" id="searchform">
					<span>时间：</span>
                    <input id="qry_dateStart" type="text" class="easyui-datebox" style="width:120px;"/>
					<span>-</span>
                    <input id="qry_dateEnd" type="text" class="easyui-datebox" style="width:120px;"/>
                    <span>公司：</span>
                    <input id="qry_companyId" style="width:260px;">
					<span>状态：</span>
					<input id="qry_status" style="width:100px;">
					<a href="javascript:void(0)" id="show_search" class="easyui-linkbutton"  onclick="balanceMgr.doSearch()">查询</a>
				</form>
			</div>
			<div style="padding:2px 5px;">
				<a href="javascript:void(0)" onclick="balanceMgr.doOutlinerc()" class="easyui-linkbutton" >充值</a>
		        <a href="javascript:void(0)" onclick="balanceMgr.doWithdraw()" class="easyui-linkbutton">提现</a>
			</div>
		</div>
		<!-- 表格工具条 终 -->
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<table id="dg"></table>
		</div>
		<!-- 数据表格 终-->
	</div>	

	<!-- 资金操作窗口 始 -->
	<div id="balanceDg" title="资金操作" class="easyui-dialog" style="width:440px;height:180px;padding:10px 5px 5px 5px"
	          closed="true" modal="true" >
        <form id="balanceForm" method="post">
        <input id="frm_type" name="type" type="hidden"/>
	    <table>
			<tr>
				<td width="100" align="right" style="font-weight:bold;line-height:30px;">公司：</td>
                <td>
                    <input id="frm_companyId" style="width:260px;" data-options="required:true"/>
                </td>
			</tr>
            <tr>
                <td align="right" style="font-weight:bold;line-height:30px;">余额：</td>
                <td>
                    <span id="balance" style="color:red;font-weight:bold;"></span>
                </td>
            </tr>
	    	<tr>
                <td align="right" style="font-weight:bold;line-height:30px;">金额：</td>
	    		<td>
                    <input id="frm_amount" type="text" class="easyui-numberbox" data-options="required:true,min:1,precision:2"/>
	    		</td>
	    	<tr>
	    	</tr>
	    		<td align="center" colspan="2" style="font-weight:bold;line-height:30px;">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onClick="balanceMgr.doOptBalance()" style="margin-top: 5px;">确认</a>
	    		</td>
	    	</tr>
	    </table>
        </form>
	</div>
	<!-- 资金操作窗口 终 -->

	<!-- 审核操作窗口 始 -->
    <div id="reasonDg" title="拒绝原因" class="easyui-dialog" style="width:300px;height:180px;padding:5px 5px 5px 5px"
         closed="true" modal="true" >
        <table>
            <tr>
                <td>
                    <input id="accountLogId" name="accountLogId" type="hidden"/>
                    <input class="easyui-textbox" id="reason" name="reason" data-options="multiline:true,validType:'maxLength[150]'" style="height:100px;width:276px;"/>
                </td>
            <tr>
            </tr>
            <td align="center">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="balanceMgr.doReject()" style="margin-top: 5px;">确认</a>
            </td>
            </tr>
        </table>
    </div>
	<!-- 审核操作窗口 终 -->
</body>
</html>