$(document).ready(function() {
    moreCity();
	travalStyle();
	travalPosition();
	superLine();
	aroundLine();
	outLine();
	homeLine();
});


//热门目的更多选项功能
function moreCity(){
	var More=$('#more_city');
	var k=0;
	    More.click(function(){
			if(k==0){
				$('.morechoose').show();
				More.html('收起');
				k=1;
				}else{
					$('.morechoose').hide();
					More.html('更多');
					k=0;
					}
			
			});
	}
//跟团形式选择（周边跟团，出境跟团，国内跟团）
function travalStyle(){
	var Li=$('.tabs li');
	    Li.click(function(){
			var now=$(this).hasClass('active_green');
			var num=$(this).index();
			var thisUl=$('.sub-tabs .thin-border').eq(num);
			var thisDiv=$('.gentuan_line').eq(num);
			if(now==false){
				Li.removeClass('active_green');
				$(this).addClass('active_green');
				$('.sub-tabs .thin-border').hide();
				$('.gentuan_line').hide();
				thisUl.show();
				thisDiv.show();
				}
			})
	}
//跟团形式选择||固定栏 
function travalPosition(){
	var tr_distanceTop=$('#tr_list').offset().top-45;
	var tr_distanceBottom=$('.sub-tabs').offset().top;
	var tr_window=$(window);
	    tr_window.scroll(function(){
	var tr_scrlloHeight=tr_window.scrollTop();
		if(tr_scrlloHeight>tr_distanceTop){
			$('#tr_list').css({'position':'fixed','left':0,'top':'45px','width':'100%','z-index':'999','background-color':'white'});
			$('.sub-tabs').css({'position':'fixed','left':0,'top':'90px','z-index':'999','width':'100%'});
			}else{
				$('#tr_list').css({'position':'static'});
				$('.sub-tabs').css({'position':'static'});
				}
			})
	}
//牛人专线地方选择	
function superLine(){
	var Li=$('.super_line li');
	var sDiv=$('.super_line li div');
	    Li.click(function(){
			var now=$(this.firstChild).hasClass('super_bg');
			var num=$(this).index();
			var thisUl=$('.super_place').eq(num);
			if(now==false){
				sDiv.removeClass('super_bg');
				sDiv.addClass('each_bg');
				$(this.firstChild).removeClass('each_bg');
				$(this.firstChild).addClass('super_bg');
				$('.super_place').hide();
				thisUl.show();
				}
			})
	}	
//周边跟团线时间选择	
function aroundLine(){
	var Li=$('.day_line li');
	var sDiv=$('.day_line li div');
	    Li.click(function(){
			var now=$(this.firstChild).hasClass('super_bg');
			var num=$(this).index();
			var thisUl=$('.around_place').eq(num);
			if(now==false){
				sDiv.removeClass('super_bg');
				sDiv.addClass('each_bg');
				$(this.firstChild).removeClass('each_bg');
				$(this.firstChild).addClass('super_bg');
				$('.around_place').hide();
				thisUl.show();
				}
			})
	}
//出境跟团线路线选择	
function outLine(){
	var Li=$('.out_line li');
	var sDiv=$('.out_line li div');
	    Li.click(function(){
			var now=$(this.firstChild).hasClass('super_bg');
			var num=$(this).index();
			var thisUl=$('.out_place').eq(num);
			if(now==false){
				sDiv.removeClass('super_bg');
				sDiv.addClass('each_bg');
				$(this.firstChild).removeClass('each_bg');
				$(this.firstChild).addClass('super_bg');
				$('.out_place').hide();
				thisUl.show();
				}
			})
	}
//国内跟团线路线选择	
function homeLine(){
	var Li=$('.home_line li');
	var sDiv=$('.home_line li div');
	    Li.click(function(){
			var now=$(this.firstChild).hasClass('super_bg');
			var num=$(this).index();
			var thisUl=$('.home_place').eq(num);
			if(now==false){
				sDiv.removeClass('super_bg');
				sDiv.addClass('each_bg');
				$(this.firstChild).removeClass('each_bg');
				$(this.firstChild).addClass('super_bg');
				$('.home_place').hide();
				thisUl.show();
				}
			})
	}
	
	
	
	