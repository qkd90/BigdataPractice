/**
 * Created by zzl on 2016/10/8.
 */
var ClientSailboatOrderManage = {
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
        ClientSailboatOrderManage.initJsp();
        ClientSailboatOrderManage.initClientSailboat_dg();
        ClientSailboatOrderManage.initClientYacht_dg();
        ClientSailboatOrderManage.initClientHuanguyou_dg();
    },

    initJsp: function() {
        $('#client_sailboat_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q, 'unit.sysUnitDetail.supplierType': 'sailboat'},
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
        $('#client_yacht_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q, 'unit.sysUnitDetail.supplierType': 'sailboat'},
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
        $('#client_huanguyou_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q, 'unit.sysUnitDetail.supplierType': 'sailboat'},
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
    initClientSailboat_dg: function() {
        var url = "/yhyorder/yhyOrder/getOrderList.jhtml?order.orderType=sailboat";
        $('#client_sailboat_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            //fitColumns:true,
            striped:true,//斑马线
            columns:[[
                {field: 'id', title: '订单ID', width: '100', align: 'left'},
                {field: 'orderNo', title: '订单编号', width: '200', align: 'left'},
                {field: 'userName', title: '取票人', width: '200', align: 'left'},
                {field: 'mobile', title: '取票人电话', width: '200', align: 'left'},
                {field: 'name', title: '预订产品名称', width: '450', align: 'left'},
                {field: 'price', title: '订单金额', width: '200', align: 'center'},
                {field: 'companyUnitName', title: '供应商', width: '200', align: 'left'},
                {field: 'createTime', title: '下单时间', width: '200', align: 'left'},
                {
                    field: 'status', title: '订单状态', width: '100', align: 'left', formatter: function (value) {
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
                    field: 'OPT', title: '操作', width: '80', align: 'center', formatter: function (value, rowData, index) {
                    var btn =  '<a href="#" style="color:blue;" onclick="ClientSailboatOrderManage.openDetail(' + rowData.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    var returnBtn = btn;
                    return returnBtn;
                }
                }

            ]],

            toolbar: '#client_sailboat_tool',
            onBeforeLoad: function(data){

                if ($("#client_sailboat_orderNo").textbox("getValue")) {
                    data['order.orderNo' ]= $("#client_sailboat_orderNo").textbox("getValue");
                }
                if ($("#client_sailboat_recName").textbox("getValue")) {
                    data['order.recName' ]= $("#client_sailboat_recName").textbox("getValue");
                }
                if ($("#client_sailboat_mobile").textbox("getValue")) {
                    data['order.mobile' ]= $("#client_sailboat_mobile").textbox("getValue");
                }
                if ($("#client_sailboat_status").combobox("getValue")) {
                    data['order.status' ]= $("#client_sailboat_status").combobox("getValue");
                }
                if ($("#client_sailboat_companyId").combobox("getValue")) {
                    data['order.companyUnit.id' ]= $("#client_sailboat_companyId").combobox("getValue");
                }
                if ($("#start_sailboat_createTime").datebox("getValue")) {
                    data['orderFormVo.startTime' ]= $("#start_sailboat_createTime").datebox("getValue");
                }
                if ($("#end_sailboat_createTime").datebox("getValue")) {
                    data['orderFormVo.endTime' ]= $("#end_sailboat_createTime").datebox("getValue");
                }
            }
        });
    },


    initClientYacht_dg: function() {
        var url = "/yhyorder/yhyOrder/getOrderList.jhtml?order.orderType=yacht";
        $('#client_yacht_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            //fitColumns:true,
            striped:true,//斑马线
            columns:[[
                {field: 'id', title: '订单ID', width: '100', align: 'left'},
                {field: 'orderNo', title: '订单编号', width: '200', align: 'left'},
                {field: 'userName', title: '取票人', width: '200', align: 'left'},
                {field: 'mobile', title: '取票人电话', width: '200', align: 'left'},

                {
                    field: 'name', title: '预订产品名称', width: '450', align: 'left'
                },
                {field: 'price', title: '订单金额', width: '200', align: 'center'},
                {
                    field: 'companyUnitName', title: '供应商', width: '200', align: 'left'
                },
                {field: 'createTime', title: '下单时间', width: '200', align: 'left'},
                {
                    field: 'status', title: '订单状态', width: '100', align: 'left', formatter: function (value) {
                    if (value == 'WAIT') {
                        return '<span class="wait" style="color: #ff4b4a">待支付</span>';
                    } else if (value == 'INVALID') {
                        return '<span class="deleted" style="color: #706f6e">无效订单</span>'
                    } else if (value == 'PAYED') {
                        return '<span class="payed" style="color: blue">已支付</span>';
                    } else if (value == 'PROCESSING') {
                        return '<span class="processing" style="color: #e200fd">处理中</span>';
                    } else if (value == 'PROCESSED') {
                        return '<span class="processed">已处理</span>';
                    } else if (value == 'UNCONFIRMED') {
                        return '<span class="unconfirmed" style="color: #3300aa">待确认</span>';
                    } else if(value == 'SUCCESS') {
                        return '<span class="success" style="color: #126808">成功</span>';
                    } else if(value == 'FAILED') {
                        return '<span class="failed" style="color: #ff4c4d">失败</span>';
                    } else if(value == 'CANCELED') {
                        return '<span class=failed style="color: #d08a1d">已取消</span>';
                    } else if (value == 'REFUND') {
                        return '<span class="deleted" style="color: #706f6e">已退款</span>'
                    }  else if(value == 'CANCELING') {
                        return '<span class=failed style="color: #c0c0c0">取消中</span>';
                    } else if (value == 'DELETED') {
                        return '<span class="deleted" style="color: #706f6e">已删除</span>'
                    } else {
                        return '<span class="other">-</span>';
                    }
                }
                },
                {
                    field: 'OPT', title: '操作', width: '80', align: 'center', formatter: function (value, row, index) {
                    var btn =  '<a href="#" style="color:blue;" onclick="ClientSailboatOrderManage.openDetail(' + row.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    //btn +=  '&nbsp;|&nbsp;<a href="#" onclick="ClientSailboatOrderManage.getLog(' + row.id + ',' + null + ')" class="easyui-linkbutton" >查看日志</a>';
                    return btn;
                }
                }

            ]],
            toolbar: '#client_yacht_tool',
            onBeforeLoad: function(data){

                if ($("#client_yacht_orderNo").textbox("getValue")) {
                    data['order.orderNo' ] = $("#client_yacht_orderNo").textbox("getValue");
                }
                if ($("#client_yacht_recName").textbox("getValue")) {
                    data['order.recName' ] = $("#client_yacht_recName").textbox("getValue");
                }
                if ($("#client_yacht_mobile").textbox("getValue")) {
                    data['order.mobile' ] = $("#client_yacht_mobile").textbox("getValue");
                }
                if ($("#client_yacht_status").combobox("getValue")) {
                    data['order.status' ] = $("#client_yacht_status").combobox("getValue");
                }
                if ($("#client_yacht_companyId").combobox("getValue")) {
                    data['order.companyUnit.id' ] = $("#client_yacht_companyId").combobox("getValue");
                }
                if ($("#start_yacht_createTime").datebox("getValue")) {
                    data['orderFormVo.startTime' ] = $("#start_yacht_createTime").datebox("getValue");
                }
                if ($("#end_yacht_createTime").datebox("getValue")) {
                    data['orderFormVo.endTime' ] = $("#end_yacht_createTime").datebox("getValue");
                }
            }
        });
    },

    initClientHuanguyou_dg: function() {
        var url = "/yhyorder/yhyOrder/getOrderList.jhtml?order.orderType=huanguyou";
        $('#client_huanguyou_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            //fitColumns:true,
            striped:true,//斑马线
            columns:[[
                {field: 'id', title: '订单ID', width: '100', align: 'left'},
                {field: 'orderNo', title: '订单编号', width: '200', align: 'left'},
                {field: 'userName', title: '取票人', width: '200', align: 'left'},
                {field: 'mobile', title: '取票人电话', width: '200', align: 'left'},
                {field: 'name', title: '预订产品名称', width: '450'},
                {field: 'price', title: '订单金额', width: '200', align: 'center'},
                {field: 'companyUnitName', title: '供应商', width: '200', align: 'left'},
                {field: 'createTime', title: '下单时间', width: '200', align: 'left'},
                {
                    field: 'status', title: '订单状态', width: '100', align: 'left', formatter: function (value) {
                    if (value == 'WAIT') {
                        return '<span class="wait" style="color: #ff4b4a">待支付</span>';
                    } else if (value == 'INVALID') {
                        return '<span class="deleted" style="color: #706f6e">无效订单</span>'
                    } else if (value == 'PAYED') {
                        return '<span class="payed" style="color: blue">已支付</span>';
                    } else if (value == 'PROCESSING') {
                        return '<span class="processing" style="color: #e200fd">处理中</span>';
                    } else if (value == 'PROCESSED') {
                        return '<span class="processed">已处理</span>';
                    } else if (value == 'UNCONFIRMED') {
                        return '<span class="unconfirmed" style="color: #3300aa">待确认</span>';
                    } else if(value == 'SUCCESS') {
                        return '<span class="success" style="color: #126808">成功</span>';
                    } else if(value == 'FAILED') {
                        return '<span class="failed" style="color: #ff4c4d">失败</span>';
                    } else if(value == 'CANCELED') {
                        return '<span class=failed style="color: #d08a1d">已取消</span>';
                    } else if (value == 'REFUND') {
                        return '<span class="deleted" style="color: #706f6e">已退款</span>'
                    } else if(value == 'CANCELING') {
                        return '<span class=failed style="color: #c0c0c0">取消中</span>';
                    } else if (value == 'DELETED') {
                        return '<span class="deleted" style="color: #706f6e">已删除</span>'
                    } else {
                        return '<span class="other">-</span>';
                    }
                }
                },
                {
                    field: 'OPT', title: '操作', width: '80', align: 'center', formatter: function (value, row, index) {
                    var btn =  '<a href="#" style="color:blue;" onclick="ClientSailboatOrderManage.openDetail(' + row.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    //btn +=  '&nbsp;|&nbsp;<a href="#" onclick="ClientSailboatOrderManage.getLog(' + row.id + ',' + null + ')" class="easyui-linkbutton" >查看日志</a>';
                    return btn;
                }
                }

            ]],
            toolbar: '#client_huanguyou_tool',
            onBeforeLoad: function(data){
                if ($("#client_huanguyou_orderNo").textbox("getValue")) {
                    data['order.orderNo' ]= $("#client_huanguyou_orderNo").textbox("getValue");
                }
                if ($("#client_huanguyou_recName").textbox("getValue")) {
                    data['order.recName' ]= $("#client_huanguyou_recName").textbox("getValue");
                }
                if ($("#client_huanguyou_mobile").textbox("getValue")) {
                    data['order.mobile' ]= $("#client_huanguyou_mobile").textbox("getValue");
                }
                if ($("#client_huanguyou_companyId").combobox("getValue")) {
                    data['order.companyUnit.id' ]= $("#client_huanguyou_companyId").combobox("getValue");
                }
                if ($("#client_huanguyou_status").combobox("getValue")) {
                    data['order.status' ]= $("#client_huanguyou_status").combobox("getValue");
                }
                if ($("#start_huanguyou_createTime").datebox("getValue")) {
                    data['orderFormVo.startTime' ]= $("#start_huanguyou_createTime").datebox("getValue");
                }
                if ($("#end_huanguyou_createTime").datebox("getValue")) {
                    data['orderFormVo.endTime' ]= $("#end_huanguyou_createTime").datebox("getValue");
                }
            }
        });
    },


    doHuanguyouSearch: function() {
        $('#client_huanguyou_dg').datagrid('load', {});
    },
    clearHuanguyouForm: function() {
        $("#client_huanguyou_form").form("reset");
        $("#client_huanguyou_orderNo").textbox("setValue", "");
        $("#client_huanguyou_recName").textbox("setValue", "");
        $("#client_huanguyou_companyId").combobox("setValue", "");
        $("#client_huanguyou_mobile").textbox("setValue", "");
        $("#client_huanguyou_status").combobox("setValue", "");
        $("#start_huanguyou_createTime").datebox("setValue", "");
        $("#end_huanguyou_createTime").datebox("setValue", "");
        $('#client_huanguyou_dg').datagrid('load', {});
    },


    doSailboatSearch: function() {
        $('#client_sailboat_dg').datagrid('load', {});
    },
    clearSailboatForm: function() {
        $("#client_sailboat_form").form("reset");
        $("#client_sailboat_orderNo").textbox("setValue", "");
        $("#client_sailboat_recName").textbox("setValue", "");
        $("#client_sailboat_mobile").textbox("setValue", "");
        $("#client_sailboat_status").combobox("setValue", "");
        $("#client_sailboat_companyId").combobox("setValue", "");
        $("#start_sailboat_createTime").datebox("setValue", "");
        $("#end_sailboat_createTime").datebox("setValue", "");
        $('#client_sailboat_dg').datagrid('load', {});
    },

    doYachtSearch: function() {
        $('#client_yacht_dg').datagrid('load', {});
    },
    clearYachtForm: function() {
        $("#client_yacht_form").form("reset");
        $("#client_yacht_orderNo").textbox("setValue", "");
        $("#client_yacht_recName").textbox("setValue", "");
        $("#client_yacht_mobile").textbox("setValue", "");
        $("#client_yacht_status").combobox("setValue", "");
        $("#client_yacht_companyId").combobox("setValue", "");
        $("#start_yacht_createTime").datebox("setValue", "");
        $("#end_yacht_createTime").datebox("setValue", "");
        $('#client_yacht_dg').datagrid('load', {});
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
                $('#client_sailboat_dg').datagrid('reload', {});
                $('#client_yacht_dg').datagrid('reload', {});
                $('#client_huanguyou_dg').datagrid('reload', {});
            }
        });
        $("#editPanel").dialog("open");
    }
};

$(function() {
    ClientSailboatOrderManage.init();
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