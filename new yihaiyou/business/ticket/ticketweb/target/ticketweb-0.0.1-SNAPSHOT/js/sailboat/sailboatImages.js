/**
 * Created by hmly_03 on 2017/2/10.
 */
var SailboatImages = {
    init: function() {
        SailboatImages.initSort();
        SailboatImages.initFrameHeight();
        SailboatImages.initJsp();
    },
    initJsp: function() {
        $(".save").click(function() {
            SailboatImages.saveProductImageSort();
        });
        $(".concle").click(function() {
            parent.$("#editPanel").dialog("close");
        });
    },
    initSort: function() {
        $( "#sortable" ).sortable();//拖拽排序
    },
    initFrameHeight: function() {
        var wiHeight = parent.$("#editPanel").height();
        var picBoxHeight = $(".pictureBox").height();
        if (picBoxHeight < wiHeight) {
            parent.$("#editIframe").height(wiHeight);
        } else {
            parent.$("#editIframe").height(picBoxHeight + 100);
        }
    },

    saveProductImageSort: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $('#image-form').form({
            url:'/ticket/ticket/saveProductImageSort.jhtml',
            success:function(data){
                data = eval('(' + data + ')');
                if (data.success) {
                    show_msg(data.errorMsg);
                    parent.$("#editPanel").dialog("close");
                } else {
                    show_msg(data.errorMsg);
                }
                $.messager.progress('close');
            }
        });
        // submit the form
        $('#image-form').submit();
    }
}

$(function() {
    SailboatImages.init();
});