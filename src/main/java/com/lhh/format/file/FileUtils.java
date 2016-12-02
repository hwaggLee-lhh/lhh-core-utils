package com.lhh.format.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * 文件工具类
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class FileUtils {
	
	/**
	 * 读取所有文件夹，如果文件夹不存在则创建（含父级）
	 * @param dir
	 * @param file
	 * @return
	 */
	public static File mkdir(String dir,String file) {
		if(dir == null) throw new IllegalArgumentException("dir must be not null");
		File result = new File(dir,file);
		parnetMkdir(result);
		return result;
	}

    /**
     * 读取所有父、祖父级文件夹，文件不存在创建
     * @param file
     * @return
     */
    public static File parentMkdir(String file) {
        if(file == null) throw new IllegalArgumentException("file must be not null");
        File result = new File(file);
        parnetMkdir(result);
        return result;
    }
    
    /**
     * 查看父级文件是否存在，不存在则创建
     * @param outputFile
     */
    public static void parnetMkdir(File outputFile) {
		if(outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
	}
    
    /**
     * 判断文件地址是否为图片（主流）后缀
     * @param subfix
     * @return
     */
    public static boolean isImage(String subfix){
    	// 声明图片后缀名数组 
    	String imgeArray [] = {"bmp","gif","jpeg","jpg","png","ico"}; 
		// 遍历名称数组 
    	for(int i = 0; i<imgeArray.length;i++){ 
    		if(subfix.equalsIgnoreCase(imgeArray[i])){
    			return true;
    		}
    	} 
    	return false; 
    }
    
    /**
     * 判断字符串类型规则
     * @param contentType
     * @return
     */
    public static String getSubffixByContentType(String contentType){
    	String contentTypeArray [][] = {{"application/x-bmp","bmp"},
    			{"image/gif","gif"},{"image/jpeg","jpg"},
    			{"image/png","png"},{"image/x-icon","ico"}}; 
    	String subffix = "";
    	for(int i=0;i<contentTypeArray.length;i++){
    		String[] contentTypeItem = contentTypeArray[i];
    		if(contentType.equalsIgnoreCase(contentTypeItem[0])){
    			subffix = contentTypeItem[1];
    			return subffix;
    		}
    	}
    	return subffix;
    }
    

	public static void main(String[] args) throws Exception {
		updateFileName(new File("C:\\Users\\huage\\Desktop\\app\\images"), "1234", false);
	}

	
	/**
	 * 修改文件名称或者目录名称
	 * @param oldFile:旧文件或目录
	 * @param newsFilename:新文件名称
	 * @param isPx:如果是文件，是否使用旧文件的后缀,true为使用旧文件后缀，如果是目录则固定值false
	 * @throws Exception
	 */
	public static void updateFileName(File oldFile,String newsFilename,boolean isPx) throws Exception {
		if( oldFile == null )return;
		String newPath = oldFile.getPath().replace(oldFile.getName(), "") + newsFilename ;
		if( oldFile.isDirectory()){//目录
			String name = oldFile.getName();
			if(  name.lastIndexOf(".")>-1 ){
				newPath += name.substring(name.lastIndexOf("."));
			}
			File fn = new File(newPath);
			oldFile.renameTo(fn);
		}else{//文件
			if( isPx ){
				String name = oldFile.getName();
				if(  name.lastIndexOf(".")>-1 ){
					newPath += name.substring(name.lastIndexOf("."));
				}
			}
			File fn = new File(newPath);
			oldFile.renameTo(fn);
		}
	}

	/**
	 * 读取文件目录，报告文件夹中的子文件
	 * @param f
	 * @param fileList
	 */
	public static void listFile(File f, List<String> fileList) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i], fileList);
			}
		} else {
			fileList.add(f.getAbsolutePath());
		}
	}

	/**
	 * 在文件的最后追加内容
	 * @param file
	 * @param conent
	 */
	public static void addFile(String file, String conent) {
		BufferedWriter out = null;
		try {
			File f = new File(file);
			if(!f.exists()){
				f.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 想新文件文件中写入内容
	 * @param file
	 * @param content
	 */
	public static void printFile(String file, String content){
		try {
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String filePath) throws Exception {
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath)));
		StringBuffer content = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			content.append(str).append("\n");
		}
		return content.toString();
	}
	
	

	 // 缓存文件头信息-文件头信息  
   public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();  
   static {  
       // images  
       mFileTypes.put("FFD8FF", "jpg");  
       mFileTypes.put("89504E47", "png");  
       mFileTypes.put("47494638", "gif");  
       mFileTypes.put("49492A00", "tif");  
       mFileTypes.put("424D", "bmp");  
       //  
       mFileTypes.put("41433130", "dwg"); // CAD  
       mFileTypes.put("38425053", "psd");  
       mFileTypes.put("7B5C727466", "rtf"); // 日记本  
       mFileTypes.put("3C3F786D6C", "xml");  
       mFileTypes.put("68746D6C3E", "html");  
       mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件  
       mFileTypes.put("D0CF11E0", "doc");  
       mFileTypes.put("5374616E64617264204A", "mdb");  
       mFileTypes.put("252150532D41646F6265", "ps");  
       mFileTypes.put("255044462D312E", "pdf");  
       mFileTypes.put("504B0304", "docx");  
       mFileTypes.put("52617221", "rar");  
       mFileTypes.put("57415645", "wav");  
       mFileTypes.put("41564920", "avi");  
       mFileTypes.put("2E524D46", "rm");  
       mFileTypes.put("000001BA", "mpg");  
       mFileTypes.put("000001B3", "mpg");  
       mFileTypes.put("6D6F6F76", "mov");  
       mFileTypes.put("3026B2758E66CF11", "asf");  
       mFileTypes.put("4D546864", "mid");  
       mFileTypes.put("1F8B08", "gz");  
       mFileTypes.put("4D5A9000", "exe/dll");  
       mFileTypes.put("75736167", "txt");  
   }  
 
   /** 
    * 根据文件路径获取文件头信息 
    *  
    * @param filePath 文件路径 req.getSession().getServletContext().getRealPath(file);
    * @return 文件头信息 
    */  
   public static String getFileType(String filePath) {  
       return mFileTypes.get(getFileHeader(filePath));  
   }  
 
   /** 
    * 根据文件路径获取文件头信息 
    *  
    * @param filePath 文件路径 
    * @return 文件头信息 
    */  
   public static String getFileHeader(String filePath) {  
       FileInputStream is = null;  
       String value = null;  
       try {  
           is = new FileInputStream(filePath);  
           byte[] b = new byte[4];  
           /* 
            * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length 
            * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len) 
            * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。 
            */  
           is.read(b, 0, b.length);  
           value = bytesToHexString(b);  
       } catch (Exception e) {  
       } finally {  
           if (null != is) {  
               try {  
                   is.close();  
               } catch (IOException e) {  
               }  
           }  
       }  
       return value;  
   }  
 
   /** 
    * 将要读取文件头信息的文件的byte数组转换成string类型表示 
    *  
    * @param src  要读取文件头信息的文件的byte数组 
    * @return 文件头信息 
    */  
   private static String bytesToHexString(byte[] src) {  
       StringBuilder builder = new StringBuilder();  
       if (src == null || src.length <= 0) {  
           return null;  
       }  
       String hv;  
       for (int i = 0; i < src.length; i++) {  
           // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写  
           hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();  
           if (hv.length() < 2) {  
               builder.append(0);  
           }  
           builder.append(hv);  
       }  
       return builder.toString();  
   }  
 
   
   public static void writeFile(String fileName,String filePath ,String sets)  
           throws IOException {  
       FileWriter fw = new FileWriter(filePath+"/"+fileName);  
       PrintWriter out = new PrintWriter(fw);  
       out.write(sets);  
       out.println();  
       fw.close();  
       out.close();  
   }  
 
   public static String readFile(String fileName,String filePath) {  
       File file = new File(filePath+"/"+fileName);  
       BufferedReader reader = null;  
       String laststr = "";  
       try {  
           reader = new BufferedReader(new FileReader(file));  
           String tempString = null;  
           while ((tempString = reader.readLine()) != null) {  
               laststr = laststr + tempString;  
           }  
           reader.close();  
       } catch(FileNotFoundException e) {
       	return null;
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           if (reader != null) {  
               try {  
                   reader.close();  
               } catch (IOException e1) {  
               }  
           }  
       }  
       return laststr;  
   }  
}
