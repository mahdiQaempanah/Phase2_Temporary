package sample.View;


import javafx.scene.layout.AnchorPane;
import sample.Model.JsonObject.BoardJson;
import sample.View.Components.FieldComponent;

public class BoardComponent {
    AnchorPane mainPane;
    FieldComponent activePlayer;
    FieldComponent inactivePlayer;

    public BoardComponent(AnchorPane mainPane){
        this.mainPane = mainPane;
        activePlayer = new FieldComponent(mainPane,true);
        inactivePlayer = new FieldComponent(mainPane,false);
    }

    public void reset(BoardJson newBoard) {
        activePlayer.build(newBoard.getActivePlayer());
        inactivePlayer.build(newBoard.getInActivePlayer());
    }
}
