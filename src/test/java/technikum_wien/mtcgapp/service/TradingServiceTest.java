package technikum_wien.mtcgapp.service;

import technikum_wien.httpserver.http.ContentType;
import technikum_wien.httpserver.http.HttpStatus;
import technikum_wien.httpserver.http.Method;
import technikum_wien.httpserver.server.Request;
import technikum_wien.httpserver.server.Response;
import technikum_wien.httpserver.server.Service;
import technikum_wien.mtcgapp.controller.TradingControllerTestTest;

public class TradingServiceTest implements Service {

    private final TradingControllerTestTest controller;

    public TradingServiceTest()
    {
        this.controller = new TradingControllerTestTest();
    }

    @Override
    public Response handleRequest(Request request) {

        //Get all available deals
        if (request.getMethod() == Method.GET)
        {
            return this.controller.getDeals();
        } //Delete a trade, Usage: /Integer tradeID
        else if (request.getMethod() == Method.DELETE && request.getPathParts().size()== 2)
        {
            return this.controller.removeDeal(Integer.parseInt(request.getPathParts().get(1)));
        } // Make a trade, Usage: /Integer tradeID
        else if (request.getMethod()== Method.POST && request.getPathParts().size()== 2)
        {
            return this.controller.makeTrade(Integer.parseInt(request.getPathParts().get(1)));
        } //Create a trade deal, Usage: /Integer cardID/String cType/Integer minDmg
        else if (request.getMethod()== Method.POST && request.getPathParts().size()== 4)
        {
            return this.controller.createDeal(
                    Integer.parseInt(request.getPathParts().get(1)),
                    request.getPathParts().get(2),
                    Integer.parseInt(request.getPathParts().get(3))
            );
        }


        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
