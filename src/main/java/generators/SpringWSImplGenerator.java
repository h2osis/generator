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

public class SpringWSImplGenerator {

	public Map<String, Object> generate(String outDir, String serviceName, String basePackageName) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		Map<String, Object> res = new HashMap<String, Object>();

		// generate webxml
		File webxml = new File(outDir + "/src/main/webapp/WEB-INF");
		webxml.mkdirs();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("src\\main\\resources\\templates\\web.xml"));

		doc.getElementsByTagName("web-app").item(0).getAttributes().getNamedItem("id").setTextContent(serviceName);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource sourceWebXml = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(webxml.getAbsolutePath() + "\\web.xml"));

		transformer.transform(sourceWebXml, result);
		System.out.println(webxml.getAbsolutePath() + "\\web.xml");
		// generate spring-config
		File springconfig = new File(outDir + "/src/main/webapp/WEB-INF/config");
		springconfig.mkdirs();

		doc = db.parse(new File("src\\main\\resources\\templates\\spring-config.xml"));
		doc.getElementsByTagName("context:component-scan").item(0).getAttributes().getNamedItem("base-package").setTextContent(basePackageName);
		doc.getElementsByTagName("bean").item(0).getAttributes().getNamedItem("id").setTextContent(serviceName);
		doc.getElementsByTagName("property").item(0).getAttributes().getNamedItem("value").setTextContent("/WEB-INF/" + serviceName + ".wsdl");

		DOMSource sourceSpringConfig = new DOMSource(doc);
		result = new StreamResult(new File(springconfig.getAbsolutePath() + "\\spring-config.xml"));

		transformer.transform(sourceSpringConfig, result);
		System.out.println(springconfig.getAbsolutePath() + "\\spring-config.xml");

		return res;

	}

}
