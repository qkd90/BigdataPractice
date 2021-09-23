/**
 * Created by dy on 2016/3/14.
 */

var AgentTicketDialog = {

    init: function() {

        AgentTicketDialog.initPriceType();
    },

    initPriceType: function() {

        var url = "/proManage/productManage/getTicketTypePriceList.jhtml?topTicketId=" + $("#ipt_topline_id").val();

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
                {field:'name',title:'类型名称',width:'200'},
                {field:'discountPrice',title:'分销价',width:'100'},
                {field:'rebate',title:'佣金',width:'100',
                    formatter: function(value, rowData, index) {
                        var hipt = '<input type="text" id="rebate_'+ index + '_'+ rowData.id +'" onkeyup="AgentTicketDialog.getChangeValue('+index+','+rowData.id+')" style="width: 99px; margin-left: -4px; height: 22px;display:none;" temp-value="0">';
                        return hipt;
                    }
                }
            ]],
            onCheck: function(index,rowData) {
                $("#rebate_"+index+"_"+rowData.id+"").show();
            },
            onUncheck: function(index,rowData) {
                $("#rebate_"+index+"_"+rowData.id+"").hide();
                $("#rebate_"+index+"_"+rowData.id+"").val("");
            }
        });

    },

    agentTicket: function() {

        var rows = $("#priceType_dg").datagrid("getChecked");

        var flag = true;

        if (rows.length > 0) {


            var typePriceArr = [];

            $.each(rows, function(i, perValue) {

                var id = perValue.id;
                var index = $("#priceType_dg").datagrid("getRowIndex", perValue);
                var rebate = $("#rebate_"+index+"_"+id+"").val();
                if (!rebate) {
                    show_msg("请完善价格类型【"+ perValue.name+"】的儿童佣金");
                    flag = false;
                    return false;
                }

                var typeObject = {
                    topTypePriceId:perValue.id,
                    rebate:rebate
                };

                typePriceArr.push(typeObject);

            });

            if (typePriceArr.length <= 0) {
                flag = false;
            }

            var typePriceOjbStr = JSON.stringify(typePriceArr);

            var data = {
                typePriceObj:typePriceOjbStr,
                ticketId:$("#ipt_topline_id").val()
                //lineRemark:$("#ipt_remark").textbox("getValue")
            };




            if (flag) {
                var url = "/proManage/productManage/doAgentTicket.jhtml";
                $.post(url,
                    data,
                    function(result) {
                        if (result.success) {
                            show_msg("门票代理成功！");
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
        var rebate = $("#rebate_"+index+"_"+id+"").val();
        if (!AgentTicketDialog.checkRate(rebate)) {
            $("#rebate_"+index+"_"+id+"").val("");
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
    AgentTicketDialog.init();
})
