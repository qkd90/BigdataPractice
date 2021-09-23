/**
 * Created by dy on 2016/11/25.
 */
var CheckHotelPriceTypeContants = {

};
var CheckHotelPriceType = {
    HotelRoomItem: null,
    getHotelPriceImagesUrl:'/hotel/hotel/getHotelImages.jhtml',
    init: function() {
        CheckHotelPriceType.initComp();
        CheckHotelPriceType.initForm();
        CheckHotelPriceType.initHotelPriceImages();
    },

    doCancel: function() {
        window.parent.$("#editPanel").dialog("close");
    },
    initComp: function() {

        CheckHotelPriceType.HotelRoomItem = $($('.room-number-item').clone(true)[0]);
        // capacity sel
        $('#capacitySel').btComboBox();

        if ($("#hide_capacitySel").val()) {
            $('#capacitySel').btComboBox({action: "select", value: $("#hide_capacitySel").val()});
        } else {
            $('#capacitySel').btComboBox({action: "select", value: 1});
        }

        // room number opt

        $('#addRoomDiv').on('click', function(event) {
            event.stopPropagation();
            // clone and choose one element
            var $roomNumItem = CheckHotelPriceType.HotelRoomItem.clone(true);
            // clear old value
            var id = "roomNum_" + new Date().getTime();
            $roomNumItem.children('input[name = "hotelPrice.roomNum"]').val(null);
            $('.room-number-group').append($roomNumItem);
        });


        var roomNumbers = [];
        if ($("#hide_room_number").val()) {
            roomNumbers = $("#hide_room_number").val().split(",");
            // clear room num info first
            $('.room-number-group').empty();
            $.each(roomNumbers, function(i, r) {
                // clone and choose one element
                var $roomNumItem = CheckHotelPriceType.HotelRoomItem.clone(true);
                // clear old value
                $roomNumItem.children('input[name = "hotelPrice.roomNum"]').val(r);
                $('.room-number-group').append($roomNumItem);
            });
        } else {

        }


        // breakfast select
        $('#breakfastSel').on('click', 'span', function(event) {
            $('#hasHreakfast').val($(this).attr('data-has-breakfast'));
            $("#breakfastSel").children().removeClass("selected");
            $(this).addClass("selected");
        });
        if ($("#hasHreakfast").val()) {
            $('#breakfastSel span[data-has-breakfast='+ $("#hasHreakfast").val() +']').click();
        }



        // if new init album
        var hotelPriceId = $('#hotelPriceId').val();
        if (!hotelPriceId || hotelPriceId == "") {
            $("#hotelRoomImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelRoom/'));
        }
        // hotel room item(! must init after bind event)
        CheckHotelPriceType.HotelRoomItem = $($('.room-number-item').clone(true)[0]);

        $('#hotelRoomImgs').on('filebatchselected', function(event, files) {
            //$(".container").css("height", $(".container").height() + $(".kv-file-content").length / 4 * 200);
            window.parent.$("#editIframe").css("height", $(".roomType").height());
            //console.log($(".kv-file-content").length);
        });


        KindEditor.create("#roomDescription", {
            resizeType: 0,
            afterChange: function () {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > 30) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0, 30);
                    this.text(strValue);
                    $.messager.show({
                        msg: '房型描述内容不能超过30字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#roomDescriptionLength').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#roomDescriptionLength').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // hotel change rule editor
        KindEditor.create("#changeRule", {
            resizeType: 0,
            afterChange: function () {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > 500) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0,500);
                    this.text(strValue);
                    $.messager.show({
                        msg: '预定须知内容不能超过500字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#changeRuleLength').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#changeRuleLength').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
    },

    onDelRoom: function(target) {
        var length = $('div.room-number-item').length;
        if (length && length > 1) {
            $(target).parent('div.room-number-item').remove();
        } else {
            $.messager.show({
                msg: "请至少保留一个房间",
                type: "warn"
            });
        }
    },

    initHotelPriceImages: function() {
        $.post(CheckHotelPriceType.getHotelPriceImagesUrl, {productId: $("#hotelId").val(), typePriceId: $("#hotelPriceId").val()},
            function(result) {
                if (result.success) {
                    $("#hotelRoomImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelRoom/', result.imageList));
                } else {
                    $("#hotelRoomImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelRoom/'));
                }
                CheckHotelPriceType.initFrameHeight();
            });

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
                        },
                        stringLength: {
                            max: 30,
                            message: '房型名称不能超过30个字符'
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
            submitHandler: CheckHotelPriceType.doSavePriceInfo
        });
    },
    doSavePriceInfo: function(validator, form, submitButton) {
        submitButton.button('loading');


        var roomDescription = $('#roomDescription').val();
        if (!roomDescription || roomDescription == "") {
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
        var changeRule = $('#changeRule').val();
        if (!changeRule || changeRule == "") {
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


        // room num check
        var roomNumCheck = true;
        $.each($('.room-number-group input[name = "hotelPrice.roomNum"]'), function(i, e) {
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
                extraData["productimages[" + i + "]." + n] = v;
            });
        });
        extraData["productId"] = $('#productId').val();
        // submit form
        $.form.commit({
            url: '/hotel/hotel/saveCheckHotelPriceInfo.jhtml',
            formId: '#hotelRoomTypeForm',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        title: "民宿管理",
                        msg: "房型保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            window.parent.$("#storage_type_dg").datagrid("load", {});
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
                    msg: "房型信息保存错误! 稍候重试!",
                    type: "error"
                });
                // reset button status
                submitButton.button('reset');
            }
        })
    },
    initFrameHeight: function() {
        window.parent.$("#editIframe").css("height", $(".roomType").height() + 50);
    }
};
$(function() {
    CheckHotelPriceType.init();
});
