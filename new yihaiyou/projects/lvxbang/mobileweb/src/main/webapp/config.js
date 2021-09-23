var GetUrl = {
    payment: apihost + '/index.html#/payment/',//支付页面
    personal: apihost + '/index.html#/my',//个人中心
    loginPage: apihost + '/index.html#/login',//登录页面
    lineOrderDetail: apihost + '/lxb.html#/orderdetail/',//线路订单详情
    ticketOrderDetail: apihost + '/lxb.html#/ticketOrderDetail/',//门票订单详情
    hotelOrderDetail: apihost + '/lxb.html#/hotelOrderDetail/',//酒店订单详情
    homePage: apihost + '/lxb.html#/index',//线路订单详情

	yxlist: apihost +  '/mobile/impression/listAll.jhtml',	//印象列表
	yxdetail: apihost +  '/mobile/impression/detail.jhtml',	//印象详情
	placeList: apihost +  '/mobile/impression/placeList.jhtml',    //地点列表
	uploadPhoto: apihost +  '/mobile/impression/uploadPhoto.jhtml',    //上传图片
	yxsave: apihost +  '/mobile/impression/save.jhtml',    //保存印象
	yjlist: './data/yjlist.js',	//游记列表
	yjdetail: './data/yjdetail.js',	//游记详情
	searchlist: './data/searchlist.js', //搜索结果列表


	//行程规划
	selectcitys: apihost +  '/mobile/city/list.jhtml', //搜索结果列表
    reverseCity: apihost + '/mobile/city/reverseCity.jhtml',
	getCurrentCity: apihost +  '/mobile/city/getCurrentCity.jhtml', //搜索结果列表
	hotCity: apihost +  '/mobile/city/hotCity.jhtml', //热门城市列表
	theme : apihost +  '/mobile/scenic/theme.jhtml', //询景点主题列表，返回景点主题名称列表。
	sceniclist : apihost +  '/mobile/scenic/list.jhtml', //询景点主题列表，返回景点主题名称列表。
	optimize : apihost +  '/mobile/plan/optimize.jhtml', //根据游玩城市天数安排和景点列表给用户推荐一份游玩行程。
	recommend : apihost +  '/mobile/traffic/recommend.jhtml', //根据行程推荐交通，包括飞机和火车；交通价格列表按价格升序排列。
	hotelRecommend : apihost +  '/mobile/hotel/recommend.jhtml', //根据行程推荐交通，包括飞机和火车；交通价格列表按价格升序排列。
	save : apihost +  '/mobile/plan/save.jhtml', //根据行程推荐交通，包括飞机和火车；交通价格列表按价格升序排列。
    planOrder: apihost + '/mobile/plan/orderNoPlan.jhtml',
    scenicdetail : apihost +  '/mobile/scenic/detail.jhtml',//景点详情
    trafficlist : apihost +  '/mobile/traffic/list.jhtml',//交通详情
    hotelDetail : apihost +  '/mobile/hotel/detail.jhtml',//交通详情
    planlist : apihost +  '/mobile/plan/searchList.jhtml',//交通详情
    hotPlans: apihost +  '/mobile/plan/hotList.jhtml',
    xldetail: apihost +  '/mobile/plan/detail.jhtml',	//线路详情
    updatePlanName: apihost +  '/mobile/plan/updateName.jhtml',  //修改线路名称
    quotePlan: apihost +  '/mobile/plan/quote.jhtml',  //复制线路
    order: apihost +  '/mobile/plan/order.jhtml',	//线路详情
    orderSave: apihost +  '/mobile/order/save.jhtml',	//线路详情
    orderLine: apihost + '/mobile/order/orderLine.jhtml', //线路下单
    touristList: apihost +  '/mobile/order/touristList.jhtml',	//线路详情
    saveTourist: apihost +  '/mobile/order/saveTourist.jhtml',	//添加乘客
    couponList: apihost +  '/mobile/order/couponList.jhtml',	//线路详情
    validateCreditCart: apihost +  '/mobile/order/validateCreditCart.jhtml',   //验证信用卡号
    hotelList: apihost +  '/mobile/hotel/list.jhtml',	//线路详情


    getPrepayId: apihost +  "/mobile/pay/getPrepayId.jhtml",	//线路详情
    getPayConfig: apihost +  "/mobile/pay/getPayConfig.jhtml",	//线路详情
    getAppPrepayId: apihost +  "/mobile/pay/getAppPrepayId.jhtml",	//线路详情
    getAppPayConfig: apihost +  "/mobile/pay/getAppPayConfig.jhtml",	//线路详情
    alinativePay: apihost +  "/mobile/pay/alinativePay.jhtml",	//线路详情

	//我的---会员中心
    	myuser: './data/myuser.js', //我的
    	//myxianlu: './data/myxianlu.js', //我的线路
    	mylvtu: './data/mylvtu.js', //我的旅途--印象
    	mylvtuyouji: './data/mylvtu.js', //我的旅途--游记
    	myorder: apihost +  '/mobile/order/list.jhtml', //我的订单
    	myorderd: './data/myorder.js', //我的订单--待支付
    	myordert: './data/myorder.js', //我的订单--已退款
    	delorder: apihost +  '/mobile/order/delete.jhtml',	//删除订单
    	myorderinfo: apihost +  '/mobile/order/detail.jhtml', //我的订单详情
    	mysc: apihost +  '/mobile/collect/list.jhtml', //我的收藏
    	myhb: apihost +  '/mobile/order/couponList.jhtml', //我的红包
		myhbn: './data/myhb.js', //新增加的红包列表页，有选中和未选中

    saveBalanceOrder: '/mobile/order/saveBalanceOrder.jhtml', //保存余额订单


	//我的
	//feedback : apihost +  '/mobile/user/feedback.jhtml', //意见反馈

    login:apihost +  '/mobile/user/login.jhtml',//会员中心
    myxianlu:apihost +  '/mobile/plan/list.jhtml',//我的线路
    delxianlu:apihost +  '/mobile/plan/delete.jhtml',	//删除线路
    mylvtu:apihost +  '/mobile/impression/list.jhtml',//我的印象
    delImpr: apihost +  '/mobile/impression/delete.jhtml',   //删除印象
	//myorder : apihost +  '/mobile/order/list.jhtml', //我的订单列表

    //会员中心
    appLogin:host +  '/mobile/user/appLogin.jhtml',	//登录
    logout:apihost +  '/mobile/user/logout.jhtml',	//退出
    checklogin:apihost +  '/mobile/user/checkLogin.jhtml',	//验证登录
    checkUser: apihost +  '/mobile/user/checkUser.jhtml',  //验证用户是否存在
    sendSms: apihost +  '/mobile/user/sendSmsCode.jhtml',  //发送短信验证码
    checkSms:apihost +  '/mobile/user/checkSmsCode.jhtml', //验证短信验证码
    forgotPassword: apihost +  '/mobile/user/forgotPassword.jhtml',    //忘记密码重置
    register:apihost +  '/mobile/user/register.jhtml', //注册
    userinfo:apihost +  '/mobile/user/detail.jhtml',	//用户信息
    updateuser: apihost + '/mobile/user/update.jhtml',	//更新用户信息
    balanceLog: apihost + '/mobile/user/balanceLog.jhtml' //余额明细
};
var keys = {
    xianluSearch: 'xianluSearch',
    selectCitys: 'selectCitys',
    fromCity: 'fromCity',
    addScenics: 'addScenics',
    scenic: 'scenic',
    selectTime: 'selectTime',
    startDay: 'startDay',
    optimizeScenics: 'scenics',
    jiaotong: 'jiaotong',
    addNodes: 'addNodes',
    jiudian: 'jiudian',
    removeNodes: 'removeNodes',
    train: 'train',
    plan: 'plan',
    coupons: 'coupons',
    usecoupon: 'usecoupon',
    contact: 'contact',
    invoice: 'invoice',
    selectedtourist: 'selectedtourist',
    productcost: 'productcost',//订单商品总金额
    creditCard: 'creditCard',//订单商品总金额
    youhui: 'youhui',
    order: 'order',
    user: 'user',
    fromQuote: 'fromQuote',
    cityMap: 'cityMap',
    hotPlans: 'hotPlans',
    place: 'place',
    yxContent: 'yxContent',
    yxPhoto: 'yxPhoto'
};


var LXB_URL = {
    // 收藏（取消收藏），参数：favoriteType，favoriteId
    favorite : apihost + "/mobile/favorite/favorite.jhtml",
    // 新版首页数据
    mIndexData: apihost + "/mobile/index/getIndexData.jhtml",
    // 新版首页线路数据
    mIndexAreaLineData: apihost + "/mobile/index/getAreaLineList.jhtml",
    // 跟团游首页数据
    mGroupTourIndexData: apihost + "/mobile/groupTour/getGtiData.jhtml",
    // 自驾游首页数据
    mDriveTourIndexData: apihost + "/mobile/driveTour/getDtiData.jhtml",
    // 自助游首页数据
    mSelfTourIndexData: apihost + "/mobile/selfTour/getStiData.jhtml",
    // 定制精品首页数据
    mCustomTourIndexData: apihost + "/mobile/customTour/getCtiData.jhtml",
    // 周边游首页数据
    mAroundTourIndexData: apihost + "/mobile/aroundTour/getAtiData.jhtml",
    // 国内游首页数据
    mInternalTourIndexData: apihost + "/mobile/internalTour/getItiData.jhtml",
    // 出境游首页数据
    mOverseasTourIndexData: apihost + "/mobile/overseasTour/getOtiData.jhtml",
    // 门票首页数据
    mTicketIndexData: apihost + "/mobile/ticket/getTkeiData.jhtml",
    // 门票-我的附近数据
    mTicketNearData: apihost + "/mobile/ticket/getTkeiNear.jhtml",
    // 酒店首页数据
    mHotelIndexData: apihost + "/mobile/hotel/getHteiData.jhtml",
    // 我的团队数
    countTeam : apihost + "/mobile/myTeam/countTeam.jhtml",
    // 我的团队列表
    listTeam : apihost + "/mobile/myTeam/listTeam.jhtml",
    // 获取微信分享配置
    getShareConfig : apihost + "/mobile/myTeam/getShareConfig.jhtml",
    // 分享页面
    doDistributeShare : apihost + "/mobile/myTeam/doDistributeShare.jhtml",
    // 景点概要信息
    scenicTicketInfo : apihost + "/mobile/scenicTicket/scenicTicketInfo.jhtml",
    // 景点门票列表信息
    scenicTicketList : apihost + "/mobile/scenicTicket/scenicTicketList.jhtml",
    // 景点详情
    scenicDesc : apihost + "/mobile/scenicTicket/scenicDesc.jhtml",
    // 景点评论
    scenicCommentList : apihost + "/mobile/scenicTicket/scenicCommentList.jhtml",
    // 票型说明
    ticketAddinfoDetail : apihost + "/mobile/scenicTicket/ticketAddinfoDetail.jhtml",
    // 酒店日历
    hotelDateCalendar : apihost + "/mobile/hotel/calendar.jhtml",
    // 酒店概要信息
    hotelInfo : apihost + "/mobile/hotel/hotelInfo.jhtml",
    // 酒店房型价格列表
    listHotelPrice : apihost + "/mobile/hotel/listHotelPrice.jhtml",
    // 酒店评论
    hotelCommentList : apihost + "/mobile/hotel/hotelCommentList.jhtml",
    //线路列表
    mXianlu_list : apihost + "/mobile/line/getLineList.jhtml",
    //线路筛查目的地
    mXianlu_areaList : apihost + "/mobile/line/getAreaList.jhtml",
    //线路详情
    mXianlu_detail : apihost + "/mobile/line/lineDetail.jhtml",
    //线路详情行程介绍
    mXianlu_day: apihost + "/mobile/line/lineDay.jhtml",
    //线路详情费用说明
    mXianlu_fare : apihost + "/mobile/line/lineTypePrice.jhtml",
    //线路详情预订须知
    mXianlu_booking : apihost + "/mobile/line/lineExplain.jhtml",
    //线路下单选择日期
    orderDate: apihost + "/mobile/order/getLineDatePriceList.jhtml",

    //目的地热门搜索
    mHotArea: apihost + "/mobile/city/hotCity.jhtml",
    //景点门票列表
    mScenicList: apihost + "/mobile/scenic/list.jhtml",
    selectcitys: apihost +  '/mobile/city/list.jhtml', //搜索结果列表
    search_line: apihost +  '/mobile/line/searchLine.jhtml', //搜索结果列表
    search_scenic: apihost +  '/mobile/scenic/searchScenic.jhtml', //搜索结果列表

    ticketDatePrice: apihost + "/mobile/order/getTicketDatePriceList.jhtml", //门票下单选择日期
    orderTicket: apihost + "/mobile/order/orderTicket.jhtml", //门票下单
    checkTicketOrder: apihost + "/mobile/order/checkTicketOrder.jhtml", //门票下单验证库存
    checkLineOrder: apihost + "/mobile/order/checkLineOrder.jhtml", //线路下单验证库存
    checkHotelOrder: apihost + "/mobile/order/checkHotelOrder.jhtml", //酒店下单验证库存
    ticketOrderDetail: apihost + "/mobile/order/ticketOrderDetail.jhtml", //门票订单详情
    orderHotel: apihost + "/mobile/order/orderHotel.jhtml", //酒店下单
    hotelOrderDetail: apihost + "/mobile/order/hotelOrderDetail.jhtml", //酒店下单
    hotelBrandList: apihost + "/mobile/hotel/listHotelBrand.jhtml", //酒店筛选-品牌
    hotelServiceList: apihost + "/mobile/hotel/listHotelService.jhtml", //酒店筛选-服务
    hotelRegionList: apihost + "/mobile/hotel/listHotelRegion.jhtml", //酒店筛选-区域
    hotelList: apihost + "/mobile/hotel/listHotel.jhtml", //酒店列表
    sceneryDetail : apihost + "/mobile/scenery/sceneryDetail.jhtml", //游艇帆船详情
    sceneryPriceType : apihost + "/mobile/scenery/sceneryPriceType.jhtml", //游艇帆船详情
    scenicDesc : apihost + "/mobile/scenery/sceneryDesc.jhtml" // 游艇帆船内容详情
};

var LXB_KEY = {
    IMG_DOMAIN: 'http://7u2inn.com2.z0.glb.qiniucdn.com',
    adultPrice: 'adultPrice',
    adultNum: 'adultNum',
    childPrice: 'childPrice',
    childNum: 'childNum',
    oasiaHotel: 'oasiaHotel',
    insurancePrice: 'insurancePrice',
    orderTotalPrice: 'orderTotalPrice',
    selectedTourist: 'selectedTourist',
    lineStartDate: 'lineStartDate',
    linetypepriceId: 'linetypepriceId',
    lineId: 'lineId',
    insurances: 'insurances',
    selectedAdultTourist: 'selectedAdultTourist',
    selectedChildTourist: 'selectedChildTourist',
    contact: 'contact',
    searchLineKey: 'lineKeywords',
    startDate: 'startDate',
    orderHotel: 'orderHotel',
    hotelDetailRoomFilter: 'hotelDetailRoomFilter',
    ticketDatePriceId: 'ticketDatePriceId',
    needGuarantee: 'needGuarantee',
    creditCard: 'creditCard'
};