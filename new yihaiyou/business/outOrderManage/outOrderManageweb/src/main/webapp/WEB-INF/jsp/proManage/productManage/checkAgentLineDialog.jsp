<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>
    <script type="text/javascript" src="/js/proManage/checkAgentLineDialog.js"></script>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
  .container01 {
      margin: 10px 20px;
  }
  .labelValue {
      font-size: 14px;
  }
</style>
<body>
  <input id="ipt_topline_id" type="hidden" name="id" value="${line.topProduct.id}">

  <input id="ipt_line_id" type="hidden" name="id" value="${line.id}">
  <div style="width: 100%; height: 100%">


      <div class="container01">
          <div style="float: left;">
              <label>线路名称：</label>
          </div>
          <div style="">
              <label>${line.name}</label>
          </div>
      </div>
      <div class="container01">
          <div style="float: left;">
              <label>产品备注：</label>
          </div>
          <div style="">
              <label>
                  <input id="ipt_remark" class="easyui-textbox"
                         data-options="multiline:true,prompt:'经销商产品备注',validType:'maxLength[60]'"
                         style="width:200px;height: 90px" value="${line.productRemark}"/>
              </label>
          </div>
      </div>
      <div class="container01">
          <div style="float: left;">
              <label>价格类型：</label>
          </div>
          <div id="priceType_dg"></div>
      </div>
      <div class="container01">
          <div style="float: left;margin-right: 20px;margin-left: 50px;">
              <a id="btn_agentLine" class="easyui-linkbutton" onclick="CheckAgentLineDialog.agentLine()"
                 >代理线路</a>
          </div>
          <div style="">
              <a class="easyui-linkbutton" onclick="CheckAgentLineDialog.cancelAgent()" >取消</a>
          </div>
      </div>
  </div>
</body>
</html>
