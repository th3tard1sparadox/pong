package game;

import java.awt.*;

public class Paddle
{
    private float x;
    private float y;
    private int width;
    private int height;
    private Color color;
    private int speed;
    private int dir;
    private int score;

    public void setScore(int s) {
        score = s;
    }

    public int getScore() {
        return score;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Color getColor() {
	return color;
    }

    public int getDir() {
	return dir;
    }

    public Paddle(final int x, final int y, final int width, final int height, final int dir) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	color = Color.white;
	this.dir = dir;
	score = 0;
    }

    public void moveUp() {
        speed = -250;
    }

    public void moveDown() {
        speed = 250;
    }

    public void stop() {
        speed = 0;
    }

    public void update(float dTime) {
	y = y + (speed * dTime);
    }
}
