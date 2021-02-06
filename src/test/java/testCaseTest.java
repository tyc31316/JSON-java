import junit.framework.TestCase;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        File xml = new File("src/main/java/a.xml");
        FileReader reader = new FileReader(xml);

        Function<String, String> transform = (String key) -> ("swe262_" + key);

        JSONObject jo = Main2.toJSONObject(reader, transform);

        System.out.println(jo.toString(4));

    }
}