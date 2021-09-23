/**
 * Created by zzl on 2016/9/7.
 */
$(function () {
    HotelRoomStatusMgr.initHotelData();
    HotelRoomStatusMgr.initHotelRoomData();
});
var HotelRoomStatusMgr = {
    hotel_searcher: $('#hotel-searcher'),
    searcher: $('#hotel-tool'),
    initHotelData: function() {
        $('#hotelId_combogrid').combogrid({
            url: "/outOrder/hotelRoom/listRoomByHotel.jhtml",
            idField: 'id',
            textField: 'name',
            panelWidth: 600,
            loadMsg: '加载中,请稍候...',
            multiple: false,
            pagination:true,
            pageList:[20,50,100],
            rownumbers:false,
            queryParams: {
                'hotel.source': "LXB"
            },
            columns:[[
                {field: 'id', title: 'ID', width: 100, align: 'center'},
                {field: 'name', title: '酒店名称', width: 440, align: 'center'},
            ]],
            toolbar: this.hotel_searcher
        });
    },
    initHotelRoomData: function() {
        $('#hotelRoomDg').datagrid({
            url: '/outOrder/hotelRoom/getRoomStatus.jhtml',
            fit: true,
            pagination:false,
            pageList:[20,30,50],
            rownumbers:false,
            columns: [[
                {
                    field: 'hotel.name',
                    title: '酒店名称',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'hotelPrice.roomName',
                    title: '房型名称',
                    align: 'center',
                    width: 150
                },
                {
                    field: 's',
                    title: '房型状态',
                    width: 500,
                    formatter: HotelRoomStatusMgr.roomStatusFormat
                }
            ]],
            toolbar: this.searcher
        });
    },
    doSearch: function() {
        var selectData = $("#hotelId_combogrid").combogrid('grid').datagrid('getSelected');
        if(selectData == null || selectData == "") {
            showMsgPlus("提示", "请先选择一个酒店", 1000);
            return;
        }
        var searchForm = {};
        searchForm['hotelId'] = $('input[name = "hotelId"]').val();
        searchForm['days'] = $('#hotel-day-range').val();
        $('#hotelRoomDg').datagrid('load', searchForm);
    },
    doHotelSearch: function() {
        var hotelSearchForm = {};
        var searchType = this.hotel_searcher.find("#hotel-search-type").val();
        hotelSearchForm[searchType] = this.hotel_searcher.find("#hotel-search-content").val();
        hotelSearchForm['hotel.source'] = "LXB";
        $("#hotelId_combogrid").combogrid('grid').datagrid('load', hotelSearchForm);
    },
    roomStatusFormat: function (value, rowData, rowIndex) {
        var html = "";
        html += "<div class='room_status'>";
        if (rowData.orderDetailList && rowData.orderDetailList.length <= 0) {
            return "<span style='color: #ff2222; font-weight: bold; font-size: 16px;'>暂无有效订单</span>"
        }
        $.each(rowData.orderDetailList, function(i, orderDetail) {
            html += "<div class='detail_item'>";
            html += "<li class='play_date'>入住:"+ orderDetail.playDate + "&nbsp;|&nbsp;"
                  + "退房: " + orderDetail.leaveDate
                  + ", 数量: " + orderDetail.num + ", " + HotelRoomStatusMgr.getCheckBtn(orderDetail) + "</li>";
            html += "<div class='tourist'>";
            $.each(orderDetail.orderTouristList, function(ti, tourist) {
                html += "<li class='tourist_li'>客户" + (ti + 1) + ":" + tourist.name;
                html += "(" + tourist.tel + "&nbsp;/&nbsp;" + tourist.idNumber + ")";
                html += "</li>"
            });
            html += "</div>"
            html += "</div>"
        });
        html += "</div>"
        return html;
    },
    getCheckBtn: function(orderDetail) {
        var status = orderDetail.status;
        if (status == "SUCCESS") {
            var checkinClick = " onClick='HotelRoomStatusMgr.openCheckin(" + orderDetail.id + ")'";
            var checkinBtn = "<a style='color: blue;text-decoration: underline;' href='#'" + checkinClick + ">办理入住</a>";
            return "状态: " + HotelRoomStatusMgr.getOrderDetailStatus(status) + "(" + checkinBtn + ")";
        } else if(status == "CHECKIN") {
            var checkoutClick = " onClick='HotelRoomStatusMgr.openCheckout(" + orderDetail.id + ")'";
            var checkoutBtn = "<a style='color: blue;text-decoration: underline;' href='#'" + checkoutClick + ">办理退房</a>";
            return "状态: " + HotelRoomStatusMgr.getOrderDetailStatus(status) + "(" + checkoutBtn + ")";
        } else {
            return "状态: " + HotelRoomStatusMgr.getOrderDetailStatus(status);
        }
    },
    openCheckin: function(orderDetailId) {
        $.messager.progress({
            msg: '正在查询入住信息, 请稍候...'
        });
        // 清空验证信息
        $('#confirm_checkin_info').empty();
        $.ajax({
            url: '/outOrder/hotelRoom/confirmCheckinInfo.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                orderDetailId: orderDetailId,
                optType: "in"
            },
            success: function(result) {
                if (result.success) {
                    var orderDetail = result.orderDetail;
                    var hotelPrice = result.hotelPrice;
                    $('#confirm_checkin_form').find('input[name = "orderDetailId"]').val(orderDetail.id);
                    $('#confirm_checkin_form').find('input[name = "hotel.id"]').val(hotelPrice.hotel.id);
                    $('#confirm_checkin_form').find('input[name = "hotel.name"]').val(hotelPrice.hotel.name);
                    $('#confirm_checkin_form').find('input[name = "roomName"]').val(hotelPrice.roomName);
                    $('#confirm_checkin_form').find('input[name = "playDate"]').val(orderDetail.playDate);
                    $('#confirm_checkin_form').find('input[name = "num"]').val(orderDetail.num);
                    var checkInfo = "";
                    checkInfo += "<div class='room_status'>";
                    $.each(orderDetail.orderTouristList, function(ti, tourist) {
                        checkInfo += "<li class='tourist_li'>客户" + (ti + 1) + ":&nbsp;&nbsp;"
                                    + "<span style='font-size: 16px; font-weight: bold'>" + tourist.name + "</span>";
                        checkInfo += "(" + tourist.tel + "&nbsp;/&nbsp;" + tourist.idNumber + ")";
                        checkInfo += "</li>"
                    });
                    checkInfo += "</div>";
                    $('#confirm_checkin_info').append(checkInfo);
                    // 打开办理入住确认框
                    $("#confirm_checkin_panel").dialog({
                        title:'办理入住',
                        modal:true,
                        onClose:function(){
                            $("#confirm_checkin_form").form('clear');
                            // 清空验证信息
                            $('#confirm_checkin_info').empty();
                        }
                    });
                    $("#confirm_checkin_panel").dialog("open");
                } else if (!result.success) {
                    $.messager.alert('办理入住', data.msg, 'error');
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('办理入住', "操作失败,请重试!", 'error');
            }
        });
    },
    openCheckout: function(orderDetailId) {
        $.messager.progress({
            msg: '正在查询退房信息, 请稍候...'
        });
        // 清空验证信息
        $('#confirm_checkout_info').empty();
        $.ajax({
            url: '/outOrder/hotelRoom/confirmCheckinInfo.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                orderDetailId: orderDetailId,
                optType: "out"
            },
            success: function(result) {
                if (result.success) {
                    var orderDetail = result.orderDetail;
                    var hotelPrice = result.hotelPrice;
                    $('#confirm_checkout_form').find('input[name = "orderDetailId"]').val(orderDetail.id);
                    $('#confirm_checkout_form').find('input[name = "hotel.id"]').val(hotelPrice.hotel.id);
                    $('#confirm_checkout_form').find('input[name = "hotel.name"]').val(hotelPrice.hotel.name);
                    $('#confirm_checkout_form').find('input[name = "roomName"]').val(hotelPrice.roomName);
                    $('#confirm_checkout_form').find('input[name = "playDate"]').val(orderDetail.playDate);
                    $('#confirm_checkout_form').find('input[name = "leaveDate"]').val(orderDetail.leaveDate);
                    $('#confirm_checkout_form').find('input[name = "nowDate"]').val(result.nowDate);
                    $('#confirm_checkout_form').find('input[name = "num"]').val(orderDetail.num);
                    $('#confirm_checkout_form').find('input[name = "booking_nights"]').val(orderDetail.num);
                    $('#confirm_checkout_form').find('input[name = "checkin_nights"]').val(orderDetail.num);
                    var checkInfo = "";
                    checkInfo += "<div class='room_status'>";
                    $.each(orderDetail.orderTouristList, function(ti, tourist) {
                        checkInfo += "<li class='tourist_li'>客户" + (ti + 1) + ":&nbsp;&nbsp;"
                            + "<span style='font-size: 16px; font-weight: bold'>" + tourist.name + "</span>";
                        checkInfo += "(" + tourist.tel + "&nbsp;/&nbsp;" + tourist.idNumber + ")";
                        checkInfo += "</li>"
                    });
                    checkInfo += "</div>";
                    $('#confirm_checkout_info').append(checkInfo);
                    // 打开办理退房确认框
                    $("#confirm_checkout_panel").dialog({
                        title:'办理退房',
                        modal:true,
                        onClose:function(){
                            $("#confirm_checkout_form").form('clear');
                            // 清空验证信息
                            $('#confirm_checkout_info').empty();
                        }
                    });
                    $("#confirm_checkout_panel").dialog("open");
                } else if (!result.success) {
                    $.messager.alert('办理退房', data.msg, 'error');
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('办理退房', "操作失败,请重试!", 'error');
            }
        });
    },
    openVerifyCheckin: function() {
        var selectData = $("#hotelId_combogrid").combogrid('grid').datagrid('getSelected');
        if(selectData == null || selectData == "") {
            showMsgPlus("入住验证", "请先选择一个酒店", 1000);
            return;
        }
        $('#hotelName').val(selectData.name);
        $('input[name = "hotel.id"]').val($('input[name = "hotelId"]').val());
        $('#hotel-check-range').val(3);
        $('#verify_checkin_panel').dialog({
            title:'入住验证',
            modal:true,
            onClose:function(){
                $("#verify_checkin_form").form('clear');
                // 清空验证记录
                $('#verify_checkin_info').empty();
            }
        });
        $('#verify_checkin_panel').dialog('open');
    },
    doVerifyCheckin: function(formName, panelName) {
        // 清空验证记录
        $('#verify_checkin_info').empty();
        $.messager.progress({
            msg: '正在验证入住信息, 请稍候...'
        });
        $('#' + formName).form('submit', {
            url: '/outOrder/hotelRoom/doVerifyCheckin.jhtml',
            success: function (data) {
                data = eval('(' + data + ')');
                if (data.success) {
                    var checkInfo = "<div class='detail-info'>"
                    $.each(data.orderDetailList, function(i, orderDetail) {
                        if (orderDetail.status == "SUCCESS") {
                            checkInfo += "<li style='background-color: #008800;color: #FFF'>验证结果" + (i + 1) + ": ";
                        } else {
                            checkInfo += "<li>验证结果" + (i + 1) + ": ";
                        }
                        checkInfo += "入住: " + orderDetail.playDate + ", ";
                        checkInfo += "房型: " + orderDetail.hotelPrice.roomName;
                        checkInfo += "状态: " + HotelRoomStatusMgr.getOrderDetailStatus(orderDetail.status);
                        checkInfo += "</li>"
                    });
                    checkInfo += "</div>"
                    $('#verify_checkin_info').append(checkInfo);
                    //console.log(data.orderDetailList);
                } else if (!data.success) {
                    showMsgPlus("入住验证", data.msg, 2000);
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('入住验证', "操作失败,请重试!", 'error');
            }
        });
    },
    doConfirmCheckin: function(formName, panelName) {
        $.messager.progress({
            msg: '正在办理入住, 请稍候...'
        });
        $('#' + formName).form('submit', {
            url: '/outOrder/hotelRoom/doConfirmCheckin.jhtml',
            success: function(result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("办理入住", result.msg);
                    $('#hotelRoomDg').datagrid('load');
                } else if (!result.success) {
                    $.messager.alert('办理入住', result.msg, 'error');
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('办理入住', "操作失败,请重试!", 'error');
            }
        });
    },
    doConfirmCheckout: function(formName, panelName) {
        $.messager.progress({
            msg: '正在办理退房, 请稍候...'
        });
        $('#' + formName).form('submit', {
            url: '/outOrder/hotelRoom/doConfirmCheckout.jhtml',
            success: function(result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("办理退房", result.msg);
                    $('#hotelRoomDg').datagrid('load');
                } else if (!result.success) {
                    $.messager.alert('办理退房', result.msg, 'error');
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('办理退房', "操作失败,请重试!", 'error');
            }
        });
    },
    getHotelRoomDataTable: function() {
        var selectData = $("#hotelId_combogrid").combogrid('grid').datagrid('getSelected');
        if(selectData == null || selectData == "") {
            showMsgPlus("酒店房态数据导出", "请先选择一个酒店", 1000);
            return;
        }
        $.messager.progress({
            title: '酒店房态数据导出',
            msg: '正在准备数据, 请稍候...'
        });
        $.ajax({
            url: '/outOrder/hotelRoom/getHotelRoomDataTable.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                hotelId: $('input[name = "hotelId"]').val(),
                days: $('#hotel-day-range').val()
            },
            success: function(result) {
                if (result.success) {
                    window.location = "/static" + result.fileUrl;
                } else {
                    $.messager.alert('酒店房态数据导出', result.msg, 'error');
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('酒店房态数据导出', "操作失败,请重试!", 'error');
            }
        });
    },
    getOrderDetailStatus: function(status) {
        if (status == null || status == "") {
            return "-";
        } else if(status == "SUCCESS") {
            return "<span class='d_status' style='background-color: #008800'>预订成功</span>";
        } else if (status == "CANCELED") {
            return "<span class='d_status' style='background-color: #8F8F8F'>已取消</span>";
        } else if (status == "FAILED") {
            return "<span class='d_status' style='background-color: #ff4c4d'>预定失败</span>";
        } else if (status == "CHECKIN") {
            return "<span class='d_status' style='background-color: #1b91ff'>已入住</span>";
        } else if (status == "CHECKOUT") {
            return "<span class='d_status' style='background-color: #e200fd'>已退房</span>";
        }
    }
};