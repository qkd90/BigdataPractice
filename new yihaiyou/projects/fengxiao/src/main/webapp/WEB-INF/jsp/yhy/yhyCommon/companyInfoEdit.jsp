<%@ page import="com.zuipin.util.QiniuUtil" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
	<%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyCommon/messageModify.css">
	<link rel="stylesheet" href="/lib/webuploader/webuploader.css">
	<link rel="stylesheet" href="/lib/cityselect/selectCity.css">

	<style type="text/css">
		.webuploader-pick {
			padding: 0px 47px!important;
		}
	</style>
    <title>商户信息修改</title>
</head>
<body>
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="mainBox">
		<div class="partTitle">商户信息</div>
			<form id="companyInfo-form" method="post">
				<div class="IDmess clearfix">
					<div class="writeBox writeBox_business">
						<ul>
							<li><span class="lefttitle"><label class="import">*</label>商户名称</span>
								<input type="hidden" name="unit.id" value="${unit.id}"/>
								<input type="hidden" name="user.id" value="${user.id}"/>
								<input type="text" name="unit.name" data-temp-name="unit-name" value="${unit.name}" required valid="notnull"/>
								<%--<span class="erroratention"><span class="gth">!</span>请填写正确的用户名</span>--%>
							</li>
							<li>
								<span class="lefttitle"><label class="import">*</label>所在地</span>
								<div class="outinput">
									<%--<input class="areainput" type="text" value="${unit.area.father.father.name}${unit.area.father.name}${unit.area.name}"  readonly/>--%>
									<input type="hidden" id="hid-area-id" name="unit.area.id" data-temp-name="unit-area-id" value="${unit.area.id}" required valid="notnull"/>
									<div class="outinput">
										<input type="text" class="IDsel" value="${unit.area.name}" id="sel-area"/>
										<ul class="areaList">
											<li data-city-id="350100" class="<s:if test="unit.area.id==350100">selSpan</s:if>">福州</li>
											<li data-city-id="350200" class="<s:if test="unit.area.id==350200">selSpan</s:if>">厦门</li>
											<li data-city-id="350300" class="<s:if test="unit.area.id==350300">selSpan</s:if>">莆田</li>
											<li data-city-id="350400" class="<s:if test="unit.area.id==350400">selSpan</s:if>">三明</li>
											<li data-city-id="350500" class="<s:if test="unit.area.id==350500">selSpan</s:if>">泉州</li>
											<li data-city-id="350600" class="<s:if test="unit.area.id==350600">selSpan</s:if>">漳州</li>
											<li data-city-id="350700" class="<s:if test="unit.area.id==350700">selSpan</s:if>">南平</li>
											<li data-city-id="350800" class="<s:if test="unit.area.id==350800">selSpan</s:if>">龙岩</li>
											<li data-city-id="350900" class="<s:if test="unit.area.id==350900">selSpan</s:if>">宁德</li>
										</ul>
									</div>
								</div>
								<span class="Validform_checktip Validform_wrong areatip" style="display: none;">您还没有选择省份或者城市！</span>
							</li>
							<li><span class="lefttitle"><label class="import">*</label>公司地址</span><input type="text" name="unit.address" data-temp-name="unit-adress" value="${unit.address}" required valid="notnull"/></li>
							<li><span class="lefttitle noi"><label class="noimport">*</label>邮箱</span><input type="text" name="user.email" value="${user.email}" valid="email"/></li>
							<li><span class="lefttitle noi"><label class="noimport">*</label>QQ</span><input type="text" name="user.qqNo" value="${user.qqNo}" valid="QQ"/></li>
							<li><span class="lefttitle"><label class="import">*</label>企业法人</span><input type="text" name="unitDetail.legalPerson" data-temp-name="unit-legalPerson" value="${unitDetail.legalPerson}" required valid="notnull"/></li>

							<li><span class="lefttitle"><label class="import">*</label>法人证件号</span>
								<div class="outinput">
									<input type="text" class="IDsel" value="身份证" id="IDsel"/>
									<ul class="IDcarList">
										<li data-type="identity_card">身份证</li>
										<li data-type="passport">护照</li>
									</ul>
								</div>
								<input type="hidden" name="unitDetail.certificateType" id="hid-idsel" data-temp-name="unitDetail-certificateType" value="${unitDetail.certificateType}" required/>
								<input type="text" name="unitDetail.legalIdCardNo" value="${unitDetail.legalIdCardNo}" data-temp-name="unitDetail-legalIdCardNo" required/></li>
							<s:if test="unitDetail.certificateType == @com.data.data.hmly.service.entity.CertificateType@identity_card">
								<input type="hidden" name="imagePath" value="${unit.sysUnitImages[0].path}" data-type="${unit.sysUnitImages[0].type}"/>
								<input type="hidden" name="imagePath" value="${unit.sysUnitImages[1].path}" data-type="${unit.sysUnitImages[1].type}"/>
							</s:if>
							<s:else>
								<input type="hidden" name="imagePath" value="${unit.sysUnitImages[0].path}" data-type="${unit.sysUnitImages[0].type}"/>
							</s:else>
							<li class="idcard-li">
								<span class="lefttitle">
									<label class="import">*</label>身份证正面照
								</span>
								<span class="upBtn" id="zheng-idcard-img">上传照片</span>
								<span class="erroratention zheng-idcard-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
							</li>
							<div class="photoArea idcard z-idcard">
								<input type='hidden' name="imgpath-z-idcard" data-temp-name="imgpath-z-idcard" required>
								<img src="/images/no_img.jpg">
							</div>
							<li class="idcard-li">
						<span class="lefttitle">
							<label class="import">*</label>身份证反面照
						</span>
								<span class="upBtn" id="fan-idcard-img">上传照片</span>
								<span class="erroratention fan-idcard-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
							</li>
							<div class="photoArea idcard f-idcard">
								<input type='hidden' name="imgpath-f-idcard" data-temp-name="imgpath-f-idcard" required>
								<img src="/images/no_img.jpg">
							</div>
							<li class="passport-li">
						<span class="lefttitle">
							<label class="import">*</label>护照
						</span>
								<span class="upBtn" id="passport-img">上传照片</span>
								<span class="erroratention passport-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
							</li>
							<div class="photoArea passport">
								<input type='hidden' name="imgpath-passport" data-temp-name="imgpath-passport" required>
								<img src="/images/no_img.jpg">
							</div>
							<li>
						<span class="lefttitle">
							<label class="import">*</label>营业执照
						</span>
								<span class="upBtn" id="business-img">上传照片</span>
								<span class="erroratention business-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
							</li>
							<div class="photoArea photoAreaMax business">
								<s:iterator value="unit.sysUnitImages" var="sysUnitImage">
									<s:if test="#sysUnitImage.type==@com.data.data.hmly.enums.SysUnitImageType@BUSINESS_LICENSE">
										<input type='hidden' name="imgpath-busines" data-temp-name="imgpath-busines" value="<s:property value="#sysUnitImage.path"/>" data-type='BUSINESS_LICENSE' required>
										<img src="<%=QiniuUtil.URL%><s:property value="#sysUnitImage.path"/>">
									</s:if>
								</s:iterator>
							</div>
							<li>
								<span class="lefttitle"><label class="import">*</label>商户类型</span>
								<input type="hidden" name="unitDetail.supplierType" data-temp-name="unitDetail-supplierType" value="${unitDetail.supplierType}" required valid="notnull">
								<span class="busi_type <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@hotel">busi_type_check</s:if>" data-value="hotel">酒店民宿</span>
								<span class="busi_type <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@sailboat">busi_type_check</s:if>" data-value="sailboat">海上休闲</span>
								<span class="busi_type <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@scenic">busi_type_check</s:if>" data-value="scenic">景点门票</span>
								<span class="busi_type <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@cruiseship">busi_type_check</s:if>" data-value="cruiseship">邮轮旅游</span>
								<span class="busi_type <s:if test="unitDetail.supplierType==@com.data.data.hmly.service.entity.SupplierType@other">busi_type_check</s:if>" data-value="other">其他</span>
							</li>
							<li><span class="lefttitle"><label class="import">*</label>固定电话</span><input type="text" name="unitDetail.telphone" data-temp-name="unitDetail-telphone" value="${unitDetail.telphone}" required valid="tel"/></li>
							<li><span class="lefttitle"><label class="import">*</label>业务联系人</span><input type="text" name="unitDetail.contactName" data-temp-name="unitDetail-contactName" value="${unitDetail.contactName}" required valid="notnull"/></li>
							<li><span class="lefttitle"><label class="import">*</label>手机号</span><input type="text" name="unitDetail.mobile" data-temp-name="unitDetail-mobile" value="${unitDetail.mobile}" required valid="mobile"/></li>

							<li><span class="lefttitle"><label class="import">*</label>银行名称</span>
								<%--<input type="text" name="unitDetail.crtbnk" data-temp-name="unitDetail-crtbnk" value="${unitDetail.crtbnk}" required valid="notnull"/>--%>
								<select name="unitDetail.crtbnk" data-temp-name="unitDetail-crtbnk" style="width: 198px; height: 32px; font-size: 16px;" required valid="notnull">
									<option value="" selected>请选择银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国工商银行"'>selected</s:if> value="中国工商银行">中国工商银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国农业银行"'>selected</s:if> value="中国农业银行">中国农业银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国工商银行"'>selected</s:if> value="中国银行">中国银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国建设银行"'>selected</s:if> value="中国建设银行">中国建设银行</option>
									<option <s:if test='unitDetail.crtbnk=="交通银行"'>selected</s:if> value="交通银行">交通银行</option>
									<option <s:if test='unitDetail.crtbnk=="中信实业银行"'>selected</s:if> value="中信实业银行">中信实业银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国光大银行"'>selected</s:if> value="中国光大银行">中国光大银行</option>
									<option <s:if test='unitDetail.crtbnk=="华夏银行"'>selected</s:if> value="华夏银行">华夏银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国民生银行"'>selected</s:if> value="中国民生银行">中国民生银行</option>
									<option <s:if test='unitDetail.crtbnk=="广东发展银行"'>selected</s:if> value="广东发展银行">广东发展银行</option>
									<option <s:if test='unitDetail.crtbnk=="深圳发展银行"'>selected</s:if> value="深圳发展银行">深圳发展银行</option>
									<option <s:if test='unitDetail.crtbnk=="招商银行"'>selected</s:if> value="招商银行">招商银行</option>
									<option <s:if test='unitDetail.crtbnk=="兴业银行"'>selected</s:if> value="兴业银行">兴业银行</option>
									<option <s:if test='unitDetail.crtbnk=="上海浦东发展银行"'>selected</s:if> value="上海浦东发展银行">上海浦东发展银行</option>
									<option <s:if test='unitDetail.crtbnk=="城市商业银行"'>selected</s:if> value="城市商业银行">城市商业银行</option>
									<option <s:if test='unitDetail.crtbnk=="农村商业银行"'>selected</s:if> value="农村商业银行">农村商业银行</option>
									<option <s:if test='unitDetail.crtbnk=="国家开发银行"'>selected</s:if> value="国家开发银行">国家开发银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国进出口银行"'>selected</s:if> value="中国进出口银行">中国进出口银行</option>
									<option <s:if test='unitDetail.crtbnk=="中国农业发展银行"'>selected</s:if> value="中国农业发展银行">中国农业发展银行</option>
									<option <s:if test='unitDetail.crtbnk=="城市信用社"'>selected</s:if> value="城市信用社">城市信用社</option>
									<option <s:if test='unitDetail.crtbnk=="农村合作银行"'>selected</s:if> value="农村合作银行">农村合作银行</option>
									<option <s:if test='unitDetail.crtbnk=="邮政储蓄"'>selected</s:if> value="邮政储蓄">邮政储蓄</option>
									<option <s:if test='unitDetail.crtbnk=="其他银行"'>selected</s:if> value="其他银行">其他银行</option>
								</select>
							</li>
							<li>
								<span class="lefttitle"><label class="import">*</label>开户行省市</span>
								<div class="outinput1" style="width: 540px">
									<input type="hidden" id="cityId" name="unitDetail.crtCity.id" data-temp-name="unitDetail-crtCity-id" value="${unitDetail.crtCity.id}" required/>
									<input type="text" class="IDsel" value="${unitDetail.crtCity.father.name}-${unitDetail.crtCity.name}" id="bank-province" readonly/>
									<input type="hidden" name="hproper" data-id="${unitDetail.crtCity.id}" id="hproper" value="${unitDetail.crtCity.name}">
									<input type="hidden" name="hcity" data-id="${unitDetail.crtCity.father.id}" id="hcity" value="${unitDetail.crtCity.father.name}">
								</div>
								<span class="Validform_checktip Validform_wrong areatip" style="display: none;">您还没有选择省份或者城市！</span>
							</li>
							<li><span class="lefttitle"><label class="import">*</label>银行开户名</span><input type="text" name="unitDetail.crtnam" data-temp-name="unitDetail-crtnam" value="${unitDetail.crtnam}" required valid="notnull"/></li>
							<li><span class="lefttitle"><label class="import">*</label>银行帐号</span><input type="text" name="unitDetail.crtacc" data-temp-name="unitDetail-crtacc" value="${unitDetail.crtacc}" required valid="crtacc"/></li>

							<li class="relactive"><span class="lefttitle noi verticalTop"><label class="noimport">*</label>经营范围</span>
								<textarea class="textA" name="unitDetail.mainBusiness">${unitDetail.mainBusiness}</textarea><span class="wordNum wordNum-mainBusiness">0/500</span>
							</li>
							<li class="relactive"><span class="lefttitle noi verticalTop"><label class="noimport">*</label>公司简介</span>
								<textarea class="textA" name="unitDetail.introduction">${unitDetail.introduction}</textarea><span class="wordNum wordNum-introduction">0/500</span>
							</li>
							<li class="hid-form-data">
							</li>
							<li><span class="handIn" onclick="CompanyInfoEdit.doSaveInfo()">提交</span></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<%--<div id="shawdom" style="position: fixed; height: 100%; width: 100%;z-index: 10; top: 0; left: 0; background-color: rgba(0, 0, 63, 0.2); display: none;"></div>--%>
	 <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/lib/webuploader/webuploader.min.js"></script>
<%--<script type="text/javascript" src="/lib/cityselect/cityJson.js"></script>--%>
<script type="text/javascript" src="/lib/cityselect/citySet.js"></script>
<script type="text/javascript" src="/lib/cityselect/Popt.js"></script>
<script src="/js/yhy/yhyCommon/messageModify.js"></script>
</html>