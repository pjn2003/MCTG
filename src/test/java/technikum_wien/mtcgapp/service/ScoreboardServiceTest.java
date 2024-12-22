package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.ScoreboardControllerTestTest;

public class ScoreboardServiceTest implements Service {

    private final ScoreboardControllerTestTest controller;

    public ScoreboardServiceTest()
    {
        this.controller = new ScoreboardControllerTestTest();
    }

    @Override
    public Response handleRequest(Request request) {

        //Returns current leaderboard
        if (request.getMethod() == Method.GET)
        {
            return this.controller.getScoreboard();
        }
        return null;
    }
}
