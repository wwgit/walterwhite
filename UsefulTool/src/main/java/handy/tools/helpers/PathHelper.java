package handy.tools.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class PathHelper extends BasicHelper {
	
	public static String resolveAbsolutePath(String relativePath) {
				
		String path = null;
		try {
			path = PathHelper.class.getClassLoader().getResource(relativePath).getPath();
		} catch (Exception e) {
			//e.printStackTrace();
			path = GetAbsoluteFilePath(relativePath);
		}
		
		//System.out.println("path: " + path);
		return path;
		
	}
	
	public static InputStream resolveAbsoluteStream(String relativePath) {
		
		InputStream fis = null;
		try {
		   fis = PathHelper.class.getClassLoader().getResourceAsStream(relativePath);
		} catch (Exception e) {
			//e.printStackTrace();
			String path = GetAbsoluteFilePath(relativePath);
			try {
				fis = new FileInputStream(path);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}	
		
		return fis;
	}

}
