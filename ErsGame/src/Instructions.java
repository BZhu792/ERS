import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Bo Han Zhu
 * @version 1.0
 */
public class Instructions extends Application {
    private final int height = 417;
    private final int width = 626;
    private String text;
    private static final String INSTRUCTIONS =
            "Objective: To make the robot run out of cards before you do. \n"
                    + "Beginning with you, you alternate turns playing cards with the robot. \n"
                    + "Click 'Play Card' to play card for yourself. \n"
                    + "When these combinations come up, you can slap the deck to take the pile: \n"
                    + "\t 'Double': Two consecutive cards with the same value. \n"
                    + "\t 'Sandwich': Two cards with the same value separated by one card. \n"
                    + "\t 'Marriage': Q followed by K, or K followed by Q. \n"
                    + "\t 'Top Bottom': The bottom and top cards of the pile are the same. \n"
                    + "\t 'Tens': Two consecutive cards that add up to 10 (A = 1). \n"
                    + "\t 'Four in a row': Four consecutive cards that are consistent ascending or descending. \n"
                    + "\t\t Caution: The cards can cross over from K to A to 2 and vice versa, e.g. 2 A K Q. \n"
                    + "But be quick! The robot will take the pile if it slaps before you! \n"
                    + "If a player slaps the deck incorrectly, they 'burn' a card to the bottom of the pile. \n"
                    + "If a special card (A, K, Q, J), the following player must play: \n"
                    + "\t 1 card for J. \n"
                    + "\t 2 cards for Q. \n"
                    + "\t 3 cards for K. \n"
                    + "\t 4 cards for A. \n"
                    + "If the following player does not produce a special card, the current player claims the pile. \n"
                    + "Once a player has zero cards and if they cannnot make a move to gain cards, they lose.";


    /**
     * Default constructor for debugging purposes only. Sets the text to 'From Game'.
     */
    public Instructions() {
        this("From Game");
    }

    /**
     * Constructor used by other classes.
     * @param text a String representing where the call on this class came from.
     */
    public Instructions(String text) {
        this.text = text;
    }

    /**
     * Main method for some IDEs that cannot launch the start method.
     * @param args Main method arguments .
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("Instructions");
        title.setFont(Font.font("Edwardian Script ITC", 40));
        title.setFill(Color.WHITE);

        Label words = new Label(INSTRUCTIONS);
        words.setWrapText(true);
        words.setFont(Font.font("Comic Sans MS", 11));
        words.setTextFill(Color.WHITE);

        BackgroundFill[] blackBackgroundFill = new BackgroundFill[1];
        blackBackgroundFill[0] = new BackgroundFill(Color.BLACK, null, null);
        Button returnToMenu = new Button();
        if (text.equals("From Menu")) {
            returnToMenu.setText("Return to Menu");
        } else {
            returnToMenu.setText("Return to Game");
        }
        returnToMenu.setTextFill(Color.WHITE);
        returnToMenu.setBackground(new Background(blackBackgroundFill));
        returnToMenu.setFont(Font.font("STENCIL", 15));
        returnToMenu.setPrefSize(150, 30);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(5);
        layout.getChildren().addAll(title, words, returnToMenu);

        Image space = new Image("JPEG/space.jpg", width, height, false, true);
        BackgroundImage spaceBackgroundImage = new BackgroundImage(space,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background spaceBackground = new Background(spaceBackgroundImage);
        layout.setBackground(spaceBackground);


        returnToMenu.setOnAction(e -> {
            if (returnToMenu.getText().equals("Return to Menu")) {
                new Menu().start(primaryStage);
            } else {
                primaryStage.close();
            }
        });
        Scene scene = new Scene(layout, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
