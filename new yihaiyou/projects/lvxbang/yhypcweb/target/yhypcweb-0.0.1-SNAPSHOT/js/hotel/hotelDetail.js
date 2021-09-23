$(window).ready(function(){
	//collection();
	searchSelect();
	aroundHotel();
	commendNum();
	photoSlide();
	photo();
	allPicture();
	judge('#serve p','#serve img');
	judge('#policy p','#policy img');
	//commendjudge();
	attentionbox();
});

//收藏
function collection(){
	var k = 0;
	$('#collection').click(function(){
		if(k == 0){
			$('.describ .love').addClass('collection');
			k = 1;
		} else {
			$('.describ .love').removeClass('collection');
			k = 0;
		}	
	})
}

// 房型，酒店信息，评价，交通
function searchSelect() {
	var Btn = $('.listLeft .list_head span');
	Btn.click(function () {
		$(this).addClass('li_he_active');
		$(this).siblings().removeClass('li_he_active');
        var top = 0;
		if ($(this).hasClass('room-type')) {
            top = $('.bookDate').offset().top - 20;
		} else if ($(this).hasClass('hotel-info')) {
            top = $('.hotelMessage').offset().top - 20;
        } else if ($(this).hasClass('hotel_comment')) {
            top = $('.commendMessage').offset().top - 20;
        } else if ($(this).hasClass('hotel-geo')) {
            top = $('.traffic').offset().top - 20;
        }
        $('body,html').animate({scrollTop: top}, 100);
	});
}

//周边住宿
function aroundHotel(){
	var thisHotel = $('.aroundList ul li');
		thisHotel.hover(function(){
			$(this).css({'backgroundColor':'#f7f7f7'});
			$(this).find('.name').css({'color':'#3996fa','fontWeight':'600'});
		},function(){
			$(this).css({'backgroundColor':'#fff'});
			$(this).find('.name').css({'color':'#000','fontWeight':'500'})
		})
}

//评论个数
function commendNum(){
	if($('.commendBox .oneCommend').length > 1){
		$('.commendBox .oneCommend:last-child').css({'borderBottom':'none'});
	}
}

//相册
function photo() {
	var li = $('.pic_min li');
	var ul_width = li.width() * li.length + 10 * li.length;
	var left = $('.t_left');
	var right = $('.t_right');
	$('.pic_min ul').css({'width': ul_width});
	right.on("click", function () {
		var pos_left = $('.pic_min ul').css('left');//第一个小图片距离左部的距离
		var pos_x = -(parseInt(pos_left) - 25) / li.width() + 1;//小图当前第一个的index
		var pos_group = -(parseInt(pos_left) - 25) / ((li.width() + 10) * 5) + 1;//组数
		var onepage = -(li.width() + 10) * 5 * pos_group + 25;
		if (li.length - pos_x >= 5) {
			$('.pic_min ul').animate({'left': onepage}, 100);
		}
	});
	left.on("click", function () {
		var pos_left = $('.pic_min ul').css('left');
		var pos_x = -(parseInt(pos_left) - 25) / (li.width() + 10) + 1;//小图当前第一个的index
		//var pos_group = Math.ceil((li.length - pos_x) / 5);
		var onepage = parseInt(pos_left) + (li.width() + 10) * 5;
		if (pos_x - 5 != 1 && pos_x - 5 > 0) {
			$('.pic_min ul').animate({'left': onepage}, 100);
		} else {
			$('.pic_min ul').animate({'left': '25px'}, 100);
		}
	});
}

//相册
function photoSlide() {
	var timer;
	var i = -1;
	var offset = 5000;
	$(function () {
		roll();
		stopmax();
		stopmin();
	});
	function slider(i) {
		$('.pic_max img').eq(i).fadeIn().siblings().hide();
		$('.pic_min ul li').eq(i - 1).siblings().removeClass('on');
		$('.pic_min ul li').eq(i - 1).addClass('on');
		var groupId = Math.floor((i - 1) / 5);
		var ulleft = - groupId * 610 +25;
		if((i - 1) % 5 == 0){
			$('.pic_min ul').animate({'left': ulleft}, 100);
		}
	}

	function roll() {
		var long = $('.pic_min li').length;
		i++;
		if (i > long) {
			i = 0;
		}
		slider(i);
		timer = setTimeout(roll, offset);
	}

	function stopmax() {
		$('.pic_max img').hover(function () {
			clearTimeout(timer);
		}, function () {
			timer = setTimeout(roll, offset);
		});
	}

	function stopmin() {
		$('.pic_min ul li').hover(function () {
			clearTimeout(timer);
			i = $(this).index();
			slider(i + 1);
		}, function () {
			timer = setTimeout(roll, offset);
		});
	}
}

function centerpic(s_top){
	var left = (window.screen.availWidth - $('.out_picbox').width())/2;
	var top = (window.screen.availHeight - $('.out_picbox').height())/2 + s_top;
    $('.out_picbox').css({'left':left,'top':top});
}

function allPicture(){
	$('.takeall ').on('click',function(){
		var scrolltop = $(window).scrollTop();
		$('.opcity9').show();
		$('.out_picbox').show();
		$('body').css({'overflow':'hidden'});
		centerpic(scrolltop);
	});
	$('.closeArea').on('click',function(){
		$('.opcity9').hide();
		$('.out_picbox').hide();
		$('body').css({'overflow':'auto'});
	});
}

function judge(judgebox,image){
	var contain = $(judgebox).length;
	if(contain == 0){
		$(image).show();
	}else{
		$(image).hide();
	}
}

function commendjudge(){
	var contain = $('.commendBox .oneCommend').length;
	if(contain == 0){
		$('.commendBox').hide();
	}else{

	}
}

function attentionbox() {
	var tag = 'li .room_state .paytype';
	var parent = $('#hotel_price_content');
        parent.on("mouseover", tag , function () {
			var type = $(this).html();
				if(type == '到付'){
					$(this).parent().find('.daofu').show();
				}else if(type == '预付'){
					$(this).parent().find('.yufu').show();
				}else{
					$(this).parent().find('.danbao').show();
				}
		});
		parent.delegate(tag, "mouseout", function () {
			$('.at_word').hide();
		});
}