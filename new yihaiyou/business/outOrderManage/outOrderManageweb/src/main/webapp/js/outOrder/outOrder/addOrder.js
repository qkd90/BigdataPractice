/**
 * Created by dy on 2016/2/23.
 */
var AddOutOrder = {
    init: function() {
        AddOutOrder.initCombox();
        //AddOutOrder.initDataGridEdit();
        AddOutOrder.initTicketList();
    },



    initCombox: function() {
        $("#cbb_productName").combobox("setValue", $("#hd_productId").val());
        $("#cbb_productName").combobox("setText", $("#hd_productName").val());

        $("#cbb_productName").combobox({
            onSelect: function (record) {

                var porId = record.id;

                $("#hipt_preOrderDay").val(record.preOrderDay);
                $("#hipt_validDay").val(record.validOrderDay);

                var url = '/outOrder/outOrder/getProduct.jhtml';

                var data = {
                    proId : porId,
                    outOrderId:$("#ipt_id").val()
                }
                $.post(url,
                    data,
                    function(result) {
                    if (result.success) {


                        //$("#ipt_beginDate").datetimebox("setValue", result.startTime);
                        //$("#ipt_endDate").datetimebox("setValue", result.endTime);
                        url = '/outOrder/outOrder/getProList.jhtml?proId='+porId;
                        $("#dg_ticketList").datagrid({url:url});

                    }
                });
            }
        });


        $("#com_orderType").combobox({
            onSelect: function(rec){
                $("#cbb_productName").combobox("clear");
                $("#cbb_productName").combobox("setValue", "");
            }
        });

        $('#dialog_addTicketNo').dialog('close')

        $("#ipt_beginDate").focus(function() {

            //onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"

            var preOrderDay = $("#hipt_preOrderDay").val();

            if (preOrderDay) {
                WdatePicker({minDate:'%y-%M-{%d+'+preOrderDay+'}'});
                $("#validSpan").html($("#hipt_validDay").val() + "天内有效！");
            } else {
                show_msg("请选择产品！");
            }


        });

    },

    backParent: function() {
        var outOrderId = $("#ipt_id").val();
        if (outOrderId) {
            var url = "/outOrder/outOrder/deleteTempOutOrder.jhtml";

            var data = {
                outOrderId: outOrderId
            }

            $.post(url, data,
                function(result){
                    if (result.success) {
                        window.parent.$("#editPanel").dialog("close");
                    }
                }
            );
        }
    },

    initDataGridEdit: function() {

        var rows = $("#dg_ticketList").datagrid('getChecked');

        $.each(rows, function(i, perValue){
            var index = $("#dg_ticketList").datagrid('getRowIndex',perValue);
            var countEdt = $("#dg_ticketList").datagrid('getEditor', {index:index,field:'count'});
            // 数量  绑定 离开事件
            $(countEdt.target).bind("blur",function(){
                alert("aa");       // 根据 数量或单价变更后计算 金额
            });
        });

    },

    initTicketList: function() {

        $("#dg_ticketList").datagrid({
            //url:'/outOrder/outOrder/getOutTicketList.jhtml?outOrderId='+$("#ipt_id").val(),
            pagination: true,
            pageList: [5, 10, 20],
            //rownumbers: true,
            fitColumns: true,
            fit: true,
            //singleSelect: true,
            columns:[[
                {field:'id',checkbox:true, width:15,sortable: true},
                {field:'name',title:"门票名称", width:100,sortable: true},
                {field:'type',title:'类型',width:100,sortable: true,
                    formatter: function (value, rowData, index) {
                        if (value == "adult") {
                            return "成人票";
                        }
                        if (value == "student") {
                            return "学生票";
                        }
                        if (value == "child") {
                            return "儿童票";
                        }
                        if (value == "oldman") {
                            return "老人票";
                        }
                        if (value == "team") {
                            return "团体票";
                        }
                        if (value == "taopiao") {
                            return "套票";
                        }
                    }
                },
                {field:'discountPrice',title:'分销价',width:100,sortable: true},
                {field:'count',title:'数量',width:100,sortable: true,
                    editor:{
                        type : "numberbox",
                        options : {
                            required : true
                        }
                    }
                }
            ]],
            toolbar: '#tool_addTicketNo',
            onCheck: function(rowIndex,rowData) {
                $("#dg_ticketList").datagrid('beginEdit', rowIndex);



                //if (rowData.orderKnow) {
                //    show_msg(rowData.orderKnow);
                //}
            },
            onUncheck: function(rowIndex, rowData) {



                var data = {
                    outOrderIdStr:$("#ipt_id").val(),
                    tickePriceId:rowData.id
                }
                var url = "/outOrder/outOrder/delOutOrderTicket.jhtml";
                $.post(url, data,
                    function(result){
                       /* if (!result.success) {
                            show_msg("添加失败请重新添加");
                        }*/
                    }
                );

                $("#dg_ticketList").datagrid('cancelEdit', rowIndex);

                if($("#ipt_totalPrice").textbox("getValue")) {

                    var totalPrice = parseInt($("#ipt_totalPrice").textbox("getValue"));

                    totalPrice = totalPrice-(rowData.count * rowData.discountPrice);

                    $("#ipt_totalPrice").textbox("setValue", totalPrice);


                }

                $('#dg_ticketList').datagrid('updateRow',{
                    index: rowIndex,
                    row: {
                        count: ''
                    }
                });
            },
            onCheckAll: function(rows) {
                $.each(rows, function(i, perValue){

                    $("#dg_ticketList").datagrid('beginEdit', i);

                });
            },
            onUncheckAll: function(rows) {
                $.each(rows, function(i, perValue){

                    var data = {
                        outOrderIdStr:$("#ipt_id").val(),
                        tickePriceId:perValue.id
                    }
                    var url = "/outOrder/outOrder/delOutOrderTicket.jhtml";
                    $.post(url, data,
                        function(result){
                            //if (!result.success) {
                            //    show_msg("添加失败请重新添加");
                            //}
                        }
                    );

                    $("#dg_ticketList").datagrid('cancelEdit', i);

                    $("#ipt_totalPrice").textbox("setValue",""); //清除总价

                    $('#dg_ticketList').datagrid('updateRow',{
                        index: i,
                        row: {
                            count: ''
                        }
                    });
                });
            }
        });

    },

    openAddTicketNo: function() {

        var rows = $("#dg_ticketList").datagrid("getChecked");

        var totalPrice = 0;

        var ticketIds = "";

        $.each(rows, function(i, perValue){
            var index = $("#dg_ticketList").datagrid("getRowIndex",perValue);
            $("#dg_ticketList").datagrid('endEdit', index);
            for (var j = 0; j<perValue.count; j++) {
                ticketIds = ticketIds + perValue.id + ",";
            }
            totalPrice += parseInt(perValue.discountPrice*perValue.count);
        });

        if (ticketIds.length > 0) {
            ticketIds = ticketIds.substr(0, ticketIds.length-1);
        }

        var data = {
            outOrderIdStr:$("#ipt_id").val(),
            tickePriceIds:ticketIds
        }
        var url = "/outOrder/outOrder/saveOutTicket.jhtml";
        $.post(url, data,
            function(result){
                if (result.success) {
                    //show_msg("添加失败请重新添加");
                }
            }
        );
        $("#ipt_totalPrice").textbox("setValue", totalPrice);
    },

    saveTicketNo: function() {

        $('#dialog_ticketNoForm').form('submit', {
            url : "/outOrder/outOrder/saveOutTicket.jhtml",
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if($(this).form('validate')){
                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });
                } else {
                    //show_msg("请完善当前页面数据");
                }
                return isValid;
            },
            success : function(result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if(result.success==true){
                    $("#dg_ticketList").datagrid("reload");
                    $('#dialog_addTicketNo').dialog('close');
                    $('#dialog_ticketNoForm').form("reset");
                }else{
                    show_msg("保存失败");
                }
            }
        });
    },

    openEditTicketNo: function() {

        var rows = $("#dg_ticketList").datagrid("getChecked");

        if (rows.length == 1) {
            $('#dialog_addTicketNo').dialog({
                title: '编辑票号',
                width: 350,
                height: 250,
                closed: false,
                cache: false,
                modal: true
            });
            $('#dialog_ticketNoForm').form('load', rows[0]);
            $("#ipt_ticketUseStatus").combobox("setValue", rows[0].useStatus);
            $("#ipt_hidden_outOrderId").val(rows[0].outOrder.id);
            $("#ipt_outOrderTickeId").val(rows[0].id);
            $("#ipt_ticketOutTime").datetimebox("setValue", rows[0].outTime);
            $('#dialog_addTicketNo').dialog('open');
        } else if (rows.length > 1) {
            show_msg("只能选择一条数据！")
        } else {
            show_msg("请选择要编辑的一条数据！")
        }

    },

    cancelTicketNo: function() {
        $('#dialog_ticketNoForm').form("reset");
        $('#dialog_addTicketNo').dialog('close');
    },

    openDelTicketNo: function() {


        var rows = $("#dg_ticketList").datagrid("getChecked");

        if (rows.length >= 1) {

            var ids = "";

            $.each(rows, function(i, perValue){
                ids = ids + perValue.id + ",";
            });

            if (ids.length > 0 ) {
                ids = ids.substr(0, ids.length - 1);
            }

            var url = "/outOrder/outOrder/delOutOrderTicket.jhtml";

            var data = {
                ids: ids
            }

            $.post(url, data,
                function(result){
                    if (result.success) {
                        $("#dg_ticketList").datagrid("reload");
                    }
                }
            );



        } else {
            show_msg("请选择要删除的数据！");
        }

    },


    saveOutOrder: function() {

        var data = {
            id:$("#ipt_id").val(),
            proType:$("#com_orderType").combobox("getValue"),
            pruductId:$("#cbb_productName").combobox("getValue"),
            contact:$("#ipt_contact").textbox("getValue"),
            phone:$("#ipt_phone").textbox("getValue"),
            totalPrice:$("#ipt_totalPrice").textbox("getValue"),
            source:$("#ipt_source").textbox("getValue"),
            validDay:$("#hipt_validDay").val(),
            startTimeStr:$("#ipt_beginDate").val()
        }


        var isValid = $(editForm).form('validate');

        var resultFlag = false;

        var beginDate = $("#ipt_beginDate").val();

        if (beginDate) {
            resultFlag = true;
        } else {
            isValid = false;
            show_msg("请完事使用日期！")
        }


        if (isValid) {
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            var url = "/outOrder/outOrder/saveOutOrder.jhtml";

            $.post(url, data,
                function(result){

                    if(result.success){
                        show_msg(result.errorMsg);
                        //支付订单
                        url = "/outOrder/outOrder/payOutOrder.jhtml";

                        var priceData = {

                            totalPrice : $("#ipt_totalPrice").textbox("getValue"),
                            outOrderId:$("#ipt_id").val()
                        };
                        $.post(url, priceData,
                            function(result){
                                $.messager.progress("close");
                                $.messager.alert('支付提示','订单支付成功！','info');
                                window.parent.$("#editPanel").dialog("close");
                                window.parent.location.reload();
                                //window.parent.$('#dg_outOrder').datagrid("reload");
                            }
                        );

                    }else{
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                    }
                }
            );








        } else {
            show_msg("请完善当前页面数据");
        }

    },


    cancelOutOrder: function() {

        var outOrderId = $("#ipt_id").val();

        if (outOrderId) {
            var url = "/outOrder/outOrder/deleteOutOrder.jhtml";

            var data = {
                outOrderId: outOrderId
            }

            $.post(url, data,
                function(result){
                    if (result.success) {
                        $("#dg_ticketList").datagrid("reload");
                    }
                }
            );
        } else {
            window.parent.$("#editPanel").dialog("close");
        }


    },

    productLoader: function(param, success, error) {
        var q = param.q || '';
        if (q.length <= 1) {
            return false
        }

        var type = $("#com_orderType").combobox("getValue");


        if (type == "scenic") {
            var url = "/outOrder/outOrder/getTicketList.jhtml";
            AddOutOrder.searchTicketAjax(q, url, success, error, type);
        } else {

        }
    },


    searchTicketAjax: function(q, url, success, error, type) {

        $.ajax({
            url : url,
            dataType : 'json',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            method : 'POST',
            data : {
                featureClass : "P",
                style : "full",
                maxRows : 20,
                orderType : type,
                name : q
            },
            success : function(data) {


                var items = $.map(data, function(item) {
                    return {
                        id : item.id,
                        name : item.name,
                        validOrderDay: item.validOrderDay,
                        preOrderDay: item.preOrderDay
                    };
                });
                success(items);
            },
            error : function() {
                error.apply(this, arguments);
            }
        });

    }



}

$(function(){
    AddOutOrder.init();
})