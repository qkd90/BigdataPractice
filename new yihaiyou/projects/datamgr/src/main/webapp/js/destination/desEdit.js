/**
 * Created by zzl on 2016/1/23.
 */
$(function () {
    loadCoverImg();
    $('#imagePanel').diyUpload({
        url: "/destination/tbAreaMgr/upload.jhtml?cityCode=" + $('#cityCode').val(),
        success: function (data) {
            console.info(data);
            //上传成功
            $('#cover').val(data.data);
        },
        error: function (err) {
            console.info('error:'+err);
        },
        buttonText: '上传',
        chunked: true,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 1,
        fileSizeLimit: 500000 * 1024,
        fileSingleSizeLimit: 50000 * 1024,
        accept: {}
    });
    $.each($("#extend_info").find("textarea"), function(i, item) {
        $(this).xheditor({
            tools: 'FontSize,|,Removeformat,|,Preview',
            skin: 'default'
        });
    });
});
// 加载目的地封面图片
function loadCoverImg() {
    var id = $("#tbAreaId").val();
    if (id != null && id > 0) {
        var imgAddress = $("#cover").val();
        if(!imgAddress.startsWith("http:"))
            imgAddress =BizConstants.QINIU_DOMAIN +imgAddress;
        showImage($('#imagePanel'), imgAddress, id);
    }
};
function getExtendInfo() {
    $("#bestVisitTime").val($("#bestVisitTime_").val());
    $("#abs").val($("#abs_").val());
    $("#history").val($("#history_").val());
    $("#art").val($("#art_").val());
    $("#weather").val($("#weather_").val());
    $("#geography").val($("#geography_").val());
    $("#environment").val($("#environment_").val());
    $("#culture").val($("#culture_").val());
    $("#language").val($("#language_").val());
    $("#festival").val($("#festival_").val());
    $("#religion").val($("#religion_").val());
    $("#nation").val($("#nation_").val());
}
function saveDestination() {
    getExtendInfo();
    $("#destination_edit_form").form('submit', {
        url: '/destination/tbAreaMgr/saveDes.jhtml',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            BgmgrUtil.backCall(data, function () {
                $.messager.alert('提示', '操作成功！', 'info', null, null);
            }, null);
        }, onLoadError: function (data) {
            console.info('load error' + data);
        },
        error: function () {
            error.apply(this, arguments);
            $.messager.alert('提示', '失败！', 'info', null, null);
        }
    });
}