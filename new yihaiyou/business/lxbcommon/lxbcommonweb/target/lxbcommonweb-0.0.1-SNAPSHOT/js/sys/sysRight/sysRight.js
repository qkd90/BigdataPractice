
var SysRight={
	init:function(){
		//初始化模块树
		//SysRight.initMenuTree();
	},
	initMenuTree:function(){
		var roleId=$("#roleId").val();
		$('#tt').treegrid({    
		    url:'/sys/sysRight/showRight.jhtml?roleId='+roleId,    
		    idField:'id',    
		    treeField:'text',
		    checkbox:true,
		    singleSelect:false,
		    width:'100%',
		    height:'95%',
		    toolbar:'#rightBar',
		    columns:[[
		        {title:'',field:'ck',width:22,formatter:
			        	function(value,row,index){
		        		if(row.hasRight=="2"){
		        			return "<input type='checkbox' id='m"+row.id+"' class='m_right' name='m_right' value='"+row.id+"' checked='checked' disabled='disabled' />";
		        		}
		        		if(row.hasRight=="1"){
		        			return "<input type='checkbox' id='m"+row.id+"' class='m_right' name='m_right' value='"+row.id+"' checked='checked' />";
		        		}
		        		if(row.hasRight=="0"){
		        			return "<input type='checkbox' id='m"+row.id+"' class='m_right' name='m_right' value='"+row.id+"' />";
		        		}
		        	}
		        },  
		        {title:'模块',field:'text',width:180,formatter:
		        	function(value,row,index){
		        		if(row.hasRight=="2"){
		        			return "<font style='color:green'>"+value+"</font>";
		        		}else{
		        			return value;
		        		}
		        	}
		        },    
		        {field:'resource',title:'资源',width:800,align:'left',formatter:
		        	function(value,row,index){
		        		if(row.resources!=null&&row.resources.length>0){
		        			var cks="<ul class='resourceUl'><li style='width:50px;'><input type='checkbox' class='r_all' />全选</li>";
		        			$.each(row.resources,function(index,item){
		        				if(item.hasRight=="2"){
				        			cks+="<li><input type='checkbox' name='r_right'  value='"+item.id+"'  checked='checked' disabled='disabled' /><font style='color:green'>"+item.name+"</font></li>";
				        		}
				        		if(item.hasRight=="1"){
				        			cks+="<li><input type='checkbox' name='r_right' value='"+item.id+"' checked='checked' />"+item.name+"</li>";
				        		}
				        		if(item.hasRight=="0"){
				        			cks+="<li><input type='checkbox' name='r_right' value='"+item.id+"'  />"+item.name+"</li>";
				        		}
		        			});
		        			cks+="</ul>";
		        			return cks;
		        		}
		        	}
		        },    
		          
		    ]],
		    onLoadSuccess:function(){
		    	$(".r_all").on('change',function(){
		    		var ck=$(this).attr("checked");
		    		var cks=$(this).parent().parent().find("input[name='r_right']");
		    		cks.each(function(){
		    			if($(this).attr("disabled")!="disabled"){
		    				if(ck!=undefined){
		    					this.checked = true;
		    				}else{
		    					this.checked=false;
		    				}
		    			}
		    		});
		    	});
		    	$(".m_right").change(function(){
		    		var ck=$(this).attr("checked");
		    		var mid=$(this).val();
		    		var children=$('#tt').treegrid("getChildren",mid);
		    		//子模块流性选择
		    		if(children!=null&&children.length>0){
		    			for(var i=0;i<children.length;i++){
		    				var rit=children[i];
		    				if($("#m"+rit.id).attr("disabled")!="disabled"){
		    					if(ck!=undefined){
		    						$("#m"+rit.id).attr("checked","checked");
		    					}else{
		    						$("#m"+rit.id).removeAttr("checked");
		    					}
		    				}
		    			}
		    		}
		    		//父模块选中
		    		var mright=$('#tt').treegrid("find",mid);
		    		if(mright.parentId!=null&&mright.parentId!=null){
		    			if(ck!=undefined){
		    				$("#m"+mright.parentId).attr("checked","checked");
		    			}
		    		}
		    	});
		    },
		    onClickCell:function(filed, row){
		    }
		});  
	},
	//保存权限
	saveSysRight:function(){
		var roleId=$("#roleId").val();
		var m_rights =[];//定义模块权限数组    
		var r_rights =[];//定义资源权限数组    
        $('input[name="m_right"]:checked').each(function(){
        	m_rights.push($(this).val());//将选中的值添加到数组chk_value中    
        });
        $('input[name="r_right"]:checked').each(function(){
        	r_rights.push($(this).val());//将选中的值添加到数组chk_value中    
        });
        $.messager.progress({
			title:'温馨提示',
			text:'数据处理中,请耐心等待...'
		});
        var url='/sys/sysRight/saveRight.jhtml';
        var params={'roleId':roleId,'m_rights':m_rights.toString(),'r_rights':r_rights.toString()};
        $.post(url,params,function(result){
        	$.messager.progress('close');
        	if(result.success){
        		show_msg("权限保存成功!");
        	}else{
        		show_msg(result.errorMsg);
        	}
        });
	}
};
$(function(){
	SysRight.init();
});