var pager;

$(document).ready(function () {
    createPager();
    scenicTheme();
    liClick();
});
function scenicTheme() {
    $.ajax({
        url: "/lvxbang/scenic/getScenicTheme.jhtml",
        type: "post",
        dataType: "json",
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var st = data[i];
                if (i > 0 && i % 7 == 0) {
                    $("#theme").append("<li class='whole'><span style='padding: 3px 4px;'></span></li><li class='whole'><span style='padding: 3px 4px;'></span></li>");
                }
                $("#theme").append("<li><a title='" + st + "' href='javaScript:;' themeName='" + st + "'>" + st + "</a></li>");
            }
            selectTheme();
            selectCity();
            scenicList();
        }
    });
}

function createPager() {
    var options = {
        countUrl: "/lvxbang/scenic/getTotalPage.jhtml",
        resultCountFn: function (result) {
            return parseInt(result[0]);
        },
        pageRenderFn: function (pageNo, pageSize, data) {
            $('#scenic').empty();
            $("#loading").show();
            scroll(0, 0);
            data.pageIndex = pageNo;
            data.pageSize = pageSize;
            $.post("/lvxbang/scenic/getScenicList.jhtml",
                data,
                function (data) {
                    $('#scenic').empty();
                    $("#loading").hide();
                    for (var i = 0; i < data.length; i++) {
                        var s = data[i];
                        if(parseInt(s.id) < 2000433){
                            s.shortComment = formatString(s.shortComment);
                        }
                        var result = $(template("tpl-scenic-list-item", s));
                        $('#scenic').append(result);
                        result.data("scenic", s);
                    }
                    collect(".collect2", false, ".collectNum");
                    lookMap();
                    $(".is_hover").hover(function () {
                        $(this).find("span").hide();
                    }, function () {
                        $(this).find("span").show();
                    });
                    $("img").lazyload({
                        effect: "fadeIn"
                    });
                    FloatEditor.markScenic();
                }
            ,"json");
        }
    };
    pager = new Pager(options);
}

function scenicList(type) {
    isClear();
    var cityIds = [];
    var n = 0;
    $("#destination .checkbox").each(function () {
        if ($(this).hasClass("checked")) {
            cityIds[n] = $(this).attr("data-id");
            n++;
        }
    });
    if ($("#destination .checkbox").length > 0 && cityIds.length == 0) {
        cityIds[0] = 999999999999999;
    }
    var father = $("#father").attr("data-father");
    var name = $("#scenicName").val();
    var themeName = $("#theme").find(".checked").attr("themeName");
    var priceRange = [];
    priceRange[0] = $("#price").find(".checked").attr("min");
    priceRange[1] = $("#price").find(".checked").attr("max");
    var level = $("#level").find(".checked").attr("level");

    var search = {
        'scenicRequest.father': father,
        'scenicRequest.name': name,
        'scenicRequest.level': level,
        'scenicRequest.theme': themeName,
        'scenicRequest.priceRange[0]': priceRange[0],
        'scenicRequest.priceRange[1]': priceRange[1]
    };

    for (var i = 0; i < cityIds.length; i++) {
        search['scenicRequest.cityIds[' + i + ']'] = cityIds[i];
    }

    if (isNull(name) || (type != null && type == 1)) {
        if ($("#sort").find(".checked").length < 1) {
            $("#sort").find("[ordercolumn=ranking]").addClass("checked");
        }
        var orderColumn = $("#sort").find(".checked").attr("orderColumn");
        var orderType = $("#sort").find(".checked").attr("orderType");

        if (orderColumn == "ranking") {
            if (orderType == "asc") {
                orderType = "desc";
            }
            else {
                orderType = "asc";
            }
        }
        search['scenicRequest.orderColumn'] = orderColumn;
        search['scenicRequest.orderType'] = orderType;
    } else {
        $("#sort").find(".checked").removeClass("checked");
    }

    pager.init(search);
}

function selectTheme() {
    $("#theme li a").each(function () {
        if ($(this).attr("themeName") == $("#selectTheme").val()) {
            $("#theme").find(".checked").removeClass("checked");
            $(this).addClass("checked");
        }
    });
}

function selectCity() {
    $(".old").each(function () {
        var p = $(".new");
        for (var i = 0; i < p.length; i++) {
            if ($(this).attr("destination") == p.attr("destination")) {
                $(this).remove();
                break;
            }
        }
    });
}


function liClick() {
    $("#level").on("click", "li a", function () {
        $("#level").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        scenicList();
    });

    $("#theme").on("click", "li a", function () {
        $("#theme").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        scenicList();
    });

    $("#price").on("click", "li a", function () {
        $("#price").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        scenicList();
    });

    $("#sort").on("click", "li", function () {
        orderClick(this);
        scenicList(1);
    });

    $("#father").on("click", "i", function () {
        $(this).closest("div").remove();
        scenicList();
    });
}
//判断清空样式
function isClear(){
    var butt = true;
    $('.whole a').each(function(){
        if(!$(this).hasClass('checked')){
            butt = false;
        }
    });
    $("#destination .checkbox").each(function () {
        if ($(this).hasClass("checked")) {
           butt = false;
        }
    });
    if(butt){
        $('.qingkong').css('background','#ffcfb2');
    }else{
        $('.qingkong').css('background','#ff6000');
    }
}

$(document).ready(function () {
    $(".stroke").click(function () {
        var myStaute = $(this).attr("data-staute");
        if (!myStaute) {
            $(this).addClass("checked").attr("data-staute", "1");
        } else {
            $(this).removeClass("checked").removeAttr("data-staute");
        }
    });

    //checkbox
    $("#destination").delegate(".checkbox .checkbox_i", "click", function () {
        if ($(this).parent("div").hasClass("checked")) {
            $(this).parent("div").removeClass("checked");
        }
        else {
            $(this).parent("div").addClass("checked");
        }
        scenicList();
    });

    //$(document).ready(function () {
    //    planEditor.init();
    //});

    CitySelector.withOption({
        selectedFn: function () {
            scenicList();
            //FloatEditor.renderCity();
        }
    });

    //回车键进行搜索
    //document.onkeydown=searchList
    //function searchList(event){
    //    event = event ? event : (window.event ? window.event : null);
    //    if (event.keyCode==13){
    //        if(!isNull($('#scenicName').val())){
    //            scenicList();
    //            $('#scenicName').next().hide();
    //        }
    //    }
    //}


   $('.qk').click(function(){
       $('#scenicName').val('');
       $(this).css('display','none');
       scenicList();
   });
});

    //回车键进行搜索
    $(function(){
        $('#scenicName').bind('keypress',function(event){
            if(event.keyCode == "13")
            {
                if(!isNull($('#scenicName').val())){
                    scenicList();
                    $('#scenicName').next().hide();
                }
            }
        });
    });

