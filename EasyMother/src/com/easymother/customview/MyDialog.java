package com.easymother.customview;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class MyDialog extends Dialog {
	private int themem;
	private int resource;

	public MyDialog(Context context, int theme) {
		super(context, theme);
	}

	public MyDialog(Context context, int theme,int resource) {
		super(context, theme);
		this.resource=resource;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(resource);
	}
}
