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
            携程相似餐厅：
            <br/>

            <div id="result_${delicacyId}">
                <span style="color: grey">加载较慢，请稍等...</span>
            </div>

        </div>
    </span>
        <br/>

        <div align="left" style="position:absolute;bottom:2px;">
            <input type="hidden" name="cityId" value="${cityId}"/>
            <input type="hidden" name="delicacyId" value="${delicacyId}"/>
            <input type="text" value="${keyword}" id="searchValue_${delicacyId}">
            <input type="button" value='搜索' id="search_${delicacyId}">
            <input type="button" value='刷新' align="right"
                   id="reflash_${delicacyId}">
            <input type="button" value='提交' align="right" onclick="submitRestaurant()"
                   id="submit_${delicacyId}">
        </div>
    </form>
    <script type="text/javascript">
        var keyword = "${keyword}";
        var city = "${districtId}";
        $.ajax({
            url: 'http://m.ctrip.com/restapi/soa2/10332/json/GetDiscoveryFoodPageViewModel',
            dataType: 'json',
            method: 'POST',
            data: {
                BrandId: 0,
                CuisineId: 0,
                CurrentDestId: 2,
                DistrictId: ${districtId>0?districtId:0},
                KeyWord: keyword,
                LocationId: 0,
                LocationLat: 0,
                LocationLon: 0,
                OrderSortId: 0,
                Distance: 0,
                PageIndex: 1,
                PageSize: 15,
                PositionLat: 0,
                PositionLon: 0,
                PriceSortId: 0,
                SceneSortId: [],
                SellSortId: [],
                ZoneId: 0,
                FoodId: 0,
                UnionCondition: "",
                "head": {
                    "cid": "32001048310005536757",
                    "ctok": "",
                    "cver": "608.002",
                    "lang": "01",
                    "sid": "8080",
                    "syscode": "32",
                    "auth": null
                },
                contentType: 'json',
            },
            success: function (data) {
                $('#result_${delicacyId}').html("");
                if (data.Restaurants.length == 0) {
                    $('#result_${delicacyId}').html("<span style='color: red'>无搜索结果</span>");
                } else {
                    $.each(data.Restaurants, function (i, n) {
                        var add = '<input type="checkbox" value="' + n.RestaurantId + '" id="' + n.RestaurantId + '" name="ctripIds" >';
                        add += '<a href="#" onclick="openUrl(\'http://you.ctrip.com/food/xian7/' + n.RestaurantId + '.html\')">' + n.RestaurantName + '</a>';
                        add += '&nbsp;<span style="color:gray">' + n.DistrictName + '</span>';
                        add += '&nbsp;<span style="color:gray">' + n.AveragePrice + '元</span><br/>';
                        $('#result_${delicacyId}').append(add);
                    });
                }
            },
            error: function () {
                alert('关联失败！');
            }
        });


        function openUrl(url) {
            console.info(url);
            window.parent.addTab('关联餐厅详情', url);
        }
        function submitRestaurant() {
            $('#form_${delicacyId}').form('submit', {
                url: '/restaurant/delicacyRestaurant/saveRes.jhtml',
                dataType: 'json',
                onSubmit: function () {
                    return $(this).form('validate');	// 返回false终止表单提交
                },
                success: function (data) {
                    BgmgrUtil.backCall(data, function () {
                        var arr = data.split('"rows":["');
                        if(arr.length>1){
                            var names = arr[1].replace('"','').replace(']}','');
                            $.messager.alert('提示', '[' + names + ']关联成功', 'info', null, null);
                        }else{
                            $.messager.alert('提示', '失败！', 'info', null, null);
                        }
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
