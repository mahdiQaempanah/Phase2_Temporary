package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Game;

public class HarpiesFeatherDuster implements Spell{
    boolean wasActivated = false;

    public void activate(Game game){
        if(!wasActivated){
            for (int i = 0; i < 5; i++) {
                game.getInactivePlayer().getField().killSpellCard(game.getInactivePlayer().getField().getSpellZone()[i]); 
            }
            wasActivated = true;
        }
    }

    public void activate(Game game,String CardName){}

    public void deactivate(Game game){}
}
