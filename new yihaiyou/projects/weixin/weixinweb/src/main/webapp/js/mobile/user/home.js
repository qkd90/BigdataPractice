/**
 * Created by vacuity on 15/12/11.
 */


$(function() {
    var accountId = $("#accountId").val();
    var userId = $("#userId").val();
    Share.config(accountId, userId);
});