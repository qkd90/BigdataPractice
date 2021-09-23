/*TicketEditOfferList*/
var TicketEditOfferList = {
		
		initTicketEditOfferList:function(){
			TicketEditOfferList.initOfferData();
			TicketEditOfferList.initToopAgree();
			$('#dd').dialog('close');  
		},
		
		addTicketPrice:function(){

			//var ticketPriceId = result.ticketPriceId;
			var t = Math.random(); 	// 保证页面刷新
			url = "/ticket/ticket/sailboatTicketEditOffer.jhtml?ticketId="+$("#ticketId").val();
			var ifr = $("#dd").children()[0];
			$(ifr).attr("src", url);
			$('#dd').dialog({
				title: '新增船票价格',
				closed: true,
				cache: false,
				modal: true
			});
			$('#dd').dialog('open');

		},
		
		
		initConf:function(){
			var inputconf = $("input[name='orderConfirm']");
			var hidden_conf = $("#hidden_conf").val();
			$.each(inputconf,function(i,perValue){
				if(perValue.value == hidden_conf){
					perValue.checked = 'checked';
				}
			});
			
			
			var payway = $("#hidden_payway").val();
            if (payway && payway != "") {
                $("#sel_payway").combobox('setValue',payway);
            }
		},
		
		// 下一步
		nextGuide:function() {
			var data = {
					"ticketId":$("#ticketId").val(),
					"ticket.payway":$("#sel_payway").combobox('getValue')
					//"ticket.orderConfirm":$("input[name='orderConfirm']:checked").val()
			};
			
			
				
				var payway = $("#sel_payway").combobox('getValue');
				//var orderConfirm = $("input[name='orderConfirm']:checked").val();
				
				if(payway.length<=0){
					alert("请选择付款方式！");
				} else {
					
					$.post("/ticket/ticket/lastSave.jhtml", 
							data ,
							function(data){
								
//								var result = eval('(' + data + ')');
								if(data.success){
									TicketUtil.buildTicket(data.tId);
									var param = data.tId;
									parent.window.showGuide(3, true,param);
								}else{
									show_msg("保存船票失败");
								}
						

							},'json'
					);
					
				}
				
				
				
			
			
			
		},
		
		addOfferType:function(){
			
			var param = "?ticketId="+$("#ticketId").val();
//			alert(param);
			parent.window.showGuide(2, true,param);
		},
		
		refreshOfferType:function(){
			$("#qryResult").datagrid("load", {});
		},
		
		editTicketPrice:function(id,ticketId){
//			var param = "?tpId="+id;
			
			var t = Math.random(); 	// 保证页面刷新
			var url = "";
			if(ticketId){
				url = "/ticket/ticket/sailboatTicketEditOffer.jhtml?ticketPriceId="+id+"&ticketId="+ticketId;
			}else{
				url = "/ticket/ticket/sailboatTicketEditOffer.jhtml?ticketPriceId="+id;
			}
			var ifr = $("#dd").children()[0];
			$(ifr).attr("src", url);
			$('#dd').dialog({    
			    title: '编辑船票价格',
			    closed: true,    
			    cache: false,    
			    modal: true   
			});  
			$('#dd').dialog('open'); 
			
//			alert(param);
//			parent.window.showGuide(2,true,param);
		},
		
		delTicketPrice:function(id){
			var data ={
					"priceId":id
			};
			$.post("/ticket/ticketPrice/delPrice.jhtml", 
					data ,
					function(data){
						
//						var result = eval('(' + data + ')');
						if(data.success){
							show_msg("删除成功！");
							$("#qryResult").datagrid("load", {});
//							var param = "?ticketId="+data.tId;
//							parent.window.showGuide(4, true,param);
						}else{
//							show_msg("保存船票失败");
						}
				
//						$("#sce_div_area").hide();
//						$("#sce_span_area").html(data.fullPath);
				
						
					},'json'
			);
			
		},
		initOfferData:function(){
			
			var url = "";
			var flag = $("#ticket_agentId").val();
			if(flag == "true"){
				url = '/ticket/ticketPrice/getDatePricelist.jhtml?prop=rebate&agent=T';
			}else{
				url = '/ticket/ticketPrice/getDatePricelist.jhtml?prop=rebate&agent=F';
			}
			
			// 构建表格
			$('#qryResult').datagrid({   
				title:"",
				data:[
//					linetypepriceId:27
//					"prop":"discountPrice"
				      ],
				url:url,
				rownumbers:false,
				border:true,
				singleSelect:true,
				striped:true,
				pagination:false,
				columns : [[{
						field : 'name',
						title : '船票类型名称',
						align : 'center',
						width : 220,
						formatter:function(value, rowData, rowIndex){
							var ticketId = $("#ticketId").val();
//							alert("aaa="+ticketId+ "-"+ rowData.ticketId);
							if(ticketId != rowData.ticket.id){
								return value + "<span style='color:green;'>[代理]</span>";
							}else{
								return value;
							}
							
						}
					}, {
						field : 'discountPrice',
						title : '销售价',
						align : 'center',
						width : 80
					}, {
						field : 'price',
						title : '结算价',
						align : 'center',
						width : 80
					}, {
					field : 'maketPrice',
					title : '市场价',
					align : 'center',
					width : 80
				}, {
						field : 'type',
						title : '票型',
						align : 'center',
						width : 95,
						codeType: 'type', 
						formatter: TicketUtil.codeFmt
					}, {
						field : "OPT",
						title : "操作",
						align : 'center',
						width : 160,
						formatter : function(value, rowData, rowIndex) {
							
							var ticketId = $("#ticketId").val();
							if(ticketId != rowData.ticket.id){
								var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketEditOfferList.editTicketPrice("+rowData.id+","+ticketId+")'>详情</a>";
								return btnEdit;
							}else{

								var btnQuantity = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketEditOfferList.onpenQuantitySalesDialog("+rowData.id+")'>设置拱量</a>";
								var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketEditOfferList.editTicketPrice("+rowData.id+")'>编辑</a>";
								var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketEditOfferList.delTicketPrice("+rowData.id+")'>删除</a>";
								var btnImages = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketEditOfferList.checkImages("+rowData.id+")'>查看相册</a>";
								//return btnEdit+"&nbsp;&nbsp;"+btnQuantity+"&nbsp;&nbsp;"+btnDel;
								return btnEdit+"&nbsp;&nbsp;"+btnDel + "&nbsp;&nbsp;" + btnImages;
							}
							
							
							
						}
					}]],
				toolbar : [{
					text : '添加报价类型',
					iconCls : 'icon-add',
					
					handler : function() {
						TicketEditOfferList.addTicketPrice();
					}
				}, "-", {
					text : '刷新',
					iconCls : 'icon-reload',
					handler : function() {
						TicketEditOfferList.refreshOfferType();
					}
				}], 
				onBeforeLoad : function(data) {   // 查询参数 
			        data.ticketId = $("#ticketId").val();
				}, 
				onLoadSuccess : function(data) {
					
				}
			});
		},

	checkImages: function(id) {

		var param = "?tpId="+id;

		var t = Math.random(); 	// 保证页面刷新
		var url = "/ticket/ticketPrice/priceTypeImagesPage.jhtml?ticketPriceId="+id+"&ticketId="+$("#ticketId").val();

		var ifr = $("#dd").children()[0];
		$(ifr).attr("src", url);
		$('#dd').dialog({
			title: '相册',
			closed: true,
			cache: false,
			modal: true,
			height: 420,
			onClose: function() {
				$('#qryResult').datagrid("load");
			}
		});
		$('#dd').dialog('open');

	},

	initToopAgree:function(){
		//查看《订单无需确认服务条款》

		var confirmHtml = ' <strong>订单无需确认服务条款</strong>';
			confirmHtml += '<p style="color:#838484;padding:5px;">内容完善中。</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">1.旅行社选择铁定出游订单无需确认后，要承诺所有带有“铁定出游”标识的旅游类目产品，都为所见所得产<br>&nbsp;&nbsp;品，产品的成人价格和配额准确可用，游客可按期顺利出行！</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">2.“铁定出游”产品详情中不得出现行程可能更改或价格可能变动，或买家需要事先咨询卖家价格或出发日期的<br>&nbsp;&nbsp;相关信息。</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">3.提供“铁定出游”产品的卖家须确保产品价格和出发日期的配额与实际所提供的一致。</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">4.因旅行社原因照成游客付款后无法保障出行或需要行程更改，旅行社必须同意退换游客定金和已付款！</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">5.提供铁定出游产品的旅行社如取消已生效订单，则该旅行社需对下单游客无条件进行全额退款！</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">6.如果旅行社拒绝退款，欣欣将从保障金中扣除相应金额支付给游客代为退款，若赔付押金金额不足，甲方须用<br>&nbsp;&nbsp;另外渠道补足游客，因旅行社退款不当造成的损失，都由旅行社承担！</p>';
			//confirmHtml += '<p style="color:#838484;padding:5px;">7.加入“铁定出游”特色服务的旅行社如出现多次违规被投诉的情形将被取消“即时确认”特色服务权限！</p>';

		$('#look_span').tooltip({
					content: $('<div style="font-color:	#d0d0d0;">'+confirmHtml+'</div>'),
					showEvent: 'click',
					onShow: function(){
						var t = $(this);
						t.tooltip('tip').unbind().bind('mouseenter', function(){
							t.tooltip('show');
						}).bind('mouseleave', function(){
							t.tooltip('hide');
						});
					}
				});
	},
	showMsg:function(result){
		$.messager.show({
			title:'温馨提示',
			showType:'show',
			msg:result,
			timeout:1500,
			 style:{
					right:'',
					bottom:''
				}

		});
	},


	onpenQuantitySalesDialog: function(id) {
		$("#quantitySales_dialog").dialog({
			title: '设置拱量',
			closed: true,
			cache: false,
			modal: true
		});

		var ifr = $("#quantitySales_dialog").children()[0];
		var url = "/ticket/ticket/quantitySalesDialog.jhtml?ticketPriceId=" + id;
		$(ifr).attr("src", url);

		$("#quantitySales_dialog").dialog("open");

	}
};


$(function(){
	TicketEditOfferList.initTicketEditOfferList();
	TicketEditOfferList.initConf();
});

