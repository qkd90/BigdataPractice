var ProductCat=$.fn.extend({
	initTree:function(){
		/* tree初始化  */
		$('#tt').tree({
			url: '/product/productCat/treeView.jhtml',
			method: 'get',
			dnd : false,
			lines:true,
			animate : true,
			onClick : function(node) {
				if (node.id!=null&&node.id!="") {
					$.ajax({
						url : "/product/productCat/get.jhtml",
						data : {"productCat.id":node.id},
						contentType: "application/x-www-form-urlencoded;charset=utf-8",
						type : "post",
						cache : false,
						dataType : "json",
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
						},
						success : function(result) {
							$('#ff').form('reset');
							var parentProductCatName="类别控制树";
							if(result[0].parentProductCatId!=null&&result[0].parentProductCatId!=""){
								parentProductCatName=result[0].parentProductCatName;
							}
							$('#ff').form('load',{
								'productCat.id':result[0].id,
								'productCat.catName':result[0].catName,
								'productCat.mallKeyWords':result[0].mallKeyWords,
								'productCat.validMonth':result[0].validMonth,
								'productCat.catSeq':result[0].catSeq,
								'productCat.mallKey':result[0].mallKey,
								'productCat.mallKeyDescription':result[0].mallKeyDescription,
								'productCat.parentProductCat.id':result[0].parentProductCatId,
								'parentName':parentProductCatName,
								'productCat.isShow':result[0].isShow,
							});
							
							$('#submitLink').attr("onclick","updateForm()");
							$('#p').panel({
								title : '修改',
							});
							$('#p').dialog('open');
						}
					});
				}
			},
			onContextMenu : function(e, node) {
				e.preventDefault();
				$(this).tree('select', node.target);
				$('#mm').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		/* penel初始化  */
		$('#p').panel({
			height : 500,
			title : '修改',
			closed : true
		});
	}, 
	
	// 已经拥有的所有树
	/*initHasPropTree:function(){
		var tId = $("#tId").val();
		alert(tId);
		 tree初始化  
		$('#hasProperty').tree({
			url: '/product/productCat/treeView.jhtml?id='+tId,
			method: 'get',
			dnd : false,
			lines:true,
			animate : true,
			onClick : function(node) {
				if (node.id!=null&&node.id!="") {
					$.ajax({
						url : "/product/productCat/get.jhtml",
						data : {"productCat.id":node.id},
						contentType: "application/x-www-form-urlencoded;charset=utf-8",
						type : "post",
						cache : false,
						dataType : "json",
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
						},
						success : function(result) {
							$('#ff').form('reset');
							var parentProductCatName="类别控制树";
							if(result[0].parentProductCatId!=null&&result[0].parentProductCatId!=""){
								parentProductCatName=result[0].parentProductCatName;
							}
							$('#ff').form('load',{
								'productCat.id':result[0].id,
								'productCat.catName':result[0].catName,
								'productCat.mallKeyWords':result[0].mallKeyWords,
								'productCat.validMonth':result[0].validMonth,
								'productCat.catSeq':result[0].catSeq,
								'productCat.mallKey':result[0].mallKey,
								'productCat.mallKeyDescription':result[0].mallKeyDescription,
								'productCat.parentProductCat.id':result[0].parentProductCatId,
								'parentName':parentProductCatName,
								'productCat.isShow':result[0].isShow,
							});
							
							$('#submitLink').attr("onclick","updateForm()");
							$('#p').panel({
								title : '修改',
							});
							$('#p').dialog('open');
						}
					});
				}
			},
			onContextMenu : function(e, node) {
				e.preventDefault();
				$(this).tree('select', node.target);
				$('#mm').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	}, */
	
});



$(function(){
	ProductCat.initTree();
	
});