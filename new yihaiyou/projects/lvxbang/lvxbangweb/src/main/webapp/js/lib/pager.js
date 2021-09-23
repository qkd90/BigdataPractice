function Pager(options) {
    var pager = this;

    var defaults =
    {
        mode: 1,                    //类型：1：分页计数分开，2：分页计数合并
        container: ".m-pager",      //容器选择器
        pageNo: 1,                  //页码
        pageSize: 10,               //每页显示条数
        pageCount: 0,               //总页数
        countUrl: "",               //查询总数的方法
        resultCountFn: null,        //解析总数查询结果的方法
        pageRenderFn: null,         //页面渲染方法
        total: 0,                   //总条数
        pageText: '<label class="total">共{page}页/{count}条</label>',
        prevButton: '<a class="prev-page" href="javascript:void(0)">&lt;&nbsp;上一页</a>',
        nextButton: ' <a class="next-page" href="javascript:void(0)">下一页&nbsp;&gt;</a>',
        lastButton: '<a class="last-page" href="javascript:void(0)">末页</a>',
        firstButton: '<a class="first-page" href="javascript:void(0)">首页</a>',
        button: '<a class="numerated-page {class}" href="javascript:void(0)">{page}</a>',
        moreButton: '<span class="dot-style">...</span>',
        searchData: null
    };

    options = $.extend(defaults, options);
    var container = $(options.container);
    container.delegate(".prev-page", "click", function () {
        options.pageNo = options.pageNo - 1;
        pager.renderPage();
    });
    container.delegate(".next-page", "click", function () {
        options.pageNo = options.pageNo + 1;
        pager.renderPage();
    });
    container.delegate(".numerated-page", "click", function () {
        options.pageNo = parseInt($(this).text());
        pager.renderPage();
    });
    container.delegate(".first-page", "click", function () {
        options.pageNo = parseInt(1);
        pager.renderPage();
    });
    container.delegate(".last-page", "click", function () {
        options.pageNo = parseInt(options.pageCount);
        pager.renderPage();
    });

    this.renderPage = function () {
        if (options.mode == 2) {
            return pager.renderPage2();
        }
        pager.renderPageNav();
        if (options.pageRenderFn != null) {
            options.pageRenderFn(options.pageNo, options.pageSize, options.searchData);
        }
    };
    this.renderPage2 = function () {
        if (options.pageRenderFn != null) {
            options.pageRenderFn(options.pageNo, options.pageSize, options.searchData);
        }
    };
    this.init = function (data, pageNo) {
        if (options.mode == 1) {
            $.post(options.countUrl, data, function (result) {
                if (pageNo != null && pageNo > 0) {
                    options.pageNo = pageNo;
                } else {
                    options.pageNo = 1;
                }
                if (options.resultCountFn) {
                    options.total = options.resultCountFn(result);
                } else {
                    options.total = result;
                }
                options.searchData = data;
                pager.renderPage();
            },"json");
        } else {
            pager.renderPage2();
        }

    };

    this.renderPageNav = function (newOptions) {
        if (newOptions) {
            options = $.extend(options, newOptions);
        }
        if (options.total <= 0) {
            container.html(options.pageText.replace("{page}", 0).replace("{count}", 0));
            $(".foodxx").hide();
            return;
        }
        container.html("");
        options.pageCount = parseInt(options.total / options.pageSize);
        if (options.total % options.pageSize > 0) {
            options.pageCount += 1;
        }
        container.append(options.pageText.replace("{page}", options.pageCount).replace("{count}", options.total));
        if (options.pageCount > 1 && options.pageNo > 1) {
            container.append(options.firstButton);
            container.append(options.prevButton);
        }
        var middle = options.pageNo;
        if (options.pageCount > 7) {
            if (options.pageNo < 4) {
                middle = 4;
            }
            if (options.pageNo > options.pageCount - 3) {
                middle = options.pageCount - 3;
            }
            if (middle > 4) {
                container.append(options.moreButton);
            }
            for (var i = middle - 3; i <= middle + 3; i++) {
                if (i == options.pageNo) {
                    container.append(options.button.replace("{page}", i).replace("{class}", "pageCurrent"));
                } else {
                    container.append(options.button.replace("{page}", i).replace("{class}", ""));
                }
            }
            if (middle < options.pageCount - 3) {
                container.append(options.moreButton);
            }
        } else {
            for (var i = 1; i <= options.pageCount; i++) {
                if (i == options.pageNo) {
                    container.append(options.button.replace("{page}", i).replace("{class}", "pageCurrent"));
                } else {
                    container.append(options.button.replace("{page}", i).replace("{class}", ""));
                }
            }
        }
        if (options.pageCount > 1 && options.pageNo < options.pageCount) {
            container.append(options.nextButton);
            container.append(options.lastButton);
        }
        if (options.pageCount == 1) {
            $(".foodxx").hide();
        } else {
            $(".foodxx").show();
        }
    };

    this.jump = function (page) {
        options.pageNo = page;
        pager.renderPageNav();
    };


}