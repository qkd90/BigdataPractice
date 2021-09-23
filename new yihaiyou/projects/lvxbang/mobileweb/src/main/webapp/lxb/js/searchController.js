/**
 * Created by dy on 2016/7/25.
 */
var searchModule = angular.module("searchModule", ['angularLocalStorage']);


searchModule.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);

searchModule.filter("cutByLength", function() {
    return function (input, maxLength, hightWord) {

        input = input || '';
        var output = '';

        if (maxLength == null || maxLength < 0 ) {
            maxLength = 0;
        }
        if (input.length > maxLength) {
            output = input.substr(0, maxLength) + "...";
        } else {
            output = input;
        }

        var hightHtml = '<font style="color: #ff6000;">' + hightWord + '</font>';

        output = output.replace(hightWord, hightHtml);
        return output;
    }
});

searchModule.directive("hTitle",function($state) {
   return {
       restrict:"AE",
       scope: {
           keyword:"=",
           proType:"="
       },
       link:function(scope, element) {
           element.bind("click", function() {
               if (scope.proType == "lineList") {
                  var params = {
                       keyword: scope.keyword,
                       cityCode: null
                   };
                   $state.go("lineList", {params: JSON.stringify(params)});
               } else if (scope.proType == "scenicList") {
                   var params = {
                       keyword: scope.keyword,
                       cityCode: null
                   };
                   $state.go("scenicList", {params: JSON.stringify(params)});
               }
           });
       }

   }
});

searchModule.controller("SearchCtrl", function($scope, $http, storage, $state, $filter) {

    $("#input_search").focus();


    var preUrl = storage.get("preUrl");
    if (!preUrl) {
        preUrl = "lineList";
    }
    $scope.goToSearch = function(){
        storage.set("preUrl", preUrl);
        $state.go("selectCity",{});
    };

    console.log(preUrl);


    $scope.backPage = function() {
        window.history.back();
    };
    $scope.resultList = [];
    $scope.hid_show = false;
    $scope.cutLenght = function (item) {
        if (item.name.length > 18) {
            return item.name.substring(0, 18) + "..."
        }
        return item.name;
    };


    var lineHistory = storage.get(LXB_KEY.searchLineKey);
    //console.log(lineHistory);
    if (lineHistory) {
        $scope.hisKeywordList = lineHistory;
    } else {
        lineHistory = [];
    }

    $scope.hotAreaList = [];
    $http.post(LXB_URL.mHotArea, {})
        .success(function(data) {
            if (data.success) {
                $scope.hotAreaList = data.sortMap.hot;
            }
        })
        .error(function(data) {
            console.log("系统错误！");
        });

    $scope.keyword;
    $scope.searchShow = false;
    $scope.hotShow = true;
    $scope.darkShow = false;

    $scope.selectCityCode = function(cityCode, name) {
        //$scope.keyword = selectKeyword;
        var params = {};
        if (preUrl == "lineList") {
            params = {
                keyword: null,
                cityCode: cityCode
            }
        } else if (preUrl == "scenicList") {
            params = {
                keyword: null,
                cityCode: cityCode
            }
        }
        $state.go(preUrl, {params: JSON.stringify(params)});
        if (name) {
            $scope.setHistoryStorage(name);
        }

    }

    //选择关键词
    $scope.selectKeyword = function(selectKeyword) {
        //$scope.keyword = selectKeyword;
        var params = {
            keyword: selectKeyword,
            cityCode: null
        }

        $state.go(preUrl, {params: JSON.stringify(params)});
    };

    //查询关键词
    $scope.searchLine = function() {

        var tempKeyWord = "厦门";
        if ($scope.keyword) {
            tempKeyWord = $scope.keyword;
        }
        var params = {
            keyword: tempKeyWord,
            cityCode: null
        }
        $state.go(preUrl, {params: JSON.stringify(params)});

        $scope.setHistoryStorage(tempKeyWord);
    };

    $scope.$watch("hid_show", function(newValue, oldValue) {
        if (newValue) {
            //$scope.setDarkDiv();
            //console.log($scope.hid_show);
        }
    });

    $scope.index = 0;
    $scope.listTime = function() {
        var the_timeout = null;

        for (var i=0; i<2; i++) {
            //$scope.setDarkDiv();
        }
    };

    $scope.setDarkDiv = function() {
            var height =getSeachlist()+45;
            console.log(height);
            var winHeight = getWinHeight();
            $scope.myObj = {
                "height" : (winHeight-height)/winHeight * 100 + "%",
                "top" : height + "px"
            }
            //clearInterval(timer);
    };


    $scope.scenicShow = false;
    $scope.lineShow = false;
    $scope.lisChange = function() {
        $scope.scenicList = [];
        $scope.lineList = [];
        if ($scope.keyword.length) {
            $http.post(LXB_URL.search_line, {keyword:$scope.keyword})
                .success(function(data) {
                    if (data.lineIsShow || data.scenicIsShow) {
                        $scope.searchShow = true;
                        $scope.darkShow = true;
                        $scope.hotShow = false;
                        if (data.lineIsShow) {
                            lineListTemp = data.lineList;
                            $scope.lineShow = true;
                            $scope.lineList = lineListTemp;
                        } else {
                            $scope.lineShow = false;
                        }

                        if (data.scenicIsShow) {
                            sceniListTemp = data.scenicList;
                            $scope.scenicShow = true;
                            $scope.scenicList = sceniListTemp;

                        } else {
                            $scope.scenicShow = false;
                        }
                    } else {
                        $scope.scenicList = [];
                        $scope.lineList = [];
                        $scope.searchShow = false;
                        $scope.darkShow = false;
                        $scope.hotShow = true;
                    }
                })
                .error(function(data) {
                    $scope.scenicList = [];
                    $scope.lineList = [];
                    $scope.searchShow = false;
                    $scope.darkShow = false;
                    $scope.hotShow = true;
                    console.log("系统错误！");
                });

        } else {
            $scope.scenicList = [];
            $scope.lineList = [];
            $scope.searchShow = false;
            $scope.darkShow = false;
            $scope.hotShow = true;
        }


        if ($scope.keyword.length <= 0) {
            $scope.scenicList = [];
            $scope.lineList = [];
            $scope.searchShow = false;
            $scope.darkShow = false;
            $scope.hotShow = true;
        }
    };
    //清楚历史记录
    $scope.clearHis = function() {
        storage.set(LXB_KEY.searchLineKey, []);
        $scope.hisKeywordList = [];
        lineHistory = [];
    };

    $scope.setHistoryStorage = function(temKeyword) {
        var setHisObj = {};
        setHisObj['keyword'] = temKeyword;
        if (lineHistory.length <= 0) {
            lineHistory.push(setHisObj);
        } else {
            var flag = true;
            for (var i=0; i<lineHistory.length; i++) {
                var item = lineHistory[i];
                if (item.keyword != temKeyword) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                lineHistory.push(setHisObj);
            }
        }
        lineHistory = sortArr(lineHistory);
        storage.set(LXB_KEY.searchLineKey, lineHistory);
        $scope.hisKeywordList = lineHistory;
    }



});

function sortArr(arr) {
    var result=[];

    var j = 0;
    for (var i=arr.length; i > 0; i--) {
        if (j < 5) {
            result[j] = arr[i-1];
        }
        j++;
    }
    return result;
};


function getWinHeight() {
    return window.screen.availHeight;
};

function getWinWidth() {
    return window.screen.availWidth;
};
function getSeachlist(){
    var listDiv = document.getElementById('seachList');
    return listDiv.offsetHeight;
}

