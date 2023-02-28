package utils;

public class Data {
	public static final String GAME_ONE = "pubg";
	public static final String GAME_TWO = "cricket";

	public static String formattedText(String game) {
		return String.format("Playing %s is fun!", game);
	}
	
}