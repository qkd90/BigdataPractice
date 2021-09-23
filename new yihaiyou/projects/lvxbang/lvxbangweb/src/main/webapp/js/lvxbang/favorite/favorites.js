$(document).ready(function(){
	//栏目加浮动
	var navbar = function(){
	var navbar_top = $(window).scrollTop();
	var height=$("#nav").offset().top;
	if(navbar_top >=height){
		$(".nav").addClass("fixed");
	} 
	if(navbar_top <=height){
		$(".nav").removeClass("fixed");
	}}
   $(window).bind("scroll", navbar);
	
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
	
	$("img").lazyload({
		effect : "fadeIn"
	});
	
	
	
	$(".stroke").click(function(){
			var myStaute = $(this).attr("data-staute");
			if(!myStaute){
				$(this).addClass("checked").attr("data-staute","1");
			}else{
				$(this).removeClass("checked").removeAttr("data-staute");
		 }
	});
	
	//选项卡
	$(".Favorites_div_fl a").click(function(){
		$(this).addClass("checked").siblings().removeClass("checked");
		$(".Favorites_div_fr").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
		var favoriteType = $(this).attr('data-favoriteType');
		buildPage(favoriteType,1);
	});
	buildPage('',1);	// 默认列表

});	

function toggleText(it) {
		var $this = $(it);

		//如果数据被保存起来了,则表示要显示所有文字
	if ($this.data('text')) {
		$this.html($this.data('text') + "<span class='collapse'>收起<i></i></span>");
		//删除完整文字
		$this.removeData('text')
	} else {
		var text = $this.text()
		$this.html(text.substring(0, 100) + "<span class='more'>更多<i></i></span>");
		//把完整文字保存起来
		$this.data('text', text);
	}
}

$(function() {
	$(".food_p").each(function() {
		if ($(this).text().length > 100) {
		toggleText(this)
		}
	});
});

//更多
$('.food_p').delegate('.more', 'click', function() {
	var p = $(this).parent();
	$(this).remove();
	toggleText(p)
	
});

//收起
$('.food_p').delegate('.collapse', 'click', function() {
	var p = $(this).parent();
	$(this).remove();
	toggleText(p)
});

// 构建分页列表
function buildPage(favoriteType,pageCurrent) {
	var pager = new Pager({
        pageNo: pageCurrent,                  //页码
        pageSize: 10,               //每页显示条数
        mode: 2,                    //类型：1：分页计数分开，2：分页计数合并
        container: "#favorites_pager_"+favoriteType,      //容器选择器
		pageRenderFn: function(pageNo, pageSize, data) {
			$.ajax({
				url:"/lvxbang/favorite/page.jhtml",
				type:"post",
				dataType:"json",
				data:{
					'favoriteType': favoriteType,
					'page': pageNo,
					'rows': pageSize
				},
				success: function(result) {
					var total = 0;
					$('#favorites_'+favoriteType).empty();
					if (result && result.data) {
						var data = result.data;
						total = result.totalCount;
						for(var i = 0; i < data.length; i++) {
							var s = data[i];
							var itemHtml = $(template("tpl-favorite-item-"+s.favoriteType, s));
							$('#favorites_'+favoriteType).append(itemHtml);
							if (s.favoriteType == 'scenic') {  // 1.景点添加到行程标记
								// 为了符合添加到行程事件的数据格式s.id必须为景点id
								s.id = s.favoriteId;
								s.type = 'scenic_info';
								s.name = s.title;
								s.score = s.score;
								s.cover = s.imgPath;
								s.adviceTime = s.adviceTime;
								itemHtml.data('scenic', s);
							}
						}
                        FloatEditor.markScenic();    // 2.景点添加到行程标记
                        bindScenic();
						buildDelEvent();	// 删除收藏事件
						//图片延迟加载
					    $("img").lazyload({
					        effect : "fadeIn"
					    });
						$(".is_hover, .food_p_hover").hover(function () {
							$(this).find("span").hide();
						}, function () {
							$(this).find("span").show();
						});
						lookMap();
					}
					pager.renderPageNav({pageNo:pageNo, pageSize: pageSize, total: total});
				},
				error:function(){
					promptWarn("操作失败，请尝试重新登录");
					//alert("操作失败，请尝试重新登录");
				}
			});
			
		}
	});
	pager.init();
}

function bindScenic() {
    var id = [];
    $(".scenic-node").each(function () {
        id.push(parseInt($(this).data("id")));
    });
    $.getJSON("/lvxbang/scenic/getScenicBriefData.jhtml", {json: JSON.stringify(id)}, function (result) {
        $(".scenic-node").each(function () {
            $(this).data("scenic", result[parseInt($(this).data("id"))]);
        })

    })
}

// 绑定收藏夹删除事件
function buildDelEvent() {
	$(".Favorites_div_fr a.close").click(function(){
		var thiz = this;
		deleteWarn("确定删除该收藏？", function (thiz) {
			var favoriteId = $(thiz).attr('data-favoriteId');
			$.ajax({
				url:"/lvxbang/favorite/clearFavorite.jhtml",
				type:"post",
				dataType:"json",
				data:{'ids': favoriteId},
				success: function(result) {
					if (result) {
						if (result.success) {
							$(thiz).parent('li').fadeOut(500,function(){
								$(thiz).remove();
								//当删除成功后，左边数字跟着更改
								var type = $(thiz).attr("type");
								var allCount = Number($('#favorite_all').html());
								var typeCount = Number($('#favorite_'+type).html());
								if(allCount<=1){
									allCount = 0;
								}else{
									allCount = allCount - 1;
								}
								if(typeCount<=1){
									typeCount=0;
								}else{
									typeCount = typeCount - 1;
								}
								$('#favorite_all').html(allCount);
								$('#favorite_'+type).html(typeCount)
								var size = $(this).siblings("li").length;
								var pageN = Number($('.pageCurrent').html());
								if(size==0){
									pageN = pageN -1 ;
								}
								buildPage('',pageN);

							});
						} else {
							promptWarn(result.errorMsg);
							//alert(result.errorMsg);
						}
					} else {
						promptWarn("操作失败");
						//alert("操作失败");
					}
				},
				error:function(){
					promptWarn("操作失败");
					//alert("操作失败");
				}
			});
		}, thiz);
	});
}

