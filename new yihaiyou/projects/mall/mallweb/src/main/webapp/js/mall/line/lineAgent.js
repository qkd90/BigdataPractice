var lineAgent = {
    
    init: function() {
    	// 获取最小报价时间（成人分销价）
    	$('.minDiscountPrice').each(function(index, element) {
			$.post("/mall/line/findMinValue.jhtml", {linetypepriceId: $(element).attr('linetypepriceId'), prop:'discountPrice'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});

    	// 获取最小报价时间（成人佣金）
    	$('.minRatePrice').each(function(index, element) {
			$.post("/mall/line/findMinValue.jhtml", {linetypepriceId: $(element).attr('linetypepriceId'), prop:'rebate'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});
    },
    // 分销
    doAgent: function () {
    	var productId = $('#productId').val();
    	var lineStatus = $('input[name="lineStatus"]:checked').val();
    	var lineName = $('#lineName').val();
    	if (!lineName) {
    		alert('产品名称不能为空');
    	}
    	$.post("/mall/line/doAgent.jhtml", {productId: productId,lineStatus: lineStatus, lineName:lineName},
			function(data){
	    		if(data && data.success==true){
	    			$('.ok').modal('show');
				}else{
					if (data && data.errorMsg) {
						alert(data.errorMsg);
					} else {
						alert("分销失败");
					}
				}
			}
		);
    },
    // 返回
    doBack: function () {
        //window.location = "/shopcart/shopcart/orderjhtml?id=" + this.lineId + "&proType=line";
    },
    // 分销后确认
    doOk: function () {
        //window.location = "/shopcart/shopcart/orderjhtml?id=" + this.lineId + "&proType=line";
    	window.location = "/";
    },
    // 分销后取消
    doCancel: function () {
		$('.ok').modal('hide');
    }

};
$(function(){
	lineAgent.init();
});
