$(document).ready(function(){
	SailboatCheck.init();
})

var SailboatCheck = {
	button : $('.checkBtn'),
	init: function() {
		SailboatCheck.button.click(function() {
			SailboatCheck.doCheck();
		});
	},
	doCheck: function() {
		$('.true').hide();
		var state = $('.check_return');
		if (!$('.centerBox input').val()) {
			state.addClass('fail');
			state.text('很抱歉，请输入验证码！');
			return;
		}
		$.ajax({
			url: '/yhy/yhySailBoat/doCheck.jhtml',
			type: 'post',
			dataType: 'json',
			progress: true,
			loadingText: '请稍候...',
			data: {
				codeId: $('.centerBox input').val()
			},
			success: function(result) {
				if (result.success) {
					state.addClass('success');
					state.text(result.errorMsg);
					SailboatCheck.getValidateCodeInfo(result.orderDetailId);
					$('.true').show();
				} else {
					if (result.used == 1) {
						state.addClass('checked');
						state.text(result.errorMsg);
						SailboatCheck.getValidateCodeInfo(result.orderDetailId);
						$('.true').show();
					} else {
						state.addClass('fail');
						state.text(result.errorMsg);
					}
				}
			},
			error: function() {
				state.addClass('fail');
				state.text('很抱歉，验证失败！');
			}
		});
	},

	getValidateCodeInfo: function(orderDetailId) {
		var url = '/yhy/yhySailBoat/getValidateCodeInfo.jhtml';
		var data = {
			orderDetailId: orderDetailId
		}
		$.post(url, data, function(result) {
			if (result.success) {
				var totalNum = result.totalNum == null ? 0 : Number(result.totalNum);
				$("#totalNum").html(totalNum);
				var unUsedNum = result.unUsedNum == null ? 0 : Number(result.unUsedNum);
				$("#unUsedNum").html(unUsedNum);
				$("#usedNum").html(totalNum - unUsedNum);
				$(".used_div").children().remove();
				$.each(result.usedValidCodeList, function(i, per) {
					var usedHtml = '';
					usedHtml += '<p>';
					usedHtml += '<span class="index">'+ (i + 1) +'</span>';
					usedHtml += '<span>验证码：</span>';
					usedHtml += '<span class="ticket_num">'+ per.code +'</span>';
					usedHtml += '<span class="ticket_time">验证时间：</span>';
					usedHtml += '<span class="ticket_num">'+ per.updateTime +'</span>';
					usedHtml += '</p>';
					$(".used_div").append(usedHtml);
				});
				$.loadData({
					scopeId: '.check_message',
					data: result
				});
			}
		});
	}
};

/*

function checkState(){
	var button = $('.checkBtn');
		button.click(function(){
			$('.true').hide();
			var state = $('.check_return');
			if($('.centerBox input').val() == 1){
				state.addClass('success');
				state.text('恭喜您，验证成功！');
				$('.true').show();
			} else if($('.centerBox input').val() == 2){
				state.addClass('fail');
				state.text('很抱歉，您的验证失败！');
			} else {
				state.addClass('checked');
				state.text('很抱歉，该验证码已验证！');
				$('.true').show();
			}
		})
}*/
