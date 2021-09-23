var checkTicket =angular.module('checkTicket', []);

	checkTicket.controller('checkArea',function($scope, $http){
		var top = ($('.stateBox').height() - $('.checkcenter').height())/2;
			$('.checkcenter').css({'marginTop': top - 5});
		$scope.shadow = false;
		$scope.success = false;
		$scope.fail = false;
		//$scope.checked = false;
		$scope.message = "";
		$scope.checkClose = function(){
			$scope.shadow = false;
			$scope.success = false;
			$scope.fail = false;
			$scope.code = "";
			$scope.message = "";
		};
		$scope.code = "";
		$scope.goOnCheck = function() {
			$scope.shadow = false;
			$scope.success = false;
			$scope.fail = false;
			$scope.message = "";
			$scope.code = "";
		}




		$scope.check = function(){
			$scope.shadow = true;
			var codeId = $scope.code;
			if (!codeId) {
				$scope.message = "，无效参数";
				$scope.fail = true;
				$scope.shadow = false;
				return ;
			}

			var reg = /^\d{12}$/;
			if (!reg.test(codeId)) {
				$scope.message = "，请输入12位数字码";
				$scope.fail = true;
				$scope.shadow = false;
				return ;
			}

			$http({
				method:'post',
				url:'/vticket/checkTicket/doCheck.jhtml',
				data:{codeId: $scope.code},
				headers:{'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj){
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					}
					return str.join("&");
				}
			}).success(function(result){
				if (result) {
					if (result.success) {
						$scope.success = true;
					} else {
						$scope.message = "，" + result.errorMsg;
						$scope.fail = true;
					}
				} else {
					$scope.fail = true;
				}
			}).error(function() {
				$scope.fail = true;

			})
		}
	})
	

	checkTicket.directive('screenheight',function(){
		return{
			restrict:'A',
			// scope: {
			// 	x: "@"
			// },
			link:function(scope,element){
				var screenHeight = $(window).height();
				// element.css({'min-height': screenHeight + Number(scope.x) + "px"});
				element.css({'min-height': screenHeight + "px"});
			}
		};
	})
	checkTicket.directive('centerbox',function(){
		return{
			restrict:'A',
			link:function(scope,element){
				var tagLeft = ($(window).width() - $(element).width())/2;
				var tagTop = ($(window).height() - $(element).height())/2;
					element.css({'left':tagLeft + "px",'top':tagTop + "px"});
			}
		};
	})