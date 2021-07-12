package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.MonsterCard.*;
import org.json.JSONObject;

public class MirrorForce implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){
        if(!wasActivated){
            String string = game.getGameLog().get(game.getGameLog().size()-1);
            JSONObject log = new JSONObject(string);
            if(log.get("type").equals("ATTACK")){
                for (int i = 0; i < 5; i++) {
                    if(game.getActivePlayer().getField().getMonsterZone()[i].getMode().equals(Mode.ATTACK)){
                        game.getActivePlayer().getField().killMonsterCard(game.getActivePlayer().getField().getMonsterZone()[i], game);
                    }
                }
            }
            wasActivated = true;
        }
    }

    public void activate(Game game,String CardName){}

    public void deactivate(Game game){}
}
