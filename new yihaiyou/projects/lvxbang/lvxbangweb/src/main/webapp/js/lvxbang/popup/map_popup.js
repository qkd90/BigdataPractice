/**
 * Created by huangpeijie on 2016-02-05,0005.
 */
//地图
$(document).ready(function () {
    var mapPop = new BMap.Map("ditu",{enableMapClick:false});//在百度地图容器中创建一个地图
    var point = new BMap.Point(0, 0);//定义一个中心点坐标
    mapPop.centerAndZoom(point, 17);
    window.mapPop = mapPop;
})

function lookMap() {
    $(".look_map").on("click", function () {
        point = new BMap.Point($(this).attr("data-ditu-baiduLng"), $(this).attr("data-ditu-baiduLat"));//定义一个中心点坐标
        mapPop.centerAndZoom(point, 17);//设定地图的中心点和坐标并将地图显示在地图容器中
        var local = new BMap.LocalSearch(mapPop, {
            renderOptions: {map: mapPop}, pageCapacity: 1
        });
        local.search($(this).attr("data-ditu-name"));
        //弹窗
        $(".mask").show();
        $(".ditulbx").fadeIn();
        //关闭
    });
    $(".hqxqdtx .close").click(function () {
        mapPop.clearOverlays();
        $(".mask,.hqxqdtx").hide();
    });
}