function SelCity(obj, e, options) {
    var ths = obj;
    var dal = '<div class="_citys"><span title="关闭" id="cColse" >×</span><ul id="_citysheng" class="_citys0"><li class="citySel province">省份</li><li class="city">城市</li></ul><div id="_citys0" class="_citys1"></div><div style="display:none" id="_citys1" class="_citys1"></div></div>';
    Iput.show({ id: ths, event: e, content: dal,width:"470"});
    $("#cColse").click(function () {
        Iput.colse();
    });
    var tb_province = [];
    if (typeof options != "object") {
        throw  new Error("参数无效");
    }
    var b = [];
    var url = options.url?options.url:"";
    var qryData = typeof options.qryData == "object"?options.qryData: {};
    var initCityId = options.initCityId?options.initCityId:"";
    var initProvinceId = options.initProvinceId?options.initProvinceId:"";
    $.ajax({
        url: url,
        data: qryData,
        async:false,
        success: function(result) {
            for (var i = 0, len = result.length; i < len; i++) {
                if ($("#hcity").attr("data-id") && $("#hcity").attr("data-id") == result[i]['id']) {
                    tb_province.push('<a class="citySel" data-level="0" data-id="' + result[i]['id'] + '" data-name="' + result[i]['name'] + '">' + result[i]['name'] + '</a>');
                } else {
                    tb_province.push('<a data-level="0" data-id="' + result[i]['id'] + '" data-name="' + result[i]['name'] + '">' + result[i]['name'] + '</a>');
                }
            }
            $("#_citys0").append(tb_province.join(""));
        },
        error: function() {}
    });


    $("#_citys0 a").click(function () {
        var g = getCity($(this), url, qryData);
        $("#_citys1 a").remove();
        $("#_citys1").append(g);
        $("._citys1").hide();
        $("._citys1:eq(1)").show();
        $("#_citys0 a,#_citys1 a").removeClass("AreaS");
        $(this).addClass("AreaS");
        var lev = $(this).data("name");
        ths.value = $(this).data("name");
        if (document.getElementById("hcity") == null) {
            var hcitys = $('<input>', {
                type: 'hidden',
                name: "hcity",
                "data-id": $(this).data("id"),
                id: "hcity",
                val: lev
            });
            $(ths).after(hcitys);
        }
        else {
            $("#hcity").val(lev);
            $("#hcity").attr("data-id", $(this).data("id"));
        }
        $("#_citys1 a").click(function () {
            $("#_citys1 a").removeClass("AreaS");
            $(this).addClass("AreaS");
            var lev =  $(this).data("name");
            if (document.getElementById("hproper") == null) {
                var hcitys = $('<input>', {
                    type: 'hidden',
                    name: "hproper",
                    "data-id": $(this).data("id"),
                    id: "hproper",
                    val: lev
                });
                $("#cityId").val($(this).data("id"));
                $(ths).after(hcitys);
            }
            else {
                $("#hproper").attr("data-id", $(this).data("id"));
                $("#cityId").val($(this).data("id"));
                $("#hproper").val(lev);
            }
            var bc = $("#hcity").val();
            ths.value = bc+ "-" + $(this).data("name");
            Iput.colse();

            $("._citys1").hide();
            $("._citys1:eq(2)").show();
        });
    });

    $("#_citysheng li").click(function () {
        $("#_citysheng li").removeClass("citySel");
        $(this).addClass("citySel");
        var s = $("#_citysheng li").index(this);
        $("._citys1").hide();
        $("._citys1:eq(" + s + ")").show();
    });

    //$(".city").click(function() {
    //    var g = getCity($(this), url, qryData);
    //    $("#_citys1 a").remove();
    //    $("#_citys1").append(g);
    //    $("._citys1").hide();
    //})
}

function getCity(obj, url, qryData) {
    var c = obj.data('id');
    var g = '';
    qryData['tbArea.id'] = c;
    qryData['tbArea.level'] = 2;
    $.ajax({
        url: url,
        data: qryData,
        async:false,
        success: function(result) {
            for (var j = 0, len = result.length; j < len; j++) {
                if ($("#hproper").length > 0 && $("#hproper").attr("data-id") == result[j]['id']) {
                    g += '<a data-level="1" class="citySel" data-id="' + result[j]['id'] + '" data-name="' + result[j]['name'] + '" title="' + result[j]['name'] + '">' + result[j]['name'] + '</a>'
                } else {
                    g += '<a data-level="1" data-id="' + result[j]['id'] + '" data-name="' + result[j]['name'] + '" title="' + result[j]['name'] + '">' + result[j]['name'] + '</a>'
                }

            }
        },
        error: function() {}
    });
    $("#_citysheng li").removeClass("citySel");
    $("#_citysheng li:eq(1)").addClass("citySel");
    return g;
}
