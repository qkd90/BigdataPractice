
function WxUtil()
{
	var ctimestamp;
	var cnonceStr;
	var csignature;
	var hasConfig = false;
	
	
	function checkConfig(cmd)
	{
		// 检查是否包含微信SDK环境
		if (!hasConfig)
		{
			// 如果必须参数中有一个为空，或者签名失效，则重新获取一次签名
			// 重新获取一次签名
			var curl = window.location.href.split('#')[0];
			$.post("/wx/ajax/config", {url : curl}, function(result)
			{
				if (!result.success)
				{
					alert("无法获取配置信息");
				}
				
				var cfg = result.data;
				ctimestamp = cfg.timestamp;
				cnonceStr = cfg.nonceStr;
				csignature = cfg.signature;
				
				wx.config({
					debug: false, 
					appId: CFG.WEIXIN_APPID,
					timestamp: ctimestamp,
					nonceStr: cnonceStr,
					signature: csignature,
					jsApiList: ["openLocation", "getLocation", "chooseImage", "uploadImage", "downloadImage", "onMenuShareTimeline", "onMenuShareAppMessage", "scanQRCode"]
				});
				
				wx.ready(function(){ hasConfig = true; cmd(); });
			});
		}
		else
		{
			cmd();
		}
		
	}
	
	this.openLocation = function(lng, lat, name, address, infoUrl, scale)
	{
		if( !scale || scale == ""){
			scale = 13;
		}
		checkConfig(function()
		{
			wx.openLocation({
				latitude : lat,
				longitude : lng,
				scale: scale,
				name : name,
				address : address,
				infoUrl : "http://www.lvxbang.com"
			});
		});
		
	};
	
	this.getLocation = function(callback)
	{
		checkConfig(function()
		{
			wx.getLocation({
			    success: function (res) 
			    {
			    	callback(res.longitude, res.latitude);
			    },
				fail: function(res)
				{
					callback();
				}
			});
		});
	};
	
	this.chooseImage = function(callback)
	{
		checkConfig(function()
		{
			wx.chooseImage({
			    success: function (res) 
			    {
			    	callback(res.localIds);
			    	
			    },
			    fail : function(res)
			    {
			    	alert(JSON.stringify(res));
			    	callback();
			    },
			    cancel: function(){
			    	callback();
			    }
			});
		});
	};
	
	this.uploadImage = function(local_id, callback)
	{
		wx.uploadImage
		({
	        localId: local_id,
	        isShowProgressTips: 1,
	        success: function (res) 
	        {
	        	callback(res);
	          
	        },
	        fail: function (res) 
	        {
	        	alert(JSON.stringify(res));
	        	callback();
	        }
	   });
	   
	};
	
	this.downloadImage = function()
	{
		//alert("下载");
		if (images.serverId.length === 0) {
	      //alert('请先使用 uploadImage 上传图片');
	      alert('请先上传图片');
	      return;
	    }
	    var i = 0, length = images.serverId.length;
	    images.localId = [];
	    function download() {
	    	wx.downloadImage({
	        serverId: images.serverId[i],
	        isShowProgressTips: 0,
	        success: function (res) {
	          i++;
//	          alert('已下载：' + i + '/' + length);
	          alert(res.localId)
	          images.localId.push(res.localId);
	          alert(images.localId);
	          if (i < length) {
	            download();
	          }
	        },
	        fail: function (res) {
	          alert(JSON.stringify(res));
	        }
	      });
	    	
	    }
	    download();
	   
	};
	
	// 点开显示大图
	this.showImage = function(this_src, index, imgArr)
	{
		// 若是微信浏览器，则使用微信自带方法
		if (isWeiXin())
		{
			wx.previewImage
			({
			    current: this_src,
			    urls: imgArr
			});
		}
		else
		{
			$("#J_mlistimg").addClass("bottom-panel--show");
			
			var imgHtml = template("tpl_mlist-img", {data : imgArr});
			$("#J_gallery_list").html("").append(imgHtml);
			
			$("#gallery_main").tab({afterHandler : function(index)
			{
				loadImg(index-1);
				loadImg(index);
				loadImg(index+1);
			}});
			
			var w = $(".img-list").width()*index;
			$(".gallery-list").css({"-webkit-transform" : "translate3d(-" + w + "px, 0px, 0px)"});
			
			function loadImg(index)
			{
				var node = $("#J_gallery_list").find(".img-list").eq(index).find("img");
				var url = node.attr("curl");
				var flag = node.attr("loadflag");
				if (flag == "true")
				{
					return;
				}
				node.attr("src", url);
				node.attr("loadflag", "true");
			}
			
			$("#J_mlist-close").click(function()
			{
				$("#J_mlistimg").removeClass("bottom-panel--show");
			});
		}
	};
	
	this.shareTimeline = function(map)
	{
		checkConfig(function()
		{
			wx.onMenuShareTimeline({
				title: map.title, // 分享标题
				link: map.link, // 分享链接
			    imgUrl: map.img,
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    }
			});
			
			wx.onMenuShareAppMessage({
				title: map.title, // 分享标题
				desc: '', // 分享描述
				link: map.link, // 分享链接
				imgUrl: map.img,
				type: '', // 分享类型,music、video或link，不填默认为link
				dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    }
			});
		});
	};
	
	this.scanQRCode = function(initscanQRCode, callback)
	{
		checkConfig(function()
		{
			//initscanQRCode();
			wx.scanQRCode({
			    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {
			    	
				    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				    
				    callback(result)
			    },
			    fail: function(res){
			    	callback();
			    	alert(JSON.stringify(res));
			    	//callback(JSON.stringify(res), _this)
			    }
			});
		});
	};
}