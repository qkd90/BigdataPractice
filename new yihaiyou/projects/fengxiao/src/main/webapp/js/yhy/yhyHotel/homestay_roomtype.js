/**
 *
 * Created by zzl on 2016/11/22.
 */
var HotelRoomType = {
    HotelRoomItem: null,
    HotelRoomDesNumLimit: 30,
    HotelChangeRuleNumLimit: 500,
    init: function() {
        HotelRoomType.initComp();
        HotelRoomType.initEvt();
        HotelRoomType.initForm();
    },
    initComp: function() {
        // capacity sel
        $('#capacitySel').btComboBox();
        // default capacity 1
        $('#capacitySel').btComboBox({action: "select", value: "1"});
        // hotel room des editor
        KindEditor.create("#roomDescription", {
            resizeType: 0,
            afterChange: function () {
                $('#roomDescription').prop('data-plaintext-num', this.count('text'));
                $('#roomDescription').nextAll('span.writLimit').html(this.count('text') + "/" + HotelRoomType.HotelRoomDesNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // hotel change rule editor
        KindEditor.create("#changeRule", {
            resizeType: 0,
            afterChange: function () {
                $('#changeRule').prop('data-plaintext-num', this.count('text'));
                $('#changeRule').nextAll('span.writLimit').html(this.count('text') + "/" + HotelRoomType.HotelChangeRuleNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // if new init album
        var hotelPriceId = $('#hotelPriceId').val();
        if (!hotelPriceId || hotelPriceId == "") {
            $("#hotelRoomImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelRoom/'));
        }
    },
    initEvt: function () {
        // room number opt
        $('span.delRoom').on('click', function(event) {
            event.stopPropagation();
            var length = $('div.room-number-item').length;
            if (length && length > 1) {
                $(this).parent('div.room-number-item').remove();
            } else {
                $.messager.show({
                    msg: "请至少保留一个房间",
                    type: "warn"
                });
            }
        });
        $('#addRoomDiv').on('click', function(event) {
            event.stopPropagation();
            // clone and choose one element
            var $roomNumItem = HotelRoomType.HotelRoomItem.clone(true);
            // clear old value
            var id = "roomNum_" + new Date().getTime();
            $roomNumItem.children('input[name = "roomNum"]').val(null);
            $('.room-number-group').append($roomNumItem);
        });
        // breakfast select
        $('#breakfastSel').on('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('#hasHreakfast').val($(this).attr('data-has-breakfast'));
        });
        // hotel room item(! must init after bind event)
        HotelRoomType.HotelRoomItem = $($('.room-number-item').clone(true)[0]);
    },
    initForm: function() {
        $('#hotelRoomTypeForm').bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '#savePriceInfoBtn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                roomName: {
                    selector: '#roomName',
                    message: '房型名称不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入房型名称'
                        }
                    }
                },
                roomDescription: {
                    selector: '#roomDescription',
                    message: '房型描述不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入房型描述'
                        },
                        stringLength: {
                            max: 500,
                            message: '房型描述不能超过500个字符'
                        }
                    }
                },
                changeRule: {
                    selector: '#changeRule',
                    message: "预订须知不正确",
                    validators: {
                        notEmpty: {
                            message: '请输入预订须知'
                        },
                        stringLength: {
                            max: 500,
                            message: '预订须知不能超过500个字符'
                        }
                    }
                }
            },
            submitHandler: HotelRoomType.doSavePriceInfo
        });
        // get form data
        HotelRoomType.getHotelPriceInfo();
    },
    // get form data
    getHotelPriceInfo: function() {
        var hotelPriceId = $('#hotelPriceId').val();
        if (hotelPriceId && hotelPriceId != "") {
            $.form.load({
                url: '/yhy/yhyHotelPrice/getYhyHotelPriceInfo.jhtml',
                formId: '#hotelRoomTypeForm',
                formData: {
                    id: hotelPriceId,
                    'productimage.childFolder': 'hotel/hotelRoom/'
                },
                onLoadSuccess: function (result) {
                    if (result.success) {
                        // hotel room des
                        KindEditor.html('#roomDescription', $('#roomDescription').val());
                        // hotel change rule
                        KindEditor.html('#changeRule', $('#changeRule').val());
                        // show other data
                        // hotel room numbers
                        var roomNumbers = result["roomNumbers"].split(",");
                        // clear room num info first
                        $('.room-number-group').empty();
                        $.each(roomNumbers, function(i, r) {
                            // clone and choose one element
                            var $roomNumItem = HotelRoomType.HotelRoomItem.clone(true);
                            // clear old value
                            $roomNumItem.children('input[name = "roomNum"]').val(r);
                            $('.room-number-group').append($roomNumItem);
                        });
                        // capacity select
                        $('#capacitySel').btComboBox({action: "select", value: result['hotelPrice.capacity']});
                        // breakfast
                        //var hasBreakfast = result['hotelPrice.breakfast'];
                        $('#breakfastSel span[data-has-breakfast='+ result['hotelPrice.breakfast'] +']').click();
                        // load img data
                        $("#hotelRoomImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelRoom/', result.imgData));
                    } else {
                        $.messager.show({
                            msg: result.msg,
                            type: 'error'
                        });
                    }
                },
                onLoadError: function () {
                    $.messager.show({
                        msg: "数据加载错误, 请重试!",
                        type: 'error'
                    });
                }
            });
        }
    },
    doSavePriceInfo: function(validator, form, submitButton) {
        submitButton.button('loading');
        // hotel room des
        var roomDescription = $('#roomDescription').val();
        var roomDesNum = $('#roomDescription').prop('data-plaintext-num');
        if (!roomDescription || roomDescription == "" || !roomDesNum || roomDesNum <= 0) {
            var top = $('#roomDescriptionLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写房型描述',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (roomDesNum > HotelRoomType.HotelRoomDesNumLimit) {
            var top = $('#roomDescriptionLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '房型描述字数过多(最多' + HotelRoomType.HotelRoomDesNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        var changeRule = $('#changeRule').val();
        var changeRuleNum = $('#changeRule').prop('data-plaintext-num');
        if (!changeRule || changeRule == "" || !changeRuleNum || changeRuleNum <= 0) {
            var top = $('#changeRuleLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写房型预订须知',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (changeRuleNum > HotelRoomType.HotelChangeRuleNumLimit) {
            var top = $('#changeRuleLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '房型预订须知字数过多(最多' + HotelRoomType.HotelChangeRuleNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        // room num check
        var roomNumCheck = true;
        $.each($('.room-number-group input[name = "roomNum"]'), function(i, e) {
            var $e = $(e);
            if (!$e.val() || $e.val() == "") {
                var top = $e.offset().top - 100;
                $('body').animate({scrollTop: top}, 100);
                $.messager.show({
                    msg: "请输入房间号码",
                    type: 'warn',
                    timeout: 1000
                });
                submitButton.button('reset');
                roomNumCheck = false;
                return false;
            }
        });
        if (!roomNumCheck) {
            return;
        }
        // construct img data
        var imgList = FileinputUtil.getImgData('#hotelRoomImgs');
        // image data check
        if (!imgList || imgList.length <= 0) {
            var top = $('#roomtype_photo_area').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请上传房型图片',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        // cover check
        if ($('.file-input span.kv-home-selected').length <= 0) {
            var top = $('#roomtype_photo_area').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请设置房型封面图片',
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
        var productId = $('#productId').val() && $('#productId').val() != "" ? $('#productId').val() : $('#hotelId').val();
        extraData["productId"] = productId;
        // submit form
        $.form.commit({
            url: '/yhy/yhyHotelPrice/savePriceInfo.jhtml',
            formId: '#hotelRoomTypeForm',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "房型保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            window.location.href = "/yhy/yhyMain/toHomeStayPriceList.jhtml";
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
                    msg: "房型信息保存错误! 稍后重试!",
                    type: "error"
                });
                // reset button status
                submitButton.button('reset');
            }
        })
    }
};
$(function() {
    HotelRoomType.init();
});