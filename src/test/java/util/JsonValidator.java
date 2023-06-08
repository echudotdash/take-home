package util;

import io.restassured.response.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;


import java.io.*;
import java.util.Set;

public class JsonValidator {

    // Create inputStreamFromResponse() method to extract the JSON data from the RestAssured response
    private static InputStream inputStreamFromResponse(Response response) {
        // Extract the response body as a string
        String responseBody = response.getBody().asString();
        // Convert the string to an InputStream
        return new ByteArrayInputStream(responseBody.getBytes());
    }

    private static InputStream inputStreamFromFile(String filePath) {
        try {
            File file = new File(filePath);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Set<ValidationMessage> validateResponseAgainstJsonSchema(Response response, String jsonSchemaFilePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance();
        InputStream jsonStream = inputStreamFromResponse(response);
        InputStream schemaStream = inputStreamFromFile(jsonSchemaFilePath);
        JsonNode json = objectMapper.readTree(jsonStream);
        JsonSchema schema = schemaFactory.getSchema(schemaStream);

        return schema.validate(json);
    }
}
