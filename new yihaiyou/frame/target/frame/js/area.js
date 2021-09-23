function initCity(val) {
    var cityCode = '0';
    if (val != undefined && val != 'undefined')
        cityCode = val.cityCode;
    $('#qry_city').combobox({
        url: '/sys/area/getCities.jhtml?provinceId=' + cityCode,
        valueField: 'cityCode',
        textField: 'name',
        onSelect: function (sel) {
            //alert(sel);
        }
    });
}

function initProvince() {
    $('#qryCity').textbox({
        onClickButton:function() {
            $('#qryCity').textbox('setValue', '');
            $('#qryCity').attr('data-country', '');
            $('#qryCity').attr('data-province', '');
            $('#qryCity').attr('data-city', '');
            // 清除查询条件
            $('#qry_cityCode').val(null);
        }
    });
    $("#qryCity").next('span').children('input').click(function() {
        var countryCode = $('#qryCity').attr('data-country');
        var provinceCode = $('#qryCity').attr('data-province');
        var cityCode = $('#qryCity').attr('data-city');
        AreaSelectDg.openForQry(countryCode, provinceCode, cityCode, function(countryCode, provinceCode, cityCode, fullName) {
            $('#qryCity').textbox('setValue', fullName.replace(/\//g, "-"));
            if (countryCode) {
                $('#qryCity').attr('data-country', countryCode);
            } else {
                $('#qryCity').attr('data-country', '');
            }
            if (provinceCode) {
                $('#qryCity').attr('data-province', provinceCode);
            } else {
                $('#qryCity').attr('data-province', '');
            }
            if (cityCode) {
                $('#qryCity').attr('data-city', cityCode);
            } else {
                $('#qryCity').attr('data-city', '');
            }
            // 是否国内判断, 国内省级,市级搜索需要特殊处理
            var isChina = countryCode && countryCode == "100000";
            $("#qry_isChina").val(isChina);
            // 保存查询条件
            if (cityCode) {
                if (isChina) {
                    // 国内城市搜索, 去掉最后两位//区//代码
                    cityCode = cityCode.substring(0, 4)
                }
                $('#qry_cityCode').val(cityCode);
            } else if (provinceCode) {
                if (isChina) {
                    // 国内省级搜索, 去掉最后四位//城市//区//代码
                    provinceCode = provinceCode.substring(0, 2);
                }
                $('#qry_cityCode').val(provinceCode);
            } else if (countryCode) {
                $('#qry_cityCode').val(countryCode);
            }
        });
    });
}