package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.mtcgapp.controller.ScoreboardController;
import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class ScoreboardService implements Service {

    private final ScoreboardController controller;

    public ScoreboardService()
    {
        this.controller = new ScoreboardController();
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
