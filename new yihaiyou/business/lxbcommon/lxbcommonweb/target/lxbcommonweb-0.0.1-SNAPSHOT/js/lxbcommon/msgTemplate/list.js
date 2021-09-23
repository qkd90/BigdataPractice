/**
 * Created by zzl on 2016/12/19.
 */
var MsgTemplateList = {
    table: $("#msgTemplateDg"),
    searcher: $("#msgTemplate-searcher"),
    init: function() {
        MsgTemplateList.initComp();
        MsgTemplateList.getDate();
    },
    initComp: function() {
        $('#search-sort-property').combobox({panelHeight: 'auto'});
        $('#search-sort-type').combobox({panelHeight: 'auto'});
    },
    getDate: function() {
        MsgTemplateList.table.datagrid({
            url: '/lxbcommon/msgTemplate/getMsgTemplateList.jhtml',
            fit: true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc"
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 60,
                    align: 'center'
                },
                {
                    field: 'title',
                    title: '标题',
                    width: 350,
                    align: 'center'
                },
                {
                    field: 'content',
                    title: '模板内容',
                    width: 500,
                    align: 'center'
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
                    width: 140,
                    align: 'center',
                    formatter: MsgTemplateList.optFormat
                }
            ]],
            toolbar: MsgTemplateList.searcher
        });
    },
    doSearch: function() {
        var searchForm = {};
        searchForm["msgTemplate.searchContent"] = MsgTemplateList.searcher.find("#search-content").val();
        searchForm['orderProperty'] = MsgTemplateList.searcher.find("#search-sort-property").combobox('getValue');
        searchForm['orderType'] = MsgTemplateList.searcher.find("#search-sort-type").combobox('getValue');
        MsgTemplateList.table.datagrid('load', searchForm);
    },
    optFormat: function (value, rowData, rowIndex) {
        var btn = "";
        var editClick = " onClick='MsgTemplateList.editMsgTemplate(" + rowData.id + ")'";
        btn += "<div class='opt' >";
        btn += "<a class='ena' href='#'" + editClick + ">编辑</a>"
        btn += "&nbsp;&nbsp;";
        btn += "</div>";
        return btn;
    },
    editMsgTemplate: function(id) {
        $("#msg_template_form").form({
            onLoadSuccess: function (result) {
                if (result.success) {
                    $("#msg_template_panel").dialog({
                        title: '编辑消息模板',
                        modal: true,
                        shadow:false,
                        onClose: function () {
                            $("#msg_template_form").form('clear');
                        }
                    });
                    $("#msg_template_panel").dialog('open');
                } else {
                    $.messager.alert('消息模板管理', "加载消息模板内容失败", 'error');
                }
            }
        });
        $("#msg_template_form").form('load', '/lxbcommon/msgTemplate/getMsgTemplateInfo.jhtml?id=' + id);
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/msgTemplate/doEdit.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("消息模板管理", "保存成功");
                    MsgTemplateList.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('消息模板管理', "保存失败, 请稍后重试!", 'error');
                }
            },
            error: function() {
                $.messager.alert('消息模板管理', "操作失败,请重试!", 'error');
            }
        });
    }
};
$(function() {
    MsgTemplateList.init();
});