<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="../common/common141.jsp" %>
    <style type="text/css">
        body, html {
            width: 100%;
            height: 100%;
            margin: 0;
            font-family: "微软雅黑";
        }
        #allmap {
            width: 100%;
            height: 90%;
            overflow: hidden;
        }
        #result {
            width: 100%;
            font-size: 12px;
        }
    </style>
    <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.3&key=1c061beb50cf259298c21e0d8a34df9f&plugin=AMap.PolyEditor"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <title>景点区域管理(高德)</title>
</head>
<body>
<form id="region_edit_form" action="/region/region/saveRegion.jhtml" method="post" enctype="multipart/form-data">
    <div id="r-result" style="float: right">
        要计算交通，请加载后，在图中选择两个景点
    </div>
    <div style="margin: 5px;">
        <span>区域:</span>
            <span>
                <select class="easyui-combobox" name="province" id="addr_province" style="width: 80px">
                    <option value="请选择" selected="selected">请选择</option>
                </select>
                <select class="easyui-combobox" name="cityField" id="addr_city" style="width: 80px">
                    <option value="请选择" selected="selected">请选择</option>
                </select>
                <select class="easyui-combobox" name="area" id="addr_area" style="width: 80px">
                    <option value="请选择" selected="selected">请选择</option>
                </select>
            </span>
        前<input type="text" class="easyui-numberspinner" style="width: 80px;"
                data-options="min:0,required:true" id="ranking" value="99999"/>名
        <input type="button" value="加载" class="easyui-linkbutton" style="width: 60px;" onclick="loadCity()"/>
        <%--地址<input type="text" id="address" class="easyui-textbox" value=""/>--%>
    </div>
    <div style="padding-bottom: 5px;">
        名称：<input id="name" name="name" value="${region.name}" data-options="required:true"/>
        <c:if test="${region.id == null}">
            <input type="button" class="button" value="添加多边形" onClick="addPolygon()"/>
        </c:if>
        <input type="button" value="开始编辑" class="easyui-linkbutton"style="width: 80px;"onClick="openEdit()"/>
        <%--<input type="button" value="完成编辑" class="easyui-linkbutton"style="width: 80px;" onClick="closeEdit()"/>--%>
        <%--<input type="button" value="清空" class="easyui-linkbutton" style="width: 60px;" onclick="clean()"/>--%>
        <input type="button" value="提交" class="easyui-linkbutton" style="width: 60px;" onclick="closeEdit();save()"/>
        <input type="hidden" name="city.id" id="cityCode" value="">
        <input type="hidden" name="regionCode" id="regionCode" value="">
        <input type="hidden" name="createTime" id="createTime" value=${region.createTime}>
        <input type="hidden" name="regionType" id="regionType" value=${region.regionType}>
        <input type="hidden" id="regionName" value="">
        <input type="hidden" id="id" name="id" value="${region.id==null?0:region.id}"/>
        <%--&nbsp;&nbsp;--%>
        <%--定位景点:--%>
        <%--<span>--%>
        <%--&lt;%&ndash;<input type="text" id="fatherSelect" value="" class="easyui-combobox"&ndash;%&gt;--%>
        <%--&lt;%&ndash;data-options="loader: scenicLoader,mode: 'remote',valueField: 'scenicId',textField: 'scenicName',onSelect:selectScenic">&ndash;%&gt;--%>
        <%--</span>--%>
    </div>
    <div style="display:none">
        描述：<input id="description" name="description" value="${region.description}"/>
    </div>
    <div style="display:none">
        坐标：
        <input type="hidden" id="points" name="pointStr" value="${region.pointStr}"/>
        <span id="pointsArea" name="pointsArea">${region.pointStr}</span>
    </div>
</form>
    <span>
        <div id="allmap" style="float:left;overflow:hidden;zoom:1;position:relative;">
            <div id="map"
                 style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>
        </div>
    </span>
<div ID="tips">
    <div>
        *在地图中标绘制多边形
        *双击完成绘制
        ＊红色为景点
        ＊蓝色为餐厅
    </div>
</div>
</span>
<script type="text/javascript">
    $.messager.progress({
        title: '提示',
        text: '正在获取区域数据, 请耐心等待...'
    });
    $(initProvince);
    var cityId = '350200';
    <c:if test="${region.id > 0}">
    cityId = '${region.city.cityCode}';
    </c:if>
    function initArea(val) {
        var regionCode = '';
        if (val != undefined && val != 'undefined')
            regionCode = val.cityCode;
        $('#addr_area').combobox({
            url: '/sys/area/getAreas.jhtml?cityId=' + regionCode,
            valueField: 'cityCode',
            textField: 'name',
            onSelect: function (sel) {
                $('#regionCode').prop('value', sel.cityCode);
                $('#regionName').prop('value', sel.fullPath);
            }
        });
    }
    function initCity(val) {
        var selProvince = '';
        if (val != undefined && val != 'undefined')
            selProvince = val.cityCode;
        $('#addr_city').combobox({
            url: '/sys/area/getCities.jhtml?provinceId=' + selProvince,
            valueField: 'cityCode',
            textField: 'name',
            onSelect: function (sel) {
                if (sel == undefined)return;
                $('#cityCode').prop('value', sel.cityCode);
                $('#regionCode').prop('value', sel.cityCode.substring(0, 4));
                $('#regionName').prop('value', sel.fullPath);
                initArea(sel);
            },
            onLoadSuccess: function () {
                if ($('#cityCode').val() == (cityId.substring(0, 2) + '0000')) {
                    $('#addr_city').combobox('select', cityId.substring(0, 4) + '00');
                }
                $.messager.progress('close');
            }
        });
    }
    function initProvince() {
        $('#addr_province').combobox({
            url: '/sys/area/getCNProvinces.jhtml',
            valueField: 'cityCode',
            textField: 'name',
            onSelect: function (sel) {
                if (sel == undefined)return;
//                console.log(sel);
                $('#cityCode').prop('value', sel.cityCode);
                $('#regionCode').prop('value', sel.cityCode.substring(0, 2));
                $('#regionName').prop('value', sel.fullPath);
                initCity(sel);
            },
            onLoadSuccess: function () {
                $('#addr_province').combobox('select', cityId.substring(0, 2) + '0000');
            }
        });
    }
</script>
<script type="text/javascript">
    var p = [];//用来存储区域的点
    var doneDraw = 0; //判断是否绘多边形结束
    // 高德地图API功能
    var map = new AMap.Map('map', {
        resizeEnable: true,
        zoom: 13,
        center: [118.131769, 24.4809]
    });
    <c:if test="${region.id > 0}">
    var point = $('#points').val().split('],[')[0].replace('[', '').split(',');
    map.setCenter([parseFloat(point[0]), parseFloat(point[1])]);
    </c:if>
    var editor = {};
    function init() {
        p = [];
        var points = "";
        //若是编辑，则在地图中显示已画区域
        <c:if test="${region.id > 0}">
        var arr = $('#points').val().split('],[');
        //高德地图
        editor._polygon = (function () {
            var path = [];//构建多边形经纬度坐标数组
            for (var i = 0; i < arr.length; i++) {
                var ps = arr[i].replace('[', '').replace(']', '').split(',');
                path.push([parseFloat(ps[0]), parseFloat(ps[1])]);
            }
            return new AMap.Polygon({
                map: map,
                path: path,
                strokeColor: "#0000ff",
                strokeOpacity: 1,
                strokeWeight: 3,
                fillColor: "#f5deb3",
                fillOpacity: 0.35
            });
        })();
        editor._polygonEditor = new AMap.PolyEditor(map, editor._polygon);
        editor._polygonEditor.open();
        </c:if>
    }
    init();
    function save() {
        $('#region_edit_form').form('submit', {
            url: '/region/region/saveRegion.jhtml?type=2',
            success: function (data) {
                console.log(data);
                $.messager.alert('提示', '提交成功', 'info', null, null);
            },
            error: function () {
                error.apply(this, arguments);
                $.messager.alert('提示', '失败！', 'info', null, null);
            }
        });
    }
    function editScenic(id) {
        window.parent.addTab('编辑景点', "/scenic/scenicInfo/edit.jhtml?id=" + id);
    }
    function loadCity() {
        $.messager.progress({
            title: '提示',
            text: '正在获取区域数据, 请耐心等待...'
        });
        console.info('load city');
        <c:if test="${region.id == null}">
        $('#address').textbox("setValue", $('#regionName').val());
        </c:if>
        var cityCode = $('#cityCode').val();
        var regionCode = $('#regionCode').val();
        var rankingLimit = $('#ranking').val();
        getScenics(regionCode, rankingLimit);
//        getRestaurants(regionCode);
        getPolygons(regionCode);
    }
    function getScenics(regionCode, rankingLimit) {//该城市的景点的坐标（rankingLimit为排名）
        var point1, point2;
        $.ajax({
            url: '/scenic/scenicInfo/listGoogleCoordinates.jhtml',
            dataType: 'json',
            method: 'POST',
            data: {
                cityCode: regionCode,
                rankingLimit: rankingLimit
            },
            success: function (data) {
                if (data.resultList.data == undefined || data.resultList.data.length == 0) {
//                    alert('没找到该城市已有的景点');
                } else {
                    $.each(data.resultList.data, function (i, n) {
                        marker = new AMap.Marker({
                            icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                            position: [n.longitude, n.latitude]
                        });
                        marker.setMap(map);
                        // 设置label标签
                        marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
                            offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
                            content: n.scenicName
                        });
                    });
                    console.info('set center:' + data.resultList.data[0].longitude + ',' + data.resultList.data[0].latitude);
                    map.setCenter([data.resultList.data[0].longitude, data.resultList.data[0].latitude]);
                }
                $.messager.progress('close');
            },
            error: function () {
                alert('加载景点失败！');
            }
        });
    }
    function getPolygons(regionCode) {//该城市的所有经验区域
        $.ajax({
            url: '/region/region/list.jhtml',
            dataType: 'json',
            method: 'POST',
            data: {
                cityCode: regionCode.length > 4 ? regionCode.substring(0, 4) : regionCode,
                type: 2
            },
            success: function (data) {
                if (data.rows == undefined || data.rows.length == 0) {
                    alert('没找到该城市已有的高德经验区域');
                } else {
                    $.each(data.rows, function (i, n) {
                        if ("${region.id}" != n.id) { // 不加载当前编辑的区域
                            var arr = n.pointStr.split('],[');
                            var points = new Array();
                            for (var i = 0; i < arr.length; i++) {
                                var ps = arr[i].replace('[', '').replace(']', '').split(',');
                                points.push([ps[0], ps[1]]);
                            }
                            var polygon = new AMap.Polygon({
                                path: points,
                                strokeColor: "#6282FF", //线颜色
                                strokeOpacity: 1, //线透明度
                                strokeWeight: 3,    //线宽
                                fillColor: "", //填充色
                                fillOpacity: 0//填充透明度
                            });
                            polygon.setMap(map);
//                            var infoWindow;
                            <%--polygon.addEventListener("mouseover", function (e) {--%>
                                <%--var opts = {--%>
                                    <%--width: 60,     // 信息窗口宽度--%>
                                    <%--height: 30,     // 信息窗口高度--%>
                                    <%--title: "<span style=\"font-size:16px;font-weight:bold\">区域：" + n.name + "</span>", // 信息窗口标题--%>
                                    <%--enableMessage: true,//设置允许信息窗发送短息--%>
                                    <%--message: "",--%>
                                <%--}--%>
                                <%--/* console.log(polygon.getBounds().getCenter()); */--%>
                                <%--var info = n.description + "<br/>优先级:" + "${region.priority}";--%>
                                <%--infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象--%>
                                <%--map.openInfoWindow(infoWindow, polygon.getBounds().getCenter()); //开启信息窗口--%>
                            <%--});--%>
                            <%--polygon.addEventListener("mouseout", function (e) {--%>
                                <%--map.closeInfoWindow();--%>
                            <%--});--%>
                        }
                    });
                }
            },
            error: function () {
                alert('加载区域失败！');
            }
        });
    }
    function addPolygon() {
        var polygonArr = new Array();//多边形覆盖物节点坐标数组
        polygonArr.push(new AMap.LngLat(map.getCenter().getLng() - 0.01, map.getCenter().getLat() - 0.01));
        polygonArr.push(new AMap.LngLat(map.getCenter().getLng() - 0.01, map.getCenter().getLat() + 0.01));
        polygonArr.push(new AMap.LngLat(map.getCenter().getLng() + 0.01, map.getCenter().getLat() + 0.01));
        polygonArr.push(new AMap.LngLat(map.getCenter().getLng() + 0.01, map.getCenter().getLat() - 0.01));
        editor._polygon = new AMap.Polygon({
            path: polygonArr,//设置多边形边界路径
            strokeColor: "#0000ff", //线颜色
            strokeOpacity: 0.2, //线透明度
            strokeWeight: 3,    //线宽
            fillColor: "#f5deb3", //填充色
            fillOpacity: 0.35//填充透明度
        });
        editor._polygon.setMap(map);
        editor._polygonEditor = new AMap.PolyEditor(map, editor._polygon);
        editor._polygonEditor.open();
    }
    function openEdit() {
        editor._polygonEditor.open();
    }
    function closeEdit() {
        console.info(editor._polygon.getPath());
        var str = '';
        editor._polygon.getPath().forEach(function (i) {
            console.info(i)
            str += "[" + i.lng + "," + i.lat + "],";
        });
        document.getElementById("points").value = str;
        document.getElementById("pointsArea").innerHTML = str;
        editor._polygonEditor.close();
    }
</script>
</body>
</html>
