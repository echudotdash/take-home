package fun;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunController {

    private static final String template = "Playing %s is fun!";
//    private int counter = 1;
//    private Map<String,Game> map = new HashMap<>();

    @GetMapping("/game")
    public DataClass greeting(@RequestParam(value="name", defaultValue="Sudoku") String name) {
    //    return Game.builder().id(fib(counter++)).text(String.format(template, name)).build();
    	return gameJson(name);
    }
    
    private DataClass gameJson(String gameName) {
    	System.out.println("gameName : " +gameName);
    	if(gameName.equals("Sudoku") || gameName.equals("sudoku")) {
    		DataClass sukoduGame= new DataClass();
    		sukoduGame.setId(1);
    		sukoduGame.setText("Sudoko Game");
    		return sukoduGame;
    	}  else if(gameName.equals("chess") || gameName.equals("Chess")) {
    		DataClass sukoduGame= new DataClass();
    		sukoduGame.setId(2);
    		sukoduGame.setText("Chess Game");
    		return sukoduGame;
    	} else {
    		DataClass sukoduGame= new DataClass();
    		sukoduGame.setId(3);
    		sukoduGame.setText("This Game not Found");
    		return sukoduGame;
    	}

    }
/*    
    private int fib(int counter) {
    		if(counter == 1 || counter == 2) {
    			return 1;
    		}
    		if(map.containsKey(counter)) {
    			return map.get(counter);
    		}
    		int value = fib(counter - 1) + fib(counter - 2);
    		map.put(counter, value);
    		return value;
    }
    */
}
