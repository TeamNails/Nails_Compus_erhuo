package com.erhuo.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NewsS implements Serializable{
 private String userid;
 private String userid2;
 private String news;
 private String newstime;
 private int goodsid;
 private int state;
public String getNews() {
	return news;
}
/**
 * @return the userid
 */
public String getUserid() {
	return userid;
}
/**
 * @param userid the userid to set
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * @return the newstime
 */
public String getNewstime() {
	return newstime;
}
/**
 * @param newstime the newstime to set
 */
public void setNewstime(String newstime) {
	this.newstime = newstime;
}


public void setNews(String news) {
	this.news = news;
}
public int getGoodsid() {
	return goodsid;
}
public void setGoodsid(int goodsid) {
	this.goodsid = goodsid;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getUserid2() {
	return userid2;
}
public void setUserid2(String userid2) {
	this.userid2 = userid2;
}
 
}
