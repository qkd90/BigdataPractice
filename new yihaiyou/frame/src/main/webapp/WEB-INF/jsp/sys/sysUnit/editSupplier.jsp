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
	<link rel="stylesheet" href="/lib/cityselect/selectCity.css">
<link rel="stylesheet" type="text/css" href="/css/sys/sysUnit/editSupplier.css">
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
<script type="text/javascript" src="/js/sys/sysUnit/auditListUtil.js"></script>
	<script type="text/javascript" src="/lib/cityselect/citySet.js"></script>
	<script type="text/javascript" src="/lib/cityselect/Popt.js"></script>
<script type="text/javascript" src="/js/sys/sysUnit/editSupplier.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px; background-color: #ffffff;">
<%--<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> --%>
	<div id="content" style="padding:0px 0px 10px 0px;">
		<a name="loctop"></a> 
		<form id="editForm" method="post">
		<input name="unit.id" type="hidden" value="<s:property value="unit.id"/>"/>
		<input id="userId" name="user.id" type="hidden" value="<s:property value="user.id"/>"/>
		<input name="unitDetail.id" type="hidden" value="<s:property value="unitDetail.id"/>"/>
		<s:if test="unit.status==-1 || unit.status==1">
		<table width="100%">
        	<tr>
			   	<td class="label"></td>
			   	<td align="right" style="padding-top:7px;">
			   		<div style="font-size:18px;font-weight:bold;color: orange;border: 1px solid #ddd;padding:3px;width:66px;margin-right:20px;text-align: center;">
			   		<s:if test="unit.status==-1">待审核</s:if>
		   			<s:if test="unit.status==1">已冻结</s:if>
					</div>
			   	</td>
			</tr>
		</table>
		</s:if>
		<div class="row_hd">帐号信息</div>
		<table>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>用户名:</td>
				<td>
					<input class="easyui-textbox" name="user.account" value="<s:property value="user.account"/>" style="width:240px;" <s:if test="user.id!=null">readonly</s:if> required="true" data-options="validType:['loginAccount','validateAccountExist']">
					<span class="tip">保证唯一性，登录帐号只能输入3-15个字母、数字、下划线</span>
				</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>操作联系人:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.userName" value="<s:property value="user.userName"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[100]']">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>手机号:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.mobile" value="<s:property value="user.mobile"/>" style="width:240px;" required="true" data-options="validType:['mobile']">
			   		<span class="tip">请确保手机可用</span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">邮箱:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.email" value="<s:property value="user.email"/>" style="width:240px;" data-options="validType:['email','maxLength[100]']">
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">QQ:</td>
			   	<td>
			   		<input class="easyui-textbox" name="user.qqNo" value="<s:property value="user.qqNo"/>" style="width:240px;" data-options="validType:'QQ'">
			   	</td>
			</tr>
		</table>
		<div class="row_hd">商户信息</div>
		<table>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>商户名称:</td>
			   	<td>
			   		<input class="easyui-textbox" name="unit.name" value="<s:property value="unit.name"/>" style="width:240px;" required="true" data-options="validType:['loginName','maxLength[150]']">
			   		<span class="tip">需和您的营业执照上公司名称相一致</span>
			   	</td>
			</tr>

        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>所在地:</td>
			   	<td>
					<input type="hidden" id="startCityId" name="unit.area.id" value="<s:property value="unit.area.id"/>"/>
					<input id="startCity" value="<s:property value="unit.area.id"/>"
						   class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="<s:property value="unit.area.father.father.id"/>" data-province="<s:property value="unit.area.father.id"/>" data-city="<s:property value="unit.area.id"/>">
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
				<td class="label"><font color="red">*&nbsp;</font>企业法人:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.legalPerson" value="<s:property value="unitDetail.legalPerson"/>" style="width:240px;" required="true" data-options="validType:['maxLength[150]']">
				</td>
			</tr>

			<tr>
				<td class="label">证件类型:</td>
				<td>
					<div class="rdo-div"><input type="radio" onchange="editSupplier.selCertificateType('identity_card')" class="left-block" name="unitDetail.certificateType" value="identity_card" <s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@identity_card || unitDetail.certificateType==null">checked</s:if>/><span class="rdo-label">身份证</span></div>
					<div class="rdo-div"><input type="radio" onchange="editSupplier.selCertificateType('passport')" class="left-block" name="unitDetail.certificateType" value="passport" <s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@passport">checked</s:if>><span class="rdo-label">护照</span></div>
				</td>
			</tr>

			<tr>
				<td class="label"><font color="red">*&nbsp;</font>法人证件号:</td>
				<td>
					<input class="easyui-textbox" id="legalIdCardNoId" name="unitDetail.legalIdCardNo" value="<s:property value="unitDetail.legalIdCardNo"/>" style="width:240px;" required="true" data-options="validType:['maxLength[30]']">
				</td>
			</tr>

				<tr class="passPartImage">
					<td class="label"><font color="red">*&nbsp;</font>护照:</td>
					<td class="passPartImage_value">
						<input type="button" id="passsPortButton" value="插入图片" />
							<%--<span class="tip">最佳尺寸120*80</span>--%>
						<input type="hidden" value="PARSSPORT">
						<input id="passPortPath" name="image" type="hidden" value="<s:property value="unit.sysUnitImages[0].path"/>"/>
					</td>
				</tr>
				<tr class="passPartImage">
					<td class="label"></td>
					<td>
						<div id="passPort">
							<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@passport">
								<img alt="" src="<%=com.zuipin.util.QiniuUtil.URL%><s:if test="unit.sysUnitImages[0].path != null"><s:property value="unit.sysUnitImages[0].path"/></s:if>" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:if>
							<s:else>
								<img alt="" src="" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:else>
						</div>
					</td>
				</tr>

				<tr class="passPartImage">
					<td class="label"><font color="red">*&nbsp;</font>营业执照:</td>
					<td class="passPartImage_value">
						<input type="button" id="businessLicenseButton1" value="插入图片" />
							<%--<span class="tip">最佳尺寸120*80</span>--%>
						<input type="hidden" value="BUSINESS_LICENSE">
						<input id="businessLicensePath1" name="image" type="hidden" value="<s:property value="unit.sysUnitImages[1].path"/>"/>
					</td>
				</tr>
				<tr  class="passPartImage">
					<td class="label"></td>
					<td>
						<div id="businessLicense1">
							<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@passport">
								<img alt="" src="<%=com.zuipin.util.QiniuUtil.URL%><s:if test="unit.sysUnitImages[1].path != null"><s:property value="unit.sysUnitImages[1].path"/></s:if>" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:if>
							<s:else>
								<img alt="" src="" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:else>

						</div>
					</td>
				</tr>
				<tr class="idCardImage">
					<td class="label"><font color="red">*&nbsp;</font>身份证正面照:</td>
					<td class="idCardImage_value">
						<input type="button" id="idcardPositiveButton" value="插入图片" />
							<%--<span class="tip">最佳尺寸120*80</span>--%>
						<input type="hidden" value="POSITIVE_IDCARD">
						<input id="idcardPositivePath" name="image" type="hidden" value="<s:property value="unit.sysUnitImages[0].path"/>"/>
					</td>
				</tr>
				<tr class="idCardImage">
					<td class="label"></td>
					<td>
						<div id="idcardPositive">
							<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@identity_card">
								<img alt="" src="<%=com.zuipin.util.QiniuUtil.URL%><s:if test="unit.sysUnitImages[0].path != null"><s:property value="unit.sysUnitImages[0].path"/></s:if>" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:if>
							<s:else>
								<img alt="" src="" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:else>
								<%--<a href="javascript:void(0)" onclick="editSupplier.delImg()">删除</a>--%>
						</div>
					</td>
				</tr>
				<tr class="idCardImage">
					<td class="label"><font color="red">*&nbsp;</font>身份证反面照:</td>
					<td class="idCardImage_value">
						<input type="button" id="idCardOppositiveButton" value="插入图片" />
							<%--<span class="tip">最佳尺寸120*80</span>--%>
						<input type="hidden" value="OPPOSITIVE_IDCARD">
						<input id="idCardOppositivePath" name="image" type="hidden" value="<s:property value="unit.sysUnitImages[1].path"/>"/>
					</td>
				</tr>
				<tr class="idCardImage">
					<td class="label"></td>
					<td>
						<div id="idCardOppositive">

							<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@identity_card">
								<img alt="" src="<%=com.zuipin.util.QiniuUtil.URL%><s:if test="unit.sysUnitImages[1].path != null"><s:property value="unit.sysUnitImages[1].path"/></s:if>" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:if>
							<s:else>
								<img alt="" src="" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:else>

						</div>
					</td>
				</tr>
				<tr class="idCardImage">
					<td class="label"><font color="red">*&nbsp;</font>营业执照:</td>
					<td class="idCardImage_value">
						<input type="button" id="businessLicenseButton" value="插入图片" />
							<%--<span class="tip">最佳尺寸120*80</span>--%>
						<input type="hidden" value="BUSINESS_LICENSE">
						<input id="businessLicensePath" name="image" type="hidden" value="<s:property value="unit.sysUnitImages[2].path"/>"/>
					</td>
				</tr>
				<tr id="businessLicense" class="idCardImage">
					<td class="label"></td>
					<td>
						<div>
							<s:if test="unitDetail.certificateType==@com.data.data.hmly.service.entity.CertificateType@identity_card">
								<img alt="" src="<%=com.zuipin.util.QiniuUtil.URL%><s:if test="unit.sysUnitImages[2].path != null"><s:property value="unit.sysUnitImages[2].path"/></s:if>" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:if>
							<s:else>
								<img alt="" src="" width="185" height="100" style="padding:5px; border: 1px dashed #E3E3E3;">
							</s:else>

						</div>
					</td>
				</tr>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>商户类型:</td>
				<td>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="hotel" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@hotel">checked</s:if>/><span class="rdo-label">酒店民宿</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="sailboat" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@sailboat">checked</s:if>><span class="rdo-label">海上休闲</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="scenic" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@scenic">checked</s:if>><span class="rdo-label">景点门票</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="cruiseship" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@cruiseship">checked</s:if>><span class="rdo-label">邮轮旅游</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="other" <s:if test="unitDetail.supplierType==null || unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@other">checked</s:if>><span class="rdo-label">其他</span></div>
					<%--<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@hotel"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@hotel">checked</s:if>/><span class="rdo-label">酒店民宿</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@sailboat"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@sailboat">checked</s:if>><span class="rdo-label">海上休闲</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@scenic"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@scenic">checked</s:if>><span class="rdo-label">景点门票</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@cruiseship"/>" <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@cruiseship">checked</s:if>><span class="rdo-label">邮轮旅游</span></div>
					<div class="rdo-div"><input type="radio" class="left-block" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@ticket"/>" <s:if test="unitDetail.supplierType==null">checked</s:if>><span class="rdo-label">其他</span></div>--%>
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
			   	<td class="label"><font color="red">*&nbsp;</font>业务联系人:</td>
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
				<td class="label"><font color="red">*&nbsp;</font>银行名称:</td>
				<td>
					<input class="easyui-combobox" id="ipt-crtbnk" data-options="
					valueField:'id',
					textField:'name',
					prompt:'请选择银行',
					required:true,
					  data:editSupplier.bankNames
					" name="unitDetail.crtbnk" value="<s:property value="unitDetail.crtbnk"/>" style="width:240px;">
				</td>
			</tr>

			<tr>
				<td class="label"><font color="red">*&nbsp;</font>开户行省市:</td>
				<td>

					<input type="hidden" id="crtCityId" name="unitDetail.crtCity.id" value="<s:property value="unitDetail.crtCity.id"/>"/>
					<input id="crtCity" value="<s:property value="unitDetail.crtCity.id"/>"
						   class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="<s:property value="unitDetail.crtCity.father.father.id"/>" data-province="<s:property value="unitDetail.crtCity.father.id"/>" data-city="<s:property value="unitDetail.crtCity.id"/>">

					<%--<input type="hidden" id="cityId" name="unitDetail.crtCity.id" data-temp-name="unitDetail-crtCity-id" value="${unitDetail.crtCity.id}" required/>--%>
					<%--<input type="text" class="city_class" value="<s:if test="unitDetail.crtCity != null">${unitDetail.crtCity.father.name}-${unitDetail.crtCity.name}</s:if><s:else>请选择省市/区</s:else>" id="bank-province" readonly/>--%>
					<%--<input type="hidden" name="hproper" data-id="${unitDetail.crtCity.id}" id="hproper" value="${unitDetail.crtCity.name}">--%>
					<%--<input type="hidden" name="hcity" data-id="${unitDetail.crtCity.father.id}" id="hcity" value="${unitDetail.crtCity.father.name}">--%>
				</td>
			</tr>

			<tr>
				<td class="label"><font color="red">*&nbsp;</font>银行开户名:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtnam" value="<s:property value="unitDetail.crtnam"/>" style="width:240px;" required="true">
				</td>
			</tr>

			<tr>
				<td class="label"><font color="red">*&nbsp;</font>银行帐号:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.crtacc" value="<s:property value="unitDetail.crtacc"/>" style="width:240px;" required="true" data-options="validType:['crtacc']">
				</td>
			</tr>

			<tr>
				<td class="label">经营范围:</td>
				<td>
					<input class="easyui-textbox" name="unitDetail.mainBusiness" value="<s:property value="unitDetail.mainBusiness"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:60px;width:320px;"/>
					<div class="tip">500字内</div>
				</td>
			</tr>
        	<tr>
			   	<td class="label">公司简介:</td>
			   	<td>
		   			<input class="easyui-textbox" name="unitDetail.introduction" value="<s:property value="unitDetail.introduction"/>" data-options="multiline:true,validType:'maxLength[500]'" style="height:60px;width:320px;"/>
		   			<div class="tip">500字内</div>
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
											<a href="javascript:void(0)" onclick="editSupplier.download('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>')">下载</a>&nbsp;&nbsp;
										</s:if>
										<s:else>
											<a href="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>">下载</a>&nbsp;&nbsp;
										</s:else>


										<a href="javascript:void(0)"  onclick="editSupplier.delTr(<s:property value="#q.index"/>)">删除</a>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<input type="hidden" id="upload" value=""/>
					<input type="button" style="width:150px; float: right" id="uploadButton" value="添加附件"/>

				</td>
			</tr>
		</table>
			<span id="hidden_span">

			</span>

	    </form>
	</div>
	<%--<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">--%>
		<div style="text-align:left;padding:5px;height:30px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editSupplier.doSave()">保存</a>
		</div>
	<div id="downDialog" class="easyui-dialog" title="图片浏览" style="width:600px;height:500px;"
		 data-options="resizable:true, closed: true, modal:true">
		<div style="padding: 10px;">
			<img id="viewImg" src="" style="width: 98%; height: 95%;">
		</div>
	</div>


<%--</div>--%>
<%--</div>--%>
</body>
</html>
