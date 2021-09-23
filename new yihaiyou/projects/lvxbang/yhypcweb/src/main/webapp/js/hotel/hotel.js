$(window).ready(function(){
	advertBox();
	titleSelect('.hotBrand .brandTitle span','active');
	searchSelect();//编辑，好评、价格排序
	titleSelectParent('.dirbox .levelcontain li','checktrue','.lev','limited','unlimited');//星级
	titleSelectParent('.dirbox .serve .limitlist li','checktrue','.ser','limited','unlimited');//服务
	titleSelect_sigle('.dirbox .price .pricecontain li','radioCheck');
	turnround();
	haveLook();
	limited();
	openMore();
	secondListSelected('.dirbox .position .secondList .seccontai li i');
	secondListSelected('.dirbox .brand .secondList .seccontai li i');
	posfir('.dirbox .position .limit span','.position .limitlist span:eq(0)');
	posfir('.dirbox .brand .limit span','.brand .limitlist span:eq(0)');
	turnroundBrand();
	takeMore();
	//mapstick();
	level('.dirbox .pos','.dirbox .position .secondList:eq(0)','.dirbox .position .secondList','.dirbox .position .limitlist span');//位置选择
	level('.dirbox .bra','.dirbox .brand .secondList:eq(0)','.dirbox .brand .secondList','.dirbox .brand .limitlist span');//品牌选择
	//collection();
})

function advertBox() {
	var bleft = $('.selectBar').position().left;
	$('.outadvertBox').css({'left':bleft});
	window.onresize=function(){
		var bleft = $('.selectBar').position().left;
		$('.outadvertBox').css({'left':bleft});
	}
}

// 热门选择
function titleSelect(btn,pattern) {
    // hot hotel sel
    var sel_region = $('#region_sel span.active').attr('data-region-id');
    $('#hot_hotel_area ul').hide();
    if (sel_region) {
        $('#hot_hotel_area ul[data-region-id=' + sel_region + ']').show();
    }
	var Btn = $(btn);
		Btn.click(function(){
            var sel_region = $(this).attr('data-region-id');
            if (sel_region) {
                $('#hot_hotel_area ul').hide();
                $('#hot_hotel_area ul[data-region-id=' + sel_region + "]").show();
            }
			$(this).addClass(pattern);
			$(this).siblings().removeClass(pattern);
		});
}

//星级选择
function titleSelectParent(btn,pattern,tag,fir,end) {
	var Btn = $(btn);
		Btn.click(function(){
			if($(tag).hasClass(fir)){
				$(tag).addClass(end);
			}
			if($(this).hasClass(pattern)){
				$(this).removeClass(pattern);
			}else{
				$(this).addClass(pattern);
			}	
		})
}

//房价选择
function titleSelect_sigle(btn,pattern) {
	var Btn = $(btn);
		Btn.click(function(){
			if($('.pri').hasClass('limited')){
				$('.pri').addClass('unlimited');
			}
			$(this).addClass(pattern);
			$(this).siblings().removeClass(pattern)
		})
}

// 立即查看
function haveLook() {
	var now = $('.hotBrand .hotelList ul li');
		now.hover(function(){
			//$(this).append(shadowBox);
            $(this).find('div.shadow').show();
			$(this).find('.roomMess').animate({'bottom':'0'},50);
		},function(){
			$(this).find('.shadow').hide();
			$(this).find('.roomMess').animate({'bottom':'-30px'},10)
		});
}

//是否不限
function limited() {
	var checkBox = $('.dirbox .line .limit span');
		checkBox.click(function(){
			if($(this).hasClass('unlimited')){
				$(this).removeClass('unlimited');
				$('.dirbox .position .limitlist span').removeClass('pos_active');
			} else {
				$(this).addClass('unlimited');
			};
			if($('.lev').hasClass('limited')){
				$(this).parent().siblings().find('.checkbox').removeClass('checktrue');
			};
			if($('.pri').hasClass('limited')){
				$(this).parent().siblings().find('.radio').removeClass('radioCheck');
			};
			if($('.ser').hasClass('limited')){
				$('.dirbox .serve .limitlist li').removeClass('checktrue');
			}
		})
}


//位置/品牌选择
function level(btn,secOne,secLine,alist){
	var checkBox = $(btn);
		checkBox.click(function(){
			if($(this).hasClass('unlimited')){
				$(secOne).css({'display':'block'});
			}else{
				$(secLine).css({'display':'none'});
				$(alist).removeClass('pos_active');
				$(this).parent().siblings().find('i').removeClass('up');
			}
		})
}

function posfir(btn,tag){
	$(btn).click(function(){
		if($(this).hasClass('unlimited')){
		$(tag).addClass('pos_active');
		}
	})
}

//二级目录展开
function openMore() {
	var k = 0;
	$('#openMore').click(function(){
		if(k == 0){
			$('.dirbox .secondList .seccontai').css({'overflow':'auto','height':'auto'});
			$('.dirbox .position .secondList .openMore i').addClass('up');
			$('.dirbox .position .secondList .openMore span').text('收起');
			k = 1;
		} else {
			$('.dirbox .secondList .seccontai').css({'overflow':'hidden','height':'25px'});
			$('.dirbox .position .secondList .openMore i').removeClass('up');
			$('.dirbox .position .secondList .openMore span').text('展开');
			k = 0;
		}
		
	})
}

//更多
function takeMore() {
	var k = 0;
	$('#takeMore').click(function(){
		if(k == 0){
			$('.limitlist .servebox .servecontain').css({'overflow':'auto','height':'auto'});
			$('.limitlist .servebox .takeMore i').addClass('up');
			$('.limitlist .servebox .takeMore span').text('收起');
			k = 1;
		} else {
			$('.limitlist .servebox .servecontain').css({'overflow':'hidden','height':'70px'});
			$('.limitlist .servebox .takeMore i').removeClass('up');
			$('.limitlist .servebox .takeMore span').text('跟多');
			k = 0;
		}
		
	})
}

//二级目录选择
function secondListSelected(btn){
	var now = $(btn);
		now.click(function(){
				$(this).addClass('radioCheck');
				$(this).parent().siblings().find('i').removeClass('radioCheck');
		})
}

//位置选择
function turnround() {
	var Btn = $('.dirbox .position .limitlist span');
		Btn.click(function(){
			if($('.dirbox .position .limit span').hasClass('unlimited')){
		
			}else{
				$(this).parent().parent().siblings().find('.limit span').removeClass('unlimited');
				$(this).parent().parent().find('.limit span').addClass('unlimited');
			}
			var num = $(this).index();
			var thisContain = $('.dirbox .position .secondList').eq(num);
				$(this).addClass('pos_active');
				$(this).find('i').addClass('up');
				$(this).siblings().removeClass('pos_active');
				$(this).siblings().find('i').removeClass('up');
				$('.dirbox .position .secondList').hide();
				thisContain.show();
		})
}

//品牌选择
function turnroundBrand() {
	var Btn = $('.dirbox .brand .limitlist span');
		Btn.click(function(){
			if($('.dirbox .brand .limit span').hasClass('unlimited')){
		
			}else{
				// $(this).parent().parent().siblings().find('.limit span').removeClass('unlimited');
				$(this).parent().parent().find('.limit span').addClass('unlimited');
			}
			var num = $(this).index();
			var thisContain = $('.dirbox .brand .secondList').eq(num);
				$(this).addClass('pos_active');
				$(this).find('i').addClass('up');
				$(this).siblings().removeClass('pos_active');
				$(this).siblings().find('i').removeClass('up');
				$('.dirbox .brand .secondList').hide();
				thisContain.show();
		})
}

// 编辑推荐，好评，价格
function searchSelect() {
	var Btn = $('.searchingList .list_head span');
		Btn.click(function(){
			$(this).addClass('li_he_active');
			$(this).siblings().removeClass('li_he_active');
			$(this).siblings().removeClass('dace_white');
			$(this).siblings().removeClass('ace_white');
			if($(this).hasClass('ace')){
				$(this).addClass('ace_white');			
			};
			if($(this).hasClass('dace')){
				$(this).addClass('dace_white');			
			}
		})
}

//地图固定
//function mapstick(){
//	var mapTop = $('.searchingList .listRight img ').position().top;
//	var left = ($(window).width() - 1200)/2 + 780;
//		$(window).scroll(function(){
//			if($('body').scrollTop() > mapTop){
//				$('.searchingList .listRight').css({'position':'fixed','top':0,'left':left});
//			} else {
//				$('.searchingList .listRight').css({'position':'static'});
//			}
//		})
//}

