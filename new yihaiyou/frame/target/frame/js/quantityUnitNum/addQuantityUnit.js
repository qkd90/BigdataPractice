/**
 * Created by dy on 2016/4/20.
 */

var AddQuntityUnit = {

    init: function() {
        AddQuntityUnit.initJsp();
    },

    /**
     * 功能描述：初始化JSP
     */
    initJsp: function() {
        $("#ipt-unitIdentityCode").textbox({
            buttonText:'查询',
            buttonIcon:'icon-search',

            onClickButton: function() {

                var identityCode = $(this).textbox("getValue");
                if (identityCode) {
                    AddQuntityUnit.searchUnitByIdentityCode(identityCode);
                } else {
                    show_msg("请输入公司串码！");
                }
            }
        });

    },

    /**
     * 功能描述：保存前验证数据
     * @returns {boolean}
     */
    saveBefore: function() {

        var dealurId = $("#dealurId").val();
        var conditionNums = $("#ipt-conditionNums").numberbox("getValue");

        var flag = true;

        if (dealurId.length <= 0) {
            show_msg("被拱量公司不能为空！");
            return false;
        }
        if (conditionNums.length <= 0) {
            show_msg("拱量条件不能为空！");
            return false;
        }

        return true;

    },

    /**
     * 功能描述：关闭弹窗
     */
    cancelAddDealurUnit: function() {
        window.parent.$("#addQuantityUnit").dialog("close");
    },

    /**
     * 功能描述：添加公司拱量关系
     */
    addDealurUnit: function() {

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        if (AddQuntityUnit.saveBefore()) {
            var data = {
                dealurId: $("#dealurId").val(),
                conditionNums:$("#ipt-conditionNums").numberbox("getValue")
            };
            var url = "/quantityUnit/quantityUnitNum/addDealurUnit.jhtml";
            $.post(
                url,
                data,
                function(result) {
                    if (result.success) {
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                        window.parent.$("#dg").datagrid("reload");
                        window.parent.$("#addQuantityUnit").dialog("close");
                    } else {
                        $.messager.progress("close");
                        show_msg(result.errorMsg);
                    }
                }
            );
        } else {
            $.messager.progress("close");
        }
    },


    /**
     * 功能描述：查询公司串码
     *          判断串码对应公司是否存在
     *          判断串码公司是否已经添加
     * @param identityCode
     */
    searchUnitByIdentityCode: function(identityCode) {

        var data = {
            identityCode: identityCode
        }

        var url = "/quantityUnit/quantityUnitNum/searchUnitByIdentityCode.jhtml";
        $.post(
            url,
            data,
            function(result) {
                if (result.success) {
                    $("#dealurId").val(result.dealerUnitId);
                    $("#dealurName").html(result.dealerUnitName);
                    $("#dealurIdentityCode").html(result.dealerUnitIdectityCode);
                    $("#dealerUnitUnitDetail").show();
                } else {
                    $("#dealerUnitUnitDetail").hide();
                    $("#dealurId").val("");
                    $("#dealurIdentityCode").html("");
                    $("#dealurName").html("");
                    show_msg(result.errorMsg);
                }
            }
        );

    }



};

$(function() {
   AddQuntityUnit.init();
});