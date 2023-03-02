package Model;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
/**
 * Model class for use with an object mapper.
 * Allows the user to save responses from the /game endpoint
 */
public class Data {
    Integer id;
    String text;
}
