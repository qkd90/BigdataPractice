var TicketPublishSuccess = {
	
		reToList:function(){
			var url = '/ticket/ticket/ticketList.jhtml';
			window.parent.location.href = url;
		},
		editTicket:function(){
			var tId = $("#ticketId").val();
//			var param = "?tId="+tId;
//			parent.window.showGuide(1, true,param);
			var url = "/ticket/ticket/addWizard.jhtml?ticketId="+tId;
			window.parent.location.href = url;
		},
		addNewTicket:function(){
			
			var url = "/ticket/ticket/addWizard.jhtml?ticketId=";
			window.parent.location.href = url;
			
//			parent.window.showGuide(1, true);
		}
};