/**
 * Created by vacuity on 15/11/24.
 */

var Team = {
    teamBtn: function() {
        $(".his-team-btn").click(function () {
            var hisTeam = $(event.target).parents(".vip-member").children(".his-team");

            if (hisTeam.hasClass("display-none")) {
                hisTeam.removeClass("display-none");
                hisTeam.addClass("display-block");
            } else {
                hisTeam.removeClass("display-block");
                hisTeam.addClass("display-none");
            }
        });

    },

    levelBtnn: function() {
        $("#one-level").click(function () {
            $("#level-one").addClass("display-block");
            $("#level-one").removeClass("display-none");
            $("#level-two").addClass("display-none");
            $("#level-two").removeClass("display-block");
            $("#level-three").addClass("display-none");
            $("#level-three").removeClass("display-block");

            $("#one-level").addClass("curr");
            $("#two-level").removeClass("curr");
            $("#three-level").removeClass("curr");
        });
        $("#two-level").click(function () {
            $("#level-one").addClass("display-none");
            $("#level-one").removeClass("display-block");
            $("#level-two").addClass("display-block");
            $("#level-two").removeClass("display-none");
            $("#level-three").addClass("display-none");
            $("#level-three").removeClass("display-block");

            $("#one-level").removeClass("curr");
            $("#two-level").addClass("curr");
            $("#three-level").removeClass("curr");
        });
        $("#three-level").click(function () {
            $("#level-one").addClass("display-none");
            $("#level-one").removeClass("display-block");
            $("#level-two").addClass("display-none");
            $("#level-two").removeClass("display-block");
            $("#level-three").addClass("display-block");
            $("#level-three").removeClass("display-none");

            $("#one-leve").removeClass("curr");
            $("#two-level").removeClass("curr");
            $("#three-level").addClass("curr");
        });
    }

}

$(function() {
    Team.teamBtn();
    Team.levelBtnn();
});

