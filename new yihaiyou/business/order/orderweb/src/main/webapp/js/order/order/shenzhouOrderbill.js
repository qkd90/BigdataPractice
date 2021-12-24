/**
 * Created by zzl on 2016/10/31.
 */
$(function() {
    OrderBill.initOrderBillDate();
    OrderBill.initOrderDetailComp();
});
var OrderBill = {
    initOrderBillDate: function() {
        $('#orderBillTable').datagrid({
            url: '/order/orderBill/summaryList.jhtml',
            fit: true,
            pagination: true,
            pageList: [10, 30, 60],
            rownumbers: false,
            singleSelect:true,
            fitColumns:true,
            striped:true,//斑马线
            columns: [[
                {
                    field: 'billNo',
                    title: '对账单号',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'billSummaryDate',
                    title: '账单日期',
                    width: 120,
                    align: 'center',
                    datePattern: 'yyyy-MM-dd',
                    formatter: BgmgrUtil.dateTimeFmt
                },
                {
                    field: 'billType',
                    title: '结算方式',
                    width: 80,
                    align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        return BgmgrUtil.settlementFmt(value, rowData.billDays);
                    }
                },
                {
                    field: 'totalBillPrice',
                    title: '当期结算额',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'totalOrderCount',
                    title: '当期订单数',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '结算状态',
                    width: 100,
                    align: 'center',
                    formatter: OrderBill.statusFormatter
                },
                //{
                //    field: 'confirmStatus',
                //    title: '确认状态',
                //    width: 100,
                //    align: 'center',
                //    formatter: OrderBill.confirmStatusFormatter
                //},
                {
                    field: 'createTime',
                    title: '生成时间',
                    width: 150,
                    align: 'center',
                    datePattern: 'yyyy-MM-dd HH:mm:ss',
                    formatter: BgmgrUtil.dateTimeFmt
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    width: 150,
                    align: 'center',
                    datePattern: 'yyyy-MM-dd HH:mm:ss',
                    formatter: BgmgrUtil.dateTimeFmt
                },
                { field: 'opt', title: '操作', width: 200, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='OrderBill.doOpenOrderDetailDg(" + rowData.id + ",\"" + rowData.billNo + "\")'>账单明细</a>";
                        if (rowData.status === 0) {
                            btn += "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='OrderBill.doRegenBill(" + rowData.id + ")'>重新生成</a>";
                            btn += "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='OrderBill.doCfmBill(" + rowData.id + ")'>账单结算</a>";
                        }
                        return btn;
                    }
                }
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                data['orderBillSummary.billNo'] = $("#search-billNo").textbox("getValue");
                data['orderBillSummary.status'] = $('#search-bill-status').combobox("getValue");
                data['orderBillSummary.billTarget'] = 'SHENZHOU';
                //data['orderBillSummary.confirmStatus'] = $('#search-confirm-status').combobox("getValue");
                data['billSummaryDateStr'] = $('#search-billSummaryDate').datebox("getValue");
            }
        });
        // 限制日期控件起始时间
        $('#search-billSummaryDate').datebox().datebox('calendar').calendar({
            validator: function(date){
                var now = new Date();
                var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                return date <= d1;
            }
        });
        $('#frm_billSummaryDate').datebox().datebox('calendar').calendar({
            validator: function(date){
                var now = new Date();
                var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                return date <= d1;
            }
        });
    },
    // 初始化订单详情列表组件
    initOrderDetailComp : function() {

    },
    // 打开账单明细窗口
    doOpenOrderDetailDg: function(billSummaryId, billNo) {
        var url = "/order/orderBill/billDetailShenzhou.jhtml?billSummaryId="+billSummaryId;
        var ifr = $("#orderDetailDg").children()[0];
        $(ifr).attr("src", url);
        $("#orderDetailDg").dialog("open");
    },
    // 关闭账单明细窗口
    closeEditPanel: function() {
        var ifr = $("#orderDetailDg").children()[0];
        $(ifr).attr("src", '');
        $("#orderDetailDg").dialog("close");
    },
    statusFormatter: function(value, rowData, rowIndex) {
        if (value === 0) {
            return "未结算";
        } else if (value === 1) {
            return "已结算";
        } else if (value === 2) {
            return "部分结算";
        }
    },
    confirmStatusFormatter: function(value, rowData, rowIndex) {
        if (value === 0) {
            return "未确认";
        } else if (value === 1) {
            return "已确认";
        } else if (value === -1) {
            return "等待商家确认";
        } else if (value == -2) {
            return "等待平台确认";
        }
    },
    doSearch: function() {
        $('#orderBillTable').datagrid('load', {});
    },
    // 打开生成账单操作窗口
    doOpenGenBillDg: function() {
        OrderBill.resetForm();
        $('#genBillDg').dialog('open');
    },
    // 重置表单数据
    resetForm: function() {
        $('#frm_billSummaryDate').datebox('setValue', '');
    },
    // 生成账单操作
    doGenBill: function() {
        var validate = $('#genBillForm').form('validate');
        if (!validate) {
            return;
        }
        var billSummaryDate = $('#frm_billSummaryDate').datebox('getValue');
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/order/orderBill/genBillSummaryShenzhou.jhtml",
            {billSummaryDateStr: billSummaryDate},
            function(data) {
                $.messager.progress("close");
                if (data && data.success) {
                    $('#genBillDg').dialog('close');
                    OrderBill.doSearch();
                } else {
                    if (data && data.errorMsg) {
                        show_msg(data.errorMsg);
                    } else {
                        show_msg("操作失败");
                    }
                }
            },
            'json'
        );
    },
    // 重新生成
    doRegenBill: function(id) {
        $.messager.confirm('温馨提示', '确认重新生成？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/order/orderBill/regenBillSummaryShenzhou.jhtml",
                    {billSummaryId : id},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            OrderBill.doSearch();
                        } else {
                            if (data && data.errorMsg) {
                                show_msg(data.errorMsg);
                            } else {
                                show_msg("操作失败");
                            }
                        }
                    },
                    'json'
                );
            }
        });
    },
    // 账单确认
    doCfmBill: function(id) {
        $.messager.confirm('温馨提示', '您确认对该账单进行结算？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/order/orderBill/cfmBillSummaryShenzhou.jhtml",
                    {billSummaryId : id},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            OrderBill.doSearch();
                        } else {
                            if (data && data.errorMsg) {
                                show_msg(data.errorMsg);
                            } else {
                                show_msg("操作失败");
                            }
                        }
                    },
                    'json'
                );
            }
        });
    }
};