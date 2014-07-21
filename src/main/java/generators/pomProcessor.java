package generators;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class pomProcessor {
	
	public Map<String, Object> process(String outDir, String serviceName, String groupId) throws TransformerException, ParserConfigurationException, SAXException, IOException{
		Map<String,Object> res = new HashMap<String, Object>();
		
		File pom = new File(outDir);
		pom.mkdirs();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(
				"src\\main\\resources\\templates\\pom.xml"));
		
		
		doc.getElementsByTagName("groupId").item(0).setTextContent(groupId);
		doc.getElementsByTagName("artifactId").item(0).setTextContent(serviceName);
		

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource sourcepom = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(
				pom.getAbsolutePath() + "\\pom.xml"));
		

		transformer.transform(sourcepom, result);
		System.out.println(pom.getAbsolutePath() + "\\"+serviceName+".wsdl");
		
		
		return res;
	}

}
