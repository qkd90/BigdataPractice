<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>供销商入驻</title>
<link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
<link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
<link href="${mallConfig.resourcePath}/css/ruzhu.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-xs-4">
    <div class="logo pull-left"><a href="/"><img src="${mallConfig.resourcePath}${mallConfig.logoPath}"></a></div>
    <div class="top-title pull-left">供销商入驻</div>
    </div>
  </div>
</div>

<div class="clearfix ruzhu-bg">
  <div class="container">
    <div class="row">
      <div class="col-xs-8">
        <div class="box" style="position: relative;">
          <h4 class="title">请提供以下合作信息</h4>
          <h5 class="subtitle">联系人信息</h5>
          <form class="form-horizontal" id="ruzhu" action="/mall/supplier/doInivited.jhtml" method="post">
			<input name="uid" type="hidden" value="<s:property value="uid"/>"/>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>您的姓名：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="userName" name="user.userName"></div>
             <div class="col-xs-6 validate-message""></div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>您的手机号：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="account" name="user.account"></div>
             <div class="col-xs-6 validate-message" id="account-message">该手机号码将作为登录账号使用，请慎重填写，不可更改</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>您的邮箱：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="email" name="user.email"></div>
             <div class="col-xs-6 validate-message">该邮箱注册后将不可更改,请确保该邮箱可用</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>QQ：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="qqNo" name="user.qqNo"></div>
             <div class="col-xs-6 validate-message"></div>
           </div>
           <hr>
          <h5 class="subtitle">企业信息</h5>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>公司名称：</label>
             <div class="col-xs-5"><input type="text" class="form-control" id="name" name="unit.name"></div>
             <div class="col-xs-5 validate-message">需和您的营业执照上公司名称相一致</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>品牌名：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="brandName" name="unitDetail.brandName"></div>
             <div class="col-xs-5 validate-message">同业合作品牌名称，如"环宇假期"</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>所在地：</label>
             <div class="col-xs-5">
             <select id="provinceId" name="provinceId" > 
              <option value="">请选择</option> 
			  <s:iterator value="areas" status="stuts">  
			  <option value="<s:property value="id"/>"><s:property value="name"/></option> 
			  </s:iterator> 
             </select>
             <select id="areaId" name="areaId"> 
              <option value="">请选择</option> 
             </select>
             </div>
             <div class="col-xs-5 validate-message"></div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>公司地址：</label>
             <div class="col-xs-5"><input type="text" class="form-control" id="address" name="unit.address"></div>
             <div class="col-xs-5 validate-message">公司详细地址</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>公司LOGO：</label>
             <div class="col-xs-2">
             	<!-- 真实上传按钮位置，因为上传图片会出现嵌套form导致jquery validate失效，采用按钮位置相对位置 -->
             	<!-- <input type="button" id="uploadButton" value="插入图片" /> -->
				<input id="filePath" name="filePath" type="hidden" value="<s:property value="filePath"/>"/>
             </div>
             <div class="col-xs-6 validate-message">最佳尺寸120*80</div>
           </div>
           <div class="form-group" id="imgView" style="<s:if test="filePath == null">display:none;</s:if>">
             <label for="tel" class="col-xs-2 text-right"></label>
             <div class="col-xs-8">
			    <div>
			    	<img alt="" src="<s:if test="filePath != null">/static<s:property value="filePath"/></s:if>" width="120" height="80" style="padding:5px; border: 1px dashed #E3E3E3;"><br>
			    	<a href="javascript:void(0)" onclick="inivited.delImg()">删除</a>
			    </div>
             </div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>供应商类型：</label>
             <div class="col-xs-10">
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@zhuangxiang"/>" checked>专线</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@zhonghe"/>">综合</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@dijie"/>">地接</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@chujing"/>">出境</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@ticket"/>">票务</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.supplierType" value="<s:property value="@com.data.data.hmly.service.entity.SupplierType@hotel"/>">酒店</label>
             </div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>经营范围：</label>
             <div class="col-xs-10">
               <label class="radio-inline"><input type="radio" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@inlang"/>" checked>国内社</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@chujing"/>">出境社</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@intenal"/>">国际社</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessScope" value="<s:property value="@com.data.data.hmly.service.entity.BusinessScope@other"/>">其他</label>
             </div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>业务类别：</label>
             <div class="col-xs-10">
               <label class="radio-inline"><input type="radio" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@dijie"/>">地接</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@zhutuan"/>">组团</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessType" value="<s:property value="@com.data.data.hmly.service.entity.BusinessType@zhonghe"/>" checked>综合</label>
             </div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>固定电话：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="telphone" name="unitDetail.telphone"></div>
             <div class="col-xs-5 validate-message">例010-12345678</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>传真：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="fax" name="unitDetail.fax"></div>
             <div class="col-xs-5 validate-message"></div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>业务主体：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="mainBody" name="unitDetail.mainBody"></div>
             <div class="col-xs-5 validate-message">合作分公司/部门名称</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>经营模式：</label>
             <div class="col-xs-10">
               <label class="radio-inline"><input type="radio" name="unitDetail.businessModel" value="<s:property value="@com.data.data.hmly.service.entity.BusinessModel@zhishu"/>" checked>直属</label>
               <label class="radio-inline"><input type="radio" name="unitDetail.businessModel" value="<s:property value="@com.data.data.hmly.service.entity.BusinessModel@guakao"/>">挂靠</label>
             </div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>主营业务：</label>
             <div class="col-xs-8"><textarea class="form-control" rows="5" name="unitDetail.mainBusiness"></textarea></div>
             <div class="col-xs-8 col-xs-offset-2 validate-message">500字内</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-2 text-right"><i>*</i>公司简介：</label>
             <div class="col-xs-8"><textarea class="form-control" rows="5" name="unitDetail.introduction"></textarea></div>
             <div class="col-xs-8 col-xs-offset-2 validate-message">500字内</div>
           </div>
           <hr>
          <h5 class="subtitle">其他信息</h5>
           <div class="form-group">
             <label for="tel" class="col-xs-3 text-right"><i>*</i>合作网络渠道：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="partnerChannel" name="unitDetail.partnerChannel"></div>
             <div class="col-xs-5 validate-message"></div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-3 text-right"><i>*</i>对应合作页面：</label>
             <div class="col-xs-4"><input type="text" class="form-control" id="partnerUrl" name="unitDetail.partnerUrl"></div>
             <div class="col-xs-5 validate-message">合作页面链接</div>
           </div>
           <div class="form-group">
             <label for="tel" class="col-xs-3 text-right"><i>*</i>优势说明：</label>
             <div class="col-xs-8"><textarea class="form-control" rows="5" name="unitDetail.partnerAdvantage"></textarea></div>
             <div class="col-xs-8 col-xs-offset-3 validate-message">请简要说明贵公司操作的主要产品线和优势产品，150字内</div>
           </div>
           <div class="form-group">
             <div class="col-xs-8 col-xs-offset-3"><input id="submit-button" name="" type="button" class="btn btn-warning rz-btn" value="提交申请"></div>
           </div>
          </form>
          <div style="position:absolute;left:150px;top:580px;">
          	<input type="button" id="uploadButton" value="插入图片"/>
          </div>
        </div>
      </div>
      <div class="col-xs-4">
        <div class="box">
          <h4 class="title">合作流程</h4>
          <div class="box-content">
          第一步：申请成为供应商<br>第二步：提交认证资料<br>(根据您提供的电话进行反馈与沟通)<br>第三步：签订合同<br>第四步：成功入驻<br>
          </div>
        </div>
        <div class="box">
          <h4 class="title">常见问题</h4>
          <div class="box-content">
          1.供应商入驻有什么资质要求？<br>
1）须提供企业法人和供销平台业务负责人的
     身份证的原件扫描件或复印件；<br>
2）须持有并提交最新有效年检的《企业法人营业
      执照》副本的原件扫描件，且拟在供销平台开展
      的经营活动不超过其核准的经营范围；<br>
3）须持有并提交有效《旅行社业务经营许可证》
     的原件扫描件，且拟在供销平台开展的经营活动
     不超过其许可经营业务范围；<br>
4）复印件、原件的扫描件及原件照片都需要加盖
     申请企业公章。所有须企业盖章的文件上只接受
     行政公章，不接受电子章、专用章等，合同中可
     加盖合同专用章。<br>

2.哪些类型的产品可以合作？<br>
以下产品可以合作：跟团游，度假，门票，酒店。<br>

3.提交合作信息后多长时间会给予回复？<br>
提交合作信息1个工作日内回复。
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--反馈成功弹窗-->
<input id="unitId" type="hidden" value="<s:property value="unitId"/>"/>
<div class="modal fade ok" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog">
    <div class="md-title"><a href="#" data-dismiss="modal" class="pull-right">X</a></div>
    <div class="md-content">
      <h4>入驻成功!<small>请耐心等待审核通过！点击确定返回首页</small></h4>
    </div>
    <div class="md-qd text-center"><a href="javascript:void(0)" onclick="inivited.doOk()" data-dismiss="modal" class="btn btn-primary">确定</a></div>
    </dl>
  </div>
</div>
<!--反馈成功弹窗--> 

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jquery.validation.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/validator-addMethod.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/messages_zh.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
<link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/js/kindeditor/themes/default/default.css?v=${mallConfig.resourceVersion}">
<script type="text/javascript" src="${mallConfig.resourcePath}/js/kindeditor/kindeditor-min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/mall/supplier/inivited.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
