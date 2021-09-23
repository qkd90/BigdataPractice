/**
 * Created by zzl on 2016/12/2.
 */
var SailboatInfo = {
    TipsNumLimit: 300,
    ScenicIntroNumLimit: 300,
    init: function() {
        SailboatInfo.initComp();
        SailboatInfo.initEvt();
        SailboatInfo.initForm();
    },

    initComp: function() {
        // tips editor
        KindEditor.create("#ticketTips", {
            resizeType: 0,
            afterChange: function () {
                $('#ticketTips').prop('data-plaintext-num', this.count('text'));
                $('#ticketTips').nextAll('span.writLimit').html(this.count('text') + "/" + SailboatInfo.TipsNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // scenic intro editor
        KindEditor.create("#scenicIntro", {
            resizeType: 0,
            afterChange: function () {
                $('#scenicIntro').prop('data-plaintext-num', this.count('text'));
                $('#scenicIntro').nextAll('span.writLimit').html(this.count('text') + "/" + SailboatInfo.ScenicIntroNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
    },
    initEvt: function() {
        // ticket type sel
        $('#ticketTypeSel').on('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            var ticketType = $(this).attr('data-ticket-type');
            if (ticketType == "sailboat") {
                $('#confirm_area').hide();
                $('#confirmSel span[data-is-needconfirm="false"]').click();
            } else {
                $('#confirm_area').show();
            }
            $('input[name = "ticket.ticketType"]').val($(this).attr('data-ticket-type'));
        });
        // scenic sel
        $('#scenicSel').on('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.scenicInfo.id"]').val($(this).attr('data-scenic-id'));
        });
        // confirm sel
        $('#confirmSel').on('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.needConfirm"]').val($(this).attr('data-is-needconfirm'));
        });
    },
    initForm: function() {
        $('#sailboatInfoForm').bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '#saveTicketInfoBtn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                ticketName: {
                    selector: '#ticketName',
                    message: '产品名称不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入名称名称'
                        }
                    }
                },
                address: {
                    selector: '#ticketAddress',
                    message: '地址不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入地址'
                        }
                    }
                },
                telephone: {
                    selector: '#ticketPhone',
                    message: '联系电话不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入联系电话'
                        },
                        regexp: {
                            regexp: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
                            message: '联系电话格式错误'
                        }
                    }
                },
                businessHours: {
                    selector: '#businessHours',
                    message: '营业时间不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入营业时间'
                        }
                    }
                },
                ticketTips: {
                    selector: '#ticketTips',
                    message: '小贴士不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入小贴士'
                        },
                        stringLength: {
                            max: 100,
                            message: '小贴士不能超过100个字符'
                        }
                    }
                },
                scenicIntro: {
                    selector: '#scenicIntro',
                    message: '景点介绍不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入景点介绍'
                        },
                        stringLength: {
                            max: 100,
                            message: '景点介绍不能超过100个字符'
                        }
                    }
                }
            },
            submitHandler: SailboatInfo.doSaveSailboatInfo
        });
        // get form data
        SailboatInfo.getSailboatInfo();
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
    doSaveSailboatInfo: function(validator, form, submitButton) {
        submitButton.button('loading');
        // ticket tips
        var ticketTips = $('#ticketTips').val();
        var tipsNum = $('#ticketTips').prop('data-plaintext-num');
        if (!ticketTips || ticketTips == "" || !tipsNum || tipsNum <= 0) {
            var top = $('#ticketTipsLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写小贴士',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (tipsNum > SailboatInfo.TipsNumLimit) {
            var top = $('#ticketTipsLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '小贴士字数过多(最多' + SailboatInfo.TipsNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        // scenic intro
        var scenicIntro = $('#scenicIntro').val();
        var scenicIntroNum = $('#scenicIntro').prop('data-plaintext-num');
        if (!scenicIntro || scenicIntro == "" || !scenicIntroNum || scenicIntroNum <= 0) {
            var top = $('#scenicIntroLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写景点介绍',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (scenicIntroNum > SailboatInfo.ScenicIntroNumLimit) {
            var top = $('#scenicIntroLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '景点介绍字数过多(最多' + SailboatInfo.ScenicIntroNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        // handle ticketName
        $('input[name = "ticket.ticketName"]').val($('#ticketName').val());
        // submit form
        $.form.commit({
            formId: '#sailboatInfoForm',
            url: '/yhy/yhySailboatInfo/saveSailboatInfo.jhtml',
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        msg: "保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            window.location.href = "/yhy/yhyMain/toSailing.jhtml"
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
                    msg: "保存错误! 稍候重试!",
                    type: "error"
                });
                submitButton.button('reset');
            }
        })
    }
};
$(function() {
    SailboatInfo.init();
});