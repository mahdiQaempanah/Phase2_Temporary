package sample.Model.JsonObject;

import sample.Model.Game.Card.MonsterCard.MonsterCard;
import sample.Model.Game.Card.SpellCard.SpellCard;
import sample.Model.Game.Player;

import java.util.ArrayList;

public class FieldJson {
    private String nickName;
    private int life;
    private CardBoardInfo monsterZone[] = new CardBoardInfo[5];
    private CardBoardInfo spellZone[] = new CardBoardInfo[5];
    private ArrayList<CardBoardInfo> hand;
    private CardBoardInfo fieldZone;
    private int graveyardSize;
    private int handSize;
    private int deckSize;


    public FieldJson(Player player){
        this.deckSize = player.getCards().size();
        this.handSize = player.getHand().size();
        this.nickName = player.getNickname();
        this.life = player.getLp();

        for(int i = 0 ; i < 5 ; i++){
            MonsterCard monsterCard = player.getField().getMonsterZone()[i];
            if(monsterCard == null)
                continue;
            this.monsterZone[i] = new CardBoardInfo(monsterCard.getMode(),monsterCard.getStatus());
        }

        for(int i = 0 ; i < 5 ; i++){
            SpellCard spellCard = player.getField().getSpellZone()[i];
            if(spellCard == null)
                continue;
            this.spellZone[i] = new CardBoardInfo(null,spellCard.getStatus());
        }

        this.graveyardSize = player.getField().getGraveyard().size();

        if(player.getField().getFieldZone() != null)
            this.fieldZone = new CardBoardInfo(null,player.getField().getFieldZone().getStatus());
    }

    public String getNickName() {
        return nickName;
    }

    public int getLife() {
        return life;
    }

    public CardBoardInfo[] getMonsterZone() {
        return monsterZone;
    }

    public CardBoardInfo[] getSpellZone() {
        return spellZone;
    }

    public CardBoardInfo getFieldZone() {
        return fieldZone;
    }

    public int getGraveyardSize() {
        return graveyardSize;
    }

    public int getHandSize() {
        return handSize;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public ArrayList<CardBoardInfo> getHand() {
        return hand;
    }
}
