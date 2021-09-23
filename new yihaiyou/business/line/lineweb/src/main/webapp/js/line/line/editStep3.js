var editStep3 = {
	limitNum: 6000,	// 字数限制仅文本
	maxLimitNum: 10000,	// 字数限制包含html标签
	lineLightPointK : null,
	orderContextK: null,
	tipContextK: null,
	
	init:function(){
		editStep3.initComp();
		editStep3.initStatus();
		PhotoJs.initSearch("/line/line/getProductImagesList.jhtml");
		PhotoJs.initTarget("editStep3");
		PhotoJs.initParams("/line/lineImg/upload.jhtml", LineConstants.lineDescImg);
        // 儿童标准未填时默认选中“无”
        var rdoChecked = $('input[name=childStandardType]:checked');
        if (!rdoChecked || rdoChecked.length < 1) {
            $('input[name=childStandardType]').first().attr('checked','checked');
        }
	},	
	// 初始化控件
	initComp:function(){
		var productId = $("#productId").val();
		var url = '/line/linedays/getLineDaysByLineId.jhtml';
        $.post(url, { productId: productId},
            function(result){
                if (result != null && result.rows.length > 0) {
                    $.each(result.rows, function(i, dayValue) {
                        var day = i + 1;
                        // 判断是否是自助游
                        if (PRODUCT_ATTR == 'ziyou') {
                            $('#aa').accordion('add', {
                                title: '第'+ day +'天',
                                content: editStep3.addPlanDayZiyou(day, dayValue)
                            });
                            editStep3.planDayHtmlInitZiyou(day);
                        } else {
                            $('#aa').accordion('add', {
                                title: '第'+ day +'天',
                                content: editStep3.addPlanDay(day, dayValue)
                            });
                            editStep3.planDayHtmlInit(day);
                            url = '/line/linedays/getLinePlanDaysBydayId.jhtml';
                            $.post(url, { dayId: dayValue.id},
                                function(result){
                                    if (result != null && result.rows.length > 0) {
                                        $.each(result.rows, function(j, timeValue){
                                            var time = j;
                                            editStep3.addPlanTime(day, timeValue);
                                            var delTimeArr = $(".del-day-time-"+ day +"");
                                            if (delTimeArr.length <= 1) {
                                                $(".del-day-time-"+ day +"").hide();
                                            }
                                            //if (j > 1) {
                                            //	$(".del-day-time-"+ day +"").hide();
                                            //} else {
                                            //	$(".del-day-time-"+ day +"").hide();
                                            //}

                                            url = '/line/linedays/getLinePlanDaysInfoByTimeId.jhtml';
                                            $.post(url, { timeId: timeValue.id},
                                                function(result){
                                                    if (result != null && result.rows) {
                                                        $.each(result.rows, function(n, timeInfoValue){
                                                            var index = n;
                                                            if (timeInfoValue.littleTitle) {
                                                                editStep3.addLittleTitle(day, time, timeInfoValue);
                                                            }

                                                            if (timeInfoValue.titleDesc) {
                                                                editStep3.addTitleContent(day, time, timeInfoValue);
                                                            }
                                                            url = '/line/linedays/getLinePlanDaysInfoImagesByTimeInfoId.jhtml';
                                                            $.post(url, { timeInfoId: timeInfoValue.id},
                                                                function(result){
                                                                    if (result != null) {
                                                                        $.each(result.rows, function(k, timeImageValue){
                                                                            editStep3.addImgDivUl(day, time, timeImageValue.imageUrl, timeImageValue.imageDesc);
                                                                        });
                                                                    }
                                                                });


                                                        });
                                                    }
                                                });

                                        });
                                    }
                                });

                        }
                    });
                } else {
                    $("#planDayId").numberspinner("setValue", 1);
                    // 判断是否是自助游
                    if (PRODUCT_ATTR == 'ziyou') {
                        $('#aa').accordion('add', {
                            title: '第'+ 1 +'天',
                            content: editStep3.addPlanDayZiyou(1)
                        });
                        editStep3.planDayHtmlInitZiyou(1);
                    } else {
                        $('#aa').accordion('add', {
                            title: '第' + 1 + '天',
                            content: editStep3.addPlanDay(1)
                        });
                        editStep3.planDayHtmlInit(1);
                        editStep3.addPlanTime(1);
                    }
                }
            });

		$("#planDayId").numberspinner({
			onSpinUp:function() {
				var day = $("#planDayId").numberspinner("getValue");
				day = Number(day);
                // 判断是否是自助游
                if (PRODUCT_ATTR == 'ziyou') {
                    $('#aa').accordion('add', {
                        title: '第'+ day +'天',
                        content: editStep3.addPlanDayZiyou(day)
                    });
                    editStep3.planDayHtmlInitZiyou(day);
                } else {
                    $('#aa').accordion('add', {
                        title: '第' + day + '天',
                        content: editStep3.addPlanDay(day)
                    });
                    editStep3.planDayHtmlInit(day);
                    editStep3.addPlanTime(day);
                    $(".del-day-time-" + day + "").hide();
                }
			},
			onSpinDown:function() {
				var day = $("#planDayId").numberspinner("getValue");
				day = Number(day);
				if (day >= 1) {
					day = day + 1;
					var panel = $('#aa').accordion('getPanel', '第'+ day +'天');
					if (panel) {
						$('#aa').accordion('remove','第'+ day +'天');
					}
				}
			}
		});

		//var productId = $('#productId').val();
		//富文本 行程亮点
		KindEditor.ready(function(K) {
			lineLightPointK = K.create('#lineLightPointK', {
				resizeType : 1,
				allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
				allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
				afterChange: function() { 
					this.sync(); 
					var hasNum = this.count('text');
					if (hasNum > editStep3.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,editStep3.limitNum);
						this.text(strValue);  
						show_msg("字数过长已被截取，请简化"); 
						//计算剩余字数
						$('textarea[name="lineLightPoint"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="lineLightPoint"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
					}
				}, 
				afterBlur: function() { 
					this.sync(); 
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});

        // 富文本 接待标准
        KindEditor.ready(function(K) {
            var receiveStandardK = K.create('#receiveStandardK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="receiveStandard"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="receiveStandard"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

        // 富文本 沿途景点
        KindEditor.ready(function(K) {
            var accrossScenicK = K.create('#accrossScenicK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="accrossScenic"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="accrossScenic"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });
		
		// 富文本 预订须知内容
		KindEditor.ready(function(K) {
			orderContextK = K.create('#orderContextK', {
				resizeType : 1,
				allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
				allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
				afterChange: function() { 
					this.sync(); 
					var hasNum = this.count('text');
					if (hasNum > editStep3.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,editStep3.limitNum);
						this.text(strValue);  
						show_msg("字数过长已被截取，请简化"); 
						//计算剩余字数
						$('textarea[name="orderContext"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="orderContext"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
					}
				}, 
				afterBlur: function() { 
					this.sync(); 
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});
		
		// 富文本 温馨提示内容
		KindEditor.ready(function(K) {
			tipContextK = K.create('#tipContextK', {
				resizeType : 1,
				allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
				allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
				afterChange: function() { 
					this.sync(); 
					var hasNum = this.count('text');
					if (hasNum > editStep3.limitNum) {
						//超过字数限制自动截取
						var strValue = this.text();
						strValue = strValue.substring(0,editStep3.limitNum);
						this.text(strValue);   
						show_msg("字数过长已被截取，请简化");
						//计算剩余字数
						$('textarea[name="tipContext"]').next().children('.green-bold').html(0);
					} else {
						//计算剩余字数
						$('textarea[name="tipContext"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
					}
				}, 
				afterBlur: function() { 
					this.sync(); 
				},
				items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
			});
		});

		// 富文本 出行须知
        KindEditor.ready(function(K) {
            var tripNoticeK = K.create('#tripNoticeK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="tripNotice"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="tripNotice"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

		// 富文本 特殊限制
        KindEditor.ready(function(K) {
            var specialLimitK = K.create('#specialLimitK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="specialLimit"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="specialLimit"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

		// 富文本 签约方式
        KindEditor.ready(function(K) {
            var signWayK = K.create('#signWayK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="signWay"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="signWay"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

		// 富文本 付款方式
        KindEditor.ready(function(K) {
            var payWayK = K.create('#payWayK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="payWay"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="payWay"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

		// 富文本 违约责任提示
        KindEditor.ready(function(K) {
            var breachTipK = K.create('#breachTipK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="breachTip"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="breachTip"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

        // 富文本 购物项目
        KindEditor.ready(function(K) {
            var shoppingDescK = K.create('#shoppingDescK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="shoppingDesc"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="shoppingDesc"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

        // 富文本 备注
        KindEditor.ready(function(K) {
            var remarkK = K.create('#remarkK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="remark"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="remark"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });

        // 富文本 儿童价特殊说明
        KindEditor.ready(function(K) {
            var childLongRemarkK = K.create('#childLongRemarkK', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > editStep3.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,editStep3.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="childLongRemark"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="childLongRemark"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
            });
        });
		
		// 初始化天及天内行程控件
		//var lineDayNum = $('.lineDay').length;
		//if (lineDayNum > 0) {
		//	for (var i = 1; i <= lineDayNum; i++) {
		//		editStep3.initDayComp("lineDay_"+i, true);
		//		var lineDayPlanNum = $('#lineDay_'+i+' .lineDayPlan').length;
		//		for (var j = 1; j <= lineDayPlanNum; j++) {
		//			editStep3.initDayPlanComp('lineDay_'+i+"_"+j, true);
		//		}
		//	}
		//	var agentFlag = $('#agentFlag').val();
		//	if (!agentFlag) {	// 非代理线路才可编辑
		//		editStep3.setBtnAddDayText();
		//	}
		//}
	},
	// 初始状态
	initStatus : function() {
		//var lineInfo = parent.window.getIfrData('step1');
		//$('#productId').val(lineInfo.productId);
		//$('#productName').html(lineInfo.productName);
		var agentFlag = $('#agentFlag').val();
		if (!agentFlag) {	// 非代理线路才可编辑
			editStep3.setBtnAddDayText();
		}
	},
	// 设置天按钮文本
	setBtnAddDayText : function() {
		var lineDayNum = $('.lineDay').length+1;
		$('#addDay').linkbutton({text:'添加第'+lineDayNum+'天行程'});
	},
	// 添加天，id规则="lineDay_"+index(day)
	doAddDay : function() {
		var lineDayNum = $('.lineDay').length+1;
		editStep3.buildDay({index:lineDayNum});
		editStep3.initDayComp("lineDay_"+lineDayNum, false);
		editStep3.setBtnAddDayText();
	},
	// 删除天
	doDelDay : function(thiz) {
		var lineDayId = $(thiz).parents('table.lineDay').attr('id');
		var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
		var lineDayNum = $('.lineDay').length;
		$('#'+lineDayId).remove();
		for (var i = index+1; i <= lineDayNum; i++) {
			$('#lineDay_'+i + ' input[name=lineDay]').val(i-1);
			$('#lineDay_'+i + ' .orange-bold').html(i-1);
			$('#lineDay_'+i + ' .delDay').html('删除第'+(i-1)+'天行程');
			$('#lineDay_'+i).attr('id', 'lineDay_'+(i-1));
		}
		editStep3.setBtnAddDayText();
	},
	// 添加天内行程，id规则="lineDay_"+index(day)+"_"+index(plan)
	doAddDayPlan : function(thiz) {
		var lineDayId = $(thiz).parents('table.lineDay').attr('id');
		var lineDayPlanNum = $('#'+lineDayId+' .lineDayPlan').length+1;
		editStep3.buildDayPlan(lineDayId, {index:lineDayPlanNum});
		editStep3.initDayPlanComp(lineDayId+"_"+lineDayPlanNum, false);
	},
	// 删除天内行程
	doDelDayPlan : function(thiz) {
		var lineDayId = $(thiz).parents('table.lineDay').attr('id');
		var lineDayPlanId = $(thiz).parents('div.lineDayPlan').attr('id');
		var index = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
		var lineDayPlanNum = $('#'+lineDayId+' .lineDayPlan').length;
		$('#'+lineDayPlanId).remove();
		for (var i = index+1; i <= lineDayPlanNum; i++) {
			$('#'+lineDayId+'_'+i).attr('id', lineDayId+'_'+(i-1));
		}
	},
	// 构建天页面代码，data={lineDay:1}
	buildDay : function(data) {
		var lineDayHtml = '<table class="lineDay" id="lineDay_'+data.index+'"><tr>'
			+'<input name="lineDay" type="hidden" value="'+data.index+'"/>'
		   	+'<td class="label bold-size18">第<span class="orange-bold bold-size18">'+data.index+'</span>天:</td>'
		   	+'<td><input name="dayDesc" style="width:420px;">'
		   	+'<a href="javascript:void(0)" onclick="editStep3.doDelDay(this)" class="line-btn delDay" style="margin-left: 40px;">删除第'+data.index+'天行程</a></td>'
		   	+'</tr><tr>'
		   	+'<td class="label"><font color="red">*&nbsp;</font>行程安排:</td>'
		   	+'<td><textarea name="arrange" style="width:600px; height:120px; visibility: hidden;"></textarea>'
		   	+'<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span></td>'
		   	+'</tr><tr>'
		   	+'<td class="label">用餐:</td>'
		   	+'<td><div class="ck-div"><input type="checkbox" class="left-block" name="meal" value="breakfast"/><span class="ck-label">早餐</span></div>'
		   	+'<div class="ck-div"><input type="checkbox" class="left-block" name="meal" value="lunch"/><span class="ck-label">中餐</span></div>'
		   	+'<div class="ck-div"><input type="checkbox" class="left-block" name="meal" value="supper"/><span class="ck-label">晚餐</span></div></td>'
		   	+'</tr><tr>'
		   	+'<td class="label">住宿:</td>'
		   	+'<td><input name="hotelId" style="width:320px;"><input name="hotelName" type="hidden"></td>'
			+'</tr><tr>'
			+'<td class="label">行程景点:</td>'
			+'<td><div class="lineDayPlanDiv"></div>'
			+'<div style="width: 442px;text-align: right;">'
			+'<a href="javascript:void(0)" onclick="editStep3.doAddDayPlan(this)" class="line-btn-add addDayPlan">添加行程景点</a>'
			+'</div></td>'
			+'</tr></table>';
		$('#lineDayDiv').append(lineDayHtml);
	},
	// 初始化天组件
	initDayComp : function(lineDayId, isPageLoad) {
		$('#'+lineDayId+' input[name=dayDesc]').textbox({validType:'maxLength[200]', required:true});
		$('#'+lineDayId+' input[name=hotelId]').combobox({ 
		  	// url:"/line/line/listSite.jhtml", 
			loader: function(param,success,error){
				if (isPageLoad) {	// 编辑时默认有值，不进行查询
					var data = new Array();
					var hotelId = $('#'+lineDayId+' input[name=hotelId]').val();
					var hotelName = $('#'+lineDayId+' input[name=hotelName]').val();
					data.push({typeId:hotelId,typeName:hotelName});
					success(data);
					isPageLoad = false;
					return false;
				}
				var q = param.q || '';
				if (q.length < 2) {return false;}
				$.ajax({
					url: '/line/line/listSite.jhtml',
					dataType: 'json',
					type: 'POST',
	                data: {q: q, proTypes:'hotel'},
					success: function(data){
						success(data);
					},
	                error: function(){
						//error.apply(this, arguments);
					}
				});
			},
		  	valueField:"typeId", 
		  	textField:"typeName",
			mode: 'remote',
			onChange: function(newValue, oldValue){
				var array = $(this).combobox('getData');
				var row = LineUtil.getByKey(array, 'typeId', newValue);
				var tempLineDayId = $(this).parents('table.lineDay').attr('id');
				if (row) {
					$('#'+tempLineDayId+' input[name=hotelName]').val(row.typeName);
				} else {
					$('#'+tempLineDayId+' input[name=hotelName]').val('');
				}
			}
		});
		// 富文本 行程安排
		var arrangeK = KindEditor.create('#'+lineDayId+' textarea[name=arrange]', {
			resizeType : 1,
			allowPreviewEmoticons : false,
            uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
            fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
			allowImageUpload : true,
            allowFileManager : true,
            filePostName: 'resource',
			afterChange: function() { 
				this.sync(); 
				var hasNum = this.count('text');
				if (hasNum > editStep3.limitNum) {
					//超过字数限制自动截取
					var strValue = this.text();
					strValue = strValue.substring(0,editStep3.limitNum);
					this.text(strValue);   
					show_msg("字数过长已被截取，请简化");
					//计算剩余字数
					$('textarea[name="arrange"]').next().children('.green-bold').html(0);
				} else {
					//计算剩余字数
					$('textarea[name="arrange"]').next().children('.green-bold').html(editStep3.limitNum-hasNum);
				}
			}, 
			afterBlur: function() { 
				this.sync(); 
			},
			items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
		});
		$('#'+lineDayId+' .delDay').linkbutton({plain:'false'});
		$('#'+lineDayId+' .addDayPlan').linkbutton({plain:'false'});
	},
	// 构建天内行程页面代码
	buildDayPlan : function(lineDayId, data) {
		var lineDayPlanHtml = '<div class="lineDayPlan" id="'+lineDayId+'_'+data.index+'" style="margin-bottom: 8px;"><input name="typeId" style="width:320px;">'
			+'<input name="type" type="hidden"><input name="typeName" type="hidden"><input name="cityId" type="hidden"><input name="foodId" type="hidden">'
			+'<a href="javascript:void(0)" onclick="editStep3.doDelDayPlan(this)" class="line-btn delDayPlan" style="margin-left:40px;">删除行程景点</a></div>';
		$('#'+lineDayId+' .lineDayPlanDiv').append(lineDayPlanHtml);
	},
	// 初始化天内行程组件
	initDayPlanComp : function(lineDayPlanId, isPageLoad) {
		$('#'+lineDayPlanId+' input[name=typeId]').combobox({ 
		  	//url:"/line/line/listSite.jhtml", 
			loader: function(param,success,error){
				if (isPageLoad) {	// 编辑时默认有值，不进行查询
					var data = new Array();
					var typeId = $('#'+lineDayPlanId+' input[name=typeId]').val();
					var typeName = $('#'+lineDayPlanId+' input[name=typeName]').val();
					data.push({typeId:typeId,typeName:typeName});
					success(data);
					isPageLoad = false;
					return false;
				}
				var q = param.q || '';
				if (q.length < 2) {return false;}
				$.ajax({
					url: '/line/line/listSite.jhtml',
					dataType: 'json',
					type: 'POST',
	                data: {q: q, proTypes:'scenic,restaurant'},
					success: function(data){
						success(data);
					},
	                error: function(){
						//error.apply(this, arguments);
					}
				});
			},
			//required: true,
		  	valueField:"typeId", 
		  	textField:"typeName",
			mode: 'remote',
			onChange: function(newValue, oldValue){
				var array = $(this).combobox('getData');
				var row = LineUtil.getByKey(array, 'typeId', newValue);
				var tempLineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
				if (row) {
					$('#'+tempLineDayPlanId+' input[name=type]').val(row.type);
					$('#'+tempLineDayPlanId+' input[name=typeName]').val(row.typeName);
					$('#'+tempLineDayPlanId+' input[name=cityId]').val(row.cityId);
					if (row.foodId) {
						$('#'+tempLineDayPlanId+' input[name=foodId]').val(row.foodId);
					} else {
						$('#'+tempLineDayPlanId+' input[name=foodId]').val('');
					}
				} else {
					$('#'+tempLineDayPlanId+' input[name=type]').val('');
					$('#'+tempLineDayPlanId+' input[name=typeName]').val('');
					$('#'+tempLineDayPlanId+' input[name=cityId]').val('');
					$('#'+tempLineDayPlanId+' input[name=foodId]').val('');
				}
			}
		});
		$('#'+lineDayPlanId+' .delDayPlan').linkbutton({plain:'false'});
		
		// 天内行程回车事件
		$('#'+lineDayPlanId+' input').keydown(function(e) {
			if(e.keyCode == 13) {
				// 判断是否是最后一个，最后一个才自动添加编辑框
				var tempLineDayId = $(this).parents('table.lineDay').attr('id');
				var tempLineDayPlanNum = $('#'+tempLineDayId+' .lineDayPlan').length;
				var tempLineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
				if (tempLineDayPlanId == tempLineDayId+'_'+tempLineDayPlanNum) {
					editStep3.doAddDayPlan('#'+tempLineDayPlanId);
					$('#'+tempLineDayId+'_'+(tempLineDayPlanNum+1)).find('input').focus();
				}
			}
		});
	},
	// 更新子表单的name
	updateInputName : function() {
		// 天，手动添加防止额外其他input
		$('.lineDay input[name=lineDay]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		$('.lineDay input[name=dayDesc]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		$('.lineDay textarea[name=arrange]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		$('.lineDay input[name=meal]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		$('.lineDay input[name=hotelId]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		$('.lineDay input[name=hotelName]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var index = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			return 'linedays['+(index-1)+'].'+attr;
		});
		// 天内行程
		$('.lineDay .lineDayPlanDiv input[name=typeId]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			// 如果是空值，不做替换
			var typeId = $(this).val();
			if (!typeId) {
				return attr;
			}
			
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var indexDay = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			var lineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
			var indexPlan = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
			return 'linedays['+(indexDay-1)+'].'+'dayPlan['+(indexPlan-1)+'].'+attr;
		});
		$('.lineDay .lineDayPlanDiv input[name=type]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			// 如果是空值，不做替换
			var type = $(this).val();
			if (!type) {
				return attr;
			}
			
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var indexDay = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			var lineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
			var indexPlan = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
			return 'linedays['+(indexDay-1)+'].'+'dayPlan['+(indexPlan-1)+'].'+attr;
		});
		$('.lineDay .lineDayPlanDiv input[name=typeName]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			// 如果是空值，不做替换
			var typeName = $(this).val();
			if (!typeName) {
				return attr;
			}
			
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var indexDay = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			var lineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
			var indexPlan = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
			return 'linedays['+(indexDay-1)+'].'+'dayPlan['+(indexPlan-1)+'].'+attr;
		});
		$('.lineDay .lineDayPlanDiv input[name=cityId]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			// 如果是空值，不做替换
			var cityId = $(this).val();
			if (!cityId) {
				return attr;
			}
			
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var indexDay = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			var lineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
			var indexPlan = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
			return 'linedays['+(indexDay-1)+'].'+'dayPlan['+(indexPlan-1)+'].'+attr;
		});
		$('.lineDay .lineDayPlanDiv input[name=foodId]').attr("name", function(index, attr) { 
			if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
				return attr;
			}
			// 如果是空值，不做替换
			var foodId = $(this).val();
			if (!foodId) {
				return attr;
			}
			
			var lineDayId = $(this).parents('table.lineDay').attr('id');
			var indexDay = parseInt(lineDayId.substr(lineDayId.lastIndexOf('_')+1));
			var lineDayPlanId = $(this).parents('div.lineDayPlan').attr('id');
			var indexPlan = parseInt(lineDayPlanId.substr(lineDayPlanId.lastIndexOf('_')+1));
			return 'linedays['+(indexDay-1)+'].'+'dayPlan['+(indexPlan-1)+'].'+attr;
		});
		
	},

	addPlanDay: function(day, dayValue) {
		var planDayHtml = '';
		planDayHtml += '<table style="width: 100%;">';
		planDayHtml += '<tr>';
		planDayHtml += '<td class="day-table-td">第'+ day +'天：</td>';

        if (dayValue) {
            planDayHtml += '<input type="hidden" id="day_id_'+ day +'" name="linedays['+ (day-1) +'].id" value="'+ dayValue.id +'">';
        }
        //else {
        //    planDayHtml += '<input type="hidden" id="day_id_'+ day +'" name="linedays['+ (day-1) +'].id" >';
        //}

		planDayHtml += '<input type="hidden" id="day_dayCount_'+ day +'" name="linedays['+ (day-1) +'].lineDay" value="'+ (day) +'">';
		planDayHtml += '<td>';
		if (dayValue) {
			planDayHtml += '<input id="day_shortDesc_'+ day +'" name="linedays['+ (day-1) +'].dayDesc" type="text" class="easyui-textbox" style="width: 500px;" value="'+ dayValue.dayDesc +'">';
		} else {
			planDayHtml += '<input id="day_shortDesc_'+ day +'" name="linedays['+ (day-1) +'].dayDesc" type="text" class="easyui-textbox" style="width: 500px;">';
		}
		planDayHtml += '</td>';
		planDayHtml += '</tr>';
		planDayHtml += '<tr>';
		planDayHtml += '<td class="day-table-td">行程描述：</td>';
		planDayHtml += '<td>';
		if (dayValue) {
			planDayHtml += '<input id="day_desc_'+ day +'" name="linedays['+ (day-1) +'].arrange" type="text" class="easyui-textbox" style="width: 550px; height:100px; " value="'+ dayValue.arrange +'">';
		} else {
			planDayHtml += '<input id="day_desc_'+ day +'" name="linedays['+ (day-1) +'].arrange" type="text" class="easyui-textbox" style="width: 550px; height:100px;">';
		}

		planDayHtml += '</td>';
		planDayHtml += '</tr>';
		planDayHtml += '<tr>';
		planDayHtml += '<td class="day-table-td" style="text-align: center">';
		planDayHtml += '<a href="#" id="add_plan_'+ day +'" class="easyui-linkbutton" plain="false" onclick="editStep3.addPlanTime('+ day +')">添加行程</a>';
		planDayHtml += '</td>';
		planDayHtml += '<td id="day_time_'+ day +'">';
		planDayHtml += '</td>';
		planDayHtml += '</tr>';
		planDayHtml += '<tr>';
		planDayHtml += '<td class="day-table-td">用餐：</td>';
		planDayHtml += '<td>';


		if (dayValue) {
			planDayHtml += '<input type="hidden" id="hid_dayMeals_'+ day +'" name="linedays['+ (day-1) +'].meals" value="'+ dayValue.meals +'" >';
			var meals = dayValue.meals;

			var breakfast = "";
			var lunch = "";
			var dinner = "";

			if (meals) {
				//早餐：含,午餐：敬请自理,晚餐：含
				var mealsArr = meals.split(",");
				var breakfastStr = mealsArr[0];
				breakfast = breakfastStr.substr(3, breakfastStr.length);

				var lunchStr = mealsArr[1];
				lunch = lunchStr.substr(3, lunchStr.length);

				var dinnerStr = mealsArr[2];
				dinner = dinnerStr.substr(3, dinnerStr.length);
			}

			planDayHtml += '<span style="margin-right: 10px;">早餐</span>';
			planDayHtml += '<span style="margin-right: 10px;">' +
			'<input type="text" id="day_breakfast_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " value="'+ breakfast +'">' +
			'</span>';
			planDayHtml += '<span style="margin-right: 10px;">午餐</span>';
			planDayHtml += '<span style="margin-right: 10px;">' +
			'<input type="text" id="day_lunch_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " value="'+ lunch +'">' +
			'</span>';
			planDayHtml += '<span style="margin-right: 10px;">晚餐</span>';
			planDayHtml += '<span>' +
			'<input type="text" id="day_dinner_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " value="'+ dinner +'">' +
			'</span>';

		} else {
			planDayHtml += '<input type="hidden" id="hid_dayMeals_'+ day +'" name="linedays['+ (day-1) +'].meals">';
			planDayHtml += '<span style="margin-right: 10px;">早餐</span>';
			planDayHtml += '<span style="margin-right: 10px;">' +
			'<input type="text" id="day_breakfast_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " data-options="">' +
			'</span>';
			planDayHtml += '<span style="margin-right: 10px;">午餐</span>';
			planDayHtml += '<span style="margin-right: 10px;">' +
			'<input type="text" id="day_lunch_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " data-options="">' +
			'</span>';
			planDayHtml += '<span style="margin-right: 10px;">晚餐</span>';
			planDayHtml += '<span>' +
			'<input type="text" id="day_dinner_'+ day +'" class="easyui-textbox" style="width: 100px; float: left " data-options="">' +
			'</span>';
		}





		planDayHtml += '</td>';
		planDayHtml += '</tr>';
		planDayHtml += '<tr>';
		planDayHtml += '<td class="day-table-td">住宿：</td>';
		planDayHtml += '<td>';
		if (dayValue) {
			planDayHtml += '<input type="text" id="day_hotel_'+ day +'" name="linedays['+ (day-1) +'].hotelName" class="easyui-textbox" style="width: 300px;" value="'+ dayValue.hotelName +'">';
		} else {
			planDayHtml += '<input type="text" id="day_hotel_'+ day +'" name="linedays['+ (day-1) +'].hotelName" class="easyui-textbox" style="width: 300px;" data-options="">';
		}

		planDayHtml += '</td>';
		planDayHtml += '</tr>';
		planDayHtml += '</table>';
		return planDayHtml;
	},
    // 添加自助游行程内容（天说明、行程描述）
    addPlanDayZiyou: function(day, dayValue) {
        var planDayHtml = '';
        planDayHtml += '<table style="width: 100%;">';
        planDayHtml += '<tr>';
        planDayHtml += '<td class="day-table-td">第'+ day +'天：</td>';
        if (dayValue) {
            planDayHtml += '<input type="hidden" id="day_id_'+ day +'" name="linedays['+ (day-1) +'].id" value="'+ dayValue.id +'">';
        }
        //else {
        //    planDayHtml += '<input type="hidden" id="day_id_'+ day +'" name="linedays['+ (day-1) +'].id" >';
        //}

        planDayHtml += '<input type="hidden" id="day_dayCount_'+ day +'" name="linedays['+ (day-1) +'].lineDay" value="'+ (day) +'">';
        planDayHtml += '<td>';
        if (dayValue) {
            planDayHtml += '<input id="day_shortDesc_'+ day +'" name="linedays['+ (day-1) +'].dayDesc" type="text" class="easyui-textbox" style="width: 500px;" value="'+ dayValue.dayDesc +'">';
        } else {
            planDayHtml += '<input id="day_shortDesc_'+ day +'" name="linedays['+ (day-1) +'].dayDesc" type="text" class="easyui-textbox" style="width: 500px;">';
        }
        planDayHtml += '</td>';
        planDayHtml += '</tr>';
        planDayHtml += '<tr>';
        planDayHtml += '<td class="day-table-td">行程描述：</td>';
        planDayHtml += '<td>';
        if (dayValue) {
            planDayHtml += '<textarea id="day_desc_'+ day +'" name="linedays['+ (day-1) +'].arrange" style="width:600px; height:240px; visibility: hidden;">' + dayValue.arrange
                +'</textarea><span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>';
        } else {
            planDayHtml += '<textarea id="day_desc_'+ day +'" name="linedays['+ (day-1) +'].arrange" style="width:600px; height:240px; visibility: hidden;">'
                +'</textarea><span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>';
        }
        planDayHtml += '</td>';
        planDayHtml += '</tr>';
        planDayHtml += '</table>';
        return planDayHtml;
    },
    // 自助游行程内容（天说明、行程描述）控件渲染
    planDayHtmlInitZiyou: function(day) {
        // 天描述
        $("#day_shortDesc_"+ day).textbox({
            prompt:'出发地城市、交通工具、目的地城市，若当日无城市变更，仅需填写行程所在城市即可',
            required:true
        });
        // 富文本 行程安排
        var arrangeK = KindEditor.create('#day_desc_'+day, {
            resizeType : 1,
            allowPreviewEmoticons : false,
            uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
            fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
            allowImageUpload : true,
            allowFileManager : true,
            filePostName: 'resource',
            afterChange: function() {
                this.sync();
                var hasNum = this.count('text');
                if (hasNum > editStep3.limitNum) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0,editStep3.limitNum);
                    this.text(strValue);
                    show_msg("字数过长已被截取，请简化");
                    //计算剩余字数
                    $('#day_desc_'+day).next().children('.green-bold').html(0);
                } else {
                    //计算剩余字数
                    $('#day_desc_'+day).next().children('.green-bold').html(editStep3.limitNum-hasNum);
                }
            },
            afterBlur: function() {
                this.sync();
            },
            items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
        });
    },

	setDayMeals: function(day) {

		var meals = "";

		var breakfast = $("#day_breakfast_"+day+"").textbox("getValue");

		if (breakfast.length > 0) {
			if (meals.length > 0) {
				meals += ",早餐：" + breakfast;
			} else {
				meals += "早餐：" + breakfast;
			}
		}

		var lunch = $("#day_lunch_"+day+"").textbox("getValue");

		if (lunch.length > 0) {
			if (meals.length > 0) {
				meals += ",午餐：" + lunch;
			} else {
				meals += "午餐：" + lunch;
			}
		}

		var dinner = $("#day_dinner_"+day+"").textbox("getValue");

		if (dinner.length > 0) {
			if (meals.length > 0) {
				meals += ",晚餐：" + dinner;
			} else {
				meals += "晚餐：" + dinner;
			}
		}

		$("#hid_dayMeals_"+day+"").val(meals);
	},

	planDayHtmlInit: function(day) {

		$("#day_shortDesc_"+ day +"").textbox({
			prompt:'出发地城市、交通工具、目的地城市，若当日无城市变更，仅需填写行程所在城市即可',
			required:true
		});
		$("#day_desc_"+ day +"").textbox({
			prompt:'请在这里输入行程描述，概述当日行程内容，内容不能超过1000字',
            multiline:true,
            validType:'maxLength[1000]'
		});
		$("#day_breakfast_"+ day +"").textbox({
			prompt:'敬请自理或含',
			//required:true,
			onChange: function() {
				editStep3.setDayMeals(day);
			}
		});
		$("#day_lunch_"+ day +"").textbox({
			prompt:'敬请自理或含',
			//required:true,
			onChange: function() {
				editStep3.setDayMeals(day);
			}
		});
		$("#day_dinner_"+ day +"").textbox({
			prompt:'敬请自理或含',
			//required:true,
			onChange: function() {
				editStep3.setDayMeals(day);
			}
		});
		$("#day_hotel_"+ day +"").textbox({
			prompt:'请在这里输入当天住宿情况'
		});

		$("#add_plan_"+ day +"").linkbutton({
			//iconCls: 'icon-add'
			//plain:true
		});

	},

	/**
	 * 新增时间节点
	 * @param day
	 */
	addPlanTime: function(day, timeValue) {
		var timeTableArr = $(".time-class-"+day+"");
		var time = 0;
		if (timeTableArr.length > 0) {
			var lastIndex = timeTableArr.last().attr("data-index");
			time = Number(lastIndex)+1;
		}
		var dayTimeHtml = '';
		dayTimeHtml += '<div class="table-time time-class-'+ day +'" id="table_time_'+ day +'_'+ time +'" data-index="'+ time +'">';
		dayTimeHtml += '<table class="day-time-table">';
		dayTimeHtml += '<tr>';
		dayTimeHtml += '<td class="none-border day-time-table-td">';
		dayTimeHtml += '行程时间：';
		dayTimeHtml += '</td>';
		dayTimeHtml += '<td class="none-border" style="padding-top: 5px;">';

		if (timeValue) {
			dayTimeHtml += '<input id="day_plan_time_'+ day +'_'+ time +'" type="text" class="easyui-textbox" value="'+ timeValue.timeNode +'" style="width: 100px; margin-right: 15px; margin-top:10px;">';
		} else {
			dayTimeHtml += '<input id="day_plan_time_'+ day +'_'+ time +'" type="text" class="easyui-textbox"  style="width: 100px; margin-right: 15px; margin-top:10px; ">';
		}
		/*dayTimeHtml += '&nbsp;&nbsp;';
		if (timeValue) {
			dayTimeHtml += '<input id="day_plan_time_content_'+ day +'_'+ time +'" type="text" class="easyui-textbox" value="'+ timeValue.timeDesc +'" style="width: 200px; height:100px">';
		} else {
			dayTimeHtml += '<input id="day_plan_time_content_'+ day +'_'+ time +'" type="text" class="easyui-textbox" style="width: 200px; height:100px">';
		}*/

		dayTimeHtml += '</td>';
		dayTimeHtml += '<td class="none-border">';
		dayTimeHtml += '<i class="iconfont iconfont-hover del-day-time-'+ day +'" onclick="editStep3.delPlan('+ day +', '+ time +')">&#xe724;</i>';
		dayTimeHtml += '</td>';
		dayTimeHtml += '</tr>';

        dayTimeHtml += '<tr>';
        dayTimeHtml += '<td class="none-border day-time-table-td">';
        dayTimeHtml += '</td>';
        dayTimeHtml += '<td class="none-border">';

        if (timeValue) {
            dayTimeHtml += '<input id="day_plan_time_content_'+ day +'_'+ time +'" type="text" class="easyui-textbox" value="'+ timeValue.timeDesc +'" style="width: 500px; height:100px">';
        } else {
            dayTimeHtml += '<input id="day_plan_time_content_'+ day +'_'+ time +'" type="text" class="easyui-textbox" style="width: 500px; height:100px">';
        }

        dayTimeHtml += '</td>';
        dayTimeHtml += '<td class="none-border">';
        dayTimeHtml += '</td>';
        dayTimeHtml += '</tr>';

		dayTimeHtml += '<tr id="day_time_btn_'+ day +'_'+ time +'" >';
		dayTimeHtml += '<td class="none-border day-time-table-td" colspan="1">';
		dayTimeHtml += '</td>';
		dayTimeHtml += '<td class="none-border" style="padding:5px;">';
		dayTimeHtml += '<a href="#" id="day_plan_btn_littleTitle_'+ day +'_'+ time +'" class="easyui-linkbutton" style="margin-right: 10PX;" onclick="editStep3.addLittleTitle('+ day +', '+ time +')" data-options="">添加小标题</a>';
		dayTimeHtml += '<a href="#" id="day_plan_btn_titleContent_'+ day +'_'+ time +'" class="easyui-linkbutton" style="margin-right: 10PX;" onclick="editStep3.addTitleContent('+ day +', '+ time +')" data-options="">添加小标题正文</a>';
		dayTimeHtml += '<a href="#" id="day_plan_btn_image_'+ day +'_'+ time +'" class="easyui-linkbutton" style="margin-right: 10PX;" onclick="editStep3.addImgBtn('+ day +', '+ time +')" data-options="">添加图片</a>';
		dayTimeHtml += '</td>';
		dayTimeHtml += '<td class="none-border" colspan="2">';
		dayTimeHtml += '</td>';
		dayTimeHtml += '</tr>';
		dayTimeHtml += '</table>';
		dayTimeHtml += '</div>';

		$("#day_time_"+ day +"").append(dayTimeHtml);

		$("#day_plan_time_"+ day +"_"+ time +"").timespinner({
			prompt:'如：7:00',
            min:'00:00'
		});
		$("#day_plan_time_content_"+ day +"_"+ time +"").textbox({
			prompt:'请在这里输入该时间段的行程描述，描述不能超过1000字',
            multiline:true,
            validType:'maxLength[1000]'
		});

		$("#day_plan_btn_littleTitle_"+ day +"_"+ time +"").linkbutton({
			//iconCls: 'icon-add'
		});
		$("#day_plan_btn_titleContent_"+ day +"_"+ time +"").linkbutton({
			//iconCls: 'icon-add'
		});

		$("#day_plan_btn_image_"+ day +"_"+ time +"").linkbutton({
			//iconCls: 'icon-add'
		});

		var dayTimeArr = $(".day-time-table");

		if (dayTimeArr.length > 1) {
			$(".del-day-time-"+ day +"").show();
		} else {
			$(".del-day-time-"+ day +"").hide();
		}

	},

	/**
	 * 新增小标题
	 * @param day
	 * @param time
	 */
	addLittleTitle: function(day, time, timeInfoValue) {

		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");
		$.each(hideCheckedArr, function(i, perValue){
			var per = $(perValue);
			per.val(0);
		});

		var index = 0;
		var hidLittleTitle = $(".input_little_title_"+ day +"_"+ time +"");
		var lastTime = 0;
		if (hidLittleTitle.length > 0) {
			lastTime = $(hidLittleTitle[hidLittleTitle.length - 1]).val();
			lastTime = Number(lastTime);
			index = lastTime + 1;

			var littleTitleHtml = '';
			littleTitleHtml += '<tr class="time-add-title little_title time_'+ day +'_'+ time + '_'+ index +'" onclick="editStep3.checkTr('+ day +','+ time +','+ index +')" id="day_plan_title_tr_' + day + '_'+ time +'_'+ index +'">';
			littleTitleHtml += '<td class="none-border day-time-table-td">';
			littleTitleHtml += '小标题：';
			littleTitleHtml += '<input type="hidden" value="1" id="hid_checked_'+ day +'_'+ time +'_'+ index +'" name="hide_checked_'+ day +'_'+ time +'" data-index="'+ index +'">';
			littleTitleHtml += '</td>';
			littleTitleHtml += '<td class="none-border" style="padding-top: 7px; padding-bottom: 5px;">'

			if (timeInfoValue) {
				littleTitleHtml += '<input id="day_plan_title_'+ day +'_'+ time + '_'+ index +'"  type="text" class="easyui-textbox" style="width: 350px; " value="'+ timeInfoValue.littleTitle +'">';
			} else {
				littleTitleHtml += '<input id="day_plan_title_'+ day +'_'+ time + '_'+ index +'"  type="text" class="easyui-textbox" style="width: 350px; " data-options="">';
			}
			littleTitleHtml += '</td>';
			littleTitleHtml += '<td class="none-border">';
			littleTitleHtml += '<input type="hidden" class="input_little_title_'+ day +'_'+ time +'" value="'+ index +'">';
			littleTitleHtml += '<i class="iconfont iconfont-hover" id="del_title_'+ day +'_'+ time +'_'+ index +'" onclick="editStep3.delPlanTitle('+ day +', '+ time +', '+ index +')">&#xe74b;</i>';
			littleTitleHtml += '</td>';
			littleTitleHtml += '</tr>';
			var timeTrArrs = $(".time_"+ day +"_"+ time +"_"+ (index-1) +"");
			//var divImgHtmlTrArrs = $("#day_plan_img_tr_"+ day +"_"+ time +"");
			if (timeTrArrs.length > 0) {
				timeTrArrs.last().after(littleTitleHtml);
			} else {
				$(".time_"+ day +"_"+ time +"_"+ index +"").last().after(littleTitleHtml);
			}
		} else {
			var littleTitleHtml = '';
			littleTitleHtml += '<tr class="time-add-title little_title time_'+ day +'_'+ time +'_'+ index +'" onclick="editStep3.checkTr('+ day +','+ time +','+ index +')" id="day_plan_title_tr_' + day + '_'+ time +'_'+ index +'">';
			littleTitleHtml += '<td class="none-border day-time-table-td">';
			littleTitleHtml += '小标题：';
			littleTitleHtml += '<input type="hidden" value="1" id="hid_checked_'+ day +'_'+ time +'_'+ index +'" name="hide_checked_'+ day +'_'+ time +'" data-index="'+ index +'">';
			littleTitleHtml += '</td>';
			littleTitleHtml += '<td class="none-border" style="padding-top: 7px; padding-bottom: 5px;">'

			if (timeInfoValue) {
				littleTitleHtml += '<input id="day_plan_title_'+ day +'_'+ time + '_'+ index +'"  type="text" class="easyui-textbox" style="width: 350px; " value="'+ timeInfoValue.littleTitle +'">';
			} else {
				littleTitleHtml += '<input id="day_plan_title_'+ day +'_'+ time + '_'+ index +'"  type="text" class="easyui-textbox" style="width: 350px; ">';
			}


			littleTitleHtml += '</td>';
			littleTitleHtml += '<td class="none-border">';
			littleTitleHtml += '<input type="hidden" class="input_little_title_'+ day +'_'+ time +'" value="'+ index +'">';
			littleTitleHtml += '<i class="iconfont iconfont-hover" id="del_title_'+ day +'_'+ time +'_'+ index +'" onclick="editStep3.delPlanTitle('+ day +', '+ time +', '+ index +')">&#xe74b;</i>';
			littleTitleHtml += '</td>';
			littleTitleHtml += '</tr>';
			$("#day_time_btn_" + day +"_" + time +"").after(littleTitleHtml);
		}
		$("#day_plan_title_"+ day +"_"+ time +"_"+ index +"").textbox({
			prompt:'请在这里输入行程小标题',
            required:true
		});

	},

	/**
	 * 新增小标题内容
	 * @param day
	 * @param time
	 */
	addTitleContent: function(day, time, timeInfoValue) {

		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");
		var index = 0;
		$.each(hideCheckedArr, function(i, perValue){
			var per = $(perValue);
			var value = per.val();
			if (value == 1) {
				index = per.attr("data-index");
				return false;
			}
		});



		var hidTitleContent = $(".input_title_content_"+ day +"_"+ time +"_"+ index +"");
		var hidLittleTitle = $(".input_little_title_"+ day +"_"+ time +"");

		if (hidLittleTitle.length > 0) {
			if (hidTitleContent.length < 1) {
				var titleContentHtml = '';
				titleContentHtml += '<tr class="time-add-title time_'+ day +'_'+ time +'_'+ index +'" onclick="editStep3.checkTr('+ day +','+ time +','+ index +')" id="day_plan_content_tr_'+ day +'_'+ time +'_'+ index +'">';
				titleContentHtml += '<td class="none-border day-time-table-td">';
				titleContentHtml += '小标题正文：';
				titleContentHtml += '</td>';
				titleContentHtml += '<td class="none-border" style="padding-top: 7px; padding-bottom: 5px;">';

				if (timeInfoValue) {
					titleContentHtml += '<textarea id="day_plan_content_'+ day +'_'+ time +'_'+ index +'" name="day_plan_content_'+ day +'_'+ time + '_' + index +'" style="width:500px; height:120px;">'+ timeInfoValue.titleDesc +'</textarea>';
				} else {
					titleContentHtml += '<textarea id="day_plan_content_'+ day +'_'+ time +'_'+ index +'" name="day_plan_content_'+ day +'_'+ time + '_' + index +'" style="width:500px; height:120px;"></textarea>';
				}

				titleContentHtml += '<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>';
				titleContentHtml += '</td>';
				titleContentHtml += '<td class="none-border">';
				titleContentHtml += '<input type="hidden" class="input_title_content_'+ day +'_'+ time +'_'+ index +'" value="'+ index +'">';
				titleContentHtml += '<i class="iconfont iconfont-hover" onclick="editStep3.delPlanContent('+ day +', '+ time +','+ index +')">&#xe74b;</i>';
				titleContentHtml += '</td>';
				titleContentHtml += '</tr>';
				$("#day_plan_title_tr_" + day + "_"+ time +"_"+ index +"").after(titleContentHtml);

				// 富文本 预订须知内容
				var editor = KindEditor.create('#day_plan_content_'+ day + '_' + time + '_'+ index, {
					resizeType : 1,
					allowPreviewEmoticons : false,
					uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
					fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
					allowImageUpload : true,
					allowFileManager : true,
					filePostName: 'resource',
					afterChange: function() {
						this.sync();
						var hasNum = this.count('text');

						if (hasNum > editStep3.limitNum) {
							//超过字数限制自动截取
							var strValue = this.text();
							strValue = strValue.substring(0, editStep3.limitNum);
							this.text(strValue);
							show_msg("字数过长已被截取，请简化");
							//计算剩余字数
							$('#day_plan_content_'+ day + '_' + time + '_'+ index).next().children('.green-bold').html(0);
						} else {
							//计算剩余字数
							$('#day_plan_content_'+ day + '_' + time + '_'+ index).next().children('.green-bold').html(editStep3.limitNum-hasNum);
						}
					},
					afterBlur: function() {
						this.sync();
					},
					items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
				});

				$("#del_title_"+ day +"_"+ time +"_"+ index +"").hide();
			} else {
				show_msg("请添加小标题！");
			}
		} else {
			show_msg("请添加小标题！");
		}
	},


	/**
	 * 检测行是否被选中
	 * @param day
	 * @param time
	 * @param index
	 */
	checkTr: function(day, time, index) {
		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");
		$.each(hideCheckedArr, function(i, perValue){
			var per = $(perValue);
			var dataIndex = per.attr("data-index");
			dataIndex = Number(dataIndex);
			if (dataIndex == index) {
				per.val(1);
			} else {
				per.val(0);
			}
		});
	},

	/**
	 * 新增图片组
	 * @param day
	 * @param time
	 */
	addImgBtn: function(day, time) {

		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");

		if (hideCheckedArr.length > 0) {

			var index = 0;
			$.each(hideCheckedArr, function(i, perValue){
				var per = $(perValue);
				var value = per.val();
				if (value == 1) {
					index = per.attr("data-index");
					return false;
				}
			});

			//var dialogHtml = PhotoJs.createPhotDiv();
            var dialogHtml = PhotoJs.createPhotMoreDiv();


			$("body").append(dialogHtml);
			$("body").mask();
			$("#hid_daytime").attr("day", day);
			$("#hid_daytime").attr("time", time);
            PhotoJs.initDiyUpload();
			//PhotoJs.createUploadBtn();
		} else {
			show_msg("请添加小标题等内容。");
		}


	},

	addImgDivUl: function(day, time, imgUrl, imgDesc) {

		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");

		var index = 0;
		$.each(hideCheckedArr, function(i, perValue){
			var per = $(perValue);
			var value = per.val();
			if (value == 1) {
				index = per.attr("data-index");
				return false;
			}
		});

		var imgIndex = 0;

		var imgArr = $(".hid_img_"+ day +"_"+ time +"_"+ index +"");
		if (imgArr.length > 0) {
			var lastIndex = imgArr.last().val();
			imgIndex = Number(lastIndex) + 1;
		}

		var divImgHtmlTrArrs = $("#day_plan_img_tr_"+ day +"_"+ time +"_"+ index +"");
		if (divImgHtmlTrArrs.length > 0) {
			editStep3.addImgDetail(day, time, index, imgIndex, imgUrl, imgDesc);
		} else {
			var divImgHtmlTr = '';
			divImgHtmlTr += '<tr class="time-add-title time_'+ day +'_'+ time +'_'+ index +'" onclick="editStep3.checkTr('+ day +','+ time +','+ index +')" id="day_plan_img_tr_'+ day +'_'+ time +'_'+ index +'">';
			divImgHtmlTr += '<td class="none-border day-time-table-td" colspan="1">';
			divImgHtmlTr += '</td>';
			divImgHtmlTr += '<td class="none-border">';
			divImgHtmlTr += '<div style="width: 100%;" id="div_imgs_'+ day +'_'+ time +'_'+ index +'">';
			divImgHtmlTr += '</div>';
			divImgHtmlTr += '</td>';
			divImgHtmlTr += '<td class="none-border">';
			divImgHtmlTr += '<input type="hidden" class="day_plan_img_tr_'+ day +'_'+ time +'" value="'+ index +'">';
			divImgHtmlTr += '</td>';
			divImgHtmlTr += '</tr>';
			$(".time_"+ day +"_"+ time +"_"+ index +"").last().after(divImgHtmlTr);
			$("#del_title_"+ day +"_"+ time +"_"+ index +"").hide();
			editStep3.addImgDetail(day, time, index, imgIndex, imgUrl, imgDesc);
		}

	},



	/**
	 * 新增单个图片
	 * @param day
	 * @param time
	 * @param index
	 * @param imgIndex
	 */
	addImgDetail: function(day, time, index, imgIndex, imgUrl, imgDesc) {
		var divImgHtml = '';
		divImgHtml += '<div id="day_plan_img_div_'+ day +'_'+ time +'_'+ index +'_'+ imgIndex +'" style="float: left;" class="div_img_level_'+ day +'_'+ time +'_'+ index +'">';
		divImgHtml += '<div style="margin-bottom: 10px;">';
		divImgHtml += '<div style="">';
		divImgHtml += '<input type="hidden" class="hid_img_'+ day +'_'+ time +'_'+ index +'" value="'+ imgIndex +'">';
		//divImgHtml += '<i class="iconfont iconfont-hover" style="color: red" onclick="editStep3.delImgDiv('+ day +', '+ time +', '+ index +','+ imgIndex +')">&#xe724;</i>';
		divImgHtml += '</div>';
		divImgHtml += '<div style="position:relative; width: 150px; height: 200px;margin: 10px;">';
		divImgHtml += '<img id="day_plan_img_'+ day +'_'+ time +'_'+ index +'_'+ imgIndex +'" src="'+ imgUrl +'" style="width: 150px; height: 160px;border: 1px solid #ccc;">';
		divImgHtml += '<i class="iconfont iconfont-hover" style="color: red; position:absolute; top:-7px; right:-2px; z-index:99;" onclick="editStep3.delImgDiv('+ day +', '+ time +', '+ index +','+ imgIndex +')">&#xe724;</i>';
		divImgHtml += '<div class="editDivClass" contentEditable="true" id="img_desc_'+ day +'_'+ time +'_'+ index +'_'+ imgIndex +'" style="text-align: center; width: 150px; margin-top: 10px;">'+ imgDesc +'</div>';
		divImgHtml += '<input type="hidden" id="hid_imgUrl_'+ day +'_'+ time +'_'+ index +'_'+ imgIndex +'" value="'+ imgUrl +'">';
		divImgHtml += '</div>';

		divImgHtml += '</div>';
		divImgHtml += '</div>';
		$("#div_imgs_"+ day +"_"+ time +"_"+ index +"").append(divImgHtml);
	},

	delPlan: function(day, time) {

		$("#table_time_"+ day +"_"+ time +"").remove();

		var dayTimeArr = $(".time-class-"+ day +"");

		if (dayTimeArr.length > 1) {
			$(".del-day-time-"+ day +"").show();
		} else {
			$(".del-day-time-"+ day +"").hide();
		}
	},

	delImgDiv: function(day, time, index, imgIndex) {
		var imgFirstLevel = $(".div_img_level_" + day + "_" + time+ "_" + index +"");
		if (imgFirstLevel.length > 1) {
			$("#day_plan_img_div_" + day + "_" + time+ "_" + index +"_" + imgIndex+ "").remove();
		} else {
			$("#day_plan_img_tr_"+ day + "_" + time + "_" + index +"").remove();

			var contentTr = $("#day_plan_content_tr_"+ day +"_"+ time +"_"+ index +"");
			if (contentTr.length <= 0) {
				$("#del_title_"+ day +"_"+ time +"_"+ index +"").show();
			}
		}

	},

	delPlanTitle: function(day, time, index) {
		$("#day_plan_title_tr_"+ day + "_" + time + "_"+ index +"").remove();
	},

	delPlanContent: function(day, time, index) {
		$("#day_plan_content_tr_"+ day + "_" + time + "_"+ index +"").remove();
		var imgTr = $("#day_plan_img_tr_"+ day + "_" + time + "_" + index +"");
		if (imgTr.length <= 0) {
			$("#del_title_"+ day +"_"+ time +"_"+ index +"").show();
		}
	},

	/**
	 * 获取整个行程的内容
	 */
	doGetData: function() {
		var days = $('#planDayId').numberspinner("getValue");
		var dayDataArr = [];
		for (var i = 0; i< days; i++) {
			var dayJson = {};
			var day = i + 1;
            var day_id = $("#day_id_"+ day +"").val();
            dayJson['id'] = day_id;
			var day_dayCounnt = $("#day_dayCount_"+ day +"").val();
			dayJson['lineDay'] = day_dayCounnt;
			var dayDesc = $("#day_shortDesc_"+ day +"").textbox("getValue");
			dayJson['dayDesc'] = dayDesc;
            // 判断是否是自助游
            if (PRODUCT_ATTR == 'ziyou') {
                var arrange = $("#day_desc_"+ day +"").val();
                dayJson['arrange'] = arrange;
            } else {
                var arrange = $("#day_desc_"+ day +"").textbox("getValue");
                dayJson['arrange'] = arrange;
                var meals = $("#hid_dayMeals_"+ day +"").val();
                dayJson['meals'] = meals;
                var hotelName = $("#day_hotel_"+ day +"").textbox("getValue");
                dayJson['hotelName'] = hotelName;
                dayJson['timeList'] = editStep3.doGetDayTimeData(day);
            }
			dayDataArr.push(dayJson);
		}
		var dayStr = JSON.stringify(dayDataArr);
		$("#planDetailId").val(dayStr);
	},

	/**
	 * 获取每一天的行程内容
	 * @param day
	 * @returns {Array}
	 */
	doGetDayTimeData: function(day){
		var timeArr = [];
		var timeTableArr = $(".time-class-"+day+"");

		$.each(timeTableArr, function(i, perValue) {
			var timeJson = {};
			var per = $(perValue);
			var time = per.attr("data-index");
			var dayTime = $("#day_plan_time_"+ day +"_"+ time +"").textbox("getValue");
			timeJson['timeNode'] = dayTime;
			var timeDesc = $("#day_plan_time_content_"+ day +"_"+ time +"").textbox("getValue");
			timeJson['timeDesc'] = timeDesc;
			timeJson['timeInfoList'] = editStep3.doGetTimeNodeData(day, time);
			timeArr.push(timeJson);
		});
		return timeArr;
	},

	/**
	 * 获取每天中时间节点的内容
	 * @param day
	 * @param time
	 * @returns {Array}
	 */
	doGetTimeNodeData: function(day, time) {

		var hideCheckedArr = $(":input[name='hide_checked_"+ day +"_"+ time +"']");
		var timeNodeArr = [];
		$.each(hideCheckedArr, function(i, perValue){
			var nodeJson = {};
			var per = $(perValue);
			var index = per.attr("data-index");
			var littleTitle = $("#day_plan_title_"+ day +"_"+ time +"_"+ index +"").textbox("getValue");
			nodeJson['littleTitle']=littleTitle;
			var littleContent = $("#day_plan_content_"+ day +"_"+ time +"_"+ index +"").val();
			nodeJson['titleDesc']=littleContent;
			nodeJson['imageList'] = editStep3.doGetImages(day, time, index);
			timeNodeArr.push(nodeJson);
		});
		return timeNodeArr;
	},

	/**
	 * 获取每个时间节点所添加的图片
	 * @param day
	 * @param time
	 * @param index
	 * @returns {Array}
	 */
	doGetImages:function(day, time, index) {
		var timeTableArr = $(".hid_img_"+ day +"_"+ time +"_"+ index +"");
		var imgArr = [];
		$.each(timeTableArr, function(i, perValue) {
			var imgJson = {};
			var per = $(perValue);
			var imgIndex = per.val();
			var imgDesc = $("#img_desc_"+ day +"_"+ time +"_"+ index +"_"+ imgIndex +"").html();
			imgJson['imageDesc'] = imgDesc;
			var imgUrl = $("#hid_imgUrl_"+ day +"_"+ time +"_"+ index +"_"+ imgIndex +"").val();
			imgJson['imageUrl'] = imgUrl;
			imgArr.push(imgJson);
		});
		return imgArr;
	},


	// 下一步
	nextGuide:function(){
		// 保存表单
		$('#editForm').form('submit', {
			url : "/line/lineexplain/saveLineexplain.jhtml",
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
					// 天数 验证
					//var lineDays = $('.lineDay');
					//if (lineDays.length <= 0) {
					//	show_msg("行程不能为空");
					//	return false;
					//}
					// 天内行程数 验证 /***取消验证（客户要求）-2016-03-22***/
					//for (var i = 0; i < lineDays.length; i++) {
					//	var lineDayPlans = $('#'+lineDays[i].id+' .lineDayPlan');
					//	if (lineDayPlans.length <= 0) {
					//		show_msg("天内行程不能为空");
					//		return false;
					//	}
					//}

					// 行程亮点 字段验证 
					var lineLightPoint = $('textarea[name$="lineLightPoint"]').val();
					if (lineLightPoint && lineLightPoint.length > editStep3.maxLimitNum) {
						show_msg("行程亮点html标签过多，请简化");
						return false;
					}
					// 行程安排 字段验证
                    if (PRODUCT_ATTR == 'ziyou') {
                        $('textarea[name$="arrange"]').each(function (index, element) {
                            var arrange = $(element).val();
                            if (!arrange) {
                                //show_msg("行程安排不能为空");
                                //return false;
                            } else if (arrange.length > editStep3.maxLimitNum) {
                                show_msg("行程安排html标签过多，请简化");
                                return false;
                            }
                        });
                    }
                    // 接待标准 字段验证
                    var receiveStandard = $('textarea[name$="receiveStandard"]').val();
                    if (receiveStandard && receiveStandard.length > editStep3.maxLimitNum) {
                        show_msg("接待标准内容html标签过多，请简化");
                        return false;
                    }
                    // 沿途景点 字段验证
                    var accrossScenic = $('textarea[name$="accrossScenic"]').val();
                    if (accrossScenic && accrossScenic.length > editStep3.maxLimitNum) {
                        show_msg("沿途景点内容html标签过多，请简化");
                        return false;
                    }
					// 预订须知内容 字段验证 
					var orderContext = $('textarea[name$="orderContext"]').val();
					if (orderContext && orderContext.length > editStep3.maxLimitNum) {
						show_msg("预订须知内容html标签过多，请简化");
						return false;
					}
					// 安全提示 字段验证
					var tipContext = $('textarea[name$="tipContext"]').val();
					if (tipContext && tipContext.length > editStep3.maxLimitNum) {
						show_msg("安全提示内容html标签过多，请简化");
						return false;
					}
                    // 出行须知 字段验证
                    var tripNotice = $('textarea[name$="tripNotice"]').val();
                    if (tripNotice && tripNotice.length > editStep3.maxLimitNum) {
                        show_msg("出行须知内容html标签过多，请简化");
                        return false;
                    }
                    // 特殊限制 字段验证
                    var specialLimit = $('textarea[name$="specialLimit"]').val();
                    if (specialLimit && specialLimit.length > editStep3.maxLimitNum) {
                        show_msg("特殊限制内容html标签过多，请简化");
                        return false;
                    }
                    // 签约方式 字段验证
                    var signWay = $('textarea[name$="signWay"]').val();
                    if (signWay && signWay.length > editStep3.maxLimitNum) {
                        show_msg("签约方式内容html标签过多，请简化");
                        return false;
                    }
                    // 付款方式 字段验证
                    var payWay = $('textarea[name$="payWay"]').val();
                    if (payWay && payWay.length > editStep3.maxLimitNum) {
                        show_msg("付款方式内容html标签过多，请简化");
                        return false;
                    }
                    // 违约责任提示 字段验证
                    var breachTip = $('textarea[name$="breachTip"]').val();
                    if (breachTip && breachTip.length > editStep3.maxLimitNum) {
                        show_msg("违约责任提示内容html标签过多，请简化");
                        return false;
                    }
                    // 儿童价特殊说明 字段验证
                    var childLongRemark = $('textarea[name$="childLongRemark"]').val();
                    if (childLongRemark && childLongRemark.length > editStep3.maxLimitNum) {
                        show_msg("儿童价特殊说明html标签过多，请简化");
                        return false;
                    }
					editStep3.updateInputName();
                    editStep3.doGetData();
					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
				} else {
					show_msg("请完善当前页面数据");
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					$('#lineexplainId').val(result.id);
					LineUtil.buildLine($('#productId').val());
					parent.window.showGuide(4, true);	
				}else{
					show_msg("保存线路失败");
				}
			}
		});	
	}	
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
	editStep3.init();
});