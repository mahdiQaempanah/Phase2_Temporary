package sample.Model.JsonObject;

import java.util.ArrayList;

public class ShowAllDecksJson{
    public final DeckGeneralInfo activeDeck;
    public final ArrayList<DeckGeneralInfo> decks;
    public ShowAllDecksJson(AccountJson user){
        if(user.getActiveDeck() != null)
            this.activeDeck = new DeckGeneralInfo(user.getActiveDeck());
        else
            this.activeDeck = null;
        this.decks = new ArrayList<>();
        for (DeckJson deck : user.getDecks()) {
            this.decks.add(new DeckGeneralInfo(deck));
        }
    }
}
