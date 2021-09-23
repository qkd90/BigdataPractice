$(document).ready(function() {
	proStyleList();
    proDestination('#pro_destination','.pro');
	proDestination('#pro_daytime','.pro_desDay');
	proDestination('#pro_selection','.pro_desSel');
	proDestination('#pro_order','.pro_desOrd');
	proCancel('#desCity_cancel','.pro_desCity');
	proEnsure('#desCity_ensure','.pro_desCity');
	proCancel('#desDay_cancel','.pro_desDay');
	proEnsure('#desDay_ensure','.pro_desDay');
	proCancel('#desSel_cancel','.pro_desSel');
	proEnsure('#desSel_ensure','.pro_desSel');
	proCancel('#desOrd_cancel','.pro_desOrd');
	proEnsure('#desOrd_ensure','.pro_desOrd');
	proCancel('#desGo_cancel','.pro');
	proEnsure('#desGo_ensure','.pro');
	proEmpty('#desCity_empty','.pro_desCity_check');
	proEmpty('#desDay_empty','.pro_desDay_check');
	proEmpty('#desSel_empty','.pro_desCity_check');
	proEmpty('#desOrd_empty','.pro_desCity_check');
	proEmpty('#desGo_empty','.pro_desCity_check1');
	checkbox();
	hideOthers();

	//province()
	
});
//目的地界面
function proDestination(id,tag){
	var des=$(id);
	    des.click(function(){
			if($('#mainShade')){
				$('#mainShade').remove();
				$('.tank').hide();
				createShade();
			    $(tag).show();
				}else{
			createShade();
			$(tag).show();
				}
			});
	}
//创建遮罩
function createShade(){
	var pro_mainHeight=$('.produc_main').height();
	var parent=$('.produc_main');
	var pro_shade=$('<div></div>');
	    pro_shade.appendTo(parent);
		pro_shade.attr('id','mainShade');
		pro_shade.addClass('produc_shade');
		pro_shade.css({'height':pro_mainHeight});
	}
//取消
function proCancel(id,tag){
	var pro_cancel=$(id);
	    pro_cancel.click(function(){
			$('#mainShade').remove();
			$(tag).hide();
			});
	}
//确性
function proEnsure(id,tag){
	var pro_ensure=$(id);
	    pro_ensure.click(function(){
			$('#mainShade').remove();
			$(tag).hide();
			});
	}
//精品定制，自助游，自驾游，跟团游切换
function proStyleList(){
	var Li=$('.top_list li');
	    Li.click(function(){
			var now=$(this).hasClass('top_list_bg');
			var num=$(this).index();
			var thisContain=$('.produc_contain').eq(num);
			if(now==false){
				Li.removeClass('top_list_bg');
				Li.addClass('top_list_gray');
				$(this).removeClass('top_list_gray');
				$(this).addClass('top_list_bg');
				$('.produc_contain').hide();
				thisContain.show();
				}
			});
	}
//清空Checkbox
function proEmpty(id,checked){
	var pro_empty=$(id);
	    pro_empty.click(function(){
			$(checked).removeAttr('checked');
			});
	}
//底部固定栏隐藏界面层叠出现解决
function hideOthers(){
	var Li=$('produc_under_ul li');
	    Li.click(function(){
			var num=$(this).index();
			var thisTank=$('.tank').eq(num);
				$('.tank').hide();
			    thisTank.show();      
			})
	}
//
function province(){
	var Li=$('.pro_province li');
	    Li.click(function(){
			var num=$(this).index();
			var thismuni=$('.pro_municipality').eq(num);
			    $('.pro_municipality').hide();
				thismuni.show();
			});
	}

function checkbox(){
	$('.pro_desDay_check').click(function(){
		if ($('#oneday').attr('checked')){
			$('.bingo1').addClass('pro_green');
		}
	});
}
	
	