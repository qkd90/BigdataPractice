"use strict";
// 请每人写一行
var routerApp = angular.module('lxbApp', ['angularLocalStorage', 'ngCookies', 'ui.router'
    , 'mIndexModule', 'mGroupTourIndexModule', 'mDriveTourIndexModule', 'mSelfTourIndexModule'
    , 'mCustomTourIndexModule', 'mAroundTourIndexModule', 'mInternalTourIndexModule', 'mOverseasTourIndexModule'
    , 'mTicketIndexModule', 'mHotelIndexModule', 'mTicketNearModule'
    , 'myTeamModule', 'scenicDetailModule', 'hotelDetailModule'
    , "orderDateModule", "orderOneModule", "orderTwoModule", "touristListModule", "editTouristModult", "orderPayModule", "orderDetailModule", "orderTicketDateModule", "orderTicketModule", "ticketTouristListModule", "ticketOrderDetailModule", "orderHotelModule", "orderHotelGuaranteeModule", "hotelOrderDetailModule", "orderLineGuaranteeModule", "orderSceneryDateModule"
    ,'lineDetailModule', "lineDayModule", "lineFareDetailModule", "lineBookingModule"
    ,'lineListModule'
    ,'searchModule'
    ,'xingcityModule'
    //,'xingcitysearchModule'
    ,'scenicListModule'
    ,'HotelListModule'
    ,'sceneryDetailModule'
]);

//var myInjector = angular.injector(["lxbApp","ng"]);
//var rootScope = myInjector.get("$rootScope");
//alert(rootScope);
/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param $rootScope {[type]}
 * @param $state {[type]}
 * @param $stateParams {[type]}
 */
routerApp.run(function ($rootScope, $state, $stateParams, $cookieStore) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
        $rootScope.previousState = from;
        $rootScope.previousParams = fromParams;
        if ($rootScope.previousParams.name && $rootScope.previousParams.name.length > 0) {
            $cookieStore.put('url', $rootScope.previousParams.name);
        }
        //alert($rootScope.previousState + ' ' + $rootScope.previousParams);
    });
});
//post支持
routerApp.config(function ($httpProvider) {
	$httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function (data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
        var param = function (obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;
            for (name in obj) {
                value = obj[name];
                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                        + encodeURIComponent(value) + '&';
                }
            }
            return query.length ? query.substr(0, query.length - 1) : query;
        };
        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
    }];
});
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param $stateProvider {[type]}
 * @param $urlRouterProvider {[type]}
 */
routerApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/index');
    $stateProvider
        //参数对象即可放json串 也可以放普通的对象
        //.state('payment', {//支付
        //    url: '/payment/{order}',
        //    templateUrl:  'tpls/payment.html'
        //})
        //.state('test', {//测试
        //    url: '/test',
        //    templateUrl:  'tpls/test.html'
        //});
        .state('index', { // 新app首页
            url: '/index',
            templateUrl: 'lxb/lxbtpls/index.html'
        })
        .state('tour/group/index', { // 新app跟团游首页
            url: '/tour/group/index',
            templateUrl: 'lxb/lxbtpls/group_tour_index.html'
        })
        .state('tour/drive/index', { // 新app自驾游首页
            url: '/tour/drive/index',
            templateUrl: 'lxb/lxbtpls/drive_tour_index.html'
        })
        .state('tour/self/index', { // 新app自助游首页
            url: '/tour/self/index',
            templateUrl: 'lxb/lxbtpls/self_tour_index.html'
        })
        .state('tour/custom/index', { //新app定制精品首页
            url: '/tour/custom/index',
            templateUrl: 'lxb/lxbtpls/custom_tour_index.html'
        })
        .state('tour/around/index', { // 新app国内游首页
            url: '/tour/around/index',
            templateUrl: 'lxb/lxbtpls/around_tour_index.html'
        })
        .state('tour/internal/index', { // 新app国内游首页
            url: '/tour/internal/index',
            templateUrl: 'lxb/lxbtpls/internal_tour_index.html'
        })
        .state('tour/overseas/index', { // 新app出境游首页
            url: '/tour/overseas/index',
            templateUrl: 'lxb/lxbtpls/overseas_tour_index.html'
        })
        .state('ticket/index', { // 新app门票首页
            url: '/ticket/index',
            templateUrl: 'lxb/lxbtpls/ticket_index.html'
        })
        .state('ticket/near', { // 新app我的附近景点门票
            url: '/ticket/near',
            templateUrl: 'lxb/lxbtpls/ticket_near.html'
        })
        .state('hotel/index', { // 新app酒店首页
            url: '/hotel/index',
            templateUrl: 'lxb/lxbtpls/hotel_index.html'
        })
        .state('myteam', {  // 我的团队
            url: '/myteam',
            templateUrl: 'lxb/lxbtpls/myTeam.html'
        })
        .state('distributeshare', {  // 分销分享链接
            url: '/distributeshare/{pid}',
            templateUrl: 'lxb/lxbtpls/distributeShare.html'
        })
        .state('scenicDetail', { // 景点门票详情
            url: '/scenicDetail/{scenicId}',
            templateUrl: 'lxb/lxbtpls/scenicDetail.html'
        })
        .state('scenicOrderKnow', { // 景点预订须知
            url: '/scenicOrderKnow/{scenicId}',
            templateUrl: 'lxb/lxbtpls/scenicOrderKnow.html'
        })
        .state('scenicTrafficGuide', { // 景点交通信息
            url: '/scenicTrafficGuide/{scenicId}',
            templateUrl: 'lxb/lxbtpls/scenicTrafficGuide.html'
        })
        .state('hotelDetail', { // 酒店详情
            url: '/hotelDetail/{hotelId}',
            templateUrl: 'lxb/lxbtpls/hotelDetail.html'
        })
        .state('hotelDetailRoomFilter', { // 酒店详情房型条件选择
            url: '/hotelDetailRoomFilter',
            templateUrl: 'lxb/lxbtpls/hotelDetailRoomFilter.html'
        })
        .state('hotelDateFilter', { // 酒店详情入住离店时间选择
            url: '/hotelDateFilter',
            templateUrl: 'lxb/lxbtpls/hotelDateFilter.html'
        })
        .state('hotelComments', { // 酒店评论
            url: '/hotelComments/{hotelId}',
            templateUrl: 'lxb/lxbtpls/hotelComments.html'
        })
        .state('orderone', { //选择资源
            url: '/orderone/{request}',
            templateUrl: 'lxb/lxbtpls/orderOne.html'
        })
        .state('ordertwo', { //填写订单
            url: '/ordertwo',
            templateUrl: 'lxb/lxbtpls/orderTwo.html'
        })
        .state('orderLineGuarantee', { //线路担保
            url: '/orderLineGuarantee',
            templateUrl: 'lxb/lxbtpls/orderLineGuarantee.html'
        })
        .state('touristlist', { //旅客列表
            url: '/touristlist',
            templateUrl: 'lxb/lxbtpls/touristList.html'
        })
        .state('edittourist', { //编辑旅客信息
            url: '/edittourist/{tourist}',
            templateUrl: 'lxb/lxbtpls/editTourist.html'
        })
        .state('orderbooking', { //订单预订须知
            url: '/orderbooking',
            templateUrl: 'lxb/lxbtpls/orderBooking.html'
        })
        .state('orderlimitpeaple', { //订单出游人限制
            url: '/orderlimitpeaple',
            templateUrl: 'lxb/lxbtpls/orderLimitPeaple.html'
        })
        .state('ordercontract', { //订单合同
            url: '/ordercontract',
            templateUrl: 'lxb/lxbtpls/orderContract.html'
        })
        .state('orderpay', { //支付
            url: '/orderpay/{orderId}',
            templateUrl: 'lxb/lxbtpls/orderPay.html'
        })
        .state('orderdetail', { //订单详情
            url: '/orderdetail/{orderId}',
            templateUrl: 'lxb/lxbtpls/orderDetail.html'
        })
        .state('lineDetail', {  // 线路详情
            url: '/lineDetail/{id}',
            templateUrl: 'lxb/lxbtpls/lineDetail.html'
        })
        .state("lineDay", {
            url: '/lineDay/{lineId}',
            templateUrl: 'lxb/lxbtpls/lineDay.html'
        })
        .state('lineFareDetail', {  // 线路详情费用说明
            url: '/lineFareDetail/{lineTypePriceId}',
            templateUrl: 'lxb/lxbtpls/lineFareDetail.html'
        })
        .state('lineBooking', {  // 线路详情预订须知
            url: '/lineBooking/{lineId}',
            templateUrl: 'lxb/lxbtpls/lineBooking.html'
        })
        .state('orderDate', {  // 线路下单选择日期
            url: '/orderDate/{lineTypePriceId}',
            templateUrl: 'lxb/lxbtpls/orderDate.html'
        })
        .state('lineList', {  // 线路列表
            url: '/lineList/{params}',
            templateUrl: 'lxb/lxbtpls/lineList.html'
        })
        //.state('lineSearch', { // 线路查询页
        //    url: '/lineSearch',
        //    templateUrl: 'lxb/lxbtpls/seaching.html'
        //})
        .state('selectCity', { // 线路查询页
            url: '/selectCity',
            templateUrl: 'lxb/lxbtpls/xing-city.html'
        })
        .state('xing-city-search', {//选择城市--搜索
            url: '/xing-city-search',
            templateUrl:  'lxb/lxbtpls/xing-city-search.html'
        }).state('scenicList', {// 景点列表
            url: '/scenicList{params}',
            templateUrl:  'lxb/lxbtpls/scenicList.html'
        })
        .state('orderTicketDate', {//门票下单选择日期
            url: '/orderTicketDate/{ticketPriceId}',
            templateUrl:  'lxb/lxbtpls/orderTicketDate.html'
        })
        .state('orderTicket', {//门票下单
            url: '/orderTicket',
            templateUrl:  'lxb/lxbtpls/orderTicket.html'
        })
        .state('ticketTouristList', {//门票下单
            url: '/ticketTouristList',
            templateUrl: 'lxb/lxbtpls/ticketTouristList.html'
        })
        .state('ticketOrderDetail', {//门票订单详情
            url: '/ticketOrderDetail/{orderId}',
            templateUrl:  'lxb/lxbtpls/ticketOrderDetail.html'
        })
        .state('orderHotel', {//酒店下单
            url: '/orderHotel/{params}',
            templateUrl: 'lxb/lxbtpls/orderHotel.html'
        })
        .state('orderHotelGuarantee', {//酒店下单担保
            url: '/orderHotelGuarantee',
            templateUrl: 'lxb/lxbtpls/orderHotelGuarantee.html'
        })
        .state('hotelOrderDetail', {//酒店订单详情
            url: '/hotelOrderDetail/{orderId}',
            templateUrl: 'lxb/lxbtpls/hotelOrderDetail.html'
        })
        .state('search', { // 线路查询页
            url: '/search',
            templateUrl: 'lxb/lxbtpls/seaching.html'
        })
        .state('hotelList', {//酒店订单详情
            url: '/hotelList',
            templateUrl: 'lxb/lxbtpls/hotelList.html'
        })
        .state('sceneryDetail', { // 游艇帆船详情
            url: '/sceneryDetail/{sceneryId}',
            templateUrl: 'lxb/lxbtpls/sceneryDetail.html'
        })
        .state('sceneryDetailMap', { // 游艇帆船详情地图
            url: '/sceneryDetailMap/{sceneryId}',
            templateUrl: 'lxb/lxbtpls/sceneryDetailMap.html'
        })
        .state('orderSceneryDate', {//游艇帆船下单选择日期
            url: '/orderSceneryDate/{ticketPriceId}',
            templateUrl:  'lxb/lxbtpls/orderTicketDate.html'
        })
    ;
});


