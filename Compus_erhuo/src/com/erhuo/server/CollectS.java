package com.erhuo.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CollectS implements Serializable{

	private String userid;
	private int goodsid;
	private String collecttime;
	private String goodsname;
	private String goodsprice;
	private String goodsphoto1;
	private String address;
	
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(String goodsprice) {
		this.goodsprice = goodsprice;
	}
	public String getGoodsphoto1() {
		return goodsphoto1;
	}
	public void setGoodsphoto1(String goodsphoto1) {
		this.goodsphoto1 = goodsphoto1;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public String getCollecttime() {
		return collecttime;
	}
	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}