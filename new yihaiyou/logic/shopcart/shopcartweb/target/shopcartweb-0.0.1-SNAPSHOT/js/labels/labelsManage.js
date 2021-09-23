var LabelManage= {
	init:function(){
		LabelManage.initTree();
		LabelManage.initAreaComp();
		LabelManage.initTable();
		LabelManage.initCombox();
		// 设置默认选中
		$("#sea_type").combobox("select", 'SCENIC');
	},
	clearSearch:function(){
		//$("#hidden_areaId").val("");
		$("#sea_name").textbox("setValue","");
		$('#left_tree').treegrid('unselectAll');
		LabelManage.doSearch();
	},
	doSearch:function(tagIds,labelId){
		var data = {
			//cityId:	$("#hidden_areaId").val(),
			name:$("#sea_name").textbox("getValue"),
			tagIds:tagIds,
			labelId:labelId,
			type:$("#sea_type").combobox('getValue')
		};
		$('#tgrid').datagrid('load',data);
	},

	initCombox:function(){
		$("#sea_type").combobox({
			valueField: 'value',
			textField: 'text',
			data: [
				{ value: 'SCENIC', text: '景点' },
				{ value: 'DELICACY', text: '美食' },
				//{ value: 'CITY', text: '城市' },
				{ value: 'PLAN', text: '定制行程' },
				{ value: 'RECOMMEND_PLAN', text: '游记' },
				{ value: 'TICKET', text: '门票' },
				{ value: 'HOTEL', text: '酒店' },
				{ value: 'CRUISESHIP', text: '邮轮' },
				{ value: 'SAILBOAT', text: '海上休闲'}
			],
			onSelect:function(record){
				LabelManage.clearSearch();
				var type = record.value;
				if(type==="SCENIC"){
					$('#tgrid').datagrid({url:'/scenic/scenic/yhyScenicList.jhtml'});
				}
				if(type==="CITY"){
					$('#tgrid').datagrid({url:'/region/area/getAreaListByLabel.jhtml'});
				}
				if(type==="RECOMMEND_PLAN"){
					$('#tgrid').datagrid({url:'/plan/recommendPlan/yhyRecommentPlanList.jhtml'});
				}
				if(type==="DELICACY"){
					$('#tgrid').datagrid({url:'/delicacy/delicacy/yhyDelicacyList.jhtml'});
				}
				if(type==="PLAN"){
					$('#tgrid').datagrid({url:'/plan/plan/yhyPlanList.jhtml'});
				}
				if(type==="TICKET"){
					$('#tgrid').datagrid({url:'/ticket/ticket/yhyTicketLabelList.jhtml'});
				}
				if(type==="HOTEL"){
					$('#tgrid').datagrid({url:'/hotel/hotel/yhyHotelList.jhtml'});
				}
				if(type==="SAILBOAT"){
					$('#tgrid').datagrid({url:'/ticket/ticket/yhySailboatLabelList.jhtml'});
				}
				if(type==="CRUISESHIP"){
					$('#tgrid').datagrid({url:'/cruiseship/cruiseShip/yhyCruiseshipLabelList.jhtml'});
				}
			}
		})
	},

	initTable:function(){
		var type = $("#sea_type").combobox("getValue");
		$('#tgrid').datagrid({
			data: [],
			border:false,
			pagination:true,
			singleSelect:true,
			fit: true,
			fitColumns:true,
			height:'100%',
			pageList:[ 10, 20, 30 ],
			columns:[[
				{field:'id',title:'产品标识',width:80},
				{field:'name',title:'产品名称',width:250,
					formatter: function (value, rowData, rowIndex) {
						var type = $("#sea_type").combobox("getValue");
						if (!type) {
							return value;
						}
						//var url = FG_DOMAIN + "/line_detail_" + productId + ".html";
						var uri = null;
						if (type==="SCENIC") {
							uri = "/scenic_detail_" + rowData.id + ".html";
						} else if(type==="CITY"){
							uri = "/city_" + rowData.id + ".html";
						} else if(type==="RECOMMEND_PLAN"){
							uri = "/guide_detail_" + rowData.id + ".html";
						} else if(type==="DELICACY"){
							uri = "/food_detail_" + rowData.id + ".html";
						} else if(type==="PLAN"){
							uri = "/plan_detail_" + rowData.id + ".html";
						} else if(type==="TICKET"){
							uri = null;
						} else if(type==="HOTEL"){
							uri = "/hotel_detail_" + rowData.id + ".html";
						} else if(type==="LINE"){
							uri = "/line_detail_" + rowData.id + ".html";
						} else {
							uri = null;
						}
						if (uri) {
							return "<a style='color:blue;' href='" + FG_DOMAIN + uri + "' target='_blank'>" + value + "</a>";
						} else {
							return value;
						}
					}
				},
				{field:'cityName',title:'地区',width:150},
				{
					field: 'updateTime',
					title: '更新时间',
					width: 140,
					align: 'center',
					formatter: function (value, rowData, rowIndex) {
						if (value != null && value != "") {
							return value;
						} else {
							return "-";
						}
					}
				},
				{field:'labelNames',title:'标签',width:260,
					formatter: function(value,row,index){
						if(row.labelNames.length>0){
							var html = "<ul>";
							console.log(row.labelNames.length);
							$.each(row.labelNames,function(i,perValue){
								if((row.labelNames.length-1)!=i){
									html += "<li style='border-bottom:1px #7F99BE dotted;'>"+ perValue +"</li>";
								}else{
									html += "<li>"+ perValue +"</li>";
								}

							});
							html += "</ul>";
							return html;
						} else {
							return "暂无标签";
						}

					}
				},
				{field:'opt',title:'操作', width:80, align:'center',
					formatter: function(value,row,index) {
						var html = '<a href="javascript:void(0)" style="color:blue;"  onclick="LabelManage.addLabels('+row.id+')">标签</a>';
						return html;

					}
				},
			]]
		});
	},
	sortLabelsItem:function(id,sort,laId){
		$("#sortForm").form('clear');
		$("#sortId").textbox("setValue",sort);
		$("#hid_targetId").val(id);
		$("#hid_labId").val(laId);
		$('#sort_dlg').dialog({
			closed: false,
			cache: false,
			modal: true
		});
		$('#sort_dlg').dialog('open');
	},
	subSort:function(){

		var sort = $("#sortId").textbox('getValue');
		var laId = $("#hid_labId").val();
		var target = $("#hid_targetId").val();
		if(sort.length>0){
			var data = {
				sort:sort,
				labelId:laId,
				targetId:target,
				type:$("#sea_type").combobox('getValue')
			};

			var url = "/labels/productLabels/saveSort.jhtml";
			$.post(url, data,
				function(result){
					if(result.success){
						if($('#left_tree').treegrid('getSelected')){
							LabelManage.searchByLaId(laId);
						}else{
							LabelManage.doSearch();
						}

						$('#sortForm').form('clear');
						$('#sort_dlg').dialog('close');
					}else{
						show_msg("序号重复！");
						$("#sortId").textbox('setValue',"");
					}
				});


		}else{
			show_msg("请设置排序号");
		}
	},
	// 产品标签
	addLabels:function(targetId) {
		var targetType = $("#sea_type").combobox("getValue");
		LabelMgrDg.open(targetType, targetId, function() {
			LabelManage.searchLabelItem();
		});
	},

	initTree:function(){
		$('#left_tree').treegrid({
			fitColumns: true,
			url:'/labels/productLabels/loadLabelTree.jhtml',
			idField:'id',
			fit:true,
			border:false,
			treeField:'name',
			showHeader:false,
			columns:[[
				{title:'标签名称',field:'name',width:'100%'},
			]],
			onClickRow:function(row){
				LabelManage.searchByLaId(row.id);
			}
		});

	},
	searchLabelItem:function(){

		var row = $('#left_tree').treegrid('getSelected');
		if(row){
			LabelManage.searchByLaId(row.id);
		}else{
			LabelManage.doSearch();
		}

	},
	searchByLaId:function(laId){
		$.post('/labels/productLabels/findTarIdsByLabelId.jhtml',{labelId:laId,type:$("#sea_type").combobox("getValue")},
			function(result){
				if(result.success){
					var tIds = result.targetIds;
					var tIdsStr = tIds.join(",");

					LabelManage.doSearch(tIdsStr,laId);
				}
			});
	},

	// 初始控件
	initAreaComp : function() {
		// 城市查询条件
		$('#qryCity').textbox({
			onClickButton:function() {
				$('#qryCity').textbox('setValue', '');
				$('#qryCity').attr('data-country', '');
				$('#qryCity').attr('data-province', '');
				$('#qryCity').attr('data-city', '');
                // 特殊处理，为了结合原来代码
                $('#hidden_areaId').val('');
			}
		});
		$("#qryCity").next('span').children('input').focus(function() {
			//$(this).blur(); // 移开焦点，否则事件会一直触发
			var country = $('#qryCity').attr('data-country');
			var province = $('#qryCity').attr('data-province');
			var city = $('#qryCity').attr('data-city');
			AreaSelectDg.openForQry(country, province, city, function(countryId, provinceId, cityId, fullName) {
				$('#qryCity').textbox('setValue', fullName);
				if (countryId) {
					$('#qryCity').attr('data-country', countryId);
				} else {
					$('#qryCity').attr('data-country', '');
				}
				if (provinceId) {
					$('#qryCity').attr('data-province', provinceId);
				} else {
					$('#qryCity').attr('data-province', '');
				}
				if (cityId) {
					$('#qryCity').attr('data-city', cityId);
				} else {
					$('#qryCity').attr('data-city', '');
				}
				// 特殊处理，为了结合原来代码
				if (cityId) {
					$('#hidden_areaId').val(cityId);
				} else if (provinceId) {
                    $('#hidden_areaId').val(provinceId);
				} else if (countryId) {
                    $('#hidden_areaId').val(countryId);
				}
                LabelManage.searchLabelItem();
			});
		});
	}
};

$(function(){
	LabelManage.init();
	//$('#sort_dlg').dialog('close');
});