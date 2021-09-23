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
	                url: "/sys/sysUser/validateMobile.jhtml?neUserId="+neUserId,
	                data: "mobile="+value,
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
	}
});
var editSupplierSelf = {
	imgFolder:'logo',
	
	init:function(){
		editSupplierSelf.initComp();
		editSupplierSelf.initStatus();
		editSupplierSelf.initCheckContract();
		if ($("#startCityId").val()) {
			AreaSelectDg.initArea($("#startCity"), $("#startCityId").val());
		}
		editSupplierSelf.initFrameHeight();
	},
	initFrameHeight: function() {
		window.parent.$("#editIframe").css("height", $("#content").height() + 50);
	},
	// 初始控件
	initComp : function() {

		// 图片上传
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#uploadButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#filePath').val(), folder:editSupplierSelf.imgFolder},
				url : '/sys/imgUpload/uploadQiniu.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						var url = K.formatUrl(result.url, 'absolute');
						$('#filePath').val(url);	
						$('#imgView img').attr('src', url);
						$('#imgView').show();
					}else{
						show_msg("图片上传失败");
					}
				},
				afterError : function(str) {
					show_msg("图片上传失败");
				}
			});
			uploadbutton.fileBox.change(function(e) {
				var filePath = uploadbutton.fileBox[0].value;
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
				
				$('input[name=oldFilePath]').val($('#filePath').val());	// 添加动态参数，隐藏标签是KindEditor自动生成的	
				uploadbutton.submit();
			});
		});
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
				var isValid = $(this).form('validate');
				if(isValid){
					var filePath = $('#filePath').val();
					if (!filePath) {
						show_msg("请上传图片");
						return false;
					}
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
					FxUtil.buildSupplier(result.id);
					show_msg("保存成功");
				}else{
					show_msg("保存失败");
				}
			}
		});
		//parent.window.showGuide(2, true);	
	},	
	// 返回
	doBack: function() {
		parent.window.auditList.closeViewPanel(false);
	},
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	},

	initCheckContract: function() {
		var url = "/contract/contract/isHasContract.jhtml";
		$.post(url, function(data) {
			if (data.isHas) {
				$("#contractUrl").append('<a href="javascript:void(0);" onclick="editSupplierSelf.doViewContract()">查看合同</a>');
			} else {
				$("#contractUrl").append('暂无合同或和已失效');
			}
		});

	},
	// 返回
	doBack: function() {
		parent.window.auditList.closeViewPanel(false);
	},

	download: function(imgPathURL) {
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

	doViewContract: function() {
		var url = "/contract/contract/viewContract.jhtml";
		// 打开编辑窗口
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		$("#editPanel").dialog({
			title:'合同详情'
		});
		$("#editPanel").dialog("open");
	}
};
$(function(){
	editSupplierSelf.init();
});