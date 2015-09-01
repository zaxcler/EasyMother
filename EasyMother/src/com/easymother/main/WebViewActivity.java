package com.easymother.main;

import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	
	private WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		EasyMotherUtils.initTitle(this, "", false);
		webview=(WebView) findViewById(R.id.webview);
		webview.loadUrl("https://www.baidu.com");
	}
	

}
