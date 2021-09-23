var editStep5 = {

    // 初始化
    init: function () {
        editStep5.initComp();
        editStep5.initListener();
        //editStep5.test();
        //editStep5.calendar();
        //editStep5.testTicket();
        editStep5.quartz();
    },

    // 初始控件
    initComp: function () {
        // 服务信息表格
        $('#serviceGrid').datagrid({
            url: '/cruiseship/cruiseShipProject/list.jhtml?classifyId=1',
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns: [[
                {
                    title: 'ID',
                    field: 'id',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '名称',
                    field: 'name',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '楼层',
                    field: 'level',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '容纳人数',
                    field: 'peopleNum',
                    width: 70,
                    align: 'center'
                },
                { field: 'classifyName', title: '分类名称', width: 80,
                    align: 'center',formatter: editStep5.classifyNameFormat

                },
                {
                    title: '介绍',
                    field: 'introduction',
                    width: 150
                },
                {
                    title: '着装类型',
                    field: 'suitType',
                    width: 150
                },
                {
                    title: '消费情况',
                    field: 'costStatus',
                    width: 150
                },
                {
                    title: '服务图片',
                    field: 'coverImage',
                    width: 200,
                    formatter: editStep5.serviceImgFormat
                },
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep5.openEditServiceDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],
            toolbar : '#serviceGridtb',
            onBeforeLoad : function(data) {
                data.shipId = $('#shipId').val();
            }
        });


        // 美食信息表格
        $('#foodGrid').datagrid({
            url: '/cruiseship/cruiseShipProject/list.jhtml?classifyId=2',
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns: [[
                {
                    title: 'ID',
                    field: 'id',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '名称',
                    field: 'name',
                    width: 70,
                    align: 'center'
                },
                { field: 'classifyName', title: '分类名称', width: 80,
                    align: 'center',formatter: editStep5.classifyNameFormat

                },
                {
                    title: '楼层',
                    field: 'level',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '容纳人数',
                    field: 'peopleNum',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '介绍',
                    field: 'introduction',
                    width: 150
                },
                {
                    title: '着装类型',
                    field: 'suitType',
                    width: 150
                },
                {
                    title: '消费情况',
                    field: 'costStatus',
                    width: 150
                },
                {
                    title: '开放状态',
                    field: 'openStatus',
                    width: 150
                },
                {
                    title: '美食图片',
                    field: 'coverImage',
                    width: 200,
                    formatter: editStep5.foodImgFormat
                },
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep5.openEditFoodDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],
            toolbar : '#foodGridtb',
            onBeforeLoad : function(data) {
                data.shipId = $('#shipId').val();
            }
        });


        // 娱乐信息表格
        $('#entainmentGrid').datagrid({
            url: '/cruiseship/cruiseShipProject/list.jhtml?classifyId=3',
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'id',
            columns: [[
                {
                    title: 'ID',
                    field: 'id',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '名称',
                    field: 'name',
                    width: 70,
                    align: 'center'
                },
                { field: 'classifyName', title: '分类名称', width: 80,
                    align: 'center',formatter: editStep5.classifyNameFormat

                },
                {
                    title: '楼层',
                    field: 'level',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '容纳人数',
                    field: 'peopleNum',
                    width: 70,
                    align: 'center'
                },
                {
                    title: '介绍',
                    field: 'introduction',
                    width: 150
                },
                {
                    title: '着装类型',
                    field: 'suitType',
                    width: 150
                },
                {
                    title: '消费情况',
                    field: 'costStatus',
                    width: 150
                },
                {
                    title: '开放状态',
                    field: 'openStatus',
                    width: 150
                },
                {
                    title: '娱乐图片',
                    field: 'coverImage',
                    width: 200,
                    formatter: editStep5.entainmentImgFormat
                },
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep5.openEditEntainmentDg(" + rowData.id + "," + rowIndex + ")'>编辑</a>";
                        return btn;
                    }
                }
            ]],
            toolbar : '#entainmentGridtb',
            onBeforeLoad : function(data) {
                data.shipId = $('#shipId').val();
            }
        });


        // 服务/美食/娱乐/设施富文本框设置
        $.each($("#serviceForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_service'
            });
        });
        $.each($("#foodForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_food'
            });
        });
        $.each($("#entainmentForm").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_entainment'
            });
        });
    },

    // 服务图片展示处理
    serviceImgFormat: function(value, rowData, rowIndex) {
        if (value != null && value != undefined && value != '') {
            var img = '<img src="' + BizConstants.QINIU_DOMAIN + value + '?imageView2/1/w/240/h/80" width="240" height="80"/>';
            var $img = $(img);
            $img.tooltip({
                position:'top',
                content:'<img src="'+ BizConstants.QINIU_DOMAIN + value + '?imageView2/2/w/500" width="500px"/>',
                showEvent:'mouseenter'
            });
            return img;
        }
        return '<span style="color: red; font-weight: bold">暂无图片</span>';
    },
    // 美食图片展示处理
    foodImgFormat: function(value, rowData, rowIndex) {
        if (value != null && value != undefined && value != '') {
            var img = '<img src="' + BizConstants.QINIU_DOMAIN + value + '?imageView2/1/w/240/h/80" width="240" height="80"/>';
            var $img = $(img);
            $img.tooltip({
                position:'top',
                content:'<img src="'+ BizConstants.QINIU_DOMAIN + value + '?imageView2/2/w/500" width="500px"/>',
                showEvent:'mouseenter'
            });
            return img;
        }
        return '<span style="color: red; font-weight: bold">暂无图片</span>';
    },
    // 娱乐图片展示处理
    entainmentImgFormat: function(value, rowData, rowIndex) {
        if (value != null && value != undefined && value != '') {
            var img = '<img src="' + BizConstants.QINIU_DOMAIN + value + '?imageView2/1/w/240/h/80" width="240" height="80"/>';
            var $img = $(img);
            $img.tooltip({
                position:'top',
                content:'<img src="'+ BizConstants.QINIU_DOMAIN + value + '?imageView2/2/w/500" width="500px"/>',
                showEvent:'mouseenter'
            });
            return img;
        }
        return '<span style="color: red; font-weight: bold">暂无图片</span>';
    },
    // 监听器
    initListener: function() {

    },
    // 服务图片上传控件
    initServiceImgUploader: function() {
        $('#service_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#service_imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#coverParent').html('<div id="coverBox"></div>');
                    $('#coverPath').prop('value', data.path);
                    $('#service_imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();
                    showImageWithoutCancel($('#coverBox'), address, data.id);
                });
                $('#fileBox_' + data.id + ' .diyCancel').click(function () {
                    if (data.path == $('#coverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#coverBox').next('div.parentFileBox').remove();
                        $('#coverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#service_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '上传图片',
            formData: {
                section: CruiseShipConstants.projectImg
            }
        });
    },
    // 美食图片上传控件
    initFoodImgUploader: function() {
        $('#food_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#food_imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#foodCoverParent').html('<div id="foodCoverBox"></div>');
                    $('#foodCoverPath').prop('value', data.path);
                    $('#food_imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();
                    showImageWithoutCancel($('#foodCoverBox'), address, data.id);
                });
                $('#fileBox_' + data.id + ' .diyCancel').click(function () {
                    if (data.path == $('#foodCoverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#foodCoverBox').next('div.parentFileBox').remove();
                        $('#foodCoverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#food_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '上传图片',
            formData: {
                section: CruiseShipConstants.projectImg
            }
        });
    },
    // 娱乐图片上传控件
    initEntainmentImgUploader: function() {
        $('#entainment_imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#entainment_imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#entainmentCoverParent').html('<div id="entainmentCoverBox"></div>');
                    $('#entainmentCoverPath').prop('value', data.path);
                    $('#entainment_imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();
                    showImageWithoutCancel($('#entainmentCoverBox'), address, data.id);
                });
                $('#fileBox_' + data.id + ' .diyCancel').click(function () {
                    if (data.path == $('#entainmentCoverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#entainmentCoverBox').next('div.parentFileBox').remove();
                        $('#entainmentCoverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#entainment_imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '上传图片',
            formData: {
                section: CruiseShipConstants.projectImg
            }
        });
    },

    // 服务信息查询
    searchService: function() {
        $('#serviceGrid').datagrid('load');
    },
    // 美食信息查询
    searchFood: function() {
        $('#foodGrid').datagrid('load');
    },
    // 娱乐信息查询
    searchEntainment: function() {
        $('#entainmentGrid').datagrid('load');
    },

    // 打开服务信息窗口
    openEditServiceDg: function(serviceId, rowIndex) {
        $("#serviceForm").form('clear');
        // 清空图片上传信息
        $('#serviceForm').find('.parentFileBox').remove();
        $('#serviceForm').find('#service_imageContent').empty();
        $('#editServiceDg').dialog({
            title:'服务信息',
            modal:true,

        });
        $('#editServiceDg').dialog('open');
        // 服务子分类表单控件
        $('#classifyName').combobox({
            url:'/cruiseship/cruiseShipProjectClassify/classifyNameList.jhtml?parentId=1',
            valueField:'id',
            textField:'classifyName'
        });


        // 初始化图片上传控件
        editStep5.initServiceImgUploader();
        var serviceRow = {};
        var service = {};
        if (serviceId) {
            var serviceRows = $('#serviceGrid').datagrid('getRows');
            serviceRow = serviceRows[rowIndex];
            service['cruiseShipProject.id'] = serviceRow.id;
            service['cruiseShipProject.name'] = serviceRow.name;
            service['cruiseShipProject.level'] = serviceRow.level;
            service['cruiseShipProject.peopleNum'] = serviceRow.peopleNum;
            service['cruiseShipProject.introduction'] = serviceRow.introduction;
            service['cruiseShipProject.suitType'] = serviceRow.suitType;
            service['cruiseShipProject.costStatus'] = serviceRow.costStatus;
            service['cruiseShipProject.showStatus'] = serviceRow.showStatus;
            service['cruiseShipProject.openStatus'] = serviceRow.openStatus;
            service['cruiseShipProject.coverImage'] = serviceRow.coverImage;
            service['cruiseShipProject.cruiseShipProjectClassify.id'] = serviceRow.cruiseShipProjectClassify.id;
            service['cruiseShipProject.cruiseShipProjectImage.path'] = serviceRow.coverImage;
            service['cruiseShipProject.cruiseShipProjectClassify.classifyName'] = serviceRow.classifyName;

        } else {
            //
            service['cruiseShipProject.suitType'] = null;
            service['cruiseShipProject.name'] = null;
        }
        service['cruiseShipProject.cruiseShip.id'] = $('#shipId').val();
        service['serviceChildFolder'] = CruiseShipConstants.projectImg;
        $('#serviceForm').form({
            onLoadSuccess: function(data) {
                if(serviceId) {
                    // 加载服务现有图片
                    editStep5.loadServiceImg(serviceId);
                }
            }
        });
        $('#serviceForm').form('load', service);
    },
    // 加载服务现有图片
    loadServiceImg: function(projectId) {
        $.ajax({
            url: '/cruiseship/cruiseShipProject/getImageList.jhtml?',
            dataType: 'json',
            data: {
                'cruiseShipProjectImage.projectId': projectId
            },
            success: function(result) {
                if (result.success) {
                    $.each(result.data, function (i, img) {
                        var address = BizConstants.QINIU_DOMAIN + img.path;
                        showImage($('#service_imagePanel'), address, img.id);
                        $('#fileBox_' + img.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                        $('#fileBox_' + img.id).find('.diyCover').click(function () {//增加事件
                            $('#coverParent').html('<div id="coverBox"></div>');
                            $('#coverPath').prop('value', img.path);
                            $('#coverImgId').prop('value', img.id);
                            $('#service_imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#coverBox'), address, img.id);
                        });
                        if (img.coverFlag) {
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#coverBox'), address, img.id);
                        }
                        $('#fileBox_' + img.id + ' .diyCancel').click(function () {
                            if (img.coverFlag) {
                                showMsgPlus('提示', '封面已经被删除!!', '3000');
                                $('#coverBox').next('div.parentFileBox').remove();
                                $('#coverPath').val(null);
                                $('#coverImgId').val(null);
                            }
                            $("#service_imageContent").append("<input type='hidden' name='imgDeleteIds' value='" + img.id + "'>");
                        });
                    });
                }
            },
            error: function (result) {
                console.log(result);
            }
        });
    },

    // 打开美食信息窗口
    openEditFoodDg: function(foodId, rowIndex) {
        $("#foodForm").form('clear');
        // 清空图片上传信息
        $('#foodForm').find('.parentFileBox').remove();
        $('#foodForm').find('#food_imageContent').empty();
        $('#editfoodDg').dialog({
            title:'美食信息',
            modal:true,

        });
        $('#editFoodDg').dialog('open');
        // 美食子分类表单控件
        $('#foodClassifyName').combobox({
            url:'/cruiseship/cruiseShipProjectClassify/classifyNameList.jhtml?parentId=2',
            valueField:'id',
            textField:'classifyName'
        });

        // 初始化图片上传控件
        editStep5.initFoodImgUploader();
        var foodRow = {};
        var food = {};
        if (foodId) {
            var foodRows = $('#foodGrid').datagrid('getRows');
            foodRow = foodRows[rowIndex];
            food['cruiseShipProject.id'] = foodRow.id;
            food['cruiseShipProject.name'] = foodRow.name;
            food['cruiseShipProject.level'] = foodRow.level;
            food['cruiseShipProject.introduction'] = foodRow.introduction;
            food['cruiseShipProject.suitType'] = foodRow.suitType;
            food['cruiseShipProject.showStatus'] = foodRow.showStatus;
            food['cruiseShipProject.openStatus'] = foodRow.openStatus;
            food['cruiseShipProject.peopleNum'] = foodRow.peopleNum;
            food['cruiseShipProject.costStatus'] = foodRow.costStatus;
            food['cruiseShipProject.cruiseShipProjectImage.path'] = foodRow.coverImage;
            food['cruiseShipProject.cruiseShipProjectClassify.id'] = foodRow.cruiseShipProjectClassify.id;
            food['cruiseShipProject.coverImage'] = foodRow.coverImage;
            food['cruiseShipProject.cruiseShipProjectImage.path'] = foodRow.foodImage;
        } else {
            //
            food['cruiseShipProject.suitType'] = null;
            food['cruiseShipProject.name'] = null;
        }
        food['cruiseShipProject.cruiseShip.id'] = $('#shipId').val();
        food['foodChildFolder'] = CruiseShipConstants.projectImg;
        $('#foodForm').form({
            onLoadSuccess: function(data) {
                if(foodId) {
                    // 加载服务现有图片
                    editStep5.loadFoodImg(foodId);
                }
            }
        });
        $('#foodForm').form('load', food);
    },
    // 加载美食现有图片
    loadFoodImg: function(projectId) {
        $.ajax({
            url: '/cruiseship/cruiseShipProject/getImageList.jhtml?',
            dataType: 'json',
            data: {
                'cruiseShipProjectImage.projectId': projectId
            },
            success: function(result) {
                if (result.success) {
                    $.each(result.data, function (i, img) {
                        var address = BizConstants.QINIU_DOMAIN + img.path;
                        showImage($('#food_imagePanel'), address, img.id);
                        $('#fileBox_' + img.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                        $('#fileBox_' + img.id).find('.diyCover').click(function () {//增加事件
                            $('#foodCoverParent').html('<div id="foodCoverBox"></div>');
                            $('#foodCoverPath').prop('value', img.path);
                            $('#coverImgId').prop('value', img.id);
                            $('#food_imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#foodCoverBox'), address, img.id);
                        });
                        if (img.coverFlag) {
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#foodCoverBox'), address, img.id);
                        }
                        $('#fileBox_' + img.id + ' .diyCancel').click(function () {
                            if (img.coverFlag) {
                                showMsgPlus('提示', '封面已经被删除!!', '3000');
                                $('#foodCoverBox').next('div.parentFileBox').remove();
                                $('#foodCoverPath').val(null);
                                $('#coverImgId').val(null);
                            }
                            $("#food_imageContent").append("<input type='hidden' name='imgDeleteIds' value='" + img.id + "'>");
                        });
                    });
                }
            },
            error: function (result) {
                console.log(result);
            }
        });
    },

    // 打开娱乐信息窗口
    openEditEntainmentDg: function(entainmentId, rowIndex) {
            $("#entainmentForm").form('clear');
            // 清空图片上传信息
            $('#entainmentForm').find('.parentFileBox').remove();
            $('#entainmentForm').find('#entainment_imageContent').empty();
        $('#editEntaopenEditinmentDg').dialog({
            title:'娱乐信息',
            modal:true,

        });
        $('#editEntainmentDg').dialog('open');
        // 娱乐子分类表单控件
        $('#entainmentClassifyName').combobox({
            url:'/cruiseship/cruiseShipProjectClassify/classifyNameList.jhtml?parentId=3',
            valueField:'id',
            textField:'classifyName',
            panelHeight: 'auto'
        });

        // 初始化图片上传控件
        editStep5.initEntainmentImgUploader();
        var entainmentRow = {};
        var entainment = {};
        if (entainmentId) {
            var entainmentRows = $('#entainmentGrid').datagrid('getRows');
            entainmentRow = entainmentRows[rowIndex];
            entainment['cruiseShipProject.id'] = entainmentRow.id;
            entainment['cruiseShipProject.name'] = entainmentRow.name;
            entainment['cruiseShipProject.level'] = entainmentRow.level;
            entainment['cruiseShipProject.introduction'] = entainmentRow.introduction;
            entainment['cruiseShipProject.suitType'] = entainmentRow.suitType;
            entainment['cruiseShipProject.costStatus'] = entainmentRow.costStatus;
            entainment['cruiseShipProject.openStatus'] = entainmentRow.openStatus;
            entainment['cruiseShipProject.peopleNum'] = entainmentRow.peopleNum;
            entainment['cruiseShipProject.showStatus'] = entainmentRow.showStatus;
            entainment['cruiseShipProject.cruiseShipProjectImage.path'] = entainmentRow.coverImage;
            entainment['cruiseShipProject.cruiseShipProjectImage.path'] = entainmentRow.path;
            entainment['cruiseShipProject.cruiseShipProjectClassify.id'] = entainmentRow.cruiseShipProjectClassify.id;
            entainment['cruiseShipProject.coverImage'] = entainmentRow.path;
            entainment['cruiseShipProject.cruiseShipProjectImage.path'] = entainmentRow.entainmentImage;
        } else {
            //
            entainment['cruiseShipProject.suitType'] = null;
            entainment['cruiseShipProject.name'] = null;
        }
        entainment['cruiseShipProject.cruiseShip.id'] = $('#shipId').val();
        entainment['entainmentChildFolder'] = CruiseShipConstants.projectImg;
        $('#entainmentForm').form({
            onLoadSuccess: function(data) {
                if(entainmentId) {
                    // 加载服务现有图片
                    editStep5.loadEntainmentImg(entainmentId);
                }
            }
        });
        $('#entainmentForm').form('load', entainment);
    },
    // 加载娱乐现有图片
    loadEntainmentImg: function(projectId) {
        $.ajax({
            url: '/cruiseship/cruiseShipProject/getImageList.jhtml?',
            dataType: 'json',
            data: {
                'cruiseShipProjectImage.projectId': projectId
            },
            success: function(result) {
                if (result.success) {
                    $.each(result.data, function (i, img) {
                        var address = BizConstants.QINIU_DOMAIN + img.path;
                        showImage($('#entainment_imagePanel'), address, img.id);
                        $('#fileBox_' + img.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                        $('#fileBox_' + img.id).find('.diyCover').click(function () {//增加事件
                            $('#entainmentCoverParent').html('<div id="entainmentCoverBox"></div>');
                            $('#entainmentCoverPath').prop('value', img.path);
                            $('#coverImgId').prop('value', img.id);
                            $('#entainment_imageBox').find('.coverSuccess').hide();
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#entainmentCoverBox'), address, img.id);
                        });
                        if (img.coverFlag) {
                            $('#fileBox_' + img.id).find('.coverSuccess').show();
                            showImageWithoutCancel($('#entainmentCoverBox'), address, img.id);
                        }
                        $('#fileBox_' + img.id + ' .diyCancel').click(function () {
                            if (img.coverFlag) {
                                showMsgPlus('提示', '封面已经被删除!!', '3000');
                                $('#entainmentCoverBox').next('div.parentFileBox').remove();
                                $('#entainmentCoverPath').val(null);
                                $('#coverImgId').val(null);
                            }
                            $("#entainment_imageContent").append("<input type='hidden' name='imgDeleteIds' value='" + img.id + "'>");
                        });
                    });
                }
            },
            error: function (result) {
                console.log(result);
            }
        });
    },
    // 关闭服务编辑窗口
    closeEditServiceDg: function() {
        $('#editServiceDg').dialog('close');
    },
    // 关闭美食编辑窗口
    closeEditFoodDg: function() {
        $('#editFoodDg').dialog('close');
    },
    // 关闭娱乐编辑窗口
    closeEditEntainmentDg: function() {
        $('#editEntainmentDg').dialog('close');
    },

    //分类名称展示处理
    classifyNameFormat:function(value,rowData,rowIndex){
        if(rowData.cruiseShipProjectClassify.id){
            var str = rowData.cruiseShipProjectClassify.classifyName;
        }
        return str;
    },
    // 清除服务表单数据
    clearServiceForm: function() {
        $('serviceForm').form('clear');
        // 清楚服务图片信息
        $('#serviceForm').find('.parentFileBox').remove();
        $('#serviceForm').find('#imageContent').empty();
    },
    // 清除美食表单数据
    clearFoodForm: function() {
        $('foodForm').form('clear');
        // 清楚美食图片信息
        $('#foodForm').find('.parentFileBox').remove();
        $('#foodForm').find('#imageContent').empty();
    },
    // 清除娱乐表单数据
    clearEntainmentForm: function() {
        $('entainmentForm').form('clear');
        // 清楚娱乐图片信息
        $('#entainmentForm').find('.parentFileBox').remove();
        $('#entainmentForm').find('#imageContent').empty();
    },

    // 服务信息保存
    saveService: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $('#serviceForm').form('submit', {
            url: '/cruiseship/cruiseShipProject/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '请完善服务信息!', 'error');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress("close");
                var result = eval('(' + data + ')');
                if (result.success) {
                    editStep5.closeEditServiceDg();
                    editStep5.searchService();
                } else {
                    showMsgPlus('提示', '保存失败', 2000);
                }
            }
        });
    },
    // 美食信息保存
    saveFood: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $('#foodForm').form('submit', {
            url: '/cruiseship/cruiseShipProject/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '请完善美食信息!', 'error');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress("close");
                var result = eval('(' + data + ')');
                if (result.success) {
                    editStep5.closeEditFoodDg();
                    editStep5.searchFood();
                } else {
                    showMsgPlus('提示', '保存失败', 2000);
                }
            }
        });
    },
    // 娱乐信息保存
    saveEntainment: function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $('#entainmentForm').form('submit', {
            url: '/cruiseship/cruiseShipProject/save.jhtml',
            onSubmit: function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '请完善娱乐信息!', 'error');
                }
                return isValid;
            },
            success: function(data) {
                $.messager.progress("close");
                var result = eval('(' + data + ')');
                if (result.success) {
                    editStep5.closeEditEntainmentDg();
                    editStep5.searchEntainment();
                } else {
                    showMsgPlus('提示', '保存失败', 2000);
                }
            }
        });
    },

    // 删除服务信息
    doDelService: function() {
        var row = $('#serviceGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的服务信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipProject/delProject.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            editStep5.searchService();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "删除成功!", 'error');
                            editStep5.searchService();
                        }
                    })
                }
            }
        );
    },
    // 删除美食信息
    doDelFood: function() {
        var row = $('#foodGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的美食信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipProject/delProject.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            editStep5.searchFood();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            editStep5.searchFood();
                        }
                    })
                }
            }
        );
    },
    // 删除娱乐信息
    doDelEntainment: function() {
        var row = $('#entainmentGrid').datagrid('getSelected');
        if (row == null) {
            showMsgPlus("提示", "请选择要删除的娱乐信息!", 3000);
            return;
        }
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
                if (r) {
                    $.messager.progress({
                        title:'温馨提示',
                        text:'处理中,请耐心等待...'
                    });
                    $.ajax({
                        url: '/cruiseship/cruiseShipProject/delProject.jhtml',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            id: row.id
                        },
                        success: function(result) {
                            $.messager.progress('close');
                            if (result.success) {
                                $.messager.alert('提示', result.msg, "info");
                            } else if (result) {
                                $.messager.alert('提示', result.msg, "error");
                            }
                            editStep5.searchEntainment();
                        },
                        error: function(result) {
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                            editStep5.searchEntainment();
                        }
                    })
                }
            }
        );
    },






    //test: function() {
    //
    //                $.ajax({
    //                    url: '/cruiseship/cruiseShipProject/test.jhtml',
    //                    type: 'post',
    //                    dataType: 'json',
    //                    data: {
    //
    //                    },
    //                    success: function(result) {
    //
    //                    },
    //                    error: function(result) {
    //
    //                    }
    //                })
    //},
    //
    //
    //calendar: function() {
    //
    //    $.ajax({
    //        url: '/cruiseship/cruiseShipProject/calendar.jhtml',
    //        type: 'post',
    //        dataType: 'json',
    //        data: {
    //
    //        },
    //        success: function(result) {
    //
    //        },
    //        error: function(result) {
    //
    //        }
    //    })
    //},
    //
    //testTicket: function() {
    //
    //    $.ajax({
    //        url: '/cruiseship/cruiseShipProject/syncTicket.jhtml',
    //        type: 'post',
    //        dataType: 'json',
    //        data: {
    //
    //        },
    //        success: function(result) {
    //
    //        },
    //        error: function(result) {
    //            ;
    //        }
    //    })
    //},

    quartz: function() {

        $.ajax({
            url: '/cruiseship/cruiseShipProject/quartz.jhtml',
            type: 'post',
            dataType: 'json',
            data: {

            },
            success: function(result) {

            },
            error: function(result) {
                ;
            }
        })
    },

    // 下一步
    nextGuide: function (winId) {
        parent.window.showGuide(winId, true);
    }
};

//返回本页面数据
function getIfrData(){
    var data = {};
    return data;
}


$(function(){
    editStep5.init();
});