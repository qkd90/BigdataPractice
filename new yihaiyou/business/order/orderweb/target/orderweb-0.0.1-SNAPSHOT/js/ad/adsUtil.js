/**
 * Created by vacuity on 15/10/27.
 */

var AdsUtil={
    // 打开方式
    openType : [{'id':'','text':'打开方式','filter':true},{'id':'NONE','text':'NONE'},{'id':'SELF','text':'SELF'},{'id':'NEWED','text':'NEWED'}],

    // 状态
    adsStatus : [{'id':'','text':'广告状态','filter':true},{'id':'UP','text':'上架'},{'id':'DOWN','text':'下架'}],

    // 初始化控件
    initComp:function(){



        //
        $('#ads_location').add('#add_ads_location').add('#edit_ads_location').combobox({
            url:'/ad/ad/getPositionList.jhtml',
            valueField:'id',
            textField:'name',
            panelHeight:'270px'
        });
        $('#ads_stime').add('#add_ads_stime').add('#edit_ads_stime').datebox({
        });
        $('#ads_etime').add('#add_ads_etime').add('#edit_ads_etime').datebox({
        });
        $('#ads_opentype').add('#add_ads_opentype').add('#edit_ads_opentype').combobox({
            data:AdsUtil.openType,
            valueField:'id',
            textField:'text',
            panelHeight:'auto'
        });
        $('#ads_status').add('#add_ads_status').add('#edit_ads_status').combobox({
            data:AdsUtil.adsStatus,
            valueField:'id',
            textField:'text',
            panelHeight:'auto'
        });
        $('#add_ads_status').combobox('select', 'UP');
        $('#add_ads_opentype').combobox('select', 'NEWED');
        // 新增
    }
};
