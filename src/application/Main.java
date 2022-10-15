package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;


public class Main extends Application {

    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;
    public static final int BALL_SIZE = 50;

    public double getBallSpeedY() {
        return ballSpeedY;
    }

    public void setBallSpeedY(double ballSpeedY) {
        this.ballSpeedY = ballSpeedY;
    }

    private double ballSpeedY = 5;

    public boolean isBallUp() {
        return isBallUp;
    }

    public void setBallUp(boolean ballUp) {
        isBallUp = ballUp;
    }

    public double getBallVelocityY() {
        return ballVelocityY;
    }

    /**
     * This function only calculates the y axis velocity  so we can have x
     * velcoity indepently   is just for the ball. to make the ball go up or
     * down at a given speed
     */
    public void calculateBallVelocityY() {
     //   this.ballVelocityY = ballVelocityY;
        if(isBallUp()){
            this.ballVelocityY = -getBallSpeedY();
        }
        else {
            this.ballVelocityY = getBallSpeedY();
        }
    }

    private double ballVelocityY = 0 ;
    private boolean isNewGame = true;

    private boolean isBallUp = false;
    private boolean isBallLeft = false;

    Set<KeyCode> KeysDown = new HashSet<>();

    @Override
    public void start(Stage stage) throws Exception {
        Group gRoot = new Group();

        Rectangle ball = new Rectangle(BALL_SIZE,BALL_SIZE);
        ball.setFill(Color.BLUE);
        gRoot.getChildren().add(ball);


        Rectangle bat = new Rectangle(80,20);
        bat.setFill(Color.RED);
        bat.setTranslateY(APP_HEIGHT-bat.getHeight());
        gRoot.getChildren().add(bat);

        Scene scene = new Scene(gRoot, APP_WIDTH, APP_HEIGHT);

        stage.setScene(scene);
        stage.setTitle("Pong-Game");
        scene.setFill(Color.WHITE);

        Label fpsLabel = new Label();
        fpsLabel.setTranslateX(2);
        fpsLabel.setTextFill(Color.BLACK);

        gRoot.getChildren().add(fpsLabel);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event){
                KeysDown.add(event.getCode());
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event){
                KeysDown.remove(event.getCode());
            }
        });


        // Setup of the Game //


        AnimationTimer loop = new AnimationTimer() {

            double old = -1; // The old time
            double elspsedTime = 0;

            public void handle(long nano) {
                calculateBallVelocityY();
                ballFallsBottomOfScreenRestartGame(ball);
                ball.setTranslateY(ball.getTranslateY() + getBallVelocityY()); // Moving the ball
                ballHitsAppTopGoDown(ball);
                // When the ball hits the bat make the ball go up again
                if (ball.getBoundsInParent().intersects(bat.getBoundsInParent())){
                   // setBallSpeedY(-5);
                    setBallUp(true);
                }


            }   //Game Loop
        };



        loop.start();

        scene.setOnMouseMoved(e->{
            bat.setTranslateX(e.getX());

        });


        stage.show();
    }

    private void ballFallsBottomOfScreenRestartGame(Rectangle ball) {
        // ball falls to bottom of the scree restart the Game.
        if (isNewGame){
            isNewGame = false;
            //TODO set ballspeed x set it to zero
            setBallSpeedY(5);
            ball.setTranslateX(100);

            ball.setTranslateY(100);
        }
        if (ball.getTranslateY()>= APP_HEIGHT){
            isNewGame = true;
        }
    }

    private void ballHitsAppTopGoDown(Rectangle ball) { //TODO change the name to a meaningfull name
        // When the ball is at top make it go down again
        if (ball.getTranslateY()== 0){
            //setBallSpeedY(5);
            setBallUp(false);
            ball.setFill(Color.RED);
            ball.setTranslateY(ball.getTranslateY()+getBallVelocityY());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}