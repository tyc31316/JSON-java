import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.util.*;

class Main2{

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
            pointer = pointer.replace("/", " ");
            String[] pathArr = pointer.split("\\s+");

            if(pathArr.length == 0){							//trivial case
                System.out.println(XML.toJSONObject(reader));
                return;
            }

            //System.out.println(Arrays.toString(pathArr));
            boolean found = false;
            boolean more = true;
            boolean isArray = false;
            JSONArray listObj = new JSONArray();
            int i = 0; // record the position in pathArr
            String tag = pathArr[0];
            String prevTag = "";

            while(x.more()) {
                while (x.more()) {
                    x.skipPast("<");
                    //System.out.println("where");
                    //System.out.println(x.nextToken());
                    if (x.nextToken().equals(tag)) {
                        // if prevTag == tag, then it is a jsonarray
                        // then we accumulate the found object
                        System.out.println(tag);
                        if(i == pathArr.length-1) {
                            if(tag.equals(prevTag)) {
                                isArray = true;
                            }
                            prevTag = tag;
                            found = true;
                            break;
                        }
                        else {
                            prevTag = tag;
                            i++;
                            tag = pathArr[i];
                        }
                    } else if(found) {
                        more = false;
                        break;
                    }

                }

                if(!more)
                    break;

                while(x.more()) {
                    if (found) {
                        x.skipPast("<");
                        if(isArray) {
                            JSONObject jo2 = new JSONObject();
                            if(XML.parse(x, jo2, tag, XMLParserConfiguration.ORIGINAL)) {
                                listObj.put(jo);
                                break;
                            }
                        }
                        else {
                            if (XML.parse(x, jo, tag, XMLParserConfiguration.ORIGINAL)) {
                                break;
                            }
                        }
                    }
                }
            }

            if(isArray) {
                System.out.println(listObj.toString(4));
            }
            else {
                System.out.println(jo.toString(4));
            }




        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }
}