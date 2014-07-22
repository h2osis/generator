package generators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;

public class TypesGenerator {

	public Map<String, Object> generate(String srcDir, String outDir, String packageName) throws IOException {
		Map<String, Object> res = new HashMap<String, Object>();

		outDir += "/src/main/java/";

		File folder = new File(srcDir);
		File[] listOfFiles = folder.listFiles();

		File out = new File(outDir);
		if (!out.exists()) {
			out.mkdirs();
		}

		for (File schemaFile : listOfFiles) {
			SchemaCompiler sc = XJC.createSchemaCompiler();

			// Setup SAX InputSource
			InputSource is = new InputSource(new FileInputStream(schemaFile));
			is.setSystemId(schemaFile.toURI().toString());
			sc.forcePackageName(packageName);

			// Parse & build
			sc.parseSchema(is);
			S2JJAXBModel model = sc.bind();
			JCodeModel jCodeModel = model.generateCode(null, null);
			jCodeModel.build(out);
		}

		return res;
	}

}
