var pager;

$(document).ready(function(){
    //checkbox
    $(".checkbox").click(function(){
        if($("input",this).attr("checked"))
        {
            $(this).addClass("checked");
        }
        else
        {
            $(this).removeClass("checked");
        }
    });

    var options={
        countUrl: "/lvxbang/delicacy/count.jhtml",
        resultCountFn: function(result) {return parseInt(result[0])},
        pageRenderFn: function(pageNo, pageSize, data) {
            $('#delicacyList').empty();
            $("#loading").show();
            scroll(0, 0);
            data.pageNo = pageNo;
            data.pageSize = pageSize;
            $.ajax({
                url:"/lvxbang/delicacy/list.jhtml",
                type:"post",
                dataType:"json",
                data:data,
                success:function(data){
                    $('#delicacyList').empty();
                    $("#loading").hide();
                    $.each(data, function(i, item) {
                        var s = item;
                        s.introduction = formatString(s.introduction);
                        var result = template("tpl-delicacy_list-item", s);
                        $('#delicacyList').append(result);
                    });
                    collect(".collect2", false, ".collectNum");
                    $(".is_hover").hover(function () {
                        $(this).find("span").hide();
                    }, function () {
                        $(this).find("span").show();
                    });
                    //图片懒加载
                    $("img").lazyload({
                        effect: "fadeIn"
                    });


                },
                error:function(){
                    window.console.log("error");
                }
            });
        }
    };
    pager = new Pager(options);
    //checkbox
    $("#destination").find(".checkbox .checkbox_i").unbind("click");
    $("#destination").find(".checkbox").unbind("click");
    $("#destination").delegate(".checkbox .checkbox_i", "click", function () {
        if ($(this).parent("div").hasClass("checked")) {
            $(this).parent("div").removeClass("checked");
        }
        else {
            $(this).parent("div").addClass("checked");
        }
        delicacyList();
    });
    CitySelector.withOption({
        panelId: '#destination',
        selectedFn: function () {
            delicacyList();
        }
    });
});

$(function(){
    //$('#destination').find('p').remove();
    cityInput();
    liClick();
    //inputChange();

});

//分页查询
function delicacyList(){
    var name = $("#delicacyName").val();
    var priceRange = new Array();
    priceRange[0] = $("#price").find(".checked").attr("min");
    priceRange[1] = $("#price").find(".checked").attr("max");
    var cuisine=$("#cuisine").find(".checked").attr("cuisineId");
    var orderColumn = $("#sort").find(".checked").attr("orderColumn");
    var orderType = $("#sort").find(".checked").attr("orderType");
    var search = {
        'delicacyRequest.name':name,
        'delicacyRequest.cuisine':cuisine,
        'delicacyRequest.priceRange[0]':priceRange[0],
        'delicacyRequest.priceRange[1]':priceRange[1],
        'delicacyRequest.orderColumn':orderColumn,
        'delicacyRequest.orderType':orderType

    };

    var cityIds = [];
    var i=0;
    $("#destination").find(".checkbox").each(function( e) {
        if($(this).hasClass("checked")) {
            search['delicacyRequest.cityIds['+i+']'] = Number($(this).data("id"));
            i++;
        }
    });

    pager.init(search);
}

//增减点击事件
function liClick() {

    $("#cuisine").on("click", "li a", function () {
        $("#cuisine").find(".checked").attr("class", "");
        $(this).attr("class", "checked");
        delicacyList();
    });

    $("#price").on("click", "li a", function () {
        $("#price").find(".checked").attr("class", "");
        $(this).attr("class", "checked");
        delicacyList();
    });

    $("#sort").on("click", "li", function () {
        orderClick(this);
        delicacyList();
    });

    //$("#selectDelicacyId").on("click", "li label", function () {
    //    var delicacy = $(this).attr("delicacy");
    //    $("#delicacyName").val(delicacy);
    //    delicacyList();
    //});
}

//获取查询列表
//function inputChange() {
//    $("#delicacyName").bind("input change", function () {
//        var name = $("#delicacyName").val();
//        var priceRange = new Array();
//        priceRange[0] = $("#price").find(".checked").attr("min");
//        priceRange[1] = $("#price").find(".checked").attr("max");
//        var cuisine=$("#cuisine").find(".checked").attr("cuisineId");
//        var orderColumn=$("#sort").find(".checked").attr("sortId");
//        var orderType=$("#sort").find(".checked").attr("sortType");
//        //var cityCode = $('#cityCode_t').val();
//        //$("#cityCode_t").val('');
//        var search = {
//            'delicacyRequest.name':name,
//            'delicacyRequest.cuisine':cuisine,
//            'delicacyRequest.priceRange[0]':priceRange[0],
//            'delicacyRequest.priceRange[1]':priceRange[1],
//            'delicacyRequest.orderColumn':orderColumn,
//            'delicacyRequest.orderType':orderType
//
//        };
//
//        var cityIds = [];
//        $("#destination p input").each(function(i, e) {
//            if($(this).attr("checked") == "checked") {
//                cityIds.push(Number($(this).val()));
//            }
//        });
//        $.each(cityIds,function(i, item) {
//            search['delicacyRequest.cityIds['+i+']'] = item;
//        });
//
//
//        $.post("/lvxbang/delicacy/suggestList.jhtml",
//            search,
//            function (data) {
//                $("#selectDelicacyId").empty();
//                for (var i = 0; i < data.length; i++) {
//                    var s = data[i].name;
//                    var n = s.indexOf(name);
//                    var s1 = "";
//                    var s2 = "";
//                    if (n < 0) {
//                        return;
//                    }
//                    if (n == 0) {
//                        s2 = s.substring(name.length, s.length);
//                    }
//                    else if (n == s.length) {
//                        s1 = s.substring(0, n);
//                    }
//                    else {
//                        var str = s.split(name);
//                        s1 = str[0];
//                        s2 = str[1];
//                    }
//                    $("#selectDelicacyId").append('<li><label delicacy="'+s+'" class="fl">'+ s1 +'<strong>' + name +
// '</strong>' + s2 + '</label></li>'); }  $("#selectDelicacyId").on("click", "li label", function () { var delicacy =
// $(this).attr("delicacy"); $("#delicacyName").val(delicacy); }); }); }); }

//获取citycode和cityname，生成input
function cityInput(){
    var cityName = $('#cityName').val();
    var cityCode = $('#cityCode').val();
    if( cityCode != "" & cityName !=""){
       $('#destination_item').after('<div class="fl checkbox checked" destination="'+cityName+'" data-id="'+cityCode+'"><span class="checkbox_i" input="options"><i></i></span>'+cityName+'</div>');
    }
    delicacyList();
}
//回车键进行搜索
$(function(){
    $('#delicacyName').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            if(!isNull($('#delicacyName').val())){
                delicacyList();
                $('#delicacyName').next().hide();
            }
        }
    });
});
