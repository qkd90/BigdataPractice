<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>管理后台</title>
    <%@include file="../common/common141.jsp" %>
</head>
<body>
<div>
    <form id="form_${delicacyId}" method="post" enctype="multipart/form-data">
        <span align='left'>
            <span style="float:right;background-color: aliceblue">
                已关联餐厅:
                <br/>
                <div id="relatied_${delicacyId}">
                    <c:forEach items="${relatieds}" var="relatied">
                        <input checked="checked" type="checkbox" value="${relatied.id}" id="" name="relatieds">
                        ${relatied.name}<br/>
                    </c:forEach>
                </div>
            </span>
            <div>
                自定义餐厅:
                <br/>

                <div id="result_diy_${delicacyId}">
                    <span style="color: grey">加载较慢, 请稍等...</span>
                </div>
            </div>
        </span>
        <br/> <br/>
        <div align="left" style="position:absolute;bottom:2px;">
            <input type="hidden" name="cityId" value="${cityId}"/>
            <input type="hidden" name="delicacyId" value="${delicacyId}"/>
            <input type="text" value="${keyword}" id="searchValue_diy_${delicacyId}">
            <input type="button" value='搜索' id="search_diy_${delicacyId}" onclick="listRes()">
            <input type="button" value='刷新' align="right" id="reflash_diy_${delicacyId}" onclick="listRes()">
            <input type="button" value='提交' align="right" onclick="submitRestaurant()" id="submit_diy_${delicacyId}">
        </div>
    </form>
    <script type="text/javascript">
    	listRes();
    	function listRes(){
    		$('#result_diy_${delicacyId}').html("<span style='color: red'>正在搜索,请稍候......</span>");
    		var keyword = decodeURI($('#searchValue_diy_${delicacyId}').val());
            /* var keyword = "${keyword}"; */
            console.log("keywordTest = "+keyword);
            var city = "${districtId}";
            $.ajax({
                url: '/restaurant/delicacyRestaurant/list.jhtml',
                dataType: 'json',
                method: 'POST',
                data: {
                    resName: keyword
                },
                success: function (data) {
                	$('#result_diy_${delicacyId}').html("");
                    if (data.rows.length == 0) {
                        $('#result_diy_${delicacyId}').html("<span style='color: red'>无搜索结果</span>");
                    } else {
                        $.each(data.rows, function (i, n) {
                            var add = '<input type="checkbox" value="' + n.id + '" id="' + n.id + '" name="diyIds" >';
                            add += '<a href="#" onclick="openUrl(\'http://you.ctrip.com/food/xian7/' + n.id + '.html\')">' + n.name + '</a>';
                            add += '&nbsp;<span style="color:gray">' + n.extend.address + '</span>';
                            add += '&nbsp;<span style="color:gray">' + n.price + '元</span><br/>';
                            $('#result_diy_${delicacyId}').append(add);
                        });
                    }
                },
                error: function () {
                    alert('关联失败！');
                }
            });
    	}
        function openUrl(url) {
            console.info(url);
            window.parent.addTab('关联餐厅详情', url);
        }
        function submitRestaurant() {
            $('#form_${delicacyId}').form('submit', {
                url: '/restaurant/delicacyRestaurant/saveRes.jhtml',
                onSubmit: function () {
                    return $(this).form('validate');	// 返回false终止表单提交
                },
                success: function (data) {
                    BgmgrUtil.backCall(data, function () {
                        $.messager.alert('提示', '操作成功！', 'info', null, null);
                    }, null);
                }, onLoadError: function (data) {
                    console.info('load error' + data);
                },
                error: function () {
                    error.apply(this, arguments);
                    $.messager.alert('提示', '失败！', 'info', null, null);
                }
            });
        }
    </script>
</div>
</body>
