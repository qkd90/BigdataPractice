/**
 * Created by dy on 2016/6/2.
 */

var ChannelManage = {
    editIndex : undefined,
    init : function() {
        ChannelManage.initTicketChanel();
        //ChannelManage.initCom();
    },
    //
    //initCom: function() {
    //    $("#ipq_signTimeStart").datebox({
    //        onSelect: function(date) {
    //            var signTimeStart = $("#ipq_signTimeStart").datebox("getValue");
    //            var signTimeEnd = $("#ipq_signTimeEnd").datebox("getValue");
    //            if (signTimeEnd) {
    //                var result = ContractUtil.dateStringCompare(signTimeEnd, signTimeStart);
    //                if (result <= 0) {
    //                    $("#ipq_signTimeStart").datebox("setValue", "");
    //                    show_msg("签约时间始不能大于签约时间末")
    //                }
    //            }
    //        }
    //    });
    //    $("#ipq_signTimeEnd").datebox({
    //        onSelect: function(date) {
    //            var signTimeStart = $("#ipq_signTimeStart").datebox("getValue");
    //            var signTimeEnd = $("#ipq_signTimeEnd").datebox("getValue");
    //            if (signTimeStart) {
    //                var result = ContractUtil.dateStringCompare(signTimeEnd, signTimeStart);
    //                if (result <= 0) {
    //                    $("#ipq_signTimeEnd").datebox("setValue", "");
    //                    show_msg("签约时间末不能小于签约时间始")
    //                }
    //            }
    //        }
    //    });
    //    $("#ipq_expireTimeStart").datebox({
    //        onSelect: function(date) {
    //            var expireTimeStart = $("#ipq_expireTimeStart").datebox("getValue");
    //            var expireTimeEnd = $("#ipq_expireTimeEnd").datebox("getValue");
    //            if (expireTimeEnd) {
    //                var result = ContractUtil.dateStringCompare(expireTimeEnd, expireTimeStart);
    //                if (result <= 0) {
    //                    $("#ipq_expireTimeStart").datebox("setValue", "");
    //                    show_msg("到期时间始不能大于到期时间末")
    //                }
    //            }
    //        }
    //    });
    //    $("#ipq_expireTimeEnd").datebox({
    //        onSelect: function(date) {
    //            var expireTimeStart = $("#ipq_expireTimeStart").datebox("getValue");
    //            var expireTimeEnd = $("#ipq_expireTimeEnd").datebox("getValue");
    //            if (expireTimeStart) {
    //                var result = ContractUtil.dateStringCompare(expireTimeEnd, expireTimeStart);
    //                if (result <= 0) {
    //                    $("#ipq_expireTimeEnd").datebox("setValue", "");
    //                    show_msg("到期时间末不能小于到期时间始")
    //                }
    //            }
    //        }
    //    });
    //},

    // 关闭编辑窗口
    //closeEditPanel: function(isRefresh) {
    //    var ifr = $("#editPanel").children()[0];
    //    $(ifr).attr("src", '');
    //    $("#editPanel").dialog("close");
    //    if (isRefresh ) {
    //        ChannelManage.doSearchShow();
    //    }
    //},

    /**
     * 新增
     */
    doAdd: function(proId, channelId, channelType) {
        //var row = $("#ticket_chanel_dg").datagrid("getSelected");
        //if (rows.length <= 0) {
        //    show_msg("请选择一条记录！");
        //    return ;
        //}

        $("#editPanel").dialog({
            title:'门票验证配置',
            height:200,
            width:300,
            buttons: [{
                text:'确定',
                handler:function(){
                    ChannelManage.doSave(proId, channelId);
                }
            },{
                text:'取消',
                handler:function(){
                    $("#editPanel").dialog("close");
                    $("#sel_channel").combobox("setValue", "");
                }
            }]
        });
        $("#editPanel").dialog("open");

        if (channelType) {
            $("#sel_channel").combobox("setValue", channelType);
        }
    },

    doSave: function(id, channelId) {
        var data = {
            'chanel.product.id':id,
            'chanel.chanel':$("#sel_channel").combobox("getValue")
        }
        if (channelId) {
            data['chanel.id'] = channelId;
        }
        var url = "/provalidatechanel/proValidateChanel/saveProChannel.jhtml";
        $.post(url, data,
            function(result){
                if (result.success) {
                    $("#ticket_chanel_dg").datagrid("reload");
                    $("#editPanel").dialog("close");
                    $("#sel_channel").combobox("setValue", "");
                    show_msg("操作成功！");
                } else {
                    show_msg("操作失败！");
                }
            }
        );


    },

    doSearchShow: function() {
        var data = {
            'chanel.product.name': $("#ipq_name").textbox("getValue"),
            'chanel.product.id': $("#ipq_proNum").textbox("getValue"),
            'chanel.chanel': $("#ipq_channel").textbox("getValue"),
            'chanel.updateTimeStartStr': $("#ipq_updateTimeStart").datebox("getValue"),
            'chanel.updateTimeEndStr': $("#ipq_updateTimeEnd").datebox("getValue"),
            'chanel.proType' : $("#ipq_proType").val()
        };
        $("#ticket_chanel_dg").datagrid("load", data);
    },

    doClearShow: function() {
        $("#ipq_name").textbox("setValue", "");
        $("#ipq_proNum").textbox("setValue", "");
        $("#ipq_channel").textbox("setValue", ""),
        $("#ipq_updateTimeStart").datebox("setValue", "");
        $("#ipq_updateTimeEnd").datebox("setValue", "");
        var data = {
        };
        $("#ticket_chanel_dg").datagrid("load", data);
    },

    initTicketChanel: function() {
        $("#ticket_chanel_dg").datagrid({
            fit:true,
            url:'/provalidatechanel/proValidateChanel/productList.jhtml',
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'id', title: '产品编号', width: 150},
                { field: 'name', title: '产品名称', width: 250},
                { field: 'chanel', title: '验证通道', width: 150, codeType:'chanel', formatter:ChannelUtil.codeFmt},
                { field: 'userName', title: '操作人', width: 150},
                { field: 'updateTime', title: '操作时间', width: 200, datePattern: "yyyy-MM-dd", formatter: ChannelUtil.dateTimeFmt},
                {field:'opt', title:'操作',width:100,
                    formatter:function(value, rowData, rowIndex) {
                        if (rowData.channelId) {
                            return "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ChannelManage.doAdd(" + rowData.id + ","+rowData.channelId+",\"" + rowData.chanel + "\")'>配置</a>";
                        } else {
                            return "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ChannelManage.doAdd(" + rowData.id + ")'>配置</a>";
                        }

                    }
                }

            ]],
            toolbar: '#show_tb',
            onBeforeLoad : function(data) {   // 查询参数
                data['chanel.product.name']= $("#ipq_name").textbox("getValue");
                data['chanel.product.id']= $("#ipq_proNum").textbox("getValue");
                data['chanel.chanel']= $("#ipq_channel").textbox("getValue");
                data['chanel.updateTimeStartStr']= $("#ipq_updateTimeStart").datebox("getValue");
                data['chanel.updateTimeEndStr']= $("#ipq_updateTimeEnd").datebox("getValue");
                data['chanel.proType'] = $("#ipq_proType").val();
            }
        });
    }

};

$(function() {
    ChannelManage.init();
});
