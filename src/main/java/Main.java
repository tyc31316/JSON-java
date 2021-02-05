

import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.util.function.Function;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

class Main{
	
	
	public static JSONObject toJSONObject(Reader reader, Function<String,String> func) {
		
		
		JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(reader);

        while(x.more()) {
            x.skipPast("<");
            if(x.more()) {
            	XML.parsetransformkey(x, jo, null, XMLParserConfiguration.ORIGINAL, func);
            }
        }

        System.out.println(jo.toString(4));
		
		
		return jo;
	}
	
	public static void main(String[] args) {
		
		
		Function<String, String> reverseString = new Function<String, String>() {
			
			
			public String apply(String str) {
				
				StringBuilder builder = new StringBuilder();
				for(int i = str.length()-1; i >= 0 ; i--) {
					builder.append(str.charAt(i));
				}
				return builder.toString();
			}
		};
		
		Function<String, String> appendstring = new Function<String, String>() {
			
			
			public String apply(String str) {
				
				return "swe262_"+str;
			}
		};
		
		
		File xml = new File("a.xml");
        FileReader reader;
		try {
			reader = new FileReader(xml);
			//toJSONObject(reader, reverseString);
			toJSONObject(reader, appendstring);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}