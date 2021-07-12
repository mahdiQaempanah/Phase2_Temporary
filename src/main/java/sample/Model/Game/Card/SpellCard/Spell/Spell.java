package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;

public interface Spell {
    public void activate(Game game);
    public void activate(Game game,String cardName);
    public void deactivate(Game game);
}
