/**
 * Created by zzl on 2016/4/25.
 */

$(function () {
    CommentMgr.initComponent();
    CommentMgr.initCommentData();
});

var CommentMgr = {
    needToLoad: false,
    table: $("#commentTg"),
    searcher: $("#comment-searcher"),
    proType: [
        {'id': '', 'text': '全部'},
        {'id': 'scenic', 'text': '景点'},
        {'id': 'delicacy', 'text': '美食'},
        {'id': 'hotel', 'text': '酒店'},
        {'id': 'recplan', 'text': '游记'},
        {'id': 'sailboat', 'text': '游艇帆船'}
    ],
    initComponent: function () {
        $("#search-comment-type").combobox({
            data: this.proType,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            width: 60,
            onSelect: function(record) {
                if (record.id == "0") {
                    $("#search-comment-scoreType").combobox('setValue', null);
                    $("#search-comment-scoreType").combobox('disable');
                } else {
                    CommentMgr.initScoreType(record.id);
                }
            },
            onLoadSuccess: function () {
                var data =  $("#search-comment-type").combobox('getData');
                $("#search-comment-type").combobox('setValue', data[0].id);
            }
        });
        $("#search-comment-scoreType").combobox('disable');
    },
    initScoreType: function(targetType) {
        $("#search-comment-scoreType").combobox({
            url: '/lxbcommon/comment/comboScoreTypeList.jhtml?targetType=' + targetType,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            width: 90,
            onSelect: function (record) {
                $("input[name = 'scoreTypeId']").val(record.id);
            },
            onLoadSuccess: function () {
                var data =  $("#search-comment-scoreType").combobox('getData');
                $("#search-comment-scoreType").combobox('setValue', data[0].id);
                $("#search-comment-scoreType").combobox('enable');
            },
            onLoadError: function() {
                $.messager.alert('评论管理', "无法获取该类型的评分项!", 'error');
            }
        });
    },
    initCommentData: function () {
        this.table.treegrid({
            url: '/lxbcommon/comment/getCommentData.jhtml',
            idField:'id',
            treeField:'content',
            method: 'post',
            queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc"
            },
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            border:true,
            striped:true,
            //title: '评论管理',
            height:'100%',
            fit: true,
            fitColumns: true,
            columns: [[
                {
                    title: 'ID',
                    field: 'id',
                    width: 50,
                    align: 'center'
                },
                {
                    title: '操作',
                    field: 'OPT',
                    width: '120',
                    align: 'center',
                    formatter: CommentMgr.optFormat
                },
                {
                    title: '评论内容',
                    field: 'content',
                    width: 400
                    //align: 'center'
                },
                {
                    title: '用户名',
                    field: 'userName',
                    width: 80,
                    align: 'center'
                },
                {
                    title: '评论类型',
                    field: 'type',
                    width: 100,
                    align: 'center',
                    formatter: CommentMgr.formatCommentType
                },
                {
                    title: '评论时间',
                    field: 'createTime',
                    width: 140,
                    align: 'center'
                },
                {
                    title: '评分',
                    field: 'commentScores',
                    width: 120,
                    align: 'center',
                    formatter: CommentMgr.formatCommentScores
                }
            ]],
            onLoadSuccess: function(row, data) {
                if (CommentMgr.needToLoad && data.rows.length == 0) {
                    CommentMgr.table.treegrid("getPager").pagination('refresh', {total: 0, pageNumber: 0});
                }
            },
            toolbar: this.searcher
        });
    },
    doSearch: function() {
        var searchForm = {};
        var searchType = this.searcher.find("#search-type").val();
        var searchContent = this.searcher.find("#search-content").val();
        if (searchContent != null && searchContent != "") {
            searchForm[searchType] = searchContent;
        }
        searchForm['comment.status'] = this.searcher.find("#search-status").val();
        searchForm['comment.type'] = this.searcher.find("input[name = 'productType']").val();
        searchForm['comment.scoreTypeId'] = this.searcher.find("input[name = 'scoreTypeId']").val();
        searchForm['comment.minScore'] = this.searcher.find("#minScore").val();
        searchForm['comment.maxScore'] = this.searcher.find("#maxScore").val();
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        CommentMgr.needToLoad = true;
        this.table.treegrid('load', searchForm);
    },
    optFormat: function(value, rowData, rowIndex) {
        var btn = "";
        var replyClick = " onClick='CommentMgr.replyComment(" + rowData.id + ")'";
        var editClick = " onClick='CommentMgr.editComment(" + rowData.id + ")'";
        var delClick = " onClick='CommentMgr.delComment(" + rowData.id + ")'";
        btn += "<div class='opt' '>";
        if (rowData.repliedId == null || rowData.repliedId == "") {
            btn += "<a class='ena' href='#'" + replyClick + ">回复</a>";
        }
        btn += "&nbsp;&nbsp;";
        btn += "<a class='ena' href='#'" + editClick + ">编辑</a>";
        btn += "&nbsp;&nbsp;";
        btn += "<a class='ena' href='#'" + delClick + ">删除</a>"
        btn += "</div>";
        return btn;
    },

    replyComment: function(id) {
        var detailUrl = "/lxbcommon/comment/getCommentDetail.jhtml?id=" + id;
        $("#reply_form").form("load", detailUrl);
        $("#reply_panel").dialog({
            title:'评论管理',
            modal:true,
            onClose:function(){
                $("#reply_form").form('clear');
            }
        });
        $("#reply_panel").dialog("open");
    },

    editComment: function(id) {
        var detailUrl = "/lxbcommon/comment/getCommentDetail.jhtml?id=" + id;
        $("#edit_form").form("load", detailUrl);
        $("#edit_panel").dialog({
            title:'评论管理',
            modal:true,
            onClose:function(){
                $("#edit_form").form('clear');
            }
        });
        $("#edit_panel").dialog("open");
    },

    delComment: function(id) {
        $.messager.confirm('评论管理', '确认要删除这条评论吗? 删除后不可恢复!', function(r){
            if (r){
                $.ajax({
                    url: '/lxbcommon/comment/delComment.jhtml',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        id: id
                    },
                    success: function(result) {
                        if (result.success) {
                            showMsgPlus("评论管理", result.msg);
                            CommentMgr.table.treegrid('reload');
                        } else {
                            $.messager.alert('评论管理', result.msg, 'error');
                            CommentMgr.table.treegrid('reload');
                        }
                    },
                    error: function(result) {
                        $.messager.alert('评论管理', result.msg, 'error');
                    }
                });
            }
        });
    },

    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/comment/replyComment.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("评论管理", result.msg);
                    CommentMgr.table.treegrid('reload');
                } else if (!result.success) {
                    $.messager.alert('评论管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('评论管理', "操作失败,请重试!", 'error');
            }
        });
    },
    editForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/comment/editComment.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("评论管理", result.msg);
                    CommentMgr.table.treegrid('reload');
                } else if (!result.success) {
                    $.messager.alert('评论管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('评论管理', "操作失败,请重试!", 'error');
            }
        });
    },

    formatCommentType: function(type) {
        if (type == "scenic") {
            return "景点评论";
        } else if (type == "delicacy") {
            return "美食评论";
        } else if (type == "recplan") {
            return "游记评论"
        } else if (type == "hotel") {
            return "酒店评论";
        }  else if (type == "sailboat") {
            return "游艇帆船评论";
        } else {
            return "其他评论";
        }
    },
    formatCommentScores: function (value, rowData, index) {
        var dataLength = value.length;
        var scoreInfo = "[";
        $.each(value, function (i, item) {
            if (i != dataLength - 1) {
                scoreInfo += item.commentScoreType.name + ":" + item.score + " , ";
            } else {
                scoreInfo += item.commentScoreType.name + ":" + item.score;
            }
        });
        scoreInfo += "]"
        if (scoreInfo == "[]") {
            return "-";
        }
        return scoreInfo;
    }
};