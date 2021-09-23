<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <title>游记列表</title>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div title="" data-options="region:'north',border:true" style="height:80px">
       	<div style="padding:10px 0 5px 10px">
	    	<table id="qryCondition">	                		    	
	    	    <tr>
	    	    <%--<td width="80" align="right"></td>--%>
                    <%--<td>--%>
                        <%--<input type="hidden" id="qry_cityCode">--%>
                        <%--<input type="hidden" id="qry_isChina">--%>
                        <%--<input id="qryCity" class="easyui-textbox"--%>
                               <%--data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"--%>
                               <%--style="width:200px" data-country="" data-province="" data-city="">--%>
                        <%--&lt;%&ndash;<select class="easyui-combobox" name="province" id="qry_province" style="width:120px">&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<option value="" selected="selected"></option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<select class="easyui-combobox" name="city" id="qry_city" style="width:120px">&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<option value="" selected="selected"></option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--</td>--%>
	    	    	<td width="80" align="right">游玩天数:</td>
			    	<td><input class="easyui-numberbox" id="qry_daysStart" name="daysStart" style="width:80px;"></td>
	    			<td width="10" align="center">-</td>
			    	<td><input class="easyui-numberbox" id="qry_daysEnd" name="daysEnd" style="width:80px;"></td>
	    	    	<!-- <td width="80" align="right">提交时间:</td>
			    	<td><input class="easyui-datebox" id="qry_dateStart" name="dateStart" style="width:120px;"></td>
	    			<td width="10" align="center">-</td>
			    	<td><input class="easyui-datebox" id="qry_dateEnd" name="dateEnd" style="width:120px;"></td> -->
			    	<td width="80" align="right">筛选：</td>
	    	    	<td>
                        <select class="easyui-combobox" name="condition" id="qry_condition" style="width: 90px;">
                            <option value="0" selected="selected">全部</option>
                            <option value="1">已上架</option>
                            <option value="2">已下架</option>
                            <option value="3">草稿</option>
                            <option value="4">无出发时间</option>
                        </select>
	    	    	</td>
                    <td width="80" align="right">游记名称:</td>
                    <td colspan="3">
                        <input class="easyui-textbox" id="qry_keyword" name="keyword"
                               data-options="prompt:'请输入游记名称/ID'" style="width:180px;">
                    </td>
                    <!-- <td align="right">上传者类型：</td>
                    <td>
                        <input class="easyui-textbox" id="qry_companyId" name="companyId" style="width:120px;"/>
                    </td> -->
                    <td colspan="4">
                        <div style="text-align:right;padding:5px 10px" >
                            <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" onClick="query()">查询</a>
                            <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" onClick="reset()">重置</a>
                        </div>
                    </td>
	    	</table>
	    </div>
	</div>

	<div data-options="region:'center',border:false">
		<table id="qryResult"></table>
	</div>
</div>
<div id="countInfoWindow" class="easyui-window" title="统计游记信息" data-options="closed:true,modal:true" style="width:600px;height:400px">
	<div data-options="region:'center',border:false">
		<table id="countResult"></table>
	</div>
</div>
</body>
</html>
<script type="text/javascript">	
$(pageInit);
function pageInit() {
    initProvince();
}
	var tmpId = "";
	// 查询
	function query() {	
		$("#qryResult").datagrid("load", {});
	}
	
	// 重置
	function reset() {
		$('#qryCondition').form('clear');
	}

	// 上架，多个id用“,”分隔
	function upSell() {
		var ids = getSelIds('2', '选中记录不能包含状态为上架的记录！');
		if (!ids) {
			return ;
		}
		$.post("/recplan/recplan/batchUpSell.jhtml",
			{ids : ids},
			function(result) {
				BgmgrUtil.backCall(result, function() {
					$.messager.alert('提示', '操作成功！', 'info', function() {
						query();
					});
				}, null);
    		}
		);
	}

	// 下架，多个id用“,”分隔
	function downSell() {
		var ids = getSelIds('1,3', '选中记录不能包含状态为草稿、下架的记录！');
		if (!ids) {
			return ;
		}
		$.post("/recplan/recplan/batchDownSell.jhtml",
			{ids : ids},
			function(result) {
				BgmgrUtil.backCall(result, function() {
					$.messager.alert('提示', '操作成功！', 'info', function() {
						query();
					});
				}, null);
    		}
		);
	}
	
	// 删除，多个id用“,”分隔
	function del() {
		var ids = getSelIds('2', '选中记录不能包含状态为上架的记录！');
		if (!ids) {
			return ;
		}
		$.messager.confirm('提示', '您确认删除选中记录？', function(r){
			if (r) {
				$.post("/recplan/recplan/batchDel.jhtml",
					{ids : ids},
					function(result) {
						BgmgrUtil.backCall(result, function() {
							$.messager.alert('提示', '操作成功！', 'info', function() {
								query();
							});
						}, null);
		    		}
				);
			}
		});
	}
	
	// 编辑
	function edit(id) {
		window.open('http://www.lvxbang.com/lvxbang/recommendPlan/edit.jhtml?recplanId='+id);
	}

	// 获取选中行的标识
	function getSelIds(excludeStatus, info) {
		var rows = $('#qryResult').datagrid('getSelections');
		if (rows.length < 1) {
			$.messager.alert('提示', '未勾选任何记录！', 'info');
			return null;
		}
		var idsArray = [];
    	for (var i = 0; i < rows.length; i++){
        	var row = rows[i];
        	if (excludeStatus && excludeStatus.indexOf(row.status+"") >= 0) {	// 有要排除的状态
       			$.messager.alert('提示', info, 'info');
       			return null;
        	}
        	idsArray.push(row.id);                     
        } 
        var ids = idsArray.join(',');
        return ids;
	}
	
	function showCountRecplanInfo(){
		$("#countResult").datagrid({url:'/recplan/recplan/countRecplanInfo.jhtml'});
		$('#countInfoWindow').window('open');
	}
	$(function(){
		var allCount=0,allOnSale=0,allOffSale=0,allDraft=0;
		$('#countResult').datagrid({
			columns:[[
			   {
					field : 'cityId',
					title : 'cityId',
					width : 80,
					align : "center",
			   },
			   {
					field : 'fullPath',
					title : '城市名称',
					width : 150,
					align : "center",
				},
				{
					field : 'totalCount',
					title : '总游记数',
					width : 80,
					align : "center",
					formatter : function(value, rowData, rowIndex) {
						if(rowData.totalCount != null){
							return rowData.totalCount;
						}else {
							return "<font color='gray'>0</font>";
						}
					}
				 },
				 {
					field : 'onSaleCount',
					title : '上架游记数',
					width : 80,
					align : "center",
					formatter : function(value, rowData, rowIndex) {
						if(rowData.onSaleCount != null){
							return rowData.onSaleCount;
						}else {
							return "<font color='gray'>0</font>";
						}
					}
				  },
				  {
					field : 'offSaleCount',
					title : '下架游记数',
					width : 80,
					align : "center",
					formatter : function(value, rowData, rowIndex) {
						if(rowData.offSaleCount != null){
							return rowData.offSaleCount;
						}else {
							return "<font color='gray'>0</font>";
						}
					}
				  },
				  {
					field : 'draftCount',
					title : '草稿游记数',
					width : 80,
					align : "center",
					formatter : function(value, rowData, rowIndex) {
						if(rowData.draftCount != null){
							return rowData.draftCount;
						}else {
							return "<font color='gray'>0</font>";
						}
					}
				}
			]],
			onLoadSuccess : function(data) {
				/* allCount,allOnSale,allOffSale,allDraft; */
				allCount = 0;
				allOnSale = 0;
				allOffSale = 0;
				allDraft = 0;
				var rows = $('#countResult').datagrid('getRows');
				for(var i = 0;i<rows.length;i++){
					allCount += rows[i].totalCount;
					allOnSale += rows[i].onSaleCount;
					allOffSale += rows[i].offSaleCount;
					allDraft += rows[i].draftCount;
				}
				$('#countInfoWindow').window('refresh');
				$('#countInfoWindow').panel({"title":"游记统计信息(全部总计:"+allCount+"条,上架总计:"+allOnSale+",下架总计:"+allOffSale+",草稿总计:"+allDraft+")"});
				$('#countInfoWindow').window('refresh');
			}
		});
	});
	
	
	// 页面加载后相关设置项
	$(function(){
		// 构建表格
		$('#qryResult').datagrid({   
			fit:true,
//			title:"游记列表",
			//data:[],
			url:'/recplan/recplan/list.jhtml',
			rownumbers:true,
			border:true,
			singleSelect:false,
			striped:true,
			pagination:true,
			//onLoadSuccess:BgmgrUtil.backCall,
			pageSize:BizConstants.PAGESIZE,
			pageList:BizConstants.PAGELIST,
			columns : [[{
					field : 'ck',
					checkbox:true
				}, {
					field : 'id',
					title : '游记ID',
					width : 80,
                    align: 'center'
				}, {
					field : 'planName',
					title : '游记名称',
					width : 200,
                    align: 'center'/*,
					formatter:function(value, rowData, rowIndex) {
//						var btn = "<a href='http://liujingw.lvxbang.cn/recommend/detail/"+rowData.id+"' target='_blank' style='color:blue;text-decoration: underline;'>"+rowData.planName+"</a>";
						var btn = "<a href='http://www.lvxbang.com/lvxbang/recommendPlan/detail.jhtml?recplanId="+rowData.id+"' target='_blank' style='color:blue;text-decoration: underline;'>"+rowData.planName+"</a>";
						return btn;
					}*/
				}, {
                    field : 'days',
                    title : '游玩天数',
                    width : 60,
                    align: 'center'
                }, {
					field : 'cityNames',
					title : '途径城市',
					width : 440
				}, {
                    field: 'userName',
                    title: '作者',
                    width: 150,
                    align: 'center'
                },{
					field : 'createTime',
					title : '创建时间',
					width : 180,
					datePattern : 'yyyy-MM-dd HH:mm:ss',
					formatter : BgmgrUtil.dateTimeFmt,
                    align: 'center'
				}, {
					field : 'modifyTime',
					title : '修改时间',
					width : 180,
					datePattern : 'yyyy-MM-dd HH:mm:ss',
					formatter : BgmgrUtil.dateTimeFmt,
                    align: 'center'
				}, {
                    field : 'startTime',
                    title : '出发时间',
                    width : 180,
                    datePattern : 'yyyy-MM-dd HH:mm:ss',
                    formatter : BgmgrUtil.dateTimeFmt,
                    align: 'center'
                }, {
					field : 'status',
					title : '状态',
					width : 100,
					align : "center",
					formatter : function (value, rowData, rowIndex) {
                        var btnEdit = "<a id='status_" + rowData.id + "'";
                        if (rowData.status == 2) {
                            btnEdit += " style='color:green;text-decoration: none;'>已上架</a>";
                        } else if (rowData.status == 1) {
                            btnEdit += " style='color:gray;text-decoration: none;'>草稿</a>";
                        } else if (rowData.status == 3) {
                            btnEdit += " style='color:gray;text-decoration: none;'>已下架</a>";
                        } else {
                            btnEdit += " style='color:gray;text-decoration: none;'>已下架</a>";
                        }
                        return btnEdit;
                    }
				}/*, {
					field : "OPT",
					title : "操作",
					align : "center",
					width : 110,
					formatter : function(value, rowData, rowIndex) {
						//var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='view("+rowData.id+")'>查看</a>";
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit("+rowData.id+")'>编辑</a>";
						return btnEdit;
					}
				}*/]],
			toolbar : [{
				text : '新建游记',
				plain:false,
				handler : function() {
					window.open('http://www.lvxbang.com/lvxbang/recommendPlan/edit.jhtml')
				}
			},
 			"-", {
 				text : '上架',

 				handler : function() {
 					upSell();
 				}
 			},
			"-", {
				text : '下架',

				handler : function() {
					downSell();
				}
			}, "-", {
				text : '删除',

				handler : function() {
					del();
				}
			}, "-", {
				text : '统计游记信息',

				handler : function() {
					showCountRecplanInfo();
				}
			}], 
			onBeforeLoad : function(data) {   // 查询参数
//				 var cityIds = $('#qry_city').combobox('getValue');
//	                if (cityIds == '') {
//	                	cityIds = $('#qry_province').combobox('getValue');
//	                    data.cityIds = cityIds.substr(0, 2);
//	                }
//	                else {
//	                    data.cityIds = cityIds.substr(0, 4);
//	                }
//                var regionCode = $('#qry_cityCode').val();
				var regionCode = 350200;
                if (regionCode && regionCode!= "") {
                    data.cityCode = regionCode;
                }
                data.isChina = $("#qry_isChina").val();
                var condition = $('#qry_condition').combobox('getValue');
                switch (condition) {
                    case '1':
                        data.status = 2;
                        break;
                    case '2':
                        data.status = 3;
                        break;
                    case '3':
                        data.status = 1;
                        break;
                    case '4':
                        data.hasStartTime = 0;
                        break;
                }
				data.daysStart = $("#qry_daysStart").numberbox("getValue"); 
				data.daysEnd = $("#qry_daysEnd").numberbox("getValue");
				/* data.dateStart = $("#qry_dateStart").datebox("getValue"); 
				data.dateEnd = $("#qry_dateEnd").datebox("getValue"); */
		        data.planName = $("#qry_keyword").textbox("getValue");
		       /*  data.companyId = $("#qry_companyId").textbox("getValue"); */
			}
		}); 
	});
	/* function openDetailPlan(id){
		window.location.href='http://27.154.225.170:10086/recommend/detail/53' + id;
	} */
</script>