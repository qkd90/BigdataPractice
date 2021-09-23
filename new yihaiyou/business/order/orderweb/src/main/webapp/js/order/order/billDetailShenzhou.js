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
            url:'/order/orderBill/orderBillDetailShenzhou.jhtml',
            pagination: true,
            pageList: [10, 30, 60],
            //rownumbers: false,
            title: '当期结算账单明细',
            border:true,
            striped:true,//斑马线
            singleSelect:true,
            columns:[[
                {field:'orderNo',title:'订单编号',width:130},
                {field:'shenzhouOrderId',title:'神州单号',width:200},
                {field:'passengerName',title:'乘客姓名',width:100},
                {field:'passengerMobile',title:'乘客手机',width:100},
                {field:'totalPrice',title:'订单总价',width:150},
                {field:'orderBillPrice',title:'结算价',width:150}
            ]],
            toolbar: '#orderDetailTb',
            onBeforeLoad : function(data) {
                data.billSummaryId = $("#search-billSummaryId").val();
            }
        });
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
