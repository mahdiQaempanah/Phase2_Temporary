package sample.Model.Game.Card.SpellCard.Spell;

import sample.Model.Game.Card.MonsterCard.MonsterCard;
import org.json.JSONObject;
import sample.Model.Game.Game;

public class TrapHole implements Spell{
    private boolean wasActivated = false;

    public void activate(Game game){
        if(!wasActivated){
            String string = game.getGameLog().get(game.getGameLog().size()-1);
            JSONObject log = new JSONObject(string);
            if(log.get("type").equals("SUMMON_MONSTER") || log.get("type").equals("FLIP_SUMMON_MONSTER")){
                int code = log.getInt("mainCard");
                MonsterCard monsterCard = null;
                for (int i = 0; i < 5; i++) {
                    if(game.getActivePlayer().getField().getMonsterZone()[i].hashCode() == code) monsterCard = game.getActivePlayer().getField().getMonsterZone()[i];
                }
                if(monsterCard.getAtk()>=1000) game.getActivePlayer().getField().killMonsterCard(monsterCard, game);
            }
            wasActivated = true;
        }
    }
    public void activate(Game game,String CardName){}

    public void deactivate(Game game){}
}
