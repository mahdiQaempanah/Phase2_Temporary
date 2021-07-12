package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.*;
import sample.Model.Game.Card.SpellCard.*;

public class Terraforming implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){}

    public void activate(Game game,String cardName){
        if(!wasActivated){
            SpellCard spellCard = null;
            for(Card card : game.getActivePlayer().getMainDeck()){
                if(card.getName().equals(cardName) && card.getCategory().equals(Category.SPELL)) spellCard = (SpellCard)card;
            }
            game.getActivePlayer().getMainDeck().remove(spellCard);
            game.getActivePlayer().addToHand(spellCard);
            wasActivated = true;
        }
    }

    public void deactivate(Game game){}
}
