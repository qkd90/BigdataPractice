//头部城市选择
$(document).ready(function(){
    $(".city").mouseenter(function(){
        $(".subcity").show();
    }).mouseleave(function() {
        $(".subcity").hide();
    });
});