package sample.Model.Game.Card.MonsterCard;

import sample.Model.Game.*;
import sample.Model.Game.Card.*;
import sample.Model.JsonObject.AttackInfo;

import java.util.ArrayList;

public class MonsterCard extends Card{
    private int level;
    private int atk;
    private int def;
    private Attribute attribute;
    private Mode mode;
    private ArrayList<Type> types;
    private MonsterCategory monsterCategory;
    private boolean isChangeModeInTurn;
    private boolean isMonsterAttackInTurn;



    public MonsterCard(String name,String description,String number,String level,String atk,String def,String attribute,String type){
        setName(name);
        setDescription(description);
        setNumber(number);
        setLevel(Integer.parseInt(level));
        setAtk(Integer.parseInt(atk));
        setDef(Integer.parseInt(def));
        setAttribute(attribute);
        types = new ArrayList<>();
        types.add(stringToType(type));
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getAtk() {
        return atk;
    }

    public void increaseAtk(int amount){
        atk += amount;
    }

    public void decreaseAtk(int amount){
        atk -= amount;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getDef() {
        return def;
    }

    public void increaseDef(int amount){
        def += amount;
    }

    public void decreaseDef(int amount){
        def -= amount;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }



    public void addToTypes(Type type){
        types.add(type);
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setMonsterCategory(MonsterCategory monsterCategory) {
        this.monsterCategory = monsterCategory;
    }

    public MonsterCategory getMonsterCategory() {
        return monsterCategory;
    }

    public boolean isChangeModeInTurn() {
        return isChangeModeInTurn;
    }

    public void setChangeModeInTurn(boolean changeModeInTurn) {
        isChangeModeInTurn = changeModeInTurn;
    }

    public boolean isMonsterAttackInTurn() {
        return isMonsterAttackInTurn;
    }

    public void setMonsterAttackInTurn(boolean monsterAttackInTurn) {
        isMonsterAttackInTurn = monsterAttackInTurn;
    }

    public AttackInfo attack(Game game, MonsterCard targetCard){
        int powerDiff = 0;
        if(targetCard.getMode().equals(Mode.ATTACK)){
            powerDiff = atk - targetCard.getAtk();
            if(powerDiff>0){
                game.getActivePlayer().getField().killMonsterCard(targetCard,game);
                game.getInactivePlayer().decreaseLp(powerDiff);
            }
            else if(powerDiff<0){
                game.getActivePlayer().getField().killMonsterCard(this, game);
                game.getActivePlayer().decreaseLp(-powerDiff);
            }
            else{
                game.getActivePlayer().getField().killMonsterCard(this, game);
                game.getInactivePlayer().getField().killMonsterCard(targetCard, game);
            }
        }
        else{
            powerDiff = atk - targetCard.getDef();
            if(powerDiff>0){
                game.getInactivePlayer().getField().killMonsterCard(targetCard, game);
            }
            else if(powerDiff<0){
                game.getActivePlayer().decreaseLp(-powerDiff);
            }
            else;
        }
        setMonsterAttackInTurn(true);
        return new AttackInfo(targetCard.getName(),targetCard.getAtk(),targetCard.getDef(), targetCard.getStatus(),targetCard.getMode(),this.getAtk()) ;
    }

    public void directAttack(Game game){
        game.getInactivePlayer().decreaseLp(atk);
        setMonsterAttackInTurn(true);
    }

    public void setAttribute(String attribute){
        switch (attribute) {
            case "DARK" :
                this.attribute = Attribute.DARK;
            case "LIGHT" :
                this.attribute = Attribute.LIGHT;
            case "FIRE" :
                this.attribute = Attribute.FIRE;
            case "WATER" :
                this.attribute = Attribute.WATER;
            case "WIND" :
                this.attribute = Attribute.WIND;
            case "EARTH" :
                this.attribute = Attribute.EARTH;
        }
    }

    public Type stringToType(String type){
        switch (type) {
            case "Normal" :
                return Type.NORMAL;
            case "Effect" :
                return Type.EFFECT;
            case "Ritual" :
                return Type.RITUAL;
            case "Warrior" :
                return Type.WARRIOR;
            case "Beast-Warrior" :
                return Type.BEAST_WARRIOR;
            case "Fiend" :
                return Type.FIEND;
            case "Aqua" :
                return Type.AQUA;
            case "Beast" :
                return Type.BEAST;
            case "Pyro" :
                return Type.PYRO;
            case "Spellcaster" :
                return Type.SPELL_CASTER;
            case "Thunder" :
                return Type.THUNDER;
            case "Dragon" :
                return Type.DRAGON;
            case "Machine" :
                return Type.MACHINE;
            case "Rock" :
                return Type.ROCK;
            case "Insect" :
                return Type.INSECT;
            case "Cyberse" :
                return Type.CYBERSE;
            case "Fairy" :
                return Type.FAIRY;
            case "Sea Serpent" :
                return Type.SEA_SERPENT;
            default :
                return Type.NORMAL;
        }
    }
}
