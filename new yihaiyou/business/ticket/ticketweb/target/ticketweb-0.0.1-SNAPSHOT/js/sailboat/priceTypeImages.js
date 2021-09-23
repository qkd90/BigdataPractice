/**
 * Created by dy on 2016/10/22.
 */
var PriceTypeImages = {

    infoImg:'sailboat/info/',

    init: function() {
        PriceTypeImages.initCom();
        PriceTypeImages.initData();
    },

    initCom: function() {
        var ticketType = $("#ticketType").val();
        if ( ticketType == 'sailboat') {
            PriceTypeImages.infoImg = 'sailboat/info/';
        } else if (ticketType == 'yacht') {
            PriceTypeImages.infoImg = 'yacht/info/';
        }

        // 图片上传
        $('#imagePanel').diyUpload({
            url: "/ticket/ticketPrice/updatePriceTypeImages.jhtml",
            success: function (data, $fileBox) {
                var address = BizConstants.QINIU_DOMAIN + data.path;

                console.log(data);

                $fileBox.remove();
                showImage($('#imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#coverParent').html('<div id="coverBox"></div>');
                    $('#coverPath').prop('value', data.path);
                    $('#imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();

                    $.each($("input[name='imgPaths']"), function(i, per) {
                        $(per).attr("cover-flag", 0);
                    });
                    $("#input_"+ data.id +"").attr("cover-flag", 1);
                    showImageWithoutCancel($('#coverBox'), address, data.id);
                });
                $('#fileBox_' + data.id).find('.diyCancel').click(function () {
                    if (data.path == $('#coverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#coverBox').next('div.parentFileBox').remove();
                        $('#coverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "' cover-flag='"+ 0 +"' name='"+ data.imgDesc +"'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '添加图片',
            chunked: true,
            // 分片大小
            chunkSize: 512 * 1024,
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit: 50,
            fileSizeLimit: 500000 * 1024,
            fileSingleSizeLimit: 50000 * 1024,
            formData: {
                section: PriceTypeImages.infoImg
            }
        });
    },
    initData: function() {
        var url = "/ticket/ticketPrice/getPriceTypeImagesList.jhtml?ticketPriceId="+$("#ticketPriceId").val()+"&ticketId="+$("#ticketId").val();
        $.post(
            url,
            {
                'ticketPrice.id':$("#ticketPriceId").val(),
                'ticketPrice.ticket.id':$("#ticketId").val()
            },
            function(data) {
                if (data.rows.length > 0) {
                    $.each(data.rows, function(i, perValue) {

                        var address = BizConstants.QINIU_DOMAIN + perValue.path;

                        showImage($('#imagePanel'), address, perValue.id);
                        $("#imageContent").append("<input id='input_" + perValue.id + "' type='hidden' name='imgPaths' value='" + perValue.path + "' cover-flag='"+ 0 +"' name='"+ perValue.imgDesc +"'>");



                        $('#fileBox_' + perValue.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                        $('#fileBox_' + perValue.id).find('.diyCover').click(function () { //增加事件
                            $('#coverParent').html('<div id="coverBox"></div>');
                            $('#coverPath').prop('value', perValue.path);
                            $('#imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + perValue.id).find('.coverSuccess').show();

                            $.each($("input[name='imgPaths']"), function(i, per) {
                                $(per).attr("cover-flag", 0);
                            });
                            $("#input_"+ perValue.id +"").attr("cover-flag", 1);
                            showImageWithoutCancel($('#coverBox'), address, perValue.id);
                        });
                        $('#fileBox_' + perValue.id).find('.diyCancel').click(function () {
                            if (data.path == $('#coverPath').val()) {
                                showMsgPlus('提示', '封面已经被删除!!', '3000');
                                $('#coverBox').next('div.parentFileBox').remove();
                                $('#coverPath').val(null);
                                $('#coverImgId').val(null);
                            }
                            $("#input_" + perValue.id).remove();
                        });

                        if (perValue.coverFlag == true) {

                            $('#coverParent').html('<div id="coverBox"></div>');
                            $('#coverPath').prop('value', perValue.path);
                            $('#imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + perValue.id).find('.coverSuccess').show();
                            $("#input_"+ perValue.id +"").attr("cover-flag", 1);
                            showImageWithoutCancel($('#coverBox'), address, perValue.id);

                        } else {
                            $("#input_"+ perValue.id + "").attr("cover-flag", 0);
                        }
                    });
                }
            }
        );
    },
    saveImages: function() {

        var url = "/ticket/ticketPrice/savePriceTypeImages.jhtml";

        var postData = {};

        var inputList = $("input[name='imgPaths']");

        if (!inputList || inputList.length <= 0) {
            show_msg("请先上传图片");
            return ;
        }

        $.each(inputList, function(i, perValue) {
            postData['ticketPriceId'] = $("#ticketPriceId").val();
            postData['productimages['+i+'].product.id'] = $("#ticketId").val();
            postData['productimages['+i+'].targetId'] = $("#ticketPriceId").val();
            postData['productimages['+i+'].proType'] = 'sailboat';
            postData['productimages['+i+'].path'] = $(perValue).val();
            postData['productimages['+i+'].imagDesc'] = $(perValue).attr("name");
            postData['productimages['+i+'].childFolder'] = PriceTypeImages.infoImg;
            if ($(perValue).attr("cover-flag") == 1) {
                postData['productimages['+i+'].coverFlag'] = true;
            } else {
                postData['productimages['+i+'].coverFlag'] = false;
            }

        });
        $.messager.progress();

        $.post(
            url,
            postData,
            function(data) {
                window.parent.$('#dd').dialog('close');
                $.messager.progress('close');
                window.parent.$.messager.show({
                    title:'温馨提示',
                    showType:'show',
                    msg:"操作成功!",
                    timeout:1500,
                    style:{
                        right:'',
                        bottom:''
                    }
                });
            }
        );
    },

    cancelSaveImage: function() {
        window.parent.$('#dd').dialog('close');
        window.parent.$('#qryResult').datagrid("load");
    }

}

$(function() {
    PriceTypeImages.init();
})