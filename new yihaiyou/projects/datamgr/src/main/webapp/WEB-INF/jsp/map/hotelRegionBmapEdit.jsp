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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
    <!--加载鼠标绘制工具-->
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <title>酒店区域管理(百度)</title>
</head>
<body>
<form id="region_edit_form" action="/region/region/saveRegion.jhtml" method="post" enctype="multipart/form-data">
    <div id="r-result" style="float: right">
        要计算交通，请加载后，在图中选择两个点
    </div>
    <div>
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
        <%--前--%>
        <input type="hidden" value="99999" data-options="min:0,required:true" id="ranking"/>
        <%--名--%>
        <input type="button" value="加载" class="easyui-linkbutton" onclick="loadCity()" style="width: 60px;"/>
        搜索地址<input type="text" id="address" class="easyui-textbox" value=""/>
        <input type="button" value="查询" class="easyui-linkbutton" style="width: 60px;"
               onclick="searchByStationName()"/>
    </div>
    <div style="padding-bottom: 5px;">
        名称：<input id="name" name="name" value="${region.name}" data-options="required:true"/>
        优先级：<input id="priority" name="priority" class="easyui-numberspinner" style="width: 60px;"
                   data-options="min:0,required:true" value="${region.priority}"/>
        <input type="button" value="清空" class="easyui-linkbutton" style="width: 60px;" onclick="clean()"/>
        <input type="button" value="提交" class="easyui-linkbutton" style="width: 60px;" onclick="save()"/>
        <%--<input type="hidden" name="cityCode" id="cityCode" value="">--%>
        <input type="hidden" name="city.id" id="cityCode" value="">
        <input type="hidden" name="regionCode" id="regionCode" value="">
        <input type="hidden" name="createTime" id="createTime" value=${region.createTime}>
        <input type="hidden" name="regionType" id="regionType" value=${region.regionType}>
        <input type="hidden" id="regionName" value="">
        <input type="hidden" id="id" name="id" value="${region.id==null?0:region.id}"/>
        <%--定位酒店:<span>--%>
            <%--<input type="text" id="fatherSelect" value="" class="easyui-combobox"--%>
                   <%--data-options="loader: hotelLoader,mode: 'remote',valueField: 'hotelId',textField: 'hotelName',onSelect:selectHotel">--%>
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
            <%--<c:if test="${scenicInfo.regionCode != null && scenicInfo.regionCode % 100 >0}">,--%>
            <%--onLoadSuccess: function () {--%>
            <%--var regionCode = '${scenicInfo.regionCode}';--%>
            <%--if ($('#regionCode').val() == (regionCode.substring(0, 4) + '00')) {--%>
            <%--$('#addr_area').combobox('select', regionCode);--%>
            <%--}--%>
            <%--}--%>
            <%--</c:if> --%>
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
    // 百度地图API功能
    var map = new BMap.Map('map');
    var p = [];//用来存储区域的点
    var doneDraw = 0; //判断是否绘多边形结束
    var poi = new BMap.Point(118.131769, 24.4809);
    <c:if test="${region.id > 0}">
    var point = $('#points').val().split('],[')[0].replace('[', '').split(',');
    poi = new BMap.Point(parseFloat(point[0]), parseFloat(point[1]));
    </c:if>

    map.centerAndZoom(poi, 13);
    map.enableScrollWheelZoom();
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    function init() {
        p = [];
        var points = "";
        var styleOptions = {
            strokeColor: "blue",    //边线颜色。
            fillColor: "green",      //填充颜色。当参数为空时，圆形将没有填充效果。
            strokeWeight: 3,       //边线的宽度，以像素为单位。
            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
            fillOpacity: 0.2,      //填充的透明度，取值范围0 - 1。
            strokeStyle: 'solid'//边线的样式，solid或dashed。
        };
        var drawingManager = new BMapLib.DrawingManager(map, {
            isOpen: false, //是否开启绘制模式
            drawingType: BMAP_DRAWING_POLYGON,
            enableDrawingTool: true, //是否显示工具栏
            drawingToolOptions: {
                anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                offset: new BMap.Size(5, 5), //偏离值
            },
            polygonOptions: styleOptions, //多边形的样式
        });
        drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
        drawingManager.addEventListener("polygoncomplete", function (e) {
            p = e.getPath();
            var ps = new Array();
            e.getPath().forEach(function (i) {
                points += "[" + i.lng + "," + i.lat + "],";
                ps.push(new BMap.Point(i.lng, i.lat));
            });

            document.getElementById("points").value = points;
            document.getElementById("pointsArea").innerHTML = points;
            //编辑
            var newpolygon = new BMap.Polygon(ps, {
                strokeColor: "red",
                fillColor: "BurlyWood",
                strokeWeight: 5,
                strokeOpacity: 0,
                fillOpacity: 0,
            });
            map.addOverlay(newpolygon);
            newpolygon.enableEditing();
            newpolygon.addEventListener("lineupdate", function (f) {
                points = '';
                newpolygon.getPath().forEach(function (i) {
                    points += "[" + i.lng + "," + i.lat + "],";
                });
                document.getElementById("points").value = points;
                document.getElementById("pointsArea").innerHTML = points;
            });

        });
        //若是编辑，则在地图中显示已画区域
        <c:if test="${region.id > 0}">
        var arr = $('#points').val().split('],[');
        var points = new Array();
        for (var i = 0; i < arr.length; i++) {
            var ps = arr[i].replace('[', '').replace(']', '').split(',');
            points.push(new BMap.Point(ps[0], ps[1]));
        }
        var polygon = new BMap.Polygon(points, {
            strokeColor: "red",
            fillColor: "BurlyWood",
            strokeWeight: 5,
            strokeOpacity: 0,
            fillOpacity: 0,
        });
        polygon.enableEditing();
        map.addOverlay(polygon);

        polygon.addEventListener("lineupdate", function (e) {
            points = '';
            polygon.getPath().forEach(function (i) {
                points += "[" + i.lng + "," + i.lat + "],";
            });
            document.getElementById("points").value = points;
            document.getElementById("pointsArea").innerHTML = points;
        });
        var infoWindow;
        polygon.addEventListener("mouseover", function (e) {
            var opts = {
                width: 60,     // 信息窗口宽度
                height: 30,     // 信息窗口高度
                title: "<span style=\"font-size:16px;font-weight:bold\">区域：${region.name}</span>", // 信息窗口标题
                enableMessage: true,//设置允许信息窗发送短息
                message: "",
            }
            /* console.log(polygon.getBounds().getCenter()); */
            var info = "${region.description}" + "<br/>优先级:" + "${region.priority}";
            infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow, polygon.getBounds().getCenter()); //开启信息窗口
        });
        polygon.addEventListener("mouseout", function (e) {
            map.closeInfoWindow();
        });
        </c:if>
    }
    function clean() {
        map.clearOverlays();//清空原来的标注
        document.getElementById("address").value = "";
        document.getElementById("name").value = "";
        document.getElementById("points").value = "";
        document.getElementById("priority").value = "0";
        $('input[name = "priority"]').val(0);
        $("#priority").numberbox('setValue', 0);
        document.getElementById("description").value = "";
        document.getElementById("points").value = "";
        document.getElementById("pointsArea").innerHTML = "";
        p = [];
        var points = "";
        var styleOptions = {
            strokeColor: "blue",    //边线颜色。
            fillColor: "green",      //填充颜色。当参数为空时，圆形将没有填充效果。
            strokeWeight: 3,       //边线的宽度，以像素为单位。
            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
            fillOpacity: 0.2,      //填充的透明度，取值范围0 - 1。
            strokeStyle: 'solid', //边线的样式，solid或dashed。
            enableEditing: true,
        };
        //实例化鼠标绘制工具
        var drawingManager = new BMapLib.DrawingManager(map, {
            isOpen: true, //是否开启绘制模式
            enableDrawingTool: false, //是否显示工具栏
            drawingToolOptions: {
                anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                offset: new BMap.Size(5, 5), //偏离值
            },
            polygonOptions: styleOptions, //多边形的样式
        });
        drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
//        drawingManager.enableCalculate();
        drawingManager.addEventListener("polygoncomplete", function (e) {
            p = e.getPath();
            e.getPath().forEach(function (i) {
                points += "[" + i.lng + "," + i.lat + "],";
            });
            document.getElementById("points").value = points;
            document.getElementById("pointsArea").innerHTML = points;
        });

    }
    init();
    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    function searchByStationName() {
//        init();
        var keyword = document.getElementById("address").value;
        document.getElementById("description").value = keyword;
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
            map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
        });
        localSearch.search(keyword);
    }
    function save() {
        $('#region_edit_form').form('submit', {
            url: '/region/hotelRegion/saveRegion.jhtml?type=1',
            success: function (data) {
                console.log(data);
                BgmgrUtil.backCall(data, function () {
                    <c:if test="${region.id == null}">
                    $.messager.confirm('提示', '提交成功！点击确定新建下一个', function (data) {
                        if (data) {
                            clean();
                            loadCity();
                            init();
                        }
                    });
                    </c:if>
                    <c:if test="${region.id >0}">
                    $.messager.alert('提示', '提交成功', 'info', null, null);
                    </c:if>
                }, null);
            },
            error: function () {
                error.apply(this, arguments);
                $.messager.alert('提示', '失败！', 'info', null, null);
            }
        });
    }
    function editHotel(id) {
        window.parent.addTab('编辑酒店', "/hotel/hotel/editWizard.jhtml?productId=" + id);
    }

    function loadCity() {
        <c:if test="${region.id == null}">
        $('#address').textbox("setValue", $('#regionName').val());
        searchByStationName();
        </c:if>
        var cityCode = $('#cityCode').val();
        var regionCode = $('#regionCode').val();
        var rankingLimit = $('#ranking').val();
        $.messager.progress({
            title: '提示',
            text: '正在加载区域数据, 请耐心等待...'
        });
//        getHotels(regionCode, rankingLimit);// 数据量过多, 谨慎加载酒店!!
//        getScenics(regionCode, rankingLimit);// 酒店区域不要加载景点
//        getRestaurants(regionCode); // 酒店区域不要加载餐厅
        getPolygons(regionCode); // 加载现有酒店商圈区域
    }

    function getHotels(regionCode, rankingLimit) {//该城市的酒店的坐标（rankingLimit为排名）
        var point1, point2;
        $.ajax({
            url: '/hotel/hotel/listCoordinates.jhtml',
            dataType: 'json',
            method: 'POST',
            data: {
                cityCode: regionCode,
                rankingLimit: rankingLimit
            },
            success: function (result) {
                if (result.data == undefined || result.data.length == 0) {
//                    alert('没找到该城市已有的景点');
                } else {
                    $.each(result.data, function (i, n) {
//                        var marker = new BMap.Marker(new BMap.Point(n.longitude, n.latitude));  // 创建标注，为要查询的地方对应的经纬度
//                        map.addOverlay(new BMap.Marker(new BMap.Point(n.longitude, n.latitude)));
                        var point = new BMap.Point(n.longitude, n.latitude);
                        var marker = new BMap.Marker(point);

                        map.addOverlay(marker);
                        var opts = {
                            width: 60,     // 信息窗口宽度
                            height: 30,     // 信息窗口高度
                            title: n.hotelName, // 信息窗口标题
                            enableMessage: true,//设置允许信息窗发送短息
                            message: ""
                        }
                        var info = n.address + "</br><a style='color: #00008B' href='#' onclick='editHotel(" + n.hotelId + ")'>点此查看详情</a>";
//                        info += "<a href='#' onclick='addpoint(" + n.id + ")'>计算交通</a>";
                        var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
                        marker.addEventListener("mouseover", function () {
                            map.openInfoWindow(infoWindow, point); //开启信息窗口
                        });

                        marker.addEventListener("click", function (e) {
                            if (point1 == undefined) {
//                                console.info('change icon');
                                var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                                    offset: new BMap.Size(10, 25), // 指定定位位置
                                    imageOffset: new BMap.Size(0, -25) // 设置图片偏移
                                });
                                e.target.setIcon(myIcon);
                                point1 = new BMap.Point(e.target.getPosition().lng, e.target.getPosition().lat);
                            } else if (point2 == undefined) {
                                var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                                    offset: new BMap.Size(10, 25), // 指定定位位置
                                    imageOffset: new BMap.Size(0, -100) // 设置图片偏移
                                });
                                e.target.setIcon(myIcon);
                                point2 = new BMap.Point(e.target.getPosition().lng, e.target.getPosition().lat);
                                compute_drive_dis(point1, point2);
                                point1 = undefined;
                                point2 = undefined;
                            }
                        });
                    });
                }
            },
            error: function () {
                $.messager.alert('提示', '区域酒店加载失败', 'error');
            }
        });
    }

    function getScenics(regionCode, rankingLimit) {//该城市的景点的坐标（rankingLimit为排名）
        var point1, point2;
        $.ajax({
            url: '/scenic/scenicInfo/listCoordinates.jhtml',
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
//                        var marker = new BMap.Marker(new BMap.Point(n.longitude, n.latitude));  // 创建标注，为要查询的地方对应的经纬度
//                        map.addOverlay(new BMap.Marker(new BMap.Point(n.longitude, n.latitude)));
                        var point = new BMap.Point(n.longitude, n.latitude);
                        var marker = new BMap.Marker(point);

                        map.addOverlay(marker);
                        var opts = {
                            width: 60,     // 信息窗口宽度
                            height: 30,     // 信息窗口高度
                            title: n.scenicName, // 信息窗口标题
                            enableMessage: true,//设置允许信息窗发送短息
                            message: ""
                        }
                        var info = n.address + "</br><a style='color: #00008B' href='#' onclick='editHotel(" + n.scenicId + ")'>点此查看详情</a>";
//                        info += "<a href='#' onclick='addpoint(" + n.id + ")'>计算交通</a>";
                        var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
                        marker.addEventListener("mouseover", function () {
                            map.openInfoWindow(infoWindow, point); //开启信息窗口
                        });

                        marker.addEventListener("click", function (e) {
                            if (point1 == undefined) {
//                                console.info('change icon');
                                var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                                    offset: new BMap.Size(10, 25), // 指定定位位置
                                    imageOffset: new BMap.Size(0, -25) // 设置图片偏移
                                });
                                e.target.setIcon(myIcon);
                                point1 = new BMap.Point(e.target.getPosition().lng, e.target.getPosition().lat);
                            } else if (point2 == undefined) {
                                var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                                    offset: new BMap.Size(10, 25), // 指定定位位置
                                    imageOffset: new BMap.Size(0, -100) // 设置图片偏移
                                });
                                e.target.setIcon(myIcon);
                                point2 = new BMap.Point(e.target.getPosition().lng, e.target.getPosition().lat);
                                compute_drive_dis(point1, point2);
                                point1 = undefined;
                                point2 = undefined;
                            }
                        });
                    });
                }
            },
            error: function () {
                alert('加载景点失败！');
            }
        });
    }
    function getRestaurants(regionCode) {//该城市的餐厅的坐标
        $.ajax({
            url: '/restaurant/delicacyRestaurant/list.jhtml',
            dataType: 'json',
            method: 'POST',
            data: {
                cityCode: regionCode.length > 4 ? regionCode.substring(0, 4) : regionCode
            },
            success: function (data) {
                if (data.rows == undefined || data.rows.length == 0) {
//                    alert('没找到该城市已有的餐厅');
                } else {
                    $.each(data.rows, function (i, n) {
                        var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                            offset: new BMap.Size(10, 25), // 指定定位位置
                            imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
                        });
                        var point = new BMap.Point(n.geoInfo.baiduLng, n.geoInfo.baiduLat);
                        var marker = new BMap.Marker(point);
                        marker.setIcon(myIcon);
                        map.addOverlay(marker);
                        var opts = {
                            width: 100,     // 信息窗口宽度
                            height: 50,     // 信息窗口高度
                            title: n.resName, // 信息窗口标题
                            enableMessage: true,//设置允许信息窗发送短息
                            message: ""
                        }
                        var infoWindow = new BMap.InfoWindow(n.resAddress, opts);  // 创建信息窗口对象
                        marker.addEventListener("mouseover", function () {
                            map.openInfoWindow(infoWindow, point); //开启信息窗口
                        });
                    });

                }
            },
            error: function () {
                alert('加载餐厅失败！');
            }
        });
    }
    function getPolygons(regionCode) {//该城市的所有酒店商圈区域
        $.ajax({
            url: '/region/hotelRegion/list.jhtml',
            dataType: 'json',
            method: 'POST',
            data: {
                cityCode: regionCode.length > 4 ? regionCode.substring(0, 4) : regionCode,
                type: 1
            },
            success: function (data) {
                if (data.rows == undefined || data.rows.length == 0) {
                    $.messager.alert('提示', '当前城市没有已存在的酒店区域', 'warning');
//                    alert('没找到该城市已有的经验区域');
                } else {
                    $.each(data.rows, function (i, n) {
                        if ("${region.id}" != n.id) { // 不加载当前编辑的区域
                            var arr = n.pointStr.split('],[');
                            var points = new Array();
                            for (var i = 0; i < arr.length; i++) {
                                var ps = arr[i].replace('[', '').replace(']', '').split(',');
                                points.push(new BMap.Point(ps[0], ps[1]));
                            }
                            var polygon = new BMap.Polygon(points, {
                                strokeColor: "#228B22",
                                fillColor: "",
                                strokeWeight: 3,
                                strokeOpacity: 0,
                                fillOpacity: 0
                            });
                            map.addOverlay(polygon);
                            var infoWindow;
                            polygon.addEventListener("mouseover", function (e) {
                                var opts = {
                                    width: 60,     // 信息窗口宽度
                                    height: 30,     // 信息窗口高度
                                    title: "<span style=\"font-size:16px;font-weight:bold\">区域：" + n.name + "</span>", // 信息窗口标题
                                    enableMessage: true,//设置允许信息窗发送短息
                                    message: "",
                                }
                                /* console.log(polygon.getBounds().getCenter()); */
                                var info = n.description + "<br/>优先级:" + "${region.priority}";
                                infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
                                map.openInfoWindow(infoWindow, polygon.getBounds().getCenter()); //开启信息窗口
                            });
                            polygon.addEventListener("mouseout", function (e) {
                                map.closeInfoWindow();
                            });
                        }
                    });
                }
                $.messager.progress('close');
            },
            error: function () {
                $.messager.alert('提示', "酒店区域加载失败!", 'error');
//                alert('加载区域失败！');
            }
        });
    }

    function compute_drive_dis(point1, point2) {
        var options = {
            onSearchComplete: function (results) {
                if (driving.getStatus() == BMAP_STATUS_SUCCESS) {
                    var plan = results.getPlan(0);
                    document.getElementById("r-result").innerHTML = "距离：" + plan.getDistance(true) + "<br/>时间：" + plan.getDuration(true);
                }
            }
        };
        var driving = new BMap.DrivingRoute(map, options);
        driving.search(point1, point2);
    }
    var scenicLoader = function (param, success, error) {
        var q = param.q || '';
        q = encodeURI(q);
        if (q.length <= 1) {
            return false
        }
        $.ajax({
            url: '/scenic/scenicInfo/listCoordinates.jhtml',
            dataType: 'json',
            data: {
                rows: 10,
                name: q,
                cityCode: $('#regionCode').val().substring(0, 4)
            },
            success: function (msg) {
                var data = msg.resultList;
                var items = $.map(data.data, function (item) {
                    return {
                        scenicId: item.scenicId,
                        scenicName: item.scenicName,
                        longitude: item.longitude,
                        latitude: item.latitude
                    };
                });
                success(items);
            },
            error: function () {
                error.apply(this, arguments);
            }
        });
    };
    function selectScenic(data) {
        var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
            offset: new BMap.Size(10, 25), // 指定定位位置
            imageOffset: new BMap.Size(0, 0) // 设置图片偏移
        });
        var point = new BMap.Point(data.longitude, data.latitude);
        map.centerAndZoom(point, 15);
        var marker = new BMap.Marker(point);
        marker.setIcon(myIcon);
        map.addOverlay(marker);
    }
</script>
</body>
</html>
