/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.demobyimage;

import java.util.List;

import com.alidao.mama.R;
import com.example.imageadapter.BasePagerAdapter.OnItemChangeListener;
import com.example.imageadapter.BasePagerAdapter.OnItemClickListener;
import com.example.imageadapter.GalleryViewPager;
import com.example.imageadapter.UrlPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryUrlActivity extends Activity {

    private GalleryViewPager mViewPager;
    private List<String> mphotoList;
    private TextView titlebar_title;
//    private String[] urls;
    private  List<String> list_imgs;
    private int my_position=0;
	private int lastIndex;
    @SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        final String[] urls = {
//                "http://cdn.duitang.com/uploads/item/201408/23/20140823055947_8fX48.thumb.700_0.png",
//                "http://img5.duitang.com/uploads/item/201404/28/20140428171600_Hjt2S.thumb.700_0.jpeg",
//                "http://img4q.duitang.com/uploads/item/201404/28/20140428171704_8C2LZ.jpeg",
//                "http://cdnq.duitang.com/uploads/item/201505/11/20150511020320_QEVFX.jpeg",
//        };
        Intent intent=getIntent();
//        urls=intent.getStringExtra("imgs");
        titlebar_title=(TextView) findViewById(R.id.titlebar_title);
        list_imgs = (List<String>) intent.getSerializableExtra("list_img");
        my_position=intent.getIntExtra("tag", my_position);
//        List<String> items = new ArrayList<String>();
////        String tag=
//        Collections.addAll(items, urls);

        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(GalleryUrlActivity.this, list_imgs);
        pagerAdapter.setOnItemChangeListener(new OnItemChangeListener()
		{
			@Override
			public void onItemChange(int currentPosition)
			{
				titlebar_title.setText((currentPosition + 1) + " / " + list_imgs.size());
				Toast.makeText(GalleryUrlActivity.this, "Current item is " + currentPosition, Toast.LENGTH_SHORT).show();
			}
			
		});
        pagerAdapter.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick() {
				// TODO Auto-generated method stub
				System.out.println("点击了返回了");
//				finish();
			}
		});
        titlebar_title.setText((my_position + 1) + " / " + list_imgs.size());
        mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        System.out.println(my_position+"-----my_position");
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(my_position, true);
        
    }
	
}