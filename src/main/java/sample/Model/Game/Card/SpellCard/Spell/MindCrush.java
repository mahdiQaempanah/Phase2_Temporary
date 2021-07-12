package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.*;

public class MindCrush implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){}

    public void activate(Game game,String CardName){
        if(!wasActivated){
            boolean hasCard = false;
            for(Card card : game.getInactivePlayer().getHand()){
                if(card.getName().equals(CardName)){
                    game.getInactivePlayer().getHand().remove(card);
                    game.getInactivePlayer().getField().getGraveyard().add(card);
                    hasCard = true;
                }
            }
            if(!hasCard){
                game.getActivePlayer().getField().getGraveyard().add(game.getActivePlayer().getRandomCard());
            }
            wasActivated = true;
        }
    }

    public void deactivate(Game game){}
}
