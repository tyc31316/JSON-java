import org.json.JSONObject;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.*;

class Main{
	
	public static void main(String[] args) {
		
		String pointer = "/catalog";
		List<Integer> tagofarray = new ArrayList();
		File xml = new File("a.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			JSONObject jo = new JSONObject();
	        XMLTokener x = new XMLTokener(reader);
			if(pointer.charAt(0) == '/') {
				pointer = pointer.substring(1);
			}
			pointer = pointer.replace("/", " ");
			String[] pathArr = pointer.split("\\s+");
			
			if(pathArr.length == 0){							//trivial case
				System.out.println(XML.toJSONObject(reader));
				return;
			}
			
			for(int i = 1 ; i < pathArr.length ; i++) {
				if(isNum( pathArr[i]) ) {
					tagofarray.add(i-1);
				}
			}
			
			
	        boolean found = false;
	        boolean more = true; 
	        int i = 0; 
	        String tag = pathArr[0];

	        while(x.more()) {
				while (x.more()) {
					x.skipPast("<");
					if (x.more()) {
						if (x.nextToken().equals(tag)) {
							
							if(i == pathArr.length-1) {
								found = true;
								break;
							}else {
								i++;
								tag = pathArr[i];
							}
						}else if(found) {
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
							if(XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
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
		
		
		
		
	}
	
	public static boolean isNum(String str) {
		
		try {
			int val = Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	
}