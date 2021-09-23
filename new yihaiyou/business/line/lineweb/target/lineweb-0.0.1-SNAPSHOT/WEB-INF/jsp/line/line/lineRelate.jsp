<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组合产品</title>
<%@ include file="../../common/common141.jsp"%>
    <link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
    <script type="text/javascript" src="/js/line/line/lineRelate.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false">
	<div title="[<s:property value="lineDisplay.productNo"/>]<s:property value="lineDisplay.name"/>" data-options="region:'center',border:true, tools:'#dsTools'" style="padding-bottom: 30px;">
		<div id="dsTools">
			<a href="javascript:void(0)" class="" style="width:28px;" onClick="LineRelate.closeChildPanel()">返回</a>
		</div>
        <input id="productId" name="productId" type="hidden" value="<s:property value="lineDisplay.id"/>"/>
        <s:iterator value="linedays" var="ld">
        <div class="row_hd" style="margin: 0;padding-top: 10px;padding-bottom: 10px;font-weight: bold;">
            <label style="color:#028dd2;">第<s:property value="lineDay"/>天</label>&nbsp;<s:property value="dayDesc"/>
        </div>
        <div style="overflow:auto;" class="priceDg" linedaysid="<s:property value="id"/>">
            <table id="hotelPrice_dg_<s:property value="id"/>" data-id="<s:property value="id"/>"></table>
            <table id="ticketPrice_dg_<s:property value="id"/>" data-id="<s:property value="id"/>"></table>
        </div>
        <div id="hotelPrice_dg_tools_<s:property value="id"/>">
            <a href="javascript:void(0)" class="" style="width:60px;" onClick="LineRelate.openHotelPriceSelectDg(<s:property value="id"/>)">新增组合</a>
        </div>
        <div id="ticketPrice_dg_tools_<s:property value="id"/>">
            <a href="javascript:void(0)" class="" style="width:60px;" onClick="LineRelate.openTicketPriceSelectDg(<s:property value="id"/>)">新增组合</a>
        </div>
        </s:iterator>
	</div>
</div>

<!-- 酒店房型列表窗口 始 -->
<div id="hotelPriceSelectDg" class="easyui-dialog" title="酒店房型列表" style="width:760px;height:460px;"
     data-options="resizable:false,modal:true,closed:true,collapsible:false,shadow:false,onClose:LineRelate.hotelPriceSelectDgClose">
    <!-- 表格工具条 始 -->
    <div id="hotelPriceSelectGridTb">
        <div style="padding:2px 5px;">
            <input id="qryHotelId" class="easyui-numberbox" data-options="prompt:'请输入酒店编号',min:0" style="width:120px;">
            <input id="qryHotelName" class="easyui-textbox" data-options="prompt:'请输入酒店名称'" style="width:200px;">
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LineRelate.searchHotelPrice()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LineRelate.doRelateHotel()">确认</a>
            <label style="color: red;line-height: 24px;float: right;margin-top:7px;">(使用酒店编号进行查询，以提高查询速度)</label>
        </div>
    </div>
    <!-- 表格工具条 终 -->
    <!-- 数据表格 始 -->
    <table id="hotelPriceSelectGrid"></table>
    <!-- 数据表格 终-->
</div>
<!-- 酒店房型列表窗口 终 -->

<!-- 景点门票列表窗口 始 -->
<div id="ticketPriceSelectDg" class="easyui-dialog" title="景点门票列表" style="width:760px;height:460px;"
     data-options="resizable:false,modal:true,closed:true,collapsible:false,shadow:false,onClose:LineRelate.ticketPriceSelectDgClose">
    <!-- 表格工具条 始 -->
    <div id="ticketPriceSelectGridTb">
        <div style="padding:2px 5px;">
            <input id="qryScenicId" class="easyui-combobox" style="width: 160px"
                   data-options="prompt:'请输入景点名称',loader:LineRelate.scenicLoader, mode:'remote', valueField:'id', textField:'name'">
            <input id="qryTicketName" class="easyui-textbox" data-options="prompt:'请输入门票名称'" style="width:160px;">
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LineRelate.searchTicketPrice()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="LineRelate.doRelateTicket()">确认</a>
            <label style="color: red;line-height: 24px;float: right;margin-top:7px;">(输入景点名称并选择进行查询)</label>
        </div>
    </div>
    <!-- 表格工具条 终 -->
    <!-- 数据表格 始 -->
    <table id="ticketPriceSelectGrid"></table>
    <!-- 数据表格 终-->
</div>
<!-- 景点门票列表窗口 终 -->

</body>
</html>