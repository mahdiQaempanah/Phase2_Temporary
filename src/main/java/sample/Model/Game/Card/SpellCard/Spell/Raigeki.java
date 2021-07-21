package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Game;

public class Raigeki implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){
        if(!wasActivated){
            for (int i = 0; i < 5; i++) {
                game.getInactivePlayer().getField().killMonsterCard(game.getInactivePlayer().getField().getMonsterZone()[i],game); 
            }
            wasActivated = true;
        }
    }

    public void activate(Game game,String CardName){}

    public void deactivate(Game game){}
}