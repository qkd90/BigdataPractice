package com.hmlyinfo.base.util.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件信息类
 *
 * @author qsRoy
 */
public class MailInfo {
	private String mailTo;
	private List<String> mailToList = new ArrayList<String>();
	private String mailSubject;
	private String mailContent;
	private List<String> mailAttachList = new ArrayList<String>();

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public List<String> getMailToList() {
		if (null == mailToList) {
			mailToList = new ArrayList<String>();
		}
		return mailToList;
	}

	public void setMailToList(List<String> mailToList) {
		this.mailToList = mailToList;
	}

	public void attachMailTo(String mailToAddr) {
		if (null == mailToList) {
			mailToList = new ArrayList<String>();
		}
		this.mailToList.add(mailToAddr);
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public List<String> getMailAttachList() {
		if (null == mailAttachList) {
			mailAttachList = new ArrayList<String>();
		}
		return mailAttachList;
	}

	public void setMailAttachList(List<String> mailAttachList) {
		this.mailAttachList = mailAttachList;
	}

	public void attachFile(String filePath) {
		if (null == mailAttachList) {
			mailAttachList = new ArrayList<String>();
		}
		this.mailAttachList.add(filePath);
	}

}
