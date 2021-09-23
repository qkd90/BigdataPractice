// 手绘图加载
var map;
var hdConfig = {};
var hdScenic = {};
var hdInfWin;
var current_zoom;

function createHdMap(cityId, hdConfig)
{
	// 清除原来放置在allmap中得占位提示
	$("#allmap").empty();
	var tileLayer = new BMap.TileLayer();
	tileLayer.getTilesUrl = function(tileCoord, zoom)
	{
	    var x = tileCoord.x;
	    var y = tileCoord.y;
        var url = 'http://7u2inn.com2.z0.glb.qiniucdn.com/hand-draw/' + cityId + '/' + zoom + '/tile' + x + '_' + y + '.jpg';     // 根据当前坐标，选取合适的瓦片图
	    return url;
	};

	var MyMap = new BMap.MapType('MyMap', tileLayer, {minZoom: hdConfig.hdmin, maxZoom: hdConfig.hdmax});
	map = new BMap.Map('allmap',{mapType:MyMap,enableMapClick:false});
	map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));
	map.centerAndZoom(new BMap.Point(0, 0), hdConfig.hdmin);
	map.enableScrollWheelZoom();
	map.addEventListener("zoomstart", function(){ map.enableScrollWheelZoom(); map.disableScrollWheelZoom(); });
	map.addEventListener("zoomend", function(){ map.enableScrollWheelZoom(); loadHdScenic(editCfg.cityCode(), hdConfig); current_zoom = map.getZoom(); });
	map.addEventListener("click", function(){ hdInfWin.close(); });
}

function loadHdScenic(cityId, cfg)
{
	map.clearOverlays();
	BMapLib.AreaRestriction.clearBounds();
	// 读取服务端数据
	$.getJSON("/lvxbang/handDraw/getScenic.jhtml?t=" + new Date().getTime(), { cityId : cityId, level : map.getZoom() }, function(result)
	{
        var scenicList = result;
			// 若返回的级别与当前级别不同，则不加载数据
			if (map.getZoom() != scenicList[0].zoomLevel)
			{
				return;
			}

			hdInfWin = new HDInfoWindow();
			map.addOverlay(hdInfWin);


		for (var i = 0; i < scenicList.length; i++)
			{
				var scenic = scenicList[i];
                hdScenic[scenic.scenicId+"_"+scenic.zoomLevel] = scenic;
				function addHDOverlay(scenic)
				{
					var hdScenic = new HandDrawScenic(new BMap.Point(scenic.lng, scenic.lat), scenic.style, scenic.width, scenic.height, cityId, scenic.zoomLevel, scenic.name, scenic.scenicId);
					map.addOverlay(hdScenic);
					if(scenic.operable == true)
					{
						hdScenic.clickExt(function()
						{
							$('.shineImga').removeClass('shineImga');
							hdInfWin.open(new BMap.Point(scenic.lng, scenic.lat), scenic.scenicId);
						});

					}
				}
				addHDOverlay(scenic);
			}

		var bd = cfg.bounds[map.getZoom()];
			if (bd == null || bd == '' || bd == undefined)
			{
				return;
			}
			//设置边界
			//var bound = new BMap.Bounds(new BMap.Point(bd.hdWest, bd.hdSouth), new BMap.Point(bd.hdEest, bd.hdNorth));
			var bound = new BMap.Bounds(new BMap.Point(-300, -220), new BMap.Point(400, 300));

			try {
				//边界控制
				BMapLib.AreaRestriction.setBounds(map, bound);
			}
			catch (e)
			{
				//alert(e);
				promptWarn(e);
			}

	});
}