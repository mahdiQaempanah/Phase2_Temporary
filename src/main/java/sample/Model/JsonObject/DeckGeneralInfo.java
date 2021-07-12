package sample.Model.JsonObject;

public class DeckGeneralInfo{
    public final String name;
    public final int sideDeckSize;
    public final int mainDeckSize;
    public DeckGeneralInfo(DeckJson deck){
        this.name = deck.getName();
        this.mainDeckSize = deck.getMainDeckSize();
        this.sideDeckSize = deck.getSideDeckSize();
    }
}
