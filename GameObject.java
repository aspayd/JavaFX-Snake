package com.snake;

import javafx.scene.canvas.GraphicsContext;

/**
 * Structure for all objects in the game (snake and food)
 */
public abstract class GameObject {

    private int x; // X-coordinate
    private int y; // Y-coordinate
    private int width; // Object width
    private int height; // Object height

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY( int y ) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Draw the object on the canvas
     * @param gc
     */
    public abstract void draw( GraphicsContext gc );

    /**
     * Object behavior logic
     */
    public void tick() {

    }
}
