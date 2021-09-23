/**
 * Created by dy on 2016/9/1.
 */
var ContractView = {
    limitNum: 5000,	// 字数限制仅文本
    init: function() {
        ContractView.initContent();
    },

    initContent:function(){
        //富文本产品详情
        var editorContent;
        KindEditor.ready(function(K) {
            editorContent = K.create('#kind_content', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                allowImageUpload : true,
                allowFileManager : true,
                disabled: true,
                readonlyMode: true,
                filePostName: 'resource',
                items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline', 'table',
                    'removeformat', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', 'image', 'multiimage', 'wordpaste', 'link' ],
                afterChange: function() {
                    this.sync();
                },
                afterBlur: function() {
                    this.sync();
                }
            });
            editorContent.readonly(true);
        });
    },

    cancelEdit: function() {
        window.parent.$("#editPanel").dialog("close");
    }
};

$(function(){
    ContractView.init();
});