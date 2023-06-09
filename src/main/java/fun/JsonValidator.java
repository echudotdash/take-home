package fun;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;



public class JsonValidator {

    public boolean isValid(String jsonResponse) throws IOException, ProcessingException {
        // Create an ObjectMapper for converting jsonResponse into JsonNode
        ObjectMapper mapper = new ObjectMapper();

        // Load the JSON schema from your resources
        InputStream schemaStream = getClass().getResourceAsStream("/response_schema.json");
        JsonNode schemaNode = mapper.readTree(schemaStream);

        // Load the JSON response into a JsonNode
        JsonNode responseNode = mapper.readTree(jsonResponse);

        // Get a JsonSchema instance
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);

        // Validate the response against the schema and get a report
        ProcessingReport report = schema.validate(responseNode);

        return report.isSuccess();
    }
}