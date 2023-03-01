package fun;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResponseData {

private Integer id;
private String text;
        @JsonProperty("id")
        public Integer getId() {
                return id;
        }
        @JsonProperty("id")
public void setId(Integer id) {
        this.id = id;
        }
        @JsonProperty("text")
public String getText() {
        return text;
        }
        @JsonProperty("text")
public void setText(String text) {
        this.text = text;
        }


        @Override
        public String toString() {
            return "Game{" +
                    "id=" + id +
                    ", text='" + text + '\'' +
                    '}';
        }

    }

