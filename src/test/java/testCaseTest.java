import junit.framework.TestCase;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.XML;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Function;
import java.util.stream.Collectors;

public class testCaseTest extends TestCase {


//    @Test
//    public void testCase() throws FileNotFoundException {
//        File xml = new File("src/main/java/simpleXML.xml");
//        FileReader reader = new FileReader(xml);
//        JSONPointer pointer = new JSONPointer("/go/note/random/hello");
//
//        JSONObject jo = Main2.toJSONObject2(reader, pointer);
//
//        System.out.println(jo.toString(4));
//
//    }
//
//    @Test
//    public void testKeyTransform() throws FileNotFoundException {
//        File xml = new File("src/main/java/books_short.xml");
//        FileReader reader = new FileReader(xml);
//
//        Function<String, String> reverseString = str -> {
//
//            StringBuilder builder = new StringBuilder();
//            for(int i = str.length()-1; i >= 0 ; i--) {
//                builder.append(str.charAt(i));
//            }
//            return builder.toString();
//        };
//
//        JSONObject jo = Main2.toJSONObject(reader, reverseString);
//
//        System.out.println(jo.toString(4));
//
//    }

    @Test
    public void testToJSONObjectStream() throws Exception {

        File xml = new File("src/main/java/a.xml");
        FileReader aReader = new FileReader(xml);

        XML.toJSONObject(aReader).toStream()
                .filter(node -> node.toString().contains("book"))
                .collect(Collectors.toList())
                .forEach(e -> System.out.println(e.getValue() + "\n" + e.getKey().toString()));

//        List<Map.Entry<String, String>> authors = XML.toJSONObject(aReader).toStream()
//                .filter(node -> node.toString().contains("title"))
//                .collect(Collectors.toList());

    }
    
    @Test
    public void testToJSONObjectStream1() throws Exception {

        JSONObject jo = new JSONObject("{\"aaaa\":null}");

        jo.toStream()
                .filter(node -> node.toString().contains("title"))
//                .collect(Collectors.toList())
                .forEach(e -> System.out.println(e.getKey() + ":\n" + e.getValue()));

//        List<Map.Entry<String, String>> authors = XML.toJSONObject(aReader).toStream()
//                .filter(node -> node.toString().contains("title"))
//                .collect(Collectors.toList());

    }
}