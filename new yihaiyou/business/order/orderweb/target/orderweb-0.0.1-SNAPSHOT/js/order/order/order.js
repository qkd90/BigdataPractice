$(function () {
    //$.getJSON("/order/order/list.jhtml", {name: ""}, function(result) {
    //    console.log(result);
    //});
});


var Orders = {
    currentEditIndex: 0,
    table: $("#total-order-table"),
    productType: null,
    searcher: $("#total-order-searcher"),
    searcher_toolbar_name: "#total-order-searcher",
    confirmTr: '  <tr id="operation-confirm-panel">' +
    '<td width="80">确认订单：</td>' +
    '<td width="250">' +
    '<label><input name="confirm" type="radio" value="WAIT"/>已确认</label>' +
    '<label><input name="confirm" type="radio" value="INVALID"/>无效</label>' +
    '</td></tr>',
    descriptionTr: ' <tr id="operation-description-panel" style=";">   ' +
    ' <td></td>   ' +
    ' <td><textarea id="operation-description"></textarea>' +
    '</td></tr>',
    tailTr: '<tr>    <td width="80">提醒游客： </td><td width="250">未填写</td></tr>',
    init: function () {
        //初始化表格数据
        Orders.initDgList();
        //console.log(Orders.table.datagrid('options'));
        $(".main-wrap").find(".tabs").find("li").click(function () {
            console.log($(this).index());
            Orders.switchTable($(this).index());
        });
    },
    switchTable: function (index) {
        this.searcher = $(".order-table").eq(index).find("#total-order-searcher");
        if (index == 0) {
            this.table = $("#total-order-table");
            this.productType = null;
            this.searcher_toolbar_name = "#total-order-searcher";
            this.searcher = $("#total-order-searcher");
        } else if (index == 1) {
            this.table = $("#line-order-table");
            this.productType = "line";
            this.searcher_toolbar_name = "#line-order-searcher";
            this.searcher = $("#line-order-searcher");
        } else if (index == 2) {
            this.table = $("#scenic-order-table");
            this.productType = "scenic";
            this.searcher_toolbar_name = "#scenic-order-searcher";
            this.searcher = $("#scenic-order-searcher");
        } else if (index == 3) {
            this.table = $("#hotel-order-table");
            this.productType = "hotel";
            this.searcher_toolbar_name = "#hotel-order-searcher";
            this.searcher = $("#hotel-order-searcher");
        } else if (index == 4) {
            this.table = $("#restaurant-order-table");
            this.productType = "restaurant";
            this.searcher_toolbar_name = "#restaurant-order-searcher";
            this.searcher = $("#restaurant-order-searcher");
        }
        this.initDgList();
    },
    doSearch: function () {
        var searchType = this.searcher.find("#search-type").val();
        var searchForm = {};
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['orderForm.order.status'] = this.searcher.find("#search-status").val();
        searchForm['orderForm.orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderForm.asc'] = this.searcher.find("#search-sort-type").val();
        searchForm['orderForm.product.proType'] = this.productType;
        searchForm['orderForm.order.filterOrderTypes[0]'] = "recharge";
        searchForm['orderForm.order.filterOrderTypes[1]'] = "withdraw";
        this.table.datagrid('load', searchForm);
    },
    //初始化表格数据
    initDgList: function () {
        this.table.datagrid({
            url: '/order/order/list.jhtml',
            queryParams: {
                'orderForm.product.proType': this.productType,
                'orderForm.orderProperty': "createTime",
                'orderForm.asc': false,
                'orderForm.order.filterOrderTypes[0]': "recharge",
                'orderForm.order.filterOrderTypes[1]': "withdraw"
            },
            pagination: true,
            pageList: [10, 30, 50],
            rownumbers: false,
            fit:true,
            //fitColumns: true,
            singleSelect: false,
            striped: true,//斑马线
            ctrlSelect: true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                {field: 'id', title: '订单ID', width: 55, sortable: true},
                {field: 'orderNo', title: '订单号', width: '10%', sortable: true},
                {
                    field: 'OPT', title: '操作', width: '10%', sortable: true, formatter: function (value, row, index) {
                    var btn =  '<a href="#" onclick="Orders.openDetail(' + row.id + ',' + index + ')" class="easyui-linkbutton" >详情</a>';
                    btn +=  '&nbsp;|&nbsp;<a href="#" onclick="Orders.getLog(' + row.id + ',' + null + ')" class="easyui-linkbutton" >查看日志</a>';
                    return btn;
                }
                },
                {field: 'recName', title: '联系人', width: '10%', sortable: true},
                {field: 'mobile', title: '联系电话', width: '10%', sortable: true},
                {field: 'orderType', title: '订单类型', width: 80, formatter: function(value, rowData, rowIndex) {
                    return getOrderType(value);
                }},
                {
                    field: '1', title: '产品类型', width: '10%', sortable: true, formatter: function (value, row) {
                    var types = '';
                    for (var i = 0; i < row.orderDetails.length; i++) {
                        var detail = row.orderDetails[i];
                        if (types.indexOf(detail.productType) >= 0) {
                            continue;
                        }
                        if (types != "") {
                            types += "/";
                        }
                        types += getProductType(detail.productType);
                    }

                    return types;
                }
                },
                {
                    field: '2', title: '预订产品名称', width: '30%', sortable: true, formatter: function (value, row) {
                    if (row.orderDetails == null) {
                        return "无效产品";
                    }
                    var name = "";
                    for (var i = 0; i < row.orderDetails.length; i++) {
                        if (name != "") {
                            name += "/";
                        }
                        if (row.orderDetails[i].product != null) {
                            name += row.orderDetails[i].product.name == "" ? "其他" : row.orderDetails[i].product.name;
                        }
                    }
                    return name;
                }
                },
                {field: 'createTime', title: '下单时间', width: '10%', sortable: true},
                {
                    field: 'status', title: '订单状态', width: '10%', sortable: true, formatter: function (value) {
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
                        return '<span class="success" style="color: #126808">预定成功</span>';
                    } else if(value == 'FAILED') {
                        return '<span class="failed" style="color: #ff4c4d">预定失败</span>';
                    } else if(value == 'CANCELED') {
                        return '<span class=failed style="color: #d08a1d">已取消</span>';
                    } else if(value == 'CANCELING') {
                        return '<span class=failed style="color: #c0c0c0">取消中</span>';
                    } else if (value == 'PARTIAL_FAILED') {
                        return '<span class="failed" style="color: #f33">部分失败</span>'
                    } else if (value == 'DELETED') {
                        return '<span class="deleted" style="color: #706f6e">已删除</span>'
                    } else if (value == 'INVALID') {
                        return '<span class="deleted" style="color: #706f6e">无效订单</span>'
                    }  else if (value == 'REFUND') {
                        return '<span class="deleted" style="color: #706f6e">已退款</span>'
                    } else {
                        return '<span class="other">-</span>';
                    }

                    }
                }
            ]],
            toolbar: this.searcher_toolbar_name
        });
    },
    openDetail: function (id, index) {
        var detailPanel = $("#detail-panel");
        $.getJSON("/order/order/detail.jhtml", {id: id}, function (order) {
                if (order == "id_required") {
                    $.messager.alert('订单详情', '无法获取订单详情, 不正确的订单号', 'error');
                    return;
                } else if (order == "order_not_exists") {
                    $.messager.alert('订单详情', '无法获取订单详情, 订单不存在', 'error');
                    return;
                }
                detailPanel.dialog({
                    title: '订单编号：' + order.orderNo,
                    modal: true,
                    fit: true,
                    width: "100%",
                    top: "0",
                    left: "0",
                    onClose: function () {
                        Orders.clearForm();
                    }
                });
                var status = 0;
                if (order.status == 'WAIT') {
                    status = '等待支付';
                } else if (order.status == 'PAYED') {
                    status = '已支付';
                } else if (order.status == 'REFUND') {
                    status = '已退款';
                } else if (order.status == 'CLOSED') {
                    status = '已关闭'
                } else if (order.status == 'UNCONFIRMED') {
                    status = '未确认'
                } else if (order.status == 'DELETED') {
                    status = '已删除'
                } else if (order.status == 'FAILED') {
                    status = '预定失败';
                } else {
                    status = '无效';
                }
                $(".order-status").find(".orange").text(status);
                detailPanel.find(".order-time").text(order.createTime);
                detailPanel.find(".ip").text(order.ipAddr);
                detailPanel.find(".name").text(order.recName);
                detailPanel.find(".email").text(order.user.email);
                detailPanel.find(".mobile").text(order.mobile);
                detailPanel.find(".telephone").text(order.mobile);
                detailPanel.find(".addition-description").text(order.remark);
                detailPanel.find(".order-old").text(order.hasOld == true ? "是" : "否");
                detailPanel.find(".order-foreign").text(order.hasForeigner == true ? "是" : "否");
                // 发票信息
                var invoice = order.invoice;
                var table_invoice = $("#table_invoice").find("tbody");
                table_invoice.html("");
                var tr_invoice = $(document.createElement("tr"));
                if (invoice != null) {
                    tr_invoice.append("<td class='order-invoice-name'>" + invoice.name + "</td>");
                    tr_invoice.append("<td class='order-invoice-title'>" + invoice.title + "</td>");
                    tr_invoice.append("<td class='order-invoice-price'>" + order.price + "</td>");
                    tr_invoice.append("<td class='order-invoice-telephone'>" + invoice.telephone + "</td>");
                    tr_invoice.append("<td class='order-invoice-address'>" + invoice.address + "</td>");
                    table_invoice.append(tr_invoice);
                    $("#no_invoice").hide();
                    $("#table_invoice").show();
                } else {
                    $("#no_invoice").show();
                    $("#table_invoice").hide();
                }
                // 保险信息
                var insuranceList = order.orderInsurances;
                var table_insurance = $("#table_insurance").find("tbody");
                table_insurance.html("");
                if (insuranceList != null && insuranceList.length > 0) {
                    $.each(insuranceList, function(i, orderInsurance){
                        var tr_insurance = $(document.createElement("tr"));
                        tr_insurance.append("<td class='order-insurance-name'>" + orderInsurance.insurance.name + "</td>");
                        tr_insurance.append("<td class='order-insurance-category'>" + orderInsurance.insurance.category.name + "</td>");
                        tr_insurance.append("<td class='order-insurance-price'>" + orderInsurance.insurance.price + "</td>");
                        tr_insurance.append("<td class='order-insurance-company'>" + orderInsurance.insurance.company + "</td>");
                        table_insurance.append(tr_insurance);
                    });
                    $("#no_insurance").hide();
                    $("#table_insurance").show();
                } else {
                    $("#no_insurance").show();
                    $("#table_insurance").hide();
                }

                var table_a = $("#order-detail-table-a").find("tbody");
                var table_b = $("#order-detail-table-b").find("tbody");
                table_a.html("");
                table_b.html("");
                //var totalPrice = 0;
                for (var i = 0; i < order.orderDetails.length; i++) {
                    var detail = order.orderDetails[i];
                    var tr_a = $(document.createElement("tr"));
                    tr_a.append("<td class=''>" + detail.id + "</td>");
                    tr_a.append("<td class=''>" + getProductDetail(detail.product) + "</td>");
                    tr_a.append("<td class='pro-type'>" + getProductType(detail.productType) + "</td>");
                    tr_a.append("<td class='price-type'>" + detail.seatType + "</td>");
                    tr_a.append("<td class='play-date' id='playDate'>" + detail.playDate.split(" ")[0] + "</td>");
                    tr_a.append("<td class='price'>" + detail.unitPrice + "</td>");
                    tr_a.append("<td class='order-count'>" + detail.num + "</td>");
                    tr_a.append("<td class='single_room_price'>" + detail.singleRoomPrice + "</td>");
                    //table_a.append(tr_a);
                    //var tr_b = $(document.createElement("tr"));
                    tr_a.append("<td class='prom-discount'>" + detail.promDiscount + "</td>");
                    tr_a.append("<td class='other-discount'>" + "0" + "</td>");
                    // 对于已经支付的订单, 其子订单状态根据子订单自身状态决定
                    if (detail.status == "BOOKING") {
                        tr_a.append("<td class='detail-status' style='color: orange'>" + "预定中" + "</td>");
                    } else if (detail.status == "SUCCESS") {
                        tr_a.append("<td class='detail-status' style='color: darkgreen'>" + "预定成功" + "</td>");
                    } else if (detail.status == "WAITING") {
                        tr_a.append("<td class='detail-status' style='color: #f55'>" + "等待支付" + "</td>");
                    } else if (detail.status == "PAYED") {
                        tr_a.append("<td class='detail-status' style='color: #000000'>" + "已支付" + "</td>");
                    } else if (detail.status == "CANCELED") {
                        tr_a.append("<td class='detail-status' style='color: #706f6e'>" + "取消" + "</td>");
                    } else if (detail.status == "CANCELING") {
                        tr_a.append("<td class='detail-status' style='color: #ff4c4d'>" + "取消中" + "</td>");
                    } else if (detail.status == "FAILED") {
                        tr_a.append("<td class='detail-status' style='color: #f33'>" + "交易失败" + "</td>");
                    } else if(detail.status == "CHECKIN") {
                        tr_a.append("<td class='detail-status' style='color: #1b91ff'>" + "已入住" + "</td>");
                    } else if(detail.status == "CHECKOUT") {
                        tr_a.append("<td class='detail-status' style='color: #e200fd'>" + "已退房" + "</td>");
                    } else {
                        // 其他未知状态, 由母订单状态决定
                        tr_a.append("<td class='detail-status' style='color: #f55'>" + status + "</td>");
                    }
                    tr_a.append("<td class='detail-realOrderId'>" + getRealOrderId(detail) + "</td>");
                    tr_a.append("<td class='detail-apiResult'>" + getApiResult(detail) + "</td>");
                    if (order.isCombineLine && i == 0) {
                        tr_a.append("<td class='detail-realOrderId'>-</td>");
                    } else {
                        tr_a.append("<td class='detail-realOrderId' style='width: 80px;'>" + isAbleToCancel(order, detail) + "</td>");
                    }
                    tr_a.append("<td class='log'>" + Orders.openLog(order.id, detail.id)+"</td>");
                    table_a.append(tr_a);
                }
                detailPanel.find(".total-price").text(order.price + "元");

                var operationTable = $("#operation-table");
                operationTable.html("");

                if (order.status == "INVALID") {
                    operationTable.append(Orders.confirmTr);
                    operationTable.find("input[name='confirm'][value='INVALID']").attr("checked", true);
                    operationTable.find("input[name='confirm']").attr("disabled", "disabled");
                    operationTable.append(Orders.descriptionTr);
                    $("#operation-description").val(order.operationDesc).attr("disabled", "disabled");
                } else if (order.status == "WAIT") {
                    operationTable.append(Orders.confirmTr);
                    operationTable.find("input[name='confirm'][value='WAIT']").click(function () {
                        $("#operation-description-panel").remove();
                    });
                    operationTable.find("input[name='confirm'][value='WAIT']").attr("checked", true);
                } else if (order.status == "UNCONFIRMED") {
                    operationTable.append(Orders.confirmTr);
                    operationTable.find("input[name='confirm'][value='WAIT']").click(function () {
                        $("#operation-description-panel").remove();
                    });
                }
                operationTable.append(Orders.tailTr);
                operationTable.find("input[name='confirm'][value='INVALID']").click(function () {
                    $("#operation-confirm-panel").after(Orders.descriptionTr);
                });

                detailPanel.dialog("open");
            }
        );
        this.currentEditIndex = index;

    },
    updateStatus: function () {
        $.post("/order/order/updateStatus.jhtml", {
            "order.id": this.table.datagrid("getSelected").id,
            "confirmStatus": $("input[name='confirm']:checked").val(),
            "payStatus": $("input[name='pay']:checked").val(),
            "operationDesc": $("#operation-description").val()
        }, function (result) {
            if (result == 'success') {
                alert("保存成功");
                Orders.closeDetail();
                Orders.table.datagrid("reload");
            } else {
                alert(result);
            }
        });
    },
    closeDetail: function () {
        Orders.clearForm();
        $("#detail-panel").dialog("close");
    },
    clearForm: function () {
        $("#detail-panel").find("input,textarea").val("");
        $("#detail-panel").find("input[type='radio']").attr("checked", null);
    },
    openLog: function(orderId, orderDetailId) {
        return "<a href='javascript:;' onclick='Orders.getLog(" + orderId + "," + orderDetailId + ")'>查看日志</a>";
    },
    getLog: function(orderId, orderDetailId) {
        $.messager.progress({
            msg: '正在读取日志,请稍候...'
        });
        $("#logDg").datagrid({
            url: '/order/orderLog/getOrderLog.jhtml',
            fit: true,
            rownumbers:false,
            pagination:true,
            nowrap: false,
            pageList:[20,30,50],
            queryParams: {
                'orderIdStr': orderId,
                'orderDetailIdStr': orderDetailId
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 55,
                    align: 'center'
                },
                {
                    field: 'logContent',
                    title: '日志内容',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 'operator.userName',
                    title: '操作者',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'recordTime',
                    title: '记录时间',
                    width: 140,
                    align: 'center'
                }
            ]],
            onLoadSuccess: function(data) {
                $("#log-panel").dialog({
                    title: '查看订单日志',
                    top: 0,
                    modal: true
                });
                $.messager.progress('close');
                $("#log-panel").dialog("open");
            }
        });
    }
};

function isAbleToCancel(order, orderDetail) {
    var orderStatus = order.status;
    var orderDetailStatus = orderDetail.status;
    if (orderStatus == "WAIT" || orderStatus == "SUCCESS" || orderStatus == "CONFIRMED" ||
        orderStatus == "FAILED" || orderStatus == "PAYED" || orderStatus == "PARTIAL_FAILED" ||
        orderStatus == "UNCONFIRMED" || orderStatus == "PROCESSED") {
        if (orderDetailStatus == "WAITING" || orderDetailStatus == "PAYED" ||
            orderDetailStatus == "FAILED" || orderDetailStatus == "SUCCESS" ||
            orderDetailStatus == null || orderDetailStatus == "") {
            if (orderDetail.product != null && orderDetail.product.source != 'LXB' && orderDetail.product.source != "") {
                var cancelBtn =  "<a href='javascript:;' onclick='cancelOrderDetail(" + orderDetail.id + ")'>退订(T)</a>";
                var editBtn = "";
                if (orderDetail.productType == "scenic") {
                    editBtn =  "<a href='javascript:;' onclick='editOrderDetail(" + order.id + "," + orderDetail.id + ")'>修改(T)</a>";
                }
                //return cancelBtn + "<br/>" + editBtn;
                return cancelBtn;
            } else if (orderDetail.product == null || orderDetail.product.source == 'LXB' || orderDetail.product.source == "") {
                var cancelBtn =  "<a href='javascript:;' onclick='cancelOutOrderDetail(" + order.id + "," + orderDetail.id + ")'>退订(G)</a>";
                var editInfoBtn = "";
                var editDateBtn = "";
                if (orderDetail.productType == "scenic") {
                    editInfoBtn =  "<a href='javascript:;' onclick='editOutOrderDetailInfo(" + order.id + "," + orderDetail.id + ")'>修改信息(G)</a>";
                    editDateBtn =  "<a href='javascript:;' onclick='editOutOrderDetailDate(" + order.id + "," + orderDetail.id + ")'>修改日期(G)</a>";
                }
                return cancelBtn + "<br/>" + editInfoBtn + "<br/>" + editDateBtn;
                //return cancelBtn;
            }
        } else {
            if (orderDetailStatus == "BOOKING") {
                return "预订中,不可退";
            } else if (orderDetailStatus == "CANCELING") {
                return "取消中,不可退";
            } else if (orderDetailStatus == "CANCELED") {
                return "已取消,不可退";
            } else if (orderDetailStatus == "CHECKIN") {
                return "已办理入住,不可退";
            } else if (orderDetailStatus == "CHECKOUT") {
                return "已办理退房,不可退";
            } else {
                return "未知状态,不可退";
            }
        }
    } else {
        if (orderStatus == "REFUND") {
            return "订单已退款";
        } else if (orderStatus == "CLOSED") {
            return "订单已关闭";
        } else if (orderStatus == "INVALID") {
            return "无效订单";
        } else if (orderStatus == "PROCESSING") {
            return "预订中,不可退";

        } else if (orderStatus == "DELETED") {
            return "已删除,不可退";
        } else {
            return "未知状态,不可退";
        }

    }
//<a href='javascript:;' onclick='cancelOrderDetail(" + detail.id + ",\"" + detail.realOrderId + "\"," + "\"" + order.status + "\"," + "\"" + detail.status + "\"" + ")'>退订</a>
}

function editOrderDetail(orderId, orderDetailId) {
// useless
}
function checkResendMsg() {
    var newMobile = $('#edit_info_form').find('input[name = "mobile"]').val();
    var oldMobile = $('#edit_info_form').find('input[name = "origin_mobile"]').val();
    if (newMobile == oldMobile) {
        $.messager.alert('修改订单', "订单联系人电话没有变化, 不能重发短信!", 'warning');
        $('#edit_info_form').find('input[name = "reSendMsg"]').attr("checked", false);
    }
}
function editOutOrderDetailInfo(orderId, orderDetailId) {
    $.messager.progress({
        title: '修改订单',
        msg: '正在获取订单信息, 请稍候...'
    });

    $.ajax({
        url: "/outOrder/outOrderEdit/getEditTicketInfo.jhtml",
        type: 'post',
        dataType: 'json',
        data: {
            orderDetailId: orderDetailId
        },
        success: function(result) {
            if (result.success) {
                var order = result.order;
                var orderDetail = result.orderDetail;
                $("#edit_info_form").form('load', {
                    orderId: orderId,
                    orderDetailId: orderDetailId,
                    recName: result.order.recName,
                    mobile: result.order.mobile,
                    origin_mobile: result.order.mobile
                });
                var touristHtml = "";
                $.each(orderDetail.orderTouristList, function(i, orderTourist) {
                    touristHtml = "";
                    touristHtml += "<li id='tourist_" + orderTourist.id + "'>";
                    touristHtml += "<span>"+ (i + 1) + ": </span>"
                    touristHtml += "<input type='hidden' name='orderTouristVos[" + i + "].id' value='" + orderTourist.id + "'>";
                    touristHtml += "<input type='text' name='orderTouristVos[" + i + "].name' value='" + orderTourist.name + "'>";
                    touristHtml += "<input type='text' name='orderTouristVos[" + i + "].tel' value='" + orderTourist.tel + "'>";
                    touristHtml += "<input type='text' name='orderTouristVos[" + i + "].idNumber' value='" + orderTourist.idNumber + "'>";
                    touristHtml += "</li>"
                    $('#order_tourist_info').append(touristHtml);
                    //
                    $('#edit_info_panel').dialog({
                        title:'修改订单',
                        modal:true,
                        onClose:function(){
                            $('#edit_info_form').form('clear');
                            $('#order_tourist_info').empty();
                            $('#resend_msg_la').hide();
                        }
                    });
                    if (orderDetail.realOrderId == null || orderDetail.realOrderId == "") {
                        $('#resend_msg_la').hide();
                    } else {
                        $('#resend_msg_la').show();
                    }
                    $('#edit_info_panel').dialog('open');
                    $.messager.progress('close');
                });
            } else {
                $.messager.progress('close');
                $.messager.alert('修改订单', result.msg, 'error');
            }
        },
        error: function(result) {
            $.messager.progress('close');
            $.messager.alert('修改订单', "操作失败,请重试!", 'error');
        }
    });
}

function editOutOrderDetailDate(orderId, orderDetailId) {
    // 先打开对话框, 提供给fullcalendar渲染日历控件
    $('#edit_date_form').form('load', {
        orderId: orderId,
        orderDetailId: orderDetailId
    });
    $('#edit_date_panel').dialog({
        title:'修改订单',
        modal:true,
        onClose:function(){
            $('#edit_date_form').form('clear');
            $('#showDate').html(null);
        }
    });
    $('#edit_date_panel').dialog('open');
    $.messager.progress({
        title: '修改订单',
        msg: '正在获取门票价格数据, 请稍候...'
    });
    // 初始化门票价格日历
    $('#ticket_price_calendar').fullCalendar({
        theme: true,
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        width: 200,
        height: 400,
        lang: 'zh-cn',
        editable: false,
        eventLimit: false, // allow "more" link when too many events
        weekNumbers: false,
        fixedWeekCount: false,
        droppable: false,
        dayClick: function (date, allDay, jsEvent, view) {
            var dateStr = date.format();
            var calEvent = $("#ticket_price_calendar").fullCalendar('clientEvents', dateStr)[0];
            if (calEvent != null && calEvent) {
                changeOrderDetailDate(calEvent);
            }
        },
        eventClick: function (calEvent, jsEvent, view) {
            if (calEvent != null && calEvent) {
                changeOrderDetailDate(calEvent);
            }
        }
    });
    // 填充数据
    $.ajax({
        url: '/outOrder/outOrderEdit/getEditTicketPrice.jhtml',
        type: 'post',
        dateType: 'json',
        data: {
            orderDetailId: orderDetailId
        },
        success: function(result) {
            if (result.success) {
                var calendarData = [];
                $.each(result.data, function(i, ticketDatePrice) {
                    calendarData.push({
                        id: ticketDatePrice.huiDate,
                        date: ticketDatePrice.huiDate,
                        title: '¥' + (Number(ticketDatePrice.priPrice) + Number(ticketDatePrice.rebate)),
                        costId: ticketDatePrice.ticketPriceId.id
                    })
                });
                // 填充价格数据
                $('#ticket_price_calendar').fullCalendar('removeEvents');
                $('#ticket_price_calendar').fullCalendar('addEventSource', calendarData);
                $.messager.progress('close');
            } else {
                $.messager.progress('close');
                $.messager.alert('修改订单', result.msg, 'error');
            }
        },
        error: function(result) {
            $.messager.progress('close');
            $.messager.alert('修改订单', "操作失败,请重试!", 'error');
        }
    });
}
function changeOrderDetailDate(data){
    $('#edit_date_form').find('input[name = "targetDate"]').val(data.date);
    $('#edit_date_form').find('#showDate').html(data.date);
    $('#edit_date_form').find('input[name = "costId"]').val(data.costId);
}
function commitEditOrderDetail(formName, panelName, url) {
    $.messager.progress({
        title: '修改订单',
        msg: '正在尝试修改, 请稍候...'
    });
    $('#' + formName).form('submit', {
        url: url,
        success: function(result) {
            result = eval('(' + result + ')');
            if (result.success) {
                var orderId = $("#" + panelName).find('input[name = "orderId"]').val();
                $("#" + panelName).dialog("close");
                showMsgPlus("修改订单", result.msg, 1500);
                // 刷新订单列表
                Orders.table.datagrid('load');
                setTimeout(function () {
                    $("#detail-panel").dialog('close');
                    Orders.openDetail(orderId, 0);
                }, 1600);
            } else if (!result.success) {
                $.messager.alert('修改订单', result.msg, 'error');
            }
            $.messager.progress('close');
        },
        onSubmit: function() {
            var isValid = $('#' + formName).form('validate');
            if (!isValid) {
                $.messager.progress('close');
                $.messager.alert('修改订单', "必填项目不能为空!", 'error');
            }
        },
        error: function() {
            $.messager.progress('close');
            $.messager.alert('修改订单', "操作失败,请重试!", 'error');
        }
    });
}

function cancelOrderDetail(orderDetailId) {
    $.messager.confirm("订单详情", "确认取消订单?", function(r) {
       if(r) {
           $.ajax({
               url: "/balance/yhyOrderCancel/cancelByOrderDetailOta.jhtml",
               type: "post",
               dataTaype: "json",
               data: {
                   orderDetailId: orderDetailId
               },
               success: function(result) {
                   $.messager.alert('订单详情', result.msg, 'info', null, function() {
                       $("#detail-panel").dialog("reload");
                   });
               },
               error: function(result) {
                   $.messager.alert('订单详情', '退订失败,稍候重试！', 'error');
               }
           });
       } else {

       }
    });
}

function cancelOutOrderDetail(orderId, orderDetailId) {
    $.messager.confirm("订单详情", "确认取消订单?", function(r) {
        if(r) {
            $.ajax({
                url: "/outOrder/outOrderCancel/cancelByOutOrderDetail.jhtml",
                type: "post",
                dataTaype: "json",
                data: {
                    orderDetailId: orderDetailId,
                    orderId: orderId
                },
                success: function(result) {
                    $.messager.alert('订单详情', result.msg, 'info', null, function() {
                        $("#detail-panel").dialog("reload");
                    });
                },
                error: function(result) {
                    $.messager.alert('订单详情', '退订失败,稍候重试！', 'error');
                }
            });
        } else {

        }
    });
}






function getRealOrderId(orderDetail) {
    var realOrderId = orderDetail.realOrderId;
    if (realOrderId == null || realOrderId == "") {
        return "-";
    } else {
        return realOrderId;
    }
}
function getApiResult(orderDetail) {
    var apiResult = orderDetail.apiResult;
    var realOrderId = orderDetail.realOrderId;
    if (apiResult != null && apiResult != "") {
        return apiResult;
    } else {
        if (realOrderId != null && realOrderId != "") {
            return "暂无结果,稍后查询";
        } else {
            return "-";
        }
    }
}
function getProductDetail(product) {
    var name = product == null || product.name == "" ||  product.name == null ? "其他" : product.name;
    var div = "<div class='product cf'>";
    //div += '<img src="http://c.cncnimg.cn/038/476/c3e5_s.jpg" width="80px" height="60px" alt="该图片已从相册删除">';
    div += '<div class="product-title">';
    div += '<span>' + name + '</span>';
    //div += '<a href="http://www.cncn.com/xianlu/638203942700" target="_blank" title="' + name + '">' + name + '</a>';
    //div += ' <em>出发城市：' + product.cityId + '</em>';
    div += '</div>';
    div += "</div>";
    return div;
}
function getProductType(productType) {
    if (productType == 'scenic') {
        return '门票';
    } else if (productType == 'restaurant') {
        return '餐厅';
    } else if (productType == 'hotel') {
        return '酒店';
    } else if (productType == 'line') {
        return '线路';
    } else if (productType == 'train') {
        return '火车票';
    } else if (productType == 'flight') {
        return '机票';
    } else if (productType == 'delicacy') {
        return '美食';
    } else if (productType == 'recplan') {
        return '游记';
    } else if (productType == 'plan') {
        return '行程';
    } else if (productType == 'insurance') {
        return '保险';
    } else if (productType == "cruiseship") {
        return '邮轮';
    }else {
        return "其他"
    }
}
function getOrderType(orderType) {
    if ("plan" === orderType) {
        return "行程";
    } else if ("ticket" === orderType) {
        return "门票";
    } else if ("train" === orderType) {
        return "火车票";
    } else if ("flight" === orderType) {
        return "机票";
    } else if ("hotel" === orderType) {
        return "酒店";
    } else if ("recharge" === orderType) {
        return "充值";
    } else if ("withdraw" === orderType) {
        return "提现";
    } else if ("line" === orderType) {
        return "线路";
    } else if ("scenic" === orderType) {
        return "门票";
    } else if ("recplan" === orderType) {
        return "游记";
    } else if ("restaurant" === orderType) {
        return "餐厅";
    } else if ("delicacy" === orderType) {
        return "美食";
        (""), (""),
            (""), (""), (""), ("");
    } else if ("ship" === orderType) {
        return "游轮";
    } else if ("cruiseship" === orderType) {
        return "邮轮";
    } else if ("ferry" === orderType) {
        return "轮渡";
    } else if ("sailboat" === orderType) {
        return "帆船";
    } else if ("yacht" === orderType) {
        return "游艇";
    } else if ("shenzhou" === orderType) {
        return "专车";
    } else {
        return "其他";
    }
}

$(function () {
    Orders.init();
    //编辑框关闭时清除表单
    $("#edit_panel").dialog({
        onClose: function () {
            $("#ff").form("clear");
        }
    });
});