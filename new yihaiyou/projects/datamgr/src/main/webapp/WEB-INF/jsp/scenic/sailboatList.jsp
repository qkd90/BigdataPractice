<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <title>港口列表</title>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<%--onload="initProvince(); $('#qry_province').combobox('select', '350000');$('#qry_city').combobox('select', '350200');">--%>
<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <!-- 查询条件 始 -->
    <div  id="toolbar">
        <table id="qryCondition">
            <tr>
                <td width="80" align="right">城市:</td>
                <td>
                    <input type="hidden" id="qry_cityCode">
                    <input type="hidden" id="qry_isChina">
                    <input id="qryCity" class="easyui-textbox"
                           data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                           style="width:200px" data-country="" data-province="" data-city="">
                    <%--<select class="easyui-combobox" name="province" id="qry_province" style="width:100px">--%>
                    <%--</select>--%>
                    <%--<select class="easyui-combobox" name="city" id="qry_city" style="width:100px">--%>
                    <%--</select>--%>
                </td>
                <td width="40" align="right">筛选:</td>
                <td>
                    <select class="easyui-combobox" name="condition" id="qry_condition" data-options="width: 80">
                        <option value="0" selected="selected">全部</option>
                        <%--<option value="1">已完善</option>--%>
                        <option value="2">未完善</option>
                        <option value="3">已下架</option>
                        <option value="4">已上架</option>
                        <option value="5">待审核</option>
                        <option value="6">未通过</option>
                        <option value="7">草稿箱</option>
                        <%-- <option value="8">无单票</option>--%>
                    </select>
                </td>
                <td width="80" align="right">附加筛选:</td>
                <td>
                    <select class="easyui-combobox" name="hasCoverImage" id="hasCoverImage" data-options="width: 100">
                        <option value="0" selected="selected">全部</option>
                        <option value="1">有封面</option>
                        <option value="2">无封面</option>
                        <option value="3">有父景点</option>
                        <option value="4">无父景点</option>
                        <option value="5">有游玩时间</option>
                        <option value="6">无游玩时间</option>
                        <option value="7">无地址</option>
                        <option value="8">无地址和时间</option>
                        <option value="9">未精确到区/县</option>
                    </select>
                </td>
                <td width="80" align="right">名称:</td>
                <td><input class="easyui-textbox" id="qry_name" name="name"
                           data-options="prompt:'请输入名称'" style="width:120px;"></td>
                <%--<td>创建人</td>--%>
                <%--<td>--%>
                <%--<select class="easyui-combobox" name="quid" id="qry_userId" style="width:120px">--%>
                <%--<option value="" selected="selected"></option>--%>
                <%--</select>--%>
                <%--<script>--%>
                <%--function initCreator() {--%>
                <%--$('#qry_userId').combobox({--%>
                <%--url: '${ctxPath}/mgrer/company/listName',--%>
                <%--valueField: 'id',--%>
                <%--textField: 'companyName'--%>
                <%--});--%>
                <%--}--%>
                <%--$(initCreator);--%>
                <%--</script>--%>
                <%--</td>--%>
                <td width="80" align="right">排名区间:</td>
                <td><input class="easyui-textbox" id="qry_orderStart" name="orderStart"
                           style="width:30px;"></td>
                <td width="10" align="center">-</td>
                <td><input class="easyui-textbox" id="qry_orderEnd" name="orderEnd" style="width:30px;">
                </td>
                <td>
                    <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px"
                       onClick="query()">查询</a>
                </td>
                <%--<td>--%>
                <%--<a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"--%>
                <%--onClick="reset()">重置</a>--%>
                <%--</td>--%>
            </tr>
        </table>
        <!-- 表格工具条 始 -->
        <div style="padding:2px 44px;">
            <a href="#" class="easyui-linkbutton" id="addButton"  onclick="add()">新增</a>
            <%--<a href="#" class="easyui-linkbutton" id="onSalesButton"   onclick="onSales()">批量上架</a>--%>
            <%--<a href="#" class="easyui-linkbutton" id="offSalesButton"   onclick="offSales()">批量下架</a>--%>
            <%--<a href="#" class="easyui-linkbutton" id="passButton"   onclick="offSales()">批量通过</a>--%>
            <%--<a href="#" class="easyui-linkbutton" id="noPassButton"   onclick="offSales()">批量不通过</a>--%>
        </div>
    </div>
    <!-- 查询条件 终-->

    <!-- 表格工具条 终 -->
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="qryResult"></table>
    </div>
    <!-- 数据表格 终-->
</div>

<div id="regionCodes"></div>
<%--<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div title="查询条件" data-options="region:'north',border:true" style="height:80px">

    </div>
    <div data-options="region:'center',border:false">

    </div>

</div>--%>
<div id = "addressInfoWin" class="easyui-window" style="width:570px;" data-options="closed:true">
    <div id="container" style="width: 550px;height: 200px;border: 1px solid gray;overflow:hidden;"></div>
    <input id="address" name="address" value="" type="hidden" />
    <input id="scenicIds" name="scenicIds" value="" type="hidden" />
    <input id="lat" name="lat" value="" type="hidden" />
    <input id="lng" name="lng" value="" type="hidden" />
</div>
</body>
</html>
<script type="text/javascript">
    $(pageInit);
    function pageInit() {
        initProvince();
//        $('#qry_province').combobox('select', null);
//        $('#qry_city').combobox('select', null);
    }
    function recordRegionCode(value) {
        if (value != undefined) {
            var exists = $('#record_' + value);
            if (exists.length == 0) {
                var record = '<input id="record_' + value + '" name="recordRegionCode" type="hidden" value="' + value + '">';
                $('#regionCodes').append(record);
            }
        }
        return '<span name="city_' + value + '"></span>';
    }
    function initShow(){
        initAreaShow();
        initTips();
    }
    function initAreaShow() {
        $('input[name="recordRegionCode"]').each(function () {
            var regionCode = $(this).val();
            $.get("${ctxPath}/common/area/get?cityCode=" + regionCode, function (result) {
                $('span[name="city_' + result.cityCode + '"]').append(result.fullPath);
            });
            $(this).remove();//清空，避免切换城市时冗余查询
        });
    }

    //地图预览
    var map = new BMap.Map("container");
    var localSearch = new BMap.LocalSearch(map);

    function showMapInfo(id,name,address,lng,lat){
        map.clearOverlays();
        $("#scenicIds").val(id);
        $("#address").val(address);
        $("#lng").val(lng);
        $("#lat").val(lat);
        $('#addressInfoWin').window('open');
        $('#addressInfoWin').panel({"title":name+"("+address+")"});
        map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
        //    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
        map.addControl(new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));   //右下角，打开

        var localSearch = new BMap.LocalSearch(map);
        localSearch.enableAutoViewport(); //允许自动调节窗体大小


        //    searchByAddress();
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
            map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
            var content = address + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
            var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            marker.addEventListener("click", function () {
                this.openInfoWindow(infoWindow);
            });
            // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        });
        localSearch.search(address);
        var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
            offset: new BMap.Size(10, 25), // 指定定位位置
            imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
        });
        var marker = new BMap.Marker(new BMap.Point(lng, lat));  // 创建标注，为要查询的地方对应的经纬度
        marker.setIcon(myIcon);
        map.addOverlay(marker);
        var content = "经度："+lng+"<br/>纬度："+lat;
        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
        marker.addEventListener("click", function () {
            this.openInfoWindow(infoWindow);
        });
    }
    function searchByAddress() {
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("address").value;
        if (keyword == undefined || keyword == '') {
            return;
        }
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
            $('#lng').val(poi.point.lng);
            $('#lat').val(poi.point.lat);
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


    // 查询
    function query() {
        $("#qryResult").datagrid("load", {});
    }

    // 重置
    function reset() {
        $('#qryCondition').form('clear');
    }

    // 新增
    function add() {
        window.parent.addTab('新增港口', "/scenic/scenicInfo/addSailboat.jhtml");
    }
    // 审核
    function audit(id) {
        window.parent.addTab('审核港口', "${ctxPath}/mgrer/scenic/audit?id=" + id);
    }
    // 修改
    function edit(id) {
//        alert(id);
        window.parent.addTab( '编辑港口', "/scenic/scenicInfo/editSailboat.jhtml?id=" + id);
    }
    // 批量下架
    function offSales() {
        var rows = $('#qryResult').datagrid('getSelections');
        if (rows.length < 1) {
            $.messager.alert('提示', '未勾选任何记录！', 'info');
            return null;
        }
        var sure = confirm("确定下架这些港口吗？");
        if (sure) {

            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                offSaleWithOutConfirm(row.id, row.name);
            }
        }
    }
    // 批量上架
    function onSales() {
        var rows = $('#qryResult').datagrid('getSelections');
        if (rows.length < 1) {
            $.messager.alert('提示', '未勾选任何记录！', 'info');
            return null;
        }
        var sure = confirm("确定批量上传这些港口吗？");
        if (sure) {
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                onSaleWithOutConfirm(row.id, row.name);
            }
        }
    }
    // 下架
    function offSaleWithOutConfirm(id, name) {
        $('#status_' + id)[0].innerHTML = '正在下架';
        $('#status_' + id).css("background-color", "orange");
        $('#status_' + id).css("color", "white");
        $.get("/scenic/scenicInfo/hide.jhtml?id=" + id, function(result) {
            $('#status_' + id)[0].innerHTML = '已下架';
            $('#status_' + id).css("background-color", "grey");
            $('#status_' + id).css("color", "white");
            $('#opt_' + id)[0].innerHTML = '上架';
            $('#opt_' + id).css("color", "blue");
            $('#opt_' + id).attr('onclick', 'onSale(' + id + ',\"' + name + '\")');
        });
    }
    //上架
    function onSaleWithOutConfirm(id, name) {
        $('#status_' + id)[0].innerHTML = '正在上架';
        $('#status_' + id).css("background-color", "orange");
        $('#status_' + id).css("color", "white");
        $.get("/scenic/scenicInfo/show.jhtml?id=" + id, function (result) {
            $('#status_' + id)[0].innerHTML = '已上架';
            $('#status_' + id).css("background-color", "green");
            $('#status_' + id).css("color", "white");
            $('#opt_' + id)[0].innerHTML = '取消上架';
            $('#opt_' + id).css("color", "blue");
            $('#opt_' + id).attr('onclick', 'offSale(' + id + ',\"' + name + '\")');
        });
    }
    // 下架
    function offSale(id, name) {
        var sure = confirm("确定下架[" + name + "]吗？");
        if (sure) {
            offSaleWithOutConfirm(id, name);
        }
    }
    //上架
    function onSale(id, name) {
        var sure = confirm("确定上架[" + name + "]吗？");
        if (sure) {
            onSaleWithOutConfirm(id, name);
        }
    }

    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
//            title: "港口列表",
            //data:[],
            thread: '',
            url: '/scenic/scenicInfo/list.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            onSelect: CheckSelect,
            onUnselect: CheckSelect,
            onSelectAll: CheckSelect,
            onUnselectAll: CheckSelect,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            queryParams: {
                type: "sailboat"
            },
            columns: [[{
                field: 'ck',
                checkbox: true
            }, {
                field: 'id',
                title: 'ID',
                align: "center",
                width: 100
            }, {
                field: 'ranking',
                title: '排名',
                align: "center",
                sortable: true,
                width: 100,
                formatter: orderFormatter
            }, {
                field: 'name',
                title: '名称',
                sortable: true,
                width: 200
            }, {
                field: 'city.fullPath',
                title: '所在地',
                width: 250,
            }, {
                field: 'ticket',
                title: '票价描述',
                width: 250,
                styler: freeOrNot
            }, {
                field: 'price',
                title: '参考价',
                sortable: true,
                align: 'center',
                styler: lowerPriceStyle,
                width: 80
            }, {
                field: 'scenicOther.adviceTimeDesc',
                title: '建议游玩时间',
                align: 'center',
                width: 150,
            },
                {
                    field: 'scenicOther.adviceTime',
                    title: '游玩分钟数',
                    align: 'center',
                    width: 80,
                },
                {
                    field: 'score',
                    title: '评分',
                    align: "center",
                    width: 50,
                }, {
                    field: 'modifyTime',
                    title: '修改时间',
                    align: "center",
                    width: 200,
                },
//            {
//                field: 'isCity',
//                title: '是否城市',
//                align: "center",
//                width: 50,
//                formatter: yesOrNot
//            }, {
//                field: 'isStation',
//                title: '是否交通',
//                align: "center",
//                width: 50,
//                formatter: yesOrNot
//            },
                {
                    field: "status",
                    title: "状态",
                    align: "center",
                    width: 100,
                    formatter: statusFormatter
                },
                {
                    field: "Map",
                    title: "地图",
                    align: "center",
                    width: 80,
                    formatter: function(value, rowData, rowIndex){
                        var iconBtn = "<a href='#' class='l-btn-text icon-map' style='width: 16px;' onClick='showMapInfo("+rowData.id+",\""+rowData.name+"\",\""+rowData.scenicOther.address+"\","+rowData.scenicGeoinfo.baiduLng+","+rowData.scenicGeoinfo.baiduLat+")'>&nbsp;</a>";
                        return iconBtn;
                    }
                },
                {
                    field: "OPT",
                    title: "操作",
                    align: "left",
                    width: 120,
                    formatter: optFormatter
                }
            ]],
            toolbar: '#toolbar',
            onBeforeLoad: function (data) {   // 查询参数
//                var regionCode = $('#qry_city').combobox('getValue');
//                if (regionCode == '') {
//                    regionCode = $('#qry_province').combobox('getValue');
//                    data.cityCode = regionCode.substr(0, 2);
//                }
//                else {
//                    data.cityCode = regionCode.substr(0, 4);
//                }
                var regionCode = $('#qry_cityCode').val();
                if (regionCode && regionCode!= "") {
                    data.cityCode = regionCode;
                }
                data.isChina = $("#qry_isChina").val();
                data.name = $("#qry_name").textbox("getValue");
//                data.updatedBy = $("#qry_userId").combobox("getValue");
                var condition = $('#qry_condition').combobox('getValue');
                switch (condition) {
                    case '1':
                        data.perfection = 1;
                        break;
                    case '2':
                        data.perfection = -1;
                        break;
                    case '3':
                        data.status = 0;
                        break;
                    case '4':
                        data.status = 1;
                        break;
                    case '5':
                        data.status = 2;
                        break;
                    case '6':
                        data.status = -99;
                        break;
                    case '7':
                        data.status = -1;
                        break;
                    case '8':
                        data.noSingleTicket = 1;
                        break;
                }
                var hasCoverImage = $('#hasCoverImage').combobox("getValue");
                switch(hasCoverImage){
                    case '1':
                        data.needCoverImage = 1;
                        break;
                    case '2':
                        data.notNeedCoverImage = 1;
                        break;
                    case '3':
                        data.hasFatherScenic = 1;
                        break;
                    case '4':
                        data.hasFatherScenic = -1;
                        break;
                    case '5':
                        data.hasAdvicehours = 1;
                        break;
                    case '6':
                        data.hasAdvicehours = -1;
                        break;
                    case '7':
                        data.hasAddress = -1;
                        break;
                    case '8':
                        data.hasAddressAndHours = -1;
                        break;
                    case '9':
                        data.noArea = 1;
                        break;
                }
                data.orderStart = $("#qry_orderStart").textbox("getValue");
                data.orderEnd = $("#qry_orderEnd").textbox("getValue");
                data.scenicType = "sailboat";
            },
            onLoadSuccess: initShow
        });
    });

    function editOrder(regionCode, id, oldOrder) {
        var newOrder = prompt("请输入新排名");
        regionCode = regionCode + '';
        if (newOrder) {
            $.ajax({
                url: '/scenic/scenicInfo/updateOrder.jhtml',
                dataType: 'json',
                method: 'POST',
                data: {
                    regionCode: regionCode.substr(0, 4),
                    id: id,
                    oldOrder: oldOrder,
                    newOrder: newOrder
                },
                success: function (msg) {
                    alert('成功');
                    $('#qry_city').combobox('select', regionCode.substr(0, 4) + '00');
                    $("#qry_orderStart").textbox("setValue", newOrder > 7 ? (newOrder - 7) : 0);
                    $("#qry_orderEnd").textbox("setValue", parseInt(newOrder) + 7);
                    query();
                    $('#qryResult').datagrid('sort', {
                        sortName: 'ranking',
                        sortOrder: 'asc'
                    });
                },
                error: function () {
                    alert('修改失败！');
                }
            });
        }

    }
    function yesOrNot(value, rowData) {
        return value == 1 ? '是' : '否';
    }
    function statusFormatter(value, rowData) {
        var btnEdit = "<a id='status_" + rowData.id + "' href='javascript:void(0)' ";
        if (rowData.status == 1) {
            btnEdit += "style='color:green;text-decoration: none;' ";
            btnEdit += " onclick='offSale(" + rowData.id + ",\"" + rowData.name + "\")'>已上架</a>";
        }
        else if (rowData.status == 0) {
            btnEdit += "style='color:gray;text-decoration: none;' ";
            btnEdit += "onclick='onSale(" + rowData.id + ",\"" + rowData.name + "\")'>已下架</a>";
        }
        else if (rowData.status == -99) {
            btnEdit += "style='background-color:red;color:white;text-decoration: underline;' ";
            btnEdit += ">审核未通过</a>";
        }
        else if (rowData.status == -1) {
            btnEdit += "style='color:blue;text-decoration: underline;' ";
            btnEdit += ">草稿</a>";
        }
        else if (rowData.status == 2) {
            btnEdit += "style='color:red;text-decoration: underline;' ";
            btnEdit += ">审核中</a>";
        }
        return btnEdit;
    }
    function optFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.id + ")'>编辑</a>";
        btnEdit += "&nbsp;&nbsp;<a id='opt_" + rowData.id + "' href='javascript:void(0)' ";
        if (rowData.status == 1) {
            btnEdit += "style='color:gray;text-decoration: underline;' ";
            btnEdit += " onclick='offSale(" + rowData.id + ",\"" + rowData.name + "\")'>下架</a>";
        } else if (rowData.status == 2) {
            btnEdit += "style='color:gray;text-decoration: underline;' ";
            btnEdit += " onclick='audit(" + rowData.id + ",\"" + rowData.name + "\")'>审核</a>";
        } else if (rowData.status == 0 || rowData.status == -1) {
            btnEdit += "style='color:green;text-decoration: underline;' ";
            btnEdit += "onclick='onSale(" + rowData.id + ",\"" + rowData.name + "\")'>上架</a>";
        } else {
            btnEdit += "></a>";
        }
//        btnEdit += "&nbsp;&nbsp;<a href='#' style='color:blue;text-decoration: underline;'" +
//                " id='relation_" + rowData.id + "' class='easyui-tooltip1' data-p1='" + rowIndex + "'>匹配</a>";
        return btnEdit;
    }

    function orderFormatter(value, rowData) {
        var btnEdit = value + "&nbsp;" + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;'" +
                " onclick='editOrder(" + rowData.city.cityCode + "," + rowData.id + "," + rowData.ranking + ")'>改</a>";
        return btnEdit;
    }

    function freeOrNot(value, row, index) {
        if (value != undefined && value.indexOf('免费') != -1) {
            return 'color:grey;';
        }
    }

    function lowerPriceStyle(value, row, index) {
        if (value != undefined && value <= 30) {
            return 'color:green;';
        }
    }
    function CheckSelect(index, row) {
        $('#onSalesButton').show();
        $('#offSalesButton').show();
        $('#passButton').show();
        $('#noPassButton').show();
        var rows = $('#qryResult').datagrid('getSelections');
        var onSale = false;
        var offSale = false;
        var onCheck = false;
        var faild = false;
        var draft = false;
        for (var i = 0; i < rows.length; i++) {
            if (row.status == 2) {//待审核
                onCheck = true;
            } else if (row.status == 1) {//已上架
                onSale = true
            } else if (row.status == 0) {//已下架
                offSale = true;
            } else if (row.status == -1) {//草稿
                draft = true;
            } else if (row.status == -99) {//未通过
                faild = true;
            }
        }
        if (onSale) {
            $('#onSalesButton').hide();
            $('#passButton').hide();
            $('#noPassButton').hide();
        }
        if (offSale) {
            $('#offSalesButton').hide();
            $('#passButton').hide();
            $('#noPassButton').hide();
        }
        if (onCheck) {
            $('#onSalesButton').hide();
            $('#offSalesButton').hide();
        }
        if (draft) {
            $('#passButton').hide();
            $('#noPassButton').hide();
        }
        if (faild) {
            $('#onSalesButton').hide();
            $('#offSalesButton').hide();
            $('#passButton').hide();
            $('#noPassButton').hide();
        }
    }
    function initTips() {
        $('#qryResult').datagrid('getPanel').find('.easyui-tooltip1').each(function () {
            var index = parseInt($(this).attr('data-p1'));
            $(this).tooltip({
                content: $('<div>加载中</div>'),
                hideEvent: 'none',
                onUpdate: function (cc) {
                    var row = $('#qryResult').datagrid('getRows')[index];
                    cc.panel({
                        title: row.name,
                        closable: true,
                        width: 500,
                        height: 'auto',
                        border: false,
                        href: '${ctxPath}/mgrer/scenicRelation/getSimilar?sid=' + row.id + '&sname=' + row.name,
                        onLoad: function () {
                            var row = $('#qryResult').datagrid('getRows')[index];
//                            $('#info_' + row.ctripId).css('color', 'green');
//                            $('#info_' + row.cncnId).css('color', 'green');
//                            $('#info_' + row.tuniuId).css('color', 'green');
//                            $('#info_' + row.mfwId).css('color', 'green');
//                            $('#info_' + row.lyId).css('color', 'green');
//
//                            $('#ck_' + row.ctripId).attr('checked', true);
//                            $('#ck_' + row.cncnId).attr('checked', true);
//                            $('#ck_' + row.tuniuId).attr('checked', true);
//                            $('#ck_' + row.mfwId).attr('checked', true);
//                            $('#ck_' + row.lyId).attr('checked', true);

                            $('#search_' + row.id).click(function () {
                                var sname = $('#searchValue_' + row.id).val();
                                cc.panel({
                                    title: row.name + "关联",
                                    closable: true,
                                    width: 500,
                                    height: 'auto',
                                    border: false,
                                    href: '${ctxPath}/mgrer/qunar/sight/getSimilar?sid=' + row.id + '&sname=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
                                        $('#search_' + row.id).click(function () {

                                        });
                                        $('#info_' + row.scenicIds).css('color', 'green');
                                        $('#ck_' + row.scenicIds).attr('checked', true);
                                    }
                                });
                            });
                        }
                    });
                },
                onShow: function () {
                    var t = $(this);
                    t.tooltip('tip').mouseleave(function () {//鼠标离开tip
                        t.tooltip('hide');
                    });
                    t.tooltip('tip').focus().blur(function () {//失去焦点
                        t.tooltip('hide');
                    });
                    t.tooltip('tip').mouseenter(function () {//当鼠标移入tip，unbind，去掉blur的效果，兼容Chrome失焦
                        t.tooltip('tip').unbind();
                        t.tooltip('tip').mouseleave(function () {
                            t.tooltip('hide');
                        })
                    });
                },
                position: 'right'
            });
        });
    }
</script>