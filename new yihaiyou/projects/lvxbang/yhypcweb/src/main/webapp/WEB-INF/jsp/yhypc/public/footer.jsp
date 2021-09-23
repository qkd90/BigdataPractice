<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/12/27
  Time: 14:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">var QINIU_BUCKET_URL = '<%=com.zuipin.util.QiniuUtil.URL%>';</script>
<script type="text/javascript">var WEB_WX_APP_ID = '<%=com.zuipin.util.PropertiesUtil.getString("WEB_WX_APP_ID")%>'</script>
<script type="text/javascript">var WEB_WX_RETURN_URI= encodeURIComponent('<%=com.zuipin.util.PropertiesUtil.getString("WEB_WX_RETURN_URI")%>?returnUrl=' + document.URL)</script>
<script type="text/javascript" src="/lib/jquery/jquery-1.11.1.min.js"></script>
<script language="javascript" type="text/javascript" src="/lib/Time/WdatePicker.js"></script>
<script type="text/javascript" src="/lib/util/md5.js"></script>
<script type="text/javascript" src="/lib/util/moment.min.js"></script>
<script type="text/javascript" src="/lib/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="/lib/util/template.js"></script>
<script type="text/javascript" src="/lib/util/template.helper.js"></script>
<script type="text/javascript" src="/js/public/custom_plugins.js"></script>
<script type="text/javascript" src="/js/public/config.js"></script>
<script type="text/javascript" src="/js/public/public.js"></script>
<%@include file="../../yhypc/public/rightBar.jsp"%>
<%@include file="../../yhypc/public/popup.jsp"%>
<script type="text/javascript" src="/js/public/user.js"></script>
<script type="text/javascript" src="/js/public/ferryUser.js"></script>
<script type="text/javascript" src="/js/public/idcard.js"></script>
