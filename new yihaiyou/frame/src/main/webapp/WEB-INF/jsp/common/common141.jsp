<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String ctx_Path = request.getContextPath();
    String host = request.getServerName();
    int port = request.getServerPort();
    String fullPath = request.getScheme() + "://" + host + ":" + port + ctx_Path + "/";
    // set to attribute
    request.setAttribute("ctxPath", ctx_Path);
    request.setAttribute("fullPath", fullPath);
%>
<script type="text/javascript">
    var ctxPath = '${ctxPath}';
    var QINIU_BUCKET_URL = '<%=com.zuipin.util.QiniuUtil.URL%>';
</script>
<meta charset="UTF-8">

<!-- jquery组件 -->
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<!-- easyui组件 -->
<script type="text/javascript" src="/jquery-easyui-1.4.1/myjquery.easyui.min.js"></script>
<script type="text/javascript" src="/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.4.1/themes/default/easyui_bootstrap.css">
<link href="/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/themes/icon.css" />
<%--<link rel="stylesheet" type="text/css" href="/css/zuipin/css/style.css" />--%>
<link rel="stylesheet" type="text/css" href="/css/zuipin/css/base.css" />
<link rel="stylesheet" type="text/css" href="/css/zuipin/css/g.css" />
<link rel="stylesheet" type="text/css" href="/css/zuipin/css/ui.css" />
<link rel="stylesheet" type="text/css" href="/css/zuipin/css/main.css" />
<!-- 扩展的easyui的图标 -->
<link rel="stylesheet" type="text/css" href="/css/common.css">
<!-- 自定义的加载蒙层组件 -->
<link rel="stylesheet" type="text/css" 	href="/css/loading.css">
<!-- 输入框检查组件 -->
<script type="text/javascript" src="/js/check.js"></script>
<!-- 快捷键组件js -->
<script type="text/javascript" src="/js/zuipin.js"></script>
<!-- easyui 扩展的组件 -->
<script type="text/javascript" src="/js/easyui_extends_combobox.js"></script>
<!-- 扩展的提示 -->
<script type="text/javascript" src="/js/jquery.tip.js"></script>
<!--jsrender -->
<script type="text/javascript" src="/js/common/jsrender.js"></script>

<script type="text/javascript" src="/js/common/common.js"></script>

<script src="/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

