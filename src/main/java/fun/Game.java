package fun;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder @Setter
public class Game {
    private Integer id;
    private String text;

}
