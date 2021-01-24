import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

class Main2{

//    public static JSONObject toJSONObject(Reader reader, JSONPointer path) {
//        JSONObject jo = new JSONObject();
//        XMLTokener x = new XMLTokener(reader);
//        String[] pathArr = path.toString().split("/");
//        ArrayList<String> numbers = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4",
//                                                                        "5","6", "7", "8", "9"));
//        int whereItIsArray = 0;
//        for(int i = 0; i < pathArr.length; i++) {
//            if(numbers.contains(pathArr[i])) {
//                whereItIsArray = i;
//                break;
//            }
//        }
//
//        while(x.more()) {
//            x.skipPast("<");
//            if(x.more()) {
//                XML.parse(x, jo, null, XMLParserConfiguration.ORIGINAL);
//            }
//        }
//        Object query = jo.query(path);
//        System.out.println(query);
//
//        return (JSONObject) query;
//    }

    public static void main(String[] args) {

        try {
            String pointer = "/test/catalog";
            File xml = new File("a.xml");
            FileReader reader = new FileReader(xml);
//            JSONPointer pointer = new JSONPointer("/test/catalog/book/2");

//            JSONObject jo = toJSONObject(reader, pointer);


            JSONObject jo = new JSONObject();
            JSONObject garbage = new JSONObject();
            XMLTokener x = new XMLTokener(reader);

            ArrayList<String> numbers = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4",
                                                        "5","6", "7", "8", "9"));

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
            boolean isArray = false;
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
                    System.out.println(token = x.nextToken());
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
                    if(numbers.contains(curtag)) {
                        found = true;           // indicating arraylist
                        isArray = true;
                    }
                    found = false;				//the token is not in our path, but what if it's a number?
                }
                int desiredIndx = 0;
                int counter = 0;
                if(isArray) {
                    desiredIndx = Integer.parseInt(curtag);
                    counter = 0;
                }

                while(x.more()) {

                    if(found && finaltag){		//found the final tag in the path, process it!
                        if(isArray) {
                            while(x.more()) {
                                if (counter == desiredIndx) {
                                    if(XML.parse(x, jo, curtag, XMLParserConfiguration.ORIGINAL)) {
                                        break;
                                    }
                                } else {
                                    if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
                                        counter++;
                                        x.skipPast("<");
                                    }
                                }
                            }
                        }
                        else {
                            more = false;
                            x.skipPast("<");
                            if (XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
                                break;
                            }
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

            while(x.more()) {
                x.skipPast("<");
                if(x.more()) {
                    XML.parse(x, jo, null, XMLParserConfiguration.ORIGINAL);
                    Object query = jo.query(pointer);
                    System.out.println(query);
                }
            }
            System.out.println(jo.toString(4));


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }




    }

}