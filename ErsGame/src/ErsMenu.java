import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErsMenu extends Application {
    final private int height = 417;
    final private int width = 626;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("ERS");
        title.setFont(Font.font("Old English Text MT", 50));
        title.setFill(Color.WHITE);

        ImageView c1 = new ImageView(new Image("JPEG/JS.jpg"));
        ImageView c2 = new ImageView(new Image("JPEG/JH.jpg"));
        ImageView c3 = new ImageView(new Image("JPEG/JC.jpg"));
        ImageView c4 = new ImageView(new Image("JPEG/JD.jpg"));
        c1.setFitWidth(100);
        c1.setFitHeight(153);
        c1.setSmooth(true);
        c1.setCache(true);
        c2.setFitWidth(100);
        c2.setFitHeight(153);
        c2.setSmooth(true);
        c2.setCache(true);
        c3.setFitWidth(100);
        c3.setFitHeight(153);
        c3.setSmooth(true);
        c3.setCache(true);
        c4.setFitWidth(100);
        c4.setFitHeight(153);
        c4.setSmooth(true);
        c4.setCache(true);
        c2.setRotate(45);
        c3.setRotate(90);
        c4.setRotate(135);
        StackPane cards = new StackPane();
        cards.setAlignment(Pos.CENTER);

        Button play = new Button();
        Image playBackground1 = new Image("JPEG/play.png", 65, 65, true, true);
        Image playBackground2 = new Image("JPEG/play2.png", 65, 65, true, true);
        BackgroundImage backgroundimage1 = new BackgroundImage(playBackground1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background2 = new Background(backgroundimage1);
        BackgroundImage backgroundimage2 = new BackgroundImage(playBackground2,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background3 = new Background(backgroundimage2);
        play.setPrefSize(65, 65);
        play.setBackground(background2);

        cards.getChildren().addAll(c1, c2, c3, c4, play);



        BackgroundFill[] bgf1 = new BackgroundFill[1];
        bgf1[0] = new BackgroundFill(Color.BLACK, null, null);
        Button instructions = new Button("INSTRUCTIONS");
        instructions.setTextFill(Color.WHITE);
        instructions.setBackground(new Background(bgf1));
        instructions.setFont(Font.font("STENCIL", 15));
        instructions.setPrefSize(130, 30);

        Text verAuth = new Text("v. 1.0 by Bo Han Zhu");
        verAuth.setFill(Color.WHITE);
        verAuth.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 12));

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(30);
        layout.getChildren().addAll(title, cards, instructions);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(verAuth);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.TOP_CENTER);
        BorderPane.setMargin(verAuth, new Insets(10));

        Image background = new Image("JPEG/space.jpg", width, height, false, true);
        BackgroundImage backgroundimage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundimage);
        borderPane.setBackground(background1);


        new AnimationTimer() {
            @Override
            public void handle(long l) {
                this.stop();
                Platform.runLater(() -> {
                    try { Thread.sleep(300); } catch (Exception e) {}
                });
                if (play.getBackground() == background2) {
                    play.setBackground(background3);
                } else {
                    play.setBackground(background2);
                }
                this.start();
            }
        }.start();
        play.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new Difficulty().start( new Stage() ) );
        });
        instructions.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new Instructions().start( new Stage() ) );
        });
        Scene scene = new Scene(borderPane, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
