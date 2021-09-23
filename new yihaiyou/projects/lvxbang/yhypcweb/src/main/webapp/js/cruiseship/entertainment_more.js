/**
 * Created by Administrator on 2017/6/5.
 */
var foodMore = {
    init:function(){
        foodMore.categorySelect();
    },
    /*内别筛选*/
    categorySelect:function(){
        $(".cabin-nav").find("li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            var category = $(this).attr("data-food");
            if(category == "all"){
                $(".server-list").find("li").show();
            }else{
                for(var i=0;i<$(".server-list").find("li").length;i++){
                    if($(".server-list").find("li").eq(i).attr("data-category") == category){
                        $(".server-list").find("li").eq(i).show();
                    }else{
                        $(".server-list").find("li").eq(i).hide();
                    }
                }
            }
        });
    }
};
$(function(){
    foodMore.init();
});