<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>管理后台</title>
    <%@include file="../common/common141.jsp" %>
</head>
<body>
<div>
    <script>
        function saveRelation(sightId, scenicIds) {
            $.ajax({
                url: '/scenic/qunarSight/saveRelation.jhtml',
                dataType: 'json',
                method: 'POST',
                data: {
                    scenicIds: scenicIds,
                    sightId: sightId
                },
                success: function (msg) {
                    $('#info_${sid}_' + scenicIds).css("color", "green");
                    $('#info_${sid}_' + scenicIds).css("text-decoration", "underline");
                },
                error: function () {
                    alert('关联失败！');
                }
            });
        }
        //        function openUrl(url) {
        //            console.info(url);
        //            window.parent.addTab('关联景点详情', url);
        //        }
        function edit(id) {
            window.parent.addTab('编辑景点', "/scenic/scenicInfo/edit.jhtml?id=" + id);
        }
    </script>

    <span align='center'>
        相似景点：
        <br/>
        <c:forEach items="${scenics}" var="scenic">
            <input type="radio" value="${scenic.id}" id="ck_${scenic.id}" name="scenic"
                   onclick="saveRelation('${sid}','${scenic.id}')">
            <a id='info_${sid}_${scenic.id}' href="#"
               onclick="edit('${scenic.id}')">${scenic.name}</a>
            <span>
                    ${scenic.scenicOther.address}
            </span>
            <br/>
        </c:forEach>
    </span>

    <span align="right">
        <input type="text" value="" id="searchValue_${sid}">
        <input type="button" value='搜索' id="search_${sid}">
    </span>
</div>
</body>