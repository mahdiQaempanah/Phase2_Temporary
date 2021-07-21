package sample.View.Graphic;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class WelcomeMenu extends Application {
    public static Stage primaryStage;
    private GridPane grid = new GridPane();
    private Button button = new Button("start");
    private LoginMenu loginMenu = new LoginMenu(this);


    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeMenu.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        buildBackground(root);
        buildLogo(root);
        buildStartButton(root);
        buildBackgroundMedia(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buildBackground(BorderPane root){
        Image backGroundImage = new Image(getClass().getResource("..\\..\\..\\Assets\\yugioh wallpaper.jpg").toExternalForm());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                false, true, false);
        root.setBackground(new Background(new BackgroundImage(backGroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }

    private void buildLogo(BorderPane root){
        Image startGifImage = new Image(getClass().getResource("..\\..\\..\\Assets\\Logos\\_images_text_yugioh.dds2.png").toExternalForm());
        ImageView StartGifImageView = new ImageView(startGifImage);
        StartGifImageView.setImage(startGifImage);
        StartGifImageView.setFitHeight(150);
        StartGifImageView.setFitWidth(200);
        root.setCenter(StartGifImageView);
    }

    private void buildStartButton(BorderPane root){
        ImageView startButton = new ImageView(new Image(getClass().getResource("..\\..\\..\\Assets\\start.png").toExternalForm()));
        startButton.setFitWidth(50);
        startButton.setFitHeight(50);
        root.setBottom(startButton);
        BorderPane.setMargin(startButton, new Insets(20));

        ImageView finalStartButton = startButton;

        startButton.setCursor(Cursor.HAND);

        startButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                finalStartButton.setEffect(new DropShadow());
            }
        });

        startButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                finalStartButton.setEffect(null);
            }
        });

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    loginMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        BorderPane.setAlignment(root.getBottom(), Pos.CENTER);
        button.setAlignment(Pos.CENTER);
    }

    private void buildBackgroundMedia(BorderPane root){
        Media media = new Media(getClass().getResource("..\\..\\..\\sounds yugioh/DP_APPEAR.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }
}
