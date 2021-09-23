/**
 * Created by zzl on 2016/12/5.
 */
var SailboatPriceType = {
    BookingInfoItem: null,
    FeeInfoItem: null,
    RefundInfoItem: null,
    BookingContentNumLimit: 500,
    FeeContentNumLimit: 500,
    RefundContentNumLimit: 500,
    init: function() {
        SailboatPriceType.initComp();
        SailboatPriceType.initEvt();
        SailboatPriceType.initForm();
    },
    initComp: function() {
        // if new, init album
        var ticketPriceId = $('#ticketPriceId').val();
        if (!ticketPriceId || ticketPriceId == "") {
            $('#ticketPriceImgs').fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.scenic, 'ticket/ticketPrice/'));
        }
    },
    initEvt: function() {
        // condition refund sel
        $('#conditionRefundSel').on('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticketPrice.isConditionRefund"]').val($(this).attr('data-condition-refund'));
        });
        // today valid sel
        $('#todayValidSel').on('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticketPrice.isTodayValid"]').val($(this).attr('data-today-valid'));
        });
        // add booking info
        $('#addBookinInfoBtn').on('click', function(event) {
            event.stopPropagation();
            var $bookingInfoItem = SailboatPriceType.BookingInfoItem.clone(true);
            var $ta = $bookingInfoItem.children('textarea[name = "content"]');
            // clear old value
            $bookingInfoItem.children('input[name = "secondTitle"]').val(null);
            $ta.val(null);
            $('#bookingInfoGroup').append($bookingInfoItem);
            KindEditor.create($ta, {
                resizeType: 0,
                afterChange: function () {
                    $ta.prop('data-plaintext-num', this.count('text'));
                    $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.BookingContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        });
        // add fee info
        $('#addFeeInfoBtn').on('click', function(event) {
            event.stopPropagation();
            var $feeInfoItem = SailboatPriceType.FeeInfoItem.clone(true);
            var $ta = $feeInfoItem.children('textarea[name = "content"]');
            // clear old value
            $feeInfoItem.children('input[name = "secondTitle"]').val(null);
            $ta.val(null);
            $('#feeInfoGroup').append($feeInfoItem);
            KindEditor.create($ta, {
                resizeType: 0,
                afterChange: function () {
                    $ta.prop('data-plaintext-num', this.count('text'));
                    $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.FeeContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        });
        // add refund info
        $('#addRefundInfoBtn').on('click', function(event) {
            event.stopPropagation();
            var $refundInfoItem = SailboatPriceType.RefundInfoItem.clone(true);
            var $ta = $refundInfoItem.children('textarea[name = "content"]');
            // clear old value
            $refundInfoItem.children('input[name = "secondTitle"]').val(null);
            $ta.val(null);
            $('#refundInfoGroup').append($refundInfoItem);
            KindEditor.create($ta, {
                resizeType: 0,
                afterChange: function () {
                    $ta.prop('data-plaintext-num', this.count('text'));
                    $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.RefundContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        });
        // del booking info item
        $('a.del-binfo-btn').on('click', function(event) {
            event.stopPropagation();
            var length = $('div.booking-info-item').length;
            if (length && length > 1) {
                $(this).parent('div.booking-info-item').remove();
            } else {
                $.messager.show({
                    msg: "请至少保留一条预订须知",
                    type: "warn"
                });
            }
        });
        // del fee info item
        $('a.del-finfo-btn').on('click', function(event) {
            event.stopPropagation();
            var length = $('div.fee-info-item').length;
            if (length && length > 1) {
                $(this).parent('div.fee-info-item').remove();
            } else {
                $.messager.show({
                    msg: "请至少保留一条费用说明",
                    type: "warn"
                });
            }
        });
        // del refund info item
        $('a.del-rinfo-btn').on('click', function(event) {
            event.stopPropagation();
            var length = $('div.refund-info-item').length;
            if (length && length > 1) {
                $(this).parent('div.refund-info-item').remove();
            } else {
                $.messager.show({
                    msg: "请至少保留一条退款说明",
                    type: "warn"
                });
            }
        });
        SailboatPriceType.BookingInfoItem = $($('.booking-info-item').clone(true)[0]);
        SailboatPriceType.FeeInfoItem = $($('.fee-info-item').clone(true)[0]);
        SailboatPriceType.RefundInfoItem = $($('.refund-info-item').clone(true)[0]);
    },
    initForm: function() {
        $('#ticketPriceForm').bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '#savePriceInfoBtn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                ticketPriceName: {
                    selector: '#ticketPriceName',
                    message: '票型名称不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入票型名称名称'
                        }
                    }
                }
            },
            submitHandler: SailboatPriceType.doSavePriceInfo
        });
        // get form data
        SailboatPriceType.getTicketPriceInfo();
    },
    // get form data
    getTicketPriceInfo: function() {
        var ticketPriceId = $('#ticketPriceId').val();
        if (ticketPriceId && ticketPriceId != "") {
            $.form.load({
                url: '/yhy/yhySailboatPrice/getYhyTicketPriceInfo.jhtml',
                formId: '#ticketPriceForm',
                formData: {
                    id: ticketPriceId,
                    'productimage.childFolder': 'ticket/ticketPrice/'
                },
                onLoadSuccess: function(result) {
                    // condition refund sel
                    $('#conditionRefundSel span[data-condition-refund='+ result['ticketPrice.isConditionRefund'] +']').click();
                    // today valid sel
                    $('#todayValidSel span[data-today-valid='+ result['ticketPrice.isTodayValid'] +']').click();
                    // booking info, fee info, refund info
                    $('#bookingInfoGroup').empty();
                    $('#feeInfoGroup').empty();
                    $('#refundInfoGroup').empty();
                    var extendList = result['priceTypeExtends'];
                    // handle extend infos
                    SailboatPriceType.showExtendInfo(extendList);
                    SailboatPriceType.buildExtendInfo();
                    // load image data
                    $('#ticketPriceImgs').fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.scenic, "ticket/ticketPrice/", result.imgData));
                },
                onLoadError: function() {
                    $.messager.show({
                        msg: "数据加载错误, 请重试!",
                        type: 'error'
                    });
                }
            })
        } else {
            // booking info, fee info, refund info
            $('#bookingInfoGroup').empty();
            $('#feeInfoGroup').empty();
            $('#refundInfoGroup').empty();
            // handle extend infos
            SailboatPriceType.showExtendInfo();
            SailboatPriceType.buildExtendInfo();
        }
    },
    doSavePriceInfo: function(validator, form, submitButton) {
        submitButton.button('loading');
        // build extend info with check
        var extendInfoCheck = SailboatPriceType.buildExtendInfo(true);
        if (!extendInfoCheck) {
            submitButton.button('reset');
            return;
        }
        var imgList = FileinputUtil.getImgData('#ticketPriceImgs');
        // image data check
        if (!imgList || imgList.length <= 0) {
            $.messager.show({
                msg: '请上传图片',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if ($('.file-input span.kv-home-selected').length <= 0) {
            $.messager.show({
                msg: '请设置封面图片',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        var extraData = {};
        $.each(imgList, function (i, img) {
            $.each(img, function(n, v) {
                extraData["imgList[" + i + "]." + n] = v;
            });
        });
        var productId = $('#productId').val() && $('#productId').val() != "" ? $('#productId').val() : $('#ticketId').val();
        extraData["productId"] = productId;
        $.form.commit({
            url: '/yhy/yhySailboatPrice/savePriceInfo.jhtml',
            formId: '#ticketPriceForm',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "票型保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to price type list
                            window.location.href = "/yhy/yhyMain/toSailboatPriceList.jhtml";
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: "error"
                    });
                }
                // reset button status
                submitButton.button('reset');
            },
            error: function() {
                $.messager.show({
                    msg: "票型信息保存错误! 稍后重试!",
                    type: "error"
                });
                // reset button status
                submitButton.button('reset');
            }
        });
    },
    showExtendInfo: function(extendList) {
        if (extendList && extendList.length > 0) {
            $.each(extendList, function(i, e) {
                if (e.firstTitle === "预订须知") {
                    var $bookingInfoItem = SailboatPriceType.BookingInfoItem.clone(true);
                    var $ta = $bookingInfoItem.children('textarea[data-name = "content"]');
                    $bookingInfoItem.children('input[data-name = "secondTitle"]').val(e.secondTitle);
                    $ta.val(e.content);
                    $('#bookingInfoGroup').append($bookingInfoItem);
                    KindEditor.create($ta, {
                        resizeType: 0,
                        afterChange: function () {
                            $ta.prop('data-plaintext-num', this.count('text'));
                            $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.BookingContentNumLimit);
                            this.sync();
                        },
                        afterBlur: function() {this.sync();},
                        items: ['fontsize', 'forecolor', 'fontname', 'bold']
                    });
                } else if(e.firstTitle === "费用说明") {
                    var $feeInfoItem = SailboatPriceType.FeeInfoItem.clone(true);
                    var $ta = $feeInfoItem.children('textarea[data-name = "content"]');
                    $feeInfoItem.children('input[data-name = "secondTitle"]').val(e.secondTitle);
                    $ta.val(e.content);
                    $('#feeInfoGroup').append($feeInfoItem);
                    KindEditor.create($ta, {
                        resizeType: 0,
                        afterChange: function () {
                            $ta.prop('data-plaintext-num', this.count('text'));
                            $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.FeeContentNumLimit);
                            this.sync();
                        },
                        afterBlur: function() {this.sync();},
                        items: ['fontsize', 'forecolor', 'fontname', 'bold']
                    });
                } else if (e.firstTitle === "退款说明") {
                    var $refundInfoItem = SailboatPriceType.RefundInfoItem.clone(true);
                    var $ta = $refundInfoItem.children('textarea[data-name = "content"]');
                    $refundInfoItem.children('input[data-name = "secondTitle"]').val(e.secondTitle);
                    $ta.val(e.content);
                    $('#refundInfoGroup').append($refundInfoItem);
                    KindEditor.create($ta, {
                        resizeType: 0,
                        afterChange: function () {
                            $ta.prop('data-plaintext-num', this.count('text'));
                            $ta.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.RefundContentNumLimit);
                            this.sync();
                        },
                        afterBlur: function() {this.sync();},
                        items: ['fontsize', 'forecolor', 'fontname', 'bold']
                    });
                }
            });
        }
        //
        if ($('div.booking-info-item').length <= 0) {
            var $bookingInfoItem = SailboatPriceType.BookingInfoItem.clone(true);
            var $bookingInfoTA = $bookingInfoItem.children('textarea[data-name = "content"]');
            $bookingInfoItem.children('input[data-name = "secondTitle"]').val(null);
            $bookingInfoTA.val(null);
            $('#bookingInfoGroup').append($bookingInfoItem);
            KindEditor.create($bookingInfoTA, {
                resizeType: 0,
                afterChange: function () {
                    $bookingInfoTA.prop('data-plaintext-num', this.count('text'));
                    $bookingInfoTA.siblings('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.BookingContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        }
        if ($('div.fee-info-item').length <= 0) {
            var $feeInfoItem = SailboatPriceType.FeeInfoItem.clone(true);
            var $feeInfoTA =  $feeInfoItem.children('textarea[data-name = "content"]');
            $feeInfoItem.children('input[data-name = "secondTitle"]').val(null);
            $feeInfoTA.val(null);
            $('#feeInfoGroup').append($feeInfoItem);
            KindEditor.create($feeInfoTA, {
                resizeType: 0,
                afterChange: function () {
                    $feeInfoTA.prop('data-plaintext-num', this.count('text'));
                    $feeInfoTA.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.FeeContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        }
        if ($('div.refund-info-item').length <= 0) {
            var $refundInfoItem = SailboatPriceType.RefundInfoItem.clone(true);
            var $refundInfoTA = $refundInfoItem.children('textarea[data-name = "content"]');
            $refundInfoItem.children('input[data-name = "secondTitle"]').val(null);
            $refundInfoTA.val(null);
            $('#refundInfoGroup').append($refundInfoItem);
            KindEditor.create($refundInfoTA, {
                resizeType: 0,
                afterChange: function () {
                    $refundInfoTA.prop('data-plaintext-num', this.count('text'));
                    $refundInfoTA.nextAll('span.writLimit').html(this.count('text') + "/" + SailboatPriceType.RefundContentNumLimit);
                    this.sync();
                },
                afterBlur: function() {this.sync();},
                items: ['fontsize', 'forecolor', 'fontname', 'bold']
            });
        }
    },
    buildExtendInfo: function(isCheck) {
        var index = 0;
        var extendInfoCheck = true;
        // build booking info
        $.each($('div.booking-info-item'), function(i, b) {
            index = i;
            var $b = $(b);
            var $secondTitle = $b.children('input[data-name = "secondTitle"]');
            var $content = $b.children('textarea[data-name = "content"]');
            var contentNum = $content.prop('data-plaintext-num');
            $b.children('input[data-name = "firstTitle"]').prop('name', 'priceTypeExtends['+ index + '].firstTitle');
            $secondTitle.prop('name', 'priceTypeExtends[' + index + '].secondTitle');
            $content.prop('name', 'priceTypeExtends[' + index + '].content');
            if (isCheck) {

               /* if (!$secondTitle.val() || $secondTitle.val() == "" || !$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {*/

                if (!$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: "请完善预订须知信息",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                if (contentNum > SailboatPriceType.BookingContentNumLimit) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: '预订须知内容字数过多(最多' + SailboatPriceType.BookingContentNumLimit + '字), 请删减!',
                        type: 'warn',
                        timeout: 2000
                    });
                    return false;
                }
            }
        });
        if (!extendInfoCheck) {
            return extendInfoCheck;
        }
        // build fee info
        $.each($('div.fee-info-item'), function(i, b) {
            index += 1;
            var $b = $(b);
            var $secondTitle = $b.children('input[data-name = "secondTitle"]');
            var $content = $b.children('textarea[data-name = "content"]');
            var contentNum = $content.prop('data-plaintext-num');
            $b.children('input[data-name = "firstTitle"]').prop('name', 'priceTypeExtends['+ index + '].firstTitle');
            $secondTitle.prop('name', 'priceTypeExtends[' + index + '].secondTitle');
            $content.prop('name', 'priceTypeExtends[' + index + '].content');
            if (isCheck) {
                /*if (!$secondTitle.val() || $secondTitle.val() == "" || !$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {*/
                if (!$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: "请完善费用说明信息",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                if (contentNum > SailboatPriceType.FeeContentNumLimit) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: '费用说明内容字数过多(最多' + SailboatPriceType.FeeContentNumLimit + '字), 请删减!',
                        type: 'warn',
                        timeout: 2000
                    });
                    return false;
                }
            }
        });
        if (!extendInfoCheck) {
            return extendInfoCheck;
        }
        // build refund info
        $.each($('div.refund-info-item'), function (i, b) {
            index += 1;
            var $b = $(b);
            var $secondTitle = $b.children('input[data-name = "secondTitle"]');
            var $content = $b.children('textarea[data-name = "content"]');
            var contentNum = $content.prop('data-plaintext-num');
            $b.children('input[data-name = "firstTitle"]').prop('name', 'priceTypeExtends['+ index + '].firstTitle');
            $secondTitle.prop('name', 'priceTypeExtends['+ index + '].secondTitle');
            $content.prop('name', 'priceTypeExtends['+ index + '].content');
            if (isCheck) {
/*
                if (!$secondTitle.val() || $secondTitle.val() == "" || !$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {*/

                if (!$content.val() || $content.val() == ""
                    || !contentNum || contentNum <= 0) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: "请完善退款说明信息",
                        type: 'warn',
                        timeout: 1000
                    });
                    return false;
                }
                if (contentNum > SailboatPriceType.RefundContentNumLimit) {
                    extendInfoCheck = false;
                    var top = $secondTitle.offset().top - 100;
                    $('body').animate({scrollTop: top}, 100);
                    $.messager.show({
                        msg: '退款说明内容字数过多(最多' + SailboatPriceType.RefundContentNumLimit + '字), 请删减!',
                        type: 'warn',
                        timeout: 2000
                    });
                    return false;
                }
            }
        });
        return extendInfoCheck;
    }
};
$(function() {
    SailboatPriceType.init();
});