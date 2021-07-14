package sample.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.ApiMessage;
import sample.Model.Game.Phase;
import sample.Model.JsonObject.BoardJson;
import sample.View.Components.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class GameViewController {
    public static final Point2D cardSize = new Point2D(104,120);
    public API api;
    public AnchorPane mainPane;
    public BoardComponent board;
    public GameStatus gameStatus;
    public Button pauseButton;
    public Label gameLogLabel;
    public GameLog gameLog;
    public Label phaseLabel;
    public Button deselectButton;
    public Button nextPhaseButton;
    public Button summonButton;
    public Button setButton;
    public Button attackButton;
    public Rectangle cardInfo;

    private void startGame(Stage stage, ApiMessage response, API api){
        this.api = api;
        board = new BoardComponent(this,mainPane);
        gameLog = new GameLog(gameLogLabel);
        throwingCoin(stage,response);
    }

    private void throwingCoin(Stage stage, ApiMessage response){
        String poshtAddress = getClass().getResource("../Image/coinPosht.png").toExternalForm();
        String rooAddress = getClass().getResource("../Image/coinRoo.png").toExternalForm();
        JSONObject messageOfResponse = new JSONObject(response.getMessage());
        boolean isPlayer2Turn = messageOfResponse.getBoolean("firstTurn");

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
            result.setLayoutX(93);result.setLayoutY(734);

            String labelText = "Player1 start the game.";
            if(!posthInGround)
            result.setText("Player2 start the game.");

            result.setText(labelText);
            result.setTextFill(Color.WHITE);
            result.setStyle("-fx-font-size:20");
            mainPane.getChildren().add(result);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent event) -> {
                mainPane.getChildren().remove(result);
                mainPane.getChildren().remove(coin);
                try {
                    resetBoard();
                    handleFirstPhase();
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

    private void handleFirstPhase() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("add_card_to_hand");
        ApiMessage response = responseFromApi(keyWords);
        JSONObject messageOfResponse = new JSONObject(response.getMessage());
        showNewGameLog(Color.WHITE,messageOfResponse.get("newCard")+" added to activePlayer hand.",2000);

        keyWords.clear();
        keyWords.add("command");keyWords.add("get_board");
        response = responseFromApi(keyWords);
        BoardJson newBoard = new Gson().fromJson(response.getMessage(), BoardJson.class);
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());

        keyWords.clear();
        keyWords.add("command");keyWords.add("nextPhase");
        response = responseFromApi(keyWords);
        Phase nowPhase = new Gson().fromJson(response.getMessage(), Phase.class);
        gameStatus.setPhase(nowPhase);
        phaseLabel.setText("Phase: " + nowPhase);
    }

    public void showNewGameLog(Color color,String message, int showTime) {
        gameLog.add(color,message,showTime);
    }

    private void resetBoard() throws Exception {
        board.reset(getBoard());
    }

    private BoardJson getBoard() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("get_board");
        ApiMessage newBoard = responseFromApi(keyWords);
        return new Gson().fromJson(newBoard.getMessage(), BoardJson.class);
    }

    public ApiMessage responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        JSONObject jsonAns = api.run(message);
        return new Gson().fromJson(String.valueOf(jsonAns),ApiMessage.class);
    }

    public void handleDeselect(ActionEvent actionEvent) {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD){
            gameStatus.reset(actionEvent);
            cardInfo.setFill(Color.BLACK);
        }
    }

    public void handleNextPhase(ActionEvent actionEvent) throws Exception {
        gameStatus.reset(actionEvent);
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("nextPhase");
        ApiMessage response = responseFromApi(keyWords);
        phaseLabel.setText("Phase: " + response.getMessage().replace("_"," ").toLowerCase().toUpperCase(Locale.ROOT));
        Phase phase = new Gson().fromJson(response.getMessage(),Phase.class);
        if(phase == Phase.DRAW_PHASE){
            resetBoard();
            handleFirstPhase();
        }
    }

    public void HandleSummon(ActionEvent actionEvent) throws Exception {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD){
            ArrayList<String> keyWords = new ArrayList<>();
            keyWords.add("command");keyWords.add("summon");
            ApiMessage response = responseFromApi(keyWords);
            if(response.getType().equals(ApiMessage.error)){
                showNewGameLog(Color.DARKRED,response.getMessage(),2000);
                return;
            }

            int tributeCount = (new JSONObject(response.getMessage())).getInt("tribute");
            gameStatus.setGameMode(GameMode.SUMMON_MONSTER_MODE);
            gameStatus.setTributes(new ArrayList<>());
            for(int i = 0 ; i < tributeCount ; i++)
                gameStatus.getTributes().add(null);

            if(tributeCount == 0)
                summonSelectedCard();
        }
    }

    private void summonSelectedCard() throws Exception {
        gameStatus.reset(null);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
    }

    public void handleSetMonster(ActionEvent actionEvent) throws Exception {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD){
            ArrayList<String> keyWords = new ArrayList<>();
            keyWords.add("command");keyWords.add("setMonster");
            ApiMessage response = responseFromApi(keyWords);
            if(response.getType().equals(ApiMessage.error)){
                showNewGameLog(Color.DARKRED,response.getMessage(),2000);
                return;
            }
            int tributeCount = (new JSONObject(response.getMessage())).getInt("tribute");
            gameStatus.setGameMode(GameMode.SET_MONSTER_MODE);
            gameStatus.setTributes(new ArrayList<>());
            for(int i = 0 ; i < tributeCount ; i++)
                gameStatus.getTributes().add(null);
            if(tributeCount == 0)
                setMonsterSelectedCard();
        }
    }

    private void setMonsterSelectedCard() throws Exception {
        gameStatus.reset(null);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
    }

    public void handleAttack(ActionEvent actionEvent) {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD &&
                Arrays.asList(board.getActivePlayer().getMonsterZone()).contains(gameStatus.getSelectedCard())){
            gameStatus.setGameMode(GameMode.ATTACK_MODE);
        }
    }

    public void handleDirectAtk(ActionEvent actionEvent) throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("directAttack");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            showNewGameLog(Color.DARKRED,response.getMessage(),2000);
            return;
        }
        int damage = new JSONObject(response.getMessage()).getInt("damage");
        board.getInactivePlayer().damage(damage);
    }

    public void handleCancel(ActionEvent actionEvent) {
        gameStatus.reset(actionEvent);
    }

    public void handleChangeMonsterMode(ActionEvent actionEvent) throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("changeMonsterMode");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            showNewGameLog(Color.DARKRED,response.getMessage(),2000);
            return;
        }
        gameStatus.reset(null);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
    }

    public void handleSetSpell(ActionEvent actionEvent) {
    }

    public void handleActiveSpell(ActionEvent actionEvent) {
    }

    public void handleEnd(ActionEvent actionEvent) {
    }
}
