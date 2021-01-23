import org.json.*;
import java.io.*;
//import java.util.*;

class Main{
	
	public static void main(String[] args) {
		
		String pointer = "/catalog";
		File xml = new File("a.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			JSONObject jo = new JSONObject();
	        XMLTokener x = new XMLTokener(reader);
	        System.out.println(x);
	        boolean found = false;
	        
	        while (x.more()) {
	            x.skipPast("<");
	            if(x.more()) {
	            	//System.out.println(x.nextToken());
	            	if(x.nextToken().equals("Active")) {
	            		found = true;
	            	}
	            	if(found) {
	            		x.skipPast("<");
	            		if(x.more())
	            			XML.parse(x, jo, "Active", XMLParserConfiguration.ORIGINAL);
	            	}
	            	
	            	
	            	//if(found && x.more())
	            		
	                
	            }
	        }
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
        //return jo;
	}
}