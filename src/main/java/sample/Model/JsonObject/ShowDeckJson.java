package sample.Model.JsonObject;

import java.util.ArrayList;

public class ShowDeckJson {
    private final String name;
    private final boolean isSideDeck;
    private final ArrayList<CardGeneralInfo> monsters;
    private final ArrayList<CardGeneralInfo> spellAndTraps;

    public ShowDeckJson(String name,boolean isSideDeck){
        this.name = name;
        this.isSideDeck = isSideDeck;
        monsters = new ArrayList<>();
        spellAndTraps = new ArrayList<>();
    }

    public void addToMonsters(CardGeneralInfo card){
        this.monsters.add(card);
    }

    public void addToSpellAndTraps(CardGeneralInfo card){
        this.spellAndTraps.add(card);
    }

    public String getName() {
        return name;
    }

    public boolean isSideDeck() {
        return isSideDeck;
    }

    public ArrayList<CardGeneralInfo> getMonsters() {
        return monsters;
    }

    public ArrayList<CardGeneralInfo> getSpellAndTraps() {
        return spellAndTraps;
    }
}
