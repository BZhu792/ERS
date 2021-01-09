import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Difficulty extends Application {
    final private int height = 417;
    final private int width = 626;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundFill[] bgf1 = new BackgroundFill[1];
        bgf1[0] = new BackgroundFill(Color.DIMGRAY, null, null);
        Button hard = new Button("Hard");
        hard.setBackground(new Background(bgf1));
        hard.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        hard.setTextFill(Color.WHITE);
        hard.setPrefSize(120, 30);
        hard.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsGame(50, 650).start( new Stage() ) );
        });

        BackgroundFill[] bgf2 = new BackgroundFill[1];
        bgf2[0] = new BackgroundFill(Color.GRAY, null, null);
        Button medium = new Button("Medium");
        medium.setBackground(new Background(bgf2));
        medium.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        medium.setTextFill(Color.WHITE);
        medium.setPrefSize(120, 30);
        medium.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsGame(35, 800).start( new Stage() ) );
        });

        BackgroundFill[] bgf3 = new BackgroundFill[1];
        bgf3[0] = new BackgroundFill(Color.DARKGRAY, null, null);
        Button easy = new Button("Easy");
        easy.setBackground(new Background(bgf3));
        easy.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        easy.setTextFill(Color.WHITE);
        easy.setPrefSize(120, 30);
        easy.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsGame(20, 1000).start( new Stage() ) );
        });

        BackgroundFill[] bgf4 = new BackgroundFill[1];
        bgf4[0] = new BackgroundFill(Color.LIGHTGRAY, null, null);
        Button newbie = new Button("Novice");
        newbie.setBackground(new Background(bgf4));
        newbie.setTextFill(Color.WHITE);
        newbie.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        newbie.setPrefSize(120, 30);
        newbie.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsGame(10, 1250).start( new Stage() ) );
        });

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.getChildren().addAll(newbie, easy, medium, hard);

        Image background = new Image("JPEG/space.jpg", width, height, false, true);
        BackgroundImage backgroundimage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundimage);
        layout.setBackground(background1);


        Scene scene = new Scene(layout, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
