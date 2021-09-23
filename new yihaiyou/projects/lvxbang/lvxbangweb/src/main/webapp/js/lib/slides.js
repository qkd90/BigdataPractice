function createSlide(slidesId,paginationId){


	var $slide = $(slidesId)
	

	var numpic = $(' li',$slide).size()-1;
	var nownow = 0;
	var inout = 0;
	var TT = 0;
	var SPEED = 5000;


	$('li',$slide).eq(0).siblings('li').css({'display':'none'});


	var ulstart = '<p id="'+paginationId+'">',
		ulcontent = '',
		ulend = '</p>';
	ADDLI();
	var pagination = $('#'+paginationId+' span');
	var paginationwidth = $('#'+paginationId).width();
	pagination.eq(0).addClass('current')
		
	function ADDLI(){
		//var lilicount = numpic + 1;
		for(var i = 0; i <= numpic; i++){
			ulcontent += '<span></span>';
		}
		
		$slide.after(ulstart + ulcontent + ulend);	
	}

	pagination.on('click',DOTCHANGE)
	
	function DOTCHANGE(){
		
		var changenow = $(this).index();
		
		$('li',$slide).eq(nownow).css('z-index','900');
		$('li',$slide).eq(changenow).css({'z-index':'800'}).show();
		pagination.eq(changenow).addClass('current').siblings('span').removeClass('current');
		$('li',$slide).eq(nownow).fadeOut(400,function(){$('li',$slide).eq(changenow).fadeIn(500);});
		nownow = changenow;
	}
	
	pagination.mouseenter(function(){
		inout = 1;
	})
	
	pagination.mouseleave(function(){
		inout = 0;
	})
	
	function GOGO(){
		
		var NN = nownow+1;
		
		if( inout == 1 ){
			} else {
			if(nownow < numpic){
			$('li',$slide).eq(nownow).css('z-index','900');
			$('li',$slide).eq(NN).css({'z-index':'800'}).show();
			pagination.eq(NN).addClass('current').siblings('span').removeClass('current');
			$('li',$slide).eq(nownow).fadeOut(400,function(){$('li',$slide).eq(NN).fadeIn(500);});
			nownow += 1;

		}else{
			NN = 0;
			$('li',$slide).eq(nownow).css('z-index','900');
			$('li',$slide).eq(NN).stop(true,true).css({'z-index':'800'}).show();
			$('li',$slide).eq(nownow).fadeOut(400,function(){$('li',$slide).eq(0).fadeIn(500);});
			pagination.eq(NN).addClass('current').siblings('span').removeClass('current');

			nownow=0;

			}
		}
		TT = setTimeout(GOGO, SPEED);
	}
	
	TT = setTimeout(GOGO, SPEED); 
}

$(function(){
	createSlide('#slides','1stPagination')

	createSlide('#slides2','2stPagination')
})