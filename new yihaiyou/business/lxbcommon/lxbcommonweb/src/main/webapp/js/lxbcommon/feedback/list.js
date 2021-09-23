/**
 * Created by zzl on 2016/4/15.
 */
$(function () {
    Feedback.init();
});


var Feedback = {
    table: $("#feedbackDg"),
    searcher: $("#feedback-searcher"),
    status: [
        {'id': 'OPEN', 'text': '未回复'},
        {'id': 'REPLYED', 'text': '已回复'},
        {'id': 'DEL', 'text': '已删除'},
    ],
    initComponent: function() {
        //
    },
    init: function() {
        this.table.datagrid({
            fit:true,
            url:'/lxbcommon/feedback/getFeedbackList.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc"
            },
            columns: [[
                {field: 'id', title: 'ID', width: 55, align: 'center'},
                {field: 'status', title: '状态', width: 100, align: 'center',
                    formatter: function(value, rowData, index){
                        return Feedback.getStatus(value);
                    }
                },
                {field:'OPT',title:'操作',width:150,sortable: true, align: 'center',
                    formatter: function (value, rowData, index) {
                        var btn = "";
                        var replyClick = " onClick='Feedback.replyForm(" + rowData.id + ")'";
                        var delClick = " onClick='Feedback.delFeedback(" + rowData.id + ")'";
                        var closeClick = " onClick='Feedback.closeFeedback(" + rowData.id + ")'";
                        var detailClick = " onClick='Feedback.detailForm(" + rowData.id + ")'";
                        btn += "<div class='opt' '>";
                        if (rowData.status == "OPEN") {
                            btn += "<a class='ena' href='#'" + replyClick + ">回复</a>";
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='ena' href='#'" + closeClick + ">关闭</a>";
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='ena' href='#'" + delClick + ">删除</a>"
                        } else if (rowData.status == "REPLYED") {
                            btn += "<a class='disa'>回复</a>";
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='ena' href='#'" + closeClick + ">关闭</a>";
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='ena' href='#'" + delClick + ">删除</a>"

                        } else if (rowData.status == "CLOSED") {
                            btn += "<a class='disa'>回复</a>";
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='disa'>关闭</a>"
                            btn += "&nbsp;&nbsp;";
                            btn += "<a class='ena' href='#'" + delClick + ">删除</a>"
                        }
                        btn += "&nbsp;&nbsp;";
                        btn += "<a class='ena' href='#'" + detailClick + ">详情</a>";
                        btn += "</div>";
                        return btn;
                    }
                },
                {
                    field: 'creator.userName',
                    title: '反馈者',
                    width: 200,
                    align: 'center',
                    formatter: function(value, rowData, index) {
                        if(rowData.creator == null) {
                            return "<span style='color: #ff4c4d'>用户不存在</span>";
                        } else {
                            var nickName = rowData.creator.nickName;
                            var userName = rowData.creator.userName;
                            if (nickName != null && nickName != "") {
                                return "<span>" + nickName + "(" +rowData.creator.id+ ")" + "</span>";
                            } else  if(userName != null && userName != "") {
                                return "<span>" + userName + "(" +rowData.creator.id+ ")" + "</span>";
                            } else {
                                return "<span>" + "-" + "(" +rowData.creator.id+ ")" + "</span>";
                            }
                        }
                    }
                },
                {
                    field: 'contact',
                    title: '联系方式',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'content',
                    title: '反馈内容',
                    width: 400,
                    align: 'center'
                },
                {
                    field: 'replier.userName',
                    title: '回复者',
                    width: 100,
                    align: 'center',
                    formatter: function(value, rowData, index) {
                        if(rowData.replyContent != null && rowData.replyContent != "" && rowData.replier == null) {
                            return "<span style='color: #ff4c4d'>用户不存在</span>";
                        } else {
                            if (rowData.replyContent == null || rowData.replyContent == "") {
                                return "<span style='color: #FF2F2F;'>暂无</span>";
                            } else {
                                var userName = rowData.replier.userName;
                                if (userName != null && userName != "") {
                                    return "<span>" + userName + "(" +rowData.replier.id+ ")" + "</span>";
                                } else {
                                    return "<span>" + "-" + "(" +rowData.replier.id+ ")" + "</span>";
                                }
                            }
                        }
                    }
                },
                {
                    field: 'replyContent',
                    title: '回复内容',
                    width: 400,
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '反馈时间',
                    width: 180,
                    align: 'center'
                },
                {
                    field: 'replyTime',
                    title: '回复时间',
                    width: 180,
                    align: 'center'
                }
            ]],
            toolbar: this.searcher,
            onBeforeLoad: function(data) {
                if ($.trim($("#qry_contact").textbox("getValue"))) {
                    data['feedback.contact'] = $.trim($("#qry_contact").textbox("getValue"));
                }
                if ($("#qry_status").combobox("getValue")) {
                    data['feedback.status'] = $("#qry_status").combobox("getValue");
                }
            }
        });
    },
    doSearch: function() {
        this.table.datagrid('load', {});
    },
    doClear: function() {
        $("#qry_contact").textbox("setValue", "");
        $("#qry_status").combobox("setValue", "");
        this.table.datagrid('load', {});
    },
    detailForm: function(id) {
        var detailUrl = "/lxbcommon/feedback/getFeedbackDetail.jhtml?id=" + id;
        $("#detail_form").form("load", detailUrl);
        $("#detail_panel").dialog({
            title:'回复客户反馈',
            modal:true,
            onClose:function(){
                $("#detail_form").form('clear');
            }
        });
        $("#detail_panel").dialog("open");
    },
    replyForm: function(id) {
        var detailUrl = "/lxbcommon/feedback/getFeedbackDetail.jhtml?id=" + id;
        $("#reply_form").form("load", detailUrl);
        $("#reply_panel").dialog({
            title:'回复客户反馈',
            modal:true,
            onClose:function(){
                $("#reply_form").form('clear');
            }
        });
        $("#reply_panel").dialog("open");
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/feedback/replyFeedback.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("反馈管理", result.msg);
                    Feedback.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('反馈管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('反馈管理', "操作失败,请重试!", 'error');
            }
        });
    },
    delFeedback: function(id) {
        $.ajax({
            url: '/lxbcommon/feedback/delFeedback.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                if (result.success) {
                    showMsgPlus("反馈管理", result.msg);
                    Feedback.table.datagrid('reload');
                } else {
                    $.messager.alert('反馈管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('反馈管理', result.msg, 'error');
            }
        });
    },
    closeFeedback: function(id) {
        $.ajax({
            url: '/lxbcommon/feedback/closeFeedback.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                if (result.success) {
                    showMsgPlus("反馈管理", result.msg);
                    Feedback.table.datagrid('reload');
                } else {
                    $.messager.alert('反馈管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('反馈管理', result.msg, 'error');
            }
        });
    },
    getStatus: function(status){
        if (status == "OPEN") {
            return "<span style='color: #FF2F2F'>未回复</span>";
        } else if (status == "REPLYED") {
            return "<span style='color: #3e8f3e'>已回复</span>";
        } else if (status == "CLOSED") {
            return "<span style='color: #706f6e'>已关闭</span>";
        }
    }
}