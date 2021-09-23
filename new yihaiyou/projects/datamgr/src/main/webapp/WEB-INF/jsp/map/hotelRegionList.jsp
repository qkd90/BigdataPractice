<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
    <title>酒店区域列表</title>
    <style type="text/css">
        .pd5{padding: 5px;}
    </style>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <%--<div title="查询条件" data-options="region:'north',border:true" style="height:80px">--%>
        <div id="toolbar" style="padding:10px 0 5px 10px">
            <table id="qryCondition">
                <tr>
                    <td width="40" align="right">城市:</td>
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

                    <td width="40" align="right">名称:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输入经验区域的名称'" style="width:200px;">
                    </td>
                    <td class="pd5">
                        <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:60px"
                           onClick="query()">查询</a>
                    </td>
                    <td class="pd5">
                        <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:60px"
                           onClick="reset()">重置</a>
                    </td>
                    <td class="pd5">
                        <a id="recomBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:60px"
                           onClick="reCompute()">重算</a>
                    </td>
                    <td class="pd5">
                        <a href="#" class="easyui-linkbutton" id="addButton" style="width: 60px;"
                           onclick="add()">新增</a>
                    </td>
                </tr>
            </table>
        </div>
    <%--</div>--%>
    <div data-options="region:'center',border:false">
        <div id="cityCodes"></div>
        <table id="qryResult"></table>
    </div>

</div>

<div id="scenicsWindow" class="easyui-window" title="酒店关联" style="width:770px;height: auto"
     data-options="closed:true,modal:true,cache:false,shadow:false">
    <form id="relateForm" method="post" enctype="multipart/form-data">
        <span id="scenicsSpan"></span>
        <span id="relateFather" style="display: none">
            父景点：
            <input id="fatherId" name="fatherId" value="" type="input"/>
            <%--<input id="childIds" name="childIds" value="" style="display: none" />--%>
            <input id="fatherSubmit" type="button" value="提交"/>
        </span>
    </form>
    <input type="button" id="showRelate" value="关联父景点">
</div>
</body>
</html>
<script type="text/javascript">

    function reCompute() {
        var cityCode = $('#qry_cityCode').val();
        if (cityCode == '') {
            show_msg("请选择要重算的城市");
        } else {
            $.messager.confirm('重算确认', '重算需要花费较长时间, 确认继续?', function(r) {
                if (r) {
                    $.messager.progress({
                        title: '区域重算',
                        msg: '正在重算, 请耐心等待...',
                        text: ''
                    });
                    cityCode = cityCode.length < 6 ? cityCode + "00" : cityCode;
                    $.ajax({
                        url: '/region/hotelRegion/recompute.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            cityCode: cityCode
                        },
                        success: function(result) {
                            if (result.success) {
                                show_msg("重算成功！");
                            } else {
                                showMsgPlus('提示', result.msg, 3000);
                            }
                            $.messager.progress('close');
                        },
                        error: function() {
                            $.messager.alert('提示', '重算失败, 稍后重试', 'error');
                            $.messager.progress('close');
                        }
                    });
                }
            });

        }
    }
    function listHotels(areaid) {
//        $('#scenicsWindow').window('close');
        $('#scenicsWindow').window('refresh');
        $('#scenicsWindow').window('open');
        $('#scenicsSpan').html('');
        $('#relateFather').hide();
        $('#showRelate').show();
        $.get("/region/hotelRegion/listHotels.jhtml?areaid=" + areaid, function (result) {
            var names = '';
            var sum = 0;
            $.each(result.rows, function (i, n) {
                names += '<input style="display:none" name="childIds" type="checkbox" value="' + n.hotel.id + '">' +
                        "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editHotel(" + n.hotel.id + ")'>" + n.hotelName + "</a>&nbsp;";
                if (sum++ > 6) {
                    names += '</br>';
                    sum = 1;
                }
            });

            $('#scenicsSpan').html(names);
            $('#showRelate').click(function () {
                $('#relateFather').show();
                $('#showRelate').hide();
                $('input[name="childIds"]').show();
            });
            $('#scenicsWindow').window('refresh');
            $('#fatherSubmit').click(function () {
                $('#relateForm').form('submit', {
                    url: '/region/hotelRegion/saveRes.jhtml',
                    dataType: 'json',
                    success: function (data) {
                        BgmgrUtil.backCall(data, function () {
                            var arr = data.split('"rows":["');
                            if (arr.length > 1) {
                                var names = arr[1].replace('"', '').replace(']}', '');
                                $.messager.alert('提示', '[' + names + ']关联成功', 'info', null, null);
                            } else {
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

            });
        });
    }
    $(pageInit);
    function pageInit() {
        initProvince();
//        $('#qry_province').combobox('select', null);
//        $('#qry_city').combobox('select', null);
    }

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
        // 清空城市查询条件
        $('#qryCity').textbox('setValue', '');
        $('#qryCity').attr('data-country', '');
        $('#qryCity').attr('data-province', '');
        $('#qryCity').attr('data-city', '');
        $('#qry_cityCode').val(null);
        $("#qryResult").datagrid("load", {});
    }

    // 新增
    function add() {
        window.parent.addTab('新增区域', "/region/hotelRegion/add.jhtml");
    }

    // 修改
    function edit(id) {
        window.parent.addTab('编辑区域', "/region/hotelRegion/edit.jhtml?id=" + id);
    }

    //
    function editHotel(id) {
        window.parent.addTab('编辑酒店', "/hotel/hotel/editWizard.jhtml?productId=" + id);
    }

    // 修改
    function del(id, name) {
        var sure = confirm("确定删除[" + name + "]吗？（将不可恢复）");
        if (sure) {
            $.get("/region/hotelRegion/del.jhtml?id=" + id);
            $("#qryResult").datagrid("load", {});
        }
    }


    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
//            title: "酒店商圈列表",
            //data:[],
            thread: '',
            url: '/region/hotelRegion/list.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
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
                width: 100,
                align: 'center'
            }, {
                field: "OPT",
                title: "操作",
                align: "left",
                width: 220,
                formatter: optFormatter
            },{
                field: 'name',
                title: '名称',
                sortable: true,
                width: 320
            }, {
                field: 'city.fullPath',
                title: '城市',
                width: 150
            }, {
                field: 'pointStr',
                title: '坐标',
                sortable: true,
                width: 800
            }, {
                field: 'priority',
                title: '优先级',
                sortable: true,
                width: 100
            }
            ]],
            toolbar: '#toolbar',
            onBeforeLoad: function (data) {   // 查询参数
                data.name = $("#qry_name").textbox("getValue");
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
            },
//            onLoadSuccess: init
        });
    });

    //    function init() {
    //        initAreaShow();
    //    }
    function optFormatter(value, rowData) {
        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.id + ")'>编辑</a>";
//        btnEdit += "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='listHotels(" + rowData.id + ",\"" + rowData.name + "\")'>酒店</a>";
        btnEdit += "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='del(" + rowData.id + ",\"" + rowData.name + "\")'>删除</a>";
        return btnEdit;
    }
</script>