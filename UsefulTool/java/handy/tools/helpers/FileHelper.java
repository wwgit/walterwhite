package handy.tools.helpers;

import handy.tools.files.FileNioPack;
import handy.tools.io.NioHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import sun.nio.ch.DirectBuffer;

public abstract class FileHelper extends NioHelper {
		
		
	/*
	 * reads/writes data from/to file using old IO APIs
	 * */	
	public static String readAllFrmFile(String path, String format) throws IOException {
		
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		StringBuilder sb = new StringBuilder();
		String line = null;
					
		is = new FileInputStream(path);
		isr = new InputStreamReader(is, format);
		br = new BufferedReader(isr);
			
		while((line = br.readLine()) != null) {
				sb.append(line);
		}
		isr.close();is.close();br.close();
				
		return sb.toString();
	}
	
	public static void appendStrToFile(String path, String data, String format) 
												throws UnsupportedEncodingException, IOException {
		Path thePath = Paths.get(path);
		Files.write(thePath, data.getBytes(format), StandardOpenOption.APPEND);
	}
	
	public static void appendListToFile(String path, List data, String format) 
												throws UnsupportedEncodingException, IOException {
			Path thePath = Paths.get(path);
			Files.write(thePath, data, Charset.forName(format), StandardOpenOption.APPEND);
}
	
	
	/*
	 * reads whole contents for Big file with buffer using new NIO APIS - using direct memory for memory saving
	 * 
	 * */
	public static String readAllFrmDirectMem(String readFilePath, String format, int buffSize) {
		
		FileNioPack fnioPack = null;
		FileInputStream fis = null;
		byte[] results = null;
		StringBuilder sb = null;
				
		try {
			
			fnioPack = new FileNioPack();
			fis = new FileInputStream(readFilePath);
			fnioPack.fInChan = fis.getChannel();
			fnioPack.byteBuf = ByteBuffer.allocateDirect(buffSize);
			results = new byte[buffSize];
			byte flat = 0;
			
			int readFlag = 0;
			sb = new StringBuilder();	
			
			while(readFlag > -1) {

				Arrays.fill(results,flat);
				readFlag = readBytesFrmFNIO(fnioPack.fInChan,fnioPack.byteBuf,results);
				String tmp = new String(results,0,fnioPack.byteBuf.limit(),format);
				sb.append(tmp);	
			}			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fnioPack.fInChan.close();
				fis.close();
				deleteDirectMem(fnioPack.byteBuf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		
		return sb.toString();
	}
	
	
	/*
	 * reads whole contents for Big file with buffer using new NIO APIS - high performance for big file
	 * default times =  100
	 * */
	public static String readFrmMapMem(String readFilePath, String format, int buffSize, int theTimes) {
		
		byte[] results = new byte[buffSize];
		StringBuilder sb = new StringBuilder();				
		FileInputStream fis = null;
		FileChannel fInChan = null;
		
		try {
			
			fis = new FileInputStream(readFilePath);
			fInChan = fis.getChannel();
			results = new byte[buffSize];
			sb = new StringBuilder();
			
			long position = 0;
			int remainder = 0;
			int cnt = (int) (fInChan.size()/buffSize);;
			
			if(fInChan.size() >= buffSize) {
				remainder = (int) (fInChan.size()%buffSize);
			} else {
				remainder = (int) (buffSize%fInChan.size());
			}
			
			int reman_cnt = cnt;
			int times = 100;
			
			/*
			 * reasonable times range is from 10 - 1000; default times = 100
			 * */
			if(theTimes >= 10 && theTimes <= 1000) {
				times = theTimes;
			}
			
			/*
			 * handles mapped memories - file is cut to several mapped memory area first
			 * */
			for (int i = 0; i < cnt/times; i++) {
				
				readStrFrmMapMem(fInChan, times, results, sb, position, format);
				sb.append(new String(results,0,results.length,format));
				reman_cnt -= times;
				position = position + buffSize*times;
				
			}
			
			/*
			 * handle the part that are too small to be taken as one mapped memory
			 * */
			if(0 < reman_cnt) {			
				readStrFrmMapMem(fInChan, reman_cnt, results, sb, position, format);
				sb.append(new String(results,0,results.length,format));
				position = position + buffSize*reman_cnt;			
			}
			
			
			/*
			 * handle the part that are too small to be taken as one buffer
			 * */
			if(0 < remainder) {
				byte[] tmp = new byte[remainder];
				readStrFrmMapMem(fInChan, 1, tmp, sb, position, format);
				sb.append(new String(tmp,0,tmp.length,format));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fInChan.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return sb.toString();
		
	}

	/*
	 * for big file - tested
	 * 
	 * */
	public static void FastFileCopy(String readFilePath, String writeToPath, int buffSize, int theTimes) {
		
		FileChannel fInChan = null;
		FileChannel fOutChan = null;
		FileInputStream fis = null;
		FileOutputStream fous = null;

		try {
			
			fis = new FileInputStream(readFilePath);
			fous = new FileOutputStream(writeToPath);
			fInChan = fis.getChannel();
			fOutChan = fous.getChannel();
			
			long position = 0;
			int remainder = 0;
			int cnt = (int) (fInChan.size()/buffSize);;
			
			if(fInChan.size() >= buffSize) {
				remainder = (int) (fInChan.size()%buffSize);				
			} else {
				remainder = (int) (buffSize%fInChan.size());
			}
			
			int reman_cnt = cnt;
			int times = 100;
			
			/*
			 * reasonable times range is from 10 - 1000; default times = 100
			 * */
			if(theTimes >= 10 && theTimes <= 1000) {
				times = theTimes;
			}
			
			/*
			 * handles mapped memories - file is cut to several mapped memory area first
			 * */
			for (int i = 0; i < cnt/times; i++) {
				
				fileCopyFNIO(fInChan, fOutChan, position, buffSize*times);
				reman_cnt -= times;
				position = position + buffSize*times;
				
			}
			
			/*
			 * handle the part that are too small to be taken as one mapped memory
			 * */
			if(0 < reman_cnt) {			
				fileCopyFNIO(fInChan, fOutChan, position, buffSize*reman_cnt);
				position = position + buffSize*reman_cnt;			
			}			
			
			/*
			 * handle the part that are too small to be taken as one buffer
			 * */
			if(0 < remainder) {
				fileCopyFNIO(fInChan, fOutChan, position, remainder);
			}			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fInChan.close();
				fis.close();
				fOutChan.close();
				fous.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
/*
 *for small size - tested
 * */	
	public static void FileCopyOld(String readFilePath, String writeToPath, int buffSize) {
		
		   InputStream fis = null;  
		   OutputStream fos = null;
		   
		   try {  
		        fis = new BufferedInputStream(new FileInputStream(readFilePath));  
		        fos = new BufferedOutputStream(new FileOutputStream(writeToPath));  
		        byte[] buf = new byte[buffSize];  
		        int i;  
		        while ((i = fis.read(buf)) != -1) {  
		            fos.write(buf, 0, i);  
		        }  
		    }  
		    catch (Exception e) {  
		        e.printStackTrace();  
		    } finally {  
		        try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		          
		    }  
		}  	

	
}
