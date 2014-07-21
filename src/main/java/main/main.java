package main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import generators.SpringWSImplGenerator;
import generators.TypesGenerator;
import generators.pomProcessor;
import generators.wsdlProcessor;

public class main {

	public static void main(String[] args) {
		
		TypesGenerator gen = new TypesGenerator();
		SpringWSImplGenerator genSpring = new SpringWSImplGenerator();
		wsdlProcessor wsdlPr = new wsdlProcessor();
		pomProcessor pomPr = new pomProcessor();
		String src = "C:/WebServiceFrame/xsd";
		String out = "C:/WebServiceFrame/service";
		String serviceName = "NatashaCallService";
		
		
		
		
		try {
			gen.generate(src, out, "ServiceTypes");
			genSpring.generate(out,serviceName, "com.service");
			wsdlPr.process("C:/WebServiceFrame/wsdl/natashaCallService.wsdl", out, serviceName);
			pomPr.process(out, serviceName, "banana.services");
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
