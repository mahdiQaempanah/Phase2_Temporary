package sample.Model.JsonObject;

import java.util.ArrayList;

public class DeckJson {
    private final String name;
    private final ArrayList<CardJson> mainDeck;
    private final ArrayList<CardJson> sideDeck;

    public DeckJson(String name){
        this.name = name;
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }

    public int getMainDeckSize(){return mainDeck.size();}

    public int getSideDeckSize(){return sideDeck.size();}

    public String getName(){
        return this.name;
    }

    public void addToMainDeck(String cardName){
        mainDeck.add(new CardJson(cardName));
    }

    public void addToSideDeck(String cardName){
        sideDeck.add(new CardJson(cardName));
    }

    public boolean isSideDeckFull(){
        return sideDeck.size() == 15;
    }

    public boolean isMainDeckFull(){
        return mainDeck.size() == 60;
    }

    public ArrayList<CardJson> getMainDeck(){
        return this.mainDeck;
    }

    public ArrayList<CardJson> getSideDeck(){
        return this.sideDeck;
    }

    public int getCntOfThisCard(String cardName){
        return getCntOfThisCardInMainDeck(cardName) + getCntOfThisCardInSideDeck(cardName);
    }

    public int getCntOfThisCardInMainDeck(String cardName){
        int ans = 0 ;
        for (CardJson card : mainDeck) {
            if(card.getName().equals(cardName))
                ans++;
        }
        return ans;
    }

    public int getCntOfThisCardInSideDeck(String cardName){
        int ans = 0 ;
        for (CardJson card : sideDeck) {
            if(card.getName().equals(cardName))
                ans++;
        }
        return ans;
    }

    public void removeFromMainDeck(String cardName){
        for (CardJson card : mainDeck) {
            if(card.getName().equals(cardName)) {
                mainDeck.remove(card);
                break;
            }
        }
    }

    public void removeFromSideDeck(String cardName){
        for (CardJson card : sideDeck) {
            if(card.getName().equals(cardName)) {
                sideDeck.remove(card);
                break;
            }
        }
    }
}
