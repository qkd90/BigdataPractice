/**
 * Created by dy on 2016/6/2.
 */
var HotelManage = {
    init : function() {
        //HotelManage.initShow();
        HotelManage.initStorage();
        HotelManage.initTabs();
        HotelManage.initCom();
    },
    initCom: function() {
        $("#storage_qry_source").combobox({
            onSelect: function(record) {
                $("#storage_dg").datagrid("load")
            }
        });
        $("#show_qry_source").combobox({
            onSelect: function(record) {
                $("#show_dg").datagrid("load")
            }
        });
    },
    initTabs:function() {
        $('#tabs').tabs({
            onSelect: function(title, index){
                if (index == 0) {
                    $("#show_dg").datagrid("load");
                } else {
                    $("#storage_dg").datagrid("load");
                }
            }
        });
    },
    // 关闭编辑窗口
    closeEditPanel: function(isRefresh) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", '');
        $("#editPanel").dialog("close");
        if (isRefresh ) {
            HotelManage.doSearchShow();
            HotelManage.doSearchStorage();
        }
    },
    /**
     * 新增
     */
    doAdd: function() {
        var url = "/hotel/hotel/addWizard.jhtml";
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    },
    doDel: function() {
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'处理中,请耐心等待...'
                });
                var rows = $("#storage_dg").datagrid("getChecked");
                var flag = true;
                if (rows.length > 0) {
                    var ids = "";
                    $.each(rows, function(i, perValue) {
                        if (perValue.status != "UP") {
                            if (i < rows.length-1) {
                                ids += perValue.id + ",";
                            } else {
                                ids += perValue.id;
                            }
                        } else {
                            flag = false;
                            return false;
                        }
                    });
                    if (flag) {
                        if (ids.length <= 0) {
                            $.messager.progress('close');
                            return;
                        }
                        var url = "/hotel/hotel/doDel.jhtml";
                        $.ajax({
                            url: url,
                            type: 'post',
                            dataType: 'json',
                            data: {
                                idStrs: ids
                            },
                            success: function(result) {
                                if (result.success) {
                                    $("#storage_dg").datagrid("reload");
                                    show_msg("操作成功！");
                                } else {
                                    show_msg("操作失败！");
                                }
                                $.messager.progress('close');
                            },
                            error: function() {
                                $.messager.progress('close');
                                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            }
                        });
                    } else {
                        $.messager.progress('close');
                        show_msg("已发布的产品不能删除，请先下架后操作！");
                    }
                } else {
                    $.messager.progress('close');
                    show_msg("请选需要删除的的民宿！");
                }
            }
        });
    },
    doSubChecking: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var rows = $("#storage_dg").datagrid("getChecked");
        if (rows.length > 0) {
            var idArr = [];
            var ids = "";
            $.each(rows, function(i, perValue) {
                if (perValue.status == "CHECKING" || perValue.status == "DOWN") {
                    idArr.push(perValue.id);
                }

            });
            if (idArr.length <= 0) {
                $.messager.progress('close');
                show_msg("请选择已下架产品或审核未通过的产品");
                return;
            } else {
                $.ajax({
                    url: '/hotel/hotel/doSubChecking.jhtml',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        idStrs:idArr.join(',')
                    },
                    success: function(result) {
                        if (result.success) {
                            $("#storage_dg").datagrid("reload");
                            show_msg("操作成功！");
                        } else {
                            show_msg("操作失败！");
                        }
                        $.messager.progress('close');
                    },
                    error: function() {
                        $.messager.progress('close');
                        $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                    }
                });
            }

        } else {
            $.messager.progress('close');
            show_msg("请选需要提交审核的产品！");
        }
    },
    doSetHide: function(dg) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var rows = $("#" +dg+"").datagrid("getChecked");
        if (rows.length > 0) {
            var ids = "";
            $.each(rows, function(i, perValue) {
                if (i < rows.length-1) {
                    ids += perValue.id + ",";
                } else {
                    ids += perValue.id;
                }
            });
            if (ids.length <= 0) {
                $.messager.progress('close');
                return;
            }
            $.ajax({
                url: '/hotel/hotel/doSetHide.jhtml',
                type: 'post',
                dataType: 'json',
                data: {
                    idStrs:ids
                },
                success: function(result) {
                    if (result.success) {
                        $("#" +dg+"").datagrid("reload");
                        show_msg("操作成功！");
                    } else {
                        show_msg("操作失败！");
                    }
                    $.messager.progress('close');
                },
                error: function() {
                    $.messager.progress('close');
                    $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                }
            });
        } else {
            $.messager.progress('close');
            show_msg("请选需要发布的产品！");
        }
    },
    doSearchStorage: function() {
        $("#storage_dg").datagrid("load", {});
    },
    doClearStorage: function() {
        //$("#storage_qry_source").combobox("setValue", "");
        $("#storage_qry_status").combobox("setValue", "");
        $("#storage_qry_name").textbox("setValue", "");
        $("#storage_qry_star").combobox("setValue", "");
        $("#storage_qry_startTime").datetimebox("setValue", "");
        $("#storage_qry_endTime").datetimebox("setValue", "");
        var data = {
        };
        $("#storage_dg").datagrid("load", data);
    },

    viewDetail: function(id,  name) {
        var url = "/hotel/hotel/viewDetail.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editDetailPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editDetailPanel").dialog({
            title:name,
            closed: false,
            closable:true,
            shadow:true
        });
        $("#editDetailPanel").dialog("open");
    },
    initStorage: function() {
        $("#storage_dg").datagrid({
            fit:true,
            url:'/hotel/hotel/getAllHotelList.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            fitColumns: true,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'id', title:'产品编号', width: 100 },
                { field: 'name', title: '名称', width: 350/*,
                    formatter : function(value, rowData, rowIndex) {
                        return '<label style="cursor:pointer;" onclick="HotelManage.viewDetail('+ rowData.id +',\''+rowData.name+'\')">' + value + '</label>';
                    }*/
                },
                { field: 'status', title: '状态', width: 100,
                    formatter: function(value, rowData, rowIndex) {
                        if (value == "DOWN"){
                            return "<span style='color: #808080;'>已下架</span>";
                        } else if (value == "UP") {
                            return "<span style='color: green;font-weight: 500'>已上架</span>";
                        } else if (value == "DEL") {
                            return "已删除";
                        } else if (value == "CHECKING") {
                            return "待审核";
                        } else if (value == "FAIL") {
                            return "审核失败";
                        }
                    }
                },
                { field: 'extend.address', title: '城市', width: 250},
                { field: 'star', title: '星级', width: 100,
                    formatter : function(value, rowData, rowIndex) {
                        if (value == 5) {
                            return "五星级";
                        } else if (value == 4) {
                            return "四星级";
                        } else if (value == 3) {
                            return "三星级";
                        } else if (value == 2) {
                            return "二星级";
                        } else if (value == 1) {
                            return "一星级";
                        } else {
                            return "未评定";
                        }
                    }
                },
                { field: 'source', title: '来源', width: 120,
                    formatter : function(value, rowData, rowIndex) {
                        if (value == "CTRIP") {
                            return "携程网";
                        } else if (value == "QUNAR") {
                            return "去哪儿网";
                        } else if (value == "ELONG") {
                            return "艺龙网";
                        } else if (value == "JUHE") {
                            return "聚合数据";
                        } else {
                            return "本平台";
                        }
                    }
                },
                { field: 'repeatFlag', title: '是否屏蔽', width: 120,
                    formatter : function(value, rowData, rowIndex) {
                        if (value) {
                            return "已屏蔽";
                        } else {
                            return "显示";
                        }
                    }
                },
                { field: 'updateTime', title: '最后更新', width: 140},
                { field: 'opt', title: '操作', width: 120, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btnUpEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doUp("+rowData.id+")'>取消屏蔽</a>";
                        var btnDownEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doDown("+rowData.id+")'>屏蔽</a>";
                        if (rowData.repeatFlag) {
                            return btnUpEdit;
                        } else {
                            return btnDownEdit;
                        }
                    }}
            ]],
            toolbar: '#storage_tb',
            onBeforeLoad : function(data) {   // 查询参数
                data['hotel.source'] = 'ELONG';
                data['hotel.status'] = $("#storage_qry_status").combobox("getValue");
                data['hotel.name'] = $("#storage_qry_name").textbox("getValue");
                data['hotel.star'] = $("#storage_qry_star").combobox("getValue");
                data.qryStartTime = $("#storage_qry_startTime").datetimebox("getValue");
                data.qryEndTime = $("#storage_qry_endTime").datetimebox("getValue");
            }
        });
    },

    doUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.post("/hotel/hotel/doSetHide.jhtml",
            {'hotel.id' : id, 'hotel.repeatFlag': false},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    $("#storage_dg").datagrid('reload');
                }else{
                    show_msg("处理失败");
                    $("#storage_dg").datagrid('reload');
                }
            }
        );
    },
    // 下架
    doDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.post("/hotel/hotel/doSetHide.jhtml",
            {'hotel.id' : id, 'hotel.status': 'DOWN', 'hotel.repeatFlag': true},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    $("#storage_dg").datagrid('reload');
                }else{
                    show_msg("处理失败");
                    $("#storage_dg").datagrid('reload');
                }
            }
        );
    },

    doEdit: function(id) {
        var url = "/hotel/hotel/editWizard.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    }
};
$(function() {
    HotelManage.init();
});
