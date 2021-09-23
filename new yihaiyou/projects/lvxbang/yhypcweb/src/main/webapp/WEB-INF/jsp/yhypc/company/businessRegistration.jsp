<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
	<script type="text/javascript">
		var QINIU_BUCKET_URL = '<%=com.zuipin.util.QiniuUtil.URL%>';
		var COMPANY_LOGIN_URL = '<s:property value="companyLoginUrl"/>';
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="/lib/webuploader/webuploader.css">
	<link rel="stylesheet" href="/lib/cityselect/selectCity.css">
	<link rel="stylesheet" type="text/css" href="/css/public/public.css">
	<link rel="stylesheet" type="text/css" href="/css/company/registration.css">
	<script src="/lib/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/lib/webuploader/webuploader.min.js"></script>
	<script type="text/javascript" src="/js/public/custom_plugins.js"></script>
	<script type="text/javascript" src="/lib/cityselect/citySet.js"></script>
	<script type="text/javascript" src="/lib/cityselect/Popt.js"></script>
	<script src="/js/company/registration.js"></script>
	<title>商户注册</title>
</head>
<body>
<div class="mainBox">
	<div class="header">
		<div class="selectBar"></div>
	</div>
	<div class="atentionTitle">完善以下信息，即可加盟一海游：</div>
	<div class="partTitle">账号信息</div>
	<form id="companyInfo-form" method="post">
		<div class="IDmess clearfix">
			<div class="writeBox">
				<ul>
					<li>
						<span class="lefttitle"><label class="import">*</label>用户名</span>
						<input type="text" name="user.account" data-temp-name="user-account" require valid="loginAccount"/>
					</li>
					<li>
						<span class="lefttitle"><label class="import">*</label>联系人</span>
						<input type="text" name="user.userName" data-temp-name="user-userName" require valid="notnull"/>
					</li>
					<li><span class="lefttitle">
						<label class="import">*</label>手机号</span>
						<input type="text" name="user.mobile" data-temp-name="user-mobile" require valid="mobile"/>
					</li>
					<li><span class="lefttitle noi"><label class="noimport">*</label>邮箱</span>
						<input type="text" name="user.email" data-temp-name="user-email" valid="email"/>
					</li>
					<li><span class="lefttitle noi"><label class="noimport">*</label>QQ</span>
						<input type="text" name="user.qqNo" data-temp-name="user-qqNo" valid="QQ"/>
					</li>
				</ul>
			</div>
			<div class="cooperateProgress">
				<div class="progressBox">
					<h3 class="proTitle">合作流程</h3>
					<div class="protabe">
						<p><span class="number">1</span>提交合作信息</p>
						<p><span class="number">2</span>一海游致电合作方</p>
						<p><span class="number">3</span>签约确认合作</p>
					</div>
				</div>
			</div>
		</div>
		<div class="partTitle">商户信息</div>
		<div class="IDmess clearfix">
			<div class="writeBox writeBox_business">
				<ul>
					<li><span class="lefttitle"><label class="import">*</label>商户名称</span>
						<input type="text" name="unit.name" data-temp-name="unit-name" require valid="notnull"/>
					</li>
					<li><span class="lefttitle"><label class="import">*</label>所在地</span>
						<div class="outinput">
							<%--<input class="areainput" type="text" value="${unit.area.father.father.name}${unit.area.father.name}${unit.area.name}"  readonly/>--%>
							<input type="hidden" id="hid-area-id" name="unit.area.id" value="350200" data-temp-name="unit-area-id" require valid="notnull"/>
							<div class="outinput">
								<input type="text" class="IDsel" value="厦门" id="sel-area"/>
								<ul class="areaList">
									<li data-city-id="350100">福州</li>
									<li data-city-id="350200" class="selSpan">厦门</li>
									<li data-city-id="350300" >莆田</li>
									<li data-city-id="350400" >三明</li>
									<li data-city-id="350500" >泉州</li>
									<li data-city-id="350600" >漳州</li>
									<li data-city-id="350700" >南平</li>
									<li data-city-id="350800" >龙岩</li>
									<li data-city-id="350900" >宁德</li>
								</ul>
							</div>
						</div>
						<span class="Validform_checktip Validform_wrong areatip" style="display: none;">您还没有选择省份或者城市！</span>
						<%--<span class="erroratention" style="display: none;"><span class="gth">!</span><span id="error-tips">您还没有选择省份或者城市</span></span>--%>
					</li>
					<li><span class="lefttitle"><label class="import">*</label>公司地址</span>
						<input type="text" name="unit.address" data-temp-name="unit-address" require valid="notnull"/></li>
					<li><span class="lefttitle"><label class="import">*</label>企业法人</span>
						<input type="text" name="unitDetail.legalPerson" data-temp-name="unitDetail-legalPerson" require valid="notnull"/></li>
					<li><span class="lefttitle"><label class="import">*</label>法人证件号</span>

						<div class="outinput">
							<input type="text" class="IDsel" value="身份证" id="IDsel"/>
							<ul class="IDcarList">
								<li data-type="identity_card">身份证</li>
								<li data-type="passport">护照</li>
							</ul>
						</div>

						<%--<div class="outinput">
							<input type="text" class="IDsel" value="身份证" id="IDsel"/>
							<ul class="IDcarList">
								<li data-type="identity_card" class="selSpan">身份证</li>
								<li data-type="passport">护照</li>
							</ul>
						</div>--%>
						<input type="hidden" name="unitDetail.certificateType" id="hid-idsel" value="identity_card" data-temp-name="unitDetail-certificateType" require valid="notnull"/>
						<input type="text" name="unitDetail.legalIdCardNo" data-temp-name="unitDetail-legalIdCardNo" require valid="notnull"/></li>

					<li class="idcard-li">
						<span class="lefttitle">
							<label class="import">*</label>身份证正面照
						</span>
						<span class="upBtn" id="zheng-idcard-img">上传照片</span>
						<span class="erroratention zheng-idcard-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
					</li>
					<div class="photoArea idcard z-idcard">
						<input type='hidden' name="imgpath" data-temp-name="imgpath-z-idcard" require>
						<%--<img src="/image/face_none.png">--%>
					</div>
					<li class="idcard-li">
					<span class="lefttitle">
						<label class="import">*</label>身份证反面照
					</span>
						<span class="upBtn" id="fan-idcard-img">上传照片</span>
						<span class="erroratention fan-idcard-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
					</li>
					<div class="photoArea idcard f-idcard">
						<input type='hidden' name="imgpath" data-temp-name="imgpath-f-idcard" require>
						<%--<img src="/image/face_none.png">--%>
					</div>


					<li class="passport-li">
					<span class="lefttitle">
						<label class="import">*</label>护照
					</span>
						<span class="upBtn" id="passport-img">上传照片</span>
						<span class="erroratention passport-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
					</li>
					<div class="photoArea passport">
						<input type='hidden' name="imgpath" data-temp-name="imgpath-passport" require>
						<%--<img src="/image/face_none.png">--%>
					</div>

					<li>
						<span class="lefttitle">
							<label class="import">*</label>营业执照
						</span>
						<span class="upBtn" id="business-img">上传照片</span>
						<span class="erroratention business-img-tip"  style="display: none;"><span class="gth">!</span>请上传图片</span>
					</li>
					<div class="photoArea photoAreaMax business">
						<input type='hidden' name="imgpath" data-type='BUSINESS_LICENSE' data-temp-name="imgpath-business" require>
						<%--<img src="/image/face_none.png">--%>
					</div>
					<li><span class="lefttitle"><label class="import">*</label>商户类型</span>
						<input type="hidden" name="unitDetail.supplierType" value="hotel" data-temp-name="unitDetail-supplierType" require valid="notnull">
						<span class="busi_type busi_type_check" data-value="hotel">酒店民宿</span>
						<span class="busi_type" data-value="sailboat">海上休闲</span>
						<span class="busi_type" data-value="scenic">景点门票</span>
						<span class="busi_type" data-value="cruiseship">邮轮旅游</span>
						<span class="busi_type" data-value="other">其他</span>
					</li>
					<li><span class="lefttitle"><label class="import">*</label>固定电话</span>
						<input type="text" name="unitDetail.telphone" data-temp-name="unitDetail-telphone" require valid="tel"/></li>
					<li><span class="lefttitle"><label class="import">*</label>业务联系人</span>
						<input type="text" name="unitDetail.contactName" data-temp-name="unitDetail-contactName" require valid="notnull"/></li>
					<li><span class="lefttitle"><label class="import">*</label>手机号</span>
						<input type="text" name="unitDetail.mobile" data-temp-name="unitDetail-mobile" require valid="mobile1"/></li>

					<li>
						<span class="lefttitle"><label class="import">*</label>银行名称</span>
						<%--<input type="text" name="unitDetail.crtbnk" data-temp-name="unitDetail-crtbnk" require valid="notnull"/>--%>
						<select style="width: 202px; height: 32px; font-size: 14px;color:#333;text-indent:10px;" name="unitDetail.crtbnk" data-temp-name="unitDetail-crtbnk" require valid="notnull">
							<option value="" selected>请选择银行</option>
							<option value="中国工商银行">中国工商银行</option>
							<option value="中国农业银行">中国农业银行</option>
							<option value="中国银行">中国银行</option>
							<option value="中国建设银行">中国建设银行</option>
							<option value="交通银行">交通银行</option>
							<option value="中信实业银行">中信实业银行</option>
							<option value="中国光大银行">中国光大银行</option>
							<option value="华夏银行">华夏银行</option>
							<option value="中国民生银行">中国民生银行</option>
							<option value="广东发展银行">广东发展银行</option>
							<option value="深圳发展银行">深圳发展银行</option>
							<option value="招商银行">招商银行</option>
							<option value="兴业银行">兴业银行</option>
							<option value="上海浦东发展银行">上海浦东发展银行</option>
							<option value="城市商业银行">城市商业银行</option>
							<option value="农村商业银行">农村商业银行</option>
							<option value="国家开发银行">国家开发银行</option>
							<option value="中国进出口银行">中国进出口银行</option>
							<option value="中国农业发展银行">中国农业发展银行</option>
							<option value="城市信用社">城市信用社</option>
							<option value="农村合作银行">农村合作银行</option>
							<option value="邮政储蓄">邮政储蓄</option>
							<option value="其他银行">其他银行</option>
						</select>

					</li>

					<li>
						<span class="lefttitle"><label class="import">*</label>开户行省市</span>

						<input type="text" class="IDsel" value="请选择省市/区" id="bank-province" readonly/>
						<input type="hidden" id="cityId" name="unitDetail.crtCity.id" data-temp-name="unitDetail-crtCity-id" value="" required valid="notnull"/>
						<%--<span class="Validform_checktip Validform_wrong areatip" style="display: none;">您还没有选择省份或者城市！</span>--%>
					</li>

					<li>
						<span class="lefttitle"><label class="import">*</label>银行开户名</span>
						<input type="text" name="unitDetail.crtnam" data-temp-name="unitDetail-crtnam" require valid="notnull"/>
					</li>
					<li>
						<span class="lefttitle"><label class="import">*</label>银行帐号</span>
						<input type="text" name="unitDetail.crtacc" data-temp-name="unitDetail-crtacc" require valid="crtacc"/>
					</li>

					<li class="relactive" style="margin-top: 30px;position:relative"><span class="lefttitle noi verticalTop"><label class="noimport">*</label>经营范围</span>
						<textarea class="textA" name="unitDetail.mainBusiness"></textarea><span class="wordNum wordNum-mainBusiness">0/500</span>
					</li>
					<li class="relactive" style="margin-top: 30px;"><span class="lefttitle noi verticalTop"><label class="noimport">*</label>公司简介</span>
						<textarea class="textA" name="unitDetail.introduction"></textarea><span class="wordNum wordNum-introduction">0/500</span>
					</li>

					<li><a class="handIn submit" onclick="CompanyInfoEdit.doSaveInfo()">提交</a></li>
					<li class="hid-form-data">
					</li>
				</ul>
			</div>
		</div>
	</form>

</div>
</body>
</html>