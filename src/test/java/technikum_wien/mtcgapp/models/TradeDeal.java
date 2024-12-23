package technikum_wien.mtcgapp.models;

import lombok.Getter;

@Getter
public class TradeDeal {



    @Getter
    private Integer dealId;
    @Getter
    private Integer cardToTrade;
    @Getter
    private String cardType;
    @Getter
    private Integer minDamage;
    @Getter
    private String username;

    public String printDeal()
    {
        String desc = "Deal " + dealId + " | Card offered: " + cardToTrade + ", Minimum Damage Wanted: " + minDamage + ", Looking for card of type: " + cardType + ", posted by "+username;
        System.out.println(desc);
        return desc;
    }

    public TradeDeal(Integer dealId, Integer cardToTrade, String cardType, Integer minDamage, String username) {
        this.dealId = dealId;
        this.cardToTrade = cardToTrade;
        this.cardType = cardType;
        this.minDamage = minDamage;
        this.username = username;
    }



}
