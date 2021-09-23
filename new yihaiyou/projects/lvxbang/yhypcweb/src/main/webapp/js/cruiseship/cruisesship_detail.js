var CruiseshipDetail = {
    pager: null,
    init: function() {
        CruiseshipDetail.queryDeInfo();
        CruiseshipDetail.queryShipInfo();
        CruiseshipDetail.commentList();
        CruiseshipDetail.BtnPosition();
        CruiseshipDetail.ulmin();
        CruiseshipDetail.photo();
        CruiseshipDetail.initSwiper();

    },
    queryDetailInfo: function() {
        $.ajax({
            url: '/yhypc/cruiseShip/details.jhtml',
            progress: true,
            data: {
                'cruiseShipDate.id':dateId
            },
            success: function(data) {

                }
            })
    },
    queryShipInfo: function() {
        $.ajax({
            url: '/yhypc/cruiseShip/getCruiseshipList.jhtml',
            progress: true,
            data: {
                'cruiseShipDate.id':dateId
            },
            success: function(data) {

            }
        })
    },

};


$(function(){
    CruiseshipDetail.init();
});







