package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;

public class DarkHole implements Spell{
    private boolean wasActivated = false;
    
    public void activate(Game game){
        if(!wasActivated){
            for (int i = 0; i < 5; i++) {
                game.getPlayer1().getField().killMonsterCard(game.getPlayer1().getField().getMonsterZone()[i], game); 
                game.getPlayer2().getField().killMonsterCard(game.getPlayer2().getField().getMonsterZone()[i], game); 
            }
            wasActivated = true;
        }
    }
    public void activate(Game game,String CardName){}
    public void deactivate(Game game){}
}
