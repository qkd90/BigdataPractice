/**
 * Created by Administrator on 2017/8/11.
 */
var ljOrder = {
    init:function(){
        ljOrder.closePop();
    },
    /*关闭弹窗提示*/
    closePop:function(){
        $(".icon-close").on("click",function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(".pop").hide();
        });
        $(".pop").on("click",function(){
            $(".pop").hide();
        });
        $(".pop-wrap").on("click",function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(".pop").show();
        });
    }
};
$(function(){
    ljOrder.init();
});