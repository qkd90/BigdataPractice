var editSupplier = {
	imgFolder:'/logo/',
	
	init:function(){
		editSupplier.initComp();
		editSupplier.initStatus();
		editSupplier.initFrameHeight();
	},	
	// 初始控件
	initComp : function() {

		if ($("#startCityId").val()) {
			AreaSelectDg.initArea($("#startCity"), $("#startCityId").val());
		}
	},
	// 初始状态
	initStatus : function() {
		
	},
	// 返回
	doBack: function() {
		parent.window.auditList.closeViewPanel(false);
	},
	initFrameHeight: function() {
		window.parent.$("#viewIframe").css("height", $("#content").height() + 50);
	},
	download: function(imgPathURL, fileName) {
		$("#downDialog").dialog({
			onBeforeOpen: function() {
				$("#viewImg").attr("src", imgPathURL);
			},
			onClose: function() {
				$("#viewImg").attr("src", "");
			}
		});
		$("#downDialog").dialog("open");
	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};
$(function(){
	editSupplier.init();
});