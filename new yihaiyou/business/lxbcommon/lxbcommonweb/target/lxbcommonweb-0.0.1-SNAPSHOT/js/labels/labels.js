var Labels = {
    selectedId:null,    // 记录单选时标识
    queryLabelId:null,  // 记录产品标签的标识
    opt:null,
    init:function(){
        Labels.initTreeTable();
        Labels.initProductLabelComp();
        // 初始化标签选择下拉框
        $('#moveLabel').combobox({
            data : [],
            loader: function(param, success, error){
                if (param.q) {
                    $.ajax({
                        dataType: 'json',
                        type: 'POST',
                        url: '/labels/labels/listLabelByKey.jhtml',
                        data: {name: param.q},
                        success: function (data) {
                            success(data);
                        },
                        error: function () {
                            //error.apply(this, arguments);
                        }
                    });
                } else {
                    $("#moveLabel").combobox("clear");
                }
            },
            prompt:'请输入并选择标签',
            valueField:"id",
            textField:"name",
            mode: 'remote'
        });
        // 产品标签窗口
        $("#productLabelDg").dialog({
            onClose:function() {
                $('#qryTargetType').combobox('setValue', '');
                // 城市清除值
                $('#qryCity').textbox('setValue', '');
                $('#qryCity').attr('data-country', '');
                $('#qryCity').attr('data-province', '');
                $('#qryCity').attr('data-city', '');
            }
        });
    },
    // 查询
    doSearch:function() {
        var keyword = $("#qryKeyword").textbox("getValue");
        var params = {};
        if (keyword) {
            params.qryWay = 'ALLTREE';  // 参数，标记查询所有
        }
        $('#tg').treegrid('load', params);
        $('#tg').treegrid('unselectAll');
    },
    // 初始化
    initTreeTable:function(){
        $('#tg').treegrid({
            fit: true,
            fitColumns:true,
            url: '/labels/labels/listLabels.jhtml',
            idField:'id',
            treeField:'name',
            border:true,
            striped:true,
            singleSelect:false,
            pagination:false,
            //title :"标签信息",
            //pageList:[ 10, 20, 30 ],
//				rownumbers:true,
            columns:[[
                {title:'标签名称', field:'name', width:200},
                {title:'标签别名', field:'alias', width: 120},
                {title:'标签排序', field:'sort', width: 80},
                {title:'标签状态', field:'status', width:100,
                    formatter:function(value, rowData) {
                        if (value === "USE") {
                            return "正常";
                        } else if (value === "IDLE") {
                            return "隐藏";
                        } else {
                            return "";
                        }
                    }
                },
                {title:'创建时间',field:'createTime',width:150},
                {title:'操作',field:'opt',width:80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Labels.editNodes(" + rowData.id + "," + rowIndex + ")'>编辑</a>&nbsp;&nbsp;"
                            + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='Labels.openProductLabelDg(" + rowData.id + ")'>产品标签</a>";
                        return btn;
                    }
                }
            ]],
            toolbar:'#labelListId',
            onExpand : function(row) {
                //var children = $("#tg").treegrid('getChildren', row.id);
                //if (children.length > 0) {
                //    $("#tg").treegrid('refresh', row.id);
                //}
            },
            onBeforeLoad : function(row, param) {
                if (row) {
                    param.parentId = row.id;
                }
                param.keyword = $("#qryKeyword").textbox("getValue");
            },
            onClickRow : function(row) {    // 选中和取消选中
                if (Labels.selectedId && Labels.selectedId == row.id) {
                    $("#tg").treegrid('unselect', row.id);
                    Labels.selectedId = null;
                } else {
                    Labels.selectedId = row.id;
                }
            }
        });

    },
    // 初始化产品标签组件
    initProductLabelComp:function(){
        // 产品类型
        $("#qryTargetType").combobox({
            valueField: 'value',
            textField: 'text',
            data: [
                { value: 'SCENIC', text: '景点' },
                { value: 'DELICACY', text: '美食' },
                { value: 'PLAN', text: '定制行程' },
                { value: 'RECOMMEND_PLAN', text: '游记' },
                { value: 'TICKET', text: '门票' },
                { value: 'HOTEL', text: '酒店' },
                { value: 'CRUISESHIP', text: '邮轮' },
                { value: 'SAILBOAT', text: '海上休闲'}
            ],
            onSelect:function(record){
                var type = record.value;
                if (type) {
                    $('#productLabelGrid').datagrid({url:'/labels/labels/listProductLabel.jhtml'});
                }
            }
        });
        // 城市查询条件
        $('#qryCity').textbox({
            onClickButton:function() {
                $('#qryCity').textbox('setValue', '');
                $('#qryCity').attr('data-country', '');
                $('#qryCity').attr('data-province', '');
                $('#qryCity').attr('data-city', '');
            }
        });
        $("#qryCity").next('span').children('input').focus(function() {
            //$(this).blur(); // 移开焦点，否则事件会一直触发
            var country = $('#qryCity').attr('data-country');
            var province = $('#qryCity').attr('data-province');
            var city = $('#qryCity').attr('data-city');
            AreaSelectDg.openForQry(country, province, city, function(countryId, provinceId, cityId, fullName) {
                $('#qryCity').textbox('setValue', fullName);
                if (countryId) {
                    $('#qryCity').attr('data-country', countryId);
                } else {
                    $('#qryCity').attr('data-country', '');
                }
                if (provinceId) {
                    $('#qryCity').attr('data-province', provinceId);
                } else {
                    $('#qryCity').attr('data-province', '');
                }
                if (cityId) {
                    $('#qryCity').attr('data-city', cityId);
                } else {
                    $('#qryCity').attr('data-city', '');
                }
                Labels.searchProduct();
            });
        });
        // 产品标签表格
        $('#productLabelGrid').datagrid({
            fit:true,
            data: [],
            border:true,
            striped:true,//斑马线
            pagination:false,
            singleSelect:true,
            columns:[[
                {field:'targetId',title:'产品标识',width:80},
                {field:'targetName',title:'产品名称',width:230,
                    formatter: function (value, rowData, rowIndex) {
                        var type = $("#qryTargetType").combobox("getValue");
                        if (!type) {
                            return value;
                        }
                        //var url = FG_DOMAIN + "/line_detail_" + productId + ".html";
                        var uri = null;
                        if (type==="SCENIC") {
                            uri = "/scenic_detail_" + rowData.id + ".html";
                        } else if(type==="CITY"){
                            uri = "/city_" + rowData.id + ".html";
                        } else if(type==="RECOMMEND_PLAN"){
                            uri = "/guide_detail_" + rowData.id + ".html";
                        } else if(type==="DELICACY"){
                            uri = "/food_detail_" + rowData.id + ".html";
                        } else if(type==="PLAN"){
                            uri = "/plan_detail_" + rowData.id + ".html";
                        } else if(type==="TICKET"){
                            uri = null;
                        } else if(type==="HOTEL"){
                            uri = "/hotel_detail_" + rowData.id + ".html";
                        } else if(type==="LINE"){
                            uri = "/line_detail_" + rowData.id + ".html";
                        } else {
                            uri = null;
                        }
                        if (uri) {
                            return "<a style='color:blue;' href='" + FG_DOMAIN + uri + "' target='_blank'>" + value + "</a>";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'targetTime',
                    title: '更新时间',
                    width: 140,
                    align: 'center',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != null && value != "") {
                            return value;
                        } else {
                            return "-";
                        }
                    }
                },
                {field:'itemSort',title:'序号',width:60,align:'center',
                    formatter: function(value, row, index){
                        return index + 1;
                    }
                },
                {field:'opt',title:'操作', width:155, align:'center',
                    formatter: function(value, row, index){
                        var allData = $('#productLabelGrid').datagrid('getData');
                        var btnUp = '';
                        if (index > 0) {
                            btnUp = '<a href="javascript:void(0)" style="color:blue;" onclick="Labels.labelUp('+row.id+','+index+')">上移</a>' + '&nbsp;';
                        } else {
                            btnUp = '<span style="color:gray;">上移</span>' + '&nbsp;';
                        }
                        var btnDown = '';
                        if (index < allData.total-1) {
                            btnDown = '<a href="javascript:void(0)" style="color:blue;" onclick="Labels.labelDown('+row.id+','+index+')">下移</a>' + '&nbsp;';
                        } else {
                            btnDown = '<span style="color:gray;">下移</a>' + '&nbsp;';
                        }
                        var btnUnbindLabel = '<a href="javascript:void(0)" style="color:blue;" onclick="Labels.unbindTable('+row.labelId+',\''+row.targetType+'\','+row.targetId+')">取消关联</a>';
                        return btnUp + btnDown + btnUnbindLabel;
                    }
                }
            ]],
            toolbar: '#productLabelTb',
            onBeforeLoad : function(data) {
                data.targetType = $("#qryTargetType").combobox("getValue");
                data.labelId = Labels.queryLabelId;
                //data.status = 'USE';
                // 城市条件（考虑可能不存在第二或者第三级），取值顺序城市>省区>国家
                var country = $('#qryCity').attr('data-country');
                var province = $('#qryCity').attr('data-province');
                var city = $('#qryCity').attr('data-city');
                if (city) {
                    data.city = city;
                } else if (province) {
                    data.city = province;
                } else if (country) {
                    data.city = country;
                }
            }
        });
    },
    // 打开产品标签窗口
    openProductLabelDg : function(labelId) {
        Labels.queryLabelId = labelId;
        $("#qryTargetType").combobox("select", 'SCENIC');
        $("#productLabelDg").dialog("open");
        //Labels.searchProduct();
    },
    // 产品标签查询
    searchProduct: function() {
        $('#productLabelGrid').datagrid('load');
    },
    // 产品标签窗口关闭
    closeProductLabelDg : function() {
        $("#productLabelDg").dialog("close");
    },
    // 保存
    saveNodes:function() {
        $('#nodeForm').form('submit', {
            url:'/labels/labels/saveLabel.jhtml',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                if(isValid){
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
                    $('#editNodeDg').dialog('close');
                    if (obj.parentId) {
                        if (Labels.opt == 'ADD') {  // 如果是添加且父节点原来是叶子节点，则更新节点叶子状态
                            var parentNode = $("#tg").treegrid('find', obj.parentId);
                            parentNode.state = "closed";
                            $("#tg").treegrid('update', parentNode);
                            $("#tg").treegrid('expand', obj.parentId);
                        } else {
                            $("#tg").treegrid('reload', obj.parentId);
                        }
                    } else {
                        $('#tg').treegrid('reload');
                    }
                } else {
                    show_msg("保存失败");
                }
            }
        });
    },
    // 清除表单数据
    clearDialog:function(){
        $('#nodeForm').form('clear');
        $("#parentId").val("");
    },
    // 添加
    addNodes : function() {
        Labels.opt = 'ADD';
        var node = $('#tg').treegrid('getSelected');
        var data = {};
        data.status = 'USE';
        if (node) { // 已选中节点
            data.parentId = node.id;
            data.parentName = node.name;
        }
        Labels.clearDialog();
        $('#editNodeDg').dialog('setTitle', '增加标签');
        $('#nodeForm').form('load', data);
        $('#editNodeDg').dialog('open');
    },
    // 编辑
    editNodes : function(id, rowIndex) {
        Labels.opt = 'EDIT';
        //var node = $('#tg').treegrid('getSelected');
        //if (!node) {
        //    show_msg("请选择标签！");
        //    return;
        //}
        var node = $('#tg').treegrid('find', id);
        var parentNode = $('#tg').treegrid('getParent', node.id);
        if (parentNode) {
            //node.parentId = parentNode.id;
            node.parentName = parentNode.name;
        }
        Labels.clearDialog();
        $('#editNodeDg').dialog('setTitle', '编辑标签');
        $('#nodeForm').form('load', node);
        $('#editNodeDg').dialog('open');
    },
    // 删除
    delNodes:function(){
        var node = $('#tg').treegrid('getSelected');
        if (!node) {
            show_msg("请选择标签！");
            return;
        }
        $.messager.confirm('温馨提示', '您确认删除选中标签及所有子标签？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/labels/labels/delLabels.jhtml",
                    {id : node.id},
                    function(result) {
                        $.messager.progress("close");
                        if(result.success) {
                            $("#tg").treegrid('remove', node.id);
                            if (result.parentId) {
                                var parentNode = $("#tg").treegrid('find', result.parentId);
                                parentNode.state = "";
                                $("#tg").treegrid('update', parentNode);
                            }
                        }else{
                            show_msg("删除失败");
                        }
                    }
                );
            }
        });
    },
    // 取消关联（表格）
    unbindTable : function(labelId, targetType, targetId) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/labels/productLabels/deleteLabelItem.jhtml",
            {labelIds : labelId, type : targetType, targetId : targetId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    Labels.searchProduct();
                } else {
                    show_msg("操作失败");
                }
            }
        );
    },
    // 标签上移
    labelUp : function(id, index) {
        var allData = $('#productLabelGrid').datagrid('getData');
        var rows = allData.rows;
        var total = allData.total;
        if (index > 0) {    // 上移不能为第一条记录
            var toDownRow = rows[index-1];
            var toUpRow = rows[index];
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            $.post("/labels/productLabels/swapLabelOrder.jhtml",
                {toUpId : toUpRow.id, toDownId : toDownRow.id},
                function(result) {
                    $.messager.progress("close");
                    if (result.success == true) {   // 页面刷新
                        rows[index-1] = toUpRow;
                        rows[index] = toDownRow;
                        $('#productLabelGrid').datagrid('refreshRow', index-1);
                        $('#productLabelGrid').datagrid('refreshRow', index);
                    } else {
                        show_msg("操作失败");
                    }
                }
            );
        }
    },
    // 标签下移
    labelDown : function(id, index) {
        var allData = $('#productLabelGrid').datagrid('getData');
        var rows = allData.rows;
        var total = allData.total;
        if (index < allData.total-1) {  // 上移不能为最后一条记录
            var toDownRow = rows[index];
            var toUpRow = rows[index+1];
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            $.post("/labels/productLabels/swapLabelOrder.jhtml",
                {toUpId : toUpRow.id, toDownId : toDownRow.id},
                function(result) {
                    $.messager.progress("close");
                    if (result.success == true) {   // 页面刷新
                        rows[index] = toUpRow;
                        rows[index+1] = toDownRow;
                        $('#productLabelGrid').datagrid('refreshRow', index);
                        $('#productLabelGrid').datagrid('refreshRow', index+1);
                    } else {
                        show_msg("操作失败");
                    }
                }
            );
        }
    },
    // 打开移动标签窗口
    openMoveNodeDg : function() {
        var nodes = $('#tg').treegrid('getSelections');
        if (!nodes || nodes.length <= 0) {
            show_msg("请选择标签！");
            return;
        }
        $('#moveNodeDg').dialog('open');
    },
    // 确认移动
    moveNodes : function() {
        var toId = $("#moveLabel").combobox("getValue");
        if (!toId) {
            toId = null;
        }
        var nodes = $('#tg').treegrid('getSelections');
        var moveIdArray = [];
        for (var i = 0; i < nodes.length; i++) {
            moveIdArray.push(nodes[i].id);
        }
        var moveIds = moveIdArray.join(',');
        // 提交表单
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/labels/labels/moveLabel.jhtml",
            {toId : toId, moveIds : moveIds},
            function(result) {
                $.messager.progress("close");
                if (result.success) {   // 页面刷新
                    $('#moveNodeDg').dialog('close');
                    Labels.doSearch();
                } else {
                    show_msg(result.errorMsg);
                }
            }
        );
    }
};

$(function(){
	Labels.init();
});