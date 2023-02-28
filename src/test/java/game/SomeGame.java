package game;

public class SomeGame {
    private int id;

    private String text;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String someString) {
        this.text = someString;
    }

    public SomeGame(){

    }
    public SomeGame(String text){
        setText(text);

    }

    public SomeGame(int id, String text){
        setId(id);
        setText(text);

    }

}
