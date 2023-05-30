import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

public class SchemaValidation {
    public static void main(String[] args) throws IOException, ProcessingException {
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
