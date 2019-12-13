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

/**
 * JavaFX snake game
 *
 * @author Adam Spayd
 *
 * date last modified: 12/13/2019
 * CS1131 Fall 2019
 */
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

        canvas = new Canvas(WIDTH, HEIGHT); // Canvas to draw the game graphics
        GraphicsContext gc = canvas.getGraphicsContext2D(); // The graphics of the canvas

        // Draw the background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Allow the canvas to receive user input through focus
        canvas.setFocusTraversable(true);

        Text score = new Text(15, 20, "Score: " + 0); // Text to draw the player's current score
        score.setFont(new Font(16));
        score.setFill(Color.WHITE);

        root.getChildren().addAll(canvas, score);

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Movement logic
        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                // Move the snake up
                short[] currentVelocity = snake.getVelocity();
                // Make sure the snake cannot move backwards into itself
                if (currentVelocity[1] != (short) snake.getHeight() || snake.getLength() < 3) {
                    snake.setVelocity(new short[]{0, (short) -snake.getHeight()});
                }
            } else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                // Move the snake down
                short[] currentVelocity = snake.getVelocity();
                // Make sure the snake cannot move backwards into itself
                if (currentVelocity[1] != (short) -snake.getHeight() || snake.getLength() < 3) {
                    snake.setVelocity(new short[]{0, (short) snake.getHeight()});
                }
            } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                // Move the snake left
                short[] currentVelocity = snake.getVelocity();
                // Make sure the snake cannot run backwards into itself
                if (currentVelocity[0] != (short) snake.getHeight() || snake.getLength() < 3) {
                    snake.setVelocity(new short[]{(short) -snake.getHeight(), 0});
                }
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                // Move the snake right
                short[] currentVelocity = snake.getVelocity();
                // Make sure the snake cannot run backwards into itself
                if (currentVelocity[0] != (short) -snake.getHeight() || snake.getLength() < 3) {
                    snake.setVelocity(new short[]{(short) snake.getWidth(), 0});
                }
            }
        });

        // Game loop
        previousNanoTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void start() {
                snake = new Snake(0, 0, WIDTH / SCALE, HEIGHT / SCALE); // Create the snake
                food = new Food(0, 0, WIDTH / SCALE, HEIGHT / SCALE); // Create the food piece
                super.start();
            }
            @Override
            public void handle(long currentNanoTime) {
                double delta = (currentNanoTime - previousNanoTime) / 1000000000.0; // Keep track of how much time has changed
                if (delta > 0.11) { // If the time change hits this threshold, run 1 frame
                    // Refresh the background
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, WIDTH, HEIGHT);
                    // Update the game objects
                    snake.tick();
                    snake.draw(gc);
                    food.draw(gc);

                    // Check if the food has been eaten by the snake
                    if (food.isEaten(snake)) {
                        snake.eat(food);
                        score.setText("Score: " + (snake.getLength() - 1) * 10);
                    } else if (snake.isDead()) {
                        // The snake has died. Display "Game Over" and stop the loop.
                        Text gameOverText = new Text("Game Over");
                        gameOverText.setFont(new Font(24));
                        gameOverText.setX((root.getWidth() / 2) - gameOverText.getBoundsInLocal().getWidth() / 2);
                        gameOverText.setY((root.getWidth() / 2) - gameOverText.getBoundsInLocal().getWidth() / 2);
                        gameOverText.setFill(Color.WHITE);
                        root.getChildren().add(gameOverText);
                        this.stop();
                    }
                    previousNanoTime = currentNanoTime; // Reset the time tracking
                }
            }
        }.start();
    }
}
