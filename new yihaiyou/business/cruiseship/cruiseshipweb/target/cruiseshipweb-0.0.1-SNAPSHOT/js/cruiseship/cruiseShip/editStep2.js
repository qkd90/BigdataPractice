var editStep2 = {
    // 初始化
    init: function () {
        editStep2.initComp();
        editStep2.initListener();
    },
    // 初始控件
    initComp: function () {
        // 甲板信息表格
        $('#deckGrid').datagrid({
            url: '/cruiseship/cruiseShipDeck/list.jhtml',
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns: [[
                {
                    title: 'ID',
                    field: 'id',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '楼层',
                    field: 'level',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '甲板描述',
                    field: 'levelDesc',
                    width: 150
                },
                {
                    title: '甲板设施',
                    field: 'deckFacility',
                    width: 150
                },
                    {
                    title: '甲板图片',
                    field: 'shapeImage',
                    width: 200,
                    formatter: editStep2.deckImgFormat
                },
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep2.openEditDeckDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],
            toolbar : '#deckGridtb',
            onBeforeLoad : function(data) {
                data.productId = $('#productId').val();
            }

        });
        // 房型信息表格
        $("#roomGrid").datagrid({
            //fit:true,
            //fitColumns:true,
            url:'/cruiseship/cruiseShipRoom/list.jhtml',
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns:[[
                //{ field: 'ck', checkbox: true },
                { field: 'id', title: '编号', width: 80},
                { field: 'name', title: '名称', width: 140},
                { field: 'roomType', title: '类型', width: 80, align: 'center', codeType: 'roomType', formatter: CruiseShipUtil.codeFmt},
                { field: 'deck', title: '楼层', width: 100},
                { field: 'area', title: '面积', width: 80},
                { field: 'facilities', title: '设施', width: 80},
                { field: 'peopleNum', title: '可住人数', width: 60},
                { field: 'forceFlag', title: '必须住满', width: 60, align: 'center', codeType: 'booleanFlag', formatter: CruiseShipUtil.codeFmt},
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep2.openEditRoomDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],
            toolbar : '#roomGridtb',
            onBeforeLoad : function(data) {
                data.productId = $('#productId').val();
            }
        });
        // 房型表单控件
        $('#roomType').combobox({
            data:CruiseShipConstants.getConstants('roomType', true),
            valueField:'id',
            textField:'text',
            panelHeight: 'auto'
        });
        $('#forceFlag').combobox({
            data:CruiseShipConstants.getConstants('booleanFlag', true),
            valueField:'id',
            textField:'text',
            panelHeight: 'auto'
        });
        // 甲板/房型信息/设施富文本框设置
        $.each($("#deckForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_deck'
            });
        });
        $.each($("#roomForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_room'
            });
        });
    },
    // 甲板图片展示处理
    deckImgFormat: function(value, rowData, rowIndex) {
        if (value != null && value != undefined && value != '') {
            var img = '<img src="' + BizConstants.QINIU_DOMAIN + value + '?imageView2/1/w/240/h/80" width="240" height="80"/>';
            var $img = $(img);
            $img.tooltip({
                position:'top',
                content:'<img src="'+ BizConstants.QINIU_DOMAIN + value + '?imageView2/2/w/500" width="500px"/>',
                showEvent:'mouseenter'
            });
            return img;
        }
        return '<span style="color: red; font-weight: bold">暂无图片</span>';
    },
    // 监听器
    initListener: function() {

    },
    // 甲板图片上传控件
    initDeckImgUploader: function() {
        $('#deck_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#deck_imagePanel'), address, data.id);
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
                $("#deck_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            multiple: false,
            buttonText: '上传图片',
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit: 1,
            formData: {
                section: CruiseShipConstants.deckImg
            }
        });
    },
    // 房型图片上传控件
    initRoomImgUploader: function() {
        $('#room_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#room_imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#coverParent').html('<div id="coverBox"></div>');
                    $('#coverPath').prop('value', data.path);
                    $('#room_imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();
                    showImageWithoutCancel($('#coverBox'), address, data.id);
                });
                $('#fileBox_' + data.id + ' .diyCancel').click(function () {
                    if (data.path == $('#coverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#coverBox').next('div.parentFileBox').remove();
                        $('#coverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#room_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '上传图片',
            formData: {
                section: CruiseShipConstants.roomImg
            }
        });
    },
    // 甲板信息查询
    searchDeck: function() {
        $('#deckGrid').datagrid('load');
    },
    // 房型查询
    searchRoom: function() {
        $('#roomGrid').datagrid('load');
    },
    // 打开甲板信息窗口
    openEditDeckDg: function(deckId, rowIndex) {
        $('#editDeckDg').dialog({
            title:'甲板信息',
            modal:true,
            onClose:function(){
                $("#deckForm").form('clear');
                // 清空图片上传信息
                $('#deckForm').find('.parentFileBox').remove();
                $('#deckForm').find('#deck_imageContent').empty();
            }
        });
        // 初始化图片上传控件
        editStep2.initDeckImgUploader();
        var deckRow = {};
        var deck = {};
        if (deckId) {
            var deckRows = $('#deckGrid').datagrid('getRows');
            deckRow = deckRows[rowIndex];
            deck['cruiseShipDeck.id'] = deckRow.id;
            deck['cruiseShipDeck.level'] = deckRow.level;
            deck['cruiseShipDeck.levelDesc'] = deckRow.levelDesc;
            deck['cruiseShipDeck.deckFacility'] = deckRow.deckFacility;
            deck['cruiseShipDeck.shapeImage'] = deckRow.shapeImage;
        } else {
            //
            deck['cruiseShipDeck.levelDesc'] = null;
            deck['cruiseShipDeck.deckFacility'] = null;
        }
        deck['cruiseShipDeck.cruiseShip.id'] = $('#productId').val();
        deck['ChildFolder'] = CruiseShipConstants.deckImg;
        $('#deckForm').form({
            onLoadSuccess: function(data) {
                if(data['cruiseShipDeck.shapeImage'] && data['cruiseShipDeck.shapeImage'].length > 0) {
                    showImageWithoutCancel($('#deck_imagePanel'), BizConstants.QINIU_DOMAIN +  data['cruiseShipDeck.shapeImage'], data['cruiseShipDeck.id']);
                }
            }
        });
        $('#deckForm').form('load', deck);
        $('#editDeckDg').dialog('open');
    },
    // 打开房型编辑窗口
    openEditRoomDg: function(roomId, rowIndex) {
        $('#editRoomDg').dialog({
            title:'房型信息',
            modal:true,
            onClose:function(){
                $("#roomForm").form('clear');
                // 清空图片上传信息
                $('#roomForm').find('.parentFileBox').remove();
                $('#roomForm').find('#room_imageContent').empty();
            }
        });
        $('#editRoomDg').dialog('open');
        // 初始化图片上传控件
        editStep2.initRoomImgUploader();
        var row = {};
        if (roomId) {
            var rows = $('#roomGrid').datagrid('getRows');
            row = rows[rowIndex];
        } else {
            row.roomType = 'inside';
            row.forceFlag = true;
            row.facilities = null;
        }
        row.cruiseShipId = $('#productId').val();
        row.roomChildFolder = CruiseShipConstants.roomImg;
        $('#roomForm').form({
            onLoadSuccess: function(data) {
                if (roomId) {
                    // 加载房型现有图片
                    editStep2.loadRoomImg(roomId);
                }
            }
        });
        $('#roomForm').form('load', row);
    },
    // 加载房型现有图片
    loadRoomImg: function(targetId) {
        $.ajax({
            url: '/cruiseship/cruiseShip/getImageList.jhtml',
            dataType: 'json',
            data: {
                'productimage.product.id' : $('#productId').val(),
                'productimage.targetId': targetId,
                'productimage.childFolder': $('#roomChildFolder').val(),
                'productimage.proType': 'cruiseship'
            },
            success: function(result) {
                if (result.success) {
                    $.each(result.data, function (i, img) {
                        var address = BizConstants.QINIU_DOMAIN + img.path;
                        showImage($('#room_imagePanel'), address, img.id);
                        $('#fileBox_' + img.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                        $('#fileBox_' + img.id).find('.diyCover').click(function () {//增加事件
                            $('#coverParent').html('<div id="coverBox"></div>');
                            $('#coverPath').prop('value', img.path);
                            $('#coverImgId').prop('value', img.id);
                            $('#room_imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#coverBox'), address, img.id);
                        });
                        if (img.coverFlag) {
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#coverBox'), address, img.id);
                        }
                        $('#fileBox_' + img.id + ' .diyCancel').click(function () {
                            if (img.coverFlag) {
                                showMsgPlus('提示', '封面已经被删除!!', '3000');
                                $('#coverBox').next('div.parentFileBox').remove();
                                $('#coverPath').val(null);
                                $('#coverImgId').val(null);
                            }
                            $("#room_imageContent").append("<input type='hidden' name='imgDeleteIds' value='" + img.id + "'>");
                        });
                    });
                }
            },
            error: function (result) {
                console.log(result);
            }
        });
    },
    // 关闭甲板编辑窗口
    closeEditDeckDg: function() {
        $('#editDeckDg').dialog('close');
    },
    // 关闭房型编辑窗口
    closeEditRoomDg: function() {
        $("#editRoomDg").dialog("close");
    },
    // 清楚甲板表单数据
    clearDeckForm: function() {
        $('deckForm').form('clear');
        // 清楚甲板图片信息
        $('#deckForm').find('.parentFileBox').remove();
        $('#deckForm').find('#imageContent').empty();
    },
    // 清除表单数据
    clearRoomForm:function(){
        $('#roomForm').form('clear');
        //$("#roomId").val("");
    },
    // 甲板信息保存
    saveDeck: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $('#deckForm').form('submit', {
            url: '/cruiseship/cruiseShipDeck/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '请完善甲板信息!', 'error');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress("close");
                var result = eval('(' + data + ')');
                if (result.success) {
                    editStep2.closeEditDeckDg();
                    editStep2.searchDeck();
                } else {
                    showMsgPlus('提示', '保存失败', 2000);
                }
            }
        });
    },
    // 房型保存
    saveRoom: function() {
        // 房型是否有图片校验
        var newImgLength = $("#room_imageContent").find('input[name = "imgPaths"]').length;
        var existImgLength = $("#room_imageBox").find('.viewThumb').children('img').length;
        if (newImgLength + existImgLength <= 0) {
            show_msg("请上传房型图片!");
            return;
        }
        // 房型图片封面校验
        if ($("#coverParent").find('.viewThumb').children('img').length <= 0) {
            showMsgPlus('提示', '请设置房型封面图片!', 3000);
            return;
        }
        $('#roomForm').form('submit', {
            url:'/cruiseship/cruiseShipRoom/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if(isValid) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });
                }
                return isValid;
            },
            success : function(data) {
                $.messager.progress("close");
                var obj = eval('(' + data + ')');
                if (obj.success) {
                    editStep2.closeEditRoomDg();
                    editStep2.searchRoom();
                } else {
                    show_msg("保存失败");
                }
            }
        });
    },
    // 删除甲板信息
    doDelDeck: function() {
        var row = $('#deckGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的甲板信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipDeck/delDeck.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            deckId: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            editStep2.searchDeck();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            editStep2.searchDeck();
                        }
                    })
                }
            }
        );
    },
    // 删房间信息
    doDelRoom: function() {
        var row = $('#roomGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的房间信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipRoom/delRoom.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            roomId: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            editStep2.searchRoom();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            editStep2.searchRoom();
                        }
                    })
                }
            }
        );
    },
    // 下一步
    nextGuide: function (winId) {
        parent.window.showGuide(winId, true);
    }
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
    editStep2.init();
});