package com.erhuo.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatS implements Serializable{
	
	private String id;
	private String userid1;
	private String userid2;
	private String time;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the userid1
	 */
	public String getUserid1() {
		return userid1;
	}
	/**
	 * @param userid1 the userid1 to set
	 */
	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}
	/**
	 * @return the userid2
	 */
	public String getUserid2() {
		return userid2;
	}
	/**
	 * @param userid2 the userid2 to set
	 */
	public void setUserid2(String userid2) {
		this.userid2 = userid2;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	private String content;

}
