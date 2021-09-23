$(function(){
	  roll();
	  stopmain('.index_lunbo');
	  stoproll('.index_Qget span');
	  })

  var timer;
  var i=-1;
  var offset=3000;
  function slide(i){
	  $('.index_lunbo a').eq(i).fadeIn().siblings().hide();
	  $('.index_Qget span').eq(i).siblings().addClass('gty_white');
	  $('.index_Qget span').eq(i).siblings().removeClass('gty_on');
	  $('.index_Qget span').eq(i).removeClass('gty_white');
	  $('.index_Qget span').eq(i).addClass('gty_on');
	  }
  function roll(){
	  i++;
	  if(i>4){
		  i=0;
		  }
		  slide(i);
		  timer=setTimeout(roll,offset);
	  }
  function stopmain(id){
	  $(id).hover(function(){
		  clearTimeout(timer);
		             },function(){
			  timer=setTimeout(roll,offset);
			  })
	  }
  function stoproll(id){
	  $(id).hover(function(){
		  clearTimeout(timer);
		     i=$(this).index();
		      slide(i);
		                 },function(){
			  timer=setTimeout(roll,offset);
			  })
	  }
	
	  