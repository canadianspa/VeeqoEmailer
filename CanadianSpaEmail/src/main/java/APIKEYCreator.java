import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class APIKEYCreator {


	public class ApiJson
	{
		String veeqoApi;
		String sendGridApi;
		String twilioSid;
		String twilioToken;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {

		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("environment.json"));
		ApiJson data = gson.fromJson(reader, ApiJson.class);
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("./src/main/java/APIKEYS.java"), "utf-8"))) {
			writer.write("public class APIKEYS {\n" + 
					"	\n" + 
					"	public static String veeqoApi = \""+data.veeqoApi+"\" ;\n" + 
					"	public static String sendGridApi = \""+data.sendGridApi+"\" ;\n" + 
					"	public static String twilioSid = \""+data.twilioSid+"\" ;\n" + 
					"	public static String twilioToken = \""+data.twilioToken+"\" ;\n" + 
					"\n" + 
					"\n" + 
					"}");
		}

	}

}
