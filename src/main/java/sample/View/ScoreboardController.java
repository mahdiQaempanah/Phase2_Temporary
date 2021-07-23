package sample.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.ScoreboardInfo;
import sample.View.Graphic.MainMenu;
import sample.View.Graphic.SocketPackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardController {
    public AnchorPane mainPane;
    private Stage primaryStage;
    public MainMenu myMainMenu;
    public HBox scoreboard;

    public void start(Stage stage, MainMenu mainMenu) throws Exception {
        primaryStage = stage;
        myMainMenu = mainMenu;
        setBackGround();
        refreshScoreboard();
    }

    private void setBackGround() {
        Rectangle req = new Rectangle(308,259,400,369);
        req.setFill(new ImagePattern(new Image(getClass().getResource("../../Image/crown.jpg").toExternalForm())));
        mainPane.getChildren().add(req);
    }

    private void refreshScoreboard() throws Exception {
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("command");keyWords.add("showScoreboard");
        ArrayList<ScoreboardInfo> scoreboardInfos = new Gson().fromJson(responseFromApi(keyWords).getMessage(),new TypeToken<List<ScoreboardInfo>>(){}.getType());
        sortScoreBoard(scoreboardInfos);
        addInfoToScoreBoard(scoreboardInfos);
        new Timeline(new KeyFrame(Duration.millis(1500), (ActionEvent event) -> {
            try {
                refreshScoreboard();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        })).play();
    }

    private void sortScoreBoard(ArrayList<ScoreboardInfo> scoreboardInfos) {
        scoreboardInfos.sort(new Comparator<ScoreboardInfo>() {
            @Override
            public int compare(ScoreboardInfo o1, ScoreboardInfo o2) {
                if(o1.score != o2.score)
                    return o2.score - o1.score;
                int i = 0;
                for(;;i++){
                    if(o1.nickname.length() == i)
                        return 1;
                    if(o2.nickname.length() == i)
                        return -1;
                    if(o1.nickname.charAt(i) != o2.nickname.charAt(i)){
                        return o1.nickname.charAt(i) - o2.nickname.charAt(i);
                    }
                }
            }
        });
    }

    private void addInfoToScoreBoard(ArrayList<ScoreboardInfo> scoreboardInfos) {
        ListView scoreboardView = new ListView();
        int rnk = 0;
        int lstScore = -1;
        for (ScoreboardInfo scoreboardInfo : scoreboardInfos) {
            if(scoreboardInfo.score != lstScore)
                rnk++;
            lstScore = scoreboardInfo.score;
            String info = rnk + ". " + scoreboardInfo.nickname + ": " + scoreboardInfo.score + " (";
            if(scoreboardInfo.isOnline())
                info += "Online)";
            else
                info += "Ofline)";
            scoreboardView.getItems().add(info);
        }
        scoreboard.getChildren().clear();
        scoreboard.getChildren().add(scoreboardView);
    }

    public void handleBack(ActionEvent actionEvent) throws Exception {
        myMainMenu.start(primaryStage);
    }

    public ApiMessage responseFromApi(ArrayList<String> keyWords) throws Exception {
        assert keyWords.size()%2 == 0;
        JSONObject message = new JSONObject();
        for(int i = 0 ; i < keyWords.size() ; i+=2)
            message.put(keyWords.get(i), keyWords.get(i + 1));
        JSONObject jsonAns = new JSONObject(SocketPackage.getInstance().getResponse(message));
        return new Gson().fromJson(String.valueOf(jsonAns),ApiMessage.class);
    }
}
