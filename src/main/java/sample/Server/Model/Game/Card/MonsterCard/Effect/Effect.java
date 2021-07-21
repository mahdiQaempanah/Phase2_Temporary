package sample.Model.Game.Card.MonsterCard.Effect;

import sample.Model.Game.*;

public interface Effect {
    public boolean isActivated(Game game);
    public void activate(Game game);
}
