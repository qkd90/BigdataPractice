/**
 * Created by dy on 2016/6/2.
 */

var ContractManage = {

    init : function() {
        ContractManage.initShow();
        ContractManage.initCom();
    },

    initCom: function() {
        /*$("#ipq_signTimeStart").datebox({
            onSelect: function(date) {
                var signTimeStart = $("#ipq_signTimeStart").datebox("getValue");
                var signTimeEnd = $("#ipq_signTimeEnd").datebox("getValue");
                if (signTimeEnd) {
                    var result = ContractUtil.dateStringCompare(signTimeEnd, signTimeStart);
                    if (result <= 0) {
                        $("#ipq_signTimeStart").datebox("setValue", "");
                        show_msg("签约时间始不能大于签约时间末")
                    }
                }
            }
        });
        $("#ipq_signTimeEnd").datebox({
            onSelect: function(date) {
                var signTimeStart = $("#ipq_signTimeStart").datebox("getValue");
                var signTimeEnd = $("#ipq_signTimeEnd").datebox("getValue");
                if (signTimeStart) {
                    var result = ContractUtil.dateStringCompare(signTimeEnd, signTimeStart);
                    if (result <= 0) {
                        $("#ipq_signTimeEnd").datebox("setValue", "");
                        show_msg("签约时间末不能小于签约时间始")
                    }
                }
            }
        });*/
        $("#ipq_expireTimeStart").datebox({
            onSelect: function(date) {
                var expireTimeStart = $("#ipq_expireTimeStart").datebox("getValue");
                var expireTimeEnd = $("#ipq_expireTimeEnd").datebox("getValue");
                if (expireTimeEnd) {
                    var result = ContractUtil.dateStringCompare(expireTimeEnd, expireTimeStart);
                    if (result <= 0) {
                        $("#ipq_expireTimeStart").datebox("setValue", "");
                        show_msg("到期时间始不能大于到期时间末")
                    }
                }
            }
        });
        $("#ipq_expireTimeEnd").datebox({
            onSelect: function(date) {
                var expireTimeStart = $("#ipq_expireTimeStart").datebox("getValue");
                var expireTimeEnd = $("#ipq_expireTimeEnd").datebox("getValue");
                if (expireTimeStart) {
                    var result = ContractUtil.dateStringCompare(expireTimeEnd, expireTimeStart);
                    if (result <= 0) {
                        $("#ipq_expireTimeEnd").datebox("setValue", "");
                        show_msg("到期时间末不能小于到期时间始")
                    }
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
            ContractManage.doSearchShow();
        }
    },

    /**
     * 新增
     */
    doAdd: function() {
        var url = "/contract/contract/editContract.jhtml";
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'新增合同'
        });
        $("#editPanel").dialog("open");
    },
    doEdit: function(id) {

        /*var rows = $("#show_dg").datagrid("getChecked");
        if (rows.length > 1) {
            show_msg("只能选择一条记录！");
            return ;
        }
        if (rows.length <= 0) {
            show_msg("请选择一条记录！");
            return ;
        }*/
        var url = "/contract/contract/editContract.jhtml?contract.id=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'编辑合同'
        });
        $("#editPanel").dialog("open");
    },

    doDel: function(id) {
        var ids = "";
        if (!id) {
            var rows = $("#show_dg").datagrid("getChecked");
            if (rows.length > 0) {
                $.each(rows, function(i, perValue) {
                        if (i < rows.length-1) {
                            ids += perValue.id + ",";
                        } else {
                            ids += perValue.id;
                        }
                    }
                );
                if (ids.length <= 0) {
                    return;
                }
            } else {
                show_msg("请选需要删除的合同！");
                return;
            }
        } else {
            ids += id;
        }
        var data = {
            idStrs:ids
        };
        var url = "/contract/contract/del.jhtml";
        $.post(url, data,
            function(result){
                if (result.success) {
                    $("#show_dg").datagrid("reload");
                    show_msg("操作成功！");
                } else {
                    show_msg("操作失败！");
                }
            }
        );
    },


    doSearchShow: function() {
        var data = {
            'contract.name': $("#ipq_name").textbox("getValue"),
            'contract.partyAnum': $("#ipq_number").textbox("getValue"),
            //'contract.qrySignTimeStart': $("#ipq_signTimeStart").datebox("getValue"),
            //'contract.qrySignTimeEnd': $("#ipq_signTimeEnd").datebox("getValue"),
            'contract.status': $("#ipq_status").combobox("getValue"),
            'contract.qryExpiTimeStart': $("#ipq_expireTimeStart").datebox("getValue"),
            'contract.qryExpiTimeEnd': $("#ipq_expireTimeEnd").datebox("getValue")
        };
        $("#show_dg").datagrid("load", data);
    },

    doClearShow: function() {
        $("#ipq_name").textbox("setValue", "");
        $("#ipq_number").textbox("setValue", "");
        //$("#ipq_signTimeStart").datebox("setValue", "");
        //$("#ipq_signTimeEnd").datebox("setValue", "");
        $("#ipq_status").combobox("setValue", "");
        $("#ipq_expireTimeStart").datebox("setValue", "");
        $("#ipq_expireTimeEnd").datebox("setValue", "");
        var data = {
        };
        $("#show_dg").datagrid("load", data);
    },


    initShow: function() {
        $("#show_dg").datagrid({
            fit:true,
            url:'/contract/contract/list.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'partyAnum', title: '合同编号', width: 150},
                { field: 'name', title: '合同名称', width: 160},
                //{ field: 'partyAunit.name', title: '甲方单位', width: 100},
                { field: 'partyBunit.name', title: '签约商户', width: 160},
                { field: 'settlementType', title: '结算方式', width: 80, formatter: function(value, row, index) {
                    if (value == 'tday') {
                        return "T+" + row.settlementValue;
                    } else if (value == 'month') {
                        return "每月" + row.settlementValue + "日";
                    } else if (value == 'week') {
                        var weekday = '';
                        switch(row.settlementValue) {
                            case 1:
                                weekday = '日';
                                break;
                            case 2:
                                weekday = '一';
                                break;
                            case 3:
                                weekday = '二';
                                break;
                            case 4:
                                weekday = '三';
                                break;
                            case 5:
                                weekday = '四';
                                break;
                            case 6:
                                weekday = '五';
                                break;
                            case 7:
                                weekday = '六';
                                break;
                            default:;
                        }
                        return "每周" + weekday;    // 星期日为1
                    } else {
                        return '';
                    }
                }},
                { field: 'valuationModels', title: '计价模式', width: 150, formatter: function(value, row, index) {
                    if (value == 'commissionModel') {
                        return "按" + row.valuationValue + "%佣金计价";
                    }
                    if (value == 'fixedModel') {
                        return "按固定" + row.valuationValue + "元价格模式";
                    }
                    if (value == 'lowPriceModel') {
                        return "底价模式";
                    }

                }},
                { field: 'signTime', title: '签约时间', width: 150, datePattern: "yyyy-MM-dd", formatter: ContractUtil.dateTimeFmt},
                { field: 'effectiveTime', title: '生效/结束时间', width: 200,
                    formatter: function(value, rowData) {
                        return ContractUtil.cutByLength(value, 10) + "/" + ContractUtil.cutByLength(rowData.expirationTime, 10);
                    }
                },
                { field: 'status', title: '合同状态', width: 80, codeType: 'status', formatter: ContractUtil.codeFmt},
                { field: 'opt', title: '操作', width: 100,formatter: function(value, row) {
                    var editBtn = '<a href="javascript:void(0)" style="color: #0000f1" onclick="ContractManage.doEdit('+ row.id +');" class="easyui-linkbutton" >编辑</a>';
                    var delBtn = '<a href="javascript:void(0)" style="color: #0000f1" onclick="ContractManage.doDel('+ row.id +');" class="easyui-linkbutton" >删除</a>';
                    return editBtn + '&nbsp;&nbsp;&nbsp;&nbsp;' + delBtn;
                }}
            ]],
            toolbar: '#show_tb',
            onBeforeLoad : function(data) {   // 查询参数
                data['contract.name']= $("#ipq_name").textbox("getValue");
                data['contract.partyAnum']= $("#ipq_number").textbox("getValue");
                //data['contract.qrySignTimeStart']= $("#ipq_signTimeStart").datebox("getValue");
                //data['contract.qrySignTimeEnd']= $("#ipq_signTimeEnd").datebox("getValue");
                data['contract.status']= $("#ipq_status").combobox("getValue");
                data['contract.qryExpiTimeStart']= $("#ipq_expireTimeStart").datebox("getValue");
                data['contract.qryExpiTimeEnd']= $("#ipq_expireTimeEnd").datebox("getValue");
            },
            onSelect : function(rowIndex, rowData) {
            },
            onUnselect : function(rowIndex, rowData) {
            },
            onLoadSuccess: function(data) {

            }
        });

    }



};

$(function() {
    ContractManage.init();
});
