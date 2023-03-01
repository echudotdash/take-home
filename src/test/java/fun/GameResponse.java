package fun;

public class GameResponse {
	 private int id;
	    private String text;

	    public int getId() {
	        return id;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    @Override
	    public String toString() {
	        return "GameResponse{" +
	                "id=" + id +
	                ", text='" + text + '\'' +
	                '}';
	    }

}
