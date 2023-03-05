package fun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Game {
	private Integer id;
	private String text;

}
