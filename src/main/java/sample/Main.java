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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sample.Controller.ProgramController;
import sample.View.Graphic.LoginMenu;
import sample.View.Graphic.WelcomeMenu;

public class Main extends Application {
    public static Point2D stageSize = new Point2D(800, 600);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Rectangle card = new Rectangle();
        card.setWidth(300);
        card.setHeight(500);
        card.setLayoutX(500);
        card.setLayoutY(100);
        card.setFill(Color.AQUA);

        Rotate rotate = new Rotate();
        //Setting the angle for the rotation
        rotate.setAngle(90);
        //Setting pivot points for the rotation
        //Adding the transformation to rectangle2
        card.getTransforms().addAll(rotate);
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(card);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();*/
     primaryStage.setWidth(stageSize.getX());
        primaryStage.setHeight(stageSize.getY());
        primaryStage.setTitle("Yu Gi Oh!");
        primaryStage.getIcons().add(new Image(getClass().getResource("../Image/Icon.png").toExternalForm()));
        primaryStage.setResizable(false);
        new WelcomeMenu().start(primaryStage);
        getClass().getResource("../Image/Cards/CardToBack.jpg").toExternalForm();
    }
}
//taghirat dar code ghabli