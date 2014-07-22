package generators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class EndpointGenerator {

	public Map<String, Object> generateEnpointClass(String outDir, String srcWsdl, String packageName, String servicePath, String serviceName, String typesPackageName, String nameSpace, List<Map<String, Object>> params)
			throws ParserConfigurationException, SAXException, IOException {
		Map<String, Object> res = new HashMap<String, Object>();

		String low = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);

		File outEndpointClass = new File(outDir + "\\src\\main\\java\\" + packageName);
		outEndpointClass.mkdirs();

		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outDir + "\\src\\main\\java\\" + packageName + "\\" + serviceName + "Endpoint.java")), true);

		out.println("package " + packageName.replace("\\", ".") + ";\n");
		out.println("import org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.ws.server.endpoint.annotation.Endpoint;\n" + "import org.springframework.ws.server.endpoint.annotation.PayloadRoot;\n"
				+ "import org.springframework.ws.server.endpoint.annotation.RequestPayload;\nimport org.springframework.ws.server.endpoint.annotation.ResponsePayload;\nimport " + servicePath.replace("\\", ".") + "." + "I" + serviceName
				+ ";\n");

		out.println("import " + typesPackageName + ".*;");

		out.println("\n@Endpoint\npublic class " + serviceName + "Endpoint {\n	private static final String TARGET_NAMESPACE = \"" + nameSpace + "\";\n");

		out.println("	private I" + serviceName + " i" + serviceName + ";\n");

		for (int i = 0; i < params.size(); i++) {
			String operationName = (String) params.get(i).get("OperationName");
			String input = (String) params.get(i).get("input");
			String output = (String) params.get(i).get("output");
			String lowInput = firstToLower(input);
			String lowOutput = firstToLower(output);
			out.println("	@PayloadRoot(localPart = \"" + lowInput + "\", namespace = TARGET_NAMESPACE)");
			out.println("	public @ResponsePayload");
			out.println("	" + output + " " + operationName + "(@RequestPayload " + input + " request) {");
			out.println("		" + output + " response = new " + output + "();");
			out.println("		" + "response =" + " i" + serviceName + "." + operationName + "(request);");
			out.println("		" + "return response;\n	}\n");
		}

		out.println("	@Autowired");
		out.println("	public void set" + firstToLower(serviceName) + "Endpoint(I" + serviceName + " p" + serviceName + ") {");
		out.println("		" + "this.i" + serviceName + " = " + "p" + serviceName + ";\n	}");

		out.println("}");

		System.out.println(packageName);

		System.out.println(outDir + "\\src\\main\\java\\" + packageName + "\\" + serviceName + ".java");
		return res;

	}

	String firstToLower(String b) {
		return b.substring(0, 1).toLowerCase() + b.substring(1);
	}
}
