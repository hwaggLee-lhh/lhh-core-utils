package com.lhh.format.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * io工具类
 * @author hwaggLee
 * @createDate 2016年12月2日
 */
public class IoUtils {

	/**
	 * fileinputStream转换成inputstrea
	 * @param fileInput
	 * @return
	 */
	public static InputStream convertToInputStream(FileInputStream fileInput) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024*4];
		int n = -1;
		InputStream inputStream = null;
		try {
			while ((n=fileInput.read(buffer)) != -1) {
				baos.write(buffer, 0, n);
				
			}
			byte[] byteArray = baos.toByteArray();
			inputStream = new ByteArrayInputStream(byteArray);
			return inputStream;
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  

	

	/**
	 * 将file写入BufferedOutputStream中
	 * @param bo
	 * @param file
	 * @throws Exception
	 */
	public static void converReadIO(BufferedOutputStream bo,File file) throws Exception{
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bi = new BufferedInputStream(in);
		int b;
		while ((b = in.read()) != -1) {
			bo.write(b);
		}
		closeIO(bi,in);
		bo.flush();//清空缓存
	}
	
	/**
	 * 将BufferedInputStream写入file中
	 * @param bo
	 * @param file
	 * @throws Exception
	 */
	public static void converWriteIO(BufferedInputStream bininput,File file) throws Exception{
		FileOutputStream out = new FileOutputStream(file);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		int b;
		while ((b = bininput.read()) != -1) {
			bout.write(b);
		}
		closeIO(bout,out);
		bout.flush();//清空缓存
	}
	
	
	/**
	 * 关闭io
	 * @param cl
	 */
	public static void closeIO(AutoCloseable... cl){
		if( cl == null || cl.length == 0 )return;
		for (AutoCloseable c : cl) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
