package sample.View;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.DeckGeneralInfo;
import sample.Model.JsonObject.ShowAllDecksJson;
import sample.View.Components.DeckComponent;
import sample.View.Graphic.MainMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class DeckMenuController {
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public API api;
    public AnchorPane mainPane;

    public void start(Stage stage, MainMenu mainMenu, API api) throws Exception {
        this.primaryStage = stage;
        this.mainPane = mainPane;
        this.api = api;
        addDecks();
    }

    private void addDecks() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("showAllDecks");
        ShowAllDecksJson allDecks = new Gson().fromJson(responseFromApi(keyWords).getMessage(),ShowAllDecksJson.class);

        for (DeckGeneralInfo deckInfo : allDecks.decks) {
            DeckComponent deck = new DeckComponent();
        }
    }


    public void editDeckHandle(ActionEvent actionEvent) {
    }

    public void deleteDeckHandle(ActionEvent actionEvent) {
    }

    public void setCreateDeck(ActionEvent actionEvent) {
    }

    public void handleSetActiveDeck(ActionEvent actionEvent) {
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
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
