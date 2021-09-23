/**
 * Created by dy on 2016/4/20.
 */

var QuantityUnitManage = {

    init: function() {
        QuantityUnitManage.initDatagrid();
    },




    // 添加
    doAdd: function() {
        var t = Math.random(); 	// 保证页面刷新
        var url = "/quantityUnit/quantityUnitNum/addQuantityUnit.jhtml?t="+t+'#loctop';
        var ifr = $("#addQuantityUnit").children()[0];
        $(ifr).attr("src", url);
        $("#addQuantityUnit").dialog("open");
    },

    /**
     * 功能描述：批量删除
     */
    doBatchDel: function() {

        var rows = $("#dg").datagrid("getChecked");
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        if (rows.length > 0) {

            var ids = [];

            $.each(rows, function(i, perValue) {
                ids.push(perValue.id);
            });

            var idStr = ids.join(",");

            var data = {
                idStr: idStr
            }
            var url = "/quantityUnit/quantityUnitNum/delQuantityUnitNum.jhtml";
            $.post(
                url,
                data,
                function(result) {
                    if (result.success) {
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                    }
                }
            );
        } else {
            $.messager.progress("close");
            show_msg("请选择需要删除的记录！");
        }


    },


    doSearch: function() {
        $("#dg").datagrid("reload");
    },

    clearSearch: function() {

        $("#qry_dealurUnitName").textbox("setValue", "");
        $("#qry_supplerUnitName").textbox("setValue", "");
        $("#qry_conditionNumStart").numberbox("setValue", "");
        $("#qry_conditionNumEnd").numberbox("setValue", "");

        $("#dg").datagrid("reload");
    },

    /**
     * 功能描述：初始数据表格
     */
    initDatagrid: function() {

        // 初始化表格数据
        $("#dg").datagrid({
            fit:true,
            //title:'线路列表',
            //height:400,
            url:'/quantityUnit/quantityUnitNum/datagrid.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'dealerUnitName', title: '公司名称', width: 240 },
                //{ field: 'dealerUnitIdentityCode', title: '公司串码', width: 120},
                { field: 'conditionNum', title: '拱量数量', width: 120},
                { field: 'suplerUnitName', title: '操作公司名称', width: 240},
                //{ field: 'suplerUnitIdentityCode', title: '操作公司串码', width: 120},
                { field: 'createTime', title: '创建时间', width: 120,
                    formatter: function(value, rowData, rowIndex) {
                        value = value.substring(0, 16);
                        return value;
                    }
                },
                { field: 'opt', title: '操作', width: 180, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "";
                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='QuantityUnitManage.doEdit("+rowData.id+","+rowData.conditionNum+")'>修改</a>";
                        var btnDel = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='QuantityUnitManage.doDels("+rowData.id+")'>删除</a>";
                        btn = btnEdit+"&nbsp;"+btnDel;
                        return btn;
                    }}
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                data.dealurUnitName = $("#qry_dealurUnitName").textbox("getValue");
                data.supplerUnitName = $("#qry_supplerUnitName").textbox("getValue");
                data.conditionNumStart = $("#qry_conditionNumStart").numberbox("getValue");
                data.conditionNumEnd = $("#qry_conditionNumEnd").numberbox("getValue");
            }
        });

    },

    /**
     * 功能描述：编辑拱量配置信息
     * @param id
     * @param conditionNum
     */
    doEdit: function(id, conditionNum) {

        $("#editQuantityNum").dialog({
            buttons:[
                {
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){

                        var data = {
                            'quantityUnitNum.id':id,
                            'quantityUnitNum.conditionNum':$("#ipt-quantityNum").numberbox("getValue")
                        };
                        $.messager.progress({
                            title:'温馨提示',
                            text:'数据处理中,请耐心等待...'
                        });
                        var url = "/quantityUnit/quantityUnitNum/editQuantityUnitNum.jhtml";
                        $.post(
                            url,
                            data,
                            function(result) {
                                if (result.success) {
                                    $.messager.progress("close");
                                    show_msg(result.errorMsg);
                                    $("#dg").datagrid("reload");
                                    $("#editQuantityNum").dialog("close");
                                } else {
                                    $.messager.progress("close");
                                    show_msg(result.errorMsg);
                                }
                            }
                        );


                    }
                },
                {
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $("#editQuantityNum").dialog("close");
                    }
                }
            ],
            onBeforeOpen: function() {
                $("#ipt-quantityNum").numberbox("setValue", conditionNum);
            },
            onBeforeClose: function() {
                $("#ipt-quantityNum").numberbox("setValue", "");
            }
        });

        $("#editQuantityNum").dialog("open");

    },

    /**
     * 功能描述：单个删除记录
     * @param id
     */
    doDels: function(id) {

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        var ids = [id];
        var idStr = ids.join(",");

        var data = {
            idStr: idStr
        }
        var url = "/quantityUnit/quantityUnitNum/delQuantityUnitNum.jhtml";
        $.post(
            url,
            data,
            function(result) {
                if (result.success) {
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                }
            }
        );
    }

};

$(function() {

    QuantityUnitManage.init();

});
