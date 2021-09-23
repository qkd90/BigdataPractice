$(window).ready(function(){
	titleSelect('.hotBrand .brandTitle span','active');
    positionSelect('.dirbox .position .positoncontain li', 'radioCheck');
    starSelect('.dirbox .level .levelcontain li', 'radioCheck');
	//level_serveSelect('.dirbox .levelcontain li','checktrue','.lev','limited','unlimited');//星级
	level_serveSelect('.dirbox .serve .limitlist li','checktrue','.ser','limited','unlimited');//服务
	priceSelect('.dirbox .price .pricecontain li','radioCheck');
    brandSelect('.dirbox .brand .brandtain li', 'radioCheck');
	limited();
	//secondListTurn('.position .limitlist span','.position .secondList','.pos');//位置二级目录转换
	//secondListTurn('.brand .limitlist span','.brand .secondList','.bra');//品牌二级目录转换
	openMore('#posMore','.position .seccontai','.position .openMore span','.position .openMore i',25);//位置二级目录展开
	openMore('#braMore','.brand .seccontai','.brand .openMore span','.brand .openMore i',25);//品牌二级目录展开
	openMore('#serMore','.servebox .servecontain','.servebox .takeMore span','.servebox .takeMore i',70);//服务更多
	//secondListSelected('.dirbox .position .secondList .seccontai li i');
	//secondListSelected('.dirbox .brand .secondList .seccontai li i');
	//pos_braLimited('.pos','.position .secondList','.position .secondList:eq(0)','.position .limitlist span','.pos_span');//位置选择
	//pos_braLimited('.bra','.brand .secondList','.brand .secondList:eq(0)','.brand .limitlist span','.bra_span');//品牌选择
	searchSelect();//编辑，好评、价格排序
	mapstick();//地图固定
	browser();
})


// 热门选择
function titleSelect(btn,pattern) {
	var Btn = $(btn);
		Btn.on('click', function(){
			$(this).addClass(pattern);
			$(this).siblings().removeClass(pattern);
		})
}

// 是否不限
function limited() {
	var checkBox = $('.dirbox .line .limit span');
		checkBox.on('click', function(){
			if($(this).hasClass('unlimited')){
				$(this).removeClass('unlimited');
                if ($(this).hasClass('pos')) {
                    $('.dirbox .position ul.positoncontain li.radio').removeClass('radioCheck');
                } else if ($(this).hasClass('lev')) {
                    $('.dirbox .level ul.levelcontain li.radio').removeClass('radioCheck');
                } else if ($(this).hasClass('pri')) {
                    $('#custom_min_price').val(null);
                    $('#custom_max_price').val(null);
                    $('.dirbox .price ul.pricecontain li.radio').removeClass('radioCheck');
                } else if ($(this).hasClass('bra')) {
                    $('.dirbox .brand ul.brandtain li.radio').removeClass('radioCheck');
                } else if ($(this).hasClass('ser')) {
                    $('.dirbox .serve ul.servecontain li.checkbox').removeClass('checktrue');
                }
			} else {
				//$(this).addClass('unlimited');
			};
			//if($('.lev').hasClass('limited')){
			//	$(this).parent().siblings().find('.checkbox').removeClass('checktrue');
			//};
			//if($('.pri').hasClass('limited')){
             //   $('#custom_min_price').val(null);
             //   $('#custom_max_price').val(null);
			//	$(this).parent().siblings().find('.radio').removeClass('radioCheck');
			//};
			//if($('.ser').hasClass('limited')){
			//	$('.dirbox .serve .limitlist li').removeClass('checktrue');
			//}
		})
}


//星级/服务选择
function level_serveSelect(btn, pattern, tag, fir, end) {
    var Btn = $(btn);
    $(document).on('click', btn, function () {
        if ($(tag).hasClass(fir)) {
            $(tag).addClass(end);
        }
        if ($(this).hasClass(pattern)) {
            $(this).removeClass(pattern);
            if ($(this).siblings("." + pattern).length == 0) {
                $(tag).removeClass(end);
            }
        } else {
            $(this).addClass(pattern);
        }
    })
}
// 商圈选择
function positionSelect(btn, pattern) {
    var Btn = $(btn);
    $(document).on('click', btn, function () {
        if($('.pos').hasClass('limited')){
            $('.pos').addClass('unlimited');
        }
        $(this).addClass(pattern);
        $(this).siblings().removeClass(pattern)
    });

}
// 星级选择
function starSelect(btn, pattern) {
    var Btn = $(btn);
    $(document).on('click', btn, function () {
        if($('.lev').hasClass('limited')){
            $('.lev').addClass('unlimited');
        }
        $(this).addClass(pattern);
        $(this).siblings().removeClass(pattern)
    });
}
//房价选择
function priceSelect(btn,pattern) {
	var Btn = $(btn);
	$(document).on('click', btn, function () {
		if ($('.pri').hasClass('limited')) {
			$('.pri').addClass('unlimited');
		}
		$(this).addClass(pattern);
		$(this).siblings().removeClass(pattern)
	});
}
// 品牌选择
function brandSelect(btn,pattern) {
    var Btn = $(btn);
    $(document).on('click', btn, function () {
        if($('.bra').hasClass('limited')){
            $('.bra').addClass('unlimited');
        }
        $(this).addClass(pattern);
        $(this).siblings().removeClass(pattern)
    });
}

//位置/品牌不限
function pos_braLimited(btn,allList,firstList,alist,firstspan){
	var checkBox = $(btn);
		checkBox.on('click', function(){
			if($(this).hasClass('unlimited')){
				$(firstList).css({'display':'block'});
				$(firstspan).addClass('pos_active');
			}else{
				$(allList).css({'display':'none'});
				$(alist).removeClass('pos_active');
				$(this).parent().siblings().find('i').removeClass('up');
			};
		});
}


//位置选择
function secondListTurn(firSpan,secList,limitSpan) {
	var Btn = $(firSpan);
		Btn.on('click', function(){
			var num = $(this).index();
			var thisContain = $(secList).eq(num);
				$(this).addClass('pos_active');
				$(this).find('i').addClass('up');
				$(this).siblings().removeClass('pos_active');
				$(this).siblings().find('i').removeClass('up');
				$(secList).hide();
				thisContain.show();
			if($(limitSpan).hasClass('unlimited')){
		
			}else{
				$(this).parent().parent().find(limitSpan).addClass('unlimited');
			}
		})
}

//二级目录展开
function openMore(btn,contain,span,i,height) {
	var k = 0;
	var high = height;
	$(btn).on('click', function(){
		if(k == 0){
			$(contain).css({'overflow':'auto','height':'auto'});
			$(i).addClass('up');
			$(span).text('收起');
			k = 1;
		} else {
			$(contain).css({'overflow':'hidden','height':high});
			$(i).removeClass('up');
			$(span).text('展开');
			k = 0;
		}
		
	})
}

//二级目录选择
function secondListSelected(btn){
	var now = $(btn);
		now.on('click', function(){
				$(this).addClass('radioCheck');
				$(this).parent().siblings().find('i').removeClass('radioCheck');
		})
}

// 编辑推荐，好评，价格
function searchSelect() {
	var Btn = $('.searchingList .list_head span');
		Btn.on('click', function() {
			var name = $('#searchWord').val();
			if (name && name != "") {
				return;
			}
            if ($(this).hasClass('li_he_active')) {
                if ($(this).index() == 0) {
                    $(this).removeClass('to-order');
                    return;
                }
                if ($(this).hasClass('ace_white')) {
                    $(this).removeClass('ace');
                    $(this).removeClass('ace_white');
                    $(this).addClass('dace');
                    $(this).addClass('dace_white');
                } else if ($(this).hasClass('dace_white')) {
                    $(this).removeClass('dace_white');
                    $(this).removeClass('dace');
                    $(this).addClass('ace');
                    $(this).addClass('ace_white');
                }
            } else {
                if ($(this).index() == 0) {
                    $(this).addClass('to-order');
                }
                $(this).addClass('li_he_active');
                $(this).siblings().removeClass('li_he_active');
                $(this).siblings().removeClass('dace_white');
                $(this).siblings().removeClass('ace_white');
                if($(this).hasClass('ace')){
                	$(this).addClass('ace_white');
                }
                if($(this).hasClass('dace')){
                	$(this).addClass('dace_white');
                }
            }
		});
}

//地图固定
/*function mapstick(){
	//var mapTop = $('.searchingList .listRight img ').position().top;
	var mapTop = $('.searchingList .listRight').position().top;
	var left = ($(window).width() - 1200)/2 + 780;
		$(window).scroll(function(){
			if ($('body').scrollTop() > mapTop) {
				if (($('body').height() - $('body').scrollTop()) > 800) {
					$('.searchingList .listRight').css({'position': 'fixed', 'top': 0, 'left': left,'margin-top': 15});
				} else {
					$('.searchingList .listRight').css({'position': 'fixed', 'top': -213, 'left': left,'margin-top': 15});
				}
			} else {
				$('.searchingList .listRight').css({'position':'static', 'margin-top': 0});
			}
		})
}*/
function mapstick() {
	/*var mapTop = $('.searchingList .listRight').position().top;*/
	/*var left = ($(window).width() - 1200) / 2 + 780;*/
	var minTop = $('.searchingList').offset().top;
	$(window).scroll(function () {
		var scrollTop = $(document).scrollTop();
		if (scrollTop > minTop) {
			/*$('.searchingList .listRight').css({'margin': 'fixed', 'top': 0, 'left': left,'margin-top': 15});*/
			$('.searchingList .listRight').css({marginTop:scrollTop-minTop + 20});
		} else if(scrollTop < minTop){
			$('.searchingList .listRight').css({marginTop:0});
		}
	});
}

function browser(){
	var browser = window.navigator;
	var hotelIndex = $('#list_content');
	if (browser.userAgent.indexOf("IE") >= 0) {
		hotelIndex.addClass('verticalMiddle');
	}
}
