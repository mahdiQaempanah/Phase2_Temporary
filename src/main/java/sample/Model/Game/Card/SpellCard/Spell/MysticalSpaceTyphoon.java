package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Game;

public class MysticalSpaceTyphoon implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game) {}

    public void activate(Game game, String cardName) {
        if(!wasActivated){
            for (int i = 0; i < 5; i++) {
                if(game.getInactivePlayer().getField().getMonsterZone()[i].getName().equals(cardName)) game.getInactivePlayer().getField().killMonsterCard(game.getInactivePlayer().getField().getMonsterZone()[i], game);
                if(game.getInactivePlayer().getField().getSpellZone()[i].getName().equals(cardName)) game.getInactivePlayer().getField().killSpellCard(game.getInactivePlayer().getField().getSpellZone()[i]);
            }
            wasActivated = true;
        }
    }

    public void deactivate(Game game){}
}
