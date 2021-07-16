package sample.View;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
import sample.View.Graphic.MainMenu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class GameViewController {
    public static final Point2D cardSize = new Point2D(104,120);
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public API api;
    public AnchorPane mainPane;

    public BoardComponent board;
    public GameStatus gameStatus;
    public Button pauseButton;

    public Label gameLogLabel;
    public GameLog gameLog;
    public Label phaseLabel;
    public Rectangle cardInfo;

    public void startGame(Stage stage, ApiMessage response, API api, MainMenu mainMenu, Stage primaryStage){
        this.myMainMenu = mainMenu;
        this.primaryStage = primaryStage;
        this.api = api;
        board = new BoardComponent(this,mainPane);
        gameLog = new GameLog(gameLogLabel);
        gameStatus = new GameStatus(mainPane,this);
        throwingCoin(stage,response);
    }

    private void throwingCoin(Stage stage, ApiMessage response){
        String poshtAddress = getClass().getResource("../../Image/coinPosht.png").toExternalForm();
        String rooAddress = getClass().getResource("../../Image/coinRoo.png").toExternalForm();
        JSONObject messageOfResponse = new JSONObject(response.getMessage());
        boolean isPlayer2Turn = messageOfResponse.getBoolean("firstTurn");

        Rectangle coin = new Rectangle();
        coin.setWidth(120);coin.setHeight(120);
        coin.setLayoutX(150);coin.setLayoutY(400);
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
            result.setLayoutX(110);result.setLayoutY(534);

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
        checkGameOver();
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("add_card_to_hand");
        ApiMessage response = responseFromApi(keyWords);
        JSONObject messageOfResponse = new JSONObject(response.getMessage());
        showNewGameLog(Color.GREEN,messageOfResponse.get("newCard")+" added to activePlayer hand.",3000);

        keyWords.clear();
        keyWords.add("command");keyWords.add("get_board");
        response = responseFromApi(keyWords);
        BoardJson newBoard = new Gson().fromJson(response.getMessage(), BoardJson.class);
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        handleNextPhase();
    }


    public void handleDeselect(ActionEvent actionEvent) {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD){
            gameStatus.reset(actionEvent);
            cardInfo.setFill(Color.BLACK);
        }
    }

    public void handleNextPhaseButton(ActionEvent actionEvent) throws Exception {
        handleNextPhase();
    }

    private void handleNextPhase() throws Exception {
        gameStatus.reset(null);
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("nextPhase");
        ApiMessage response = responseFromApi(keyWords);
        phaseLabel.setText("Phase: " + response.getMessage().substring(1,response.getMessage().length()-1).replace("_"," ").toLowerCase().toLowerCase());
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
                showNewGameLog(Color.RED,response.getMessage(),2000);
                return;
            }

            int tributeCount = (new JSONObject(response.getMessage())).getInt("tribute");
            gameStatus.setGameMode(GameMode.SUMMON_MONSTER_MODE);
            gameStatus.setTributes(new ArrayList<>());
            for(int i = 0 ; i < tributeCount ; i++)
                gameStatus.getTributes().add(null);

            if(tributeCount == 0)
                summonSelectedCard(true);
        }
    }

    public void handleSetMonster(ActionEvent actionEvent) throws Exception {
        if(gameStatus.getGameMode() == GameMode.SELECTED_CARD){
            ArrayList<String> keyWords = new ArrayList<>();
            keyWords.add("command");keyWords.add("setMonster");
            ApiMessage response = responseFromApi(keyWords);
            if(response.getType().equals(ApiMessage.error)){
                showNewGameLog(Color.RED,response.getMessage(),2000);
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
            showNewGameLog(Color.RED,response.getMessage(),2000);
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
            showNewGameLog(Color.RED,response.getMessage(),2000);
            return;
        }
        showNewGameLog(Color.GREEN,"monster mode changed.",3000);
        gameStatus.reset(null);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
    }

    public void handleSetSpell(ActionEvent actionEvent) throws Exception {
        if(gameStatus.getGameMode() != GameMode.SELECTED_CARD)
            return;

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("setSpell");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            showNewGameLog(Color.RED,response.getMessage(),3000);
            return;
        }
        showNewGameLog(Color.GREEN,"spell was set.",3000);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        board.getActivePlayer().buildSpellZone(newBoard.getActivePlayer().getSpellZone(),ActivePlayerCardsCoordinates.spellZone);
    }

    public void handleActiveSpell(ActionEvent actionEvent) throws Exception {
        if(gameStatus.getGameMode() != GameMode.SELECTED_CARD)
            return;

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("activeEffect");
        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            showNewGameLog(Color.RED,response.getMessage(),3000);
            return;
        }
        showNewGameLog(Color.GREEN,"spell was activated.",3000);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        board.getActivePlayer().buildSpellZone(newBoard.getActivePlayer().getSpellZone(),ActivePlayerCardsCoordinates.spellZone);
    }


    public void summonSelectedCard(boolean withoutTributes) throws Exception {
        if(withoutTributes){
            showNewGameLog(Color.GREEN,"monster was summoned.",3000);
            gameStatus.reset(null);
            BoardJson newBoard = getBoard();
            board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
            board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
            return;
        }

        Integer address1 = null;Integer address2 = null;
        if(gameStatus.getTributes().size() >= 1)
            address1 = board.getActivePlayer().getId(Arrays.asList(board.getActivePlayer().getMonsterZone()),gameStatus.getTributes().get(0));
        if(gameStatus.getTributes().size() >= 2)
            address2 = board.getActivePlayer().getId(Arrays.asList(board.getActivePlayer().getMonsterZone()),gameStatus.getTributes().get(1));

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("tribute");
        keyWords.add("numberOfTributes");keyWords.add(String.valueOf(gameStatus.getTributes().size()));
        if(address1 != null)
            keyWords.add("address1");keyWords.add(String.valueOf(address1));
        if(address2 != null)
            keyWords.add("address2");keyWords.add(String.valueOf(address2));

        ApiMessage response = responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            showNewGameLog(Color.RED,response.getMessage(),3000);
            gameStatus.reset(null);
            return;
        }else{
            showNewGameLog(Color.GREEN,"monster was summoned.",3000);
            gameStatus.reset(null);
            BoardJson newBoard = getBoard();
            board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
            board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
        }
    }

    public void setMonsterSelectedCard() throws Exception {
        showNewGameLog(Color.GREEN,"monster was set.",3000);
        gameStatus.reset(null);
        BoardJson newBoard = getBoard();
        board.getActivePlayer().buildHand(newBoard.getActivePlayer().getHand());
        board.getActivePlayer().buildMonsterZone(newBoard.getActivePlayer().getMonsterZone(), ActivePlayerCardsCoordinates.monsterZone);
    }

    public void checkGameOver() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("isGameOver");
        ApiMessage response = responseFromApi(keyWords);
        JSONObject message = new JSONObject(response.getMessage());
        if(message.getBoolean("isOver")){
            showNewGameLog(Color.GOLD,message.getString("winner")+" win the match. (•ᴗ•)",6000);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(6000), (ActionEvent event) -> {
                try {
                    myMainMenu.start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }));
            timeline.play();
        }
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

        cardAddress = "../../Image/Cards/" + cardAddress + ".jpg";
        return  getClass().getResource(cardAddress).toExternalForm();
    }

    public void handlePause(ActionEvent actionEvent) {
        GameMode nowMode = gameStatus.getGameMode();
        gameStatus.setGameMode(GameMode.CANT_SELECT_CARD);

        Rectangle hideButtons = new Rectangle(418,179);
        hideButtons.setLayoutX(0);hideButtons.setLayoutY(796);
        hideButtons.setFill(Color.BLACK);
        mainPane.getChildren().add(hideButtons);

        VBox container = new VBox();
        Button endGame = new Button("End game");
        Button backToGame = new Button("continue playing.");
        container.setStyle("-fx-background-color: Blue; -fx-effect:  dropshadow(three-pass-box,  Blue, 50, 0.6, 0, 0)");
        container.getChildren().addAll(endGame,backToGame);
        mainPane.getChildren().add(container);

        backToGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPane.getChildren().removeAll(container,hideButtons);
                gameStatus.setGameMode(nowMode);
            }
        });

        endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    myMainMenu.start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void resetBoard() throws Exception {
        board.reset(getBoard());
    }

    public BoardJson getBoard() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("get_board");
        ApiMessage newBoard = responseFromApi(keyWords);
        return new Gson().fromJson(newBoard.getMessage(), BoardJson.class);
    }

    public void showNewGameLog(Color color,String message, int showTime) {
        gameLog.add(color,message,showTime);
    }

    public ApiMessage responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        JSONObject jsonAns = api.run(message);
        return new Gson().fromJson(String.valueOf(jsonAns),ApiMessage.class);
    }
}

