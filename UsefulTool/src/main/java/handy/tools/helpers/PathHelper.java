package handy.tools.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class PathHelper extends FileHelper {
	
	public static String resolveAbsolutePath(String relativePath) {
				
		String path = null;
		try {
			//System.out.println("relativePath AbsolutePath: " + this.getClass().getClassLoader());
			path = PathHelper.class.getClassLoader().getResource(relativePath).getPath();
		} catch (Exception e) {
			//e.printStackTrace();
			path = GetAbsoluteFilePath(relativePath);
		}
		
		System.out.println("path: " + path);
		return path;
		
	}
	
	public InputStream resolveAbsoluteStream(String relativePath) {
		
		InputStream fis = null;
		try {
			//System.out.println("relativePath AbsoluteStream: " + relativePath.getClass());
		  // fis = this.getClass().getClassLoader().getResourceAsStream(relativePath);
			fis = getFileInputStream(resolveAbsolutePath(relativePath));
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
