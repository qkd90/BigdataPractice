/**
 * Created by vacuity on 15/11/24.
 */


var WechatResource = {

    // 初始化表格数据
    initDgList:function() {
        $("#dg").datagrid({
            fit:true,
            //title:'资源列表',
            //height:400,
            url:'/wechat/wechatResource/getList.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            fitColumns: true,
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'resName', title: '资源名称', width: 240, sortable: true },
                { field: 'resType', title: '资源类型', width: 150, sortable: true},
                { field: 'content', title: '内容', width: 150, sortable: true },
                { field: 'resObjectParam', title: '资源对象参数', width: 150, sortable: true },
                { field: 'validFlag', title: '是否有效', width: 100, sortable: true,
                    formatter : function(value, row, rowIndex){
                        if(row.validFlag!=null && row.validFlag==true){
                            return "<font style='color:blue'>有效</font>";
                        } else {
                            return "<font style='color:blue'>无效</font>";
                        }
                    }
                },
                { field: 'createTime', title: '添加日期', width: 150, sortable: true},
                {field:'end',title:'操作',width:350,sortable: true,formatter:function(value,row,index){
                    var editClick=" onClick='WechatResource.editForm("+row.id+")'";
                    var editbtn = "<a style='color: blue;text-decoration: underline;' href='#'"+editClick+">编辑</a>";
                    return editbtn;
                } }
            ]],
            toolbar: '#tb',
        });
    },

    doSearch: function () {
        var searchForm = {};
        searchForm['resName'] = $("#resName").val();
        $('#dg').datagrid('load', searchForm);
    },

    addForm:function(){
    	$("#res-type").combobox("setValue","click");
    	$("#res-valid").combobox("setValue","true");
        $("#resource-panel").dialog({
            title:'新增资源',
            modal:true,
            top:"20",
            left:"100",
            onClose:function(){
                $("#resource-form").form('clear');
            }
        });
        $("#resource-panel").dialog("open");
    },

    editForm:function(id){
    	
    	$("#res_id").val("");
    	$("#res-name").val("");
    	$("#res-type").combobox("setValue","");
    	$("#res-content").val("");
    	$("#res-ObjectParam").val("");
    	$("#res-valid").combobox("setValue","");
    	
    	$.post("/wechat/wechatResource/editRes.jhtml", {id:id},
		   function(result){
    		console.log(result);
    			if(result){
    				
    				 $("#resource-panel").dialog({
    			            title:'编辑资源',
    			            modal:true,
    			            top:"20",
    			            left:"100",
    			            onClose:function(){
    			                $("#resource-form").form('clear');
    			            }
    			        });
    				 
    				 	$("#res_id").val(result.id);
    				 	$("#res-name").val(result.resName);
    			    	$("#res-type").combobox("setValue",result.resType);
    			    	$("#res-content").val(result.content);
    			    	$("#res-ObjectParam").val(result.resObjectParam);
    			    	if(result.validFlag){
    			    		$("#res-valid").combobox("setValue","true");
    			    	}else{
    			    		$("#res-valid").combobox("setValue","false");
    			    	}
    			    	
    			        $("#resource-panel").dialog("open");
    				
    			}
    			
			
	
		   });

    	
//        var editUrl="/wechat/wechatResource/editRes.jhtml?id="+id;
//        $("#resource-form").form('load',editUrl);
       
    },

    // 有效
    doValid: function(tbId) {
        var rows = $('#'+tbId).datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.post("/wechat/wechatResource/doValidByIds.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    WechatResource.doSearch();
                }else{
                    show_msg("处理失败");
                }
            }
        );
        //Ads.doSearch();
    },
    // 无效
    doInvalid: function(tbId) {
        var rows = $('#'+tbId).datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.post("/wechat/wechatResource/doInvalidByIds.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("处理成功");
                    WechatResource.doSearch();
                }else{
                    show_msg("处理失败");
                }
            }
        );

    },

}


$(function(){
    WechatResource.initDgList();
});