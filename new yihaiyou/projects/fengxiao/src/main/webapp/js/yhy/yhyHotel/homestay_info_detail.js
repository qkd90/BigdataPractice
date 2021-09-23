/**
 * Created by zzl on 2016/11/18.
 */
var HotelInfoDetail = {
    hotelServiceItem: $($('.service-item').clone()[0]),
    init: function() {
        HotelInfoDetail.initComp();
        HotelInfoDetail.initAreaSel();
        HotelInfoDetail.initHotelServiceAmenities();
    },

    initComp: function() {
        // star combo and default star 3
        $('#hotelStar').btComboBox();
        // disable star combo
        $('#hotelStar').btComboBox('disable');
        // hotel intro editor
        KindEditor.create("#hotelIntro", {
            resizeType: 0,
            readonlyMode : true,
            afterChange: function () {
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        KindEditor.create("#hotelPolicy", {
            resizeType: 0,
            readonlyMode : true,
            afterChange: function () {
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
    },
    initAreaSel: function() {
        // region sel
        $('#regionSel ul.regionUl li').one('click', function(event) {
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
                        var $serviceItem = HotelInfoDetail.hotelServiceItem.clone(true);
                        var $ck = $serviceItem.children('input[name = "hotel.serviceAmenities"]');
                        $ck.val(s.serviceId);
                        $ck.after(s.serviceName);
                        $('#service_group').append($serviceItem);
                    });
                    // init the form
                    HotelInfoDetail.initForm();
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
        // get form data
        HotelInfoDetail.getHotelInfo();
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
                        // load img data / read only
                        if (result.imgData && result.imgData.length > 0) {
                            $("#hotelImgs").fileinput(FileinputUtil.readonlyImgCfg(result.imgData));
                        } else {
                            $('#hotelImgs').replaceWith("<span style='line-height: 30px'>暂无图片</span>");
                        }
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
    HotelInfoDetail.init();
});