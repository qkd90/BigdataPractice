/**
 * Created by Sane on 16/3/23.
 */
$(document).ready(function () {

    var url = window.location.href;
    var type = url.split('?')[1].split('=')[1];
    if (type=='flight') {
        $('#li_flight').click();
    }else{
        $('#li_train').click();
    }
    $.ajax({
        url:"/lvxbang/destination/getIpCity.jhtml",
        type:"post",
        dataType:"json",
        data:{
        },
        success:function(data){
            var name = $('#flightLeaveCity').val();
            if(isNull(name)){
                $('#flightLeaveCity').val(data.name);
                $('#flightLeaveCity').attr("data-areaid",data.id);
            }
            var date = new Date();
            if(isNull($('#fjcf').val())){
                $('#fjcf').val(date.format("yyyy-MM-dd"));
            }
            if(isNull($('#fjd1').val())){
                $('#fjd1').val(date.format("yyyy-MM-dd"));
            }
            name = $('#trainLeaveCity').val();
            if(isNull(name)){
                $('#trainLeaveCity').val(data.name);
                $('#trainLeaveCity').attr("data-areaid",data.id);
            }
            date = new Date();
            if(isNull($('#hccf').val())){
                $('#hccf').val(date.format("yyyy-MM-dd"));
            }
        }
    });

    $(".mailTablePlan input").bind('keypress',function(event) {
        if (event.keyCode == "13") {
            $(this).parents(".mailTablePlan").find(".toSearch").click();
        }
    });
});
//日历自动选择时间
function dateChange1(){
        var lDate = new Date(Date.parse($('#hccf').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#hcfc').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#hcfc').val(lDate.format("yyyy-MM-dd"));
        }
        $('#hcfc').focus().click();
}
function dateChange2(){
        var lDate = new Date(Date.parse($('#fjcf').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#fjfc').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#fjfc').val(lDate.format("yyyy-MM-dd"));
        }
        $('#fjfc').focus().click();
}
function dateChange3(){
        var lDate = new Date(Date.parse($('#fjd1').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#fjd2').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#fjd2').val(lDate.format("yyyy-MM-dd"));
        }
        $('#fjd2').focus().click();
}
