package com.easymother.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.alidao.mama.R;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;

public class CompressImage {
	
	public static Bitmap createBitmapThumbnail(Bitmap bitMap) {  
	    int width = bitMap.getWidth();  
	    int height = bitMap.getHeight();  
	    // 设置想要的大小  
	    int newWidth = 99;  
	    int newHeight = 99;  
	    // 计算缩放比例  
	    float scaleWidth = ((float) newWidth) / width;  
	    float scaleHeight = ((float) newHeight) / height;  
	    // 取得想要缩放的matrix参数  
	    Matrix matrix = new Matrix();  
	    matrix.postScale(scaleWidth, scaleHeight);  
	    // 得到新的图片  
	    Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,  
	            matrix, true);  
	    return newBitMap;  
	} 
	/**
	 * 
	 * @param c
	 * @param filepath
	 * @return
	 */
	public static synchronized Bitmap getCompressionBitMap(Context c, String filePath)
	{
		Bitmap bitmap = null;

		File file = new File(filePath);

		if (file.exists())
		{
			int maxImageByte = (100 * 1024);// 最大图片是100kb
			int sampleSize = 0;
			if (file.length() > maxImageByte)
				sampleSize = (int) (file.length() / maxImageByte);

			BitmapFactory.Options options = new BitmapFactory.Options();
			if (sampleSize > 1)
				options.inSampleSize = sampleSize;

			try
			{
				bitmap = BitmapFactory.decodeFile(filePath, options);
			}
			catch (Exception e)
			{
				return bitmap;
			}

		}
		else
		{
			bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.photo);
		}
		return bitmap;
	}
	
	/**
	 * 得到真实的路径
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath(final Context context, final Uri uri)
	{
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		}
		else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			Cursor cursor = context.getContentResolver().query(uri, new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor)
			{
				if (cursor.moveToFirst())
				{
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	
	
	//计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);

	        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);

	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    Bitmap bitmap=BitmapFactory.decodeFile(filePath, options);
	    
//	    ByteArrayOutputStream stream=new ByteArrayOutputStream();
//	    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
//	    byte[] b=stream.toByteArray();
//	    Bitmap bitmap1=BitmapFactory.decodeByteArray(b, 0, b.length);
	    return bitmap;
	    }

}
