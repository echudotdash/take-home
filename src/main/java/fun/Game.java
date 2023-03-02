package fun;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Game {
    private static int counter = 1;
    private int id;
    private String text;

    public Game(String text) {
        this.id = counter++;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
