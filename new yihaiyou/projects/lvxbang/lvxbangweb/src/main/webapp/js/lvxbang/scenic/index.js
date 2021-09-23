$(document).ready(function () {
    searchClick();
});

function searchClick() {
    $("#scenicTheme").on("click", "a", function () {
        var theme = $(this).attr("theme");
        $("#theme").val(theme);
        $("#searchTheme").submit();
    });
}