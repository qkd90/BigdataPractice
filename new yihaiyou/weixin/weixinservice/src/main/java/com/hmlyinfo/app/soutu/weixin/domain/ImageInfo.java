package com.hmlyinfo.app.soutu.weixin.domain;

public class ImageInfo {
	
	private String srcName;
	private String tgtName;
	private byte[] file;
	private String virtualPath;
	private String shareTime;
	
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	public String getTgtName() {
		return tgtName;
	}
	public void setTgtName(String tgtName) {
		this.tgtName = tgtName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getVirtualPath() {
		return virtualPath;
	}
	public void setVirtualPath(String virtualPath) {
		this.virtualPath = virtualPath;
	}

	public String getShareTime() {
		return shareTime;
	}

	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}


}
