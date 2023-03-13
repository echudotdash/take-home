@RestController
public class GameController {

    private static final String DEFAULT_GAME = "Sudoku";
    private static final String PLAYING_TEMPLATE = "Playing %s is fun!";
    private static final String ERROR_TEMPLATE = "Error: %s";
    private static final int DEFAULT_ID = 1;

    private int counter = 1;
    private Map<Integer, Integer> map = new HashMap<>();

    @GetMapping(value = "/game")
    public ResponseEntity<Game> getGame(@RequestParam(value="name", required=false) String name) {
        String gameName = name != null ? name : DEFAULT_GAME;
        int id = fib(counter++);
        String text = String.format(PLAYING_TEMPLATE, gameName);
        Game game = Game.builder().id(id).text(text).build();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    private int fib(int counter) {
        if (counter == 1 || counter == 2) {
            return 1;
        }
        if (map.containsKey(counter)) {
            return map.get(counter);
        }
        int value = fib(counter - 1) + fib(counter - 2);
        map.put(counter, value);
        return value;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Game> handleException(Exception ex) {
        String errorMessage = String.format(ERROR_TEMPLATE, ex.getMessage());
        Game game = Game.builder().id(DEFAULT_ID).text(errorMessage).build();
        return new ResponseEntity<>(game, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
