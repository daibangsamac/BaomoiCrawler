package source;
import java.io.IOException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 


public class GsonParser {
	
	private Gson gson;
	
	public GsonParser() throws IOException {
		// TODO Auto-generated constructor stub	
		this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
	}
	
	public void writeOutput(Article article, FileReadWrite file) throws IOException {	
		String s;
		
		s = this.gson.toJson(article);
		s = s.replaceAll("\\\\u003c", "<");
		s = s.replaceAll("\\\\u003e", ">");
		s = s.replaceAll("\\\\u003d", "=");
		s = s.replaceAll("\\\\u0027", "'");
		s = s.replaceAll("\\\\u0026", "&");
		file.bufferWrite().write(s);
	}
}
