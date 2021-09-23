var sy = $.extend({}, sy);// 定义全局对象，类似于命名空间或包的作用 

var picFlag = false;
var describe="";
var datagrid;//商品管理的datagrid
var mkdatagrid;//商品组成的datagrid
var mkdialog;//商品组成
var mkInfodatagrid;//物料的datagrid
var productCatTree;//商品分类树
var url;
var editIndex = undefined;//结束编辑


/********自定义dialog**************/
sy.dialog = function(options) {
	var opts = $.extend({
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	return $('<div/>').dialog(opts);
};


//当载入的时候执行
$(function() {
	ProductJS.initHeight();
	ProductJS.initImage();
	ProductJS.initProductCatTree();
	ProductJS.init();
});

/**
 * 商品模块的JS文件
 */
var ProductJS = $.extend({
	//////////////////////////////////初始化部分资源///////////////////////////////////////////////////
	
	/**
	 * 初始化table高度
	 */
	initHeight : function() {
		//可见区域高度
		var cWidth = document.body.clientHeight;
		//查询条件部分高度
		var topWidth = document.getElementById("id_top").scrollHeight;
		//重设表格数据高度
		$("#dg")[0].style.height = cWidth - topWidth ;
	},
	
	/**
	 * 初始化图片
	 */
	initImage : function() {
		$("#uploadify").uploadify({
			'swf' : '/Backstage/Style/jQuery-uploadify/uploadify.swf',
			// 'script' : 'ProductAction!fileUpload.do',//后台处理的请求
			'uploader' : '/product/product/fileUpload.jhtml',
			'cancelImg' : '/Backstage/Include/images/vtip_arrow.png',
			'folder' : '/Uplaod/temp',//您想将文件保存到的路径
			'queueID' : 'fileQueue',//与下面的id对应
			'fileObjName' : 'uploadify',
			'queueSizeLimit' : 5,
			'fileDesc' : '图片格式',
			'fileTypeExts' : '*.gif;*.jpg;*.png', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			'auto' : false,
			'multi' : true,
			'simUploadLimit' : 2,
			'buttonText' : '选择图片',
			'onQueueComplete' : function(file, data, response) {
				picFlag = true;
			}
		});
	},
	
	/**
	 * 商品分类树 
	 */
	initProductCatTree : function() {
		productCatTree=$('#tt').tree({
			url : '/product/productCat/treeView.jhtml',
			method : 'post',
			animate : true,
			dnd : true,
			onClick : function(node) {
				$('#dg').datagrid('load', {
					'product.productCat.id' : node.id
				});
			}
		});
	},

	
	//////////////////////////////////查询商品///////////////////////////////////////////////////
	/**
	 * 初始化网格，数据
	 */
	init : function() {
		datagrid=$("#dg").datagrid({
			title:'商品管理',
			url:'/product/product/search.jhtml',
			idField: 'id',
			pagination:true,
			rownumbers:true,
			fitColumns:false,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			frozenColumns: [[
			                 { field: 'ck', checkbox: true },
			                 { field: 'id', title: 'id', width: 80, sortable: true ,hidden:true}
			                 ]],
			columns: [[
	                   { field: 'proNo', title: '商品编码', width: 120, sortable: true },
	                   { field: 'proName', title: '商品名称', width: 300, sortable: true },
	                   { field: 'specifications', title: '规格', width: 100 },
	                   { field: 'proUnit', title: '单位', width: 70, sortable: true },
	                   { field: 'weight', title: '重量（克）', width: 80, sortable: true },
	                   { field: 'costPrice', title: '成本价（元）', width: 80, sortable: true },
	                   { field: 'salePrice', title: '销售价（元）', width: 80, sortable: true },
	                   { field: 'purchasePrice', title: '采购价（元）', width: 80, sortable: true },
	                   { field: 'supplierName', title: '供应商', width: 200, sortable: true,formatter : 
	                	   function(value, row, index) {
	                	   	if(row.supplier!=null){
	                	   		return row.supplier.supplierName;
	                	   	}else{
	                	   		return "";
	                	   	}
	                	   }
	                   },
	                   { field: 'status', title: '状态', width: 70, sortable: true },
	                   { field: 'productType', title: '商品标识', width: 80, sortable: true,formatter:function(val){
	                	   if(val==0){
	                		   return "自营商品";
	                	   }else if(val==1){
	                		   return "联营商品";
	                	   }
	                   }}
	               ]]
		});
	},
	
	/**
	 * 商品管理界面的商品资料查询
	 */
	doSearch : function() {
		datagrid.datagrid('clearSelections');
		datagrid.datagrid('load', {
			'proName' : $('#proName').val(),
			'proNo' : $('#proNo').val(),
			'supplier.supplierName' : $('#supplierName').val(),
			'isDis' : $("#isDis").combobox("getValue")
		});
	},
	

	//////////////////////////////////删除商品///////////////////////////////////////////////////
	/********删除商品信息*************/
	destroy : function() {
		jQuery.ajaxSettings.traditional = true;
		var row = $('#dg').datagrid('getSelections');
		if(row.length!=0){
			var array = new Array();
			$.each(row, function(n, value) {
				array.push(value.id);
			});
			if (row) {
				$.messager.confirm('温馨提示', '确定要删除该商品信息吗？', function(r) {
					if (r) {
						$.get('/product/product/deleteBatch.jhtml', {
							'idList' : array,
						}, function(result) {
							if (result.success) {
								datagrid.datagrid('reload'); 
								$.messager.show({ 
									title : '温馨提示',
									msg : '成功删除该商品信息！'
								});
							} else {
								$.messager.show({ 
									title : '温馨提示',
									msg : result.errorMsg
								});
							}
						}, 'json');
					}
				});
			}
		}else{
			$.messager.alert("温馨提示","请先选择要删除的商品信息","warning");
		}
	},

	//////////////////////////////////编辑商品///////////////////////////////////////////////////
	/**编辑商品**/
	editProduct : function() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$("#addOrEdit").dialog({
				title:'编辑商品信息',
				fit:true,
				modal:true, 
				width: 900,    
			    height: 500,
				onLoad:function(){
					ProductEdit.initData();
				}
			});
			$("#addOrEdit").dialog('open').dialog('refresh','/product/product/productEdit.jhtml?id='+row.id);
		} else {
			$.messager.alert("温馨提示", "请选择要编辑的商品信息","warning");
		}
	},
	
	//////////////////////////////////组合清单///////////////////////////////////////////////////
	/**
	 * 打开组合清单界面
	 */
	openCombinDetail : function(){
		//////////////////
		var editIndex = undefined;
		var row = $("#dg").datagrid("getSelected");
		if (row == null || row == '' || row == undefined){
			//提示
			$.messager.alert("提示：","未选中行!");
			return false;
		}
		$("#combinDetailWin").dialog({
			fit:true,
			title:'商品组合',
			modal:true,
			toolbar:'#cd-buttons1',
			buttons:'#cd-buttons2',
			onLoad:function(){
			}
		});
		
		var productId = row.id;
		$("#combinDetail").datagrid({
			url:'/product/product/searchProductDetail.jhtml?id='+productId,
			nowrap:false,
			height:400,
			rownumbers:true,
			fitColumns:true,
			singleSelect:true,
			columns: [[
				  { field: 'bId', title: '组合商品Id', width: 200, hidden : true},
		          { field: 'proNo', title: '商品编码', width: 200},
	              { field: 'proName', title: '商品名称', width: 400
	              },
	              { field: 'proUnit', title: '单位', width: 100
	              },
	              { field: 'comBinQty', title: '数量', width: 100, editor:{type:'numberbox',options:{precision:0,required:'true'}}}
	        ]],
	        onClickRow:function(index, rowData){
	        	if (editIndex != index) {
	        		//判断是否有未关闭的编辑
	        		if (endEditing()) {
	        			$("#combinDetail").datagrid('selectRow', index).datagrid('beginEdit', index);
	        			editIndex=index;
	        		} else {
	        			$("#combinDetail").datagrid('selectRow', editIndex);
	        		}
	        	}else{
	        		if (endEditing()) {
	        			$("#combinDetail").datagrid('selectRow', index).datagrid('beginEdit', index);
	        			editIndex=index;
	        		} else {
	        			$("#combinDetail").datagrid('selectRow', editIndex);
	        		}
	        	}
	        	editIndex=index;
	        	
	        	function endEditing() {
	        		if (editIndex == undefined) {
	        			return true;
	        		}else{
	        			//判断是否为空,为空补默认值
	        			$("#combinDetail").datagrid('endEdit', editIndex);
	        			editIndex = undefined;
	        			return true;
	        		}
	        	}
	        },
	        onAfterEdit : function(rowIndex, rowData, changes){
//	        	$("#combinDetail").datagrid('endEdit', rowIndex);
	        	editIndex = undefined;
	        }
		});
	},
	
	/**
	 * 添加组合清单中的物料信息
	 */
	addProBill : function() {
		$('#combinAddPro').dialog({
			title : '新增商品信息',
			width : 1024, 
			height : 500
		});
		$('#combinAddPro').datagrid({
			title:'商品管理',
			url:'/product/product/search.jhtml',
			pagination:true,
			singleSelect:false,
			selectOnCheck:false,
			checkOnSelect:false,
			columns: [[
			         { field: 'proNo', title: '商品编码', width: 150, sortable: true },
	                 { field: 'proName', title: '商品名称', width: 350, sortable: true },
	                 { field: 'salePrice', title: '销售价', width: 100, sortable: true },
	                 { field: 'costPrice', title: '成本价', width: 100, sortable: true },
	                 { field: 'proUnit', title: '单位', width: 100, sortable: true },
	                 { field: 'status', title: '状态', width: 100, sortable: true },
	                 { field: 'supplierName', title: '供应商', width: 150, sortable: true,formatter : 
	              	   function(value, row, index) {
	              	   	if(row.supplier!=null){
	              	   		return row.supplier.supplierName;
	              	   	}
	              	   }
	                 },
	         ]],
	         onDblClickRow:function(rowIndex, rowData){
	        	$('#combinDetail').datagrid('appendRow',rowData);
	 		 	$("#combinAddPro").dialog('close');
	        }
		});
		function bindEvents(){
			//绑定选中
			$("#queryPurchaseApply-ok").unbind("click").bind('click',function(e){
				$('#combinAddPro').dialog('close');
				var rows = $('#combinAddPro').datagrid('getSelections');
				if (rows != undefined && rows.length > 0) {
					for(var i=0; i<rows.length; i++) {
						$('#combinDetail').datagrid('appendRow',rows[i]);
					}
				}
			});
		}
		bindEvents();
	},
	

	/**
	 * 删除组合清单中的物料
	 */
	delProBill : function() {
		var flag = this.getDataTotal("combinDetail");
		if (! flag) {
			return false;
		}
		var row=$("#combinDetail").datagrid("getSelected");
		if(row == null || row.length <= 0){
			$.messager.alert("提示：","请选择要删除的数据!");
		}else{
			$("#combinDetail").datagrid("deleteRow",$("#combinDetail").datagrid("getRowIndex",row));
		}
	},
	
	/**
	 * 获取所有数据个数，并提示
	 */
	getDataTotal : function(id) {
		var obj = $("#"+id).datagrid("getData");
		var total = obj.total;
		if (total <= 0){
			$.messager.alert("提示：","暂无数据!");
			return false;
		}
		return true;
	},

	/**
	 * 编辑组合清单中的物料信息
	 */
	editProBill : function () {
		this.endAllEdit();
		var flag = this.getDataTotal("combinDetail");
		if (! flag) {
			return false;
		}
		var row=$("#combinDetail").datagrid("getSelected");
		if(row==null||row.length==0){
			$.messager.alert("提示：","未选中编辑数据!");
			return false;
		}else{
			$.messager.progress({
			     title:'正在操作中,请稍候...'
			});
			var param = {
					"hiddenjson":JSON.stringify(row)
			};
			$.post("/product/product/editProBill.jhtml",param,function(result){
				if(result!=null){
					$.messager.progress("close");
					if(!result.success){
						$.messager.show({
							title : '温馨提示',
							msg : result.errorMsg
						});
					}else{
						$.messager.show({
							title : '温馨提示',
							msg : '商品组合修改成功!'
						});
					}
				}
			});
		}
	},
	
	/**
	 * 保存组合清单	
	 */
	saveProBill : function() {
		this.endAllEdit();
		$('#combinDetail').datagrid('selectAll');//所有选择的行
		var row = datagrid.datagrid('getSelected');
		var id=row.id;
		var rows=$('#combinDetail').datagrid("getRows");
		if (rows.length <= 0) {
			$.messager.alert("提示：","暂无商品，无法保存!");
			return false;
		}
		for (var i=0; i<rows.length; i++){
			var obj = rows[i];
			var comBinQty = obj.comBinQty;
			if (comBinQty == null || comBinQty == undefined || comBinQty == '') {
				$.messager.alert("提示：", "数量不能为空");
				return false;
			}
		}
		var hiddenjson=JSON.stringify(rows);
		$("#hiddenjson").val(hiddenjson);
		
		$.messager.progress({
		     title:'加载中...',
		     msg:'正在操作中,请稍候...',
		});
		var param = {
			"hiddenjson":hiddenjson,
			"id":id
		};
		$.post("/product/product/saveCombinDetail.jhtml",param,function(result){
				if(result!=null){
					$.messager.progress("close");
					if(!result.success){
						$.messager.show({
							title : '温馨提示',
							msg : result.errorMsg
						});
					}else{
						$.messager.show({
							title : '温馨提示',
							msg : '商品组合保存成功!'
						});
//						$('#dlgMakeUpInfo').dialog('close');
					}
				}
			});
	},
	/**
	 * 结束组合清单框编辑
	 */
	endAllEdit : function () {
		var rows = $('#combinDetail').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			$('#combinDetail').datagrid('endEdit',i);
		}
	},
	
	/**
	 * 商品管理界面的商品资料查询
	 */
	doSearch2 : function() {
		$("#combinAddPro").datagrid('load', {
			'proNo' : $('#proNo2').val()
		});
	},

	//////////////////////////////////////////新增商品/////////////////////////////////////////////////	

	/** 新增商品 */
	newProduct : function() {
		$("#addOrEdit").dialog({
			title:'新增',
			fit:true,
			modal:true, 
			width: 900,    
		    height: 500,
			onLoad:function(){
				ProductJS.loadRes();
			}
		});
		$("#addOrEdit").dialog('open').dialog('refresh','/product/product/productAdd.jhtml?');
	},
	
	/**
	 * 新增商品初始化
	 * @returns
	 */
	loadRes : function() {
		var f = $('#fm');
		var productCat = f.find('input[name=productCatId]');
		productCat.combotree({
			lines : true,
			url : '/product/productCat/treeView.jhtml'
		});
		var editor=new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:960 });
		editor.render('addEditor');
		
		$.post("/product/product/findMaxNo.jhtml",{},function(result){
			//给商品编码设值
			$("#product_prono").val(result.maxno);
		});
	},

	/**
	 * 保存新增商品
	 */
	save : function(){
		var t = $('#catTree').combotree('tree');	// 获取树对象
		var catlist = t.tree('getSelected');	// 获取选择的节点
		if(catlist==null||(catlist!=null&&(catlist.text=='商品总分类'||catlist.text==''))){
			$.messager.show({
				title : '温馨提示',
				msg : '请选择商品类别！'
			});
			return false;
		}
		$("#productCatId").val(catlist.id);
		var f = $('#fm');
		f.form('submit', {
			url : '/product/product/add.jhtml?catId='+catlist.id,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(results) {
				var result = eval('(' + results + ')');
				if (result.msg) {
					$.messager.show({
						title : '温馨提示',
						msg : result.msg
					});
				} else {
					$.messager.alert("温馨提示","商品新增成功","info",function(){
						location.href="/product/product/manage.jhtml";
					});
				}
			}
		});
	},
	/**
	 * 供应商查询
	 * @returns
	 */
	searchSupplier : function(){ 
	    $('#selDg').combogrid('grid').datagrid('load',{  
	    	'supplier.supplierCode': $('#supplierCode').val(),  
	    	'supplier.supplierName': $('#suppliername').val()
	    });  
	},

	/**
	 * 是否是虚拟产品
	 */
	isSelect : function() {
		var b = $("#isVirtual")[0].checked;
		if(b == true) {
			$("#isVirtual").val(1);
		} else {
			$("#isVirtual").val(0);
		}
	},

	
	//////////////////////////////////////////商品清单/////////////////////////////////////////////////	
	/**
	 * 商品编辑组成页面*
	 */
	editMakeUp : function() {
		var row = datagrid.datagrid('getSelected');
		if(row!=null){
		var makeupId = row.id;// 主商品ID
		mkdialog=sy.dialog({
			title:'编辑【<font style="color:red">'+row.proName+'</font>】的商品组成',
			href : '/product/product/productMakeUp.jhtml',
			width : 1000,
			height : 550,
			top : 10,
			modal:true,
			onLoad:function(){//加载物料组成的网格
				var f=mkdialog.find('#productMakeUpType');
				var makeUpTable = f.find('#dgMakeUp'); // 表格展示数据
				var makeUpType = f.find('#makeUpTypeId');// 左侧树状菜单
				$("#main_product_id").val(row.id);//新增需要的参数
				
				productCatTree=makeUpType.tree({
					url : '/product/productCat/typeTreeView.jhtml?makeupId='+makeupId,
					method : 'post',
					animate : true,
					lines : true,
					onClick : function(node) {
						$("#node_id").val(node.id);
						$("#node_name").val(node.text);
						mkdatagrid.datagrid('load', {
							'makeupName' : node.text
						});
					}
				});
				mkdatagrid=makeUpTable.datagrid({
					height:480,
					url:'/product/productMakeUp/search.jhtml',//查询商品组成
					idField: 'proId',
					pagination:true,
					rownumbers:true,
					fitColumns:true,
					singleSelect:true,
					queryParams: {//
						id:row.id
					},
					columns: [[
			                   { field: 'proName', title: '物料名称', width: 250,editor:{type:'text'}},
			                   { field: 'proNo', title: '商品编码', width: 150,align:'center'},
			                   { field: 'proUnit', title: '单位', width: 100,align:'center'},
			                   { field: 'productCount', title: '组成数量', width: 100,align:'center',editor:{type:'numberbox',options:{precision:4,required:'true'}}},
			               ]],
			         onClickRow:function(rowIndex, rowData){//编辑行
			        	 ProductJS.onClickRow(rowIndex);
			         },
			         onLoadSuccess:function(data){
			        	 makeUpTable.datagrid('appendRow',{rows:[{}]});
					 },
			         toolbar : [ {
			   				text : '增加',
			   				iconCls : 'icon-add',
			   				handler : function() {
			   					ProductJS.newRaw($("#node_id").val(),makeupId);
			   				}
			   			}, '-', {
			   				text : '删除',
			   				iconCls : 'icon-remove',
			   				handler : function() {
			   					ProductJS.deleteRaw();
			   				}
			   			}, '-', {
			   				text : '保存',
			   				iconCls : 'icon-save',
			   				handler : function() {
			   					ProductJS.saveRaw($("#node_id").val(),makeupId);
			   				}
			   			}, '-', {
			   				text : '撤销更改',
			   				iconCls : 'icon-undo',
			   				handler : function() {
			   					ProductJS.rejectProduct();
			   				}
			   			}],
				});
			}
		});
		}else{
			$.messager.alert("温馨提示", "请选择要编辑的商品组成信息","warning");
		}
	},
	
	
	//////////////////////////////////////////商品清单/////////////////////////////////////////////////
	
	
	//////////////////////////////////////////导入商品编码文件打印/////////////////////////////////////////////////
	// 跳转上传文件弹出框
	importPrintPronoUrl : function(){
		$('#uploadWindow').window({
			title : '导入商品编码文件'
		});
		$('#uploadWindow').window('open');
		$('#uploadWindow').window('refresh', '/repertory/store/importPronos.jhtml');
	},
	
	
	//////////////////////////////////////////导入商品组成/////////////////////////////////////////////////
	// 导入商品组成
	importMakeUp : function(){
		$.messager.confirm('温馨提示', '确定要重新导入商品的组成吗？', function(r) {
			if (r) {
				$.messager.progress({
					title:'温馨提示',
					msg:"正在导入商品组成中..."
				});
				$.post('/product/product/importMakeNoLoop.jhtml', {}, function(result) {
					$.messager.progress('close');
					if(result!=null){
						if(result.success){
							$.messager.show({
								title:'温馨提示',
								msg:'导入商品组成成功',
								timeout:2000
							});
						}else{
							$.messager.show({
								title:'温馨提示',
								msg:'导入商品组成出错，请联系管理员',
								timeout:2000
							});
						}
					}
				});
			}
		});
	},
	
	
	
	//////////////////////////////////////////公共资源部分/////////////////////////////////////////////////
	/**
	 * 图片文件选择
	 */
	fileChange : function() {
		// alert(document.all("TbxName").value);
		// 获取图片的url路径
		var fileValue = $("#file").val();
		var fileType = fileValue.substring(fileValue.lastIndexOf("."),
				fileValue.length);
		// 验证jpg根式的图片
		if (fileType == ".jpg" || fileType == ".gif" || fileType == ".png") {
		} else {
			$("#img").attr({
				src : "",
				style : "display:none"
			});
			;
			alert("文件仅支持jpg,gif,png格式!");
			return false;
		}
		// 文件对象
		var fileObj = document.getElementById("file");
		// img对象
		var imgObj = document.getElementById("img");
		// 火狐浏览器
		if (fileObj.files && fileObj.files[0]) {
			imgObj.style.display = "block";
			imgObj.style.width = "214px";
			imgObj.style.height = "100px";
			// 火狐7以下版本
			// imgObj.src = fileObj.files[0].getAsDataURL();
			// 火狐7以及以上版本
			imgObj.src = window.URL.createObjectURL(fileObj.files[0]);
		} else {
			//IE浏览器
			fileObj.select();
			var imgSrc = document.selection.createRange().text;
			//获取图片的div
			var localImgObj = document.getElementById("localImg");
			localImgObj.style.width = "214px";
			localImgObj.style.height = "100px";
			//图片异常的扑捉,防止用户修改后缀来伪造图片
			try {
				localImgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImgObj.filters
						.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			} catch (e) {
				alert("你上传的图片格式不正确,请重新选择");
				return false;
			}
			imgObj.style.display = "none";
			document.selection.empty();
		}
		return true;
	},
	
	/**
	 * 单击编辑行
	 */
	onClickRow : function(index){
		if (editIndex != index) {
			if (ProductJS.endEditing()) {
				mkdatagrid.datagrid('selectRow', index).datagrid('beginEdit', index);
				ProductJS.setEditing(index); 
				editIndex=index;
			} else {
				mkdatagrid.datagrid('selectRow', editIndex);
			}
		}else{
			if (ProductJS.endEditing()) {
				mkdatagrid.datagrid('selectRow', index).datagrid('beginEdit', index);
				ProductJS.setEditing(index); 
				editIndex=index;
			} else {
				mkdatagrid.datagrid('selectRow', editIndex);
			}
		}
		editIndex=index;
		
	},

	/**
	 * 结束编辑
	 */
	endEditing : function() {
		if (editIndex == undefined) {
			return true;
		}else{
			//判断是否为空,为空补默认值
			mkdatagrid.datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		}
	},
	

	//编辑格数据
	setEditing : function(rowIndex){   
	    var editors = mkdatagrid.datagrid('getEditors', rowIndex);
	    var nameEditor = editors[0];
	    var row = mkdatagrid.datagrid('getSelected');
	    var productNo = "";
	    if(row.proName!=null){
	    	// 获取到商品名称的值
	    	$(editors[0].target).val(row.proName); 
	    	// 获取到商品编码的值
	    	productNo = row.proNo;
	    }
	    // 根据商品名称来动态获取数据
	    nameEditor.target.bind('keydown', function(){   
	        if(event.keyCode==13){
	        	$.messager.progress({
			        title:'查询商品中...',
			        msg:'正在努力挖掘资源中,请稍候...',
			    });
	        	var param={'productName':$(editors[0].target).val(),'productNo':productNo};
	    		$.post('/product/productMakeUp/searchProNameOrNo.jhtml',param,function(result){
	    			if(result!=null){
	    				$.messager.progress('close');
	    				
	    				if(result.noValue){
	    					// 当返回空数据时
	    					$.messager.alert("温馨提示","没有搜索到相匹配的商品信息","info");
	    					
	    				}else if(result.singleUpdateProduct!=null){
	    					
	    					// 当商品名称不为空，销售小计不为空并且只返回一条数据时
	       				 var rows=mkdatagrid.datagrid("getRows");
	      		        	 var isHas=true;
	      		        	 var num = 0; // 判断适量，当同一商品大于等于1的时候，则返回false
	      		        	 $.each(rows,function(n,value){
	      		        		if(value.id==null){
	   		        			 
		   		        		 }else{
		      		        		 if(result.singleUpdateProduct.id==value.id){
		      		        			 num+=1;
		      		        			 if(num==1){
		      			        			 $.messager.alert("温馨提示","该商品已存在,请不要重复添加","info");
		      			        			 //定位到具体一行
		      			        			 onClickRows(n);
		      			        			 isHas=false;
		      			        			 return false;
		      		        			 }
		      		        		 }
		   		        		 }
	      		        	 });
	      		        	 if(isHas){
	      		        		mkdatagrid.datagrid('updateRow',{
	      		        			 index :rowIndex,
	      			        		 row:result.singleUpdateProduct
	      			        	 });
	      		        		$.messager.show({
	      							title : '温馨提示',
	      							msg : "更新成功!"
	      						});
	      		        	 }
	      		        	 
	    				}else if(result.moreUpdateProduct!=null){
	    					
	    					// 当商品名称不为空，销售小计不为空并且返回多条数据时
	    					var productName=$(editors[0].target).val();
	    					$("#ddgrid").datagrid({
								url:'/product/productMakeUp/searchProNameAndNo.jhtml',
								toolbar:'#search-probtns',
								nowrap:false,
								height:500,
								idField: 'id',
								pagination:true,
//								pageList:[15,30,50],
								rownumbers:true,
								fitColumns:true,
								singleSelect:true,
								columns: [[
						                   { field: 'proName', title: '商品名称', width: 400, sortable: true },
						                   { field: 'proNo', title: '商品编码', width: 200, sortable: true },
						                   { field: 'specifications', title: '规格', width: 120, sortable: true },
						                   { field: 'purchasePrice', title: '采购价', width: 140, sortable: true },
						                   { field: 'proUnit', title: '单位', width: 100, sortable: true },
						                   { field: 'inventoryCount', title: '库存量', width: 180, sortable: true },
						                   { field: 'status', title: '状态', width: 100, sortable: true },
						                   { field: 'supplierName', title: '供应商', width: 180, sortable: true,formatter : 
						                	   function(value, row, index) {
						                	   	if(row.supplier!=null){
						                	   		return row.supplier.supplierName;
						                	   	}
						                	   }
						                   },
						               ]],
						         onDblClickRow:function(childIndex, rowData){
						        	 //双击选择商品保存时
						        	 var param = {'productId':rowData.id};
						        	 $.post('/product/productMakeUp/insertOrderPro.jhtml',param,function(result){
						        			// 当商品名称不为空，销售小计为空并且只返回一条数据时
					    				 var rows=mkdatagrid.datagrid("getRows");
					   		        	 var isHas=true;
					   		        	 var num = 0; // 判断适量，当同一商品大于等于1的时候，则返回false
					   		        	 $.each(rows,function(n,value){
					   		        		if(value.id==null){
							        			 
							        		 }else{
						   		        		 if(result.id==value.id){
						   		        			 num+=1;
						   		        			 if(num==1){
						   			        			 $.messager.alert("温馨提示","该商品已存在,请不要重复添加","info");
						   			        			 //定位到具体一行
						   			        			 onClickRows(n);
						   			        			 isHas=false;
						   			        			 return false;
						   		        			 }
						   		        		 }
							        		 }
					   		        	 });
					   		        	 if(isHas){
					   		        		mkdatagrid.datagrid('updateRow',{
						    					index: rowIndex,
						    					row:result
						    				});
					   		        		$.messager.show({
					   							title : '温馨提示',
					   							msg : "更新成功!"
					   						});
					   		        	 }
					        			 
						        	 },'json');
					        		 $("#dd").dialog('close');
						         }
							});
	    					$('#ddgrid').datagrid('load', {
	    						'productName' : productName
	    					});
	    					$('#dd').dialog('open').dialog('setTitle', '商品管理   搜索关键词：'+$(editors[0].target).val());
	    					
	    				}else if(result.singleAddProduct!=null){
	    					
	    					// 当商品名称不为空，销售小计为空并且只返回一条数据时
	    				 var rows=mkdatagrid.datagrid("getRows");
	   		        	 var isHas=true;
	   		        	 var num = 0; // 判断适量，当同一商品大于等于1的时候，则返回false
	   		        	 $.each(rows,function(n,value){
	   		        		if(value.id==null){
			        			 
			        		 }else{
		   		        		 if(result.singleAddProduct.id==value.id){
		   		        			 num+=1;
		   		        			 if(num==1){
		   			        			 $.messager.alert("温馨提示","该商品已存在,请不要重复添加","info");
		   			        			 //定位到具体一行
		   			        			 onClickRows(n);
		   			        			 isHas=false;
		   			        			 return false;
		   		        			 }
		   		        		 }
			        		 }
	   		        	 });
	   		        	 if(isHas){
	   		        		mkdatagrid.datagrid('insertRow',{
	   		        			 index :rows.length-1,
	   			        		 row:result.singleAddProduct
	   			        	 });
	   		        		$(editors[0].target).val("");
	   		        		$.messager.show({
	   							title : '温馨提示',
	   							msg : "添加成功!"
	   						});
	   		        	 }
	   		        	 
	    				}else if(result.moreAddProduct!=null){
	    					
	    					// 当商品名称不为空，销售小计为空并且返回多条数据时
	    					var productName=$(editors[0].target).val();
	    					$("#ddgrid").datagrid({
								url:'/product/productMakeUp/searchProNameAndNo.jhtml',
								toolbar:'#search-probtns',
								nowrap:false,
								height:500,
								idField: 'id',
								pagination:true,
//								pageList:[15,30,50],
								rownumbers:true,
								fitColumns:true,
								singleSelect:true,
								columns: [[
						                   { field: 'proName', title: '商品名称', width: 400, sortable: true },
						                   { field: 'proNo', title: '商品编码', width: 200, sortable: true },
						                   { field: 'specifications', title: '规格', width: 120, sortable: true },
						                   { field: 'purchasePrice', title: '采购价', width: 140, sortable: true },
						                   { field: 'proUnit', title: '单位', width: 100, sortable: true },
						                   { field: 'inventoryCount', title: '库存量', width: 180, sortable: true },
						                   { field: 'status', title: '状态', width: 100, sortable: true },
						                   { field: 'supplierName', title: '供应商', width: 180, sortable: true,formatter : 
						                	   function(value, row, index) {
						                	   	if(row.supplier!=null){
						                	   		return row.supplier.supplierName;
						                	   	}
						                	   }
						                   },
						               ]],
						         onDblClickRow:function(childIndex, rowData){
						        	 //双击选择商品保存时
						        	 var param = {'productId':rowData.id};
						        	 $.post('/product/productMakeUp/insertOrderPro.jhtml',param,function(result){
						        			// 当商品名称不为空，销售小计为空并且只返回一条数据时
					    				 var rows=mkdatagrid.datagrid("getRows");
					   		        	 var isHas=true;
					   		        	 var num = 0; // 判断适量，当同一商品大于等于1的时候，则返回false
					   		        	 $.each(rows,function(n,value){
					   		        		if(value.id==null){
							        			 
							        		 }else{
						   		        		 if(result.id==value.id){
						   		        			 num+=1;
						   		        			 if(num==1){
						   			        			 $.messager.alert("温馨提示","该商品已存在,请不要重复添加","info");
						   			        			 //定位到具体一行
						   			        			 onClickRows(n);
						   			        			 isHas=false;
						   			        			 return false;
						   		        			 }
						   		        		 }
							        		 }
					   		        	 });
					   		        	 if(isHas){
					   		        		mkdatagrid.datagrid('insertRow',{
				    							index :rows.length-1,
				    							row:result
				    						});
					   		        		$(editors[0].target).val("");
					   		        		$.messager.show({
					   							title : '温馨提示',
					   							msg : "添加成功!"
					   						});
					   		        	 }
						        	 },'json');
					        		 $("#dd").dialog('close');
						         }
							});
	    					$('#ddgrid').datagrid('load', {
	    						'productName' : productName
	    					});
	    					$('#dd').dialog('open').dialog('setTitle', '商品管理   搜索关键词：'+$(editors[0].target).val());
	    				}// end if
	    			}
	    			
	    		}, 'json');
	        }
	    });
	}, 
	
	

	/**
	 * 添加物料
	 */
	newRaw : function(treeId,makeupId) {
		 var p=sy.dialog({
			title:'物料信息',
			href : '/product/product/productMakeUpInfo.jhtml',
			width : 1000,
			height : 500,
			top : 20,
			modal:true,
			onLoad:function(){//加载物料组成的网格
				var f = p.find('#productMakeUpInfo');
				var pmuidg=f.find("#dg");
				var pmuitt=f.find("#tt");
				
				productCatTree=pmuitt.tree({
					url : '/product/productCat/treeView.jhtml',
					method : 'post',
					animate : true,
					dnd : true,
					onClick : function(node) {
						mkInfodatagrid.datagrid('load', {
							'catId' : node.id,
							'product.productCat.id' : node.id
						});
					}
				});
				mkInfodatagrid=pmuidg.datagrid({
					title:'商品管理（双击选择数据）',
					height:428,
					url:'/product/product/searchTree.jhtml?treeId='+treeId+'&makeupId='+makeupId,
					idField: 'id',
					pagination:true,
					rownumbers:true,
					fitColumns:true,
					singleSelect:true,
					columns: [[
			                   { field: 'proName', title: '商品名称', width: 400, sortable: true },
			                   { field: 'proNo', title: '商品编码', width: 200, sortable: true },
			                   { field: 'specifications', title: '规格', width: 120, sortable: true },
			                   { field: 'purchasePrice', title: '采购价', width: 140, sortable: true },
			                   { field: 'proUnit', title: '单位', width: 100, sortable: true },
			                   { field: 'inventoryCount', title: '库存量', width: 180, sortable: true },
			                   { field: 'status', title: '状态', width: 100, sortable: true },
			                   { field: 'supplierName', title: '供应商', width: 180, sortable: true,formatter : 
			                	   function(value, row, index) {
			                	   	if(row.supplier!=null){
			                	   		return row.supplier.supplierName;
			                	   	}
			                	   }
			                   },
			               ]],
			         onDblClickRow:function(rowIndex, rowData){//双击时选择
			        	 var rows=mkdatagrid.datagrid("getRows");
			        	 var isHas=true;
			        	 $.each(rows,function(n,value){
			        		 if(rowData.id==value.id){
			        			 $.messager.alert("温馨提示","该商品已存在,请修改其数量","info");
			        			 //定位到具体一行
			        			 onClickRow(n);
			        			 isHas=false;
			        			 return false;
			        		 }
			        	 });
			        	 if(isHas){
			        		 mkdatagrid.datagrid('insertRow',{
			        			 index : rows.length-1,
				        		 row:rowData,
				        		 'productMakeUp.productCount':1
				        	 });
			        	 }
			        	p.dialog('close');
			         }
				});
			}
		});
	},



	/**
	 * 保存商品组成
	 */
	saveRaw : function(treeId,makeupId) {
		var rows=mkdatagrid.datagrid("getRows");
		var flg=true;
		
		//完成编辑功能再保存
		$.each(rows, function(n, value) {
			if(value.id==null){
				mkdatagrid.datagrid('deleteRow', rows.length-1);
			}else{
				mkdatagrid.datagrid('endEdit',n);
				if(value.productCount==undefined||value.productCount==null||value.productCount==""||value.productCount<=0){
					ProductJS.onClickRow(n);
					flg=ProductJS.showAlert("组成数量不能为空或者小于等于0!");
					return false;
				}
			}
		});
		if(rows==null||rows==undefined||rows==""||rows.length==0){
			flg=ProductJS.showAlert("没有要保存的商品信息!");
			return false;
		}
		if(flg==true){
			var hiddjson=JSON.stringify(rows);
			$("#hiddenjson").val(hiddjson);
			$("#fmMakeUp").form('submit', {
				url : '/product/productMakeUp/add.jhtml?treeId='+treeId,
				onSubmit: function(){
					return $(this).form('validate');
				},
				success : function(data) {
					var result =JSON.parse(data);
					if(result.errorMsg){
						$.messager.show({
							title:"温馨提示",
							msg:result.errorMsg
						});
						mkdialog.dialog('refresh');
					}else{
						$.messager.show({
							title:"温馨提示",
							msg:'提交成功'
						});
						mkdialog.dialog('refresh');
					}
				}
			});
		}
	},

	/**
	 * 显示提示信息
	 */
	showAlert : function(msg){
		$.messager.alert("温馨提示", msg);
		return false;
	},
	

	/**
	 * 移除不删除主商品组成的物料
	 */
	destroyRaw : function() {
		var row = mkdatagrid.datagrid('getSelected');
		var rowIndex = mkdatagrid.datagrid('getRowIndex', row);
		mkdatagrid.datagrid('deleteRow',rowIndex);
	},

	/**
	 * 移除删除主商品组成的物料
	 */
	deleteRaw : function(){
		var row = mkdatagrid.datagrid('getSelected');
		var main_product_id=$("#main_product_id").val();
		if(row.id==null){
			$.messager.alert("温馨提示","该信息暂未保存，无法执行删除操作！","info");
			return false;
		}
		if (row) {
			var main_row_id=row.id;
			$.messager.confirm('温馨提示', '确定要删除该商品信息吗？', function(r) {
				if (r) {
					$.post('/product/productMakeUp/deleteById.jhtml', {
						"makeupName" : $("#node_name").val(),
						"main_product_id" : main_product_id,
						"id":main_row_id
					}, function(result) {
						if (result.success) {
							//mkdatagrid.datagrid('reload');
							$.messager.confirm("温馨提示","删除成功，是否重新加载页面",function(r){
								if(r){
									mkdialog.dialog('refresh');// 整个弹出框页面数据重新加载
								}else{
									mkdatagrid.datagrid('reload'); // 重新加载datagrid数据
								}
								var rows = mkdatagrid.datagrid('getRows');
								if(rows.length==0){
									mkdatagrid.datagrid('appendRow',{rows:[{}]});
								}
							});
						} else {
							$.messager.alert("温馨提示",result.errorMsg,"info",function(r){
								mkdialog.dialog('refresh');
							});
							/*$.messager.show({ 
								title : '错误',
								msg : result.errorMsg
							});*/
						}
					}, 'json');
				}
			});
		}else{
			$.messager.alert("温馨提示","<font style='padding-left:75px;'>请选择要删除的数据行</font>");
		}
	},

	/**
	 * 撤销更改
	 */
	rejectProduct : function(){
		var data = mkdatagrid.datagrid('getSelected');
		/*if(data.id!=null){
			$.messager.alert("温馨提示","该商品已保存，如需撤销请执行删除操作");
		}else{*/
			var index = mkdatagrid.datagrid('getRowIndex',data);
			mkdatagrid.datagrid('deleteRow', index);
	},

	/**
	 * 图片编辑页面
	 */
	toPicManage : function() {
		var row = $('#dg').treegrid('getSelected');
		if(row!=null){
			location.href = "/product/product/toPicManage.jhtml?product.id=" + row.id;
		}else{
			$.messager.alert("温馨提示","请先选择要上传图片的商品信息","warning");
		}
	}


});





/**
 * 商品编辑
 */
var ProductEdit = $.extend({
	/**
	 * 初始化商品编辑界面信息
	 */
	initData : function() {
		var f = $('#fmEdit');
		var productCat = f.find('input[name=productCatId]');
		productCat.combotree({
			lines : true,
			url : '/product/productCat/productWithTreeView.jhtml'
		});
		var jsonstr=$("json"); /*<s:property value="json" escape="false" />*/;
		var row= typeof jsonstr =="string"?eval('('+jsonstr+')'):jsonstr;
		f.form('load', row);
		if(row.defaultImg){
			//设置图片显示
			f.find("#defaultImg").attr("src",row.defaultImg);
			f.find("#defaultImg").css("display","").css("width","350px").css("height","200px");
		}else{
			//没图片显示暂无图片
			f.find("#defaultImg").attr("src","/Upload/product/nopic.tmp");
			f.find("#defaultImg").css("display","").css("width","350px").css("height","200px");
		}
		var describeId = $("#describeId").val();
		var editor=new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:960 });
		editor.render('editEditor');
		editor.addListener("ready", function () {
			editor.setContent(describeId);
		});
	},

	/**
	 * 保存商品编辑
	 */
	saveEdit : function(){
		var t = $('#cattreeEdit').combotree('tree');	// 获取树对象
		var catlist = t.tree('getSelected');	// 获取选择的节点
		if(catlist==null||(catlist!=null&&(catlist.text=='商品总分类'||catlist.text==''))){
			$.messager.show({
				title : '温馨提示',
				msg : '请选择商品类别！'
			});
			return false;
		}
		$("#edit_productCatId").val(catlist.id);
		var f = $('#fmEdit');
		f.form('submit', {
			url : '/product/product/update.jhtml?catId='+catlist.id,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(results) {
				var result = eval('(' + results + ')');
				if (result.errorMsg) {
					if (result.isProNoExist) {
						$.messager.alert("温馨提示", result.errorMsg, "info",function(){
							//商品编码框获得焦点
							$("#proNoEdit").focus();
							//文本外框显示为红色
							$("#proNoEdit").css("border","2px solid red");
						});
					} else {
						$.messager.alert("温馨提示", result.errorMsg);
					}
				} else {
					$.messager.alert("温馨提示","修改成功","info",function(){
						location.href="/product/product/manage.jhtml";
					});
				}
			}
		});
	},
	
	/*********添加物料时页面的查询***************/
	doSearch3 : function() {
		mkInfodatagrid.datagrid('load', {
			'product.proName' : $('#proName3').val(),
			'product.proNo' : $('#proNo3').val(),
			'product.supplier.supplierName' : $('#supplierName3').val()
		});
	}

		
});