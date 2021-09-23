/**
 * Created by vacuity on 15/11/13.
 */

var MallLine = {
    pageIndex : 1,
    totalCount : 1,
    conditionStr : "",
    init : function() {
        this.pageIndex = parseInt($("#pageIndex").text());
        this.totalCount = parseInt($("#totalCount").text());
        this.conditionStr = $("#conditionStr").text();
        this.initPage();
        this.initSelectedItems();
        this.searchBtn();
        this.initSelectedNav();
    },
    initSelectedNav : function(){
        var line_type = $("#line-type").val();
        if (line_type == "lines") {
            $("#topnav a").removeClass('curr');
            $("#navLine").addClass('curr');
        } else {
            $(".mainnav").find("a").removeClass("curr");
            $(".mainnav").find(".supplier-line").addClass("curr");
        }

    },
    initSelectedItems : function(){
        var url = location.search;
        if(url.length > 0 && url.substring(0, 1) == '?'){
            url = url.substring(1, url.length);
            var items = url.split("&");
            for(var i = 0;i < items.length;i++){
                var item = items[i].split("=");
                if(item[1].length > 0){
                    var name = item[0];
                    var val = decodeURI(item[1]).replace("天","").replace("以上","");
                    $("#no" + name).removeClass("curr");
                    $("#" + name).find('a[key='+val+']').addClass("curr");
                    if(name == 'lineName'){
                        $(".search-kw").val(val);
                    }
                }
            }
        }

    },
    initPage : function() {
        $("#page-list").pagination(this.totalCount, {
            link_to : "javascript:;",
            items_per_page : 10,
            prev_text: "&laquo;上一页",
            next_text: "下一页&raquo;",
            current_page : this.pageIndex,
            num_display_entries : 5,
            prev_show_always: false,
            next_show_always: false,
            callback: function(pageIndex,panel){
                MallLine.searchBySelect(pageIndex);
            }
        });
    },
    initSelect : function() {
        this.citySelect();
        this.productAttrSelect();
        this.lineDaySelect();
        this.supplierNameSelect();

    },

    citySelect : function() {
        $("#nocity").on("click", function() {
            $("#city").find("a[class='curr']").removeClass("curr");
            $("#nocity").addClass("curr");
            MallLine.searchBySelect(0);
        });
        $("#city").on("click", 'a', function() {
            $("#nocity").removeClass("curr");
            $("#city").find("a[class='curr']").removeClass("curr");
            $(this).addClass("curr");
            MallLine.searchBySelect(0);
        });
    },

    supplierNameSelect : function() {
        $("#nosupplierName").on("click", function() {
            $("#supplierName").find("a[class='curr']").removeClass("curr");
            $("#nosupplierName").addClass("curr");
            MallLine.searchBySelect(0);
        });
        $("#supplierName").on("click", 'a', function() {
            $("#nosupplierName").removeClass("curr");
            $("#supplierName").find("a[class='curr']").removeClass("curr");
            $(this).addClass("curr");
            MallLine.searchBySelect(0);
        });
    },

    productAttrSelect : function() {
        $("#noproductAttr").on("click", function() {
            $("#productAttr").find("a[class='curr']").removeClass("curr");
            $("#noproductAttr").addClass("curr");
            MallLine.searchBySelect(0);
        });
        $("#productAttr").on("click", 'a', function() {
            $("#noproductAttr").removeClass("curr");
            $("#productAttr").find("a[class='curr']").removeClass("curr");
            $(this).addClass("curr");
            MallLine.searchBySelect(0);
        });
    },

    lineDaySelect : function() {
        $("#nolineDay").on("click", function() {
            $("#lineDay").find("a[class='curr']").removeClass("curr");
            $("#nolineDay").addClass("curr");
            MallLine.searchBySelect(0);
        });
        $("#lineDay").on("click", 'a', function() {
            $("#nolineDay").removeClass("curr");
            $("#lineDay").find("a[class='curr']").removeClass("curr");
            $(this).addClass("curr");
            MallLine.searchBySelect(0);
        });
    },

    searchBtn : function() {
//		var ticketName = $(".search-kw").val();
        var line_type = $("#line-type").val();
        if (line_type == "lines") {
            $(".search-bt").on("click", function() {
                MallLine.searchBySelect(0);
            });
            $(".search-kw").keydown(function(e){
                if (event.keyCode == 13) {
                    MallLine.searchBySelect(0);
                }
            });
        } else {
            $("#search-btn").on("click", function() {
                MallLine.searchBySelect(0);
            });
            $("#kw").keydown(function(e){
                if (event.keyCode == 13) {
                    MallLine.searchBySelect(0);
                }
            });
        }



    },

    selectBtn : function() {
        MallLine.initPage();
        MallLine.searchBySelect();
    },
    searchBySelect : function(page) {
        var city = $("#city").find("a[class='curr']").text();
        var productAttr = $("#productAttr").find("a[class='curr']").text();
        var lineDay = $("#lineDay").find("a[class='curr']").text();
        var supplierName = $("#supplierName").find("a[class='curr']").text();
        var cost = "";
        var mincost = "";
        var maxcost = "";
        var lineName = "";
        var supplierId = "";


        var line_type = $("#line-type").val();
        if (line_type == "lines") {
            lineName = $(".search-kw").val();
        } else {
            lineName = $("#kw").val();
            supplierId = $("#supplierId").val();
        }
        if ($("#mincost").val() == "" && $("#maxcost").val() == "") {
            cost = $("#ticket-cost").find("a[class='curr']").attr("id");
        } else {
            mincost = $("#mincost").val() == undefined?"":$("#mincost").val() ;
            maxcost = $("#maxcost").val() == undefined?"":$("#maxcost").val();
        }
        // $(".search-bt").on("click", function () {
        // ticketName = $(".search-kw").val();
        //
        // });
        MallLine.conditionStr = "";
        MallLine.conditionStr +='&lineName=' + lineName;
        MallLine.conditionStr +='&productAttr='  + productAttr;
        MallLine.conditionStr +='&city='  + city;
        MallLine.conditionStr +='&lineDay='  + lineDay;
        MallLine.conditionStr +='&supplierName='  + supplierName;
        MallLine.conditionStr +='&cost='  + cost;
        MallLine.conditionStr +='&mincost='  + mincost;
        MallLine.conditionStr +='&maxcost='  + maxcost;
        if (line_type != "lines") {
            MallLine.conditionStr +='&id='  + supplierId;
        }
        $.get('/mall/line/searchLine.jhtml', {
            'pageIndex' : page,
            'lineName' : encodeURI(lineName),
            'productAttr' : encodeURI(productAttr),
            'city' : encodeURI(city),
            'lineDay' : encodeURI(lineDay),
            'supplierName' : encodeURI(supplierName),
            'cost' : cost,
            'mincost' : mincost,
            'maxcost' : maxcost,
            'id' : supplierId
        }, function(result) {
            $("#linesPanel").empty();
            var html = $("#lineTmpl").render(result.lines);
            $("#linesPanel").append(html);
            MallLine.pageIndex = page;
            MallLine.totalCount = result.page.totalCount;
            MallLine.initPage(pageIndex,totalCount);
            MallLine.changeLink();
        });

    },
    changeLink : function(){
        var json={time:new Date().getTime()};
        var line_type = $("#line-type").val();
        if (line_type == "lines") {
            window.history.pushState(json,"",'/mall/line/list.jhtml?pageIndex=' + MallLine.pageIndex + MallLine.conditionStr);
        } else {
            window.history.pushState(json,"",'/mall/supplier/lines.jhtml?pageIndex=' + MallLine.pageIndex + MallLine.conditionStr);
        }

    },
    makePagination : function(total) {
        $("#page-list").pagination(total, {
            link_to : "javascript:;",
            items_per_page : 10,
            current_page : 0,
            num_display_entries : 5,
            callback : MallLine.searchBySelect
        });
    }

}

