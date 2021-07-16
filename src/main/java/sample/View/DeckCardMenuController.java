package sample.View;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.CardGeneralInfo;
import sample.Model.JsonObject.ShowDeckJson;
import sample.View.Graphic.MainMenu;


import java.util.ArrayList;
import java.util.Collection;

public class DeckCardMenuController {
    public static final Point2D cardSize = new Point2D(60,70);
    public ImageView cardInfo;
    public TextField sideField;
    public TextField mainField;
    String deckName;
    public Rectangle selectedCard;
    public AnchorPane mainPane;
    public Label apiLog;
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public API api;

    public ShowDeckJson mainDeck;
    ArrayList<Rectangle> mainCards;

    public ShowDeckJson sideDeck;
    ArrayList<Rectangle> sideCards;

    public void start(String deckName, Stage primaryStage, MainMenu myMainMenu, API api) throws Exception {
        this.deckName = deckName;
        this.primaryStage = primaryStage;
        this.myMainMenu = myMainMenu;
        this.api = api;
        buildCards();
    }

    private void buildCards() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("showDeck");
        keyWords.add("deckName");keyWords.add(deckName);
        keyWords.add("isMainSide");keyWords.add("false");
        buildSideDeck(new Gson().fromJson(responseFromApi(keyWords).getMessage(),ShowDeckJson.class));

        keyWords.clear();
        keyWords.add("command");keyWords.add("showDeck");
        keyWords.add("deckName");keyWords.add(deckName);
        keyWords.add("isMainSide");keyWords.add("true");
        buildMainDeck(new Gson().fromJson(responseFromApi(keyWords).getMessage(),ShowDeckJson.class));
    }


    private void buildSideDeck(ShowDeckJson deckInfo) {
        int id = -1;
        int xBegin = 360;
        int yBegin = 270;

        if(sideCards != null){
            for (Rectangle card : sideCards) {
                mainPane.getChildren().remove(card);
            }
        }


        sideDeck = deckInfo;
        sideCards = new ArrayList<>();

        for (CardGeneralInfo monster : deckInfo.getMonsters()) {
            id++;
            Rectangle mons = new Rectangle(cardSize.getX(),cardSize.getY());
            mons.setLayoutX(xBegin + id*(0.6)*cardSize.getX());
            mons.setLayoutY(yBegin);
            mons.setFill(new ImagePattern(new Image(getCardPictureAddress(monster.getName()))));
            activeCard(mons);
            mainPane.getChildren().add(mons);
            sideCards.add(mons);
        }

        for (CardGeneralInfo spell : deckInfo.getSpellAndTraps()) {
            id++;
            Rectangle mons = new Rectangle(cardSize.getX(),cardSize.getY());
            mons.setLayoutX(xBegin + id*(0.6)*cardSize.getX());
            mons.setLayoutY(yBegin);
            mons.setFill(new ImagePattern(new Image(getCardPictureAddress(spell.getName()))));
            activeCard(mons);
            mainPane.getChildren().add(mons);
            sideCards.add(mons);
        }
    }

    private void buildMainDeck(ShowDeckJson deckInfo) {
        int id = -1;
        int xBegin = 380;
        int yBegin = 620;

        if(mainCards!= null){
            for (Rectangle card : mainCards) {
                mainPane.getChildren().remove(card);
            }
        }

        mainDeck = deckInfo;
        mainCards = new ArrayList<>();
        for (CardGeneralInfo monster : deckInfo.getMonsters()) {
            id++;
            Rectangle mons = new Rectangle(cardSize.getX(),cardSize.getY());
            mons.setLayoutX(xBegin + id*(0.6)*cardSize.getX());
            mons.setLayoutY(yBegin);
            mons.setFill(new ImagePattern(new Image(getCardPictureAddress(monster.getName()))));
            activeCard(mons);
            mainPane.getChildren().add(mons);
            mainCards.add(mons);
        }

        for (CardGeneralInfo spell : deckInfo.getSpellAndTraps()) {
            id++;
            Rectangle mons = new Rectangle(cardSize.getX(),cardSize.getY());
            mons.setLayoutX(xBegin + id*(0.6)*cardSize.getX());
            mons.setLayoutY(yBegin);
            mons.setFill(new ImagePattern(new Image(getCardPictureAddress(spell.getName()))));
            activeCard(mons);
            mainPane.getChildren().add(mons);
            mainCards.add(mons);
        }
    }

    public ApiMessage responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        JSONObject jsonAns = api.run(message);
        return new Gson().fromJson(String.valueOf(jsonAns),ApiMessage.class);
    }

    public String getCardPictureAddress(String cardName) {
        cardName = cardName.trim().toLowerCase();
        char[] card = new char[cardName.length()];
        for(int i = 0 ; i < card.length ; i++) {
            if((i == 0 || cardName.charAt(i-1) == ' ') && cardName.charAt(i) != ' ')
                card[i] = (char) ('A' + cardName.charAt(i) - 'a');
            else
                card[i] = cardName.charAt(i);
        }

        String[] cardWords = new String(card).split(" ");
        String cardAddress = new String();
        for (String cardWord : cardWords) {
            cardAddress = cardAddress.concat(cardWord);
        }

        try {
            cardAddress = "../../Image/Cards/" + cardAddress + ".jpg";
            System.out.println(cardAddress);
            return  getClass().getResource(cardAddress).toExternalForm();
        }
        catch (Exception ex){
            return null;
        }
    }

    public void activeCard(Rectangle card){
        card.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImagePattern imagePattern = (ImagePattern) card.getFill();
                card.setStyle("-fx-effect: dropshadow(three-pass-box,  #ffffff, 50, 0.6, 0, 0)");
                cardInfo.setImage(imagePattern.getImage());
            }
        });

        card.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                card.setStyle(null);
            }
        });

        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    clickCard(card,event);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        card.setStyle(null);
    }

    public void inactiveCard(Rectangle card){
        card.setOnMouseEntered(null);
        card.setOnMouseExited(null);
        card.setOnMouseClicked(null);
        card.setStyle("-fx-effect: dropshadow(three-pass-box,  #00c832, 50, 0.6, 0, 0)");
    }

    private void clickCard(Rectangle card, MouseEvent event) {
        if(selectedCard!=null){
            activeCard(selectedCard);
        }
        selectedCard = card;
        inactiveCard(card);
    }


    public void handleAddNewCardToSideDeck(ActionEvent actionEvent) throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("addCardToDeck");
        keyWords.add("deckName");keyWords.add(deckName);
        keyWords.add("cardName");keyWords.add(sideField.getText());
        keyWords.add("isMainDeck");keyWords.add("false");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }

        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        buildCards();
        return;
    }

    public void handleAddNewCardToMainDeck(ActionEvent actionEvent) throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("addCardToDeck");
        keyWords.add("deckName");keyWords.add(deckName);
        keyWords.add("cardName");keyWords.add(mainField.getText());
        keyWords.add("isMainDeck");keyWords.add("true");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }

        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        buildCards();
        return;
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
    }

    public void handleDelete(ActionEvent actionEvent) throws Exception {
        if(selectedCard == null){
            apiLog.setText("no card selected.");
            apiLog.setTextFill(Color.RED);
            return;
        }
        int id;

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("removeCardFromDeck");
        keyWords.add("deckName");keyWords.add(deckName);
        keyWords.add("isMainDeck");

        if((id=getId(mainCards,selectedCard)) != -1){
            keyWords.add("true");
            keyWords.add("cardName");keyWords.add(mainDeck.getCardName(id));
        }

        else{
            id = getId(sideCards,selectedCard);
            keyWords.add("false");
            keyWords.add("cardName");keyWords.add(sideDeck.getCardName(id));
        }

        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }

        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        buildCards();
        return;
    }

    public int getId(Collection searchIn, Object searchFor){
        int ans = -1;
        for (Object o : searchIn) {
            ans++;
            if(searchFor.equals(o))
                return ans;
        }
        return -1;
    }

}
