<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../common/head.jsp" %>
    <title>景点排名列表</title>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div title="查询条件" data-options="region:'north',border:true" style="height:80px">
        <div style="padding:10px 0 5px 10px">
            <table id="qryCondition">
                <tr>
                    <td width="80" align="right">城市:</td>
                    <td>
                        <select class="easyui-combobox" name="province" id="qry_province" style="width:120px">
                            <option value="" selected="selected"></option>
                        </select>
                        <select class="easyui-combobox" name="city" id="qry_city" style="width:120px">
                            <option value="" selected="selected"></option>
                        </select>
                    </td>
                    <td width="80" align="right">景点名称:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输入景点名称'" style="width:100px;"></td>
                    <td width="80" align="right">排名区间:</td>
                    <td><input class="easyui-textbox" id="qry_orderStart" name="orderStart"
                               style="width:100px;"></td>
                    <td width="10" align="center">-</td>
                    <td><input class="easyui-textbox" id="qry_orderEnd" name="orderEnd" style="width:100px;"
                            >
                    </td>
                    <td>
                        <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"
                           onClick="query()">查询</a>
                    </td>
                    <td>
                        <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"
                           onClick="reset()">重置</a>
                    </td>
                </tr>
            </table>
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
        $('#qry_province').combobox('select', null);
        $('#qry_city').combobox('select', null);
    }
    function recordCityCode(value) {
        if (value != undefined) {
            var exists = $('#record_' + value);
            if (exists.length == 0) {
                var record = '<input id="record_' + value + '" name="recordCityCode" type="hidden" value="' + value + '">';
                $('#cityCodes').append(record);
            }
        }
        return '<span name="city_' + value + '"></span>';
    }
    function initAreaShow() {
        $('input[name="recordCityCode"]').each(function () {
            var cityCode = $(this).val();
            $.get("${ctxPath}/common/area/get?cityCode=" + cityCode, function (result) {
                $('span[name="city_' + result.cityCode + '"]').append(result.fullPath);
            });
            $(this).remove();//清空，避免切换城市时冗余查询
        });
    }
    // 查询
    function query() {
        $("#qryResult").datagrid("load", {});
    }

    // 重置
    function reset() {
        $('#qryCondition').form('clear');
    }



    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
            title: "景点列表",
            thread: '',
            url: '${ctxPath}/mgrer/scenic/list.json',
            border: true,
            singleSelect: false,
            striped: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            columns: [[ {
                field: 'id',
                title: 'ID',
                width: 60
            }, {
                field: 'name',
                title: '名称',
                sortable: true,
                width: 200
            }, {
                field: 'cityCode',
                title: '所在地',
                width: 100,
                formatter: recordCityCode
            }, {
                field: 'ticket',
                title: '票价描述',
                width: 180,
                styler: freeOrNot
            }, {
                field: 'price',
                title: '参考价',
                sortable: true,
                styler: lowerPriceStyle,
                width: 100
            }, {
                field: 'adviceTime',
                title: '建议游玩时间',
                align: 'center',
                width: 120,
            }, {
                field: 'score',
                title: '评分',
                align: "center",
                width: 60,

            }, {
                field: "status",
                title: "状态",
                align: "center",
                width: 100,
                formatter: statusFormatter
            }
            ]],
            onBeforeLoad: function (data) {   // 查询参数
                var cityCode = $('#qry_city').combobox('getValue');
                if (cityCode == '') {
                    cityCode = $('#qry_province').combobox('getValue');
                    data.cityCode = cityCode.substr(0, 2);
                }
                else {
                    data.cityCode = cityCode.substr(0, 4);
                }
                data.name = $("#qry_name").textbox("getValue");
                data.orderStart = $("#qry_orderStart").textbox("getValue");
                data.orderEnd = $("#qry_orderEnd").textbox("getValue");
            },
            onLoadSuccess: initAreaShow
        });
    });


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

    function initCity(val) {
        var cityCode = '0';
        if (val != undefined && val != 'undefined')
            cityCode = val.cityCode;
        $('#qry_city').combobox({
            url: '${ctxPath}/common/area/getChild?father=' + cityCode,
            valueField: 'cityCode',
            textField: 'name',
            onSelect: function (sel) {
                //alert(sel);
            }
        });
    }

    function initProvince() {
        $('#qry_province').combobox({
            url: '${ctxPath}/common/area/getProvinces.json',
            valueField: 'cityCode',
            textField: 'name',
            onSelect: function (sel) {
                initCity(sel)
            }
        });
    }


</script>