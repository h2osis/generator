package generators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class endpointGenerator {
	
	public Map<String, Object> generateEnpointClass(String outDir, String srcWsdl, String packageName, String serviceName, String typesPackageName) throws ParserConfigurationException, SAXException, IOException{
		Map<String,Object> res = new HashMap<String,Object>();
		
		File wsdl = new File(srcWsdl);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(
				srcWsdl));
		
		
		File outEndpointClass = new File(outDir+"\\src\\main\\java\\"+packageName);
		outEndpointClass.mkdirs();
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outDir+"\\src\\main\\java\\"+packageName+"\\"+serviceName+"Endpoint.java")), true);
		
		out.println("package "+packageName.replace("\\", ".")+";\n");
		out.println("import org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.ws.server.endpoint.annotation.Endpoint;\n"
				+ "import org.springframework.ws.server.endpoint.annotation.PayloadRoot;\n"
				+ "import org.springframework.ws.server.endpoint.annotation.RequestPayload;\nimport org.springframework.ws.server.endpoint.annotation.ResponsePayload;\n\n");
		
		out.println("import "+typesPackageName+".*;");
		
		
		String tns = 
				doc.getElementsByTagName("wsdl:definitions").item(0).getAttributes()
				.getNamedItem("xmlns:tns").getTextContent();
		
		
		out.println("@Endpoint\npublic class "+serviceName+"Endpoint {\n	private static final String TARGET_NAMESPACE = \""+tns+"\";");
		
		//TODO do class and interface generation
		out.println("}");
		
		
		
		
		System.out.println(packageName);
		
		
		
		
		
		
		
		
		System.out.println(outDir+"\\src\\main\\java\\"+packageName+"\\"+serviceName+".java");
		return res;
		
	}
	
	

}
