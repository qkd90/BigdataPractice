///**
// * Created by Administrator on 2017/7/31.
// */
//var newCruiseDetailModule = angular.module('newCruiseDetailModule',[]);
///*轮播指令*/
//newCruiseDetailModule.directive('swiper',function($timeout){
//    return {
//        restrict: "EA",
//        scope: {
//            data:"="
//        },
//        template: '<div class="swiper-container silder" id="newCruiseBanner">'+
//        '<ul class="swiper-wrapper">'+
//        '<li class="swiper-slide" ng-repeat="item in data">'+
//        '<a class="img40" href="{{item.contentUrl}}" rel="external nofollow" ><img ng-src="{{item.imgId}}" alt="" /></a>'+
//        '</li>'+
//        '</ul>'+
//        '<div class="swiper-pagination"></div>'+
//        '</div>',
//        link: function(scope, element, attrs) {
//            $timeout(function(){
//                var swiper = new Swiper('.swiper-container', { //轮播图绑定样式名
//                    pagination: '.swiper-pagination',
//                    paginationClickable: true,
//                    autoplay: 2500,
//                    loop : true,
//                });
//            },100);
//        }
//    };
//});
//newCruiseDetailModule.controller('NewCruiseDetailController',function($scope){
//    /*数据*/
//    $scope.data = {
//        /*舱房类型数据*/
//        cabinClassify:[
//            {id:1,cabinListStatus:true,cabinName:"内舱房",roomList:[{roomId:1,roomName:"随机内舱房",roomPopStatus:false},{roomId:2,roomName:"随机内地铺",roomPopStatus:false}]},
//            {id:2,cabinListStatus:false,cabinName:"海景房",roomList:[{roomId:1,roomName:"随机海景房",roomPopStatus:false},{roomId:2,roomName:"海景地铺",roomPopStatus:false}]},
//            {id:3,cabinListStatus:false,cabinName:"高级舱房",roomList:[{roomId:1,roomName:"随机高级舱房",roomPopStatus:false},{roomId:2,roomName:"高档地铺",roomPopStatus:false}]}
//        ],
//        bannerImgList:[
//            {id:1,imgUrl:'../image/banner01.png'},
//            {id:2,imgUrl:'../image/banner02.png'},
//            {id:3,imgUrl:'../image/banner03.png'}
//        ]
//    };
//    /*方法*/
//    $scope.method = {
//        /*舱房列表  展开*/
//        listSwitch:function(id){
//            angular.forEach($scope.data.cabinClassify,function(item){
//                if(item.id == id){
//                    if(item.cabinListStatus){
//                        item.cabinListStatus = false;
//                    }else{
//                        item.cabinListStatus = true;
//                    }
//                }
//            });
//        },
//        /*查看舱房详情  弹窗*/
//        popSwitch:function(id){
//            angular.forEach($scope.data.cabinClassify,function(item){
//                angular.forEach(item.roomList,function(single){
//                    if(single.id == id){
//                        if(single.roomPopStatus){
//                            single.roomPopStatus = false;
//                        }else{
//                            single.roomPopStatus = true;
//                        }
//                    }
//                });
//            });
//        },
//        /**/
//    };
//    console.log($scope.data);
//});