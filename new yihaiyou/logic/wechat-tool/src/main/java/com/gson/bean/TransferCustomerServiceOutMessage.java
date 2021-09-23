package com.gson.bean;

public class TransferCustomerServiceOutMessage extends OutMessage {

	private String	MsgType	= "transfer_customer_service";
	private TransInfo TransInfo;
    // 事件
    private String Event;
    private String EventKey;

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

    public TransInfo getTransInfo() {
        return TransInfo;
    }

    public void setTransInfo(TransInfo transInfo) {
        TransInfo = transInfo;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
