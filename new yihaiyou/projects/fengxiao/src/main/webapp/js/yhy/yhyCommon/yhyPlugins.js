/**
 * Created by zzl on 2016/11/9.
 */
($.extend({
    /**
     * options.scopeId：选择器
     * options.data：待加载数据
     * @param options
     * 要求：需要给标签设置model属性，并设属性值（data中的key值）
     */
    loadData: function(options) {
        var _scopeId = options.scopeId;
        var _data = options.data && typeof options.data === "object" ? options.data : {};
        if (typeof _scopeId !== "string") {
            throw new Error("scopeId must be a string !");
        }
        var $scope = $(_scopeId);
        for (key in _data) {
            var key = key;
            var value = _data[key];
            $scope.find("[model='"+key+"'],[model='"+key+"[]']").each(function(){
                tagName = $(this)[0].tagName;
                if (tagName == 'INPUT') {
                    var type = $(this).attr('type');
                    if (type == 'radio') {
                        $(this).attr('checked', $(this).val() == value);
                    } else if (type=='checkbox') {
                        if (Object.prototype.toString.call(value) === '[object Array]') {
                            for(var i = 0; i < value.length; i++){
                                if($(this).val() == value[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        } else {
                            var  arr = value.split(',');
                            for(var i = 0; i < arr.length; i++) {
                                if ($(this).val() == arr[i]) {
                                    $(this).attr('checked', true);
                                    break;
                                }
                            }
                        }
                    } else {
                        $(this).val(value);
                    }
                } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                    $(this).val(value);
                } else if (tagName == 'IMG') {
                    $(this).attr("src", value);
                } else {
                    $(this).html(value);
                }
            });
        }
    },
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
                            };
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
    messager: {
        show: function(options) {
            if (typeof options !== "object") {
                throw new Error("options must be an object")
            }
            options = $.extend({type: 'info', btns: [], backdrop: 'static'}, options);
            var typeArray = ["info", "success", "warn", "error"];
            var _stamp = new Date().getTime();
            var _title = options.title;
            var _msg = options.msg;
            var _btns =  options.btns;
            var _type = options.type;
            var _iconCls = options.iconCls;
            var _width = options.width;
            var _backdrop = options.backdrop ? options.backdrop : "static";
            var _timeout = options.timeout && typeof options.timeout === "number" ? options.timeout : 0;
            var _afterShown = options.afterShown && typeof options.afterShown === "function" ? options.afterShown : function(event){};
            var _onShow = options.onShow && typeof options.onShow === "function" ? options.onShow : function(event){};
            var _afterClosed = options.afterClosed && typeof options.afterClosed === "function" ? options.afterClosed : function(event){};
            var _onClose = options.onClose && typeof options.onClose === "function" ? options.onClose : function(event){};
            var _onLoadSuccess = options.onLoadSuccess && typeof options.onLoadSuccess ? options.onLoadSuccess : null;
            _type = _type && typeof _type === "string"? _type : "info";
            if (!_msg || _msg.length <= 0) {
                throw new Error("msg can not be null or empty");
            };
            if (!(_btns instanceof Array)) {
                throw new Error("btns must be an Array");
            }
            // modal, modal-dialog, modal-content
            var _modalHtml = "<div id='dialog_" + _stamp + "' class='modal' role='dialog' tabindex='-1'>";
            _modalHtml += "<div class='modal-dialog yhy-modal-dialog'>";
            _modalHtml += "<div class='modal-content yhy-modal-content ";
            if (!_title || _title <= 0) {
                _modalHtml += "without-title ";
                _modalHtml += _type;
            }
            _modalHtml += "'>";
            // title
            if (_title && _title.length > 0) {
                _modalHtml += "<div class='modal-header yhy-modal-header ";
                _modalHtml += _type;
                _modalHtml += "'>";
                _modalHtml += "<button type='button' class='close yhy-btn-close'";
                _modalHtml += "data-dismiss='modal'>";
                _modalHtml += "<span>&times;</span>";
                _modalHtml += "</button>";
                _modalHtml += "<h4 class='modal-title yhy-modal-title ";
                _modalHtml += _type;
                _modalHtml += "'>";
                _modalHtml += _title;
                _modalHtml += "</h4>";
                _modalHtml += "</div>";
            }
            // body
            _modalHtml += "<div class='modal-body " + _iconCls + "'>";
            if (!_title || _title.length <= 0) {
                _modalHtml += "<button type='button' class='close yhy-btn-close' data-dismiss='modal'>";
                _modalHtml += "<span>&times;</span>";
                _modalHtml += "</button>";
            }
            _modalHtml += "<p>" + _msg + "</p>";
            _modalHtml += "</div>";
            // footer
            if (_btns.length > 0) {
                _modalHtml += "<div class='modal-footer yhy-modal-footer'>";
                _modalHtml += "<div class='btn-area'>";
                $.each(_btns, function(i, btn) {
                    var btnStamp = new Date().getTime() + i;
                    var _btnCls = btn.btnCls && btn.btnCls.length > 0 ? btn.btnCls : "btn-default";
                    var _btnText = btn.btnText && btn.btnText.length > 0 ? btn.btnText : "button_" + i;
                    var _callback = btn.callback && typeof btn.callback === "function" ? btn.callback : function(event){};
                    _modalHtml += "<button id='button_" + btnStamp + "' type='button' class='btn ";
                    _modalHtml += _btnCls;
                    _modalHtml += "'>";
                    _modalHtml += _btnText;
                    _modalHtml += "</button>";
                    $('body').on('click', '#button_' + btnStamp,  {
                        dialog: '#dialog_' + _stamp,
                        btn: '#button_' + btnStamp}, _callback);
                });
                _modalHtml += "</div></div>";
            }
            _modalHtml += "</div></div></div>";
            $('body').append(_modalHtml);
            // 垂直居中计算
            var top = (window.screen.availHeight - $('#dialog_' + _stamp + ' div.modal-content').height()) / 2;
            //var scrollY =  document.documentElement.scrollTop || document.body.scrollTop;
            top = top > 100 ? top : 100;
            $('#dialog_' + _stamp + ' div.modal-content').css('margin-top', top - 100);
            // modal dialog width judgment
            if (_width && _width > 0) {
                _width = _width <= 160 ? 160 : _width;
            } else if(!_width || _width === "auto" || _width === "" || _width <= 0) {
                _width = _msg.length * 25 <= 160 ? 160 : _msg.length * 25;
            }
            $('#dialog_' + _stamp + ' div.yhy-modal-dialog').css('width', _width);
            $('#dialog_' + _stamp).modal({backdrop: _backdrop, keyboard: false});
            $('#dialog_' + _stamp).on('show.bs.modal', _onShow);
            $('#dialog_' + _stamp).on('shown.bs.modal', _afterShown);
            $('#dialog_' + _stamp).on('hide.bs.modal', _onClose);
            $('#dialog_' + _stamp).on('hidden.bs.modal', function(event) {
                _afterClosed(event);
                $('#dialog_' + _stamp).remove();
                // exist opened modal dialog judgment
                if ($('body').find('.modal').hasClass('in')) {
                    $('body').addClass('modal-open');
                }
            });
            if (_onLoadSuccess) {
                $('#dialog_' + _stamp).on('loaded.bs.modal', _onLoadSuccess);
            };
            if (_timeout > 0) {
                setTimeout(function() {
                    $('#dialog_' + _stamp).modal('hide');
                }, _timeout);
            }
            $('#dialog_' + _stamp).modal('show');
        }
    }
}));