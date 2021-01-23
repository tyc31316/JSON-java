import org.json.JSONObject;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
//import java.util.*;

class Main{
	
	public static void main(String[] args) {
		
		String pointer = "/catalog/book";
		File xml = new File("a.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			JSONObject jo = new JSONObject();
	        XMLTokener x = new XMLTokener(reader);
	        //System.out.println(x);

			if(pointer.charAt(0) == '/') {
				pointer = pointer.substring(1);
			}
			pointer = pointer.replace("/", "//s");
			String[] pathArr = pointer.split("//s+");
//
//			for(int i = 0; i < pathArr.length; i++) {
//				System.out.println(pathArr[i]);
//			}

			//System.out.println(Arrays.toString(pathArr));
	        boolean found = false;
	        int i = 0; // record the position in pathArr
	        String tag = pathArr[0];

	        while(x.more()) {
				while (!found) {
					x.skipPast("<");
					if (x.more()) {
						if (x.nextToken().equals(tag)) {
							
							if(i == pathArr.length-1) {
								found = true;
							}else {
								i++;
								tag = pathArr[i];
							}
							//found = true;
								
						}
					}
				}
				
				if (found) {
					
					x.skipPast("<");
					
					if (x.more()) {
						
						System.out.println(tag);
						//System.out.println("!"+x.nextToken());
						//x.nextToken();
						
						if(XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) 
							break;
						//System.out.println(XML.parse(x, jo, "to", XMLParserConfiguration.ORIGINAL));
						//System.out.println(jo.toString(4));
							
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