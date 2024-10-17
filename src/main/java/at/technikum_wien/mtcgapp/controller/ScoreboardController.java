package at.technikum_wien.mtcgapp.controller;

import at.technikum_wien.mtcgapp.dummydata.UserDummyData;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.mtcgapp.models.User;

import java.util.List;

public class ScoreboardController extends Controller {
    private UserDummyData dummyData;
    public ScoreboardController()
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
