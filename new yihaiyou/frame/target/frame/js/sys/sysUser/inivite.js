var inivite = {
	init:function(){
		inivite.initStatus();
	},	
	// 初始状态
	initStatus : function() {
		//复制邀请链接
		$('.J_copy_btn').click(function(){
		    var link=$('.J_link_copy').val();
		    $('.J_link_copy').focus().select();
		    if(window.clipboardData){
		        window.clipboardData.setData('text', link);
		        show_msg("复制成功！");
		    }else{
		    	show_msg("浏览器不支持，请手动复制！");
		    }
		});
	}
};

$(function(){
	inivite.init();
});