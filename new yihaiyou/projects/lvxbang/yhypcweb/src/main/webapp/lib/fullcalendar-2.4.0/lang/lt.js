!function (a) {
    "function" == typeof define && define.amd ? define(["jquery", "moment"], a) : a(jQuery, moment)
}(function (a, b) {
    function c(a, b, c, d) {
        return b ? "kelios sekundės" : d ? "kelių sekundžių" : "kelias sekundes"
    }

    function d(a, b, c, d) {
        return b ? f(c)[0] : d ? f(c)[1] : f(c)[2]
    }

    function e(a) {
        return a % 10 === 0 || a > 10 && 20 > a
    }

    function f(a) {
        return i[a].split("_")
    }

    function g(a, b, c, g) {
        var h = a + " ";
        return 1 === a ? h + d(a, b, c[0], g) : b ? h + (e(a) ? f(c)[1] : f(c)[0]) : g ? h + f(c)[1] : h + (e(a) ? f(c)[1] : f(c)[2])
    }

    function h(a, b) {
        var c = -1 === b.indexOf("dddd HH:mm"), d = j[a.day()];
        return c ? d : d.substring(0, d.length - 2) + "į"
    }

    var i = {
        m: "minutė_minutės_minutę",
        mm: "minutės_minučių_minutes",
        h: "valanda_valandos_valandą",
        hh: "valandos_valandų_valandas",
        d: "diena_dienos_dieną",
        dd: "dienos_dienų_dienas",
        M: "mėnuo_mėnesio_mėnesį",
        MM: "mėnesiai_mėnesių_mėnesius",
        y: "metai_metų_metus",
        yy: "metai_metų_metus"
    }, j = "sekmadienis_pirmadienis_antradienis_trečiadienis_ketvirtadienis_penktadienis_šeštadienis".split("_");
    (b.defineLocale || b.lang).call(b, "lt", {
        months: "sausio_vasario_kovo_balandžio_gegužės_birželio_liepos_rugpjūčio_rugsėjo_spalio_lapkričio_gruodžio".split("_"),
        monthsShort: "sau_vas_kov_bal_geg_bir_lie_rgp_rgs_spa_lap_grd".split("_"),
        weekdays: h,
        weekdaysShort: "Sek_Pir_Ant_Tre_Ket_Pen_Šeš".split("_"),
        weekdaysMin: "S_P_A_T_K_Pn_Š".split("_"),
        longDateFormat: {
            LT: "HH:mm",
            LTS: "LT:ss",
            L: "YYYY-MM-DD",
            LL: "YYYY [m.] MMMM D [d.]",
            LLL: "YYYY [m.] MMMM D [d.], LT [val.]",
            LLLL: "YYYY [m.] MMMM D [d.], dddd, LT [val.]",
            l: "YYYY-MM-DD",
            ll: "YYYY [m.] MMMM D [d.]",
            lll: "YYYY [m.] MMMM D [d.], LT [val.]",
            llll: "YYYY [m.] MMMM D [d.], ddd, LT [val.]"
        },
        calendar: {
            sameDay: "[Šiandien] LT",
            nextDay: "[Rytoj] LT",
            nextWeek: "dddd LT",
            lastDay: "[Vakar] LT",
            lastWeek: "[Praėjusį] dddd LT",
            sameElse: "L"
        },
        relativeTime: {
            future: "po %s",
            past: "prieš %s",
            s: c,
            m: d,
            mm: g,
            h: d,
            hh: g,
            d: d,
            dd: g,
            M: d,
            MM: g,
            y: d,
            yy: g
        },
        ordinalParse: /\d{1,2}-oji/,
        ordinal: function (a) {
            return a + "-oji"
        },
        week: {dow: 1, doy: 4}
    }), a.fullCalendar.datepickerLang("lt", "lt", {
        closeText: "Uždaryti",
        prevText: "&#x3C;Atgal",
        nextText: "Pirmyn&#x3E;",
        currentText: "Šiandien",
        monthNames: ["Sausis", "Vasaris", "Kovas", "Balandis", "Gegužė", "Birželis", "Liepa", "Rugpjūtis", "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"],
        monthNamesShort: ["Sau", "Vas", "Kov", "Bal", "Geg", "Bir", "Lie", "Rugp", "Rugs", "Spa", "Lap", "Gru"],
        dayNames: ["sekmadienis", "pirmadienis", "antradienis", "trečiadienis", "ketvirtadienis", "penktadienis", "šeštadienis"],
        dayNamesShort: ["sek", "pir", "ant", "tre", "ket", "pen", "šeš"],
        dayNamesMin: ["Se", "Pr", "An", "Tr", "Ke", "Pe", "Še"],
        weekHeader: "SAV",
        dateFormat: "yy-mm-dd",
        firstDay: 1,
        isRTL: !1,
        showMonthAfterYear: !0,
        yearSuffix: ""
    }), a.fullCalendar.lang("lt", {
        buttonText: {month: "Mėnuo", week: "Savaitė", day: "Diena", list: "Darbotvarkė"},
        allDayText: "Visą dieną",
        eventLimitText: "daugiau"
    })
});