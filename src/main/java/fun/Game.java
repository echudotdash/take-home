package fun;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class Game {
	private Integer id;
    private String text;
}
