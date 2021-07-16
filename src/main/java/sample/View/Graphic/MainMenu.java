package sample.View.Graphic;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Controller.API;


import java.io.FileInputStream;
import java.io.InputStream;

public class MainMenu extends Application {
    static private Stage primaryStage;
    public Scene myScene;
    public String nickname;
    public Button logOutButton = new Button("logout");
    public Label title = new Label();
    public HBox optionsBox = new HBox();
    public String username;
    public WelcomeMenu myWelcomeMenu;
    public API api;


    //  public CardMenu cardMenu=new CardMenu();
    //  public ProfileMenu profileMenu=new ProfileMenu();
    //  public ScoreBoardMenu scoreBoardMenu=new ScoreBoardMenu();
    //  public ShopMenu shopMenu=new ShopMenu();

    public MainMenu(String username, String nickname, API api) {
        this.username = username;
        this.nickname = nickname;
        this.api = api;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(myScene != null){
            primaryStage.setScene(myScene);
            primaryStage.show();
            return;
        }

        MainMenu.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        root.setBottom(logOutButton);
        root.setCenter(optionsBox);
        title.setText("Main");

        buildBackground(root);
        buildLogo(root);
        buildOptionsBox();
        buildShopButton(root);
        buildCardButton(root);
        buildScoreBoardButton(root);
        buildProfileButton(root);
        buildDuelButton(root);
        buildLogoutButton();
        buildImportAndExportButton(root);

        myScene = new Scene(root);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void buildBackground(BorderPane root) {
        Image backGroundImage = new Image(getClass().getResource("..\\..\\..\\Assets\\1300534.jpg").toExternalForm());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                false, true, false);
        root.setBackground(new Background(new BackgroundImage(backGroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }

    private void buildOptionsBox() {
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setSpacing(20);
        optionsBox.setPadding(new Insets(10, 10, 10, 10));
        optionsBox.setStyle("-fx-background-color: lightblue;-fx-max-height: 120;-fx-max-width: 120");
        BorderPane.setAlignment(optionsBox, Pos.CENTER);
    }

    private void buildLogo(BorderPane root) {
        Image image = new Image(getClass().getResource("..\\..\\..\\Assets\\Logos\\_images_text_yugioh.dds2.png").toExternalForm());
        ImageView i = new ImageView(image);
        BorderPane.setAlignment(i, Pos.CENTER);
        root.setTop(i);
    }

    private void buildImportAndExportButton(BorderPane root) {
        Rectangle importExportButton = new Rectangle();
        importExportButton.setWidth(100);importExportButton.setHeight(100);

        Image image5 = new Image(getClass().getResource("..\\..\\..\\Assets\\import-export-1780274-1513718.png").toExternalForm());
        ImagePattern img5 = new ImagePattern(image5);
        importExportButton.setFill(img5);

        optionsBox.getChildren().add(importExportButton);
        addEffectToButton(importExportButton);
        importExportButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //
            }
        });
    }

    private void buildDuelButton(BorderPane root) {
        Rectangle duelButton = new Rectangle();
        duelButton.setWidth(100);duelButton.setHeight(100);

        Image image6 = new Image(getClass().getResource("..\\..\\..\\Assets\\download.jfif").toExternalForm());
        ImagePattern img6 = new ImagePattern(image6);
        duelButton.setFill(img6);

        optionsBox.getChildren().add(duelButton);
        addEffectToButton(duelButton);
        MainMenu me = this;
        duelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new BeforeDuelMenu(me,api,primaryStage).start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void buildProfileButton(BorderPane root) {
        Rectangle profileButton = new Rectangle();
        profileButton.setWidth(100);profileButton.setHeight(100);

        Image image4 = new Image(getClass().getResource("..\\..\\..\\Assets\\account.png").toExternalForm());
        ImagePattern img4 = new ImagePattern(image4);
        profileButton.setFill(img4);
        
        optionsBox.getChildren().add(profileButton);
        addEffectToButton(profileButton);
        profileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu profileMenu = new ProfileMenu();
                    profileMenu.setPriorMenu(new MainMenu(username, nickname, api));
                    profileMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void buildCardButton(BorderPane root) {
        Rectangle cardButton = new Rectangle();
        cardButton.setWidth(100);cardButton.setHeight(100);

        Image image2 = new Image(getClass().getResource("..\\..\\..\\Assets\\Icons\\card.png").toExternalForm());
        ImagePattern img2 = new ImagePattern(image2);
        cardButton.setFill(img2);

        optionsBox.getChildren().add(cardButton);
        addEffectToButton(cardButton);
        cardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //set prior
                /*
                try {
                    CardMenu cardMenu=new CardMenu();
                    cardMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                 */
            }
        });
    }

    private void buildScoreBoardButton(BorderPane root){
        Rectangle scoreboardButton = new Rectangle();
        scoreboardButton.setWidth(100);scoreboardButton.setHeight(100);

        Image image3 = new Image(getClass().getResource("..\\..\\..\\Assets\\scorboard.png").toExternalForm());
        ImagePattern img3 = new ImagePattern(image3);
        scoreboardButton.setFill(img3);

        optionsBox.getChildren().add(scoreboardButton);
        addEffectToButton(scoreboardButton);
        scoreboardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                    scoreBoardMenu.setPriorMenu(new MainMenu(username, nickname, api));
                    scoreBoardMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void buildShopButton(BorderPane root) {
        Rectangle shopButton = new Rectangle();
        shopButton.setWidth(100);shopButton.setHeight(100);

        Image image1 = new Image(getClass().getResource("..\\..\\..\\Assets\\shop.png").toExternalForm());
        ImagePattern img1 = new ImagePattern(image1);
        shopButton.setFill(img1);

        optionsBox.getChildren().add(shopButton);
        addEffectToButton(shopButton);
        shopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ShopMenu shopMenu = new ShopMenu();
                    shopMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void buildLogoutButton() {
        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    myWelcomeMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        logOutButton.setPadding(new Insets(20));
        logOutButton.setStyle("-fx-padding: 20");
        BorderPane.setAlignment(logOutButton, Pos.CENTER);
    }

    private void addEffectToButton(Rectangle button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }

        });

        button.setCursor(Cursor.HAND);
    }

    public void setPriorMenu(WelcomeMenu welcomeMenu) {
        this.myWelcomeMenu = welcomeMenu;
    }
}

