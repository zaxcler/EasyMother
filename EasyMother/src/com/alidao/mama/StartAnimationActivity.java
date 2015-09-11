package com.alidao.mama;

import com.easymother.main.MainActivity;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class StartAnimationActivity extends Activity {
	private ImageView welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_activity);
		welcome=(ImageView) findViewById(R.id.welcome);
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.welcome);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				EasyMotherUtils.goActivity(StartAnimationActivity.this, MainActivity.class);
				StartAnimationActivity.this.finish();
			}
		});
		welcome.setAnimation(animation);
		welcome.startAnimation(animation);
		
		
	
	}
}
