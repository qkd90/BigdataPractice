/**
 * Created by huangpeijie on 2016-01-06,0006.
 */
function collect(favorite, isDetail, collectNum) {
    if (isDetail) {
        checkFavorite(favorite, collectNum);
    } else {
        checkFavorites(favorite);
    }
    $(favorite).on("click", function () {
        addFavorite(this, isDetail, collectNum);
    });
}

function addFavorite(favorite, isDetail, collectNum) {
    if (has_no_User(function () {
            login_checkFavorite(favorite, isDetail, collectNum)
        })) {
        $(favorite).removeClass("checked").removeClass("exists");
        return;
    }
    var id = $(favorite).data("favorite-id");
    var type = $(favorite).data("favorite-type");
    if ($(favorite).hasClass("exists")) {
        $.post(
            "/lvxbang/favorite/batchClearFavorite.jhtml",
            {
                'favoriteId': id,
                'favoriteType': type
            },
            function (result) {
                if (result.success) {
                    if (type == "line") {
                        if (isDetail) {
                            $(favorite).text("关注");
                            $(collectNum).text(result.collectNum);
                        } else {
                            $(favorite).find("span").text("关注");
                        }
                    } else if (isDetail) {
                        $(favorite).html("<i></i>收藏<br/>" + result.collectNum);
                        $(collectNum).children("span").text(result.collectNum);
                    }
                    else {
                        $(favorite).text("收藏");
                        $(favorite).parents("li").find(collectNum).children("span").text(result.collectNum);
                    }
                    $(favorite).removeClass("checked").removeClass("exists");
                }
                else {
                    promptWarn(result.errorMsg);
                }
            },
            "json"
        )
    }
    else {
        $.post(
            "/lvxbang/favorite/addFavorite.jhtml",
            {
                'otherFavorite.favoriteId': id,
                'otherFavorite.favoriteType': type
            },
            function (result) {
                if (result.success) {
                    if (type == "line") {
                        if (isDetail) {
                            $(favorite).text("已关注");
                            $(collectNum).text(result.collectNum);
                        } else {
                            $(favorite).find("span").text("已关注");
                        }
                    } else if (isDetail) {
                        $(favorite).html("<i></i>已收藏<br/>" + result.collectNum);
                        $(collectNum).children("span").text(result.collectNum);
                    }
                    else {
                        $(favorite).text("已收藏");
                        $(favorite).parents("li").find(collectNum).children("span").text(result.collectNum);
                    }
                    $(favorite).addClass("checked").addClass("exists");
                }
                else {
                    if (result.errorMsg == null) {
                        //has_no_User();
                        $(favorite).removeClass("checked").removeClass("exists");
                    }
                    else {
                        promptWarn(result.errorMsg);

                    }
                }
            },
            "json"
        )
    }
}

function checkFavorite(favorite, collectNum) {
    var id = $(favorite).data("favorite-id");
    var type = $(favorite).data("favorite-type");
    $.post(
        "/lvxbang/favorite/checkExists.jhtml",
        {
            'favoriteId': id,
            'favoriteType': type
        },
        function (result) {
            if (type == "line") {
                $(collectNum).text(result.collectNum);
                if (result.exists) {
                    $(favorite).addClass("exists").text("已关注");;
                } else {
                    $(favorite).removeClass("exists").text("关注");
                }
                return;
            }
            $(collectNum).children("span").text(result.collectNum);
            if (result.exists) {
                $(favorite).html("<i></i>已收藏<br/>" + result.collectNum).addClass("checked").addClass("exists");
            }
            else {
                $(favorite).html("<i></i>收藏<br/>" + result.collectNum).removeClass("checked").removeClass("exists");
            }
        },
        "json"
    )
}

function checkFavorites(favorites) {
    var data = {};
    var i = 0;
    $(favorites).each(function () {
        var id = $(this).data("favorite-id");
        data['ids[' + i + ']'] = id;
        i++;
    });
    data['favoriteType'] = $(favorites).data("favorite-type");
    $.post(
        "/lvxbang/favorite/batchCheckExists.jhtml",
        data,
        function (result) {
            if (result.success) {
                var ids = result.ids;
                for (var i = 0; i < ids.length; i++) {
                    var favorite;
                    if (data.favoriteType == "line") {
                        favorite = $(".favorite[data-favorite-id=" + ids[i] + "]");
                        favorite.find("span").text("已关注");
                    } else {
                        favorite = $("span[data-favorite-id=" + ids[i] + "]");
                        favorite.html("已收藏");
                    }
                    favorite.addClass("checked").addClass("exists");
                }
            }
        },
        "json"
    )
}
//登录后判断是否已经收藏需不需要进行收藏动作
function login_checkFavorite(favorite, isDetail, collectNum) {
    var id = $(favorite).data("favorite-id");
    var type = $(favorite).data("favorite-type");
    $.post(
        "/lvxbang/favorite/checkExists.jhtml",
        {
            'favoriteId': id,
            'favoriteType': type
        },
        function (result) {
            if (type == "line") {
                $(collectNum).text(result.collectNum);
                if (result.exists) {
                    $(favorite).addClass("exists").text("已关注");;
                } else {
                    addFavorite(favorite, isDetail, collectNum);
                }
                return;
            }
            $(collectNum).children("span").text(result.collectNum);
            if (result.exists) {
                $(favorite).html("<i></i>已收藏<br/>" + result.collectNum).addClass("checked").addClass("exists");
            }
            else {
                addFavorite(favorite, isDetail, collectNum);
            }
        },
        "json"
    );
}