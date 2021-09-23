/**
 * Created by hmly_03 on 2017/2/10.
 */
var PictureSort = {
    init: function() {
        PictureSort.initSort();
        PictureSort.initJsp();
    },
    initJsp: function() {
        $(".save").click(function() {
            PictureSort.saveProductImageSort();
        });
        $(".concle").click(function() {
            history.go(-1);
        });
    },
    initSort: function() {
        $( "#sortable" ).sortable();//拖拽排序
    },

    saveProductImageSort: function() {

        var extraData = {};
        $.each($(":input[name='ids']"), function(i, perInput) {
            extraData['ids['+ i +']'] = $(perInput).val();
        });

        $.form.commit({
            url: '/yhy/yhyMain/saveProductImageSort.jhtml',
            formId: '#image-form',
            extraData: extraData,
            success: function(data) {
                //data = eval('(' + data + ')');
                if (data.success) {
                    $.messager.show({
                        msg: data.errorMsg,
                        type: "success"
                    });
                    history.go(-1);
                } else {
                    $.messager.show({
                        msg: data.errorMsg,
                        type: "error"
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: "保存错误！",
                    type: "error"
                });
            }
        });
    }
}

$(function() {
    PictureSort.init();
});