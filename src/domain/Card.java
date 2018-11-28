package domain;

public class Card {
	
	private long id;
	private long deckId;
	private String type;
	private String name;
	
		
	public Card(long id, long deckId, String type, String name) {
		this.id = id;
		this.deckId = deckId;
		this.type = type;
		this.name = name;
	}
	
	public Card(long deckId, String type, String name) {
		this.deckId = deckId;
		this.type = type;
		this.name = name;
	}
	
	public Card(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Card(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public long getDeckId() {
		return deckId;
	}
	
	public void setDeckId(long deckId) {
		this.deckId = deckId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	

}
