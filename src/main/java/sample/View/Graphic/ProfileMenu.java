package sample.View.Graphic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu extends Application {

    private String username;
    LoginMenu loginMenu= new LoginMenu();
    MainMenu mainMenu;
    public JSONObject request_JSON = new JSONObject();
    public JSONObject response;
    API request=new API();


    @Override
    public void start(Stage primaryStage) throws Exception {


        //set scene and ...

        primaryStage.setTitle("yo-gy-oh");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();



        Image backGroundImage = new Image(getClass().getResource("..\\..\\..\\Assets\\profile.jpg").toExternalForm());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                false, true, false);
        root.setBackground(new Background(new BackgroundImage(backGroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));


        //set lables and buttons


        Label title = new Label("Account");
        title.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width" +
                ":2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center;-fx-font-size: 100 px;-fx-padding: 10px");
        title.setPadding(new Insets(30));

        title.setMinSize(200,200);
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);


        VBox vBox = new VBox();
        HBox hBox = new HBox();
        //get username

        //set picture
        Label usernameLable = new Label(mainMenu.username);

        usernameLable.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;" +
                "-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center ;-fx-font-size: 60 px");

        ///another lable
        hBox.getChildren().add(usernameLable);
        hBox.setAlignment(Pos.CENTER);

        VBox options = new VBox();
        options.setSpacing(20);
        Button changeNicknameButton = new Button("change nickname");
        Button changePassButton = new Button("change password");

        options.getChildren().addAll(changeNicknameButton, changePassButton);
        options.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(hBox, options);


        root.setCenter(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);


        Popup popup = PopupBuilder.create().content(hBox).build();
        popup.setAnchorX(400);
        popup.setAnchorY(300);


        Button buttonS = new Button("submit");

        buttonS.setPadding(new Insets(20));
        HBox hhBox = new HBox();
        Button buttonL = new Button("cancel");
        hhBox.getChildren().addAll(buttonS, buttonL);

        hhBox.setSpacing(8);
        hhBox.setPadding(new Insets(10, 10, 10, 10));
        hhBox.setAlignment(Pos.CENTER);

        Label onTMessaeg = new Label();

        Label NickLable = new Label("Your new nickname");
        TextField nickText = new TextField();
        nickText.setMaxWidth(250);



        Label userLable = new Label("Your prior pass");
        TextField userText = new TextField();
        userText.setMaxWidth(250);
        Label passLable = new Label("Your new pass");
        PasswordField passtext = new PasswordField();
        passtext.setMaxWidth(250);


        VBox vvBox = new VBox();
        vvBox.setSpacing(5);
        vvBox.setPadding(new Insets(10, 10, 10, 10));
        vvBox.getChildren().addAll(userLable, userText, passLable, passtext, hhBox, onTMessaeg);
        vvBox.setAlignment(Pos.CENTER);
        vvBox.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;" +
                "-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center center");
        popup.getContent().add(vvBox);

        //////////////////////////////////////////////////// buttons actions

        changePassButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                popup.show(primaryStage);

                buttonS.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean isDone=changePass(userText.getText(),passtext.getText());
                            if (! isDone) onTMessaeg.setText("incorrect password");
                            else popup.hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                buttonL.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        popup.hide();
                    }
                });


            }
        });
        ////////////////////////////////////////////////////////////////

        changeNicknameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Popup popup = PopupBuilder.create().content(hBox).build();
                popup.setAnchorX(400);
                popup.setAnchorY(300);

                Button caButton=new Button("cancel");
                Button subButton=new Button("submit");
                HBox hBox1=new HBox();
                hBox1.setSpacing(8);
                hBox1.setPadding(new Insets(10, 10, 10, 10));
                hBox1.getChildren().addAll(caButton,subButton);
                hBox1.setAlignment(Pos.CENTER);
                Label lable= new Label("enter your new nickname");
                VBox vBox1=new VBox();
                vBox1.setSpacing(8);
                vBox1.setPadding(new Insets(10, 10, 10, 10));
                vBox1.getChildren().addAll(lable,nickText,hBox1);
                vBox1.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;" +
                        "-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;-fx-alignment: center center");
                popup.getContent().add(vBox1);

                popup.show(primaryStage);



                caButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        popup.hide();
                    }
                });

                subButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       boolean isDone;
                        try {
                            isDone = changeNickname(nickText.getText());
                            popup.hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });


            }
        });



        //set back button
        Button backbutton=new Button("back");


        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        backbutton.setPadding(new Insets(10));
        backbutton.setAlignment(Pos.CENTER);

        root.setBottom(backbutton);
        BorderPane.setAlignment(backbutton,Pos.CENTER);




    }
    public void setPriorMenu(MainMenu mainMenu){
        this.mainMenu=mainMenu;
    }

    public static Matcher commandMatch(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) return matcher;
        else return null;
    }


    public JSONObject js_Pass(String... args) throws Exception {
        for (int i = 0; i <= args.length - 2; i += 2) {
            request_JSON.put(args[i], args[i + 1]);
        }

        JSONObject response = request.run(request_JSON);
        request_JSON=new JSONObject();
        return response;
    }

    public boolean changeNickname(String newNickname) throws Exception {

        JSONObject response
                = js_Pass("command", "change_Profile_nickname", "nickname", newNickname);
        if (response.get("type").equals("error")) return true;

        else {
            return false;
        }
    }
        public boolean changePass(String priorPass,String newPass) throws Exception {


            JSONObject response = js_Pass("command", "change_" +
                    "Profile_password", "currentPass",priorPass, "newPass", newPass);

            if (response.get("type").equals("error")) return false;

            else {
                return true;
            }
        }

}
