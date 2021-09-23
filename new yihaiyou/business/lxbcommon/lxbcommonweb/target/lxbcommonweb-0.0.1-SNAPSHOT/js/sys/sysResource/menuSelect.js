
var SysMenu={
	doSearch:function(){
		$("#dg2").datagrid('load',{
			'menuname':$("#m_menuname").val(),
			'status':$("#m_status").val(),
			'url':$("#m_url").val(),
		});
	},
	//初始化模块树
	initMenuTree:function(){
		$.ajax({  
	         type : "post",  
	          url : "/sys/sysMenu/loadMenuTree.jhtml",  
	          async : false,  
	          success : function(data){  
	        	  $('#tt2').tree({
		  				data : data.menus,
		  				method : 'post',
		  				animate : true,
		  				onClick : function(node) {
		  					$("#eastPanel2").panel({
		  						'title':node.text
		  					});
		  					$('#dg2').datagrid('load', {
		  						'parentId' : node.id
		  					});
		  				}
		  			}); 
	          }  
	     }); 
	},
	//初始化表格数据
	initDgList:function(){
		$("#dg2").datagrid({
			height:600,
			url:'/sys/SysMenu/searchMenu.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,
			striped:true,//斑马线
			ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
			onDblClickRow:function(index,rowdata){
				SysMenu.selectMenu(rowdata.menuid,rowdata.menuname);
			},
			columns: [[
	                   { field: 'menuname', title: '模块名称', width: 350, sortable: true },
	                   { field: 'parentId', title: '上级模块', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.parentId!=null){
	                		   var parentMenu=$("#tt").tree("find",row.parentId);
	                		   if(parentMenu!=null){
	                			   return parentMenu.text;
	                		   }
	                	   }
	                   } },
	                   { field: 'url', title: '链接', width: 350, sortable: true },
	                   //{ field: 'menuImg', title: '模块大图标', width: 350, sortable: true },
	                   { field: 'icon', title: '模块图标', width: 350, sortable: true },
	                   { field: 'menulevel', title: '模块等级', width: 350, sortable: true },
	                   { field: 'status', title: '状态', width: 350, sortable: true,formatter:function(value,row,index){
	                	   if(row.status!=null&&row.status==1){
	                		   return "<font style='color:red'>冻结</font>";
	                	   }else{
	                		   return "<font style='color:green'>启用</font>";
	                	   }
	                   } },
	                   { field: 'seq', title: '排序号', width: 350, sortable: true },
	                   { field: 'remark', title: '模块描述', width: 350, sortable: true },
	                   { field: 'button', title: '按纽', width: 350,formatter:function(value,row,index){
	                		 return "<input type='button' value='选择' onClick='SysMenu.selectMenu(\""+row.menuid+"\",\""+row.menuname+"\")'>";
	                   } },
            ]],
		});
	},
	
	//清除表单
	clearForm:function(){
		$("#ff").form("clear");
	},
	//选择模块
	selectMenu:function(menuid,mname){
		if(menuid!=null&&mname!=""){
			$("#menuid").val(menuid);
			$("#menuname").textbox("setValue",mname);
			selectMenuDialog.dialog("close");
		}
	}
};
