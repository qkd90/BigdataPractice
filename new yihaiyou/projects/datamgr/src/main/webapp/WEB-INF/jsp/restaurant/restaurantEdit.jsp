<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>管理后台</title>
    <%@include file="../common/common141.jsp" %>
    <script type="text/javascript" src="/js/area.js"></script>
    <script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
    <script>
        $(pageInit);

        function pageInit() {
            $('#contactDiv').css("display", "block");
            $('#submitDiv').css("display", "block");
            $('#imagePanel').diyUpload({
                url: "/restaurant/delicacyRestaurant/image/upload.jhtml?restaurantName=" + $('#foodName').val(),
                success: function (data) {
                    //上传成功
                    $('#resPicture').val(data.data);
                },
                error: function (err) {
                    console.info(err);
                },
                buttonText: '上传',
                chunked: true,
                // 分片大小
                chunkSize: 512 * 1024,
                //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
                fileNumLimit: 1,
                fileSizeLimit: 500000 * 1024,
                fileSingleSizeLimit: 50000 * 1024,
                accept: {}
            });
            initProvince();
        }
        var cityCode = '${restaurant.city.cityCode}';
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
                }
                <c:if test="${restaurant.city.cityCode >0 && restaurant.city.cityCode % 10000 > 0}">,
                onLoadSuccess: function () {
                    if ($('#cityCode').val() == (cityCode.substring(0, 2) + '0000')) {
                        $('#addr_city').combobox('select', cityCode.substring(0, 4) + '00');
                    }
                }
                </c:if>
            });
        }
        function initProvince() {
            $('#addr_province').combobox({
                url: '/sys/area/getProvinces.jhtml',
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    if (sel == undefined)return;
                    $('#cityCode').prop('value', sel.cityCode);
                    initCity(sel);
                }<c:if test="${restaurant.city.cityCode >0}">,
                onLoadSuccess: function () {
                    var cityCode = '${restaurant.city.cityCode}';
                    $('#addr_province').combobox('select', cityCode.substring(0, 2) + '0000');
                }</c:if>
            });
        }
        function submitRestaurant() {
            $('#scenic_edit_form').form('submit', {
                url: '/restaurant/delicacyRestaurant/saveRestaurant.jhtml',
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
    <style>
        #imageBox {
            margin: 3px 3px;
            width: 300px;
            height: 200px;
            background: #e4f5ff
        }
    </style>
</head>
<body style="overflow: auto">
<form id="scenic_edit_form" action="/restaurant/delicacyRestaurant/saveRestaurant.jhtml" method="post" enctype="multipart/form-data">
    <div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
        <div title="基本信息"  selected="selected" style="padding:10px;">
            <div style="width:650px;margin-top: 10px;float:right">
                <div id="imageBox">
                    <div id="imagePanel"></div>
                    <c:if test="${restaurant.id > 0}">
                        <script>
                            var id = ${restaurant.id};
                            var address = '${restaurant.cover}';
                            if(!address.startsWith("http:"))
                                address = BizConstants.QINIU_DOMAIN + address;
                            showImage($('#imagePanel'), address, id);
                            //                        $('#fileBox_' + id).find('.diyCancel').click(function () {//增加事件
                            //                            var removed = '<input type="hidden" name="removedImgs" value="' + id + '"> ';
                            //                            $('#imageBox').append(removed);
                            //                        });
                        </script>
                    </c:if>
                </div>
            </div>

            <table>
                <tr>
                    <td>餐厅名称:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" id="resName" value="${restaurant.name}"
                               data-options="required:true">
                    </td>

                    <td>评分:</td>
                    <td>
                        <input name="score" value="${restaurant.score}" class="easyui-numberspinner"
                               data-options="min:0,max:100,required:true">
                    </td>
                </tr>

                <tr>
                    <td>电话:</td>
                    <td><input name="telephone" value="${restaurant.extend.telephone}"
                               class="easyui-textbox" type="text" name="tel"
                               data-options="required:true,validType:'phoneRex',prompt:'请填写手机或座机号码'">
                    </td>
                    <td>价格:</td>
                    <td>
                        <input name="price" id="price" value="${restaurant.price >0?restaurant.price:0}"
                               class="easyui-numberspinner" data-options="min:0,required:true">
                    </td>
                </tr>
                <tr>
                    <td>区域:</td>
                    <td>
                        <select class="easyui-combobox" name="province" id="addr_province">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                        <select class="easyui-combobox" name="cityCode" id="addr_city">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                    </td>
                    <td>经度:</td>
                    <td>
                        <input id="lng" name="baiduLng" class="easyui-textbox"
                               data-options="required:true,prompt:'可按地址获取...'"
                               type="text" value="${restaurant.geoInfo.baiduLng}"/>
                    </td>
                </tr>
                <tr>
                    <td>地址:</td>
                    <td><input id="address" name="address" value="${restaurant.extend.address}"
                               class="easyui-textbox" type="text" data-options="required:true">
                    </td>

                    <td>纬度:</td>
                    <td>
                        <input id="lat" name="baiduLat" class="easyui-textbox"
                               data-options="required:true,prompt:'可按地址获取...'"
                               type="text" value="${restaurant.geoInfo.baiduLat}"/>
                    </td>

                </tr>
                <tr>
                    <td>餐厅特色:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="feature" value="${restaurant.feature}"
                               data-options="required:true">
                    </td>
                    <td>热度:</td>
                    <td>
                        <input name="hotNum" value="${restaurant.hotNum}" class="easyui-numberspinner"
                               data-options="min:0,max:100,required:true">
                    </td>
                </tr>
                <tr>
                    <td>营业时间:</td>
                    <td><input name="shopHours" value="${restaurant.extend.shopHours}" class="easyui-textbox"
                               data-options="multiline:true" style="width:200px;height:100px"></td>
                </tr>
            </table>
            <div>
                <div id="container"
                     style="width: 550px;height: 200px;border: 1px solid gray;overflow:hidden;">
                </div>
                <a href="javascript:void(0)" style="width:150px;height:30px" class="easyui-linkbutton"

                   onclick="searchByStationName()">按地址获取经纬度</a>
            </div>

        </div>
        <div id="submitDiv" title="提交" style="text-align:right;padding:5px 10px">
            <input type="hidden" name="status" id="status" value="${restaurant.status}">
            <input type="hidden" name="cover" id="resPicture" value="${restaurant.cover}">
            <input type="hidden" name="id" value="${restaurant.id }"/>
            <input type="hidden" name="sourceId" value="${restaurant.sourceId}"/>
            <input type="hidden" id="cityCode" name="cityCode" value="${restaurant.city.cityCode}"/>
            <!--<p>此处预览</p>-->
            <div style="padding:5px 0;">
                <p style="color:red;">管理员提交即时生效，请谨慎操作</p>

                <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                   onclick="if(confirm('确定提交吗？')){submitRestaurant()}"
                        >提交</a>
                <c:if test="${restaurant.id == null}">
                    <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                       onclick="if(confirm('不保存并关闭本页？')){window.parent.closeTab('新增餐厅', '餐厅管理','');}"
                            >取消</a>
                </c:if>
                <c:if test="${restaurant.id > 0}">
                    <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                       onclick="if(confirm('不保存并关闭本页？')){window.parent.closeTab('编辑餐厅', '餐厅管理','');}"
                            >取消</a>
                </c:if>
            </div>
        </div>
    </div>

</form>
</body>
<script type="text/javascript">
    var map = new BMap.Map("container");
    //    map.centerAndZoom("", 12);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    <c:if test="${restaurant.id > 0 }">
    //    searchByAddress();
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        map.centerAndZoom(poi.point, 13);
        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
        map.addOverlay(marker);
        var content = document.getElementById("address").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
        marker.addEventListener("click", function () {
            this.openInfoWindow(infoWindow);
        });
        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    });
    localSearch.search(document.getElementById("address").value);
    var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
        offset: new BMap.Size(10, 25), // 指定定位位置
        imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
    });
    var marker = new BMap.Marker(new BMap.Point(${restaurant.geoInfo.baiduLng}, ${restaurant.geoInfo.baiduLat}));  // 创建标注，为要查询的地方对应的经纬度
    marker.setIcon(myIcon);
    map.addOverlay(marker);
    var content = "经度：${restaurant.geoInfo.baiduLng}<br/>纬度：${restaurant.geoInfo.baiduLat}";
    var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
    marker.addEventListener("click", function () {
        this.openInfoWindow(infoWindow);
    });
    </c:if>
    function searchByStationName() {
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("address").value;
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
//            document.getElementById("lng").value = poi.point.lng;
//            document.getElementById("lat").value = poi.point.lat;
            $('#lng').textbox('setValue', poi.point.lng);
            $('#lat').textbox('setValue', poi.point.lat);
            map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
            var content = document.getElementById("address").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
            var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            marker.addEventListener("click", function () {
                this.openInfoWindow(infoWindow);
            });
            // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        });
        localSearch.search(keyword);
    }
</script>
</html>