/**
 * Created by zzl on 2016/12/2.
 */
var SailboatInfoDetail = {
    init: function() {
        SailboatInfoDetail.initComp();
        SailboatInfoDetail.initEvt();
        SailboatInfoDetail.initForm();
    },

    initComp: function() {
        // tips editor
        KindEditor.create("#ticketTips", {
            resizeType: 0,
            readonlyMode: true,
            afterChange: function () {this.sync();},
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // scenic intro editor
        KindEditor.create("#scenicIntro", {
            resizeType: 0,
            readonlyMode: true,
            afterChange: function () {this.sync();},
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
    },
    initEvt: function() {
        // ticket type sel
        $('#ticketTypeSel').one('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.ticketType"]').val($(this).attr('data-ticket-type'));
        });
        // scenic sel
        $('#scenicSel').one('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.scenicInfo.id"]').val($(this).attr('data-scenic-id'));
        });
        // confirm sel
        $('#confirmSel').one('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.needConfirm"]').val($(this).attr('data-is-needconfirm'));
        });
    },
    initForm: function() {
        // get form data
        SailboatInfoDetail.getSailboatInfo();
    },
    // get form data
    getSailboatInfo: function() {
        var ticketId = $('#ticketId').val();
        if (ticketId && ticketId != "") {
            $.form.load({
                url: '/yhy/yhySailboatInfo/getSailboatInfo.jhtml',
                formId: '#sailboatInfoForm',
                formData: {id: ticketId},
                onLoadSuccess: function(result) {
                    if (result.success) {
                        // ticket tips
                        KindEditor.html('#ticketTips', $('#ticketTips').val());
                        // scenic intro
                        KindEditor.html('#scenicIntro', $('#scenicIntro').val());
                        // ticket type sel
                        $('#ticketTypeSel span[data-ticket-type="' + result['ticket.ticketType'] + '"]').click();
                        // scenic sel
                        $('#scenicSel span[data-scenic-id="' + result['ticket.scenicInfo.id'] + '"]').click();
                        // need confirm sel
                        $('#confirmSel span[data-is-needconfirm="' + result['ticket.needConfirm'] + '"]').click();
                    } else {
                        $.messager.show({
                            msg: result.msg,
                            type: 'error'
                        });
                    }
                },
                onLoadError: function() {
                    $.messager.show({
                        msg: "数据加载错误, 请重试!",
                        type: 'error'
                    });
                }
            });
        }
    },
};
$(function() {
    SailboatInfoDetail.init();
});