$(function() {
    BillDetail.initComp();
    // 结算类型转换
    var billType = $('#billType').html();
    var billDays = $('#billDays').val();
    var settlementStr = BgmgrUtil.settlementFmt(billType, billDays)
    $('#billType').html(settlementStr);
    // 状态转换
    var status = parseInt($('#status').html());
    var statusStr = BillDetail.statusFormatter(status);
    $('#status').html(statusStr);
});
var BillDetail = {

    initComp: function() {
        // 当期结算账单明细
        $('#orderDetailGrid').datagrid({
            //fit:true,
            url:'/order/orderBill/orderDetailList.jhtml',
            pagination: true,
            pageList: [10, 30, 60],
            //rownumbers: false,
            title: '当期结算账单明细',
            border:true,
            striped:true,//斑马线
            singleSelect:true,
            columns:[[
                {field:'orderNo',title:'订单号',width:200, align: 'center'},
                {field:'productName',title:'产品名称',width:360},
                {field:'totalPrice',title:'订单总价',width:150},
                {field:'orderBillPrice',title:'结算价',width:150}
            ]],
            toolbar: '#orderDetailTb',
            onBeforeLoad : function(data) {
                data.billSummaryId = $("#search-billSummaryId").val();
            }
        });
        // 退款订单详情表格
        $('#refundDetailGrid').datagrid({
            //fit:true,
            url:'/order/orderBill/refundDetailList.jhtml',
            pagination: true,
            pageList: [10, 30, 60],
            //rownumbers: false,
            title: '当期退款账单明细',
            border:true,
            striped:true,//斑马线
            singleSelect:true,
            columns:[[
                {field:'orderNo',title:'订单号',width:200, align: 'center'},
                {field:'productName',title:'产品名称',width:360},
                {field:'totalPrice',title:'订单总价',width:150},
                {field:'refundBillAmount',title:'退款金额',width:150},
                {field:'refundDate',title:'退款日期',width:150,datePattern: 'yyyy-MM-dd',formatter: BgmgrUtil.dateTimeFmt}
            ]],
            toolbar: '#refundDetailTb',
            onBeforeLoad : function(data) {
                data.billSummaryId = $("#search-billSummaryId").val();
            }
        });
    },
    // 订单明细查询
    doSearchOrderDetail: function() {
        $('#orderDetailGrid').datagrid({url:'/order/orderBill/orderDetailList.jhtml'});
    },
    statusFormatter: function(value, rowData, rowIndex) {
        if (value === 0) {
            return "未结算";
        } else if (value === 1) {
            return "已结算";
        } else if (value === 2) {
            return "部分结算";
        }
    }
};
