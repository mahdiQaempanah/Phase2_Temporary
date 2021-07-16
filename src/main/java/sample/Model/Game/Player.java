package sample.Model.Game;

import sample.Model.Game.Card.*;
import sample.Model.Game.Card.MonsterCard.*;
import sample.Model.Game.Card.SpellCard.*;
import sample.Model.JsonObject.AttackInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String nickname;
    private Field field;
    private int lp;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private ArrayList<Card> cards;
    private ArrayList<Card> hand;
    private boolean canRitualSummon;
    private boolean isMonsterSummon;
    private boolean isMonsterSet;
    private Card selectedCard;

    public Player(String nickName,ArrayList<String> mainDeck,ArrayList<String> sideDeck){
        this.mainDeck = new ArrayList<>();
        this.sideDeck = new ArrayList<>();
        setNickname(nickName);
        setField(new Field());
        setLp(8000);
        setMainDeck(mainDeck);
        setSideDeck(sideDeck);
        System.out.println(this.mainDeck);
        setCards(this.mainDeck);
        setHand(new ArrayList<Card>());
        setSelectedCard(null);

    }

    public void resetRound(){
        isMonsterSummon = false;
        isMonsterSet = false;
        for (MonsterCard monsterCard : field.getMonsterZone()) {
            if(monsterCard != null){
                monsterCard.setMonsterAttackInTurn(false);
                monsterCard.setChangeModeInTurn(false);
            }
        }
    }

    public void setCanRitualSummon(boolean canRitualSummon) {
        this.canRitualSummon = canRitualSummon;
    }

    public boolean getCardRitualSummon(){
        return canRitualSummon;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public int getLp() {
        return lp;
    }

    public void decreaseLp(int amount){
        this.lp -= amount;
    }

    public void setMainDeck(ArrayList<String> mainDeckNames) {
        boolean isMonster = false;
        String line = ""; 
        for(String name : mainDeckNames){
            try{
                BufferedReader reader = new BufferedReader(new FileReader("target\\classes\\Database\\Monster.csv"));
                while ((line = reader.readLine()) != null){  
                    String[] card = line.split(",");
                    if(card[0].equals(name)){
                        isMonster = true;
                        if(card[4].equals("Normal")) mainDeck.add(new MonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                        else if(card[4].equals("Ritual")) mainDeck.add(new RitualMonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                        else mainDeck.add(new EffectiveMonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                    }
                }
                reader.close(); 
            }catch (IOException e){
                System.out.println("monster not found");
            }
            if(!isMonster){ 
                try{
                    BufferedReader reader = new BufferedReader(new FileReader("target\\classes\\Database\\SpellTrap.csv"));
                    while ((line = reader.readLine()) != null){  
                        String[] card = line.split(",");
                        if(card[0].equals(name)){
                            mainDeck.add(new SpellCard(card[0],card[3],"0",card[1],card[2]));
                        }
                    }
                    reader.close(); 
                }catch (IOException e){
                    System.out.println("spell not found");
                }
            }
        }
    }

    public void setSideDeck(ArrayList<String> sideDeckNames){
        boolean isMonster = false;
        String line = "";
        for(String name : sideDeckNames){
            try{
                BufferedReader reader = new BufferedReader(new FileReader("target\\classes\\Database\\Monster.csv"));
                while ((line = reader.readLine()) != null){
                    String[] card = line.split(",");
                    if(card[0].equals(name)){
                        isMonster = true;
                        if(card[4].equals("Normal")) sideDeck.add(new MonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                        else if(card[4].equals("Ritual")) sideDeck.add(new RitualMonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                        else sideDeck.add(new EffectiveMonsterCard(card[0],card[7],"0",card[1],card[5],card[6],card[3],card[4]));
                    }
                }
                reader.close();
            }catch (IOException e){
                System.out.println("OHHH noo");
            }
            if(!isMonster){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader("target\\classes\\Database\\SpellTrap.csv"));
                    while ((line = reader.readLine()) != null){
                        String[] card = line.split(",");
                        if(card[0].equals(name)){
                            sideDeck.add(new SpellCard(card[0],card[3],"0",card[1],card[2]));
                        }
                    }
                    reader.close();
                }catch (IOException e){
                }
            }
        }
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void removeFromCards(Card card){
        cards.remove(card);
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand(){
        return this.hand;
    }

    public void addToHand(Card card){
        hand.add(card);
    }

    public void removeFromHand(Card card){
        hand.remove(card);
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Card getRandomCard(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(cards.size());
        Card randomCard = cards.get(randomIndex);
        return randomCard;
    }

    public Card draw(){
        Card card = this.getRandomCard();
        this.removeFromCards(card);
        this.addToHand(card);
        return card;
    }

    public void summon(MonsterCard monsterCard){
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SUMMON);
        monsterCard.setMode(Mode.ATTACK);
        this.getField().addToMonsterZone(monsterCard);
        setMonsterSummon(true);
    }

    public void tributeSummon(MonsterCard monsterCard,MonsterCard sacrificeCard,Game game){
        this.getField().killMonsterCard(sacrificeCard, game);
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SUMMON);
        monsterCard.setMode(Mode.ATTACK);
        this.getField().addToMonsterZone(monsterCard);
        setMonsterSummon(true);
    }

    public void tributeSummon(MonsterCard monsterCard,MonsterCard sacrificeCard1,MonsterCard sacrificeCard2, Game game){
        this.getField().killMonsterCard(sacrificeCard1, game);
        this.getField().killMonsterCard(sacrificeCard2, game);
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SUMMON);
        monsterCard.setMode(Mode.ATTACK);
        this.getField().addToMonsterZone(monsterCard);
        setMonsterSummon(true);
    }

    public void setMonster(MonsterCard monsterCard){
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SET);
        monsterCard.setMode(Mode.DEFENSE);
        this.getField().addToMonsterZone(monsterCard);
        setMonsterSet(true);
    }

    public void tributeSet(MonsterCard monsterCard,MonsterCard sacrificeCard, Game game){
        this.getField().killMonsterCard(sacrificeCard, game);
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SET);
        monsterCard.setMode(Mode.DEFENSE);
        this.getField().addToMonsterZone(monsterCard);
    }

    public void tributeSet(MonsterCard monsterCard,MonsterCard sacrificeCard1,MonsterCard sacrificeCard2,Game game){
        this.getField().killMonsterCard(sacrificeCard1, game);
        this.getField().killMonsterCard(sacrificeCard2, game);
        this.removeFromHand(monsterCard);
        monsterCard.setStatus(Status.SET);
        monsterCard.setMode(Mode.DEFENSE);
        this.getField().addToMonsterZone(monsterCard);
    }

    public void changeMode(MonsterCard monsterCard){
        Mode newMode = Mode.ATTACK;
        if(newMode == monsterCard.getMode())
            newMode = Mode.DEFENSE;
        monsterCard.setMode(newMode);
        monsterCard.setChangeModeInTurn(true);
    }

    public void flipSummon(MonsterCard monsterCard){
        monsterCard.setStatus(Status.SUMMON);
        monsterCard.setMode(Mode.ATTACK);
    }

    public AttackInfo attack(Game game, MonsterCard attackerCard, MonsterCard targetCard){
        return attackerCard.attack(game, targetCard);
    }

    public void directAttack(Game game,MonsterCard attackerCard){
        attackerCard.directAttack(game);
    }

    public void activateSpell(Game game,SpellCard spellCard){
        this.removeFromHand(spellCard);
        spellCard.setStatus(Status.SUMMON);
        this.getField().addToSpellZone(spellCard);
        spellCard.getSpell().activate(game);
        spellCard.setActivateInTurn(true);
    }

    public void activateField(Game game,SpellCard spellCard){
        this.removeFromHand(spellCard);
        spellCard.setStatus(Status.SUMMON);
        this.getField().setFieldZone(spellCard);
        spellCard.getSpell().activate(game);
        spellCard.setActivateInTurn(true);
    }

    public void setSpell(SpellCard spellCard){
        this.removeFromHand(spellCard);
        spellCard.setStatus(Status.SET);
        this.getField().addToSpellZone(spellCard);
    }

    public void switchSpell(Game game,SpellCard spellCard){
        spellCard.setStatus(Status.SUMMON);
        spellCard.getSpell().activate(game);
    }

    public void ritualSummon(RitualMonsterCard ritualMonsterCard,ArrayList<MonsterCard> sacrificeCards,Mode mode,Game game){
        for(MonsterCard sacrifiseCard : sacrificeCards){
            this.getField().killMonsterCard(sacrifiseCard, game);
        }
        summon(ritualMonsterCard);
        changeMode(ritualMonsterCard);
    }



    public boolean containCard(Card card){
        if(hand.contains(card))
            return true;
        if(field.isSpellZoneContains(card))
            return true;
        if(field.isMonsterZoneContains(card))
            return true;
        if(field.getGraveyard().contains(card))
            return true;
        if(field.getFieldZone() == card)
            return true;
        return false;
    }

    public boolean isMonsterSummon() {
        return isMonsterSummon;
    }

    public void setMonsterSummon(boolean monsterSummon) {
        isMonsterSummon = monsterSummon;
    }

    public boolean isMonsterSet() {
        return isMonsterSet;
    }

    public void setMonsterSet(boolean monsterSet) {
        isMonsterSet = monsterSet;
    }
}