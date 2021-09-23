function MapModule(options)
{
	var bmap;
	var hotelDlg;
	var layers = new Object();
	var hotelOverlays = [];
	var currActive = 0;
    var defaultLevel = 13;
    // 当前激活的景点
    var currFocusScenic;
    // 地图上的圆
    var circleOverlay;
    // 临时节点，用于展示地图上的景点、推荐酒店
    var tmpMarker;
	var defaults =
	{
		line_color : "gray",
		autoActivemap : false,
		nor : 
		{
			scenic : {img: "/static/img/bmap/loc_ic_01.png", size : { width: 29, height: 46 }},
			hotel : {img: "/static/img/bmap/loc_ic_01.png", size : { width: 29, height: 46 }},
			food : {img: "/static/img/bmap/loc_ic_01.png", size : { width: 29, height: 46 }}
		},
		active : 
		{
			scenic : {img: "/static/img/bmap/jid.png", size : { width: 29, height: 46 }},
			hotel : {img: "/static/img/bmap/jd.png", size : { width: 29, height: 46 }},
			food : {img: "/static/img/bmap/ms.png", size : { width: 29, height: 46 }}
		}
	};
	
	options = $.extend(defaults, options);
	
	/**
	 * 地图初始化
	 */
	this.initMap = function(containerId, lng, lat, lv)
	{
		bmap = new BMap.Map(containerId, { enableMapClick : false });
		this.centerAndZoom(lng, lat, lv);
        defaultLevel = lv;
	};
	
	this.centerAndZoom = function(lng, lat, lv)
	{
		bmap.centerAndZoom(new BMap.Point(lng, lat), lv);
	};
	
	this.initHotelDlg = function(days)
	{
		// 初始化酒店详情对话框
		hotelDlg = new HotelDlg(days);
		bmap.addOverlay(hotelDlg);
		
		bmap.addEventListener("click",function(e){ hotelDlg.close(); })
	};

	this.initNav = function(daycount)
	{
		$("#J_map-nav-wrap").empty();
		for (var i = 0; i < daycount; i++)
		{
			$("#J_map-nav-wrap").append(template("tpl_map_day", {day : i + 1}));
		}
	}
	
	this.showmap = function()
	{
		$("#J_plan-map-wrap").addClass("show-panel");
		//$("#J_plan-map-wrap").show();
	};
	
	this.closemap = function()
	{
		$("#J_plan-map-wrap").removeClass("show-panel");
		//$("#J_plan-map-wrap").hide();
	};
	
	this.clearMap = function()
	{
		bmap.clearOverlays();
		layers = {};
	}
	
	/**
	 * 清除某一天的图层
	 */
	function clearLayer(day)
	{
		// 如果没有记录这天的数据，则没有什么好清除的
		if (!layers[day])
		{
			return;
		}
		
		// 如果有线信息，则擦除线路信息
		if (layers[day].lines)
		{
			for (var i = 0; i < layers[day].lines.length; i++)
			{
				var line = layers[day].lines[i];
				for (var j = 0; j < line.length; j++)
				{
					bmap.removeOverlay(line[j]);
				}
				
			}
		}
		
		// 如果有点信息，则擦除点信息
		if (layers[day].markers)
		{
			for (var i = 0; i < layers[day].markers.length; i++)
			{
				bmap.removeOverlay(layers[day].markers[i]);
			}
		}
	}
	
	/**
	 * 将某一天的行程地图按照传入的点集合进行更新
	 * 只绘制点，不绘制线
	 */
	this.updateDay = function(layer, day)
	{
//		console.log("updateDay:" + day);
		// 清除原有的覆盖物
		clearLayer(day);
		// 更新当天的点坐标信息和路线信息
		layers[day] = layer;
//		console.log(layer);
		norDay(day);
	}
	
	/**
	 * 将一天的节点设置为普通显示效果
	 */
	function norDay(day)
	{
		var layer = layers[day];
		var markers = [];
		
		if (!layer)
		{
			window.console.error(day);
		}
		
		// 绘制点
		for (var i = 0; i < layer.points.length; i++)
		{
			var p = layer.points[i];
			var marker = new NormalScenic(p, day);
//			console.log(marker);
			markers.push(marker);
			bmap.addOverlay(marker);
		}
		// 存储每天的点信息和线信息，以备擦除的时候使用
		layers[day].markers = markers;
	}
	
	/**
	 * 高亮某一天的地图
	 */
	this.activeDay = function(day)
	{
//		var activedays = this.getActiveDays();
		var overlays = getDayMarkers(day);
		for (var i = 0; i < overlays.length; i++)
		{
			var overlay = overlays[i];
			if (overlay.getday && overlay.getday() == day)
			{
				overlay.active();
			}
		}
		
		this.setViewport([day]);
	}
	
	/**
	 * 激活某个酒店marker
	 */
	this.activeHotelMarker = function(id)
	{
		for (var i = 0; i < hotelOverlays.length; i++)
		{
			var overlay = hotelOverlays[i];
			var hotelid = overlay._data.data.id;
			if (hotelid == id)
			{
				overlay.active();
			}
			else
			{
				overlay.unactive();
			}
		}
	}
	
	this.focusHotelMarker = function(id)
	{
		for (var i = 0; i < hotelOverlays.length; i++)
		{
			var overlay = hotelOverlays[i];
			var hotelid = overlay._data.data.id;
			if (hotelid == id)
			{
				overlay.focus();
			}
			else
			{
				overlay.unfocus();
			}
		}
	};
	
	this.addedHotelMarker = function(id)
	{
		for (var i = 0; i < hotelOverlays.length; i++)
		{
			var overlay = hotelOverlays[i];
			var hotelid = overlay._data.data.id;
			if (hotelid == id)
			{
				overlay.added();
			}
			else
			{
				overlay.unadd();
			}
		}
	};
	
	/**
	 * 将某个景点设置为焦点，当查看景点周边的时候会调用
	 */
	this.focusScenic = function(day, scenicid)
	{
		var markers = [];
		var days = planModule.getDayCounts();
		for (var i = 0; i < days; i++)
		{
			markers = markers.concat(layers[i].markers);
		}
		
		for (var i = 0; i < markers.length; i++)
		{
			var scenicMarker = markers[i];
			var data = scenicMarker._data;
			if (data.id == scenicid)
			{
				scenicMarker.focus();
				currFoucsScenic = scenicMarker._data;
			}
			else
			{
				scenicMarker.unfocus();
			}
		}
	}
	
	this.setViewport = function(days)
	{
		var alloverlays = [];
		var viewport = [];
		
		for (var i = 0; i < days.length; i++)
		{
			var overlays = getDayMarkers(days[i]);
			alloverlays = alloverlays.concat(overlays);
		}
		for (var i = 0; i < alloverlays.length; i++)
		{
			viewport.push(alloverlays[i]._point);
		}
		// 地图缩放
		bmap.setViewport(viewport);
	}
	
	/**
	 * 根据输入的点集合地图视角
	 */
	this.setViewportByPoints = function(points)
	{
		var viewport = [];
		for (var i = 0; i < points.length; i++)
		{
			var point = new BMap.Point(points[i].lng, points[i].lat);
			viewport.push(point);
		}
		if (currFocusScenic)
		{
			//console.log(currFocusScenic);
		}
		
		// 地图缩放
		bmap.setViewport(viewport);
	}
	
	/**
	 * 在地图上绘制所有酒店和景点
	 */
	this.activeAll = function()
	{
		$(".map-nav .all-plan-in-map").addClass("active");
		
		var days = [];
		this.getMapDlg().find("#J_map-nav-wrap>li").each(function()
		{
			var day = $(this).index();
			days.push(day);
			mapModule.activeDay(day);
			mapModule.drawDayLine(day);
		});
		
		this.setViewport(days);
	}
	
	/**
	 * 在地图上画一个指定半径的圆
	 */
	this.drawCycle = function(data)
	{
		//console.log(data);
		if (circleOverlay)
		{
			bmap.removeOverlay(circleOverlay);
		}
		if (data.lat && data.lng && data.dist)
		{
			var point = new BMap.Point(data.lng, data.lat);
			var circle = new BMap.Circle(point, data.dist, {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5, fillColor:""});
			bmap.addOverlay(circle);
			
			circleOverlay = circle;
		}
	}
	
	
//	this.getActiveDays = function()
//	{
//		var activedays = [];
//		this.getMapDlg().find("#J_map-nav-wrap>li").each(function()
//		{
//			if ($(this).hasClass("active"))
//			{
//				activedays.push($(this).index());
//			}
//		});
//	}
	
	/**
	 *  取消高亮某一天的地图
	 */
	this.unactiveDay = function(day)
	{
		var overlays = getDayMarkers(day);
		for (var i = 0; i < overlays.length; i++)
		{
			var overlay = overlays[i];
			if (overlay.getday && overlay.getday() == day)
			{
				overlay.unactive();
			}
		}
	}
	
	function getDayMarkers(day)
	{
		var dayMarkers = [];
		if (layers[day] && layers[day].markers)
		{
			dayMarkers = layers[day].markers;
		}
		
		return dayMarkers;
	}
	
	/**
	 * 绘制某一天景点的连线
	 */
	this.drawDayLine = function(day)
	{
		// 绘制前先删除当天旧的线
		this.removeDayLine(day);
		
		var markers = getDayMarkers(day);
		var lines = [];
		for (var i = 1; i < markers.length; i++)
		{
			var p1 = markers[i - 1]._point;
			var p2 = markers[i]._point;
			var polyline = createLine(p1, p2);
			bmap.addOverlay(polyline);
			lines.push(polyline);
		}
		
		// 如果当天包含酒店，则绘制酒店到开始景点和结束景点的线路
		if (layers[day].hotel)
		{
			var hp = layers[day].hotel._point;
			var polyline1 = createLine(markers[0]._point, hp);
			var polyline2 = createLine(hp, markers[markers.length - 1]._point);
			bmap.addOverlay(polyline1);
			bmap.addOverlay(polyline2);
			lines.push(polyline1);
			lines.push(polyline2);
		}
		
		// 存储线条，待擦除的时候使用
		layers[day].lines = lines;
	};
	
	function createLine(p1, p2)
	{
		var polyline = new BMap.Polyline([ p1, p2 ],{strokeColor:options.line_color, strokeWeight:2, strokeOpacity:0.5});
		return polyline;
	}
	
	this.removeDayLine = function(day)
	{
		if (!layers[day] || !layers[day].lines)
		{
			return;
		}
		for (var i = 0; i < layers[day].lines.length; i++)
		{
			bmap.removeOverlay(layers[day].lines[i]);
		}
		layers[day].lines = [];
	};
	
	/**
	 * 查询两点之间的直线距离
	 */
	this.getDistance = function(from, to)
	{
		var startPoint = new BMap.Point(from.lng, from.lat);
		var endPoint = new BMap.Point(to.lng, to.lat);
		
		return bmap.getDistance(startPoint, endPoint);
	}
	
	this.getDistanceStr = function(from, to)
	{
		var meter = this.getDistance(from, to);
		
		return meterToKilo(meter, 1);
	}
	
	function meterToKilo(meter, dotnum)
	{
		var kilo = (meter / 1000);
		//return kilo.toFixed(dotnum) + "公里";
		return kilo.toFixed(dotnum);
	}
	
	
	/**
	 * 查询两点之间的交通，包括公交、驾车和步行
	 */
	this.queryTraffic = function(from, to, querytype, callback)
	{
		var startPoint = new BMap.Point(from.lng, from.lat);
		var endPoint = new BMap.Point(to.lng, to.lat);
		
		if (querytype.bus)
		{
			// 查询公交信息
			var bustransit = new BMap.TransitRoute(bmap,
			{
				onSearchComplete : function(result)
				{ 
					var resultdata = new Object();
					resultdata.type = 1;
					if (result.getNumPlans() > 0)
					{
						resultdata.success = true;
						var myPlan = result.getPlan(0);
						resultdata.time = myPlan.getDuration();
						// 公交花费为1元
						resultdata.cost = 1;
						resultdata.dist = myPlan.getDistance(true);
						resultdata.timenum = myPlan.getDuration(false);
						resultdata.distnum = myPlan.getDistance(false);
						resultdata.desc = myPlan.getDescription(false);
						resultdata.line = getLine(result);
					}
					else
					{
						resultdata.success = false;
					}
					
					callback(from, to, resultdata);
				}
			});
			bustransit.search(startPoint, endPoint);
		}
		
		
		if (querytype.car)
		{
			// 查询驾车信息
			var cartransit = new BMap.DrivingRoute(bmap, 
			{
				onSearchComplete : function(result)
				{ 
					var resultdata = new Object();
					resultdata.type = 2;
					if (result.getNumPlans())
					{
						resultdata.success = true;
						var myPlan = result.getPlan(0);
						resultdata.time = myPlan.getDuration();
						resultdata.dist = myPlan.getDistance();
						resultdata.timenum = myPlan.getDuration(false);
						resultdata.distnum = myPlan.getDistance(false);
						resultdata.desc = "";
						// 获取打车花费可能出错
						try
						{
							resultdata.cost = result.taxiFare.day.totalFare;
						}
						catch (e){}
						resultdata.line = getLine(result);
						
					}
					else
					{
						resultdata.success = false;
					}
					
					callback(from, to, resultdata);
				}
			});
			cartransit.search(startPoint, endPoint);
		}
		
		if (querytype.walk)
		{
			// 查询步行信息
			var walktransit = new BMap.WalkingRoute(bmap, 
			{
				onSearchComplete : function(result)
				{ 
					var resultdata = new Object();
					resultdata.success = true;
					resultdata.type = 3;
					var resultdata;
					if (result.getNumPlans() > 0)
					{
						var myPlan = result.getPlan(0);
						resultdata.time = myPlan.getDuration();
						resultdata.dist = myPlan.getDistance();
						resultdata.timenum = myPlan.getDuration(false);
						resultdata.distnum = myPlan.getDistance(false);
						resultdata.desc = "";
						// 步行的花费是0
						resultdata.cost = 0;
						resultdata.line = getLine(result);
					}
					else
					{
						resultdata.success = false;
					}
					
					callback(from, to, resultdata);
				}
			});		
			walktransit.search(startPoint, endPoint);
		}
		
		if (querytype.line)
		{
			var resultdata = new Object();
			resultdata.success = true;
			resultdata.type = 4;
			resultdata.dist = mapModule.getDistanceStr(from, to);
			callback(from, to, resultdata);
		}
		
	}
	
	
	function getLine(result)
	{
		var plan = result.getPlan(0);
		if (plan == undefined)
	    {
	    	return;
	    }
		
		var overlayArr = new Array();
		
	    // 绘制驾车步行线路
	    for (var i = 0; i < plan.getNumRoutes(); i ++)
	    {
	        var route = plan.getRoute(i);
	        if (route.getDistance(false) <= 0){continue;}
	        
	        // 驾车线路
	        if(route.getRouteType() == BMAP_ROUTE_TYPE_DRIVING)
	        {
	        	overlayArr.push(new BMap.Polyline(route.getPath(), {strokeColor: "red", strokeOpacity:0.75,strokeWeight:4,enableMassClear:true}));
	        }
	        // 步行路线
	        else if (route.getRouteType() == BMAP_ROUTE_TYPE_WALKING)
	        {
	        	overlayArr.push(new BMap.Polyline(route.getPath(), {strokeColor: "red", strokeOpacity:0.75,strokeWeight:4,enableMassClear:true}));
	        }
	        // 绘制公交的步行部分线路
	        else 
	        {
	        	overlayArr.push(new BMap.Polyline(route.getPath(), {strokeColor: "red", strokeOpacity:0.75,strokeWeight:4,enableMassClear:true}));
	        }
	    }
	    
	    try
	    {
	    	// 绘制公交线路
	    	for (var i = 0; i < plan.getNumLines(); i++)
	    	{
	    		var line = plan.getLine(i);
	    		overlayArr.push(new BMap.Polyline(line.getPath(), {strokeColor: src.attr("line-color"), strokeOpacity:0.75,strokeWeight:4,enableMassClear:true}));
	    	}
	    }
	    catch (e){}
	    
	    return overlayArr;
	}
	
	/**
	 * 绘制点
	 */
	function drawPoint(point)
	{
		
	}
	
	/**
	 * 添加临时显示用的酒店覆盖物
	 */
	this.addHotels = function(points)
	{
		this.removeHotels();
		
		// 添加酒店覆盖物
		for (var i = 0; i < points.length; i++)
		{
			var hotelMarker = new HotelOverlay(points[i]);
			bmap.addOverlay(hotelMarker);
			
			hotelOverlays.push(hotelMarker);
		}
		
		// TODO 调整地图视野
		
	};
	
	this.removeHotels = function()
	{
		// 如果已经有酒店覆盖物，先删除
		for (var i = 0; i < hotelOverlays.length; i++)
		{
			bmap.removeOverlay(hotelOverlays[i]);
		}
		// 从地图上删除后，也清除原来保存的marker
		hotelOverlays = [];
	}
	
	this.addTmpMark = function(p)
	{
		if (tmpMarker)
		{
			bmap.removeOverlay(tmpMarker);
		}
		tmpMarker = new TmpMarker(p);
		bmap.addOverlay(tmpMarker);
	};
	
	/**
	 * 将临时景点居中
	 */
	this.centerTmpMark = function()
	{
		if (tmpMarker)
		{
			var position = bmap.pointToOverlayPixel(tmpMarker._point);
			var p_point = bmap.overlayPixelToPoint({x: position.x , y: (position.y - 150)});
			bmap.panTo(p_point);
		}
	}
	
	/**
	 * 显示地图对话框
	 */
	this.showHotelDlg = function(data, setViewport)
	{
		this.processHoteldata(data);
		var point = new BMap.Point(data.longitude, data.latitude);
		hotelDlg.openHotel(point, data);
		
		// 如果需要调整地图视角
		if (setViewport)
		{
			var position = bmap.pointToOverlayPixel(point);
			var p_point = bmap.overlayPixelToPoint({x: position.x , y: (position.y - 150)});
			bmap.panTo(p_point);
		}
		
		return false;
	}
	
	/**
	 * 预处理酒店数据
	 * 添加上已经添加到第几天的信息
	 * 添加上总天数信息
	 * 添加上每天选择的酒店信息
	 */
	this.processHoteldata = function(data)
	{
		var hotelMap = {};
		var hotelcMap = {};
		var hotelArr = [];
		// 获取当前地图每天使用了哪些酒店
		for (var i = 0; i < planModule.getDayCounts(); i++)
		{
			if (layers[i].hotel)
			{
				var selecthotelid = layers[i].hotel._data.data.id;
				hotelMap[i] = selecthotelid;
				hotelcMap[selecthotelid] = true;
				// 显示的天数从1开始
				if (selecthotelid == data.id)
				{
					hotelArr.push(i + 1);
				}
			}
		}
		data.hotelMap = hotelMap;
		data.hotelArr = hotelArr;
		data.hotelcMap = hotelcMap;
	}
	
	this.showScenicDlg = function(data)
	{
		var point = new BMap.Point(data.longitude, data.latitude);
		hotelDlg.openScenic(point, data);
	}
	
	this.closeScenicDlg = function()
	{
		hotelDlg.close();
	};
	
	/**
	 * 添加一天的酒店
	 */
	this.addDayHotel = function(point, day)
	{
		//console.log(day);
		var layer = layers[day];
		if (layer.hotel)
		{
			bmap.removeOverlay(layer.hotel);
			layer.hotel = undefined;
		}
		
		var marker = new HotelOverlay(point);
		bmap.addOverlay(marker);
		layer.hotel = marker;
		
		// 重新绘制线
		this.drawDayLine(day);
	}
	
	this.updateNav = function(delday)
	{
		var navs = this.getMapDlg().find("#J_map-nav-wrap>li");
		navs.eq(delday).remove();
		navs.each(function()
		{
			$(this).find("a").text("D" + ($(this).index() + 1));
			if ($(this).hasClass("active"))
			{
				// TODO 激活改天的地图
				mapModule.activeDay($(this).index());
			}
		});
	}
	
	this.getMapDlg = function()
	{
		return $("#J_plan-map-wrap");
	}
	
}

function Point(tripnode)
{
	this.lat = 0;
	this.lng = 0;
	this.tripType = 1;
	this.name = "";
	this.tripId = 0;
	this.cityCode = 0;
	
	if (tripnode)
	{
		this.lat = tripnode.attr("lat");
		this.lng = tripnode.attr("lng");
		this.tripType = tripnode.attr("tripType");
		this.name = tripnode.attr("tname");
		this.tripId = tripnode.attr("tid");
		this.cityCode = tripnode.attr("tripCode");
		this.pid = tripnode.attr("pid");
		this.src = tripnode;
		this.id = tripnode.attr("tid");
		this.img = tripnode.attr("img");
	}
	
	this.setValueByScenic = function(data)
	{
		this.lat = data.latitude;
		this.lng = data.longitude;
		this.tripType = "1";
		this.name = data.name;
		this.cityCode = data.cityCode;
		this.data = data;
		
	}
	
	this.setvalueByHotel = function(data)
	{
		this.lat = data.latitude;
		this.lng = data.longitude;
		this.tripType = "3";
		this.name = data.hotelName;
		this.cityCode = data.cityCode;
		this.data = data;
	}
}


/**
 * 定义覆盖物-普通景点
 */
function NormalScenic(p, day)
{
    this._point = new BMap.Point(p.lng, p.lat);
    this._data = p;
    this._data.day = day;
    this._day = day;
}

NormalScenic.prototype = new BMap.Overlay();

NormalScenic.prototype.initialize = function(map)
{
    this._map = map;
    var div = $(template("tpl_mark_normalscenic", this._data));
    //var tripdiv = div.find(".normal-scenic-mark");
    switch (this._data.tripType)
    {
        case "1":
            div.addClass("normal-scenic-mark--scenic");
            break;
        case "2":
            div.addClass("normal-scenic-mark--food");
            break;
        case "3":
            div.addClass("normal-scenic-mark--hotel");
            break;
        case "4":
            div.addClass("normal-scenic-mark--trans");
            break;
    }
    this._div = div.get(0);
    this._map.getPanes().labelPane.appendChild(this._div);
    return this._div;
}

NormalScenic.prototype.draw = function()
{
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - $(this._div).width() / 2 + "px";
    this._div.style.top = pixel.y - $(this._div).height() / 2 + "px";
}

NormalScenic.prototype.active = function()
{
    $(this._div).addClass("mark-normal-active");
}

NormalScenic.prototype.unactive = function()
{
    $(this._div).removeClass("mark-normal-active");
}

NormalScenic.prototype.isactive = function()
{
    return $(this._div).hasClass("mark-normal-active");
}

/**
 * 激活景点，当查看该景点的周边时，该景点需要被激活
 */
NormalScenic.prototype.focus = function()
{
    $(this._div).addClass("mark-normal-focus");
}

NormalScenic.prototype.unfocus = function()
{
    $(this._div).removeClass("mark-normal-focus");
}

NormalScenic.prototype.getday = function()
{
    return this._day;
}

/**
 * 定义覆盖物-激活的景点
 */
function ActiveScenic(p)
{
    this._point = new BMap.Point(p.lng, p.lat);
    this._data = p;
}

ActiveScenic.prototype = new BMap.Overlay();

ActiveScenic.prototype.initialize = function(map)
{
    this._map = map;
    var div = $(template("tpl_mark_activescenic", this._data));
    var tripdiv = div.find(".active-scenic-mark");
    switch (this._data.tripType)
    {
        case "1":
            tripdiv.addClass("active-scenic-mark--scenic");
            break;
        case "2":
            tripdiv.addClass("active-scenic-mark--food");
            break;
        case "3":
            tripdiv.addClass("active-scenic-mark--hotel");
            break;
        case "4":
            tripdiv.addClass("active-scenic-mark--trans");
            break;
    }
    this._div = div.get(0);
    this._map.getPanes().labelPane.appendChild(this._div);
    return this._div;
}

ActiveScenic.prototype.draw = function()
{
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - 12 + "px";
    this._div.style.top = pixel.y - 30 + "px";
}

/**
 * 定义临时在地图上显示的marker
 */
function TmpMarker(p)
{
    this._point = new BMap.Point(p.lng, p.lat);
    this._data = p;
    // 景点
    if (p.tripType == "1")
    {
        this._tplname = "tpl_mark_tmp_scenic";
    }
    // 酒店目标
    else
    {
        this._tplname = "tpl_mark_tmp_hotel";
    }
}

TmpMarker.prototype = new BMap.Overlay();

TmpMarker.prototype.initialize = function(map)
{
    this._map = map;
    var div = $(template(this._tplname, this._data));
    var tripdiv = div.find(".tmp-scenic-labelwrap");
    switch (this._data.tripType)
    {
        case "1":
            tripdiv.addClass("tmp-scenic-labelwrap--scenic");
            break;
        case "2":
            tripdiv.addClass("tmp-scenic-labelwrap--food");
            break;
        case "3":
            tripdiv.addClass("tmp-scenic-labelwrap--hotel");
            break;
        case "4":
            tripdiv.addClass("tmp-scenic-labelwrap--trans");
            break;
    }
    this._div = div.get(0);
    this._map.getPanes().labelPane.appendChild(this._div);

    // TODO 添加点击事件

    return this._div;
}

TmpMarker.prototype.draw = function()
{
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - $(this._div).width() / 2 + "px";
    this._div.style.top = pixel.y - $(this._div).height() / 2 + "px";
}

/**
 * 定义酒店覆盖物
 */
function HotelOverlay(p, tplname)
{
    this._point = new BMap.Point(p.lng, p.lat);
    this._data = p;
    if (tplname)
    {
        this._tplname = tplname;
    }
    else
    {
        this._tplname = "tpl_mark_hotel";
    }
}

HotelOverlay.prototype = new BMap.Overlay();

HotelOverlay.prototype.initialize = function(map)
{
    this._map = map;
    var div = $(template(this._tplname, this._data));
    // TODO 初始化天数选择

    this._div = div.get(0);
    this._map.getPanes().labelPane.appendChild(this._div);
//	console.log(this._div);
    return this._div;
}

HotelOverlay.prototype.draw = function()
{
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - $(this._div).width() / 2 + "px";
    this._div.style.top = pixel.y - $(this._div).height() / 2 + "px";
}

HotelOverlay.prototype.active = function()
{
    $(this._div).addClass("mark-hotel--active");
}

HotelOverlay.prototype.unactive = function()
{
    $(this._div).removeClass("mark-hotel--active");
}

HotelOverlay.prototype.focus = function()
{
    $(this._div).addClass("mark-hotel--focus");
}

HotelOverlay.prototype.unfocus = function()
{
    $(this._div).removeClass("mark-hotel--focus");
}

HotelOverlay.prototype.added = function()
{
    $(this._div).addClass("mark-hotel--added");
}

HotelOverlay.prototype.unadd = function()
{
    $(this._div).removeClass("mark-hotel--added");
}

/**
 * 自定义酒店详情对话框
 */
function HotelDlg(days)
{
    this._dayarr = [];
    for (var i = 0; i < days; i++)
    {
        this._dayarr.push(i);
    }
    this._point = new BMap.Point(117.931555, 24.589019);
}

HotelDlg.prototype = new BMap.Overlay();

HotelDlg.prototype.initialize = function(map)
{
    this._map = map;
    var div = $(template("tpl_dialog_hotel", {days : this._dayarr}));

    // TODO 初始化天数选择

    this._div = div.get(0);
    this._map.getPanes().floatPane.appendChild(this._div);
    // 阻止事件冒泡
    this._div.onclick = function(event)
    {
        event.stopPropagation();
    }
    return this._div;
}

HotelDlg.prototype.draw = function()
{
    this.setPosition();
}

HotelDlg.prototype.openHotel = function(point, data)
{
    data.days = this._dayarr;
    var hotelHtml = $(template("tpl_dialog_hoteldetail", data));
    var jqdiv = $(this._div).children();
    jqdiv.empty().append(hotelHtml);
    this._point = point;
    this._data = data;
    this.tripType = 3;
    // 添加点击事件
    hotelHtml.find("a.commint-add-btn").click(function()
    {
        dlg.close();
        var ui = $(this);
        commitAddHotel(ui);
    });

    var dlg = this;
    hotelHtml.find("a.hotel-map-close-btn").click(function()
    {
        dlg.close();
    });

    var position = this._map.pointToOverlayPixel(point);
    // 修改样式酒店详情框
    $(this._div).removeClass("common-mapdialog--scenic").addClass("common-mapdialog--hotel");
    var map = this._map;
    this.setPosition();
    this._div.style.display = "block";
//	var p_point = this._map.overlayPixelToPoint({x: position.x, y: (position.y-160)});
    // 根据所点击的景点，地图移至中心点
//	this._map.panTo(p_point);
}

HotelDlg.prototype.openScenic = function(point, data)
{
    var scenicHtml = $(template("tpl_dialog_scenicdetail", data));
    var position = this._map.pointToOverlayPixel(point);
    this._point = point;
    this._data = data;
    this.tripType = 1;
    // 修改样式成景点详情框
    $(this._div).removeClass("common-mapdialog--hotel").addClass("common-mapdialog--scenic");
    $(this._div).empty().append(scenicHtml);

    var dlg = this;
    scenicHtml.find("a.hotel-map-close-btn").click(function()
    {
        dlg.close();
    });

    this.setPosition();
    this._div.style.display = "block";
}

HotelDlg.prototype.setPosition = function()
{
    var position = this._map.pointToOverlayPixel(this._point);
    var tripType = this.tripType;
    // 景点
    if (tripType == 1)
    {
        this._div.style.left = position.x - $(this._div).width() / 2 - 15 + "px";
        this._div.style.top = position.y - $(this._div).height() - 30 + "px";
    }
    // 酒店
    else
    {
        this._div.style.left = position.x - $(this._div).width() / 2 - 6 + "px";
        this._div.style.top = position.y - $(this._div).height() - 25 + "px";
    }
}

HotelDlg.prototype.close = function()
{
    mapModule.focusHotelMarker(0);
    this._div.style.display = "none";
};


/**
 * 覆盖物的一些事件函数
 */

/**
 * 显示景点详情
 * @param id
 * @param tripType
 */
function showScenicDetail(id)
{
    var data = scenicModule.getScenicDataById(id);
    data.day = planModule.hasschedule(1, id);
    mapModule.showScenicDlg(data);

    stopEventFun();
    return false;
}

/**
 * 地图上添加景点
 */
function mapAddScenic(id)
{
    mapModule.closeScenicDlg();
    scenicModule.addtoPlan(id);
}


/**
 * 显示酒店详情
 * @param id
 * @returns {Boolean}
 */
function showHotelDetail(id)
{
    // TODO 添加点击延迟计算
    hotelModule.showHotelDlg(id);
    mapModule.focusHotelMarker(id);
    event.stopPropagation();
    return false;
}
