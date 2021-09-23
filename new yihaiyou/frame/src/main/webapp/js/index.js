////////////////////////首页顶层菜单////////////////////////
$(function(){
	Menu.changeMenu();
});
var Menu=$.fn.extend({
	changeMenu:function(){
		$(".j-smenu").click(function(){
			var s=$(this);
			//var href=$.trim(s.attr("href"));
			var menuid=$.trim(s.attr("menuid"));
			var param={'parentId':menuid};
			var url="/main/index/changeMenu.jhtml";
			showLoading();
			$.post(url,param,function(result) {
				hideLoading();
				if (result.success) {
					var _menus=result.menuList;
					InitLeftMenu(_menus,true);
				} 
			}, 'json');
		});
	}
});
function showLoading()
{
    document.getElementById("over").style.display = "block";
    document.getElementById("loadlayout").style.display = "block";
}
function hideLoading()
{
    document.getElementById("over").style.display = "none";
    document.getElementById("loadlayout").style.display = "none";
}
// 校验两次输入密码是否一致
$.extend($.fn.validatebox.defaults.rules, {
	equals : {
		validator: function(value, param){
			return value == $(param[0]).val();
		},
		message: '两次密码输入不一致'
	}
});
// 打开修改密码窗口
function openEditPwdDg() {
	$('#editPwdDg').window('open');
}
// 确认修改密码
function cfmSavePwd() {
	$('#pwdForm').form('submit', {
		url:'/sys/login/changeFenxiaoPWD.jhtml',
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if(isValid){
				$.messager.progress({
					title:'温馨提示',
					text:'数据处理中,请耐心等待...'
				});
			}
			return isValid;
		},
		success : function(data) {
			$.messager.progress("close");
			var obj = eval('(' + data + ')');
			if (obj) {
				show_msg(obj.errorMsg);
				$('#editPwdDg').dialog('close');
			} else {
				show_msg(obj.errorMsg);
			}
		}
	});
}
// 退出
function loginOut() {
	$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
		if (r) {
			var url = "/sys/login/loginOut.jhtml";
			var param = {};
			$.post(url, param, function(r) {
				if (r.success) {
					location.href="/sys/login/login.jhtml";
				}
			});
		}
	});
}