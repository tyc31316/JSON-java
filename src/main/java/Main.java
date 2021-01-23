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
		
		String pointer = "/test/catalog/book";
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

			System.out.println(Arrays.toString(pathArr));
	        boolean found = false;
	        boolean first = false;
	        boolean more = true;
	        int i = 0; // record the position in pathArr
	        String tag = pathArr[0];

	        while(x.more()) {
				while (x.more()) {
					x.skipPast("<");
					if (x.more()) {
						//System.out.println(x.nextToken());
						if (x.nextToken().equals(tag)) {
							
							if(i == pathArr.length-1) {
								System.out.println(tag);
								found = true;
								break;
							}else {
								i++;
								tag = pathArr[i];
							}
						}else if(first) {
							more = false;
							break;
						}
					}
				}
				if(!more)
					break;
				while(x.more()) {
				if (found) {
					x.skipPast("<");
					if (x.more()) {
						//System.out.println(tag);
						if(XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
							//System.out.println(jo.toString(4));
							first = true;
							break;
						}
						
					}
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