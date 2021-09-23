/**
 * Created by zzl on 2017/01/18.
 */
($.extend({
    form: {
        load: function(options) {
            var _formId = options.formId;
            var _url = options.url;
            var _data = options.data && typeof options.data === "object" ? options.data : {};
            var _formData = options.formData && typeof options.formData === "object" ? options.formData : {};
            var _onLoadSuccess = options.onLoadSuccess && typeof options.onLoadSuccess === "function" ? options.onLoadSuccess : function(result){};
            var _onLoadError = options.onLoadError && typeof options.onLoadError === "function" ? options.onLoadError : function(){};
            if (typeof _formId !== "string") {
                throw new Error("formId must be a string !");
            }
            if (_url && typeof _url !== "string") {
                throw new Error("url must be a string !")
            }
            var $form = $(_formId);
            if (_url) {
                $.ajax({
                    url: _url,
                    type: 'post',
                    dataType: 'json',
                    progress: true,
                    data: _formData,
                    success: function(result) {
                        $.each(result, function(name, value) {
                            $e = $form.find('[name="' + name +'"]');
                            var eType = $e.attr('type');
                            if ($e.length == 1 && eType != "checkbox" && eType != "radio") {
                                $e.val(value);
                            }
                        });
                        _onLoadSuccess(result);
                        // validate the form
                        var _bv = $form.data('bootstrapValidator');
                        if (_bv) {
                            _bv.validate();
                        }
                    },
                    error: _onLoadError
                })
            }
            $.each(_data, function(name, value) {
                $form.find('[name="' + name +'"]').val(value);
            });
        },
        commit: function(options) {
            var _formId = options.formId;
            if (typeof _formId != "string") {
                throw new Error("arguments [formId] must be a string !");
            }
            var $form = $(_formId);
            if ($form.length <= 0) {
                throw new Error("can not find needed form area by id" + _formId + " !");
            }
            if ($form.length > 1) {
                throw new Error("ambiguous form id" + _formId + " !");
            }
            var _url = options.url;
            if (!_url || typeof _url != "string") {
                throw new Error("arguments [url] must be a string !");
            }
            var _extraData = options.extraData && typeof options.extraData === "object" ? options.extraData : {};
            var _success = options.success;
            var _error = options.error;
            if (typeof _success != "function") {
                throw new Error( _success + " is not a function !");
            }
            if (typeof _success != "function") {
                throw new Error( _success + " is not a function !");
            }
            var formDataArray = $form.serializeArray();
            // handle extra post data
            $.each(_extraData, function(name, value) {
                formDataArray.push({name: name, value: value});
            });
            var formData = {};
            $.each(formDataArray, function(i, obj) {
                if (formData[obj.name] && formData[obj.name].length > 0) {
                    formData[obj.name] = formData[obj.name] + "," + obj.value;
                } else {
                    formData[obj.name] = obj.value;
                }
            });
            var _formData = $.extend({}, formData);
            $.ajax({
                url: _url,
                type: 'post',
                dataType: 'json',
                progress: true,
                data: _formData,
                success: _success,
                error: _error
            });
        },
        reset: function(options) {
            var _formId = options.formId;
            if (typeof _formId != "string") {
                throw new Error("arguments [formId] must be a string !");
            }
            var $form = $(_formId);
            if ($form.length <= 0) {
                throw new Error("can not find needed form area by id" + _formId + " !");
            }
            $.each($form.find('input'), function(i, e) {
                var $e = $(e);
                var type = $e.attr("type");
                if (type === "text") {
                    $e.val(null);
                } else if (type === "checkbox") {
                    $e.prop('checked', false);
                } else if (type === "radio") {
                    $e.prop("checked", false);
                } else {
                    $e.val(null);
                }
            });
            $form.find('textarea, select').val(null);
        },
        loadData: function(formData){
            var obj = eval("("+jsonStr+")");
            var key,value,tagName,type,arr;
            for(x in obj){
                key = x;
                value = obj[x];

                $("[name='"+key+"'],[name='"+key+"[]']").each(function(){
                    tagName = $(this)[0].tagName;
                    type = $(this).attr('type');
                    if(tagName=='INPUT'){
                        if(type=='radio'){
                            $(this).attr('checked',$(this).val()==value);
                        }else if(type=='checkbox'){
                            arr = value.split(',');
                            for(var i =0;i<arr.length;i++){
                                if($(this).val()==arr[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        }else{
                            $(this).val(value);
                        }
                    }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                        $(this).val(value);
                    }

                });
            }
        }
    },
    message:{
        alert: function(options) {
            if (typeof options != "object") {
                throw  new Error("参数无效");
            }
            var _shadow = '<div class="windowShadow"></div>';
            var _title = options.title?options.title:"";
            var _info = options.info;
            var _afterClosed = options.afterClosed && typeof options.afterClosed === "function" ? options.afterClosed : function(event){};

            if (!_info && _info.length <= 0 ) {
                throw  new Error("info不能为空！");
            }

            var _alertHtml = '<div class="attentionBox">';
            _alertHtml += '<span class="closebtn"></span>';
            _alertHtml += '<h3>';
            _alertHtml += _title;
            _alertHtml += '</h3>';
            _alertHtml += '<div class="attenContain">';
            _alertHtml += '<p>';
            _alertHtml += _info;
            _alertHtml += '</p>';
            _alertHtml += '</div>';
            _alertHtml += '<div class="btn">确认</div>';
            _alertHtml += '</div>';
            var _modalHtml = _shadow + _alertHtml;
            $("body").append(_modalHtml);
            var s_top = $(window).scrollTop();
            var left = (window.screen.availWidth - $(".attentionBox").width()) / 2;
            //var top = (window.screen.availHeight - $(".attentionBox").height()) / 2;
            var top = (window.screen.availHeight - $(".attentionBox").height()) / 2 + s_top;
            $(".attentionBox").css({"top": top, "left": left});
            $('body').css({'overflow':'hidden'});


            $(".attentionBox .closebtn,.attentionBox .btn").on("click", function(event) {
                $(".windowShadow").remove();
                $(".attentionBox").remove();
                $('body').css({'overflow':'auto'});
                _afterClosed(event);
            });

        },
        confirm: function(options) {
            if (typeof options != "object") {
                throw  new Error("参数无效");
            }
            var _shadow = '<div class="windowShadow"></div>';
            var _title = options.title?options.title:"";
            var _info = options.info;
            var _no = options.no && typeof options.no === "function" ? options.no : function(event){};
            var _yes = options.yes && typeof options.yes === "function" ? options.yes : function(event){};


            if (!_info && _info.length <= 0 ) {
                throw  new Error("info不能为空！");
            }

            var _alertHtml = '<div class="attentionBox">';
            _alertHtml += '<span class="closebtn"></span>';
            _alertHtml += '<h3>';
            _alertHtml += _title;
            _alertHtml += '</h3>';
            _alertHtml += '<div class="attenContain">';
            _alertHtml += '<p>';
            _alertHtml += _info;
            _alertHtml += '</p>';
            _alertHtml += '</div>';
            _alertHtml += '<div class="btn_double"><span class="yes">确认</span><span class="no">取消</span></div>';
            _alertHtml += '</div>';
            var _modalHtml = _shadow + _alertHtml;
            $("body").append(_modalHtml);
            var s_top = $(window).scrollTop();
            var left = (window.screen.availWidth - $(".attentionBox").width()) / 2;
            var top = (window.screen.availHeight - $(".attentionBox").height()) / 2 + s_top;
            $('body').css({'overflow':'hidden'});
            $(".attentionBox").css({"top": top, "left": left});

            $(".closebtn").on("click", function(event) {
                $(".windowShadow").remove();
                $(".attentionBox").remove();
                $('body').css({'overflow':'auto'});
            });

            $(".no").on("click", function(event) {
                $(".windowShadow").remove();
                $(".attentionBox").remove();
                $('body').css({'overflow':'auto'});
                _no(event);
            });
            $(".yes").on("click", function(event) {
                $(".windowShadow").remove();
                $(".attentionBox").remove();
                $('body').css({'overflow':'auto'});
                _yes(event);
            });

        }
    },
    addDate: function(a, dadd){
        a = a.valueOf();
        a = a + dadd * 24 * 60 * 60 * 1000;
        a = new Date(a);
        var fullYear = a.getFullYear();
        var month = a.getMonth()+1 < 10? "0"+(a.getMonth()+1): a.getMonth()+1;
        var day = a.getDate() < 10? "0" +  a.getDate():  a.getDate();
        return fullYear + "-" + month + "-" + day;
    }

}));