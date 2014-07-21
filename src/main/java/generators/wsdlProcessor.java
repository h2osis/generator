package generators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class wsdlProcessor {
	
	public Map<String, Object> process(String srcWsdl, String outDir, String serviceName) throws IOException, SAXException, ParserConfigurationException, TransformerException{
		Map<String, Object> res = new  HashMap<String, Object>();
		
		File wsdl = new File(outDir + "/src/main/webapp/WEB-INF");
		wsdl.mkdirs();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(
				srcWsdl));

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource sourcewsdl = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(
				wsdl.getAbsolutePath() + "\\"+serviceName+".wsdl"));
		

		transformer.transform(sourcewsdl, result);
		System.out.println(wsdl.getAbsolutePath() + "\\"+serviceName+".wsdl");
		
		return res;
		
	}

}
