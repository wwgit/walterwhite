package handy.tools.constants;

import handy.tools.constants.DataTypes;

import java.io.InputStream;


/* 0 == ascii stream, 1 == binary stream
 * 	if(flag == 0) {
			return DataTypes.JAVA_LANG_BINARY_STREAM;
		} else if(flag == 1) {
			return DataTypes.JAVA_LANG_ASCII_STREAM;
 * 
 * */
public class ComplexValue {
	
	public int flag;
	public InputStream data;

}
