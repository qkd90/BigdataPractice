rem load image
move /Y images\yhy\loading_*.gif images\yhy\loading.gif
rem my97
move /Y js\My97DatePicker\skin\WdatePicker_*.css js\My97DatePicker\skin\WdatePicker.css
rem kindeditor
move /Y js\kindeditor\themes\qq\qq_*.css js\kindeditor\themes\qq\qq.css
move /Y js\kindeditor\themes\simple\simple_*.css js\kindeditor\themes\simple\simple.css
move /Y js\kindeditor\themes\default\default_*.css js\kindeditor\themes\default\default.css
move /Y js\kindeditor\lang\zh_CN_*.js js\kindeditor\lang\zh_CN.js
rem kindeditor plugins use java copy without version
rem move /Y js\kindeditor\plugins\code\prettify_*.css js\kindeditor\plugins\code\prettify.css
rem move /Y js\kindeditor\plugins\link\link_*.js js\kindeditor\plugins\link\link.js
copy /Y lib\kindeditor-4.1.11-zh-CN\themes\default\default_*.css lib\kindeditor-4.1.11-zh-CN\themes\default\default.css
rem xheditor
move /Y js\xheditor-1.2.2\xheditor_skin\default\ui_*.css js\xheditor-1.2.2\xheditor_skin\default\ui.css
move /Y js\xheditor-1.2.2\xheditor_skin\default\iframe_*.css js\xheditor-1.2.2\xheditor_skin\default\iframe.css

rem config files
copy /Y ..\..\bg-conf\*  WEB-INF\classes
exit