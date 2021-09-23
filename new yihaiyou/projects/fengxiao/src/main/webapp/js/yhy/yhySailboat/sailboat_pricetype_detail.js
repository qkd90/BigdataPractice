/**
 * Created by zzl on 2016/12/5.
 */
var SailboatPriceType = {
    BookingInfoItem: null,
    FeeInfoItem: null,
    RefundInfoItem: null,
    init: function() {
        SailboatPriceType.initComp();
        SailboatPriceType.initEvt();
        SailboatPriceType.initForm();
    },
    initComp: function() {},
    initEvt: function() {
        // condition refund sel
        $('#conditionRefundSel').one('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticketPrice.isConditionRefund"]').val($(this).attr('data-condition-refund'));
        });
        // today valid sel
        $('#todayValidSel').one('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticketPrice.isTodayValid"]').val($(this).attr('data-today-valid'));
        });
        SailboatPriceType.BookingInfoItem = $($('.booking-info-item').clone(true)[0]);
        SailboatPriceType.FeeInfoItem = $($('.fee-info-item').clone(true)[0]);
        SailboatPriceType.RefundInfoItem = $($('.refund-info-item').clone(true)[0]);
    },
    initForm: function() {
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
                    // load image data
                    if (result.imgData && result.imgData.length > 0) {
                        $("#ticketPriceImgs").fileinput(FileinputUtil.readonlyImgCfg(result.imgData));
                    } else {
                        $('#ticketPriceImgs').replaceWith("<span style='line-height: 30px'>暂无图片</span>");
                    }
                },
                onLoadError: function() {
                    $.messager.show({
                        msg: "数据加载错误, 请重试!",
                        type: 'error'
                    });
                }
            })
        }
    },
    showExtendInfo: function(extendList) {
        $.each(extendList, function(i, e) {
            if (e.firstTitle === "预订须知") {
                var $bookingInfoItem = SailboatPriceType.BookingInfoItem.clone(true);
                var $ta = $bookingInfoItem.children('textarea[name = "content"]');
                $bookingInfoItem.children('input[name = "secondTitle"]').val(e.secondTitle);
                $ta.val(e.content);
                $('#bookingInfoGroup').append($bookingInfoItem);
                KindEditor.create($ta, {
                    resizeType: 0,
                    readonlyMode: true,
                    afterChange: function () {this.sync();},
                    afterBlur: function() {this.sync();},
                    items: ['fontsize', 'forecolor', 'fontname', 'bold']
                });
            } else if(e.firstTitle === "费用说明") {
                var $feeInfoItem = SailboatPriceType.FeeInfoItem.clone(true);
                var $ta = $feeInfoItem.children('textarea[name = "content"]');
                $feeInfoItem.children('input[name = "secondTitle"]').val(e.secondTitle);
                $ta.val(e.content);
                $('#feeInfoGroup').append($feeInfoItem);
                KindEditor.create($ta, {
                    resizeType: 0,
                    readonlyMode: true,
                    afterChange: function () {this.sync();},
                    afterBlur: function() {this.sync();},
                    items: ['fontsize', 'forecolor', 'fontname', 'bold']
                });
            } else if (e.firstTitle === "退款说明") {
                var $refundInfoItem = SailboatPriceType.RefundInfoItem.clone(true);
                var $ta = $refundInfoItem.children('textarea[name = "content"]')
                $refundInfoItem.children('input[name = "secondTitle"]').val(e.secondTitle);
                $ta.val(e.content);
                $('#refundInfoGroup').append($refundInfoItem);
                KindEditor.create($ta, {
                    resizeType: 0,
                    readonlyMode: true,
                    afterChange: function () {this.sync();},
                    afterBlur: function() {this.sync();},
                    items: ['fontsize', 'forecolor', 'fontname', 'bold']
                });
            }
        });
        //
        if ($('div.booking-info-item').length <= 0) {
            $('#refundInfoGroup').append("<span style='line-height: 30px'>暂无信息</span>")
        }
        if ($('div.fee-info-item').length <= 0) {
            $('#feeInfoGroup').append("<span style='line-height: 30px'>暂无信息</span>");
        }
        if ($('div.refund-info-item').length <= 0) {
            $('#refundInfoGroup').append("<span style='line-height: 30px'>暂无信息</span>");
        }
    }
};
$(function() {
    SailboatPriceType.init();
});