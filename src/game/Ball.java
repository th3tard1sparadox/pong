package game;

import java.awt.*;
import java.util.Random;

public class Ball
{
    private float x;
    private float y;
    private int speed;
    private float angle;
    private int size;
    private Color color;
    private float MAX = 30f;
    private float MIN = -30f;
    private boolean lost = false;

    public boolean isLost() {
	return lost;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public int getSize() {
	return size;
    }

    public Color getColor() {
	return color;
    }

    Random generator = new Random();

    public Ball(final int x, final int y, final int size) {
        respawn(x, y);
	this.size = size;
    }

    private int randSpeed(int max, int min) {
        return generator.nextInt((max + 1 - min)) + min;
    }

    private float randAngle(float max, float min) {
        float degrees = (generator.nextFloat() * (max - min)) + min;
        return (float) Math.toRadians(degrees);
    }

    public void setRandColor() {
        int r = generator.nextInt(256);
	int g = generator.nextInt(256);
	int b = generator.nextInt(256);
        color = new Color(r, g, b);
    }

    public boolean lost(int windowWidth) {
        if (x <= 0 || x >= windowWidth) {
            return true;
	}
        return false;
    }

    public void bounceCheckPaddle(Paddle paddle) {
	if (x >= paddle.getX() && x <= paddle.getX() + paddle.getWidth() && y >= paddle.getY() &&
	    y <= paddle.getY() + paddle.getHeight()) {
	    	float pos = (float) (paddle.getHeight()/2) - (y - paddle.getY());
		bounce(false, pos, paddle.getHeight(), paddle.getDir());
	}
    }

    public void bounceCheckWall(int windowHeight) {
	if (y <= 0 || y >= windowHeight) {
	    bounce(true, 0, 0, 0);
	}
    }

    public void bounce(boolean wall, float pos, int paddleHeight, int dir) {
        if (wall) {
	    angle = -angle;
	}
        else {
            if (pos <= paddleHeight/6 || pos >= -paddleHeight/6) {
		speed = randSpeed(300, 260);
	    }
            else if (pos <= paddleHeight/3 || pos >= -paddleHeight/3) {
		speed = randSpeed(240, 190);
	    }
            else {
		speed = randSpeed(150, 100);
	    }
            angle = (float) Math.toRadians(90f + dir * (90f + pos / paddleHeight * 120f));
	}
	setRandColor();
    }

    public void respawn(int x, int y) {
	this.x = x;
	this.y = y;
	speed = randSpeed(300, 100);
	angle = randAngle(MAX, MIN);
	color = Color.white;
	lost = false;
    }

    public void update(float dTime, Paddle rightPaddle, Paddle leftPaddle, int windowHeight, int windowWidth) {
        x = (float) (x + Math.cos(angle) * speed * dTime);
	y = (float) (y + Math.sin(angle) * speed * dTime);
	bounceCheckPaddle(rightPaddle);
    	bounceCheckPaddle(leftPaddle);
    	bounceCheckWall(windowHeight);
    	if (lost(windowWidth)) {
    	    lost = true;
	}
    }

}
