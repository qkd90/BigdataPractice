<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
    <title>餐厅列表</title>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
        <div id="toolbar" style="padding:10px 0 5px 10px">
            <table id="qryCondition">
                <tr>
                    <td width="80" align="right">城市:</td>
                    <td>
                        <input type="hidden" id="qry_cityCode">
                        <input type="hidden" id="qry_isChina">
                        <input id="qryCity" class="easyui-textbox"
                               data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                               style="width:200px" data-country="" data-province="" data-city="">
                        <%--<select class="easyui-combobox" name="province" id="qry_province" style="width:120px">--%>
                            <%--<option value="" selected="selected"></option>--%>
                        <%--</select>--%>
                        <%--<select class="easyui-combobox" name="city" id="qry_city" style="width:120px">--%>
                            <%--<option value="" selected="selected"></option>--%>
                        <%--</select>--%>
                    </td>
                     <td width="80" align="right">封面筛选:</td>
                     <td>
                        <select class="easyui-combobox" name="hasCoverImage" id="hasCoverImage">
                            <option value="0" selected="selected">全部</option>
                            <option value="1">有封面</option>
                            <option value="2">无封面</option>
                        </select>
                    </td>
                    <td width="80" align="right">名称:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输入餐厅名称'" style="width:200px;"></td>
                    <td></td>
                    <td></td>
                    <td>
                        <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px;margin-left: 12px;"
                           onClick="query()">查询</a>
                    </td>
                    <td>
                        <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px;margin-left: 12px;"
                           onClick="reset()">重置</a>
                    </td>
                </tr>
            </table>
            <div style="padding:2px 44px;">
                <a href="#" class="easyui-linkbutton" id="addButton"  onclick="add()">新增美食</a>
                <a href="#" class="easyui-linkbutton" id="addButton2"   onclick="addRestaurant()">新增餐厅</a>
            </div>
        </div>
    <div data-options="region:'center',border:false">
        <div id="cityCodes"></div>
        <table id="qryResult"></table>
    </div>

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
    <%--function initCity(val) {--%>
        <%--var cityCode = '0';--%>
        <%--if (val != undefined && val != 'undefined')--%>
            <%--cityCode = val.cityCode;--%>
        <%--$('#qry_city').combobox({--%>
            <%--url: '${ctxPath}/common/area/getChild?father=' + cityCode,--%>
            <%--valueField: 'cityCode',--%>
            <%--textField: 'name',--%>
            <%--onSelect: function (sel) {--%>
                <%--//alert(sel);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--function initProvince() {--%>
        <%--$('#qry_province').combobox({--%>
            <%--url: '${ctxPath}/common/area/getProvinces.json',--%>
            <%--valueField: 'cityCode',--%>
            <%--textField: 'name',--%>
            <%--onSelect: function (sel) {--%>
                <%--initCity(sel)--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--function recordCityCode(value) {--%>
        <%--if (value != undefined) {--%>
            <%--var exists = $('#record_' + value);--%>
            <%--if (exists.length == 0) {--%>
                <%--var record = '<input id="record_' + value + '" name="recordCityCode" type="hidden" value="' + value + '">';--%>
                <%--$('#cityCodes').append(record);--%>
            <%--}--%>
        <%--}--%>
        <%--return '<span name="city_' + value + '"></span>';--%>
    <%--}--%>
    <%--function initAreaShow() {--%>
        <%--$('input[name="recordCityCode"]').each(function () {--%>
            <%--var cityCode = $(this).val();--%>
            <%--$.get("${ctxPath}/common/area/get?cityCode=" + cityCode, function (result) {--%>
                <%--$('span[name="city_' + result.cityCode + '"]').append(result.fullPath.replace('||', '-'));--%>
            <%--});--%>
            <%--$(this).remove();//清空，避免切换城市时冗余查询--%>
        <%--});--%>
    <%--}--%>
    // 查询
    function query() {
        $("#qryResult").datagrid("load", {});
    }

    // 重置
    function reset() {
        $('#qryCondition').form('clear');
    }

    // 餐厅上下架
    function changeStatus(id, name, btnTxt) {
        var sure = confirm(" 确定要" + btnTxt + ": " + name + " 吗?");
        var status = 1;
        if (sure) {
            $('#status_' + id)[0].innerHTML = '正在' + btnTxt;
            $('#status_' + id).css("background-color", "orange");
            $('#status_' + id).css("color", "white");
            if (btnTxt == "下架") {
                status = 2;
            }
            $.get("/restaurant/delicacyRestaurant/changeStatus.jhtml?resId=" + id + "&status=" + status , function (result) {
                if (result.msg = "scccess") {
                    $('#status_' + id)[0].innerHTML = (btnTxt == '上架') ? '已上架' : '已下架';
                    $('#status_' + id).css("background-color", "");
                    $('#status_' + id).css("color", btnTxt == "上架" ? "green" : "gray");
                    $('#opt_' + id)[0].innerHTML = btnTxt == "上架" ? "下架" : "上架";
                    $('#opt_' + id).css("color", btnTxt == "上架" ? "gray" : "green");
                    var txt = (btnTxt == "上架") ? "下架" : "上架";
                    $('#opt_' + id).attr('onclick', 'changeStatus(' + id + ',\"' + name + '\",\"'+ txt + '\")');
                } else {
                    $.messager.alert('上下架提示', '操作失败! 检查参数!', 'error');
                }
            });
        }
    }

    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
//            title: "景点列表",
            thread: '',
            url: '/restaurant/delicacyRestaurant/list.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
            fitColumns: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            columns: [[{
                field: 'ck',
                checkbox: true
            },{
                field: 'id',
                title: 'ID',
                width: 80,
                align: "center"
            }, {
                field: 'cover',
                title: '图片',
                formatter: imgFormatter,
                width: 100
            }, {
                field: 'name',
                title: '名称',
                sortable: true,
                width: 280,
                align: "center"
            }, {
                field: 'price',
                title: '价格',
                sortable: true,
                width: 40,
                align: "center"
            }, {
                field: 'city.cityCode',
                title: '所在地',
                width: 250,
                align: "center"
            }, {
                field: 'extend.address',
                title: '地址',
                sortable: true,
                width: 340
            }, {
                field: "status",
                title: "状态",
                align: "center",
                width: 60,
                formatter: statusFormatter
            }, {
                field: "OPT",
                title: "操作",
                align: "center",
                width: 290,
                formatter: optFormatter
            }
            ]],
            toolbar: '#toolbar',
            onBeforeLoad: function (data) {   // 查询参数

                data.resName = $("#qry_name").textbox("getValue");
                var regionCode = $('#qry_cityCode').val();
                if (regionCode && regionCode!= "") {
                    data.cityCode = regionCode;
                }
                data.isChina = $("#qry_isChina").val();
//                var cityCode = $('#qry_city').combobox('getValue');
//                if (cityCode == '') {
//                    cityCode = $('#qry_province').combobox('getValue');
//                    data.cityCode = cityCode.substr(0, 2);
//                }
//                else {
//                    data.cityCode = cityCode.substr(0, 4);
//                }
                var hasCoverImage = $('#hasCoverImage').combobox("getValue");
                switch(hasCoverImage){
                	case '1':
                		data.needCoverImage = 1;
                		break;
                	case '2':
                		data.notNeedCoverImage = 1;
                		break;
                }
//                var condition = $('#qry_condition').combobox('getValue');
//                switch (condition) {
//                    case '1':
//                        data.relationed = -1;
//                        break;
//                    case '2':
//                        data.relationed = 1;
//                        break;
//                }
            }, onLoadSuccess: init
        });
    });
    function init() {
//        initAreaShow();
    }
    // 新增
    function add() {
        window.parent.addTab('新增美食', "/restaurant/delicacy/add.jhtml");
    }
    // 新增餐厅
    function addRestaurant() {
        window.parent.addTab('新增餐厅', "/restaurant/delicacyRestaurant/add.jhtml");
    }
    // 修改
    function edit(id) {
        window.parent.addTab('编辑餐厅', "/restaurant/delicacyRestaurant/edit.jhtml?id=" + id);
    }
    function statusFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a id='status_" + rowData.id + "'";
        if (rowData.status == 1) {
            btnEdit += " style='color:green;text-decoration: none;'>已上架</a>";
        }
        else if (rowData.status == 2) {
            btnEdit += " style='color:gray;text-decoration: none;'>已下架</a>";
        }
        return btnEdit;
    }
    function optFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a id='opt_" + rowData.id + "' href='javascript:void(0)' ";
        if (rowData.status == 1) {
            btnEdit += "style='color:gray;text-decoration: underline;' ";
            btnEdit += " onclick='changeStatus(" + rowData.id + ",\"" + rowData.name + "\",\"下架\")'>下架</a>";
        } else if (rowData.status == 2) {
            btnEdit += "style='color:green;text-decoration: underline;' ";
            btnEdit += " onclick='changeStatus(" + rowData.id + ",\"" + rowData.name + "\",\"上架\")'>上架</a>";
        }
        btnEdit += "&nbsp;&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.id + ")'>编辑</a>";
        return btnEdit;
    }
    function imgFormatter(value, rowData, rowIndex) {
        if (rowData.cover != undefined && rowData.cover != '') {
            return '<img src="' + BizConstants.QINIU_DOMAIN + rowData.cover + '?imageView2/1/w/100/h/80/q/75' + '"/>';
        }
        return '<span style="color: red">无</span>';
    }

</script>