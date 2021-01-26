import org.json.JSONObject;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class Main3{
	
	public static void main(String[] args) {
		
		String pointer = "/note/random";
		File xml = new File("simpleXML.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			JSONObject jo = new JSONObject();
			JSONObject garbage = new JSONObject();
	        XMLTokener x = new XMLTokener(reader);
	        if(pointer.length() == 0) {
	        	System.out.println(jo.toString());
	        	return;
	        }
			if(pointer.charAt(0) == '/') {
				pointer = pointer.substring(1);
			}
			pointer = pointer.replace("/", " ");
			String[] pathArr = pointer.split("\\s+");

			
			if(pathArr.length == 0){							//trivial case
				System.out.println(XML.toJSONObject(reader));
				return;
			}
			int index = -1;				//record the index of JSONArray in the path
			int count = 0;				//count to see if index is reached or not
			boolean isarray = false;	// true for an array false for an object
			boolean more = true;		//stop reading immediately when false
	        boolean found = false;		//found some tag in the path (could be the tags in the middle)
	        boolean finaltag = false;	//found the last tag in the path
	        int i = 0; 					//record the position in pathArr
	        String tag = pathArr[0];	//current tag in the pathArr
	        String curtag = "";			//current tag that is read from the xml file
	        Object token;				//token read from the xml file

	        while(x.more() && more) {
	        	
	        		/*************************************************************************
	        		 * read through xml file and find a valid tag (which should be a String) *
	        		 *************************************************************************/

	        		x.skipPast("<");

					if (x.more()) {

						// find the token that IS a String
						token = x.nextToken();
						while(!(token instanceof String) && x.more()){
							x.skipPast("<");
							if(x.more())
								token = x.nextToken();
						}

						//check if the token match our tag in path
	                    if(x.more()) {

	                    	curtag = (String) token;

	                    	if(curtag.equals(tag)){					//the token is in our path
		                        found = true;
		                        if(i == pathArr.length-1) {					//it is the last tag in our path (hence should not be an array)
		                        	isarray = false;
		                            finaltag = true;
		                        }else if( isNum( pathArr[i+1] ) ) {			//the tag is for an JSON array
		                        	index = Integer.parseInt(pathArr[i+1]);		//the target index
		                        	isarray = true;
		                        	if( i == pathArr.length-2 ){				//this is the last tag for an JSON Array
		                        		finaltag = true;
		                        	}
		                        	else{										//this is a middle tag for JSON Array
		                        		
		                        		finaltag = false;
		                        		//i+=2;
		                        		//tag = pathArr[i];
		                        	}
		                        }
		                        else {										//it is one of the middle tag for JSON Object
		                        	isarray = false;
		                            finaltag = false;
		                            i++;
		                            tag = pathArr[i];
		                        }
		                    }else{									//the token is not in our path
		                        found = false;
		                        isarray = false;
		                    }
	                    }
					}

					/**************************************************************
					 * Given current token, extract the object if tag is matched, *
					 * otherwise throw the whole object into garbage 			  *
					 **************************************************************/


					if(!isarray) {			//this is a tag for JSONObject
						//System.out.println("1 "+curtag);
						if(found && finaltag){		//found the final tag in the path, process it!
							more = false;
							while(x.more()) {
		                        x.skipPast("<");
								if (x.more()) {
									if(XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
										break;
									}
								}
							}
	                    }else if(!found){			//unwanted tag, put the whole thing in garbage
	                        while(x.more()) {
		                    	x.skipPast("<");
		                        if (x.more()) {

									if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
										break;
									}
								}
	                        }
	                    }else{}						//some middle tags, just do nothing and keep looking for the next tag


					}else {					// this is a tag for JSONArray
						//System.out.println(curtag);
						if(count == index && finaltag) {	//found the index, and this is the last tag in pathArr

							more = false;
							while(x.more()) {
								x.skipPast("<");
								if (x.more()) {
									if(XML.parse(x, jo, curtag, XMLParserConfiguration.ORIGINAL)) {
										break;
									}
								}
							}
						}else if(count < index) {			//not the correct index, put the whole thing in garbage

							count++;
							while(x.more()) {
								x.skipPast("<");
		                        if (x.more()) {

									if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
										break;
									}
								}
							}
						}else if(count > index) {			//this only happens when index < 0 (illegal index!!!)
							more = false;					//stop reading directly
						}
						else { 								//correct index but not final tag, reset the count
							count = 0;						// and keep looking for next tag
							i +=2;
							tag = pathArr[i]; 
						}					
															

					}

			}
	        //System.out.println(garbage.toString(4));
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