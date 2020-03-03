
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class task2 extends Application {

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 500;
    private static final int FPS = 60;
    static ArrayList<Integer> size = new ArrayList<>();
    static ArrayList<Integer> arrX = new ArrayList<>();
    static ArrayList<Integer> arrY = new ArrayList<>();
    static final Random RANDOM = new Random();
    static final int UNIVERSE = 200;

    private boolean closed;
    private GraphicsContext gc;

    private int x = 0;
    private int y = 0;

    public static void main(String[] args) {
        //stars
        for (int i = 0; i < UNIVERSE; i++) {
            int stars = RANDOM.nextInt(3);
            size.add(stars);
            int x = RANDOM.nextInt(800);
            arrX.add(x);
            int y = RANDOM.nextInt(500);
            arrY.add(y);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFxSample");
        Canvas canvas = new Canvas();
        canvas.setWidth(BOARD_WIDTH);
        canvas.setHeight(BOARD_HEIGHT);
        BorderPane group = new BorderPane(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();
        new Thread(this::runMainGameLoopInThread).start();
    }

    @Override
    public void stop() {
        closed = true;
    }


    private void runMainGameLoopInThread() {
        while (!closed) {
            // run in UI thread
            Platform.runLater(this::drawFrame);
            try {
                int pauseBetweenFramesMillis = 1000 / FPS;
                Thread.sleep(pauseBetweenFramesMillis);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        //space
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 500);
        //stars
        gc.setFill(Color.WHITE);
        for (int i = 0; i < UNIVERSE; i++) {
            gc.fillOval(arrX.get(i), arrY.get(i), size.get(i), size.get(i));
        }
        //ball
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        final double DIAMETER = 50;
        gc.fillOval(x, y, DIAMETER, DIAMETER);
        gc.strokeOval(x, y, DIAMETER, DIAMETER);
        x++;
        y++;

    }

}