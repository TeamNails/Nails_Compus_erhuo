package com.erhuo.app.util;

import java.util.ArrayList;

import com.erhuo.message.ChatMain;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;

public class Tools {

	public static String IP = "192.168.1.110";
	public static int PORT_1 = 8211;
	public static int PORT_2 = 8212;
	public static int PORT_3 = 8213;
	public static int PORT_4 = 8214;
	public static String name = "";
	public static String userid = "";
	public static String password = "";
	public static Context context=null;
	public static NotificationManager manager=null;
	public static Notification notification=null;
	public static PendingIntent pendingIntent=null;
	public static int num=0;
	public static int num2=0;
	public static int isinchat=0;
	public static ArrayList Listtime=null;
	public static ChatMain chatmain=null;
	public static String userid2 = "";
	public static Handler handler = null;
}
