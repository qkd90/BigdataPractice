/**
 * Created by vacuity on 15/10/19.
 */
var EditCategory = {
	initCombobox : function(type) {
		var requesturl = "/goods/goods/getCategoryRootData.jhtml?type=" + type;
		$('#edit_parentcategory').combobox({
			url: requesturl,
			valueField: 'id',
			textField: 'name',
			panelHeight: 'auto',
			onSelect: function(record) {
				if (record.id == 0) {
					$("#edit_picture").show();
				} else {
					$("#edit_picture").hide();
				}
			}
		});
	}
};
