package com.erhuo.app.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

import android.util.Log;

import com.erhuo.app.util.Tools;
import com.erhuo.server.MyMessage;

public class LoginClientTCP extends Thread {

	private static ObjectInputStream oin = null;
	private static ObjectOutputStream oout = null;

	private static void conn() throws Exception {

		Socket socket = new Socket(Tools.IP, Tools.PORT_1);
		oout = new ObjectOutputStream(socket.getOutputStream());
		oin = new ObjectInputStream(socket.getInputStream());

	}
	public static void sendMessage(final MyMessage msg) {
		new Thread() {
			public void run() {
				synchronized (LoginClientTCP.class) {
					try {
						oout.writeObject(msg);
						oout.flush();
						oin.readObject();
					} catch (Exception e) {
					}

				}
			};
		}.start();

	}
	
	public static void close() {
		final MyMessage msg=new MyMessage();
		msg.setType(msg.LOGOUT);
		new Thread() {
			public void run() {
				synchronized (LoginClientTCP.class) {
					try {
						oout.writeObject(msg);
						oout.flush();
						oin.readObject();
						oout.close();
						oin.close();
					} catch (Exception e) {
					}

				}
			};
		}.start();

	}

	public static String login(String userid, String password) throws Exception {
		conn();
		MyMessage msg = new MyMessage();
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("userid", userid);
		table.put("password", password);
		msg.setType(MyMessage.LOGIN);
		msg.setValue(table);
		oout.writeObject(msg);
		oout.flush();
		MyMessage m2 = (MyMessage) oin.readObject();
		Tools.userid = userid;
		String message = (m2.getReturnValue().get("message").toString());
		if (!message.equalsIgnoreCase("ok")) {
			try {
				oout.close();
				oin.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			oout.close();
			oin.close();
			// 开启接收服务器
//			new AcionClientTCP().start();
			/* AcionClientTCP mr = new AcionClientTCP(userid);
	           Thread t = new Thread(mr);

	           t.start();
*/
		}
		return message;
	}

}
