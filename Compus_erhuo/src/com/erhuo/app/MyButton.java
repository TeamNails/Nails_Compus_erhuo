package com.erhuo.app;

import android.content.Context;
import android.widget.Button;

public class MyButton extends Button {

	public MyButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
