package com.Library.ToolsClass;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

public class FileUtils
{
	public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CommunitySteward" + File.separator;

	public static void saveBitmap(Bitmap bm, String picName)
	{
		// Log.e("", "保存图片");
		try
		{
			if (!isFileExist(""))
			{
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists())
			{
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			// Log.e("", "已经保存");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException
	{
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName)
	{
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName)
	{
		File file = new File(SDPATH + fileName);
		if (file.isFile())
		{
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir()
	{
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles())
		{
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path)
	{
		try
		{
			File f = new File(path);
			if (!f.exists())
			{
				return false;
			}
		}
		catch (Exception e)
		{

			return false;
		}
		return true;
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath)
	{
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName)
	{
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 在SDCard上创建文件
	 * */
	public File CreateFileInSDCard(String fileName, String dir) throws Exception
	{

		// 如果文件存在，先删除
		if (IsFileExist(SDPATH + dir + File.separator) == true)
		{
			DeleteFile(fileName, SDPATH + dir + File.separator);
		}
		// 创建文件
		File file = new File(SDPATH + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SDCard上创建目录
	 * */
	public File CreateSDDir(String dir)
	{
		File file = new File(SDPATH + dir + File.separator);

		return file;
	}

	/**
	 * 判断SDCard上的文件是否存在
	 * */
	public static boolean IsFileExist(String path)
	{
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 创建文件
	 */
	public static boolean CreateFile(String path)
	{
		try
		{
			File dir = new File(path);
			if (dir.exists() == true)
				dir.delete();

			dir.getParentFile().mkdirs();
			return true;

		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @param path
	 */
	public void DeleteFile(String fileName, String path)
	{

		File file = new File(SDPATH + path + File.separator + fileName);
		file.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @param path
	 */
	public static void DeleteFileByPath(String path)
	{
		File file = new File(path);
		if (file.exists() == true)
			file.delete();
	}

	/**
	 * 删除文件夹下的所有文件
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public static void DeleteDirectory(String path)
	{
		File root = new File(path);
		File[] currentFiles = root.listFiles();
		if (currentFiles != null)
		{
			for (int n = 0; n < currentFiles.length; n++)
			{
				File file = currentFiles[n];
				if (file.exists())
					file.delete();
			}
		}
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param path
	 *            要删除的根目录
	 */
	public static void RecursionDeleteFile(String path)
	{
		File file = new File(path);
		if (file.isFile())
		{
			file.delete();
			return;
		}
		if (file.isDirectory())
		{
			File[] childFile = file.listFiles();
			if (childFile == null)
			{
				file.delete();
				return;
			}
			for (File f : childFile)
			{
				RecursionDeleteFile(f.getPath());
			}
			file.delete();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public void DeleteFile(String filePath)
	{
		File file = new File(filePath);
		if (file.exists())
			file.delete();
	}

	/**
	 * 将一个InputStream写入SDCard
	 * */
	public File WriteToSDCardFromInput(String path, String fileName, InputStream inputStream)
	{
		File file = null;
		OutputStream outputStream = null;

		try
		{
			CreateSDDir(path);
			file = CreateFileInSDCard(fileName, path);

			outputStream = new FileOutputStream(file);
			byte data[] = new byte[4 * 1024];
			int tmp;
			while ((tmp = inputStream.read(data)) != -1)
			{
				outputStream.write(data, 0, tmp);
			}
			outputStream.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				outputStream.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * @param file
	 * @return
	 */
	static public byte[] GetBytesFromFile(File file)
	{
		if (file == null)
		{
			return null;
		}
		try
		{

			FileInputStream inStream = new FileInputStream(file);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;

			while ((len = inStream.read(buffer)) != -1)
			{
				// 写入数据
				outStream.write(buffer, 0, len);
			}
			// 得到文件的二进制数据

			inStream.close();
			outStream.close();
			return outStream.toByteArray();
		}
		catch (IOException e)
		{

		}
		return null;
	}

	/**
	 * 复制文件
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static boolean CopyFile(String oldPath, String newPath)
	{
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists())
			{
				// 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];

				while ((byteread = inStream.read(buffer)) != -1)
				{
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();

				return true;
			}
		}
		catch (Exception e)
		{
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

		return false;
	}

	/**
	 * 将文本写入文件
	 * 
	 * @param path
	 * @param strContext
	 * @return
	 */
	public static boolean WriteTextToFile(String path, String strContext)
	{
		FileOutputStream fis = null;
		try
		{
			File file = new File(path);

			if (file.exists() == false)
				file.getParentFile().mkdirs();

			fis = new FileOutputStream(path);
			byte[] bytes = strContext.getBytes();
			fis.write(bytes, 0, bytes.length);
		}
		catch (IOException e)
		{
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			try
			{
				fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e2)
			{
			}
		}

		return true;
	}

	/**
	 * 从文件中获取文本
	 * 
	 * @param path
	 * @return
	 */
	public static String ReadTextFromFile(String path)
	{
		File file = new File(path);

		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		Reader in = null;
		try
		{

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);// 文件编码Unicode,UTF-8,ASCII,GB2312,Big5
			in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1)
			{
				buffer.append((char) ch);
			}
			fis.close();
			isr.close();
			in.close();

			return buffer.toString();

		}
		catch (IOException e)
		{
			return "";
		}
	}

	/**
	 * 若strPath目录不存在，则创建
	 * 
	 * @param strPath
	 */
	public static boolean CreateDirectory(String dirPath)
	{
		try
		{
			File dir = new File(dirPath);
			if (dir.exists() && dir.isDirectory())
				return true;
			else
			{
				dir.getParentFile().mkdirs();
				return true;
			}
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 *            文件目录
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static File NewFile(String filePath, String fileName)
	{
		File file = null;
		try
		{
			file = new File(filePath, fileName);
			file.delete();
			file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 把文件数据写入文件
	 * 
	 * @param file
	 *            要写入文件
	 * @param data
	 *            文件数据
	 * @param offset
	 *            写入位置
	 * @param count
	 *            写入长度
	 * @throws IOException
	 */
	public static void WriteFile(File file, byte[] data, int offset, int count) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(file, true);
		fos.write(data, offset, count);
		fos.flush();
		fos.close();
	}

	/**
	 * 获取文件的字节数组
	 * 
	 * @param filePath
	 *            文件目录
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public static byte[] ReadFile(String filePath, String fileName) throws IOException
	{
		File file = new File(filePath, fileName);
		file.createNewFile();
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		int leng = bis.available();
		byte[] b = new byte[leng];
		bis.read(b, 0, leng);
		bis.close();
		return b;

	}

	/**
	 * 文件类型
	 * 
	 * @author HUITU-CHEN
	 * 
	 */
	public enum FILE_TYPE
	{
		html, image, pdf, text, audio, video, chm, word, excel, powerpoint,
	}

	/**
	 * 判断手机是否有SD卡。
	 * 
	 * @return 有SD卡返回true，没有返回false。
	 */
	public static boolean hasSDCard()
	{
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 可用于获取打开以下文件的intent PDF,PPT,WORD,EXCEL,CHM,HTML,TEXT,AUDIO,VIDEO
	 * 
	 * @param FilePath
	 *            文件路径
	 * @param Type
	 *            文件类型
	 * @return Intent
	 */
	public static Intent GetOpenFileIntent(String FilePath, FILE_TYPE Type)
	{
		String strType = "";
		Uri uri = null;
		Intent intent = new Intent("android.intent.action.VIEW");

		switch (Type)
		{
		case html:
			strType = "text/html";
			uri = Uri.parse(FilePath).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(FilePath).build();
			break;

		case image:
			strType = "image/*";
			break;

		case pdf:
			strType = "application/pdf";
			break;

		case text:
			strType = "text/plain";
			break;

		case audio:
			strType = "audio/*";
			break;

		case video:
			strType = "video/*";
			break;

		case chm:
			strType = "application/x-chm";
			break;

		case word:
			strType = "application/msword";
			break;

		case excel:
			strType = "application/vnd.ms-excel";
			break;

		case powerpoint:
			strType = "application/vnd.ms-powerpoint";
			break;

		default:
			break;
		}

		if (Type == FILE_TYPE.image || Type == FILE_TYPE.pdf || Type == FILE_TYPE.text || Type == FILE_TYPE.chm || Type == FILE_TYPE.word || Type == FILE_TYPE.excel || Type == FILE_TYPE.powerpoint)
		{
			uri = Uri.fromFile(new File(FilePath));
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		else if (Type == FILE_TYPE.audio || Type == FILE_TYPE.video)
		{
			uri = Uri.fromFile(new File(FilePath));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("oneshot", 0);
			intent.putExtra("configchange", 0);
		}
		intent.setDataAndType(uri, strType);

		return intent;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS)
	{
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024)
		{
			fileSizeString = df.format((double) fileS) + "B";
		}
		else if (fileS < 1048576)
		{
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		}
		else if (fileS < 1073741824)
		{
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		}
		else
		{
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir)
	{
		if (dir == null)
		{
			return 0;
		}
		if (!dir.isDirectory())
		{
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files)
		{
			if (file.isFile())
			{
				dirSize += file.length();
			}
			else if (file.isDirectory())
			{
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	public static Bitmap getBitmapFromFile(File dst, int width, int height)
	{
		if (null != dst && dst.exists())
		{
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0)
			{
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try
			{
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			}
			catch (OutOfMemoryError e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
	{
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8)
		{
			roundedSize = 1;
			while (roundedSize < initialSize)
			{
				roundedSize <<= 1;
			}
		}
		else
		{
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
	{
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound)
		{
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1))
		{
			return 1;
		}
		else if (minSideLength == -1)
		{
			return lowerBound;
		}
		else
		{
			return upperBound;
		}
	}

}
