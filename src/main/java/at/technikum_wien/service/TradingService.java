package at.technikum_wien.service;

import at.technikum_wien.controller.DeckController;
import at.technikum_wien.controller.TradingController;
import at.technikum_wien.httpserver.server.Request;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.httpserver.server.Service;

public class TradingService implements Service {

    private final TradingController controller;

    public TradingService()
    {
        this.controller = new TradingController();
    }

    @Override
    public Response handleRequest(Request request) {


        return null;
    }

}
