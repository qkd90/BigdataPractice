package com.zuipin.util;

import java.io.Serializable;

public class SMTPProxy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7647834654343037380L;
	protected String name = null;
	protected String externalIp = null;
	protected String innerIp = null;
	protected int smtpPort = 25;
	protected int pop3Port = 110;
	protected String user = null;
	protected String pass = null;
	protected long interval = 50;
	protected boolean needAuth = true;
	protected String fromName = null;
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getReplyAddress() {
		return replyAddress;
	}

	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}

	protected String fromAddress = null;
	protected String replyName = null;
	protected String replyAddress = null;

	protected static long lastInvokerTime = 0;

	public void checkInterval() {
		long now = System.currentTimeMillis();
		long l = now - lastInvokerTime;
		if (l < interval) {
			try {
				Thread.sleep(interval - l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lastInvokerTime = now;
	}

	@Override
	public String toString() {
		return name + "," + innerIp + "," + externalIp + "," + smtpPort + "," + pop3Port + "," + user + "," + pass;
	}

	public String getExternalIp() {
		return externalIp;
	}
	public void setExternalIp(String externalIp) {
		this.externalIp = externalIp;
	}
	public String getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}

}