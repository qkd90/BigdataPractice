<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><s:property value="ticketDisplay.name"/></title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/shangjia.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    tr.odd {background-color:#FFF;}
	tr.even {background-color:#F7F7F7;}
	.orange-bold {color: orange !important;font-weight: bold;}
    </style>
</head>
<body>
<!--头部开始-->
<jsp:include page="/WEB-INF/jsp/mall/common/header.jsp"></jsp:include>
<!--头部结束-->

<!--内容开始-->
<div class="clearfix container-bg">
  <div class="container">
    <div class="row">
      <div class="box sqfenxiao">
        <form class="form-horizontal">
        	<input id="productId" type="hidden" value="<s:property value="ticketDisplay.id"/>">
          <div class="form-group">
            <label class="col-xs-2 control-label">产品名称：</label>
            <div class="col-xs-7">
              <p class="form-control-static"><input id="ticketName" type="text" class="form-control"  value="<s:property value="ticketDisplay.name"/>" maxlength="150"></p>
              <p class="form-control-static-sub">建议您修改产品名称，精准的产品名称描述能有效增加游客订单</p>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-2 control-label">产品价格：</label>
            <div class="col-xs-7">
	          <!--价格阶梯-->
	          <div class="info-price-list text-center clearfix">
	          	<table class="table table-striped table-hover table-bordered">
		            <thead>
		            <tr>
		                <th style="text-align: center">价格类型</th>
		                <th style="text-align: center">分销价</th>
		                <th style="text-align: center">佣金</th>
		            </tr>
		            </thead>
		            <tbody>
			   		<s:iterator value="ticketPrices" status="stuts">   
		            <s:if test="#stuts.odd == true">
	                <tr class="odd">
	                </s:if>
	                <s:else>
	                <tr class="even">
	                </s:else>
		                <td><s:property value="name"/></td>
                    	<td><span class="orange-bold"><span class="minDiscountPrice" tickettypepriceId="<s:property value="id"/>">0</span></span>元起</td>
                    	<td><span class="orange-bold"><span class="minCommission" tickettypepriceId="<s:property value="id"/>">0</span></span>元起</td>
		            </tr>
			        </s:iterator>
		            </tbody>
		        </table>
	          </div>
	          <!--价格阶梯-->              
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-2 control-label">是否发布：</label>
            <div class="col-xs-8"><label class="radio-inline">
			  <input type="radio" name="ticketStatus" value="<s:property value="@com.data.data.hmly.service.common.entity.enums.ProductStatus@DOWN"/>" checked> 放到采购门票仓库，编辑后发布（推荐）
			</label>
			<label class="radio-inline">
			  <input type="radio" name="ticketStatus" value="<s:property value="@com.data.data.hmly.service.common.entity.enums.ProductStatus@UP"/>"> 直接发布
			</label>
            </div>
          </div>
          <div class="form-group">
            <div class="col-xs-10 col-xs-offset-2">
              <p class="form-control-static"><input class="btn btn-warning" type="button" value="确定" onclick="TicketAgent.doAgent()"><input class="btn btn-primary" type="button" value="返回"  onclick="TicketAgent.doBack()"></p>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<!--反馈成功弹窗-->
<div class="modal fade ok" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog">
    <div class="md-title"><a href="#" data-dismiss="modal" class="pull-right">X</a></div>
    <div class="md-content">
      <h4>分销成功!<small>点击确定返回首页</small></h4>
    </div>
    <div class="md-qd text-center"><a href="javascript:void(0)" onclick="TicketAgent.doOk()" data-dismiss="modal" class="btn btn-primary">确定</a></div>
    </dl>
  </div>
</div>
<!--反馈成功弹窗--> 	
<!--内容结束-->

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jquery.pin.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/mall/ticket/ticketAgent.js?v=${mallConfig.resourceVersion}"></script>