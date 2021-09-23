/**
 * Created by vacuity on 15/11/4.
 */

var Detail={
    init: function(userId){
        listUrl = "/customer/customer/orderList.jhtml?id=" + userId;
        $('#orderDg').datagrid({
            url: listUrl,
            pagination: true,
            pageList: [10, 20],
            rownumbers: true,
            fitColumns: true,
            columns: [[
                {field: 'orderNo', title: '订单编号', width: 100, sortable: true},
                {field: 'recName', title: '用户名', width: 100, sortable: true},
                {field: 'address', title: '地址', width: 100, sortable: true},
                {field: 'mobilePhone', title: '电话', width: 100, sortable: true},
                {field:'status',title:'订单状态',width:100,sortable: true,formatter:function(value,row,index){
                    if(row.status!=null && row.status=="UNCONFIRMED"){
                        return "<font style='color:blue'>等待确认</font>";
                    }else if(row.status!=null && row.status=="WAIT"){
                        return "<font style='color:red'>等待支付</font>";
                    }else if(row.status!=null && row.status=="REFUND"){
                        return "<font style='color:forestgreen'>已退款</font>";
                    }else if(row.status!=null && row.status=="CLOSED"){
                        return "<font style='color:forestgreen'>已关闭</font>";
                    }else if(row.status!=null && row.status=="INVALID"){
                        return "<font style='color:forestgreen'>无效订单</font>";
                    }
                } },
                {field: 'createTime', title: '下单时间', width: 100, sortable: true},
            ]]
        });
    },
}

