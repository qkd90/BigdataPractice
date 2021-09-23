var DestinationIndex = {

    init: function () {
//		DestinationIndex.initCategories();
//        DestinationIndex.initDestination();
    },


    ininBtnSeach: function () {
        if ($("#distinationNameId").val().length > 0) {
            var url = "/lvxbang/destination/getAreaInfo.jhtml";
            $.post(url, {"name": $("#distinationNameId").val()},
                function (data) {
                    var random = parseInt(Math.random() * 10000);
                    if (data.success) {
                        window.location.href = "/lvxbang/destination/detail.jhtml?cityCode=" + data.id + "&random=" + random;
                    } else {
                        promptWarn("无此目的地，请更换！");
                        //alert("无当前目的地，请更换目的地！");
                    }
                },"json");
        } else {
        	$(".ws100").children(".categories_Addmore2").show();
//            promptMessage("请输入目的地！");
            //alert("请输入目的地！");
        }
    },


    initDestination: function () {
        $("#distinationNameId").focus(function(){
            $("#addmore2Id").show();
        }).keyup(function () {
            if ($.trim($(this).val()).length>0) {
                $("#addmore2Id").hide();
            } else {
                $("#addmore2Id").show();
            }
        });
        $(".add_more_tab").find("a").click(function () {
            $(".add_more_tab").find("a").removeClass("checked");
            $(this).addClass("checked");
            var panels = $("#dest_addmore_dl").find("dd");
            var current = $(this).data("label");
            panels.hide();
            if (current == "hot") {
                panels.each(function () {
                    if ($(this).data("label") == "hot") {
                        $(this).show();
                    }
                })
            } else {
                var regex = new RegExp("[" + current + "]{1}", "i");
                panels.each(function () {
                    if ($(this).data("label").length == 1 && regex.test($(this).data("label"))) {
                        $(this).show();
                    }
                })
            }
        });
        $("#hot_id").click();
    }
};

$(function(){
	DestinationIndex.init();
    $('#ipt-destination').bind('keypress',function(event) {
        if (event.keyCode == "13") {
            $(this).parent().next().click();
        }
    });
});
//搜索框
$(".dest_list").each(function() {
    var category = $(this);
    category.find(".input").keyup(function(e) {
        if (e.keyCode == 13) {
            return;
        }
        var keyword = $.trim($(this).val());
        if (keyword.length == 0) {
            return;
        }
        var regex = /[a-zA-Z]+/;
        if (regex.test(keyword)) {
            return;
        }
        $.post(category.attr("data-url"), {name: keyword}, function (result) {
            if (result.length > 0) {
                var html = "";
                //window.console.log(result);
                $.each(result, function (i, data) {
                    data.key = data.name.replace(keyword, "<strong style='color:#39F;'>"+keyword+"</strong>");
                    html += "<a href='/lvxbang/destination/detail.jhtml?cityCode='"+data.id+" style='color:gray;'>" + template("tpl-suggest-item", data) +"</a>";
                });
                category.find("#dest_ul_id").html("").append(html);
                category.find(".suggest-item").click(function () {
                    category.find(".input").val($(this).attr("data-text"));
                })
            }
        },"json");
        category.find(".categories_div").show();
    });
});

$(document).on("click", function(){
	$(".categories_div").hide(); 
});


