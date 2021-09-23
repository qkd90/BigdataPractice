/**
 * Created by zzl on 2016/4/28.
 */

$(function () {
    initProvince();
    PlanMgr.init();
});

var PlanMgr = {
    table: $("#planDataDg"),
    searcher: $("#qryCondition"),
    init: function () {
        this.table.datagrid({
            fit: true,
            //title: "行程规划列表",
            thread: '',
            url: '/plan/planMgr/getPlanList.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            fitColumns: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: [20,30,50],
            queryParams: {
                'orderProperty': "startTime",
                'orderType': "desc"
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: 180,
                    align: 'center',
                    formatter: PlanMgr.optionFormat
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    align: 'center',
                    formatter: PlanMgr.statusFormat
                },
                {
                    field: 'name',
                    title: '行程名称',
                    width: '260',
                    align: 'center',
                    formatter: PlanMgr.nameFormat
                },
                {
                    field: 'subtitle',
                    title: '副标题',
                    width: '120',
                    align: 'center',
                },
                {
                    field: 'recReason',
                    title: '专家推荐理由',
                    width: '200',
                    align: 'center'
                },
                {
                    field: 'userName',
                    title: '规划者用户名',
                    width: '120',
                    align: 'center'
                },
                //{
                //    field: 'nickName',
                //    title: '规划者昵称',
                //    width: '120',
                //    align: 'center'
                //},
                {
                    field: 'cover',
                    title: '封面图片',
                    width: '180',
                    align: 'center',
                    formatter: PlanMgr.coverFormat
                },
                {
                    field: 'days',
                    title: '行程天数',
                    width: '60',
                    align: 'center'
                },
                {
                    field: 'cost',
                    title: '行程总花费',
                    width: '120',
                    align: 'center'
                },
                {
                    field: 'date',
                    title: '出发时间',
                    width: '140',
                    align: 'center'
                },
                {
                    field: 'citys',
                    title: '途径城市',
                    width: '200',
                    align: 'center'
                }
            ]],
            toolbar: '#toolbar'
        });
    },
    coverFormat: function(value, rowData, index) {
        if (value != undefined && value != '' && value != null) {
            return '<img src="' + BizConstants.QINIU_DOMAIN + value + '?imageView2/1/w/180/h/80/q/75' + '"/>';
        }
        return '<span style="color: red">暂无封面</span>';
    },
    nameFormat: function(value, rowData, index) {
        var a = "<a target='_blank' style='color:blue;'";
        a += "href='http://plan.lvxbang.com/lvxbang/plan/detail.jhtml?planId=";
        a += rowData.id;
        a += "'>" + value + "</a>";
        return a;
    },
    statusFormat: function(value, rowData, index) {
        if (value == 1) {
            return "<span style='color: #3e8f3e'>已上架</span>";
        } else if (value == 2) {
            return "<span style='color: #8F8F8F'>已下架</span>";
        } else if (value == 3) {
            return "<span style='color: #FF2F2F'>已删除</span>";
        } else {
            return "<span style='color: #706f6e'>未知</span>";
        }
    },
    optionFormat: function(value, rowData, index) {
        var status = rowData.status;
        var btn = "";
        var editClick = " onClick='PlanMgr.editPlan(" + rowData.id + ")'";
        var upClick = " onClick='PlanMgr.upStatus("+ rowData.id + ")'";
        var downClick = " onClick='PlanMgr.downStatus(" + rowData.id +")'";
        var delClick = " onClick='PlanMgr.delStatus(" + rowData.id + ")'";
        btn += "<div class='opt'>";
        if (status == 1) {
            btn += "<a class='ens' href='#'" + editClick + ">编辑</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>上架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ens' href='#'" + downClick + ">下架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ens' href='#'" + delClick + ">删除</a>";
        } else if (status == 2) {
            btn += "<a class='ens' href='#'" + editClick + ">编辑</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ena' href='#'" + upClick +">上架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>下架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ens' href='#'" + delClick + ">删除</a>";
        } else if (status == 3) {
            btn += "<a class='ens' href='#'" + editClick + ">编辑</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ena' href='#'" + upClick +">上架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>下架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>删除</a>";
        } else {
            btn += "<a class='ens' href='#'" + editClick + ">编辑</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ena' href='#'" + upClick +">上架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>下架</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='disa'>删除</a>";
        }
        btn += "</div>";
        return btn;
    },
    upStatus: function(id) {
        $.ajax({
            url: '/plan/planMgr/upPlan.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function(result) {
                if (result.success) {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                    PlanMgr.table.datagrid('reload');
                } else {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                }
            },
            error: function() {
                $.messager.progress('close');
                showMsgPlus("行程规划管理", "操作失败!稍候重试!", 800);
            },
            beforeSend: function() {
                $.messager.progress();
            }
        });
    },
    downStatus: function(id) {
        $.ajax({
            url: '/plan/planMgr/downPlan.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function(result) {
                if (result.success) {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                    PlanMgr.table.datagrid('reload');
                } else {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                }
            },
            error: function() {
                $.messager.progress('close');
                showMsgPlus("行程规划管理", "操作失败!稍候重试!", 800);
            },
            beforeSend: function() {
                $.messager.progress();
            }
        });
    },
    delStatus: function(id) {
        $.ajax({
            url: '/plan/planMgr/delPlan.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function(result) {
                if (result.success) {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                    PlanMgr.table.datagrid('reload');
                } else {
                    $.messager.progress('close');
                    showMsgPlus("行程规划管理", result.msg, 800);
                }
            },
            error: function() {
                $.messager.progress('close');
                showMsgPlus("行程规划管理", "操作失败!稍候重试!", 800);
            },
            beforeSend: function() {
                $.messager.progress();
            }
        });
    },
    doSearch: function () {
        var searchForm = {};
        var cityCode = $('#qry_cityCode').val();
        if(cityCode && cityCode != "") {
            searchForm['cityCode'] = cityCode
        }
        searchForm["isChina"] = $("#qry_isChina").val();
        var searchCondition = $("#qry_condition").combobox('getValue');
        var searchContent = $("#qry_content").textbox('getValue');
        searchForm[searchCondition] = searchContent;
        searchForm['orderProperty'] = $("#qry_orderProperty").combobox('getValue');
        searchForm['orderType'] = $("#qry_orderType").combobox('getValue');
        this.table.datagrid('load', searchForm);
    },
    reset: function () {
        this.searcher.form('clear');
    },
    editPlan: function(id) {
        var url = "/plan/planMgr/getPlanDetail.jhtml?id=" + id;
        $("#plan_form").form("load", url);
        $("#plan_panel").dialog({
            title:'回复客户反馈',
            modal:true,
            onClose:function(){
                $("#plan_form").form('clear');
            }
        });
        $("#plan_panel").dialog("open");
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/plan/planMgr/doEditPlan.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("行程管理", result.msg);
                    PlanMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('行程管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('行程管理', "操作失败,请重试!", 'error');
            }
        });
    }
};