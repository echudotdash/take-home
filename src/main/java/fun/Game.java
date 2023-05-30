package fun;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Game {
    private Integer id;
    private String text;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}