package com.erhuo.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pingluns  implements Serializable {
	private String username;
	private String leavewordtime;
	private String  leavewordc;
	public String getLeavewordtime() {
		return leavewordtime;
	}
	public void setLeavewordtime(String leavewordtime) {
		this.leavewordtime = leavewordtime;
	}
	public String getLeavewordc() {
		return leavewordc;
	}
	public void setLeavewordc(String leavewordc) {
		this.leavewordc = leavewordc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
