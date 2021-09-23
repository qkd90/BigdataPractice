/**
 * Created by dy on 2016/6/23.
 */

var PhotoJs = {
    //K:window.KindEditor,
    searUrl :null,
    targetObject :null,
    uploadUrl:null,
    folder:null,
    data:[
        {
            'imgUrl':'http://m.tuniucdn.com/fb2/t1/G1/M00/F5/9A/Cii9EVdRYhGIFHrZAAKX1rIf16YAAGW7wCYfj0AApfu536_w320_h240_c1_t0.jpg',
            'title':'华安大地土楼1'
        },
        {
            'imgUrl':'http://m.tuniucdn.com/fb2/t1/G1/M00/F5/9A/Cii9EVdRYhGIFHrZAAKX1rIf16YAAGW7wCYfj0AApfu536_w320_h240_c1_t0.jpg',
            'title':'华安大地土楼2'
        },
        {
            'imgUrl':'http://m.tuniucdn.com/fb2/t1/G1/M00/F5/9A/Cii9EVdRYhGIFHrZAAKX1rIf16YAAGW7wCYfj0AApfu536_w320_h240_c1_t0.jpg',
            'title':'华安大地土楼3'
        },
        {
            'imgUrl':'http://m.tuniucdn.com/fb2/t1/G1/M00/F5/9A/Cii9EVdRYhGIFHrZAAKX1rIf16YAAGW7wCYfj0AApfu536_w320_h240_c1_t0.jpg',
            'title':'华安大地土楼4'
        },
        {
            'imgUrl':'http://m.tuniucdn.com/fb2/t1/G1/M00/F5/9A/Cii9EVdRYhGIFHrZAAKX1rIf16YAAGW7wCYfj0AApfu536_w320_h240_c1_t0.jpg',
            'title':'华安大地土楼5'
        }
    ],
    onUpload: function() {
        /*$.ajaxFileUpload({
            url: '/line/lineImg/upload.jhtml',
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'file', // 上传文件的id、name属性名
            dataType: 'application/json', //返回值类型，一般设置为json、application/json
            //elementIds: elementIds, //传递参数到服务器
            success: function(data, status){
                alert("success:"+data);
            },
            error: function(data, status, e){
                alert("false:"+e);
            }
        });*/



    },
    initSearch: function(searUrl) {
        PhotoJs.searUrl = searUrl;
    },
    initTarget: function(target) {
        PhotoJs.targetObject = target;
    },

    initParams : function (uploadUrl, folder) {
        PhotoJs.uploadUrl = uploadUrl;
        PhotoJs.folder = folder;
    },

    selImgOk: function() {

        var day = $("#hid_daytime").attr("day");
        var time = $("#hid_daytime").attr("time");

        if (PhotoJs.targetObject == "addStep3") {
            $("input[name='fileName1']:checked").each(function(){
                if ("checked" == $(this).attr("checked")) {
                    var imagUrl = $(this).val();
                    var title = $(this).attr("title");

                    if (PhotoJs.targetObject == "addStep3") {
                        addStep3.addImgDivUl(day, time, imagUrl, title);
                    } else {
                        editStep3.addImgDivUl(day, time, imagUrl, title);
                    }


                }
            });
        } else if (PhotoJs.targetObject == "editStep3") {
            $("input[name='fileName1']:checked").each(function(){
                if ("checked" == $(this).attr("checked")) {
                    var imagUrl = $(this).val();
                    var title = $(this).attr("title");
                    if (PhotoJs.targetObject == "editStep3") {
                        editStep3.addImgDivUl(day, time, imagUrl, title);
                    } else {
                        editStep3.addImgDivUl(day, time, imagUrl, title);
                    }


                }
            });
        } else if (PhotoJs.targetObject == "editStep1") {
           var checkArr = $("input[name='fileName1']:checked");

            $.each(checkArr, function(i, perValue) {
                if ("checked" == $(perValue).attr("checked")) {
                    var imagUrl = $(this).val();
                    var title = $(this).attr("title");
                    editStep1.editStepUlImg(imagUrl, title);
                }
            });




        } else if (PhotoJs.targetObject == "addStep1") {
            var checkArr = $("input[name='fileName1']:checked");

            $.each(checkArr, function(i, perValue) {
                if ("checked" == $(perValue).attr("checked")) {
                    var imagUrl = $(this).attr("imgurl");
                    var title = $(this).attr("title");
                    addStep1.addStepUlImg(imagUrl, title);
                }
            });
        }


        PhotoJs.delDialog();
    },
    searchImgs: function() {
        $("#imgPreviewList").children().remove();
        var keyword = $("#ipt_keyword").val();
        if (keyword) {
            if (PhotoJs.searUrl != null && PhotoJs.searUrl.length > 0) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post(PhotoJs.searUrl, {keyword: keyword},
                    function(result){
                        $.messager.progress("close");
                        if (result) {
                            if (result.rows) {
                                $.each(result.rows, function(i, perValue) {
                                    showImage($('#as'), perValue.path, i, perValue.imagDesc);
                                    $("#name_"+ i +"").show();
                                    //PhotoJs.addImgLi(i, perValue.path, perValue.imagDesc);
                                });
                            } else {
                                show_msg("暂无数据！")
                            }
                        } else {
                            show_msg("暂无数据！")
                        }
                    }
                );
            }
        } else {
            show_msg("请输入图片关键词");
        }


    },
    addImgLi: function(i, imgUrl, title) {
        var ulChildren = $("#imgPreviewList").children();
        if (ulChildren.length > 0) {
            $("#imgPreviewList").children().last().after(PhotoJs.createImgLi(i, imgUrl, title));
        } else {
            $("#imgPreviewList").append(PhotoJs.createImgLi(i, imgUrl, title));
        }
    },
    delDialog: function() {
        $("#dingwei").remove();
        $("body").unmask();
    },

    showFileDetail: function(file) {
        if (file.files.length > 0) {
            var fileName = file.files[0].name;
            var fileSize = file.files[0].size;
            fileSize = fileSize/1024;
            fileSize = fileSize.toFixed(2);
            $("#fileTitle").html("文件名称："+ fileName);
            $("#fileSize").html("文件大小："+ fileSize+ "KB");

            $("#seletBtn").after(PhotoJs.createUploadFileDiv());
        }
    },

    ajaxFileUpload: function() {
        $.ajaxFileUpload({
            url: '/line/lineImg/upload.jhtml',
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'uploadFile', // 上传文件的id、name属性名
            dataType: 'json', //返回值类型，一般设置为json、application/json
            //elementIds: elementIds, //传递参数到服务器
            success: function(data){
                if (data.success) {
                    show_msg("图片保存成功！");
                } else {
                    show_msg(data.errorCode);
                }
            },
            error: function(data, status, e){
                alert("false:"+e);
            }
        });
    },

    cancelUploadFile: function() {
        $("#fileTitle").html("");
        $("#fileSize").html("");
        $("#uploadFileDiv").hide();
    },
    createImgLi: function(i, imgUrl, title) {
        var imgLi = '';
        imgLi += '<li class="span3">';
        imgLi += '<div class="thumbnail">';
        imgLi += '<a class="thumbnail" style="min-height:150px;">';
        imgLi += '<img width="200" height="150" style="width:200px;height:150px;" src="'+ imgUrl +'">';
        imgLi += '</a>';
        imgLi += '<div class="caption ellipsis" style="padding:0px; padding-top: 10px;" title="'+ title +'">';
        imgLi += '<label style="display:inline">';
        imgLi += '<input type="checkbox" name="ischecked" imgurl="'+ imgUrl +'" placeholder="" title="'+ title +'">'+ title +'</label>';
        imgLi += '</div>';
        imgLi += '</div>';
        imgLi += '</li>';
        return imgLi;
    },

    createUploadBtn: function() {
        // 图片上传
        //KindEditor.ready(function(K) {
            var uploadbutton = KindEditor.uploadbutton({
                button : KindEditor('#uploadButton')[0],
                fieldName : 'resource',
                extraParams : {/*oldFilePath:$('#filePath').val(),*/ folder:LineConstants.lineDescImg},
                url : '/line/lineImg/uploadSave.jhtml?productId='+$("#productId").val(),
                afterUpload : function(result) {
                    $.messager.progress("close");
                    if(result.error==0) {
                        $("#imgPreviewList").children().remove();
                        var url = KindEditor.formatUrl(result.url, 'absolute');
                        var imagDesc = result.imagDesc;
                        $("#ipt_keyword").val(imagDesc);
                        PhotoJs.addImgLi(0, url, imagDesc);

                        //$('#filePath').val(url);
                        //$('#imgView img').attr('src', '/static'+url);
                        //$('#imgView').show();
                    }else{
                        show_msg("图片上传失败");
                    }
                },
                afterError : function(str) {
                    show_msg("图片上传失败");
                }
            });
            uploadbutton.fileBox.change(function(e) {
                var filePath = uploadbutton.fileBox[0].value;
                if (!filePath) {
                    show_msg("图片格式不正确");
                    return ;
                }
                var suffix = filePath.substr(filePath.lastIndexOf("."));
                suffix = suffix.toLowerCase();
                if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
                    show_msg("图片格式不正确");
                    return ;
                }
                $.messager.progress({
                    title:'温馨提示',
                    text:'图片上传中,请耐心等待...'
                });

                $('input[name=oldFilePath]').val(/*$('#filePath').val()*/);	// 添加动态参数，隐藏标签是KindEditor自动生成的
                uploadbutton.submit();
            });
        //});
    },

    createPhotDiv: function() {
        var dialogHtml = '';
        dialogHtml += '<div id="dingwei" class="modal-dialog">';
        dialogHtml += '<div class="modal-content">';
        dialogHtml += '<div class="modal-header">';
        dialogHtml += '<button type="button" class="close" data-dismiss="modal" onclick="PhotoJs.delDialog()" aria-hidden="true">';
        dialogHtml += '×';
        dialogHtml += '</button>';
        dialogHtml += '<h4 class="modal-title" id="myModalLabel">';
        dialogHtml += '<input type="hidden" day="" time="" id="hid_daytime">';
        dialogHtml += '我的相册';
        dialogHtml += '</h4>';
        dialogHtml += '</div>';
        dialogHtml += '<div class="modal-body">';
        dialogHtml += '<div class="body-header">';
        dialogHtml += '<table style="width: 100%">';
        dialogHtml += '<tr>';
        dialogHtml += '<td style="text-align: left">';
        dialogHtml += '<div class="input-group">';
        dialogHtml += '<input type="text" id="ipt_keyword" class="form-control">';
        dialogHtml += '<span class="input-group-btn">';
        dialogHtml += '<button class="btn btn-default" onclick="PhotoJs.searchImgs()" type="button">';
        dialogHtml += 'Go!';
        dialogHtml += '</button>';
        dialogHtml += '</span>';
        dialogHtml += '</div><!-- /input-group -->';
        dialogHtml += '</td>';
        dialogHtml += '<td style="text-align: right">';
        //dialogHtml += '<a href="javascript:;" class="file" id="seletBtn">选择文件';
        //dialogHtml += '<input type="file" id="uploadFile" name="" onchange="PhotoJs.showFileDetail(this)">';
        //dialogHtml += '</a>';
        //dialogHtml += '<input type="file" id="file1" name="file" />'
        dialogHtml += '<input type="button" id="uploadButton"  class="" style="" data-dismiss="modal" value="上传图片"/>';
        dialogHtml += '</td>';
        dialogHtml += '</tr>';
        dialogHtml += '</table>';
        dialogHtml += '</div>';
        dialogHtml += '<div align="left" style="height:285px; width: 100%">';
        dialogHtml += '<fieldset class="fieldset-img">';
        dialogHtml += '<legend class="fieldset-legend">图片预览</legend>';
        dialogHtml += '<ul id="imgPreviewList" class="view-ul" style="">';

        dialogHtml += '</ul>';
        dialogHtml += '</fieldset>';
        dialogHtml += '</div>';
        dialogHtml += '</div>';
        dialogHtml += '<div class="modal-footer">';
        dialogHtml += '<button type="button" class="btn btn-default" onclick="PhotoJs.delDialog()" data-dismiss="modal">关闭</button>';
        dialogHtml += '<button type="button" onclick="PhotoJs.selImgOk()" class="btn btn-primary">确定</button>';
        dialogHtml += '</div>';
        dialogHtml += '</div>';
        return dialogHtml;
    },

    createPhotMoreDiv: function() {
        var dialogHtml = '';
        dialogHtml += '<div id="dingwei" class="modal-dialog">';
        dialogHtml += '<div class="modal-content">';
        dialogHtml += '<div class="modal-header">';
        dialogHtml += '<button type="button" class="close" data-dismiss="modal" onclick="PhotoJs.delDialog()" aria-hidden="true">';
        dialogHtml += '×';
        dialogHtml += '</button>';
        dialogHtml += '<h4 class="modal-title" id="myModalLabel">';
        dialogHtml += '<input type="hidden" day="" time="" id="hid_daytime">';
        dialogHtml += '我的相册';
        dialogHtml += '</h4>';
        dialogHtml += '</div>';
        dialogHtml += '<div class="modal-body">';
        dialogHtml += '<div class="body-header">';
        dialogHtml += '<table style="width: 100%">';
        dialogHtml += '<tr>';
        dialogHtml += '<td class="table-td">';

        dialogHtml += '<div id="as"></div>'

        //dialogHtml += '<div style="width: 180px;" class="parentFileBox">';
        //
        //dialogHtml += '</div>';
        dialogHtml += '<fieldset class="fieldset-img parentFileBox" style="width:727px;position: absolute;margin-top: 22px;">';
        dialogHtml += '<legend class="fieldset-legend">图片预览</legend>';
        dialogHtml += '<ul id="imgPreviewList" class="fileBoxUl" style="">';
        dialogHtml += '</ul>';
        dialogHtml += '</fieldset>';
        dialogHtml += '</td>';
        dialogHtml += '<td class="table-td" style="text-align: right">';
        dialogHtml += '<div class="input-group" style="margin-left: 450px;">';
        dialogHtml += '<input type="text" id="ipt_keyword" class="form-control">';
        dialogHtml += '<span class="input-group-btn">';
        dialogHtml += '<button class="btn btn-default" onclick="PhotoJs.searchImgs()" type="button">';
        dialogHtml += 'Go!';
        dialogHtml += '</button>';
        dialogHtml += '</span>';
        dialogHtml += '</div><!-- /input-group -->';
        //dialogHtml += '<a href="javascript:;" class="file" id="seletBtn">选择文件';
        //dialogHtml += '<input type="file" id="uploadFile" name="" onchange="PhotoJs.showFileDetail(this)">';
        //dialogHtml += '</a>';
        //dialogHtml += '<input type="file" id="file1" name="file" />'

        //dialogHtml += '<input type="button" id="uploadButton"  class="" style="" data-dismiss="modal" value="上传图片"/>';
        dialogHtml += '</td>';
        dialogHtml += '</tr>';
        dialogHtml += '</table>';
        dialogHtml += '</div>';
        //dialogHtml += '<div align="left" style="height:285px; width: 100%">';
        //dialogHtml += '<fieldset class="fieldset-img">';
        //dialogHtml += '<legend class="fieldset-legend">图片预览</legend>';
        //dialogHtml += '<ul id="imgPreviewList" class="view-ul" style="">';
        //dialogHtml += '<div></div>';
        //dialogHtml += '</ul>';
        //dialogHtml += '</fieldset>';
        //dialogHtml += '</div>';
        dialogHtml += '</div>';
        dialogHtml += '<div class="modal-footer">';
        dialogHtml += '<button type="button" class="btn btn-default" onclick="PhotoJs.delDialog()" data-dismiss="modal">关闭</button>';
        dialogHtml += '<button type="button" onclick="PhotoJs.selImgOk()" class="btn btn-primary">确定</button>';
        dialogHtml += '</div>';
        dialogHtml += '</div>';
        return dialogHtml;
    },

    initDiyUpload: function() {
        //fieldName : 'resource',
        //    extraParams : {/*oldFilePath:$('#filePath').val(),*/ folder:LineConstants.lineDescImg},
        $('#as').diyUpload({
            url:'/line/lineImg/uploadPics.jhtml?productId=' + $("#productId").val() + '&folder=' + LineConstants.lineDescImg,
            success:function( result ) {
                //showImage($('#as'), data.url, 0);
                if (result.success) {
                    //uploadSuccess($('#as'), result.url, result.imgIndex, this);
                    $("#name_"+ result.imgIndex +"").show();
                    $("#name_"+ result.imgIndex +"").val(result.url);
                }
            },
            error:function( err ) {
                console.info( err );
            },
            buttonText : '添加图片',
            chunked:true,
            // 分片大小
            chunkSize:512 * 1024,
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit:30,
            fileSizeLimit:500000 * 1024,
            fileSingleSizeLimit:50000 * 1024,
            accept: {}
        });

    },

    createUploadFileDiv: function() {
        var fileDiv = '';
        fileDiv += '<div id="uploadFileDiv" style="z-index:3;position: absolute; top: 60px; left: 400px; width: 200px; height: 170px; border: 1px solid rgba(82, 168, 236, 0.8); border-radius: 3px; padding: 8px; z-index: 99999; box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset, rgba(82, 168, 236, 0.6) 0px 0px 8px; overflow: hidden; line-height: 18px; background: rgb(255, 255, 255);">';
        fileDiv += '<div id="fileListUploadFile">';
        fileDiv += '<div style="height:120px;overflow-y:auto;margin-bottom: 10px;">';
        fileDiv += '<div class="row" style="margin-bottom:5px;margin-left: 0px;">';
        fileDiv += '<div id="fileTitle" title="Hydrangeas.jpg" class="" style="text-align:left;height:18px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;width:180px;margin-left:0px;">文件名称：Hydrangeas.jpg</div>';
        fileDiv += '<div id="fileSize" class="show del" style="text-align: left;" value="Hydrangeas.jpg">文件大小：581.33KB</div>';
        fileDiv += '</div>';
        fileDiv += '</div>';
        fileDiv += '</div>';
        fileDiv += '<div id="hintArea" style="color:red;height: 20px;"></div>';
        fileDiv += '<div style="text-align:right;">';
        fileDiv += '<a id="innerOkUploadFile" onclick="PhotoJs.ajaxFileUpload()" style="margin-right:10px;display:inline;width:auto;font-size:12px;background-color: rgb(208, 207, 207);padding: 5px;" class="btn icon-ok">确定</a>';
        fileDiv += '<a id="innerCancelUploadFile" onclick="PhotoJs.cancelUploadFile()" class="btn icon-remove" style="display:inline;width:auto;font-size:12px;background-color: rgb(208, 207, 207);padding: 5px;">取消</a>';
        fileDiv += '</div>';
        fileDiv += '</div>';

        return fileDiv;
    }
};
$(function(){
   //PhotoJs.initUpload(PhotoJs.uploadUrl, PhotoJs.folder);
    $('#imagePanel').diyUpload({
        url: "/line/lineImg/uploadLineImg.jhtml",
        success: function (data, $fileBox) {
            console.log(data);
            var address = BizConstants.QINIU_DOMAIN + data.path;
            $fileBox.remove();
            showImage($('#imagePanel'), address, data.id);
            $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
            //$('#fileBox_' + data.data.id).find('.diyCover').click(function () {//增加事件
            //    $('#coverParent').html('<div id="coverBox"></div>');
            //    $('#coverLarge').prop('value', data.data.imgUrl);
            //    $('#imageBox').find('.coverSuccess').hide();
            //    $('#fileBox_' + data.data.id).find('.coverSuccess').show();
            //    showImage($('#coverBox'), address, data.data.id);
            //});
            $('#fileBox_' + data.id).find('.diyCancel').click(function () {
                $("#input_" + data.id).remove();
            });
            $("#imageContent").append("<input id='input_" + data.id + "' type='hidden' name='lineImgPaths' value='" + data.path + "'>");
        },
        error: function (err) {
            console.info(err);
        },
        buttonText: '上传图片',
        chunked: true,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 50,
        fileSizeLimit: 500000 * 1024,
        fileSingleSizeLimit: 50000 * 1024,
        formData: {
            section: LineConstants.lineDescImg
        }
    });
});



