package sample.View.Components;


import javafx.scene.layout.AnchorPane;
import sample.Model.JsonObject.BoardJson;
import sample.View.Components.FieldComponent;
import sample.View.GameViewController;

public class BoardComponent {
    AnchorPane mainPane;
    FieldComponent activePlayer;
    FieldComponent inactivePlayer;

    public BoardComponent(GameViewController controller,AnchorPane mainPane){
        this.mainPane = mainPane;
        activePlayer = new FieldComponent(controller, mainPane,true);
        inactivePlayer = new FieldComponent(controller, mainPane,false);
    }

    public void reset(BoardJson newBoard) {
        activePlayer.build(newBoard.getActivePlayer());
        inactivePlayer.build(newBoard.getInActivePlayer());
    }

    public FieldComponent getActivePlayer() {
        return activePlayer;
    }

    public FieldComponent getInactivePlayer() {
        return inactivePlayer;
    }
}
