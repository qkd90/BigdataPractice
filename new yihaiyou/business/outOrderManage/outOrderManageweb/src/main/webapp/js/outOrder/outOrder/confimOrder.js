/**
 * Created by dy on 2016/3/15.
 */
var ConfimOrder = {

    init: function() {
        ConfimOrder.initConfimOrderDetails();
    },

    initConfimOrderDetails: function() {

        var orderId = $("#confim_orderId").val();

        var url = "/outOrder/outOrder/getConfimOrderDetails.jhtml?outOrderId="+orderId;
        $('#dg_orderDetails').datagrid({
            url:url,
            fit:true,
            width: '540px',
            height: '220px',
            columns:[[
                //{field:'id',checkbox:true,title:'',width:'1%'},
                {field:'ticketName',title:'门票类型',width:'30%'},
                {field:'startTime',title:'有效期',width:'40%',
                    formatter: function(value, rowData, index) {
                        var startTime = value.substring(0, 10);
                        var endTime = rowData.endTime;
                        endTime = endTime.substring(0, 10);
                        return startTime + "至" + endTime;
                    }
                },
                {field:'price',title:'单价',width:'10%'},
                {field:'count',title:'数量',width:'10%'},
                {field:'totalPrice',title:'小计',width:'10%'},
                {field:'actualPay',title:'实际支出',width:'10%'}
            ]]
        });


    }


}


$(function() {
    ConfimOrder.init();
});
