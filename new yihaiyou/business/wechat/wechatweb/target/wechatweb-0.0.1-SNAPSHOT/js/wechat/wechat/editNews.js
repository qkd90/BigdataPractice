/**
 * Created by vacuity on 15/11/20.
 */
$(function () {
    EditNews.init();
//    window.setTimeout("EditNews.autoSendData()",500);
//    window.setInterval("EditNews.autoSendData()",10000); 
});
var EditNews = {
    newsImg: "wechat/wechatNews/",
    init: function () {
        EditNews.initJsp();
        EditNews.initHiddenIndex();
        EditNews.initTextArea();
        EditNews.initCheckbox();
        EditNews.sendDataToFather();
        EditNews.selectCategory();
        EditNews.initData();
    },
    btn_save: function (itemId) {
        var dataInputs = window.parent.$("input[name='app_data']");
        $.each(dataInputs, function (i, perValue) {
            var perInputValue = $(perValue).val();
            var url = "/wechat/wechatDataNews/saveNews.jhtml";
            var data = {
                "itemId": itemId,
                "data": perInputValue
            };
            $.post(url, data,
                function (result) {
                    if (result.success) {
                        var index = result.index;
                        var id = result.id;
                        var userId = result.userId;
                        var itemId = result.itemId;
                        var input_data = window.parent.$("#app_" + index + "").val();
                        if (input_data.length > 0) {
                            var input_data = eval("(" + input_data + ")");
                            if (index == input_data.index) {
                                input_data.id = id;
                                input_data.userId = userId;
                                input_data.itemId = itemId;
                                var str = JSON.stringify(input_data);
                                console.log("str=" + str);
                                var reply = window.parent.$("#hidden_reply").val();
                                if (reply) {
                                    window.parent.parent.ReplyNews.doSearch();
                                } else {
                                    window.parent.parent.WechatData.doSearch("news_dg");
                                }
                                window.parent.$("#app_" + index + "").val(str);
                                window.parent.parent.$('#editNews').dialog("close");
                            }
                        }
                    }
                });
        });
    },
    submitData: function (data) {
        var url = "/wechat/wechatDataNews/saveNews.jhtml";
        var post_data = data;
        var dataInputs = window.parent.$("input[name='app_data']");
//    	var dataInputs = window.parent.$("input[name='app_data']");
        $.post(url, post_data,
            function (result) {
                if (result.success) {
                    var index = result.index;
                    var id = result.id;
                    var userId = result.userId;
                    var itemId = result.itemId;
                    var input_data = window.parent.$("#app_" + index + "").val();
                    if (input_data.length > 0) {
                        var input_data = eval("(" + input_data + ")");
                        if (index == input_data.index) {
                            input_data.id = id;
                            input_data.userId = userId;
                            input_data.itemId = itemId;
                            var str = JSON.stringify(input_data);
                            window.parent.$("#app_" + index + "").val(str);
                            window.parent.parent.WechatData.doSearch("#news_dg");
                            window.parent.parent.$('#editNews').dialog("close");
                        }
                    }
                }
            });
    },
    saveBefore: function () {
        var flag = true;
        var dataInputs = window.parent.$("input[name='app_data']");
        for (var i = dataInputs.length - 1; i >= 0; --i) {
            var perData = $(dataInputs[i]).val();
            console.log(perData);
            if (perData) {
                var data = eval("(" + perData + ")");
                if (!(data.title.length > 0)) {
                    show_msg("请完善第" + (data.index) + "个图文标题！");
                    flag = false;
                    break;
                    //if (data.img_path) {
                    //    flag = true;
                    //} else {
                    //    show_msg("请完善第" + (data.index) + "个图文封面图！");
                    //    flag = false;
                    //    break;
                    //}
                }
            }
        }
        return flag;
    },
    saveItem: function () {
        var url = "/wechat/wechatDataItem/saveItem.jhtml";
        var itemId = window.parent.$("#itemId").val();
        $.post(url, {"type": "news", "itemId": itemId},
            function (result) {
                if (result.success) {
                    var id = result.id;
                    window.parent.$("#itemId").val(id);
                    var input_datas = window.parent.$("input[name='app_data']");
                    for (var i = input_datas.length; i > 0; --i) {
                        var per_data = $(input_datas[i - 1]).val();
                        var input_data = eval("(" + per_data + ")");
                        input_data.itemId = id;
                        var str = JSON.stringify(input_data);
                        window.parent.$("#app_" + input_data.index + "").val(str);
                    }
                    if (EditNews.saveBefore()) {
                        EditNews.btn_save(result.id);
                    }
                }
            });
//    	return flag;
    },
    sendDataToFather: function () {
        $("#textbox_author").textbox({
            onChange: function (newValue, oldValue) {
                EditNews.autoSendData();
            }
        });
        $("#text_box_url").textbox({
            onChange: function (newValue, oldValue) {
                EditNews.autoSendData();
            }
        });
        $("#abstractStr").textbox({
            onChange: function (newValue, oldVlaue) {
                EditNews.autoSendData();
            }
        });
        /*$("#abstractStr").change(function(){
         EditNews.autoSendData();
         });*/
    },

    selectCategory: function(category) {
        $("#editNews_category").combotree({
            onLoadSuccess: function() {
                $("#editNews_category").combotree('setValue', category);
                $(":input[name = 'category']").val(category);
            },
            onSelect: function(node) {
                $(":input[name='category']").val(node.id);
                EditNews.autoSendData();
            }
        });
    },
    initData: function () {
        var index = $("#hidden_index").val();
        var data = window.parent.$("#app_" + index + "").val();
        if (data.length > 0) {
            var data = eval("(" + data + ")");
            if (index == data.index) {
                EditNews.selectCategory(data.category);
                $("#textbox_title").textbox('setValue', data.title);
                $("#textbox_author").textbox('setValue', data.author);
                if (data.img_path != null && (data.img_path).length > 0) {
                    $("#filePath").val(data.img_path);
                    $('#imgView img').attr('src', QINIU_BUCKET_URL + data.img_path);
                    $('#imgView').show();
                }
                $("#abstractStr").textbox('setValue', data.abstractText);
                $("#news_content").html(data.content);
                $("#text_box_url").textbox('setValue', data.url);
                $("#newsId").val(data.id);
                $("#newsUserId").val(data.userId);
                $("#newsItemId").val(data.itemId);
                if ((data.is_checked).length > 0) {
                    if (data.is_checked == 0) {
                        $("#img2text").prop("checked", false);
                        $("#hidden_ischeck").val(0);
                    } else {
                        $("#img2text").prop("checked", true);
                        $("#hidden_ischeck").val(1);
                    }
                }
            }
        }
    },
    initJsp: function () {
        $("#abstractStr").textbox({
            onChange: function (newValue, oldVlaue) {
                $("#hidden_abstract").val($("#abstractStr").textbox('getValue'));
            }
        });
        //
        //EditNews.initCategory();
        // 图片上传
        KindEditor.ready(function (K) {
            var uploadbutton = K.uploadbutton({
                button: K('#add_descpic')[0],
                fieldName: 'resource',
                url: '/sys/fileInputUpload/uploadImg.jhtml',
                extraParams: {childFolder: EditNews.newsImg},
                afterUpload: function (result) {
                    $.messager.progress("close");
                    if (result.initialPreview.length > 0) {
                        var url = K.formatUrl(result.initialPreview[0], 'absolute');
                        $('#filePath').val(result.initialPreviewConfig[0].key);
//						alert(url);
                        $('#imgView img').attr('src', url);
                        $('#imgView').show();

                        var index = $("#hidden_index").val();
                        if (index == 1) {
                            window.parent.$("#mytitle_img_1").attr('src', url);
                            EditNews.autoSendData();
                        } else {
                            window.parent.$("#img_" + index + "").attr('src', url);
                            EditNews.autoSendData();
                        }

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
    makeData: function () {
        var nameList = $('input[name]');
        var data = {};
        $.each(nameList, function (i, perValue) {
            if (perValue.name != "folder" && perValue.name != "resource" && perValue.name != "oldFilePath") {
                data[perValue.name] = $(perValue).val();
            }
        });
        var str = JSON.stringify(data);
        return str;
    },
    autoSendData: function () {
        var index = $("#hidden_index").val();
        var html = "";
        html = "<input type='hidden' id='app_" + index + "' name='app_data' value='" + EditNews.makeData() + "' />";
        window.parent.$("#app_" + index + "").val(EditNews.makeData());
    },
    removeImg: function () {
        // 异步删除图片文件
        $.post("/sys/fileInputUpload/delFile.jhtml",
            {key: $('#filePath').val()},
            function (result) {
                $('#filePath').val('');
                $('#imgView img').attr('src', '');
                $('#imgView').hide();
                var index = $("#hidden_index").val();
                EditNews.autoSendData();
                if (index == 1) {
                    window.parent.$("#mytitle_img_1").attr('src', "");
//						window.parent.$("#mytitle_1").css("background-position", " no-repeat center center");
                } else {
                    window.parent.$("#img_" + index + "").attr('src', "");
                }
            }
        );
    },
    initCheckbox: function () {
        var filePath = $("#filePath").val();
        $("#checkbox_div").click(function () {
            if ($("#img2text").prop("checked")) {
                $("#img2text").prop("checked", false);
                $("#hidden_ischeck").val(0);
                EditNews.autoSendData();
            } else {
                $("#img2text").prop("checked", true);
                $("#hidden_ischeck").val(1);
                EditNews.autoSendData();
            }
        });
        $("#img2text").click(function () {
            if ($("#img2text").prop("checked")) {
                $("#img2text").prop("checked", false);
                $("#hidden_ischeck").val(0);
            } else {
                $("#img2text").prop("checked", true);
                $("#hidden_ischeck").val(1);
            }
        });
    },
    initHiddenIndex: function () {
        var index = $("#hidden_index").val();
        var title = $("#textbox_title").textbox('getValue');
        $("#textbox_title").textbox({
            onChange: function (newValue, oldValue) {
                if (index == 1) {
                    newValue = newValue.substr(0, 21);
                    window.parent.$("#title_" + index + "").html(newValue);
                    EditNews.autoSendData();
                } else {
                    newValue = newValue.substr(0, 10);
                    window.parent.$("#title_" + index + "").html(newValue);
                    EditNews.autoSendData();
                }
            }
        });
    },
    initTextArea: function () {
        var content;
        KindEditor.ready(function (K) {
            content = K.create('#news_content', {
                resizeType: 0,
                //allowPreviewEmoticons: false,
                //uploadJson: '/wechat/wechatDataImg/uploadImg.jhtml?folder=' + EditNews.newsImg,
                //fileManagerJson: '/wechat/wechatDataImg/imgsView.jhtml?folder=' + EditNews.newsImg,
                //allowImageUpload: true,
                //allowFileManager: true,
                filePostName: 'resource',
                items: ['fontsize', 'forecolor', 'fontname', 'bold'],
                afterChange: function () {
                    this.sync();
                    $("#hidden_content").val(this.html());
                    EditNews.autoSendData();
                },
                afterBlur: function () {
                    this.sync();
                }
            });
        });
    }
}
