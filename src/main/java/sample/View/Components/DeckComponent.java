package sample.View.Components;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.View.DeckMenuController;





public class DeckComponent {
    public Point2D deckComponentSize = new Point2D(240,200);
    private DeckMenuController myController;
    public Rectangle deck;
    public Label deckInfo;

    public DeckComponent(String name, int deckSize, boolean isActiveDeck,DeckMenuController deckMenuController) {
        myController = deckMenuController;
        deckInfo = new Label("Deck name: " + name + "\nDeck size: " + deckSize);
        deckInfo.setTextFill(Color.WHITE);
        deckInfo.setFont(new Font(16));

        deck = new Rectangle(deckComponentSize.getX(),deckComponentSize.getY());
        String address = getClass().getResource("../../../Image/deck.jpg").toExternalForm();
        if(isActiveDeck){
            address = getClass().getResource("../../../Image/activeDeck.jpg").toExternalForm();
        }
        deck.setFill(new ImagePattern(new Image(address)));

    }

    public String getDeckName(){
        return deckInfo.getText().split("\n")[0].substring(11);
    }

    public void setDeckPosition(Point2D coordinates){
        deck.setLayoutX(coordinates.getX());
        deck.setLayoutY(coordinates.getY());
    }


    public void setDeckInfoPosition(Point2D coordinates){
        deckInfo.setLayoutX(coordinates.getX());
        deckInfo.setLayoutY(coordinates.getY());
    }

    public void activeEffect(){
        DeckComponent me = this;

        deck.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deck.setStyle("-fx-effect: dropshadow(three-pass-box,  #ffffff, 50, 0.6, 0, 0)");
            }
        });

        deck.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deck.setStyle(null);
            }
        });

        deck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    myController.clickDeck(me);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        deck.setStyle(null);
    }

    public void inactiveEffect(){
        deck.setOnMouseEntered(null);
        deck.setOnMouseExited(null);
        deck.setOnMouseClicked(null);
        deck.setStyle("-fx-effect: dropshadow(three-pass-box,  #00c832, 50, 0.6, 0, 0)");
    }
}
