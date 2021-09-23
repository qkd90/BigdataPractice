
  var timer;
  var i=-1;
  var offset=3000;
  function slide(i){
	  $('.gty_main a').eq(i).fadeIn().siblings().hide();
	  $('.gty_get span').eq(i).siblings().addClass('gty_white');
	  $('.gty_get span').eq(i).siblings().removeClass('gty_on');
	  $('.gty_get span').eq(i).removeClass('gty_white');
	  $('.gty_get span').eq(i).addClass('gty_on');
	  }
  function roll(){
	  i++;
	  if(i>4){
		  i=0;
		  }
		  slide(i);
		  timer=setTimeout(roll,offset);
	  }
  function stopmain(){
	  $('.gty_main').hover(function(){
		  clearTimeout(timer);
		             },function(){
			  timer=setTimeout(roll,offset);
			  })
	  }
  function stoproll(){
	  $('.gty_get span').hover(function(){
		  clearTimeout(timer);
		     i=$(this).index();
		      slide(i);
		                 },function(){
			  timer=setTimeout(roll,offset);
			  })
	  }
	  $(function(){
	  roll();
	  stopmain();
	  stoproll();
	  })