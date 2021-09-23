/**
 * Created by zzl on 2016/10/8.
 */
var ElongOrderManage = {
    orderStatus: [
        {value: '待确认', id: 'UNCONFIRMED'},
        {value: '预订成功', id: 'SUCCESS'},
        {value: '预订失败', id: 'FAILED'},
        {value: '待支付', id: 'WAIT'},
        {value: '已退款', id: 'REFUND'},
        {value: '已取消', id: 'CANCELED'},
        {value: '无效订单', id: 'INVALID'}
    ],
    init: function() {
        ElongOrderManage.initElongOrderManage_dg();
    },
    initElongOrderManage_dg: function() {
        var url = "/yhyorder/yhyElongHotelOrder/getOrderList.jhtml";
        $('#client_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            fitColumns:true,
            columns:[[
                {field: 'id', title: '订单ID', width: '100'},
                {field: 'orderNo', title: '订单编号', width: '200'},
                //{field: 'userName', title: '联系人', width: '200'},
                {
                    field: 'name', title: '预订产品名称', width: '450'
                },
                {field: 'price', title: '订单金额', width: '200', align: 'center'},
                {field: 'mobile', title: '联系电话', width: '200'},
                {field: 'createTime', title: '下单时间', width: '200'},
                {
                    field: 'status', title: '订单状态', width: '200', formatter: function (value) {
                    if (value == 'WAIT') {
                        return '<span class="wait" style="color: #ff4b4a">待支付</span>';
                    } else if (value == 'PAYED') {
                        return '<span class="payed" style="color: blue">已支付</span>';
                    } else if (value == 'PROCESSING') {
                        return '<span class="processing" style="color: #e200fd">处理中</span>';
                    } else if (value == 'PROCESSED') {
                        return '<span class="processed">已处理</span>';
                    } else if (value == 'UNCONFIRMED') {
                        return '<span class="unconfirmed" style="color: #3300aa">待确认</span>';
                    } else if(value == 'SUCCESS') {
                        return '<span class="success" style="color: #126808">预订成功</span>';
                    } else if(value == 'FAILED') {
                        return '<span class="failed" style="color: #ff4c4d">预订失败</span>';
                    } else if(value == 'CANCELED') {
                        return '<span class=failed style="color: #d08a1d">已取消</span>';
                    } else if(value == 'CANCELING') {
                        return '<span class=failed style="color: #c0c0c0">取消中</span>';
                    } else if (value == 'DELETED') {
                        return '<span class="deleted" style="color: #706f6e">已删除</span>'
                    } else if (value == 'REFUND') {
                        return '<span class="deleted" style="color: #67706a">已退款</span>'
                    } else if (value == 'INVALID') {
                        return '<span class="deleted" style="color: #706f6e">无效订单</span>'
                    } else {
                        return '<span class="other">-</span>';
                    }
                }
                },
                {
                    field: 'OPT', title: '操作', width: '100', align: 'center', formatter: function (value, rowData, index) {
                    var btn =  '<a href="#" style="color:blue;" onclick="ElongOrderManage.openDetail(' + rowData.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    var returnBtn = btn;
                    return returnBtn;
                }
                }

            ]],
            toolbar: '#client_tool',
            onBeforeLoad: function(data) {
                data['order.orderType'] = 'hotel';
                data['order.thirdOrderSources[0]'] = 'ELONG';
                if ($("#client_orderNo").textbox("getValue")) {
                    data['order.orderNo'] = $("#client_orderNo").textbox("getValue");
                }
                if ($("#client_recName").textbox("getValue")) {
                    data['order.recName'] = $("#client_recName").textbox("getValue");
                }
                if ($("#client_mobile").textbox("getValue")) {
                    data['order.mobile'] = $("#client_mobile").textbox("getValue");
                }
                if ($("#client_status").combobox("getValue")) {
                    data['order.status'] = $("#client_status").combobox("getValue");
                }
                if ($("#start_createTime").datebox("getValue")) {
                    data['orderFormVo.startTime'] = $("#start_createTime").datebox("getValue");
                }
                if ($("#end_createTime").datebox("getValue")) {
                    data['orderFormVo.endTime'] = $("#end_createTime").datebox("getValue");
                }
            }
        });
    },



    doSearch: function() {
        $('#client_dg').datagrid('load', {});
    },
    clearForm: function() {
        $("#client_form").form("reset");
        $("#client_orderNo").textbox("setValue", "");
        $("#client_recName").textbox("setValue", "");
        $("#client_mobile").textbox("setValue", "");
        $("#client_status").combobox("setValue", "");
        $("#start_createTime").datebox("setValue", "");
        $("#end_createTime").datebox("setValue", "");
        $('#client_dg').datagrid('load', {});
    },
    openDetail: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/yhyorder/yhyElongHotelOrder/orderDetail.jhtml?orderId="+id;
        $(ifr).attr("src", url);
        $(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '订单详情',
            closed: false,
            cache: false,
            draggable:false,
            closable:true,
            modal: true
        });
        $("#editPanel").dialog("open");
    }
};

$(function() {
    ElongOrderManage.init();
});

function getOrderType(orderType) {
    if (orderType == 'scenic') {
        return '门票';
    } else if (orderType == 'restaurant') {
        return '餐厅';
    } else if (orderType == 'hotel') {
        return '酒店';
    } else if (orderType == 'line') {
        return '线路';
    } else if (orderType == 'train') {
        return '火车票';
    } else if (orderType == 'flight') {
        return '机票';
    } else if (orderType == 'delicacy') {
        return '美食';
    } else if (orderType == 'recplan') {
        return '游记';
    } else if (orderType == 'plan') {
        return '行程';
    } else if (orderType == 'sailboat') {
        return '帆船';
    } else if (orderType == 'yacht') {
        return '游艇';
    } else if (orderType == 'insurance') {
        return '保险';
    } else if (orderType == "cruiseship") {
        return '邮轮';
    }else {
        return "其他"
    }
}