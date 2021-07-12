package sample.View;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.BoardJson;


import java.net.URL;
import java.util.ArrayList;

public class GameViewController {
    public static final Point2D cardSize = new Point2D(104,120);
    public API api;
    public AnchorPane mainPane;
    public BoardComponent board;

    private void startGame(Stage stage, JSONObject response, API api){
        this.api = api;
        board = new BoardComponent(mainPane);
    }

    private void ThrowingCoin(Stage stage, JSONObject response){
        String poshtAddress = getClass().getResource("../Image/coinPosht.png").toExternalForm();
        String rooAddress = getClass().getResource("../Image/coinRoo.png").toExternalForm();
        boolean isPlayer2Turn = response.getBoolean("firstTurn");

        Rectangle coin = new Rectangle();
        coin.setWidth(120);coin.setHeight(120);
        coin.setLayoutX(150);coin.setLayoutY(600);
        coin.setFill(new ImagePattern(new Image(poshtAddress)));

        int numberOfCoinRotation = 16;
        if(isPlayer2Turn)
            numberOfCoinRotation++;
        boolean posthInGround = true;
        mainPane.getChildren().add(coin);
        spinningCoin(coin,numberOfCoinRotation,posthInGround,poshtAddress,rooAddress);
    }

    private void spinningCoin(Rectangle coin, int numberOfCoinRotation, boolean posthInGround, String poshtAddress, String rooAddress) {
        if(numberOfCoinRotation == 0){
            Label result = new Label();
            result.setLayoutX(93);
            result.setLayoutY(734);
            String labelText= "Player1 start the game.";
            if(!posthInGround)
            result.setText("Player2 start the game.");
            result.setText(labelText);
            result.setTextFill(Color.WHITE);
            result.setStyle("-fx-font-size:20");
            mainPane.getChildren().add(result);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
                mainPane.getChildren().remove(result);
                mainPane.getChildren().remove(coin);
                try {
                    resetBoard();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }));
            timeline.play();
            return;
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
            if(posthInGround)
                coin.setFill(new ImagePattern(new Image(rooAddress)));
            else
                coin.setFill(new ImagePattern(new Image(poshtAddress)));
            spinningCoin(coin,numberOfCoinRotation-1,!posthInGround,poshtAddress,rooAddress);
        }));
        timeline.play();
    }

    private void resetBoard() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("get_board");
        JSONObject newBoard = responseFromApi(keyWords);
        board.reset(new Gson().fromJson(newBoard.toString(), BoardJson.class));
    }

    public JSONObject responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        return api.run(message);
    }
}
