package fun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class GameController {

    private static final String template = "Playing %s is fun!";
    private int counter = 1;
    private Map<Integer,Integer> map = new HashMap<>();

    @RequestMapping("/game")
    public Game greeting(@RequestParam(value="name", defaultValue="Sudoku") String name) {
        log.info("********** NEW REQUEST **********");
        int fibNum = fib(counter++);
        log.info("ID: "+ fibNum +" Game: " + name );
        Game gameResponse = Game.builder()
                .id(fibNum)
                .text(String.format(template, name))
                .build();

        return gameResponse;
    }
    
    private int fib(int counter) {
    		if(counter == 1 || counter == 2) {
    			return 1;
    		}
    		if(map.containsKey(counter)) {
                int result = map.get(counter);
                log.info("Memoization used for fib of "+counter+" = "+result);
    			return result;
    		}
    		int value = fib(counter - 1) + fib(counter - 2);
    		map.put(counter, value);
    		return value;
    }
}
