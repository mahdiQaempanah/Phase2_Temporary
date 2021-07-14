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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONObject;
import sample.Controller.API;

//
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Application {


    API request = new API();
    public Scanner scan = new Scanner(System.in);
    public String command;
    public JSONObject request_JSON = new JSONObject();
    public JSONObject response;
    public static boolean ID = true;
    MainMenu mainMenu;



    @Override
    public void start(Stage primaryStage) throws Exception {


        //set scene and ...
        BorderPane root = new BorderPane();
        StackPane root2 = new StackPane();
        root.setCenter(root2);
        root2.setAlignment(Pos.CENTER);
        primaryStage.setTitle("yu-gy-oh");
        Scene scene = new Scene(root, 800, 600);


        //set background image



        Media media = new Media(getClass().getResource("..\\..\\..\\sounds yugioh/FUSION.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);

        mediaPlayer.play();



        Image backGroundImage = new Image(getClass().getResource("..\\..\\..\\Assets\\yugioh wallpaper.jpg").toExternalForm());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                false, true, false);
        root.setBackground(new Background(new BackgroundImage(backGroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        primaryStage.setScene(scene);

        //set login box for username and password

        Rectangle inSideFrame = new Rectangle();
        inSideFrame.setHeight(300);
        inSideFrame.setWidth(300);

        Rectangle outSideFrame = new Rectangle();
        outSideFrame.setHeight(400);
        outSideFrame.setWidth(500);
        outSideFrame.setFill(Color.PINK);


        inSideFrame.setFill(Color.LIGHTBLUE);
        root2.getChildren().add(outSideFrame);
        root2.getChildren().add(inSideFrame);

        HBox loginKeysContainer = new HBox();
        Button loginButton = new Button("login");
        Button signUpButton = new Button("sign up");
        loginKeysContainer.getChildren().addAll(signUpButton, loginButton);

        loginKeysContainer.setSpacing(8);
        loginKeysContainer.setPadding(new Insets(10, 10, 10, 10));
        loginKeysContainer.setAlignment(Pos.CENTER);

        Label userLable = new Label("Your Username");      // username box
        TextField userText = new TextField();
        userText.setMaxWidth(250);

        Label nickLable = new Label("Your Nickname");      // username box
        TextField nickText = new TextField();
        nickText.setMaxWidth(250);

        Label passLable = new Label("Your Password");       //password box
        PasswordField passtext = new PasswordField();
        passtext.setMaxWidth(250);


        Label onTMessaeg = new Label("input your informations");
        VBox loginElementContainer = new VBox();
        loginElementContainer.setSpacing(8);
        loginElementContainer.setPadding(new Insets(10, 10, 10, 10));

        //addd nodes to vbox

        loginElementContainer.getChildren().addAll(userLable, userText, nickLable, nickText, passLable, passtext,
                loginKeysContainer, onTMessaeg);

        loginElementContainer.setAlignment(Pos.CENTER);
        root2.getChildren().add(loginElementContainer);


        //set login button action


        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                JSONObject response = null;
                try {
                    response = js_Pass("command", "crate_new_user", "username", userText.getText(),
                            "password", passtext.getText(), "nickname", nickText.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                userText.clear();
                nickText.clear();
                passtext.clear();
               onTMessaeg.setText(response.get("message").toString());

            }


        });

//set signup button

       loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                JSONObject response = null;
                try {
                    response = js_Pass("command", "login_user", "username", userText.getText(), "password", passtext.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!response.get("type").toString().equals("error") && (! userText.getText().isEmpty())&& (!passtext.getText().isEmpty())) {

                    onTMessaeg.setText(response.get("message").toString());
                    mainMenu = new MainMenu(userText.getText(), nickText.getText());
                    mainMenu.setPriorMenu(new LoginMenu());
                    try {
                        mainMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else onTMessaeg.setText("input your username and password both");


//d
            }
        });

       loginElementContainer.setOnMouseEntered(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               loginElementContainer.setEffect(new DropShadow());
           }
       });
        loginElementContainer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginElementContainer.setEffect(null);
            }
        });

        //set back button

        Button backbutton = new Button("back");
        backbutton.setAlignment(Pos.CENTER);
        backbutton.setPadding(new Insets(10));
        root.setBottom(backbutton);
        BorderPane.setAlignment(backbutton, Pos.CENTER);
        BorderPane.setMargin(backbutton, new Insets(10));


        //setBackBotton action

        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                return;

            }

        });




        primaryStage.show();


    }


    public static void clearJSONOBJ(JSONObject o) {
        while (o.length() > 0) {
            o.remove((String) o.keys().next());
        }
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
        request_JSON = new JSONObject();
        return response;
    }


}
