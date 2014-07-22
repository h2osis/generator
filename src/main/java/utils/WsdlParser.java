package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WsdlParser {

	public static String getOperationName(String srcWsdl) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(srcWsdl));
		return (doc.getElementsByTagName("wsdl:definitions").item(0).getAttributes().getNamedItem("xmlns:tns").getTextContent());
	}

	public static List<Map<String, Object>> getOperations(String srcWsdl) throws ParserConfigurationException, SAXException, IOException {
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(srcWsdl));

		NodeList operations = doc.getElementsByTagName("wsdl:binding").item(0).getChildNodes();

		for (int i = 0; i < operations.getLength(); i++) {
			if (operations.item(i).getNodeName().contains("operation")) {

				NodeList types = operations.item(i).getChildNodes();

				Map<String, Object> operation = new HashMap<String, Object>();

				operation.put("OperationName", operations.item(i).getAttributes().getNamedItem("name").getTextContent());

				for (int j = 0; j < types.getLength(); j++) {

					if (types.item(j).getNodeName().contains("input")) {

						String type = types.item(j).getAttributes().getNamedItem("name").getTextContent();
						type = type.substring(0, 1).toUpperCase() + type.substring(1, type.length());

						operation.put("input", type);

					}

					if (types.item(j).getNodeName().contains("output")) {

						String type = types.item(j).getAttributes().getNamedItem("name").getTextContent();
						type = type.substring(0, 1).toUpperCase() + type.substring(1, type.length());

						operation.put("output", type);

					}

				}

				resList.add(operation);

			}
		}

		return resList;
	}
}
