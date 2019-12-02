package com.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Snake extends GameObject{

    private List<BodyPart> snake;
    private short[] velocity = new short[2]; // { x vel, y vel }

    public Snake(int x, int y, int width, int height) {
        super(x, y, width, height);
        snake = new LinkedList<>();
        // Create snake head
        BodyPart head = new BodyPart(x, y, getWidth(), getHeight());
        snake.add(head);
        // Start with the snake moving to the right
        velocity[0] = (short) getWidth();
        velocity[1] = 0;
    }

    public void eat(Food food) {
        BodyPart tail = snake.get(snake.size() - 1);
        snake.add(new BodyPart(tail.getX(), tail.getY(), tail.getWidth(), tail.getHeight()));
        food.spawnFood();
    }

    @Override
    public void tick() {
        // Move the body
        if (snake.size() > 1) {
            // Remove the tail
            snake.remove(snake.size() - 1);
            // Create a new piece behind the head
            snake.add(1, new BodyPart(getX(), getY(), getWidth(), getHeight()));
        }
        // Move the head
        setX(getX() + velocity[0]);
        setY(getY() + velocity[1]);
        // Restrict the snake
        restrictCoordinates();
    }

    private void restrictCoordinates() {
        if (getX() < 0) {
            setX(0);
        } else if (getX() > Game.WIDTH - getWidth()) {
            setX(Game.WIDTH - getWidth());
        }

        if (getY() < 0) {
            setY(0);
        } else if (getY() > Game.HEIGHT - getHeight()) {
            setY(Game.HEIGHT - getHeight());
        }
    }

    public boolean isDead() {
        boolean isDead = false;
        if (snake.size() == 2) {
            BodyPart tail = snake.get(1);
            if (tail.getX() == getX() && tail.getY() == getY()) {
                isDead = true;
            }
        } else {
            for (int i = 1; i < snake.size() - 1; i++) {
                if (snake.get(i).getX() == getX() && snake.get(i).getY() == getY()) {
                    isDead = true;
                }
            }
        }
        return isDead;
    }

    /**
     * Draw the full snake
     * @param gc GraphicsContext
     */
    @Override
    public void draw(GraphicsContext gc) {
        for (BodyPart bp : snake) {
            bp.draw(gc);
        }
    }

    /**
     * Get the x coordinate of the snake head
     * @return the x coordinate
     */
    @Override
    public int getX() {
        if (snake.size() == 0) {
            throw new NullPointerException();
        }
        return snake.get(0).getX();
    }

    /**
     * Set the x coordinate of the snake head
     */
    @Override
    public void setX(int x) {
        if (snake.size() == 0) {
            throw new NullPointerException();
        }
        snake.get(0).setX(x);
    }

    /**
     * Get the y coordinate of the snake head
     * @return the y coordinate
     */
    @Override
    public int getY() {
        if (snake.size() == 0) {
            throw new NullPointerException();
        }
        return snake.get(0).getY();
    }

    /**
     * Set the y coordinate of the snake head
     */
    @Override
    public void setY(int y) {
        if (snake.size() == 0) {
            throw new NullPointerException();
        }
        snake.get(0).setY(y);
    }

    public short[] getVelocity() {
        return velocity;
    }

    public void setVelocity(short[] velocity) {
        this.velocity = velocity;
    }

    public int getLength() {
        return snake.size();
    }

    private class BodyPart extends GameObject {
        public BodyPart(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        @Override
        public void draw(GraphicsContext gc) {
            gc.setFill(Color.GREEN);
            gc.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }
}
