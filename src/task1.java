import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.Random;


public class task1 extends Application {

    private static final Random RANDOM = new Random();
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 500;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Space");
        Canvas canvas = new Canvas();
        canvas.setWidth(BOARD_WIDTH);
        canvas.setHeight(BOARD_HEIGHT);
        BorderPane group = new BorderPane(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        test(gc);
    }

    private void test(GraphicsContext gc) {

        //space
        gc.fillRect(0, 0, 800, 500);
        gc.setFill(Color.WHITE);
        //planet
        gc.fillOval(120, 120, 200, 200);
        gc.fillOval(350, 120, 20, 20);
        gc.fillOval(400, 180, 50, 50);
        //stars
        for (int i = 0; i < 200; i++) {
            int stars = RANDOM.nextInt(3);
            gc.fillOval(RANDOM.nextInt(800), RANDOM.nextInt(300), stars, stars);
            gc.fillOval(RANDOM.nextInt(800), RANDOM.nextInt(10) + 297, stars * 50, stars * 50);
        }
        gc.fillOval(500, 120, 2, 2);
        gc.fillOval(550, 150, 2, 2);
        gc.fillOval(750, 120, 2, 2);
        gc.fillOval(800, 10, 2, 2);
        gc.fillOval(450, 20, 5, 5);
        //rings
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(100, 215, 800, 10, -180, 240, ArcType.OPEN);
        gc.strokeArc(100, 220, 800, 15, -180, 240, ArcType.OPEN);
        gc.setStroke(Color.WHITE);
        gc.strokeArc(-100, 202, 800, 25, -180, 360, ArcType.OPEN);
        gc.strokeArc(-175, 198, 900, 38, -180, 360, ArcType.OPEN);
        //ground
        gc.fillRect(0, 300, 800, 500);
        //outline
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeArc(-100, 300, 1200, 20, 0, 180, ArcType.OPEN);
        //craters
        gc.strokeArc(200, 310, 200, 10, 0, 240, ArcType.OPEN);
        gc.strokeArc(-100, 308, 200, 5, 0, 360, ArcType.OPEN);
        gc.strokeArc(700, 308, 80, 5, 0, 270, ArcType.OPEN);
        gc.strokeArc(500, 320, 40, 5, 0, 270, ArcType.OPEN);
        gc.strokeArc(450, 318, 40, 5, 0, 270, ArcType.OPEN);
        gc.strokeArc(200, 330, 350, 40, 0, 200, ArcType.OPEN);
        gc.strokeArc(530, 375, 200, 30, 0, 290, ArcType.OPEN);
        gc.strokeArc(-100, 400, 800, 100, 0, 200, ArcType.OPEN);
        gc.strokeArc(500, 308, 20, 5, 0, 360, ArcType.OPEN);
        //dirt
        gc.setFill(Color.WHITE);
        for (int i = 0; i < 200; i++) {
            int dirt = RANDOM.nextInt(3);
            gc.fillOval(RANDOM.nextInt(800), RANDOM.nextInt(100) + 300, dirt * 5, dirt);
            gc.fillOval(RANDOM.nextInt(800), RANDOM.nextInt(100) + 400, dirt * 10, dirt * 2);
            gc.fillOval(RANDOM.nextInt(800), RANDOM.nextInt(100) + 500, dirt * 20, dirt * 4);
        }
    }

}
