<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>
    <%--<script language="javascript" type="text/javascript" src="/js/outOrder/Time/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="/js/outOrder/outOrder/addOrder.js"></script>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
  .row_hd {
    height: 27px;
    width: 100%;
    line-height: 27px;
    border-bottom: 1px solid #ddd;
    background: #f4f4f4;
    margin: 5px 15px 10px 15px;
    padding-left: 10px;
    font-weight: 700;
    color: #666;
  }
  .label_class {
    width: 80px;
    float: left;
    text-align: right;
    margin-right: 15px;
  }
  .coupon_class {
    margin-top: 5px;
    margin-left: 20px;
  }
  .botton_class {
    margin-top: 22px;
    margin-left: 122px;
  }
  .botton_class a{
    margin-right: 20px;
  }
    .dialog {
        margin-left: 5px;
    }
    .dialog_botton{
        margin-left: 100px;
        margin-top: 25px;
    }
</style>
<body>
    <form method="POST" action="http://channel.zmyou.com/ota/api">
        u：
        <input type="text" name="key" value="1445498571891" />
        <br>
        p：
        <input type="text" name="no" value="9" />
        <br>
        body：
        <input type="text" name="body" value='{"action":"GET_PRODUCT"}' />
        <br>
        sign：
        <input type="text" name="token" value="79a7f3eddae8d11c6d87ecea54665692" />
        <br>
        <input type="submit" value="提交" />
    </form>
</body>
</html>
