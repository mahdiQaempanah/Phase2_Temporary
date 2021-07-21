package sample.View;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.DeckGeneralInfo;
import sample.Model.JsonObject.ShowAllDecksJson;
import sample.View.Components.DeckComponent;
import sample.View.Graphic.MainMenu;
import sample.View.Graphic.SocketPackage;

import java.util.ArrayList;
import java.util.Collection;

public class DeckMenuController {
    public AnchorPane mainPane;
    public Label apiLog;
    private int deckInRow = 5;
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public DeckComponent selectedComponent;


    public void start(Stage stage, MainMenu mainMenu) throws Exception {
        this.primaryStage = stage;
        this.myMainMenu = mainMenu;
        addDecks();
    }

    private void addDecks() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("showAllDecks");
        ShowAllDecksJson allDecks = new Gson().fromJson(responseFromApi(keyWords).getMessage(),ShowAllDecksJson.class);

        int id = -1;
        for (DeckGeneralInfo deckInfo : allDecks.decks) {
            System.out.println(deckInfo.name);
            id++;
            DeckComponent deck = new DeckComponent(deckInfo.name,deckInfo.mainDeckSize + deckInfo.sideDeckSize,allDecks.activeDeck!= null && allDecks.activeDeck.name.equals(deckInfo.name),this);
            deck.activeEffect();
            deck.setDeckPosition(new Point2D(100+(id%deckInRow) * (deck.deckComponentSize.getX()+10),100+(id/deckInRow) * (deck.deckComponentSize.getY()+10)));
            deck.setDeckInfoPosition(new Point2D(105+(id%deckInRow) * (deck.deckComponentSize.getX()+10),127+(id/deckInRow) * (deck.deckComponentSize.getY()+10)));
            deck.activeEffect();
            mainPane.getChildren().add(deck.deck);
            mainPane.getChildren().add(deck.deckInfo);
        }
    }

    public void editDeckHandle(ActionEvent actionEvent) throws Exception {
        if(selectedComponent == null){
            apiLog.setText("no deck selected.");
            apiLog.setTextFill(Color.RED);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/DeckCardMenu.fxml"));
        Parent root = loader.load();
        DeckCardMenuController controller = (DeckCardMenuController) loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        controller.start(selectedComponent.getDeckName(),primaryStage,myMainMenu);
    }

    public void deleteDeckHandle(ActionEvent actionEvent) throws Exception {
        if(selectedComponent == null){
            apiLog.setText("no deck selected.");
            apiLog.setTextFill(Color.RED);
            return;
        }
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("deleteDeck");
        keyWords.add("deckName");keyWords.add(selectedComponent.getDeckName());
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }
        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
            try {
                myMainMenu.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));
        timeline.play();
    }

    public void handleCreateDeck(ActionEvent actionEvent) {
        apiLog.setText("write the deck name, and then press button:");
        apiLog.setTextFill(Color.WHITE);

        TextField textField = new TextField();
        textField.setLayoutX(1220);textField.setLayoutY(776);

        Button button = new Button("create:");
        button.setLayoutX(1220);button.setLayoutY(736);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ArrayList<String> keyWords = new ArrayList<>();
                keyWords.add("command");keyWords.add("createDeck");
                keyWords.add("deckName");keyWords.add(textField.getText());
                try {
                    ApiMessage response = responseFromApi(keyWords);
                    if(response.getType().equals(ApiMessage.error)){
                        apiLog.setText(response.getMessage());
                        apiLog.setTextFill(Color.RED);
                        return;
                    }
                    apiLog.setText(response.getMessage());
                    apiLog.setTextFill(Color.GREEN);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent eventt) -> {
                        try {
                            myMainMenu.start(primaryStage);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }));
                    timeline.play();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        mainPane.getChildren().add(textField);
        mainPane.getChildren().add(button);

    }

    public void handleSetActiveDeck(ActionEvent actionEvent) throws Exception {
        if(selectedComponent == null){
            apiLog.setText("no deck selected.");
            apiLog.setTextFill(Color.RED);
            return;
        }

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("setDeckActivate");
        keyWords.add("deckName");keyWords.add(selectedComponent.getDeckName());
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            apiLog.setText(response.getMessage());
            apiLog.setTextFill(Color.RED);
            return;
        }
        apiLog.setText(response.getMessage());
        apiLog.setTextFill(Color.GREEN);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
            try {
                myMainMenu.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));
        timeline.play();
    }



    public void clickDeck(DeckComponent deck) {
        if(selectedComponent != null)
            selectedComponent.activeEffect();
        selectedComponent = deck;
        deck.inactiveEffect();
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
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
