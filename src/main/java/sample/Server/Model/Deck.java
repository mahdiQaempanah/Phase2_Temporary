package sample.Model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<String> mainDeck;
    private ArrayList<String> sideDeck;

    public Deck(String name){
        setName(name);
        mainDeck = new ArrayList<String>();
        sideDeck = new ArrayList<String>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCardToMainDeck(String cardName){
        mainDeck.add(cardName);
    }

    public void removeCardFromMainDeck(String cardName){
        mainDeck.remove(cardName);
    }

    public ArrayList<String> getMainDeck() {
        return mainDeck;
    }

    public int getMainDeckSize(){
        return mainDeck.size();
    }

    public void addCardToSideDeck(String cardName){
        sideDeck.add(cardName);
    }

    public void removeCardFromSideDeck(String cardName){
        sideDeck.remove(cardName);
    }

    public ArrayList<String> getSideDeck() {
        return sideDeck;
    }

    public int getSideDeckSize(){
        return sideDeck.size();
    }

    public boolean isValid(){
        if(mainDeck.size() >= 40) return true;
        else return false;
    }

    public int cardCount(String cardName){
        int count = 0;
        for(String c : mainDeck){
            if(c.equals(cardName)) count++;
        }
        for(String c : sideDeck){
            if(c.equals(cardName)) count++;
        }
        return count;
    }
}