/**
 * Created by vacuity on 15/10/15.
 */
var AddCategory = {
    initCombobox: function (type) {
        var requesturl = "/goods/goods/getCategoryRootData.jhtml?type=" + type;
        $('#add_parentcategory').combobox({
            url: requesturl,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            onSelect: function (record) {
                if (record.id == 0) {
                    $("#add-picture").show();
                } else {
                    $("#add-picture").hide();
                }
            },
            onLoadSuccess: function() {
                $('#add_parentcategory').combobox('select', '0');
            }
        });
    }
};




