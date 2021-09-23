/**
 * Created by HMLY on 2016-12-19.
 */
$(function(){
    $("#routeDatalist").hide();
    /*列表展开*/
    datalist($("#routeInputBtn"),$("#routeDatalist"));
});

/*列表 展开 收缩*/
function datalist(obj,target){
    obj.click(function(ev){
        var ev = ev || event;
        ev.stopPropagation();
        if(target[0].style.display == 'none'){
            target.slideDown("800");
        }else{
            target.slideUp("800");
        }
    });
    target.find("li").click(function(){
        var inputVal = $(this).html();
        obj.val(inputVal);
        target.slideUp();
    });
    $("body").click(function(){
        target.slideUp();
    });
}