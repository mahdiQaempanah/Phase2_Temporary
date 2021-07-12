package sample.Model.JsonObject;

public class BoardJson {
    private FieldJson ActivePlayer;
    private FieldJson inActivePlayer;

    public BoardJson(FieldJson activePlayer, FieldJson inActivePlayer) {
        ActivePlayer = activePlayer;
        this.inActivePlayer = inActivePlayer;
    }

    public FieldJson getActivePlayer() {
        return ActivePlayer;
    }

    public FieldJson getInActivePlayer() {
        return inActivePlayer;
    }
}
