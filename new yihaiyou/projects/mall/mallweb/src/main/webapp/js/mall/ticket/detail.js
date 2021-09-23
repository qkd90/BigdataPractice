var TicketDetail = {
    ticketId: null,
    init: function () {
        this.ticketId = $("#ticketId").val();
        this.refreshPriceList();

        $(".order-count").each(function () {
            var panel = $(this);
            var counter = panel.find(".num");
            panel.find(".minus").click(function () {
                if (parseInt(counter.val()) > 0) {
                    counter.val(parseInt(counter.val()) - 1);
                }
            });
            panel.find(".plus").click(function () {
                counter.val(parseInt(counter.val()) + 1);
            });
        });
    },
    refreshPriceList: function () {

        $.getJSON("/mall/ticket/getPriceList.jhtml", {"ticketId": $("#ticketId").val()}, function (result) {
            if (result.success) {
                $(".ticket-price-row").each(function () {
                    var panel = $(this);
                    var priceMap = result[panel.find(".ticketPriceId").val()];
                    panel.find(".salePrice>span").text(priceMap.dateList[0].priPrice);
                    panel.find(".commission>span").text(priceMap.price.commission);
                    var zebraEnableDates = [];
                    var day = null;
                    var month = null;
                    var year = null;
                    var dateStr = "";
                    var priceList = priceMap.dateList;
                    for (var i = 0; i < priceList.length; i++) {
                        var date = new Date(priceList[i].huiDate);
                        if (year != date.getFullYear()) {
                            if (dateStr != "") {
                                dateStr += "-" + day + " " + month + " " + year;
                                zebraEnableDates.push(dateStr);
                            }
                            year = date.getFullYear();
                            month = date.getMonth() + 1;
                            day = date.getDate();
                            dateStr = day;
                        } else if (month != date.getMonth() + 1) {
                            dateStr += "-" + day + " " + month + " " + year;
                            zebraEnableDates.push(dateStr);
                            month = date.getMonth() + 1;
                            day = date.getDate();
                            dateStr = day;
                        } else if (day != date.getDate() - 1) {
                            dateStr += "-" + day + " " + month + " " + year;
                            zebraEnableDates.push(dateStr);
                            day = date.getDate();
                            dateStr = day;
                        } else {
                            day = date.getDate();
                        }
                    }

                    panel.find(".date-picker").val(priceMap.startDate);
                    panel.find(".date-picker").Zebra_DatePicker({
                        enabled_dates: zebraEnableDates,
                        disabled_dates: ['* * * *'],
                        direction: [true, true]
                    });
                    panel.find(".order-now").click(function() {
                        panel.find("form").submit();
                    })
                });
                refreshTable();
            }

        })
    },
    lpad: function (num) {
        if (num < 10) {
            return '0' + num;
        } else {
            return '' + num;
        }
    }

};

TicketDetail.init();