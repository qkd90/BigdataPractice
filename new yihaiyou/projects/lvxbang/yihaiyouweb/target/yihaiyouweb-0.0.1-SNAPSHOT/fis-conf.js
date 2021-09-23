// 忽略文件
fis.config.merge({
    project : { exclude : /^\/((data|logs|libs)\/|yihaiyou\.html)/i }
});
//fis.config.set('project.exclude', /^\/data\//i);
//自动去除console.log等调试信息
fis.config.set('settings.optimizer.uglify-js', {
    compress : {
        drop_console: true
    }
});
// 不压缩变量名，由于某些js框架不能修改变量名，如：angular
fis.config.set('settings.optimizer.uglify-js', {
    mangle: false
});
// 资源合并开启fis-postpackager-simple插件，必须先装插件$ npm install -g fis-postpackager-simple
fis.config.set('modules.postpackager', 'simple');
fis.config.set('pack', {
    'yihaiyou/js/yihaiyou_all.js': [
        'yihaiyou/js/**.js'
    ],
     'yihaiyou/css/yihaiyou_all.css': [
     'yihaiyou/css/**.css'
     ]
});