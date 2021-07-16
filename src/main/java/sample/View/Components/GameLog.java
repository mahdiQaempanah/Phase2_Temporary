package sample.View.Components;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameLog {
    private Label gameLogLabel;
    private Queue<Color> colorsQueue = new LinkedList<>();
    private Queue<Integer> showTimeQueue = new LinkedList<>();
    private Queue<String> messageQueue = new LinkedList<>();
    private Timeline show;
    private FadeTransition fade;

    public GameLog(Label gameLogLabel){
        this.gameLogLabel = gameLogLabel;
    }

    public void add(Color color, String message, int showTime){
        colorsQueue.add(color);
        messageQueue.add(message);
        showTimeQueue.add(showTime);
        showNewGameLog();
    }

    private void showNewGameLog() {
        Color color = colorsQueue.remove();
        String message = messageQueue.remove();
        int showTime = showTimeQueue.remove();

        if(show != null){
            show.pause();
            show = null;
        }

        if (fade != null) {
            fade.pause();
            fade = null;
        }


        gameLogLabel.setText(message);
        gameLogLabel.setTextFill(color);
        show = new Timeline(new KeyFrame(Duration.millis(showTime), (ActionEvent event) -> {
            show = null;
            fadeGameLogText();
        }));
        show.play();
    }

    private void fadeGameLogText() {
        fade = new FadeTransition(Duration.millis(1000));
        fade.setNode(gameLogLabel);
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameLogLabel.setText("");
                fade = null;
            }
        });
        fade.play();
    }
}
