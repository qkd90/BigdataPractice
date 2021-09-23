/**
 * Created by dy on 2016/8/23.
 */
/**
 * Created by dy on 2016/8/23.
 */
var TrafficManage = {

    init: function() {
        TrafficManage.initDatagrid();
    },



    initDatagrid: function() {
        $("#show_dg").datagrid({
            fit:true,
            url:'/traffic/ferry/list.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            fitColumns:true,
            columns: [[
                //{ field: 'ck', checkbox: true },
                { field: 'trafficCode', title: '航班号', width: 80},
                //{ field: 'trafficModel', title: '车型/机型', width: 120},
                { field: 'status', title: '状态', width: 60,
                    formatter : function(value, rowData, rowIndex) {
                        if (value == "UP") {
                            return "上架";
                        } else if (value == "DOWN") {
                            return "下架";
                        }
                    }
                },
                //{ field: 'theme', title: '酒店主题', width: 100},
                { field: 'leaveTransportation.name', title: '出发码头', width: 120},
                { field: 'arriveTransportation.name', title: '到达码头', width: 120},
                { field: 'leaveTime', title: '开航时间', width: 60},
                { field: 'price', title: '票价', width: 60},
                { field: 'ferryInventory', title: '余票', width: 80},
                { field: 'company', title: '公司', width: 100},
                { field: 'source', title: '来源', width: 100,
                    formatter : function(value, rowData, rowIndex) {
                        if (value == "JUHE") {
                            return "聚合网";
                        } else if (value == "ELONG") {
                            return "艺龙网";
                        } else if (value == "QUNAR") {
                            return "去哪儿网";
                        } else if (value == "CTRIP") {
                            return "携程";
                        } else {
                            return "旅行帮";
                        }
                    }
                },
            ]],
            toolbar: '#show_tb',
            onBeforeLoad: function(data) {
                data["leaveDate"] = $("#leaveTime").datebox("getValue");
            }
        });

    },
    doSearch: function() {
        var data = {
            'traffic.trafficCode': $("#trafficCode").textbox("getValue"),
            'traffic.leaveTransportation.id': $("#leavePort").combobox("getValue"),
            'traffic.arriveTransportation.id': $("#arrivePort").combobox("getValue"),
            'traffic.source': $("#show_qry_source").combobox("getValue"),
            'leaveDate': $("#leaveTime").datebox("getValue")
        }
        $("#show_dg").datagrid("load", data);
    },
    reset: function() {
        $("#show_dg").datagrid("load", {});
        $("#trafficCode").textbox("setValue", "");
        $("#leavePort").combobox("setValue", "");
        $("#arrivePort").combobox("setValue", "");
        $("#show_qry_source").combobox("setValue", "");
    },

    transportLoader: function(param, success, error) {
        var q = param.q || '';
        if (q.length <= 1) {
            return false
        }
        $.ajax({
            url : '/transportation/transportation/list.jhtml',
            dataType : 'json',
            //contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type : 'POST',
            data : {
                featureClass : "P",
                style : "full",
                pageSize : 20,
                'transportation.searchName' : q
            },
            success : function(data) {
                var items = $.map(data.rows, function(item) {
                    return {
                        id:item.id,
                        name:item.searchName
                    };
                });
                console.log(items);
                success(items);
            },
            error : function() {
                error.apply(this, arguments);
            }
        });
    },
    addTraffic: function() {

        var url = "/traffic/ferry/addWizard.jhtml";
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");

    },
    openEditFrame: function() {
        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 1) {
            show_msg("只能选择一条记录，请重新选择！");
        } else if (rows.length <= 0) {
            show_msg("请选择一条记录！")
        } else {
            TrafficManage.editTraffic(rows[0].id);
        }
    },

    editTraffic: function(productId) {
        var url = "/traffic/ferry/editWizard.jhtml?productId="+productId;
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    },
    delTraffic: function() {
        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 0) {
            var url = "/traffic/ferry/delTraffic.jhtml";
            $.each(rows, function(i, perValue) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post(
                    url,
                    {productId:perValue.id},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success) {
                            $("#show_dg").datagrid("load", {});
                            show_msg("删除成功！")
                        }
                    }
                );

            });

        } else if (rows.length <= 0) {
            show_msg("请至少选择一条记录！");
        }

    },
    upTraffic: function() {

        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 0) {
            var url = "/traffic/ferry/upOrDownTraffic.jhtml";
            $.each(rows, function(i, perValue) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                if (perValue.status != "UP") {
                    $.post(
                        url,
                        {
                            'traffic.id':perValue.id,
                            'traffic.status':"UP"
                        },
                        function(result) {
                            $.messager.progress("close");
                            if (result.success) {
                                $("#show_dg").datagrid("load", {});
                                show_msg("上架成功！")
                            }
                        }
                    );
                } else {
                    $.messager.progress("close");
                }
            });

        } else if (rows.length <= 0) {
            show_msg("请至少选择一条记录！");
        }

    },
    downTraffic: function() {

        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 0) {
            var url = "/traffic/ferry/upOrDownTraffic.jhtml";
            $.each(rows, function(i, perValue) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                if (perValue.status != "DOWN") {
                    $.post(
                        url,
                        {
                            'traffic.id':perValue.id,
                            'traffic.status':"DOWN"
                        },
                        function(result) {
                            $.messager.progress("close");
                            if (result.success) {
                                $("#show_dg").datagrid("load", {});
                                show_msg("下架成功！")
                            }
                        }
                    );
                } else {
                    $.messager.progress("close");
                }
            });

        } else if (rows.length <= 0) {
            show_msg("请至少选择一条记录！");
        }

    },

    // 关闭编辑窗口
    closeEditPanel: function(isRefresh) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", '');
        $("#editPanel").dialog("close");
        if (isRefresh ) {
            TrafficManage.doSearch();
        }
    }

}

$(function() {
    TrafficManage.init();
});