package fun;


public class GameObject {
	private Integer id;
    private String text;
    
    
    public GameObject() {
		super();
	}
    
    
    public GameObject(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
    
    
	public Integer getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
}
