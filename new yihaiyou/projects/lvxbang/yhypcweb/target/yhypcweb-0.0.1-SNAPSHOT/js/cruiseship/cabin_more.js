/**
 * Created by Administrator on 2017/6/3.
 */
var cabinMore = {
    init:function(){
        cabinMore.cabinCategory();
        cabinMore.detailsCollapse();
    },
    /*导航点击删选*/
    cabinCategory:function() {
        $(".cabin-nav").find("li").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            var cabinCategory=$(this).attr("data-cabin");
            if(cabinCategory == "all"){
                $(".cabin-group").show();
            }else{
                for(var i=0;i<$(".cabin-group").length;i++){
                    if($(".cabin-group").eq(i).attr("data-category") == cabinCategory ){
                        $(".cabin-group").eq(i).show().siblings().hide();
                    }
                }
            }
        });
    },
    /*详情展开*/
    detailsCollapse:function(){
        $(".cabin-operation").click(function(){
            if(!$(this).hasClass("active")){
                $(".cabin-txt").hide();
                $(".cabin-operation").html("详情<i></i>");
                $(".cabin-operation").removeClass("active");
                $(this).addClass("active");
                $(this).html("收起<i></i>");
                $(this).closest(".body-group").siblings(".cabin-txt").show();
            }else{
                $(this).removeClass("active");
                $(this).html("详情<i></i>");
                $(this).closest(".body-group").siblings(".cabin-txt").hide();
            }
        });
    }
};
$(function(){
    cabinMore.init();
});