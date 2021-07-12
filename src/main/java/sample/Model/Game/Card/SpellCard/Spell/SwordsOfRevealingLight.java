package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.Status;

public class SwordsOfRevealingLight implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){
        if(!wasActivated){
            boolean hasSet = false;
            for (int i = 0; i < 5; i++) {
                if(game.getInactivePlayer().getField().getMonsterZone()[i].getStatus().equals(Status.SET)) hasSet = true;
            }
            if(hasSet){
                for (int i = 0; i < 5; i++) {
                    game.getInactivePlayer().getField().getMonsterZone()[i].setStatus(Status.SET);
                }
            }
        }
    }

    public void activate(Game game,String cardName){}

    public void deactivate(Game game){}
}
