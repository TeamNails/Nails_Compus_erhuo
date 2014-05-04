package com.erhuo.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SellS implements Serializable{
	private int goodsid;
	private String goodsname;
	private String goodsclass;
	private String goodsprice;
	private String goodsinfo;
	private String selltime;
	private String goodsphone;
	private String userid;
	private String usersellname;
	private String gkey;
	private String goodsphoto1;
	private String goodsphoto2;
	private String goodsphoto3;
	private String address;
	private String qq;
	private String email;
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	private String grade;
	private String sex;
	private String username;
	
	public String getGoodsphoto1() {
		return goodsphoto1;
	}
	public void setGoodsphoto1(String goodsphoto1) {
		this.goodsphoto1 = goodsphoto1;
	}
	public String getGoodsphoto2() {
		return goodsphoto2;
	}
	public void setGoodsphoto2(String goodsphoto2) {
		this.goodsphoto2 = goodsphoto2;
	}
	public String getGoodsphoto3() {
		return goodsphoto3;
	}
	public void setGoodsphoto3(String goodsphoto3) {
		this.goodsphoto3 = goodsphoto3;
	}

	public String getGkey() {
		return gkey;
	}
	public void setGkey(String gkey) {
		this.gkey = gkey;
	}
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getGoodsclass() {
		return goodsclass;
	}
	public void setGoodsclass(String goodsclass) {
		this.goodsclass = goodsclass;
	}
	public String getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(String goodsprice) {
		this.goodsprice = goodsprice;
	}
	public String getGoodsinfo() {
		return goodsinfo;
	}
	public void setGoodsinfo(String goodsinfo) {
		this.goodsinfo = goodsinfo;
	}
	public String getGoodsphone() {
		return goodsphone;
	}
	public void setGoodsphone(String goodsphone) {
		this.goodsphone = goodsphone;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsersellname() {
		return usersellname;
	}
	public void setUsersellname(String usersellname) {
		this.usersellname = usersellname;
	}
	public String getSelltime() {
		return selltime;
	}
	public void setSelltime(String selltime) {
		this.selltime = selltime;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}