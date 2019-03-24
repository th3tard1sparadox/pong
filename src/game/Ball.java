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
    private static float MAX_ANGLE = 30f;
    private static float MIN_ANGLE = -30f;
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

    public void randColor() {
        float h = generator.nextFloat();
	float s = generator.nextFloat() * (1f - 0.6f) + 0.6f;
	float b = generator.nextFloat() * (1f - 0.6f) + 0.6f;
        color = new Color(Color.HSBtoRGB(h, s, b));
    }

    public boolean lost(int windowWidth) {
        if (x <= 0 || x >= windowWidth) {
            return true;
	}
        return false;
    }

    public void bounceCheckPaddle(Paddle paddle) {
	if ((x >= paddle.getX() || x + size >= paddle.getX()) &&
	    (x <= paddle.getX() + paddle.getWidth() || x + size <= paddle.getX() + paddle.getWidth()) &&
	    (y >= paddle.getY() || y + size >= paddle.getY()) &&
	    (y <= paddle.getY() + paddle.getHeight() || y + size <= paddle.getY() + paddle.getHeight())) {
	    	float pos = (float) (paddle.getHeight() / 2) - (y + size / 2 - paddle.getY());
	    	if (pos > paddle.getHeight() / 2) {
	    	    pos = paddle.getHeight() / 2;
		}
	    	else if (pos < -paddle.getHeight() / 2) {
	    	    pos = -paddle.getHeight() / 2;
		}
		bounce(false, pos, paddle.getHeight(), paddle.getDir());
	}
    }

    public void bounceCheckWall(int windowHeight) {
	if (y <= 0 || y >= windowHeight - size) {
	    bounce(true, 0, 0, 0);
	}
    }

    public void bounce(boolean wall, float pos, int paddleHeight, int dir) {
        if (wall) {
	    angle = -angle;
	}
        else {
            speed = (int) (150 + Math.abs(pos) / paddleHeight * 550);
            angle = (float) Math.toRadians(90f + dir * (90f + pos / paddleHeight * 120f));
	}
	randColor();
    }

    public void respawn(int x, int y) {
	this.x = x;
	this.y = y;
	speed = randSpeed(300, 100);
	angle = randAngle(MAX_ANGLE, MIN_ANGLE);
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
