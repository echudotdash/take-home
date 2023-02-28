package fun;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadData {

   @DataProvider(name="sendData")
    public static Object[] getData() throws IOException {
       JsonPath js=new JsonPath(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\test\\resources\\testData.json"))));

       int nameCount=js.getInt("name.size()");

       Object[] o=new Object[nameCount];
       for(int index=0;index<nameCount;index++){
           String name=js.get("name["+index+"]");
           o[index]=name;
       }

       return o;
   }
}
