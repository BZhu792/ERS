import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Bo Han Zhu
 * @version 1.0
 */
public class Run extends Application {
    private final int height = 417;
    private final int width = 626;
    private int minTurnMessUp; //minimum number of turns for the bot to mess up
    private final int maxTurnMessUp = 30; //actually is this value + minTurnMessUp
    private int minBotSlapSpeed; //minimum slapping speed of the bot in milliseconds
    private final int maxBotSlapSpeed = 500; //actually is this value + minBotSlapSpeed
    private Timer timer; //timer for many TimerTasks
    private Random rand = new Random(); //to give randomness to bot slapping speeds and mess-ups
    static int humanNumOfTimes = 1; //the number of times a human needs to play after a card
    static int botNumOfTimes = 1; // the number of times a bot needs to play after a card
    static boolean humanGo = true; //tracker variable for if bot can take pile (false = take)
    static int botTake = -1; //tracker variable for if bot can take pile (0 = take)
    static boolean botGo = true; //tracker variable for if human can take pile (false = take)
    static int humanTake = -1; //tracker variable for if human can take pile (0 = take)
    private static boolean display = true; //on/off variable for results screen (true = on)

    /**
     * Default constructor, used for debugging purposes.
     * Runs the game at the "Easy" level: minTurnMessUp = 20, minBotSlapSpeed = 1000.
     */
    public Run() {
        this(20, 1000);
    }

    /**
     * Constructor accessed from the Difficulty class.
     * @param minTurnMessUp A number representing the minimum turns for a bot mis-slap.
     * @param minBotSlapSpeed A number representing the minimum milliseconds for the bot to slap correctly.
     */
    public Run(int minTurnMessUp, int minBotSlapSpeed) {
        this.minTurnMessUp = minTurnMessUp;
        this.minBotSlapSpeed = minBotSlapSpeed;
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

        //System.out.println("Starting game with: minTurnMessUp " + minTurnMessUp + ", minBotSlapSpeed " + minBotSlapSpeed);

        //INITIALIZE BACKEND DECKS, PLAYERS, AND BOT MESS-UPS
        Deck deck = new Deck();
        ArrayList<Card> allCards = deck.toShuffledArrayList();
        ArrayList<Card> humanCards = new ArrayList<>(allCards.subList(0, 26));
        ArrayList<Card> botCards = new ArrayList<>(allCards.subList(26, 52));
        ArrayList<Card> pile = new ArrayList<>();
        ArrayList<Card> burnPile = new ArrayList<>();

        Player human = new Player("Player", humanCards, true);
        Player bot = new Player("Bot", botCards, false);

        MutableInteger counter = new MutableInteger(0);
        MutableInteger messUp = new MutableInteger(minTurnMessUp + rand.nextInt(maxTurnMessUp));

        //INITIALIZE JAVAFX FRAME
        //initialize quit and instructions button
        BackgroundFill[] blackBackgroundFill = new BackgroundFill[1];
        blackBackgroundFill[0] = new BackgroundFill(Color.BLACK, null, null);
        Button quit = new Button("Quit");
        quit.setBackground(new Background(blackBackgroundFill));
        quit.setTextFill(Color.WHITE);
        quit.setFont(Font.font("IMPACT", 10));
        quit.setPrefSize(75, 20);
        quit.setOnAction(e -> {
            System.exit(0);
        });
        Button instructions = new Button("Instructions");
        instructions.setBackground(new Background(blackBackgroundFill));
        instructions.setTextFill(Color.WHITE);
        instructions.setFont(Font.font("IMPACT", 10));
        instructions.setPrefSize(75, 20);
        instructions.setOnAction(e -> {
            Platform.runLater(() -> {
                new Instructions("From Game").start(new Stage());
            });
        });
        VBox settings = new VBox();
        settings.setAlignment(Pos.CENTER_RIGHT);
        settings.setSpacing(5);
        settings.getChildren().addAll(quit, instructions);


        //initialize activity display text
        Text activity = new Text("Begin Game. PLAYER TURN");
        activity.setFont(Font.font("IMPACT", 12));
        activity.setFill(Color.WHITE);

        //initialize card display
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(200, 200);


        //initialize card count displays using VBox
        Text hCardsRemaining = new Text("Player cards remaining: " + human.howManyCards());
        Text bCardsRemaining = new Text("Bot cards remaining: " + bot.howManyCards());
        Text pileCards = new Text("Cards in pile: " + pile.size());
        hCardsRemaining.setFont(Font.font("IMPACT", 15));
        bCardsRemaining.setFont(Font.font("IMPACT", 15));
        pileCards.setFont(Font.font("IMPACT", 15));
        hCardsRemaining.setFill(Color.WHITE);
        bCardsRemaining.setFill(Color.WHITE);
        pileCards.setFill(Color.DARKGOLDENROD);
        VBox score = new VBox();
        score.setAlignment(Pos.CENTER);
        score.setSpacing(2);
        score.getChildren().addAll(hCardsRemaining, bCardsRemaining, pileCards);

        //design USER buttons
        Button playCard = new Button("Play Card");
        Button slapDeck = new Button("Slap Deck");
        playCard.setFont(Font.font("STENCIL", 15));
        slapDeck.setFont(Font.font("STENCIL", 15));
        BackgroundFill[] darkSlateGrayBackgroundFill = new BackgroundFill[1];
        darkSlateGrayBackgroundFill[0] = new BackgroundFill(Color.DARKSLATEGRAY, null, null);
        playCard.setBackground(new Background(darkSlateGrayBackgroundFill));
        BackgroundFill[] darkGoldenRodBackgroundFill = new BackgroundFill[1];
        darkGoldenRodBackgroundFill[0] = new BackgroundFill(Color.DARKGOLDENROD, null, null);
        slapDeck.setBackground(new Background(darkGoldenRodBackgroundFill));
        playCard.setTextFill(Color.WHITE);
        slapDeck.setTextFill(Color.WHITE);
        playCard.setStyle("-fx-border-color: white;");
        slapDeck.setStyle("-fx-border-color: white;");
        playCard.setShape(new Polygon(0, 0, 40, 50, 250, 50, 210, 0));
        slapDeck.setShape(new Polygon(0, 50, 40, 0, 250, 0, 210, 50));
        playCard.setPrefSize(120, 30);
        slapDeck.setPrefSize(120, 30);
        HBox buttons = new HBox();
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(playCard, slapDeck);

        //design main VBox Layout
        VBox mainVBox = new VBox();
        mainVBox.setSpacing(10);
        mainVBox.getChildren().addAll(score, stackPane, activity, buttons);
        mainVBox.setAlignment(Pos.CENTER);

        //design overall BorderPane Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mainVBox);
        borderPane.setTop(settings);
        BorderPane.setAlignment(borderPane.getTop(), Pos.CENTER_RIGHT);

        //design and set scene's background image
        Image green = new Image("JPEG/background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(green,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        borderPane.setBackground(background);

        //initialize scene
        Scene scene = new Scene(borderPane, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);

        //implement USER buttons
        playCard.setOnAction(new PlayerPlay(human, bot, pile, burnPile, activity, hCardsRemaining, pileCards, counter, stackPane));
        slapDeck.setOnAction(new PlayerSlap(human, bot, pile, burnPile, activity, hCardsRemaining, pileCards, stackPane));

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                //playCard toggle on/off
                if (human.isTurn()) {
                    playCard.setDisable(false);
                } else {
                    playCard.setDisable(true);
                }

                //bot misSlap
                if (counter.getValue() == messUp.getValue()) {
                    counter.setValue(0);
                    messUp.setValue(minTurnMessUp + rand.nextInt(maxTurnMessUp));
                    timer = new Timer();
                    if (!canSlap(pile)) {
                        timer.schedule(new BotMisSlap(bot, pile, burnPile, activity, bCardsRemaining, pileCards), 100);
                    }
                }
                //Check for if human/bot can slap
                if (canSlap(pile)) {
                    this.stop();
                    slapDeck.setOnAction(new PlayerSlap(human, bot, pile, burnPile, activity, hCardsRemaining, pileCards, stackPane));
                    timer = new Timer();
                    timer.schedule(new BotSlap(human, bot, pile, burnPile, activity, bCardsRemaining, pileCards, stackPane), minBotSlapSpeed + rand.nextInt(maxBotSlapSpeed));
                    this.start();
                }

                //bot play to special card
                if (pile.size() > 0 && pile.get(pile.size() - 1).isSpecial() && !canSlap(pile) && bot.isTurn() && botGo) {
                    this.stop();
                    botGo = false;
                    humanTake = pile.get(pile.size() - 1).getCardsAfter();
                    //System.out.println("Card: " + pile.get(pile.size() - 1));
                    botNumOfTimes = pile.get(pile.size() - 1).getCardsAfter();
                    this.start();
                }
                //bot play
                if (humanGo && humanTake != 0 && botTake != 0 && bot.isTurn() && bot.howManyCards() > 0 && !canSlap(pile)) {
                    bot.setTurn(false);
                    timer = new Timer();
                    timer.schedule(new BotPlay(human, bot, pile, burnPile, activity, bCardsRemaining, pileCards, counter, stackPane), 1500);
                }
                //human play to special card
                if (pile.size() > 0 && pile.get(pile.size() - 1).isSpecial() && !canSlap(pile) && human.isTurn() && humanGo) {
                    this.stop();
                    humanGo = false;
                    botTake = pile.get(pile.size() - 1).getCardsAfter();
                    //System.out.println("Card: " + pile.get(pile.size() - 1));
                    humanNumOfTimes = pile.get(pile.size() - 1).getCardsAfter();
                    this.start();
                }
                //human takes pile
                if (!botGo && humanTake == 0 && !canSlap(pile)) {
                    playCard.setDisable(true);
                    human.setTurn(false);
                    bot.setTurn(false);
                    botGo = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            human.takePile(pile, burnPile);
                            activity.setFill(Color.WHITE);
                            activity.setText("Player takes pile. PLAYER TURN");
                            hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                            pileCards.setText("Cards in pile: 0");
                            Platform.runLater(() -> {
                                stackPane.getChildren().clear();
                            });
                            //System.out.println("Human takes pile");
                            humanGo = true;
                            botGo = true;
                            humanTake = -1;
                            botTake = -1;
                            humanNumOfTimes = 1;
                            botNumOfTimes = 1;
                            human.setTurn(true);
                            playCard.setDisable(false);
                        }
                    }, 500);

                }
                //bot takes pile
                if (!humanGo && botTake == 0 && !canSlap(pile)) {
                    playCard.setDisable(true);
                    bot.setTurn(false);
                    human.setTurn(false);
                    humanGo = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            bot.takePile(pile, burnPile);
                            activity.setFill(Color.WHITE);
                            activity.setText("Bot takes pile. BOT TURN");
                            bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                            pileCards.setText("Cards in pile: 0");
                            Platform.runLater(() -> {
                                stackPane.getChildren().clear();
                            });
                            //System.out.println("Bot takes pile");
                            humanGo = true;
                            botGo = true;
                            humanNumOfTimes = 1;
                            botNumOfTimes = 1;
                            botTake = -1;
                            humanTake = -1;
                            bot.setTurn(true);
                            playCard.setDisable(false);
                        }
                    }, 500);
                }

                //Checks for bot victory
                if (human.howManyCards() <= 0 && !canSlap(pile) && botGo) {
                    this.stop();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (human.howManyCards() <= 0 && display) {
                                display = false;
                                Platform.runLater(() -> {
                                    Text text = new Text("You Lost!");
                                    text.setFont(Font.font("Edwardian Script ITC", 80));
                                    text.setFill(Color.RED);
                                    Button quit = new Button("Quit");
                                    quit.setBackground(new Background(darkSlateGrayBackgroundFill));
                                    quit.setFont(Font.font("STENCIL", 25));
                                    quit.setTextFill(Color.WHITE);
                                    quit.setPrefSize(150, 50);
                                    quit.setShape(new Polygon(0, 0, 150, 0, 120, 50, 30, 50));

                                    VBox column = new VBox();
                                    column.setSpacing(30);
                                    column.setAlignment(Pos.CENTER);
                                    column.getChildren().addAll(text, quit);

                                    BorderPane borderPane = new BorderPane();
                                    borderPane.setCenter(column);
                                    borderPane.setBackground(background);
                                    quit.setOnAction(e -> {
                                        System.exit(0);
                                    });
                                    Scene defeat = new Scene(borderPane, width, height);
                                    primaryStage.setScene(defeat);
                                    primaryStage.show();
                                });
                            }
                        }
                    }, 550);
                    this.start();
                }
                //Checks for human victory
                if (bot.howManyCards() <= 0 && !canSlap(pile) && humanGo) {
                    this.stop();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (bot.howManyCards() <= 0 && display) {
                                display = false;
                                Platform.runLater(() -> {
                                    Text text = new Text("You Win!");
                                    text.setFont(Font.font("Edwardian Script ITC", 80));
                                    text.setFill(Color.WHITE);
                                    Button quit = new Button("Quit");
                                    quit.setBackground(new Background(darkSlateGrayBackgroundFill));
                                    quit.setFont(Font.font("STENCIL", 25));
                                    quit.setTextFill(Color.WHITE);
                                    quit.setPrefSize(150, 50);
                                    quit.setShape(new Polygon(0, 0, 150, 0, 120, 50, 30, 50));

                                    VBox column = new VBox();
                                    column.setSpacing(30);
                                    column.setAlignment(Pos.CENTER);
                                    column.getChildren().addAll(text, quit);

                                    BorderPane borderPane = new BorderPane();
                                    borderPane.setCenter(column);
                                    borderPane.setBackground(background);
                                    quit.setOnAction(e -> {
                                        System.exit(0);
                                    });
                                    Scene victory = new Scene(borderPane, width, height);
                                    primaryStage.setScene(victory);
                                    primaryStage.show();
                                });
                            }

                        }
                    }, 550);
                    this.start();
                }
            }
        }.start();
        primaryStage.setResizable(false);
        primaryStage.show();
    } //end start()

    /**
     * Method to check if an ArrayList of Cards can be slapped or not.
     * @param pile an ArrayList of Cards representing the current pile.
     * @return true if the pile can be slapped, false if the pile cannot be slapped.
     */
    private static boolean canSlap(ArrayList<Card> pile) {
        if (pile.size() <= 1) {
            return false;
        }
        int last = pile.size() - 1;
        if (pile.get(last).equals(pile.get(last - 1))) {
            //System.out.println("Double");
            return true;
        } else if (pile.get(last).getValue() == 12 && pile.get(last - 1).getValue() == 13) {
            //System.out.println("Marriage1");
            return true;
        } else if (pile.get(last).getValue() == 13 && pile.get(last - 1).getValue() == 12) {
            //System.out.println("Marriage2");
            return true;
        } else if (pile.size() >= 3 && pile.get(last).equals(pile.get(last - 2))) {
            //System.out.println("Sandwich");
            return true;
        } else if (pile.get(0).getValue() == pile.get(pile.size() - 1).getValue()) {
            //System.out.println("Top Bottom");
            return true;
        } else if (pile.size() >= 2 && (pile.get(pile.size() - 1).getValue() + pile.get(pile.size() - 2).getValue() == 10)) {
            //System.out.println("Tens");
            return true;
        } else if (pile.size() >= 4) {
            Card c1 = pile.get(pile.size() - 1);
            Card c2 = pile.get(pile.size() - 2);
            Card c3 = pile.get(pile.size() - 3);
            Card c4 = pile.get(pile.size() - 4);

            if ((c1.getValue() == c2.getValue() + 1) && (c2.getValue() == c3.getValue() + 1) && (c3.getValue() == c4.getValue() + 1)) {
                //System.out.println("Four in a row1");
                return true;
            } else if ((c1.getValue() == c2.getValue() - 1) && (c2.getValue() == c3.getValue() - 1) && (c3.getValue() == c4.getValue() - 1)) {
                //System.out.println("Four in a row2");
                return true;
            } else if (c1.getValue() == 11 && c2.getValue() == 12 && c3.getValue() == 13 && c4.getValue() == 1) {
                //System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 12 && c2.getValue() == 13 && c3.getValue() == 1 && c4.getValue() == 2) {
                //System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 13 && c2.getValue() == 1 && c3.getValue() == 2 && c4.getValue() == 3) {
                //System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 1 && c2.getValue() == 13 && c3.getValue() == 12 && c4.getValue() == 11) {
                //System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 2 && c2.getValue() == 1 && c3.getValue() == 13 && c4.getValue() == 12) {
                //System.out.println("Four in a row3");
                return true;
            } else {
                return c1.getValue() == 3 && c2.getValue() == 2 && c3.getValue() == 1 && c4.getValue() == 13;
            }
        } else {
            return false;
        }
    } //end method

    /**
     * Implements EventHandler.
     * Helper class corresponding to the player playing a card.
     */
    private class PlayerPlay implements EventHandler<ActionEvent> {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private Text activity;
        private Text hCardsRemaining;
        private Text pileCards;
        private MutableInteger counter;
        private StackPane stackPane;

        /**
         * Constructor for PlayerPlay.
         * @param human The human Player.
         * @param bot The bot Player.
         * @param pile The ArrayList of Cards corresponding to the current pile.
         * @param burnPile The ArrayList of Cards corresponding to the burn pile.
         * @param activity The Text representing the activity.
         * @param hCardsRemaining The Text representing how many cards the human has left.
         * @param pileCards The Text representing how many cards are on the pile.
         * @param counter The MutableInteger keeping track of turns.
         * @param stackPane The StackPane of cards.
         */
        PlayerPlay(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, Text activity, Text hCardsRemaining, Text pileCards, MutableInteger counter, StackPane stackPane) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.activity = activity;
            this.hCardsRemaining = hCardsRemaining;
            this.pileCards = pileCards;
            this.counter = counter;
            this.stackPane = stackPane;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (human.isTurn() && human.howManyCards() > 0 && !canSlap(pile)) {
                activity.setFill(Color.WHITE);
                human.playCard(pile);
                //System.out.println("Player plays outside, pile: " + burnPile +  pile);
                ImageView card = new ImageView();
                card.setImage(pile.get(pile.size() - 1).getImage());
                card.setRotate(rand.nextInt(360));
                card.setFitWidth(100);
                card.setFitHeight(153);
                Platform.runLater(() -> {
                    stackPane.getChildren().add(card);
                });
                hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                this.counter.increment();
                botTake--;
                if (humanNumOfTimes == 1 && !humanGo && !pile.get(pile.size() - 1).isSpecial()) {
                    botTake = 0;
                    humanGo = false;
                } else if (humanNumOfTimes == 1 || pile.get(pile.size() - 1).isSpecial()) {
                    humanGo = true;
                    botGo = true;
                    humanTake = -1;
                    botTake = -1;
                    humanNumOfTimes = 1;
                    activity.setText("BOT TURN");
                    human.setTurn(false);
                    bot.setTurn(true);
                } else {
                    humanNumOfTimes--;
                    activity.setText("PLAYER TURN");
                }
            }
            //System.out.println("humanGo : " + humanGo + ", botTake: " + botTake + ", humanTake: " + humanTake);

        }
    } //end class

    /**
     * Implements EventHandler
     * Helper class corresponding to the human slapping the pile.
     */
    private class PlayerSlap implements EventHandler<ActionEvent> {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private Text activity;
        private Text hCardsRemaining;
        private Text pileCards;
        private StackPane stackPane;

        /**
         * Constructor for PlayerSlap.
         * @param human The human Player.
         * @param bot The bot Player.
         * @param pile The ArrayList of Cards corresponding to the current pile.
         * @param burnPile The ArrayList of Cards corresponding to the burn pile.
         * @param activity The Text representing the activity.
         * @param hCardsRemaining The Text representing how many cards the human has left.
         * @param pileCards The Text representing how many cards are on the pile.
         * @param stackPane The StackPane of cards.
         */
        PlayerSlap(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, Text activity, Text hCardsRemaining, Text pileCards, StackPane stackPane) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.activity = activity;
            this.hCardsRemaining = hCardsRemaining;
            this.pileCards = pileCards;
            this.stackPane = stackPane;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (human.howManyCards() > 0) {
                if (canSlap(pile)) {
                    //System.out.println("Player slaps outside, pile:" + burnPile + pile);
                    human.takePile(pile, burnPile);
                    Platform.runLater(() -> {
                        stackPane.getChildren().clear();
                    });
                    activity.setFill(Color.WHITE);
                    activity.setText("Player slaps correctly. PLAYER TURN");
                    hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                    int size = pile.size() + burnPile.size();
                    pileCards.setText("Cards on pile: " + size);
                    botGo = true;
                    humanGo = true;
                    humanTake = -1;
                    botTake = -1;
                    botNumOfTimes = 1;
                    humanNumOfTimes = 1;
                    human.setTurn(true);
                    bot.setTurn(false);
                } else if (pile.size() != 0) {
                    //System.out.println("Player burns card, pile: " + burnPile + pile);
                    human.burnCard(burnPile);
                    activity.setFill(Color.RED);
                    if (activity.getText().equals("BOT TURN") || activity.getText().equals("PLAYER TURN")) {
                        activity.setText("Player slaps incorrectly. " + activity.getText());
                    } else {
                        if (human.isTurn()) {
                            activity.setText("Player slaps incorrectly. PLAYER TURN");
                        } else {
                            activity.setText("Player slaps incorrectly. BOT TURN");
                        }
                    }
                    hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                    int size = pile.size() + burnPile.size();
                    pileCards.setText("Cards on pile: " + size);
                }
                //System.out.println("Pile now: " + burnPile + pile);

            }
            //System.out.println("humanGo : " + humanGo + ", botTake: " + botTake + ", humanTake: " + humanTake);
        }
    } //end class

    /**
     * Extends TimerTask
     * Helper class corresponding to bot playing a card.
     */
    private class BotPlay extends TimerTask {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;
        private MutableInteger counter;
        private StackPane stackPane;

        /**
         * Constructor for BotPlay.
         * @param human The human Player.
         * @param bot The bot Player.
         * @param pile The ArrayList of Cards corresponding to the current pile.
         * @param burnPile The ArrayList of Cards corresponding to the burn pile.
         * @param activity The Text representing the activity.
         * @param bCardsRemaining The Text representing how many cards the bot has left.
         * @param pileCards The Text representing how many cards are on the pile.
         * @param counter The MutableInteger keeping track of turns.
         * @param stackPane The StackPane of cards.
         */
        BotPlay(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, Text activity, Text bCardsRemaining, Text pileCards, MutableInteger counter, StackPane stackPane) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
            this.counter = counter;
            this.stackPane = stackPane;
        }

        @Override
        public void run() {
            if (bot.howManyCards() > 0 && !canSlap(pile)) {
                bot.playCard(pile);
                activity.setFill(Color.WHITE);
                //System.out.println("Bot Plays outside, pile: " + burnPile + pile);
                ImageView card = new ImageView();
                card.setImage(pile.get(pile.size() - 1).getImage());
                card.setRotate(rand.nextInt(360));
                card.setFitWidth(100);
                card.setFitHeight(153);
                Platform.runLater(() -> {
                    stackPane.getChildren().add(card);
                });
                bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                this.counter.increment();
                humanTake--;
                if (botNumOfTimes == 1 && !botGo && !pile.get(pile.size() - 1).isSpecial()) {
                    humanTake = 0;
                    botGo = false;
                } else if (botNumOfTimes == 1 || pile.get(pile.size() - 1).isSpecial()) {
                    botGo = true;
                    humanGo = true;
                    humanTake = -1;
                    botTake = -1;
                    botNumOfTimes = 1;
                    activity.setText("PLAYER TURN");
                    human.setTurn(true);
                    bot.setTurn(false);
                } else {
                    botNumOfTimes--;
                    activity.setText("BOT TURN");
                    human.setTurn(false);
                    bot.setTurn(true);
                }
                //System.out.println("humanGo: " + humanGo + ", botTake: " + botTake + ", botGo: " + botGo + ", humanTake: " + humanTake);
            }
        }
    } //end class

    /**
     * Extends TimerTask.
     * Helper class corresponding to the bot slapping the deck CORRECTLY;
     */
    private class BotSlap extends TimerTask {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;
        private StackPane stackPane;

        /**
         * Constructor for BotSlap.
         * @param human The human Player.
         * @param bot The bot Player.
         * @param pile The ArrayList of Cards corresponding to the current pile.
         * @param burnPile The ArrayList of Cards corresponding to the burn pile.
         * @param activity The Text representing the activity.
         * @param bCardsRemaining The Text representing how many cards the bot has left.
         * @param pileCards The Text representing how many cards are on the pile.
         * @param stackPane The StackPane of cards.
         */
        BotSlap(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, Text activity, Text bCardsRemaining, Text pileCards, StackPane stackPane) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
            this.stackPane = stackPane;
        }

        @Override
        public void run() {
            if (pile.size() != 0 && canSlap(pile)) {
                //System.out.println("Bot slaps pile, pile: " + burnPile + pile);
                bot.takePile(pile, burnPile);
                Platform.runLater(() -> {
                    stackPane.getChildren().clear();
                });
                activity.setFill(Color.WHITE);
                activity.setText("Bot slaps correctly. BOT TURN");
                bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                botGo = true;
                humanGo = true;
                botTake = -1;
                humanTake = -1;
                botNumOfTimes = 1;
                humanNumOfTimes = 1;
                //System.out.println("humanGo (true) : " + humanGo + ", botTake (!= 0): " + botTake + ", humanTake: " + humanTake);
                bot.setTurn(true);
                human.setTurn(false);
                //System.out.println("Pile now: " + burnPile + pile);
            }

        }
    } //end class

    /**
     * Extends TimerTask.
     * Helper class corresponding to the bot incorrectly slapping the pile.
     */
    private class BotMisSlap extends TimerTask {
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;

        /**
         * Constructor for BotMisSlap.
         * @param bot The bot Player.
         * @param pile The ArrayList of Cards corresponding to the current pile.
         * @param burnPile The ArrayList of Cards corresponding to the burn pile.
         * @param activity The Text representing the activity.
         * @param bCardsRemaining The Text representing how many cards the bot has left.
         * @param pileCards The Text representing how many cards are on the pile.
         */
        BotMisSlap(Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, Text activity, Text bCardsRemaining, Text pileCards) {
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
        }
        @Override
        public void run() {
            //System.out.println("Bot burns card, pile: " + burnPile + pile);
            bot.burnCard(burnPile);
            activity.setFill(Color.RED);
            if (activity.getText().equals("BOT TURN") || activity.getText().equals("PLAYER TURN")) {
                activity.setText("Bot slaps incorrectly. " + activity.getText());
            } else {
                if (bot.isTurn()) {
                    activity.setText("Bot slaps incorrectly. BOT TURN");
                } else {
                    activity.setText("Bot slaps incorrectly. PLAYER TURN");
                }
            }
            //System.out.println("humanGo (true) : " + humanGo + ", botTake (!= 0): " + botTake + ", humanTake: " + humanTake);
            bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
            int size = pile.size() + burnPile.size();
            pileCards.setText("Cards on pile: " + size);
            //System.out.println("Pile now:" + burnPile + pile);
        }
    } //end class

    /**
     * Helper class so that integers can be passed by reference.
     */
    private class MutableInteger {
        private int value;

        /**
         * Constructor for MutableInteger.
         * @param value the int value of this object.
         */
        MutableInteger(int value) {
            this.value = value;
        }

        /**
         *
         * @return An int representation of this object.
         */
        public int getValue() {
            return this.value;
        }

        /**
         *
         * @param value Sets that object to this int value.
         */
        public void setValue(int value) {
            this.value = value;
        }

        /**
         * Increments this object by a value of 1.
         */
        public void increment() {
            this.value += 1;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    } //end class

} //end file
