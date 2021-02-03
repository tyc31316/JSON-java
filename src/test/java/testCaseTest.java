import junit.framework.TestCase;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class testCaseTest extends TestCase {


    @Test
    public void testCase() throws FileNotFoundException {
        File xml = new File("src/main/java/simpleXML.xml");
        FileReader reader = new FileReader(xml);
        JSONPointer pointer = new JSONPointer("/note/random/hello");

        JSONObject jo = Main2.toJSONObject2(reader, pointer);

//        System.out.println(jo.toString(4));

    }
}