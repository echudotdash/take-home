package utils;

import java.util.Arrays;

public class Constants {
 	public static final String BASE_URI = "http://localhost:8080";
 	public static final String BASE_PATH = BASE_URI + "/game";
	public static final String[] GAMES_LIST = new String[]{Arrays.toString(new String[]{"Catalan", "Blackjack", "Ludo"})};;
	public static final String DEFAULT_GAME = "Sudoku";
	public static final String SCHEMA_PATH = "src/test/java/utils/gameResponseSchema.json";

 }