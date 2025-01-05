package at.technikum_wien.mtcgapp.service;

import at.technikum_wien.httpserver.http.Method;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;
import at.technikum_wien.mtcgapp.controller.LotteryController;
import at.technikum_wien.mtcgapp.controller.ScoreboardController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LotteryService implements Service {

    private final LotteryController controller;

    public LotteryService()
    {
        this.controller = new LotteryController();
    }

    @Override
    public Response handleRequest(Request request) {

        try {
            String json = request.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode n = mapper.readTree(json);

            if (request.getMethod() == Method.POST) {
                return this.controller.playLottery(n.get("Username").asText());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
