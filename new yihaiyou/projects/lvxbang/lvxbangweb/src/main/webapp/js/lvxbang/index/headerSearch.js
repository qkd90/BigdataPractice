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
		var keyword = $("#keyword").text();
		var index = $(this).index();
		var type;
		if (index == 0) {
			type = "all";
		} else if (index == 1) {
			type = "plan";
		} else if (index == 2) {
			type = "guide";
		} else if (index == 3) {
			type = "scenic";
		} else if (index == 4) {
			type = "hotel";
		} else if (index == 5) {
			type = "food";
		} else if (index == 6) {
			type = "line";
		}
		keyword = encodeURI(encodeURI(keyword));
		window.location.href = "/search_" + keyword + "_" + type + ".html";
	});
	var type = $("#type").val();
	var a = $(".Favorites_div_fl a");
	var checked = a.eq(0);
	if (type == "plan") {
		checked = a.eq(1);
	} else if (type == "guide") {
		checked = a.eq(2);
	} else if (type == "scenic") {
		checked = a.eq(3);
	} else if (type == "hotel") {
		checked = a.eq(4);
	} else if (type == "food") {
		checked = a.eq(5);
	} else if (type == "line") {
		checked = a.eq(6);
	}
	checked.addClass("checked");
	$(".Favorites_div_fr").find(".mailTablePlan").eq(checked.index()).show().siblings(".mailTablePlan").hide();
	buildPage(checked.attr("data-favoriteType"), checked);

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
function buildPage(favoriteType, athiz) {
	var pager = new Pager({
        pageNo: 1,                  //页码
        pageSize: 10,               //每页显示条数
        mode: 2,                    //类型：1：分页计数分开，2：分页计数合并
        container: "#favorites_pager_"+favoriteType,      //容器选择器
		pageRenderFn: function(pageNo, pageSize, data) {
            // 加载数据时图片
            var divMain = null;
            if (athiz) {
                divMain = $(".Favorites_div_fr").find(".mailTablePlan").eq($(athiz).index());
            } else {
                divMain = $(".Favorites_div_fr").find(".mailTablePlan").eq(0);
            }
            divMain.find('.Favorites_div_fr_ul').remove();
            divMain.prepend('<div style="margin-top:84px;" class="textC"><img src="/images/loadingx.gif"/></div>');

            $.ajax({
				url:"/lvxbang/index/searchPage.jhtml",
				type:"post",
				dataType:"json",
				data:{
					'solrType': favoriteType,
					'keyword': $('#keyword').html(),
					'page': pageNo,
					'rows': pageSize
				},
				success: function(result) {
					var total = 0;
					if (result && result.data) {
						var data = result.data;
						total = result.totalCount;
						if (total > 0) {
                            divMain.find('.textC').remove();
							divMain.prepend('<ul class="Favorites_div_fr_ul" id="favorites_'+favoriteType+'"></ul>');
							for(var i = 0; i < data.length; i++) {
								var s = data[i];
								var itemHtml = $(template("tpl-favorite-item-"+s.type, s));
								$('#favorites_'+favoriteType).append(itemHtml);
                                if (s.type == 'scenic_info') {  // 1.景点添加到行程标记
                                    itemHtml.data('scenic', s);
                                }
							}
                            FloatEditor.markScenic();    // 2.景点添加到行程标记
							// buildDelEvent();	// 删除收藏事件
							//图片延迟加载
							$("img").lazyload({
								effect : "fadeIn",
                                load : resizePic
							});
							$(".is_hover, .food_p_hover").hover(function () {
								$(this).find("span").hide();
							}, function () {
								$(this).find("span").show();
							});
							lookMap();
							pager.renderPageNav({pageNo:pageNo, pageSize: pageSize, total: total});
						} else {
							// 未加载到数据时图片
							divMain.html('<div style="margin-top:84px;" class="textC"><img src="/images/null.jpg" width="261px" height="163px"/></div>');
						}
					}
				},
				error:function(){
					//alert("操作失败");
					promptWarn("操作失败");
				}
			});
			
		}
	});
	pager.init();
}

// 绑定收藏夹删除事件
function buildDelEvent() {
	$(".Favorites_div_fr a.close").click(function(){
		var thiz = this;
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
						});
					} else {
						promptWarn(result.errorMsg);
						//alert(result.errorMsg);
					}
				} else {
					//alert("操作失败");
					promptWarn("操作失败");
				}
			},
			error:function(){
				promptWarn("操作失败");
				//alert("操作失败");
			}
		});
	});
}

