package handy.tools.io;


import handy.tools.helpers.FundationHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import sun.nio.ch.DirectBuffer;

public abstract class NioHelper extends FundationHelper {
	
	//testing done
	protected static void deleteDirectMem(ByteBuffer byteBuffer) throws Exception {
		if(null != byteBuffer && byteBuffer.isDirect()) {
			((DirectBuffer)byteBuffer).cleaner().clean();
		}
	}
	
	//testing done
	public static char[] byteArrToCharArr(byte[] byteArray, String charFormat) throws Exception {
		
		if(null == byteArray || byteArray.length < 1) {
			throw new IOException("from call byteArrToCharArr: input array invalid");
		}

		ByteBuffer buffer = ByteBuffer.wrap(byteArray);
		Charset charset = Charset.forName(charFormat);
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuf = decoder.decode(buffer);

		//System.out.println("in func: " + charBuf.array().length);
		return charBuf.array();
	}
	
	public static String byteArrToString(byte[] byteArray, String format) throws Exception {
		
		if(null == byteArray || byteArray.length < 1) {
			throw new IOException("from call byteArrToCharArr: input array invalid\n");
		}		
		return new String(byteArray,0,byteArray.length, format);		
	}
	
	//testing done
	protected static int readBytesFrmFNIO(FileChannel fInChan, ByteBuffer byteBuf, byte[] results) throws Exception {
		
		if(null == byteBuf || null == fInChan || null == results) {
			throw new Exception("input parameter is Null. It's Not initialized !");
		}
							
		byteBuf.clear();
		int readFlag = fInChan.read(byteBuf);
		byteBuf.flip();
		byteBuf.get(results, 0, byteBuf.limit());
		
		return readFlag;
		
	}
	
	//testing done	
	protected static int readCharsFrmFNIO(FileChannel fInChan, ByteBuffer byteBuf, 
										byte[] results, char[] charResults, String charFormat) throws Exception {
		
		int readFlag = 0;
		readFlag = readBytesFrmFNIO(fInChan,byteBuf,results);
		Charset charset = Charset.forName(charFormat);
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuf = decoder.decode(byteBuf);
		charBuf.get(charResults,0,charBuf.limit());
		//fnioPack.byteBuf.get(results, 0, fnioPack.byteBuf.limit());			
		
		return readFlag;
		
	}
	
	/* description: read all that in mapped memory as string. - testing done
	 * times: total mapped memory =  buffsize * times
	 * 
	 * */
	protected static void readStrFrmMapMem(FileChannel fInChan, int times, byte[] results,StringBuilder sb,
										long position, String format) throws Exception {
		
		byte flat = 0;
		MappedByteBuffer mapByteBuff = fInChan.map(FileChannel.MapMode.READ_ONLY, 
													position, results.length*times);
				
			for(int j = 0; j < times; j++) {
				Arrays.fill(results,flat);
				mapByteBuff.get(results);
				sb.append(new String(results,0,results.length,format));
			}
	}
	
	
	/*description: write to mapped memory as bytes
	 *  
	 * */
	public static void writeStrToMapMem(FileChannel fOutChan, String data,
										  int position, String format) throws Exception {

		byte[] byteData = data.getBytes(format);
		
		MappedByteBuffer mapByteBuff = fOutChan.map(FileChannel.MapMode.READ_WRITE, 
													position, byteData.length);
		mapByteBuff.put(byteData);
		
	}
	
	/*description: copy file to another file using given buffer and mapped memory - high performance -  tested
	 * 
	 * 
	 * */
	protected static void fileCopyFNIO(FileChannel fInChan, FileChannel fOutChan, long position, int size) throws Exception {
		
		MappedByteBuffer mapByteBuff = fInChan.map(FileChannel.MapMode.READ_ONLY, 
				position, size);
		fOutChan.write(mapByteBuff);		
		
	}
	

}
