/**
 * Created by dy on 2016/8/23.
 */
var TransportTationManage = {

    init: function() {
        TransportTationManage.initDatagrid();
    },

    initDatagrid: function() {
        $("#show_dg").datagrid({
            fit:true,
            url:'/transportation/transportation/list.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            fitColumns:true,
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'name', title: '名称', width: 200},
                { field: 'cityName', title: '城市', width: 100},
                { field: 'type', title: '类型', width: 80, align:'center',
                    formatter : function(value, rowData, rowIndex) {
                        //1、火车站，2、机场，3、汽车站
                        if (value == 1) {
                            return "火车站";
                        } else if (value == 2) {
                            return "机场";
                        } else if (value == 3) {
                            return "汽车站";
                        } else if (value == 4) {
                            return "码头";
                        }
                    }
                },
                //{ field: 'theme', title: '酒店主题', width: 100},
                { field: 'address', title: '地址', width: 160},
                { field: 'status', title: '交通枢纽的状态', width: 160,
                //-1代表失效/无用/重复,0代表可用但旅行帮无交通接口,1-可用且有交通接口
                    formatter : function(value, rowData, rowIndex) {
                        if (value == -1) {
                            return "失效/无用/重复";
                        } else if (value == 0) {
                            return "可用但旅行帮无交通接口";
                        } else if (value == 1) {
                            return "可用且有交通接口";
                        }
                    }
                },
            ]],
            toolbar: '#show_tb'
        });

    },
    doSearch: function() {
        var data = {
            'transportation.name': $("#transportName").textbox("getValue"),
            'transportation.cityName' : $("#transportCityName").textbox("getValue"),
            'transportation.type': $("#transportType").combobox("getValue")
        }
        $("#show_dg").datagrid("load", data);
    },
    reset: function() {
        $("#show_dg").datagrid("load", {});
        $("#searchform").form("reset");
    },
    delTransport: function() {
        var url = "/transportation/transportation/delTransport.jhtml";
        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 0) {
            $.each(rows, function(i,perValue) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post(
                    url,
                    {'transportation.id': perValue.id},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success) {
                            $("#show_dg").datagrid("load", {});
                            show_msg("删除成功！");
                        } else {
                            show_msg("操作失败！");
                        }
                    }
                );
            });
        } else if (rows.length <= 0) {
            show_msg("请至少选择一条记录！");
        }

    },
    addTransport: function() {
        var url = "/transportation/transportation/editTransport.jhtml";
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'新增交通站点'
        });
        $("#editPanel").dialog("open");

    },
    editTransport: function(portId) {
        var rows = $("#show_dg").datagrid("getSelections");
        if (rows.length > 1) {
            show_msg("只能选择一条记录，请重新选择！");
        } else if (rows.length <= 0) {
            show_msg("请选择一条记录！")
        } else {
            TransportTationManage.onpenEdit(rows[0].id);
        }

    },
    onpenEdit: function(portId) {
        var url = "/transportation/transportation/editTransport.jhtml?transportation.id="+portId;
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'编辑交通站点'
        });
        $("#editPanel").dialog("open");
    }

}

$(function() {
    TransportTationManage.init();
});