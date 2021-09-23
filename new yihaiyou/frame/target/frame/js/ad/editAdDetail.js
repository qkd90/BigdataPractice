/**
 * Created by vacuity on 15/10/26.
 */


var AdsDetail={
    AdsContanst: 'ggs/banner/',
    init:function(){
        AdsDetail.uploadAdImge();
        AdsUtil.initComp();
        AdsDetail.initCommp();
    },
    initCommp: function() {
        if ($("#filePath").val()) {
            $('#imgView img').attr('src', QINIU_BUCKET_URL + $("#filePath").val());
            $('#imgView').show();
        }
    },
    uploadAdImge: function() {
        // 图片上传
        KindEditor.ready(function (K) {
            var uploadbutton = K.uploadbutton({
                button: K('#add_descpic')[0],
                fieldName: 'resource',
                width:200,
                url: '/ad/ad/uploadImg.jhtml',
                extraParams: {oldFilePath: $('#filePath').val(), folder: AdsDetail.AdsContanst},
                afterUpload: function (result) {
                    $.messager.progress("close");
                    if (result.success == true) {
                        var url = K.formatUrl(result.url, 'absolute');
                        $('#filePath').val(url);
                        $('#imgView img').attr('src', url);
                        $('#imgView').show();
                    } else {
                        show_msg("图片上传失败");
                    }
                },
                afterError: function (str) {
                    show_msg("图片上传失败");
                }
            });
            uploadbutton.fileBox.change(function (e) {
                var filePath = uploadbutton.fileBox[0].value;
                if (!filePath) {
                    show_msg("图片格式不正确");
                    return;
                }
                var suffix = filePath.substr(filePath.lastIndexOf("."));
                suffix = suffix.toLowerCase();
                if ((suffix != '.jpg') && (suffix != '.gif') && (suffix != '.jpeg') && (suffix != '.png') && (suffix != '.bmp')) {
                    show_msg("图片格式不正确");
                    return;
                }
                $.messager.progress({
                    title: '温馨提示',
                    text: '图片上传中,请耐心等待...'
                });
                uploadbutton.submit();
            });
        });
    },

    deleteImg: function() {
        $('#filePath').val("");
        $('#imgView img').attr('src', "");
        $('#imgView').hide();
    },

    commitForm: function() {
        $("#adsform").form('submit', {
            url: '/ad/ad/saveAds.jhtml',
            success: function (data) {
                data = eval('(' + data + ')');
                if (data.success) {
                    //$("#" + panelName).dialog("close");
                    showMsgPlus("广告管理", data.msg);
                    window.parent.$("#editPanel").dialog("close");
                    window.parent.$("#dg").datagrid('reload');
                    //$("#dg").datagrid('reload');
                } else if (!data.success) {
                    $.messager.alert('广告管理', data.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('广告管理', "操作失败,请重试!", 'error');
            }
        });
    },

    doCancel: function() {
        window.parent.$("#editPanel").dialog("close");
        //window.parent.$("#dg").datagrid('reload');
    }
};

$(function(){
    AdsDetail.init();
});

