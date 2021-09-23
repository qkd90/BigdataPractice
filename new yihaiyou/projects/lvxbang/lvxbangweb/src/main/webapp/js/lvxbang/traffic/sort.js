/**
 * Created by vacuity on 16/1/26.
 */

var Sort = {
    listId: null,
    sortColumn: null,
    sortType: null,


    sortTicket: function (listId) {
        var list = $("#" + listId).find("li").sort(sortTicket);
        $("#" + listId).empty().append(list);
        moreFlight();
    },


    changeSort: function (listId, sortId, sortColumn, typeImg, index) {
        //
        Sort.sortColumn = sortColumn;
        var selected = $("#" + sortId);
        // 改变排序类型
        if (selected.hasClass("ioc") || selected.hasClass("ioc4")) {
            //
            if (selected.hasClass("ioc")) {
                selected.removeClass("ioc");
                selected.addClass("ioc4");
                Sort.sortType = "asc";
            } else if (selected.hasClass("ioc4")) {
                selected.removeClass("ioc4");
                selected.addClass("ioc");
                Sort.sortType = "desc";
            }
        } else {
            // 改变排序类别
            var typeImgList = $("." + typeImg);
            for (var i = 0; i < typeImgList.size(); i++) {
                var img = typeImgList.eq(i);
                if (i != index) {
                    if (img.hasClass("ioc")) {
                        img.removeClass("ioc");
                        img.addClass("ioc2");
                        img.parent("span").removeClass("selected");
                    } else if (img.hasClass("ioc4")) {
                        img.removeClass("ioc4");
                        img.addClass("ioc3");
                        img.parent("span").removeClass("selected");
                    }
                } else {
                    if (img.hasClass("ioc2")) {
                        img.removeClass("ioc2");
                        img.addClass("ioc");
                        img.parent("span").addClass("selected");
                        Sort.sortType = "desc";
                    } else if (img.hasClass("ioc3")) {
                        img.removeClass("ioc3");
                        img.addClass("ioc4");
                        img.parent("span").addClass("selected");
                        Sort.sortType = "asc";
                    }
                }
            }
        }

        Sort.sortTicket(listId);

        bindTishi3();
    },
}


function sortTicket(a, b) {
    //
    var m = 1;
    var n = -1;
    if (Sort.sortType == "desc") {
        m = -1;
        n = 1;
    }
    if (Sort.sortColumn == "sortTime" || Sort.sortColumn == "sortPrice") {
        return Number($(a).find("input[name=" + Sort.sortColumn + "]").val()) > Number($(b).find("input[name=" + Sort.sortColumn + "]").val()) ? m : n;
    }
    return $(a).find("input[name=" + Sort.sortColumn + "]").val() > $(b).find("input[name=" + Sort.sortColumn + "]").val() ? m : n;
}