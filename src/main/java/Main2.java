import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Stack;

class Main2{

    public static boolean isNum(String str) {

        try {
            int val = Integer.parseInt(str);
        }catch(NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static JSONObject toJSONObject(Reader reader, JSONPointer path) {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(reader);

        // re-write parse, return a boolean value parsed
        // change all return false/true to set parsed = true/false

        while(x.more()) {
            x.skipPast("<");
            if(x.more()) {
                XML.parse(x, jo, null, XMLParserConfiguration.ORIGINAL);
            }
        }

        try {
            JSONObject query = (JSONObject) jo.query(path);
            return query;
        } catch (Exception ex) {
            jo = new JSONObject();
            return jo;
        }

    }


    public static JSONObject toJSONObject2(Reader reader, JSONPointer path) {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(reader);
        String paths = path.toString();

        Stack<String> stack = new Stack<String>();
        boolean[] found = new boolean[1];
        found[0] = false;
        while(x.more()) {
            x.skipPast("<");
            if(x.more()) {
                XML.parse3(x, jo, null, found, paths, stack, XMLParserConfiguration.ORIGINAL);
                // thought found should be returned as true but it is not???
                // original idea is that if found is true and stack size is 0,
                // then we don't need to go further even if there are more tags
                // currently still unable to do so
                System.out.println(found[0]);
                if(stack.size() == 0 && found[0]) {
                    break;
                }
            }
        }
        System.out.println(jo.toString(4));
        JSONObject result = (JSONObject) jo.query(paths);
        if(result != null) {
            return result;
        }
        else {
            return new JSONObject();
        }
    }

    public static void main(String[] args) {

        try {
            File xml = new File("simpleXML.xml");
            FileReader reader = new FileReader(xml);
            JSONPointer pointer = new JSONPointer("/note/to/random/0");

            JSONObject jo = toJSONObject(reader, pointer);



//            JSONObject jo = new JSONObject();
//            JSONObject garbage = new JSONObject();
//            XMLTokener x = new XMLTokener(reader);
//
//            ArrayList<String> numbers = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4",
//                                                        "5","6", "7", "8", "9"));
//
//            if(pointer.charAt(0) == '/') {
//                pointer = pointer.substring(1);
//            }
//            pointer = pointer.replace("/", " ");
//            String[] pathArr = pointer.split("\\s+");
//
//
//            if(pathArr.length == 0){							//trivial case
//                System.out.println(XML.toJSONObject(reader));
//                return;
//            }
//
//            boolean more = true;		//stop reading immediately when false
//            boolean found = false;		//found some tag in the path (could be the tags in the middle)
//            boolean finaltag = false;	//found the last tag in the path
//            boolean isArray = false;
//            int i = 0; 					//record the position in pathArr
//            String tag = pathArr[0];	//current tag in the pathArr
//            String curtag = "";			//tags that read from the xml file
//            Object token;				//token read from the xml file
//
//            ArrayList<String> tags = new ArrayList<String>();
//
//            while(x.more() && more) {
//
//                x.skipPast("<");
//
//                // find the token that IS a String
//                token = x.nextToken();
//                while(!(token instanceof String) && x.more()){
//                    x.skipPast("<");
//                    System.out.println(token = x.nextToken());
//                }
//
//                //check if the token match our tag in path
//                curtag = (String) token;
//                tags.add(curtag);
//                if(curtag.equals(tag)){			//the token is in our path
//                    found = true;
//                    if(i == pathArr.length-1) {
//                        finaltag = true;		//it is the last tag in our path
//                    }else {
//                        finaltag = false;		//it is one of the middle tags
//                        i++;
//                        tag = pathArr[i];
//                    }
//                }
//                else {
//                    if(numbers.contains(curtag)) {
//                        found = true;           // indicating arraylist
//                        isArray = true;
//                    }
//                    found = false;				//the token is not in our path, but what if it's a number?
//                }
//                int desiredIndx = 0;
//                int counter = 0;
//                if(isArray) {
//                    desiredIndx = Integer.parseInt(curtag);
//                    counter = 0;
//                }
//
//                while(x.more()) {
//
//                    if(found && finaltag){		//found the final tag in the path, process it!
//                        if(isArray) {
//                            x.skipPast("<");
//
//
////                            while(x.more()) {
////                                if (counter == desiredIndx) {
////                                    if(XML.parse(x, jo, curtag, XMLParserConfiguration.ORIGINAL)) {
////                                        break;
////                                    }
////                                } else {
////                                    if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
////                                        counter++;
////                                        x.skipPast("<");
////                                    }
////                                }
////                            }
//                        }
//                        else {
//                            more = false;
//                            x.skipPast("<");
//                            if (XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
//                                break;
//                            }
//                        }
//                    } else if(!found){			//unwanted tag, put the whole thing in garbage
//                        x.skipPast("<");
//
//                        if(XML.parse(x, garbage, curtag, XMLParserConfiguration.ORIGINAL)) {
//                            break;
//                        }
//                    } else {						//some middle tags, just do nothing and keep looking for the next tag
//                        break;
//                    }
//                }
//            }
//
//            while(x.more()) {
//                x.skipPast("<");
//                if(x.more()) {
//                    XML.parse(x, jo, null, XMLParserConfiguration.ORIGINAL);
//                    Object query = jo.query(pointer);
//                    System.out.println(query);
//                }
//            }
//            System.out.println(jo.toString(4));
//

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }




    }

}