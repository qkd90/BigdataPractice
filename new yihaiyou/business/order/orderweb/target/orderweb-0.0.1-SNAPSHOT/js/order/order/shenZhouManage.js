var ShenZhouOrder = {
    serviceId:[
        {'id':'','text':''},
        {'id':'7','text':'接机'},
        {'id':'8','text':'送机'},
        {'id':'11','text':'半日租'},
        {'id':'12','text':'日租'},
        {'id':'13','text':'预约用车'},
        {'id':'14','text':'立即叫车'}
    ],
    carGroupId:[
        {'id':'','text':''},
        {'id':'2','text':'公务轿车'},
        {'id':'3','text':'商务'},
        {'id':'4','text':'豪华轿车'}
    ],
    //unpaid("未支付"), paying("支付处理中"), paymentFailure("支付失败"), partPayment("部分支付"), paid("已支付");
    paymentStatus:[
        {'id':'','text':''},
        {'id':'unpaid','text':'未支付'},
        {'id':'paying','text':'支付处理中'},
        {'id':'paymentFailure','text':'支付失败'},
        {'id':'partPayment','text':'部分支付'},
        {'id':'paid','text':'已支付'}
    ],

    //created("新建"), invalid("无效"), dispatched("已派单"), arriving("已出发"), arrived("已到达"), canceled("已取消"), serviceStarted("已开始服务"),
    //serviceFinished("已结束服务"), feeSubmitted("已提交费用"), paid("已支付待评价"), completed("已完成");

    status:[
        {'id':'','text':''},
        {'id':'created','text':'新建'},
        {'id':'deleted','text':'已删除'},
        {'id':'invalid','text':'无效'},
        {'id':'dispatched','text':'已派单'},
        {'id':'arriving','text':'已出发'},
        {'id':'arrived','text':'已到达'},

        {'id':'canceled','text':'已取消'},
        {'id':'serviceStarted','text':'已开始服务'},
        {'id':'serviceFinished','text':'已结束服务'},
        {'id':'feeSubmitted','text':'已提交费用'},
        {'id':'paid','text':'已支付待评价'},

        {'id':'completed','text':'已完成'}
    ],

    getConstants : function(code, filter) {
        var resultArray = [];
        var codeArray = ShenZhouOrder[code];
        for (var i = 0; i < codeArray.length; i++) {
            var item = codeArray[i];
            if (filter && item.filter) {	// 过滤
                continue ;
            }
            resultArray.push(item);
        }
        return resultArray;
    },
    // 根据代码类型和代码取对应中文名称
    getCodeName : function(codeType, code) {
        if (codeType) {
            var codeArray = ShenZhouOrder[codeType];
            for (var i = 0; i < codeArray.length; i++) {
                if (codeArray[i].id == code) {
                    return codeArray[i].text ;
                }
            }
        }
        return '';
    },
    // 表格代码转换
    codeFmt : function(value, rowData, rowIndex) {
        if (value && this.codeType) {
            return ShenZhouOrder.getCodeName(this.codeType, value);
        }
        return '';
    },

    init: function() {
        ShenZhouOrder.initDataGrid();
    },

    initDataGrid: function() {

        $('#dg').datagrid({
            url:'/order/shenZhouOrder/shenZhouOrderList.jhtml',
            pagination: true,
            pageList: [10, 20, 30, 50, 100],
            rownumbers: true,
            border:false,
            selectOnCheck:true,
            singleSelect:true,
            fitColumns: true,
            fit:true,
            columns:[[
                //{field:'id', checkbox:true, width:100},
                {field:'orderNo', title:'订单编号', width:120},
                {field:'serviceId', title:'服务类型', width:80, align:'center', codeType: 'serviceId', formatter: ShenZhouOrder.codeFmt},
                {field:'carGroupId', title:'车组', width:80, align:'center', codeType: 'carGroupId', formatter: ShenZhouOrder.codeFmt},
                {field:'status', title:'状态', width:70, align:'center', codeType: 'status', formatter: ShenZhouOrder.codeFmt},
                {field:'paymentStatus', title:'订单支付状态', align:'center', width:80, codeType: 'paymentStatus', formatter: ShenZhouOrder.codeFmt},
                {field:'totalPrice', title:'总费用', width:60, align:'center'},
                {field:'passengerMobile',title:'乘客手机号',width:100},
                {field:'driverName',title:'司机姓名',width:100},
                {field:'vehicleNo',title:'车牌号',width:100},
                {field:'user.account',title:'下单帐号',width:100},
                {field:'createTime',title:'下单时间',width:120},
                {field:'opt',title:'操作',width:50, align: 'center',
                    formatter: function(value, rowData, index) {
                        return '<a style="color:blue;" onclick="ShenZhouOrder.doDetail('+ rowData.id +','+ rowData.orderNo +')">详情</a>'
                    }
                }
            ]],
            toolbar:'#searchForm'
        });

    },

    doDetail: function(id, orderNo) {
        var url = "/order/shenZhouOrder/shenZhouOrderDetail.jhtml?shenzhouOrder.id=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            draggable:false,
            title:'订单编号：' + orderNo
        });
        $("#editPanel").dialog("open");
    },

    doSearch: function() {
        var searchForm = {
            'shenzhouOrder.orderNo': $("#sea-orderNo").textbox("getValue"),
            'shenzhouOrder.serviceId': $("#sea-serviceId").combobox("getValue"),
            'shenzhouOrder.status': $("#sea-status").combobox("getValue"),
            'shenzhouOrder.paymentStatus': $("#sea-paymentStatus").combobox("getValue"),
            'shenzhouOrder.passengerName': $("#sea-passengerName").textbox("getValue"),
            'shenzhouOrder.passengerMobile': $("#sea-passengerMobile").textbox("getValue"),
            'shenzhouOrder.user.account': $("#sea-account").textbox("getValue"),
            'shenzhouOrder.vehicleNo': $("#sea-vehicleNo").textbox("getValue"),
            'rStartTime': $("#sea-rStartTime").datetimebox("getValue"),
            'rEndTime': $("#sea-rEndTime").datetimebox("getValue")
        }
        $('#dg').datagrid("load", searchForm);
    },

    doClear: function() {
        $("#sea-orderNo").textbox("setValue","");
        $("#sea-serviceId").combobox("setValue","");
        $("#sea-status").combobox("setValue","");
        $("#sea-paymentStatus").combobox("setValue","");
        $("#sea-passengerName").textbox("setValue","");
        $("#sea-passengerMobile").textbox("setValue","");
        $("#sea-account").textbox("setValue","");
        $("#sea-vehicleNo").textbox("setValue","");
        $("#sea-rStartTime").datetimebox("setValue","");
        $("#sea-rEndTime").datetimebox("setValue","");
        $('#dg').datagrid("load", {});
    }


};

$(function(){
    ShenZhouOrder.init();
});
