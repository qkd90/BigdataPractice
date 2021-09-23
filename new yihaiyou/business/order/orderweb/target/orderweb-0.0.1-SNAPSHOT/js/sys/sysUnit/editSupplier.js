$.extend($.fn.validatebox.defaults.rules, {
	validateMobileExist: {
		validator: function (value, param) {
			var b = false;
			if (value) {
				var neUserId = $('#userId').val();
				$.ajax({
	                tyep: "post",
	                dataType: "json",
	                async: false,//是否异步执行（该属性已被遗弃）
	                url: "/sys/sysUser/validateUser.jhtml?neUserId="+neUserId,
	                data: "accountUser.mobile="+value,
	                success: function (result) {
	                    b = result.notExisted;
	                },
	                error: function (errorMSG) {
	                    b = false;
	                }
	            });
			}
            return b;
		},
		message: '该手机号已被使用'
	},
	validateAccountExist: {
		validator: function (value, param) {
			var b = false;
			if (value) {
				var neUserId = $('#userId').val();
				$.ajax({
					tyep: "post",
					dataType: "json",
					async: false,//是否异步执行（该属性已被遗弃）
					url: "/sys/sysUser/validateUser.jhtml?neUserId="+neUserId,
					data: "accountUser.account="+value,
					success: function (result) {
						b = result.notExisted;
					},
					error: function (errorMSG) {
						b = false;
					}
				});
			}
			return b;
		},
		message: '该用户名已被使用'
	}
});
var editSupplier = {
	imgFolder:'unit/supliber',
	fileFolder:'unit/qualification',
	passsPortButton: null,
	businessLicenseButton1: null,
	businessLicenseButton: null,
	idCardOppositiveButton: null,
	idcardPositiveButton: null,
	bankNames:[
		{id:'中国工商银行', name:'中国工商银行'},
		{id:'中国农业银行', name:'中国农业银行'},
		{id:'中国银行', name:'中国银行'},
		{id:'交通银行', name:'交通银行'},
		{id:'中信实业银行', name:'中信实业银行'},
		{id:'中国光大银行', name:'中国光大银行'},
		{id:'华夏银行', name:'华夏银行'},
		{id:'中国民生银行', name:'中国民生银行'},
		{id:'广东发展银行', name:'广东发展银行'},
		{id:'深圳发展银行', name:'深圳发展银行'},
		{id:'招商银行', name:'招商银行'},
		{id:'兴业银行', name:'兴业银行'},
		{id:'上海浦东发展银行', name:'上海浦东发展银行'},
		{id:'城市商业银行', name:'城市商业银行'},
		{id:'农村商业银行', name:'农村商业银行'},
		{id:'国家开发银行', name:'国家开发银行'},
		{id:'中国进出口银行', name:'中国进出口银行'},
		{id:'中国农业发展银行', name:'中国农业发展银行'},
		{id:'城市信用社', name:'城市信用社'},
		{id:'农村信用社', name:'农村信用社'},
		{id:'农村合作银行', name:'农村合作银行'},
		{id:'邮政储蓄', name:'邮政储蓄'},
		{id:'其他银行', name:'其他银行'}
	],
	init:function(){
		editSupplier.initUploadKindeditor();
		editSupplier.initComp();
		editSupplier.initStatus();
		editSupplier.initFrameHeight();
	},

	initAppendice: function() {
		if ($("#contractId").val()) {
			var url = "/contract/contract/getContractAppendiceList.jhtml";
			$.post(url,
				{'contract.id': $("#contractId").val()},
				function(data) {
					if (data.success) {
						$.each(data.appendiceList, function(i, perValue) {
							editSupplier.addTr(perValue);
						});

					} else {
					}
				}
			);
		}
	},

	appendTr: function(index, result) {
		var html_tr = "";
		html_tr += '<tr id="tr_'+ index +'" class="appendice_class" rows="'+ index +'">';
		html_tr += '<td width="320px">'+ result.name +'</td>';
		html_tr += '<input type="hidden" value="'+ result.name +'">' ;
		html_tr += '<input type="hidden" value="'+ result.path +'">' ;
		html_tr += '<input type="hidden" value="'+ result.type +'">' ;
		html_tr += '<td align="center">' ;
		html_tr += '<a href="'+ QINIU_BUCKET_URL + result.path+'" >下载</a>&nbsp;&nbsp;' ;
		html_tr += '<a href="javascript:void(0)"  onclick="editSupplier.delTr('+ index +')">删除</a>' ;
		html_tr += '</td>';
		html_tr += '</tr>';
		$("#appendicesTbody").append(html_tr);
		editSupplier.initFrameHeight();
	},
	addTr: function(result) {
		var appdeniceClassList = $(".appendice_class");
		var index = appdeniceClassList.length + 1;
		editSupplier.appendTr(index, result);
	},
	delTr: function(index) {
		$("#tr_" + index +"").remove();
	},
	download: function(imgPathURL) {
		/*var url = "/sys/sysUnit/downloadFile.jhtml";
		$("#downDialog").dialog({
			buttons:[{
				text:'保存',
				handler:function(){
					var filePath = $("#filePath").textbox("getValue");
					var patrn=/^[a-zA-Z]:\\[a-zA-Z_0-9\\]*//*;
					if (!patrn.exec(filePath)){
						show_msg("路径格式错误！");
						return ;
					}
					$.messager.progress({
						title:'温馨提示',
						text:'下载中,请耐心等待...'
					});
					var data = {'imgPathURL': imgPathURL, fileName: fileName, filePath: filePath};
					$.ajax({
						type: "POST",
						url: url,
						data: data,
						progress: true,
						loadingText: '正在下载中....',
						success: function(data){
							if (data.success) {
								$.messager.progress("close");
								show_msg("下载成功！");
								$("#filePath").textbox("setValue", "");
								$("#downDialog").dialog("close");
							} else {
								show_msg("下载失败！");
								$.messager.progress("close");
							}
						},
						error: function(error) {
							show_msg("下载失败，请重试");
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
					$("#filePath").textbox("setValue", "");
					$("#downDialog").dialog("close");
				}
			}]

		});*/
		$("#downDialog").dialog({
			onBeforeOpen: function() {
				$("#viewImg").attr("src", imgPathURL);
			},
			onClose: function() {
				$("#viewImg").attr("src", "");
			}
		});
		$("#downDialog").dialog("open");
	},

	doSaveAsIMG: function() {

		if(document.all._SAVEASIMAGE_TEMP_FRAME.src!="about:blank")
			window.frames['_SAVEASIMAGE_TEMP_FRAME'].document.execCommand("SaveAs");

		/*if ($("#IframeReportImg").attr("src") != "about:blank") {
			window.frames["IframeReportImg"].document.execCommand("SaveAs");
		}*/
		//document.frames("IframeReportImg").document.execCommand("SaveAs");
	},
	//判断是否为ie浏览器
	browserIsIe: function () {
		if (!!window.ActiveXObject || "ActiveXObject" in window)
			return true;
		else
			return false;
	},

initUploadKindeditor: function() {
		var uploadbutton = KindEditor.uploadbutton({
			button : KindEditor('#uploadButton')[0],
			fieldName : 'resource',
			extraParams : {folder:editSupplier.fileFolder},
			url : '/sys/imgUpload/uploadFileQiniu.jhtml',
			afterUpload : function(data) {
				$.messager.progress("close");
				if (data.success) {
					editSupplier.addTr(data.result);
				} else {
					show_msg(data.errorMsg);
				}
			},
			afterError : function(str) {
				$.messager.progress("close");

				alert('自定义错误信息: ' + str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			$.messager.progress({
				title:'温馨提示',
				text:'附件上传中,请耐心等待...'
			});

			uploadbutton.submit();
		});
	},

	initIdCardUpload: function() {
		// 图片上传
		if (!editSupplier.idcardPositiveButton) {
			editSupplier.idcardPositiveButton = KindEditor.uploadbutton({
				button : KindEditor('#idcardPositiveButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#idcardPositivePath').val(), folder:editSupplier.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						$('#idcardPositivePath').val(result.path);
						$('#idcardPositive img').attr('src', QINIU_BUCKET_URL + result.path);
						//$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
		}

		editSupplier.idcardPositiveButton.fileBox.change(function(e) {
			var filePath = editSupplier.idcardPositiveButton.fileBox[0].value;
			if (!filePath) {
				show_msg("图片格式不正确");
				return ;
			}
			var suffix = filePath.substr(filePath.lastIndexOf("."));
			suffix = suffix.toLowerCase();
			if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
				show_msg("图片格式不正确");
				return ;
			}
			$.messager.progress({
				title:'温馨提示',
				text:'图片上传中,请耐心等待...'
			});

			//$('input[name=oldFilePath]').val($('#idcardPositivePath').val());	// 添加动态参数，隐藏标签是KindEditor自动生成的
			editSupplier.idcardPositiveButton.submit();
		});

		if (!editSupplier.idCardOppositiveButton) {
			editSupplier.idCardOppositiveButton = KindEditor.uploadbutton({
				button : KindEditor('#idCardOppositiveButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#idCardOppositivePath').val(), folder:editSupplier.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						$('#idCardOppositivePath').val(result.path);
						$('#idCardOppositive img').attr('src', QINIU_BUCKET_URL + result.path);
						//$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
		}

		editSupplier.idCardOppositiveButton.fileBox.change(function(e) {
				var filePath = editSupplier.idCardOppositiveButton.fileBox[0].value;
				if (!filePath) {
					show_msg("图片格式不正确");
					return ;
				}
				var suffix = filePath.substr(filePath.lastIndexOf("."));
				suffix = suffix.toLowerCase();
				if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
					show_msg("图片格式不正确");
					return ;
				}
				$.messager.progress({
					title:'温馨提示',
					text:'图片上传中,请耐心等待...'
				});

				//$('input[name=oldFilePath]').val($('#filePath').val());	// 添加动态参数，隐藏标签是KindEditor自动生成的
			editSupplier.idCardOppositiveButton.submit();
		});

		if (!editSupplier.businessLicenseButton) {
			editSupplier.businessLicenseButton = KindEditor.uploadbutton({
				button : KindEditor('#businessLicenseButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#businessLicensePath').val(), folder:editSupplier.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						$('#businessLicensePath').val(result.path);
						$('#businessLicense img').attr('src', QINIU_BUCKET_URL + result.path);
						//$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
		}

		editSupplier.businessLicenseButton.fileBox.change(function(e) {
				var filePath = editSupplier.businessLicenseButton.fileBox[0].value;
				if (!filePath) {
					show_msg("图片格式不正确");
					return ;
				}
				var suffix = filePath.substr(filePath.lastIndexOf("."));
				suffix = suffix.toLowerCase();
				if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
					show_msg("图片格式不正确");
					return ;
				}
				$.messager.progress({
					title:'温馨提示',
					text:'图片上传中,请耐心等待...'
				});
			editSupplier.businessLicenseButton.submit();
		});
	},

	initPassportUpload: function() {
		if (!editSupplier.passsPortButton) {
			editSupplier.passsPortButton = KindEditor.uploadbutton({
				button : KindEditor('#passsPortButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#passPortPath').val(), folder:editSupplier.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						$('#passPortPath').val(result.path);
						$('#passPort img').attr('src', QINIU_BUCKET_URL + result.path);
						//$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
		}
		editSupplier.passsPortButton.fileBox.change(function(e) {
			var filePath = editSupplier.passsPortButton.fileBox[0].value;
			if (!filePath) {
				show_msg("图片格式不正确");
				return ;
			}
			var suffix = filePath.substr(filePath.lastIndexOf("."));
			suffix = suffix.toLowerCase();
			if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
				show_msg("图片格式不正确");
				return ;
			}
			$.messager.progress({
				title:'温馨提示',
				text:'图片上传中,请耐心等待...'
			});
			editSupplier.passsPortButton.submit();
		});
		if (!editSupplier.businessLicenseButton1) {
			editSupplier.businessLicenseButton1 = KindEditor.uploadbutton({
				button : KindEditor('#businessLicenseButton1')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#businessLicensePath1').val(), folder:editSupplier.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						$('#businessLicensePath1').val(result.path);
						$('#businessLicense1 img').attr('src', QINIU_BUCKET_URL + result.path);
						//$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
		}

		editSupplier.businessLicenseButton1.fileBox.change(function(e) {
			var filePath = editSupplier.businessLicenseButton1.fileBox[0].value;
			if (!filePath) {
				show_msg("图片格式不正确");
				return ;
			}
			var suffix = filePath.substr(filePath.lastIndexOf("."));
			suffix = suffix.toLowerCase();
			if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
				show_msg("图片格式不正确");
				return ;
			}
			$.messager.progress({
				title:'温馨提示',
				text:'图片上传中,请耐心等待...'
			});

				editSupplier.businessLicenseButton1.submit();
		});
	},


	// 初始控件
	initComp : function() {



		if ($("#startCityId").val()) {
			AreaSelectDg.initArea($("#startCity"), $("#startCityId").val());
		}
		// 出发城市查询条件
		$('#startCity').textbox({
			onClickButton:function() {
				$('#startCity').textbox('setValue', '');
				$('#startCity').attr('data-country', '');
				$('#startCity').attr('data-province', '');
				$('#startCity').attr('data-city', '');
				// 特殊处理，为了结合原来代码
				$('#startCityId').val('');
			}
		});
		$("#startCity").next('span').children('input').click(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#startCity').attr('data-country');
			var province = $('#startCity').attr('data-province');
			var city = $('#startCity').attr('data-city');
			AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
				$('#startCity').textbox('setValue', fullName);
				if (countryId) {
					$('#startCity').attr('data-country', countryId);
					$('#startCityId').val(countryId);
				} else {
					$('#startCity').attr('data-country', '');
				}
				if (provinceId) {
					$('#startCity').attr('data-province', provinceId);
					$('#startCityId').val(provinceId);
				} else {
					$('#startCity').attr('data-province', '');
				}
				if (cityId) {
					$('#startCity').attr('data-city', cityId);
					$('#startCityId').val(cityId);
				} else {
					$('#startCity').attr('data-city', '');
				}
			});
		});



		if ($("#crtCityId").val()) {
			AreaSelectDg.initArea($("#crtCity"), $("#crtCityId").val());
		}
		// 出发城市查询条件
		$('#crtCity').textbox({
			onClickButton:function() {
				$('#crtCity').textbox('setValue', '');
				$('#crtCity').attr('data-country', '');
				$('#crtCity').attr('data-province', '');
				$('#crtCity').attr('data-city', '');
				// 特殊处理，为了结合原来代码
				$('#crtCityId').val('');
			}
		});
		$("#crtCity").next('span').children('input').click(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#crtCity').attr('data-country');
			var province = $('#crtCity').attr('data-province');
			var city = $('#crtCity').attr('data-city');
			AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
				$('#crtCity').textbox('setValue', fullName);
				if (countryId) {
					$('#crtCity').attr('data-country', countryId);
					$('#crtCityId').val(countryId);
				} else {
					$('#crtCity').attr('data-country', '');
				}
				if (provinceId) {
					$('#crtCity').attr('data-province', provinceId);
					$('#crtCityId').val(provinceId);
				} else {
					$('#crtCity').attr('data-province', '');
				}
				if (cityId) {
					$('#crtCity').attr('data-city', cityId);
					$('#crtCityId').val(cityId);
				} else {
					$('#crtCity').attr('data-city', '');
				}
			});
		});



		if($(":input[name='unitDetail.certificateType']:checked").val()=='passport') {
			$(".passPartImage").show();
			$(".idCardImage").hide();
			editSupplier.initPassportUpload();
		} else {
			$(".passPartImage").hide();
			$(".idCardImage").show();
			editSupplier.initIdCardUpload();
		}
	},

	selCertificateType: function(type) {
		$("#legalIdCardNoId").textbox("setValue", "");
		if (type == 'passport') {
			$('#businessLicensePath1').val($('#businessLicensePath').val());
			if ($('#businessLicensePath').val().length > 0) {
				$('#businessLicense1 img').attr('src', QINIU_BUCKET_URL + $('#businessLicensePath').val());
			}
			$(".passPartImage").show();
			$(".idCardImage").hide();
			$.each($(".idCardImage :input[name='image']"), function(i, input) {
				$(input).val("");
			});
			$.each($(".idCardImage img"), function(i, input) {
				$(input).attr("src", "");
			});
			editSupplier.initPassportUpload();

		} else {
			$('#businessLicensePath').val($('#businessLicensePath1').val());
			if ($('#businessLicensePath1').val().length > 0) {
				$('#businessLicense img').attr('src', QINIU_BUCKET_URL + $('#businessLicensePath1').val());
			}
			//$('#businessLicense img').attr('src', QINIU_BUCKET_URL + $('#businessLicensePath1').val());
			$(".passPartImage").hide();
			$(".idCardImage").show();
			$.each($(".passPartImage :input[name='image']"), function(i, input) {
				$(input).val("");
			});
			$.each($(".passPartImage img"), function(i, input) {
				$(input).attr("src", "");
			});
			editSupplier.initIdCardUpload();

		}
		editSupplier.initFrameHeight();
	},



	// 初始状态
	initStatus : function() {
		
	},
	// 删除图片
	delImg : function() {
		// 异步删除图片文件
		//$.post("/sys/imgUpload/delFile.jhtml",
		//	{oldFilePath : $('#filePath').val()},
		//	function(result) {
		//		// TODO 暂时不做提示
    		//}
		//);
		
		$('#filePath').val('');	
		$('#imgView img').attr('src', '');
		$('#imgView').hide();
	},
	// 保存
	doSave:function(){
		// 保存表单
		$('#editForm').form('submit', {
			url : "/sys/sysUnit/saveSupplier.jhtml",
			onSubmit : function() {

				$.messager.progress({
					title:'温馨提示',
					text:'数据处理中,请耐心等待...'
				});

				var isValid = $(this).form('validate');
				/*if(isValid){
					var idcardPositivePath = $('#idcardPositivePath').val();
					if (!idcardPositivePath) {
						show_msg("请上传身份证正面照");
						return false;
					}
					var idCardOppositivePath = $('#idCardOppositivePath').val();
					if (!idCardOppositivePath) {
						show_msg("请上传身份证反面照");
						return false;
					}
					var businessLicensePath = $('#businessLicensePath').val();
					if (!businessLicensePath) {
						show_msg("请上传营业执照");
						return false;
					}
					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
				} else {
					show_msg("请完善当前页面数据");
				}*/

				if($(":input[name='unitDetail.certificateType']:checked").val()=='passport') {
					$.each($(".passPartImage_value"), function(i, passPartImage) {
						var inputs = $(passPartImage).children(":input[type='hidden']");
						if (!inputs[1].value) {
							if (i == 0) {
								show_msg("请上传护照");
							} else {
								show_msg("请上传营业执照");
							}
							isValid = false;
							$.messager.progress("close");
							return false;
						}
						$("#hidden_span").append('<input type="hidden" name="unitImages['+ i +'].type" value="'+ inputs[0].value +'">');
						$("#hidden_span").append('<input type="hidden" name="unitImages['+ i +'].path" value="'+ inputs[1].value +'">');
					});

				} else {
					$.each($(".idCardImage_value"), function(i, idCardImage) {
						var inputs = $(idCardImage).children(":input[type='hidden']");
						if (!inputs[1].value) {
							if (i == 0) {
								show_msg("请上传身份证正面照");
							} else if(i == 1) {
								show_msg("请上传身份证反面照");
							} else {
								show_msg("请上传营业执照");
							}
							isValid = false;
							$.messager.progress("close");
							return false;
						}
						$("#hidden_span").append('<input type="hidden" name="unitImages['+ i +'].type" value="'+ inputs[0].value +'">');
						$("#hidden_span").append('<input type="hidden" name="unitImages['+ i +'].path" value="'+ inputs[1].value +'">');
					});
				}

				if ($("#ipt-crtbnk").combobox("getValue").length <= 0) {

					$("html, body").animate({
						scrollTop: $("#ipt-crtbnk").offset().top - 50 }, {duration: 500,easing: "swing"});

					show_msg("请选择银行！");
					isValid = false;
					$.messager.progress("close");
					return false;
				}

				if ($("#crtCityId").val().length <= 0) {
					$("html, body").animate({
						scrollTop: $("#ipt-crtbnk").offset().top - 50 }, {duration: 500,easing: "swing"});
					show_msg("请选择开户行省市/区！");
					isValid = false;
					$.messager.progress("close");
					return false;
				}

				var appdeniceClassList = $(".appendice_class");
				$.each(appdeniceClassList, function(i, perValue) {
					var inputs = $(perValue).find("input");
					$("#appendicesTbody").append('<input type="hidden" name="unitQualifications['+ i +'].name" value="'+ inputs[0].value +'">');
					$("#appendicesTbody").append('<input type="hidden" name="unitQualifications['+ i +'].path" value="'+ inputs[1].value +'">');
					$("#appendicesTbody").append('<input type="hidden" name="unitQualifications['+ i +'].type" value="'+ inputs[2].value +'">');

				});



				if (!isValid) {
					$.messager.progress("close");
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					$.messager.alert('温馨提示', '操作成功', 'info', function() {
						window.parent.$("#editPanel").dialog("close");
						window.parent.$("#dg").datagrid('load');
						//FxUtil.buildSupplier(result.id);
						//window.location.reload();
	  				});	
				}else{
					show_msg("操作失败");
				}
			}
		});
		//parent.window.showGuide(2, true);	
	},	
	// 返回
	doBack: function() {
		parent.window.auditList.closeViewPanel(false);
	},
	initFrameHeight: function() {
		window.parent.$("#editIframe").css("height", $("#content").height() + 50);
	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};
$(function(){
	editSupplier.init();
});