function tokeydown(){
	if(event.keyCode==13){
        vsubform("ADMIN");
	}
}
function imgClick(eleId){          
    document.getElementById(eleId).src="/images/checkNum.jsp?"+ new Date().getTime();
}

function vsubform(type){
  var panel = "_" + type;
  if(!$("#account"+panel).val()){
   $("#validatetd"+panel).find("span").html("请输入登录账号!");
   $("#validatetd"+panel).show();
   $("#account"+panel).focus();
   return false;
  }
  if(!$("#password"+panel).val()){
   $("#validatetd"+panel).find("span").html("请输入密码!");
   $("#validatetd"+panel).show();
   $("#password"+panel).focus();
   return false;
  }
  if(!$("#validateVlue"+panel).val()){
   $("#validatetd"+panel).find("span").html("请输入验证码!");
   $("#validatetd"+panel).show();
   $("#validateVlue"+panel).focus();
   return false;
  }
    $("#password"+panel).next("input[name=password]").val(hex_md5($("#password"+panel).val()));
  $("#account").val($("#account"+panel).val());
  $("#password").val($("#password"+panel).val());
  $("#validateVlue").val($("#validateVlue"+panel).val());
  //$("#userAccount").val($("#userAccount"+panel).val());
  $("#userType").val(type);
  $("#WfStaffAction").attr("action","/sys/login/doLogin.jhtml");
  $("#WfStaffAction").submit();
}

function loginOut(){
	var url="/sys/login/loginOut.jhtml";
	var param={};
	$.post(url,param,function(r){
		 $("#submitbtn").show();
		 if(r.success){
			 location.href="/sys/login/login.jhtml";
		 }
	 });
}