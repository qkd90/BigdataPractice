var editStep3 = {
	init:function(){
		editStep3.initStatus();
	},	
	// 初始状态
	//initStatus : function() {
	//	//var lineInfo = parent.window.getIfrData('step1');
	//	$('#productId').val(lineInfo.productId);
	//	$('#productName').html(lineInfo.productName);
	//},
	// 返回修改管理
	doBackLineMgr:function(){
		parent.window.closeChildPanel();	
	},	
	// 编辑线路
	doEditLine : function() {
		var productId = $('#productId').val();
		var url = "/traffic/traffic/editWizard.jhtml?productId="+productId;
		parent.window.location.href = url;
	},
	// 添加线路
	doAddLine : function() {
		var url = "/traffic/traffic/addWizard.jhtml";
		parent.window.location.href = url;
	}
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
	editStep3.init();
});