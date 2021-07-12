package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Game;

public class AdvancedRitualArt implements Spell{
    private boolean wasActivated = false;
    
    public void activate(Game game) {
        if(!wasActivated){
            game.getActivePlayer().setCanRitualSummon(true);
            wasActivated = true;
        }
    }

    public void activate(Game game, String cardName) {}

    public void deactivate(Game game) {}
}
