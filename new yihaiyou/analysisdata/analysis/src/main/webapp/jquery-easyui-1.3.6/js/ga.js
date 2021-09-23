var Statistic = undefined;
(function($, window, document, undefined) {
    var $window = $(window);

    $.fn.lazyload = function(options) {
        var elements = this;
        var $container;
        var settings = {
            threshold       : 0,
            failure_limit   : 0,
            event           : "scroll",
            effect          : "show",
            container       : window,
            data_attribute  : "original",
            skip_invisible  : true,
            appear          : null,
            load            : null,
            placeholder     : "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC"
        };

        function update() {
            var counter = 0;

            elements.each(function() {
                var $this = $(this);
                if (settings.skip_invisible && !$this.is(":visible")) {
                    return;
                }
                if ($.abovethetop(this, settings) ||
                    $.leftofbegin(this, settings)) {
                        /* Nothing. */
                } else if (!$.belowthefold(this, settings) &&
                    !$.rightoffold(this, settings)) {
                        $this.trigger("appear");
                        /* if we found an image we'll load, reset the counter */
                        counter = 0;
                } else {
                    if (++counter > settings.failure_limit) {
                        return false;
                    }
                }
            });

        }

        if(options) {
            /* Maintain BC for a couple of versions. */
            if (undefined !== options.failurelimit) {
                options.failure_limit = options.failurelimit;
                delete options.failurelimit;
            }
            if (undefined !== options.effectspeed) {
                options.effect_speed = options.effectspeed;
                delete options.effectspeed;
            }

            $.extend(settings, options);
        }

        /* Cache container as jQuery as object. */
        $container = (settings.container === undefined ||
                      settings.container === window) ? $window : $(settings.container);

        /* Fire one scroll event per scroll. Not one scroll event per image. */
        if (0 === settings.event.indexOf("scroll")) {
            $container.bind(settings.event, function() {
                return update();
            });
        }

        this.each(function() {
            var self = this;
            var $self = $(self);

            self.loaded = false;

            /* If no src attribute given use data:uri. */
            if ($self.attr("src") === undefined || $self.attr("src") === false) {
                if ($self.is("img")) {
                    $self.attr("src", settings.placeholder);
                }
            }

            /* When appear is triggered load original image. */
            $self.one("appear", function() {
                if (!this.loaded) {
                    if (settings.appear) {
                        var elements_left = elements.length;
                        settings.appear.call(self, elements_left, settings);
                    }
                    $("<img />")
                        .bind("load", function() {

                            var original = $self.attr("data-" + settings.data_attribute);
                            $self.hide();
                            if ($self.is("img")) {
                            	var paindex = original.lastIndexOf('?'); 
                            	if(paindex != -1){
                            		var para = original.substring(original.lastIndexOf('?')+1);
                            		var maps = para.split('&');
                            		for(var i = 0;i < maps.length;i++){
                            			var items = maps[i].split('=');
                            			if(items[0].toLowerCase() == '_product'){
                            				isProduct = true;
                            				proId = items[1];
                            				var y = $self.offset().top
                            				var x = $self.offset().left
                            				var url = document.URL || '';
                            				var args = 'product=' + proId + "&x="+x + "&y="+y + "&url=" + url;
                            				Statistic.request(args);
                            			}
                            		}
                            		
                            	}
                                $self.attr("src", original);
                            } else {
                                $self.css("background-image", "url('" + original + "')");
                            }
                            $self[settings.effect](settings.effect_speed);

                            self.loaded = true;

                            /* Remove image from array so it is not looped next time. */
                            var temp = $.grep(elements, function(element) {
                                return !element.loaded;
                            });
                            elements = $(temp);

                            if (settings.load) {
                                var elements_left = elements.length;
                                settings.load.call(self, elements_left, settings);
                            }
                        })
                        .attr("src", $self.attr("data-" + settings.data_attribute));
                }
            });

            /* When wanted event is triggered load original image */
            /* by triggering appear.                              */
            if (0 !== settings.event.indexOf("scroll")) {
                $self.bind(settings.event, function() {
                    if (!self.loaded) {
                        $self.trigger("appear");
                    }
                });
            }
        });

        /* Check if something appears when window is resized. */
        $window.bind("resize", function() {
            update();
        });

        /* With IOS5 force loading images when navigating with back button. */
        /* Non optimal workaround. */
        if ((/(?:iphone|ipod|ipad).*os 5/gi).test(navigator.appVersion)) {
            $window.bind("pageshow", function(event) {
                if (event.originalEvent && event.originalEvent.persisted) {
                    elements.each(function() {
                        $(this).trigger("appear");
                    });
                }
            });
        }

        /* Force initial check if images should appear. */
        $(document).ready(function() {
            update();
        });

        return this;
    };

    /* Convenience methods in jQuery namespace.           */
    /* Use as  $.belowthefold(element, {threshold : 100, container : window}) */

    $.belowthefold = function(element, settings) {
        var fold;

        if (settings.container === undefined || settings.container === window) {
            fold = (window.innerHeight ? window.innerHeight : $window.height()) + $window.scrollTop();
        } else {
            fold = $(settings.container).offset().top + $(settings.container).height();
        }

        return fold <= $(element).offset().top - settings.threshold;
    };

    $.rightoffold = function(element, settings) {
        var fold;

        if (settings.container === undefined || settings.container === window) {
            fold = $window.width() + $window.scrollLeft();
        } else {
            fold = $(settings.container).offset().left + $(settings.container).width();
        }

        return fold <= $(element).offset().left - settings.threshold;
    };

    $.abovethetop = function(element, settings) {
        var fold;

        if (settings.container === undefined || settings.container === window) {
            fold = $window.scrollTop();
        } else {
            fold = $(settings.container).offset().top;
        }

        return fold >= $(element).offset().top + settings.threshold  + $(element).height();
    };

    $.leftofbegin = function(element, settings) {
        var fold;

        if (settings.container === undefined || settings.container === window) {
            fold = $window.scrollLeft();
        } else {
            fold = $(settings.container).offset().left;
        }

        return fold >= $(element).offset().left + settings.threshold + $(element).width();
    };

    $.inviewport = function(element, settings) {
         return !$.rightoffold(element, settings) && !$.leftofbegin(element, settings) &&
                !$.belowthefold(element, settings) && !$.abovethetop(element, settings);
     };

    /* Custom selectors for your convenience.   */
    /* Use as $("img:below-the-fold").something() or */
    /* $("img").filter(":below-the-fold").something() which is faster */

    $.extend($.expr[":"], {
        "below-the-fold" : function(a) { return $.belowthefold(a, {threshold : 0}); },
        "above-the-top"  : function(a) { return !$.belowthefold(a, {threshold : 0}); },
        "right-of-screen": function(a) { return $.rightoffold(a, {threshold : 0}); },
        "left-of-screen" : function(a) { return !$.rightoffold(a, {threshold : 0}); },
        "in-viewport"    : function(a) { return $.inviewport(a, {threshold : 0}); },
        /* Maintain BC for couple of versions. */
        "above-the-fold" : function(a) { return !$.belowthefold(a, {threshold : 0}); },
        "right-of-fold"  : function(a) { return $.rightoffold(a, {threshold : 0}); },
        "left-of-fold"   : function(a) { return !$.rightoffold(a, {threshold : 0}); }
    });

})(jQuery, window, document);

(function(){
	Statistic = {
			//server : 'http://master.khadoop.com:19999/collection/visitor/collect.jhtml?',
			server : '/collection/visitor/collect.jhtml?',
			points : [],
			params : {},
			isReady : false,
			binit : false,
			pageloadimg : new Image(1, 1), 
			pagex : 0,
			pagey : 0,
			pageid : '',
			uuid : '',
			CHARS : '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split(''),   
			loadfinish : false,
			getUuid : function (len) {   
				len = len == undefined?16:len;
			    var chars = this.CHARS, uuid = [], i;   
			    var radix = chars.length;
			    for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];   
			    return uuid.join('');   
			},
			setCookie : function(c_name,value,expiredays)
			{
				var exdate=new Date();
				exdate.setDate(exdate.getDate()+expiredays);
				var expires = ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
				var path = ";path=/";
				document.cookie=c_name+ "=" +escape(value) + expires + path;
			},
			delCookie : function(name)
			{
			    var exp = new Date();
			    exp.setTime(exp.getTime() - 1);
			    var cval=getCookie(name);
			    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
			},
			getCookie : function(c_name)//取cookies函数        
			{
				if (document.cookie.length>0){
					  c_start=document.cookie.indexOf(c_name + "=");
					  if (c_start!=-1){ 
						    c_start=c_start + c_name.length+1;
						    c_end=document.cookie.indexOf(";",c_start);
						    if (c_end==-1) c_end=document.cookie.length;
						    return unescape(document.cookie.substring(c_start,c_end));
					  } 
				}
				return "";
			},
			resetUUIDCookie : function(){
				var dayStr = Statistic.getDayStr(new Date());
				var value = Statistic.uuid + ";" + dayStr;				
				Statistic.setCookie('_UUID',value,365);
			},
			request : function(args){
				new Image(1, 1).src = this.server + args+"&_t="+(Math.random()*99999999+1)+"&_pageid="+this.pageid+"&__kuuid="+this.uuid;
			},
			pageload : function(args){
				this.pageloadimg.src = this.server + args+"&_t="+(Math.random()*99999999+1)+"&_pageid="+this.pageid+"&__kuuid="+this.uuid;
				//new Image(1, 1).src = this.server + args+"&_t="+(Math.random()*99999999+1)+"&_pageid="+this.pageid+"&__kuuid="+this.uuid;
				$("img").lazyload({
					effect : "fadeIn",
					threshold : 200
				});
				Statistic.monitorMouse();
			},
			monitorMouse : function(){
				$(document).mousemove(function(e){
					Statistic.pagex = e.pageX;
					Statistic.pagey = e.pageY;
				});
				$(document).click(function(e){ 
					if(e && e.pageX && e.pageY){
						var url = document.URL || '';
						var args = "click=" + e.pageX + "," + e.pageY+"&url="+url;
						args += Statistic.configStr();
						Statistic.request(args);
					}
				});
				setInterval(function(){
					var url = document.URL || '';
					var args = "mouse=" + Statistic.pagex + "," + Statistic.pagey+"&url="+url;
					args += Statistic.configStr();
					Statistic.request(args);
				},10000);
			},
			initparams : function(){
				if(document) {
			        this.params.domain = document.domain || ''; 
			        this.params.url = document.URL || ''; 
			        this.params.title = document.title || ''; 
			        this.params.referrer = document.referrer || ''; 
			        this.params.bodyWidth = document.body.scrollWidth || '0'; 
			        this.params.bodyHeight = document.body.scrollHeight || '0';
			    }   
			    //Window对象数据
			    if(window && window.screen) {
			        this.params.sh = window.screen.height || 0;
			        this.params.sw = window.screen.width || 0;
			        this.params.cd = window.screen.colorDepth || 0;
			    }   
			    //navigator对象数据
			    if(navigator) {
			        this.params.lang = navigator.language || ''; 
			        this.params.cookieEnabled = (navigator.cookieEnabled)? true : false; 
			    }   
			    
			    //拼接参数串
			    var args = ''; 
			    for(var i in this.params) {
			        if(args != '') {
			            args += '&';
			        }   
			        args += i + '=' + encodeURIComponent(this.params[i]);
			    }
			    args += this.configStr();
			    //解析_maq配置
			    
			    return args;
			},
			configStr : function(){
				var args = '';
				if(typeof _maq != "undefined") {
			    	for(var i in _maq) {
			    		if(_maq[i].length < 2) break;
			    		var str = '';
			    		for(var index = 1;index < _maq[i].length;index++){
			    			str += str.length > 0? "," + encodeURIComponent(_maq[i][index]):encodeURIComponent(_maq[i][index]);
			    		}
			    		args += "&"+_maq[i][0] + '=' + str;
			    	}   
			    }
				return args;
			},
			getDayStr : function(now){
				var year = now.getYear() + 1900;
				var month = now.getMonth() + 1;
				var day = now.getDate();
				var hour = now.getHours();
				var minutes = now.getMinutes();
				var second = now.getSeconds();
				return year + "/" + month + "/" + day + " " + hour + ":"+minutes+":"+second;
			},
			init : function(){
			    //Document对象数据
				if(!Statistic.binit){
					Statistic.pageid = Math.random()*99999999+1;
					var idname = '_UUID';
					var uuid = Statistic.getCookie(idname);
					if(uuid){
						var uuidstr = uuid.split(';')[0];
						var timestr = uuid.split(';')[1];
						Statistic.uuid = uuidstr;
//						if(new Date() - new Date(timestr).getTime() > 20 * 60 * 1000){
//							var dayStr = Statistic.getDayStr(new Date());
//							var value = Statistic.getUuid() + ";" + dayStr;
//							Statistic.setCookie(idname,value,20);
//						}
					}else{
						var dayStr = Statistic.getDayStr(new Date());
						Statistic.uuid = Statistic.getUuid();
						var value = Statistic.uuid + ";" + dayStr;
						Statistic.setCookie(idname,value,365);
					}
					var args = Statistic.initparams();
					Statistic.pageload(args);
				}
				Statistic.binit = true;
			}
	};
	
//	$(function(){
//		Statistic.init();
//	});
	if (document.readyState=="complete")
	{
		setTimeout(Statistic.init, 1);	        
	}
	else if (document.addEventListener) {
        // Use the handy event callback
        document.addEventListener( "DOMContentLoaded", Statistic.init, false );

        // A fallback to window.onload, that will always work
        window.addEventListener( "load", Statistic.init, false );

    // If IE event model is used
    }else{
    	document.attachEvent( "onreadystatechange", Statistic.init );

        // A fallback to window.onload, that will always work
        window.attachEvent( "onload", Statistic.init );

        // If IE and not a frame
        // continually check to see if the document is ready
        var top = false;

        try {
            top = window.frameElement == null && document.documentElement;
        } catch(e) {}

        if ( top && top.doScroll ) {
            (function doScrollCheck() {
                if(!Statistic.isReady) {
                    try {
                        top.doScroll("left");
                    } catch(e) {
                        return setTimeout( doScrollCheck,1);
                    }
                    Statistic.init();
                }
            })();
        }
	}
})();