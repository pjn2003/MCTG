package at.technikum_wien.mtcgapp.models;

import lombok.Getter;
import lombok.Setter;

public class BattleResult {

    @Getter
    @Setter
    Integer winner;

    @Getter
    @Setter
    String resultString;
}
