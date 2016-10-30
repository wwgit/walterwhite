package handy.tools.interfaces;

import handy.tools.files.FileNioPack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import sun.nio.ch.DirectBuffer;

public abstract class FileHelper {
	
	
	/*
	 * basic method - reading/writing resources initialize
	 * 
	 * */
	protected static FileChannel InitReadFileChannel(String readFilePath) throws FileNotFoundException {
		
		FileInputStream fis = new FileInputStream(readFilePath);
		return fis.getChannel();

	}	
	protected static FileChannel InitWriteFileChannel(String writeFilePath) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(writeFilePath);
		return fos.getChannel();
	}
	
	/*
	 * basic metho - close reading/write resources
	 * 
	 * */
	protected static void closeReadFileChannel(FileChannel fInChan) throws IOException {
		if(null != fInChan) {
			fInChan.close();
		}
	}
	protected static void closeWriteFileChannel(FileChannel fOutChan) throws IOException {
		if(null != fOutChan) {
			fOutChan.close();
		}
	}
	protected static void deleteDirectMem(ByteBuffer byteBuffer) throws Exception {
		if(null != byteBuffer && byteBuffer.isDirect()) {
			((DirectBuffer)byteBuffer).cleaner().clean();
		}
	}
	
	
	
	/*
	 * reads whole contents from file using old IO APIs
	 * */	
	public static String readFileContents(String path) {
		
		//String contents = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try {
			
			is = new FileInputStream(path);
			isr = new InputStreamReader(is,"utf-8");
			br = new BufferedReader(isr);
			
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			isr.close();is.close();br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return sb.toString();
	}

	/*
	 * reads buffers from file channel using highly efficient NIO APIS
	 * 
	 * */
	public static byte[] readByteBufFrmFile(FileNioPack fnioPack, byte[] results) throws Exception {
		
		if(null == fnioPack) {
			throw new Exception("input parameter FileNioPack Object is Null. It's Not initialized !");
		}
						
		if(fnioPack.readFlag > -1) {
			
			fnioPack.byteBuf.clear();
			fnioPack.readFlag = fnioPack.fInChan.read(fnioPack.byteBuf);
			fnioPack.byteBuf.flip();
			fnioPack.byteBuf.get(results, 0, fnioPack.byteBuf.limit());
		} 
		
		return results;
	}
	
	public static String readStringFrmFile(FileNioPack fnioPack, CharsetDecoder decoder) throws Exception {
		
		if(null == fnioPack) {
			throw new Exception("input parameter FileNioPack Object is Null. It's Not initialized !");
		}
		
		if(fnioPack.readFlag > -1) {
			
			fnioPack.byteBuf.clear();
			fnioPack.readFlag = fnioPack.fInChan.read(fnioPack.byteBuf);
			fnioPack.byteBuf.flip();
			//result = new byte[fnioPack.byteBuf.limit()];
			//fnioPack.byteBuf.get(results, 0, fnioPack.byteBuf.limit());
			fnioPack.charBuf = decoder.decode(fnioPack.byteBuf);
			//System.out.println("reading byte: " + result.toString());

		} 
		
		return fnioPack.charBuf.toString();
	}
	
	
	/*
	 * reads whole contents of file with buffer using new NIO APIS
	 * 
	 * */
	public static String readContentsFrmFile(String readFilePath, String format, int buffSize) throws Exception {
		
		FileNioPack fnioPack = new FileNioPack();
		fnioPack.fInChan = InitReadFileChannel(readFilePath);
		fnioPack.byteBuf = ByteBuffer.allocateDirect(buffSize);
		
		//StringBuilder sb = new StringBuilder();
		String str = null;
		Charset charset = Charset.forName(format);
		CharsetDecoder decoder = charset.newDecoder();
		byte[] results = new byte[buffSize];
		byte flat = 0;
		while(fnioPack.readFlag > -1) {
			
			Arrays.fill(results,flat);
			
			String tmp = new String(readByteBufFrmFile(fnioPack,results),0,fnioPack.byteBuf.limit(),format);
			str += tmp;
			//System.out.println("reading buff: " + str);
			
		}
		//Thread.sleep(15000);
		System.out.println("calling deleteDirectMem");
		closeReadFileChannel(fnioPack.fInChan);
		deleteDirectMem(fnioPack.byteBuf);
		//Thread.sleep(15000);
		return str;
	}	
	
}
