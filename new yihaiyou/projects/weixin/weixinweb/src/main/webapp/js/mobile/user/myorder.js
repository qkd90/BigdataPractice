/**
 * Created by vacuity on 15/12/14.
 */


$(function()
{
    $("body").scrollTop(0);
    initList();

});

function initList()
{

    $.post("/mobile/user/myOrders.jhtml", function(result)
    {
        if(!result || result.length <= 0){
            $("#no-orders").removeClass("display-none");
        } else {
            $("#no-orders").addClass("display-none");
            $("#myorders").empty();
            var html = $("#order-list").render(result);
            $("#myorders").append(html);
        }
    });
}