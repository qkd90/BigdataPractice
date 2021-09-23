//针对微信内置浏览器,把页面缩小到0.55
var viewport = document.querySelector("meta[name=viewport]");
var winWidths=$(window).width();
var densityDpi=640/winWidths;
    densityDpi= densityDpi>1?300*640*densityDpi/640:densityDpi;
if(isWeixin()){
    viewport.setAttribute('content', 'width=640, initial-scale=0.55, target-densityDpi='+densityDpi);
}else{
    viewport.setAttribute('content', 'width=640, user-scalable=no');
    window.setTimeout(function(){
        viewport.setAttribute('content', 'width=640, user-scalable=yes');
    },1000);
}
function isWeixin(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

//长按弹出删除条
//var myTouch = util.toucher(document.getElementById('xlgllist'));
//myTouch.on('longTap','.sublx',function(e){
//    //常按显示删除按钮
//	//alert('这只是个demo！');
//	$(".delete-xianlu").removeClass("hidden");
//});
////点击关闭，隐藏删除栏
//$(".goback-close").click(function(){
//  $(".delete-xianlu").addClass("hidden");
//});
////点击取消，隐藏删除栏
//$(".quxiao").click(function(){
//  $(".delete-xianlu").addClass("hidden");
//});
//拖动排序
//var list = document.getElementById("xlgllist");
//new Sortable(list); // That's all.