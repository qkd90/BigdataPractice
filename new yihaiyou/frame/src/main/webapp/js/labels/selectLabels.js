var SelectLabel = {
    searcher: $("#selectLabel-searcher"),
    table: $("#dialog_tree"),
    init: function () {
        SelectLabel.initTree();
        SelectLabel.initCheck();
    },
    saveItems: function () {
        $.messager.confirm('确认', '您确认保存吗？', function (r) {
            if (r) {
                window.parent.LabelManage.doSearch();
                window.parent.$('#dlg').dialog('close');
            }
        });
    },
    initCheck: function () {
        $.each($(".m_right"), function (i, perValue) {
            if ($(perValue).prop("checked")) {
                alert(perValue.name);
            } else {
            }
        });
    },
    subSort: function () {
        var sort = $("#sortId").textbox('getValue');
        var laId = $("#hid_label").val();
        if (sort.length > 0) {
            var data = {
                sort: sort,
                labelId: laId,
                targetId: $("#hi_targetId").val(),
                type: $("#hi_type").val()
            };
            var url = "/labels/productLabels/saveSort.jhtml";
            $.post(url, data,
                function (result) {
                    if (result.success) {
                        var columns = $('#dialog_tree').datagrid("sort").columns;
                        var rows = $('#dialog_tree').datagrid("getRows");
//				                	 alert(laId+","+sort);
//				                	 $("#m"+laId+"").val(sort);
//				                	 $("#m"+laId+"").val(sort);
                        $('#dialog_tree').treegrid('reload');
                        window.parent.$('#tgrid').datagrid('reload');
                        $('#sortForm').form('clear');
                        $('#dlg').dialog('close');
                    } else {
                        show_msg("序号重复！");
                        $("#sortId").textbox('setValue', "");
                    }
                });
        } else {
            show_msg("请设置排序号");
        }
    },
    openSort: function (id) {
        if ($("#m" + id + "").prop("checked")) {
            var targetId = $("#hi_targetId").val();
            var laId = id;
            var type = $("#hi_type").val();
            var data = {
                laId: laId,
                type: type,
                targetId: targetId
            };
            var url = "/labels/productLabels/editLaItemSort.jhtml";
            $.post(url, data,
                function (result) {
                    if (result.success) {
                        $("#sortId").textbox('setValue', result.sort);
                        $('#dlg').dialog({
                            closed: false,
                            cache: false,
                            modal: true
                        });
                        $("#hid_label").val(id);
                        $('#dlg').dialog('open');
                    } else {
                        $('#dlg').dialog({
                            closed: false,
                            cache: false,
                            modal: true
                        });
                        $("#hid_label").val(id);
                        $('#dlg').dialog('open');
                    }
                });
        } else {
            show_msg("请选择标签！");
        }
    },
    initTree: function () {
        var id = $("#hi_targetId").val();
        var type = $("#hi_type").val();
        this.table.treegrid({
            url: '/labels/productLabels/showLabelTree.jhtml?targetId=' + id + '&type=' + type,
            idField: 'id',
            method: 'get',
            treeField: 'name',
            checkbox: true,
            title: '标签列表',
            singleSelect: true,
//			    collapsible:true,
            height: '100%',
            pagination: true,
            pageSize: 10,
            pageList: [10],
//			    showHeader:false,
            columns: [[
                {
                    title: '', field: 'ck', width: '5%',
                    formatter: function (value, row, index) {
                        if (row.hasChild == "1") {
                            return value;
                        } else if (row.hasChild == "0") {
                            if (row.hasLabel == "1") {
                                return "<input type='checkbox' id='m" + row.id + "' class='m_right' name='" + row.id + "' onchange='SelectLabel.saveLabelItem(" + row.id + ")' value='" + row.sort + "' checked='checked' />";
                            }
                            if (row.hasLabel == "0") {
                                return "<input type='checkbox' id='m" + row.id + "' class='m_right' name='" + row.id + "' onchange='SelectLabel.saveLabelItem(" + row.id + ")' value='" + row.sort + "' />";
                            }
                        }
                    }
                },
                {title: '标签名称', field: 'name', width: 450},
                {
                    title: '排序', field: 'sort', width: '10%',
                    formatter: function (value, row, index) {
                        if (value != "0") {
                            return value;
                        }
                    }
                },
                {
                    title: '操作', field: 'opt', width: '25%',
                    formatter: function (value, row, index) {
                        if (row.hasChild == "1") {
                            return "";
                        } else {
                            return "<a href='javascript:void(0)' id='b" + row.id + "' style='color:blue;'  onclick='SelectLabel.openSort(" + row.id + ")'>设置排序</a>";
                        }
                    }
                }
            ]],
            toolbar: this.searcher
        });
    },
    doSearch: function () {
        var searchForm = {};
        searchForm['label.searchName'] = encodeURI(this.searcher.find("#search-content").val());
        this.table.treegrid('load', searchForm);
    },
    saveLabelItem: function (id) {
        var row = $('#dialog_tree').treegrid('find', id);
        if ($("#m" + row.id + "").prop("checked")) {
            var data = {
                targetId: $("#hi_targetId").val(),
                type: $("#hi_type").val(),
                labelIds: row.id,
            };
            var url = "/labels/productLabels/saveLabelItem.jhtml";
            $.post(url, data,
                function (result) {
                    if (result.success) {
                        $('#dialog_tree').treegrid('reload');
                        show_msg("选择成功！");
                        window.parent.$('#tgrid').datagrid('reload');
//			                	 window.parent.LabelManage.doSearch();
                    }
                });
        } else {
            var data = {
                targetId: $("#hi_targetId").val(),
                type: $("#hi_type").val(),
                labelIds: row.id,
            };
            var url = "/labels/productLabels/deleteLabelItem.jhtml";
            $.post(url, data,
                function (result) {
                    if (result.success) {
                        $('#dialog_tree').treegrid('reload');
                        show_msg("取消选择！");
                        window.parent.$('#tgrid').datagrid('reload');
//			                	 window.parent.LabelManage.doSearch();
                    }
                });
        }
//			console.log(row);
    }
}
$(function () {
    SelectLabel.init();
    $('#dlg').dialog('close');
//	$("#dlg").dialog('close');
});