$(document).ready(function(){
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
	},

	checkInput: function() {
		var flag = true;
		$.each($("input[require]"), function(i, perInput) {
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
				} else if ($(perInput).attr("data-temp-name") == "user-mobile") {
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
				} else if ($(perInput).attr("data-temp-name") == "user-account") {
					var reg = /^[\u0391-\uFFE5\w]+$/;
					if (!reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">用户名只允许汉字、英文字母、数字及下划线！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
						flag = false;
						return false;
					} else if (!CompanyInfoEdit.checkLoginAccount($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">用户名已被使用！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
						flag = false;
						return false;
					} else {
						$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						flag = true;
					}
				}  else {
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
			return false;
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
			return false;
		} else {
			$("#error-tip-"+ $("select[name='unitDetail.crtbnk']").attr("data-temp-name") +"").remove();
			flag = true;
		}

		if ($("input[name='unitDetail.crtCity.id']").val().length <= 0) {
			if ($("#error-tip-"+ $("input[name='unitDetail.crtCity.id']").attr("data-temp-name") +"").length <= 0) {
				$("input[name='unitDetail.crtCity.id']").after('<span class="erroratention" id="error-tip-'+ $("input[name='unitDetail.crtCity.id']").attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">请选择省市！</span></span>');
			}
			$("html, body").animate({
				scrollTop: $("input[name='unitDetail.crtCity.id']").offset().top - 50 }, {duration: 500,easing: "swing"});
			flag = false;
			return false;
		} else {
			$("#error-tip-"+ $("input[name='unitDetail.crtCity.id']").attr("data-temp-name") +"").remove();
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
				} else if ($(perInput).attr("valid") == "loginAccount") {
					var reg = /^[\u0391-\uFFE5\w]+$/;
					if ($(perInput).val().length <= 0) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length > 0) {
							$("#error-tip-"+ $(perInput).attr("data-temp-name") +"").remove();
						}
						$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">不能为空！</span></span>');
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else if (!reg.test($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
							$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">用户名只允许汉字、英文字母、数字及下划线！！</span></span>');
						}
						$("html, body").animate({
							scrollTop: $(perInput).offset().top - 50 }, {duration: 500,easing: "swing"});
					} else if (!CompanyInfoEdit.checkLoginAccount($(perInput).val())) {
						if ($("#error-tip-"+ $(perInput).attr("data-temp-name") +"").length <= 0) {
							$(perInput).after('<span class="erroratention" id="error-tip-'+ $(perInput).attr("data-temp-name") +'"><span class="gth">!</span><span id="error-tips">用户名已被使用！</span></span>');
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
				} else if ($(perInput).attr("valid") == "mobile1") {
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

	checkLoginAccount: function(value) {
		var flag = true;
		$.ajax({
			tyep: "post",
			dataType: "json",
			async: false,//是否异步执行（该属性已被遗弃）
			url: "/yhypc/user/validateUser.jhtml",
			data: "accountUser.account=" + value,
			success: function (result) {
				if (result.notExisted) {
					flag = true;
				} else {
					flag = false;
				}
			},
			error: function (errorMSG) {
				flag = false;
			}
		});

		return flag;
	},

	initJsp: function() {

		$(".closebtn").on("click", function() {
			$(".windowShadow").hide();
			$(".attentionBox").hide();
		});

		//初始化省市区
		$("#bank-province").click(function (e) {
			var settings = {
				qryData:{
					'tbArea.id': 100000,
					'tbArea.level': 1
				},
				url : "/yhypc/company/getAreaList.jhtml"
			}
			SelCity(this, e, settings);
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

		var data = {};

		$.each($("input[name^='user.']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});
		$.each($("input[name^='unit.']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});
		$.each($("input[name^='unitDetail.']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});
		$.each($("select[name^='unitDetail.']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});
		$.each($("textarea[name^='unitDetail.']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});
		$.each($("input[name^='unitImages']"), function(i, perInput) {
			data[''+ $(perInput).attr("name") +''] = $(perInput).val();
		});


		$.ajax({
			url: '/yhypc/company/saveCompanyInfo.jhtml',
			data: data,
			type: "post",
			progress: true,
			success: function(result) {
				if (result.success) {

					$.message.alert({
						title:"提示",
						info:result.info,
						afterClosed: function() {
							window.location.href = COMPANY_LOGIN_URL;
						}
					});

				} else {

					$.message.alert({
						title:"提示",
						info:result.info
					});
				}
			},
			error: function() {
				$.message.alert({
					title:"提示",
					info:"保存错误！"
				});
			}
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
		});
		tag.on('click',function(){
			var val = $(this).html();
			$('.areaList').children().removeClass("selSpan");
			$(this).addClass("selSpan");
			$("#hid-area-id").val($(this).attr("data-city-id"));
			$('#sel-area').val(val);
		})
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
						$('.f-idcard').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' require><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					} else {
						$('.z-idcard').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' require><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					}
				});
			} else {
				$('.f-idcard').empty().append("<input type='hidden' name='imgpath' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' require>");
				$('.z-idcard').empty().append("<input type='hidden' name='imgpath' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' require>");
			}

			CompanyInfoEdit.initZidcardUploader();
			CompanyInfoEdit.initFidcardUploader()
		} else {
			$(".passport-li").show();
			$(".passport").show();

			if ($("input[name='imagePath']").length > 0) {
				$.each($("input[name='imagePath']"), function(i, perImg) {
					if($(perImg).attr("data-type") == "PARSSPORT") {
						$('.passport').empty().append("<input type='hidden' name='imgpath' value='"+ $(perImg).val() +"' data-type='PARSSPORT' data-temp-name='imgpath-passport' require><img src='"+ QINIU_BUCKET_URL + $(perImg).val() +"'>");
					}
				});
			} else {
				$('.passport').empty().append("<input type='hidden' name='imgpath' data-type='PARSSPORT' data-temp-name='imgpath-passport' require>");
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
			server: '/yhypc/upload/imageUpload.jhtml',
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
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='POSITIVE_IDCARD' data-temp-name='imgpath-z-idcard' require>" +
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
			server: '/yhypc/upload/imageUpload.jhtml',
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
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='OPPOSITIVE_IDCARD' data-temp-name='imgpath-f-idcard' require>" +
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
			server: '/yhypc/upload/imageUpload.jhtml',
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
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='BUSINESS_LICENSE' data-temp-name='imgpath-busines' require>" +
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
			server: '/yhypc/upload/imageUpload.jhtml',
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
			$div.empty().append("<input type='hidden' name='imgpath' value='"+ response.path +"' data-type='PARSSPORT' data-temp-name='imgpath-passport' require>" +
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
