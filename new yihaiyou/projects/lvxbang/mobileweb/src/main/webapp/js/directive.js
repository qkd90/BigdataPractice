routerApp
    .directive('loading', function ($http) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.bind('click', function (event) {
                    scope.$apply(attrs.howtoload)();
                });
            }
        }
    })//行程规划 - 城市选择
    .directive('city', function ($http, storage) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.click(function () {
                    var scope = angular.element(document.querySelector("#SelectCityCtrl")).scope();
                    scope.$apply(function () {
                        element.toggleClass("active");
                        var cityScope = angular.element(element).scope();
                        var liCity = $("li[citid='" + cityScope.city.id + "']");
                        if (element.hasClass('active')) {
                            scope.selectCitys.push(cityScope.city);
                            for (var i = 0; i < liCity.length; i++) {
                                var item = $(liCity[i]);
                                if (!item.hasClass('active')) {
                                    item.addClass('active');
                                }
                            }
                        } else {
                            for (var i = 0; i < scope.selectCitys.length; i++) {
                                if (scope.selectCitys[i].id == cityScope.city.id) {
                                    scope.selectCitys.splice(i, 1);
                                    for (var i = 0; i < liCity.length; i++) {
                                        var item = $(liCity[i]);
                                        if (item.hasClass('active')) {
                                            item.removeClass('active');
                                        }
                                    }
                                }
                            }
                        }
                        //var storage = angular.element(document.body).injector().get('storage');
                        storage.set(keys.selectCitys, scope.selectCitys);
                    });
                });
            }
        }
    })
    .directive('spinner', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {

                element.spinner({
                    min: 1,
                    value: (attrs.value == null || attrs.value == undefined || attrs.value == '') ? 1 : attrs.value
                });
            }
        }
    })
    .directive('selectfromcity', function ($http, storage, $state) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.click(function () {
                    var cityScope = angular.element(element).scope();
                    storage.set(keys.fromCity, cityScope.city);
                    $state.go('xing-cfd', {fromQuote: cityScope.fromQuote});
                });
            }
        }
    })
    .directive('menuitem', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.click(function () {
                    element.parents('li').find('span').html($(this).html());
                    element.parents('li').find('ul').slideUp();
                    scope.$apply(function () {
                        scope.showDark = false;
                    })

                });
            }
        }
    })
    .directive('selecttime', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.click(function () {
                    element.parent().find('li').removeClass('active');
                    element.addClass('active');
                });
            }
        }
    })
    .directive('mylink', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.click(function () {
                    location.href = attrs.mylink;
                });
            }
        }
    })
    .directive('icheckblue', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%' // optional
                });
            }
        }
    })
    .directive('icheckgreen', function ($http, $cookieStore) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var obj = scope.$parent;
                    //scope.$apply(obj.$parent.$parent.changeTraffic)();

                    var traffics = obj.$parent.city.traffics;
                    if (String(true) == attrs.isreturn) {
                        traffics = obj.$parent.city.returnTraffics;
                    }
                    var traffic = obj.traffics;
                    $.each(traffics, function (i, item) {
                        if (item.trafficHash == traffic.trafficHash) {
                            item.selected = true;
                        } else {
                            item.selected = false;
                        }
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.jiaotong, obj.$parent.$parent.trafficList);
                    var checkbox = $(event.currentTarget).parents(".addjiaotong").parent().siblings().find("input[type=checkbox]");
                    if (checkbox.attr("isreturn") == $(event.currentTarget).attr("isreturn")) {
                        checkbox.iCheck("uncheck");
                    }
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var obj = scope.$parent;
                    //scope.$apply(obj.$parent.$parent.changeTraffic)();

                    var traffics = obj.$parent.city.traffics;
                    if (String(true) == attrs.isreturn) {
                        traffics = obj.$parent.city.returnTraffics;
                    }
                    var traffic = obj.traffics;
                    $.each(traffics, function (i, item) {
                        if (item.trafficHash == traffic.trafficHash) {
                            item.selected = true;
                        } else {
                            item.selected = false;
                        }
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.jiaotong, obj.$parent.$parent.trafficList);
                    var checkbox = $(event.currentTarget).parents(".addjiaotong").parent().siblings().find("input[type=checkbox]");
                    if (checkbox.attr("isreturn") == $(event.currentTarget).attr("isreturn")) {
                        checkbox.iCheck("check");
                    }
                });
            }
        }
    })
    .directive('selectjiudian', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%' // optional
                });
                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var hotel = scope.$parent.hotel;
                    hotel.checked = true;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.jiudian, scope.$parent.$parent.hotels);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var hotel = scope.$parent.hotel;
                    hotel.checked = false;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.jiudian, scope.$parent.$parent.hotels);
                });
            }
        }
    })
    .directive('reload', function ($http, $cookieStore, $state) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.click(function () {
                    location.reload();
                });
            }
        }
    })
    .directive('parentall', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                function countTotal(order) {
                    var total = 0;
                    $.each(order.hotels, function (i, item) {
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        if (item.check) {
                            total += item.price + item.additionalFuelTax + item.airportBuildFee
                        }
                    });
                    $.each(order.trains, function (i, item) {
                        if (item.check) {
                            total += item.price
                        }
                    });
                    $.each(order.days, function (i, item) {
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                if (item.check) {
                                    total += ticket.price;
                                }
                            }
                        });
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.productcost, total);
                    $("#total").html(total);
                    //为什么这里appli不生效？
                    //scope.$apply(function () {
                    //    scope.total = total;
                    //});
                }

                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parent = scope.$parent.order[attrs.parent];
                    scope.$parent.order[attrs.parent + 'check'] = true;
                    $.each(parent, function (i, item) {
                        item.check = true;
                    });
                    element.parent().parent().next().find("div.icheckbox_square-green").addClass('checked');
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parent = scope.$parent.order[attrs.parent];
                    scope.$parent.order[attrs.parent + 'check'] = false;
                    $.each(parent, function (i, item) {
                        item.check = false;
                    });
                    element.parent().parent().next().find("div.icheckbox_square-green").removeClass('checked');
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
            }
        }
    })
    .directive('normalchild', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                function countTotal(order) {
                    var total = 0;
                    $.each(order.hotels, function (i, item) {
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        if (item.check) {
                            total += item.price + item.additionalFuelTax + item.airportBuildFee
                        }
                    });
                    $.each(order.trains, function (i, item) {
                        if (item.check) {
                            total += item.price
                        }
                    });
                    $.each(order.days, function (i, item) {
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                if (item.check) {
                                    total += ticket.price;
                                }
                            }
                        });
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.productcost, total);
                    $("#total").html(total);
                    //为什么这里appli不生效？
                    //scope.$apply(function () {
                    //    scope.total = total;
                    //});
                }

                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parents = scope.$parent.$parent.order[attrs.parent + 's'];
                    if (element.parents('div.panel-body').find("div.icheckbox_square-green.checked").size() + 1 == parents.length) {
                        scope.$parent.$parent.order[attrs.parent + 'scheck'] = true;
                        element.parents('div.panel-body').prev().find("div.icheckbox_square-green").addClass('checked')
                    }
                    scope[attrs.parent].check = true;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    scope.$parent.$parent.order['trainscheck'] = false;
                    element.parents('div.panel-body').prev().find("div.icheckbox_square-green").removeClass('checked');
                    scope[attrs.parent].check = false;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
            }
        }
    })
    .directive('dayall', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                function countTotal(order) {
                    var total = 0;
                    $.each(order.hotels, function (i, item) {
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        if (item.check) {
                            total += item.price + item.additionalFuelTax + item.airportBuildFee
                        }
                    });
                    $.each(order.trains, function (i, item) {
                        if (item.check) {
                            total += item.price
                        }
                    });
                    $.each(order.days, function (i, item) {
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                if (item.check) {
                                    total += ticket.price;
                                }
                            }
                        });
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.productcost, total);
                    $("#total").html(total);
                    //为什么这里appli不生效？
                    //scope.$apply(function () {
                    //    scope.total = total;
                    //});
                }

                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    scope.day.check = true;
                    $.each(scope.day.tickets, function (i, item) {
                        item.check = true;
                    });
                    element.parent().parent().next().find("div.icheckbox_square-green").addClass('checked');
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    $.each(scope.day.tickets, function (i, item) {
                        item.check = false;
                    });
                    scope.day.check = false;
                    element.parent().parent().next().find("div.icheckbox_square-green").removeClass('checked');
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
            }
        }
    })
    .directive('ticket', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                function countTotal(order) {
                    var total = 0;
                    $.each(order.hotels, function (i, item) {
                        item.check = true;
                        //total += item.price
                    });
                    $.each(order.planes, function (i, item) {
                        if (item.check) {
                            total += item.price + item.additionalFuelTax + item.airportBuildFee
                        }
                    });
                    $.each(order.trains, function (i, item) {
                        if (item.check) {
                            total += item.price
                        }
                    });
                    $.each(order.days, function (i, item) {
                        $.each(item.tickets, function (i, ticket) {
                            if (ticket.status == 'normal') {
                                if (ticket.check) {
                                    total += ticket.price;
                                }
                            }
                        });
                    });
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.productcost, total);
                    $("#total").html(total);
                    //为什么这里appli不生效？
                    //scope.$apply(function () {
                    //    scope.total = total;
                    //});
                }

                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parents = scope.$parent.$parent.day;
                    if (element.parents('div.panel-body').find("div.icheckbox_square-green.checked").size() + 1 == parents.tickets.length) {
                        parents.check = true;
                        element.parents('div.panel-body').prev().find("div.icheckbox_square-green").addClass('checked')
                    }
                    scope.$parent.ticket.check = true;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parents = scope.$parent.$parent.day;
                    parents.check = false;
                    element.parents('div.panel-body').prev().find("div.icheckbox_square-green").removeClass('checked');
                    scope.$parent.ticket.check = false;
                    var storage = angular.element(document.body).injector().get('storage');
                    storage.set(keys.order, scope.$parent.order);
                    countTotal(scope.$parent.order);
                });
            }
        }
    })
    .directive('userselected', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var storage = angular.element(document.body).injector().get('storage');
                    var selectedtourist = storage.get(keys.selectedtourist) == null ? [] : storage.get(keys.selectedtourist);
                    scope.tourist.check = true;
                    selectedtourist.push(scope.tourist);
                    scope.$parent.$parent.selectedtourist = selectedtourist;
                    storage.set(keys.selectedtourist, selectedtourist);
                    //angular.element(document.body).scope().$apply(function () {
                    //    scope.$parent.$parent.$parent.selectNum;
                    //});
                    $("#selectNum").html(selectedtourist.length);

                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var storage = angular.element(document.body).injector().get('storage');
                    var selectedtourist = storage.get(keys.selectedtourist) == null ? [] : storage.get(keys.selectedtourist);
                    scope.tourist.check = false;
                    for (var i = 0; i < selectedtourist.length; i++) {
                        var item = selectedtourist[i];
                        if (item.touristId == scope.tourist.touristId) {
                            selectedtourist.splice(i, 1);
                            break;
                        }
                    }
                    scope.$parent.$parent.selectedtourist = selectedtourist;
                    storage.set(keys.selectedtourist, selectedtourist);
                    //angular.element(document.body).scope().$apply(function () {
                    //    scope.$parent.$parent.$parent.selectNum;
                    //});
                    $("#selectNum").html(selectedtourist.length);
                });
            }
        }
    })
    .directive('couponselected', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                //element.iCheck({
                //    checkboxClass: 'icheckbox_square-green',
                //    radioClass: 'iradio_square-green',
                //    increaseArea: '20%' // optional
                //});
                element.click(function () {
                    scope.$apply(function () {
                        var scope = angular.element(event.currentTarget).scope();
                        var storage = angular.element(document.body).injector().get('storage');
                        //storage.set(keys.usecoupon, scope.data);
                        var total = storage.get(keys.productcost);
                        if (!scope.data.check) {
                            if (parseFloat(total) >= scope.data.useCondition) {
                                scope.data.check = true;
                                storage.set(keys.usecoupon, scope.data);
                            } else {
                                bootbox.alert("此优惠券满" + scope.data.useCondition + "可用，您目前订单金额为" + total);
                                element.removeAttr('checked');
                            }
                        } else {
                            storage.remove(keys.usecoupon);
                            scope.data.check = false;
                        }

                    });
                });
                //element.on('ifChecked', function (event) {
                //    var scope = angular.element(event.currentTarget).scope();
                //    var storage = angular.element(document.body).injector().get('storage');
                //    storage.set(keys.usecoupon, scope.data);
                //    var total = storage.get(keys.productcost);
                //    if(parseFloat(total) >= scope.data.useCondition){
                //        storage.set(keys.usecoupon, scope.data);
                //    }else{
                //        bootbox.alert("此优惠券满" + scope.data.useCondition + "可用，您目前订单金额为" + total);
                //        element.parent().removeClass('checked');
                //    }
                //});
                //element.on('ifUnchecked', function (event) {
                //    var scope = angular.element(event.currentTarget).scope();
                //    var storage = angular.element(document.body).injector().get('storage');
                //    var total = storage.get(keys.productcost);
                //    storage.remove(keys.usecoupon);
                //});
            }
        }
    })
    .directive('showandhide', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green-bx',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                element.on('ifChecked ifUnchecked', function (event) {
                    $(attrs.target).toggle();
                });
            }
        }
    })
    .directive('payselect', function ($http, $cookieStore, storage) {
        var store = storage;
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                });
                element.on('ifChecked', function (event) {
                    //$(attrs.target).toggle();
                    console.info('ddd');
                });
            }
        }
    })
    .directive('ajaxloading', function ($rootScope) {
        return {
            restrict: 'AE',
            replace: true,
            template: '<div class="ajaxloading" style="display:none"></div>',
            //link: function (scope, element, attr) {
            //    if(!$rootScope.ajaxloading){
            //        $rootScope.ajaxloading = true;
            //    }
            //    scope.$watch(scope.ajaxloading, function (old,val) {
            //        if (val)
            //            $(element).show();
            //        else
            //            $(element).hide();
            //    });
            //
            //},
            controller: ['$scope', '$attrs', function ($scope, $attrs) {
                //$rootScope.ajaxloading = true;
                //$scope.$watch($rootScope.ajaxloading, function (newVal, oldVal) {
                //    console.log('>>>>>>>child watch parent scope[Count]:' + oldVal + ' newVal:' + newVal);
                //});
                $scope.$on('load', function (event, args) {
                    //console.log("child scope on(监听) recieve count Change event :" + args.val);
                    if (args.load)
                        $("div.ajaxloading").show();
                    else
                        $("div.ajaxloading").hide();
                });
            }]
        }
    })
    .directive('owlcarousel', function () {
        return {
            restrict: 'AE',
            link: function (scope, element, attr) {
                setTimeout(function () {
                    element.owlCarousel({
                        singleItem: true,
                        //autoHeight: true,
                        pagination: false
                    });
                    //xianludays.jumpTo(day);
                    var owl = element.data('owlCarousel');
                    if (attr.day) {
                        owl.jumpTo(attr.day);
                    } else {
                        owl.jumpTo(0);
                    }
                }, 1000);
            }
        }
    })
    .directive('isOver', function () {
        return {
            restrict: 'A',
            scope: {
                over: '=isOver'
            },
            link: function (scope, element, attr) {
                if (scope.$parent.$last) {
                    scope.over = true;
                }
            }
        }
    })
    .directive("bnLazySrc", function ($window, $document) {

            // I manage all the images that are currently being
            // monitored on the page for lazy loading.
            var lazyLoader = (function () {

                // I maintain a list of images that lazy-loading
                // and have yet to be rendered.
                var images = [];

                // I define the render timer for the lazy loading
                // images to that the DOM-querying (for offsets)
                // is chunked in groups.
                var renderTimer = null;
                var renderDelay = 100;

                // I cache the window element as a jQuery reference.
                var win = $($window);

                // I cache the document document height so that
                // we can respond to changes in the height due to
                // dynamic content.
                var doc = $document;
                var documentHeight = doc.height();
                var documentTimer = null;
                var documentDelay = 2000;

                // I determine if the window dimension events
                // (ie. resize, scroll) are currenlty being
                // monitored for changes.
                var isWatchingWindow = false;


                // ---
                // PUBLIC METHODS.
                // ---


                // I start monitoring the given image for visibility
                // and then render it when necessary.
                function addImage(image) {

                    images.push(image);

                    if (!renderTimer) {

                        startRenderTimer();

                    }

                    if (!isWatchingWindow) {

                        startWatchingWindow();

                    }

                }


                // I remove the given image from the render queue.
                function removeImage(image) {

                    // Remove the given image from the render queue.
                    for (var i = 0; i < images.length; i++) {

                        if (images[i] === image) {

                            images.splice(i, 1);
                            break;

                        }

                    }

                    // If removing the given image has cleared the
                    // render queue, then we can stop monitoring
                    // the window and the image queue.
                    if (!images.length) {

                        clearRenderTimer();

                        stopWatchingWindow();

                    }

                }


                // ---
                // PRIVATE METHODS.
                // ---


                // I check the document height to see if it's changed.
                function checkDocumentHeight() {

                    // If the render time is currently active, then
                    // don't bother getting the document height -
                    // it won't actually do anything.
                    if (renderTimer) {

                        return;

                    }

                    var currentDocumentHeight = doc.height();

                    // If the height has not changed, then ignore -
                    // no more images could have come into view.
                    if (currentDocumentHeight === documentHeight) {

                        return;

                    }

                    // Cache the new document height.
                    documentHeight = currentDocumentHeight;

                    startRenderTimer();

                }


                // I check the lazy-load images that have yet to
                // be rendered.
                function checkImages() {

                    // Log here so we can see how often this
                    // gets called during page activity.
                    console.log("Checking for visible images...");

                    var visible = [];
                    var hidden = [];

                    // Determine the window dimensions.
                    var windowHeight = win.height();
                    var scrollTop = win.scrollTop();

                    // Calculate the viewport offsets.
                    var topFoldOffset = scrollTop;
                    var bottomFoldOffset = ( topFoldOffset + windowHeight );

                    // Query the DOM for layout and seperate the
                    // images into two different categories: those
                    // that are now in the viewport and those that
                    // still remain hidden.
                    for (var i = 0; i < images.length; i++) {

                        var image = images[i];

                        if (image.isVisible(topFoldOffset, bottomFoldOffset)) {

                            visible.push(image);

                        } else {

                            hidden.push(image);

                        }

                    }

                    // Update the DOM with new image source values.
                    for (var i = 0; i < visible.length; i++) {

                        visible[i].render();

                    }

                    // Keep the still-hidden images as the new
                    // image queue to be monitored.
                    images = hidden;

                    // Clear the render timer so that it can be set
                    // again in response to window changes.
                    clearRenderTimer();

                    // If we've rendered all the images, then stop
                    // monitoring the window for changes.
                    if (!images.length) {

                        stopWatchingWindow();

                    }

                }


                // I clear the render timer so that we can easily
                // check to see if the timer is running.
                function clearRenderTimer() {

                    clearTimeout(renderTimer);

                    renderTimer = null;

                }


                // I start the render time, allowing more images to
                // be added to the images queue before the render
                // action is executed.
                function startRenderTimer() {

                    renderTimer = setTimeout(checkImages, renderDelay);

                }


                // I start watching the window for changes in dimension.
                function startWatchingWindow() {

                    isWatchingWindow = true;

                    // Listen for window changes.
                    win.on("resize.bnLazySrc", windowChanged);
                    win.on("scroll.bnLazySrc", windowChanged);

                    // Set up a timer to watch for document-height changes.
                    documentTimer = setInterval(checkDocumentHeight, documentDelay);

                }


                // I stop watching the window for changes in dimension.
                function stopWatchingWindow() {

                    isWatchingWindow = false;

                    // Stop watching for window changes.
                    win.off("resize.bnLazySrc");
                    win.off("scroll.bnLazySrc");

                    // Stop watching for document changes.
                    clearInterval(documentTimer);

                }


                // I start the render time if the window changes.
                function windowChanged() {

                    if (!renderTimer) {

                        startRenderTimer();

                    }

                }


                // Return the public API.
                return ({
                    addImage: addImage,
                    removeImage: removeImage
                });

            })();


            // ------------------------------------------ //
            // ------------------------------------------ //


            // I represent a single lazy-load image.
            function LazyImage(element) {

                // I am the interpolated LAZY SRC attribute of
                // the image as reported by AngularJS.
                var source = null;

                // I determine if the image has already been
                // rendered (ie, that it has been exposed to the
                // viewport and the source had been loaded).
                var isRendered = false;

                // I am the cached height of the element. We are
                // going to assume that the image doesn't change
                // height over time.
                var height = null;


                // ---
                // PUBLIC METHODS.
                // ---


                // I determine if the element is above the given
                // fold of the page.
                function isVisible(topFoldOffset, bottomFoldOffset) {

                    // If the element is not visible because it
                    // is hidden, don't bother testing it.

                    //cancel unvisible show
                    //if (!element.is(":visible")) {
                    //    return ( false );
                    //}

                    // If the height has not yet been calculated,
                    // the cache it for the duration of the page.
                    if (height === null) {

                        height = element.height();

                    }

                    // Update the dimensions of the element.
                    var top = element.offset().top;
                    var bottom = ( top + height );

                    // Return true if the element is:
                    // 1. The top offset is in view.
                    // 2. The bottom offset is in view.
                    // 3. The element is overlapping the viewport.
                    return (
                        (
                            ( top <= bottomFoldOffset ) &&
                            ( top >= topFoldOffset )
                        )
                        ||
                        (
                            ( bottom <= bottomFoldOffset ) &&
                            ( bottom >= topFoldOffset )
                        )
                        ||
                        (
                            ( top <= topFoldOffset ) &&
                            ( bottom >= bottomFoldOffset )
                        )
                    );

                }


                // I move the cached source into the live source.
                function render() {

                    isRendered = true;

                    renderSource();

                }


                // I set the interpolated source value reported
                // by the directive / AngularJS.
                function setSource(newSource) {

                    source = newSource;

                    if (isRendered) {

                        renderSource();

                    }

                }


                // ---
                // PRIVATE METHODS.
                // ---


                // I load the lazy source value into the actual
                // source value of the image element.
                function renderSource() {

                    element[0].src = source;

                }


                // Return the public API.
                return ({
                    isVisible: isVisible,
                    render: render,
                    setSource: setSource
                });

            }


            // ------------------------------------------ //
            // ------------------------------------------ //


            // I bind the UI events to the scope.
            function link($scope, element, attributes) {

                var lazyImage = new LazyImage(element);

                // Start watching the image for changes in its
                // visibility.
                lazyLoader.addImage(lazyImage);


                // Since the lazy-src will likely need some sort
                // of string interpolation, we don't want to
                attributes.$observe(
                    "bnLazySrc",
                    function (newSource) {

                        lazyImage.setSource(newSource);

                    }
                );


                // When the scope is destroyed, we need to remove
                // the image from the render queue.
                $scope.$on(
                    "$destroy",
                    function () {

                        lazyLoader.removeImage(lazyImage);

                    }
                );

            }


            // Return the directive configuration.
            return ({
                link: link,
                restrict: "A"
            });

        }
    )
    .directive('linetouristcheck', function ($http, $cookieStore, storage) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                }).each(function () {
                    if ($(this).scope().tourist.check) {
                        $(this).iCheck("check");
                    }
                });
                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parentScope = scope.$parent.$parent;
                    var selectedTouristList = parentScope.selectedAdultTourist;
                    //var num = parentScope.adultNum;
                    var date = new Date();
                    if (Number((date.getYear() + 1900) * 10000 + (date.getMonth() + 1) * 100 + date.getDate()) - Number(scope.tourist.idNumber.substr(6, 8)) < 12 * 10000) {
                        selectedTouristList = parentScope.selectedChildTourist;
                        //num = parentScope.childNum;
                    }
                    //if (selectedTouristList.length < num) {
                        scope.tourist.check = true;
                        selectedTouristList.push(scope.tourist);
                    //} else {
                    //    angular.element(event.target).iCheck("uncheck");
                    //}
                    $("#selectNum").html(parentScope.selectedAdultTourist.length + parentScope.selectedChildTourist.length);

                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parentScope = scope.$parent.$parent;
                    var selectedTouristList = parentScope.selectedAdultTourist;
                    var date = new Date();
                    if (Number((date.getYear() + 1900) * 10000 + (date.getMonth() + 1) * 100 + date.getDate()) - Number(scope.tourist.idNumber.substr(6, 8)) < 12 * 10000) {
                        selectedTouristList = parentScope.selectedChildTourist;
                    }
                    scope.tourist.check = false;
                    angular.forEach(selectedTouristList, function(tourist, i, list) {
                        if (tourist.touristId == scope.tourist.touristId) {
                            list.splice(i, 1);
                            return true;
                        }
                    });
                    $("#selectNum").html(parentScope.selectedAdultTourist.length + parentScope.selectedChildTourist.length);
                });
            }
        }
    })
    .directive('tickettouristcheck', function ($http, $cookieStore, storage) {
        return {
            restrict: "AE",
            link: function (scope, element, attrs, storage) {
                element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                    increaseArea: '20%' // optional
                }).each(function () {
                    if ($(this).scope().tourist.check) {
                        $(this).iCheck("check");
                    }
                });
                element.on('ifChecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parentScope = scope.$parent.$parent;
                    scope.tourist.check = true;
                    parentScope.selectedtourist.push(scope.tourist);
                    $("#selectNum").html(parentScope.selectedtourist.length);
                });
                element.on('ifUnchecked', function (event) {
                    var scope = angular.element(event.currentTarget).scope();
                    var parentScope = scope.$parent.$parent;
                    scope.tourist.check = false;
                    angular.forEach(parentScope.selectedtourist, function (tourist, i, list) {
                        if (tourist.touristId == scope.tourist.touristId) {
                            list.splice(i, 1);
                            return true;
                        }
                    });
                    $("#selectNum").html(parentScope.selectedtourist.length);
                });
            }
        }
    })
    .directive("scrollToTop", function () {
        return {
            restrict: "AE",
            link: function (scope, element) {
                element.on("click", function () {
                    $('html,body').animate({"scrollTop": 0}, 1000);
                });
            }
        }
    })
    .directive("showMore", function ($interval) {
        return {
            restrict: "A",
            scope: {
                showHeight: "@",
                completeFlag: "@",
                showText: "@",
                hideText: "@"
            },
            link: function (scope, element) {
                var prev = element.prev();
                var i = $interval(function () {
                    if (scope.completeFlag == "true") {
                        scope.showMore = false;
                        if (prev.height() > Number(scope.showHeight)) {
                            prev.css("max-height", scope.showHeight + "px").css("overflow", "hidden");
                            element.show().click(function () {
                                if (scope.showMore) {
                                    prev.css("max-height", scope.showHeight + "px");
                                    element.find("a").text(scope.hideText);
                                } else {
                                    prev.css("max-height", "none");
                                    element.find("a").text(scope.showText);
                                }
                                scope.showMore = !scope.showMore;
                            });
                        }
                        $interval.cancel(i);
                    }
                }, 500);
            }
        }
    })
    .directive("fullCalendar", function () {
        return {
            restrict: "A",
            scope: {
                calendarData: "@",
                calendarEventClick: "&",
                calendarDayClick: "&",
                calendarCalEvent: "="
            },
            link: function (scope, element) {
                element.fullCalendar({
                    header: {
                        left: 'prev',
                        center: 'title',
                        right: 'next'
                    },
                    weekMode: "fixed",
                    selectHelper: function () {
                    },
                    lang: 'zh-cn',
                    buttonIcons: true, // show the prev/next text
                    buttonText: {prev: '<', next: '>'},
                    weekNumbers: false,
                    fixedWeekCount: false,
                    selectable: true,
                    unselectAuto: false,
                    eventLimit: true, // allow "more" link when too many events
                    monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
                    dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"],
                    today: ["今天"],
                    dayClick: function (date, allDay, jsEvent, view) {
                        scope.$apply(function () {
                            scope.calendarDayClick({date: date.format()});
                        });
                    },
                    eventClick: function (calEvent, jsEvent, view) {
                        scope.$apply(function () {
                            scope.calendarEventClick({calEvent: calEvent});
                            element.fullCalendar('select', $.fullCalendar.moment(calEvent.date));
                        });
                    }
                });
                scope.$watch("calendarData", function () {
                    element.fullCalendar('removeEvents');
                    if (scope.calendarData != null && scope.calendarData != "") {
                        var calendarData = JSON.parse(scope.calendarData);
                        element.fullCalendar('addEventSource', calendarData);
                        scope.calendarEventClick({calEvent: calendarData[0]});
                        element.fullCalendar('select', $.fullCalendar.moment(calendarData[0].date));
                    }
                });
            }
        }
    })
    .directive("numChanger", function () {
        return {
            restrict: "A",
            scope: {
                minNum: "@",
                maxNum: "@",
                scopeNum: "=",
                minusElementId: "@",
                addElementId: "@"
            },
            link: function (scope) {
                scope.minNum = parseInt(scope.minNum);
                scope.maxNum = parseInt(scope.maxNum);
                var minus = $("#" + scope.minusElementId);
                minus.click(function () {
                    scope.$apply(function () {
                        if (scope.scopeNum <= scope.minNum) {
                            scope.scopeNum = scope.minNum;
                        } else {
                            scope.scopeNum--;
                        }
                        if (scope.scopeNum == scope.minNum) {
                            minus.addClass("disabled");
                        }
                    })
                });
                $("#" + scope.addElementId).click(function () {
                    scope.$apply(function () {
                        if (scope.scopeNum >= scope.maxNum) {
                            scope.scopeNum = scope.maxNum;
                        } else {
                            scope.scopeNum++;
                        }
                        minus.removeClass("disabled");
                    });
                });
            }
        }
    })
    .directive('goLineList', function ($state) {
        return {
            restrict: "AE",
            scope: {
                cityCode: "="
            },
            link: function (scope, elemnt) {
                elemnt.click(function () {
                    var params = {
                        keyword: null,
                        cityCode: scope.cityCode
                    };
                    $state.go('lineList', {params: JSON.stringify(params)});
                });
            }
        }
    })
    .directive('goScenicList', function($state) {
        return {
            restrict: "AE",
            scope: {
                cityCode: "=",
                sort: "=",
                label: "="
            },
            link: function (scope, element) {
                element.click(function () {
                    var label =  encodeURIComponent(scope.label);
                    console.log(label);
                    var params = {
                        cityCode: scope.cityCode,
                        sort: scope.sort,
                        sortOrder: scope.sortOrder,
                        label:label
                    };
                    $state.go('scenicList', {params: JSON.stringify(params)});
                });
            }
        };
    })
    .directive('travalPosition', function () {
        return {
            restrict: "AE",
            link: function (scope, element) {
                var tr_distanceTop = $('#tr_list').offset().top - 45;
                var tr_distanceBottom=$('.sub-tabs').offset().top;
                var tr_window = $(window);
                tr_window.scroll(function() {
                    var tr_scrlloHeight = tr_window.scrollTop();
                    if(tr_scrlloHeight > tr_distanceTop) {
                        $('#tr_list').css({'position':'fixed','left':0,'top':'45px','width':'100%','z-index':'999','background-color':'white'});
                        $('.sub-tabs').css({'position':'fixed','left':0,'top':'90px','z-index':'999','width':'100%'});
                    } else {
                        $('#tr_list').css({'position':'static'});
                        $('.sub-tabs').css({'position':'static'});
                    }
                })
            }
        }
    })
    .directive('goBack', function ($state) {
        return {
            restrict: "AE",
            link: function (scope, element) {
                element.click(function () {
                    history.go(-1);
                });
            }
        }
    })
    .directive('callBackSelectCity', function($state, storage) {
        return {
            restrict: "AE",
            scope: {
                cityCodeKey: "=",
                cityNameKey: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    storage.set('reurl', document.URL);
                    storage.set('cityCodeKey', scope.cityCodeKey);
                    storage.set('cityNameKey', scope.cityNameKey);
                    $state.go('selectCity');
                });
            }
        }
    })
    .directive('searchDir', function($state, storage) {
        return {
            restrict: "AE",
            scope: {
                preUrl: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    storage.set('preUrl', scope.preUrl);
                    $state.go('search');
                });
            }
        }
    })
    .directive("simpleShowMore", function () {
        return {
            restrict: "A",
            scope: {
                showHeight: "@",
                showClass: "@"
            },
            link: function (scope, element) {
                scope.showMore = false;
                element.click(function () {
                    if (scope.showMore) {
                        element.css("max-height", scope.showHeight + "px").removeClass(scope.showClass);
                    } else {
                        element.css("max-height", "none").addClass(scope.showClass);
                    }
                    scope.showMore = !scope.showMore;
                });
            }
        }
    })
    .directive("goPersonal", function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function () {
                    location.href = GetUrl.personal;
                })
            }
        }
    })
    .directive("selectHotelDate", function() {
        return {
            restrict: "AE",
            scope: {
                isFirst: "=",
                hotelDateRange: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    $('#J_hotel_header').hide();
                    $('#hotel_main').addClass('hide');
                    $('#calendarArea').removeClass('hide');
                    // 点开酒店日历
                    $('#calendar').click();
                    // 自动加一个月
                    if (scope.isFirst) {
                        scope.isFirst = false;
                        scope.hotelDateRange.nextMonthFun();
                    }
                });
            }
        }
    })
    .directive("hotelDateBack", function () {
        return {
            restrict: "AE",
            scope: {
                hotelDateRange: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    $('#J_hotel_header').show();
                    $('#hotel_main').removeClass('hide');
                    $('#calendarArea').addClass('hide');
                    scope.hotelDateRange.close();
                });
            }
        }
    })
    .directive('hotelIndexStarArea', function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function () {
                    if ($('.price-star-area').hasClass('hide')) {
                        $('.price-star-area').removeClass('hide');
                    } else {
                        $('.price-star-area').addClass('hide');
                    }
                });
            }
        }
    })
    .directive('noHotelIndexStarArea', function() {
        return {
            restrict: 'AE',
            link: function(scope, element) {
                element.click(function (evement) {
                    event.stopPropagation();
                });
            }
        }
    })
    .directive('showHotelPlusConditionArea', function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function (event) {
                    if ($('#hotel_plus_condition_area').hasClass('active')) {
                        $('#hotel_plus_condition_area').removeClass('active');
                    } else {
                        $('#hotel_plus_condition_area').addClass('active');
                    }
                })
            }
        }
    })
    .directive('noHotelPlusConditionArea', function() {
        return {
            restrict: 'AE',
            link: function(scope, element) {
                element.click(function (evement) {
                    event.stopPropagation();
                });
            }
        }
    })
    .directive('selectHotelPrice', function() {
        return {
            restrict: "AE",
            scope: {
                minPrice: "=",
                maxPrice: "=",
                minHotelPrice: "=",
                maxHotelPrice: "=",
                priceInfo: "="
            },
            link: function(scope, element) {
                element.click(function(event) {
                    event.stopPropagation();
                    if (element.hasClass('on')) {
                        return;
                    }
                    scope.$apply(function () {
                        scope.priceInfo = element.html();
                        if(scope.minPrice != null) {
                            scope.minHotelPrice = scope.minPrice;
                        } else {
                            scope.minHotelPrice = null;
                        }
                        if (scope.maxPrice > 0) {
                            scope.maxHotelPrice = scope.maxPrice;
                        } else {
                            scope.maxHotelPrice = null;
                        }
                    });
                    element.siblings().removeClass('on');
                    element.addClass('on');
                    //console.log(scope.minHotelPrice + "==" + scope.maxHotelPrice);
                });
            }
        }
    })
    .directive('selectHotelStar', function() {
        return {
            restrict: "AE",
            scope: {
                star: "=",
                selectStarArr: "=",
                starInfoArr: "="
            },
            link: function(scope, element) {
                element.click(function(event) {
                    event.stopPropagation();
                    scope.$apply(function () {
                        if (element.hasClass('on') && scope.star != null) {
                            var removeIndex = scope.selectStarArr.indexOf(scope.star);
                            var removeStarInfoIndex = scope.starInfoArr.indexOf(element.html());
                            //console.log("index: " + removeIndex);
                            if (removeIndex != -1) {
                                scope.selectStarArr.splice(removeIndex, 1);
                            }
                            if (removeStarInfoIndex != -1) {
                                scope.starInfoArr.splice(removeStarInfoIndex, 1);
                            }
                            element.removeClass('on');
                            if (element.parent('ul').find('li.on').length == 0) {
                                element.siblings().eq(0).addClass('on');
                                scope.selectStarArr= [];
                            }
                        } else {
                            if (scope.star != null) {
                                //console.log("push star " + scope.star);
                                scope.selectStarArr.push(scope.star);
                                scope.starInfoArr.push(element.html());
                                element.siblings().eq(0).removeClass('on');
                                element.addClass('on');
                            } else {
                                element.siblings().removeClass('on');
                                element.addClass('on');
                                scope.selectStarArr= [];
                                scope.starInfoArr = [];
                            }
                        }
                    });
                    //console.log(scope.selectStarArr);
                    //console.log(scope.starInfoArr);
                });
            }
        }
    })
    .directive('selectSingleHotelStar', function() {
        return {
            restrict: "AE",
            scope: {
                star: "=",
                selectStarArr: "=",
                starInfoArr: "="
            },
            link: function(scope, element) {
                element.click(function(event) {
                    event.stopPropagation();
                    if (element.hasClass('on')) {
                        return;
                    }
                    scope.$apply(function () {
                        // 单选星级模式, 每次清空
                        scope.selectStarArr= [];
                        scope.starInfoArr = [];
                        if (scope.star != null) {
                            //console.log("push star " + scope.star);
                            scope.selectStarArr.push(scope.star);
                            scope.starInfoArr.push(element.html());
                            element.siblings().removeClass('on');
                            element.addClass('on');
                        } else {
                            element.siblings().removeClass('on');
                            element.addClass('on');
                            scope.selectStarArr = [];
                            scope.starInfoArr = [];
                        }
                    });
                    //console.log(scope.selectStarArr);
                    //console.log(scope.starInfoArr);
                });
            }
        }
    })
    .directive('clearHotelPriceInfo', function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function (event) {
                    event.stopPropagation();
                    $('#hotel_price_list').children('li').removeClass('on');
                    $('#hotel_price_list').children('li').eq(0).addClass('on');
                    $('#hotel_star_list').children('li').removeClass('on');
                    $('#hotel_star_list').children('li').eq(0).addClass('on');
                    $('#hotel_price_show').addClass('show');
                    $('#clear_hotel_price').addClass('hide');
                });
            }
        }
    })
    .directive('clearHotelPlusInfo', function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function (event) {
                    event.stopPropagation();
                    $('#hotel-plus-condition-cat').children('li').removeClass('active');
                    $('#hotel-plus-condition-cat').children('li').eq(0).addClass('active');
                    $('#hotel-region-list').children('li').removeClass('active');
                    $('#hotel-region-list').children('li').eq(0).addClass('active')
                    $('#hotel-brand-list').children('li').removeClass('active');
                    $('#hotel-brand-list').children('li').eq(0).addClass('active')
                    $('#hotel-service-list').children('li').removeClass('active');
                    $('#hotel-service-list').children('li').eq(0).addClass('active')
                    $('#hotel_plus_show').addClass('show');
                    $('#clear_hotel_plus').addClass('hide');
                });
            }
        }
    })
    .directive('switchHotelPlusConditionTab', function() {
        return {
            restrict: "AE",
            link: function(scope, element) {
                element.click(function() {
                    if (element.hasClass('active')) {
                        return;
                    }
                    var index = element.index();
                    element.siblings().removeClass('active');
                    element.addClass('active');
                    $('.filter-sidebar-content').removeClass('active');
                    $('.filter-sidebar-content').eq(index).addClass('active');
                });
            }
        }
    })
    .directive('selectHotelRegion', function() {
        return {
            restrict: "AE",
            scope: {
                regionId: "=",
                regionName: "=",
                hotelRegionId: "=",
                hotelRegionName: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    if (element.hasClass('active')) {
                        return;
                    }
                    scope.$parent.$parent.$apply(function () {
                        element.addClass('active');
                        element.siblings().removeClass('active');
                        if (scope.regionId != null) {
                            scope.$parent.$parent.hotelRegionId = scope.regionId;
                            scope.$parent.$parent.hotelRegionName = scope.regionName;
                        } else {
                            scope.$parent.hotelRegionId = null;
                            scope.$parent.hotelRegionName = null;
                        }
                    });
                });
            }
        }
    })
    .directive('selectHotelBrand', function() {
        return {
            restrict: "AE",
            scope: {
                brandId: "=",
                brandName: "=",
                hotelBrandId: "=",
                hotelBrandName: "="
            },
            link: function(scope, element) {
                element.click(function () {
                    if (element.hasClass('active')) {
                        return;
                    }
                    scope.$parent.$parent.$apply(function () {
                        element.addClass('active');
                        element.siblings().removeClass('active');
                        if (scope.brandId != null) {
                            scope.$parent.$parent.hotelBrandId = scope.brandId;
                            scope.$parent.$parent.hotelBrandName = scope.brandName;
                        } else {
                            scope.$parent.hotelBrandId = null;
                            scope.$parent.hotelBrandName = null;
                        }
                    });
                });
            }
        }
    })
    .directive('selectHotelService', function() {
        return {
            restrict: "AE",
            scope: {
                serviceId: "=",
                serviceName: "=",
                hotelServiceId: "=",
                hotelServiceName: "="
            },
            link: function(scope, element) {
                element.click(function() {
                    if (element.hasClass('active')) {
                        return;
                    }
                    scope.$parent.$parent.$apply(function () {
                        element.addClass('active');
                        element.siblings().removeClass('active');
                        if (scope.serviceId != null) {
                            scope.$parent.$parent.hotelServiceId = scope.serviceId;
                            scope.$parent.$parent.hotelServiceName = scope.serviceName;
                        } else {
                            scope.$parent.hotelServiceId = null;
                            scope.$parent.hotelServiceName = null;
                        }
                    });
                });
            }
        }
    })
    .directive('moreDest', function($state, storage) {
        return {
            restrict: "AE",
            //scope: {
            //    preUrl: "="
            //},
            link: function(scope, element) {
                element.click(function () {
                    var preUrl = storage.get("preUrl");
                    storage.set('preUrl', preUrl);
                    $state.go('selectCity');
                });
            }
        }
    })
    .directive('proTabChange', function($state, storage) {
        return {
            restrict:"AE",
            scope: {
                tabIndex:"=",
                keyword:"=",
                cityCode:"="
            },
            link:function(scope, element) {
                element.click(function() {

                    if (scope.tabIndex == 1) {
                        scope.tabName = "精品定制";
                        scope.listLine(1);
                        scope.jingpinShow = true;
                        scope.zizhuShow = false;
                        scope.zijiaShow = false;
                        scope.gentuanShow = false;
                        $("#jingpinLi").addClass("top_list_bg");
                        $("#zizhuLi").removeClass("top_list_bg");
                        $("#zijiaLi").removeClass("top_list_bg");
                        $("#gentuanLi").removeClass("top_list_bg");
                    } else if (scope.tabIndex == 2) {
                        scope.tabName = "自助游";
                        scope.listLine(2);
                        scope.jingpinShow = false;
                        scope.zizhuShow = true;
                        scope.zijiaShow = false;
                        scope.gentuanShow = false;
                        $("#jingpinLi").removeClass("top_list_bg");
                        $("#zizhuLi").addClass("top_list_bg");
                        $("#zijiaLi").removeClass("top_list_bg");
                        $("#gentuanLi").removeClass("top_list_bg");
                    } else if (scope.tabIndex == 3) {
                        scope.tabName = "自驾游";
                        scope.listLine(3);
                        scope.jingpinShow = false;
                        scope.zizhuShow = false;
                        scope.zijiaShow = true;
                        scope.gentuanShow = false;
                        $("#jingpinLi").removeClass("top_list_bg");
                        $("#zizhuLi").removeClass("top_list_bg");
                        $("#zijiaLi").addClass("top_list_bg");
                        $("#gentuanLi").removeClass("top_list_bg");
                    } else {
                        scope.tabName = "跟团游";
                        scope.listLine(4);
                        scope.jingpinShow = false;
                        scope.zizhuShow = false;
                        scope.zijiaShow = false;
                        scope.gentuanShow = true;
                        $("#jingpinLi").removeClass("top_list_bg");
                        $("#zizhuLi").removeClass("top_list_bg");
                        $("#zijiaLi").removeClass("top_list_bg");
                        $("#gentuanLi").addClass("top_list_bg");
                    }

                });
            }
        }
    })
    .directive('hotelShaiXuan', function($state, storage) {
        return {
            restrict: "AE",
            scope: {
                selId: "=",
                type: "="
            },
            link: function(scope, element) {
                element.click(function () {

                    if (scope.type == "brand") {
                        var brandIdList = storage.get("brandIdList");
                        if (!brandIdList) {
                            brandIdList = [];
                        }

                        var brandLabelArr = $(".brand-ul").children("li").children("label");
                        $.each(brandLabelArr, function(i, label) {
                            $(label).removeClass("icon-sure");
                            $(label).addClass("icon-no-select");
                        });

                        $("#brand_"+scope.selId+"").removeClass("icon-no-select");
                        $("#brand_"+scope.selId+"").addClass("icon-sure");

                        //for (var i = 0; i < brandIdList.length; i++) {
                        //    if (brandIdList[i] == scope.selId) {
                        //        brandIdList.splice(i, 1);
                        //    }
                        //}
                        if (scope.selId != 0) {
                            brandIdList = [scope.selId];
                        } else {
                            brandIdList = [];
                        }
                        storage.set("brandIdList", brandIdList);

                        console.log("brandIdList:" + brandIdList);

                        /*

                        if (element.children("label").hasClass("icon-sure")) {
                            if (scope.selId != 0) {
                                $("#brand_"+scope.selId+"").removeClass("icon-sure");
                                $("#brand_"+scope.selId+"").addClass("icon-no-select");
                            } else {
                                brandIdList = [];
                            }
                            for (var i = 0; i < brandIdList.length; i++) {
                                if (brandIdList[i] == scope.selId) {
                                    brandIdList.splice(i, 1);
                                }
                            }
                            if (brandIdList.length == 0) {
                                $("#brand_0").removeClass("icon-no-select");
                                $("#brand_0").addClass("icon-sure");
                            }
                            storage.set("brandIdList", brandIdList);
                            console.log("brand_:" + brandIdList);
                        } else {
                            if (scope.selId != 0) {
                                $("#brand_0").removeClass("icon-sure");
                                $("#brand_0").addClass("icon-no-select");
                            } else if (scope.selId == 0) {
                                var brandLabelArr = $(".brand-ul").children("li").children("label");
                                $.each(brandLabelArr, function(i, label) {
                                    $(label).removeClass("icon-sure");
                                    $(label).addClass("icon-no-select");
                                });
                            }
                            $("#brand_"+scope.selId+"").removeClass("icon-no-select");
                            $("#brand_"+scope.selId+"").addClass("icon-sure");

                            if (scope.selId != 0) {
                                brandIdList.push(scope.selId);
                            } else {
                                brandIdList = [];
                            }
                            console.log("brand_:" + brandIdList);
                            storage.set("brandIdList", brandIdList);
                        }

                        */


                    } else if (scope.type == "service") {

                        var serviceIdList = storage.get("serviceIdList");
                        if (!serviceIdList) {
                            serviceIdList = [];
                        }

                        var serviceLabelArr = $(".service-ul").children("li").children("label");
                        $.each(serviceLabelArr, function(i, label) {
                            $(label).removeClass("icon-sure");
                            $(label).addClass("icon-no-select");
                        });

                        $("#service_"+scope.selId+"").removeClass("icon-no-select");
                        $("#service_"+scope.selId+"").addClass("icon-sure");



                        if (scope.selId != 0) {
                            serviceIdList = [scope.selId];
                        } else {
                            serviceIdList = [];
                        }
                        storage.set("serviceIdList", serviceIdList);

                        console.log("serviceIdList:" + serviceIdList);

                        /*

                        if (element.children("label").hasClass("icon-sure")) {

                            if (scope.selId != 0) {
                                $("#service_"+scope.selId+"").removeClass("icon-sure");
                                $("#service_"+scope.selId+"").addClass("icon-no-select");
                            } else {
                                serviceIdList = [];
                            }
                            for (var i = 0; i < serviceIdList.length; i++) {
                                if (serviceIdList[i] == scope.selId) {
                                    serviceIdList.splice(i, 1);
                                }
                            }
                            if (serviceIdList.length == 0) {
                                $("#service_0").removeClass("icon-no-select");
                                $("#service_0").addClass("icon-sure");
                            }
                            storage.set("serviceIdList", serviceIdList);
                            console.log("service_:"+serviceIdList);

                        } else {
                            if (scope.selId != 0) {
                                $("#service_0").removeClass("icon-sure");
                                $("#service_0").addClass("icon-no-select");

                            } else if (scope.selId == 0) {
                                var serviceLabelArr = $(".service-ul").children("li").children("label");
                                $.each(serviceLabelArr, function(i, label) {
                                    $(label).removeClass("icon-sure");
                                    $(label).addClass("icon-no-select");
                                });
                            }
                            $("#service_"+scope.selId+"").removeClass("icon-no-select");
                            $("#service_"+scope.selId+"").addClass("icon-sure");

                            if (scope.selId != 0) {
                                serviceIdList.push(scope.selId);
                            } else {
                                serviceIdList = [];
                            }
                            storage.set("serviceIdList", serviceIdList);
                            console.log("service_:"+serviceIdList);

                        }



                        */
                    } else if (scope.type == "regin") {

                        var regionIdList = storage.get("regionIdList");
                        if (!regionIdList) {
                            regionIdList = null;
                        }

                        var reginLabelArr = $(".regin-ul").children("li").children("label");
                        $.each(reginLabelArr, function(i, label) {
                            $(label).removeClass("icon-sure");
                            $(label).addClass("icon-no-select");
                        });

                        $("#regin_"+scope.selId+"").removeClass("icon-no-select");
                        $("#regin_"+scope.selId+"").addClass("icon-sure");


                        if (scope.selId != 0) {
                            regionIdList = scope.selId;
                        } else {
                            regionIdList = null;
                        }
                        storage.set("regionIdList", regionIdList);

                        console.log("regionIdList:" + regionIdList);


                        /*

                        if (element.children("label").hasClass("icon-sure")) {

                            if (scope.selId != 0) {
                                $("#regin_"+scope.selId+"").removeClass("icon-sure");
                                $("#regin_"+scope.selId+"").addClass("icon-no-select");
                            } else {
                                regionIdList = [];
                            }
                            for (var i = 0; i < regionIdList.length; i++) {
                                if (regionIdList[i] == scope.selId) {
                                    regionIdList.splice(i, 1);
                                }
                            }
                            if (regionIdList.length == 0) {
                                $("#regin_0").removeClass("icon-no-select");
                                $("#regin_0").addClass("icon-sure");
                            }
                            storage.set("regionIdList", regionIdList);
                            console.log("regin_:"+regionIdList);

                        } else {
                            if (scope.selId != 0) {
                                $("#regin_0").removeClass("icon-sure");
                                $("#regin_0").addClass("icon-no-select");

                            } else if (scope.selId == 0) {
                                var reginLabelArr = $(".regin-ul").children("li").children("label");
                                $.each(reginLabelArr, function(i, label) {
                                    $(label).removeClass("icon-sure");
                                    $(label).addClass("icon-no-select");
                                });
                            }
                            $("#regin_"+scope.selId+"").removeClass("icon-no-select");
                            $("#regin_"+scope.selId+"").addClass("icon-sure");

                            if (scope.selId != 0) {
                                regionIdList.push(scope.selId);
                            } else {
                                regionIdList = [];
                            }
                            storage.set("regionIdList", regionIdList);
                            console.log("regin_:"+regionIdList);

                        }


                        */
                    }
                });
            }

        }
    })
;


