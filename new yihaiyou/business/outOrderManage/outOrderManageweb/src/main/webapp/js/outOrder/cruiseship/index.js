/**
 * Created by zzl on 2016/10/8.
 */
var ClientCruiseShipOrderManage = {
    init: function() {
        ClientCruiseShipOrderManage.initClientCruiseShip_dg();
    },
    initClientCruiseShip_dg: function() {
        var url = "/outOrder/outOrder/getClientOrderList.jhtml?type=cruiseship";
        $('#client_cruiseship_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true,//斑马线
            columns:[[
                //{field:'id', checkbox: true },
                {field:'orderNo',title:'订单编号',width:'10%'},
                {field:'opt',title:'操作',width:'10%',sortable: false,
                    formatter: function(value, rowData, index) {
                        return '<a onclick="ClientCruiseShipOrderManage.openCruiseshipDetail('+ rowData.id +')">查看</a>';
                    }
                },
                {field:'proName',title:'邮轮名称',width:'16%'},

                {field:'msgCount',title:'使用日期',width:'14%',sortable: false,
                    formatter: function(value, rowData, index) {
                        if(rowData.jszxOrderDetailList.length>0){
                            var html = "<ul>";
                            $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                if((rowData.jszxOrderDetailList.length-1) != i){
                                    var startTime = "";
                                    if (perValue.startTime) {
                                        startTime = perValue.startTime;
                                        startTime = startTime.substr(0, 10);
                                    }
                                    var endTime = "";
                                    if (perValue.endTime) {
                                        endTime = perValue.endTime;
                                        endTime = endTime.substr(0, 10);
                                        html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ startTime + "---"+ endTime +"</li>";
                                    } else {
                                        html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ startTime + "</li>";
                                    }

                                }else{
                                    var startTime = "";
                                    if (perValue.startTime) {
                                        startTime = perValue.startTime;
                                        startTime = startTime.substr(0, 10);
                                    }
                                    var endTime = "";
                                    if (perValue.endTime) {
                                        endTime = perValue.endTime;
                                        endTime = endTime.substr(0, 10);
                                        html += "<li>"+ startTime + "---" + endTime +"</li>";
                                    } else {
                                        html += "<li>"+ startTime + "</li>";
                                    }

                                }
                            });
                            html += "</ul>";
                            return html;
                        }
                    }
                },
                {field:'count',title:'票种数量',width:'5%',sortable: false, align:'center',
                    formatter: function(value, rowData, index) {
                        if(rowData.jszxOrderDetailList.length>0){
                            var html = "<ul>";
                            $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                if((rowData.jszxOrderDetailList.length-1) != i){
                                    var count = perValue.count;
                                    html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ count +"</li>";
                                }else{
                                    var count = perValue.count;
                                    html += "<li>"+ count +"</li>";
                                }
                            });
                            html += "</ul>";
                            return html;
                        }
                    }
                },
                {field:'totalPrice',title:'总价',width:'5%',sortable: false, align:'center'},
                {field:'actualPayPrice',title:'实际支出',width:'5%',sortable: false, align:'center'},
                {field:'status',title:'状态',width:'8%',
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
                {field:'contact',title:'联系人',width:'6%'},
                {field:'phone',title:'联系电话',width:'8%'},
                {field:'supplierName',title:'供应商',width:'10%'},
                {field:'companyName',title:'经销商',width:'10%'},
                {field:'userAccount',title:'下单帐号',width:'8%',sortable: false},
                {field:'createTime',title:'下单日期',width:'10%',
                    formatter: function(value, rowData, index) {
                        if (value) {
                            value = value.substr(0, 16);
                        }
                        return value;
                    }
                }

            ]],
            toolbar: '#client_cruiseship_tool'
        });
    },
    doCruiseshipSearch: function() {
        $('#client_cruiseship_dg').datagrid('load', {
            'jszxOrder.product.name' : $("#client_cruiseship_proName").textbox("getValue"),
            'jszxOrder.contact' : $("#client_cruiseship_contact").textbox("getValue"),
            'jszxOrder.user.account' : $("#client_cruiseship_createby").textbox("getValue"),
            'jszxOrder.orderNo' : $("#client_cruiseship_orderNo").textbox("getValue"),
            'jszxOrder.phone' : $("#client_cruiseship_phone").textbox("getValue"),
            'jszxOrder.status' : $("#client_cruiseship_status").combobox("getValue"),
            'startCreateTimeStr' : $("#start_cruiseship_createTime").datebox("getValue"),
            'endCreateTimeStr' : $("#end_cruiseship_createTime").datebox("getValue"),
            'startUseTimeStr':$("#start_cruiseship_useTime").datebox("getValue"),
            'endUseTimeStr':$("#end_cruiseship_useTime").datebox("getValue"),
        });
    },
    clearCruiseshipForm: function() {
        $("#client_cruiseship_form").form("reset");
        $("#client_cruiseship_proName").textbox("setValue", "");
        $("#client_cruiseship_contact").textbox("setValue", "");
        $("#client_cruiseship_phone").textbox("setValue", "");
        $("#client_cruiseship_createby").textbox("setValue", "");
        $("#client_cruiseship_orderNo").textbox("setValue", "");
        $("#client_cruiseship_status").combobox("setValue", "");
        $("#start_cruiseship_createTime").datebox("setValue", "");
        $("#end_cruiseship_createTime").datebox("setValue", "");
        $("#start_cruiseship_useTime").datebox("setValue", "");
        $("#end_cruiseship_useTime").datebox("setValue", "");
        $('#client_cruiseship_dg').datagrid('load', {});
    },
    openCruiseshipDetail: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/jszxLineOrder/cruiseshipOrderDetail.jhtml?outOrderId="+id;
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
    }
};
$(function () {
    ClientCruiseShipOrderManage.init();
});