$(document).ready(function() {
    //insuChange();
	forSure('#forsure_1');
	forSure('#forsure_2');
	forSure('#forsure_3');
	forSure('#forsure_4');
	//hideInsurance();
	fareDetail();
});

function insuChange(){
	var insurance=$('#insurance_change');
	var k=0;
	    insurance.click(function(){
			if(k==0){
				$(this).html('保存保险');
				k=1;
				}else{
					$(this).html('更改保险');
					k=0;
					}
			});
	}
function forSure(id){
	var sure=$(id);
	    sure.click(function(){
    var popo=sure.hasClass('for_green');
		if(popo==false){
			sure.removeClass('for_gray');
			sure.addClass('for_green');
			}else{
				sure.removeClass('for_green');
			sure.addClass('for_gray');
				}
			});
	}
function hideInsurance(){
   var sure=$(id);
   var popo=sure.hasClass('for_green');
   if(popo=true){
	   sure.parent().hide();
	   }
	}
//创建遮罩
function createShade(){
	var pro_mainHeight=$('#totalDiv').height();
	var parent=$('#totalDiv');
	var pro_shade=$('<div></div>');
	    pro_shade.appendTo(parent);
		pro_shade.attr('id','mainShade');
		pro_shade.addClass('produc_shade');
		pro_shade.css({'height':pro_mainHeight});
	}
//费用明细
function fareDetail(){
	var fare=$('#fare_detail');
	var k=0;
	    fare.click(function(){
			if(k==0){
				createShade();
				$('.fare_detail').show();
				fare.removeClass('fare_green');
				k=1;
				}else{
				  $('#mainShade').remove();
				  $('.fare_detail').hide();
				  fare.addClass('fare_green');
				  k=0;
					}
			});
	}
	
	
	
	
	
	