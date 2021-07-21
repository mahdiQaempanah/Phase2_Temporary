package sample;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import sample.View.Graphic.WelcomeMenu;

public class Main extends Application {
    public static Point2D stageSize ;


    static {
        stageSize = new Point2D(1950, 1030);
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(stageSize.getX());
        primaryStage.setHeight(stageSize.getY());
        primaryStage.setTitle("Yu Gi Oh!");
        primaryStage.getIcons().add(new Image(getClass().getResource("../Image/Icon.png").toExternalForm()));
        primaryStage.setResizable(false);
        new WelcomeMenu().start(primaryStage);
        getClass().getResource("../Image/Cards/CardToBack.jpg").toExternalForm();
    }
}
