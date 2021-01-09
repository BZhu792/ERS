import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Instructions extends Application {
    final private int height = 417;
    final private int width = 626;
    final private static String INSTRUCTIONS =
            "Objective: To make the robot run out of cards before you do. \n" +
                    "Beginning with you, you alternate turns playing cards with the robot. \n" +
                    "Click 'Play Card' to play card for yourself. \n" +
                    "When these combinations come up, you can slap the deck to take the pile: \n" +
                    "\t 'Double': Two consecutive cards with the same value. \n" +
                    "\t 'Sandwich': Two cards with the same value separated by one card. \n" +
                    "\t 'Marriage': Q followed by K, or K followed by Q. \n" +
                    "\t 'Top Bottom': The bottom and top cards of the pile are the same. \n" +
                    "\t 'Tens': Two consecutive cards that add up to 10 (A = 1). \n" +
                    "\t 'Four in a row': Four consecutive cards that are consistent ascending or descending. \n" +
                    "\t\t Caution: The cards can cross over from K to A to 2 and vice versa, e.g. 2 A K Q. \n" +
                    "But be quick! The robot will take the pile if it slaps before you! \n" +
                    "If a player slaps the deck incorrectly, they 'burn' a card to the bottom of the pile. \n" +
                    "If a special card (A, K, Q, J), the following player must play: \n" +
                    "\t 1 card for J. \n" +
                    "\t 2 cards for Q. \n" +
                    "\t 3 cards for K. \n" +
                    "\t 4 cards for A. \n" +
                    "If the following player does not produce a special card, the current player claims the pile. \n" +
                    "Once a player has zero cards, even if they can make a move to gain cards, they lose.";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("Instructions");
        title.setFont(Font.font("Edwardian Script ITC", 40));
        title.setFill(Color.WHITE);


        Label label = new Label(INSTRUCTIONS);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 11; -fx-text-fill: white;");




        BackgroundFill[] bgf1 = new BackgroundFill[1];
        bgf1[0] = new BackgroundFill(Color.BLACK, null, null);
        Button instructions = new Button("Return to Menu");
        instructions.setTextFill(Color.WHITE);
        instructions.setBackground(new Background(bgf1));
        instructions.setFont(Font.font("STENCIL", 15));
        instructions.setPrefSize(150, 30);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(5);
        layout.getChildren().addAll(title, label, instructions);

        Image background = new Image("JPEG/space.jpg", width, height, false, true);
        BackgroundImage backgroundimage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundimage);
        layout.setBackground(background1);


        instructions.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsMenu().start( new Stage() ) );
        });
        Scene scene = new Scene(layout, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}
