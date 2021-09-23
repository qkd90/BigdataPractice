!function (a) {
    "function" == typeof define && define.amd ? define(["jquery", "moment"], a) : a(jQuery, moment)
}(function (a, b) {
    function c(a) {
        return 5 > a % 10 && a % 10 > 1 && ~~(a / 10) % 10 !== 1
    }

    function d(a, b, d) {
        var e = a + " ";
        switch (d) {
            case"m":
                return b ? "minuta" : "minutę";
            case"mm":
                return e + (c(a) ? "minuty" : "minut");
            case"h":
                return b ? "godzina" : "godzinę";
            case"hh":
                return e + (c(a) ? "godziny" : "godzin");
            case"MM":
                return e + (c(a) ? "miesiące" : "miesięcy");
            case"yy":
                return e + (c(a) ? "lata" : "lat")
        }
    }

    var e = "styczeń_luty_marzec_kwiecień_maj_czerwiec_lipiec_sierpień_wrzesień_październik_listopad_grudzień".split("_"), f = "stycznia_lutego_marca_kwietnia_maja_czerwca_lipca_sierpnia_września_października_listopada_grudnia".split("_");
    (b.defineLocale || b.lang).call(b, "pl", {
        months: function (a, b) {
            return /D MMMM/.test(b) ? f[a.month()] : e[a.month()]
        },
        monthsShort: "sty_lut_mar_kwi_maj_cze_lip_sie_wrz_paź_lis_gru".split("_"),
        weekdays: "niedziela_poniedziałek_wtorek_środa_czwartek_piątek_sobota".split("_"),
        weekdaysShort: "nie_pon_wt_śr_czw_pt_sb".split("_"),
        weekdaysMin: "N_Pn_Wt_Śr_Cz_Pt_So".split("_"),
        longDateFormat: {
            LT: "HH:mm",
            LTS: "LT:ss",
            L: "DD.MM.YYYY",
            LL: "D MMMM YYYY",
            LLL: "D MMMM YYYY LT",
            LLLL: "dddd, D MMMM YYYY LT"
        },
        calendar: {
            sameDay: "[Dziś o] LT",
            nextDay: "[Jutro o] LT",
            nextWeek: "[W] dddd [o] LT",
            lastDay: "[Wczoraj o] LT",
            lastWeek: function () {
                switch (this.day()) {
                    case 0:
                        return "[W zeszłą niedzielę o] LT";
                    case 3:
                        return "[W zeszłą środę o] LT";
                    case 6:
                        return "[W zeszłą sobotę o] LT";
                    default:
                        return "[W zeszły] dddd [o] LT"
                }
            },
            sameElse: "L"
        },
        relativeTime: {
            future: "za %s",
            past: "%s temu",
            s: "kilka sekund",
            m: d,
            mm: d,
            h: d,
            hh: d,
            d: "1 dzień",
            dd: "%d dni",
            M: "miesiąc",
            MM: d,
            y: "rok",
            yy: d
        },
        ordinalParse: /\d{1,2}\./,
        ordinal: "%d.",
        week: {dow: 1, doy: 4}
    }), a.fullCalendar.datepickerLang("pl", "pl", {
        closeText: "Zamknij",
        prevText: "&#x3C;Poprzedni",
        nextText: "Następny&#x3E;",
        currentText: "Dziś",
        monthNames: ["Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"],
        monthNamesShort: ["Sty", "Lu", "Mar", "Kw", "Maj", "Cze", "Lip", "Sie", "Wrz", "Pa", "Lis", "Gru"],
        dayNames: ["Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"],
        dayNamesShort: ["Nie", "Pn", "Wt", "Śr", "Czw", "Pt", "So"],
        dayNamesMin: ["N", "Pn", "Wt", "Śr", "Cz", "Pt", "So"],
        weekHeader: "Tydz",
        dateFormat: "dd.mm.yy",
        firstDay: 1,
        isRTL: !1,
        showMonthAfterYear: !1,
        yearSuffix: ""
    }), a.fullCalendar.lang("pl", {
        buttonText: {month: "Miesiąc", week: "Tydzień", day: "Dzień", list: "Plan dnia"},
        allDayText: "Cały dzień",
        eventLimitText: "więcej"
    })
});