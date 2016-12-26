package handy.tools.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public abstract class XmlHelper extends BasicHelper {

	public static void writeXMLFileUTF8(String writeToPath, Document doc) {
		
		XMLWriter xmlWriter = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(DATA_FORMAT_UTF8);
		try {
			String path = PathHelper.resolveAbsolutePath(writeToPath);
			Writer fileWriter = new FileWriter(path);
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
	
	/*debug done
	 * 
	 * */
	public static void createXmlDoc(String xmlPath, String rootName, Map<String, Object> subElements) {
		
		Document doc = DocumentHelper.createDocument();
		Iterator<String> it = subElements.keySet().iterator();
		Element root = DocumentHelper.createElement(rootName);
		
		for(int i = 0; it.hasNext(); i++) {
			String key = (String) it.next();
			//System.out.println("key = " + key);
			//System.out.println("value = " + subElements.get(key));
			Element ele = DocumentHelper.createElement(key);
			ele.setText((String) subElements.get(key));
			root.add(ele);
		}
		
		doc.add(root);
		writeXMLFileUTF8(xmlPath, doc);	
		
	}
	
	/*debug done
	 * 
	 * */
	public static void createEmptyXmlDoc(String xmlPath, String rootName) {
		
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement(rootName);
		doc.add(root);
		writeXMLFileUTF8(xmlPath, doc);
	}

	/*debug done
	 * 
	 * */
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
	
	/*debug done
	 * 
	 * */
	private static Element findElement(Document doc, String eleName) {
				
		Element root = doc.getRootElement();
		if(eleName.equals(root.getName())) {
			//System.out.println("get root element:" + root.getName());
			return root;
		} 
		
		Element ele = root.element(eleName);
		return ele;
	}
	
	public static List<?> findElements(Document doc, String eleName) {
		
		Element root = doc.getRootElement();
		List<?> results = root.elements(eleName);
		
		return results;
	}
	
	/*debug done
	 * 
	 * */
	public static Map<String,String> getAttributesOfElement(Element ele) {
		
		Map<String,String> result = new HashMap<String,String>();
		
		for(int i = 0; i < ele.attributeCount(); i++) {
			Attribute attr = ele.attribute(i);
			
			//System.out.println("attribute name: " + attr.getName());
			//System.out.println("attribute value: " + attr.getValue());
			result.put(attr.getName(), attr.getValue());
		}
		
		return result;
	}

	
	/*debug done
	 * 
	 * */
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
		
		writeXMLFileUTF8(xmlPath, doc);		
	}
	
	
	/*simple spring like methods
	 * 
	 * 
	 * */
	public static Object getBean(String xmlPath, String beanId) {
		
		Object beanObj = null;
		Document doc = readXmlFrmFile(xmlPath);
		Element beanElement = null;
		
		try {
			beanElement = getBeanById(doc,beanId);
			beanObj = InitBean(beanElement);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanObj;
		
	}
	
	public static Element getBeanById(Document doc, String beanId) throws Exception {
		
		Element root = doc.getRootElement();
		if(!"beans".equals(root.getName())) {
			throw new Exception("bean config format error: root element is not beans");
		}
		Element beanElement = null;
		beanElement = root.elementByID(beanId);
		
		if(null == beanElement) {
			List<Element> beans = null;
			beans = root.elements("bean");
			for(int i = 0; i < beans.size(); i++ ) {
				beanElement = beans.get(i);
				if(beanElement.attribute("id").getValue().equals(beanId)) {
					break;
				}
			}
		}

		return beanElement;
	}
	
	public static Object InitBean(Element beanEle) {
		
		String beanClazName = beanEle.attributeValue("class");
		
		List<Element> propertyElements = beanEle.elements("property");
		Map<String, String> properties = null;
		Class<?> beanClaz = null;
		Object beanObj = null;
		
		try {
			beanClaz = TypeHelper.getRequireClass(beanClazName);
			properties = ReflectHelper.retrieveBeanProperties(beanClaz);
			beanObj = beanClaz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < propertyElements.size(); i++) {
			String propName = propertyElements.get(i).attribute("name").getValue();
			String propValue = propertyElements.get(i).attribute("value").getValue();
			String propType = properties.get(propName);
			ReflectHelper.callSetter(beanObj, propName, propType, propValue);
		}
		
		return beanObj;
	}
	
}
