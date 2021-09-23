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
<link rel="stylesheet" type="text/css" href="/css/sys/syssite/manage.css"/>
<script type="text/javascript" src="/js/sys/syssite/sitemanage.js"></script>
    <style type="text/css">
        #searchform label {
            margin-right: 10px;
        }
    </style>
</head>
<body >
	<!-- 数据表格 始 -->
	<table id="dg"></table>
	<!-- 数据表格 终-->
	<!-- 数据表格 按纽组 始 -->
	<div id="toolbar" >
        <div style="margin: 10px 0px 10px 10px;">
            <form id="searchform">
                <label>站点名:</label>
                <label><input  type="text" id="role_name" class="easyui-textbox" style="widows: 100px;"/></label>
                <label>描述:</label>
                <label><input  type="text" id="role_remark" class="easyui-textbox" style="widows: 100px;"/></label>
                <label>站点状态:</label>
                <label>
                    <input  type="text" id="role_status" class="easyui-combobox" style="width: 100px;"
                            data-options="
                                    valueField:'label',
                                    textField:'value',
                                    panelHeight:50,
                                    data: [{
                                        label: '0',
                                        value: '激活'
                                    },{
                                        label: '1',
                                        value: '冻结'
                                    }]
                                    "
                            />
                </label>
                <label>
                    <a href="javascript:void(0)" class="easyui-linkbutton" id="search">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
                </label>
            </form>
        </div>
        <div style="margin: 10px 0px 5px 10px;">
            <a id="addbtn" href="#" onclick="SysSite.openAddForm();"  class="easyui-linkbutton" >添加站点</a>
            <a id="editbtn" href="#" onclick="SysSite.openEditForm();" class="easyui-linkbutton" >编辑站点</a>
        </div>

	</div>
	<!-- 数据表格 按纽组 终 -->
	
	<!-- 新增框  始-->
<!-- 	<div class="easyui-dialog" id="edit_panel" closed="true"  onClose="SysSite.clearForm()" style="width:500px;top: 80px;"> -->
	<div class="easyui-dialog" id="edit_panel" closed="true"   style="width:500px;top: 80px;">
        <form id="ff" method="post">
            <table cellpadding="5">
                <tr>
                    <td>站点名:</td>
                    <td>
                    <!-- <input type="hidden" name="id"/> -->
                    <input class="easyui-textbox"  type="text" name="sysSite.sitename" data-options="required:true"></input></td>
<!--                     <input class="easyui-textbox" id="text_site_name" type="text" name="sysSite.sitename" data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>公司名称:</td>
                    <td>
                    <input type="hidden"  name="id"/>
                    <input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
<!--                     <input class="easyui-textbox" id="text_name" type="text" name="name" data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>网址:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysSite.siteurl"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_siteurl" type="text" name="sysSite.siteurl"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>城市:</td>
                    <td><s:select list="#request.province" listKey="id" listValue="name" name="province" id="province"></s:select> - <s:select list="#request.city" listKey="id" listValue="name" name="area.id" id="city"></s:select> </td>
<%--                     <td><s:select list="#request.province" listKey="id" listValue="name" name="province" id="province"></s:select> - <s:select list="#request.city" listKey="id" listValue="name" name="area.id" id="city"></s:select> </td> --%>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td><input class="easyui-textbox"  type="text" name="address"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_address" type="text" name="address"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>供应商类型:</td>
                    <td>
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="zhuangxiang"  /> 专线
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="zhonghe"  /> 综合
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="dijie"  /> 地接
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="chujing"  /> 出境
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="ticket"  /> 票务
                    <input type="radio" name="sysUnitUnitDetail.supplierType" value="hotel"  /> 酒店

<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="zhuangxiang"  /> 专线 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="zhonghe"  /> 综合 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="dijie"  /> 地接 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="chujing"  /> 出境 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="ticket"  /> 票务 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.supplierType" value="hotel"  /> 酒店 -->
                    </td>
                </tr>
                <tr>
                    <td>获取经营范围:</td>
                    <td>
                    <input type="radio" name="sysUnitUnitDetail.businessScope" value="inlang"  /> 国内社
                    <input type="radio" name="sysUnitUnitDetail.businessScope" value="chujing"  /> 出境社
                    <input type="radio" name="sysUnitUnitDetail.businessScope" value="intenal"  /> 国际社
                    <input type="radio" name="sysUnitUnitDetail.businessScope" value="other"  /> 其它

<!--                     <input type="radio" name="sysUnitUnitDetail.businessScope" value="inlang"  /> 国内社 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.businessScope" value="chujing"  /> 出境社 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.businessScope" value="intenal"  /> 国际社 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.businessScope" value="other"  /> 其它 -->
                    </td>
                </tr>
                <tr>
                    <td>业务类别:</td>
                    <td>
                    <input type="radio" name="sysUnitUnitDetail.businessType" value="dijie"  /> 地接
                    <input type="radio" name="sysUnitUnitDetail.businessType" value="zhutuan"  /> 组团
                    <input type="radio" name="sysUnitUnitDetail.businessType" value="zhonehe"  /> 综合

<!--                     <input type="radio" name="sysUnitUnitDetail.businessType" value="dijie"  /> 地接 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.businessType" value="zhutuan"  /> 组团 -->
<!--                     <input type="radio" name="sysUnitUnitDetail.businessType" value="zhonehe"  /> 综合 -->
                    </td>
                </tr>
                <tr>
                    <td>固定电话:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysUnitUnitDetail.telphone"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udtelphone" type="text" name="sysUnitUnitDetail.telphone"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>传真:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysUnitUnitDetail.fax"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udfax" type="text" name="sysUnitUnitDetail.fax"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>业务主体:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysUnitUnitDetail.mainBody"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udmainBody" type="text" name="sysUnitUnitDetail.mainBody"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>经营范围:</td>
                    <td> 
	                    <input type="radio" name="sysUnitUnitDetail.businessModel" value="zhishu"  /> 直属
	                    <input type="radio" name="sysUnitUnitDetail.businessModel" value="guakao"  /> 挂靠

<!-- 	                    <input type="radio" name="sysUnitUnitDetail.businessModel" value="zhishu"  /> 直属 -->
<!-- 	                    <input type="radio" name="sysUnitUnitDetail.businessModel" value="guakao"  /> 挂靠 -->
                    </td>
                </tr>
                <tr>
                    <td>合作网络渠道:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysUnitUnitDetail.partnerChannel"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udpartnerChannel" type="text" name="sysUnitUnitDetail.partnerChannel"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>对应合作页面:</td>
                    <td><input class="easyui-textbox"  type="text" name="sysUnitUnitDetail.partnerUrl"  data-options="required:true"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udpartnerUrl" type="text" name="sysUnitUnitDetail.partnerUrl"  data-options="required:true"></input></td> -->
                </tr>
                <tr>
                    <td>优势说明:</td>
                    <td><input class="easyui-textbox"  name="sysUnitUnitDetail.partnerAdvantage" data-options="multiline:true" style="height:60px"></input></td>
<!--                     <td><input class="easyui-textbox" id="text_udpartnerAdvantage" name="sysUnitUnitDetail.partnerAdvantage" data-options="multiline:true" style="height:60px"></input></td> -->
                </tr>
               
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysSite.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysSite.clearForm()">重置</a>
        </div>
    </div>
	<!-- 新增框 终 -->

	<!-- 编辑框  始-->
	<div class="easyui-dialog" id="edit_dialog" closed="true"  onClose="SysSite.clearForm()" style="width:500px;top: 80px;">
        <form id="editForm" method="post">
            <table cellpadding="5">
                <tr>
                    <td>站点名:</td>
                    <td>
                    <input type="hidden" id="hidden_unitid" name="uid"/> 
                    <input class="easyui-textbox" id="text_site_name" type="text" name="sitename" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>公司名称:</td>
                    <td>
                    <input type="hidden" id="hidden_siteid" name="sid"/>
                    <input class="easyui-textbox" id="text_name" type="text" name="name" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>网址:</td>
                    <td><input class="easyui-textbox" id="text_siteurl" type="text" name="siteurl"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>城市:</td>
                    <td><s:select list="#request.province" listKey="id" listValue="name" name="province" id="edit_province"></s:select> - <s:select list="#request.city" listKey="id" listValue="name" name="areaid" id="edit_city"></s:select> </td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td><input class="easyui-textbox" id="text_address" type="text" name="address"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>供应商类型:</td>
                    <td>
                    <input type="radio" name="supplierType" value="zhuangxiang"  /> 专线
                    <input type="radio" name="supplierType" value="zhonghe"  /> 综合
                    <input type="radio" name="supplierType" value="dijie"  /> 地接
                    <input type="radio" name="supplierType" value="chujing"  /> 出境
                    <input type="radio" name="supplierType" value="ticket"  /> 票务
                    <input type="radio" name="supplierType" value="hotel"  /> 酒店
                    </td>
                </tr>
                <tr>
                    <td>获取经营范围:</td>
                    <td>
                    <input type="radio" name="businessScope" value="inlang"  /> 国内社
                    <input type="radio" name="businessScope" value="chujing"  /> 出境社
                    <input type="radio" name="businessScope" value="intenal"  /> 国际社
                    <input type="radio" name="businessScope" value="other"  /> 其它
                    </td>
                </tr>
                <tr>
                    <td>业务类别:</td>
                    <td>
                    <input type="radio" name="businessType" value="dijie"  /> 地接
                    <input type="radio" name="businessType" value="zhutuan"  /> 组团
                    <input type="radio" name="businessType" value="zhonehe"  /> 综合
                    </td>
                </tr>
                <tr>
                    <td>固定电话:</td>
                    <td><input class="easyui-textbox" id="text_udtelphone" type="text" name="telphone"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>传真:</td>
                    <td><input class="easyui-textbox" id="text_udfax" type="text" name="fax"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>业务主体:</td>
                    <td><input class="easyui-textbox" id="text_udmainBody" type="text" name="mainBody"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>经营范围:</td>
                    <td> 
	                    <input type="radio" name="businessModel" value="zhishu"  /> 直属
	                    <input type="radio" name="businessModel" value="guakao"  /> 挂靠
                    </td>
                </tr>
                <tr>
                    <td>合作网络渠道:</td>
                    <td><input class="easyui-textbox" id="text_udpartnerChannel" type="text" name="partnerChannel"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>对应合作页面:</td>
                    <td><input class="easyui-textbox" id="text_udpartnerUrl" type="text" name="partnerUrl"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>优势说明:</td>
                    <td><input class="easyui-textbox" id="text_udpartnerAdvantage" name="partnerAdvantage" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
               
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysSite.submitEditForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysSite.clearForm()">重置</a>
        </div>
    </div>
	<!-- 编辑框 终 -->
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="站点信息" 
        data-options="fit:true,resizable:true,modal:true,closed:true"> 
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:1480px;"></iframe>  
	</div> 
	
</body>
</html>