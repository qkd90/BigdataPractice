/**
 * Created by dy on 2016/10/12.
 */
var FerryOrderIndex = {
    init: function() {
        FerryOrderIndex.initClientShip_dg();
    },

    initClientShip_dg: function() {
        var url = "/yhyorder/yhyFerryOrder/list.jhtml";
        $('#client_ship_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            fitColumns:true,
            columns:[[
                {field:'orderNo',title:'订单编号',width:'200'},
                {field:'flightNumber',title:'航班号',width:'150'},
                {field:'name',title:'航线名称',width:'450'},
                {field:'departTime',title:'发班时间',width:'200'},
                {field:'price',title:'订单金额',width:'100'},
                {field:'createTime',title:'下单时间',width:'200',sortable: false, align:'center'},
                {field:'status',title:'状态',width:'150',
                    formatter: function(value, rowData, index) {
                        if (value == "SUCCESS") {
                            return "交易完成";
                        }
                        if (value == "CONFIRMED") {
                            return "已确认";
                        }
                        if (value == "FAILED") {
                            return "失败";
                        }
                        if (value == "PARTIAL_FAILED") {
                            return "部分失败";
                        }
                        if (value == "UNCONFIRMED") {
                            return "等待确认";
                        }

                        if (value == "WAIT") {
                            return "等待支付";
                        }
                        if (value == "PAYED") {
                            return "已支付";
                        }
                        if (value == "REFUND") {
                            return "已退款";
                        }
                        if (value == "CANCELED") {
                            return "已取消";
                        }
                        if (value == "CANCELING") {
                            return "取消中";
                        }

                        if (value == "DELETED") {
                            return "已删除";
                        }
                        if (value == "CLOSED") {
                            return "已关闭";
                        }
                        if (value == "INVALID") {
                            return "无效订单";
                        }
                        if (value == "PROCESSING") {
                            return "处理中";
                        }
                        if (value == "PROCESSED") {
                            return "已处理";
                        }
                    }
                },
                //{field:'userName',title:'联系人',width:'8%'},
                //{field:'mobile',title:'联系电话',width:'8%'},
                {field:'opt',title:'操作',width:'100', formatter: function(value, row, index) {
                    var btn = '<a onclick="FerryOrderIndex.openFerryOrderDetail('+ row.id +')">详情</a>';
                    //if (row.status == "SUCCESS") {
                    //    btn += '&nbsp;<a onclick="FerryOrderIndex.doDefundOrder('+ row.id +')">申请退款</a>';
                    //}
                    return btn;
                }}
            ]],
            toolbar: '#client_ship_tool',
            onBeforeLoad: function(data) {
                data['order.searchKeyword'] = $("#client_ship_keyword").textbox("getValue");
                data['order.status'] = $("#client_ship_status").combobox("getValue");
                data['order.orderType'] = 'ferry';
                data['order.thirdOrderSources[0]'] = 'FERRY';
                data['orderFormVo.startTime'] = $("#start_ship_createTime").datebox("getValue");
                data['orderFormVo.endTime'] = $("#end_ship_createTime").datebox("getValue");
                if ($("#start_ship_useTime").datebox("getValue") && $("#end_ship_useTime").textbox("getValue")) {
                    data['order.departTime'] = $("#start_ship_useTime").datebox("getValue") + " " + $("#end_ship_useTime").textbox("getValue");
                } else if ($("#start_ship_useTime").datebox("getValue") && !$("#end_ship_useTime").textbox("getValue")) {
                    data['order.departTime'] = $("#start_ship_useTime").datebox("getValue");
                } else if (!$("#start_ship_useTime").datebox("getValue") && $("#end_ship_useTime").textbox("getValue")) {
                    data['order.departTime'] = $("#end_ship_useTime").textbox("getValue");
                }

            }
        });
    },
    doShipSearch: function() {
        $('#client_ship_dg').datagrid('load', {});
    },
    clearShipForm: function() {
        $("#client_ship_form").form("reset");
        $("#client_ship_keyword").textbox("setValue", "");
        $("#client_ship_status").combobox("setValue", "");
        $("#start_ship_createTime").datebox("setValue", "");
        $("#end_ship_createTime").datebox("setValue", "");
        $("#start_ship_useTime").datebox("setValue", "");
        $("#end_ship_useTime").textbox("setValue", "");
        $('#client_ship_dg').datagrid('load', {});
    },
    openFerryOrderDetail: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/yhyorder/yhyFerryOrder/ferryOrderDetail.jhtml?ferryOrder.id="+id;
        $(ifr).attr("src", url);
        $(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '订单详情',
            width: 400,
            height: 400,
            closed: false,
            draggable:false,
            cache: false,
            closable:true,
            modal: true
        });
        $("#editPanel").dialog("open");
    },
    // 申请退款
    doDefundOrder: function(id) {
        $.messager.confirm('温馨提示', '确认提交轮渡退款申请？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/yhyorder/yhyFerryOrder/defundOrder.jhtml",
                    {ferryOrderId : id},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            FerryOrderIndex.doShipSearch();
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

$(function() {
    FerryOrderIndex.init();
})