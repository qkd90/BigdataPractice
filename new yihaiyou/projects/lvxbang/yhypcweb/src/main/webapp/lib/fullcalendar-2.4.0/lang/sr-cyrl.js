!function (a) {
    "function" == typeof define && define.amd ? define(["jquery", "moment"], a) : a(jQuery, moment)
}(function (a, b) {
    var c = {
        words: {
            m: ["један минут", "једне минуте"],
            mm: ["минут", "минуте", "минута"],
            h: ["један сат", "једног сата"],
            hh: ["сат", "сата", "сати"],
            dd: ["дан", "дана", "дана"],
            MM: ["месец", "месеца", "месеци"],
            yy: ["година", "године", "година"]
        }, correctGrammaticalCase: function (a, b) {
            return 1 === a ? b[0] : a >= 2 && 4 >= a ? b[1] : b[2]
        }, translate: function (a, b, d) {
            var e = c.words[d];
            return 1 === d.length ? b ? e[0] : e[1] : a + " " + c.correctGrammaticalCase(a, e)
        }
    };
    (b.defineLocale || b.lang).call(b, "sr-cyrl", {
        months: ["јануар", "фебруар", "март", "април", "мај", "јун", "јул", "август", "септембар", "октобар", "новембар", "децембар"],
        monthsShort: ["јан.", "феб.", "мар.", "апр.", "мај", "јун", "јул", "авг.", "сеп.", "окт.", "нов.", "дец."],
        weekdays: ["недеља", "понедељак", "уторак", "среда", "четвртак", "петак", "субота"],
        weekdaysShort: ["нед.", "пон.", "уто.", "сре.", "чет.", "пет.", "суб."],
        weekdaysMin: ["не", "по", "ут", "ср", "че", "пе", "су"],
        longDateFormat: {
            LT: "H:mm",
            LTS: "LT:ss",
            L: "DD. MM. YYYY",
            LL: "D. MMMM YYYY",
            LLL: "D. MMMM YYYY LT",
            LLLL: "dddd, D. MMMM YYYY LT"
        },
        calendar: {
            sameDay: "[данас у] LT", nextDay: "[сутра у] LT", nextWeek: function () {
                switch (this.day()) {
                    case 0:
                        return "[у] [недељу] [у] LT";
                    case 3:
                        return "[у] [среду] [у] LT";
                    case 6:
                        return "[у] [суботу] [у] LT";
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                        return "[у] dddd [у] LT"
                }
            }, lastDay: "[јуче у] LT", lastWeek: function () {
                var a = ["[прошле] [недеље] [у] LT", "[прошлог] [понедељка] [у] LT", "[прошлог] [уторка] [у] LT", "[прошле] [среде] [у] LT", "[прошлог] [четвртка] [у] LT", "[прошлог] [петка] [у] LT", "[прошле] [суботе] [у] LT"];
                return a[this.day()]
            }, sameElse: "L"
        },
        relativeTime: {
            future: "за %s",
            past: "пре %s",
            s: "неколико секунди",
            m: c.translate,
            mm: c.translate,
            h: c.translate,
            hh: c.translate,
            d: "дан",
            dd: c.translate,
            M: "месец",
            MM: c.translate,
            y: "годину",
            yy: c.translate
        },
        ordinalParse: /\d{1,2}\./,
        ordinal: "%d.",
        week: {dow: 1, doy: 7}
    }), a.fullCalendar.datepickerLang("sr-cyrl", "sr", {
        closeText: "Затвори",
        prevText: "&#x3C;",
        nextText: "&#x3E;",
        currentText: "Данас",
        monthNames: ["Јануар", "Фебруар", "Март", "Април", "Мај", "Јун", "Јул", "Август", "Септембар", "Октобар", "Новембар", "Децембар"],
        monthNamesShort: ["Јан", "Феб", "Мар", "Апр", "Мај", "Јун", "Јул", "Авг", "Сеп", "Окт", "Нов", "Дец"],
        dayNames: ["Недеља", "Понедељак", "Уторак", "Среда", "Четвртак", "Петак", "Субота"],
        dayNamesShort: ["Нед", "Пон", "Уто", "Сре", "Чет", "Пет", "Суб"],
        dayNamesMin: ["Не", "По", "Ут", "Ср", "Че", "Пе", "Су"],
        weekHeader: "Сед",
        dateFormat: "dd.mm.yy",
        firstDay: 1,
        isRTL: !1,
        showMonthAfterYear: !1,
        yearSuffix: ""
    }), a.fullCalendar.lang("sr-cyrl", {
        buttonText: {month: "Месец", week: "Недеља", day: "Дан", list: "Планер"},
        allDayText: "Цео дан",
        eventLimitText: function (a) {
            return "+ још " + a
        }
    })
});