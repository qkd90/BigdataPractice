<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>站点维护</title>
<%@ include file="../../common/common141.jsp"%>
<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/syssite/editSite.css">
<script type="text/javascript" src="/js/sys/syssite/editSite.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:0px 0px 10px 0px;">
		<a name="loctop"></a> 
		<form id="editForm" method="post">
		<input name="unit.id" type="hidden" value="<s:property value="unit.id"/>"/>
		<input name="site.id" type="hidden" value="<s:property value="site.id"/>"/>
		<input id="userId" name="user.id" type="hidden" value="<s:property value="user.id"/>"/>
		<input name="unitDetail.id" type="hidden" value="<s:property value="unitDetail.id"/>"/>
		<div class="row_hd">站点信息</div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>站点名称:</td>
			   	<td>
		   			<input class="easyui-textbox" name="site.sitename" value="<s:property value="site.sitename"/>" required="true" style="width:240px;" data-options="validType:['loginName','maxLength[100]']"/>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>站点地址:</td>
			   	<td>
			   		<input class="easyui-textbox" name="site.siteurl" value="<s:property value="site.siteurl"/>" style="width:240px;" required="true" data-options="validType:['url','maxLength[100]']">
			   	</td>
			</tr>
		</table>
		<div class="row_hd">联系人信息</div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>姓名:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.userName" value="<s:property value="user.userName"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[100]']">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>手机号:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.account" value="<s:property value="user.account"/>" style="width:240px;" required="true" data-options="validType:['mobile','validateMobileExist']">
			   		<span class="tip">该手机号码将作为登录账号使用，请慎重填写</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>邮箱:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.email" value="<s:property value="user.email"/>" style="width:240px;" required="true" data-options="validType:['email','maxLength[100]']">
			   		<span class="tip">请确保该邮箱可用</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>QQ:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.qqNo" value="<s:property value="user.qqNo"/>" style="width:240px;" required="true" data-options="validType:'QQ'">
			   	</td>
			</tr>
		</table>
		<div class="row_hd">企业信息</div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>公司名称:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unit.name" value="<s:property value="unit.name"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[150]']">
			   		<span class="tip">需和您的营业执照上公司名称相一致</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>品牌名称:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.brandName" value="<s:property value="unitDetail.brandName"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[150]']">
			   		<span class="tip">同业合作品牌名称，如"环宇假期"</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>所在地:</td>
			   	<td>
			   		<input class="easyui-combobox" id="province" name="province" required="true" style="width:130px;"/>
			   		<input class="easyui-combobox" id="areaId" name="areaId" value="<s:property value="areaId"/>" required="true" style="width:100px;"/>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>公司地址:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unit.address" value="<s:property value="unit.address"/>" style="width:240px;" required="true" data-options="validType:['maxLength[150]']">
			   		<span class="tip">公司详细地址</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>公司LOGO:</td>
			   	<td>
			   		<input type="button" id="uploadButton" value="插入图片" />
			   		<span class="tip">最佳尺寸120*80</span>
					<input id="filePath" name="filePath" type="hidden" value="<s:property value="filePath"/>"/>
			   	</td>
			</tr>
        	<tr id="imgView" style="<s:if test="filePath == null || filePath == ''">display:none;</s:if>">
			   	<td class="label"></td>
			   	<td>
			    <div>
			    	<img alt="" src="<s:if test="filePath != null">/static<s:property value="filePath"/></s:if>" width="120" height="80" style="padding:5px; border: 1px dashed #E3E3E3;"><br>
			    	<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>
			    </div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>供应商类型:</td>
			   	<td>
			   		<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@zhuangxiang"/>" <s:if test="unitDetail.supplierType==null || unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@zhuangxiang">checked</s:if>/><span class="rdo-label">专线</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@zhonghe"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@zhonghe">checked</s:if>><span class="rdo-label">综合</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@dijie"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@dijie">checked</s:if>><span class="rdo-label">地接</span></div>
			   		<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@chujing"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@chujing">checked</s:if>><span class="rdo-label">出境</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@ticket"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@ticket">checked</s:if>><span class="rdo-label">票务</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@hotel"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@hotel">checked</s:if>><span class="rdo-label">酒店</span></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>经营范围:</td>
			   	<td>
			   		<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@inlang"/>" <s:if test="unitDetail.businessScope==null || unitDetail.businessScope==@com.data.data.hmly.service.entity.BusinessScope@inlang">checked</s:if>><span class="rdo-label">国内社</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@chujing"/>" <s:if test="unitDetail.businessScope==@com.data.data.hmly.service.entity.BusinessScope@chujing">checked</s:if>><span class="rdo-label">出境社</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@intenal"/>" <s:if test="unitDetail.businessScope==@com.data.data.hmly.service.entity.BusinessScope@intenal">checked</s:if>><span class="rdo-label">国际社</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@other"/>" <s:if test="unitDetail.businessScope==@com.data.data.hmly.service.entity.BusinessScope@other">checked</s:if>><span class="rdo-label">其他</span></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>业务类别:</td>
			   	<td>
			   		<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@dijie"/>" <s:if test="unitDetail.businessType==@com.data.data.hmly.service.entity.BusinessType@dijie">checked</s:if>><span class="rdo-label">地接</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@zhutuan"/>" <s:if test="unitDetail.businessType==@com.data.data.hmly.service.entity.BusinessType@zhutuan">checked</s:if>><span class="rdo-label">组团</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@zhonghe"/>" <s:if test="unitDetail.businessType==null || unitDetail.businessType==@com.data.data.hmly.service.entity.BusinessType@zhonghe">checked</s:if>><span class="rdo-label">综合</span></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>固定电话:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.telphone" value="<s:property value="unitDetail.telphone"/>" style="width:240px;" required="true" data-options="validType:'tel'">
			   		<span class="tip">例010-12345678</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>传真:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.fax" value="<s:property value="unitDetail.fax"/>" style="width:240px;" required="true" data-options="validType:'tel'">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>业务主体:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.mainBody" value="<s:property value="unitDetail.mainBody"/>" style="width:240px;" required="true" data-options="validType:'maxLength[150]'">
			   		<span class="tip">合作分公司/部门名称</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>经营模式:</td>
			   	<td>
			   		<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessModel" value="<s:property value="@com.data.data.hmly.service.entity.BusinessModel@zhishu"/>" <s:if test="unitDetail.businessModel==null || unitDetail.businessModel==@com.data.data.hmly.service.entity.BusinessModel@zhishu">checked</s:if>><span class="rdo-label">直属</span></div>
				   	<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.businessModel" value="<s:property value="@com.data.data.hmly.service.entity.BusinessModel@guakao"/>" <s:if test="unitDetail.businessModel==@com.data.data.hmly.service.entity.BusinessModel@guakao">checked</s:if>><span class="rdo-label">挂靠</span></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>联系人:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.contactName" value="<s:property value="unitDetail.contactName"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[100]']">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>联系手机:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.mobile" value="<s:property value="unitDetail.mobile"/>" style="width:240px;" required="true" data-options="validType:['mobile']">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>主营业务:</td>
			   	<td>
		   			<input class="easyui-textbox" name="unitDetail.mainBusiness" value="<s:property value="unitDetail.mainBusiness"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:60px;width:320px;"></input>
		   			<div class="tip">500字内</div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>公司简介:</td>
			   	<td>
		   			<input class="easyui-textbox" name="unitDetail.introduction" value="<s:property value="unitDetail.introduction"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:60px;width:320px;"></input>
		   			<div class="tip">500字内</div>
			   	</td>
			</tr>
		</table>
		<div class="row_hd">其他信息</div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>合作网络渠道:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.partnerChannel" value="<s:property value="unitDetail.partnerChannel"/>" style="width:240px;" required="true" data-options="validType:'maxLength[150]'">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>对应合作页面:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unitDetail.partnerUrl" value="<s:property value="unitDetail.partnerUrl"/>" style="width:240px;" required="true" data-options="validType:['url','maxLength[100]']">
			   		<span class="tip">合作页面链接</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>优势说明:</td>
			   	<td>
		   			<input class="easyui-textbox" name="unitDetail.partnerAdvantage" value="<s:property value="unitDetail.partnerAdvantage"/>" data-options="multiline:true,validType:'maxLength[150]'" style="height:60px;width:320px;"></input>
		   			<div class="tip">请简要说明贵公司操作的主要产品线和优势产品，150字内</div>
			   	</td>
			</tr>
		</table>
	    </form>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:left;padding:5px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editSite.doSave()">保存</a>
		</div>  
	</div>
</div>
</body>
</html>
