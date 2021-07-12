package sample.Model.JsonObject;

import sample.Model.Game.Card.MonsterCard.Mode;
import sample.Model.Game.Card.Status;

public class AttackInfo {
    private final String targetMonsterName;
    private final int targetAtk;
    private final int targetDef;
    private final Status targetStatus;
    private final Mode targetMode;
    private final int attakerAtk;

    public AttackInfo(String targetMonsterName, int targetAtk, int targetDef, Status targetStatus, Mode targetMode, int attakerAtk) {
        this.targetMonsterName = targetMonsterName;
        this.targetAtk = targetAtk;
        this.targetDef = targetDef;
        this.targetStatus = targetStatus;
        this.targetMode = targetMode;
        this.attakerAtk = attakerAtk;
    }

    public Status getTargetStatus() {
        return this.targetStatus;
    }

    public Mode getTargetMode() {
        return this.targetMode;
    }

    public int getAttakerAtk() {
        return this.attakerAtk;
    }

    public int getTargetAtk() {
        return this.targetAtk;
    }

    public int getTargetDef() {
        return this.targetDef;
    }

    public String getTargetMonsterName() {
        return this.targetMonsterName;
    }
}
