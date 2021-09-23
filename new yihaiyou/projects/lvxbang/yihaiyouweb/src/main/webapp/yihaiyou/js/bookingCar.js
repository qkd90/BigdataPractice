//$(document).ready(function(){
//车型选择
//var lis=$('.chosewant .selcar ul li');
//    lis.click(function(){
//    	var num=$(this).index();
//    	var p_fir=$('.chosewant .selcar ul li p:first-child').eq(num);
//    	          $('.chosewant .selcar ul li p:first-child').removeClass('bgpx');
//    	          $('.chosewant .selcar ul li p:first-child').addClass('bgp');
//    	    p_fir.addClass('bgpx')
//    })
//下车地点选择
//var down=$('#get_off');
//    down.click(function(){
//    	createshadow();
//    	$('body').css({'overflow':'hidden'});
//    	$('.car_off').show();
//    })
//var lis2=$('.tagplace ul li');
//    lis2.click(function(){
//    	$('.tagplace ul li').removeClass('tagbgx');
//    	$('.tagplace ul li').addClass('tagbg');
//    	$(this).addClass('tagbgx');
//    	$('.shadow').remove();
//    	$('.car_off').hide();
//    })
//    $(".bk_main").delegate(".shadow", "click", function() {
//    	$('.shadow').remove();
//    	$('.car_off').hide();
//    })
//取消用车
var cons = $('#cansolecar');
cons.click(function () {
    var Wwindow = $(window).width();
    var Wfor = $('.forcans').width();
    var lef = (Wwindow - Wfor) / 2;
    createshadow();
    $('body').css({'overflow': 'hidden'});
    $('.forcans').show();
    $('.forcans').css({'position': 'fixed', 'top': '200px', 'left': lef, 'z-index': '11'});
});
var yes = $('#yesB');
var no = $('#noB');
yes.click(function () {
    $('.shadow').remove();
    $('.forcans').hide();
})
no.click(function () {
    $('.shadow').remove();
    $('.forcans').hide();
})
//
var sheight = $(window).height()
$('#shadowbox').css({'height': sheight});


//})

function createshadow() {
    var main = $('.bk_main');
    var sheight = $(window).height();
    var bk_shadow = $('<div></div>');
    bk_shadow.appendTo(main);
    bk_shadow.attr('id', 'main_Shadow');
    bk_shadow.addClass('shadow');
    bk_shadow.css({'min-height': sheight})

}
