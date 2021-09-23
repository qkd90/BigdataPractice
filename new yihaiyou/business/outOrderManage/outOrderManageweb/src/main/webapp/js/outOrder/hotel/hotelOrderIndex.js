/**
 * Created by dy on 2016/10/12.
 */
var HotelOrderIndex = {
    init: function() {
        HotelOrderIndex.initClientHotel_dg();
    },

    initClientHotel_dg: function() {
        var url = "/outOrder/outOrder/getClientOrderList.jhtml?type=hotel";
        $('#client_hotel_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true,//斑马线
            columns:[[
                {field:'id', checkbox: true , align: 'center'},
                {field:'orderNo',title:'订单编号',width:'10%' , align: 'center'},
                {field:'opt', title:'操作', width:'10%', align:'center', formatter: function(value, rowData, index) {
                    return '<a onclick="HotelOrderIndex.openHotelDetail('+ rowData.id +')">查看</a>'
                }},
                {field:'proName',title:'产品名称',width:'16%' , align: 'center'},
                {field:'totalPrice',title:'总价',width:'5%' , align: 'center'},
                {field:'actualPayPrice',title:'实际支出',width:'5%' , align: 'center',sortable: false, align:'center',
                    formatter: function(value, rowData, index) {
                        if (rowData.status != "CANCELED") {
                            if (rowData.isConfirm == 0) {
                                return "未确认";
                            }
                            if (rowData.isConfirm == 1) {
                                return value;
                            }
                            if (rowData.isConfirm == -2) {
                                return value;
                            }
                        } else {
                            return "已取消";
                        }
                    }
                },
                {field:'status',title:'状态',width:'8%' , align: 'center',
                    formatter: function(value, rowData, index) {
                        //CANCELED("已取消"),UNPAY("待付款"),PAYED("已付款"),UNCANCEL("待取消");
                        if (value == "CANCELED") {
                            return "已取消";
                        }
                        if (value == "UNPAY") {
                            return "待付款";
                        }
                        if (value == "PAYED") {
                            return "已付款";
                        }
                        if (value == "UNCANCEL") {
                            return "待取消";
                        }
                        if (value == "WAITING") {
                            return "待确认";
                        }
                    }
                },
                {field:'contact',title:'联系人',width:'6%' , align: 'center'},
                {field:'phone',title:'联系电话',width:'8%' , align: 'center'},
                {field:'supplierName',title:'供应商',width:'10%' , align: 'center'},
                {field:'companyName',title:'经销商',width:'10%' , align: 'center'},
                {field:'userAccount',title:'下单帐号',width:'8%',sortable: false , align: 'center'},
                {field:'createTime',title:'下单日期',width:'10%' , align: 'center',
                    formatter: function(value, rowData, index) {
                        if (value) {
                            value = value.substr(0, 16);
                        }
                        return value;
                    }
                }
            ]],
            toolbar: '#client_hotel_tool'
        });
    },
    openHotelDetail: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/jszxLineOrder/hotelOrderDetail.jhtml?outOrderId="+id;
        $(ifr).attr("src", url);
        $(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '订单详情',
            width: 400,
            height: 400,
            closed: false,
            cache: false,
            closable:false,
            modal: true
        });
        $("#editPanel").dialog("open");
    },
    doHotelSearch: function() {
        $('#client_hotel_dg').datagrid('load', {
            'jszxOrder.product.name' : $("#client_hotel_proName").textbox("getValue"),
            'jszxOrder.contact' : $("#client_hotel_contact").textbox("getValue"),
            'jszxOrder.user.account' : $("#client_hotel_createby").textbox("getValue"),
            'jszxOrder.orderNo' : $("#client_hotel_orderNo").textbox("getValue"),
            'jszxOrder.phone' : $("#client_hotel_phone").textbox("getValue"),
            'jszxOrder.status' : $("#client_hotel_status").combobox("getValue"),
            'startCreateTimeStr' : $("#start_hotel_createTime").datebox("getValue"),
            'endCreateTimeStr' : $("#end_hotel_createTime").datebox("getValue"),
            'startUseTimeStr':$("#start_hotel_checkinTime").datebox("getValue"),
            'endUseTimeStr':$("#end_hotel_checkinTime").datebox("getValue"),
            'startCheckoutTimeStr': $('#start_hotel_checkoutTime').datebox('getValue'),
            'endCheckoutTimeStr': $('#end_hotel_checkoutTime').datebox('getValue')
        });
    },
    clearHotelForm: function() {
        $("#client_hotel_form").form("reset");
        $("#client_hotel_proName").textbox("setValue", "");
        $("#client_hotel_contact").textbox("setValue", "");
        $("#client_hotel_phone").textbox("setValue", "");
        $("#client_hotel_createby").textbox("setValue", "");
        $("#client_hotel_orderNo").textbox("setValue", "");
        $("#client_hotel_status").combobox("setValue", "");
        $("#start_hotel_createTime").datebox("setValue", "");
        $("#end_hotel_createTime").datebox("setValue", "");
        $("#client_hotel_useStatus").combobox("setValue", "");
        $("#start_hotel_useTime").datebox("setValue", "");
        $("#end_hotel_useTime").datebox("setValue", "");
        $("#start_hotel_checkinTime").datebox("setValue", "");
        $("#end_hotel_checkinTime").datebox("setValue", "");
        $("#start_hotel_checkoutTime").datebox("setValue", "");
        $("#end_hotel_checkoutTime").datebox("setValue", "");
        $('#client_hotel_dg').datagrid('load', {});
    }
}

$(function() {
    HotelOrderIndex.init();
})