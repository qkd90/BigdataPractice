/**
 *  线路查询组件，路线绘制以及景点删除后的处理
 */

$.fn.baiduroute = function(options)
{
	var cont = $(this);
	
	querycar = function(src, startPoint, endPoint)
	{
		var cartransit = new BMap.DrivingRoute(map, 
		{
			onSearchComplete : function(result){ carSearchComplete(result, src); }
		});
		
		cartransit.search(startPoint, endPoint);
	};
	
	carSearchComplete = function(result, src)
	{
		var myTime = "";
		src.parents("li.item-signel:first").attr("traveltype", "2");
		if (result.getNumPlans() > 0)
		{
			var myPlan = result.getPlan(0);
			src.find(".station-path-summary").attr("traveltime", myPlan.getDuration());
			src.find(".station-path-summary").attr("numtime", myPlan.getDuration(false));
			src.find(".station-path-summary").attr("numdist", myPlan.getDistance(true));
			src.find(".station-path-summary").find(".route-distance").html("距离" + myPlan.getDistance(true));
			try
			{
				src.find(".station-path-summary").attr("price", result.taxiFare.day.totalFare);
				myTime = "，打车约" + myPlan.getDuration() + "，花费" + result.taxiFare.day.totalFare + "元";
			}
			catch (e)
			{
				src.find(".station-path-summary").attr("price", "0");
				myTime = "，打车约" + myPlan.getDuration();
			}
			
			src.find(".car-summary").html(myTime);
		}
		else
		{
			// 两点间距离
			var dis = map.getDistance(startPoint, endPoint);
			if (dis > 1000)
			{
				src.find(".station-path-summary").attr("numdist", (dis/1000).toFixed(1) + "公里");
				src.find(".station-path-summary").find(".route-distance").html("距离" + (dis/1000).toFixed(1) + "公里");
			}
			else
			{
				src.find(".station-path-summary").attr("numdist", dis.toFixed(0) + "米");
				src.find(".station-path-summary").find(".route-distance").html("距离" + dis.toFixed(0) + "米");
			}
		}
		
		if (isNull(myTime))
		{
			src.find(".station-path-summary").attr("numtime", "0");
			src.find(".station-path-summary").attr("price", "0");
		}
		
		src.parents("li.item-signel:first").attr("status", "ready");
	};
	
	querywalk = function(src, startPoint, endPoint)
	{
		// 查询驾车信息
		var walktransit = new BMap.WalkingRoute(map, 
		{
			onSearchComplete : function(result){ walkSearchComplete(result, src, startPoint, endPoint); }
		});
		
		walktransit.search(startPoint, endPoint);
	};
	
	walkSearchComplete = function(result, src, startPoint, endPoint)
	{
		var myTime = "";
		if (result.getNumPlans() > 0)
		{
			var myPlan = result.getPlan(0);
			myTime = "，步行约" + myPlan.getDuration();
			
			src.find(".station-path-summary").attr("traveltime", myPlan.getDuration());
			src.find(".station-path-summary").attr("price", "0");
			src.find(".walk-summary").html(myTime);
			
			var dis = myPlan.getDistance(false);
			src.find(".station-path-summary").attr("numtime", dis);
			src.find(".station-path-summary").attr("numdist", myPlan.getDistance(true));
			src.find(".station-path-summary").find(".route-distance").html("距离" + myPlan.getDistance(true));
			
			if (dis > 1000)
			{
                querycar(src, startPoint, endPoint);
			}
			else
			{
				src.parents("li.item-signel:first").attr("traveltype", "3");
				src.parents("li.item-signel:first").attr("status", "ready");
			}
		}
		else
		{
			querycar(src, startPoint, endPoint);
		}
	};
	
	init();
	
	function init()
	{
		var startPoint = new BMap.Point(options.start.lng, options.start.lat);
		var endPoint = new BMap.Point(options.end.lng, options.end.lat);
		querywalk(cont, startPoint, endPoint);
	}
};