package sample.Model.JsonObject;

import sample.Model.Game.Card.MonsterCard.Mode;
import sample.Model.Game.Card.Status;

public class AttackInfo {
    public String looserCardName;
    public String looserPlayer;
    private   String targetMonsterName;
    public int decreaseLpForLooser;
    private  int targetAtk;
    private  int targetDef;
    private  Status targetStatus;
    private  Mode targetMode;
    private  int attakerAtk;

    public AttackInfo(String looserCardName, String looserPlayer,int decreaseLpForLooser){
        this.looserCardName = looserCardName;
        this.looserPlayer = looserPlayer;
        this.decreaseLpForLooser = decreaseLpForLooser;
    }

    public AttackInfo(String targetMonsterName, int targetAtk, int targetDef, Status targetStatus, Mode targetMode, int attakerAtk) {
        this.targetMonsterName = targetMonsterName;
        this.targetAtk = targetAtk;
        this.targetDef = targetDef;
        this.targetStatus = targetStatus;
        this.targetMode = targetMode;
        this.attakerAtk = attakerAtk;
    }

}
