/**
 * Created by zzl on 2016/11/18.
 */
var HotelInfo = {
    hotelServiceItem: $($('.service-item').clone()[0]),
    HotelIntroNumLimit: 500,
    HotelPolicyNumLimit: 500,
    init: function() {
        HotelInfo.initComp();
        HotelInfo.initEvt();
        HotelInfo.initAreaSel();
        HotelInfo.initHotelServiceAmenities();
    },

    initComp: function() {
        // star combo and default star 3
        $('#hotelStar').btComboBox();
        $('#hotelStar').btComboBox({action:"select", value:"3"});
        // hotel intro editor
        KindEditor.create("#hotelIntro", {
            resizeType: 0,
            afterChange: function () {
                $('#hotelIntro').prop('data-plaintext-num', this.count('text'));
                $('#hotelIntro').nextAll('span.writLimit').html(this.count('text') + "/" + HotelInfo.HotelIntroNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // hotel policy editor
        KindEditor.create("#hotelPolicy", {
            resizeType: 0,
            afterChange: function () {
                $('#hotelPolicy').prop('data-plaintext-num', this.count('text'));
                $('#hotelPolicy').nextAll('span.writLimit').html(this.count('text') + "/" + HotelInfo.HotelPolicyNumLimit);
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // if new, init album
        var hotelId = $('#hotelId').val();
        if (!hotelId || hotelId == "") {
            $("#hotelImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelDesc/'));
        }
    },
    initEvt: function() {
        var geo = new BMap.Geocoder();
        $('#hotelAddress').on('blur', function(event) {
            event.stopPropagation();
            var hotelArea = $('#hotelArea').val();
            if (!hotelArea || hotelArea == "") {
                $.messager.show({
                    msg: '请选择民宿区域!',
                    type: 'error',
                    timeout: 2000
                });
                return;
            }
            var hotelAddress = $('#hotelAddress').val();
            if (!hotelAddress || hotelAddress == "") {
                $.messager.show({
                    msg: '请输入民宿详细地址!',
                    type: 'error',
                    timeout: 2000
                });
                return;
            }
            var address = hotelArea + hotelAddress;
            geo.getPoint(address, function(point) {
                if (point) {
                    $('#hotelLng').val(point.lng);
                    $('#hotelLat').val(point.lat);
                } else {
                    // fail to convert address to geo info
                    console.log("can't not get geo info by hotel address :" + address);
                }
            });
        });
    },
    initAreaSel: function() {
        // show address box
        $('#hotelArea').on('click', function(event) {
            event.stopPropagation();
            $('#addressSelBox').show();
        });
        // hide address box
        $('.roomBaseMess').on('click', function(event) {
            $('#addressSelBox').hide();
        });
        $('#addressSelBox').on('click', function(event) {
            event.stopPropagation();
        });
        // switch title tab
        $('#addressTitleTab li').on('click', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('active');
            $(this).addClass('active');
            $('#' + $(this).attr('data-show-box')).siblings().removeClass('disb');
            $('#' + $(this).attr('data-show-box')).addClass('disb');
        });
        // province sel
        $('#provinceSel ul.provinceUl li').on('click', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('#provinceTitleTab').siblings().removeClass('active');
            $('#provinceTitleTab').removeClass('active');
            $('#provinceSel').siblings('div.selbox').removeClass('disb');
            $('#provinceSel').removeClass('disb');
            // auto switch to citySel
            $('#cityTitleTab').addClass('active');
            $('#citySel').addClass('disb');

        });
        // city sel
        $('#citySel ul.cityUl li').on('click', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('#cityTitleTab').removeClass('active');
            $('#cityTitleTab').siblings('div.selbox').removeClass('active');
            $('#citySel').removeClass('disb');
            // auto switch to regionSel
            $('#regionTitleTab').addClass('active');
            $('#regionSel').addClass('disb');
        });
        // region sel
        $('#regionSel ul.regionUl li').on('click', function(event) {
            event.stopPropagation();
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            //$('#regionTitleTab').removeClass('active');
            $('#regionTitleTab').siblings('div.selbox').removeClass('active');
            //$('#regionSel').removeClass('disb');
            // set data and close address box
            $('#cityId').val($(this).attr('data-city-id'));
            $('#addressSelBox').hide();
            var p = $.trim($('#provinceSel ul.provinceUl li.selected').html()) + "省";
            var c = $.trim($('#citySel ul.cityUl li.selected').html()) + "市";
            var r = $.trim($('#regionSel ul.regionUl li.selected').html()) + "区";
            $('#hotelArea').val(p + c + r);
            // for ie
            $('#hotelArea').focus();
            // validate
            $('#hotelInfoForm').data('bootstrapValidator')
                .updateStatus('hotelArea', 'VALID')
                .validateField('hotelArea');
        });
    },
    initHotelServiceAmenities: function() {
        $.ajax({
            url: '/yhy/yhyHotelInfo/getHotelServiceAmenities.jhtml',
            type: 'post',
            dataType: 'json',
            data: {},
            success: function(result) {
                // load hotel service amenities
                if (result.success) {
                    $('#service_group').empty();
                    $.each(result.serviceList, function(i, s) {
                        var $serviceItem = HotelInfo.hotelServiceItem.clone(true);
                        var $ck = $serviceItem.children('input[name = "hotel.serviceAmenities"]');
                        $ck.val(s.serviceId);
                        $ck.after(s.serviceName);
                        $('#service_group').append($serviceItem);
                    });
                    // init the form
                    HotelInfo.initForm();
                } else {
                    $.messager.show({
                        msg: '加载酒店服务项出错! 请重试',
                        type: 'error'
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: '加载酒店服务项出错! 请重试',
                    type: 'error'
                });
            }
        })
    },
    initForm: function() {
        // form validate
        $('#hotelInfoForm').bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '#saveHotelInfoBtn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                hotelName: {
                    selector: '#hotelName',
                    message: '民宿名称不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入名宿名称'
                        }
                    }
                },
                hotelArea: {
                    selector: '#hotelArea',
                    message: '民宿区域不正确',
                    validators: {
                        notEmpty: {
                            message: '请选择民宿区域'
                        }
                    }
                },
                hotelAddress: {
                    selector: '#hotelAddress',
                    message: '民宿详细地址不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入民宿详细地址'
                        }
                    }
                },
                //hotelPhone: {
                //    selector: '#hotelPhone',
                //    message: '联系电话不正确',
                //    validators: {
                //        notEmpty: {
                //            message: '请输入民宿联系电话'
                //        },
                //        regexp: {
                //            regexp: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
                //            message: '联系电话格式错误'
                //        }
                //    }
                //},
                hotelIntro: {
                    selector: '#hotelIntro',
                    message: '民宿简介不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入民宿简介'
                        },
                        stringLength: {
                            max: 500,
                            message: '民宿简介不能超过500个字符'
                        }
                    }
                },
                'hotel.serviceAmenities': {
                    message: '民宿服务项不正确',
                    validators: {
                        notEmpty: {
                            message: '请选择设施服务'
                        }
                    }
                },
                hotelPolicy: {
                    selector: '#hotelPolicy',
                    message: '民宿政策不正确',
                    validators: {
                        notEmpty: {
                            message: '请输入民宿政策信息'
                        },
                        stringLength: {
                            max: 500,
                            message: '民宿政策不能超过500个字符'
                        }
                    }
                }
            },
            submitHandler: HotelInfo.doSaveHotelInfo
        });
        // get form data
        HotelInfo.getHotelInfo();
    },
    // get form data
    getHotelInfo: function() {
        var hotelId = $('#hotelId').val();
        if (hotelId && hotelId != "") {
            $.form.load({
                url: '/yhy/yhyHotelInfo/getYhyHotelInfo.jhtml',
                formId: '#hotelInfoForm',
                formData: {
                    id: hotelId,
                    'productimage.product.id' : hotelId,
                    'productimage.childFolder': 'hotel/hotelDesc/'
                },
                onLoadSuccess: function(result) {
                    if (result.success) {
                        // show other data
                        // hotel star
                        $('#hotelStar').btComboBox({action:"select", value: result['hotel.star'] + ""});
                        // hotel area sel
                        $('#regionSel ul.regionUl li[data-city-id=' + result['hotel.cityId'] + ']').click();
                        // hotel intro
                        KindEditor.html('#hotelIntro', $('#hotelIntro').val());
                        // hotel policy
                        KindEditor.html('#hotelPolicy', $('#hotelPolicy').val());
                        // hotel service amenities
                        var ser = result['hotel.serviceAmenities'].split(",");
                        $.each(ser, function(i, s) {
                            $('input[name = "hotel.serviceAmenities"][value = '+ s +']').attr('checked', "checked");
                        });
                        // load img data
                        $("#hotelImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelDesc/', result.imgData));
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
    // 获取图片数据
    getImgData : function() {
        var imgData = FileinputUtil.getImgData('#hotelImgs');
        for (var i = 0; i < imgData.length; i++) {
            console.log(imgData[i].proType + '|' + imgData[i].path);
        }
        var files = $('#hotelImgs').fileinput('getFileStack'); // returns file list selected
        console.log('FileStack size is ' + files.length);
    },
    doSaveHotelInfo: function(validator, form, submitButton) {
        // button status
        submitButton.button('loading');
        // hotel intro
        var intro = $('#hotelIntro').val();
        var introNum = $('#hotelIntro').prop('data-plaintext-num');
        if (!intro || intro == "" || !introNum || introNum <= 0) {
            var top = $('#hotelIntroLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写民宿简介',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (introNum > HotelInfo.HotelIntroNumLimit) {
            var top = $('#hotelIntroLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '民宿简介字数过多(最多' + HotelInfo.HotelIntroNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        // hotel policy
        var policy = $('#hotelPolicy').val();
        var policyNum = $('#hotelPolicy').prop('data-plaintext-num');
        if (!policy || policy == "" || !policyNum || policyNum <= 0) {
            var top = $('#hotelPolicyLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请填写民宿政策',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        if (policyNum > 500) {
            var top = $('#hotelPolicyLab').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '民宿政策字数过多(最多' + HotelInfo.HotelPolicyNumLimit + '字), 请删减!',
                type: "warn",
                timeout: 2000
            });
            submitButton.button('reset');
            return;
        }
        // construct img data
        var imgList = FileinputUtil.getImgData('#hotelImgs');
        // image data check
        if (!imgList || imgList.length <= 0) {
            var top = $('#hotel_photo_area').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请上传民宿图片',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        // cover check
        if ($('.file-input span.kv-home-selected').length <= 0) {
            var top = $('#hotel_photo_area').offset().top - 100;
            $('body').animate({scrollTop: top}, 100);
            $.messager.show({
                msg: '请设置民宿封面图片',
                type: "warn",
                timeout: 1000
            });
            submitButton.button('reset');
            return;
        }
        var extraData = {};
        $.each(imgList, function(i, img) {
            $.each(img, function(n, v) {
                extraData["imgList[" + i +"]." + n] = v;
            });
        });
        // submit form
        $.form.commit({
            formId: '#hotelInfoForm',
            url: '/yhy/yhyHotelInfo/saveHotelInfo.jhtml',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        msg: "民宿信息保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            window.location.href = "/yhy/yhyMain/toHomestay.jhtml"
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
                    msg: "民宿保存错误! 稍候重试!",
                    type: "error"
                });
                submitButton.button('reset');
            }
        })
    }
};
$(function() {
    HotelInfo.init();
});