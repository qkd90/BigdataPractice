/**
 * Created by vacuity on 15/10/14.
 */
$(function () {
    GoodsList.init();
});

var GoodsList={
    init: function(){
        GoodsList.getCategoryType();
    },
    getSelectTabId: function() {
        var tab = $("#tt").tabs('getSelected');
        var tab_id = tab.panel('options').id;
        return tab_id;
    },
    goTargetTab: function(title) {
        $("#tt").tabs('select', title);
    },
    statusData : [
        {'id':'SHOW','text':'显示'},
        {'id':'HIDE','text':'隐藏'}
        //{'id':'DEL','text':'删除'}
    ],
    selectCategoryType: "",
    getCategoryType: function() {
        $.ajax({
            url: '/goods/categoryType/categoryTypeList.jhtml',
            type: 'post',
            dataType: 'json',
            data: {},
            success: function(result){
                if (result.success) {
                    $.each(result.data, function(index, typeItem) {
                        GoodsList.addTab(typeItem);
                        // 初始化各个具体分类数据
                        GoodsList.initCategoryData(typeItem);
                    });
                    $("#tt").tabs('select', 0);
                } else {
                    $.messager.alert('分类管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('分类管理', '获取分类信息出错,稍候重试!', 'error');
            }
        });
    },
    addTab: function(typeItem) {
        $("#tt").tabs('add', {
            title: typeItem.typeDes,
            closable: false,
            content: GoodsList.getTabContent(typeItem.type, typeItem.typeDes),
            id: typeItem.type
        });
    },
    getTabContent: function(type, typeDes) {
        var content = "";
        content += "<div title='" + typeDes + "' style='height: 100%;'>";
        content += "<table id='"+ type + "table' class='easyui-treegrid' style='height:auto;' fit='true'>";
        content += "</table></div>";
        return content;
    },
    initCategoryData: function(typeItem) {
        $("#" + typeItem.type + "table").treegrid({
            url: '/goods/goods/getCategoryData.jhtml',
            idField:'id',
            treeField:'name',
            queryParams: {
                'typeId': typeItem.id
            },
            columns:[[
                {field:'name',title:'分类标题',width:500,sortable: true},
                {field:'status',title:'是否显示',width:300,align:'left',sortable: true,formatter:function(value,row,index){
                    var status = row.status;
                    if (status != null ) {
                        if (status == "SHOW") {
                            return "<span style='color:blue'>显示</span>";
                        } else if (status == "HIDE") {
                            return "<span style='color:brown'>隐藏</span>";
                        } else if (status == "DEL")  {
                            return "<span style='color:darkgray'>隐藏</span>";
                        }
                    }
                } },
                {field:'sortOrder',title:'排序',width:300,sortable: true},
                {field:'end',title:'操作',width:250,sortable: true,formatter:function(value, row, index){
                    var delClick = " onClick='GoodsList.delCategory(" + row.id + ")'";
                    var editClick=" onClick='GoodsList.editCategory(" + row.id + ")'";
                    var editbtn = "<a style='color: blue;text-decoration: underline;' href='#'" + editClick + ">修改</a>";
                    var deletebtn = "<a style='color: blue;text-decoration: underline;' href='#'" + delClick + ">删除</a></div>";
                    var space = "&nbsp;";
                    var btn = space + editbtn + space + deletebtn;
                    return btn;
                } }
            ]]
        });
    },
    refresh: function() {
        $("#" + GoodsList.getSelectTabId() + "table").treegrid('reload');
    },
    initComponent: function(compName) {
        //初始化顶级分类控件
        $("#" + compName).combobox({
            url: '/goods/categoryType/comboxTypeList.jhtml',
            valueField: 'type',
            textField: 'typeDes',
            panelHeight: 'auto',
            onSelect: function (record) {
                if (compName == "add_category_type") {
                    AddCategory.initCombobox(record.type);
                } else if (compName == "edit_category_type") {
                    EditCategory.initCombobox(record.type);
                }
                GoodsList.selectCategoryType = record.type;
            },
            onLoadSuccess: function() {
                $("#" + compName).combobox("select", GoodsList.getSelectTabId());
            }
        });
        // 状态选择控件
        $("#add_status").add("#edit_status").combobox({
            data: GoodsList.statusData,
            valueField:'id',
            textField:'text',
            panelHeight:'auto'
        });
    },
    addCategory: function(){
        GoodsList.initComponent("add_category_type");
        $("#add_panel").dialog({
            title:'新增分类',
            modal:true,
            onClose:function(){
                $("#add_categoryform").form('clear');
            }
        });
        AddCategory.initCombobox(GoodsList.selectCategoryType);
        $("#add_panel").dialog("open");
    },
    editCategory: function(id){
        GoodsList.initComponent("edit_category_type");
        var editUrl="/goods/goods/getCategoryMap.jhtml?id=" + id;
        $("#edit_categoryform").form('load', editUrl,
            {
                onLoadSuccess: function () {
                    EditCategory.initCombobox(GoodsList.selectCategoryType);
                }
            });
        $("#edit_panel").dialog({
            title:'编辑分类',
            modal:true,
            onClose:function(){
                $("#edit_categoryform").form('clear');
            }
        });
        $("#edit_panel").dialog("open");
    },
    delCategory: function(id) {
        $.ajax({
            url: '/goods/goods/delCategory.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id,
            },
            success: function (result) {
                if (result.success) {
                    showMsgPlus("分类管理", result.msg);
                    $("#" + GoodsList.getSelectTabId() + "table").treegrid('reload');
                } else {
                    $.messager.alert('分类管理', result.msg, 'error');
                }
            },
            error: function(result) {
                $.messager.alert('分类管理', result.msg, 'error');
            }
        });
    },
    submitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/goods/goods/getForm.jhtml',
            success: function (data) {
                data = eval('(' + data + ')');
                if (data.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("分类管理", data.msg);
                    //console.log($("#add_category_type").combobox('getValue'));
                    $("#" + GoodsList.selectCategoryType + "table").treegrid('reload');
                    GoodsList.goTargetTab(data.title);

                } else if (!data.success) {
                    $.messager.alert('分类管理', data.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('分类管理', "操作失败,请重试!", 'error');
            }
        });
    },
    addSuperCatrgory: function() {
        $("#add_super_panel").dialog({
            title:'新增顶级分类',
            modal:true,
            onClose:function(){
                $("#add_supercategoryform").form('clear');
            }
        });
        $("#add_super_panel").dialog("open");
    },
    doAddSuperCatrgory: function() {
        $("#add_supercategoryform").form('submit', {
            url: '/goods/categoryType/addCategoryType.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#add_super_panel").dialog("close");
                    showMsgPlus("分类管理", result.msg, 1000);
                    GoodsList.addTab(result.data);
                    GoodsList.initCategoryData(result.data);
                } else if (!result.success) {
                    $.messager.alert('分类管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('分类管理', "操作失败,请重试!", 'error');
            }
        });
    }
};