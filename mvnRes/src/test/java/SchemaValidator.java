import java.io.IOException;

public class SchemaValidator {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        JsonSchema schema = schemaFactory.getJsonSchema("file://path/to/schema.json");

        GameResponse response = objectMapper.readValue(jsonString, GameResponse.class);
        ProcessingReport report = schema.validate(objectMapper.readTree(jsonString));

        if (report.isSuccess()) {
            System.out.println("Response is valid against the schema.");
        } else {
            System.out.println("Response is not valid against the schema.");
            System.out.println(report);
        }
    }
}
