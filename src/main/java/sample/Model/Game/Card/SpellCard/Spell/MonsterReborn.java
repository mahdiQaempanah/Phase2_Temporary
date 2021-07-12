package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.*;
import sample.Model.Game.Card.MonsterCard.*;

public class MonsterReborn implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){}

    public void activate(Game game,String cardName){
        if(!wasActivated){
            MonsterCard monsterCard = null;
            for(Card card : game.getActivePlayer().getField().getGraveyard()){
                if(card.getName().equals(cardName) && card.getCategory().equals(Category.MONSTER)) monsterCard = (MonsterCard)card;
            }
            game.getActivePlayer().getField().getGraveyard().remove(monsterCard);
            game.getActivePlayer().getField().addToMonsterZone(monsterCard);
            wasActivated = true;
        }
    }

    public void deactivate(Game game){}
}
