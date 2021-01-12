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

/**
 * @author Bo Han Zhu
 * @version 1.0
 */
public class Difficulty extends Application {
    private final int height = 417;
    private final int width = 626;

    /**
     * Main method for some IDEs that cannot launch the start method.
     * @param args Main method arguments .
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundFill[] dimGrayBackgroundFill = new BackgroundFill[1];
        dimGrayBackgroundFill[0] = new BackgroundFill(Color.DIMGRAY, null, null);
        Button hard = new Button("Hard");
        hard.setBackground(new Background(dimGrayBackgroundFill));
        hard.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        hard.setTextFill(Color.WHITE);
        hard.setPrefSize(120, 30);
        hard.setOnAction(e -> {
            new Run(50, 650).start(primaryStage);
        });

        BackgroundFill[] grayBackgroundFill = new BackgroundFill[1];
        grayBackgroundFill[0] = new BackgroundFill(Color.GRAY, null, null);
        Button medium = new Button("Medium");
        medium.setBackground(new Background(grayBackgroundFill));
        medium.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        medium.setTextFill(Color.WHITE);
        medium.setPrefSize(120, 30);
        medium.setOnAction(e -> {
            new Run(35, 800).start(primaryStage);
        });

        BackgroundFill[] darkGrayBackgroundFill = new BackgroundFill[1];
        darkGrayBackgroundFill[0] = new BackgroundFill(Color.DARKGRAY, null, null);
        Button easy = new Button("Easy");
        easy.setBackground(new Background(darkGrayBackgroundFill));
        easy.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        easy.setTextFill(Color.WHITE);
        easy.setPrefSize(120, 30);
        easy.setOnAction(e -> {
            new Run(20, 1000).start(primaryStage);
        });

        BackgroundFill[] lightGrayBackgroundFill = new BackgroundFill[1];
        lightGrayBackgroundFill[0] = new BackgroundFill(Color.LIGHTGRAY, null, null);
        Button novice = new Button("Novice");
        novice.setBackground(new Background(lightGrayBackgroundFill));
        novice.setTextFill(Color.WHITE);
        novice.setFont(Font.font("STENCIL", FontWeight.BOLD, 15));
        novice.setPrefSize(120, 30);
        novice.setOnAction(e -> {
            new Run(10, 1250).start(primaryStage);
        });


        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.getChildren().addAll(novice, easy, medium, hard);

        Image space = new Image("JPEG/space.jpg", width, height, false, true);
        BackgroundImage spaceBackgroundImage = new BackgroundImage(space,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background spaceBackground = new Background(spaceBackgroundImage);
        layout.setBackground(spaceBackground);

        Scene scene = new Scene(layout, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
