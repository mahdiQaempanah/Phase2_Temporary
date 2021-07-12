package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {
    public static Point2D stageSize = new Point2D(1950, 1030);
    @Override
    public void start(Stage primaryStage) throws Exception{

       /* primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Fxml/GameView.fxml"))));
        primaryStage.setWidth(stageSize.getX());
        primaryStage.setHeight(stageSize.getY());
        primaryStage.setTitle("Yu Gi Oh");
        primaryStage.getIcons().add(new Image(getClass().getResource("../Image/Icon.png").toExternalForm()));
        primaryStage.setResizable(false);
        primaryStage.show();
        Label label = new Label();*/
        Canvas canvas = new Canvas(400, 400);
        double x = 100;
        double y = 200;
        double width = 100;
        double height = 200;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double rotationCenterX = (x + width) / 2;
        double rotationCenterY = (y + height) / 2;

        gc.save();
        gc.transform(new Affine(new Rotate(90, rotationCenterX, rotationCenterY)));
        gc.fillRect(0, 0, width, height);
        gc.restore();

        Scene scene = new Scene(new Group(canvas));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
//taghirat dar code ghabli