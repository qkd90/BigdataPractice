/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
    WechatAccount.init();
    
    $.extend($.fn.validatebox.defaults.rules, {
        origId: {
          validator: function (value) {
        	  
        	  var exist=$.ajax({
                  url:"/wechat/wechatAccount/validateOriId.jhtml",
                  data:{valiContent:value,id:$("#id").val()},
                  type:'POST',
                  async:false
              }).responseText;
        	  
        	  var obj = eval('(' + exist + ')');
        	  
        	  if(obj.success){
        		  return true;
        	  }else{
        		  return false;
        	  }
          },
    		message: 'ORIGINALID已存在，请重新编写！'
        }
    });
});




var WechatAccount={

    searchparam: $("#searchparam"),
    

    init: function(){
        $('#accountDg').datagrid({
            url:'/wechat/wechatAccount/getAccountList.jhtml',
            pagination: true,
            pageList: [10, 20],
            border: false,
            fit:true,
            rownumbers: true,
            fitColumns: true,
            singleSelect: true,
            checkOnSelect: true,
            columns:[[
                {field:'id', checkbox:true, width:100,sortable: false},
                {field:'account',title:'公众号',width:100,sortable: false},
                {field:'appId',title:'APPID',width:110,sortable: false},
                {field:'appSecret',title:'APPSECRET',width:170,sortable: false},
                {field:'originalId',title:'ORIGINALID',width:110,sortable: false},
                {field:'user.userName',title:'创建人',width:90,sortable: false},
                {field:'validFlag',title:'状态',width:50,sortable:false,
                    formatter : function(value, row, rowIndex){
                        if(row.validFlag!=null && row.validFlag==true){
                            return "<span style='color:green'>有效</span>";
                        }else {
                            return "<span style='color:red'>无效</span>";
                        }
                    }
                },
                {field:'createTime',title:'创建时间',width:100,sortable: false},
                {field:'end',title:'操作',width:120, align:'center', sortable: false,formatter:function(value,row,index){
                    var validClick=" onClick='WechatAccount.changeValid("+row.id+",true)'";
                    var invalidClick=" onClick='WechatAccount.changeValid("+row.id+",false)'";
                    var syncClick=" onClick='WechatAccount.syncMenu("+row.id+")'";
                    var qrcodeClick=" onClick='WechatAccount.qrcodeDialog("+row.id+")'";
                    var menuClick=" onClick='WechatAccount.menuDialog("+row.id+")'";
                    var editClick=" onClick='WechatAccount.editInfo("+row.id+")'";
                    var validbtn = "<a style='color: blue;' href='#'"+validClick+">设为有效</a>";
                    var invalidbtn = "<a style='color: blue;' href='#'"+invalidClick+">设为无效</a>";
                    var menuBtn = "<a style='color: blue;' href='#'"+menuClick+">菜单编辑</a>";
                    var syncBtn = "<a style='color: blue;' href='#'"+syncClick+">菜单同步</a>";
                    var editBtn = "<a style='color: blue;' href='#'"+editClick+">编辑</a>";
                    var qrcodeBtn = "<a style='color: blue;' href='#'"+qrcodeClick+">查看二维码</a></div>";
                    var space = "&nbsp;&nbsp;";


                    var clickZone = "";
                    if(row.validFlag!=null && row.validFlag==true){
                        clickZone = invalidbtn + space + qrcodeBtn;
                    }else {
                        clickZone = validbtn + space + qrcodeBtn;
                    }
                    return clickZone;
                } }
            ]],
            toolbar:"#searchparam"
            //onDblClickCell: function(index,field,value){
            //    var rows = $('#accountDg').datagrid('getSelections');
            //    var id = rows[0].id;
            //    WechatAccount.editAccount(id);
            //}
        });
    },

    doSearch: function () {
        var searchForm = {};
        searchForm['account'] = $("#search-account").val();
        $('#accountDg').datagrid('load', searchForm);
    },

    doClear: function() {
        var searchForm = {};
        $("#search-account").val("");
        searchForm['account'] = $("#search-account").val();
        $('#accountDg').datagrid('load', searchForm);
    },

    editInfo:function(){

        var rows = $('#accountDg').datagrid('getChecked');

        if (rows.length <= 0) {
            show_msg("请选择要编辑的对象！");
            return ;
        }

        var row = rows[0];

    	var url = "/wechat/wechatAccount/getAccountInfo.jhtml?id=" + row.id;
     	$('#add-account-form').form('load', url);
    	$("#add-account-dialog").dialog({
            title:'编辑公众号',
            modal:true,
            top:"20",
            left:"100",
            onClose:function(){
                $("#aaccountdd--form").form('clear');
            }
        });
    	
    	$("#account").textbox({editable:false,}); 
    	$("#appId").textbox({editable:false,}); 
    	$("#appSecret").textbox({editable:false,}); 
    	$("#originalId").textbox({editable:false,}); 
    	$("#mchId").textbox({editable:false,}); 

        $("#add-account-dialog").dialog("open");
       
        
    	
    },
    
    addAccount: function () {
    	$("#id").val("");
    	$("#account").textbox({editable:true,}); 
    	$("#appId").textbox({editable:true,}); 
    	$("#appSecret").textbox({editable:true,}); 
    	$("#originalId").textbox({editable:true,}); 
    	$("#mchId").textbox({editable:true,}); 
    	$("#account").textbox("setValue",""); 
    	$("#appId").textbox("setValue",""); 
    	$("#appSecret").textbox("setValue",""); 
    	$("#originalId").textbox("setValue",""); 
    	$("#mchId").textbox("setValue",""); 
    	$("#mchKey").textbox("setValue",""); 
    	$("#defaultTplId").textbox("setValue",""); 
        $("#add-account-dialog").dialog({
            title:'新增公众号',
            modal:true,
            top:"20",
            left:"100",
            onClose:function(){
                $("#aaccountdd--form").form('clear');
            }
        });
        $("#add-account-dialog").dialog("open");
    },

    changeValid: function (id, validFlag) {

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中，请稍后...'
        });

        $.post("/wechat/wechatAccount/changeValid.jhtml",
            {id : id, validFlag : validFlag},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    $.messager.progress("close");
                    show_msg("处理成功");
                    WechatAccount.doSearch();
                }else{
                    $.messager.progress("close");
                    show_msg("处理失败");
                }
            }
        );
    },

    syncMenu: function (){
        var rows = $('#accountDg').datagrid('getChecked');
        if (rows.length <= 0) {
            show_msg("请选择要编辑的对象！");
            return ;
        }
        var row = rows[0];
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中，请稍后...'
        });
        $.post("/wechat/wechat/syncWechatMenu.jhtml",
            {accountId : row.id},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    $.messager.progress("close");
                    show_msg("处理成功");
                }else{
                    $.messager.progress("close");
                    show_msg("处理失败");
                }
            }
        );
    },

    qrcodeDialog: function (id) {

        var t = Math.random(); 	// 保证页面刷新
        url = "/wechat/wechatQrcode/list.jhtml?accountId=" + id;
        var ifr = $("#qrcode-dialog").children()[0];
        $(ifr).attr("src", url);
        $("#qrcode-dialog").dialog({
            //fit: true,
            width:600,
            height:500,
            modal: true,
            title:"二维码管理"
        });

    },

    menuDialog: function(){

        var rows = $('#accountDg').datagrid('getChecked');

        if (rows.length <= 0) {
            show_msg("请选择要编辑的对象！");
            return ;
        }

        var row = rows[0];

        var menuUrl = "/wechat/wechatMenu/manage.jhtml?accountId=" + row.id;
        $("#menu-dialog").dialog({
            href: menuUrl,
            fit: true,
            title: "菜单管理",
            onOpen: function(){
            },
            onClose: function () {
                WechatMenu.flag = true;
                WechatMenu.currNode = null;
                WechatMenu.currMax = 16;
                WechatMenu.inValid = true;
                WechatMenu.firstFlag = true;
            }
        });
    },
    
    submitData:function(){
    	
    	$.messager.progress({title:"提示信息",msg:"正在保存"}); 

    	$('#add-account-form').form('submit', {
			url : "/wechat/wechatAccount/saveAccount.jhtml",
			onSubmit : function() {
				var isValid = $("#add-account-form").form('validate');
				
				if(isValid){
					return isValid;
				}else{
					show_msg("请完善公众号信息！");
					return isValid;
				}
				
				
			},
			success : function(result) {
				$.messager.progress("close");
				var data = eval('(' + result + ')');
				if(data.success){
					$("#add-account-dialog").dialog("close");
					show_msg("保存成功！");
					var searchForm = {};
			        searchForm['account'] = $("#search-account").val();
			        $('#accountDg').datagrid('load', searchForm);
				}else{
					show_msg("操作失败！");
				}
			}
		});
    },

}



