package sample.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.RequestForPlayGame;
import sample.View.Graphic.MainMenu;
import sample.View.Graphic.SocketPackage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeforeDuelMenuController {
    public Label apiLog;
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public AnchorPane mainPane;
    public Button startButton;
    public TextField opponentField;
    public VBox opponentContainer;


    public void start(MainMenu mainMenu, Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.myMainMenu = mainMenu;
        addWaitingOpponents();
    }

    private void addWaitingOpponents() throws IOException {
        JSONObject request = new JSONObject().put("command","getOpponentsWaiting");
        ArrayList<RequestForPlayGame> opponents = new Gson().fromJson(new JSONObject(SocketPackage.getInstance().getResponse(request)).getString("message"),new TypeToken<List<RequestForPlayGame>>(){}.getType());
        for (RequestForPlayGame opponent : opponents) {
            Button opponentButton = new Button(opponent.getUserWantPlayGame());
            opponentButton.setCursor(Cursor.HAND);
            opponentButton.setTextFill(Color.WHITE);
            opponentButton.setStyle("-fx-background-color: #a58b15");
            opponentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        handleStartGame(opponent.getUserWantPlayGame());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
            opponentContainer.getChildren().add(opponentButton);
        }
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
    }

    public void handleStartGame(ActionEvent actionEvent) throws Exception {
        JSONObject request = new JSONObject();
        JSONObject response;

        if(opponentField.getText() == null || opponentField.getText().isEmpty()){
            request.put("command","addNewOpponentRequest");
            request.put("opponentName","");
        }
        else {
            request.put("command","addNewOpponentRequest");
            request.put("opponentName",opponentField.getText());
        }

        ApiMessage result = new Gson().fromJson(SocketPackage.getInstance().getResponse(request),ApiMessage.class);
        response = new JSONObject(result.getMessage());

        if(response.getBoolean("gameCreate")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/GameView.fxml"));
            Parent root = loader.load();
            GameViewController controller = (GameViewController) loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            controller.startGame(response.getInt("gameId"),primaryStage,myMainMenu);
        }
        else if(!response.isNull("detail")){
            apiLog.setText(response.getString("detail"));
            apiLog.setTextFill(Color.RED);
            Thread.sleep(1500);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/BeforeDuel.fxml"));
            Parent root = loader.load();
            BeforeDuelMenuController controller = (BeforeDuelMenuController) loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            controller.start(myMainMenu,primaryStage);
        }
        else{
            handleLoadingView();
        }
    }

    private void handleLoadingView() throws Exception {
        Rectangle blackReq = new Rectangle(0,0,1950,1030);
        blackReq.setStyle("-fx-background-color: black");
        mainPane.getChildren().add(blackReq);

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("../../Image/loading.mp4").toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setLayoutX(810);mediaView.setLayoutY(400);
        mediaView.setFitHeight(290);mediaView.setFitWidth(290);
        mainPane.getChildren().add(mediaView);

        Button cancelButton = new Button("cancel");
        cancelButton.setLayoutX(900);cancelButton.setLayoutY(730);
        cancelButton.setPrefHeight(56);cancelButton.setPrefWidth(111);
        cancelButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject request = new JSONObject().put("command","deleteGameRequest");
                    SocketPackage.getInstance().getResponse(request);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/BeforeDuel.fxml"));
                    Parent root = loader.load();
                    BeforeDuelMenuController controller = (BeforeDuelMenuController) loader.getController();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                    controller.start(myMainMenu,primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        mainPane.getChildren().add(cancelButton);
        checkStillLoading();
        JSONObject request = new JSONObject().put("command","getIsGameStartWithMe");
        ApiMessage result = new Gson().fromJson(SocketPackage.getInstance().getResponse(request),ApiMessage.class);
        JSONObject response = new JSONObject(result.getMessage());
        if(response.getBoolean("isGameStart")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/GameView.fxml"));
            Parent root = loader.load();
            GameViewController controller = (GameViewController) loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            controller.startGame(response.getInt("gameId"),primaryStage,myMainMenu);
        }
    }

    private void checkStillLoading() {
        System.out.println("hah");
        new Timeline(new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
            JSONObject request = new JSONObject().put("command","getIsGameStartWithMe");
            ApiMessage result = null;
            try {
                result = new Gson().fromJson(SocketPackage.getInstance().getResponse(request), ApiMessage.class);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            JSONObject response = new JSONObject(result.getMessage());
            if(!response.getBoolean("isGameStart"))
                checkStillLoading();
        })).play();
    }

    private void handleStartGame(String userWantPlayGame) throws Exception {
        JSONObject request = new JSONObject();
        request.put("command","startGameWithDefinedUser");
        request.put("opponent",userWantPlayGame);
        JSONObject response = new JSONObject( SocketPackage.getInstance().getResponse(request));

        if(response.getString("type").equals(ApiMessage.error)){
            apiLog.setText(response.getString("message"));
            apiLog.setTextFill(Color.RED);
            new Timeline(new KeyFrame(Duration.millis(1500), (ActionEvent event) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/BeforeDuel.fxml"));
                    Parent root = loader.load();
                    BeforeDuelMenuController controller = (BeforeDuelMenuController) loader.getController();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                    controller.start(myMainMenu,primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            })).play();
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Fxml/GameView.fxml"));
            Parent root = loader.load();
            GameViewController controller = (GameViewController) loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            controller.startGame(new JSONObject(response.getString("message")).getInt("gameId"),primaryStage,myMainMenu);
        }
    }


}
