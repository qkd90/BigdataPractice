//判断用户是否浏览过，没有则浏览数(viewNum)加一

$(function () {
    // 收藏相关方法
    collect(".d_collect", true, ".collectNum");
    TravelNote.init();
});
