import org.json.*;
import java.io.*;
//import java.util.*;

class Main{
	
	public static void main(String[] args) {
		
//		String pointer = "/note/to/random";
		File xml = new File("a.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			JSONObject jo = new JSONObject();
	        XMLTokener x = new XMLTokener(reader);
	        //System.out.println(x);
	        boolean found = false;

	        while(x.more()) {
				while (!found) {
					x.skipPast("<");
					if (x.more()) {
						//System.out.println(x.nextToken());
						if (x.nextToken().equals("Name")) {
							found = true;
						}
					}
				}
				if (found) {
					x.skipPast("<");
					if (x.more()) {
						if(XML.parse(x, jo, "Name", XMLParserConfiguration.ORIGINAL))
							break;
							
					}
						
					

				}
			}
	        System.out.println(jo.toString(4));
	        
			




		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
        //return jo;
	}
}