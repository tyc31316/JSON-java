import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;
//import org.json.junit.Util;
//import org.json.junit.XMLTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.*;
import java.util.Scanner;

class Main{
	
public static JSONObject toJSONObject(Reader reader, JSONPointer path, JSONObject replacement) {
    	
    	JSONObject jo = XML.toJSONObject(reader);
    	String pointer = path.toString();
    	
    	if(pointer.charAt(0) == '/') {
			pointer = pointer.substring(1);
		}
		pointer = pointer.replace("/", " ");
		String[] pathArr = pointer.split("\\s+");
		//System.out.println(pathArr.length);
		if( pathArr.length == 0)
			return jo;
    	
		JSONObject tempob = jo; 			//temporary object for iteration
        JSONArray tempar = new JSONArray(); //temporary array for iteration
        boolean type = true; 				//true for object false for array
        int n = pathArr.length;
        for(int i = 0 ; i < n-1 ; i++){
        	System.out.println(i);
            if(type){
                if(tempob.opt(pathArr[i]) instanceof JSONObject){
                    tempob = (JSONObject)tempob.opt(pathArr[i]);
                }else if(tempob.opt(pathArr[i]) instanceof JSONArray){
                    tempar =(JSONArray) tempob.opt(pathArr[i]);
                    type = false;
                }else{
                	
                    return jo;
                }
            }else{
                if(tempar.opt(Integer.parseInt(pathArr[i])) instanceof JSONObject){
                    tempob = (JSONObject) tempar.opt(Integer.parseInt(pathArr[i]));
                    type = true;
                }else if(tempar.opt(Integer.parseInt(pathArr[i])) instanceof JSONArray){
                    tempar = (JSONArray) tempar.opt(Integer.parseInt(pathArr[i]));
                }else{
                    
                    return jo;
                }
            }
        }
        if(type){
        	//System.out.println("!");
        	System.out.println(pathArr[n-1]);
        	System.out.println(tempob.toString(4));
            if(tempob.opt(pathArr[n-1]) != null) {
            	//System.out.println("!");
                tempob.put(pathArr[n-1],replacement);
            }
        }
        else{
            if(tempar.opt(Integer.parseInt(pathArr[n-1])) != null)
                tempar.put(Integer.parseInt(pathArr[n-1]),replacement);
        }
		
    	return jo;
    }
	
	public static void main(String[] args) {
		
		
        //System.out.println(actual);
        //InputStream jsonStream = null;
        try {
        	File xml = new File("a.xml");
    		FileReader xmlReader = new FileReader (xml);
    		JSONObject actual = toJSONObject(xmlReader, new JSONPointer("/test/catalog"), new JSONObject("{\"a\":\"b\"}"));
    		//System.out.println(actual.toString(4));
    		File json = new File("a_replace1.json");
            Scanner jsonReader = new Scanner (json);
            StringBuilder builder = new StringBuilder();
            while(jsonReader.hasNext()) {
            	builder.append(jsonReader.nextLine());
            }
            //JSONObject ex
            final JSONObject expected = new JSONObject(builder.toString());
            //System.out.println(expected);
            //System.out.println(actual);
            //AssertEquals(actual,expected);
            //Util.compareActualVsExpectedJsonObjects(actual,expected);
        } catch(FileNotFoundException e) {}
		
		/*
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
		return true;*/
	}
	
	
	
}