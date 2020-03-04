
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

    static final Random RANDOM = new Random();
    private static final int BOARD_X = 800;
    private static final int BOARD_Y = 500;
    static final int UNIVERSE = 200;
    static final int BALLS_NUMBER = 10;
    private static final int FPS = 60;
    static ArrayList<Integer> size = new ArrayList<>();
    static ArrayList<Integer> arrX = new ArrayList<>();
    static ArrayList<Integer> arrY = new ArrayList<>();
    public static ArrayList<Ball> balls = new ArrayList<>();

    private boolean closed;
    private GraphicsContext gc;


    public static class Ball {
        public int DIAMETER = 0;
        public int x = 0;
        public int velocity = 1;
        public int y = 0;
        public boolean vectorX = true;
        public boolean vectorY = true;
        public int red = 0;
        public int green = 0;
        public int blue = 0;

        public Ball() {
            this.DIAMETER = RANDOM.nextInt(40) + 10;
            this.red = RANDOM.nextInt(255);
            this.green = RANDOM.nextInt(255);
            this.blue = RANDOM.nextInt(255);
            this.vectorX = RANDOM.nextBoolean();
            this.vectorY = RANDOM.nextBoolean();
            this.x = RANDOM.nextInt(BOARD_X - DIAMETER);
            this.y = RANDOM.nextInt(BOARD_Y - DIAMETER);
            this.velocity = RANDOM.nextInt(10) + 1;
        }
    }

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

        for (int i = 0; i < BALLS_NUMBER; i++) {
            balls.add(createBall(i));
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bouncing Ballllllllllllllllllls");
        Canvas canvas = new Canvas();
        canvas.setWidth(BOARD_X);
        canvas.setHeight(BOARD_Y);
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
        gc.fillRect(0, 0, BOARD_X, BOARD_Y);
        //stars
        gc.setFill(Color.WHITE);
        for (int i = 0; i < UNIVERSE; i++) {
            gc.fillOval(arrX.get(i), arrY.get(i), size.get(i), size.get(i));
        }

        for (int i = 0; i < BALLS_NUMBER; i++) {
            //ball
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.setFill(Color.rgb(balls.get(i).red, balls.get(i).green, balls.get(i).blue));
            gc.fillOval(balls.get(i).x, balls.get(i).y, balls.get(i).DIAMETER, balls.get(i).DIAMETER);
            gc.strokeOval(balls.get(i).x, balls.get(i).y, balls.get(i).DIAMETER, balls.get(i).DIAMETER);
            move(balls.get(i), i);
        }

    }

    public static void move(Ball ball, int ballIndex) {
        collision(ball);
        ballCollision(ball, ballIndex);
        int transform = 1 * ball.velocity;
        if (ball.vectorX && ball.vectorY) {
            if (ball.x <= BOARD_X) {
                ball.x += transform;
            }
            if (ball.y <= BOARD_Y) {
                ball.y += transform;
            }
        } else if (!ball.vectorX && ball.vectorY) {
            if (ball.x >= 0) {
                ball.x -= transform;
            }
            if (ball.y <= BOARD_Y) {
                ball.y += transform;
            }
        } else if (!ball.vectorX && !ball.vectorY) {
            if (ball.x >= 0) {
                ball.x -= transform;
            }
            if (ball.y >= 0) {
                ball.y -= transform;
            }
        } else if (ball.vectorX && !ball.vectorY) {
            if (ball.x <= BOARD_X) {
                ball.x += transform;
            }
            if (ball.y >= 0) {
                ball.y -= transform;
            }
        }
    }

    private static void collision(Ball ball) {
        if (ball.x >= BOARD_X - ball.DIAMETER || ball.x <= 0) {
            ball.vectorX = !ball.vectorX;
        }
        if (ball.y >= BOARD_Y - ball.DIAMETER || ball.y <= 0) {
            ball.vectorY = !ball.vectorY;
        }
    }

    private static boolean ballCollision(Ball ball, int ballIndex) {
        if (ballIndex == 0) {
            return false;
        }
        int tolerance = 1;
        for (int i = 0; i < BALLS_NUMBER; i++) {
            if (ballIndex == i) {
                break;
            }
            if (i != ballIndex) {
                if (ball.x >= balls.get(i).x + tolerance - balls.get(i).DIAMETER / 2 && ball.x <= balls.get(i).x + balls.get(i).DIAMETER + tolerance) {
                    if (ball.y >= balls.get(i).y + tolerance - balls.get(i).DIAMETER / 2 && ball.y <= balls.get(i).y + balls.get(i).DIAMETER + tolerance) {
                        ball.vectorY = !ball.vectorY;
                        ball.vectorX = !ball.vectorX;
                        balls.get(i).vectorY = !balls.get(i).vectorY;
                        balls.get(i).vectorX = !balls.get(i).vectorX;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Ball createBall(int ballIndex) {
        Ball tmp = new Ball();
        if (!ballCollision(tmp, ballIndex)) {
            return tmp;
        }
        return createBall(ballIndex);

    }
}