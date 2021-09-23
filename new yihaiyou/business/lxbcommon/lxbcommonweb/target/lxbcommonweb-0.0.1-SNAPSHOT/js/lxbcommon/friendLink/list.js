/**
 * Created by zzl on 2017/3/3.
 */
var FriendLink = {
    logoFolder: 'friendlink/logo/',
    status: {'SHOW': '展示', 'HIDE': '隐藏', 'EXPIRED': '已过期'},
    init: function() {
        FriendLink.initComp();
        FriendLink.initTable();
    },
    initComp: function() {
        FriendLink.initImgUp();
    },
    initImgUp: function() {
        // 图片上传
        KindEditor.ready(function (K) {
            var uploadbutton = K.uploadbutton({
                button: K('#add_logopic')[0],
                fieldName: 'resource',
                width:200,
                url: '/lxbcommon/friendLink/uploadLinkLogoImg.jhtml',
                extraParams: {oldFilePath: $('#filePath').val(), folder: FriendLink.logoFolder},
                afterUpload: function (result) {
                    $.messager.progress("close");
                    if (result.success == true) {
                        var url = K.formatUrl(result.url, 'absolute');
                        FriendLink.previewImg(url);
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
    previewImg: function(src) {
        $('#logoPath').val(src);
        $('#imgView img').attr('src', src);
        $('#imgView').show();
    },
    deleteImg: function() {
        $('#filePath').val("");
        $('#logoPath img').attr('src', "");
        $('#imgView').hide();
    },
    initTable: function() {
        $('#friendLinkDg').datagrid({
            url: '/lxbcommon/friendLink/getFriendLinkData.jhtml',
            fit: true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc"
            },
            columns:[[
                {
                    field: 'id',
                    title: 'ID',
                    width: 55,
                    align: 'center'
                },
                {
                    field: 'linkName',
                    title: '链接名称',
                    width: 140,
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    align: 'center',
                    formatter: function(value, rowData, rowIndex) {
                        return FriendLink.status[value];
                    }
                },
                {
                    field: 'url',
                    title: '链接地址',
                    width: 220,
                    align: 'center'
                },
                { field: 'linkLogo', title: '链接logo', width: 200, sortable: false, align: 'center',
                    formatter : function(value, row, rowIndex) {
                        if (value != null && value != undefined && value != '') {
                            return '<img src="' + QINIU_BUCKET_URL + value + '"width="160" height="60" style="padding:2px;">';
                        }
                        return '<span style="color: red; font-weight: bold">暂无图片</span>';
                    }
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 140,
                    align: 'center'
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: '120',
                    align: 'center',
                    formatter: FriendLink.optFormat
                },
            ]],
            toolbar: '#friendLink-searcher'
        });
    },
    optFormat: function (value, rowData, rowIndex) {
        var btn = "";
        var delClick = " onClick='FriendLink.delFriendLink(" + rowData.id + ")'";
        var editClick = " onClick='FriendLink.editFriendLink(" + rowData.id + ")'";
        btn += "<div class='opt' >";
        btn += "<a class='ena' href='#'" + editClick + ">编辑/查看</a>";
        btn += "&nbsp;&nbsp;";
        if (rowData.status != "DEL") {
            btn += "<a class='ena' href='#'" + delClick + ">删除</a>";
            btn += "&nbsp;&nbsp;";
        }
        btn += "</div>";
        return btn;
    },
    doSearch: function() {
        var searchForm = {};
        searchForm['friendLink.linkName'] = $("#search-content").val();
        searchForm['friendLink.status'] = $("#search-status").combobox('getValue');
        $('#friendLinkDg').datagrid('load', searchForm);
    },
    doClear: function() {
        $("#qry_contact").textbox("setValue", null);
        $("#qry_status").combobox("setValue", null);
        $('#friendLinkDg').datagrid('load', {});
    },
    addFriendLink: function() {
        $("#friendLink_panel").dialog({
            title: '添加友情链接',
            modal: true,
            shadow:false,
            top: 50,
            onClose: function () {
                $("#friendLink_form").form('clear');
                FriendLink.deleteImg();
            }
        });
        $("#friendLink_panel").dialog('open');
    },
    editFriendLink: function(id) {
        $.messager.progress({
            title: '温馨提示',
            text: '正在获取链接信息, 请稍候...'
        });
        $('#friendLink_form').form({
            onLoadSuccess: function(data) {
                // 加载logo
                FriendLink.previewImg(QINIU_BUCKET_URL + data['friendLink.linkLogo']);
                $("#friendLink_panel").dialog({
                    title: '查看/编辑链接',
                    modal: true,
                    shadow:false,
                    top: 50,
                    onClose: function () {
                        $("#friendLink_form").form('clear');
                        FriendLink.deleteImg();
                    }
                });
                $("#friendLink_panel").dialog('open');
                $.messager.progress("close");
            }
        });
        $("#friendLink_form").form('load', '/lxbcommon/friendLink/detailLink.jhtml?id=' + id);
    },
    doSaveFriendLink: function() {
        $("#friendLink_form").form('submit', {
            url: '/lxbcommon/friendLink/doSaveFriendLink.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#friendLink_panel").dialog("close");
                    showMsgPlus("友情链接管理", "保存成功!");
                    $('#friendLinkDg').datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('友情链接管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('友情链接管理', "操作失败,请重试!", 'error');
            }
        });
    }
};
$(function () {
    FriendLink.init();
});