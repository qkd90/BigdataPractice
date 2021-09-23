/**
 * Created by dy on 2016/2/23.
 */
var OutOrderManage = {
    init: function() {
        OutOrderManage.initJsp();
        OutOrderManage.initDataGrid();
        OutOrderManage.initTrimTicket();
        OutOrderManage.initTrimLine();
    },
    initJsp: function() {

        $('#activity_tab').tabs({
            onSelect:function(title, index){
                if (index == 0) {
                    OutOrderManage.initDataGrid();
                } else if (index == 1) {
                    OutOrderManage.initLineDataGrid();
                }

            }
        });
    },

    initTrimTicket: function() {

        $('#ipt_orderNo').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_orderNo").textbox("setValue", _trim);
            }
        });

        $('#ipt_proName').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_proName").textbox("setValue", _trim);
            }
        });
        $('#ticket_createby').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ticket_createby").textbox("setValue", _trim);
            }
        });

        $('#com_status').combobox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#com_status").combobox("setValue", _trim);
            }
        });
        $('#com_useStatus').combobox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#com_useStatus").combobox("setValue", _trim);
            }
        });
        $('#start_ticket_createTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#start_ticket_createTime").datebox("setValue", _trim);
            }
        });
        $('#end_ticket_createTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#end_ticket_createTime").datebox("setValue", _trim);
            }
        });

        $('#ipt_contact').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_contact").textbox("setValue", _trim);
            }
        });
        $('#ipt_phone').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_phone").textbox("setValue", _trim);
            }
        });

    },
    initTrimLine: function() {

        $('#ipt_line_orderNo').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_line_orderNo").textbox("setValue", _trim);
            }
        });

        $('#ipt_line_proName').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_line_proName").textbox("setValue", _trim);
            }
        });
        $('#line_createby').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#line_createby").textbox("setValue", _trim);
            }
        });

        $('#com_line_status').combobox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#com_line_status").combobox("setValue", _trim);
            }
        });
        $('#com_line_useStatus').combobox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#com_line_useStatus").combobox("setValue", _trim);
            }
        });
        $('#start_line_createTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#start_line_createTime").datebox("setValue", _trim);
            }
        });
        $('#end_line_createTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#end_line_createTime").datebox("setValue", _trim);
            }
        });

        $('#ipt_line_contact').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_line_contact").textbox("setValue", _trim);
            }
        });
        $('#ipt_line_phone').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#ipt_line_phone").textbox("setValue", _trim);
            }
        });

        $('#start_line_useTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#start_line_useTime").datebox("setValue", _trim);
            }
        });
        $('#end_line_useTime').datebox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#end_line_useTime").datebox("setValue", _trim);
            }
        });

    },

    downloadLineExcel: function() {

        var url = "/outOrder/outOrder/loadLineExcel.jhtml";

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        var data = {
            'jszxOrder.proName':$("#ipt_line_proName").textbox("getValue"),
            'jszxOrder.contact':$("#ipt_line_contact").textbox("getValue"),
            'jszxOrder.user.account':$("#line_createby").textbox("getValue"),
            'jszxOrder.orderNo':$("#ipt_line_orderNo").textbox("getValue"),
            'jszxOrder.phone':$("#ipt_line_phone").textbox("getValue"),
            'jszxOrder.status':$("#com_line_status").combobox("getValue"),
            startCreateTimeStr:$("#start_line_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_line_createTime").datebox("getValue"),
            startUseTimeStr:$("#start_line_useTime").datebox("getValue"),
            endUseTimeStr:$("#end_line_useTime").datebox("getValue"),
            'jszxOrder.detailUseStatus':$("#com_line_useStatus").combobox("getValue")
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

    downloadTicketExcel: function() {

        var url = "/outOrder/outOrder/loadTicketExcel.jhtml";

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        var data = {
            'jszxOrder.proName':$("#ipt_proName").textbox("getValue"),
            'jszxOrder.contact':$("#ipt_contact").textbox("getValue"),
            'jszxOrder.user.account':$("#ticket_createby").textbox("getValue"),
            'jszxOrder.orderNo':$("#ipt_orderNo").textbox("getValue"),
            'jszxOrder.phone':$("#ipt_phone").textbox("getValue"),
            'jszxOrder.status':$("#com_status").combobox("getValue"),
            startCreateTimeStr:$("#start_ticket_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_ticket_createTime").datebox("getValue"),
            'jszxOrder.detailUseStatus':$("#com_useStatus").combobox("getValue")
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

    loadTicketValidateExcel:function() {

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

                        var startTimeStr=$("#startTime").val();
                        var endTimeStr=$("#endTime").val();

                        var data = {
                            startTimeStr:$("#startTime").val(),
                            endTimeStr:$("#endTime").val()
                        };

                        if (startTimeStr && endTimeStr) {
                            var url = '/scemanager/scemanager/testExcel.jhtml';
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
    },


    clearTicketSearchForm: function() {
        $("#searchForm_activity").form("reset");
        var data = {
            proName:$("#ipt_proName").textbox("getValue"),
            contact:$("#ipt_contact").textbox("getValue"),
            createbyName:$("#ticket_createby").textbox("getValue"),
            orderNo:$("#ipt_orderNo").textbox("getValue"),
            phone:$("#ipt_phone").textbox("getValue"),
            statusStr:$("#com_status").combobox("getValue"),
            startCreateTimeStr:$("#start_ticket_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_ticket_createTime").datebox("getValue"),
            useStatusStr:$("#com_useStatus").combobox("getValue")
        }
        $("#dg_outOrder").datagrid("reload", data);
    },

    clearLineSearchForm: function() {
        $("#searchForm_line").form("reset");
        var data = {
            proName:$("#ipt_line_proName").textbox("getValue"),
            contact:$("#ipt_line_contact").textbox("getValue"),
            phone:$("#ipt_line_phone").textbox("getValue"),
            orderNo:$("#ipt_line_orderNo").textbox("getValue"),
            createbyName:$("#line_createby").textbox("getValue"),
            statusStr:$("#com_line_status").combobox("getValue"),
            startCreateTimeStr:$("#start_line_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_line_createTime").datebox("getValue"),
            startUseTimeStr:$("#start_line_useTime").datebox("getValue"),
            endUseTimeStr:$("#end_line_useTime").datebox("getValue"),
            useStatusStr:$("#com_line_useStatus").combobox("getValue")
        }

        $("#dg_line").datagrid("reload", data);
    },
    doTicketSearch: function() {
        var data = {
            proName:$("#ipt_proName").textbox("getValue"),
            contact:$("#ipt_contact").textbox("getValue"),
            orderNo:$("#ipt_orderNo").textbox("getValue"),
            createbyName:$("#ticket_createby").textbox("getValue"),
            phone:$("#ipt_phone").textbox("getValue"),
            statusStr:$("#com_status").combobox("getValue"),
            startCreateTimeStr:$("#start_ticket_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_ticket_createTime").datebox("getValue"),
            useStatusStr:$("#com_useStatus").combobox("getValue")
        }

        $("#dg_outOrder").datagrid("load", data);
    },

    doLineSearch: function() {
        var data = {
            proName:$("#ipt_line_proName").textbox("getValue"),
            contact:$("#ipt_line_contact").textbox("getValue"),
            orderNo:$("#ipt_line_orderNo").textbox("getValue"),
            createbyName:$("#line_createby").textbox("getValue"),
            phone:$("#ipt_line_phone").textbox("getValue"),
            statusStr:$("#com_line_status").combobox("getValue"),
            startCreateTimeStr:$("#start_line_createTime").datebox("getValue"),
            endCreateTimeStr:$("#end_line_createTime").datebox("getValue"),
            startUseTimeStr:$("#start_line_useTime").datebox("getValue"),
            endUseTimeStr:$("#end_line_useTime").datebox("getValue"),
            useStatusStr:$("#com_line_useStatus").combobox("getValue")
        }

        $("#dg_line").datagrid("load", data);
    },


    initLineDataGrid: function() {
        $("#dg_line").datagrid({
            url:'/outOrder/outOrder/getJszxOrderLineList.jhtml?typeStr=' + "line",
            pagination: true,
            pageList: [10, 20, 30],
            singleSelect:true,
            border:false,
            rownumbers: true,
            fitColumns: true,
            fit: true,
            columns:[[
                //{field:'id',checkbox:true, width:15,sortable: false},
                {field:'orderNo',title:"订单编号", width:'10%',sortable: false},
                {field:'proName',title:'产品名称',width:'16%',sortable: false},
                {field:'proType',title:'线路价格类型',width:'10%',sortable: false,
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
                {field:'msgCount',title:'出发时间',width:'6%',sortable: false,
                    formatter: function(value, rowData, index) {
                        if(rowData.jszxOrderDetailList.length>0){
                            var html = "<ul>";
                            $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                var startTime = perValue.startTime;

                                startTime = startTime.substr(0, 10);

                                if((rowData.jszxOrderDetailList.length-1) != i){
                                    html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ startTime +"</li>";
                                }else{
                                    html += "<li>"+ startTime +"</li>";
                                }
                            });
                            html += "</ul>";
                            return html;
                        }
                    }
                },
                {field:'btnStatus',title:'出游状态',width:'12%',sortable: false,
                    formatter: function(value, rowData, index) {
                        if (rowData.status == 'UNPAY') {
                            return "待付款";
                        } else if (rowData.status == 'WAITING') {
                            return "待确认";
                        } else {
                            if(rowData.jszxOrderDetailList.length>0){
                                var html = "<ul>";
                                $.each(rowData.jszxOrderDetailList,function(i,perValue){
                                    var playStatus = perValue.detailStatus;

                                    if((rowData.jszxOrderDetailList.length-1) != i){
                                        html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ playStatus +"</li>";
                                    }else{
                                        html += "<li>"+ playStatus +"</li>";
                                    }
                                });
                                html += "</ul>";
                                return html;
                            }
                        }
                    }
                },
                {field:'totalPrice',title:'总价',width:'5%',sortable: false},
                {field:'actualPayPrice',title:'实际支出',width:'5%',sortable: false, align:'center'},
                {field:'status',title:'状态',width:'8%',sortable: false,
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
                {field:'contact',title:'联系人',width:'6%',sortable: false},
                {field:'phone',title:'联系电话',width:'8%',sortable: false},
                {field:'createTime',title:'订单日期',width:'10%',sortable: false,
                    formatter: function(value, rowData, index) {
                        value = value.substr(0, 16);
                        return value;
                    }
                },
                {field:'userAccount',title:'下单帐号',width:'8%',sortable: false},
                {field:'supplierName',title:'供应商',width:'10%',sortable: false},
                {field:'opt',title:'操作',width:'6%',sortable: false,align:'center',
                    formatter: function(value, rowData, index) {
                        return  '<a class="easyui-linkbutton" style="color:blue;" onclick="OutOrderManage.checkLineDetail('+rowData.id+')">查看</a>'
                    }
                }
            ]],
            toolbar: '#line-tool',
            onBeforeLoad : function(data) {   // 查询参数
                data.proName=$("#ipt_line_proName").textbox("getValue");
                data.contact=$("#ipt_line_contact").textbox("getValue");
                data.orderNo=$("#ipt_line_orderNo").textbox("getValue");
                data.createbyName=$("#line_createby").textbox("getValue");
                data.phone=$("#ipt_line_phone").textbox("getValue");
                data.statusStr=$("#com_line_status").combobox("getValue");
                data.startCreateTimeStr=$("#start_line_createTime").datebox("getValue");
                data.endCreateTimeStr=$("#end_line_createTime").datebox("getValue");
                data.startUseTimeStr=$("#start_line_useTime").datebox("getValue");
                data.endUseTimeStr=$("#end_line_useTime").datebox("getValue");
                data.useStatusStr=$("#com_line_useStatus").combobox("getValue");
            }
        });

        $("#editPanel").dialog("close");
    },

    initDataGrid: function() {
        $("#dg_outOrder").datagrid({
            url:'/outOrder/outOrder/getJszxOrderTicketList.jhtml?typeStr=' + "scenic",
            pagination: true,
            pageList: [10, 20, 30],
            singleSelect:true,
            rownumbers: true,
            border:false,
            fitColumns: true,
            fit: true,
            columns:[[
                //{field:'id',checkbox:true, width:15,sortable: false},
                {field:'orderNo',title:"订单编号", width:'10%',sortable: false},
                {field:'proName',title:'产品名称',width:'9%',sortable: false},
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
                {field:'status',title:'状态',width:'5%',sortable: false, align:'center',
                    formatter: function(value, rowData, index) {
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
                {field:'contact',title:'联系人',width:'6%',sortable: false},
                {field:'phone',title:'联系电话',width:'8%',sortable: false},
                {field:'createTime',title:'下单日期',width:'10%',sortable: false,
                    formatter: function(value, rowData, index) {
                        value = value.substr(0, 16);
                        return value;
                    }
                },
                {field:'userAccount',title:'下单帐号',width:'8%',sortable: false},
                {field:'opt',title:'操作',width:'8%',sortable: false, align:'center',
                    formatter: function(value, rowData, index) {

                        var checkHtml = '<a class="easyui-linkbutton" style="color:blue;" onclick="OutOrderManage.checkDetail('+rowData.id+')">查看</a>';
                        var valiHtml = '<a class="easyui-linkbutton" style="color:blue;" onclick="OutOrderManage.checkValidateDetail('+ index +','+rowData.id +')">验票详情</a>';
                        var space = '&nbsp;&nbsp;'


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
                                return checkHtml;
                            } else {
                                return checkHtml + space + valiHtml;
                            }
                        } else {
                            return checkHtml;
                        }


                    }
                }
            ]],
            toolbar: '#ticket-tool',
            onBeforeLoad : function(data) {   // 查询参数
                data.proName=$("#ipt_proName").textbox("getValue");
                data.contact=$("#ipt_contact").textbox("getValue");
                data.orderNo=$("#ipt_orderNo").textbox("getValue");
                data.createbyName=$("#ticket_createby").textbox("getValue");
                data.phone=$("#ipt_phone").textbox("getValue");
                data.statusStr=$("#com_status").combobox("getValue");
                data.startCreateTimeStr=$("#start_ticket_createTime").datebox("getValue");
                data.endCreateTimeStr=$("#end_ticket_createTime").datebox("getValue");
                data.useStatusStr=$("#com_useStatus").combobox("getValue");
            }
        });

        $("#editPanel").dialog("close");
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
                $("#dg_outOrder").datagrid("selectRow", index);
                var row = $("#dg_outOrder").datagrid("getSelected");

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

    checkDetail: function(id) {

        var ifr = $("#editPanel1").children()[0];
        var url = "/outOrder/outOrder/checkDetail.jhtml?outOrderId="+id;
        $(ifr).attr("src", url);
        $(ifr).css("height", "1000px");
        $("#editPanel1").dialog({
            title: '订单详情',
            width: 400,
            height: 400,
            closed: false,
            cache: false,
            closable:false,
            modal: true
        });
        $("#editPanel1").dialog("open");
    },
    checkLineDetail: function(id) {

        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/jszxLineOrder/checkLineDetail.jhtml?outOrderId="+id;
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
}

$(function(){
    OutOrderManage.init();
})
