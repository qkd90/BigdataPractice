$(function() {
	FollowerList.init();
});

var FollowerList = {
    table: $('#follower_dg'),
    init: function () {
        FollowerList.initDialog();
        FollowerList.initTextArea();
        FollowerList.initCommbox();
        FollowerList.initFollower();
        FollowerList.initTooltip();
    },

    doSearch: function () {
        var data = {
            accountid: $("#select_accountid").combobox("getValue"),
            nickname: $("#search_nickname").textbox("getValue"),
            isSupport:$("#isKefu").combobox("getValue")
        };
        $('#follower_dg').datagrid('load', data);
    },

    clearSearch: function () {
        $("#search_nickname").textbox("setValue", "");
        $("#isKefu").combobox("setValue", "");
        var data = {
            accountid: $("#select_accountid").combobox("getValue")
        };
        $('#follower_dg').datagrid('load', data);

    },


    initTooltip: function () {

        $("#tt").tooltip({
            position: 'bottom',
            content: '<div style="padding:5px;background:#eee;color:#000">This is the tooltip message.</div>',
            onShow: function () {
                $(this).tooltip('tip').css({
                    backgroundColor: '#fff000',
                    borderColor: '#ff0000',
                    boxShadow: '1px 1px 3px #292929'
                });
            },
            onPosition: function () {
                $(this).tooltip('tip').css('left', $(this).offset().left);
                $(this).tooltip('arrow').css('left', 20);
            }
        });


    },

    initFollower: function () {
        // 构建表格
       this.table.datagrid({
            fit: true,
            //title: "用户列表",
            data: [],
            url: '/wechat/wechatFollower/followerDatagrid.jhtml',
            border: true,
            striped: true,
            pagination: true,
            pageList: [10, 20, 30],
            rownumbers: true,
            fitColumns: true,
            columns: [[
                {field: '', checkbox: 'true'},
                {field: 'id', title: '编号', align: 'center', width: 40},
                {
                    field: 'OPT',
                    title: '操作',
                    align: 'center',
                    width: 40,
                    formatter: FollowerList.optFormat
                },
                {
                    field: 'headImgUrl', title: '粉丝', align: 'left', width: 300,
                    formatter: function (value, rowData, rowIndex) {
                        var imgStr = "<div id='show_info_" + rowData.id + "'  style='float:left;margin:5px 15px 5px 5px;width:50px;height:50px;cursor: pointer;'><img src='" + value + "' alt='' width='50px' height='50px' style='border:0;'></img></div>";
                        var nickname = "<div style='font-size:16px;margin-top: 20px;'>" + rowData.nickName + "</div>";
                        return imgStr + nickname;
                    }
                },
                {
                    field: 'country', title: '地区', align: 'center', width: 100,
                    formatter: function (value, rowData, rowIndex) {
                        var area = value + " " + rowData.province + " " + rowData.city
                        return area;
                    }
                },
                {field: 'subscribeTime', title: '关注时间', align: "right", align: 'center', width: 100},
            ]],
            toolbar: '#addTool',
            onBeforeLoad: function (data) {   // 查询参数

                var accId = $("#select_accountid").combobox("getValue");
                if (accId.length > 0) {
                    data.accountid = $("#select_accountid").combobox("getValue");
                } else {
                    data.accountid = $("#hidden_accountId").val();
                }
                data.nickname = $("#search_nickname").textbox("getValue");
            },
            onLoadSuccess: function (data) {

            }
        });
    },
    optFormat: function (value, rowData, rowIndex) {
        var btn = "";
        var setSupporterClick = " onClick='FollowerList.doSetSupporter(\"" + rowData.openId + "\"," + rowData.followAccount.id + ")'";
        var delSupporterClick = " onClick='FollowerList.doDelSupporter(\"" + rowData.openId + "\"," + rowData.followAccount.id + ")'";
        btn += "<div class='opt' '>";
        if (rowData.isSupporter) {
            btn += "<a class='ena' href='#'" + delSupporterClick + ">删除客服</a>";
            btn += "&nbsp;&nbsp;";
        } else {
            btn += "<a class='ena' href='#'" + setSupporterClick + ">设为客服</a>";
            btn += "&nbsp;&nbsp;";
        }
        btn += "&nbsp;&nbsp;";
        btn += "</div>";
        return btn;
    },
    doSetSupporter: function (openId, accountId) {
        $.ajax({
            url: '/wechat/wechatFollower/setSupporter.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'openId' : openId,
                'accountId' : accountId
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("微信客服管理", result.msg);
                    FollowerList.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('微信客服管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('微信客服管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }
        });
    },
    doDelSupporter: function (openId, accountId) {
        $.ajax({
            url: '/wechat/wechatFollower/delSupporter.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'openId' : openId,
                'accountId' : accountId
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("微信客服管理", result.msg);
                    FollowerList.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('微信客服管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('微信客服管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }
        });
    },
    initCommbox: function () {
        $("#select_accountid")
            .combobox(
            {
                url: '/wechat/wechatAutoReply/selectAccountList.jhtml',
                editable: true, //不可编辑状态
                cache: false,
                panelHeight: 'auto',//自动高度适合
                valueField: 'id',
                textField: 'account',
                onSelect: function (rec) {
                    var data = {
                        accountid: rec.id,
                        nickname: $("#search_nickname").textbox("getValue")
                    };

                    $('#follower_dg').datagrid('load', data);

                },
                onLoadSuccess: function () {
                    $("#select_accountid")
                        .combobox('setValue', $("#hidden_accountId").val());
                }
            });

    },

    initTextArea: function () {
        var content;
        KindEditor.ready(function (K) {
            content = K.create('#msg_content', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                items: ['link'],
                newlineTag: "br",
                filterMode: true,
                htmlTags: {a: ['class', 'href', 'target', 'name', 'style'], br: ['/']},
                afterFocus: function () {
                    this.sync();
                    var self = this;

                    if ("请输入消息内容" === self.text()) {
                        self.html("");
                    }


                },
                afterChange: function () {
                    this.sync();
                    var self = this;
                    var countStr = $("#changeFontCount").html();
                    var count = parseInt(countStr);
                    $("#changeFontCount").html(600 - self.count('text'));

                },
                afterBlur: function () {
                    var self = this;
                    self.sync();
                },
                afterCreate: function () {
                    var self = this;
                    KindEditor.ctrl(self.edit.doc, 13, function () {
                        self.sync();

                    });

                }
            });

            $('#msg_dialog').dialog({
                onClose: function () {

                    content.html("");
                }
            });
        });

    },


    initDialog: function () {
        $('#inform_dialog').dialog({
            onClose: function () {
                $("#inform_form").form("clear");
            }
        });


    },

    synFollower: function () {
        var accountIdStr = $("#select_accountid").combobox('getValue');
        var data = {accountid: accountIdStr};
        var url = "/wechat/wechatFollower/sysFollowerList.jhtml";
        $.messager.progress({
            title: '温馨提示',
            text: '数据处理中,请耐心等待...'
        });
        $.post(url, data,
            function (result) {

                if (result.success == true) {
                    $.messager.progress('close');
                    $('#follower_dg').datagrid('load', data);
                } else {
                    $.messager.progress('close');
                    show_msg("同步失败");
                }
            });


    },

    openInformDialog: function () {

        var rows = $("#follower_dg").datagrid("getSelections");

        var accountIdStr = $("#select_accountid").combobox('getValue');

        $("#sel_inform_type").combobox('setValue', 'deliver');

        if (accountIdStr.length > 0) {
            if (rows.length > 0) {
                var ids = [];
                $.each(rows, function (i, perValue) {
                    ids[i] = perValue.openId;
                });
                $("#inform_fansIds").val(ids.toString());
                $("#inform_accountId").val(accountIdStr);
                $('#inform_dialog').dialog({
                    title: '编辑通知',
                    width: 450,
                    height: 300,
                    closed: false,
                    cache: false,
                    modal: true
                });
            } else {
                show_msg("请选择通知粉丝！");
            }


        } else {
            show_msg("请选择公众号！");
        }


    },

    CheckUrl: function (str) {
        var RegUrl = new RegExp();
        RegUrl.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&\?\/.=]+$");//jihua.cnblogs.com
        if (!RegUrl.test(str)) {
            return false;
        }
        return true;
    },

    sendInform: function () {
        // 保存表单
        $('#inform_form').form('submit', {
            url: "/wechat/wechatFollower/sendTplMessage.jhtml",
            onSubmit: function () {
                var isValid = false;

                var sel_inform_type = $("#sel_inform_type").combobox("getValue");
                var inform_content = $("#inform_content").textbox("getValue");
                var jump_url = $("#jump_url").textbox("getValue");

                if (sel_inform_type.length > 0) {
                    isValid = true;
                } else {
                    show_msg("请完善通知类型！");
                }
                if (inform_content.length > 0) {
                    isValid = true;
                } else if (inform_content.length > 200) {
                    isValid = false;
                    show_msg("通知内容字数不超过200字！");
                } else {
                    isValid = false;
                    show_msg("请完善通知内容！");
                }
                return isValid;
            },
            success: function (result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if (result.success == true) {
                    $.messager.alert('温馨提示', '操作成功', 'info', function () {
                        $('#inform_dialog').dialog("close");
                    });
                } else {
                    show_msg("操作失败");
                }
            }
        });

    },
    openMsgDialog: function () {

        var rows = $("#follower_dg").datagrid("getSelections");

        var accountIdStr = $("#select_accountid").combobox('getValue');

        if (accountIdStr.length > 0) {
            $.messager.confirm('温馨提示', '群发消息，请注意群发次数！', function (flag) {
                if (flag) {

                    $("#msg_accountId").val(accountIdStr);
                    $('#msg_dialog').dialog({
                        title: '编辑消息',
                        width: 487,
                        height: 350,
                        closed: false,
                        cache: false,
                        modal: true
                    });
                    $("#msg_content").html("请输入消息内容");
                }

            });


        } else {
            show_msg("请选择公众号！");
        }

    },

    sendMsg: function () {

        $('#msg_form').form('submit', {
            url: "/wechat/wechatFollower/massSendallText.jhtml",
            onSubmit: function () {
                var isValid = false;

                var fontStr = $("#changeFontCount").html();

                var count = parseInt(fontStr);


                /*
                 if(count<600&&count>=0){
                 isValid = true;
                 }else{
                 isValid = false;
                 show_msg("请重新编辑消息内容！");
                 }*/

                var textArea = $("#msg_content").val();
                if ("请输入消息内容" === textArea) {
                    isValid = false;
                    show_msg("请重新编辑消息内容！");
                } else if (count >= 600) {
                    isValid = false;
                    show_msg("请输入消息内容！");
                } else if (count <= 0) {
                    isValid = false;
                    show_msg("请重新编辑消息内容！");
                } else {
                    isValid = true;
                }


                return isValid;
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.success == true) {
                    $.messager.alert('温馨提示', '操作成功', 'info', function () {
                        $('#msg_dialog').dialog("close");
                    });
                } else {
                    show_msg("操作失败");
                }
            }
        });

    }
};

    