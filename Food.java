package com.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food extends GameObject {
    public Food(int x, int y, int width, int height) {
        super(x, y, width, height);
        spawnFood();
    }

    public void spawnFood() {
        int numRows = Game.WIDTH / Game.SCALE;
        int numCols = Game.HEIGHT / Game.SCALE;
        setX((int) (Math.random() * Game.WIDTH / numRows) * numRows);
        setY((int) (Math.random() * Game.HEIGHT / numCols) * numCols);
    }

    public boolean isEaten(Snake snake) {
        return snake.getX() == getX() && snake.getY() == getY();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
