var MyContact = {
    pager: null,
    optType: null,
    init: function() {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                //MyContact.initComp();
                MyContact.initEvt();
                MyContact.createPager();
                MyContact.initData();
                MyContact.brower();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function () {
        // center box
        var left = (window.screen.width - $('.setpswordBox').width())/2;
        var top = (window.screen.height - $('.setpswordBox').height())/2.5;
        $('.setpswordBox').css({'left':left,'top':top});
        // date sel
        $('#birthDateSel').datetimepicker({
            closeOnDateSelect: true,
            scrollInput: false,
            todayButton: false,
            timepicker: false,
            format: "YYYY-MM-DD",
            formatDate: "YYYY-MM-DD",
            maxDate: 0
        });
    },
    initEvt: function() {
        // tourist item sel
        $('span.index').on('click', function (event) {
            event.stopPropagation();
            var state = $(this).hasClass('check');
            if (state == false) {
                $(this).addClass('check');
            } else {
                $(this).removeClass('check');
            }
        });
        //// open box
        $('.createMarch').on('click', function (event) {
            event.stopPropagation();
            MyContact.optType = "add";
            Popup.openAddTourist();
        });

        // edit
        $(document).on('click', 'label.edi', function (event) {
            event.stopPropagation();
            MyContact.editTouristInfo($(this).attr('data-t-id'));
        });
        // del
        $(document).on('click', 'label.del', function(event) {
            event.stopPropagation();
            MyContact.doDelTourist($(this).attr('data-t-id'));
        });

        // save
        $('#saveTbTN').on('click', function(event) {
            event.stopPropagation();
            if (Tourist.doCheckTouristInfoForm()) {
                $.form.commit({
                    formId: '#tourist_form',
                    url: '/yhypc/personal/doSaveTourist.jhtml',
                    success: function(result) {
                        if (result.success) {
                            var info = "";
                            if (MyContact.optType && MyContact.optType == "add") {
                                info = "新增成功！";
                            } else {
                                info = "联系人编辑成功！"
                            }
                            $.message.alert({
                                title: "提示",
                                info: info,
                                afterClosed: function() {
                                    Popup.closeDialog();    //关闭弹窗
                                    MyContact.getMyTourist();
                                    // 清空表单数据
                                    Tourist.clearTouristInfoForm();
                                }
                            });

                        } else {
                            $('#save_msg').html(null).html(result.msg);
                        }
                    },
                    error: function() {
                        $('#save_msg').html(null).html("操作失败, 请重试");
                    }
                });
            }
            //MyContact.doSaveTouristInfo();
        });
    },
    openTouristBox: function() {
        $('.shadow').show();
        $('.setpswordBox').show();
        $('body').css({'overflow': 'hidden'});
    },
    closeTouristBox: function() {
        $('.shadow').hide();
        $('.setpswordBox').hide();
        $('body').css({'overflow': 'auto'});
        // clear data
        $.form.reset({formId: '#tourist_form'});
        $('#sexSel span').removeClass('checksex');
        $('#idTypeSel span').removeClass('checksex');
        $('#peopleTypeSel span').removeClass('checksex');
        $('#save_msg').html(null)
    },
    createPager: function() {
        var page = {
            countUrl: '/yhypc/personal/countMyTourist.jhtml',
            resultCountFn: function (result) {
                if (result.success) {
                    return parseInt(result.count);
                } else {
                    if (result.code == "req_login") {YhyUser.goLogin();}
                    return 0;
                }
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/personal/getMyTourist.jhtml',
                    data: data,
                    progress: true,
                    success: function(result) {
                        if (result.success) {
                            $('#tourist_content').empty();
                            $.each(result.data, function(i, t) {
                                t.index = i + 1;
                                var touristItem = template('tourist_item', t);
                                $('#tourist_content').append(touristItem);
                            });
                            scroll(0, 480);
                        }
                    }
                });
            }
        };
        MyContact.pager = new Pager(page);
    },
    initData: function() {
        MyContact.getMyTourist();
    },
    brower: function(){
        var browser = window.navigator;
        if(browser.userAgent.indexOf("Chrom") >= 0){
            $('.setpswordBox').addClass('chromBox');
        }else if( browser.userAgent.indexOf("Firefox") >= 0){
            $('.setpswordBox').addClass('FirefoxBox');
        }
    },
    getMyTourist: function() {
        var searchRequest = {};
        MyContact.pager.init(searchRequest);
    },
    editTouristInfo: function(id) {
        $.form.load({
            formId: '#tourist_form',
            url: '/yhypc/personal/getTouristInfo.jhtml',
            formData: {id: id},
            onLoadSuccess: function(result) {
                MyContact.optType = "edit";
                if (result.success) {
                    // sex sel
                    $('#sexSel span[data-sex-value = "' + result['tourist.gender'] + '"]').click();
                    // id type sel
                    $('#idTypeSel span[data-idType-value = "' + result['tourist.idType'] + '"]').click();
                    // people type sel
                    $('#peopleTypeSel span[data-peopleType-value = "' + result['tourist.peopleType'] + '"]').click();
                } else {
                    if (result.code == "req_login") {YhyUser.goLogin();}
                    $('#save_msg').html(null).html(result.msg);
                }
                Popup.openAddTourist();
            },
            onLoadError: function() {
                Popup.openAddTourist();
                $('#save_msg').html(null).html("操作失败, 请重试");
            }
        })
    },

    doDelTourist: function(id) {
        $.ajax({
            url: '/yhypc/personal/doDelTourist.jhtml',
            data: {id: id},
            progress: true,
            success: function(result) {
                if (result.success) {
                    MyContact.getMyTourist();
                }
            },
            error: function() {
            }
        });
    }
};


$(function(){
    MyContact.init();
});