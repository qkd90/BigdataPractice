/**
 * Created by dy on 2016/10/12.
 */
var TicketOrderIndex = {
    init: function () {
        TicketOrderIndex.initClientTicket_dg();
    },

    initClientTicket_dg: function() {
        var url = "/outOrder/outOrder/getClientOrderList.jhtml?type=scenic";
        $('#client_ticket_dg').datagrid({
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
                {field:'proName',title:'产品名称',width:'16%'},
                {field:'proType',title:'门票价格类型',width:'10%',sortable: false,
                    formatter: function(value, rowData, index) {
                        if(rowData.jszxOrderDetailList.length>0){
                            var html = "<ul>";
                            $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                if((rowData.jszxOrderDetailList.length-1) != i){
                                    html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ perValue.ticketName +"</li>";
                                }else{
                                    html += "<li>"+ perValue.ticketName +"</li>";
                                }
                            });
                            html += "</ul>";
                            return html;
                        }
                    }
                },
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
                                    }
                                    html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ startTime + "---"+ endTime +"</li>";
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
                                    }
                                    html += "<li>"+ startTime + "---" + endTime +"</li>";
                                }
                            });
                            html += "</ul>";
                            return html;
                        }
                    }
                },
                {field:'detailStatus',title:'使用状态',width:'12%',sortable: false,
                    formatter: function(value, rowData, index) {
                        if (rowData.status == 'UNPAY') {
                            return '待支付';
                        } else if (rowData.status == 'WAITING') {
                            return '待确认';
                        } else {
                            if(rowData.jszxOrderDetailList.length>0){
                                var html = "<ul>";
                                $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                    if((rowData.jszxOrderDetailList.length-1) != i){
                                        var useStatusStr = perValue.detailStatus;
                                        html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ useStatusStr +"</li>";
                                    }else{
                                        var useStatusStr = perValue.detailStatus;
                                        html += "<li>"+ useStatusStr +"</li>";
                                    }
                                });
                                html += "</ul>";
                                return html;
                            }
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
                },
                {field:'opt',title:'操作',width:'8%', align:'center',
                    formatter: function(value, rowData, index) {
                        var space = "&nbsp;&nbsp;";
                        //var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ClientOrderManage.openLineOrderDetail('+rowData.id+')">订单确认</a>';
                        var btn_confuse = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketOrderIndex.confuseLineOrder('+rowData.id+')">拒绝</a>';
                        var btn_check = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketOrderIndex.checkClientTicketOrder('+rowData.id+')">查看</a>';
                        var valiHtml = '<a class="easyui-linkbutton" style="color:blue;" onclick="TicketOrderIndex.checkValidateDetail('+ index +','+rowData.id +')">验票详情</a>';
                        var initCount = 0;
                        var refundCount =0;
                        var restCount =0;
                        var novalidCount = 0;
                        if(rowData.jszxOrderDetailList.length>0){
                            $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                initCount = perValue.count;  //3
                                if (initCount) {
                                    initCount = Number(initCount);
                                }
                                refundCount = perValue.refundCount;  //1
                                if (refundCount) {
                                    refundCount = Number(refundCount);
                                }
                                restCount = perValue.restCount;      //2
                                if (restCount) {
                                    restCount = Number(restCount);
                                }
                                novalidCount += initCount - restCount - refundCount;
                            });
                        }
                        //console.log("novalidCount:" + novalidCount);
                        if ( novalidCount > 0 ) {
                            if (rowData.status == "UNPAY") {
                                return btn_check;
                            } else {
                                return btn_check + space + valiHtml;
                            }
                        } else {
                            return btn_check;
                        }
                    }
                }
            ]],
            toolbar: '#client_ticket_tool'
        });
    },
    doTicketSearch: function() {
        $('#client_ticket_dg').datagrid('load', {
            'jszxOrder.product.name' : $("#client_ticket_proName").textbox("getValue"),
            'jszxOrder.contact' : $("#client_ticket_contact").textbox("getValue"),
            'jszxOrder.user.account' : $("#client_ticket_createby").textbox("getValue"),
            'jszxOrder.orderNo' : $("#client_ticket_orderNo").textbox("getValue"),
            'jszxOrder.phone' : $("#client_ticket_phone").textbox("getValue"),
            'jszxOrder.status' : $("#client_ticket_status").combobox("getValue"),
            'startCreateTimeStr' : $("#start_ticket_createTime").datebox("getValue"),
            'endCreateTimeStr' : $("#end_ticket_createTime").datebox("getValue"),
            'jszxOrder.detailUseStatus' : $("#client_ticket_useStatus").combobox("getValue")
        });
    },
    clearTicketForm: function() {
        $("#client_ticket_form").form("reset");
        $("#client_ticket_proName").textbox("setValue", "");
        $("#client_ticket_contact").textbox("setValue", "");
        $("#client_ticket_createby").textbox("setValue", ""),
            $("#client_ticket_orderNo").textbox("setValue", ""),
            $("#client_ticket_phone").textbox("setValue", "");
        $("#client_ticket_status").combobox("setValue","");
        $("#start_ticket_createTime").datebox("setValue","");
        $("#end_ticket_createTime").datebox("setValue","");
        $("#client_ticket_useStatus").combobox("setValue" ,"");
        $('#client_ticket_dg').datagrid('load', {
        });
    },
    downloadTicketSuplierExcel:function() {
        var url = "/outOrder/outOrder/loadClientTicketExcel.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        var data = {
            'jszxOrder.proName':$("#client_ticket_proName").textbox("getValue"),
            'jszxOrder.contact':$("#client_ticket_contact").textbox("getValue"),
            'jszxOrder.user.account':$("#client_ticket_createby").textbox("getValue"),
            'jszxOrder.orderNo':$("#client_ticket_orderNo").textbox("getValue"),
            'jszxOrder.phone':$("#client_ticket_phone").textbox("getValue"),
            'jszxOrder.status':$("#client_ticket_status").combobox("getValue"),
            startCreateTimeStr:$("#start_ticket_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_ticket_createTime").datebox("getValue"),
            'jszxOrder.detailUseStatus':$("#client_ticket_useStatus").combobox("getValue")
        }
        $.post(url, data, function(result) {
            if (result.success) {
                $.messager.progress("close");
                window.location = "/static"+result.downloadUrl;
                show_msg(result.info);
            } else {
                $.messager.progress("close");
                show_msg(result.info);
            }
        });
    },
    confuseLineOrder: function(id) {
        var url = "/outOrder/jszxLineOrder/confuseLineOrder.jhtml?outOrderId="+id;
        $.post(url, function(result){
            if (result.success) {
                show_msg(result.errorMsg);
                $('#client_line_dg').datagrid('reload');
            } else {
                show_msg(result.errorMsg);
                $('#client_line_dg').datagrid('reload');
            }
        });
    },
    checkClientTicketOrder: function(id) {
        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/outOrder/checkClientTicketDetail.jhtml?outOrderId="+id;
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
    checkValidateDetail: function(index, id) {
        var ifr = $("#editPanel2").children()[0];
        $("#editPanel2").dialog({
            width: 600,
            height: 400,
            top:10,
            left:'25%',
            closed: false,
            cache: false,
            modal: true,
            onBeforeOpen:function() {
                $("#client_ticket_dg").datagrid("selectRow", index);
                var row = $("#client_ticket_dg").datagrid("getSelected");
                $("#proName").html(row.proName);
                var url = '/outOrder/outOrder/getTicketValidataInfo.jhtml?outOrderId=' + id;
                $("#dg_validateInfo").datagrid({
                    url:url,
                    title:'门票验证详情列表',
                    singleSelect:true,
                    rownumbers: true,
                    border:false,
                    fitColumns: true,
                    columns:[[
                        {field:'productName',title:'价格类型名称',width:150},
                        {field:'validateCount',title:'数量',width:30, align:'center'},
                        {field:'validateTime',title:'验证时间',width:100},
                        {field:'validateByName',title:'操作人',width:70}
                    ]]
                });
            }
        });
        $("#editPanel2").dialog("open");
    },
    loadTicketValidateSuplierExcel:function() {
        $("#editPanel3").dialog({
            width: 270,
            height: 170,
            top:'center',
            left:'center',
            closed: false,
            cache: false,
            modal: true,
            onBeforeClose:function() {
                $("#startTime").val("");
                $("#endTime").val("");
            },
            buttons:[
                {
                    text:'确定导出',
                    handler:function(){
                        var startTimeStr=$("#client_startTime").val();
                        var endTimeStr=$("#client_endTime").val();
                        var data = {
                            startTimeStr:$("#client_startTime").val(),
                            endTimeStr:$("#client_endTime").val()
                        };
                        if (startTimeStr && endTimeStr) {
                            var url = '/scemanager/scemanager/testExcel.jhtml?isSuplier=Yes';
                            $.messager.progress({
                                title:'温馨提示',
                                text:'数据处理中,请勿关闭页面...'
                            });
                            $.post(url, data, function(result) {
                                    if (result.success) {
                                        window.location = "/static" + result.downloadPath; //执行下载操作
                                        show_msg(result.info);
                                        $.messager.progress("close");
                                        $("#editPanel3").dialog("close");
                                    } else {
                                        show_msg(result.info);
                                        $.messager.progress("close");
                                    }
                                }
                            );
                        } else {
                            show_msg("条件不能为空！");
                        }
                    }
                },
                {
                    text:'取消',
                    handler:function(){
                        $("#editPanel3").dialog("close");
                    }
                }
            ]
        });
        $("#editPanel3").dialog("open");
    }
}

$(function(){
    TicketOrderIndex.init();
})