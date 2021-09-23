function _navSelect()
{
	if (getEvtTgt().parents(".select-block:first").hasClass("chosen"))
	{
		_closenavSelect();
	}
	else
	{
		_closenavSelect();
		getEvtTgt().parents(".select-block:first").addClass("chosen");
		var index = getEvtTgt().parents(".select-block:first").index();
		$(".select-wrap").find(".list-content").eq(index).show();
	}
}

function _closenavSelect()
{
	$(".select-block").removeClass("chosen");
	$(".list-content").hide();
}