/**
 * Created by dy on 2016/2/23.
 */
var AddTicketOrder = {
    init: function() {
        AddTicketOrder.initCombox();
        //AddOutOrder.initDataGridEdit();
        //AddTicketOrder.initCanlender();
        AddTicketOrder.initTicketList();
    },



    initCombox: function() {


        var porId = $("#ipt_ticket").val();

        var url = '/outOrder/outOrder/getProList.jhtml?proId='+porId;
        $("#dg_ticketList").datagrid({url:url});

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
        var ticketType = $("#hipt_ticketType").val();
        if (ticketType == 'scenic') {
            $("#la_ticketType").html("景点门票");
        }
        if (ticketType == 'shows') {
            $("#la_ticketType").html("演唱会门票");
        }
        if (ticketType == 'boat') {
            $("#la_ticketType").html("船票");
        }


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


    initTicketList: function() {

        $("#dg_ticketList").datagrid({
            //url:'/outOrder/outOrder/getOutTicketList.jhtml?outOrderId='+$("#ipt_id").val(),
            pagination: true,
            pageList: [5, 10, 20],
            title:"门票票种（<span style='color: red;'>请勾选需要的票种并编辑使用时间和数量</span>）",
            //rownumbers: true,
            fitColumns: true,
            fit: true,
            selectOnCheck:false,
            checkOnSelect:false,
            singleSelect: true,
            columns:[[
                {field:'id',checkbox:true, width:15,sortable: false},
                {field:'name',title:"门票名称", width:120,sortable: false},
                {field:'type',title:'类型',width:100,sortable: false,
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
                {field:'discountPrice',title:'分销价',width:100,sortable: false,
                    formatter: function(value, rowData, index) {
                        var spanValue = '<span id="price_'+ rowData.id +'">' +value + '</span>';
                        var spanRebate = '<input type="hidden" id="rebate_'+ rowData.id +'" value="">';
                        return spanValue + spanRebate;
                    }
                },
                {field:'startTime',title:'使用时间',width:110,sortable: false,
                    formatter: function(value, rowData, index) {
                        var hipt = '<input type="text" class="calender_class"  readonly="readonly" id="startTime_'+ rowData.id +'" onclick="AddTicketOrder.onclickStartTime('+ index +','+ rowData.id +')">';
                        return hipt;
                    }
                },
                {field:'count',title:'数量',width:100,sortable: false,

                    formatter: function(value, rowData, index) {

                        var hipt = '<input type="text" class="count_class" id="count_i'+rowData.id+'" onkeyup="AddTicketOrder.getChangeValue('+index+','+rowData.id+')" style="width: 105px;height: 22px;" temp-value="0">';

                        return hipt;

                    }
                }
            ]],

            onCheck: function(rowIndex, rowData) {

                $("#startTime_"+rowData.id+"").show();
                $("#count_i"+rowData.id+"").show();


                var startTime = $("#startTime_"+rowData.id+"").val();

                if (rowData.type == "child") {

                    if (startTime) {
                        $("#startTime_"+rowData.id+"").val(startTime);
                    }
                }

            },

            onUncheck: function(rowIndex, rowData) {

                $("#startTime_"+rowData.id+"").val("");
                $("#startTime_"+rowData.id+"").hide();


                var count = $("#count_i"+ rowData.id +"").attr("temp-value");

                var totalPrice = $("#ipt_totalPrice").numberbox("getValue");
                var price = $("#price_"+ rowData.id +"").val();

                price = parseFloat(price);
                count = parseInt(count);
                totalPrice = parseFloat(totalPrice);
                //totalPrice = Number(totalPrice);
                if (totalPrice) {
                    totalPrice = totalPrice - (count*price);
                    $("#ipt_totalPrice").numberbox("setValue", totalPrice);
                } else {
                    totalPrice = 0;
                    $("#ipt_totalPrice").numberbox("setValue", "");
                }

                $("#count_i"+rowData.id+"").val("");
                $("#count_i"+rowData.id+"").hide();
                $("#count_i"+ rowData.id +"").attr("temp-value", 0);

            }


        });

    },

    onclickStartTime: function(index, id) {

        $('#dg_ticketList').datagrid('selectRow', index);
        //var rowData = $('#dg_ticketList').datagrid('getSelected');
        AddTicketOrder.selectStartTime(index);

    },

    getChangeValue: function(index, id) {

        var input_value = $("#count_i"+id+"").val();
        var oldV = $("#count_i"+id+"").attr("temp-value");

        $("#dg_ticketList").datagrid("selectRow", index);
        var row = $("#dg_ticketList").datagrid("getSelected");

        var startTime = $("#startTime_"+id+"").val();
        var price = $("#price_"+id+"").html();


        var totalPrice = $("#ipt_totalPrice").numberbox("getValue");
        if (startTime) {

            if (AddTicketOrder.checkRate(input_value)) {

                    $("#count_i"+id+"").attr("temp-value", input_value);
                        price = parseFloat(price);
                        //price = Number(price);
                        var newV = parseFloat(input_value);
                        oldV = Number(oldV);
                    if (totalPrice) {
                        totalPrice = totalPrice - price * oldV;
                        totalPrice = totalPrice + price * newV;
                        $("#ipt_totalPrice").numberbox("setValue", totalPrice);
                    } else {
                        totalPrice = 0;
                        totalPrice =  newV * price;
                        $("#ipt_totalPrice").numberbox("setValue", totalPrice);
                    }

            } else {
                totalPrice = totalPrice - price * oldV;
                $("#ipt_totalPrice").numberbox("setValue", totalPrice);
                $("#count_i"+id+"").attr("temp-value", 0);
                $("#count_i"+id+"").val("");
                $("#count_i"+id+"").focus();
            }
        } else {
            $("#count_i"+id+"").val("");
            show_msg("请完善使用日期");
        }

    },

    //判断正整数
    checkRate: function(inputStr) {
        var re = /^[1-9]+[0-9]*]*$/;
        if (!re.test(inputStr)) {
            //show_msg("请输入正整数");
            return false;
        } else {
            return true;
        }
    },

    selectStartTime: function(index) {
        //
        var row = $("#dg_ticketList").datagrid("getSelected");
        //var rowIndex = $("#dg_ticketList").datagrid(row);
        //
        var ifr = $("#sel_startTime").children()[0];
        var url = "/outOrder/outOrder/selectDatePrice.jhtml?ticketPriceId=" + row.id + "&index=" + index + "&ticketId=" + $("#ipt_ticket").val();
        $(ifr).attr("src", url);
        $("#sel_startTime").dialog({
            title: '选择日历价格',
            width: 350,
            height: 400,
            closed: false,
            cache: false,
            modal: true
        });
        $("#sel_startTime").dialog("open");
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


    saveOutOrder: function() {

        var isValid = $(editForm).form('validate');

        var orderDetailList = [];

        var orderDetailListStr = "";

        var rowsAll = $("#dg_ticketList").datagrid("getChecked");

        if (rowsAll.length <= 0) {
            isValid = false;
            show_msg("请选择需要的价格类型，并编辑使用日期和数量");
        }

        $.each(rowsAll, function(i, perValue) {

            var count = $("#count_i"+perValue.id+"").val();
            var startTime = $("#startTime_"+perValue.id+"").val();
            var price = $("#price_"+perValue.id+"").html();
            var rebate = $("#rebate_"+perValue.id+"").val();

            count = Number(count);
            price = parseFloat(price);
            rebate = parseFloat(rebate);

            var salesPrice = rebate * count + price * count;

            if (startTime && count) {

                var orderDetailData = {
                    orderStartTime:startTime,
                    count:count,
                    ticketPriceId:perValue.id,
                    ticketPrice:price,
                    salesPrice:salesPrice,
                    validay:$("#hipt_validDay").val()
                };
                var strObj = JSON.stringify(orderDetailData);
                orderDetailListStr = orderDetailListStr + strObj + ";";
                orderDetailList.push(orderDetailData);
            } else {
                show_msg("请完善数量");
            }

        });

        if (orderDetailListStr.length > 0 ) {
            orderDetailListStr = orderDetailListStr.substr(0, orderDetailListStr.length - 1);
        }

        //console.log(orderDetailList);

        if (orderDetailList.length <= 0) {
            isValid = false;
            show_msg("请完善票号列表数量和使用日期！")
        }

        var orderId = $("#hipt_orderId").val();

        //if (orderId) {
        //
        //    AddTicketOrder.noconfimOrder(orderId);
        //
        //    isValid = false;
        //}

        var orderData = {
            outOrderId:orderId,
            proType:$("#hipt_proType").val(),
            ticketId:$("#ipt_ticket").val(),
            contact:$("#ipt_contact").textbox("getValue"),
            phone:$("#ipt_phone").textbox("getValue"),
            totalPrice:$("#ipt_totalPrice").textbox("getValue"),
            source:$("#ipt_source").textbox("getValue"),
            validDay:$("#hipt_validDay").val(),
            orderDetailListStr:orderDetailListStr
        }


        if (isValid) {
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            var url = "/outOrder/outOrder/saveOutOrder.jhtml";
            $.post(url, orderData,
                function(result){
                    var orderId = result.orderId;
                    if(result.success){

                        $("#hipt_orderId").val(orderId);
                        var confirm = $("#hipt_ticketConfim").val();
                        $.messager.progress("close");
                        show_msg("订单保存成功！");
                        AddTicketOrder.noconfimOrder(orderId);
                    }else{
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                    }
                }
            );
        } else if(!orderId) {
            show_msg("请完善当前页面数据");
        }

    },

    onpenRechargeDialog: function(money) {

        //var orderId = $("#ipt_id").val();

        var ifr = $("#editPanel").children()[0];
        var url = "/balance/balance/createOrder.jhtml?money=" + money ;
        $(ifr).attr("src", url);

        $("#editPanel").dialog(
            {
                title: '充值余额',
                width: 700,
                //fit:true,
                height: 400,
                closed: false,
                cache: false,
                modal: true
            }
        );
        $("#editPanel").dialog("open");

    },


    payOrder: function(orderId) {

        var url = "/outOrder/outOrder/payOutOrder.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post(url, {outOrderId:orderId},
            function(result){
                $.messager.progress("close");
                $.messager.alert('支付提示','订单支付成功！','info');
                window.parent.$("#editPanel").dialog("close");
                window.parent.location.reload();
                show_msg("订单详情请请前往直销订单查看！")
                //window.parent.location.href = "/outOrder/outOrder/manage.jhtml";
            }
        );

    },

    noconfimOrder: function(id) {


        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/outOrder/confimOrder.jhtml?outOrderId=" + id ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '确认订单并支付',
            width: 600,
            height: 450,
            resizable:true,
            left:'25%',
            top:50,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确认支付',
                handler:function(){
                    AddTicketOrder.payOrder(id);
                }
            },{
                text:'取消支付',
                handler:function(){
                    $("#confim_order").dialog("close");
                }
            }]

        });


        var url = "/outOrder/outOrder/checkUserBalance.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'检测余额中,请耐心等待...'
        });
        $.post(url, {outOrderId:id},
            function(result){
                $.messager.progress("close");
                if (result.success) {
                    $("#confim_order").dialog("open");
                } else {
                    var rechargePrice = result.rechargePrice;
                    if (rechargePrice) {
                        $.messager.confirm('余额不足', result.errorMsg, function(r){
                            if (r){
                                AddTicketOrder.onpenRechargeDialog(rechargePrice);
                            } else {
                                show_msg("您已取消充值操作！");
                            }
                        });
                    } else {
                        show_msg(result.errorMsg);
                    }
                }
            }
        );




    },

    confimOrder: function(id) {

        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/outOrder/confimOrder.jhtml?outOrderId=" + id ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '该订单需要供应商确认，请提交',
            width: 600,
            height: 450,
            left:'25%',
            top:50,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确认提交',
                handler:function(){
                    AddTicketOrder.payOrder(id);
                }
            },{
                text:'取消提交',
                handler:function(){
                    $("#confim_order").dialog("close");
                }
            }]

        });

        var url = "/outOrder/outOrder/checkUserBalance.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'检测余额中,请耐心等待...'
        });
        $.post(url, {outOrderId:id},
            function(result){
                $.messager.progress("close");
                if (result.success) {
                    $("#confim_order").dialog("open");
                } else {
                    var rechargePrice = result.rechargePrice;
                    if (rechargePrice) {
                        $.messager.confirm('余额不足', result.errorMsg, function(r){
                            if (r){
                                AddTicketOrder.onpenRechargeDialog(rechargePrice);
                            } else {
                                show_msg("您已取消充值操作！");
                            }
                        });
                    } else {
                        show_msg(result.errorMsg);
                    }
                }
            }
        );



    },


    cancelOutOrder: function() {

        var outOrderId = $("#hipt_orderId").val();

        if (outOrderId) {

            var url = "/outOrder/outOrder/deleteOutOrder.jhtml";
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
        } else {
            window.parent.$("#editPanel").dialog("close");
        }


    }










}

$(function(){
    AddTicketOrder.init();

    $("#dg_ticketList").focusout(function() {
        $("#dg_ticketList").datagrid('endEdit');
    });

    $(".datagrid-btable").focusout(function() {
        var rows = $("#dg_ticketList").datagrid("getRows");
        $("#dg_ticketList").datagrid('acceptChanges');

    });

})

