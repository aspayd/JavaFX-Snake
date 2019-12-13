package com.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Food object - the snake's objective
 */
public class Food extends GameObject {
    public Food(int x, int y, int width, int height) {
        super(x, y, width, height);
        spawnFood();
    }

    /**
     * Change the food's x and y coordinates to a random spot on the game grid
     */
    public void spawnFood() {
        // Spawn the food in a random grid cell
        int numRows = Game.WIDTH / Game.SCALE; // Get the number of rows on the grid
        int numCols = Game.HEIGHT / Game.SCALE; // Get the number of columns on the grid
        setX((int) (Math.random() * Game.WIDTH / numRows) * numRows);
        setY((int) (Math.random() * Game.HEIGHT / numCols) * numCols);
    }

    /**
     * Check if the food has been eaten by the snake
     * @param snake - the player snake
     * @return True if the food has been eaten, False otherwise
     */
    public boolean isEaten(Snake snake) {
        // Check if the snake's head is on a food piece
        return snake.getX() == getX() && snake.getY() == getY();
    }

    /**
     * Draw the food on the canvas
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        // draw the food piece as a red rectangle
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
