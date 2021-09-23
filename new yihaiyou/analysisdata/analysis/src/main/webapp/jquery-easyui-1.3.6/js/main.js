var Data = {
		
		loadCustomerTree : function(){
			$('#tt').tree({
			    url: '/crm/frm/loadCustomerTree.jhtml'
			});
		},
		bindCustomerAttrEvent : function(){
			$("#aa .item div").click(function(){
				var name = $(this).attr('name');
				var txt = $(this).text();
				var type = $(this).attr('type');
				Data.mainAttrHtml(name,type,txt);
			});
		},
		mainAttrHtml : function(name,type,txt){
			var select = $("#conditions").find('select[name="select'+name+'"]');
			var contain = select.size() > 0?true:false;
			var html = '';
			var appended;
			if(!contain){
				html += '<div name='+name+' type='+type+'>';
				html += txt + ':';
				html += $("#"+type+"Template").render({name : name,contain : contain});
				html += '</div>';
				appended = $("#conditions").append(html);
			}else{
				html += $("#"+type+"Template").render({name : name,contain : contain});
				appended = select.parent().parent().append(html);
			}
			$("#conditions").find('select[name^="select"]').unbind().change(function(){
				var txt = $(this).find('option:selected').text();
				if(type != 'date'){
					if(txt != '区间'){
						$(this).next().next().hide();
						$(this).next().show();
					}else{
						$(this).next().next().show();
						$(this).next().hide();
					}
				}else{
					if(txt != '区间'){
						$(this).next().next().next().hide();
						$(this).next().next().show();
					}else{
						$(this).next().next().next().show();
						$(this).next().next().hide();
					}
				}
			});
			$("#conditions div[type='date'] input").each(function(){
				if($(this).attr('class') == undefined){
					$(this).datebox();
				}
			});
			$("#conditions .itemClose").unbind().click(function(){
				if($(this).parent().parent().find('select').size() == 1){
					$(this).parent().parent().remove();
				}else{
					$(this).parent().remove();
				}
			});
			
		},
		getParams : function(){
			var divs = $("#conditions").children();
			var params = new Array();
			for(var i = 0;i < divs.length;i++){
				var name = $(divs[i]).attr('name');
				var type = $(divs[i]).attr('type');
				var param = new Array();
				var spans = $(divs[i]).children('span');
				for(var j = 0;j < spans.length;j++){
					var span = $(spans[j]);
					var select1 = null;
					var select2 = null;
					if(span.find('select').size() > 1){
						select1 = $(span.find('select')[0]).find('option:selected').val();
						select2 = $(span.find('select')[1]).find('option:selected').val();
					}else{
						select1 = $(span.find('select')[0]).find('option:selected').val();
					}
					var ope = select2 == null?select1:select2;
					var con = select2 == null?'':select1;
					var val1 = '';
					var val2 = '';
					if(ope != '区间'){
						if(type != 'date'){
							val1 = $(span.children()[1]).val();
						}else{
							val1 = $(span.children()[1]).datebox('getValue');
						}
					}else{
						if(type != 'date'){
							val1 = $($(span.children('span > span')[0]).children()[0]).val();
							val2 = $($(span.children('span > span')[0]).children()[1]).val();
						}else{
							val1 = $($(span.children('span > span')[1]).children()[0]).datebox('getValue');
							val2 = $($(span.children('span > span')[1]).children()[2]).datebox('getValue');
						}
						
					}
					params.push([name,type,con,ope,val1,val2]);
				}
			}
			return params;
		},
		saveCustomerGroup : function(){
			$("#btnSaveGroup").click(function(){
				var paramstr = Data.getParamsStr();
				console.info(paramstr);
			});
		},
		getParamsStr : function(){
			var params = Data.getParams();
			var args = '';
			for(var i in params) {
	    		if(params[i].length < 2) break;
	    		var str = '';
	    		for(var index = 1;index < params[i].length;index++){
	    			str += str.length > 0? "," + encodeURIComponent(params[i][index]):encodeURIComponent(params[i][index]);
	    		}
	    		args += "&"+params[i][0] + '=' + str;
	    	}  
			return args;
		},
		bindAnalysisPlayform : function(){
			$("#btnAnalysis").click(function(){
				var paramstr = Data.getParamsStr();
				$("#cpf").attr('src','/crm/frm/analysisCustomer.jhtml?' + paramstr);
				var win = $("#analysisPlayform").window({
					fit : true,
					modal:true,
					title : '客户数据分析操作平台'
				});
				win.window('open');
			});
		},
		init : function(){
			this.loadCustomerTree();
			this.bindCustomerAttrEvent();
			this.saveCustomerGroup();
			this.bindAnalysisPlayform();
//			this.btnStatisticNum();
		}
};
$(function(){
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y +'-' + m + '-' + d;
	}
	Data.init();
});
