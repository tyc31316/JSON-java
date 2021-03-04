

import org.json.XML;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

class Main{
	

	
	public static void main(String[] args) {
		
		
		File xml = new File("src/main/java/SwissProt.xml");
		FileReader reader;
		try {
			reader = new FileReader(xml);
			Future<JSONObject> future = XML.toFutureJSONObject(reader);
			
			
			while( !future.isDone()) {
				System.out.println("Still reading...");
				Thread.sleep(1000);
			}
			JSONObject jo = future.get();
			File output = new File("src/main/java/result.json");
			FileWriter writer = new FileWriter(output);
			jo.write(writer);
			writer.flush();
			
			System.out.println("Finished writing!");
			
		}catch(FileNotFoundException e) {}
		catch(InterruptedException e){}
		catch(ExecutionException e){
			
		}catch(IOException e) {}
		
		
	}
}