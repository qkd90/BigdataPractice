/**
 * Created by dy on 2016/2/16.
 */
var ProductActivity = {
    init : function() {
        ProductActivity.initDg_activity();
    },
    openAddActivity : function() {
        $.post("/weixinh5/productActivities/saveTempActivity.jhtml", function(data){

            if (data.success) {
                var url = "/weixinh5/productActivities/addActivity.jhtml?activityId="+data.aId;
                var ifr = $("#editPanel").children()[0];
                $(ifr).attr("src", url);
                $("#editPanel").dialog({
                    title: '新增活动',
                    width: 400,
                    height: 300,
                    closed: false,
                    cache: false,
                    modal: true
                });
                $("#editPanel").dialog("open");
            }


        });

    },
    openEditActivity : function(id) {

        var url = "/weixinh5/productActivities/editActivity.jhtml?activityId=" + id;
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '新增活动',
            width: 400,
            height: 300,
            closed: false,
            cache: false,
            modal: true
        });
        $("#editPanel").dialog("open");

    },
    delActivity : function(id) {

        var data = {
            activityId: id
        };

        var url = "/weixinh5/productActivities/cancelSave.jhtml";
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    show_msg("删除成功！");
                    $('#dg_activity').datagrid("reload");
                }
            }
        );


    },



    upOrDownAllActivity:function(ids, statusStr) {

        var data = {
            activityIds: ids,
            statusStr:statusStr
        };
        var url = "/weixinh5/productActivities/upOrDownAllActivity.jhtml";
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    if (statusStr == "UP") {
                        show_msg("上架成功！");
                        $('#dg_activity').datagrid("reload");
                    } else {
                        show_msg("下架成功！");
                        $('#dg_activity').datagrid("reload");
                    }

                }
            }
        );


    },

    upOrDownAllActivityBtn: function(statusStr) {

        var rows = $('#dg_activity').datagrid("getSelections");

        if (rows.length > 0 ) {

            var ids = "";
            if (statusStr == "UP") {
                $.each(rows, function(i, perValue){

                    if (perValue.status == "DOWN") {
                        ids = ids + perValue.id + ",";
                    }

                });
            } else {
                $.each(rows, function(i, perValue){

                    if (perValue.status == "UP") {
                        ids = ids + perValue.id + ",";
                    }

                });

            }

            if (ids.length > 0) {
                ids = ids.substr(0, ids.length - 1);
            }
            ProductActivity.upOrDownAllActivity(ids, statusStr);




        } else {
            show_msg("请选择数据！")
        }

    },

    upActivity: function(id) {
        var data = {
            activityId: id,
            typeStr: "UP"
        };

        var url = "/weixinh5/productActivities/upOrDownActivity.jhtml";
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    show_msg("上架成功！");
                    $('#dg_activity').datagrid("reload");
                }
            }
        );
    },
    downActivity: function(id) {
        var data = {
            activityId: id,
            typeStr: "DOWN"
        };
        var url = "/weixinh5/productActivities/upOrDownActivity.jhtml";
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    show_msg("下架成功！");
                    $('#dg_activity').datagrid("reload");
                }
            }
        );
    },

    deletAllActivityBtn: function() {

        var rows = $('#dg_activity').datagrid("getSelections");

        if (rows.length > 0 ) {
            var ids = "";
            $.each(rows, function(i, perValue){

                if (perValue.status == "UP") {
                    show_msg("所选数据中，部分数据正在使用中，请下架后重新删除！");
                    return false;
                } else {
                    ids = ids + perValue.id + ",";
                }

            });
            ids = ids.substr(0, ids.length-1);
            ProductActivity.delAllActivity(ids);

        } else {
            show_msg("请选择需要删除的数据！")
        }

    },

    delAllActivity: function(ids) {
        var data = {
            activityIds: ids
        };
        var url = "/weixinh5/productActivities/delAllActivity.jhtml";
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    show_msg("删除成功！");
                    $('#dg_activity').datagrid("reload");
                }
            }
        );
    },

    doSearch: function() {
        var data = {
            typeStr: $("#com_type").combobox("getValue"),
            statusStr: $("#com_status").combobox("getValue"),
            nameStr: $("#ipt_name").textbox("getValue")
        };
        $('#dg_activity').datagrid("reload",data);
    },

    clearForm: function() {

        $("#searchForm_activity").form("reset");

        var data = {
            typeStr: $("#com_type").combobox("getValue"),
            statusStr: $("#com_status").combobox("getValue"),
            nameStr: $("#ipt_name").textbox("getValue")
        };
        $('#dg_activity').datagrid("reload",data);
    },

    initDg_activity : function() {
        $('#dg_activity').datagrid({
            url:'/weixinh5/productActivities/activityList.jhtml',
            pagination: true,
            pageList: [10, 20, 30],
            rownumbers: true,
            fitColumns: true,
            fit: true,
            columns:[[
                {field:'ck',checkbox:true, width:15,sortable: true},
                {field:'name',title:"活动名称", width:200,sortable: true},
                {field:'type',title:'活动类型',width:150,sortable: true,
                    formatter: function(value, rowData, index) {

                        if (value === "coupon") {
                            return "优惠券";
                        } else {
                            return "限时抢购";
                        }
                    }
                },
                {field:'validate',title:'有效时间',width:200,sortable: true,
                    formatter: function(value, rowData, index) {

                        return rowData.startTime +"---"+ rowData.endTime;

                    }
                },
                {field:'status',title:'状态',width:100,sortable: true,
                    formatter: function(value, rowData, index) {
                        if (value === "DOWN") {
                            return "已下架";
                        }

                        if (value === "UP") {
                            return "正在进行中";
                        }
                    }
                },
                {field:'createTime',title:'创建时间',width:100,sortable: true},
                {field:'opt',title:'操作',width:200,sortable: true,
                    formatter: function(value, rowData, index) {

                        var eidtBtn = '<a style="margin: 5px; color:blue;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="ProductActivity.openEditActivity('+rowData.id+')">编辑</a>';
                        var delBtn = '<a style="margin: 5px; color:blue;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="ProductActivity.delActivity('+rowData.id+')">删除</a>';
                        var upBtn = '<a style="margin: 5px; color:blue;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="ProductActivity.upActivity('+rowData.id+')">上架</a>';
                        var downBtn = '<a style="margin: 5px; color:blue;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="ProductActivity.downActivity('+rowData.id+')">下架</a>';
                        var space = '&nbsp;&nbsp;&nbsp;&nbsp;'

                        if (rowData.status === "UP") {
                            return eidtBtn+space+delBtn+space+downBtn;
                        } else {
                            return eidtBtn+space+delBtn+space+upBtn;
                        }

                    }
                },
            ]],
            toolbar: '#tool_activity'
        });
    }
}

$(function() {
    ProductActivity.init();
})