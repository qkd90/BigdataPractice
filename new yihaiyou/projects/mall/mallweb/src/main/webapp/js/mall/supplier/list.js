var MallSupplier = {
	pageIndex : 1,
	totalCount : 1,
	conditionStr : "",
	init : function() {
		this.pageIndex = parseInt($("#pageIndex").text());
		this.totalCount = parseInt($("#totalCount").text());
		this.conditionStr = $("#conditionStr").text();
		this.initPage();
		this.initSelectedItems();
		this.searchBtn();
		this.initSelectedNav();
	},
	initSelectedNav : function(){
		$("#topnav a").removeClass('curr');
		$("#navSupplier").addClass('curr');
	},
	initSelectedItems : function(){
		var url = location.search;
		if(url.length > 0 && url.substring(0, 1) == '?'){
			url = url.substring(1, url.length);
			var items = url.split("&");
			for(var i = 0;i < items.length;i++){
				var item = items[i].split("=");
				if(item[1].length > 0){
					var name = item[0];
					var val = decodeURI(item[1]).replace("天","").replace("以上","");
					$("#no" + name).removeClass("curr");
					$("#" + name).find('a[key='+val+']').addClass("curr");
					if(name == 'supplierName'){
						$(".search-kw").val(val);
					}
				}
			}
		}
		
	},
	initPage : function() {
		$("#page-list").pagination(this.totalCount, {
			link_to : "javascript:;",
			items_per_page : 10,
			prev_text: "&laquo;上一页",
	        next_text: "下一页&raquo;",
			current_page : this.pageIndex,
			num_display_entries : 5,
			prev_show_always: false,
	        next_show_always: false,
			callback: function(pageIndex,panel){
				MallSupplier.searchBySelect(pageIndex);
			}
		});
	},
	initSelect : function() {
		this.citySelect();
		this.supplierTypeSelect();
		
	},

	citySelect : function() {
		$("#nocity").on("click", function() {
			$("#city").find("a[class='curr']").removeClass("curr");
			$("#nocity").addClass("curr");
			MallSupplier.searchBySelect(0);
		});
		$("#city").on("click", 'a', function() {
			$("#nocity").removeClass("curr");
			$("#city").find("a[class='curr']").removeClass("curr");
			$(this).addClass("curr");
			MallSupplier.searchBySelect(0);
		});
	},
	
	supplierTypeSelect : function() {
		$("#nosupplierType").on("click", function() {
			$("#supplierType").find("a[class='curr']").removeClass("curr");
			$("#nosupplierType").addClass("curr");
			MallSupplier.searchBySelect(0);
		});
		$("#supplierType").on("click", 'a', function() {
			$("#nosupplierType").removeClass("curr");
			$("#supplierType").find("a[class='curr']").removeClass("curr");
			$(this).addClass("curr");
			MallSupplier.searchBySelect(0);
		});
	},
	
	searchBtn : function() {
//		var ticketName = $(".search-kw").val();
		$(".search-bt").on("click", function() {
			MallSupplier.searchBySelect(0);
		});
		$(".search-kw").keydown(function(e){
			if (event.keyCode == 13) { 
				MallSupplier.searchBySelect(0);
			} 
		});
	},
	searchBySelect : function(page) {
		var city = $("#city").find("a[class='curr']").attr('key');
		var supplierType = $("#supplierType").find("a[class='curr']").attr('key');
		var supplierName = $(".search-kw").val();
		city = city == undefined?"":city;
		supplierType = supplierType == undefined?"":supplierType;
		MallSupplier.conditionStr = "";
		MallSupplier.conditionStr +='&supplierName=' + supplierName;
		MallSupplier.conditionStr +='&city='  + city;
		MallSupplier.conditionStr +='&supplierType='  + supplierType;
		$.get('/mall/supplier/searchSupplier.jhtml', {
			'pageIndex' : page,
			'supplierName' : supplierName,
			'city' : city,
			'supplierType' : supplierType,
		}, function(result) {
			$("#suppliersPanel").empty();
			var html = $("#supplierTmpl").render(result.units);
		    $("#suppliersPanel").append(html);
		    MallSupplier.pageIndex = page;
		    MallSupplier.totalCount = result.page.totalCount;
		    MallSupplier.initPage(pageIndex,totalCount);
		    MallSupplier.changeLink();
		});

	},
	changeLink : function(){
		var json={time:new Date().getTime()};  
		window.history.pushState(json,"",'/mall/supplier/list.jhtml?pageIndex=' + MallSupplier.pageIndex + MallSupplier.conditionStr);
	},
	makePagination : function(total) {
		$("#page-list").pagination(total, {
			link_to : "javascript:;",
			items_per_page : 10,
			current_page : 0,
			num_display_entries : 5,
			callback : MallSupplier.searchBySelect
		});
	}

}

$(function() {
	MallSupplier.init();
	MallSupplier.initSelect();
//	MallSupplier.initSearch();
//	MallSupplier.searchBtn();
})
