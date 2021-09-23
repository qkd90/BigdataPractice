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
<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/sys/sysUnit/editSupplier.css">
<style type="text/css">
.textbox-text-readonly {color:gray;}
</style>
<script type="text/javascript" src="/js/sys/sysUnit/auditListUtil.js"></script>
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
<script type="text/javascript" src="/js/sys/sysUnit/editSupplierSelf.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:0px 0px 10px 0px;">
		<form id="editForm" method="post">
		<input name="unit.id" type="hidden" value="<s:property value="unit.id"/>"/>
		<input id="userId" name="user.id" type="hidden" value="<s:property value="user.id"/>"/>
		<input name="unitDetail.id" type="hidden" value="<s:property value="unitDetail.id"/>"/>
		<div class="row_hd">帐号信息</div>
		<table>
			<tr>
				<td class="label">用户名:</td>
				<td>
					<input class="easyui-textbox" name="user.account" readonly="true" value="<s:property value="user.account"/>" style="width:240px;"  data-options="validType:['loginAccount','maxLength[100]']">
				</td>
			</tr>
			<tr>
				<td class="label">操作联系人:</td>
				<td>
					<input class="easyui-textbox" name="user.userName" readonly="true" value="<s:property value="user.userName"/>" style="width:240px;"  data-options="validType:['loginName','maxLength[100]']">
				</td>
			</tr>
			<tr>
				<td class="label">手机号:</td>
				<td>
					<input class="easyui-textbox" name="user.mobile" readonly="true" value="<s:property value="user.mobile"/>" style="width:240px;"  >
					<%--<span class="tip">该手机号码为登录账号使用</span>--%>
				</td>
			</tr>
			<tr>
				<td class="label">邮箱:</td>
				<td>
					<input class="easyui-textbox" name="user.email" readonly="true" value="<s:property value="user.email"/>" style="width:240px;" data-options="validType:['email','maxLength[100]']">
				</td>
			</tr>
			<tr>
				<td class="label">QQ:</td>
				<td>
					<input class="easyui-textbox" name="user.qqNo" readonly="true" value="<s:property value="user.qqNo"/>" style="width:240px;" data-options="validType:'QQ'">
				</td>
			</tr>
		</table>
		<div class="row_hd">商户信息</div>
		<table>
			<tr>
				<td class="label">商户名称:</td>
				<td>
					<input class="easyui-textbox" name="unit.name" value="<s:property value="unit.name"/>" readonly="true" style="width:240px;"  data-options="validType:['loginName','maxLength[150]']">
				</td>
			</tr>
			<tr>
				<td class="label">所在地:</td>
				<td>
					<input type="hidden" id="startCityId" name="unit.area.id" value="<s:property value="unit.area.id"/>"/>
					<input id="startCity" readonly="true" value="<s:property value="unit.area.id"/>"
						   class="easyui-textbox" data-options="buttonText:'×', required: true, editable:true,prompt:'点击选择出发城市'" style="width:200px" data-country="<s:property value="unit.area.father.father.id"/>" data-province="<s:property value="unit.area.father.id"/>" data-city="<s:property value="unit.area.id"/>">
				</td>
			</tr>
			<tr>
				<td class="label">公司地址:</td>
				<td>
					<input class="easyui-textbox" name="unit.address" readonly="true" value="<s:property value="unit.address"/>" style="width:240px;"  data-options="validType:['maxLength[150]']">
				</td>
			</tr>

			<tr>
				<td class="label">企业法人:</td>
				<td>
					<input class="easyui-textbox" readonly="true" name="unitDetail.legalPerson" value="<s:property value="unitDetail.legalPerson"/>" style="width:240px;"  data-options="validType:['maxLength[150]']">
				</td>
			</tr>
			<tr>
				<td class="label">证件类型:</td>
				<td>
					<div class="rdo-div"><input type="radio" class="left-block" disabled name="unitDetail.certificateType" value="identity_card" <s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@identity_card || unitDetail.certificateType==null">checked</s:if>/><span class="rdo-label">身份证</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" disabled name="unitDetail.certificateType" value="passport" <s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@passport">checked</s:if>><span class="rdo-label">护照</span></div>
				</td>
			</tr>

			<tr>
				<td class="label"><font color="red">*&nbsp;</font>法人证件号:</td>
				<td>
					<input class="easyui-textbox" id="legalIdCardNoId" name="unitDetail.legalIdCardNo" value="<s:property value="unitDetail.legalIdCardNo"/>" style="width:240px;"  data-options="validType:['maxLength[30]']">
				</td>
			</tr>
			<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@passport">
				<tr>
					<td class="label">护照:</td>
					<td>
						<div>
							<img alt="" <s:if test="unit.sysUnitImages[0].path != null">src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="unit.sysUnitImages[0].path"/>"</s:if><s:else>src=""</s:else> width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
								<%--<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>--%>
						</div>
					</td>
				</tr>
				<tr id="businessLicense">
					<td class="label">营业执照:</td>
					<td>
						<div>
							<img alt="" <s:if test="unit.sysUnitImages[1].path != null">src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="unit.sysUnitImages[1].path"/>"</s:if><s:else>src=""</s:else> width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
								<%--<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>--%>
						</div>
					</td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td class="label">身份证正面照:</td>
					<td>
						<div >
							<img alt="" <s:if test="unit.sysUnitImages[0].path != null">src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="unit.sysUnitImages[0].path"/>"</s:if><s:else>src=""</s:else> width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
								<%--<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>--%>
						</div>
					</td>
				</tr>
				<tr >
					<td class="label">身份证反面照:</td>
					<td>
						<div >
							<img alt="" <s:if test="unit.sysUnitImages[1].path != null">src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="unit.sysUnitImages[1].path"/>"</s:if> <s:else>src=""</s:else> width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
						</div>
					</td>
				</tr>
				<tr >
					<td class="label">营业执照:</td>
					<td>
						<div>
							<img alt="" <s:if test="unit.sysUnitImages[2].path != null">src="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="unit.sysUnitImages[2].path"/>"</s:if><s:else>src=""</s:else> width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
								<%--<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>--%>
						</div>
					</td>
				</tr>
			</s:else>
			<tr>
				<td class="label">供应商类型:</td>
				<td>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" disabled value="hotel" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@hotel">checked</s:if>/><span class="rdo-label">酒店民宿</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" disabled value="sailboat" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@sailboat">checked</s:if>><span class="rdo-label">海上休闲</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" disabled value="scenic" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@scenic">checked</s:if>><span class="rdo-label">景点门票</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" disabled value="cruiseship" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@cruiseship">checked</s:if>><span class="rdo-label">邮轮旅游</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" disabled value="other" <s:if test="unitDetail.supplierType==null||unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@other">checked</s:if>><span class="rdo-label">其他</span></div>
				</td>
			</tr>

			<tr>
				<td class="label">固定电话:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.telphone" readonly="true" value="<s:property value="unitDetail.telphone"/>" style="width:240px;"  data-options="validType:'tel'">
				</td>
			</tr>
			<tr>
				<td class="label">业务联系人:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.contactName" readonly="true" value="<s:property value="unitDetail.contactName"/>" style="width:240px;"  data-options="validType:['loginName','maxLength[100]']">
				</td>
			</tr>
			<tr>
				<td class="label">联系手机:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.mobile" readonly="true" value="<s:property value="unitDetail.mobile"/>" style="width:240px;"  data-options="validType:['mobile']">
				</td>
			</tr>

			<tr>
				<td class="label">银行名称:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtbnk" readonly="true" value="<s:property value="unitDetail.crtbnk"/>" style="width:240px;"  data-options="validType:['mobile']">
				</td>
			</tr>
			<tr>
				<td class="label">开户行省市:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtbnk" readonly="true" value="<s:if test="unitDetail.crtCity != null">${unitDetail.crtCity.father.name}-${unitDetail.crtCity.name}</s:if>" style="width:240px;"  data-options="validType:['mobile']">
				</td>
			</tr>
			<tr>
				<td class="label">银行开户行:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtnam"  readonly="true" value="<s:property value="unitDetail.crtnam"/>" style="width:240px;" >
				</td>
			</tr>

			<tr>
				<td class="label">银行帐号:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtacc"  readonly="true" value="<s:property value="unitDetail.crtacc"/>" style="width:240px;"  data-options="validType:['crtacc']">
				</td>
			</tr>

			<tr>
				<td class="label">经营范围:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.mainBusiness" readonly="true" value="<s:property value="unitDetail.mainBusiness"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:60px;width:320px;"/>
					<div class="tip">500字内</div>
				</td>
			</tr>
			<tr>
				<td class="label">公司简介:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.introduction" readonly="true" value="<s:property value="unitDetail.introduction"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:100px;width:450px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">商家资质:
					<p style="font-size: 10px;">（支持word、表格、pdf、jpeg、jpg、png）</p>
				</td>
				<td>
					<table width="450px" style="float: left; margin-right: 10px;">
						<thead>
						<tr>
							<td width="320px">文件名称</td>
							<td>操作</td>
						</tr>
						</thead>
						<tbody id="appendicesTbody">
						<s:iterator value="unit.qualificationList" var="qualification" status="q">
							<tr id="tr_<s:property value="#q.index"/>" class="appendice_class" rows="<s:property value="#q.index"/>">
								<td width="320px">
									<s:property value="#qualification.name"/>
									<input type="hidden" value="<s:property value="#qualification.name"/>">
									<input type="hidden" value="<s:property value="#qualification.path"/>">
									<input type="hidden" value="<s:property value="#qualification.type"/>">
								</td>
								<td align="center">
									<s:if test="#qualification.type==@com.data.data.hmly.service.entity.DoucementType@image">
										<a href="javascript:void(0)" onclick="editSupplierSelf.download('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>')">下载</a>&nbsp;&nbsp;
									</s:if>
									<s:else>
										<a href="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>">下载</a>&nbsp;&nbsp;
									</s:else>
									<%--<a href="javascript:void(0)"  onclick="editSupplier.delTr(<s:property value="#q.index"/>)">删除</a>--%>
								</td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
					<%--<input type="hidden" id="upload" value=""/>
					<input type="button" style="width:150px; float: right" id="uploadButton" value="添加附件"/>--%>

				</td>
			</tr>
		</table>

	    </form>
		<div style="text-align:left;padding:5px;height:30px;margin-left: 20px;">
		</div>
	</div>
</div>

<div id="editPanel" class="easyui-dialog" title=""
	 data-options="fit:true,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
	<iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
</div>
<div id="downDialog" class="easyui-dialog" title="图片浏览" style="width:600px;height:500px;"
	 data-options="resizable:true, closed: true, modal:true">
	<div style="padding: 10px;">
		<img id="viewImg" src="" style="width: 98%; height: 95%;">
	</div>
</div>
</body>
</html>
