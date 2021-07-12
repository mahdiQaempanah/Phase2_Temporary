package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;

public class PotOfGreed implements Spell{
    private boolean wasActivated = false;
    
    public void activate(Game game){
        if(!wasActivated){
            game.getActivePlayer().draw();
            game.getActivePlayer().draw();
            wasActivated = true;
        }
    }

    public void activate(Game game,String cardName){}

    public void deactivate(Game game){}
}
