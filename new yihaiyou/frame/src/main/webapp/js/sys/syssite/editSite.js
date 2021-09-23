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
var editSite = {
	imgFolder:'/logo/',
	
	init:function(){
		editSite.initComp();
		editSite.initStatus();
	},	
	// 初始控件
	initComp : function() {

		// 省联动控件
		var city = $('input[name="areaId"]').val();
		$("#province").combobox({ 
		  	url:"/sys/area/listArea.jhtml", 
		  	valueField:"id", 
		  	textField:"name",
			onChange : function(newValue, oldValue) {
				$("#areaId").combobox("clear");
				if (newValue) {
				  	var url = "/sys/area/listArea.jhtml?fatherId="+newValue;
					$("#areaId").combobox("reload", url);
					if (city) {	// 只做一次赋值
						$("#areaId").combobox('setValue', city);
						city = null;
					}
				}
			},
			onLoadSuccess : function() {
				if (city) {
					var pro = city.substr(0,2)+'0000';
					$("#province").combobox('setValue', pro);
				}
			}
		});
		
		// 市控件
		$("#areaId").combobox({ 
			data:[],
		  	valueField:"id", 
		  	textField:"name"
		});
		
		// 图片上传
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#uploadButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#filePath').val(), folder:editSite.imgFolder},
				url : '/sys/imgUpload/upload.jhtml',
				afterUpload : function(result) {
					$.messager.progress("close");
					if(result.success==true) {
						var url = K.formatUrl(result.url, 'absolute');
						$('#filePath').val(url);	
						$('#imgView img').attr('src', '/static'+url);
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
		$.post("/sys/imgUpload/delFile.jhtml",
			{oldFilePath : $('#filePath').val()},
			function(result) {
				// TODO 暂时不做提示
    		}
		);
		
		$('#filePath').val('');	
		$('#imgView img').attr('src', '');
		$('#imgView').hide();
	},
	// 保存
	doSave:function(){
		// 保存表单
		$('#editForm').form('submit', {
			url : "/sys/sysSite/saveSite.jhtml",
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
//				alert(isValid);
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					$.messager.alert('温馨提示', '操作成功', 'info', function() {
						parent.window.SysSite.closeEditPanel(true);
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
	//清除表单
	clearForm:function(){
		$("#editForm").form("clear");
	}
};
$(function(){
	editSite.init();
});