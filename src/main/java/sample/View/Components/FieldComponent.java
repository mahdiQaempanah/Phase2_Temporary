package sample.View.Components;

import com.google.gson.Gson;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.Game.Card.MonsterCard.Mode;
import sample.Model.Game.Card.Status;
import sample.Model.JsonObject.AttackInfo;
import sample.Model.JsonObject.BoardJson;
import sample.Model.JsonObject.CardBoardInfo;
import sample.Model.JsonObject.FieldJson;
import sample.View.GameViewController;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FieldComponent {
    public static final Point2D cardSize = new Point2D(104,120);
    private AnchorPane mainPane;
    private GameViewController myController;
    private Label userInfo;
    private Rectangle[] monsterZone;
    private Rectangle[] spellZone;
    private Rectangle fieldZone;
    private ArrayList<Rectangle> hand;
    private Label graveYardSize;
    private Label deckZoneSize;
    private boolean isActivePlayer;

    public FieldComponent(GameViewController controller,AnchorPane mainPane,boolean isActivePlayer){
        this.myController = controller;
        this.mainPane = mainPane;
        this.isActivePlayer = isActivePlayer;
        monsterZone = new Rectangle[5];
        spellZone = new Rectangle[5];
        hand = new ArrayList<>();
    }

    public void build(FieldJson field) {
        if(isActivePlayer){
            buildMonsterZone(field.getMonsterZone(),ActivePlayerCardsCoordinates.monsterZone);
            buildSpellZone(field.getSpellZone(),ActivePlayerCardsCoordinates.spellZone);
            buildGraveYard(field.getGraveyardSize(),ActivePlayerCardsCoordinates.graveYard);
            buildDeckZone(field.getDeckSize(),ActivePlayerCardsCoordinates.deckZone);
            buildUserInfo(field.getNickName(),field.getLife(),ActivePlayerCardsCoordinates.userInfo);
            buildFieldZone(field.getFieldZone(),ActivePlayerCardsCoordinates.fieldZone);
            buildHand(field.getHand());
        }else{
            buildMonsterZone(field.getMonsterZone(),InactivePlayerCardsCoordinates.monsterZone);
            buildSpellZone(field.getSpellZone(),InactivePlayerCardsCoordinates.spellZone);
            buildGraveYard(field.getGraveyardSize(),InactivePlayerCardsCoordinates.graveYard);
            buildDeckZone(field.getDeckSize(),InactivePlayerCardsCoordinates.deckZone);
            buildUserInfo(field.getNickName(),field.getLife(),InactivePlayerCardsCoordinates.userInfo);
            buildFieldZone(field.getFieldZone(),InactivePlayerCardsCoordinates.fieldZone);
           buildHand(field.getHand());
        }
    }

    public void buildMonsterZone(CardBoardInfo[] monsters, Point2D[] monsterZone) {
        for (Rectangle monster : this.monsterZone) {
            if(monster != null)
                mainPane.getChildren().remove(monster);
            monster = null;
        }

        for(int i = 0 ; i < 5 ; i++){
            CardBoardInfo monster = monsters[i];
            if(monster != null){
                System.out.println("not null");
                if(monster.getStatus() == Status.SET){
                    this.monsterZone[i] = addHorizontalCardToPane("CardToBack",monsterZone[i]);
                }
                else if(monster.getMode() == Mode.DEFENSE){
                    this.monsterZone[i] = addHorizontalCardToPane(monster.getName(),monsterZone[i]);
                }
                else if(monster.getMode() == Mode.ATTACK){
                    this.monsterZone[i] = addCardToPane(monster.getName(),monsterZone[i]);
                }
            }
        }
    }

    public void buildSpellZone(CardBoardInfo[] spells, Point2D[] spellZone) {
        for (Rectangle spell : this.spellZone) {
            if(spell != null)
                mainPane.getChildren().remove(spell);
            spell = null;
        }

        for(int i = 0 ; i < 5 ; i++){
            CardBoardInfo spell = spells[i];
            if(spell != null){
                if(spell.getStatus() == Status.SET){
                    this.spellZone[i] = addCardToPane("CardToBack",spellZone[i]);
                }
                else
                    this.spellZone[i] = addCardToPane(spell.getName(),spellZone[i]);
            }
        }
    }

    public void buildGraveYard(int graveYardSizeInt, Point2D graveYard) {
        if(this.graveYardSize != null){
            mainPane.getChildren().remove(this.graveYardSize);
            this.graveYardSize = null;
        }

        graveYardSize = new Label();
        //graveYardSize.setLayoutX(442);graveYardSize.setLayoutY(380);
        graveYardSize.setLayoutX(graveYard.getX());graveYardSize.setLayoutY(graveYard.getY());
        graveYardSize.setPrefWidth(102);graveYardSize.setPrefHeight(52);
        graveYardSize.setAlignment(Pos.CENTER);
        graveYardSize.setTextFill(Color.WHITE);
        graveYardSize.setFont(Font.font("Webdings",35));
        graveYardSize.setText("+"+graveYardSizeInt);
        graveYardSize.setCursor(Cursor.HAND);
        graveYardSize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ShowGraveYard();
            }
        });

        mainPane.getChildren().add(graveYardSize);
    }

    public void buildDeckZone(int deckSize, Point2D deckZone) {
        if(this.deckZoneSize != null){
            mainPane.getChildren().remove(this.deckZoneSize);
            this.deckZoneSize = null;
        }
        deckZoneSize = new Label();
        deckZoneSize.setLayoutX(deckZone.getX());deckZoneSize.setLayoutY(deckZone.getY());
        deckZoneSize.setPrefWidth(102);deckZoneSize.setPrefHeight(52);
        deckZoneSize.setAlignment(Pos.CENTER);
        deckZoneSize.setTextFill(Color.WHITE);
        deckZoneSize.setFont(Font.font("Webdings",35));
        deckZoneSize.setText("+"+deckSize);
        mainPane.getChildren().add(deckZoneSize);
    }

    public void buildUserInfo(String nickName, int life, Point2D userInfo) {
        if(this.userInfo != null)
            mainPane.getChildren().remove(this.userInfo);

        this.userInfo = new Label();
        this.userInfo.setPrefWidth(1000);
        this.userInfo.setPrefHeight(1000);
        this.userInfo.setLayoutX(userInfo.getX());this.userInfo.setLayoutY(userInfo.getY());
        this.userInfo.setPrefWidth(102);this.userInfo.setPrefHeight(52);
        this.userInfo.setTextFill(Color.WHITE);
        this.userInfo.setFont(Font.font("Webdings",15));
        this.userInfo.setText("nickname: " + nickName + "\nLp: " + life);

        mainPane.getChildren().add(this.userInfo);
    }

    public void buildFieldZone(CardBoardInfo fieldZoneInfo, Point2D fieldZoneCoordinates) {
        if(fieldZone != null){
            mainPane.getChildren().remove(fieldZone);
            fieldZone = null;
        }
        if(fieldZoneInfo != null)
            fieldZone = addCardToPane(fieldZoneInfo.getName(),fieldZoneCoordinates);
    }

    public void buildHand(ArrayList<CardBoardInfo> hand){
        ArrayList<String> handCardsName = new ArrayList<>();

        for (Rectangle card : this.hand) {
            if(card != null)
                mainPane.getChildren().remove(card);
            card = null;
        }
        this.hand.clear();

        if(isActivePlayer){
            for(int i = 0 ; i < hand.size() ; i++)
                this.hand.add(addCardToPane(hand.get(i).getName(),new Point2D(ActivePlayerCardsCoordinates.startHand.getX()+i*(cardSize.getX()*0.66),
                        ActivePlayerCardsCoordinates.startHand.getY())));
        }else{
            for(int i = 0 ; i < hand.size() ; i++)
                this.hand.add(addCardToPane("CardToBack",new Point2D(InactivePlayerCardsCoordinates.startHand.getX()+i*(cardSize.getX()*0.66),
                        InactivePlayerCardsCoordinates.startHand.getY())));
        }
    }

    private void ShowGraveYard() {
    }

    private Rectangle addCardToPane(String cardName,Point2D coordinates){
        Rectangle card = new Rectangle();
        card.setWidth(cardSize.getX());
        card.setHeight(cardSize.getY());
        card.setLayoutX(coordinates.getX());
        card.setLayoutY(coordinates.getY());
        card.setFill(new ImagePattern(new Image(myController.getCardPictureAddress(cardName))));
        activateCard(card);
        mainPane.getChildren().add(card);
        return card;
    }

    private Rectangle addHorizontalCardToPane(String cardName, Point2D coordinates) {
        System.out.println(cardName);
        Rectangle card = new Rectangle();
        card.setWidth(80);
        card.setHeight(156);
        card.setLayoutX(coordinates.getX()-24+156);
        card.setLayoutY(coordinates.getY()+18);
        card.setFill(new ImagePattern(new Image(myController.getCardPictureAddress(cardName))));
        activateCard(card);
        Rotate rotate = new Rotate();
        rotate.setAngle(90);
        card.getTransforms().addAll(rotate);
        mainPane.getChildren().add(card);
        return card;
    }

    private void clickCard(Rectangle card, MouseEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if(!isActivePlayer && hand.contains(card)){
            myController.showNewGameLog(Color.INDIANRED,"you cant select opponent hands card.",3000);
            return;
        }

        switch (myController.gameStatus.getGameMode()) {
            case NONE:
                selectCard(card,event);
                break;
            case SELECTED_CARD:
                myController.showNewGameLog(Color.RED,"you already select card.", 2000);
                break;
            case CANT_SELECT_CARD:
                break;
            case ATTACK_MODE:
                attemptToAttack(card);
                break;
            case SET_MONSTER_MODE:
            case SUMMON_MONSTER_MODE:
                if(!isActivePlayer || !Arrays.asList(monsterZone).contains(card))
                    break;
                disableCard(card);
                card.setStyle("-fx-effect: dropshadow(three-pass-box,  #c80000, 50, 0.6, 0, 0)");
                for (Rectangle tribute : myController.gameStatus.getTributes()) {
                    if(tribute == null){
                        tribute = card;
                        break;
                    }
                }
                if(!myController.gameStatus.getTributes().contains(null)){
                    if(myController.gameStatus.getGameMode() == GameMode.SUMMON_MONSTER_MODE)
                        myController.summonSelectedCard(false);
                    else
                        myController.setMonsterSelectedCard();
                }

                break;

        }

    }

    private void selectCard(Rectangle card, MouseEvent event) throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("selectCard");
        String zone = "";
        int id = 0;

        if((id = getId(hand,card)) != -1)
            zone = "hand_zone";

        else if((id = getId(Arrays.asList(monsterZone),card)) != -1)
            zone = "monster_zone";

        else if((id = getId(Arrays.asList(spellZone),card)) != -1)
            zone = "spell_zone";

        else if(fieldZone == card)
            zone = "field_zone";

        keyWords.add("zone");keyWords.add(zone);
        keyWords.add("id");keyWords.add(String.valueOf(id));
        keyWords.add("isActivePlayer");keyWords.add(String.valueOf(isActivePlayer));
        ApiMessage response = myController.responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            myController.showNewGameLog(Color.RED,response.getMessage(),3000);
        }else{
            myController.showNewGameLog(Color.DARKGREEN,"card selected.",3000);
            myController.gameStatus.setSelectedCard(card);
            myController.gameStatus.setGameMode(GameMode.SELECTED_CARD);
            disableCard(card);
            card.setStyle("-fx-effect: dropshadow(three-pass-box,  #7216cb, 50, 0.6, 0, 0)");
            showCardInfo((String) new JSONObject(response.getMessage()).get("cardName"));
        }
    }

    private void attemptToAttack(Rectangle card) throws Exception {
        int id;
        if((id=getId(Arrays.asList(myController.board.getInactivePlayer().getMonsterZone()),card)) == -1){
            myController.showNewGameLog(Color.RED,"invalid card selected.",2000);
            return;
        }

        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("attack");
        keyWords.add("id");keyWords.add(String.valueOf(id));

        ApiMessage response = myController.responseFromApi(keyWords);
        if(response.getType().equals(ApiMessage.error)){
            myController.showNewGameLog(Color.RED, response.getMessage(), 2000);
            return;
        }

        AttackInfo attackInfo = new Gson().fromJson(response.getMessage(),AttackInfo.class);
        if(!attackInfo.looserCardName.isEmpty()){
            myController.showNewGameLog(Color.INDIANRED, attackInfo.looserPlayer + " lost " + attackInfo.looserCardName+".", 2000);
        }
        if(attackInfo.decreaseLpForLooser != 0){
            myController.showNewGameLog(Color.INDIANRED, attackInfo.decreaseLpForLooser + " decrease Lp for " + attackInfo.looserPlayer+".", 2000);
        }
        myController.gameStatus.reset(null);
        myController.board.reset(myController.getBoard());
        myController.checkGameOver();
    }

    private void showCardInfo(String cardName) {
        myController.cardInfo.setFill(new ImagePattern(new Image(myController.getCardPictureAddress(cardName))));
        //Todo: fix this
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

    public void activateCard(Rectangle card){
        card.setEffect(null);
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
    }

    private void disableCard(Rectangle card) {
        card.setOnMouseEntered(null);
        card.setOnMouseExited(null);
        card.setOnMouseClicked(null);
    }

    public void damage(int damage) throws Exception {
        myController.showNewGameLog(Color.RED,"inactive player damaged " + damage,3000);
        BoardJson newBoard = myController.getBoard();
        buildUserInfo(newBoard.getInActivePlayer().getNickName(),newBoard.getInActivePlayer().getLife(),InactivePlayerCardsCoordinates.userInfo);
        myController.checkGameOver();
    }

    public static Point2D getCardSize() {
        return cardSize;
    }

    public Rectangle[] getMonsterZone() {
        return monsterZone;
    }

    public Rectangle[] getSpellZone() {
        return spellZone;
    }

    public Rectangle getFieldZone() {
        return fieldZone;
    }

    public ArrayList<Rectangle> getHand() {
        return hand;
    }
}
