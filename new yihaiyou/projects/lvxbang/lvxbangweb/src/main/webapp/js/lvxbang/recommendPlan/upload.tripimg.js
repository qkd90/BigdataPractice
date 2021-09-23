/**
 * Created by zzl on 2016/3/9.
 */
//上传器对象
//var tripimg_uploader = null;
// 文件总大小限制
var tripimg_max_total_fileSize = 0;
// 单个文件大小限制
var tripimg_max_singlel_fileSize = 0;
function createTripImgUploader(options) {
    if (isNull(options)) {
        window.console.log('创建上传器时, 配置错误!请传入基本参数!')
        return;
    }
    //通用图片上传配置
    var tripimg_uploader = WebUploader.create({
        swf: '/js/lib/webuploader/Uploader.swf',
        server: options.url,
        pick: {
            id: options.picker,
            innerHTML: '',
            multiple: true
        },
        auto: false, // 关闭自动上传
        accept: {   // 接受的图片文件类型选项
            title: 'Images',
            extensions: 'jpg,jpeg,png,gif,bmp',
            mimeTypes: 'image/*'
        },
        thumb: {
            width: 110,
            height: 110,
            // 缩略图质量,仅对jpg/jpeg格式有效
            quality: 80,
            // 是否允许被放大(设为false时缩略图不会失真)
            allowMagnify: true,
            // 是否允许裁剪
            crop: false,
            // 强制转为jpeg格式
            type: 'image/jpeg'
        },
        compress: { // 图片上传压缩选项, 此处保留500W像素, 90%质量
            width: 2560,
            height: 1920,
            quality: 90,
            allowMagnify: false,
            //关闭自动裁剪
            crop: false,
            // 是否保留头部meta信息。
            preserveHeaders: true,
            // 不要将图片压缩地比原来更大,此参数影响图片自动纠正功能
            noCompressIfLarger: true,
            // 如果图片小于此字节,则不会压缩
            compressSize: 512000
        },
        // 开启预先准备下个文件
        prepareNextFile: true,
        // 开启分片上传
        chunked: true,
        // 单片1M
        chunkSize: 1048576,
        // 某分片上传失败, 重试10次
        chunkRetry: 10,
        // 开启5个上传线程
        threads: 5,
        // 上传时附带的表单数据, 预留, 留空
        formData: options.formData,
        // 上传使用的方式
        method: 'POST',
        // 单次批量最多几张图片
        fileNumLimit: 20,
        // 单个文件限制5M大小
        fileSingleSizeLimit: 5242880,
        // 文件总大小限制50M大小
        fileSizeLimit: 52428800,
        // 允许重复文件
        duplicate: false
    });
    //总上传文件数目
    var total_file_num = 0;
    //成功上传文件数目
    var success_file_num = 0;
    //总进度条比例单位
    var total_progress_unit = 0.0;
    // 总进度条长度
    var total_progress_width = 0.0;
    // 当前已加入队列的文件数目
    //var current_file_num = 0;
    // 单次最多文件数目
    //var max_file_num = tripimg_uploader.options.fileNumLimit;
    // 文件总大小限制
    tripimg_max_total_fileSize = WebUploader.Base.formatSize(tripimg_uploader.options.fileSizeLimit, 0, ['B', 'KB', 'MB']);
    // 单个文件大小限制
    tripimg_max_singlel_fileSize = WebUploader.Base.formatSize(tripimg_uploader.options.fileSingleSizeLimit, 0, ['B', 'KB', 'MB'])
    $("#upload_tip_a").html('').html(
        '本次最多可以上传' + tripimg_uploader.options.fileNumLimit + '张图片'
        + ', 图片选择完成后, 请点击开始上传!'
    )
    $("#upload_tip_b").html('').html(
        '单张图片不可超过' + tripimg_max_singlel_fileSize
        + ', 全部图片大小不可超过' + tripimg_max_total_fileSize
    );
    //TODO
    //bugs: 问题: tripimg_uploader.getStats().queueNum 队列中的文件数目为何一直为0 ???
    // 文件加入队列之前的操作 // 此处拦截超出数量限制的文件
    //tripimg_uploader.on('beforeFileQueued', function (file) {
    //    if (current_file_num >= max_file_num) {
    //        return false;
    //    } else {
    //        return true;
    //    }
    //});
    //当文件队列被加入时候
    tripimg_uploader.on('fileQueued', function (file) {
        //打开上传组件窗口
        if (!$(".Upload_xp").hasClass("open") && !$(".mask").hasClass("open")) {
            $(".Upload_xp").addClass("open");
            $(".mask").addClass("open");
        }
        // 当前队里中的文件数目 加一!!
        //current_file_num += 1;
        var $li = $('<li id="f_' + file.id + '">'
            + '<img><div class="s"><span style="">' + file.name + '</span></div>'
            + '<p>等待上传</p><i class="closex"></i></li>');
        var $img = $li.find('img');
        var $info = $li.find('p');
        var $close = $li.find("i.closex");
        $(".Upload_xp_ul").append($li);
        //创建缩略图
        tripimg_uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>暂无预览!</span>');
                //$info.html('').html('等待上传');
                return;
            }
            $img.attr('src', src);
        });
        // 绑定单个图片文件取消上传事件
        $close.click(function () {
            // 从队列中移除该文件
            tripimg_uploader.removeFile(file, true);
            $("#total_file_num").html(Number($("#total_file_num").html()) - 1);
            // 总文件数目减一
            total_file_num -= 1;
            // 重新计算总上传进度条单位
            total_progress_unit = Number((100 / total_file_num).toFixed(1));//保留1位小数
            //console.log('新进度条单位: ' + total_progress_unit);
            $li.remove();
            if (total_file_num == 0) {
                // 重新显示文件选择按钮
                //$("#picker").show();
                $(".Upload_xp").find("i.close").click();
            }
        });
    });
    //当文件队列加载完成时候(所有文件!!)
    tripimg_uploader.on('filesQueued', function (files) {
        //没有文件加载到队列时候, 直接返回, 不显示上传组件
        total_file_num = files.length;
        if (total_file_num <= 0) {
            return;
        }
        // 隐藏文件选择按钮
        $("#picker").hide();
        // 解除之前打开上传窗口时候绑定的窗口关闭点击事件
        $(".Upload_xp").find("i.close").unbind('click');
        // 解除之前打开上传窗口时候绑定的上传点击事件
        $("#start_upload_btn").unbind('click');
        total_progress_unit = Number((100 / total_file_num).toFixed(1));//保留1位小数
        //console.log(totalFileNum);
        //console.log(total_progress_unit);
        total_file_num += Number($("#total_file_num").html());
        //$("#upload_count_info").html('');
        $("#upload_count_info").find('span.orange').html('0');
        $("#total_file_num").html(total_file_num);
        //$("#upload_count_info").html('<span class="orange">0</span>/<span>' + total_file_num + '</span>张');
        $("#upload_total_info").find("span").css('width', '0%');
        //注册上传按钮事件
        $("#start_upload_btn").click(function () {
            if (Number($("#total_file_num").html()) <= 0) {
                promptWarn("没有需要上传的文件!");
                //alert('没有需要上传的文件!');
                return;
            }
            // 当上传开始后, 解除所有队列中图片的取消上传事件, 不可再从队列中移除此文件
            $.each($(".Upload_xp_ul li i"), function (i, e) {
                $(this).unbind('click');
                $(this).remove();
            });
            tripimg_uploader.upload();
            $("#start_upload_btn").html('正在上传');
            // 解除本次的上传点击事件响应(直到本次上传操作全部完成,无论成功失败!,详见下方[uploadFinished]事件处理)
            $("#start_upload_btn").unbind('click');
        });
        //注册上传框关闭事件, 完成文件队列的情况操作
        $(".Upload_xp").find("i.close").click(function () {
            //将队列中的文件移除 如果有的话
            //console.log(files.length);
            //console.log(tripimg_uploader);
            $.each(files, function (i, file) {
                //console.log(file);
                if (tripimg_uploader != null) {
                    tripimg_uploader.removeFile(file, true);
                }
            });
            //console.log('队列中文件数目-关闭窗口后: ' + tripimg_uploader.getStats().queueNum);
            // 重置上传器 !!重要!!
            resetQueue();
            // 关闭上传窗口
            $(".Upload_xp").removeClass("open");
            $(".mask").removeClass("open");
            // 清空窗口中显示的总文件数目/列表
            $("#total_file_num").html('');
            // 清空文件列表
            $(".Upload_xp_ul").html('');
            // 重置进度条和计数信息
            $("#upload_count_info").find('span.orange').html('0');
            $("#total_file_num").html('0');
            $("#upload_total_info").find("span").css('width', '0%');
            // 显示文件选择按钮
            $("#picker").show();
            // 恢复按钮文字
            $("#start_upload_btn").html('开始上传');
            // 取消本次窗口关闭按钮绑定事件, 避免重复绑定
            $(".Upload_xp").find("i.close").unbind('click');
            // 取消本次上传按钮事件, 避免重复绑定
            $("#start_upload_btn").unbind('click');
            //console.log(tripimg_uploader.getStats().queueNum);
        });
    });
    //某个文件开始上传前触发, 一个文件只会触发一次
    tripimg_uploader.on('uploadStart', function (file) {
        var $li = $("#f_" + file.id);
        var $info = $li.find("p");
        $info.html('').html('正在上传');
    });
    //文件上传过程中,进度条事件
    tripimg_uploader.on('uploadProgress', function (file, percentage) {
        var $li = $("#f_" + file.id);
        var $percent = $li.find("span");
        percentage = (percentage * 100).toFixed(1);//上传百分比保留一位小数
        if (percentage == 100) {
            $percent.html('').css('width', percentage + '%').html('正在保存');
        } else {
            $percent.html('').css('width', percentage + '%').html(percentage + '%');
        }
    });
    //当某个文件上传到服务器!成功时候
    tripimg_uploader.on('uploadSuccess', function (file, response) {
        var $li = $("#f_" + file.id);
        var $percent = $li.find("span");
        // 执行某张图片上传到七牛成功后的回调函数
        var saveResult = options.singleSuccessHandler(response, file);
        if (saveResult) {
            //获取已经成功上传的文件数目
            success_file_num = tripimg_uploader.getStats().successNum;
            // 上传总进度条累加
            total_progress_width += total_progress_unit;
            //console.log(total_progress_unit);
            //console.log(total_progress_width);
            $percent.html('').css('width', '100%').html('上传成功');
            $("#upload_count_info").find('span.orange').html(success_file_num);
            $("#upload_total_info").find('span').css('width', total_progress_width + "%");
        } else {
            tripimg_uploader.getStats().successNum -= 1;
            $percent.css('background-color', '#F44336');
            $percent.css('width', '100%');
            $percent.html('').html('上传失败!');
        }
    });
    //当某个文件上传错误时候
    tripimg_uploader.on('uploadError', function (file, reason) {
        var $li = $("#f_" + file.id);
        var $error = $li.find("span");
        $error.css('background-color', '#F44336');
        $error.css('width', '100%');
        $error.html('').html('上传失败!');
    });
    //当某个文件上传完成时候, 不管是否成功
    tripimg_uploader.on('uploadComplete', function (file) {
        var $li = $("#f_" + file.id);
        $li.find("p").remove();
    });
    // 当所有文件上传结束时候
    tripimg_uploader.on('uploadFinished', function () {
        // 执行上传操作全部完成后的回调函数(无论是否全部成功)
        var allSaveResult = options.allSuccessHandler();
        if (allSaveResult) {
            $("#start_upload_btn").html('').html('上传完成!');
            $("#start_upload_btn").click(function () {
                $(".Upload_xp").find("i.close").click();
                // 取消本次上传按钮事件, 避免重复绑定
                $("#start_upload_btn").unbind('click');
            });
            $(".Upload_xp").find("i.close").click();
        }
        // 重置上传器
        resetQueue();
    });
    // 上传对象发生错误时候
    tripimg_uploader.on('error', function (code) {
        //console.log(code);
        var info = '';
        switch (code) {
            case 'Q_EXCEED_NUM_LIMIT' :
                info = '文件数量超过限制,本次最多' + tripimg_uploader.options.fileNumLimit + '张';
                break;
            case 'Q_EXCEED_SIZE_LIMIT' :
                info = '文件总大小不能超过' + tripimg_max_total_fileSize;
                break;
            case 'F_EXCEED_SIZE' :
                info = '单个文件大小不能超过' + tripimg_max_singlel_fileSize;
                break;
            case 'F_DUPLICATE' :
                info = '选择了重复的文件!';
                break;
            case 'Q_TYPE_DENIED' :
                info = '文件类型错误!'
                break;
            default :
                info = '上错出错, 错误代码: ' + code;
                break;
        }
        //console.log(info);
        promptWarn(info);
        //alert(info);
    });

    function resetQueue() {
        // 重置上传器, 同时清空各项计数
        if (tripimg_uploader != null) {
            //tripimg_uploader.destroy();
            //tripimg_uploader = null;
            tripimg_uploader.reset();
        }
        //console.log('重置后队列中文件数目: ' + tripimg_uploader.getStats().queueNum);
        //console.log('队列中文件数目-重置后: ' + tripimg_uploader.getStats().queueNum);
        //console.log('队列中上传成功的文件数-重置后: ' + tripimg_uploader.getStats().successNum);
        //console.log('队列中上传中的文件数-重置后: ' + tripimg_uploader.getStats().progressNum);
        //console.log('队列中被删除的文件数-重置后: ' + tripimg_uploader.getStats().cancelNum);
        //console.log('队列中无效的文件数-重置后: ' + tripimg_uploader.getStats().invalidNum);
        //console.log('队列中上传失败的文件数-重置后: ' + tripimg_uploader.getStats().uploadFailNum);
        //console.log('队列中被暂停的文件数-重置后: ' + tripimg_uploader.getStats().interruptNum);
        //总上传文件数目
        total_file_num = 0;
        //成功上传文件数目
        success_file_num = 0;
        //总进度条比例单位
        total_progress_unit = 0.0;
        // 总进度条长度
        total_progress_width = 0.0;
        // 队列中的文件数目变量
        //current_file_num = 0;
    }

    //console.log(tripimg_uploader);
}
// 通用上传器初始化配置方法
function initTripImgUploader(options) {
    if (isNull(options)) {
        window.console.log('初始化上传器时, 配置错误!请传入基本参数!')
        return;
    }
    if (isNull(options.url)) {
        window.console.log('上传url地址不能为空!')
        return;
    }
    if (!isNull(options.formData) && typeof (options.formData) == 'object') {
        options['formData'] = options.formData;
    }else if (isNull(options.formData)) {
        options['formData'] = {};
    } else {
        window.console.log('配置上传器上传携带数据参数错误! 参数类型错误! 只可以为object!')
        return;
    }
    if (!isNull(options.singleSuccessHandler) && typeof (options.singleSuccessHandler) == 'function') {
        options['singleSuccessHandler'] = options.singleSuccessHandler;
    } else if (isNull(options.successHandler)) {
        options['singleSuccessHandler'] = function(response) {return true;};
    } else {
        window.console.log('配置上传器单个成功上传回调方法错误! 参数类型错误! 只可以为函数类型')
        return;
    }
    if (!isNull(options.allSuccessHandler) && typeof (options.allSuccessHandler) == 'function') {
        options['allSuccessHandler'] = options.allSuccessHandler;
    } else if (isNull(options.successHandler)) {
        options['allSuccessHandler'] = function(response) {return true;};
    } else {
        window.console.log('配置上传器所有成功上传回调方法错误! 参数类型错误! 只可以为函数类型')
        return;
    }
    // 创建上传器
    createTripImgUploader(options);
    $("#start_upload_btn").html('').html('开始上传');
    //打开上传组件窗口
    //$(".Upload_xp").addClass("open");
    //$(".mask").addClass("open");
    // 取消本次窗口关闭按钮绑定事件, 避免重复绑定
    $(".Upload_xp").find("i.close").unbind('click');
    // 取消本次上传按钮事件, 避免重复绑定
    $("#start_upload_btn").unbind('click');
    $(".Upload_xp").find("i.close").click(function () {
        $(".Upload_xp").removeClass("open");
        $(".mask").removeClass("open");
        tripimg_uploader.reset();
        //tripimg_uploader = null;
        // 取消本次窗口关闭按钮绑定事件, 避免重复绑定
        $(".Upload_xp").find("i.close").unbind('click');
        // 取消本次上传按钮事件, 避免重复绑定
        $("#start_upload_btn").unbind('click');
    });
    $("#start_upload_btn").click(function () {
        promptWarn("没有需要上传的文件!");
        //alert('没有需要上传的文件!');
        return;
    });
}