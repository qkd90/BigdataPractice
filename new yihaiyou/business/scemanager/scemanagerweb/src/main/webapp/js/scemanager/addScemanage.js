$.extend($.fn.validatebox.defaults.rules, {
	validateAccountExist: {
		validator: function (value, param) {
			var b = false;
			if (value) {
				//var neUserId = $('#userId').val();
				$.ajax({
					tyep: "post",
					dataType: "json",
					async: false,//是否异步执行（该属性已被遗弃）
					url: "/scemanager/scemanager/checkAccountIsValidate.jhtml",
					data: "newName="+value,
					success: function (result) {
						b = result.success;
					},
					error: function (errorMSG) {
						b = false;
					}
				});
			}
			return b;
		},
		message: '该帐号已被使用'
	}
});


var AddScemanage= {
		logopre:'/scemanager/scemanagerLogo/',
		init:function(){
			AddScemanage.initProCommbox();
			AddScemanage.initKindEditor();
			AddScemanage.initCommbox();
		},
		
		// 清除表单
		clearForm : function() {
			$("#ff").form("reset");
		},
		
		removeImg:function(){
//			$("#filePath").val();
			
			$.post('/scemanager/scemanager/delFile.jhtml', {
				oldFilePath : $("#filePath").val()
			}, function(result) {
//				if (result.success) {
//					$("#dg").datagrid("reload");
//					show_msg("重置密码成功");
//				} else {
//					show_msg(result.errorMsg);
//				}
			});
			
			$('#filePath').val('');	
			$('#imgView img').attr('src', '');
			$('#imgView').hide();
		},
		
		
		initCommbox:function(){
			
			
			$("#combobox_sitename")
			.combobox(
					{
						onSelect : function(param) {
							var id = param.id;
							var name = param.name;
							
							$("#sitename_id").val(name);
							$("#hidden_scenicid").val(id);
							
						}
					});

            //
            //
			//$("#ipt_accountId").textbox({
			//	onChange: function(newValue, oldValue) {
			//		if (newValue) {
			//			var url = "/scemanager/scemanager/checkAccountIsValidate.jhtml";
			//			var data = {
			//				newName: newValue
			//			};
            //
			//			$.ajax({
			//				type: "POST",
			//				url: url,
			//				dataType: "json",
			//				data: data,
			//				async: false,//是否异步执行,
			//				success: function(msg){
			//					if (msg.success) {
			//						$("#tip_span").css("color", "#1aff09");
			//						$("#tip_span").html("此帐可以使用");
			//					} else {
			//						$("#tip_span").css("color", "red");
			//						$("#tip_span").html("此帐号已被占用，请重新填写");
            //
			//					}
			//				}
			//			});
            //
			//		} else {
			//			$("#tip_span").css("color", "");
			//			$("#tip_span").html("");
			//		}
            //
			//	}
			//});
            //


		},
		

		initKindEditor:function(){
			
			
			// 图片上传
			KindEditor.ready(function(K) {
				var uploadbutton = K.uploadbutton({
					button : K('#updateLogo')[0],
					fieldName : 'resource',
					url : '/scemanager/scemanager/upload.jhtml',
					extraParams : {oldFilePath:$('#filePath').val(),folder:AddScemanage.logopre},
					afterUpload : function(result) {
						$.messager.progress("close");
						if(result.success==true) {
							var url = K.formatUrl(result.url, 'absolute');
							
							$('#filePath').val(url);
							url = "/static"+url;
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
					uploadbutton.submit();
				});
			});
	

		},
		
		
		
		
		scenicLoader:function(param, success, error) {
			var q = param.q || '';
			if (q.length <= 1) {
				return false
			}
			$.ajax({
				url : '/scemanager/scemanager/getScenicList.jhtml',
				dataType : 'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				method : 'POST',
				data : {
					featureClass : "P",
					style : "full",
					maxRows : 20,
					name_startsWith : q
				},
				success : function(data) {

					
					var items = $.map(data, function(item) {
						return {
							id : item.id,
							name : item.name
							
						};
					});
					success(items);
				},
				error : function() {
					error.apply(this, arguments);
				}
			});
		},
		
		// 提交表单
		submitForm : function() {
//			$.messager.progress({
//				title : '温馨提示',
//				text : '数据处理中,请耐心等待...'
//			});
			$('#ff').form('submit', {
				url : "/scemanager/scemanager/addSceManager.jhtml",
				onSubmit : function() {
					if ($(this).form('validate') == false) {
						$.messager.progress('close');
					}
					return $(this).form('validate');
				},
				success : function(result) {
					
//					$.messager.progress("close");
					var result = eval('(' + result + ')');
					if (result.success == true) {
						show_msg("保存成功!");
						window.parent.$("#edit_panel").dialog("close");
						window.parent.$("#dg").datagrid("reload");
					} else {
						show_msg(result.errorMsg);
					}
					
				}
			});
		},
		
		
		
		initProCommbox:function(){
    		
    		$("#sec_proNameId")
			.combobox(
					{
						 url:'/scemanager/scemanager/getProvince.jhtml?name_startsWith=100000', 
				          editable:false, //不可编辑状态
				          cache: false,
						  width:150,
				          panelHeight: '200',//自动高度适合
				          valueField:'id',   
				          textField:'name',
				          
				          
				          onHidePanel: function(){
					      $("#sec_cityNameId").combobox("setValue",'');
					      var lanmuid = $('#sec_proNameId').combobox('getValue');	
					      $.ajax({
					        type: "POST",
					        url: "/scemanager/scemanager/getCity.jhtml?name_startsWith="+lanmuid,
					        cache: false,
					        dataType : "json",
					        success: function(data){
					        	$("#sec_cityNameId").combobox("loadData",data);
					        }
					       }); 	
					          },
				          onLoadSuccess : function() {
								var city = $('#startCityIdHidden').val();
								if (city) {
									var pro = city.substr(0,2)+'0000';
									$("#sec_proNameId").combobox('setValue', pro);
								}
							}
					});
			
			
			$('#sec_cityNameId').combobox({ 
			      //url:'itemManage!categorytbl', 
			      editable:false, //不可编辑状态
			      cache: false,
				  width:150,
			      panelHeight: '200',//自动高度适合
			      valueField:'id',   
			      textField:'name',
			      
			      onLoadSuccess : function() {
						var city = $('#startCityIdHidden').val();
						
						if (city) {
							city = city.substring(0,4)+"00";
					      $.ajax({
					        type: "POST",
					        url: "/scemanager/scemanager/getAreaByCitycode.jhtml?cityCode="+city,
					        cache: false,
					        dataType : "json",
					        success: function(data){
//					        	$("#sec_cityNameId").combobox('setValue', data.id);
//					        	$("#sec_cityNameId").combobox('setText', data.name);
					        }
					       });
						
						
						
							
						}
					} 
			      
			     });



		}
};

$(function(){
	AddScemanage.init();
});