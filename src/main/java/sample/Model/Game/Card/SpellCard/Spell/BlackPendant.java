package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Card.MonsterCard.*;
import sample.Model.Game.Game;

public class BlackPendant implements Spell{
    private boolean wasActivated = false;
    private MonsterCard bindedCard;

    public void activate(Game game){}

    public void activate(Game game,String cardName){
        if(!wasActivated){
            for(MonsterCard monsterCard : game.getActivePlayer().getField().getMonsterZone()){
                if(monsterCard.getName().equals(cardName)) bindedCard = monsterCard;
            }
            bindedCard.increaseAtk(500);
            wasActivated = true;
        }
    }
    
    public void deactivate(Game game){
        bindedCard.decreaseAtk(500);
        game.getActivePlayer().decreaseLp(500);
    }
}
