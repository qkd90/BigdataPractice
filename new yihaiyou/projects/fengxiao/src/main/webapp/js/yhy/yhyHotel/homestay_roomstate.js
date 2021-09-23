/**
 * Created by zzl on 2016/12/8.
 */
var HotelRoomState = {
    HotelRoomStateListTable: null,
    HotelRoomStateItem: null,
    HotelRoomMemberItem: null,
    HotelRoomStateDetailItem: null,
    HotelRoomMemberDetailItem: null,
    OrderDetailStatus: {SUCCESS: "未入住", CHECKIN: "已入住", CHECKOUT: "已退房"},
    IdTypes: {IDCARD: "二代身份证", STUDENTCARD: "学生证", PASSPORT: "护照",OTHER: "其他 ", HKMP: "港澳通行证", TWP: "台湾通行证"},
    init: function() {
        HotelRoomState.initComp();
        HotelRoomState.initEvt();
        HotelRoomState.getHotelRoomStateList();
    },
    initComp: function() {
        $('input[name = "startDate"], input[name = "endDate"]').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: 'true',
            language: 'zh-CN',
            minView: 'month'
        });
        $('#searchStatusSel').btComboBox();
    },
    initEvt: function() {
        // search btn
        $('#searchRoomStateBtn').on('click', function(event) {
            event.stopPropagation();
            HotelRoomState.doSearchRoomState(true);
        });
        // close check in btn
        $('#check_in_form span.checkclose').on('click', function(event) {
            event.stopPropagation();
            $.form.reset({formId: '#check_in_form'});
            $('#check_in_form div.hotel-room-group').empty();
            $('#check_in_form button.sureBtn').button('reset');
            $('div.checkin').fadeOut();
            $('.shadow').addClass('disn');
        });
        // close check out btn
        $('#check_out_form span.checkclose').on('click', function(event) {
            event.stopPropagation();
            $.form.reset({formId: '#check_out_form'});
            $('#check_out_form').find('div.recName').empty();
            $('#check_out_form').find('div.mobile').empty();
            $('#check_out_form div.hotel-room-group').empty();
            $('#check_out_form button.sureBtn').button('reset');
            $('div.checkout').fadeOut();
            $('.shadow').addClass('disn');
        });
        // close check detail btn
        $('#check_detail_form span.checkclose').on('click', function(event) {
            event.stopPropagation();
            $.form.reset({formId: '#check_detail_form'});
            $('#check_detail_form').find('div.recName').empty();
            $('#check_detail_form').find('div.mobile').empty();
            $('#check_detail_form').find('div.remark').empty();
            $('#check_detail_form div.hotel-room-group').empty();
            $('#check_detail_form button.sureBtn').button('reset');
            $('div.checkmess').fadeOut();
            $('.shadow').addClass('disn');
        });
        // validate check in btn
        $('#validate_checkin_btn').on('click', function (event) {
            event.stopPropagation();
            HotelRoomState.doValidateCheckIn();
        });
        // save hotel room state btn
        $('#check_in_form button.sureBtn').on('click', function(event) {
            event.stopPropagation();
            HotelRoomState.doSaveHotelRoomState();
        });
        // save check out btn
        $('#check_out_form button.sureBtn').on('click', function(event) {
            event.stopPropagation();
            HotelRoomState.doCheckOut();
        });
        // close check detail btn
        $('#check_detail_form button.sureBtn').on('click', function(event) {
            event.stopPropagation();
            $('#check_detail_form span.checkclose').click();
        });
        // add member btn
        $('div.add-member').on('click', function(event) {
            event.stopPropagation();
            var memberLength =  $(this).siblings('div.hotel-member-group').find('div.hotel-member-item').length;
            var capacity = $('#check_in_form input[name = "capacity"]').val();
            if (memberLength == capacity) {
                $.messager.show({
                    msg: "该房型最多可入住" + capacity + "人!",
                    type: "warn"
                });
            } else {
                var $roomMemberItem = HotelRoomState.HotelRoomMemberItem.clone(true);
                $(this).siblings('div.hotel-member-group').append($roomMemberItem);
                var top = $roomMemberItem.offset().top - $('#check_in_form div.checkbody').offset().top + $('#check_in_form div.checkbody').scrollTop();
                $('#check_in_body').animate({scrollTop: top}, 100);
            }
        });
        // del member btn
        $('div.del-member').on('click', function(event) {
            event.stopPropagation();
            var memberLength =  $(this).parent('div.hotel-member-item').siblings('div.hotel-member-item').length;
            if (memberLength && memberLength > 0) {
                $(this).parent('div.hotel-member-item').remove();
            } else {
                $.messager.show({
                    msg: "至少需要一名入住人!",
                    type: "warn"
                });
            }
        });
        // hotel room state / member item (check in)
        HotelRoomState.HotelRoomStateItem = $('#check_in_form div.hotel-room-item').clone(true).eq(0);
        HotelRoomState.HotelRoomMemberItem = $('#check_in_form div.hotel-member-item').clone(true).eq(0);
        // hotel room state / member item (detail)
        HotelRoomState.HotelRoomStateDetailItem = $('#check_detail_form div.hotel-room-item').clone(true).eq(0);
        HotelRoomState.HotelRoomMemberDetailItem = $('#check_detail_form div.hotel-member-item').clone(true).eq(0);
    },
    getHotelRoomStateList: function() {
        HotelRoomState.HotelRoomStateListTable = $('#hotelRoomStateList').DataTable({
            //"processing": true,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            //"stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['productId'] = $('#productId').val();
                data['searchContent'] = $('input[name = "searchContent"]').val();
                data['startDate'] = $('input[name = "startDate"]').val();
                data['endDate'] = $('input[name = "endDate"]').val();
                data['status'] = $('input[name = "status"]').val();
                $.ajax({
                    url: '/yhy/yhyHotelRoomState/getHotelRoomStateList.jhtml',
                    type: 'post',
                    dataType: 'json',
                    progress: true,
                    data: data,
                    success: function(result) {
                        if (result.success) {
                            callback(result);
                        } else {
                            $.messager.show({
                                msg: result.msg,
                                type: "error"
                            });
                        }
                    },
                    error: function() {
                        $.messager.show({
                            msg: "数据加载错误, 请稍候重试!",
                            type: "error"
                        });
                    }
                });
            },
            columns: [
                {data: 'order.orderNo', 'defaultContent': "-"},
                {data: 'order.mobile', 'defaultContent': "-"},
                {data: 'playDate', 'defaultContent': "-", 'render': function(value, type, row, meta) {
                    return moment(value).format('YYYY-MM-DD');
                }},
                {data: 'leaveDate', 'defaultContent': "-", 'render': function(value, type, row, meta) {
                    return moment(value).format('YYYY-MM-DD');
                }},
                {data: 'seatType', 'defaultContent': "-"},
                {data: 'roomNums', 'defaultContent': "-", 'render': function(value, type, row, meta) {
                    return value && value != "" ? value : "-";
                }},
                {data: 'status', 'defaultContent': "-", 'render': function(value, type, row, meta) {
                    return HotelRoomState.OrderDetailStatus[value];
                }},
                {data: null, 'defaultContent': "-", 'render':  HotelRoomState.getOpt},
            ]
        });
        YhyCommon.Table = HotelRoomState.HotelRoomStateListTable;
    },
    getOpt: function(value, type, row, meta) {
        var status = row.status;
        var playDate = moment(row.playDate, 'YYYY-MM-DD');
        var nowDate = moment();
        var optHtml = "";
        var orderDetailId = row.id;
        var checkInOpt = "<a onclick='HotelRoomState.showCheckIn("+ orderDetailId +")' class='link-span'>办理入住</a>";
        var checkOutOpt = "<a onclick='HotelRoomState.showCheckOut("+ orderDetailId +")' class='link-span'>离店结算</a>";
        var checkDetailOpt = "<a onclick='HotelRoomState.showCheckDetail("+ orderDetailId +")' class='link-span'>入住信息</a>";
        if (playDate.isBefore(nowDate, "day")) {
            if (status === "SUCCESS") {
                optHtml =  "-";
            } else if (status === "CHECKIN") {
                optHtml = checkOutOpt + checkDetailOpt;
            } else if (status === "CHECKOUT") {
                optHtml = checkDetailOpt;
            }
        } else if(playDate.isSame(nowDate, "day")) {
            if (status === "SUCCESS") {
                optHtml = checkInOpt;
            } else if (status === "CHECKIN") {
                optHtml = checkOutOpt + checkDetailOpt;
            } else if (status === "CHECKOUT") {
                optHtml = checkDetailOpt;
            }
        } else if(playDate.isAfter(nowDate, "day")) {
            optHtml = "-";
        }
        return optHtml;
    },
    doSearchRoomState: function(resetPage) {
        if (resetPage === true) {
            HotelRoomState.HotelRoomStateListTable.ajax.reload(function(){}, true);
        } else if (resetPage === false) {
            HotelRoomState.HotelRoomStateListTable.ajax.reload(function(){}, false);
        } else {
            HotelRoomState.HotelRoomStateListTable.ajax.reload(function(){}, true);
        }
    },
    showCheckIn: function(orderDetailId) {
        HotelRoomState.getCheckInfo(orderDetailId, "check_in_form", function(result) {
            // clear room state / member item
            $('#check_in_form div.hotel-room-group').empty();
            for(var i = 0; i < result.num; i++) {
                var $roomStateItem = HotelRoomState.HotelRoomStateItem.clone(true);
                var $roomMemberItem = HotelRoomState.HotelRoomMemberItem.clone(true);
                $roomStateItem.find('div.hotel-member-group').empty();
                $roomStateItem.find('div.hotel-member-group').append($roomMemberItem);
                $.each(result.roomNumbers.split(","), function(j, no) {
                    if (no && no != "") {
                        var opt = "<option value='" + no + "'>" + no + "</option>";
                        $roomStateItem.find('select[data-name = "roomNo"]').append(opt);
                    }
                });
                // set values
                $roomStateItem.find('input[name = "playDate"]').val(result.playDate);
                $roomStateItem.find('input[name = "leaveDate"]').val(result.leaveDate);
                $roomStateItem.find('div.days').html(null).html(result.days + "晚");
                $roomStateItem.find('input[name = "seatType"]').val(result.seatType);
                $('#check_in_form div.checkfooter span.money').html(null).html(result.totalPrice);
                $('#check_in_form div.hotel-room-group').append($roomStateItem);
            }
            // show check in dialog
            $('.shadow').removeClass('disn');
            $('div.checkin').fadeIn();
        }, function() {
            $.messager.show({
                msg: "订单数据读取失败, 稍后重试!",
                type: "error"
            });
        });
    },
    showCheckOut: function(orderDetailId) {
        HotelRoomState.getCheckInfo(orderDetailId, "check_out_form", function(result) {
            HotelRoomState.showRoomStateInfo(result, "check_out_form");
            // show check out dialog
            $('.shadow').removeClass('disn');
            $('div.checkout').fadeIn();
        }, function() {
            $.messager.show({
                msg: "订单数据读取失败, 稍后重试!",
                type: "error"
            });
        });
    },
    showCheckDetail: function(orderDetailId) {
        HotelRoomState.getCheckInfo(orderDetailId, "check_detail_form", function (result) {
            HotelRoomState.showRoomStateInfo(result, "check_detail_form");
            // show check detail dialog
            $('.shadow').removeClass('disn');
            $('div.checkmess').fadeIn();
        }, function () {
            $.messager.show({
                msg: "订单数据读取失败, 稍后重试!",
                type: "error"
            });
        });
    },
    getCheckInfo: function(orderDetailId, formId, successCallback, errorCallback) {
        $.form.load({
            url: '/yhy/yhyHotelRoomState/getCheckInfo.jhtml',
            formId: '#' + formId,
            formData: {orderDetailId: orderDetailId},
            onLoadSuccess: successCallback,
            onLoadError: errorCallback
        });
    },
    showRoomStateInfo: function(result, formId) {
        var $form = $("#" + formId);
        $form.find('div.recName').html(null).html(result.recName);
        $form.find('div.mobile').html(null).html(result.mobile);
        $form.find('div.validateCode').html(null).html(result.validateCode);
        $form.find('div.remark').html(null).html(result.remark);
        // clear room state / member item
        $form.find('div.hotel-room-group').empty();
        if (result.roomStateList && result.roomStateList.length > 0) {
            $.each(result.roomStateList, function(i, roomState) {
                var $roomStateItem = HotelRoomState.HotelRoomStateDetailItem.clone(true);
                $roomStateItem.find('div.playDate').html(null).html(moment(roomState.checkInDate).format('YYYY-MM-DD'));
                $roomStateItem.find('div.leaveDate').html(null).html(moment().format('YYYY-MM-DD'));
                $roomStateItem.find('div.nights').html(null).html(roomState.nights + "晚");
                $roomStateItem.find('div.seatType').html(null).html(result.seatType);
                $roomStateItem.find('div.roomNo').html(null).html(roomState.roomNo);
                $roomStateItem.find('div.hotel-member-group').empty();
                $.each(roomState.memberList, function(j, member) {
                    var $roomMemberItem = HotelRoomState.HotelRoomMemberDetailItem.clone(true);
                    $roomMemberItem.find('div.name').html(null).html(member.name);
                    $roomMemberItem.find('div.idType').html(null).html(HotelRoomState.IdTypes[member.idType]);
                    $roomMemberItem.find('div.idNumber').html(null).html(member.idNumber);
                    $roomStateItem.find('div.hotel-member-group').append($roomMemberItem);
                });
                $form.find('div.checkfooter span.money').html(null).html(result.totalPrice);
                $form.find('div.hotel-room-group').append($roomStateItem);
            });
        } else {
            $.messager.show({
                msg: "未发现入住记录!",
                type: "warn"
            });
        }
    },
    buildCheckInInfo: function() {
        var infoCheck = true;
        var roomNums = [];
        var validateCode = $('#check_in_form input[name = "validateCode"]').val();
        if (!validateCode || validateCode == "") {
            $.messager.show({
                msg: "请输入验证码!",
                type: 'warn',
                timeout: 1000
            });
            return false;
        }
        $.each($('#check_in_form div.hotel-room-item'), function(i, roomItem) {
            var $roomItem = $(roomItem);
            var $roomNumSel = $roomItem.find('select[data-name = "roomNo"]');
            if (!$roomNumSel.val() ||  $roomNumSel.val() == "") {
                infoCheck = false;
                $('#check_in_body').animate({scrollTop: $roomNumSel.offset.top - 100}, 100);
                $.messager.show({
                    msg: "请选择房间号码!",
                    type: 'warn',
                    timeout: 1000
                });
                return false;
            };
            // check any repeat room number
            if (roomNums.find(function(roomNum) {return roomNum === $roomNumSel.val()})) {
                infoCheck = false;
                $('#check_in_body').animate({scrollTop: $roomNumSel.offset.top - 100}, 100);
                $.messager.show({
                    msg: "房间号: " + $roomNumSel.val() + "重复, 请选择其他房间!",
                    type: 'warn',
                    timeout: 1000
                });
                return false;
            } else {
                roomNums.push($roomNumSel.val());
            }
            $roomNumSel.prop("name", "roomStateList[" + i + "].roomNo");
            $.each($roomItem.find('div.hotel-member-item'), function(j, memberItem) {
                var $memberItem = $(memberItem);
                var $nameInput = $memberItem.find('input[data-name = "name"]');
                var $idTypeSel = $memberItem.find('select[data-name = "idType"]');
                var $idNumInput = $memberItem.find('input[data-name = "idNumber"]');
                if (!$nameInput.val() ||  $nameInput.val() == "") {
                    infoCheck = false;
                    $('#check_in_body').animate({scrollTop: $nameInput.offset.top - 100}, 100);
                    $.messager.show({
                        msg: "请完填写客户姓名!",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                if (!$idTypeSel.val() ||  $idTypeSel.val() == "") {
                    infoCheck = false;
                    $('#check_in_body').animate({scrollTop: $idTypeSel.offset.top - 100}, 100);
                    $.messager.show({
                        msg: "请选择证件类型!",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                if (!$idNumInput.val() ||  $idNumInput.val() == "") {
                    infoCheck = false;
                    $('#check_in_body').animate({scrollTop: $idNumInput.offset.top - 100}, 100);
                    $.messager.show({
                        msg: "请完填写证件号码!",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                $nameInput.prop("name", "roomStateList[" + i + "].memberList[" + j + "].name");
                $idTypeSel.prop("name", "roomStateList[" + i + "].memberList[" + j + "].idType");
                $idNumInput.prop("name", "roomStateList[" + i + "].memberList[" + j + "].idNumber");
            });
            if (!infoCheck) {
                return false;
            }
        });
        // set room numbers values
        $('input[name = "roomNums"]').val(roomNums.join());
        return infoCheck;
    },
    doValidateCheckIn: function() {
        var orderDetailId = $('#check_in_form input[name = "orderDetailId"]').val();
        var validateCode = $('#check_in_form input[name = "validateCode"]').val();
        if (!orderDetailId || orderDetailId == "") {
            $.messager.show({msg: "订单信息校验失败!", type: "error"});
            return;
        }
        if (!validateCode || validateCode == "") {
            $.messager.show({msg: "请输入验证码!", type: "error"});
            return;
        }
        $.ajax({
            url: '/yhy/yhyHotelRoomState/doValidateCheckIn.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                orderDetailId: orderDetailId,
                validateCode: validateCode
            },
            progress: true,
            success: function(result) {
                if (result.success) {
                    $.messager.show({msg: result.msg, type: result.validate});
                } else {
                    $.messager.show({msg: result.msg, type: "error"});
                }
            },
            error: function() {
                $.messager.show({msg: "校验失败! 稍候重试!", type: "error"});
            }
        });
    },
    doSaveHotelRoomState: function() {
        var $submitButton = $('#check_in_form button.sureBtn');
        $submitButton.button('loading');
        var infoCheck = HotelRoomState.buildCheckInInfo();
        if (!infoCheck) {
            $submitButton.button('reset');
            return;
        }
        $.form.commit({
            url: '/yhy/yhyHotelRoomState/doSaveHotelRoomState.jhtml',
            formId: '#check_in_form',
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "办理入住成功!",
                        type: "success",
                        timeout: 2000,
                        afterClosed: function() {
                            $('#check_in_form span.checkclose').click();
                            HotelRoomState.doSearchRoomState(false);
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: "error"
                    });
                }
                // reset button status
                $submitButton.button('reset');
            },
            error: function() {
                $.messager.show({
                    msg: "办理入住失败! 稍后重试!",
                    type: "error"
                });
                // reset button status
                $submitButton.button('reset');
            }
        });
    },
    doCheckOut: function() {
        var $submitButton = $('#check_out_form button.sureBtn');
        $submitButton.button('loading');
        $.form.commit({
            url: '/yhy/yhyHotelRoomState/doCheckOut.jhtml',
            formId: '#check_out_form',
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "办理退房成功!",
                        type: "success",
                        timeout: 2000,
                        afterClosed: function() {
                            $('#check_out_form span.checkclose').click();
                            HotelRoomState.doSearchRoomState(false);
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: "error"
                    });
                }
                // reset button status
                $submitButton.button('reset');
            },
            error: function() {
                $.messager.show({
                    msg: "办理退房失败! 稍后重试!",
                    type: "error"
                });
                // reset button status
                $submitButton.button('reset');
            }
        });
    },
    getTopLeft: function() {
        var wWidth = $(window).width(),
            wHeight = $(window).height(),
            tWidth = $(effect).width(),
            tHeight = $(effect).height(),
            left = (wWidth - tWidth)/2,
            top = (wHeight - tHeight)/2;
        return [left, top];
    }
};
$(function() {
    HotelRoomState.init();
})

