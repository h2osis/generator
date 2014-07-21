package generators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ServiceImplGenerator {

	public Map<String, Object> generate(String outDir, String packageName,
			String serviceName, List<Map<String, Object>> operations,
			String typesPackageName) throws ParserConfigurationException,
			SAXException, IOException {
		Map<String, Object> res = new HashMap<String, Object>();

		File packageFolder = new File(outDir + "\\src\\main\\java\\"
				+ packageName);
		packageFolder.mkdirs();

		PrintWriter outI = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(outDir + "\\src\\main\\java\\"
						+ packageName + "\\I" + serviceName + ".java")), true);

		outI.println("package " + packageName.replace("\\", ".") + ";\n");
		outI.println("import " + typesPackageName + ".*;\n");

		outI.println("public interface I" + serviceName + " {\n\n");

		for (Map<String, Object> operation : operations) {

			outI.println("	public " + operation.get("output") + " "
					+ operation.get("OperationName") + "("
					+ operation.get("input") + " req);\n\n");

		}

		outI.println("}");

		PrintWriter outC = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(outDir + "\\src\\main\\java\\"
						+ packageName + "\\" + serviceName + ".java")), true);

		outC.println("package " + packageName.replace("\\", ".") + ";\n");
		outC.println("import org.springframework.stereotype.Service;\n\n");

		outC.println("import " + typesPackageName + ".*;\n");

		outC.println("@Service");
		outC.println("public class " + serviceName + " implements I"
				+ serviceName + " {\n\n");

		for (Map<String, Object> operation : operations) {

			outC.println("	@Override\n	public " + operation.get("output") + " "
					+ operation.get("OperationName") + "("
					+ operation.get("input") + " req){");
			outC.println("		"+operation.get("output") + " res = new "
					+ operation.get("output") + "();\n\n		return res;\n	}\n");

		}

		outC.println("}");

		return res;

	}

}
