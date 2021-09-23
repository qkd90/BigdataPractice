
var RecommendPlanList = {
	pager: null,
	activeObj: $(".xuanzhong").attr("data-order-column"),
	init: function() {
		RecommendPlanList.IE8Css();
		RecommendPlanList.initJsp();
		RecommendPlanList.createPager();
		RecommendPlanList.getRecommendPlanList();
	},

	initJsp: function() {

		if ($("#keyword").val()) {
			var routeBtn = '<button id="btn-keyword" type="button" data-name="keyword" onclick="RecommendPlanList.delCondition(this)">'+ $("#keyword").val() +'</button>';
			$("#form-sum-condition").append(routeBtn);
			$(".clear-all-sel-label").show();
		}

		$(".clear-all-sel-label").click(function() {
			$(":input[name='startDate']").first().prop("checked", "checked");
			$(":input[name='dayRange']").first().prop("checked", "checked");
			$("#keyword").val("");
			$("#form-sum-condition").empty();
			$(".clear-all-sel-label").hide();
			RecommendPlanList.getRecommendPlanList();     //查询游轮列表数据
		});


		$(".tuijianhaoping").find("li").click(function() {
			if (RecommendPlanList.activeObj == $(this).attr("data-order-column")) {
				if ($(this).attr("data-order-column") != "collectNum") {
					if ($(this).attr("data-order-type") == "desc") {
						$(this).attr("data-order-type", "asc");
						$(this).find("span").removeClass("desc");
						$(this).find("span").addClass("asc");
					} else {
						$(this).attr("data-order-type", "desc");
						$(this).find("span").removeClass("desc");
						$(this).find("span").addClass("desc");
					}
				}
			}
			$(".tuijianhaoping").children().removeClass("xuanzhong");
			$(this).addClass("xuanzhong");
			RecommendPlanList.activeObj = $(this).attr("data-order-column");
			RecommendPlanList.getRecommendPlanList();     //查询游轮列表数据
		});

		$(":input[name='startDate']").click(function() {
			$("#btn-startDate").remove();
			if (!$(this).val()) {
				if ($("#form-sum-condition").children().length == 0) {
					$(".clear-all-sel-label").hide();
				}
			} else {
				var routeBtn = '<button id="btn-startDate" type="button" data-name="startDate" onclick="RecommendPlanList.delCondition(this)">'+ $(this).val() +'</button>';
				$("#form-sum-condition").append(routeBtn);
				$(".clear-all-sel-label").show();
			}
			RecommendPlanList.getRecommendPlanList();     //查询游轮列表数据
		});
		$(":input[name='dayRange']").click(function() {
			$("#btn-dayRange").remove();
			if (!$(this).val()) {
				if ($("#form-sum-condition").children().length == 0) {
					$(".clear-all-sel-label").hide();
				}
			} else {
				var routeBtn = '<button id="btn-dayRange" type="button" data-name="dayRange" onclick="RecommendPlanList.delCondition(this)">'+ $(this).val() +'</button>';
				$("#form-sum-condition").append(routeBtn);
				$(".clear-all-sel-label").show();
			}
			RecommendPlanList.getRecommendPlanList();     //查询游轮列表数据
		});
	},
	delCondition: function(target) {

		if ($(target).attr("data-name") == "keyword") {
			$("#"+ $(target).attr("data-name") +"").val("");
		} else {
			$(":input[name='"+ $(target).attr("data-name") +"']:first").prop("checked", "checked");
		}
		$(target).remove();
		if ($("#form-sum-condition").children().length == 0) {
			$(".clear-all-sel-label").hide();
		}
		RecommendPlanList.getRecommendPlanList();     //查询游轮列表数据
	},

	createPager: function() {
		var options = {
			countUrl: "/yhypc/recommendPlan/getTotalPage.jhtml",
			resultCountFn: function (result) {
				return parseInt(result[0]);
			},
			pageRenderFn: function (pageNo, pageSize, data) {
				$('.allyouji').empty();
				//$("#loading").show();
				scroll(0, 0);
				data.pageIndex = pageNo;
				data.pageSize = pageSize;
				$.ajax({
					url: '/yhypc/recommendPlan/getRecommendPlanList.jhtml',
                    progress: true,
                    data: data,
                    success: function(data) {
                        $('.allyouji').empty();
                        $("#totalProduct").html(data.page.totalCount);
                        for (var i = 0; i < data.recommendPlanResponseList.length; i++) {
                            var s = data.recommendPlanResponseList[i];
                            if(parseInt(s.id) < 2000433){
                                s.shortComment = formatString(s.shortComment);
                            }
                            var desc = s.description;
                            if (desc.length > 180) {
                                s['description'] = desc.substring(0, 180)+ "...";
                            }
                            var coverPath = s.coverPath;
                            if (coverPath.indexOf("http") < 0) {
                                s['coverPath'] =  QINIU_BUCKET_URL + coverPath + "?imageView2/1/w/241/h/140/q/75";
                            }
                            var result = $(template("tpl-recommendPlan-list-item", s));
                            $('.allyouji').append(result);
                            result.data("cruiseship", s);
                        }
                    },
                    error: function() {}
                });
			}
		};
		RecommendPlanList.pager = new Pager(options);
	},
	getRecommendPlanList: function() {
		var search = {};

		if ($("#keyword").val()) {
			search['recommendPlanSearchRequest.name'] = $("#keyword").val();
		}

		if ($(":input[name='startDate']:checked").val()) {
			search['recommendPlanSearchRequest.monthRange[0]'] = $(":input[name='startDate']:checked").attr("data-start-month");
			search['recommendPlanSearchRequest.monthRange[1]'] = $(":input[name='startDate']:checked").attr("data-end-month");
		}
		if ($(":input[name='dayRange']:checked").val()) {
			search['recommendPlanSearchRequest.dayRange[0]'] = $(":input[name='dayRange']:checked").attr("data-start-day");
			search['recommendPlanSearchRequest.dayRange[1]'] = $(":input[name='dayRange']:checked").attr("data-end-day");
		}
		search['recommendPlanSearchRequest.orderColumn'] = $(".tuijianhaoping .xuanzhong").attr("data-order-column");
		search['recommendPlanSearchRequest.orderType'] = $(".tuijianhaoping .xuanzhong").attr("data-order-type");
		RecommendPlanList.pager.init(search);
	},
	IE8Css:function(){
		var explorer = window.navigator.userAgent ;
		if (explorer.indexOf("MSIE 8.0") >= 0) {
			$('.mask-bg').css({'filter':'Mask(Color=#f3f8fe)'});
		}
	}

};

$(document).ready(function(){
	RecommendPlanList.init();
});

	