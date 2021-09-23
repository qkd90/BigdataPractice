<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>管理后台</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>

</head>
<body>
<div>
    <script>
        function saveRelation(scenicIds, sourceId, source, sourceName, data_source_url) {
            $.ajax({
                url: '${ctxPath}/mgrer/scenicRelation/saveRelation',
                dataType: 'json',
                method: 'POST',
                data: {
                    scenicIds: scenicIds,
                    sourceId: sourceId,
                    source: source,
                    sourceName: sourceName,
                    sourceUrl: data_source_url
                },
                success: function (msg) {
                    $('#info_' + sourceId).css("color", "green");
                    $('#info_' + sourceId).css("text-decoration", "underline");
                },
                error: function () {
                    alert('关联失败！');
                }
            });
        }
        <%--function delRelation(scenicIds, sourceId, source, sourceName, data_source_url) {--%>
            <%--$.get("${ctxPath}/mgrer/scenicRelation/delRelation?scenic_id=" + scenicIds + "&source_id=" + sourceId, function (result) {--%>
                <%--$('#info_' + sourceId).css("color", "gray");--%>
                <%--$('#info_' + sourceId).css("text-decoration", "gray");--%>
            <%--});--%>
        <%--}--%>
        function openUrl(url) {
//            console.info(url);
            if(url.indexOf('cncn')!=-1){
                window.open(url);
            }else{
                window.parent.addTab('景点详情', url);
            }
        }
    </script>
    <span align='center'>
        携程：
        <c:forEach items="${ctrip}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="ctrip"
                   onclick="saveRelation('${sid}','${scenic.id}','ctrip','${scenic.name}','${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">
            <a id='info_${scenic.id}' href="#"
               onclick="openUrl('${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">${scenic.name}</a>
        </c:forEach>
    </span>
    <br/>
     <span>
        同程:
        <c:forEach items="${ly}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="ly"
                   onclick="saveRelation('${sid}','${scenic.id}','ly','${scenic.name}','${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">
            <a id='info_${scenic.id}' href="#"
               onclick="openUrl('${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">${scenic.name}</a>
        </c:forEach>
    </span>
    <br/>
     <span>
        途牛：
        <c:forEach items="${tuniu}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="tuniu"
                   onclick="saveRelation('${sid}','${scenic.id}','tuniu','${scenic.name}','${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">
            <a id='info_${scenic.id}' href="#"
               onclick="openUrl('${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">${scenic.name}</a>
        </c:forEach>
    </span>

    <br/>
        <span>
        欣欣:
        <c:forEach items="${cncn}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="cncn"
                   onclick="saveRelation('${sid}','${scenic.id}','cncn','${scenic.name}','${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">
            <a id='info_${scenic.id}' href="#"
               onclick="openUrl('${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">${scenic.name}</a>
        </c:forEach>
    </span>
    <br/>
     <span>
        马蜂窝：
        <c:forEach items="${mfw}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="mfw"
                   onclick="saveRelation('${sid}','${scenic.id}','mfw','${scenic.name}','${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">
            <a id='info_${scenic.id}' href="#"
               onclick="openUrl('${scenic.data_source_url!=null?scenic.data_source_url.trim():""}')">${scenic.name}</a>
        </c:forEach>
    </span>
    <br/>
    <span align="right">
        <input type="text" value="" id="searchValue_${sid}">
        <input type="button" value='搜索' id="search_${sid}">
    </span>
</div>
</body>