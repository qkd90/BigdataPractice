/**
 * Created by dy on 2017/2/28.
 */
var ClientOrderManage = {
    orderStatus: [
        {value: '预订成功', id: 'SUCCESS'},
        {value: '预订失败', id: 'FAILED'},
        {value: '待支付', id: 'WAIT'},
        {value: '已退款', id: 'REFUND'},
        {value: '已取消', id: 'CANCELED'},
        {value: '无效订单', id: 'INVALID'}
    ],
    /*UNCONFIRMED("待确认"), WAIT("待支付"),
     SUCCESS("预订成功"), FAILED("预订失败"),
     REFUND("已退款"), CANCELED("已取消"), INVALID("无效订单"),
     */
    cruishipOrderStatus: [
        {value: '待确认', id: 'UNCONFIRMED'},
        {value: '预订成功', id: 'SUCCESS'},
        {value: '预订失败', id: 'FAILED'},
        {value: '待支付', id: 'WAIT'},
        {value: '已退款', id: 'REFUND'},
        {value: '已取消', id: 'CANCELED'},
        {value: '无效订单', id: 'INVALID'}
    ],

    planOrderStatus: [
        {value: '待确认', id: 'UNCONFIRMED'},
        {value: '预订成功', id: 'SUCCESS'},
        {value: '预订失败', id: 'FAILED'},
        {value: '部分失败', id: 'PARTIAL_FAILED'},
        {value: '待支付', id: 'WAIT'},
        {value: '已退款', id: 'REFUND'},
        {value: '已取消', id: 'CANCELED'},
        {value: '无效订单', id: 'INVALID'}
    ],
    init: function() {
        ClientOrderManage.initJsp();
        ClientOrderManage.initClient_dg();
        ClientOrderManage.initConfirmOrderDg();
    },
    initJsp: function() {
        $('#client_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q, 'unit.sysUnitDetail.supplierType': 'hotel'},
                    success: function(data){
                        success(data);
                    },
                    error: function(){
                        //error.apply(this, arguments);
                    }
                });
            },
            prompt:'请输入商户名称',
            valueField:"unitId",
            textField:"unitName",
            mode: 'remote'
        });
    },

    initClient_dg: function() {
        var url = "/yhyorder/yhyOrder/getOrderList.jhtml";
        $('#client_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            //fitColumns:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field: 'id', title: '订单ID', width: '100'},
                {field: 'orderNo', title: '订单编号', width: '200'},
                {
                    field: 'name', title: '预订产品名称', width: '450'
                },
                {field: 'price', title: '订单金额', width: '200', align: 'center'},
                {
                    field: 'companyUnitName', title: '供应商', width: '200', align: 'left'
                },
                {field: 'mobile', title: '联系电话', width: '200'},
                {field: 'createTime', title: '下单时间', width: '200'},
                {
                    field: 'status', title: '订单状态', width: '100', formatter: function (value) {
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
                    } else if (value == 'PARTIAL_FAILED') {
                        return '<span class="unconfirmed" style="color: #3300aa">部分失败</span>';
                    } else if(value == 'SUCCESS') {
                        return '<span class="success" style="color: #126808">预订成功</span>';
                    } else if(value == 'FAILED') {
                        return '<span class="failed" style="color: #ff4c4d">预订失败</span>';
                    } else if(value == 'CANCELED') {
                        return '<span class=failed style="color: #d08a1d">已取消</span>';
                    } else if (value == 'REFUND') {
                        return '<span class="deleted" style="color: #706f6e">已退款</span>'
                    } else if (value == 'INVALID') {
                        return '<span class="deleted" style="color: #706f6e">无效订单</span>'
                    } else {
                        return '<span class="other">-</span>';
                    }
                }
                },
                {
                    field: 'OPT', title: '操作', width: '80', align: 'center', formatter: function (value, rowData, index) {
                    var btn =  '<a href="#" style="color:blue;" onclick="ClientOrderManage.openDetail(' + rowData.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    //btn +=  '&nbsp;|&nbsp;<a href="#" onclick="Orders.getLog(' + row.id + ',' + null + ')" class="easyui-linkbutton" >查看日志</a>';
                    //var confirmBtn = '&nbsp;|&nbsp;<a href="#" style="color:blue;" onclick="ClientOrderManage.openConfirmDialog(' + rowData.id + ')" class="easyui-linkbutton" >确认订单</a>'
                    //var returnBtn = btn;
                    //if (rowData.status === 'UNCONFIRMED') {
                    //    returnBtn += confirmBtn;
                    //}
                    return btn;
                }
                }

            ]],
            toolbar: '#client_tool',
            onBeforeLoad: function(data) {

                if ($("#orderType").val() == 'plan') {
                    data['order.orderType'] = $("#orderType").val();
                } else {
                    data['order.orderType'] = $("#orderType").val();
                    data['order.thirdOrderSources[0]'] = 'LXB';
                }

                if ($("#client_orderNo").textbox("getValue")) {
                    data['order.orderNo'] = $("#client_orderNo").textbox("getValue");
                }

                if ($("#client_mobile").textbox("getValue")) {
                    data['order.mobile'] = $("#client_mobile").textbox("getValue");
                }
                if ($("#client_status").combobox("getValue")) {
                    data['order.status'] = $("#client_status").combobox("getValue");
                }
                if ($("#client_companyId").combobox("getValue")) {
                    data['order.companyUnit.id'] = $("#client_companyId").combobox("getValue");
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
        $("#client_companyId").combobox("setValue", "");
        $("#start_createTime").datebox("setValue", "");
        $("#end_createTime").datebox("setValue", "");
        $('#client_dg').datagrid('load', {});
    },


    openDetail: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/yhyorder/yhyOrder/orderDetail.jhtml?orderId="+id;
        $(ifr).attr("src", url);
        $(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '订单详情',
            closed: false,
            cache: false,
            closable:true,
            draggable:false,
            modal: true,
            onClose: function() {
                $('#client_dg').datagrid('reload', {});
            }
        });
        $("#editPanel").dialog("open");

    },

    openConfirmDialog: function(id) {
        $("#confirmDialog").dialog({
            title: '确认订单',
            closed: true,
            width:600,
            height:500,
            cache: false,
            closable:true,
            modal: true,
            buttons: [
                {
                    text:'确认',
                    handler: function() {
                        ClientOrderManage.doConfirm();
                    }
                },
                {
                    text:'取消订单',
                    handler: function() {
                        ClientOrderManage.doCancel();
                    }
                },
                {
                    text:'退出',
                    handler: function() {
                        $("#confirmDialog").dialog("close");
                    }
                }
            ]
        });
        ClientOrderManage.getUnConfirmData(id);
    },





    getUnConfirmData: function(id) {
        var url = "/yhyorder/yhyOrder/getConfirmOrderList.jhtml";
        $.post(url, {'orderId': id },
            function(data){
                if (data.total > 0) {
                    $("#confirmDialog").dialog("open");
                    $('#confirmOrder_dg').datagrid('loadData',data.rows);
                } else {
                    show_msg("订单已确认！");
                }
            }
        );
    },

    initConfirmOrderDg: function() {
        $('#confirmOrder_dg').datagrid({
            //rownumbers:true,
            fit:true,
            singleSelect:false,
            striped:true,//斑马线
            columns:[[
                {field:'ck',checkbox:true},
                {field: 'orderNo', title: '订单编号', width: '29%'},
                {field: 'orderName', title: '预订产品名称', width: '40%'},
                {field: 'orderType', title: '类型', width: '8%',
                    formatter: function (value, row) {
                        var types = '';
                        types = getOrderType(value);
                        return types;
                    }
                },
                {
                    field: 'detailStatus', title: '状态', width: '12%',
                    formatter: function (value) {
                        if (value == 'WAIT') {
                            return '<span class="wait" style="color: #ff4b4a">待支付</span>';
                        } else if (value == 'PAYED') {
                            return '<span class="payed" style="color: blue">已支付</span>';
                        } else if (value == 'PROCESSING') {
                            return '<span class="processing" style="color: #e200fd">处理中</span>';
                        } else if (value == 'PROCESSED') {
                            return '<span class="processed">已处理</span>';
                        } else if (value == 'UNCONFIRMED') {
                            return '<span class="unconfirmed" style="color: #3300aa">等待确认</span>';
                        } else if(value == 'SUCCESS') {
                            return '<span class="success" style="color: #126808">成功</span>';
                        } else if(value == 'FAILED') {
                            return '<span class="failed" style="color: #ff4c4d">失败</span>';
                        } else if(value == 'CANCELED') {
                            return '<span class=failed style="color: #d08a1d">已取消</span>';
                        } else if(value == 'CANCELING') {
                            return '<span class=failed style="color: #c0c0c0">取消中</span>';
                        } else if (value == 'DELETED') {
                            return '<span class="deleted" style="color: #706f6e">已删除</span>'
                        }else {
                            return '<span class="other">-</span>';
                        }
                    }
                }

            ]]
        });
    }

};

$(function() {
    ClientOrderManage.init();
});

function getOrderType(orderType) {
    if (orderType == 'scenic' || orderType == 'ticket') {
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