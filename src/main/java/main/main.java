package main;

import generators.ServiceImplGenerator;
import generators.SpringWSImplGenerator;
import generators.TypesGenerator;
import generators.endpointGenerator;
import generators.pomProcessor;
import generators.wsdlProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import utils.WsdlParser;

public class main {

	public static void main(String[] args) {

		TypesGenerator gen = new TypesGenerator();
		SpringWSImplGenerator genSpring = new SpringWSImplGenerator();
		wsdlProcessor wsdlPr = new wsdlProcessor();
		pomProcessor pomPr = new pomProcessor();
		endpointGenerator endP = new endpointGenerator();
		ServiceImplGenerator implGen = new ServiceImplGenerator();

		String src = "C:/WebServiceFrame/xsd"; // path to xsd
		String out = "C:/WebServiceFrame/service"; // path to output service
		String serviceName = "NatashaCallService"; // service name
		String wsdl = "C:/WebServiceFrame/wsdl/natashaCallService.wsdl"; // path
																			// to
																			// wsdl
		String typesPackage = "ServiceTypes";
		List<Map<String, Object>> operations = new ArrayList<Map<String, Object>>();

		try {
			operations = WsdlParser.getOperations(wsdl);
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			gen.generate(src, out, typesPackage);
			genSpring.generate(out, serviceName, "com.service");
			wsdlPr.process(wsdl, out, serviceName);
			pomPr.process(out, serviceName, "banana.services");
			endP.generateEnpointClass(out, wsdl, "com\\h2osis\\endpoints", serviceName, typesPackage);
			implGen.generate(out, "com\\h2osis\\service", serviceName, operations, typesPackage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
