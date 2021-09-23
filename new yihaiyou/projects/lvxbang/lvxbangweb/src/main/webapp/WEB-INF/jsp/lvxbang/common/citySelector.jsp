<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<div class="posiA des_main Addmore" style="display: none;padding: 0;border: 0;">
    <jx:include fileAttr="/static/master/lvxbang/common/citySelector.htm" targetObject="lvXBangBuildService" targetMethod="buildCommon" validDay="90"></jx:include>
</div>
<script type="text/html" id="tpl-city-radio">
    <div class="fl checkbox checked" destination="{{name}}" data-id="{{id}}"><span class="checkbox_i" input="options"><i></i></span>{{name}}</div>
</script>
<script type="text/html" id="tpl-city-history">
    <a class="city-selector-button" href="javascript:" data-id="{{id}}" title="{{name}}">{{name}}</a>
</script>