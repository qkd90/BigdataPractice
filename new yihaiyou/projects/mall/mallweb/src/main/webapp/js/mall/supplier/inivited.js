var inivited = {
	accountValid: false,
	imgFolder:'/logo/',
    
    init: function() {
    	// 表单验证
    	$("#ruzhu").validate({
            rules: {
            	'user.userName': {
                    required: true,
                    rangelength: [1, 20]
                },
                'user.account': {
                    required: true,
                    mobile: true
                },
                'user.email': {
                    required: true,
                    email: true,
                    rangelength: [1, 50]
                },
                'user.qqNo': {
                    required: true,
                    digits: true,
                    rangelength: [1, 20]
                },
                'unit.name': {
                    required: true,
                    rangelength: [1, 150]
                },
                'unitDetail.brandName': {
                    required: true,
                    rangelength: [1, 150]
                },
                'areaId': {
                    required: true
                },
                'filePath': {
                    required: true
                },
                'unit.address': {
                    required: true,
                    rangelength: [1, 150]
                },
                'unitDetail.telphone': {
                    required: true,
                    phone: true
                },
                'unitDetail.fax': {
                    required: true,
                    phone: true
                },
                'unitDetail.mainBody': {
                    required: true,
                    rangelength: [1, 150]
                },
                'unitDetail.mainBusiness': {
                    required: true,
                    rangelength: [1, 500]
                },
                'unitDetail.introduction': {
                    required: true,
                    rangelength: [1, 500]
                },
                'unitDetail.partnerChannel': {
                    required: true,
                    rangelength: [1, 150]
                },
                'unitDetail.partnerUrl': {
                    required: true,
                    url: true,
                    rangelength: [1, 150]
                },
                'unitDetail.partnerAdvantage': {
                    required: true,
                    rangelength: [1, 150]
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo($(element).parents(".form-group").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });

    	// 账户唯一性验证
        $("#account").focusout(function () {
            var account = $(this).val();
            if (account.trim().length < 1) {
                return;
            }
            if (!$(this).valid()) {
            	return;
            }
            $.get("/mall/supplier/validateMobile.jhtml", {mobile: account}, function (result) {
                if (result == "valid") {
                    $("#account-message").text("该手机号没有被使用过").removeClass("text-danger").addClass("text-success");
                    inivited.accountValid = true;
                    return;
                }
                if (result.length>50) {
                    console.log(result);
                    result = "服务器异常";
                }
                $("#account-message").text(result).addClass("text-danger").removeClass("text-success");
                inivited.accountValid = false;
            });
        });

        // 提交按钮事件
        $("#submit-button").click(function () {
            var valid = $("#ruzhu").valid();
            if (valid && inivited.accountValid) {
            	var filePath = $('#filePath').val();
            	if (!filePath) {
            		alert('请上传图片');
            		return ;
            	}
            	$("#ruzhu").submit();
            }
        });
        
        // 省市联动控件
        $('#provinceId').change(function() {
            var temp_html = '<option value="">请选择</option>';
            var fatherId = $('#provinceId').val();
            if (fatherId) {
            	$.post("/mall/supplier/listArea.jhtml", {fatherId: fatherId},
        			function(data){
        				if (data) {
        					for (var i = 0; i < data.length; i++) {
        						temp_html = temp_html+'<option value="'+data[i].id+'">'+data[i].name+'</option>';
        					}
        				}
                    	$('#areaId').html(temp_html);
        			}
        		);
            } else {
            	$('#areaId').html(temp_html);
            }
        });
		
		// 图片上传
		KindEditor.ready(function(K) {
			var uploadbutton = K.uploadbutton({
				button : K('#uploadButton')[0],
				fieldName : 'resource',
				extraParams : {oldFilePath:$('#filePath').val(), folder:inivited.imgFolder},
				url : '/mall/img/upload.jhtml',
				afterUpload : function(result) {
					/*$.messager.progress("close");*/
					if(result.success==true) {
						var url = K.formatUrl(result.url, 'absolute');
						$('#filePath').val(url);	
						$('#imgView img').attr('src', '/static'+url);
						$('#imgView').show();
					}else{
						alert("图片上传失败");
					}
				},
				afterError : function(str) {
					alert("图片上传失败");
				}
			});
			uploadbutton.fileBox.change(function(e) {
				var filePath = uploadbutton.fileBox[0].value;
				if (!filePath) {
					alert("图片格式不正确");
					return ;
				}
				var suffix = filePath.substr(filePath.lastIndexOf("."));
				suffix = suffix.toLowerCase(); 
				if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){ 
					alert("图片格式不正确");
					return ;
				}
				/*$.messager.progress({
					title:'温馨提示',
					text:'图片上传中,请耐心等待...'
				});*/
				
				$('input[name=oldFilePath]').val($('#filePath').val());	// 添加动态参数，隐藏标签是KindEditor自动生成的	
				uploadbutton.submit();
			});
		});

        // 默认提示框
        if($('#unitId').val()) {
        	$.post('/mall/build/buildOneSupplier.jhtml', {id:$('#unitId').val()},
    			function(data){
    			}
    		);
        	$('.ok').modal('show');
        }
        
    },
	// 删除图片
	delImg : function() {
		// 异步删除图片文件
		$.post("/mall/img/delFile.jhtml",
			{oldFilePath : $('#filePath').val()},
			function(result) {
				// TODO 暂时不做提示
    		}
		);
		
		$('#filePath').val('');	
		$('#imgView img').attr('src', '');
		$('#imgView').hide();
	},
    // 分销后确认
    doOk: function () {
        //window.location = "/shopcart/shopcart/orderjhtml?id=" + this.lineId + "&proType=line";
    	window.location = "/";
    },
    // 分销后取消
    doCancel: function () {
		$('.ok').modal('hide');
    }

};
$(function(){
	inivited.init();
});
