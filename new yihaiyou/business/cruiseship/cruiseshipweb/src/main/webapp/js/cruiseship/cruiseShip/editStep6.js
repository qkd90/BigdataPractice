/**
 * Created by zzl on 2016/9/22.
 */
var editStep6 = {
    init:function(){
        editStep6.initStatus();
    },
    // 初始状态
    initStatus : function() {
        //var lineInfo = parent.window.getIfrData('step1');
        //$('#productId').val(lineInfo.productId);
        //$('#productName').html(lineInfo.productName);
    },
    // 返回修改管理
    doBackCruiseShipMgr:function(){
        parent.window.closeChildPanel();
    },
    // 编辑线路
    doEditCruiseShip : function() {
        var productId = $('#productId').val();
        var url = "/cruiseship/cruiseShip/editWizard.jhtml?productId="+productId;
        parent.window.location.href = url;
    },
    // 添加线路
    doAddCruiseShip : function() {
        var url = "/cruiseship/cruiseShip/editWizard.jhtml";
        parent.window.location.href = url;
    }
};

//返回本页面数据
function getIfrData(){
    var data = {};
    return data;
}

$(function(){
    editStep6.init();
});