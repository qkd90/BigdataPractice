/**
 * 商品属性
 */
var datagrid;
var editRow = undefined; // 定义全局变量：当前编辑的行


(function($){
	//定义方法数组
	var methods={
			//快速查询获取商品
			fastAddGood:function(jq){
				return jq.each(function(){
					var dt=$(this);
					fastSelectGood(function(rows){
						for(var i=0;i<rows.length;i++){
							var row = rows[i];
							row.qty=1;
							dt.datagrid('appendRow', row);
							PurchasePaymentFormJS.updateFooter(dt);
						}
					},q);
				});
			},
			addSupplier:function(jq){
				return jq.each(function(){
					selectSupplier(function(rows){
						for(var i=0;i<rows.length;i++){
							var row = rows[i];
							$("#supplierId").val(row.id);
							$("#supplierName").val(row.supplierName);
						}
					});
				});
			}
	};
	
	//重写
	$.fn.detailgrid = function(options, param){
		if(typeof options=='string'){
			var method = methods[options];
			if (method){
				return method(this);
			} else {
				return this.datagrid(options);
			}
		}
		options=options||{};
		return this.each(function(){
			$.data(this, 'detailgrid', {
				options: options
			});
			buildGrid(this, options);
		});
	};
	
	//创建grid
	function buildGrid(target, options){
		purchaseApplydatagrid=$(target).datagrid($.extend({},options,{
			singleSelect:true, 
			rownumbers:true,
			showFooter:true,
			height:400,
			columns : [ [ 
				{
					title : 'id',
					field : 'id',
					width : 20,
					hidden : true
				},
				{
					title : '序号',
					field : 'showOrder',
					width : 100,
					editor : {
						type : 'text'
					}
				},
				{
					title : '后台显示值',
					field : 'nameAdmin',
					width : 150,
					editor : {
						type : 'text'
					}
				},
				{
					title : '前台显示值',
					field : 'name',
					width : 150,
					editor : {
						type : 'text'
					}
				}
			]],
			/*onClickCell:function(index,field){
				//双击开启编辑行
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                if (editRow == undefined) {
                    ProductPropertyAddJS.editCell(target, index, field);
                    editRow = index;
                }
			},*/
			onClickRow:function(index,field){
				ProductPropertyAddJS.editCell(target, index, field);
			},
			onBeforeLoad:function(){
				var propertyId = $("#propertyId").val();
				if (propertyId != null && propertyId != '' && propertyId != undefined) {
					ProductPropertyAddJS.showDetail(propertyId);
				}
				
				var rows = $('#dg-details').datagrid('getRows');
				 if(rows == undefined || rows == '' || rows == null || rows.length < 1){
						$('#dg-details').datagrid('appendRow',{rows:[{}]});
					}
			},
			onLoadSuccess:function(){
//				var rows = $('#dg-details').datagrid('getRows');
//				if(rows == undefined || rows == '' || rows == null || rows.length < 1){
//					$('#dg-details').datagrid('appendRow',{rows:[{}]});
//				}
			}
		}));
		
		
		//防止点击自身datagrid
		$(target).datagrid('getPanel').unbind('.detail').bind('mousedown.detail',function(e){
			e.stopPropagation();
		});
		
		
		$("#invoiceType").bind('change',function(){
			updateItems(target);
		});
	}
	
	
})(jQuery);


/**
 * 新增模块
 */
var ProductPropertyAddJS=$.fn.extend({
	    
        addLine:function () {//添加列表的操作按钮添加，修改，删除等
            $('#dg-details').datagrid('appendRow',{rows:[{}]});
        }, 
    
    
    /**
	 * 编辑单元格
	 */
	editCell : function (target,index,field){
		var opts = $(target).datagrid('options');
		debugger;
		if(opts.editIndex!=index){
			$(target).datagrid('endEdit', opts.editIndex);
			opts.editIndex = index;
			$(target).datagrid('selectRow', index);
			$(target).datagrid('beginEdit', index);
			var editors = $(target).datagrid('getEditors', index);
			var showOrder = editors[0];//序号
			var nameAdmin = editors[1];//属性名称后台
			var name = editors[2];//属性名称前端
			var editor =$(target).datagrid('getEditor', {index:index,field:field});
			if (editor){
				setTimeout(function(){
					editor.target.focus();//获取焦点
				}, 0);
			}
			/*showOrder.target.bind('blur',{field:showOrder.field},function(e){
				var row = $(target).datagrid('getSelected');
				if(row!=null){
					row.showOrder = showOrder.target.val();
					$(target).datagrid('updateRow',{index:index,row:row});
					$(target).datagrid('endEdit', opts.editIndex);
					opts.editIndex = undefined;
				}
			});
			nameAdmin.target.bind('blur',{field:nameAdmin.field},function(e){
				var row = $(target).datagrid('getSelected');
				if(row!=null){
					row.nameAdmin = nameAdmin.target.val();
					$(target).datagrid('updateRow',{index:index,row:row});
					$(target).datagrid('endEdit', opts.editIndex);
					opts.editIndex = undefined;
				}
			});
			
			name.target.bind('blur',{field:name.field},function(e){
				var row = $(target).datagrid('getSelected');
				if(row!=null){
					row.name = name.target.val();
					$(target).datagrid('updateRow',{index:index,row:row});
					$(target).datagrid('endEdit', opts.editIndex);
					opts.editIndex = undefined;
				}
			});
			*/
			for(var i=1; i<editors.length; i++){
				editors[i].target.bind('keydown',{field:editors[i].field},function(e){
					if(e.keyCode==13){
						var row = $(target).datagrid('getSelected');
						if(row!=null){
							row.showOrder = editors[0].target.val();
							row.nameAdmin = editors[1].target.val();
							row.name = editors[2].target.val();
							$(target).datagrid('updateRow',{index:index,row:row});
							$(target).datagrid('endEdit', opts.editIndex);
							opts.editIndex = undefined;
						}
					}
					if (e.keyCode == 38){	// up
						if (opts.editIndex > 0){
							editCell(target, opts.editIndex-1, e.data.field);
						}
					} else if (e.keyCode == 40){	// down
						if (opts.editIndex < $(target).datagrid('getRows').length-1){
							editCell(target, parseInt(opts.editIndex)+1, e.data.field);
						}
					}
				});
			}
		}
	},
	
	//删除商品
	removeObject : function(){
		var obj = $('#dg-details').datagrid("getSelected");
		var index = $('#dg-details').datagrid("getRowIndex",obj);
		$('#dg-details').datagrid('deleteRow',index);
	},
	
	/**
	 * 保存或修改
	 */
	saveProperty : function(flag) {
		var name = $("#name2").val();
		var propId = $("#propertyId").val();
		var nameAdmin = $("#nameAdmin2").val();
		
		var rows = $('#dg-details').datagrid("getRows");
		var params = [];
		debugger;
		for (var i=0; i<rows.length; i++) {
			if (!rows[i].showOrder) {
				continue;
			}
			var map={
					"id":rows[i].id,
					"name":rows[i].name,
					"showOrder":rows[i].showOrder,
					"nameAdmin":rows[i].nameAdmin
			};
			params.push(map);
		}
		var pp = {
				"id" : propId,
				"name" : name,
				"nameAdmin" : nameAdmin
		}
		var hiddenJson= JSON.stringify(params);
		var url = "/product/property/saveProperty.jhtml?ids="+ hiddenJson;
		if (flag == 'edit') {
			url = "/product/property/updateProperty.jhtml?ids="+ hiddenJson;
		}
		$.post(url, pp, function(result){
			if(result!=null){
				$.messager.progress("close");
				if(!result.success){
					$.messager.show({
						title : '温馨提示',
						msg : result.msg
					});
				}else{
					$.messager.show({
						title : '温馨提示',
						msg : result.msg
					});
					$("#addId").dialog('close');
					// 重新加载
					$("#dg").datagrid('reload');
				}
			}
		});
	},
	
	/**
	 * 展示属性值列表
	 * 循环添加一行
	 */
	showDetail : function(propertyId) {
		$.post("/product/property/findPropValsByPId.jhtml?id="+propertyId, function(result){
			if(result!=null){
				//循环遍历出来
				debugger;
				var data = result.rows;
				for(var i=0; i<data.length; i++) {
					// 构造数据， 添加一行
					var row = [];
					var map={
							"id":data[i].id,
							"name":data[i].name,
							"showOrder":data[i].showOrder,
							"nameAdmin":data[i].nameAdmin
					};
					row.push(map);
					$('#dg-details').datagrid('appendRow',map);
				}
				var rows = $('#dg-details').datagrid('getRows');
				for (var i=0; i<rows.length; i++) {
					if(!rows[i].id){
						$('#dg-details').datagrid('deleteRow', i);
						// 刷新
						//$('#dg-details').datagrid('reload');
					}
				}
			}
		});
	}

});
