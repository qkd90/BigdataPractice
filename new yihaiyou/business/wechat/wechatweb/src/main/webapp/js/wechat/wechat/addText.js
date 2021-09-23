/**
 * Created by vacuity on 15/11/20.
 */
$(function() {
    AddText.init();
});
var AddText={
    init: function(){
    	AddText.initTextArea();
    	AddText.initInput();
    },
    initInput:function(){
        $($('#input_title').textbox('textbox')).bind("keyup", function (e) {
            var textTitle = $("#input_title").textbox("getText");
            var titleLength = textTitle.length;
            if(titleLength <= 64){
                $("#textLength").html(titleLength);
            }else if(titleLength == 0){
                $("#textLength").html("0");
            }else{
                show_msg("标题长度过长，请重新编辑！");
            }
        });
        // 栏目选择组件
        $("#input_category").combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=message',
            required: true,
            width: 280
        });
    },
    saveBefore:function(){
    	var flag = true;
    	if($("#input_title").textbox("getValue").length<=0){
    		return flag = false;
    	}
    	var html = $("#text_content").val();
//    	alert(html);
    	if(html.length<=0){
    		return flag = false;
    	}
    	return flag;
    },
    addText:function(){
    	$('#textFormId').form('submit', {
			url : "/wechat/wechatDataText/saveText.jhtml",
			onSubmit : function() {
				var isValid = AddText.saveBefore();
				if(isValid){
					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
				} else {
					show_msg("请完善当前页面数据");
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
//					show_msg("操作成功");
					var hidden_reply = $("#hidden_reply").val();
					$.messager.alert('温馨提示', '操作成功', 'info', function() {
						if(hidden_reply){
							window.parent.ReplyText.doSearch();
							window.parent.$("#editText").dialog("close");
						}else{
							parent.window.WechatData.closeEditText(true);
						}
	  				});	
				}else{
					show_msg("操作失败");
				}
			}
		});
//    	alert("a");
    },
    initTextArea:function(){
    	var content;
		KindEditor.ready(function(K) {
			content = K.create('#text_content', {
                width: '280px',
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [ 'link'],
				newlineTag : "br",
				filterMode : true,
				htmlTags : {a : ['class', 'href', 'target', 'name', 'style'], br : ['/']},
				afterChange: function() { 
					this.sync(); 
					//if(this.count()<=1200){
					//	K('#textareaLength1').html(1200-this.count());
					//}else{
					//	show_msg("链接元素过长，请重新编辑！");
					//}
                    //
					//if(this.count('text')<=600){
					//	K('#textareaLength2').html(600-this.count('text'));
					//}else{
					//	show_msg("纯文本字数，请重新编辑！");
					//}
					
//					var reg=new RegExp("<br />","g"); //创建正则RegExp对象  
					var stringObj=this.html();  
//					var newstr=stringObj.replace(reg,""); 
					$("#hidden_content").val(stringObj);
					
				}, 
				afterBlur: function() { 
					this.sync(); 
					
				},
			});
		});
    }
}
