package sample.Model.Game;

import sample.Model.Game.Card.*;
import sample.Model.Game.Card.MonsterCard.*;
import sample.Model.Game.Card.SpellCard.*;

import java.util.ArrayList;

public class Field {
    private MonsterCard monsterZone[] = new MonsterCard[5];
    private SpellCard spellZone[] = new SpellCard[5];
    private ArrayList<Card> graveyard = new ArrayList<>();
    private SpellCard fieldZone;

    public MonsterCard[] getMonsterZone() {
        return monsterZone;
    }

    public SpellCard[] getSpellZone() {
        return spellZone;
    }

    public void setFieldZone(SpellCard fieldZone) {
        this.fieldZone = fieldZone;
    }

    public SpellCard getFieldZone(){
        return this.fieldZone;
    }

    public ArrayList<Card> getGraveyard(){
        return graveyard;
    }

    public void addToMonsterZone(MonsterCard monsterCard){
        for (int index = 0; index < 5; index++) {
            if(monsterZone[index]==null) monsterZone[index] = monsterCard;
        }
    }

    public void addToSpellZone(SpellCard spellCard){
        for (int index = 0; index < 5; index++) {
            if(spellZone[index]==null) spellZone[index] = spellCard;
        } 
    }

    public void addToGraveyard(Card card){
        graveyard.add(card);
    }

    public void removeFromMonsterZone(MonsterCard monsterCard){
        for (int index = 0; index < 5; index++) {
            if(monsterZone[index]==monsterCard) monsterZone[index] = null;
        }
    }

    public void removeFromSpellZone(SpellCard spellCard){
        for (int index = 0; index < 5; index++) {
            if(spellZone[index]==spellCard) spellZone[index] = null;
        }
    }

    public void emptyFieldZone(){
        setFieldZone(null);
    }
    
    public void killMonsterCard(MonsterCard monsterCard, Game game){
        removeFromMonsterZone(monsterCard);
        addToGraveyard(monsterCard);
        game.addToGameLog(GameLogType.DEATH_MONSTER,monsterCard.hashCode());
    }

    public void killSpellCard(SpellCard spellCard){
        removeFromSpellZone(spellCard);
        addToGraveyard(spellCard);
    }

    public void killFieldCard(SpellCard spellCard){
        emptyFieldZone();
        addToGraveyard(spellCard);
    }

    public boolean isMonsterZoneContains(Object object){
        for(int i = 0 ; i < monsterZone.length ; i++){
            if(monsterZone[i] == object)
                return true;
        }
        return false;
    }

    public boolean isSpellZoneContains(Object object){
        for(int i = 0 ; i < spellZone.length ; i++){
            if(spellZone[i] == object)
                return true;
        }
        return false;
    }

    public int getCntFreeCellsInMonsterZone() {
        int ans = 0 ;
        for(int i = 0 ; i < monsterZone.length ; i++){
            if(monsterZone[i] == null)
                ans++;
        }
            return ans;
    }

    public int getCntFreeCellsInSpellZone() {
        int ans = 0 ;
        for(int i = 0 ; i < spellZone.length ; i++){
            if(spellZone[i] == null)
                ans++;
        }
        return ans;
    }
}


