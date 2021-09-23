/**
 *
 * Created by zzl on 2016/11/22.
 */
var HotelRoomTypeDetail = {
    HotelRoomItem: null,
    init: function() {
        HotelRoomTypeDetail.initComp();
        HotelRoomTypeDetail.initEvt();
        HotelRoomTypeDetail.initForm();
    },
    initComp: function() {
        // capacity sel
        $('#capacitySel').btComboBox();
        // default capacity 1
        $('#capacitySel').btComboBox('disable');
        // hotel room des editor
        KindEditor.create("#roomDescription", {
            resizeType: 0,
            readonlyMode : true,
            afterChange: function () {
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
        // hotel change rule editor
        KindEditor.create("#changeRule", {
            resizeType: 0,
            readonlyMode : true,
            afterChange: function () {
                this.sync();
            },
            afterBlur: function() {this.sync();},
            items: ['fontsize', 'forecolor', 'fontname', 'bold']
        });
    },
    initEvt: function() {
        // breakfast select
        $('#breakfastSel').one('click', 'span', function(event) {
            $(this).siblings().removeClass('selected');
            $(this).addClass('selected');
            $('#hasHreakfast').val($(this).attr('data-has-breakfast'));
        });
        // hotel room item(! must init after bind event)
        HotelRoomTypeDetail.HotelRoomItem = $($('.room-number-item').clone(true)[0]);
    },
    initForm: function() {
        // get form data
        HotelRoomTypeDetail.getHotelPriceInfo();
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
                        // show other data
                        // hotel room des
                        KindEditor.html('#roomDescription', $('#roomDescription').val());
                        // hotel change rule
                        KindEditor.html('#changeRule', $('#changeRule').val());
                        // hotel room numbers
                        var roomNumbers = result["roomNumbers"].split(",");
                        // clear room num info first
                        $('.room-number-group').empty();
                        $.each(roomNumbers, function(i, r) {
                            // clone and choose one element
                            var $roomNumItem = HotelRoomTypeDetail.HotelRoomItem.clone(true);
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
                        if (result.imgData && result.imgData.length > 0) {
                            $("#hotelRoomImgs").fileinput(FileinputUtil.readonlyImgCfg(result.imgData));
                        } else {
                            $('#hotelRoomImgs').replaceWith("<span style='line-height: 30px'>暂无图片</span>");
                        }
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
    }
};
$(function() {
    HotelRoomTypeDetail.init();
});