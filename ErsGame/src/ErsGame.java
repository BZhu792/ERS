import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ErsGame extends Application {
    final private int height = 417;
    final private int width = 626;
    private int minTurnMessUp;
    final private int maxTurnMessUp = 30; //actually is this value + minTurnMessUp
    private int minBotSlapSpeed;
    final private int maxBotSlapSpeed = 500; //actually is this value + minBotSlapSpeed
    private Timer timer;
    private Random rand = new Random();
    private boolean humanSwitch = true;
    public static int humanNumOfTimes = 1;
    public static boolean go = true;
    public static int steal = -1;
    public static boolean move = true;
    public static int botNumOfTimes = 1;
    public static int humanSteal = -1;

    public ErsGame() {
        this(20, 1000);
    }
    public ErsGame(int minTurnMessUp, int minBotSlapSpeed) {
        this.minTurnMessUp = minTurnMessUp;
        this.minBotSlapSpeed = minBotSlapSpeed;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting game with: minTurnMessUp " + minTurnMessUp + ", minBotSlapSpeed " + minBotSlapSpeed);

        //INITIALIZE BACKEND DECKS, PLAYERS, AND BOT MESSUPS
        Deck deck = new Deck();
        ArrayList<Card> allCards = deck.toShuffledArrayList();
        ArrayList<Card> humanCards = new ArrayList<>(allCards.subList(0, 26));
        ArrayList<Card> botCards = new ArrayList<> (allCards.subList(26, 52));
        ArrayList<Card> pile = new ArrayList<>();
        ArrayList<Card> burnPile = new ArrayList<>();

        Player human = new Player("Player", humanCards, true);
        Player bot = new Player("Bot", botCards, false);

        mutableInteger counter = new mutableInteger(0);
        mutableInteger messUp = new mutableInteger(minTurnMessUp + rand.nextInt(maxTurnMessUp));

        //INITIALIZE JAVAFX FRAME
        //initialize quit button
        BackgroundFill[] bgf9 = new BackgroundFill[1];
        bgf9[0] = new BackgroundFill(Color.BLACK, null, null);
        Button returnToMenu = new Button("Return to Menu");
        returnToMenu.setBackground(new Background(bgf9));
        returnToMenu.setTextFill(Color.WHITE);
        returnToMenu.setFont(Font.font("IMPACT", 10));
        returnToMenu.setPrefSize(85, 20);
        returnToMenu.setOnAction(e -> {
            primaryStage.close();
            Platform.runLater( () -> new ErsMenu().start( new Stage() ) );
        });

        //initialize activity display text
        Text activity = new Text("Begin Game. PLAYER TURN");
        activity.setFont(Font.font("IMPACT", 12));
        activity.setFill(Color.WHITE);

        //initialize card display
        ImageView cardOnPile = new ImageView();
        Image black = new Image("JPEG/black.jpg");
        try {
            cardOnPile.setImage(pile.get(pile.size() - 1).getImage());
        } catch (Exception e) {
            cardOnPile.setImage(black);
        }
        cardOnPile.setFitWidth(100);
        cardOnPile.setFitHeight(153);
        cardOnPile.setSmooth(true);
        cardOnPile.setCache(true);

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
        BackgroundFill[] bgf1 = new BackgroundFill[1];
        bgf1[0] = new BackgroundFill(Color.DARKSLATEGRAY, null, null);
        playCard.setBackground(new Background(bgf1));
        BackgroundFill[] bgf2 = new BackgroundFill[1];
        bgf2[0] = new BackgroundFill(Color.DARKGOLDENROD, null, null);
        slapDeck.setBackground(new Background(bgf2));
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
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(score, cardOnPile, activity, buttons);
        layout.setAlignment(Pos.CENTER);

        //design overall BorderPane Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setTop(returnToMenu);
        BorderPane.setAlignment(borderPane.getTop(), Pos.CENTER_RIGHT);

        //design and set scene's background image
        Image background = new Image("JPEG/background.jpg");
        BackgroundImage backgroundimage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundimage);
        borderPane.setBackground(background1);

        //initialize scene
        Scene scene = new Scene(borderPane, width, height);
        primaryStage.setTitle("ERS");
        primaryStage.setScene(scene);

        //implement USER buttons
        playCard.setOnAction(new playerPlay(human, bot, pile, burnPile, cardOnPile, activity, hCardsRemaining, pileCards, counter, humanSwitch));
        slapDeck.setOnAction(new playerSlap(human, bot, pile, burnPile, cardOnPile, black, activity, hCardsRemaining, pileCards));

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
                    if (!canSlap(pile)){
                        timer.schedule(new botMisSlap(bot, pile, burnPile, cardOnPile, activity, bCardsRemaining, pileCards), 100);
                    }
                }
                //Check for if human/bot can slap
                if (canSlap(pile)) {
                    this.stop();
                    slapDeck.setOnAction(new playerSlap(human, bot, pile, burnPile, cardOnPile, black, activity, hCardsRemaining, pileCards));
                    timer = new Timer();
                    timer.schedule(new botSlap(human, bot, pile, burnPile, cardOnPile, black, activity, bCardsRemaining, pileCards), minBotSlapSpeed + rand.nextInt(maxBotSlapSpeed));
                    this.start();
                }

                //bot play to special card
                if (pile.size() > 0 && pile.get(pile.size() - 1).isSpecial() && !canSlap(pile) && bot.isTurn() && move) {
                    this.stop();
                    move = false;
                    humanSteal = pile.get(pile.size() - 1).getCardsAfter();
                    System.out.println("Card: " + pile.get(pile.size() - 1));
                    botNumOfTimes = pile.get(pile.size() - 1).getCardsAfter();
                    this.start();
                }
                //bot play
                if (go && humanSteal != 0 && steal != 0 && bot.isTurn() && bot.howManyCards() > 0 && !canSlap(pile)) {
                    bot.setTurn(false);
                    timer = new Timer();
                    timer.schedule(new botPlay(human, bot, pile, burnPile, cardOnPile, activity, bCardsRemaining, pileCards, counter, true), 1500);
                }
                //human play to special card
                if (pile.size() > 0 && pile.get(pile.size() - 1).isSpecial() && !canSlap(pile) && human.isTurn() && go) {
                    this.stop();
                    go = false;
                    steal = pile.get(pile.size() - 1).getCardsAfter();
                    System.out.println("Card: " + pile.get(pile.size() - 1));
                    ErsGame.humanNumOfTimes = pile.get(pile.size() - 1).getCardsAfter();
                    this.start();
                }
                //human takes pile
                if (!move && humanSteal == 0 && !canSlap(pile)) {
                    playCard.setDisable(true);
                    human.setTurn(false);
                    bot.setTurn(false);
                    move = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (humanSteal == 0) {
                                human.takePile(pile, burnPile);
                                activity.setText("Player takes pile. PLAYER TURN");
                                hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                                pileCards.setText("Cards in pile: 0");
                                cardOnPile.setImage(black);
                                System.out.println("Human takes pile");
                                go = true;
                                move = true;
                                humanSteal = -1;
                                steal = -1;
                                humanNumOfTimes = 1;
                                botNumOfTimes = 1;
                                human.setTurn(true);
                                playCard.setDisable(false);
                            }
                        }
                    }, 500);

                }
                //bot takes pile
                if (!go && steal == 0 && !canSlap(pile)) {
                    playCard.setDisable(true);
                    bot.setTurn(false);
                    human.setTurn(false);
                    go = true;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (steal == 0) {
                                bot.takePile(pile, burnPile);
                                activity.setText("Bot takes pile. BOT TURN");
                                bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                                pileCards.setText("Cards in pile: 0");
                                cardOnPile.setImage(black);
                                System.out.println("Bot takes pile");
                                go = true;
                                move = true;
                                humanNumOfTimes = 1;
                                botNumOfTimes = 1;
                                steal = -1;
                                humanSteal = -1;
                                bot.setTurn(true);
                                playCard.setDisable(false);
                            }
                        }
                    }, 500);
                }

                //Checks for bot victory
                if (human.howManyCards() <= 0) {
                    this.stop();
                    Platform.runLater(() -> {
                        Text text = new Text("You Lost!");
                        text.setFont(Font.font("Edwardian Script ITC", 80));
                        text.setFill(Color.WHITE);
                        Button playAgain = new Button("Menu");
                        Button quit = new Button("Quit");
                        playAgain.setBackground(new Background(bgf1));
                        quit.setBackground(new Background(bgf2));
                        playAgain.setFont(Font.font("Comic Sans MS", 20));
                        quit.setFont(Font.font("Comic Sans MS", 20));
                        playAgain.setPrefSize(150, 50);
                        quit.setPrefSize(150, 50);


                        VBox row = new VBox();
                        row.setSpacing(20);
                        row.setAlignment(Pos.CENTER);
                        row.getChildren().addAll(playAgain, quit);
                        VBox column = new VBox();
                        column.setSpacing(30);
                        column.setAlignment(Pos.CENTER);
                        column.getChildren().addAll(text, row);

                        BorderPane borderPane = new BorderPane();
                        borderPane.setCenter(column);
                        borderPane.setBackground(background1);
                        playAgain.setOnAction(e -> {
                            primaryStage.close();
                            Platform.runLater( () -> new ErsMenu().start( new Stage() ) );
                        });
                        quit.setOnAction(e -> {
                            System.exit(0);
                        });
                        Scene defeat = new Scene(borderPane, width, height);
                        primaryStage.setScene(defeat);
                        primaryStage.show();

                    });
                }
                //Checks for human victory
                if (bot.howManyCards() <= 0) {
                    this.stop();
                    Platform.runLater(() -> {
                        Text text = new Text("You Win!");
                        text.setFont(Font.font("Edwardian Script ITC", 80));
                        text.setFill(Color.WHITE);
                        Button playAgain = new Button("Menu");
                        Button quit = new Button("Quit");
                        playAgain.setBackground(new Background(bgf1));
                        quit.setBackground(new Background(bgf2));
                        playAgain.setFont(Font.font("Comic Sans MS", 20));
                        quit.setFont(Font.font("Comic Sans MS", 20));
                        playAgain.setPrefSize(150, 50);
                        quit.setPrefSize(150, 50);


                        VBox row = new VBox();
                        row.setSpacing(20);
                        row.setAlignment(Pos.CENTER);
                        row.getChildren().addAll(playAgain, quit);
                        VBox column = new VBox();
                        column.setSpacing(30);
                        column.setAlignment(Pos.CENTER);
                        column.getChildren().addAll(text, row);

                        BorderPane borderPane = new BorderPane();
                        borderPane.setCenter(column);
                        borderPane.setBackground(background1);
                        playAgain.setOnAction(e -> {
                            primaryStage.close();
                            Platform.runLater( () -> new ErsMenu().start( new Stage() ) );
                        });
                        quit.setOnAction(e -> {
                            System.exit(0);
                        });
                        Scene victory = new Scene(borderPane, width, height);
                        primaryStage.setScene(victory);
                        primaryStage.show();
                    });
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
            System.out.println("Double");
            return true;
        } else if (pile.get(last).getValue() == 12 && pile.get(last - 1).getValue() == 13) {
            System.out.println("Marriage1");
            return true;
        } else if (pile.get(last).getValue() == 13 && pile.get(last - 1).getValue() == 12) {
            System.out.println("Marriage2");
            return true;
        } else if (pile.size() >= 3 && pile.get(last).equals(pile.get(last - 2))) {
            System.out.println("Sandwich");
            return true;
        } else if (pile.get(0).getValue() == pile.get(pile.size() - 1).getValue()) {
            System.out.println("Top Bottom");
            return true;
        } else if (pile.size() >= 2 && (pile.get(pile.size() - 1).getValue() + pile.get(pile.size() - 2).getValue() == 10)) {
            System.out.println("Tens");
            return true;
        }
        else if (pile.size() >= 4) {
            Card c1 = pile.get(pile.size() - 1);
            Card c2 = pile.get(pile.size() - 2);
            Card c3 = pile.get(pile.size() - 3);
            Card c4 = pile.get(pile.size() - 4);

            if ((c1.getValue() == c2.getValue() + 1) && (c2.getValue() == c3.getValue() + 1) && (c3.getValue() == c4.getValue() + 1)) {
                System.out.println("Four in a row1");
                return true;
            } else if ((c1.getValue() == c2.getValue() - 1) && (c2.getValue() == c3.getValue() - 1) && (c3.getValue() == c4.getValue() - 1)) {
                System.out.println("Four in a row2");
                return true;
            } else if (c1.getValue() == 11 && c2.getValue() == 12 && c3.getValue() == 13 && c4.getValue() == 1) {
                System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 12 && c2.getValue() == 13 && c3.getValue() == 1 && c4.getValue() == 2) {
                System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 13 && c2.getValue() == 1 && c3.getValue() == 2 && c4.getValue() == 3) {
                System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 1 && c2.getValue() == 13 && c3.getValue() == 12 && c4.getValue() == 11) {
                System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 2 && c2.getValue() == 1 && c3.getValue() == 13 && c4.getValue() == 12) {
                System.out.println("Four in a row3");
                return true;
            } else if (c1.getValue() == 3 && c2.getValue() == 2 && c3.getValue() == 1 && c4.getValue() == 13) {
                System.out.println("Four in a row3");
                return true;
            }
            else {
                return false;
            }
          }
         else {
            return false;
        }
    }

    /**
     * Implements EventHandler.
     * Helper class corresponding to the player playing a card.
     */
    private class playerPlay implements EventHandler<ActionEvent> {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private ImageView cardOnPile;
        private Text activity;
        private Text hCardsRemaining;
        private Text pileCards;
        private mutableInteger counter;
        private boolean switchTurns;

        public playerPlay(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, ImageView cardOnPile, Text activity, Text hCardsRemaining, Text pileCards, mutableInteger counter, boolean switchTurns) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.cardOnPile = cardOnPile;
            this.activity = activity;
            this.hCardsRemaining = hCardsRemaining;
            this.pileCards = pileCards;
            this.counter = counter;
            this.switchTurns = switchTurns;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (human.isTurn() && human.howManyCards() > 0 && !canSlap(pile)) {
                human.playCard(pile);
                System.out.println("Player plays outside, pile: " + burnPile +  pile);
                cardOnPile.setImage(pile.get(pile.size() - 1).getImage());
                hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                this.counter.increment();
                steal--;
                if (ErsGame.humanNumOfTimes == 1 && !go && !pile.get(pile.size() - 1).isSpecial()) {
                    steal = 0;
                    go = false;
                } else if (ErsGame.humanNumOfTimes == 1 || pile.get(pile.size() - 1).isSpecial()) {
                    go = true;
                    move = true;
                    humanSteal = -1;
                    steal = -1;
                    humanNumOfTimes = 1;
                    activity.setText("BOT TURN");
                    human.setTurn(false);
                    bot.setTurn(true);
                } else {
                    ErsGame.humanNumOfTimes--;
                    activity.setText("PLAYER TURN");
                }
            }
            System.out.println("Go : " + go + ", steal: " + steal + ", humanSteal: " + humanSteal);

        }
    }
    /**
     * Implements EventHandler
     * Helper class corresponding to the human slapping the pile.
     */
    private class playerSlap implements EventHandler<ActionEvent> {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private ImageView cardOnPile;
        private Image black;
        private Text activity;
        private Text hCardsRemaining;
        private Text pileCards;

        public playerSlap(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, ImageView cardOnPile, Image black, Text activity, Text hCardsRemaining, Text pileCards) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.cardOnPile = cardOnPile;
            this.black = black;
            this.activity = activity;
            this.hCardsRemaining = hCardsRemaining;
            this.pileCards = pileCards;
        }
        @Override
        public void handle(ActionEvent actionEvent) {
            if (human.howManyCards() > 0) {
                if (canSlap(pile)) {
                    System.out.println("Player slaps outside, pile:" + burnPile + pile);
                    human.takePile(pile, burnPile);
                    cardOnPile.setImage(black);
                    activity.setText("Player slaps correctly. PLAYER TURN");
                    hCardsRemaining.setText("Player cards remaining: " + human.howManyCards());
                    int size = pile.size() + burnPile.size();
                    pileCards.setText("Cards on pile: " + size);
                    move = true;
                    go = true;
                    humanSteal = -1;
                    steal = -1;
                    human.setTurn(true);
                    bot.setTurn(false);
                } else if (pile.size() != 0) {
                    System.out.println("Player burns card, pile: " + burnPile + pile);
                    human.burnCard(burnPile);
                    cardOnPile.setImage(pile.get(pile.size() - 1).getImage());
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
                System.out.println("Pile now: " + burnPile + pile);

            }
            System.out.println("Go : " + go + ", steal: " + steal + ", humanSteal: " + humanSteal);
        }
    }

    /**
     * Extends TimerTask
     * Helper class corresponding to bot playing a card.
     */
    private class botPlay extends TimerTask {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private ImageView cardOnPile;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;
        private mutableInteger counter;
        private boolean switchTurns;

        public botPlay(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, ImageView cardOnPile, Text activity, Text bCardsRemaining, Text pileCards, mutableInteger counter, boolean switchTurns) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.cardOnPile = cardOnPile;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
            this.counter = counter;
            this.switchTurns = switchTurns;
        }


        @Override
        public void run() {
            if (bot.howManyCards() > 0 && !canSlap(pile)) {
                bot.playCard(pile);
                System.out.println("Bot Plays outside, pile: " + burnPile + pile);
                cardOnPile.setImage(pile.get(pile.size() - 1).getImage());
                bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                this.counter.increment();
                humanSteal--;
                if (botNumOfTimes == 1 && !move && !pile.get(pile.size() - 1).isSpecial()) {
                    humanSteal = 0;
                    move = false;
                } else if (botNumOfTimes == 1 || pile.get(pile.size() - 1).isSpecial()) {
                    move = true;
                    go = true;
                    humanSteal = -1;
                    steal = -1;
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
                System.out.println("Go: " + go + ", steal: " + steal + ", move: " + move + ", humanSteal: " + humanSteal);
            }
        }
    }//end class

    /**
     * Extends TimerTask.
     * Helper class corresponding to the bot slapping the deck CORRECTLY;
     */
    private class botSlap extends TimerTask {
        private Player human;
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private ImageView cardOnPile;
        private Image black;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;

        public botSlap(Player human, Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, ImageView cardOnPile, Image black, Text activity, Text bCardsRemaining, Text pileCards) {
            this.human = human;
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.cardOnPile = cardOnPile;
            this.black = black;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
        }

        @Override
        public void run() {
            if (pile.size() != 0 && canSlap(pile)) {
                System.out.println("Bot slaps pile, pile: " + burnPile + pile);
                bot.takePile(pile, burnPile);
                cardOnPile.setImage(black);
                activity.setText("Bot slaps correctly. BOT TURN");
                bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
                int size = pile.size() + burnPile.size();
                pileCards.setText("Cards on pile: " + size);
                move = true;
                go = true;
                steal = -1;
                humanSteal = -1;
                System.out.println("Go (true) : " + go + ", steal (!= 0): " + steal + ", humanSteal: " + humanSteal);
                bot.setTurn(true);
                human.setTurn(false);
                System.out.println("Pile now: " + burnPile + pile);
            }

        }
    }

    /**
     * Extends TimerTask.
     * Helper class corresponding to the bot incorrectly slapping the pile.
     */
    private class botMisSlap extends TimerTask {
        private Player bot;
        private ArrayList<Card> pile;
        private ArrayList<Card> burnPile;
        private ImageView cardOnPile;
        private Text activity;
        private Text bCardsRemaining;
        private Text pileCards;

        public botMisSlap(Player bot, ArrayList<Card> pile, ArrayList<Card> burnPile, ImageView cardOnPile, Text activity, Text bCardsRemaining, Text pileCards) {
            this.bot = bot;
            this.pile = pile;
            this.burnPile = burnPile;
            this.cardOnPile = cardOnPile;
            this.activity = activity;
            this.bCardsRemaining = bCardsRemaining;
            this.pileCards = pileCards;
        }
        @Override
        public void run() {
            System.out.println("Bot burns card, pile: " + burnPile + pile);
            bot.burnCard(burnPile);
            if (activity.getText().equals("BOT TURN") || activity.getText().equals("PLAYER TURN")) {
                activity.setText("Bot slaps incorrectly. " + activity.getText());
            } else {
                if (bot.isTurn()) {
                    activity.setText("Bot slaps incorrectly. BOT TURN");
                } else {
                    activity.setText("Bot slaps incorrectly. PLAYER TURN");
                }
            }
            System.out.println("Go (true) : " + go + ", steal (!= 0): " + steal + ", humanSteal: " + humanSteal);
            bCardsRemaining.setText("Bot cards remaining: " + bot.howManyCards());
            int size = pile.size() + burnPile.size();
            pileCards.setText("Cards on pile: " + size);
            System.out.println("Pile now:" + burnPile + pile);
        }
    }

    /**
     * Helper class so that integers can be passed by reference.
     */
    private class mutableInteger {
        private int value;

        public mutableInteger(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void increment() {
            this.value += 1;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public static void setFinalCard(Card card) {

    }


}
