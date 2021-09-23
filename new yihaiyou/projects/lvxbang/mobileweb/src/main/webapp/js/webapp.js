"use strict";
var routerApp = angular.module('lxbApp', ['angularLocalStorage', 'ngCookies', 'ui.router', 'yxlistModule', 'yxdetailModule', 'YoujiModule', 'YjdetailModule', 'SelectCityModule', 'xingcfdModule', 'xingjingdianModule', 'xinggailanModule', 'xingxianlumapModule', 'xingjiaotongModule', 'xingjiudianModule', 'xingjingdianinfoModule', 'xingjiaotonghuocheModule', 'xingjiaotonginfoModule', 'xingjiaotongjipiaoModule', 'xingjiudianinfoModule', 'xingcityModule', 'xianluModule','xingxianluorderinfoModule','xingaddlvkeModule','youkechangModule','xinghbModule','paymentModule','jiudiansearchModule','xingcitysearchModule','xingxlbianjiModule','mybianjiuserModule','xingorderokModule',
    'YoujiModule', 'xingxianluinfoModule', 'xingjingdianmapModule', 'MyuserModule', 'myxianluModule', 'myltModule', 'myorderModule', 'myordertModule', 'myorderdModule', 'myorderinfoModule', 'myscModule', 'myhbModule', 'yijianModule', 'loginModule', 'regModule', 'shaixuanModule', 'mygerenModule', 'searchmddModule', 'xingjingdiansearchModule', 'xingjiudiankeysearchModule', 'searchfujinModule', 'xieganxiangModule', 'searchdifangModule', 'xieganxiangpicModule', 'myresetpwModule', 'myresetyzmModule', 'mynewModule', 'xingbiaotiModule', 'aboutModule', "balanceModule", "balanceLogModule"
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
    $urlRouterProvider.otherwise('/xianlu/');
    $stateProvider
        .state('loading', {//开场动画
            url: '/loading',
            templateUrl:  'tpls/loading.html'
        })
        .state('yinxiang', {//印象列表
            url: '/yinxiang',
            //templateUrl:  'tpls/yxlist.html'
            templateUrl: 'tpls/yxlist.html'
        })
        .state('yxdetail', {//印象详情
            url: '/yxdetail/{id}',
            templateUrl:  'tpls/yxdetail.html'
        })
        .state('youji', {//游记列表
            url: '/youji',
            templateUrl:  'tpls/yjlist.html'
        })
        .state('yjdetail', {//游记详情
            url: '/yjdetail',
            templateUrl:  'tpls/yjdetail.html'
        })
        .state('search', {//搜索框
            url: '/search',
            templateUrl:  'tpls/search.html'
        })
        .state('searchlist', {//搜索结果
            url: '/searchlist',
            templateUrl:  'tpls/searchlist.html'
        })
        .state('search-fujin', {//搜索--附近
            url: '/search-fujin/{type}',
            templateUrl:  'tpls/search-fujin.html'
        })
        .state('search-difang', {//搜索--输入地方名
            url: '/search-difang',
            templateUrl:  'tpls/search-difang.html'
        })
        .state('search-mdd', {//搜索--目的地
            url: '/search-mdd',
            templateUrl:  'tpls/search-mdd.html'
        })
        .state('ganxiang', {//感想
            url: '/ganxiang',
            templateUrl:  'tpls/ganxiang.html'
        })
        .state('xieganxiang', {//编辑感想
            url: '/xieganxiang',
            templateUrl:  'tpls/xieganxiang.html'
        })
        .state('xieganxiang-ok', {//编辑感想完成
            url: '/xieganxiang-ok',
            templateUrl:  'tpls/xieganxiang-ok.html'
        })
        .state('xieganxiang-pic', {//感想相册
            url: '/xieganxiang-pic',
            templateUrl:  'tpls/xieganxiang-pic.html'
        })
        .state('my', {//我的
            url: '/my',
            templateUrl:  'tpls/my.html'
        })
        .state('my-xl', {//我的线路
            url: '/my-xl',
            templateUrl:  'tpls/my-xl.html'
        })
        .state('my-lt', {//我的旅途-印象
            url: '/my-lt',
            templateUrl:  'tpls/my-lt.html'
        })
        .state('my-lt-yj', {//我的旅途-游记
            url: '/my-lt-yj',
            templateUrl:  'tpls/my-lt-yj.html'
        })
        .state('my-bianjiyouji', {//我的--编辑游记
            url: '/my-bianjiyouji',
            templateUrl:  'tpls/my-bianjiyouji.html'
        })
        .state('my-xieyouji', {//我的--写游记
            url: '/my-xieyouji',
            templateUrl:  'tpls/my-xieyouji.html'
        })
        .state('my-order', {//我的订单--已付款
            url: '/my-order',
            templateUrl:  'tpls/my-order.html'
        })
        .state('my-order-dfk', {//我的订单--待付款
            url: '/my-order-dfk',
            templateUrl:  'tpls/my-order-dfk.html'
        })
        .state('my-order-tk', {//我的订单--已退款
            url: '/my-order-tk',
            templateUrl:  'tpls/my-order-tk.html'
        })
        .state('my-order-info', {//我的订单--详情--修复和支付按钮
            url: '/my-order-info/{id}',
            templateUrl:  'tpls/my-order-info.html'
        })
        .state('my-order-info-tk', {//我的订单--详情--退款按钮
            url: '/my-order-info-tk/{id}',
            templateUrl:  'tpls/my-order-info-tk.html'
        })
        .state('my-sc', {//我的收藏
            url: '/my-sc',
            templateUrl:  'tpls/my-sc.html'
        })
        .state('my-geren', {//我的--编辑个人信息
            url: '/my-geren',
            templateUrl:  'tpls/my-geren.html'
        })
        .state('my-yj', {//我的--意见反馈
            url: '/my-yj',
            templateUrl:  'tpls/my-yj.html'
        })
        .state('my-sz', {//我的--设置
            url: '/my-sz',
            templateUrl:  'tpls/my-sz.html'
        })
        .state('my-hb', {//我的--红包
            url: '/my-hb',
            templateUrl:  'tpls/my-hb.html'
        })
        .state('my-hb-n',{//我的--红包-新增加的选中或未选中
            url: '/my-hb-n',
            templateUrl:  'tpls/my-hb-n.html'
        })
        .state('login', {//登录
            url: '/login',
            templateUrl:  'tpls/login.html'
        })
        .state('reg', {//注册
            url: '/reg',
            templateUrl:  'tpls/reg.html'
        })
        .state('my-reset-pw', {//找回密码
            url: '/my-reset-pw',
            templateUrl:  'tpls/my-reset-pw.html'
        })
        .state('my-reset-yzm', {//找回密码--验证码
            url: '/my-reset-yzm/{account}',
            templateUrl:  'tpls/my-reset-yzm.html'
        })
        .state('my-new-pw', {//找回密码--输入新密码
            url: '/my-new-pw/{request}',
            templateUrl:  'tpls/my-new-pw.html'
        })
        .state('my-new-pw-ok', {//找回密码--成功
            url: '/my-new-pw-ok',
            templateUrl:  'tpls/my-new-pw-ok.html'
        })
        .state('my-bianji-user', {//编辑常用旅客信息
            url: '/my-bianji-user/{tourist}',
            templateUrl:  'tpls/my-bianji-user.html'
        })
        .state('xing', {//行程规划
            url: '/xing',
            templateUrl: 'tpls/xing.html'
        })
        .state('xing-cfd', {//选择出发时间/地点
            url: '/xing-cfd/{fromQuote}',
            templateUrl:  'tpls/xing-cfd.html'
        })
        .state('xing-city', {//选择城市
            url: '/xing-city/{fromQuote}',
            templateUrl:  'tpls/xing-city.html'
        })
        .state('xing-city-search', {//选择城市--搜索
            url: '/xing-city-search',
            templateUrl:  'tpls/xing-city-search.html'
        })
        .state('xing-jingdian', {//添加景点
            url: '/xing-jingdian',
            templateUrl:  'tpls/xing-jingdian.html'
        })
        .state('xing-jingdian-search', {//添加景点--搜索
            url: '/xing-jingdian-search/{cityIds}',
            templateUrl:  'tpls/xing-jingdian-search.html'
        })
        .state('xing-jingdian-info', {//景点详情---加入线路
            url: '/xing-jingdian-info/{id}',
            templateUrl:  'tpls/xing-jingdian-info.html'
        })
        .state('xing-jingdian-quxiao', {//景点详情--取消线路
            url: '/xing-jingdian-quxiao',
            templateUrl:  'tpls/xing-jingdian-quxiao.html'
        })
        .state('xing-jingdian-map', {//景点详情--景点地图
            url: '/xing-jingdian-map',
            templateUrl:  'tpls/xing-jingdian-map.html'
        })
        .state('xing-gailan', {//景点详情--概览
            url: '/xing-gailan/{fromQuote}',
            templateUrl:  'tpls/xing-gailan.html'
        })
        .state('xing-gailan-delete', {//景点详情--概览 删除
            url: '/xing-gailan-delete',
            templateUrl:  'tpls/xing-gailan-delete.html'
        })
        .state('xing-biaoti', {//写线路标题
            url: '/xing-biaoti/{request}',
            templateUrl:  'tpls/xing-biaoti.html'
        })
        .state('xing-xlbianji', {//景点详情--编辑线路
            url: '/xing-xlbianji/{day}',
            templateUrl:  'tpls/xing-xlbianji.html'
        })
        .state('xing-xianlu-map', {//线路地图总览
            url: '/xing-xianlu-map/{day}',
            templateUrl:  'tpls/xing-xianlu-map.html'
        })
        .state('xing-jiaotong', {//添加交通
            url: '/xing-jiaotong',
            templateUrl:  'tpls/xing-jiaotong.html'
        })
        .state('xing-jiaotong-info', {//交通---班次详情
            url: '/xing-jiaotong-info/{traffics}',
            templateUrl:  'tpls/xing-jiaotong-info.html'
        })
        .state('xing-jiaotong-jipiao', {//交通---班次详情
            url: '/xing-jiaotong-jipiao/{traffics}',
            templateUrl:  'tpls/xing-jiaotong-jipiao.html'
        })
        .state('xing-jiaotong-huoche', {//交通---班次详情
            url: '/xing-jiaotong-huoche/{traffics}',
            templateUrl:  'tpls/xing-jiaotong-huoche.html'
        })
        .state('xing-jiudian', {//添加酒店
            url: '/xing-jiudian',
            templateUrl:  'tpls/xing-jiudian.html'
        })
        .state('xing-jiudian-search', {//酒店查询
            url: '/xing-jiudian-search/{hotel}',
            templateUrl:  'tpls/xing-jiudian-search.html'
        })
        .state('xing-jiudian-key-search', {//酒店查询
            url: '/xing-jiudian-key-search/{hotel}',
            templateUrl:  'tpls/xing-jiudian-key-search.html'
        })
        .state('xing-jiudian-info', {//酒店详情
            url: '/xing-jiudian-info/{hotel}',
            templateUrl:  'tpls/xing-jiudian-info.html'
        })
        .state('xing-xingcheng-info', {//线路--行程详情
            url: '/xing-xingcheng-info',
            templateUrl:  'tpls/xing-xingcheng-info.html'
        })
        .state('xing-xianlu-order-info', {//线路订单详情
            url: '/xing-xianlu-order-info/{planIdorderId}',
            templateUrl:  'tpls/xing-xianlu-order-info.html'
        })
        .state('xing-add-lvke', {//填写旅客信息
            url: '/xing-add-lvke',
            templateUrl:  'tpls/xing-add-lvke.html'
        })
        .state('xing-order-ok', {//确认行程订单详情
            url: '/xing-order-ok',
            templateUrl:  'tpls/xing-order-ok.html'
        })
        .state('youhuiquan', {//使用优惠券
            url: '/youhuiquan',
            templateUrl:  'tpls/youhuiquan.html'
        })
        .state('youke-chang', {//常用游客
            url: '/youke-chang',
            templateUrl:  'tpls/youke-chang.html'
        })
        .state('payment', {//支付
            url: '/payment/{order}',
            templateUrl:  'tpls/payment.html'
        })
        .state('shaixuan', {//筛选
            url: '/shaixuan',
            templateUrl:  'tpls/shaixuan.html'
        })
        .state('xianlu', {//线路
            url: '/xianlu/{first}',
            templateUrl:  'tpls/xianlu.html'
        })
        .state('xing-xianlu-info', {//线路详情
            url: '/xing-xianlu-info/{id}',
            templateUrl:  'tpls/xing-xianlu-info.html'
        })
        .state('xing-hb', {//线路详情
            url: '/xing-hb',
            templateUrl:  'tpls/xing-hb.html',
        })
        .state('about', {//线路详情
            url: '/about',
            templateUrl:  'tpls/about.html',
        })
        .state('balance', {//余额
            url: '/balance',
            templateUrl: 'tpls/balance.html',
        })
        .state('balanceLog', {//余额明细
            url: '/balanceLog',
            templateUrl: 'tpls/balanceLog.html',
        })
        .state('test', {//测试
            url: '/test',
            templateUrl:  'tpls/test.html'
        });
});


