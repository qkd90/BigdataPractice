var TicketAgent = {
    
    init: function() {
    	// 获取最小报价时间（成人分销价）
    	$('.minDiscountPrice').each(function(index, element) {
			$.post("/mall/ticket/findMinValue.jhtml", {tickettypepriceId: $(element).attr('tickettypepriceId'), prop:'priPrice'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});

    	// 获取最小报价时间（成人佣金）
    	$('.minCommission').each(function(index, element) {
			$.post("/mall/ticket/findMinValue.jhtml", {tickettypepriceId: $(element).attr('tickettypepriceId'), prop:'rebate'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});
    },
    // 分销
    doAgent: function () {
    	var productId = $('#productId').val();
    	var ticketStatus = $('input[name="ticketStatus"]:checked').val();
    	var ticketName = $('#ticketName').val();
    	if (!ticketName) {
    		alert('产品名称不能为空');
    	}
    	$.post("/mall/ticket/doAgent.jhtml", {productId: productId,ticketStatus: ticketStatus, ticketName:ticketName},
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
    	window.location = "/mall/ticket/list.jhtml";
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
	TicketAgent.init();
});
