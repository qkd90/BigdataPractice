$(function () {
    $(".recommended, .selected").each(function () {
        if ($(this).prop("checked")) {
            $(this).parents(".city-list-wrap").slideDown();
        }
    });
    $(".province-section").each(function () {
        var province = $(this);
        province.find(".province-title>label").click(function() {
            province.find(".city-list-wrap").slideToggle();
        });
    });
    $(".submit").click(function () {
        var data = {};
        var index = 0;
        $(".city").each(function () {
            data['supplierCityList[' + index + '].id'] = $(this).find(".id").val();
            data['supplierCityList[' + index + '].city.id'] = $(this).find(".cityId").val();
            data['supplierCityList[' + index + '].recommended'] = $(this).find(".recommended").prop("checked");
            data['supplierCityList[' + index + '].selected'] = $(this).find(".selected").prop("checked");
            index++;
        });
        $.post("/area/area/selectSupplierCity.jhtml", data, function (result) {
            if (result == 'success') {
                window.location.reload();
            }
        })
    });
    $(".city").each(function () {
        var city = $(this);
        city.find(".selected").click(function () {
            if ($(this).prop("checked")) {
                city.find(".recommended").attr("disabled", null).parent().removeClass("disabled");
            } else {
                city.find(".recommended").attr("disabled", "disabled").attr("checked", null).parent().addClass("disabled");
            }
        });
    })
});