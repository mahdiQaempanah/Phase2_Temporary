package sample.Model.Game;

import sample.Model.Game.Card.GameLogType;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLogInfo {
    private GameLogType type;
    private int mainCard;
    private int targetCard;
    private ArrayList<Integer> tributes;
    private HashMap<String ,String> anotherData;
    private Phase nowPhase;

    public GameLogInfo(){
        this.tributes = new ArrayList<>();
        this.anotherData = new HashMap<>();
    }

    public GameLogInfo(GameLogType type, int mainCard){
        this.type = type;
        this.mainCard = mainCard;
    }

    public GameLogType getType() {
        return type;
    }

    public void setType(GameLogType type) {
        this.type = type;
    }

    public int getMainCard() {
        return mainCard;
    }

    public void setMainCard(int mainCard) {
        this.mainCard = mainCard;
    }

    public int getTargetCard() {
        return targetCard;
    }

    public void setTargetCard(int targetCard) {
        this.targetCard = targetCard;
    }

    public ArrayList<Integer> getTributes() {
        return tributes;
    }

    public void setTributes(ArrayList<Integer> tributes) {
        this.tributes = tributes;
    }

    public HashMap<String, String> getAnotherData() {
        return anotherData;
    }

    public void setAnotherData(HashMap<String, String> anotherData) {
        this.anotherData = anotherData;
    }

    public Phase getNowPhase() {
        return nowPhase;
    }

    public void setNowPhase(Phase phase) {
        this.nowPhase = phase;
    }
}
