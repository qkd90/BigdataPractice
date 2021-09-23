/**
 * Created by zzl on 2016/6/15.
 */

$(function() {
    initProvince();
    CustomReqMgr.init();
});

var CustomReqMgr = {
    table: $("#customReqDg"),
    searcher: $("#customReq-searcher"),
    init: function() {
        this.table.datagrid({
            fit:true,
            url:'/lxbcommon/customReq/getCustomReqList.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc"
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 55,
                    align: 'center'
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: 140,
                    align: 'center',
                    formatter: CustomReqMgr.optFormat
                },
                {
                    field: 'status',
                    title: '状态',
                    width: '70',
                    align: 'center',
                    formatter: CustomReqMgr.statusFormat
                },
                {
                    field: 'remark',
                    title: '备注',
                    width: 200,
                    align: 'center'
                },
                {
                    field: 'customType',
                    title: '定制类型',
                    width: 70,
                    align: 'center',
                    formatter: CustomReqMgr.customTypeFormat
                },
                {
                    field: 'startCity.fullPath',
                    title: '出发地点',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'customRequireDestinations',
                    title: '目的地',
                    width: '150',
                    align: 'center',
                    formatter: CustomReqMgr.destinationFormat
                },
                {
                    field: 'startDate',
                    title: '出发日期',
                    width: '100',
                    align: 'center'
                },
                {
                    field: 'day',
                    title: '天数',
                    width: 60,
                    align: 'center'
                },
                {
                    field: 'arrange',
                    title: '行程安排',
                    width: '70',
                    align: 'center',
                    formatter: CustomReqMgr.arrangeFormat
                },
                {
                    field: 'adult',
                    title: '数量(成)',
                    width: '70',
                    align: 'center'
                },
                {
                    field: 'child',
                    title: '数量(童)',
                    width: '70',
                    align: 'center'
                },
                {
                    field: 'contactor',
                    title: '联系人',
                    width: '100',
                    align: 'center'
                },
                {
                    field: 'contactPhone',
                    title: '联系电话',
                    width: '120',
                    align: 'center'
                },
                {
                    field: 'contactEmail',
                    title: '联系邮箱',
                    width: '120',
                    align: 'center'
                },
                {
                    field: 'member.userName',
                    title: '提交者',
                    width: '120',
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: '140',
                    align: 'center'
                },
                {
                    field: 'handler.userName',
                    title: '处理人',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'handleTime',
                    title: '处理时间',
                    width: 140,
                    align: 'center'
                }
            ]],
            toolbar: this.searcher
        });
    },
    optFormat: function(value, rowData, rowIndex) {
        var btn = "";
        var remarkClick = " onClick='CustomReqMgr.modifyRemark(" + rowData.id + ")'";
        var handleClick = " onClick='CustomReqMgr.handleReq(" + rowData.id + ")'"
        var acceptClick = " onClick='CustomReqMgr.acceptReq(" + rowData.id + ")'";
        var refuseClick = " onClick='CustomReqMgr.refuseReq(" + rowData.id + ")'";
        var cancelClick = " onClick='CustomReqMgr.cancelReq(" + rowData.id + ")'";
        btn += "<div class='opt' '>";
        btn += "<a class='ena' href='#'" + remarkClick + ">备注</a>";
        if (rowData.status == "handling") {
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ena' href='#'" + cancelClick + ">取消</a>";
            btn += "&nbsp;&nbsp;";
            btn += "<a class='ena' href='#'" + handleClick + ">已处理</a>";
        } else if (rowData.status == "handled") {
            //...
        } else if (rowData.status == "cancel") {
            //...
        }
        btn += "</div>";
        return btn;
    },
    customTypeFormat: function(value, rowData, rowIndex) {
        if (value == "company") {
            return "公司";
        } else if (value == "home") {
            return "家庭";
        } else if (value == "other") {
            return "其他";
        } else {
            return "未知";
        }
    },
    arrangeFormat: function(value, rowData, rowIndex) {
        if (value == "compact") {
            return "紧凑";
        } else if (value == "moderate") {
            return "适中";
        } else if (value == "loose") {
            return "宽松";
        } else if (value == "unsure") {
            return "不确定";
        } else {
            return "未知";
        }
    },
    statusFormat: function(value, rowData, rowIndex) {
        if (value == "handling") {
            return "<span style='color: #fd971f'>处理中</span>";
        } else if (value == "handled") {
            return "<span style='color: #3e8f3e'>已处理</span>";
        } else if (value == "cancel") {
            return "<span style='color: #706f6e'>已取消</span>";
        } else {
            return "<span style='color: #9fa8b4'>未知</span>";
        }
    },
    destinationFormat: function(value, rowData, rowIndex) {
        if (value != null && value != "") {
            var des = "";
            $.each(value, function(i, reqDes) {
                des += reqDes.city.name + "/";
            });
            des = des.substring(0, des.length - 1);
            return des;
        } else {
            return "-"
        }
    },
    modifyRemark: function(id) {
        var url = "/lxbcommon/customReq/getCustomReqDetail.jhtml?id=" + id;
        $("#detail_form").form("load", url);
        $("#detail_panel").dialog({
            title:'私人定制管理',
            modal:true,
            onClose:function(){
                $("#detail_form").form('clear');
            }
        });
        $("#detail_panel").dialog("open");
    },
    cancelReq: function(id) {
        $.ajax({
            url: '/lxbcommon/customReq/cancelCustomReq.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                if (result.success) {
                    showMsgPlus("私人定制管理", result.msg);
                    CustomReqMgr.table.datagrid('reload');
                } else {
                    $.messager.alert('私人定制管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('私人定制管理', result.msg, 'error');
            }
        });

    },
    handleReq: function(id) {
        $.ajax({
            url: '/lxbcommon/customReq/handleCustomReq.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                if (result.success) {
                    showMsgPlus("私人定制管理", result.msg);
                    CustomReqMgr.table.datagrid('reload');
                } else {
                    $.messager.alert('私人定制管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('私人定制管理', result.msg, 'error');
            }
        });
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/customReq/modifyCustomReq.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("私人定制管理", result.msg);
                    CustomReqMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('私人定制管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('私人定制管理', "操作失败,请重试!", 'error');
            }
        });
    },
    doSearch: function() {
        var searchForm = {};
        var searchType = this.searcher.find("#search-type").val();
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['customRequire.status'] = this.searcher.find("#search-status").val();
        searchForm['customRequire.customType'] = this.searcher.find("#search-customType").val();
        searchForm['customRequire.arrange'] = this.searcher.find("#search-arrange").val();
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        var regionCode = $('#qry_cityCode').val();
        if (regionCode && regionCode!= "") {
            searchForm['customRequire.startCity.cityCode'] = regionCode;
        }
        searchForm['customRequire.isChina'] = $("#qry_isChina").val();
        //if(cityCode == null || cityCode == "") {
        //    cityCode = $("#qry_province").combobox('getValue');
        //    if (cityCode != null && cityCode != "") {
        //        searchForm['customRequire.startCity.cityCode'] = cityCode.substr(0, 2);
        //    }
        //} else {
        //    searchForm['customRequire.startCity.cityCode'] = cityCode.substr(0, 4);
        //}
        this.table.datagrid('load', searchForm);
    }

};