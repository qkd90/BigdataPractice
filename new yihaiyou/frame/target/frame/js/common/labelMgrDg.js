/** 标签选择面板
 * 使用方法：引入该js，调用入口：LabelMgrDg.open('LINE', productId, Line.doGener);
 */
var LabelMgrDg = {
    initFlag : false,   // 是否已初始化标识
    targetType : null,
    targetId : null,
    callbackFunc : null,    // 窗口关闭回调函数，不带参数
	// 页面内容初始化
	initComp : function() {
        if(LabelMgrDg.initFlag) {
            return;
        }
        LabelMgrDg.initFlag = true;
        // 动态加入标签内容
        var html = '<div id="frmLabelDg" style="width:660px;height:460px;">'
            + '<div id="frmLabelTabs">'
            + '<div id="frmSelableLabel" title="可选标签">'
            + ' <div id="frmSelableLabelGridTb"><input id="frmSelableLabelGridQry" style="width:240px;"/></div>'
            + ' <table id="frmSelableLabelGrid"></table>'
            + '</div>'
            + '<div id="frmSeledLabel" title="已选标签">'
            //+ ' <div id="frmSeledLabelGridTb"><input id="frmSeledLabelGridQry" style="width:130px;"/></div>'
            + ' <table id="frmSeledLabelGrid"></table>'
            + '</div>'
            + '</div>'
            + '</div>';
        $('body').append(html);
        // 渲染组件
        $("#frmLabelDg").dialog({
            title : "选择标签",
            resizable:false,
            modal:true,
            closed:true,
            collapsible:false,
            shadow:false,
            onClose:function() {
                if (LabelMgrDg.callbackFunc) {
                    LabelMgrDg.callbackFunc.apply(this, []);
                }
                $("#frmSelableLabelGridQry").textbox("setValue", '');
            }
        });
        $('#frmLabelTabs').tabs({
            border:false,
            fit:true
        });
        // 工具栏组件
        $('#frmSelableLabelGridQry').searchbox({
            searcher:function(value,name){
                LabelMgrDg.search('frmSelableLabelGrid', 0);
            },
            prompt:'请输入标签名称'
        });
        //$('#frmSeledLabelGridQry').searchbox({
        //    searcher:function(value,name){
        //        LabelMgrDg.search('frmSeledLabelGrid', 1);
        //    },
        //    prompt:'请输入标签名称'
        //});
        // 表格组件
        $('#frmSelableLabelGrid').treegrid({
            fit: true,
            //url: '/labels/labels/listLabels.jhtml',
            data:[],
            idField:'id',
            treeField:'name',
            border:true,
            striped:true,
            pagination:false,
            title :"",
            columns:[[
                {title:'标签名称', field:'name', width:280},
                {title:'标签别名', field:'alias', width:200},
                {title:'标签状态', field:'status', width:60,
                    formatter:function(value, rowData) {
                        if (value == "USE") {
                            return "正常";
                        } else if (value == "IDLE") {
                            return "隐藏";
                        } else {
                            return "";
                        }
                    }
                },
                {title:'操作',field:'opt',width:80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (rowData.selected) {
                            return "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='LabelMgrDg.doUnbindTree(" + rowData.id + ",\"" + rowData.parentId + "\")'>取消关联</a>";
                        } else {
                            if (rowData.status == "USE" && rowData.leaf) {
                                return "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='LabelMgrDg.doBindTree(" + rowData.id + ",\"" + rowData.parentId + "\")'>关联标签</a>";
                            } else {
                                return "";
                            }
                        }
                    }
                }
            ]],
            toolbar: '#frmSelableLabelGridTb',
            onExpand : function(row) {
                //var children = $("#frmSelableLabelGrid").treegrid('getChildren', row.id);
                //if (children.length > 0) {
                //    $("#frmSelableLabelGrid").treegrid('refresh', row.id);
                //}
                LabelMgrDg.qryWay = null;
            },
            onBeforeLoad : function(row, param) {
                if (row) {
                    param.parentId = row.id;
                }
                param.targetType = LabelMgrDg.targetType;
                param.targetId = LabelMgrDg.targetId;
                param.keyword = $("#frmSelableLabelGridQry").textbox("getValue");
                //param.status = 'USE'; // 为了保证树形显示正确，不过滤状态
                if (LabelMgrDg.qryWay) {
                    param.qryWay = 'ALLTREE';  // 参数，标记查询所有
                }
            }
        });
        $("#frmSeledLabelGrid").datagrid({
            fit:true,
            //url:'/line/line/search.jhtml',
            data: [],
            singleSelect:true,
            border:true,
            striped:true,//斑马线
            pagination:false,
            columns:[[
                {title:'标签名称', field:'name', width:240},
                {title:'标签状态', field:'status', width:60,
                    formatter:function(value, rowData) {
                        if (value == "USE") {
                            return "正常";
                        } else if (value == "IDLE") {
                            return "隐藏";
                        } else {
                            return "";
                        }
                    }
                },
                {title:'所在路径', field:'dir', width:220},
                //{title:'显示排序', field:'sort', width: 60},
                {title:'操作',field:'opt',width:80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        return "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='LabelMgrDg.doUnbindTable(" + rowData.id + ")'>取消关联</a>";
                    }
                }
            ]],
            //toolbar: '#frmSeledLabelGridTb',
            onBeforeLoad : function(data) {
                data['targetType'] = LabelMgrDg.targetType;
                data.targetId = LabelMgrDg.targetId;
                //data.keyword = $("#frmSeledLabelGridQry").textbox("getValue");
                //data.status = 'USE';
            }
        });
        LabelMgrDg.initEvent();
    },
    // 绑定事件
    initEvent : function() {
        $('#frmLabelTabs').tabs({
            onSelect: function(title, index){
                var tab = $('#frmLabelTabs').tabs('getTab', index);
                var activeTabId = tab[0].id;
                LabelMgrDg.search(activeTabId+'Grid', index);
            }
        });
    },
    // 查询
    search : function(gridId, index) {
        if (index === 0) {
            var keyword = $("#frmSelableLabelGridQry").textbox("getValue");
            if (keyword) {
                LabelMgrDg.qryWay = 'ALLTREE';  // 参数，标记查询所有
            }
            //$('#'+gridId).treegrid({url:'/labels/labels/listLabels.jhtml'});
            $('#'+gridId).treegrid({url:'/labels/labels/listNoExistsLabels.jhtml'});
        } else if (index == 1) {
            $('#'+gridId).datagrid({url:'/labels/labels/listBindLabels.jhtml'});
        }
    },
    // 取消关联（表格树）
    doUnbindTree : function(id, parentId) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/labels/productLabels/deleteLabelItem.jhtml",
            {labelIds : id, type : LabelMgrDg.targetType, targetId : LabelMgrDg.targetId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    if (parentId) {
                        $("#frmSelableLabelGrid").treegrid('reload', parentId);
                    } else {
                        $('#frmSelableLabelGrid').treegrid('reload');
                    }
                } else {
                    show_msg("操作失败");
                }
            }
        );
    },
    // 取消关联（表格）
    doUnbindTable : function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/labels/productLabels/deleteLabelItem.jhtml",
            {labelIds : id, type : LabelMgrDg.targetType, targetId : LabelMgrDg.targetId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    LabelMgrDg.search('frmSeledLabelGrid', 1);
                } else {
                    show_msg("操作失败");
                }
            }
        );
    },
    // 关联标签（表格树）
    doBindTree : function(id, parentId) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/labels/productLabels/saveLabelItem.jhtml",
            {labelIds : id, type : LabelMgrDg.targetType, targetId : LabelMgrDg.targetId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    if (parentId) {
                        $("#frmSelableLabelGrid").treegrid('reload', parentId);
                    } else {
                        $('#frmSelableLabelGrid').treegrid('reload');
                    }
                } else {
                    show_msg("操作失败");
                }
            }
        );
    },
    // 打开窗口，参数：类型、标识
    open : function(targetType, targetId, callbackFunc) {
        LabelMgrDg.targetType = targetType;
        LabelMgrDg.targetId = targetId;
        LabelMgrDg.callbackFunc = callbackFunc;
        LabelMgrDg.initComp();
        $("#frmLabelDg").dialog("open");
        // 判断是否是选择第一个，如果是刷数据，否则默认打开选择第一个
        var tab = $('#frmLabelTabs').tabs('getSelected');
        var index = $('#frmLabelTabs').tabs('getTabIndex',tab);
        if (index === 0) {
            LabelMgrDg.search('frmSelableLabelGrid', 0);
        } else {
            $('#frmLabelTabs').tabs('select', 0);
        }
    }
};