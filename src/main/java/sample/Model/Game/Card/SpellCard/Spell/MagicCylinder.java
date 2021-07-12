package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.*;
import sample.Model.Game.Card.MonsterCard.*;
import org.json.JSONObject;

public class MagicCylinder implements Spell{
    private boolean wasActivated = false;
    
    public void activate(Game game){
        if(!wasActivated){
            String string = game.getGameLog().get(game.getGameLog().size()-1);
            JSONObject log = new JSONObject(string);
            if(log.get("type").equals("ATTACK")){
                int code = log.getInt("mainCard");
                MonsterCard monsterCard = null;
                for (int i = 0; i < 5; i++) {
                    if(game.getActivePlayer().getField().getMonsterZone()[i].hashCode() == code) monsterCard = game.getActivePlayer().getField().getMonsterZone()[i];
                }
                monsterCard.setMonsterAttackInTurn(true);
                game.getActivePlayer().decreaseLp(monsterCard.getAtk());
            }
            wasActivated = true;
        }
    }

    public void activate(Game game,String CardName){}

    public void deactivate(Game game){}
}
