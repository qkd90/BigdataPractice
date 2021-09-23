/** kindeditor扩展 */
// 自定义插件 
KindEditor.lang({
	imgsView : '图库浏览'
});
KindEditor.plugin('imgsView', function(K) {
	var self = this, name = 'imgsView';
	self.clickToolbar(name, function() {
		var editor = K.editor({
			fileManagerJson : self.fileManagerJson
		});
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					self.insertHtml('<img src="'+url+'">');
					editor.hideDialog();
				}
			});
		});
	});
});