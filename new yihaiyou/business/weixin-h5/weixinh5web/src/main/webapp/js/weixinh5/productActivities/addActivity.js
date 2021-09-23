/**
 * Created by dy on 2016/2/17.
 */
var AddActivity = {
    init : function() {
        AddActivity.initElement();
        AddActivity.initSelectCombobox();
    },

    selCondition : function(index) {
        if (index === 'yes') {
            $("#ipt_lowPrice").numberbox({readonly:false});
        } else {
            $("#ipt_lowPrice").numberbox({readonly:true});
        }
    },
    selLimitGet : function(index) {
        if (index === 'yes') {
            $("#perLimitId").numberbox({readonly:false});
        } else {
            $("#perLimitId").numberbox({readonly:true});
        }
    },
    selectTuig : function(str) {
        if (str === 'com_buyer') {
            $("#com_buyer").show();
            $("#com_saler").hide();
        } else {
            $("#com_saler").show();
            $("#com_buyer").hide();
        }
    },


    cancelSave: function() {

        var url = "/weixinh5/productActivities/cancelSave.jhtml";
        var data = {
            activityId : $("#ipt_lastId").val()
        };
        $.post(url, data,
            function(msg){
                if (msg.success) {
                    window.parent.$("#editPanel").dialog("close");
                    window.parent.$('#dg_activity').datagrid("reload");
                }
            });
    },
    selectAllProduct: function(lineIds, aId) {
        var data = {
            "lineIds" : lineIds,
            "activityId" : aId
        };
        var url = "/weixinh5/productActivities/selectAllProduct.jhtml";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(msg){
                //console.log(msg);
            }
        });
    },
    cancelAllProduct: function(lineIds, aId) {
        var data = {
            "lineIds" : lineIds,
            "activityId" : aId
        };
        var url = "/weixinh5/productActivities/cancelAllProduct.jhtml";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(msg){
                //console.log(msg);
            }
        });
    },

    //表格线路查询
    doSearch: function() {
        $('#dg_product').datagrid('reload',{
            proType: $("#ipt_proType").combobox('getValue'),
            productName: $("#ipt_productName").textbox('getValue')
        });
    },

    operaProduct : function(str) {
        if (str === 'someProduct') {
            $("#product_list").show();
            $('#dg_product').datagrid({
                url:'/weixinh5/productActivities/productGrid.jhtml?lastId='+ $("#ipt_lastId").val(),
                pagination: true,
                pageList: [10, 20, 30],
                rownumbers: true,
                fitColumns: true,
                fit: true,
                columns:[[
                    {field:'id',checkbox:true, width:8,sortable: true, align: 'center'},
                    {field:'name',title:'产品名称',width:100,sortable: true}
                ]],
                onUncheckAll: function(rows) {
                    var lineIds = "";

                    $.each(
                        rows, function(i, perValue) {
                            lineIds = lineIds + perValue.id + ",";
                        }
                    );

                    if (lineIds.length > 0) {
                        lineIds = lineIds.substr(0, lineIds.length-1);
                    }
                    AddActivity.cancelAllProduct(lineIds, $("#ipt_lastId").val());
                },
                onCheckAll: function(rows) {
                    var lineIds = "";
                    $.each(
                        rows, function(i, perValue) {
                            lineIds = lineIds + perValue.id + ",";
                        }
                    );

                    if (lineIds.length > 0) {
                        lineIds = lineIds.substr(0, lineIds.length-1);
                    }
                    AddActivity.selectAllProduct(lineIds, $("#ipt_lastId").val());
                },
                onUncheck: function(rowIndex, rowData) {
                    AddActivity.cancelProduct(rowData.id, $("#ipt_lastId").val());
                },
                onCheck: function(rowIndex, rowData) {

                    AddActivity.selectProduct(rowData.id, $("#ipt_lastId").val());
                },
                onLoadSuccess: function(data) {
                    $.each(data.rows, function(i, perValue){
                        if (perValue.isChecked == 1) {
                            $('#dg_product').datagrid('selectRow',i);
                        }
                    });

                }
            });
        } else {
            $("#product_list").hide();
            var data = {
                "activityId" : $("#ipt_lastId").val(),
                "cancelAll" : "yes"
            };

            var url = "/weixinh5/productActivities/cancelProduct.jhtml";
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                success: function(msg){
                    console.log(msg);
                }
            });

        }
    },

    selectProduct: function(productId, activityId) {
        //alert(productId + "," +activityId);

        var data = {
            "productId" : productId,
            "activityId" : activityId
        };

        var url = "/weixinh5/productActivities/selectProduct.jhtml";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(msg){
                console.log(msg);
            }
        });

    },
    cancelProduct: function(productId, activityId) {
        var data = {
            "productId" : productId,
            "activityId" : activityId
        };
        var url = "/weixinh5/productActivities/cancelProduct.jhtml";
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(msg){
                console.log(msg);
            }
        });
    },

    initSelectCombobox : function() {
        $("#com_activityName").combobox({
            onSelect : function(record){

                if (record.label ==='flashsale') {      //限时抢购
                    $("#product_list").hide();
                    $("#bodyForm").form("reset");
                    $("#ipt_instructions").textbox("setValue", "");
                    $(".coupon_class").hide();
                    $(".info").hide();
                    $(".limit").show();
                } else {
                    $("#bodyForm").form("reset");
                    $("#product_list").hide();
                    $("#ipt_instructions").textbox("setValue", "");
                    $(".coupon_class").show();
                    $(".info").show();
                    $(".limit").show();
                }
            }
        });
    },

    initElement : function() {
        $("#product_list").hide();
        //$("#com_buyer").show();
        $("#com_saler").hide();
        $("#perLimitId").numberbox({readonly:true});
        $("#ipt_lowPrice").numberbox({readonly:true});
    },


    /**
     * 保存活动
     */
    saveActivity: function() {

        var aType = $("#com_activityName").combobox("getValue");

        if (aType ==='flashsale') {      //限时抢购

            var data = AddActivity.flashsaleData();

            var validate = AddActivity.flashsaleValidate();

            if (validate) {
                $.messager.confirm('保存提示', '是否立即上架？', function(r){
                    if (r){
                        data.statusStr = "UP";
                        var url = "/weixinh5/productActivities/saveActivity.jhtml";

                        $.post(url, data,
                            function(result){
                                if (result.success) {
                                    show_msg("保存成功！");
                                    window.parent.$("#editPanel").dialog("close");
                                    window.parent.$('#dg_activity').datagrid("reload");
                                }
                            }
                        );
                    } else {
                        data.statusStr = "DOWN";
                        var url = "/weixinh5/productActivities/saveActivity.jhtml";

                        $.post(url, data,
                            function(result){
                                if (result.success) {
                                    show_msg("保存成功！");
                                    window.parent.$("#editPanel").dialog("close");
                                    window.parent.$('#dg_activity').datagrid("reload");
                                }
                            }
                        );
                    }
                });


            }




        } else {                         //优惠券

            var data = AddActivity.couponData();

            var validate = AddActivity.couponValidate();

            if (validate) {
                $.messager.confirm('保存提示', '是否立即上架？', function(r){
                    if (r){
                        data.statusStr = "UP";
                        var url = "/weixinh5/productActivities/saveActivity.jhtml";

                        $.post(url, data,
                            function(result){
                                if (result.success) {
                                    show_msg("保存成功！");
                                    window.parent.$("#editPanel").dialog("close");
                                    window.parent.$('#dg_activity').datagrid("reload");
                                }
                            }
                        );
                    } else {
                        data.statusStr = "DOWN";
                        var url = "/weixinh5/productActivities/saveActivity.jhtml";

                        $.post(url, data,
                            function(result){
                                if (result.success) {
                                    show_msg("保存成功！");
                                    window.parent.$("#editPanel").dialog("close");
                                    window.parent.$('#dg_activity').datagrid("reload");
                                }
                            }
                        );
                    }
                });


            }



        }
    },


    flashsaleValidate:function() {

        var flag = true;
        var v_startTime = $("#ipt_startTime").datetimebox("getValue");
        var v_endTime = $("#ipt_endTime").datetimebox("getValue");
        var v_instructions = $("#ipt_instructions").textbox("getValue");
        var v_name = $("#ipt_name").textbox("getValue");

        if (v_name.length <= 0) {
            flag = false;
            show_msg("活动名称不能为空!");
            return flag;
        }
        if (v_startTime.length <= 0) {
            flag = false;
            show_msg("有效时间：开始时间不能为空!");
            return flag;
        }
        if (v_endTime.length <= 0) {
            flag = false;
            show_msg("有效时间：结束时间不能为空!");
            return flag;
        }
        if (v_instructions.length <= 0) {
            flag = false;
            show_msg("使用说明不能为空!");
            return flag;
        }

        return flag;
    },

    flashsaleData: function() {

        var data = {
            id: $("#ipt_lastId").val(),
            nameStr: $("#ipt_name").textbox("getValue"),
            type: $("#com_activityName").combobox("getValue"),
            startTimeStr: $("#ipt_startTime").datetimebox("getValue"),
            endTimeStr: $("#ipt_endTime").datetimebox("getValue"),
            perCounts: $("#perLimitId").numberbox("getValue"),
            productType: $("#ipt_productType").combobox("getValue"),
            instructions: $("#ipt_instructions").textbox("getValue"),
            statusStr:""
        }
        return data;
    },

    couponValidate:function() {

        var flag = true;

        var v_faceValue = $("#ipt_faceValue").numberbox("getValue");
        var v_number = $("#ipt_number").numberbox("getValue");
        var v_startTime = $("#ipt_startTime").datetimebox("getValue");
        var v_endTime = $("#ipt_endTime").datetimebox("getValue");
        var v_instructions = $("#ipt_instructions").textbox("getValue");
        var v_name = $("#ipt_name").textbox("getValue");

        if (v_name.length <= 0) {
            flag = false;
            show_msg("活动名称不能为空!");
            return flag;
        }
        if (v_faceValue.length <= 0) {
            flag = false;
            show_msg("面值不能为空!");
            return flag;
        }
        if (v_number.length <= 0 ) {
            flag = false;
            show_msg("发行总量不能为空!");
            return flag;
        }
        if (v_startTime.length <= 0) {
            flag = false;
            show_msg("有效时间：开始时间不能为空!");
            return flag;
        }
        if (v_endTime.length <= 0) {
            flag = false;
            show_msg("有效时间：结束时间不能为空!");
            return flag;
        }
        if (v_instructions.length <= 0) {
            flag = false;
            show_msg("使用说明不能为空!");
            return flag;
        }

        return flag;
    },
    couponData: function() {

        var sceneTypeStr = "";

        var iptRadioArr = $("input[name='promotway']");

        $.each(iptRadioArr, function(i, perValue){

            if($(perValue).prop("checked")) {

                if ($(perValue).val() == 'buyerget') {
                    sceneTypeStr = $("#slt_buyerget").combobox("getValue");
                } else {
                    sceneTypeStr = $("#slt_sellersend").combobox("getValue");
                }

            }

        });

        var data = {

            id: $("#ipt_lastId").val(),
            nameStr: $("#ipt_name").textbox("getValue"),
            type: $("#com_activityName").combobox("getValue"),
            faceValue: $("#ipt_faceValue").numberbox("getValue"),
            number: $("#ipt_number").numberbox("getValue"),
            startTimeStr: $("#ipt_startTime").datetimebox("getValue"),
            endTimeStr: $("#ipt_endTime").datetimebox("getValue"),
            lowPrice: $("#ipt_lowPrice").numberbox("getValue"),
            perCounts: $("#perLimitId").numberbox("getValue"),
            productType: $("#ipt_productType").combobox("getValue"),
            instructions: $("#ipt_instructions").textbox("getValue"),
            promotway: $("input[name='promotway']:checked").val(),
            sceneType: sceneTypeStr,
            statusStr:""
        };

        return data;

    }


}

$(function(){
    AddActivity.init();
})