package com.erhuo.server;

import java.io.Serializable;
import java.util.Hashtable;

@SuppressWarnings("serial")
public class MyMessage implements Serializable {
	
	public static final String LOGIN="LOGIN";
	public static final String LOGOUT="LOGOUT";
	public static final String REG="REG";
	public static final String FIND_USER="FIND_USER"; 
	public static final String DOWNLOAD="DOWNLOAD";
	public static final String USERINFO="USERINFO";
	public static final String CHANGENAME="CHANGENAME";
	public static final String CHANGEPASSWORD="CHANGEPASSWORD";
	public static final String CHANGESEX="CHANGESEX";
	public static final String CHANGEAGE="CHANGEAGE";
	public static final String ADDGOODS="ADDGOODS";
	public static final String ADDASKBUY="ADDASKBUY";
	public static final String DOWNQIUGOU="DOWNQIUGOU";
	public static final String MAINSHOW="MAINSHOW";
	public static final String GOODSSHOW="GOODSSHOW";
	public static final String MESSAGE="MESSAGE";
	public static final String SHOUCANG="SHOUCANG";
	public static final String SHOUCANGJ="SHOUCANGJ";
	public static final String MANAGEGOODS="MANAGEGOODS";
	public static final String MANAGEFORBUY="MANAGEFORBUY";
	public static final String DELETECOLLECT="DELETECOLLECT";
	public static final String GOODSINFOSHOW="GOODSINFOSHOW";
	public static final String PINGLUN_DOWN="PINGLUN_DOWN";
    public static final String PINGLUN="PINGLUN";
    public static final String USERQIUGOUINFO="USERQIUGOUINFO";
    public static final String QIUGOUDELETE="QIUGOUDELETE";
    public static final String USERGOODSINFO="USERGOODSINFO";
    public static final String USERGOODSDELETE="USERGOODSDELETE";
    public static final String CHANGEASKFORINFO="CHANGEASKFORINFO";
    public static final String CHANGEGOODSINFO="CHANGEGOODSINFO";
    public static final String CHANGEGOODS="CHANGEGOODS";
    public static final String CHANGEASKBUY="CHANGEASKBUY";
    public static final String CATAGORYSEARCH="CATAGORYSEARCH";
    public static final String SEARCH="SEARCH";
    public static final String LoadMoreMAINSHOW="LoadMoreMAINSHOW";
    public static final String MAIN_CATAGORYSEARCHREFRESH="MAIN_CATAGORYSEARCHREFRESH";
    public static final String MOREMAIN_CATAGORYSEARCH="MOREMAIN_CATAGORYSEARCH";
    public static final String MAIN_CATAGORYSEARCH="MAIN_CATAGORYSEARCH";
    public static final String REFRESHMAINSHOW="REFRESHMAINSHOW";
    public static final String REFRESHASKBUY="REFRESHASKBUY";
    public static final String MOREASKBUY="MOREASKBUY";
    public static final String LOADISHAVEQIUGOU="LOADISHAVEQIUGOU";
    public static final String DOWNNEWS="DOWNNEWS";
    public static final String REFRESHGOODS="REFRESHGOODS";
    public static final String CHANGESTATE="CHANGESTATE";
    public static final String STATENUM="STATENUM";
    public static final String MAIN_CATAGORYPOPULARITY="MAIN_CATAGORYPOPULARITY";
    public static final String POPULARITYREFRESH="POPULARITYREFRESH";
    public static final String POPULARITYMORE="POPULARITYMORE";
    public static final String CHANGEADDRESS="CHANGEADDRESS";
    public static final String CHANGEGRADE="CHANGEGRADE";
    public static final String FIRSTMESSAGE="FIRSTMESSAGE";
    public static final String SECONDMESSAGE="SECONDMESSAGE";
    public static final String REFRESHMESSAGE="REFRESHMESSAGE";
    public static final String DOWNCHATMESSAGE="DOWNCHATMESSAGE";
    public static final String CHATNAME="CHATNAME";  
    public static final String CHANGEQQ="CHANGEQQ";
    public static final String CHANGEEMAIL="CHANGEEMAIL";
    public static final String MESSAGEDELETE="MESSAGEDELETE";
    
	public Hashtable<String,Object> value = null;
	public Hashtable<String,Object> returnValue = null;

	public String type = "";

	public Hashtable<String,Object> getValue() {
		return value;
	}

	public void setValue(Hashtable<String,Object> value) {
		this.value = value;
	}

	public Hashtable<String,Object> getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Hashtable<String,Object> returnValue) {
		this.returnValue = returnValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
