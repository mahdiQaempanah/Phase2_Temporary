package sample.Model;

import java.util.ArrayList;

public class Account{
    private String username;
    private String password;
    private String nickname;
    private int score;
    private int money;
    private ArrayList<String> purchasedCardsNames;
    private ArrayList<Deck> decks;
    private Deck activeDeck;

    public Account(String username, String password, String nickname){
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setScore(0);
        setMoney(100000);
        purchasedCardsNames = new ArrayList<String>();
        decks = new ArrayList<Deck>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score){
        this.score += score;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
    
    public void increaseMoney(int money){
        this.money += money;
    }

    public void decreaseMoney(int money){
        this.money -= money;
    }

    public void addToPurchasedCard(String cardName){
        purchasedCardsNames.add(cardName);
    }

    public ArrayList<String> getpurchasedCardsNames() {
        return purchasedCardsNames;
    }

    public boolean hasCard(String cardName){
       return purchasedCardsNames.contains(cardName);
    }

    public void addTodecks(Deck deck){
        decks.add(deck);
    }

    public ArrayList<Deck> getdecks() {
        return decks;
    }

    public boolean hasDeck(Deck deck){
        return decks.contains(deck);
    }

    public Deck getDeckByName(String name){
        for(Deck deck : decks){
            if(deck.getName().equals(name)) return deck;
        }
        return null;
    }

    public void setactiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public Deck getactiveDeck() {
        return activeDeck;
    }
}