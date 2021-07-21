package sample.Model.JsonObject;

import sample.Model.Game.Card.MonsterCard.Mode;
import sample.Model.Game.Card.Status;

public class CardBoardInfo {
    private Mode mode;
    private Status status;
    private String name;

    public CardBoardInfo(Mode mode, Status status, String name) {
        this.mode = mode;
        this.status = status;
        this.name = name;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() { return this.name;}
}
