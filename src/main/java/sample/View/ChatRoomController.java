package sample.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.View.Graphic.MainMenu;
import sample.View.Graphic.SocketPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomController {
    public AnchorPane mainPane;
    public TextField userMessage;
    private Stage primaryStage;
    public MainMenu myMainMenu;

    public ScrollPane chatContainer;
    public VBox chatBox;

    public void start(Stage stage, MainMenu mainMenu) throws Exception {
        primaryStage = stage;
        myMainMenu = mainMenu;
        reloadChat();
    }

    private void reloadChat() throws IOException {
        ArrayList<String> messages = new Gson().fromJson(responseFromApi("command","getChatMessages").getMessage(),new TypeToken<List<String>>(){}.getType());
        chatBox = new VBox(messages.size());
        chatContainer.setContent(chatBox);
        for (String message : messages) {
            chatBox.getChildren().add(new Label(message));
        }

        new Timeline(new KeyFrame(Duration.millis(1500), (ActionEvent event) -> {
            try {
                reloadChat();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        })).play();
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
    }

    public void handleSendMessage(ActionEvent actionEvent) throws IOException {
        responseFromApi("command","addMessageToChat","message",userMessage.getText());
        userMessage.clear();
    }


    public ApiMessage responseFromApi(String ...keyWords) throws IOException {
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.length ; i+=2)
            message.put(keyWords[i],keyWords[i+1]);
        JSONObject jsonAns = new JSONObject(SocketPackage.getInstance().getResponse(message));
        return new Gson().fromJson(String.valueOf(jsonAns),ApiMessage.class);
    }
}
