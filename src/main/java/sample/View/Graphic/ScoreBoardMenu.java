package sample.View.Graphic;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;
import sample.Model.JsonObject.ScoreboardInfo;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ScoreBoardMenu extends Application {
    private API request=new API();

    private String username;
    public BorderPane root = new BorderPane();
    //MainMenu mainMenu;
    public JSONObject request_JSON = new JSONObject();
    public JSONObject response;
    private MainMenu mainMenu;


    @Override
    public void start(Stage primaryStage) throws Exception {




        // StackPane root = FXMLLoader.load(getClass().getResource("resources/sample.fxml"));
        Scene scene = new Scene(root, 900, 550);
        //scene.getStylesheet().add("path/Stylesheet.css");

        primaryStage.setScene(scene);

        primaryStage.setTitle("yu-gi-oh");


        primaryStage.show();


        Label title = new Label("scoreboard");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center;-fx-font-size: 50 px");
        // title.setBackground(new Background(new BackgroundImage()));
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);


        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();


        root.setCenter(scrollPane);

        VBox scoreBar = new VBox();
        scoreBar.setSpacing(5);
        scoreBar.setAlignment(Pos.CENTER);


        ///////////////////////////////////////////////////////////////    ???

        int priorScore = 0;
        int i = 1, j = 1;
        for (ScoreboardInfo user :
                loadScores()) {
            String scoreLine;
            Label userLable = new Label();
            if (j == 20) break;

            if (i == 1) {
               userLable.setText(i + "- " + user.nickname + ": " + user.score);
                j++;
            } else if (user.score == priorScore) {
               userLable.setText(i + "-  " + user.nickname + ": " + user.score);
                j++;
            } else {
                userLable.setText(j + "- " + user.nickname + ":  " + user.score);
                i = j;
                j++;
            }
            priorScore = user.score;
            userLable.setAlignment(Pos.CENTER);
            userLable.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center center" +
                    ";-fx-font-size: 30 px");
            scoreBar.getChildren().add(userLable);


        }




        //////////////////////////////////////////////////////////////


        //set back button
        Button backbutton=new Button("back");


        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                try {
                    mainMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                 */
            }

        });
        backbutton.setPadding(new Insets(10));
        backbutton.setAlignment(Pos.CENTER);

        root.setBottom(backbutton);
        BorderPane.setAlignment(backbutton,Pos.CENTER);




    }
   /* public void setPriorMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    */

    public LinkedList<ScoreboardInfo> loadScores() throws Exception {

        JSONObject response = js_Pass("command", "show_scorboard");
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<ScoreboardInfo>>() {}.getType();
        ArrayList<ScoreboardInfo> scoreboardInfos = gson.fromJson((String) response.get("message"), userListType);

        LinkedList<ScoreboardInfo> scoreList = new LinkedList<>(scoreboardInfos);
        List<ScoreboardInfo> interChange = new ArrayList<>();
        int quantity = scoreboardInfos.size();

        if (quantity==0){
            System.out.println("none");return null;
        }
        for (int i = 0; i <= quantity - 2; i++) {
            for (int j = i + 1; i <= quantity - 1; i++) {

                if (scoreList.get(i).score < scoreList.get(j).score) {
                    interChange.add(scoreboardInfos.get(j));
                    scoreList.set(j, scoreList.get(i));
                    scoreList.set(i, interChange.get(0));
                    interChange.clear();

                } else if ((scoreList.get(i).score == scoreList.get(j).score)) {

                    if (scoreList.get(i).nickname.compareTo(scoreList.get(j).nickname) < 0) {
                        interChange.add(scoreList.get(j));
                        scoreList.set(j, scoreList.get(i));
                        scoreList.set(i, interChange.get(0));
                        interChange.clear();
                    }

                }


            }


        }
        return scoreList;
    }
    public JSONObject js_Pass(String... args) throws Exception {
        for (int i = 0; i <= args.length - 2; i += 2) {
            request_JSON.put(args[i], args[i + 1]);
        }

        JSONObject response = request.run(request_JSON);
        request_JSON = new JSONObject();
        return response;
    }

    public void setPriorMenu(MainMenu mainMenu) {
        this.mainMenu=mainMenu;
    }
    /*
    public void setPrior(MainMenu mainMenu){this.mainMenu=mainMenu;}

     */
}
