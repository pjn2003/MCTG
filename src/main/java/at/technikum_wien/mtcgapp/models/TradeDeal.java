package at.technikum_wien.mtcgapp.models;

import lombok.Getter;

@Getter
public class TradeDeal {

    public enum CardType
    {
        Monster,
        Spell
    }

    @Getter
    private Integer dealId;
    @Getter
    private Card cardToTrade;
    @Getter
    private CardType cardType;
    @Getter
    private Integer minDamage;

    public TradeDeal(Integer dealId, Card cardToTrade, CardType cardType, Integer minDamage) {
        this.dealId = dealId;
        this.cardToTrade = cardToTrade;
        this.cardType = cardType;
        this.minDamage = minDamage;
    }



}
