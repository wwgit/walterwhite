package handy.tools.helpers;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public abstract class PathHelper extends BasicHelper {
	
	public static String resolveAbsolutePath(String relativePath) {
		
		System.out.println("path: " + relativePath);
		String path = PathHelper.class.getClassLoader().getResource(relativePath).getPath();
		
		//File file = new File(url.getPath()).getParent();
		
		System.out.println("path: " + path);
		
		//String proRootPath = System.getProperty("user.dir");
		
		//return proRootPath.replace("\\", "/") + relativePath.replace("\\", "/");
		return path;
		
	}
	
	public static InputStream resolveAbsoluteStream(String relativePath) {
		
		InputStream fis = PathHelper.class.getClassLoader().getResourceAsStream(relativePath);
		
		return fis;
	}

}
