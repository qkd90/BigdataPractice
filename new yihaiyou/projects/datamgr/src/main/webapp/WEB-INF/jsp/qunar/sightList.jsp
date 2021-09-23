<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <title>去哪儿关联列表</title>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div title="查询条件" data-options="region:'north',border:true" style="height:80px">
        <div style="padding:10px 0 5px 10px">
            <table id="qryCondition">
                <tr>
                    <td width="80" align="right">景点所在地:</td>
                    <td><input class="easyui-textbox" id="qry_area" name="area"
                               data-options="prompt:'请输入省市区名称'" style="width:200px;"></td>
                    <td></td>
                    <td width="80" align="right">筛选:</td>
                    <td>
                        <select class="easyui-combobox" name="condition" id="qry_condition">
                            <option value="0" selected="selected">全部</option>
                            <option value="1">已关联</option>
                            <option value="2">未关联</option>
                        </select>
                    </td>


                    <td width="80" align="right">去哪儿景点:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输入景点名称'" style="width:200px;"></td>
                    <td></td>
                    <td width="80" align="right">关联景点:</td>
                    <td><input class="easyui-textbox" id="qry_sname" name="sname"
                               data-options="prompt:'请输入景点名称'" style="width:200px;"></td>
                    <td></td>
                    <td></td>
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
        <table id="qryResult"></table>
    </div>

</div>
</body>
</html>
<script type="text/javascript">
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
            //data:[],
            thread: '',
            url: '/scenic/qunarSight/list.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            columns: [[{
                field: 'id',
                title: 'ID',
                width: 100
            }, {
                field: 'name',
                title: '名称',
                sortable: true,
                width: 200,
                formatter: nameFormatter
            }, {
                field: 'scenicName',
                title: '关联景点',
                sortable: true,
                width: 200
            }, {
                field: 'scenicIds',
                title: '关联景点id',
                sortable: true,
                width: 100
            }, {
                field: 'areaNamePath',
                title: '所在地',
                width: 300
            }, {
                field: 'modifyTime',
                title: '修改时间',
                align: 'center',
                width: 170,
                formatter: BgmgrUtil.dateTimeFmt
            }, {
                field: "status",
                title: "状态",
                align: "center",
                width: 150,
                formatter: statusFormatter
            }, {
                field: "OPT",
                title: "操作",
                align: "center",
                width: 170,
                formatter: optFormatter
            }
            ]],
            onBeforeLoad: function (data) {   // 查询参数

                data.name = $("#qry_name").textbox("getValue");
                data.areaNamePath = $("#qry_area").textbox("getValue");
                var condition = $('#qry_condition').combobox('getValue');
                switch (condition) {
                    case '1':
                        data.relationed = -1;
                        break;
                    case '2':
                        data.relationed = 1;
                        break;
                }
            }, onLoadSuccess: initTips
        });
    });

    function statusFormatter(value, rowData) {
        if (rowData.scenicIds > 0) {
            return '已关联';
        }
        return '未关联';
    }

    function optFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a href='#' style='color:blue;text-decoration: underline;'" +
                " id='relation_" + rowData.id + "' class='easyui-tooltip1' data-p1='" + rowIndex + "'>关联</a>";
        return btnEdit;
    }
    function nameFormatter(value, rowData, rowIndex) {
//        var btnEdit = "<a href='#' onclick='openUrl(\"http://piao.qunar.com/ticket/list.htm?keyword="
//                + value + "&region=&from=mpl_search_suggest\")'>"+value+"</a>";
        var btnEdit = "<a href='#' onclick='openUrl(\"http://piao.qunar.com/ticket/detail_" + rowData.id + ".html\")'>" + value + "</a>";
        return btnEdit;
    }
    function openUrl(url) {
        window.parent.addTab('景点详情', url);
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
                        title: row.name + "关联",
                        closable: true,
                        width: 500,
                        height: 'auto',
                        border: false,
                        href: '/scenic/qunarSight/getSimilar.jhtml?sid=' + row.id + '&sname=' + row.name,
                        onLoad: function () {
                            var row = $('#qryResult').datagrid('getRows')[index];
                            $('#search_' + row.id).click(function () {
                                var sname = $('#searchValue_' + row.id).val();
                                cc.panel({
                                    title: row.name + "关联",
                                    closable: true,
                                    width: 500,
                                    height: 'auto',
                                    border: false,
                                    href: '/scenic/qunarSight/getSimilar.jhtml?sid=' + row.id + '&sname=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
                                        $('#search_' + row.id).click(function () {

                                        });
                                        $('#info_' + row.id + '_' + row.scenicIds).css('color', 'green');
                                        $('#ck_' + row.id + '_' + row.scenicIds).attr('checked', true);
                                    }
                                });
                            });
                            $('#info_' + row.id + '_' + row.scenicIds).css('color', 'green');
                            $('#ck_' + row.id + '_' + row.scenicIds).attr('checked', true);
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