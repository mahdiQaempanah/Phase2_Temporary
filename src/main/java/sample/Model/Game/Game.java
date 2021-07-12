package sample.Model.Game;

import sample.Model.Game.Card.GameLogType;
import sample.Model.Game.Card.SpellCard.SpellCard;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int id;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private Phase phase;
    private int rounds;
    private static int counter;
    private static ArrayList<Game> games;
    private ArrayList<String> gameLog;


    static{
        counter = 1;
        games = new ArrayList<Game>();
    }

    public Game(Player player1, Player player2, int round){
        setId(counter);
        setPlayer1(player1);
        setPlayer2(player2);
        setRounds(rounds);
        setActivePlayer(getRandomPlayer());
        setInactivePlayer(player1);
        if(activePlayer == inactivePlayer)
            setInactivePlayer(player2);
        setPhase(Phase.BATTLE_PHASE);
        gameLog = new ArrayList<>();
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setInactivePlayer(Player inactivePlayer) {
        this.inactivePlayer = inactivePlayer;
    }

    public Player getInactivePlayer() {
        return inactivePlayer;
    }

    public void nextTurn(){
        Player tmp = getActivePlayer() ;
        setActivePlayer(getInactivePlayer());
        setInactivePlayer(tmp);
    }

    public void nextPhase(){
        switch (getPhase()) {
            case DRAW_PHASE:
                setPhase(Phase.STANDBY_PHASE);
                break;
            case STANDBY_PHASE:
                setPhase(Phase.MAIN_PHASE_1);
                break;
            case MAIN_PHASE_1:
                setPhase(Phase.BATTLE_PHASE);
                break;
            case BATTLE_PHASE:
                setPhase(Phase.MAIN_PHASE_2);
                break;
            case MAIN_PHASE_2:
                setPhase(Phase.END_PHASE);
                break;
            case END_PHASE:
                setPhase(Phase.DRAW_PHASE);
                nextTurn();
                break;
            default:
                setPhase(Phase.DRAW_PHASE);
                break;
        }
    }


    public static Game getGameByID(int id){
        for(Game game : games){
            if(game.getId()==id) return game;
        }
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getRounds() {
        return rounds;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getRandomPlayer(){
        Random rand = new Random();
        int randomPlayerNumber = rand.nextInt(2)+1;
        if(randomPlayerNumber==1) return player1;
        else return player2;
    }

    public ArrayList<String> getGameLog(){
        return this.gameLog;
    }



    public void addToGameLog(String log){
        gameLog.add(log);
        checkEffects(activePlayer);
        checkEffects(inactivePlayer);
    }

    private void checkEffects(Player player) {
        if(player.getField().getFieldZone()!=null)
            player.getField().getFieldZone().activate(this,null);
        for (SpellCard spellCard : player.getField().getSpellZone()) {
            if(spellCard != null)
                spellCard.activate(this, null);
        }
    }

    public void addToGameLog(GameLogInfo log){
        addToGameLog(new Gson().toJson(log));
    }

    public void addToGameLog(GameLogType type, int mainCardIdentity){
        GameLogInfo gameLogInfo = new GameLogInfo(type,mainCardIdentity);
        addToGameLog(gameLogInfo);
    }

    public void addNextPhaseLog(Phase nowPhase) {
        GameLogInfo gameLogInfo = new GameLogInfo();
        gameLogInfo.setType(GameLogType.NEXT_PHASE);
        gameLogInfo.setNowPhase(nowPhase);
        addToGameLog(gameLogInfo);
    }

    public void addSummonMonsterLog(int cardIdentity) {
        GameLogInfo gameLogInfo = new GameLogInfo(GameLogType.SUMMON_MONSTER,cardIdentity);
        addToGameLog(gameLogInfo);
    }

    public void addSummonMonsterWith1Tribute(int cardIdentity, int victimIdentity) {
        GameLogInfo gameLogInfo = new GameLogInfo(GameLogType.SUMMON_MONSTER,cardIdentity);
        gameLogInfo.getTributes().add(victimIdentity);
        addToGameLog(gameLogInfo);
    }

    public void addSummonMonsterWith2Tributes(int cardIdentity, int victim1Identity, int victim2Identity) {
        GameLogInfo gameLogInfo = new GameLogInfo(GameLogType.SUMMON_MONSTER,cardIdentity);
        gameLogInfo.getTributes().add(victim1Identity);
        gameLogInfo.getTributes().add(victim2Identity);
        addToGameLog(gameLogInfo);
    }

    public void addAttackLog(int cardIdentity, int targetIdentity) {
        GameLogInfo gameLogInfo = new GameLogInfo(GameLogType.ATTACK,cardIdentity);
        gameLogInfo.setTargetCard(targetIdentity);
        addToGameLog(gameLogInfo);
    }
}