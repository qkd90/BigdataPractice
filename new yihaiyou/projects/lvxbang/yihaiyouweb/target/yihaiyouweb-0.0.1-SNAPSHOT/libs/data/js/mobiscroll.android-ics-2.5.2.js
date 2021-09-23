(function ($) {
    var theme = {
        defaults: {
            dateOrder: 'Mddyy',
            mode: 'clickpick',
            rows: 3,
            width: 50,
            height: 40,
            showLabel: false,
            useShortLabels: true,
        }
    };
    ;

    $.mobiscroll.themes['android-ics'] = theme;
    $.mobiscroll.themes['android-ics light'] = theme;

})(jQuery);

