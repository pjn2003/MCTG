package at.technikum_wien.controller;

import at.technikum_wien.dummydata.DummyCards;
import at.technikum_wien.httpserver.http.ContentType;
import at.technikum_wien.httpserver.http.HttpStatus;
import at.technikum_wien.httpserver.server.Response;
import at.technikum_wien.models.MonsterCard;
import at.technikum_wien.models.SpellCard;
import at.technikum_wien.models.TradeDeal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradingController extends Controller {

    List<TradeDeal> deals;
    DummyCards cards;
    ObjectMapper objectMapper;

    public TradingController()
    {
        deals = new ArrayList<>();
        cards = new DummyCards();
        objectMapper = new ObjectMapper();
        //Dummy data
        deals.add(new TradeDeal(1, cards.getCard(2), TradeDeal.CardType.Monster,20 ));
        deals.add(new TradeDeal(2, cards.getCard(1), TradeDeal.CardType.Monster,4 ));
        deals.add(new TradeDeal(3, cards.getCard(5), TradeDeal.CardType.Spell,10 ));

    }

    public Response removeDeal(Integer dealId)
    {
        for (TradeDeal deal : deals) {
            if (Objects.equals(deal.getDealId(), dealId)) {
                deals.remove(deal);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "Deal deleted!"
                );
            }

        }

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "Deal not found!"
        );
    }


    public Response makeTrade(Integer dealId)
    {
        for (TradeDeal deal : deals) {
            if (Objects.equals(deal.getDealId(), dealId)) {
                deals.remove(deal);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"Traded!\" }"

                );
            }

        }

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "Deal not found!"
        );

    }

    public Response createDeal(Integer cardId,String cType,Integer minDmg)
    {
        if (cards.getCard(cardId)!=null)
        {

            TradeDeal.CardType carType = TradeDeal.CardType.Monster;
            if (cType.equals("Spell"))
                carType = TradeDeal.CardType.Spell;

            Integer highestId =0;
            for (TradeDeal deal : deals) {
                if (deal.getDealId() > highestId)
                    highestId = deal.getDealId();
            }

            TradeDeal td = new TradeDeal(highestId+1,cards.getCard(cardId),carType, minDmg);
            deals.add(td);
            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    "{ \"message\" : \"Deal created!\" }"
            );

        }

        return new Response(
                HttpStatus.NOT_FOUND, ContentType.JSON, "You can't trade a card that doesn't exist!"
        );


    }

    public Response getDeals()
    {
        try {
            String json = "";
            for (TradeDeal deal : deals) {
                json += objectMapper.writeValueAsString(deal);
            }
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    json

            );

        }
        catch (JsonProcessingException e) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    e.getMessage()
            );
        }
    }



}
