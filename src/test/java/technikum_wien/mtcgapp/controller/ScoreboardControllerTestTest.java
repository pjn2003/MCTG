package technikum_wien.mtcgapp.controller;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.server.Response;
import technikum_wien.mtcgapp.dummydata.UserDummyData;
import technikum_wien.mtcgapp.models.User;

import java.util.List;

public class ScoreboardControllerTestTest extends ControllerTest {
    private UserDummyData dummyData;
    public ScoreboardControllerTestTest()
    {
        this.dummyData = new UserDummyData(false);
    }


    public Response getScoreboard()
    {
        List<User> users = this.dummyData.getUsers();
        users.sort((a,b)->b.getElo().compareTo(a.getElo()));

        String result = "";
        Integer count=1;

        for (User user : users)
        {
            result += count +". " + user.getUsername() + " - ELO: " + user.getElo() + "\n";
            count++;
        }

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"message\" : \"Scoreboard:\"\n%s }".formatted(result)
        );

    }


}
