//////////////通用的商品查询/////////////////
// 使用方法1.selectGood(function(rows){});调用此方法会返回rows对象
//callback(rows)回调函数
function selectGood(callback,type){
		if(type=='缺货'){
			var dlg = $('#dlg-selectStockOutGood');//获取元素<div>
			if(!dlg.length){
				dlg = $('<div id="dlg-selectStockOutGood"></div>').appendTo('body');
				dlg.dialog({
					title:'缺货商品',
					width:900,
					height:500,
					closed:true,
					modal:true,
					href:'/main/index/selectStockOutGood.jhtml',
					onLoad:function(){
						bindStockOutEvents();
					}
				});
			}
			//绑定选中事件
			function bindStockOutEvents(){
				//绑定选中
				$("#queryStockOutGood-ok").unbind("click").bind('click',function(e){
					dlg.dialog('close');
					var rows = $('#queryStockOutGood-dt-goods').datagrid('getSelections');
					callback(rows);
					$('#queryStockOutGood-dt-goods').datagrid('clearSelections');//清除所有选择的行
				});
				//绑定查询
				$("#queryStockOutGood-q").unbind("keydown").bind('keydown',function(e){
					if (e.keyCode == 13){
						queryStockOutGood_query();//查询
					}
				});
			}
			bindStockOutEvents();
			dlg.dialog('open');
		}
		else if(type=='即将缺货'){
			var dlg = $('#dlg-selectWillStockOutGood');//获取元素<div>
			if(!dlg.length){
				dlg = $('<div id="dlg-selectWillStockOutGood"></div>').appendTo('body');
				dlg.dialog({
					title:'即将缺货商品',
					width:900,
					height:500,
					closed:true,
					modal:true,
					href:'/main/index/selectWillStockOutGood.jhtml?supplierId='+supplierId,
					onLoad:function(){
						bindWillStockOutEvents();
					}
				});
			}
			//绑定选中事件
			function bindWillStockOutEvents(){
				//绑定选中
				$("#queryWillStockOutGood-ok").unbind("click").bind('click',function(e){
					dlg.dialog('close');
					var rows = $('#queryWillStockOutGood-dt-goods').datagrid('getSelections');
					callback(rows);
					$('#queryWillStockOutGood-dt-goods').datagrid('clearSelections');//清除所有选择的行
				});
				//绑定查询
				$("#queryWillStockOutGood-q").unbind("keydown").bind('keydown',function(e){
					if (e.keyCode == 13){
						queryWillStockOutGood_query();//查询
					}
				});
			}
			bindWillStockOutEvents();
			dlg.dialog('open');
		}
		else{
			var dlg = $('#dlg-selectGood');//获取元素<div>
			if(!dlg.length){
				dlg = $('<div id="dlg-selectGood"></div>').appendTo('body');
				dlg.dialog({
					title:'选择商品',
					width:900,
					height:500,
					closed:true,
					modal:true,
					href:'/main/index/selectGood.jhtml',
					onLoad:function(){
						bindEvents();
					}
				});
			}
			//绑定选中事件
			function bindEvents(){
				//绑定选中
				$("#queryGood-ok").unbind("click").bind('click',function(e){
					dlg.dialog('close');
					var rows = $('#queryGood-dt-goods').datagrid('getSelections');
					callback(rows);
					$('#queryGood-dt-goods').datagrid('clearSelections');//清除所有选择的行
				});
				//绑定查询
				$("#queryGood-q").unbind("keydown").bind('keydown',function(e){
					if (e.keyCode == 13){
						queryGood_query();//查询
					}
				});
			}
			bindEvents();
			dlg.dialog('open');
		}
}

//会员选择
function selectMember(callback){
	var dlg = $('#dlg-selectMember');//新增元素<div>
	if(!dlg.length){
		dlg = $('<div id="dlg-selectMember"></div>').appendTo('body');
		dlg.dialog({
			title:'选择会员',
			width:900,
			height:500,
			closed:true,
			modal:true,
			href:'/main/index/selectMember.jhtml',
			onLoad:function(){
				bindEvents();
			}
		});
	}
	//绑定选中事件
	function bindEvents(){
		//绑定选中
		$("#queryMember-ok").unbind("click").bind('click',function(e){
			dlg.dialog('close');
			var rows = $('#queryMember-dt-members').datagrid('getSelections');
			callback(rows);
			$('#queryMember-dt-members').datagrid('clearSelections');//清除所有选择的行
		});
		//绑定查询
		$("#queryMember-q").unbind("keydown").bind('keydown',function(e){
			if (e.keyCode == 13){
				queryMember();//查询
			}
		});
	}
	bindEvents();
	dlg.dialog('open');
}
//查询供应商
function selectSupplier(callback){
	var dlg = $('#dlg-selectSupplier');//获取元素<div>
	if(!dlg.length){
		dlg = $('<div id="dlg-selectSupplier"></div>').appendTo('body');
		dlg.dialog({
			title:'选择供应商',
			width:800,
			height:500,
			closed:true,
			modal:true,
			href:'/main/index/selectSupplier.jhtml',
			onLoad:function(){
				bindEvents();
			}
		});
	}
	//绑定选中事件
	function bindEvents(){
		//绑定选中
		$("#querySupplier-ok").unbind("click").bind('click',function(e){
			dlg.dialog('close');
			var rows = $('#querySupplier-dt-suppliers').datagrid('getSelections');
			callback(rows);
			$('#querySupplier-dt-suppliers').datagrid('clearSelections');//清除所有选择的行
		});
		//绑定查询
		$("#querySupplier-q").unbind("keydown").bind('keydown',function(e){
			if (e.keyCode == 13){
				querySupplier();//查询
			}
		});
	}
	bindEvents();
	dlg.dialog('open');
}
//快速查询商品
//callback:回调函数
//q:是参数
function fastSelectGood(callback,q){
	//查询是否只有唯一一个
	$.messager.progress({
		title:'加载中...',
		text:'努力搜索中...'
	});
	$.post('/product/product/getItems.jhtml',{q:q},function(data){
		$.messager.progress('close');
		var result=data.rows;
		if(result.length==1){
			callback(result);
		}else{
			var dlg = $('#dlg-fastSelectGood');//获取元素<div>
			if (!dlg.length){
				dlg = $('<div id="dlg-fastSelectGood"></div>').appendTo('body');
				dlg.dialog({
					title:'选择商品',
					width:900,
					height:500,
					closed:true,
					modal:true,
					href:'/main/index/fastSelectGood.jhtml',
					onLoad:function(){
						loadProductItem();
					}
				});
			}
			function loadProductItem(){
				$('#fast_queryGood-dt-goods').datagrid({
					queryParams: {
						q: q,//查询的关键字,
					},
					onLoadSuccess:function(data){
						//绑定选中
						$("#fast_queryGood-ok").unbind("click").bind('click',function(e){
							dlg.dialog('close');
							var rows = $('#fast_queryGood-dt-goods').datagrid('getSelections');
							callback(rows);
							$('#fast_queryGood-dt-goods').datagrid('clearSelections');//清除所有选择的行
						});
						//绑定查询
						$("#fast_queryGood-q").unbind("keydown").bind('keydown',function(e){
							if (e.keyCode == 13){
								fast_queryGood_query();//查询
							}
						});
					}
				});
			}
			loadProductItem();
			dlg.dialog('open');
		}
	});
	
}

//快速查询会员
function fastSelectMember(callback,q){
	//查询是否只有唯一一个
	$.messager.progress({
		title:'加载中...',
		text:'努力搜索中...'
	});
	$.post('/member/member/getItems.jhtml',{q:q},function(data){
		$.messager.progress('close');
		var result=data.rows;
		if(result.length==1){
			callback(result);
		}else{
			var dlg = $('#dlg-fastSelectMember');//获取元素<div>
			if (!dlg.length){
				dlg = $('<div id="dlg-fastSelectMember"></div>').appendTo('body');
				dlg.dialog({
					title:'选择会员',
					width:900,
					height:500,
					closed:true,
					modal:true,
					href:'/main/index/fastSelectMember.jhtml',
					onLoad:function(){
						loadMemberItem();
					}
				});
			}
			function loadMemberItem(){
				$('#fast_queryMember-dt-members').datagrid({
					queryParams: {
						q: q,//查询的关键字,
					},
					onLoadSuccess:function(data){
						//绑定选中
						$("#fast_queryMember-ok").unbind("click").bind('click',function(e){
							dlg.dialog('close');
							var rows = $('#fast_queryMember-dt-members').datagrid('getSelections');
							callback(rows);
							$('#fast_queryMember-dt-members').datagrid('clearSelections');//清除所有选择的行
						});
						//绑定查询
						$("#fast_queryMember-q").unbind("keydown").bind('keydown',function(e){
							if (e.keyCode == 13){
								fast_queryMember();//查询
							}
						});
					}
				});
			}
			loadMemberItem();
			dlg.dialog('open');
		}
	});
}
