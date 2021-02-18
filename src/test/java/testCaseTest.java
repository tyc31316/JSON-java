import junit.framework.TestCase;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.Test;

import java.io.*;
import java.util.function.Function;

public class testCaseTest extends TestCase {


    @Test
    public void testCase() throws FileNotFoundException {
        File xml = new File("src/main/java/simpleXML.xml");
        FileReader reader = new FileReader(xml);
        JSONPointer pointer = new JSONPointer("/go/note/random/hello");

        JSONObject jo = Main2.toJSONObject2(reader, pointer);

        System.out.println(jo.toString(4));

    }

    @Test
    public void testKeyTransform() throws FileNotFoundException {
        File xml = new File("src/main/java/books_short.xml");
        FileReader reader = new FileReader(xml);

        Function<String, String> reverseString = str -> {

            StringBuilder builder = new StringBuilder();
            for(int i = str.length()-1; i >= 0 ; i--) {
                builder.append(str.charAt(i));
            }
            return builder.toString();
        };

        JSONObject jo = Main2.toJSONObject(reader, reverseString);

        System.out.println(jo.toString(4));

    }

    @Test
    public void testToJSONObjectStream() throws FileNotFoundException {

        File xml = new File("src/main/java/books_short.xml");
        FileReader aReader = new FileReader(xml);
        // need to give a file name
        Writer writer = new PrintWriter("");


        JSONObject jo = Main2.toJSONObjectStream(aReader).write(writer);
    }
}