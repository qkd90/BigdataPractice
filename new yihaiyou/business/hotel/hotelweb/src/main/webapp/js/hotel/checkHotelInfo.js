/**
 * Created by dy on 2016/11/24.
 */

var CheckHotelInfoContants = {
    starLevel: [{id:0, text:"无"}, {id:1, text:"一星级"}, {id:2, text:"二星级"}, {id:3, text:"三星级"}, {id:4, text:"四星级"}, {id:5, text:"五星级"}]
};
var CheckHotelInfo = {
    hotelServiceItem: $($('.service-item').clone()[0]),
    getHotelImagesUrl:'/hotel/hotel/getHotelImages.jhtml',
    init: function() {

        CheckHotelInfo.initHotelServiceAmenities();
        CheckHotelInfo.initAreaSel();
        CheckHotelInfo.initComp();
        CheckHotelInfo.initHotelImages();

        $('#hotelImgs').on('filebatchselected', function(event, files) {
            //$(".container").css("height", $(".container").height() + $(".kv-file-content").length / 4 * 200);
            window.parent.$("#editIframe").css("height", $(".container").height());
            //console.log($(".kv-file-content").length);
        });
    },
    initHotelImages: function() {
        $.post(CheckHotelInfo.getHotelImagesUrl, {productId: $("#hotelId").val()},
            function(result) {
            if (result.success) {
                $("#hotelImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelDesc/', result.imageList));
            } else {
                $("#hotelImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelDesc/'));
            }
                CheckHotelInfo.initFrameHeight();


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
                        var $serviceItem = CheckHotelInfo.hotelServiceItem.clone(true);
                        var $ck = $serviceItem.children('input[name = "hotel.serviceAmenities"]');
                        $ck.val(s.serviceId);
                        $ck.after(s.serviceName);

                        if ($("#hid_hotelServiceAmenities").val()) {
                            var ser = $("#hid_hotelServiceAmenities").val().split(",");
                            $.each(ser, function(i, per) {
                                if (s.serviceId == per) {
                                    $ck.attr('checked', "checked");
                                }
                                //$('input[name = "hotel.serviceAmenities"][value = '+ s +']').attr('checked', "checked");
                            });
                        }

                        $('#service_group').append($serviceItem);
                    });
                    // init the form
                    CheckHotelInfo.initForm();
                } else {
                    CheckHotelInfo.initForm();
                    $.messager.show({
                        msg: '加载酒店服务项出错! 请重试',
                        type: 'error'
                    });
                }
            },
            error: function() {
                CheckHotelInfo.initForm();
                $.messager.show({
                    msg: '加载酒店服务项出错! 请重试',
                    type: 'error'
                });
            }
        });

    },

    initFrameHeight: function() {
        window.parent.$("#editIframe").css("height", $(".container").height() + 50);
    },
    initComp: function() {
        // star combo and default star 3
        $('#hotelStar').btComboBox();
        if ($("#hid_hotelStar").val()) {
            $('#hotelStar').btComboBox({action:"select", value:$("#hid_hotelStar").val()});
        } else {
            $('#hotelStar').btComboBox({action:"select", value:"3"});
        }

        if ($("#hid_hotelCityId").val()) {
            $('#regionSel ul.regionUl li[data-city-id=' + $("#hid_hotelCityId").val() + ']').click();
        }

        KindEditor.create("#hotelIntro", {
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
                        msg: '民宿简介内容不能超过500字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#introLenth').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#introLenth').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // hotel policy editor
        KindEditor.create("#hotelPolicy", {
            resizeType: 0,
            afterChange: function () {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > 500) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0, 500);
                    this.text(strValue);
                    $.messager.show({
                        msg: '民宿简介内容不能超过500字符',
                        type: "warn",
                        timeout: 1000
                    });
                    //计算剩余字数
                    KindEditor('#policyLenth').html(hasNum);
                } else {
                    //计算剩余字数
                    KindEditor('#policyLenth').html(hasNum);
                }
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
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
            //$('#CheckHotelInfoForm').data('bootstrapValidator')
            //    .updateStatus('hotelArea', 'VALID')
            //    .validateField('hotelArea');
        });
    },
    initForm: function() {
        // form validate
        $('#hotelInfoForm').bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '#saveCheckHotelInfoBtn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                hotelName: {
                    selector: '#hotelName',
                    message: "民宿名称不正确",
                    validators: {
                        notEmpty: {
                            message: '请输入名宿名称'
                        },
                        stringLength: {
                            max: 30,
                            message: '民宿名称不能超过30个字符'
                        }
                    }
                },
                hotelArea: {
                    selector: '#hotelArea',
                    message: '酒店区域不正确',
                    validators: {
                        notEmpty: {
                            message: '请选择酒店区域'
                        }
                    }
                },
                hotelAddress: {
                    selector: '#hotelAddress',
                    message: '请完善酒店详细地址',
                    validators: {
                        notEmpty: {
                            message: '请完善酒店详细地址'
                        }
                    }
                },
                /*hotelPhone: {
                    selector: '#hotelPhone',
                    message: '请输入电话',
                    validators: {
                        notEmpty: {
                            message: '请完善电话'
                        },
                        regexp: {
                            regexp: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
                            message: '联系电话格式错误'
                        }
                    }
                },*/
                hotelIntro: {
                    selector: '#hotelIntro',
                    message: '请输入简介',
                    validators: {
                        notEmpty: {
                            message: '请完善简介'
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
                'hotel.policy': {
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
            submitHandler: CheckHotelInfo.doSaveCheckHotelInfo
        });
        // get form data
        //CheckHotelInfo.getCheckHotelInfo();
    },
    // get form data
    getCheckHotelInfo: function() {
        var hotelId = $('#hotelId').val();
        if (hotelId && hotelId != "") {
            $.form.load({
                url: '/yhy/yhyCheckHotelInfo/getYhyCheckHotelInfo.jhtml',
                formId: '#CheckHotelInfoForm',
                formData: {
                    id: hotelId,
                    'productimage.product.id' : hotelId,
                    'productimage.childFolder': 'hotel/hotelDesc/'
                },
                onLoadSuccess: function(result) {
                    // show other data
                    // hotel star
                    $('#hotelStar').btComboBox({action:"select", value: result['hotel.star']});
                    // hotel area sel
                    $('#regionSel ul.regionUl li[data-city-id=' + result['hotel.cityId'] + ']').click();
                    // hotel service amenities

                    var ser = result['hotel.serviceAmenities'].split(",");
                    $.each(ser, function(i, s) {
                        $('input[name = "hotel.serviceAmenities"][value = '+ s +']').attr('checked', "checked");
                    });
                    // load img data
                    $("#hotelImgs").fileinput(FileinputUtil.defaultImgCfg(FileinputUtil.PRO_TYPE.hotel, 'hotel/hotelDesc/', result.imgData));
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
    doSaveCheckHotelInfo: function(validator, form, submitButton) {
        // button status
        submitButton.button('loading');
        // construct img data
        var imgList = FileinputUtil.getImgData('#hotelImgs');


        var intro = $('#hotelIntro').val();
        if (!intro || intro == "") {
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
        // hotel policy
        var policy = $('#hotelPolicy').val();
        if (!policy || policy == "") {
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

        var extraData = {};
        $.each(imgList, function(i, img) {
            $.each(img, function(n, v) {
                extraData["productimages[" + i +"]." + n] = v;
            });
        });

        $.form.commit({
            formId: '#hotelInfoForm',
            url: '/hotel/hotel/saveCheckHotelInfo.jhtml',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        title: "民宿管理",
                        msg: "民宿信息保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            window.parent.$("#storage_dg").datagrid("load", {});
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
                    msg: "民宿保存错误! 稍候重试!",
                    type: "error"
                });
                submitButton.button('reset');
            }
        })



        /*

         $.each(imgList, function(i, img) {
         $.each(img, function(n, v) {
         //extraData["productimages[" + i +"]." + n] = v;
         $('#hotelImgs').append('<input type="hidden" name="productimages['+ i +'].'+ n +'" value="'+ v +'">');
         });
         });
        $.ajax({
            cache: true,
            type: "POST",
            url:"/hotel/hotel/saveCheckHotelInfo.jhtml",
            data:$('#hotelInfoForm').serialize(),// 你的formid
            async: false,
            error: function(request) {
                $.messager.show({
                    msg: "民宿保存错误! 稍候重试!",
                    type: "error"
                });
            },
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        title: "民宿管理",
                        msg: "民宿信息保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            window.parent.$("#editPanel").dialog("close");
                            // jump to hotel list
                            //window.location.href = "/yhy/yhyMain/toHomestay.jhtml"
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: "error"
                    });
                }
            }
        });*/
/*
        // submit form
        $('#CheckHotelInfoForm').submit({
            formId: '#CheckHotelInfoForm',
            url: '/hotel/hotel/saveCheckHotelInfo.jhtml',
            extraData: extraData,
            success: function(result) {
                if (result.success) {
                    // show result
                    $.messager.show({
                        title: "民宿管理",
                        msg: "民宿信息保存成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            // jump to hotel list
                            //window.location.href = "/yhy/yhyMain/toHomestay.jhtml"
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: "error"
                    });
                }
                // reset button status
                //submitButton.button('reset');
            },
            error: function() {
                $.messager.show({
                    msg: "民宿保存错误! 稍候重试!",
                    type: "error"
                });
                //submitButton.button('reset');
            }
        })*/
    },
    doCancel: function() {
        window.parent.$("#editPanel").dialog("close");
    }
};

$(function() {
    CheckHotelInfo.init();
})
