/**
 * Created by zzl on 2016/3/7.
 */

var cover_uploader = null;
var cover_max_total_fileSize = 0;
var cover_max_single_fileSize = 0;
function createCoverUploader(options) {
    if (isNull(options)) {
        window.console.log('创建上传器时, 配置错误!请传入基本参数!')
        return;
    }
    // 封面上传器配置
    cover_uploader = WebUploader.create({
        swf: '/js/lib/webuploader/Uploader.swf',
        server: options.url,
        pick: {
            id: options.picker,
            innerHTML: '',
            multiple: false
        },
        accept: {   // 接受的图片文件类型选项
            title: 'Images',
            extensions: 'jpg,jpeg,png,gif,bmp',
            mimeTypes: 'image/*'
        },
        thumb: {
            width: 1000,
            height: 400,
            quality: 80, // 缩略图质量,仅对jpg/jpeg格式有效
            allowMagnify: true, // 是否允许被放大(设为false时缩略图不会失真)
            crop: false, // 是否允许裁剪
            type: 'image/jpeg' // 强制转为jpeg格式
        },
        auto: true, // 开启自动上传
        chunked: true, // 开启分片上传
        chunkSize: 1048576, // 单片1M
        chunkRetry: 10,  // 某分片上传失败, 重试10次
        threads: 5, // 开启5个上传线程
        formData: options.formData, // 上传时附带的表单数据, 预留, 留空
        method: 'POST', // 上传使用的方式
        fileNumLimit: 1, // 单次批量最多几张图片
        fileSingleSizeLimit: 5242880, // 单个文件限制5M大小
        fileSizeLimit: 52428800, // 文件总大小限制50M大小
        duplicate: false // 允许重复文件
    });
    cover_max_total_fileSize = WebUploader.Base.formatSize(cover_uploader.options.fileSizeLimit, 0, ['B', 'KB', 'MB']);
    cover_max_single_fileSize = WebUploader.Base.formatSize(cover_uploader.options.fileSingleSizeLimit, 0, ['B', 'KB', 'MB'])
    cover_uploader.on('fileQueued', function (file) {
        cover_uploader.makeThumb(file, function (error, src){
            if (!error) {
                //loadCoverImg(src);
            }
        });
    });
    cover_uploader.on('uploadStart', function(file) {
        $('#coverUpload').hide();
        $('.shangchuan').show();
    });
    cover_uploader.on('uploadProgress', function(file, percentage) {
        percentage = (percentage * 100).toFixed(1);
        if (percentage <= 90) {
            $('#cover_progress').css('width', percentage + '%');
        }
    });
    cover_uploader.on('uploadSuccess', function (file, response) {
        var saveResult = options.singleSuccessHandler(response, file);
        if (saveResult) {
            $('#cover_up_info').html('').html('上传成功!');
            $('#cover_progress').css('width', '100%');
            setTimeout(function() {
                $('#coverUpload').show();
                $('.shangchuan').hide();
                $('#cover_up_info').html('').html('正在上传封面');
                $('#cover_progress').css('width', '0%');
            }, 2000);
        }
    });
    cover_uploader.on('uploadError', function (file, errCode) {
        $('#cover_up_info').html('').html('上传失败,请重新上传!');
        $('#cover_progress').css('width', '100%');
        $('#cover_progress').css('background', '#f55');
        setTimeout(function() {
            $('#coverUpload').show();
            $('.shangchuan').hide();
            $('#cover_up_info').html('').html('正在上传封面');
            $('#cover_progress').css('width', '0%');
            $('#cover_progress').css('background', '#34bf82');
        }, 2000);
    });
    cover_uploader.on('uploadFinished', function () {
        var allSaveResult = options.allSuccessHandler();
        if (allSaveResult) {
        }
        cover_uploader.reset();
    });
    cover_uploader.on('error', function (code) {
        var info = '';
        switch (code) {
            case 'Q_EXCEED_NUM_LIMIT' :
                info = '文件数量超过限制,本次最多' + cover_uploader.options.fileNumLimit + '张';
                break;
            case 'Q_EXCEED_SIZE_LIMIT' :
                info = '文件总大小不能超过' + cover_max_total_fileSize;
                break;
            case 'F_EXCEED_SIZE' :
                info = '单个文件大小不能超过' + cover_max_single_fileSize;
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
        promptWarn(info);
    });
}
function initCoverUploader(options) {
    if (isNull(options)) {
        window.console.log('初始化上传器时, 配置错误!请传入基本参数!')
        return;
    }
    if (isNull(options.url)) {
        window.console.log('上传url地址不能为空!')
        return;
    }
    if (isNull(options.picker)) {
        window.console.log('上传按钮容器不能为空!')
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
    if (cover_uploader == null) {
        createCoverUploader(options);
    }
}