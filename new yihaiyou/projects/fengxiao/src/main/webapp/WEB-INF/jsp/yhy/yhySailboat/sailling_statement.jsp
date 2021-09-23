<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" href="/css/yhy/yhySailboat/sailling_statement.css">
    <title>海上休闲/财务结算账单明细</title>
</head>
<body class="sailFinance includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="statementList">
		<div class="pageTitle clearfix">
			<span class="firTitle">账单明细</span>
			<s:if test="orderBillSummary.confirmStatus == 0">
				<span class="forSure" onclick="SailboatStatement.confirmOrderBill()">确认</span>
			</s:if>
			<span class="reTurn" onclick="SailboatStatement.goback()">返回</span>
		</div>
		<div class="selectBar clearfix">
			<div class="selB_1 statementNum clearfix">
			    <div class="left"></div>
				<div class="right">
					<input type="hidden" id="orderBillSummaryId" value="${orderBillSummary.id}">
					<p class="p1">对账单号</p> 
					<p class="p2">${orderBillSummary.billNo}</p>
				</div>
			</div>
			<div class="selB_1 statementTime clearfix">
				<div class="left"></div>
				<div class="right">
					<p class="p1">出账时间</p> 
					<p class="p2"><s:date name="orderBillSummary.billDate" format="yyyy-MM-dd"></s:date></p>
				</div>
			</div>
			<div class="selB_1 statementType clearfix">
				<div class="left"></div>
				<div class="right">
					<p class="p1">结算方式</p> 
					<p class="p2">
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@D0 == orderBillSummary.billType">D+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@D1 == orderBillSummary.billType">D+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@DN == orderBillSummary.billType">D+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@T0 == orderBillSummary.billType">T+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@T1 == orderBillSummary.billType">T+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@TN == orderBillSummary.billType">T+${orderBillSummary.billDays}</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@week == orderBillSummary.billType">
							每周
							<s:if test="orderBillSummary.billDays == 1">日</s:if>
							<s:if test="orderBillSummary.billDays == 2">一</s:if>
							<s:if test="orderBillSummary.billDays == 3">二</s:if>
							<s:if test="orderBillSummary.billDays == 4">三</s:if>
							<s:if test="orderBillSummary.billDays == 5">四</s:if>
							<s:if test="orderBillSummary.billDays == 6">五</s:if>
							<s:if test="orderBillSummary.billDays == 7">六</s:if>
						</s:if>
						<s:if test="@com.data.data.hmly.service.order.entity.enums.OrderBillType@month == orderBillSummary.billType">每月${orderBillSummary.billDays}</s:if>
					</p>
				</div>
			</div>
			<%--<div class="selB_1 statementGet clearfix">--%>
				<%--<div class="left"></div>--%>
				<%--<div class="right">--%>
					<%--<p class="p1">应收款</p> --%>
					<%--<p class="p2">¥${orderBillSummary.totalBillPrice}</p>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="selB_1 statementGet clearfix">
				<div class="abox" id="totalBillPrice_id">
					<span class="aname dname">当期收款</span>
					<span class="pr_count"><lable>¥</lable><s:if test="orderBillSummary.totalBillPrice != null">${orderBillSummary.totalBillPrice}</s:if><s:else>0</s:else></span>
				</div>
				<div class="fuhao">－</div>
				<div class="abox" id="refundPrice_id">
					<span class="aname bname">当期退款</span>
					<span class="pr_count"><lable>¥</lable><s:if test="orderBillSummary.refundPrice != null">${orderBillSummary.refundPrice}</s:if><s:else>0</s:else> </span>
				</div>
				<div class="fuhao">＝</div>
				<div class="abox">
					<span class="aname cname">实际收款</span>

					<s:if test="orderBillSummary.confirmStatus == 0">
						<span class="pr_count"><lable>¥</lable><s:if test="orderBillSummary.notBillPrice != null">${orderBillSummary.notBillPrice}</s:if><s:else>0</s:else></span>
					</s:if>
					<s:else>
						<span class="pr_count"><lable>¥</lable><s:if test="orderBillSummary.alreadyBillPrice != null">${orderBillSummary.alreadyBillPrice}</s:if><s:else>0</s:else></span>
					</s:else>
				</div>
			</div>
		</div>
		<div class="statementTable messageList_header" style="display:block">
			<table class="table table-striped" id="orderBillDetailDg">
				<%--<tr class="headTr">--%>
				  	<%--<th class="checkNum">订单号</th>--%>
				  	<%--<th class="checkDays">票型名称</th>--%>
				  	<%--<th class="checkDate">结算单价</th>--%>
				  	<%--<th class="checkDate">数量</th>--%>
				  	<%--<th class="statement">结算总价</th>--%>
				<%--</tr>--%>
			</table>
		</div>
		<div class="statementTable messageList_header">
			<table class="table table-striped" id="refund-bill-order-detail">
			</table>
		</div>
		<%--<div class="pageChange">
			<nav>
			    <ul class="pagination clearfix">
			    	<li><a href="#">首页</a></li>
			        <li><a href="#">上一页</a></li>			        
			        <li class="active"><a href="#">1</a></li>
			        <li><a href="#">2</a></li>
			        <li><a href="#">3</a></li>
			        <li><a href="#">4</a></li>
			        <li><a href="#">5</a></li>		        
			        <li><a href="#">下一页</a></li>
			        <li><a href="#">尾页</a></li>
			    </ul>
			</nav>
		</div>--%>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_statement.js"></script>
</html>