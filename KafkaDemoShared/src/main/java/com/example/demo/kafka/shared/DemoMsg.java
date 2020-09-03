package com.example.demo.kafka.shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DemoMsg implements Serializable {

	// Default Serial Version UID
	private static final long serialVersionUID = 1L;

	private Date published;

	private Integer code;

	private String msg;

	public DemoMsg(Integer code, String msg) {
		this.published = new Date();
		this.code = code;
		this.msg = msg;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DemoMsg))
			return false;
		
		DemoMsg dmsg = (DemoMsg) obj;
		
		return published.equals(dmsg.getPublished()) &&
				code.equals(dmsg.getCode()) &&
				msg.equals(dmsg.getMsg());
	}
	
	@Override
	public String toString() {
		return LocalDateTime.ofInstant(published.toInstant(), ZoneId.systemDefault()).toString() + ":" + code + ":"
				+ msg;
	}
}
