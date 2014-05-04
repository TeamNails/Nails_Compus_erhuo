package com.erhuo.server;
import java.io.Serializable;

@SuppressWarnings("serial")
public class AskBuyS implements Serializable{
	public String qq;
	public String email;
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAgoodsname() {
		return agoodsname;
	}
	public void setAgoodsname(String agoodsname) {
		this.agoodsname = agoodsname;
	}
	public String getAgoodsclass() {
		return agoodsclass;
	}
	public void setAgoodsclass(String agoodsclass) {
		this.agoodsclass = agoodsclass;
	}
	public String getAskbuycontent() {
		return askbuycontent;
	}
	public void setAskbuycontent(String askbuycontent) {
		this.askbuycontent = askbuycontent;
	}
	public String getAskbuytime() {
		return askbuytime;
	}
	public void setAskbuytime(String askbuytime) {
		this.askbuytime = askbuytime;
	}
	public String getAskbuyphone() {
		return askbuyphone;
	}
	public void setAskbuyphone(String askbuyphone) {
		this.askbuyphone = askbuyphone;
	}
	public int getAskbuyid() {
		return askbuyid;
	}
	public void setAskbuyid(int askbuyid) {
		this.askbuyid = askbuyid;
	}
	public String getUserqiuname() {
		return userqiuname;
	}
	public void setUserqiuname(String userqiuname) {
		this.userqiuname = userqiuname;
	}
	private int askbuyid;
	private String userid;
	private String agoodsname;
	private String agoodsclass;
	private String askbuycontent;
	private String askbuytime;
	private String askbuyphone;
	private String userqiuname;
	
	
}