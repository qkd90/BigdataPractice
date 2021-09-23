/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
var yhyapp = angular.module("yhyapp", ['angularLocalStorage', 'ngCookies', 'ui.router',
    "bk_car",
    "youlun",
    "orderDateModule", "orderOneModule", "orderTwoModule", "touristListModule", "editTouristModult", "orderPayModule", "orderDetailModule", "orderTicketDateModule", "orderTicketModule", "ticketTouristListModule", "ticketOrderDetailModule", "orderHotelModule", "orderHotelGuaranteeModule", "hotelOrderDetailModule", "orderLineGuaranteeModule", "orderSailboatYachtModule", "saillingOrderDetailModule",
    "personalCenter",
    'mTicketIndexModule',
    'mTicketNearModule',
    'scenicListModule',
    'mHotelIndexModule', 'HotelListModule', 'hotelDetailModule',
    'indexModule',
    'searchModule','xingcityModule',
    'scenicDetailModule',
    'scenicDetailModule',
    'searchModule',
    'ferryModule',
    "planModule",
    "saillingModule",
    "guideModule",
    "foodModule",
    "ihaitaiModule",
    "feedBackModule",
   // "cruiseListSortModule",
    "cruiseShip",
    "cruiseListSortModule",
    "cruiseEditorOrderModule",
    //"newCruiseDetailModule",
    "LvjiModule"

]);
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function (fmt) { //author: meizz
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

//post支持
yhyapp.config(function ($httpProvider) {
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

var QINIU_BUCKET_URL;
yhyapp.config(function ($stateProvider, $urlRouterProvider) {
    $.ajax({
        url: "/yihaiyou/index/qiniu.jhtml",
        async: false,
        success : function(data){
            QINIU_BUCKET_URL = data;
        }
    });
    $urlRouterProvider.otherwise('/index');
    $stateProvider.state('index', {
        url: '/index',
        templateUrl: 'yihaiyou/tpls/index.html'
    })
        .state('bookingCar', {
            url: '/bookingCar',
            templateUrl: 'yihaiyou/tpls/bookingCar.html'
        })
        .state('shangche', {
            url: '/shangche',
            templateUrl: 'yihaiyou/tpls/shangche.html'
        })
        .state('xiache', {
            url: '/xiache',
            templateUrl: 'yihaiyou/tpls/xiache.html'
        })
        .state('payment', {
            url: '/payment/{orderId}',
            templateUrl: 'yihaiyou/tpls/payment.html'
        })
        .state('payment2', {
            url: '/payment2',
            templateUrl: 'yihaiyou/tpls/payment2.html'
        })
        //.state("login", {
        //    url: '/login',
        //    templateUrl: 'yihaiyou/tpls/login.html'
        //})
        .state('search', { // 线路查询页
            url: '/search',
            templateUrl: 'yihaiyou/tpls/seaching.html'
        })
        .state('selectCity', { // 线路查询页
            url: '/selectCity',
            templateUrl: 'yihaiyou/tpls/xing-city.html'
        })
        .state("cruiseShipIndex", {     //邮轮首页
            url: "/cruiseShipIndex",
            templateUrl: "yihaiyou/tpls/newCruiseList.html"
        })
        .state("cruiseShipSearch", {     //邮轮搜索
            url: "/cruiseShipSearch",
            templateUrl: "yihaiyou/tpls/cruiseShipSearch.html"
        })
        .state("orderMustKnow", {    //预定须知
            url: "/orderMustKnow/{dateId}",
            templateUrl: "yihaiyou/tpls/newMustKnow.html"
        })
        .state("cruiseShipRouteDetail", {    //路线详情
            url: "/cruiseShipRouteDetail/{dateId}",
            templateUrl: "yihaiyou/tpls/courseDetails.html"
        })
        .state("editOrder", {    //订单填写
            url: "/editOrder/{dateId}",
            templateUrl: "yihaiyou/tpls/cruiseEditOrder.html"
        })
        .state("cruiseShipList", {    //邮轮列表
            url: "/cruiseShipList/",
            templateUrl: "yihaiyou/tpls/newCruiseList.html"
        })
        .state("shipList", {    //邮轮列表
            url: "/shipList/",
            templateUrl: "yihaiyou/tpls/cruiseListSort.html"
        })
        .state("cruiseDetails", {    //邮轮详情
            url: "/cruiseDetails/{dateId}",
            templateUrl: "yihaiyou/tpls/newCruiseDetail.html"
        })
        .state("orderCruiseShip", {     //邮轮下单
            url: "/orderCruiseShip/{roomType}",
            templateUrl: "yihaiyou/tpls/orderCruiseShip.html"
        })
        .state("cruiseShipDate", {      //邮轮选择日期
            url: "/cruiseShipDate/{shipId}",
            templateUrl: "yihaiyou/tpls/cruiseShipDate.html"
        })
        .state("cruiseShipOrderDetail", {      //邮轮订单详情
            url: "/cruiseShipOrderDetail/{orderId}",
            templateUrl: "yihaiyou/tpls/cruiseShipOrderDetail.html"
        })
        .state('ticket/index', { // 景点首页
            url: '/ticket/index',
            templateUrl: 'yihaiyou/tpls/ticket_index.html'
        })
        .state('ticket/near', { // 新app我的附近景点门票
            url: '/ticket/near',
            templateUrl: 'yihaiyou/tpls/ticket_near.html'
        })
        .state('scenicList', {// 景点列表
            url: '/scenicList{params}',
            templateUrl: 'yihaiyou/tpls/scenicList.html'
        })
        .state('scenicDetail', { // 景点门票详情
            url: '/scenicDetail/{scenicId}',
            templateUrl: 'yihaiyou/tpls/scenicDetail.html'
        })
        .state('scenicOrderKnow', { // 景点预订须知
            url: '/scenicOrderKnow/{scenicId}',
            templateUrl: 'yihaiyou/tpls/scenicOrderKnow.html'
        })
        .state('scenicTrafficGuide', { // 景点交通信息
            url: '/scenicTrafficGuide/{scenicId}',
            templateUrl: 'yihaiyou/tpls/scenicTrafficGuide.html'
        })
        .state('hotel/index',{ // 酒店民宿首页
            url: '/hotel/index',
            templateUrl: 'yihaiyou/tpls/hotel_index.html'
        })
        .state('hotelList', {//酒店民宿列表页
            url: '/hotelList',
            templateUrl: 'yihaiyou/tpls/hotelList.html'
        })
        .state('hotelDetail', { // 酒店民宿详情页
            url: '/hotelDetail/{hotelId}',
            templateUrl: 'yihaiyou/tpls/hotelDetail.html'
        })
        .state('hotelDetailRoomFilter', { // 酒店详情房型条件选择
            url: '/hotelDetailRoomFilter',
            templateUrl: 'yihaiyou/tpls/hotelDetailRoomFilter.html'
        })
        .state('hotelDateFilter', { // 酒店详情入住离店时间选择
            url: '/hotelDateFilter',
            templateUrl: 'yihaiyou/tpls/hotelDateFilter.html'
        })
        .state('hotelComments', { // 酒店评论
            url: '/hotelComments/{hotelId}',
            templateUrl: 'yihaiyou/tpls/hotelComments.html'
        })
        .state('orderHotel', {//酒店下单
            url: '/orderHotel/{params}',
            templateUrl: 'yihaiyou/tpls/orderHotel.html'
        })
        .state('orderHotelGuarantee', {//酒店下单担保
            url: '/orderHotelGuarantee',
            templateUrl: 'yihaiyou/tpls/orderHotelGuarantee.html'
        })
        .state('hotelOrderDetail', {//酒店订单详情
            url: '/hotelOrderDetail/{orderId}',
            templateUrl: 'yihaiyou/tpls/hotelOrderDetail.html'
        })
        .state("sailboat/list", {   //海上休闲列表
            url: "/sailboat/list/{index}",
            templateUrl: "yihaiyou/tpls/sailing_list.html"
        })
        .state("sailling/index", {  //海上休闲首页
            url: "/sailling/index",
            templateUrl: "yihaiyou/tpls/sailling_index.html"
        })
        .state("sailling/detail", {   //海上休闲详情
            url: "/sailling/detail/{sailId}",
            templateUrl: "yihaiyou/tpls/sailling_detail.html"
        })
        .state("sailling/ticketDetail", {   //海上休闲详情
            url: "/sailling/ticketDetail/{priceId}",
            templateUrl: "yihaiyou/tpls/sailling_ticket_detail.html"
        })
        .state("sailling/feature", {   //海上休闲景点特色
            url: "/sailling/feature/{ticketId}",
            templateUrl: "yihaiyou/tpls/sailling_scenic_feature.html"
        })
        .state("sailling/date", {   //海上休闲价格日历
            url: "/sailling/date/{ticketPriceId}",
            templateUrl: "yihaiyou/tpls/sailling_date.html"
        })
        .state("sailling/order", {  //海上休闲订单
            url: "/sailling/order",
            templateUrl: "yihaiyou/tpls/sailling_order.html"
        })
        .state("sailling/orderDetail", {  //海上休闲订单
            url: "/sailling/orderDetail/{orderId}",
            templateUrl: "yihaiyou/tpls/sailling_order_detail.html"
        })
        .state("sailling/saillingCommend", {  //海上休闲评论
            url: "/sailling/saillingCommend/{ticketPriceId}",
            templateUrl: "yihaiyou/tpls/sailling_commend.html"
        })
        .state("guide/index", {  //游记攻略首页
            url: "/guide/index",
            templateUrl: "yihaiyou/tpls/guide_index.html"
        })
        .state("guide/detail", {  //游记攻略详情
            url: "/guide/detail/{recommendPlanId}",
            templateUrl: "yihaiyou/tpls/guide_detail.html"
        })
        .state("guide/comment", {  //游记攻略评论
            url: "/guide/comment/{recommendPlanId}",
            templateUrl: "yihaiyou/tpls/guide_comment.html"
        })
        .state('orderTicketDate', {//门票下单选择日期
            url: '/orderTicketDate/{ticketPriceId}',
            templateUrl: 'yihaiyou/tpls/orderTicketDate.html'
        })
        .state('orderTicket', {//门票下单
            url: '/orderTicket',
            templateUrl: 'yihaiyou/tpls/orderTicket.html'
        })
        .state('ticketTouristList', {//门票下单
            url: '/ticketTouristList',
            templateUrl: 'yihaiyou/tpls/ticketTouristList.html'
        })
        .state('ticketOrderDetail', {//门票订单详情
            url: '/ticketOrderDetail/{orderId}',
            templateUrl: 'yihaiyou/tpls/ticketOrderDetail.html'
        })
        .state('ferryList', { //轮渡船票查询结果列表
            url: '/ferryList',
            templateUrl: 'yihaiyou/tpls/ferry_list.html'
        })
        .state('ferryVisitor', { //轮渡船票游客选择
            url: '/ferryVisitor',
            templateUrl: 'yihaiyou/tpls/ferry_visitor.html'
        })
        .state('ferryVisitorEditor', { //轮渡船票游客新增
            url: '/ferryVisitorEditor',
            templateUrl: 'yihaiyou/tpls/ferry_visitor_editor.html'
        })
        .state('ferryOrder', { //轮渡船票订单
            url: '/ferryOrder',
            templateUrl: 'yihaiyou/tpls/ferry_order.html'
        })
        .state('ferryOrderKnow', { //轮渡船票订单购票须知
            url: '/ferryOrderKnow',
            templateUrl: 'yihaiyou/tpls/ferry_order_know.html'
        })
        .state('ferrySearch', { //轮渡船票查询
            url: '/ferrySearch',
            templateUrl: 'yihaiyou/tpls/ferry_search.html'
        })
        .state('ferryLogin', { //轮渡船票账号登录
            url: '/ferryLogin',
            templateUrl: 'yihaiyou/tpls/ferry_login.html'
        })
        .state('ferryRegister', { //轮渡船票账号注册
            url: '/ferryRegister',
            templateUrl: 'yihaiyou/tpls/ferry_register.html'
        })
        .state('ferryRegisterBack', { //轮渡船票账号注册返回
            url: '/ferryRegisterBack',
            templateUrl: 'yihaiyou/tpls/ferry_register_back.html'
        })
        .state('ferryRealname', { //轮渡船票实名信息
            url: '/ferryRealname',
            templateUrl: 'yihaiyou/tpls/ferry_realname.html'
        })
        .state('ferryDoRealname', { //轮渡船票实名信息
            url: '/ferryDoRealname',
            templateUrl: 'yihaiyou/tpls/ferry_do_realname.html'
        })
        .state('ferryOrderDetail', { //轮渡船票订单详情
            url: '/ferryOrderDetail/{orderId}',
            templateUrl: 'yihaiyou/tpls/ferry_order_detail.html'
        })
        .state('personalIndex', {//我的海游
            url: '/personalIndex',
            templateUrl: 'yihaiyou/tpls/personalIndex.html'
        })
        .state('personalInfo', {//我的信息
            url: '/personalInfo',
            templateUrl: 'yihaiyou/tpls/personalInfo.html'
        })
        .state('personalChangeName', {//修改昵称
            url: '/personalChangeName',
            templateUrl: 'yihaiyou/tpls/personalChangeName.html'
        })
        .state('personalChangeTrueName', {//修改姓名
            url: '/personalChangeTrueName',
            templateUrl: 'yihaiyou/tpls/personalChangeTrueName.html'
        })
        .state('personalChangeSex', {//修改性别
            url: '/personalChangeSex',
            templateUrl: 'yihaiyou/tpls/personalChangeSex.html'
        })
        .state('personalChangeEmail', {//修改邮箱
            url: '/personalChangeEmail',
            templateUrl: 'yihaiyou/tpls/personalChangeEmail.html'
        })
        .state('personalRecord', {//消费记录
            url: '/personalRecord',
            templateUrl: 'yihaiyou/tpls/personalRecord.html'
        })
        .state('personalChangePhone', {//修改手机
            url: '/personalChangePhone',
            templateUrl: 'yihaiyou/tpls/personalChangePhone.html'
        })
        .state('personalChangeIdNumber', {//修改身份证
            url: '/personalChangeIdNumber',
            templateUrl: 'yihaiyou/tpls/personalChangeIdNumber.html'
        })
        .state('personalChangeBankNo', {//修改银行卡
            url: '/personalChangeBankNo',
            templateUrl: 'yihaiyou/tpls/personalChangeBankNo.html'
        })
        .state('personalChangePassword', {//修改密码
            url: '/personalChangePassword',
            templateUrl: 'yihaiyou/tpls/personalChangePassword.html'
        })
        .state('personalChangePayPassword', {//修改支付密码
            url: '/personalChangePayPassword',
            templateUrl: 'yihaiyou/tpls/personalChangePayPassword.html'
        })
        .state('personalWallet', {//我的钱包
            url: '/personalWallet',
            templateUrl: 'yihaiyou/tpls/personalWallet.html'
        })
        .state('personalRecharge', {//充值
            url: '/payfor/personalRecharge',
            templateUrl: 'yihaiyou/tpls/personalRecharge.html'
        })
        .state('personalWithdraw', {//提现
            url: '/personalWithdraw',
            templateUrl: 'yihaiyou/tpls/personalWithdraw.html'
        })
        .state('personalTouristList', {//选择常用游客
            url: '/personalTouristList',
            templateUrl: 'yihaiyou/tpls/personalTouristList.html'
        })
        .state('personalWriteTourMess', {//编辑游客信息
            url: '/personalWriteTourMess/{touristId}',
            templateUrl: 'yihaiyou/tpls/personalWriteTourMess.html'
        })
        .state('personalSelection', {//我的收藏
            url: '/personalSelection',
            templateUrl: 'yihaiyou/tpls/personalSelection.html'
        })
        .state('personalComment', {//我的点评
            url: '/personalComment',
            templateUrl: 'yihaiyou/tpls/personalComment.html'
        })
        .state('personalOrder', {//我的订单
            url: '/personalOrder/{index}',
            templateUrl: 'yihaiyou/tpls/personalOrder.html'
        })
        .state('orderPay', {//支付
            url: '/payfor/{orderId}',
            templateUrl: 'yihaiyou/tpls/orderPay.html'
        })
        .state('planDemand', {//智能行程需求
            url: '/planDemand',
            templateUrl: 'yihaiyou/tpls/planDemand.html'
        })
        .state('planSelectScenic', {//智能行程选择景点
            url: '/planSelectScenic/{params}',
            templateUrl: 'yihaiyou/tpls/planSelectScenic.html'
        })
        .state('planScenicDetail', {//智能行程选择景点
            url: '/planScenicDetail/{params}',
            templateUrl: 'yihaiyou/tpls/planScenicDetail.html'
        })
        .state('planDetail', {//智能行程详情
            url: '/planDetail',
            templateUrl: 'yihaiyou/tpls/planDetail.html'
        })
        .state('planChangeHotel', {//智能行程更换酒店
            url: '/planChangeHotel',
            templateUrl: 'yihaiyou/tpls/planChangeHotel.html'
        })
        .state('planChangeHotelDetail', {//智能行程更换酒店详情
            url: '/planChangeHotelDetail/{hotelId}',
            templateUrl: 'yihaiyou/tpls/planChangeHotelDetail.html'
        })
        .state('orderPlan', {//行程下单
            url: '/orderPlan',
            templateUrl: 'yihaiyou/tpls/orderPlan.html'
        })
        .state('planTouristList', {//旅客列表
            url: '/planTouristList',
            templateUrl: 'yihaiyou/tpls/planTouristList.html'
        })
        .state('planOrderDetail', {//行程订单详情
            url: '/planOrderDetail/{id}',
            templateUrl: 'yihaiyou/tpls/planOrderDetail.html'
        })
        .state('planferrylist', {//智能行程添加船票
            url: '/planferrylist/',
            templateUrl: 'yihaiyou/tpls/planferrylist.html'
        })
        .state('sailling_album', {//帆船相册
            url: '/sailling_album/{params}',
            templateUrl: 'yihaiyou/tpls/sailling_album.html'
        })
        .state('foodList', {//美食
            url: '/foodList',
            templateUrl: 'yihaiyou/tpls/foodList.html'
        })
        .state('ihaitai', {    // i海台
            url: '/ihaitai',
            templateUrl: 'yihaiyou/tpls/ihaitai.html'
        })
        .state('feedBack', {    //意见反馈
            url: '/feedBack',
            templateUrl: 'yihaiyou/tpls/feed_back.html'
        })
        .state('electronicGuide', {    //电子导览
            url: '/electronicGuide',
            templateUrl: 'yihaiyou/tpls/electronicGuide.html'
        })
        .state('waittingPage', {    //敬请期待
            url: '/waittingPage',
            templateUrl: 'yihaiyou/tpls/waittingPage.html'
        })

        .state('timeOut', {    //登录超时
            url: '/timeOut',
            templateUrl: 'yihaiyou/tpls/timeOut.html'
        })
        .state('ferry_firmorder', {    //确认订单
            url: '/ferry_firmorder/{orderId}',
            templateUrl: 'yihaiyou/tpls/ferry_firmorder.html'
        })
        .state('hotel_album', {    //酒店相册
            url: '/hotel_album',
            templateUrl: 'yihaiyou/tpls/hotel_album.html'
        })
        .state('personalMarchList', {    //我的行程
            url: '/personalMarchList',
            templateUrl: 'yihaiyou/tpls/personalMarchList.html'
        })
        .state('personalMarchDetail', {    //我的行程
            url: '/personalMarchDetail/{planId}',
            templateUrl: 'yihaiyou/tpls/personalMarchDetail.html'
        })
        .state('xiazhang', {    //我的行程
            url: '/xiazhang',
            templateUrl: 'yihaiyou/tpls/xiazhang.html'
        })
        /*2017-7-25*/
        .state('newCruiseList', {    //新增邮轮列表
            url: '/newCruiseList',
            templateUrl: 'yihaiyou/tpls/newCruiseList.html'
        })
        .state('newMustKnow', {    //预定需知
            url: '/newMustKnow',
            templateUrl: 'yihaiyou/tpls/newMustKnow.html'
        })
        .state('newQuestion', {    //常见问题
            url: '/newQuestion',
            templateUrl: 'yihaiyou/tpls/newQuestion.html'
        })
        .state('courseDetails', {    //航线详情
            url: '/courseDetails',
            templateUrl: 'yihaiyou/tpls/courseDetails.html'
        })
        .state('cruiseListSort', {    //邮轮列表筛选、排序
            url: '/cruiseListSort',
            templateUrl: 'yihaiyou/tpls/cruiseListSort.html'
        })
        .state('newCruiseDetail', {    //邮轮详情
            url: '/newCruiseDetail',
            templateUrl: 'yihaiyou/tpls/newCruiseDetail.html'
        })
        .state('cruiseEditOrder', {    //邮轮订单填写
            url: '/cruiseEditOrder',
            templateUrl: 'yihaiyou/tpls/cruiseEditOrder.html'
        })
        .state('lvjiOrder', {    //驴记下单页面
            url: '/lvjiOrder/{num}',
            templateUrl: 'yihaiyou/tpls/lvjiOrder.html'
        })
        .state('lvjiOrderDetail', {    //驴记订单详情页面
            url: '/lvjiOrderDetail/{orderId}',
            templateUrl: 'yihaiyou/tpls/lvjiOrderDetail.html'
        })
        .state('lvjiPay', {    //驴记付款页面
            url: '/lvjiPay/{orderId}',
            templateUrl: 'yihaiyou/tpls/lvjiPay.html'
        })
        .state('lvjiList', {    //驴记列表页面
            url: '/lvjiList',
            templateUrl: 'yihaiyou/tpls/lvjisearch.html'
        })
        .state('lvjiPage', {    //驴记付款页面
            url: '/lvjiPage',
            templateUrl: 'yihaiyou/tpls/lvjiPage.html'
        })

});

yhyapp.filter('trustHtml', function ($sce) {
    return function (input) {
        return $sce.trustAsHtml(input);
    }

}).filter('getImgUrl', function() {
    return function(path) {
        if (path == null) {
            return "";
        } else if (path.indexOf('http') === 0) {
            return path;
        } else {
            return QINIU_BUCKET_URL + path;
        }
    };
});