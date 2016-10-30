package handy.tools.files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

import sun.nio.ch.DirectBuffer;
import handy.tools.interfaces.NioPack;

public class FileNioPack extends NioPack {

	public int readFlag = 0;
	public int writeFlag = 0;
	
	public FileChannel fInChan = null;
	public FileChannel fOutChan = null;
	public ByteBuffer byteBuf = null;
	public CharBuffer charBuf = null;
	
}
