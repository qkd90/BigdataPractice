/*!
 * 文件上传工具方法
 * bootstrap-fileinput v4.3.6
 * http://plugins.krajee.com/file-input
 */
var FileinputUtil = {
    QINIU_DOMAIN : QINIU_BUCKET_URL,
    // 产品类型
    PRO_TYPE : {'hotel':'hotel','scenic':'scenic','cruiseship':'cruiseship','sailboat':'sailboat'},

    /*
     * 获取图片数据，上传组件中所有存在和已上传的图片数据
     * fileinputName 组件标识，例如：'#input-id'
     * 返回数据格式：[{id: int, proType: string, childFolder: string, path: string, coverFlag: boolean}]
     * id为空表示新增数据
     */
    getImgData : function(fileinputName) {
        var previewConfig = $(fileinputName).fileinput('getPreviewConfig');
        var imgData = [];
        if (previewConfig && previewConfig.length > 0) {
            for (var i = 0; i < previewConfig.length; i++) {
                if (!previewConfig[i]) {    // 删除后，存在null的值
                    continue;
                }
                if (previewConfig[i].extra) {
                    imgData.push(previewConfig[i].extra);
                }
            }
        }
        return imgData;
    },

    /*
     * 获取图片数据，区分添加、更新、删除数据，不包含未修改数据
     * fileinputName 组件标识，例如：'#input-id'
     * initImgData 初始图片数据数组，数据格式：[{id: int, proType: string（必填）, childFolder: string, path: string, coverFlag: boolean}]
     * 返回数据格式：{addData:[], updateData:[], deleteData:[]}
     */
    getImgDataOperation : function(fileinputName, initImgData) {
        var resultData = {addData:[], updateData:[], deleteData:[]};
        var imgData = FileinputUtil.getImgData(fileinputName);
        if (initImgData) {
            for (var i = 0; i < imgData.length; i++) {
                if (imgData[i].id) {
                    var isExisted = false;
                    for (var j = 0; j < initImgData.length; j++) {
                        if (imgData[i].id == initImgData[j].id) {
                            if (imgData[i].coverFlag != initImgData[j].coverFlag && imgData[i].path != initImgData[j].path) {   // 更新
                                resultData.updateData.push(imgData[i]);
                            }
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        resultData.deleteData.push(imgData[i]);
                    }
                } else {    // 新增
                    resultData.addData.push(imgData[i]);
                }
            }
        } else {
            resultData.addData = imgData;
        }
        return resultData;
    },

    /*
     * 默认配置项
     * proType 产品类型，参考productimage.proType枚举
     * childFolder 子目录名称，可为空
     * initImgData 初始图片数据数组，数据格式：[{id: int, proType: string（必填）, childFolder: string, path: string, coverFlag: boolean}]
     */
	defaultImgCfg : function(proType, childFolder, initImgData) {
        if (!childFolder) {
            childFolder = '';
        }
        var initialPreview = [];
        var initialPreviewConfig = [];
		if (initImgData && initImgData.length > 0) {
            for (var i = 0; i < initImgData.length; i++) {
                initialPreview.push(FileinputUtil.QINIU_DOMAIN + initImgData[i].path);
                var previewCfg = {key: initImgData[i].path, homeflag: initImgData[i].coverFlag, extra:
                    {id: initImgData[i].id, proType: initImgData[i].proType, childFolder: initImgData[i].childFolder, path: initImgData[i].path, coverFlag: initImgData[i].coverFlag, showOrder: initImgData[i].showOrder}};
                initialPreviewConfig.push(previewCfg);
            }
		}

		return {
			language: 'zh',
			uploadUrl: "/sys/fileInputUpload/uploadImg.jhtml",
            uploadExtraData: {proType: proType, childFolder: childFolder},   // a value for uploadUrl
			deleteUrl: "/sys/fileInputUpload/delFile.jhtml",
			initialPreview: initialPreview,
			initialPreviewAsData: true,
			initialPreviewConfig: initialPreviewConfig,
			overwriteInitial: false,
			uploadAsync: true,
			allowedFileExtensions: ["jpg", "png", "gif"],
			maxFileSize: 2000,	// KB
			maxFileCount: 20,	// the maximum number of files allowed for each multiple upload
			validateInitialCount: true,	// whether to include initial preview file count (server uploaded files) in validating
			maxImageWidth: 2048,	// px
			maxImageHeight: 2048,	// px
			//initialCaption: "The Moon and the Earth",
			showCaption: false,
			showClose: false,
			browseClass: "btn btn-success",
			browseIcon: "<i class=\"glyphicon glyphicon-picture\"></i> ",
			removeClass: "btn btn-danger",
			removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
			uploadClass: "btn btn-info",
			uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> ",
			previewSettings : {
				image: {width: "160px", height: "140px"}
			},
			layoutTemplates: {
				main2: '{preview}\n<div class="kv-upload-progress hide"></div>\n<div class="input-group-btn">\n{remove}\n{cancel}\n{upload}\n{browse}\n</div>\n',
				footer: '<div class="file-thumbnail-footer">\n' +
					//'    <div class="file-caption-name" style="width:{width}">{caption}</div>\n' +
					'    {progress} {actions}\n' +
					'</div>',
				actions: '<div class="file-actions">\n' +
					'    <div class="file-footer-buttons">\n' +
					'        {upload} {delete} {zoom} {other}' +
					'    </div>\n' +
					'    {CUSTOM_TAG_HOME}\n' +
					//'    <div class="file-upload-indicator" title="{indicatorTitle}">{indicator}</div>\n' +
					'    <div class="clearfix"></div>\n' +
					'</div>'
			},
			customLayoutTags: {
				'{CUSTOM_TAG_HOME}': '<span class="{homeClass}" title="封面" {dataKey}></span>'
			}
		};
	},

    /*
     * 只读配置项，控件设置只读属性：<input id="input-25" name="input25[]" type="file" readonly class="file-loading">
     * initImgData 初始图片数据数组，数据格式：[{id: int, proType: string（必填）, childFolder: string, path: string, coverFlag: boolean}]
     */
    readonlyImgCfg : function(initImgData) {
        var initialPreview = [];
        var initialPreviewConfig = [];
        if (initImgData && initImgData.length > 0) {
            for (var i = 0; i < initImgData.length; i++) {
                initialPreview.push(FileinputUtil.QINIU_DOMAIN + initImgData[i].path);
                var previewCfg = {key: initImgData[i].path, homeflag: initImgData[i].coverFlag, extra:
                {id: initImgData[i].id, proType: initImgData[i].proType, childFolder: initImgData[i].childFolder, path: initImgData[i].path, coverFlag: initImgData[i].coverFlag, showOrder: initImgData[i].showOrder}};
                initialPreviewConfig.push(previewCfg);
            }
        }

        return {
            language: 'zh',
            initialPreview: initialPreview,
            initialPreviewAsData: true,
            initialPreviewConfig: initialPreviewConfig,
            overwriteInitial: false,
            uploadAsync: true,
            allowedFileExtensions: ["jpg", "png", "gif"],
            maxFileSize: 2000,	// KB
            maxFileCount: 20,	// the maximum number of files allowed for each multiple upload
            validateInitialCount: true,	// whether to include initial preview file count (server uploaded files) in validating
            maxImageWidth: 2048,	// px
            maxImageHeight: 2048,	// px
            //initialCaption: "The Moon and the Earth",
            showCaption: false,
            showClose: false,
            showUpload: false,
            showRemove: false,
            showBrowse: false,
            previewSettings : {
                image: {width: "160px", height: "140px"}
            },
            layoutTemplates: {
                main2: '{preview}\n<div class="kv-upload-progress hide"></div>\n<div class="input-group-btn">\n{remove}\n{cancel}\n{upload}\n{browse}\n</div>\n',
                footer: '<div class="file-thumbnail-footer">\n' +
                    //'    <div class="file-caption-name" style="width:{width}">{caption}</div>\n' +
                '    {actions}\n' +
                '</div>',
                actions: '<div class="file-actions">\n' +
                '    <div class="file-footer-buttons">\n' +
                '        {zoom}' +
                '    </div>\n' +
                '    {CUSTOM_TAG_HOME}\n' +
                    //'    <div class="file-upload-indicator" title="{indicatorTitle}">{indicator}</div>\n' +
                '    <div class="clearfix"></div>\n' +
                '</div>'
            },
            customLayoutTags: {
                '{CUSTOM_TAG_HOME}': '<span class="{homeClass}" title="封面" {dataKey}></span>'
            }
        };
    }
};
 
 