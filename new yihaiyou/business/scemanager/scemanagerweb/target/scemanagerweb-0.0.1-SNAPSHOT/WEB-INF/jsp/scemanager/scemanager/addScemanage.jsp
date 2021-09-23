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
<script type="text/javascript" src="/js/scemanager/addScemanage.js"></script>
    <style>
        #edit_div .gongying input{height:15px;}
    </style>
</head>
<body style="background-color: white;">

	
	<!-- 编辑框  始-->
	<div id="edit_div" style="width:500px;top: 80px;padding-left:10px;padding-top:5px;">
        <form id="ff" method="post">
            <table cellpadding="5" >
                <tr>
                    <td>景点名称:</td>
                    <td>
                    <!-- <input type="hidden" name="id"/> -->
                    <input type="hidden" name="sitename" id="sitename_id" value=""/>
                    <input type="hidden" name="scenicid" id="hidden_scenicid" value=""/>
                    <input class="easyui-combobox" id="combobox_sitename"  data-options="required:true,
		                    loader: AddScemanage.scenicLoader,
											mode: 'remote',
											valueField: 'id',
											textField: 'name' "></td>
                </tr>
                <tr >
                    <td>账户:</td>
                    <td>
                    <input type="hidden" name="id"/>
                    <input class="easyui-textbox" id="ipt_accountId" type="text" name="name" data-options="required:true,validType:['validateAccountExist']"><span id="tip_span" style="margin-left: 10px;"></span></td>
                </tr>
<!--                 <tr> -->
<!--                     <td>网址:</td> -->
<!--                     <td><input class="easyui-textbox" type="text" name="siteurl"  data-options="required:true"/></td> -->
<!--                 </tr> -->
                <tr >
                    <td>城市:</td>
                    <td>
                    
                    	<select class="easyui-combobox" style="width: 100px"
							id="sec_proNameId" value="" data-options="required:true" >
							</select>
						<select class="easyui-combobox" style="width: 100px"
						id="sec_cityNameId" name="area" value="" data-options="required:true" >
						</select>
                    
                   
                    </td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td><input class="easyui-textbox" type="text" name="address"  data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>供应商类型:</td>
                    <td style="height:28px" class="gongying">
                    <input type="radio" name="supplierType" value="zhuangxiang" checked="checked" /> 专线
                    <input type="radio" name="supplierType" value="zhonghe"  /> 综合
                    <input type="radio" name="supplierType" value="dijie"  /> 地接
                    <input type="radio" name="supplierType" value="chujing"  /> 出境
                    <input type="radio" name="supplierType" value="ticket"  /> 票务
                    <input type="radio" name="supplierType" value="hotel"  /> 酒店
                    </td>
                </tr>
                <tr>
                    <td>获取经营范围:</td>
                    <td  style="height:28px" class="gongying">
                    <input type="radio" name="businessScope" value="inlang" checked="checked" /> 国内社
                    <input type="radio" name="businessScope" value="chujing"  /> 出境社
                    <input type="radio" name="businessScope" value="intenal"  /> 国际社
                    <input type="radio" name="businessScope" value="other"  /> 其它
                    </td>
                </tr>
                <tr>
                    <td>业务类别:</td>
                    <td  style="height:28px" class="gongying">
                    <input type="radio" name="businessType" value="dijie" checked="checked" /> 地接
                    <input type="radio" name="businessType" value="zhutuan"  /> 组团
                    <input type="radio" name="businessType" value="zhonghe"  /> 综合
                    </td>
                </tr>
                <tr>
                    <td>固定电话:</td>
                    <td><input class="easyui-textbox" type="text" name="telphone"  data-options="required:true, validType:['phone']"/></td>
                </tr>
                <tr>
                    <td>传真:</td>
                    <td><input class="easyui-textbox" type="text" name="fax"  data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>业务主体:</td>
                    <td><input class="easyui-textbox" type="text" name="mainBody"  data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>经营范围:</td>
                    <td  style="height:28px" class="gongying">
	                    <input type="radio" name="businessModel" value="zhishu" checked="checked" /> 直属
	                    <input type="radio" name="businessModel" value="guakao"  /> 挂靠
                    </td>
                </tr>
                <tr>
                    <td>合作网络渠道:</td>
                    <td><input class="easyui-textbox" type="text" name="partnerChannel"  data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>对应合作页面:</td>
                    <td><input class="easyui-textbox" type="text" name="partnerUrl"  data-options="required:true,validType:['url']"/><span style="margin-left: 20px;color: #cfcfc0">如：http://www.baidu.com</span></td>
                </tr>
                    	<input id="filePath" name="filePath" type="hidden" value=""/>
                    	
                <tr>
                    <td>优势说明:</td>
                    <td><input class="easyui-textbox" name="partnerAdvantage" data-options="multiline:true" style="height:60px"/></td>
                </tr>
               
            </table>
        </form>
        <div>
       		 <label style="margin-right: 4px;">上传企业LOGO:</label><input type="button" id="updateLogo" value="点击上传">
        </div>
        
        <div id="imgView" style="position: fixed; display:none;   margin-left: 280px; margin-top: -125px;">
			<img alt="" src="" width="100" height="100"><br>
			<a href="javascript:;" id="remove_img_id" onclick="AddScemanage.removeImg()" class="easyui-linkbutton line-btn" style="margin-left: 105px;margin-top: -25px;" >删除</a>
		</div>
        
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="AddScemanage.submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="AddScemanage.clearForm()">重置表单</a>
        </div>
    </div>
	<!-- 编辑框 终 -->
	
	
</body>
</html>