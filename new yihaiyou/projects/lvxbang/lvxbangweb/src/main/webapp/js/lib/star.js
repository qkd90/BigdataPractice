//star
$(document).ready(function(){

    $(".star").each(function(){
        var star=this;
        var stepW = $(this).attr("mname");
        var description = new Array("很差不推荐","一般不推荐","推荐","较推荐","强烈推荐");
        var stars = $("> li",star);
        $("~ .current-rating",star).css("width",0);
        stars.each(function(i){
            $(stars[i]).click(function(e){
                var n = i+1;
                $("~ .current-rating",star).css({"width":stepW*n});
               var descriptionTemp = description[i];
                $(this).find('a').blur();
                $(star).data("description",descriptionTemp);
                return stopDefault(e);
            });
        });
        stars.each(function(i){
            $(this).hover(
                function(){
                    $("~.description",$(star).parent()).text(description[i]);
                },
                function(){
                    var descriptionTemp=$(star).data("description");
                    if(descriptionTemp != null)
                        $("~.description",$(star).parent()).text(descriptionTemp);
                    else 
                        $("~.description",$(star).parent()).text(" ");
                }
            );
        });
    });
   
});
function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};
