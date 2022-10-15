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
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;


public class Main extends Application {
    Point2D size = new Point2D(1000,800);
    Set<KeyCode> KeysDown = new HashSet<>();

    @Override
    public void start(Stage stage) throws Exception {
        Group gRoot = new Group();
        Scene scene = new Scene(gRoot, size.getX(), size.getY());

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
                if (old < 0) old = nano;   // if old is negative
                double delta = (nano - old) / 1e9;

                old = nano;

                elspsedTime += delta;

                fpsLabel.setText(String.format("%.2f %.2f", 1 / delta, elspsedTime));

                //Game Loop
            }
        };



        loop.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}