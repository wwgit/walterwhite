package handy.tools.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public abstract class XmlHelper extends BasicHelper {

	public static void createXmlDoc(String xmlPath, String rootName, Map<String, Object> subElements) {
		
		Document doc = DocumentHelper.createDocument();
		Iterator<String> it = subElements.keySet().iterator();
		Element root = DocumentHelper.createElement(rootName);
		
		for(int i = 0; it.hasNext(); i++) {
			String key = (String) it.next();
			System.out.println("key = " + key);
			System.out.println("value = " + subElements.get(key));
			Element ele = DocumentHelper.createElement(key);
			ele.setText((String) subElements.get(key));
			root.add(ele);
		}
		
		doc.add(root);
		XMLWriter xmlWriter = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(DATA_FORMAT_UTF8);
		try {
			
			Writer fileWriter = new FileWriter(xmlPath);
			xmlWriter = new XMLWriter(fileWriter, format);
			xmlWriter.write(doc);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void createEmptyXmlDoc(String xmlPath, String rootName) {
		
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement(rootName);
		XMLWriter xmlWriter = null;
		Writer fileWriter = null;
		doc.add(root);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(DATA_FORMAT_UTF8);
		try {
			//System.out.println("get xml path: " + PathHelper.resolveAbsolutePath(xmlPath));
			fileWriter = new FileWriter(xmlPath);
			xmlWriter = new XMLWriter(fileWriter, format);
			xmlWriter.write(doc);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Document readXmlFrmFile(String xmlPath) {
		
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			
			doc = reader.read(PathHelper.resolveAbsolutePath(xmlPath));
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return doc;
	}
	
	public static Element findElement(Document doc, String eleName) {
				
		Element root = doc.getRootElement();
		if(eleName.equals(root.getName())) {
			System.out.println("get root element:" + root.getName());
			return root;
		} 
		
		Element ele = root.element(eleName);
		return ele;
	}
	
	public static void addSubElements(String xmlPath, String eleName, Map<String, Object> subElements) {
		
		Document doc = readXmlFrmFile(xmlPath);
		if(null == doc) {
			return;
		}
		
		Element ele = findElement(doc, eleName);
		if(null == ele) {
			return;
		}
		
		Iterator<String> it = subElements.keySet().iterator();
		Element subEle = null;
		
		for(int i = 0; it.hasNext(); i++) {
			String key = (String) it.next();
			System.out.println("key = " + key);
			System.out.println("value = " + subElements.get(key));
			subEle = DocumentHelper.createElement(key);
			subEle.setText((String) subElements.get(key));
			ele.add(subEle);
		}
		
		
		XMLWriter xmlWriter = null;
		Writer fileWriter = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(DATA_FORMAT_UTF8);
		try {
			//System.out.println("get xml path: " + PathHelper.resolveAbsolutePath(xmlPath));
			fileWriter = new FileWriter(PathHelper.resolveAbsolutePath(xmlPath));
			xmlWriter = new XMLWriter(fileWriter, format);
			xmlWriter.write(doc);			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
