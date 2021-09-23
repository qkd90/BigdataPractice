$(document).ready(function(){
	//搜索框
	$(".categories .input").click(function(){
		$(this).next(".categories_div").show();
	});
	
	$(".categories_div li").click(function(){
		var label=$("label",this).text();
		$(this).closest(".categories").children(".input").val(label);
	});
	
	$('.categories  .input').on('click',function(event){
		// 阻止冒泡
		if (event.stopPropagation) {    // standard
		    event.stopPropagation();
		} else {    
			// IE6-8
		    event.cancelBubble = true;
		}
	});
	$(document).on("click", function(){ 
		$(".categories_div").hide(); 
	});
	
//	$("img").lazyload({
//		effect : "fadeIn"
//	});
	
	$("a.clear").click(function(){
		deleteWarn("确定清空消息通知？", function () {
			$.ajax({
				url:"/lvxbang/message/batchClearMessage.jhtml",
				type:"post",
				dataType:"json",
				data:{},
				success: function(result) {
					if (result) {
						if (result.success) {
							location.reload();	// 重新加载当前页面
						} else {
							promptWarn(result.errorMsg);
						}
					} else {
						promptWarn("操作失败");
					}
				},
				error:function(){
					promptWarn("操作失败");
				}
			});
		});
//		$(".System_ul").fadeOut(500,function(){
//			$(this).remove();
//		});
	});

	buildPage();	// 加载列表
	
});	

//构建分页列表
function buildPage() {
	var pager = new Pager({
	pageNo: 1,                  //页码
	pageSize: 10,               //每页显示条数
	mode: 2,                    //类型：1：分页计数分开，2：分页计数合并
//	container: "#favorites_pager_"+favoriteType,      //容器选择器
	pageRenderFn: function(pageNo, pageSize, data) {
			$.ajax({
				url:"/lvxbang/message/page.jhtml",
				type:"post",
				dataType:"json",
				data:{
					'page': pageNo,
					'rows': pageSize
				},
				success: function(result) {
					var total = 0;
					$('#message_').empty();
					if (result && result.data) {
						var data = result.data;
						total = result.totalCount;
						for(var i = 0; i < data.length; i++) {
							var s = data[i];
							var itemHtml = template("tpl-message-item-"+s.msgType, s);
							$('#message_').append(itemHtml);
						}
						buildDelEvent();	// 删除消息事件
						//图片延迟加载
					    $("img").lazyload({
					        effect : "fadeIn"
					    });
					    // 设置总数
					    $('#totalSpan').html(total);
					}
					pager.renderPageNav({pageNo:pageNo, pageSize: pageSize, total: total});
				},
				error:function(){
					promptWarn("操作失败，请尝试重新登录");
				}
			});
			
		}
	});
	pager.init();
}

//绑定收藏夹删除事件
function buildDelEvent() {
	$(".System_ul li").hover(function(){
		$("a.close",this).show();
	},function(){
		$("a.close",this).hide();
	});
	
	$("a.close").click(function(){
		var thiz = this;
		var messageId = $(thiz).attr('data-messageId');
		$.ajax({
			url:"/lvxbang/message/clearMessage.jhtml",
			type:"post",
			dataType:"json",
			data:{'ids': messageId},
			success: function(result) {
				if (result) {
					if (result.success) {
						$(thiz).parent('li').fadeOut(500,function(){
							$(thiz).remove();
						});

					    // 设置总数
						var total = parseInt($('#totalSpan').html())-1;
					    $('#totalSpan').html(total);
					} else {
						promptWarn(result.errorMsg);
					}
				} else {
					promptWarn("操作失败");
				}
			},
			error:function(){
				promptWarn("操作失败");
			}
		});
	});
	
}
