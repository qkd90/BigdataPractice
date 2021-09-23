/**
 * Created by vacuity on 15/10/26.
 */


var Ads={

    AdsContanst: 'ads/banner/',
    init:function(){
        AdsUtil.initComp();
        Ads.initDgList();
    },

    // 表格查询
    doSearch:function(){
        var searchForm = {};
        searchForm['location'] = $("#ads_location").combobox("getValue");
        searchForm['st'] = $("input[name='sTime']").val();
        searchForm['et'] = $("input[name='eTime']").val();
        searchForm['openType'] = $("#ads_opentype").combobox("getValue");
        searchForm['adsStatus'] = $("#ads_status").combobox("getValue");
        $("#dg").datagrid('load', searchForm);
    },

    doClear: function() {
        $("#searchform").form("reset");
        $("#dg").datagrid('load', {});
    },
    // 删除
    doDel: function(tbId) {
        var rows = $('#'+tbId).datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        this.commitDel(ids);
        //Ads.doSearch();
    },
    commitDel: function(ids) {
        $.post("/ad/ad/delByIds.jhtml",
            {ids: ids},
            function (result) {
                $.messager.progress("close");
                if (result.success == true) {
                    show_msg("删除成功");
                    $("#dg").datagrid('reload');
                } else {
                    show_msg("删除失败");
                    $("#dg").datagrid('reload');
                }
            }
        );
    },
    // 上架
    doUp: function(tbId) {
        var rows = $('#'+tbId).datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.post("/ad/ad/doUpByIds.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    $("#dg").datagrid('reload');
                }else{
                    show_msg("处理失败");
                    $("#dg").datagrid('reload');
                }
            }
        );
    },
    // 下架
    doDown: function(tbId) {
        var rows = $('#'+tbId).datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.post("/ad/ad/doDownByIds.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    $("#dg").datagrid('reload');
                }else{
                    show_msg("处理失败");
                    $("#dg").datagrid('reload');
                }
            }
        );
    },
    // 修改
    doEdit:function(productId){
        var url = "/line/line/editWizard.jhtml?productId="+productId;
        window.location.href = url;
    },

    // 初始化表格数据
    initDgList:function() {
        $("#dg").datagrid({
            fit:true,
            //title:'线路列表',
            //height:400,
            url:'/ad/ad/search.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'adStatus', title: '状态', width: 110, sortable: true, align: 'center',
                    formatter : function(value, row, rowIndex){
                        if(row.adStatus!=null && row.adStatus=="UP"){
                            return "<span style='color:green'>上架</span>";
                        }else {
                            return "<span style='color:red'>下架</span>";
                        }
                    }
                },
                {field:'end',title:'操作',width:120,sortable: true, align: 'center',
                    formatter: function (value, row, index) {
                        var delClick = " onClick='Ads.commitDel("+ row.id +")'"
                        var editClick = " onClick='Ads.editForm(" + row.id + ")'";
                        var editbtn = "<a style='color: blue;text-decoration: underline;' href='#'" + editClick + ">修改</a>";
                        var deletebtn = "<a style='color: blue;text-decoration: underline;' href='#'" + delClick + ">删除</a></div>";
                        var space = "&nbsp;";
                        var aaa = editbtn + space + deletebtn;
                        return aaa;
                    }
                },
                { field: 'sysResourceMap.name', title: '广告位置', width: 200, sortable: true, align: 'center'},
                { field: 'adTitle', title: '广告标题', width: 170, sortable: false, align: 'center'},
                { field: 'sort', title: '排序', width: 100, sortable: false, align: 'center'},
                { field: 'imgPath', title: '广告图片', width: 290, sortable: false, align: 'center',
                    formatter : function(value, row, rowIndex){
                        if (value != null && value != undefined && value != '') {
                            return '<img src="' + QINIU_BUCKET_URL + value + '"width="240" height="80"/>';
                        }
                        return '<span style="color: red; font-weight: bold">暂无图片</span>';
                    }
                },
                { field: 'startTime', title: '开始日期', width: 190, sortable: true, align: 'center'},
                { field: 'endTime', title: '结束日期', width: 190, sortable: true, align: 'center'},
                { field: 'openType', title: '打开类型', width: 110, sortable: true, align: 'center',
                    formatter : function(value, row, rowIndex){
                        if(row.openType!=null && row.openType=="NEWED"){
                            return "<span style='color:blue'>Newed</span>";
                        }else if(row.openType!=null && row.openType=="SELF"){
                            return "<span style='color:blue'>Self</span>";
                        }else{
                            return "<span style='color:blue'>None</span>";
                        }
                    }
                },
                { field: 'addTime', title: '添加日期', width: 190, sortable: true, align:'center'}
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                data.adsLocation = $("#ads_location").combobox("getValue");
                data.adsStime = $("input[name='sTime']").val();
                data.adsEtime = $("input[name='eTime']").val();
                data.adsOpenType = $("#ads_opentype").combobox("getValue");
                data.adsStatus = $("#ads_status").combobox("getValue");;
            }
        });
    },



    addForm: function() {
        var ifr = $("#editPanel").children()[0];
        var url = "/ad/ad/editAdDetail.jhtml";
        $(ifr).attr("src", url);
        //$(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '广告新增',
            width: 470,
            height: 520,
            closed: false,
            cache: false,
            closable:true,
            modal: true
        });
        $("#editPanel").dialog("open");
    },

    editForm:function(id){
        var ifr = $("#editPanel").children()[0];
        var url = "/ad/ad/editAdDetail.jhtml?id="+id;
        $(ifr).attr("src", url);
        //$(ifr).css("height", "750px");
        $("#editPanel").dialog({
            title: '广告编辑',
            width: 470,
            height: 520,
            closed: false,
            cache: false,
            closable:true,
            modal: true
        });
        $("#editPanel").dialog("open");
        /*var editUrl="/ad/ad/getAdsMap.jhtml?id="+id;
        $("#editform").form('load',editUrl);

        $("#edit_panel").dialog("open");
        $("#editform").form({
            onLoadSuccess: function(data) {
                if (data.imgPath) {
                    $('#edit_imgView img').attr('src', "http://7u2inn.com2.z0.glb.qiniucdn.com/" + data.imgPath);
                    $('#edit_imgView').show();
                }
            }
        });*/
    }

};

$(function(){
    Ads.init();
});

