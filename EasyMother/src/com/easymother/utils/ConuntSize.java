package com.easymother.utils;


	import java.io.File;
	import java.text.DecimalFormat;

import android.text.TextUtils;

import java.io.FileInputStream;
import java.math.BigDecimal;
	 
	
	public class  ConuntSize { 
	 private static ConuntSize instance;
	 
	 public ConuntSize()
	 {
	 
	 }
	 
	 public static ConuntSize getInstance()
	 {
	  if (instance == null)
	  {
	   instance = new ConuntSize();
	  }
	 return instance;
	 }
	 
	 
	 public long getFileSizes(File f) throws Exception
	 {
	  long s = 0;
	  if (f.exists())
	  {
	   FileInputStream fis = null;
	   fis = new FileInputStream(f);
	   s = fis.available();
	  }
	  else
	  {
	   f.createNewFile();
	   System.out.println("文件不存在");
	  }
	  return s;
	 }
	 
	 /**   
	     * 获取文件夹大小   
	     * @param file File实例   
	     * @return long      
	     */     
	    public static long getFolderSize(java.io.File file){    
	   
	        long size = 0;    
	        try {  
	            java.io.File[] fileList = file.listFiles();     
	            for (int i = 0; i < fileList.length; i++)     
	            {     
	                if (fileList[i].isDirectory())     
	                {     
	                    size = size + getFolderSize(fileList[i]);    
	   
	                }else{     
	                    size = size + fileList[i].length();    
	   
	                }     
	            }  
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }     
	       //return size/1048576;    
	        return size;    
	    }    
	 
	    /**   
	     * 删除指定目录下文件及目录    
	     * @param deleteThisPath   
	     * @param filepath   
	     * @return    
	     */     
	    public void deleteFolderFile(String filePath, boolean deleteThisPath) {     
	        if (!TextUtils.isEmpty(filePath)) {     
	            try {  
	                File file = new File(filePath);     
	                if (file.isDirectory()) {// 处理目录     
	                    File files[] = file.listFiles();     
	                    for (int i = 0; i < files.length; i++) {     
	                        deleteFolderFile(files[i].getAbsolutePath(), true);     
	                    }      
	                }     
	                if (deleteThisPath) {     
	                    if (!file.isDirectory()) {// 如果是文件，删除     
	                        file.delete();     
	                    } else {// 目录     
	                   if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除     
	                            file.delete();     
	                        }     
	                    }     
	                }  
	            } catch (Exception e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }     
	        }     
	    }   
	 
	    
	    /** 
	     * 格式化单位 
	     * @param size 
	     * @return 
	     */  
	    public static String getFormatSize(double size) {  
	        double kiloByte = size/1024;  
	        if(kiloByte < 1) {  
	            return size + "Byte(s)";  
	        }  
	          
	        double megaByte = kiloByte/1024;  
	        if(megaByte < 1) {  
	            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
	            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
	        }  
	          
	        double gigaByte = megaByte/1024;  
	        if(gigaByte < 1) {  
	            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
	            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
	        }  
	          
	        double teraBytes = gigaByte/1024;  
	        if(teraBytes < 1) {  
	            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
	            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
	        }  
	        BigDecimal result4 = new BigDecimal(teraBytes);  
	        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
	    }  
	    
	    
	 
	 public String FormetFileSize(long fileS)
	 {// 转换文件大小
	  DecimalFormat df = new DecimalFormat("#.00");
	  String fileSizeString = "";
	  if (fileS < 1024)
	  {
	   fileSizeString = df.format((double) fileS) + "B";
	  }
	  else if (fileS < 1048576)
	   {
	    fileSizeString = df.format((double) fileS / 1024) + "K";
	   }
	   else if (fileS < 1073741824)
	   {
	    fileSizeString = df.format((double) fileS / 1048576) + "M";
	   }
	   else
	   {
	    fileSizeString = df.format((double) fileS / 1073741824) + "G";
	   }
	  return fileSizeString;
	 }
	 
	 
	
	 

}
