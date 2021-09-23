/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
	KeyWordReply.init();
});

var KeyWordReply = {

	init : function() {
		KeyWordReply.initTextArea();
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
			content = K.create('#keywordId', {
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

				},
				afterBlur : function() {
					this.sync();
				},
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
							html += '<a href="#" style="margin-top:-0.2em;padding-right:5px;margin-left: 10px;curosr:pointer;" onclick="KeyWordReply.delTagLi('+index+')">x</a>';
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
	
	
	addKeyword:function(index){
//		alert(index);
		KeyWordReply.saveBefore();
		
		var inputList = $("input[name='hidden_keyword']");
		
		var divArr = window.parent.$("#keyword_panel_"+index+"").children();
		var lastIndex = 0;
		if(divArr.length>0){
			lastIndex = $(divArr[divArr.length-1]).attr("name");
			lastIndex = parseInt(lastIndex);
		}
		
		$.each(inputList,function(i,perValue){
			i = i+1;
			i = lastIndex + i;
			console.log("i="+i);
			var value = $(perValue).val();
			if(value.length>0){
				var html = '<div style="height:22px;border-top:1px solid #e7e7eb;padding:15px;" name="'+i+'" id="div_'+index+'_'+(i)+'">';
				html += '<div style="float:left;"><em id="keyword_'+index+'_'+(i)+'" name="em_keyword_'+index+'">';
				html += value;
				html += '</em></div>';
				html += "<div style='margin-left:900px;'>";
				html += "<select id='select_"+index+"_"+(i)+"' onchange='AutoReply.selectPipei("+index+","+(i)+")'>";
				html += "<option value='explicit'>";
				html += "全匹配";
				html += "</option>";
				html += "<option value='implicit'>";
				html += "模糊";
				html += "</option>";
				html += "</select>";
				html += "<input type='hidden' id='select_pi_"+index+"_"+(i)+"' name='selet_pi_"+index+"' value='explicit'>";
				html += "<a href='#' class='easyui-linkbutton' style='margin-left: 20px;' onclick='AutoReply.editKeyword("+index+","+(i)+")' data-options=''>编辑</a>";
				html += "<a href='#' class='easyui-linkbutton' style='margin-left: 20px;' onclick='AutoReply.delKeyword("+index+","+(i)+")' data-options=''>删除</a>";
				html += '</div></div>';
				window.parent.$("#keyword_panel_"+index+"").append(html);
				window.parent.$("#keyword_"+index+"_"+(i)+"").after('<input type="hidden" id="keyword_input_'+index+'_'+(i)+'" name="keyword_'+index+'" value="'+value+'">');
			}
			
		});
		
		KeyWordReply.cancel();
		
	},
}
