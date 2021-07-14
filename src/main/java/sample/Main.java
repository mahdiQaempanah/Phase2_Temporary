package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sample.Controller.ProgramController;
import sample.View.Graphic.LoginMenu;

public class Main extends Application {

public static Stage primaryStage;
        GridPane grid = new GridPane();
        Button button = new Button("start");
        LoginMenu loginMenu=new LoginMenu();





public static void main(String[] args) throws Exception {
        launch(args);
        }

@Override
public void start(Stage primaryStage) throws Exception {


        BorderPane root = new BorderPane();
        primaryStage.setTitle("yo-gy-oh");
        // primaryStage.getIcons().add(new Image(getClass().getResource("resources/logo.png").toExternalForm()));
        Scene scene = new Scene(root, 800, 600);


        Image backGroundImage = new Image(getClass().getResource("..\\Assets\\yugioh wallpaper.jpg").toExternalForm());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
        false, true, false);
        root.setBackground(new Background(new BackgroundImage(backGroundImage,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        bSize)));







        Image startGifImage = new Image(getClass().getResource("..\\Assets\\Logos\\_images_text_yugioh.dds2.png").toExternalForm());
        ImageView StartGifImageView = new ImageView(startGifImage);




        // set background for scene


        primaryStage.setScene(scene);

        //set gif for first scene


        StartGifImageView.setImage(startGifImage);
        StartGifImageView.setFitHeight(150);
        StartGifImageView.setFitWidth(200);
        root.setCenter(StartGifImageView);

        /// set start button



        Image startButtonImage = new Image(getClass().getResource("..\\Assets\\start.png").toExternalForm());
        ImageView startButtonImageView = new ImageView(startButtonImage);
        startButtonImageView.setFitWidth(50);
        startButtonImageView.setFitHeight(50);


        root.setBottom(startButtonImageView);

        BorderPane.setMargin(startButtonImageView, new Insets(20));

        //set action and animation hover for start button

        startButtonImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
        startButtonImageView.setEffect(new DropShadow());
        }
        });


        startButtonImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
        startButtonImageView.setEffect(null);
        }
        });

        startButtonImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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


        //end

        Media media = new Media(getClass().getResource("..\\sounds yugioh/DP_APPEAR.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);

        mediaPlayer.play();


        primaryStage.show();

        //set initialize media





        }}
//taghirat dar code ghabli