package handy.tools.files;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

import handy.tools.interfaces.NioPack;

public class FileNioPack extends NioPack {

	public int readFlag = 0;
	public int writeFlag = 0;
	
	public FileChannel fInChan = null;
	public FileChannel fOutChan = null;
	public ByteBuffer byteBuf = null;
	public CharBuffer charBuf = null;
	
}
