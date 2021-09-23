/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
	EditKeyword.init();
});

var EditKeyword = {

	init : function() {
		EditKeyword.initTextArea();
	},

	cancel : function() {
		window.parent.$("#editPanel").dialog("close");
	},

	delTagLi : function(index) {
		$("#li_" + index).remove();
		$("#li_input_" + index).remove();

	},

	initTextArea : function() {
		var content;
		KindEditor.ready(function(K) {
			content = K.create('#editkeyword', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [ '' ],
				afterChange : function() {
					this.sync();
					var text = this.html();
					text = text.replace(/<\/?[^>]*>/g,''); //去除HTML tag
					text = text.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
					text=text.replace(/&nbsp;/ig,'');//去掉&nbsp;
					var htmlArr = text.replace(/\\/g, "\\\\").replace(
							/\\/g, "\\/").replace(/\'/g, "\\\'").replace(
							/\"/g, "\\\"").split('\n');
					var textStr = "";
					$.each(htmlArr, function(i, perValue) {
						if (perValue==="") {
						}else{
							textStr = textStr + perValue.trim();
						}
					});
					
					if (textStr.length <= 30) {
						$("#changeword").html(30 - textStr.length);
					} else {
						show_msg("输入内容超过30个字符，请重新编辑");
					}
					
					$("#hidden_keyword").val(textStr);

				},
				afterBlur : function() {
					this.sync();
				},
				/*
				afterCreate : function() {
					var self = this;
					KindEditor.ctrl(self.edit.doc, 13, function() {
						self.sync();


						var text = self.html();
						text = text.replace(/<\/?[^>]*>/g,''); //去除HTML tag
						text = text.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
						text=text.replace(/&nbsp;/ig,'');//去掉&nbsp;
						var htmlArr = text.replace(/\\/g, "\\\\").replace(
								/\\/g, "\\/").replace(/\'/g, "\\\'").replace(
								/\"/g, "\\\"").split('\n');
						var textStr = "";
						$.each(htmlArr, function(i, perValue) {
							if (perValue==="") {
							}else{
								textStr = textStr + perValue.trim();
							}

						});

						
						var html = '';
						var index = $("#ul_add_li").children().length+1;
							html += '<li id="li_'+index+'" style="float:left;background:#E5E7EC;margin:5px;height: 24px;line-height: 25px;margin-left:15px;">';
							html += '<em id="em_'+index+'" style="margin-left:10px;"></em>';
							html += '<a href="#" style="margin-top:-0.2em;padding-right:5px;margin-left: 10px;curosr:pointer;" onclick="EditKeyword.delTagLi('+index+')">x</a>';
							html += '<input type="hidden" name="hidden_keyword" id="li_input_'+index+'" value="">';
							html += '</li>';
						if(textStr.length>0){
							if(index < 4){
								$("#ul_add_li").append(html);
								
								$("#em_"+index+"").html(textStr);
								$("#li_input_"+index+"").val(textStr);
								self.html("");
							}else{
								show_msg("包括当前输入的关键词，只能添加4个关键词！");
							}
						}
						

					});
				}
				
				*/
			});


		});

	},
	
	saveBefore:function(){
		
		var text = $("#keywordId").val();
		
		
		text = text.replace(/<\/?[^>]*>/g,''); //去除HTML tag
		text = text.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
		text=text.replace(/&nbsp;/ig,'');//去掉&nbsp;
		var htmlArr = text.replace(/\\/g, "\\\\").replace(
				/\\/g, "\\/").replace(/\'/g, "\\\'").replace(
				/\"/g, "\\\"").split('\n');
		var textStr = "";
		$.each(htmlArr, function(i, perValue) {
			if (perValue==="") {
			}else{
				textStr = textStr + perValue.trim();
			}
		});
		var html = '<input type="hidden" name="hidden_keyword" value="'+textStr+'">';
		
		
		$("#ul_add_li").append(html);
		
	},
	
	
	addKeyword:function(){
		
		var index = $("#index_id").val();
		var key_index = $("#key_index").val();
		var keyword = $("#hidden_keyword").val();
		
		console.log("#keyword_input_"+index+"_"+key_index+"");
//		keyword_1_1
		window.parent.$("#keyword_"+index+"_"+key_index+"").html(keyword);
		if(window.parent.$("#keyword_input_"+index+"_"+key_index+"")){
			window.parent.$("#keyword_input_"+index+"_"+key_index+"").val(keyword);
		}
		
		
		EditKeyword.cancel();
		
	},
}
