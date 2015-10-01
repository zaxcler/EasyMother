package com.easymother.main;

import com.alidao.mama.R;
import com.easymother.customview.MyLoadingProgress;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	
	private WebView webview;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_activity);
		EasyMotherUtils.initTitle(this, "", false);
		intent=getIntent();
		String url=intent.getStringExtra("url");
		webview=(WebView) findViewById(R.id.webview);
		WebSettings settings=webview.getSettings();
		settings.setJavaScriptEnabled(true);
		MyLoadingProgress.showLoadingDialog(this);
		webview.setWebViewClient(new WebViewClient(){
			/*
			 * 重写该方法使加载网页在webview中，不调用浏览器
			 * (non-Javadoc)
			 * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				MyLoadingProgress.closeLoadingDialog();
				super.onPageFinished(view, url);
			}
		});
		webview.loadUrl(url);
	}
	

}
