<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../common/head.jsp" %>
    <title>景点列表</title>
    <script type="text/javascript">
        $(pageInit);
        function pageInit() {
            initProvince();
            $('#qry_province').combobox('select', null);
            $('#qry_city').combobox('select', null);
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
        function query() {
            $("#qryResult").datagrid("load", {});
        }
        function openUrl(url) {
            if(url.indexOf('cncn')!=-1){
                window.open(url);
            }else{
                window.parent.addTab('景点详情', url);
            }
        }

        function delRelation(scenic_id, source_id) {
            $.get("${ctxPath}/mgrer/scenicRelation/delRelation?scenic_id=" + scenic_id + "&source_id=" + source_id, function (result) {
                $('#' + source_id).hide();
                $('#a_' + source_id).hide();
            });
        }
    </script>
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
                    <td width="80" align="right">筛选:</td>
                    <td>
                        <select class="easyui-combobox" name="condition" id="qry_condition">
                            <option value="-1">全部</option>
                            <option value="0" selected="selected">已关联0个</option>
                            <option value="1">已关联1个</option>
                            <option value="2">已关联2个</option>
                            <option value="3">已关联3个</option>
                            <option value="4">已关联4个</option>
                            <option value="5">已关联5个</option>
                            <option value="6">模糊匹配</option>
                        </select>
                        <select class="easyui-combobox" name="fields" id="qry_fields">
                            <option value="0" selected="selected">不论是否完整</option>
                            <option value="1">缺失字段</option>
                            <option value="2">已完整匹配</option>
                        </select>
                        <%--<select class="easyui-combobox" name="rankingLimit" id="qry_rankingLimit">--%>
                        <%--<option value="0" selected="selected">全部排名</option>--%>
                        <%--<option value="10">排名前10</option>--%>
                        <%--<option value="50">排名前50</option>--%>
                        <%--<option value="100">排名前100</option>--%>
                        <%--<option value="200">排名前200</option>--%>
                        <%--<option value="300">排名前300</option>--%>
                        <%--</select>--%>
                    </td>
                    <td width="80" align="right">排名区间:</td>
                    <td><input class="easyui-textbox" id="qry_orderStart" name="orderStart"
                               style="width:30px;"></td>
                    <td width="10" align="center">-</td>
                    <td><input class="easyui-textbox" id="qry_orderEnd" name="orderEnd" style="width:30px;"
                            >
                    </td>
                    <td width="80" align="right">景点名称:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输入景点名称'" style="width:120px;"></td>
                    <td></td>
                    <td></td>
                    <td>
                        <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px"
                           onClick="query()">查询</a>
                    </td>
                    <td>
                        <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px"
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
    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
            title: "景点列表",
            //data:[],
            thread: '',
            url: '${ctxPath}/mgrer/scenicRelation/listRelation.json',
            border: true,
            singleSelect: true,
            striped: true,
            pagination: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            columns: [[{
                field: 'scenicIds',
                title: 'ID',
                align: "center",
                width: 60
            }, {
                field: 'scenicName',
                title: '名称',
                sortable: true,
                align: "center",
                formatter: nameFormatter,
                width: 133
            }, {
                field: 'ctripName',
                title: '携程',
                sortable: true,
                align: "center",
                formatter: ctripFormatter,
                width: 133
            }, {
                field: 'lyName',
                title: '同程',
                sortable: true,
                align: "center",
                formatter: lyFormatter,
                width: 133
            }, {
                field: 'tuniuName',
                title: '途牛',
                sortable: true,
                align: "center",
                formatter: tuniuFormatter,
                width: 133
            }, {
                field: 'cncnName',
                title: '欣欣',
                sortable: true,
                align: "center",
                formatter: cncnFormatter,
                width: 133
            }, {
                field: 'mfwName',
                title: '马蜂窝',
                sortable: true,
                align: "center",
                formatter: mfwFormatter,
                width: 133
            }, {
                field: "OPT",
                title: "操作",
                align: "left",
                width: 120,
                formatter: optFormatter
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
                var condition = parseInt($('#qry_condition').combobox('getValue'));

                if (condition > 0) {
                    if (condition > 5) {
                        data.solred = 1;
                    }
                    else {
                        data.relatied = condition;
                    }
                }
                var fields = parseInt($('#qry_fields').combobox('getValue'));
                data.fields = fields;
//                var rankingLimit = parseInt($('#qry_rankingLimit').combobox('getValue'));
//                data.rankingLimit = rankingLimit;
                data.orderStart = $("#qry_orderStart").textbox("getValue");
                data.orderEnd = $("#qry_orderEnd").textbox("getValue");
            }
            , onLoadSuccess: initTips
        });
    });
    // 修改
    function edit(id, name) {
        window.parent.addTab( '编辑景点', "${ctxPath}/mgrer/scenic/edit?id=" + id);
    }
    function nameFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.scenicIds + ",\"" + rowData.scenicName + "\")'>"+rowData.scenicName+"</a>";
        return btnEdit;
    }
    function optFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a href='#' style='color:blue;text-decoration: underline;'" +
                " id='relation_" + rowData.scenicIds + "' class='easyui-tooltip1' data-p1='" + rowIndex + "'>关联</a>";
        return btnEdit;
    }
    function ctripFormatter(value, rowData, rowIndex) {
        if (value == undefined || value == '') {
            return '';
        }
        var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" + (rowData.ctripUrl != undefined ? rowData.ctripUrl.trim() : "") + "\")' " +
                "link='" + rowData.ctripUrl + "' style='color:black;text-decoration: none;'" +
                " id='" + rowData.ctripId + "'  class='easyui-tooltip2' data-p2='" + rowIndex + "'>" + value + "</a>";

        btnEdit = "<a id='a_" + rowData.ctripId + "'  onclick='delRelation(" + rowData.scenicIds + "," + rowData.ctripId + ")' style='color:gray'>X</a>&nbsp;&nbsp;" + btnEdit;
        if (value != rowData.scenicName) {
            return "<B><U>" + btnEdit + "</U></B>";
        }
        return btnEdit;
    }
    function cncnFormatter(value, rowData, rowIndex) {
        if (value == undefined || value == '') {
            return '';
        }
        var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" + rowData.cncnUrl + "\")' " +
                "link='" + rowData.cncnUrl + "' style='color:black;text-decoration: none;'" +
                " id='" + rowData.cncnId + "'  class='easyui-tooltip2' data-p2='" + rowIndex + "'>" + value + "</a>";

        btnEdit = "<a id='a_" + rowData.cncnId + "' onclick='delRelation(" + rowData.scenicIds + "," + rowData.cncnId + ")' style='color:gray'>X</a>&nbsp;&nbsp;" + btnEdit;
        if (value != rowData.scenicName) {
            return "<B><U>" + btnEdit + "</U></B>";
        }
        return btnEdit;
    }
    function lyFormatter(value, rowData, rowIndex) {
        if (value == undefined || value == '') {
            return '';
        }
        var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" + rowData.lyUrl + "\")' " +
                "link='" + rowData.lyUrl + "' style='color:black;text-decoration: none;'" +
                " id='" + rowData.lyId + "'  class='easyui-tooltip2' data-p2='" + rowIndex + "'>" + value + "</a>";

        btnEdit = "<a id='a_" + rowData.lyId + "'  onclick='delRelation(" + rowData.scenicIds + "," + rowData.lyId + ")' style='color:gray'>X</a>&nbsp;&nbsp;" + btnEdit;
        if (value != rowData.scenicName) {
            return "<B><U>" + btnEdit + "</U></B>";
        }
        return btnEdit;
    }
    function tuniuFormatter(value, rowData, rowIndex) {
        if (value == undefined || value == '') {
            return '';
        }
        var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" + rowData.tuniuUrl + "\")' " +
                "link='" + rowData.tuniuUrl + "' style='color:black;text-decoration: none;'" +
                " id='" + rowData.tuniuId + "'  class='easyui-tooltip2' data-p2='" + rowIndex + "'>" + value + "</a>";

        btnEdit = "<a id='a_" + rowData.tuniuId + "'  onclick='delRelation(" + rowData.scenicIds + "," + rowData.tuniuId + ")' style='color:gray'>X</a>&nbsp;&nbsp;" + btnEdit;
        if (value != rowData.scenicName) {
            return "<B><U>" + btnEdit + "</U></B>";
        }
        return btnEdit;
    }
    function mfwFormatter(value, rowData, rowIndex) {
        if (value == undefined || value == '') {
            return '';
        }
        var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" + rowData.mfwUrl + "\")' " +
                "link='" + rowData.mfwUrl + "' style='color:black;text-decoration: none;'" +
                " id='" + rowData.mfwId + "'  class='easyui-tooltip2' data-p2='" + rowIndex + "'>" + value + "</a>";

        btnEdit = "<a id='a_" + rowData.mfwId + "'  onclick='delRelation(" + rowData.scenicIds + "," + rowData.mfwId + ")' style='color:gray'>X</a>&nbsp;&nbsp;" + btnEdit;
        if (value != rowData.scenicName) {
            return "<B><U>" + btnEdit + "</U></B>";
        }
        return btnEdit;
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
                        title: row.scenicName,
                        closable: true,
                        width: 500,
                        height: 'auto',
                        border: false,
                        href: '${ctxPath}/mgrer/scenicRelation/getSimilar?sid=' + row.scenicIds + '&sname=' + row.scenicName,
                        onLoad: function () {
                            var row = $('#qryResult').datagrid('getRows')[index];
                            $('#info_' + row.ctripId).css('color', 'green');
                            $('#info_' + row.cncnId).css('color', 'green');
                            $('#info_' + row.tuniuId).css('color', 'green');
                            $('#info_' + row.mfwId).css('color', 'green');
                            $('#info_' + row.lyId).css('color', 'green');

                            $('#ck_' + row.ctripId).attr('checked', true);
                            $('#ck_' + row.cncnId).attr('checked', true);
                            $('#ck_' + row.tuniuId).attr('checked', true);
                            $('#ck_' + row.mfwId).attr('checked', true);
                            $('#ck_' + row.lyId).attr('checked', true);

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
                    })
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

