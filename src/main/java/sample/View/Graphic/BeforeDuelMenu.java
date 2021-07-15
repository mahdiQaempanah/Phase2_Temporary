package sample.View.Graphic;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.ApiMessage;
import sample.View.GameViewController;

import java.io.IOException;

public class BeforeDuelMenu extends Application {
    private Stage primaryStage;
    private MainMenu myMainView;
    private API api;

    public BeforeDuelMenu(MainMenu myMainView, API api, Stage primaryStage){
        this.primaryStage = primaryStage;
        this.myMainView = myMainView;
        this.api = api;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: Black");
        buildStartDuelComponents(root);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buildStartDuelComponents(AnchorPane root) {
        Label opponentUsernameLabel = new Label();
        opponentUsernameLabel.setText("opponent username:");
        opponentUsernameLabel.setFont(new Font(25));
        opponentUsernameLabel.setTextFill(Color.WHITE);
        opponentUsernameLabel.setLayoutX(650);
        opponentUsernameLabel.setLayoutY(381);

        Label apiLog = new Label();
        apiLog.setFont(new Font(25));
        apiLog.setTextFill(Color.DARKRED);
        apiLog.setLayoutX(650);
        apiLog.setLayoutY(481);

        TextField opponentUsername = new TextField();
        opponentUsername.setLayoutX(opponentUsernameLabel.getLayoutX() + 250);
        opponentUsername.setLayoutY(381);

        Button startDuel = new Button();
        startDuel.setLayoutY(opponentUsername.getLayoutY() + 50);
        startDuel.setLayoutX(opponentUsername.getLayoutX());
        startDuel.setText("start Duel");
        startDuel.setTextFill(Color.WHITE);
        startDuel.setStyle("-fx-background-color: #840099");
        startDuel.setCursor(Cursor.HAND);
        startDuel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject response = new JSONObject();
                    response.put("command","duelNewGame");
                    response.put("opponent",opponentUsername.getText());
                    response = api.run(response);
                    ApiMessage apiMessage = new Gson().fromJson(String.valueOf(response),ApiMessage.class);
                    if(apiMessage.getType().equals(ApiMessage.error)){
                        apiLog.setText(apiMessage.getMessage());
                    }else{
                        startDuel(apiMessage);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        Button back = new Button();
        back.setLayoutY(opponentUsername.getLayoutY() + 50);
        back.setLayoutX(opponentUsername.getLayoutX() + 90);
        back.setText("Back");
        back.setTextFill(Color.WHITE);
        back.setStyle("-fx-background-color: #1c038c");
        back.setCursor(Cursor.HAND);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    myMainView.start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        root.getChildren().addAll(opponentUsernameLabel,opponentUsername,startDuel,back,apiLog);
    }

    private void startDuel(ApiMessage apiMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../Fxml/GameView.fxml"));
        Parent root = loader.load();
        GameViewController controller = (GameViewController) loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        controller.startGame(primaryStage,apiMessage,api,myMainView,primaryStage);
    }
}
