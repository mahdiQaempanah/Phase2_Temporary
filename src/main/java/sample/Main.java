package sample;

import com.google.gson.Gson;
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
import sample.Controller.ProgramController;
import sample.Model.Game.Phase;

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
        System.out.println(new Gson().toJson(Phase.MAIN_PHASE_1));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
//taghirat dar code ghabli