// 忽略文件
//fis.config.merge({
//    project : { exclude : '/js/My97DatePicker/skin/WdatePicker.css' }
//});
//自动去除console.log等调试信息
fis.config.set('settings.optimizer.uglify-js', {
    compress : {
        drop_console: true
    }
});