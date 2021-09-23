var CusType=$.extend({
	clickrow:undefined,
	//初始化页面信息
	init:function(){
		//初始化树形菜单
		$('#tt').treegrid({    
		    url:'/customer/custype/loadSTtree.jhtml',    
		    idField:'id',    
		    treeField:'text', 
		    showHeader:false,
		    fitColumns:true,
		    columns:[[    
		        {field:'text',title:'text'},    
		    ]],
		    toolbar:'#toolbar',
		    onContextMenu:function(e, row){
		    	e.preventDefault();
		    	var isShow=row.isShow;
		    	CusType.clickrow=row;
		    	if(isShow){
		    		// 查找“显示”项并禁用它
		    		var item = $('#mm').menu('findItem', '隐藏');
		    		if(item!=undefined){
		    			$('#mm').menu('removeItem', item.target);
		    		}
		    	}else{
		    		// 查找“隐藏”项并禁用它
		    		var item = $('#mm').menu('findItem', '显示');
		    		if(item!=undefined){
		    			$('#mm').menu('removeItem', item.target);
		    		}
		    	}
		    	if(row.type==0){
		    		
		    	}
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
		    },
		    //加载成功后
		    onLoadSuccess:function(){
		    	
		    }
		});
		
		$(".propertys").click(function(){
			var obj=$(this);
			var propertyname=obj.attr("cpname");
			var cptid=obj.attr("cptid");
			var cpid=obj.attr("cpid");
			//查询相同属性分类
			var props=$("ul[cptid='"+cptid+"']");
			//已经添加过(存在)相关属性分类
			var propli="<li cpid='"+cpid+"'>";
			var propli2=propertyname+"<input type='text' value='等于' /> <input type='text' value='厦门' /> </li>";
			if(props!=null&&props.length>0){
				//查询是否有相同属性
				var prop=$("li[cpid='"+cpid+"']");
				if(prop!=null&&prop.length>0){
					//已经添加过(存在)相同属性
					props.append(propli+"<input type='text' value='并且' />"+propli2);
				}else{
					//已经添加过(存在)相同属性
					props.append(propli+propli2);
				}
			}else{
				var cptdiv=$("div[cpptid='"+cptid+"']");
				cptdiv.append("<ul class'propertyUL' cptid='"+cptid+"'>"+propli+propli2+"</ul>");
				cptdiv.panel("open");
			}
		});
	}
	//添加属性事件
	
});
//点击添加类目按钮
function showAddPackage(e){
	var row=CusType.clickrow;
	if(row==null||row=='-1'){
		alert(CusType.clickrow.text);
	}
}