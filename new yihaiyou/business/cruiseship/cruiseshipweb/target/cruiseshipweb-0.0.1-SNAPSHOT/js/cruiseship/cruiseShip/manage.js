var CruiseShip = {
	init:function(){
		CruiseShip.initComp();
		CruiseShip.initStatus();
        CruiseShip.initTabs()
        $("#tabs").tabs("select", 0)
        CruiseShip.initLineInfo_dg();
	},
    //选项卡数据加载
    initTabs: function() {
        $('#tabs').tabs({
            onSelect:function(title, index){
                if (index == 0) {
                    CruiseShip.initLineInfo_dg();
                } else if (index == 1) {
                    CruiseShip.initProject_dg();
                }
            }
        });
    },
    // 初始化控件
    initComp : function() {
        // 查询控件
        $('#qry_status').combobox({
            data: CruiseShipConstants.qry_status,
            mode: 'local',
            valueField: 'id',
            panelHeight: 'auto',
            textField: 'text'
        });



        // 查询控件
        $('#show_status').combobox({
            data: CruiseShipConstants.show_status,
            mode: 'local',
            valueField: 'id',
            panelHeight: 'auto',
            textField: 'text'
        });
        //// 查询控件
        //$('#projectType').combobox({
        //    data:CruiseShipConstants.projectType,
        //    mode: 'local',
        //    valueField:'id',
        //    panelHeight: 'auto',
        //    textField:'text'
        //});
        // 项目信息表格




        // 项目类型表单控件
        $('#projectType').combobox({
            data:CruiseShipConstants.getConstants('projectType', true),
            valueField:'id',
            textField:'text',
            panelHeight: 'auto'
        });

        /*//展示状态控件
        $('#showStatus').combobox({
            data:CruiseShipConstants.getConstants('booleanFlag', true),
            valueField:'id',
            textField:'text',
            panelHeight: 'auto'
        });*/

        // 项目富文本框设置
        $.each($("#projectForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_project'
            });
        });

    },
    // 初始化表格数据
    initLineInfo_dg : function (){$("#dg").datagrid({
        fit: true,
        //title:'线路列表',
        //height:400,
        url: '/cruiseship/cruiseShip/search.jhtml',
        pagination: true,
        pageList: [20, 30, 50],
        rownumbers: true,
        fitColumns: true,
        singleSelect: false,
        striped: true,//斑马线
        ctrlSelect: true,// 组合键选取多条数据：ctrl+鼠标左键
        columns: [[
            //{ field: 'ck', checkbox: true },
            {field: 'productNo', title: '邮轮编号', width: 100},
            {field: 'name', title: '邮轮名称', width: 360},
            {
                field: 'status',
                title: '状态',
                width: 80,
                align: 'center',
                codeType: 'status',
                formatter: CruiseShipUtil.codeFmt
            },
            {field: 'startCity', title: '出发城市', width: 100},
            {field: 'arriveCity', title: '到达城市', width: 100},
            {
                field: 'satisfaction', title: '满意度', width: 80,
                formatter: function (value, rowData, rowIndex) {
                    if (value) {
                        return value + '%';
                    }
                    return '';
                }
            },
            {field: 'commentNum', title: '评论数', width: 80},
            {field: 'collectionNum', title: '收藏数', width: 80},
            {field: 'updateTime', title: '最后更新', width: 150},
            {
                field: 'opt', title: '操作', width: 100, align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='CruiseShip.doEdit(" + rowData.id + ")'>编辑</a>";
                    return btn;
                }
            }
        ]],
        toolbar: '#tb',
        onBeforeLoad: function (data) {   // 查询参数
            data.status = $("#qry_status").combobox("getValue");
            data.keyword = $("#qry_keyword").textbox("getValue");
        }
    });},
    initProject_dg : function () {
        $('#projectGrid').datagrid({
            url: '/cruiseship/cruiseShipProjectClassify/list.jhtml',
            pagination: false,
            singleSelect: true,
            striped: true,//斑马线
            ctrlSelect: true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns: [[
                {
                    title: '分类编号',
                    field: 'id',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '分类名称',
                    field: 'classifyName',
                    width: 70,
                    align: 'center'
                },
                {
                    field: 'projectType', title: '类型',
                    width: 80, align: 'center',
                    codeType: 'projectType', formatter: CruiseShip.typeFormat
                },
                /*{
                 //title: '展示状态',
                 field: 'showStatus',
                 //width: 70,
                 //align: 'center',
                 formatter: CruiseShip.showStatusFormat
                 },*/


                {
                    field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter: function (value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='CruiseShip.openEditProjectDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],

            toolbar: '#projectGridtb',
            onBeforeLoad: function (data) {   // 查询参数
                data.parentId = $("#show_status").combobox("getValue");
                data.keyword = $("#show_keyword").textbox("getValue");
            },
            ////项目分类下拉
            //toolbar: '#projectGridtb',
            //onBeforeLoad : function(data) {   // 查询参数
            //    data.status = $("#projectType").combobox("getValue");
            //    data.keyword = $("#projectType").textbox("getValue");
            //}
        });
    },
    // 项目图片展示处理
    projectImgFormat: function(value, rowData, rowIndex) {
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
    /*//展示状态处理
    showStatusFormat:function(value,rowData,rowIndex) {
        if (rowData.showStatus){
            var str = "是";
        }
        else str = "否";
        return str;
    },*/
    //类型名称展示处理
    typeFormat:function(value,rowData,rowIndex){
        if(rowData.parentId){
            var str = rowData.cruiseShipProjectClassify.classifyName;
        }
        return str;
    },



    // 监听器
    initListener: function() {

    },
    // 项目图片上传控件
    initProjectImgUploader: function() {
        $('#project_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#project_imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');

                $('#fileBox_' + data.id).find('.diyCancel').click(function () {
                    $("#input_" + data.id).remove();
                });
                $("#project_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            multiple: false,
            buttonText: '上传图片',
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit: 1,
            formData: {
                section: CruiseShipConstants.projectImg
            }
        });
    },


    // 项目信息查询
    searchProject: function() {
        $('#projectGrid').datagrid('load');
    },


    // 打开项目信息窗口
    openEditProjectDg: function(projectId, rowIndex) {
        $('#editProjectDg').dialog({
            title:'项目信息',
            modal:true,
            onClose:function(){
                $("#projectForm").form('clear');
                // 清空图片上传信息
                $('#projectForm').find('.parentFileBox').remove();
                $('#projectForm').find('#project_imageContent').empty();
            },
            onBeforeOpen:function(){
                // 初始化图片上传控件
                CruiseShip.initProjectImgUploader();
                var projectRow = {};
                var project = {};
                if (projectId) {
                    var projectRows = $('#projectGrid').datagrid('getRows');
                    projectRow = projectRows[rowIndex];
                    project['cruiseShipProjectClassify.id'] = projectRow.id;
                    project['cruiseShipProjectClassify.classifyName'] = projectRow.classifyName;
                    project['cruiseShipProjectClassify.showStatus'] = projectRow.showStatus;
                    project['cruiseShipProjectClassify.coverImage'] = projectRow.coverImage;
                    project['cruiseShipProjectClassify.cruiseShipProjectClassify.id'] = projectRow.parentId;
                } else {
                    //
                    project['cruiseShipProjectClassify.name'] = null;
                }
                //project['cruiseShipProjectClassify.id'] = $('#projectId').val();
                project['projectChildFolder'] = CruiseShipConstants.projectImg;
                $('#projectForm').form({
                    onLoadSuccess: function(data) {
                        if(data['cruiseShipProjectClassify.coverImage'] && data['cruiseShipProjectClassify.coverImage'].length > 0) {
                            showImageWithoutCancel($('#project_imagePanel'), BizConstants.QINIU_DOMAIN +  data['cruiseShipProjectClassify.coverImage'], data['cruiseShipProjectClassify.id']);
                        }
                    }
                });
                $('#projectForm').form('load', project);
            }
        });

        $('#editProjectDg').dialog('open');
    },


    // 关闭项目编辑窗口
    closeEditProjectDg: function() {
        $('#editProjectDg').dialog('close');
    },

    // 清楚项目表单数据
    clearProjectForm: function() {
        $('projectForm').form('clear');
        // 清楚项目图片信息
        $('#projectForm').find('.parentFileBox').remove();
        $('#projectForm').find('#imageContent').empty();
    },

    // 项目信息保存
    saveProject: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $('#projectForm').form('submit', {
            url: '/cruiseship/cruiseShipProjectClassify/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '请完善项目信息!', 'error');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress("close");
                var result = eval('(' + data + ')');
                if (result.success) {
                    CruiseShip.closeEditProjectDg();
                    CruiseShip.searchProject();
                } else {
                    showMsgPlus('提示', '保存失败', 2000);
                }
            }
        });
    },


    // 删除项目信息
    doDelProject: function() {
        var row = $('#projectGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的项目信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipProjectClassify/delProject.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            projectId: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            CruiseShip.searchProject();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            CruiseShip.searchProject();
                        }
                    })
                }
            }
        );
    },


	// 初始状态
	initStatus : function() {
		
	},
    // 打开编辑窗口
    openEditPanel: function(url) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    },
    // 关闭编辑窗口
    closeEditPanel: function(isRefresh) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", '');
        $("#editPanel").dialog("close");
        CruiseShip.doSearch();
    },
	// 表格查询
	doSearch:function(){
		$('#dg').datagrid('load', {});
	},

	// 查看
	doView:function(id){
        var url = FG_DOMAIN + "/line_detail_" + id + ".html";
        window.open(url, '_blank');
	},
    // 新增
    doAdd : function() {
        var url = "/cruiseship/cruiseShip/editWizard.jhtml";
        CruiseShip.openEditPanel(url);
    },
    // 修改
    doEdit:function(productId) {
        var url = "/cruiseship/cruiseShip/editWizard.jhtml?productId="+productId;
        CruiseShip.openEditPanel(url);
    },
    // 删除
    doDel : function() {
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
            if (r) {
                CruiseShip.doBatch('DOWN', 'DEL');
            }
        });
    },
    // 批量提交审核
    doBatchShow : function() {
        CruiseShip.doBatch('CHECKING');
    },
    // 批量下架
    doBatchHide : function() {
        CruiseShip.doBatch('DOWN');
    },
    // 批量操作
    doBatch : function(statusToUpdate) {
        var rows = $('#dg').datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            if (statusToUpdate == "CHECKING") {
                if (row.status == "FAIL" || row.status == "DOWN") {
                    idsArray.push(row.id);
                }
            } else {
                if (row.status != statusToUpdate) {		// 状态：UP("上架"), DOWN("下架"), DEL("删除");
                    idsArray.push(row.id);
                }
            }
        }

        if (idsArray.length > 0) {
            var ids = idsArray.join(',');
            CruiseShip.doUpdateStatus(ids, statusToUpdate);
        } else {
            if (statusToUpdate == "CHECKING") {
                show_msg("请选择已下架产品或审核未通过的产品");
            } else if (statusToUpdate == "DOWN") {
                show_msg("请选择已上架产品或审核未通过的产品");
            }

        }
    },
    // 更新状态
    doUpdateStatus: function(ids, lineStatusToUpdate, reason) {
        if (!reason) {  // 未传入该参数，默认值
            reason = '';
        }
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.ajax({
            url: '/cruiseship/cruiseShip/doUpdateStatus.jhtml',
            data: {
                ids: ids,
                status: lineStatusToUpdate
            },
            success: function (result) {
                $.messager.progress("close");
                if (result.success) {
                    showMsgPlus("提示", result.msg, 1800);
                    CruiseShip.doSearch();
                } else {
                    CruiseShip.doSearch();
                    showMsgPlus("提示", result.msg, 1800);
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        });
    },
    // 不通过-确认
    doUnThroughDg : function() {
        var lineId = $("#lineId").val();
        var reason = $("#reason").val();
        CruiseShip.doUpdateStatus(lineId, 'hide', reason);
        $('#reasonDg').dialog('close');
    },
    // 通过
    doThrough : function(id) {
        CruiseShip.doUpdateStatus(id, 'show');
    },
    // 不通过
    doUnThrough : function(id) {
        $("#lineId").val(id);
        $('#reasonDg').dialog('open');
    },
    // 上架
    doShow : function(id) {
        CruiseShip.doUpdateStatus(id, 'show');
    },
    // 下架
    doHide : function(id) {
        CruiseShip.doUpdateStatus(id, 'hide');
    }
};


//返回本页面数据
function getIfrData(){
    var data = {};
    return data;
}
$(function(){
	CruiseShip.init();
});

