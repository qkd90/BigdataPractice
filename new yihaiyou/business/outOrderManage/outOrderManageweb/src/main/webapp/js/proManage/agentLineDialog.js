/**
 * Created by dy on 2016/3/14.
 */

var AgentLineDialog = {

    init: function() {

        AgentLineDialog.initPriceType();
    },

    initPriceType: function() {

        var url = "/proManage/productManage/getLineTypePriceList.jhtml?toplineId=" + $("#ipt_topline_id").val();

        $('#priceType_dg').datagrid({
            url:url,
            //fit:true,
            //pagination:true,
            //pageList:[10,20,50],
            border:false,
            rownumbers:false,
            width:430,
            height:'auto',
            singleSelect:false,
            selectOnCheck:false,
            checkOnSelect:false,
            striped:true,//斑马线
            columns:[[
                {field:'id', checkbox: true },
                {field:'quoteName',title:'类型名称',width:'200'},
                {field:'adultRebate',title:'成人佣金',width:'100',
                    formatter: function(value, rowData, index) {

                        var hipt = '<input type="text" id="adult_'+ index + '_'+ rowData.id +'" onkeyup="AgentLineDialog.getChangeValue('+index+','+rowData.id+')" style="width: 95px;height: 22px;display:none;" temp-value="0">';

                        return hipt;

                    }
                },
                {field:'childRebate',title:'儿童佣金',width:'100',
                    formatter: function(value, rowData, index) {

                        var hipt = '<input type="text" id="child_'+ index + '_' + rowData.id +'" onkeyup="AgentLineDialog.getChangeValue('+index+','+rowData.id+')" style="width: 95px;height: 22px;display:none;" temp-value="0">';

                        return hipt;

                    }}
            ]],
            onCheck: function(index,rowData) {
                $("#child_"+index+"_"+rowData.id+"").show();
                $("#adult_"+index+"_"+rowData.id+"").show();
            },
            onUncheck: function(index,rowData) {

                $("#child_"+index+"_"+rowData.id+"").hide();
                $("#child_"+index+"_"+rowData.id+"").val("");
                $("#adult_"+index+"_"+rowData.id+"").hide();
                $("#adult_"+index+"_"+rowData.id+"").val("");

            }
        });

    },

    agentLine: function() {

        var rows = $("#priceType_dg").datagrid("getChecked");

        var flag = true;

        if (rows.length > 0) {


            var typePriceArr = [];

            $.each(rows, function(i, perValue) {

                var id = perValue.id;
                var index = $("#priceType_dg").datagrid("getRowIndex", perValue);
                var childRebate = $("#child_"+index+"_"+id+"").val();
                var adultRebate = $("#adult_"+index+"_"+id+"").val();
                if (!childRebate) {
                    show_msg("请完善价格类型【"+ perValue.quoteName+"】的儿童佣金");
                    flag = false;
                    return false;
                }
                if (!adultRebate) {
                    show_msg("请完善价格类型【"+ perValue.quoteName+"】的成人佣金");
                    flag = false;
                    return false;
                }
                var typeObject = {
                	topLineId:$("#ipt_topline_id").val(),
                    topTypePriceId:perValue.id,
                	//topTypePrice:perValue.marketPrice,
                	quoteName:perValue.quoteName,
                	quoteDesc:perValue.quoteDesc,
                	marketPrice:perValue.marketPrice,
                	childRebate:childRebate,
                	adultRebate:adultRebate,
                	status:perValue.status

                };

                typePriceArr.push(typeObject);

            });

            if (typePriceArr.length <= 0) {
                flag = false;
            }

            var typePriceOjbStr = JSON.stringify(typePriceArr);

            var data = {
                typePriceObj:typePriceOjbStr,
                lineId:$("#ipt_topline_id").val(),
                lineRemark:$("#ipt_remark").textbox("getValue")
            };




            if (flag) {
                var url = "/proManage/productManage/doAgentLine.jhtml";
                $.post(url,
                    data,
                    function(result) {
                        if (result.success) {
                            show_msg("线路代理成功！");
                            window.parent.$("#editPanel").dialog("close");
                        }
                    }
                );
            }
        } else {
            show_msg("请选择价格类型并其编辑佣金");
        }



    },

    cancelAgent: function() {
        window.parent.$("#editPanel").dialog("close");
    },

    getChangeValue: function(index, id) {
        var childRebate = $("#child_"+index+"_"+id+"").val();
        var adultRebate = $("#adult_"+index+"_"+id+"").val();

        if (!AgentLineDialog.checkRate(childRebate)) {
            $("#child_"+index+"_"+id+"").val("");
        }
        if (!AgentLineDialog.checkRate(adultRebate)) {
            $("#adult_"+index+"_"+id+"").val("");
        }
    },
    //判断正整数
    checkRate: function(inputStr) {
        //var reg = $(this).val().match(/\d+\.?\d{0,2}/);
        //var re = /^[1-9]+[0-9]*]*$/;
        var re = /\d+\.?\d{0,2}/;
        if (!re.test(inputStr)) {
            //show_msg("请输入正整数");
            return false;
        } else {
            return true;
        }
    }

}

$(function() {
    AgentLineDialog.init();
})
