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
                    field: 'refundPrice',
                    title: '当期退款额',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'refundCount',
                    title: '当期退款数',
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
                            btn += "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='OrderBill.doOpenCfmBillDg(" + rowIndex + ")'>账单核对</a>";
                        }
                        return btn;
                    }
                }
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                data['orderBillSummary.billNo'] = $("#search-billNo").textbox("getValue");
                data['orderBillSummary.status'] = $('#search-bill-status').combobox("getValue");
                data['orderBillSummary.billTarget'] = 'FERRY';
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
        var url = "/order/orderBill/billDetailFerry.jhtml?billSummaryId="+billSummaryId;
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
        $.post("/order/orderBill/genBillSummaryFerry.jhtml",
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
                $.post("/order/orderBill/regenBillSummaryFerry.jhtml",
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
    // 打开账单核对窗口
    doOpenCfmBillDg: function(rowIndex) {
        var rows = $('#orderBillTable').datagrid('getRows');
        var row = rows[rowIndex];
        // 加载轮渡对账统计信息
        $.messager.progress({
            title:'温馨提示',
            text:'从轮渡获取对账统计信息,请耐心等待...'
        });
        $.post("/yhyorder/yhyFerryOrder/getOrderCollect.jhtml",
            {billSummaryId : row.id},
            function(data){
                $.messager.progress("close");
                if (data && data.success) {
                    $('#lbl_saleAmount').html(data.saleAmount);  // 订单金额
                    $('#lbl_saleCount').html(data.saleCount);  // 订单数量
                    $('#lbl_returnCount').html(data.returnCount);  // 退款数量
                    $('#lbl_returnAmount').html(data.returnAmount);  // 退款金额
                    $('#lbl_poundageAmount').html(data.poundageAmount);  // 手续费
                    $('#lbl_billAmountFerry').html(data.saleAmount-data.returnAmount);  // 结算金额
                    $('#query_data_result').val('success');
                } else {
                    $('#lbl_saleAmount').html('获取数据失败');  // 订单金额
                    $('#lbl_saleCount').html('获取数据失败');  // 订单数量
                    $('#lbl_returnCount').html('获取数据失败');  // 退款数量
                    $('#lbl_returnAmount').html('获取数据失败');  // 退款金额
                    $('#lbl_poundageAmount').html('获取数据失败');  // 手续费
                    $('#lbl_billAmountFerry').html('获取数据失败');  // 结算金额
                    $('#query_data_result').val('');
                }
                $('#search-cfm-billSummaryId').val(row.id);
                $('#lbl_totalBillPrice').html(row.totalBillPrice);  // 订单金额
                $('#lbl_totalOrderCount').html(row.totalOrderCount);  // 订单数量
                $('#lbl_refundCount').html(row.refundCount);  // 退款数量
                $('#lbl_refundPrice').html(row.refundPrice);  // 退款金额
                $('#lbl_refundFee').html(row.refundFee);  // 手续费
                $('#lbl_billAmount').html(row.totalBillPrice-row.refundPrice);  // 结算金额
                $('#cfmBillDg').dialog('open');
            },
            'json'
        );
    },
    // 打开账单核对窗口
    doCloseCfmBillDg: function() {
        $('#cfmBillDg').dialog('close');
    },
    // 账单确认
    doCfmBill: function(id) {
        var billSummaryId = $('#search-cfm-billSummaryId').val();
        var queryDataResult = $('#query_data_result').val();
        var saleCount = null;  // 订单数量
        var saleAmount = null;  // 订单金额
        var returnCount = null;  // 退款数量
        var returnAmount = null;  // 退款金额
        var poundageAmount = null;  // 手续费
        if (queryDataResult == 'success') {
            saleCount = $('#lbl_saleCount').html();  // 订单数量
            saleAmount = $('#lbl_saleAmount').html();  // 订单金额
            returnCount = $('#lbl_returnCount').html();  // 退款数量
            returnAmount = $('#lbl_returnAmount').html();  // 退款金额
            poundageAmount = $('#lbl_poundageAmount').html();  // 手续费
        }
        $.messager.confirm('温馨提示', '您确认对该账单进行结算？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/order/orderBill/cfmBillSummaryFerry.jhtml",
                    {billSummaryId:billSummaryId, saleCount:saleCount,saleAmount:saleAmount,returnCount:returnCount,
                        returnAmount:returnAmount,poundageAmount:poundageAmount},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            OrderBill.doCloseCfmBillDg();
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
