package sample.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.CardGeneralInfo;
import sample.View.Graphic.MainMenu;
import sample.View.Graphic.SocketPackage;

import java.util.ArrayList;
import java.util.List;

public class ShopMenuController {
    public static final Point2D cardSize = new Point2D(120,145);
    public Rectangle selectedCard;
    public int inRow = 13;
    public Button buyButton;
    public Label apiLog;
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public AnchorPane mainPane;

    private ArrayList<CardGeneralInfo> cardsInf0;
    private ArrayList<Rectangle> cards;
    
    public void start(Stage stage, MainMenu myMainMenu) throws Exception {
        cards = new ArrayList<>();
        this.myMainMenu = myMainMenu;
        this.primaryStage = stage;
        addCards();
        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    buyCard();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void buyCard() throws Exception {
        if(selectedCard == null){
            apiLog.setText("no card selected.");
            apiLog.setTextFill(Color.RED);
            return;
        }

        int id = -1;
        for (Rectangle card : cards) {
            id++;
            if(card == selectedCard)
                break;
        }

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("buyCard");
        keyWords.add("cardName");keyWords.add(cardsInf0.get(id).getName());
        ApiMessage response = responseFromApi(keyWords);

        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }

        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        return;
    }

    private void addCards() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("shopShowAll");
        cardsInf0 = new Gson().fromJson(responseFromApi(keyWords).getMessage(),new TypeToken<List<CardGeneralInfo>>(){}.getType());
        int id = -1;

        for(int i = 0 ; i < cardsInf0.size() ; i++){
            if(getCardPictureAddress(cardsInf0.get(i).getName()) == null){
                cardsInf0.remove(i);
                i--;
            }
        }

        for (CardGeneralInfo cardInfo : cardsInf0) {
            String cardAddress = getCardPictureAddress(cardInfo.getName());
            id++;
            Rectangle card = new Rectangle(cardSize.getX(),cardSize.getY());
            card.setLayoutX(65 + (id%inRow)*cardSize.getX());card.setLayoutY(65 + (id/inRow)*cardSize.getY());
            card.setFill(new ImagePattern(new Image(cardAddress)));

            card.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    card.setStyle("-fx-effect: dropshadow(three-pass-box,  #ffffff, 50, 0.6, 0, 0)");
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

            cards.add(card);
            mainPane.getChildren().add(card);
        }
        assert cardsInf0.size() == cards.size();
    }

    private void clickCard(Rectangle card, MouseEvent event) throws Exception {
        if(selectedCard != null){
            selectedCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    card.setStyle("-fx-effect: dropshadow(three-pass-box,  #ffffff, 50, 0.6, 0, 0)");
                }
            });

            selectedCard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    card.setStyle(null);
                }
            });

            selectedCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        clickCard(card,event);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });

            selectedCard.setStyle(null);
        }
        buyButton.setDisable(false);
        card.setOnMouseEntered(null);
        card.setOnMouseExited(null);
        card.setOnMouseClicked(null);
        card.setStyle("-fx-effect: dropshadow(three-pass-box,  #00c832, 50, 0.6, 0, 0)");
        selectedCard = card;
        attemptToDisableBuyButton();
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
    }

    private void attemptToDisableBuyButton() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("getMoney");
        int money = new JSONObject(responseFromApi(keyWords).getMessage()).getInt("money");
        int id = -1;
        for (Rectangle card : cards) {
            id++;
            if(card == selectedCard);
        }

        if(money < cardsInf0.get(id).getPrice())
            buyButton.setDisable(true);
    }

    public ApiMessage responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        JSONObject jsonAns = new JSONObject(SocketPackage.getInstance().getResponse(message));
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
}
