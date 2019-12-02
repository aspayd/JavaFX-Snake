package com.snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    public final static int WIDTH = 500;
    public final static int HEIGHT = 500;
    public final static int SCALE = 20;
    private Canvas canvas;
    private Snake snake;
    private Food food;

    private long previousNanoTime;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new AnchorPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw the background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        canvas.setFocusTraversable(true);

        Text score = new Text(15, 20, "Score: " + 0);
        score.setFont(new Font(16));
        score.setFill(Color.WHITE);

        root.getChildren().addAll(canvas, score);

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                // Move the snake up
                snake.setVelocity(new short[]{0, (short) -snake.getHeight()});
            } else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                // Move the snake down
                snake.setVelocity(new short[]{0, (short) snake.getHeight()});
            } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                // Move the snake left
                snake.setVelocity(new short[]{(short) -snake.getWidth(), 0});
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                // Move the snake right
                snake.setVelocity(new short[]{(short) snake.getWidth(), 0});
            }
        });

        previousNanoTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void start() {
                snake = new Snake(0, 0, WIDTH / SCALE, HEIGHT / SCALE);
                food = new Food(0, 0, WIDTH / SCALE, HEIGHT / SCALE);
                super.start();
            }
            @Override
            public void handle(long currentNanoTime) {
                double delta = (currentNanoTime - previousNanoTime) / 1000000000.0;
                if (delta > 0.12) {
                    // Refresh the background
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, WIDTH, HEIGHT);
                    // Update the game objects
                    snake.tick();
                    snake.draw(gc);
                    food.draw(gc);
                    if (food.isEaten(snake)) {
                        snake.eat(food);
                        score.setText("Score: " + (snake.getLength() - 1) * 10);
                    } else if (snake.isDead()) {
                        this.stop();
                    }
                    previousNanoTime = currentNanoTime;
                }
            }
        }.start();
    }
}
