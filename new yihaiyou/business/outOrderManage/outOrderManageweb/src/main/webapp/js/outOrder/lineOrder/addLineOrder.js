/**
 * Created by dy on 2016/2/23.
 */
var AddLineOrder = {
    init: function() {
        AddLineOrder.initCombox();
        AddLineOrder.initLineTypeList();


    },



    initCombox: function() {


        var porId = $("#ipt_line").val();

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
        var lineType = $("#hipt_lineType").val();
        //'city','around','china'
        if (lineType == 'city') {
            $("#la_lineType").html("厦门游");
        }
        if (lineType == 'around') {
            $("#la_lineType").html("周边游");
        }
        if (lineType == 'china') {
            $("#la_lineType").html("国内游");
        }


        var goway = $("#hipt_goway").val();

        if (goway != "bus" && goway != "car") {
            $("#idcard_div").show();
            $("#ipt_idCard").textbox({
                required: true
            });
        } else {
            $("#idcard_div").hide();
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


    initLineTypeList: function() {

        $("#dg_ticketList").datagrid({
            url:'/outOrder/jszxLineOrder/getLineTypeList.jhtml?lineId='+$("#ipt_line").val(),
            //pagination: true,
            //pageList: [5, 10, 20],
            //rownumbers: true,
            title:"线路价格列表（<span style='color: red;'>请勾选需要的价格类型并编辑使用时间和数量</span>）",
            fitColumns: true,
            fit: true,
            selectOnCheck:false,
            checkOnSelect:false,
            singleSelect: true,
            columns:[[
                {field:'id',checkbox:true, width:15,sortable: false},
                {field:'name',title:"线路类型", width:100,sortable: false},
                {field:'type',title:'类型',width:100,sortable: false,
                    formatter: function (value, rowData, index) {
                        if (value == "adult") {
                            return "成人";
                        }
                        if (value == "child") {
                            return "儿童";
                        }
                    }
                },
                {field:'price',title:'分销价',width:100,sortable: false,
                    formatter: function(value, rowData, index) {
                        var spanValue = '<span id="price_'+ rowData.type +'_'+ rowData.id +'">' +value + '</span>';
                        var spanRebate = '<input type="hidden" id="rebate_'+ rowData.type +'_'+ rowData.id +'" value="">';
                        return spanValue + spanRebate;
                    }
                },
                {field:'startTime',title:'使用时间',width:110,sortable: false,

                    formatter: function(value, rowData, index) {
                        var hipt = '<input type="text" class="calender_class"  readonly="readonly" id="startTime_'+ rowData.type +'_'+ rowData.id +'" onclick="AddLineOrder.onclickStartTime('+ index +','+ rowData.id +')">';
                        return hipt;
                    }
                },
                {field:'count',title:'数量',width:100,sortable: false,

                    formatter: function(value, rowData, index) {
                        var hipt = '<input type="text" class="count_class" id="count_'+ rowData.type +'_'+ rowData.id +'" onkeyup="AddLineOrder.getChangeValue('+ index +','+ rowData.id +')" temp-value="0">';

                        return hipt;

                    }
                }
            ]],

            onCheck: function(rowIndex, rowData) {

                $("#startTime_"+rowData.type+"_"+rowData.id+"").show();
                $("#count_"+rowData.type+"_"+rowData.id+"").show();


                var startTime = $("#startTime_adult_"+rowData.id+"").val();

                if (rowData.type == "child") {

                    if (startTime) {
                        $("#startTime_"+rowData.type+"_"+rowData.id+"").val(startTime);
                    }
                }

            },

            onUncheck: function(rowIndex, rowData) {

                $("#startTime_"+rowData.type+"_"+rowData.id+"").val("");
                $("#startTime_"+rowData.type+"_"+rowData.id+"").hide();


                var count = $("#count_"+ rowData.type +"_"+ rowData.id +"").attr("temp-value");

                var totalPrice = $("#ipt_totalPrice").numberbox("getValue");
                var price = $("#price_"+ rowData.type +"_"+ rowData.id +"").val();

                price = parseFloat(price);
                count = parseInt(count);
                totalPrice = parseFloat(totalPrice);
                if (totalPrice) {
                    totalPrice = totalPrice - (count*price);
                    $("#ipt_totalPrice").numberbox("setValue", totalPrice);
                } else {
                    totalPrice = 0;
                    $("#ipt_totalPrice").numberbox("setValue", "");
                }



                $("#count_"+rowData.type+"_"+rowData.id+"").val("");
                $("#count_"+rowData.type+"_"+rowData.id+"").hide();
                $("#count_"+ rowData.type +"_"+ rowData.id +"").attr("temp-value", 0);

            },
            onLoadSuccess: function() {
                AddLineOrder.mergeGridColCells($(this), "name");
            }

        });



    },


    onclickStartTime: function(index, id) {

        $('#dg_ticketList').datagrid('selectRow', index);
        var rowData = $('#dg_ticketList').datagrid('getSelected');
        AddLineOrder.selectStartTime(index, rowData.type);

    },

    getChangeValue: function(index, id) {


        $("#dg_ticketList").datagrid("selectRow", index);
        var row = $("#dg_ticketList").datagrid("getSelected");

        var type = "";
        if (row.type == "adult") {
            type = "adult";
        } else {
            type = "child";
        }

        var input_value = $("#count_"+ type +"_"+ id +"").val();
        var oldV = $("#count_"+ type +"_"+ id +"").attr("temp-value");



        var startTime = $("#startTime_"+ type +"_"+ id +"").val();
        var price = $("#price_"+ type +"_"+ id +"").html();


        var totalPrice = $("#ipt_totalPrice").numberbox("getValue");
        if (startTime) {

            if (AddLineOrder.checkRate(input_value)) {

                $("#count_"+ type +"_"+ id +"").attr("temp-value", input_value);

                price = parseFloat(price);
                var newV = parseFloat(input_value);
                oldV = parseFloat(oldV);

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
                $("#count_"+ type +"_"+ id +"").attr("temp-value", 0);
                $("#count_"+ type +"_"+ id +"").val("");
                $("#count_"+ type +"_"+ id +"").focus();
            }
        } else {
            $("#count_"+ type +"_"+ id +"").val("");
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

    selectStartTime: function(index, type) {
        //
        $("#dg_ticketList").datagrid("selectRow", index);

        var row = $("#dg_ticketList").datagrid("getSelected");

        //var rowIndex = $("#dg_ticketList").datagrid(row);
        //
        var ifr = $("#sel_startTime").children()[0];
        var url = "/outOrder/jszxLineOrder/selectDatePrice.jhtml?typePriceId=" + row.id + "&index=" + index +"&type="+ type + "&lineId=" + $("#ipt_line").val();
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




        var isValid = $("#editForm").form('validate');

        var orderDetailList = [];

        var orderDetailListStr = "";

        var rowsAll = $("#dg_ticketList").datagrid("getChecked");

        if (rowsAll.length <= 0) {
            isValid = false;
            show_msg("请选择需要的价格类型，并编辑使用日期和数量");
        }

        //var priceRows = rowsAll.rows;

        $.each(rowsAll, function(i, perValue) {

            var startTime = $("#startTime_"+perValue.type+"_"+perValue.id+"").val();
            var count = $("#count_"+perValue.type+"_"+perValue.id+"").val();
            var rebate = $("#rebate_"+perValue.type+"_"+perValue.id+"").val();
            var price = $("#price_"+perValue.type+"_"+perValue.id+"").html();

            count = Number(count);
            price = parseFloat(price);
            rebate = parseFloat(rebate);

            var salesPrice = rebate * count + price * count;

            if (startTime && count) {

                rebate = parseFloat(rebate);

                var orderDetailData = {
                    orderStartTime:startTime,
                    count:count,
                    type:perValue.type,
                    ticketName:perValue.name,
                    ticketPriceId:perValue.id,
                    salesPrice: salesPrice,
                    ticketPrice:price,
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

        console.log(orderDetailList);

        if (orderDetailList.length <= 0) {
            isValid = false;
            show_msg("请完善票号列表数量和使用日期！")
        }

        var orderId = $("#hipt_orderId").val();

        //if (orderId) {
        //
        //    AddLineOrder.confimOrder(orderId);
        //
        //    isValid = false;
        //
        //}

        var orderData = {
            'outOrderId':orderId,
            'jszxOrder.proType':$("#hipt_proType").val(),
            'jszxOrder.product.id':$("#ipt_line").val(),
            'jszxOrder.contact':$("#ipt_contact").textbox("getValue"),
            'jszxOrder.phone':$("#ipt_phone").textbox("getValue"),
            'jszxOrder.idcard':$("#ipt_idCard").textbox("getValue"),
            'jszxOrder.totalPrice':$("#ipt_totalPrice").textbox("getValue"),
            'jszxOrder.source':$("#ipt_source").textbox("getValue"),
            'jszxOrder.validDay':$("#hipt_validDay").val(),
            orderDetailListStr:orderDetailListStr
        }

        if (isValid) {
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            var url = "/outOrder/jszxLineOrder/saveLineOrder.jhtml";
            $.post(url, orderData,
                function(result){
                    if(result.success){
                        var orderId = result.orderId;
                        $("#hipt_orderId").val(orderId);
                        $.messager.progress("close");
                        show_msg("订单保存成功！");
                        var isconfim = $("#hipt_lineConfim").val();
                        if (isconfim == "confirm") {
                            AddLineOrder.confimOrder(orderId);
                        } else {
                            AddLineOrder.noConfimOrder(orderId);
                        }
                        //AddLineOrder.saveOrderDetail(orderDetailListStr, orderId);
                    }else{
                        $.messager.progress("close");
                        show_msg("保存失败！");
                        //var money = result.money;
                        //
                        //$.messager.confirm('余额不足', '还需充值：￥'+ money +'，您确定要充值吗？', function(r){
                        //    if (r){
                        //        AddLineOrder.onpenRechargeDialog(money);
                        //    } else {
                        //        show_msg("您已取消充值操作！");
                        //    }
                        //});
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

    saveOrderDetail: function(orderDetailListStr, orderId) {

        var flag = false;
        var data = {
            orderDetailListStr:orderDetailListStr
        }


        var url = "/outOrder/jszxLineOrder/saveOrderDetail.jhtml?orderId=" + orderId;
        $.post(url, data,
            function(result){
                if(result.success){
                    flag = true;
                    $.messager.progress("close");
                    show_msg("订单保存成功！");

                    var isconfim = $("#hipt_lineConfim").val();

                    if (isconfim == "confirm") {
                        AddLineOrder.confimOrder(orderId);
                    } else {
                        AddLineOrder.noConfimOrder(orderId);
                    }

                }else{
                    flag = false;
                    return flag;
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                }
            }
        );

    },

        payOrder: function(orderId) {

            var url = "/outOrder/jszxLineOrder/payOutOrder.jhtml";
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            $.post(url, {outOrderId:orderId},
                function(result){
                    $.messager.progress("close");
                    //show_msg("订单提交成功，供应商确认后会自动扣除余额！");
                    $.messager.alert('订单提交成功','供应商确认后会自动扣除余额！','info');
                    window.parent.$("#editPanel").dialog("close");
                    window.parent.location.reload();

                    //window.parent.location.href = "/outOrder/outOrder/manage.jhtml";
                }
            );

        },

    confimOrder: function(id) {




        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/jszxLineOrder/confimOrder.jhtml?outOrderId=" + id ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '确认订单并提交',
            width: 600,
            height: 450,
            left:'25%',
            top:50,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'提交订单',
                handler:function(){
                    AddLineOrder.payOrder(id);
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
                                AddLineOrder.onpenRechargeDialog(rechargePrice);
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

    noConfimOrder: function(id) {
        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/jszxLineOrder/confimOrder.jhtml?outOrderId=" + id ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '确认订单并支付',
            width: 600,
            height: 450,
            left:'25%',
            top:50,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'支付订单',
                handler:function(){
                    AddLineOrder.payOrder(id);
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
                                AddLineOrder.onpenRechargeDialog(rechargePrice);
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


    },

    mergeGridColCells: function(grid, rowFildName) {
        var rows=grid.datagrid('getRows' );
        var startIndex=0;
        var endIndex=0;
        if(rows.length< 1)
        {
            return;
        }
        $.each(rows, function(i,row){
            if(row[rowFildName]==rows[startIndex][rowFildName])
            {
                endIndex=i;
            }
            else
            {
                grid.datagrid( 'mergeCells',{
                    index: startIndex,
                    field: rowFildName,
                    rowspan: endIndex -startIndex+1
                });
                startIndex=i;
                endIndex=i;
            }

        });
        grid.datagrid( 'mergeCells',{
            index: startIndex,
            field: rowFildName,
            rowspan: endIndex -startIndex+1
        });
    }











}

$(function(){
    AddLineOrder.init();

    $("#dg_ticketList").focusout(function() {
        $("#dg_ticketList").datagrid('endEdit');
    });

    $(".datagrid-btable").focusout(function() {
        var rows = $("#dg_ticketList").datagrid("getRows");
        $("#dg_ticketList").datagrid('acceptChanges');

    });

})

