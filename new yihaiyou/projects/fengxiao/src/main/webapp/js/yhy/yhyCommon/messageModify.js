$(document).ready(function(){
	//selectIDcar();
	//closeIDcarList();
	//busiType();
	CompanyInfoEdit.init();
	areaSel();
})



var CompanyInfoEdit = {
	fan_idcard: null,
	zheng_idcard: null,
	passport: null,
	businessimg: null,
	init: function() {
		CompanyInfoEdit.initJsp();
		CompanyInfoEdit.busiType();
		CompanyInfoEdit.selectIDcar();
		CompanyInfoEdit.closeIDcarList();
		CompanyInfoEdit.selectArea();
		CompanyInfoEdit.closeAreaList();
		CompanyInfoEdit.initCheckInput();
		//CompanyInfoEdit.initPrivince();
		//CompanyInfoEdit.initCity();
	},

	initPrivince: function() {
		$("#bank-province").after("<ul class='provinceList' open-flag='0' style='height: 250px; overflow-y: auto;'></ul>");
		$("#bank-province").after("<input hidden='hidden' id='province-id'/>");
		var qryData = {'tbArea.id': 100000};
		var url = "/yhy/yhyMain/getAreaList.jhtml";
		$.ajax({
			url: url,
			data: qryData,
			progress: false,
			success: function(result) {
				$(".provinceList li").remove();
				$.each(result, function(i, perArea) {
					$(".provinceList").append('<li data-city-id="'+ perArea.id +'" onclick="CompanyInfoEdit.clickProvinceLi(this)">'+ perArea.name +'</li>');
				});
			},
			error: function() {}
		});
	},
	//触发省，弹出列
	clickProvinceInput: function() {
		$('.cityList').slideUp(100);
		$('.cityList').attr("open-flag", 0);

		if ($('.provinceList').attr("open-flag") == 0){
			if ($(".provinceList").children().length > 0) {
				$('.provinceList').slideDown(100);
				$('.provinceList').attr("open-flag", 1);
				$("#shawdom").show();
			}
		} else {
			$('.provinceList').slideUp(100);
			$('.provinceList').attr("open-flag", 0);
			$("#shawdom").hide();
		}
	},
	//选择省
	clickProvinceLi: function(target) {
		event.stopPropagation();
		$('.provinceList').slideUp(100);
		$('.provinceList').attr("open-flag", 0);
		$("#shawdom").hide();
		$(".provinceList li").removeClass("selSpan");
		$(target).addClass("selSpan");
		//设置省
		$("#bank-province").val($(target).html());
		$("#province-id").val($(target).attr("data-city-id"));
		$("#city-id").attr("province-id", $(target).attr("data-city-id"));

		//清空市/区
		$(".cityList li").remove();
		$("#bank-city").val("请选择市/区");
		$("#city-id").val("");
		$('.cityList').slideUp(100);
	},

	clickCity: function() {
		if ($("#province-id").val()) {
			$('.provinceList').slideUp(100);
			$('.provinceList').attr("open-flag", 0);
			var qryData = {'tbArea.id': $("#province-id").val()};
			var url = "/yhy/yhyMain/getAreaList.jhtml";

			if ($(".cityList").children().length > 0) {	//判断当前省ID是否为上次选择的省ID，如果一样无需从服务器加载数据，否则重新加载
				if ($('.cityList').attr("open-flag") == 0){
					$('.cityList').slideDown(100);
					$('.cityList').attr("open-flag", 1);
					$("#shawdom").show();
				} else {
					$('.cityList').slideUp(100);
					$('.cityList').attr("open-flag", 0);
					$("#shawdom").hide();
				}
			} else {
				$.ajax({
					url: url,
					data: qryData,
					progress: false,
					success: function(result) {
						$(".cityList li").remove();
						$.each(result, function(i, perArea) {
							$(".cityList").append('<li data-city-id="'+ perArea.id +'" onclick="CompanyInfoEdit.clickCityLi(this)">'+ perArea.name +'</li>');
						});

						if ($('.cityList').attr("open-flag") == 0){
							if ($(".cityList").children().length > 0) {
								$('.cityList').slideDown(100);
								$('.cityList').attr("open-flag", 1);
								$("#shawdom").show();
							}
						} else {
							$('.cityList').slideUp(100);
							$('.cityList').attr("open-flag", 0);
							$("#shawdom").hide();
						}

					},
					error: function() {}
				});
			}
			$("#error-tip-city").remove();
		} else {
			if ($("#error-tip-city").length == 0) {
				$("#bank-city").after('<span class="erroratention" id="error-tip-city"><span class="gth">!</span><span id="error-tips">选择省！</span></span>');
			}
		}
	},

	clickCityLi: function(target) {
		$('.cityList').slideUp(100);
		$('.cityList').attr("open-flag", 0);


		$("#shawdom").hide();
		$(".cityList li").removeClass("selSpan");
		$(target).addClass("selSpan");
		//设置市/区
		$("#bank-city").val($(target).html());
		$("#city-id").val($(target).attr("data-city-id"));
	},

	initCity: function() {
		$("#bank-city").after("<ul class='cityList' open-flag='0' style='height: 250px; overflow-y: auto;'></ul>");
		$("#bank-city").after("<input hidden='hidden' id='city-id' province-id=''/>");
	},

	checkInput: function() {
		var flag = true;
		$.each($("input[required]"), function(i, perInput) {
			if ($(perInput).val().length <= 0) {
				if ($(perInput).attr("data-temp-name") == "imgpath-z-idcard") {
					$(".zheng-idcard-img-tip").show();
					$("html, body").animate({
						scrollTop: $(".zheng-idcard-img-tip").offset().top - 50 }, {duration: 500,easing: "swing"});
				} else if ($(perInput).attr("data-temp-name") == "imgpath-f-idcard") {
					$(".fan-idcard-img-tip").show();
					$("html, body").animate({
						scrollTop: $(".fan-idcard-img-tip").offset().top - 50 }, {duration: 500,easing: "swing"});
				} else if ($(perInput).attr("data-temp-name") == "imgpath-passport") {
					$(".passport-img-tip").show();
					$("html, body").animate({
						scrollTop: $(".passport-img-tip").offset().top - 50 }, {duration: 500,easing: "swing"});
				} else if ($(perInput).attr("data-temp-name") == "imgpath-business") {
					$(".business-img-tip").show();
					$("html, body").animate({
						scrollTop: $(".business-img-tip").offset().top - 50 }, {duration: 500,easing: "swing"});
				} else {
					if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
					}
					$("html, body").animate({
						scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
				}
				flag = false;
				return false;
			} else {
				if ($(perInput).attr("data-temp-name") == "unitDetail-telphone") {
					var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;
					if (!reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">电话号码格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
						flag = false;
						return false;
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						flag = true;
					}
				} else if ($(perInput).attr("data-temp-name") == "unitDetail-mobile") {
					var reg = /^1[3|4|5|6|7|8|9]\d{9,9}$/;
					if (!reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">手机号码格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
						flag = false;
						return false;
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						flag = true;
					}
				} else if ($(perInput).attr("data-temp-name") == "unitDetail-crtacc") {
					var reg = /^(\d{16}|\d{19})$/;
					if (!reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">银行帐号格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
						flag = false;
						return false;
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						flag = true;
					}
				} else {
					$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					flag = true;
				}

			}
		});

		if (!flag) {
			return false;
		}

		if ($("input[name='user.email']").val().length > 0 && !/\w@\w*\.\w/.test($("input[name='user.email']").val())) {
			if ($("#error-tip-"+ $("input[name='user.email']").attr("data-temp-name") +"").length <= 0) {
				$("input[name='user.email']").after('<span class="erroratention" id="error-tip-'+ $("input[name='user.email']").attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">邮箱格式不正确！</span></span>');
			}
			$("html, body").animate({
				scrollTop: $("input[name='user.email']").offset().top - 50 }, {duration: 500,easing: "swing"});
			flag = false;
			return;
		} else {
			$("#error-tip-"+ $("input[name='user.email']").attr("data-temp-name") +"").remove();
			flag = true;
		}


		if ($("select[name='unitDetail.crtbnk']").val().length <= 0) {
			if ($("#error-tip-"+ $("select[name='unitDetail.crtbnk']").attr("data-temp-name") +"").length <= 0) {
				$("select[name='unitDetail.crtbnk']").after('<span class="erroratention" id="error-tip-'+ $("select[name='unitDetail.crtbnk']").attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">请选择银行！</span></span>');
			}
			$("html, body").animate({
				scrollTop: $("select[name='unitDetail.crtbnk']").offset().top - 50 }, {duration: 500,easing: "swing"});
			flag = false;
			return;
		} else {
			$("#error-tip-"+ $("select[name='unitDetail.crtbnk']").attr("data-temp-name") +"").remove();
			flag = true;
		}


		if (!flag) {
			return false;
		}

		if ($("input[name='user.qqNo']").val().length > 0 && !/^[1-9]\d{4,9}$/.test($("input[name='user.qqNo']").val())) {
			if ($("#error-tip-"+ $("input[name='user.qqNo']").attr("data-temp-name") +"").length <= 0) {
				$("input[name='user.qqNo']").after('<span class="erroratention" id="error-tip-'+ $("input[name='user.qqNo']").attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">邮箱格式不正确！</span></span>');
			}
			$("html, body").animate({
				scrollTop: $("input[name='user.qqNo']").offset().top - 50 }, {duration: 500,easing: "swing"});
			flag = false;
			return;
		} else {
			$("#error-tip-"+ $("input[name='user.qqNo']").attr("data-temp-name") +"").remove();
			flag = true;
		}

		if (!flag) {
			return false;
		}

		$(".hid-form-data").empty();

		$.each($("input[name^='imgpath']"), function(i, perImg) {
			var imgObj = {};
			if (!($(perImg).val()) && $(perImg).val().length <= 0) {
				if ($(perImg).attr("data-type") == "POSITIVE_IDCARD") {
					$("html, body").animate({
						scrollTop: $("#zheng-idcard-img").offset().top - 50 }, {duration: 500,easing: "swing"});
					$(".zheng-idcard-img-tip").show();
				} else if ($(perImg).attr("data-type") == "OPPOSITIVE_IDCARD") {
					$("html, body").animate({
						scrollTop: $("#fan-idcard-img").offset().top - 50 }, {duration: 500,easing: "swing"});
					$(".fan-idcard-img-tip").show();
				} else if ($(perImg).attr("data-type") == "BUSINESS_LICENSE") {
					$("html, body").animate({
						scrollTop: $("#business-img").offset().top - 50 }, {duration: 500,easing: "swing"});
					$(".business-img-tip").show();
				} else if ($(perImg).attr("data-type") == "PASSPORT") {
					$("html, body").animate({
						scrollTop: $("#passport-img").offset().top - 50 }, {duration: 500,easing: "swing"});
					$(".passport-img-tip").show();
				}
				flag = false;
				return flag;
			} else {
				$(".hid-form-data").append('<input type="hidden" name="unitImages['+ i +'].path" value="'+ $(perImg).val() +'">');
				$(".hid-form-data").append('<input type="hidden" name="unitImages['+ i +'].type" value="'+ $(perImg).attr("data-type") +'">');
			}
		});

		return flag;
	},

	initCheckInput: function() {
		$.each($("input[valid]"), function(i, perInput) {
			$(perInput).on("keyup", function(){
				if ($(perInput).attr("valid") == "email") {
					var reg = /\w@\w*\.\w/;
					if ($(perInput).val().length > 0 && !reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
							$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">邮箱格式不正确！</span></span>');
						}
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				} else if ($(perInput).attr("valid") == "QQ") {
					var reg = /^[1-9]\d{4,9}$/;
					if ($(perInput).val().length > 0 && !reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
							$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">QQ号码格式不正确！</span></span>');
						}
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				} else if ($(perInput).attr("valid") == "mobile") {
					var reg = /^1[3|4|5|6|7|8|9]\d{9,9}$/;
					if ($(perInput).val().length <= 0) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else if ($(perInput).val().length > 0 && !reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">手机号码格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				} else if ($(perInput).attr("valid") == "tel") {
					var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;
					if ($(perInput).val().length <= 0) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else if ($(perInput).val().length > 0 && !reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">电话号码格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				} else if ($(perInput).attr("valid") == "crtacc") {
					var reg = /^(\d{16}|\d{19})$/;
					if ($(perInput).val().length <= 0) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else if ($(perInput).val().length > 0 && !reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">银行帐号格式不正确！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				} else {
					if ($(perInput).val().length <= 0) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
							$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
						}
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
					}
				}

			});

		});
	},

	initJsp: function() {

		$("#bank-province").click(function (e) {
			var settings = {
				qryData:{
					'tbArea.id': 100000,
					'tbArea.level': 1
					},
				initCityId: $("#cityId").val(),
				initProvinceId: $("#cityId").val(),
				url : "/yhy/yhyMain/getAreaList.jhtml"
			}
			SelCity(this, e, settings);
			//SelCity(this, e);
		});

		CompanyInfoEdit.initBusinessImgUploader();
		CompanyInfoEdit.initIdCard($("#hid-idsel").val());
		$(".wordNum-mainBusiness").html($(":input[name='unitDetail.mainBusiness']").val().length + "/500");
		$(":input[name='unitDetail.mainBusiness']").on("keyup", function() {
			if ($(this).val().length > 500) {
				if (!$("#error-tip-"+ $(this).attr("temp-name") +"").length > 0) {
					$(this).after('<span class="erroratention" id="error-tip-'+ $(this).attr("temp-name") +'"><span class="gth">!</span><span id="error-tips">经营范围字数不能超过500字，请删减！</span></span>');
				} else {
					$("#error-tip-"+ $(this).attr("temp-name") +"").remove();
					$(this).after('<span class="erroratention" id="error-tip-'+ $(this).attr("temp-name") +'"><span class="gth">!</span><span id="error-tips">经营范围字数不能超过500字，请删减！</span></span>');
				}
				var value = $(this).val().substr(0, 500);
				$(this).val(value);
			} else {
				$(".wordNum-mainBusiness").html($(this).val().length + "/500");
				$("#error-tip-"+ $(this).attr("temp-name") +"").remove();
			}

		});
		$(".wordNum-introduction").html($(":input[name='unitDetail.introduction']").val().length + "/500");
		$(":input[name='unitDetail.introduction']").on("keyup", function() {
			if ($(this).val().length > 500) {
				if (!$("#error-tip-"+ $(this).attr("temp-name") +"").length > 0) {
					$(this).after('<span class="erroratention" id="error-tip-'+ $(this).attr("temp-name") +'"><span class="gth">!</span><span id="error-tips">公司简介字数不能超过500字，请删减！</span></span>');
				} else {
					$("#error-tip-"+ $(this).attr("temp-name") +"").remove();
					$(this).after('<span class="erroratention" id="error-tip-'+ $(this).attr("temp-name") +'"><span class="gth">!</span><span id="error-tips">公司简介字数不能超过500字，请删减！</span></span>');
				}
				var value = $(this).val().substr(0, 500);
				$(this).val(value);
			} else {
				$(".wordNum-introduction").html($(this).val().length + "/500");
				$("#error-tip-"+ $(this).attr("temp-name") +"").remove();
			}

		});
	},


	doSaveInfo: function() {
		if (!CompanyInfoEdit.checkInput()) {
			return;
		}

		$.form.commit({
			formId: '#companyInfo-form',
			url: '/yhy/yhyMain/doSaveCompanyInfo.jhtml',
			success: function(result) {
				if (result.success) {
					$.messager.show({
						msg: "保存成功",
						type: "success",
						timeout: 3000,
						afterClosed: function() {
							history.go(-1);
						}
					});
				} else {
					$.messager.show({
						msg: result.msg,
						type: "error"
					});
				}
			},
			error: function() {
				$.messager.show({
					msg: "保存错误! 稍候重试!",
					type: "error"
				});
			}
		})

	},




	selectBankProvince: function (){
		var btn = $('#bank-province');
		btn.on('click',function(event){
			event.stopPropagation();
			$('.bank-provinceList').slideDown(100);

		});
	},


	selectArea: function (){
		var btn = $('#sel-area');
		btn.on('click',function(event){
			event.stopPropagation();
			$('.areaList').slideDown(100);

		})
	},

	closeAreaList: function (){
		var area = $('.mainBox');
		var tag = $('.areaList li');
		area.on('click',function(){
			$('.IDcarList').slideUp(100);
			$('.areaList').slideUp(100);
			$('.bank-provinceList').slideUp(100);
			$('.bank-cityList').slideUp(100);
		});
		tag.on('click',function(){
			var val = $(this).html();
			$('.areaList').children().removeClass("selSpan");
			$(this).addClass("selSpan");
			$("#hid-area-id").val($(this).attr("data-city-id"));
			$('#sel-area').val(val);
		})
	},


	datalist: function(obj, hidObj, tagObj, silbings) {
		tagObj.hide();
		var onOff = true;
		obj.click(function(ev){
			var ev = ev || event;
			ev.stopPropagation();
			tagObj.slideDown('2000');
			tagObj.find('li').hover(
				function(){
					$(this).addClass('active');
				},function(){
					$(this).removeClass('active');
				}
			);
			tagObj.find('li').click(function(){
				tagObj.slideUp('2000');
				var inputVal = $(this).html();
				var dataId = $(this).attr("data-id");
				hidObj.val(dataId);
				obj.val(inputVal);
			});
		});
		silbings.click(function(){
			tagObj.slideUp('2000');
		});
		$("body").click(function(){
			tagObj.slideUp('2000');
		});
	},

	selectIDcar: function (){
		var btn = $('#IDsel');
		btn.on('click',function(event){
			event.stopPropagation();
			$('.areaList').slideUp(100);
			$('.IDcarList').slideDown(100);
		})
	},
	closeIDcarList: function (){
		var area = $('.mainBox');
		var tag = $('.IDcarList li');
		area.on('click',function(){
			$('.IDcarList').slideUp(100);
		});
		tag.on('click',function(){
			var val = $(this).html();
			$('.IDcarList').children().removeClass("selSpan");
			$(this).addClass("selSpan");
			$("input[name='imagePath']").remove();
			$("#hid-idsel").val($(this).attr("data-type"));
			CompanyInfoEdit.initIdCard($(this).attr("data-type"));
			$('#IDsel').val(val);
		})
	},



	initIdCard: function(type) {
		if (type == "identity_card") {
			$(".idcard-li").show();
			$(".idcard").show();
			$(".passport-li").hide();
			$(".passport").hide();
			$('.passport').empty();

			if ($("input[name='imagePath']").length > 0) {
				$.each($("input[name='imagePath']"), function(i, perImg) {
					if($(perImg).attr("data-type") == "OPPOSITIVE_IDCARD") {
						$('.f-idcard').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' required><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					} else {
						$('.z-idcard').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' required><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					}
				});
			} else {
				$('.f-idcard').empty().append("<input type='hidden' name='imgpath' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' required><img src='/images/no_img.jpg'>");
				$('.z-idcard').empty().append("<input type='hidden' name='imgpath' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' required><img src='/images/no_img.jpg'>");
			}

			CompanyInfoEdit.initZidcardUploader();
			CompanyInfoEdit.initFidcardUploader()
		} else {
			$(".passport-li").show();
			$(".passport").show();

			if ($("input[name='imagePath']").length > 0) {
				$.each($("input[name='imagePath']"), function(i, perImg) {
					if($(perImg).attr("data-type") == "PARSSPORT") {
						$('.passport').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='PARSSPORT' data-temp-name='imgpath-passport' required><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					}
				});
			} else {
				$('.passport').empty().append("<input type='hidden' name='imgpath' data-type='PARSSPORT' data-temp-name='imgpath-passport' required><img src='/images/no_img.jpg'>");
			}

			$('.f-idcard').empty();
			$('.z-idcard').empty();

			$(".idcard-li").hide();
			$(".idcard").hide();
			CompanyInfoEdit.initPassportImgUploader();
		}
	},
	initZidcardUploader: function() {
		CompanyInfoEdit.zheng_idcard = WebUploader.create({
			server: '/sys/imgUpload/imageUpload.jhtml',
			swf: '/js/lib/webuploader/Uploader.swf',
			//pick: {id: '#zheng-idcard-img', innerHTML: '上传图片', multiple: false},
			auto: true,
			method: 'post',
			formData: {section: "unit/supliber"},
			accept: {
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
			},
			thumb: {width: 200, height: 200, crop: true},
			compress: {width: 200, height: 200, crop: true},
			fileSingleSizeLimit: 2097152
		});
		CompanyInfoEdit.zheng_idcard.addButton({
			id: '#zheng-idcard-img',
			innerHTML: '上传图片',
			multiple: false
		});


		CompanyInfoEdit.zheng_idcard.on('uploadSuccess', function(file, response) {
			$(".zheng-idcard-img-tip").hide();
			var $div = $('.z-idcard');
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' required>" +
			"<img src='"+ QINIU_BUCKET_URL + response.path + "'>");
		});
		CompanyInfoEdit.zheng_idcard.on('uploadError', function(code) {
			$(".zheng-idcard-img-tip").html("上传错误" + code);
			$(".zheng-idcard-img-tip").show();
		});
		CompanyInfoEdit.zheng_idcard.on('error', function (code) {window.console.log(code);});
	},

	initFidcardUploader: function() {
		CompanyInfoEdit.fan_idcard = WebUploader.create({
			server: '/sys/imgUpload/imageUpload.jhtml',
			swf: '/js/lib/webuploader/Uploader.swf',
			//pick: {id: '#zheng-idcard-img', innerHTML: '上传图片', multiple: false},
			auto: true,
			method: 'post',
			formData: {section: "unit/supliber/"},
			accept: {
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
			},
			thumb: {width: 200, height: 200, crop: true},
			compress: {width: 200, height: 200, crop: true},
			fileSingleSizeLimit: 2097152
		});

		CompanyInfoEdit.fan_idcard.addButton({
			id: '#fan-idcard-img',
			innerHTML: '上传图片',
			multiple: false
		});

		CompanyInfoEdit.fan_idcard.on('uploadSuccess', function(file, response) {
			$(".fan-idcard-img-tip").hide();
			var $div = $('.f-idcard');
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' required>" +
			"<img src='"+ QINIU_BUCKET_URL + response.path + "'>");
		});
		CompanyInfoEdit.fan_idcard.on('uploadError', function(code) {
			$(".fan-idcard-img-tip").html("上传错误" + code);
			$(".fan-idcard-img-tip").show();
		});
		CompanyInfoEdit.fan_idcard.on('error', function (code) {window.console.log(code);});
	},

	initBusinessImgUploader: function() {
		CompanyInfoEdit.businessimg = WebUploader.create({
			server: '/sys/imgUpload/imageUpload.jhtml',
			swf: '/js/lib/webuploader/Uploader.swf',
			//pick: {id: '#zheng-idcard-img', innerHTML: '上传图片', multiple: false},
			auto: true,
			method: 'post',
			formData: {section: "unit/supliber/"},
			accept: {
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
			},
			thumb: {width: 200, height: 200, crop: true},
			compress: {width: 200, height: 200, crop: true},
			fileSingleSizeLimit: 2097152
		});

		CompanyInfoEdit.businessimg.addButton({
			id: '#business-img',
			innerHTML: '上传图片',
			multiple: false
		});

		CompanyInfoEdit.businessimg.on('uploadSuccess', function(file, response) {
			$(".business-img-tip").hide();
			var $div = $('.business');
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='BUSINESS_LICENSE' data-temp-name='imgpath-busines' required>" +
			"<img src='"+ QINIU_BUCKET_URL + response.path + "'>");
		});
		CompanyInfoEdit.businessimg.on('uploadError', function(code) {
			$(".business-img-tip").html("上传错误" + code);
			$(".business-img-tip").show();
		});
		CompanyInfoEdit.businessimg.on('error', function (code) {window.console.log(code);});

	},

	initPassportImgUploader: function() {
		CompanyInfoEdit.passport = WebUploader.create({
			server: '/sys/imgUpload/imageUpload.jhtml',
			swf: '/js/lib/webuploader/Uploader.swf',
			//pick: {id: '#zheng-idcard-img', innerHTML: '上传图片', multiple: false},
			auto: true,
			method: 'post',
			formData: {section: "unit/supliber/"},
			accept: {
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
			},
			thumb: {width: 200, height: 200, crop: true},
			compress: {width: 200, height: 200, crop: true},
			fileSingleSizeLimit: 2097152
		});

		CompanyInfoEdit.passport.addButton({
			id: '#passport-img',
			innerHTML: '上传图片',
			multiple: false
		});

		CompanyInfoEdit.passport.on('uploadSuccess', function(file, response) {
			$(".passport-img-tip").hide();
			var $div = $('.passport');
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='PARSSPORT' data-temp-name='imgpath-passport' required>" +
			"<img src='"+ QINIU_BUCKET_URL + response.path + "'>");
		});
		CompanyInfoEdit.passport.on('uploadError', function(code) {
			$(".passport-img-tip").html("上传错误" + code);
			$(".passport-img-tip").show();
		});
		CompanyInfoEdit.passport.on('error', function (code) {window.console.log(code);});

	},
	busiType: function (){
		var type = $('.busi_type');
		type.on('click',function(){
			$(":input[name='unitDetail.supplierType']").val($(this).attr("data-value"));
			$(this).addClass('busi_type_check');
			$(this).siblings().removeClass('busi_type_check');
		})
	}

}


function areaSel(){
	var h_span = $('.areaHead span');
	var h_p = $('.areap');
	var areaSpan = $('.areap span');
	$('.areainput').focus(function(){
		$('.areaContain').slideDown();
	});
	h_p.each(function (i, ele) {
		$(ele).find('span').on("click", function () {
			$('.areatip').hide();
			h_span.removeClass('areaActive');
			if($(this).parent().index() == 1){
				$('.areainput').val('福建');
				$("#hid-area-id").val($(this).attr("data-city-id"));
				h_span.eq(1).addClass('areaActive')
			}else if($(this).parent().index() == 2){
				$('.areainput').val('福建厦门');
				$("#hid-area-id").val($(this).attr("data-city-id"));
				h_span.eq(2).addClass('areaActive')
			}
			if($(this).parent().index() == 3){
				var qu = $(this).html();
				$('.areainput').val('福建厦门' + qu);
				$("#hid-area-id").val($(this).attr("data-city-id"));
				$('.areaContain').slideUp();
			}else{
				h_p.hide();
				h_span.eq(i + 1).addClass('check_p');
				$(this).parent().next().show();
			}
		});
	});
	h_span.on('click',function(){
		var check = $(this).hasClass('check_p');
		var num = $(this).index();
		if(check == false){
			$('.erroratention').show();
		}else{
			$(this).siblings().removeClass('areaActive');
			$(this).addClass('areaActive');
			$('.areap').hide();
			$('.areap').eq(num).show()
			$(this).find('span').show();
		}
	});
	areaSpan.on('click',function(){
		var selSpan = $(this).hasClass('selSpan');
		if(selSpan == false){
			$(this).siblings().removeClass('selSpan')
			$(this).addClass('selSpan')
		}else{
			$(this).removeClass('selSpan')
		}
	})
}
