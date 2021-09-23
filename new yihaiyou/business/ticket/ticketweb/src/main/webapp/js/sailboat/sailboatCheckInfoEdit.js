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
        SailboatInfo.initFrameHeight();
    },

    initComp: function() {
        // tips editor
        KindEditor.create("#ticketTips", {
            resizeType: 0,
            afterChange: function () {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > SailboatInfo.TipsNumLimit) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0, SailboatInfo.TipsNumLimit);
                    this.text(strValue);
                    $.messager.show({
                        msg: '小贴士内容不能超过300字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#ticketTipLength').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#ticketTipLength').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // scenic intro editor
        KindEditor.create("#scenicIntro", {
            resizeType: 0,
            afterChange: function () {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > SailboatInfo.ScenicIntroNumLimit) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0, SailboatInfo.ScenicIntroNumLimit);
                    this.text(strValue);
                    $.messager.show({
                        msg: '景点介绍内容不能超过300字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#scenicIntroLength').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#scenicIntroLength').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });

        $(".btn1").click(function() {
            window.parent.$("#editPanel").dialog("close");
        });
    },
    initEvt: function() {

        if ($(":input[name='ticket.ticketType']").val()) {
            $("#ticketTypeSel").children().removeClass('selected');
            $.each($("#ticketTypeSel").children(), function(i, per) {
                if ($(":input[name='ticket.ticketType']").val() == $(per).attr("data-ticket-type")) {
                    $(per).addClass('selected');
                    return false;
                }
            });
        }

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

        if ($(":input[name='ticket.scenicInfo.id']").val()) {
            $("#scenicSel").children().removeClass('selected');

            $.each($("#scenicSel").children(), function(i, per) {
                if ($(":input[name='ticket.scenicInfo.id']").val() == $(per).attr("data-scenic-id")) {
                    $(per).addClass('selected');
                    return false;
                }
            });
        }

        // scenic sel
        $('#scenicSel').on('click', 'span', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('input[name = "ticket.scenicInfo.id"]').val($(this).attr('data-scenic-id'));
        });
        // 帆船类型, 隐藏 "是否确认" 选择区域, 并默认为无需确认
        if ($(":input[name='ticket.ticketType']").val() == "sailboat") {
            $('#confirm_area').hide();
            $(":input[name='ticket.needConfirm']").val("false");
            $('#confirmSel span').removeClass('selected');
            $('#confirmSel span[data-is-needconfirm="false"]').addClass("selected");
        }
        if ($(":input[name='ticket.needConfirm']").val()) {
            $("#confirmSel").children().removeClass('selected');
            $.each($("#confirmSel").children(), function(i, per) {
                if ($(":input[name='ticket.needConfirm']").val() == $(per).attr("data-is-needconfirm")) {
                    $(per).addClass('selected');
                    return false;
                }
            });
        }

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
                        },
                        stringLength: {
                            max: 30,
                            message: '产品名称不能超过30个字符'
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
                /*ticketTips: {
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
                }*/
            },
            submitHandler: SailboatInfo.doSaveSailboatInfo
        });
        // get form data
        /*SailboatInfo.getSailboatInfo();*/
    },
    // get form data
    /*getSailboatInfo: function() {
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
    },*/
    doSaveSailboatInfo: function(validator, form, submitButton) {
        submitButton.button('loading');
        // hotel intro
        var ticketTips = $('#ticketTips').val();
        if (!ticketTips || ticketTips == "") {
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
        // scenic intro
        var scenicIntro = $('#scenicIntro').val();
        if (!scenicIntro || scenicIntro == "") {
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
        // handle ticketName
        $('input[name = "ticket.ticketName"]').val($('#ticketName').val());
        // submit form
        $.form.commit({
            formId: '#sailboatInfoForm',
            url: '/ticket/ticket/saveSailboatInfo.jhtml',
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        msg: "保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            TicketUtil.buildSailboat(result.id);
                            window.parent.$("#show_dg").datagrid("load", {});
                            window.parent.$("#editPanel").dialog("close");
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
    },
    initFrameHeight: function() {
        window.parent.$("#editIframe").css("height", $(".sailIndex").height() + 50);
    }
};
$(function() {
    SailboatInfo.init();
});