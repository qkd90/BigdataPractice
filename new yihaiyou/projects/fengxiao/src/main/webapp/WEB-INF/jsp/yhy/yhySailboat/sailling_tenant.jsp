<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_tenant.css">
    <script src="../src/js/homestay.js"></script>
    <title>海上休闲/商户信息</title>
</head>
<body  class="sailTenant">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<input type="hidden" id="userId" value="${user.id}">
	<div class="accountMessage">
		<div class="detail_header">
			<span class="detail_title">账号信息</span>
		</div>
		<ul class="account_detailUl accUl">

			<li>用户名：<span class="username" model="account">${companyInfoData.account}</span></li>
			<li>密码：<span class="password" >********</span> <input type="hidden" model="password" id="passwordId"> <span class="rewrite" id="changePassword">修改密码</span></li>
			<li>姓名：<span model="userName">${companyInfoData.userName}</span>
				<%--<span class="rewrite" id="changeName"><input id="userNameId" type="hidden" model="userName" value="${companyInfoData.userName}">修改姓名</span>--%>
			</li>
			<li>手机号：<span model="mobile">${companyInfoData.mobile}</span><%--<span class="showL">绑定</span><span class="operateL">绑定</span>--%>
				<%--<input id="mobileId" type="hidden" model="mobile" value="${companyInfoData.mobile}"><span class="rewrite" id="changePhoneNum">修改手机号</span>--%>
			</li>

			<%--<li>用户名：<span class="username" model="account"></span></li>
			<li>密码：<span class="password">**********</span><span class="rewrite" id="changePassword">修改密码</span></li>
			<li>姓名：<span model="userName"></span><input id="userNameId" type="hidden" model="userName"><span class="rewrite" id="changeName" >修改姓名</span></li>
			<li>手机号：<span model="mobile"></span> <input id="mobileId" type="hidden" model="mobile"> <span class="rewrite" id="changePhoneNum">修改手机号</span></li>--%>
		</ul>
	</div>
	<div class="commpenyMessage">
		<div class="detail_header">
			<span class="detail_title">企业信息</span>
			<s:if test="#companyInfoData.status!=0">
				<span class="modify_message" onclick="SailboatTenant.goCompanyEditInfo()">修改企业信息</span>
			</s:if>
		</div>
		<ul class="account_detailUl company_info">
			<input type="hidden" id="contractId" value="${companyInfoData.contractId}">
			<li>合同起始时间：<span model="effectiveTime">
					<s:if test="companyInfoData.effectiveTime != null">
						<s:date name="companyInfoData.effectiveTime" format="yyyy-MM-dd"></s:date>
					</s:if>
					<s:elseif test="companyInfoData.effectiveTime == null">
						请签约合同
					</s:elseif>
				</span></li>
			<li>合同终止时间：<span model="expirationTime">
					<s:if test="companyInfoData.expirationTime != null">
						<s:date name="companyInfoData.expirationTime" format="yyyy-MM-dd"></s:date>
					</s:if>
					<s:elseif test="companyInfoData.expirationTime == null">
						请签约合同
					</s:elseif>
				</span></li>
			<li>结算周期： <span model="settlementType">${companyInfoData.settlementType}</span></li>
			<li>计价模式： <span model="valuationModels">${companyInfoData.valuationModels}</span></li>
			<li>企业法人： <span model="legalPerson">${companyInfoData.legalPerson}</span></li>
			<s:if test="companyInfoData.certificateType=='identity_card'">
				<li>法人身份证：<span model="legalIdCardNo">${companyInfoData.legalIdCardNo}</span></li>
			</s:if>
			<s:else>
				<li>法人护照：<span model="passportNo">${companyInfoData.legalIdCardNo}</span></li>
			</s:else>
			<s:if test="companyInfoData.mainBusiness != null">
				<li class="clearfix">经营范围：<span model="mainBusiness" class="bussiRange">${companyInfoData.mainBusiness}</span></li>
			</s:if>
			<s:if test="companyInfoData.certificateType=='identity_card'">
				<li class="idBox clearfix">
					身份证：
					<div class="idBackground">
						<s:if test="companyInfoData.positiveIdcard != null">
							<img model="positiveIdcard" src="${companyInfoData.positiveIdcard}">
						</s:if>
						<s:else>
							<img model="positiveIdcard" src="/images/yhy/card02.png">
						</s:else>
					</div>
					<div class="idFace">
						<s:if test="companyInfoData.oppositiveIdcard != null">
							<img model="oppositiveIdcard" src="${companyInfoData.oppositiveIdcard}">
						</s:if>
						<s:else>
							<img model="oppositiveIdcard" src="/images/yhy/card01.png">
						</s:else>

					</div>
				</li>
			</s:if>
			<s:else>
				<li class="idBox clearfix">
					护照：
					<div class="idBusiness">
						<s:if test="companyInfoData.passportImg != null">
							<img model="positiveIdcard" src="${companyInfoData.passportImg}">
						</s:if>
						<s:else>
							<img model="positiveIdcard" src="/images/yhy/card02.png">
						</s:else>
					</div>
				</li>
			</s:else>
			<li class="idBox clearfix">
				营业执照：

				<div class="idBusiness">
					<s:if test="companyInfoData.businessLicenseImg != null">
						<img model="businessLicenseImg" src="${companyInfoData.businessLicenseImg}">
					</s:if>
					<s:else>
						<img model="businessLicenseImg" src="/images/yhy/card03.png">
					</s:else>
				</div>
			</li>
		</ul>
	</div>
	<div class="accountMessage">
		<div class="detail_header">
			<span class="detail_title">合同附件</span>
		</div>
		<ul class="account_detailUl accUl">
			<s:iterator value="companyInfoData.appendiceses" var="appendices" status="q">
				<li><s:property value="#appendices.name"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<s:if test="#appendices.type==@com.data.data.hmly.service.contract.entity.nums.ContractAppendiceType@image">
						<a href="javascript:void(0)" onclick="SailboatTenant.openDownLoad('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#appendices.path"/>')">下载</a>&nbsp;&nbsp;
					</s:if>
					<s:else>
						<a href="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#appendices.path"/>">下载</a>&nbsp;&nbsp;
					</s:else>
					<%--<a href="javascript:void(0)" onclick="SailboatTenant.openDownLoad('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#appendices.path"/>', '<s:property value="#appendices.name"/>')">下载</a>--%>
				</li>
					<%--<a href="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#appendices.path"/>">下载</a></li>--%>
			</s:iterator>
		</ul>
	</div>
	<div class="accountMessage">
		<div class="detail_header">
			<span class="detail_title">商户资质</span>
		</div>
		<ul class="account_detailUl accUl">
			<s:iterator value="companyInfoData.qualificationList" var="qualification" status="q">
				<li><s:property value="#qualification.name"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<s:if test="#qualification.type==@com.data.data.hmly.service.entity.DoucementType@image">
						<a href="javascript:void(0)" onclick="SailboatTenant.openDownLoad('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>')">下载</a>&nbsp;&nbsp;
					</s:if>
					<s:else>
						<a href="<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>">下载</a>&nbsp;&nbsp;
					</s:else>
					<%--<a href="javascript:void(0)" onclick="SailboatTenant.openDownLoad('<%=com.zuipin.util.QiniuUtil.URL%><s:property value="#qualification.path"/>', '<s:property value="#qualification.name"/>')">下载</a>--%>
				</li>
			</s:iterator>
		</ul>
	</div>
<!-- 	修改密码 -->
	<div class="modal fade changePasswordBox" id="revisePassword">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<form id="editPasswordFormId">
					<div class="modal-body clearfix">

						<table style="width: 100%">
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle">
										原密码
									</div>
								</td>
								<td>
									<div class="form-group input-group" style="margin-bottom: 0px;">
										<input type="password" name="oldPassword" class="form-control">
									</div>
								</td>
							</tr>
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle">
										新密码
									</div>
								</td>
								<td>
									<div class="form-group input-group" style="margin-bottom: 0px;">
										<input type="password" name="newPassword" class="form-control">
									</div>
								</td>
							</tr>
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle">
										确认新密码
									</div>
								</td>
								<td>
									<div class="form-group input-group" style="margin-bottom: 0px;">
										<input type="password" name="reNewPassword" class="form-control">
									</div>
								</td>
							</tr>
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle" style="margin-top: 5px;">
										验证码
									</div>
								</td>
								<td>
									<div class="form-group input-group" style="margin-bottom: 0px;">
										<input type="text" class="form-control" name="validCode" style="width: 166px">
										<img src="/images/checkNum.jsp" style="height: 34px;margin-left: 4px;">
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">确认修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="modal fade changeNameBox" id="download">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">浏览图片</h4>
				</div>
				<img id="viewImg" src="" style="width: 98%; height: 95%;">
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 127px;">关闭</button>
				</div>
			</div>
		</div>
	</div>

<!-- 	修改姓名 -->
	<div class="modal fade changeNameBox" id="reviseName">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">修改姓名</h4>
				</div>
				<form id="userNameFormId">
					<div class="modal-body clearfix">

						<ul>
							<li>
								<div class="leftTitle">
									姓名
								</div>
								<div class="form-group input-group" style="margin-bottom: 0px;">
									<input type="text" name="userName" id="tempUserName" class="form-control" style="width: 200px">
								</div>
							</li>
						</ul>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">确认修改</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<!-- 	修改手机号 -->
	<div class="modal fade changePhoneBox" id="revisePhoneNum">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">修改手机号</h4>
				</div>
				<form id="validate_mobile">
					<div class="modal-body clearfix">
						<table style="width: 100%">
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle">
										原手机号
									</div>
								</td>
								<td>
									<div class="input-group" id="oldMobile">
									</div>
								</td>
							</tr>
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle">
										新手机号
									</div>
								</td>
								<td>
									<div style="width:100%;">
										<div class="form-group input-group" style="margin-bottom: 0px;float: left">
											<input type="text" id="newMobileId" name="mobile" class="form-control" style="width: 160px;">
										</div>
										<div id="sendSms" class="getCode" style="width: 120px;float: right; line-height: 28px;" onclick="SailboatTenant.getValidateMsgCode()">获取短信验证码</div>
									</div>
								</td>
							</tr>
							<tr style="height: 40px;">
								<td align="right" style="padding-right: 5px;">
									<div class="leftTitle" style="margin-top: 5px;">
										验证码
									</div>
								</td>
								<td>
									<div class="form-group input-group" style="margin-bottom: 0px;">
										<input type="text" class="form-control" name="validCode" style="width: 125px">
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">确认修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_tenant.js"></script>
</html>