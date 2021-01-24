import org.json.JSONObject;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class Main2{

    public static void main(String[] args) {

        String pointer = "/test/catalog/book";
        File xml = new File("a.xml");
        FileReader reader;
        try {
            reader = new FileReader(xml);
            JSONObject jo = new JSONObject();
            JSONObject garbage = new JSONObject();
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

            boolean more = true;		//stop reading immediately when false
            boolean found = false;		//found some tag in the path (could be the tags in the middle)
            boolean finaltag = false;	//found the last tag in the path
            int i = 0; 					//record the position in pathArr
            String tag = pathArr[0];	//current tag in the pathArr
            String curtag = "";			//tags that read from the xml file
            Object token;				//token read from the xml file

            while(x.more() && more) {

                x.skipPast("<");

                    // find the token that IS a String
                    token = x.nextToken();
                    while(!(token instanceof String) && x.more()){
                        x.skipPast("<");
                        token = x.nextToken();
                    }

                    //check if the token match our tag in path

                    curtag = (String) token;
                    if(curtag.equals(tag)){			//the token is in our path
                        found = true;
                        if(i == pathArr.length-1) {
                            finaltag = true;		//it is the last tag in our path
                        }else {
                            finaltag = false;		//it is one of the middle tags
                            i++;
                            tag = pathArr[i];
                        }
                    }
                    else {
                        found = false;				//the token is not in our path
                    }


                while(x.more()) {

                    if(found && finaltag){		//found the final tag in the path, process it!
                        more = false;
                        x.skipPast("<");
                        if(XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
                            break;
                        }
                    } else if(!found){			//unwanted tag, put the whole thing in garbage
                        x.skipPast("<");

                        if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
                            break;
                        }
                    } else {						//some middle tags, just do nothing and keep looking for the next tag
                        break;
                    }
                }
            }
            //System.out.println(garbage.toString(4));
            System.out.println(jo.toString(4));







        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }




    }

}