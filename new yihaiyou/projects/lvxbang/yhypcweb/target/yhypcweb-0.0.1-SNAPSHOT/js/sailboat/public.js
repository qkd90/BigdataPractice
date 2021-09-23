$(window).ready(function() {
	pullDown('#myyihaiyou','.myyihaiyou');
	pullDown('#customService','.customService');
	rightBar();
	rightMove();
});

//头部下拉菜单
function pullDown(btn,tag) {
	$(btn).click(function(event){
		event.stopPropagation(); 
		$(this).siblings().find('ul').hide();
		$(tag).slideDown(300);
	});
	$('body').click(function(){
		$(tag).slideUp(300);
	})
}


//头部列表切换
 function titleList() {
	 var Li = $('.selectBar .nav ul li');
	 var classtag = ["Index", "DIY","hotel","boatTicket","sailBoat","scenic","cruise","food","guide"," travel"];
	 	$.each(classtag, function(id, element){
			if($('body').hasClass(element)){
				 Li.removeClass('NavList');
				 Li.eq(id).addClass('NavList');
			}
		});
 }

 // 侧栏
 function rightBar() {
 	var winHeight = $(window).height();
 		$('.rightBar').css({'height':winHeight})
 }

 function rightMove() {
 	var Li = $('.rightBar ul li');
 		Li.hover(function(){
 			$(this).addClass('active');
 			$(this).animate({'width':'120px'},100);
 			$(this).find('span').animate({'opacity':'1'},100,function(){
 				$(this).parent().addClass('active');
 			});
 		},function(){
 			$(this).find('span').animate({'opacity':'0'},100,function(){
 				$(this).parent().removeClass('active')
 			});
 			$(this).animate({'width':'50px'},100);

 		}
 		);
 }