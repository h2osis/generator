package main;

import generators.EndpointGenerator;
import generators.PomProcessor;
import generators.ServiceImplGenerator;
import generators.SpringWSImplGenerator;
import generators.TypesGenerator;
import generators.WsdlProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import utils.WsdlParser;

public class Main {

	public static void main(String[] args) {

		TypesGenerator gen = new TypesGenerator();
		SpringWSImplGenerator genSpring = new SpringWSImplGenerator();
		WsdlProcessor wsdlPr = new WsdlProcessor();
		PomProcessor pomPr = new PomProcessor();
		EndpointGenerator endP = new EndpointGenerator();
		ServiceImplGenerator implGen = new ServiceImplGenerator();

		String src = "C:/WebServiceFrame/xsd";
		String out = "C:/WebServiceFrame/service";
		String serviceName = "NatashaCallService";
		String wsdl = "C:/WebServiceFrame/wsdl/natashaCallService.wsdl";
		String typesPackage = "com.h2osis.types";
		List<Map<String, Object>> operations = new ArrayList<Map<String, Object>>();
		String nameSpace = new String();
		try {
			operations = WsdlParser.getOperations(wsdl);
			nameSpace = WsdlParser.getOperationName(wsdl);
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			gen.generate(src, out, typesPackage);
			genSpring.generate(out, serviceName, "com");
			wsdlPr.process(wsdl, out, serviceName);
			pomPr.process(out, serviceName, "banana.services", "natashaCall-0.0.1-SNAPSHOT"); //4th should be setted for addressing to the server
			endP.generateEnpointClass(out, wsdl, "com\\h2osis\\endpoints", "com\\h2osis\\service", serviceName, typesPackage, nameSpace, operations);
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
