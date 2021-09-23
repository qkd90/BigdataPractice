

var CAPlayform = {
	bindTreeEvent : function(){
		$('#tt').tree({
			data : [{
				text: '所有会员RFM全貌',
				href : 'http://www.baidu.com'
			},{
				text: '所有会员RFM明细',
				href : '/crm/frm/rfmdetail.jhtml'
			},{
				text: '购买分析',
				href : '/crm/frm/buydetail.jhtml'
			},{
				text: '活动分析',
				children : [{
					text : '新老客户活动对比',
					href : '/activity/newAndOld/newAndOldActivity.jhtml'
				}]
			},{
				text: '常用命令',
				children : [{
					text : '重置会员RFM属性',
					href : '/crm/frm/resetRfmDetailUsers.jhtml'
				}]
			}],
			onClick: function(node){
				$("#right").load(node.href);
			}
		});
	},
	init : function() {
		this.bindTreeEvent();
	}
};
$(function() {
	CAPlayform.init();
});