package game;

public class Ai
{
    private Ball ball;
    private Paddle paddle;
    private float prevX;
    private float prevY;

    public Ai(final Ball ball, final Paddle paddle) {
	this.ball = ball;
	this.paddle = paddle;
	prevX = ball.getX();
	prevY = ball.getY();
    }

    public void updateEasy() {
        //if (ball.getX() <= prevX) {
	    if (ball.getY() > paddle.getY() + paddle.getHeight() / 4) {
		paddle.moveDown();
	    } else if (ball.getY() < paddle.getY() + paddle.getHeight() * 3 / 4) {
		paddle.moveUp();
	    }
	/*}
        else {
            paddle.stop();
	}*/
        prevX = ball.getX();
    }

    public void updateHard() {
        float k = (prevY - ball.getY()) / (prevX - ball.getX());
        float m = ball.getY() - k * ball.getX();
        float goalPos =  k * paddle.getX() + m;
	//if (ball.getX() <= prevX) {
	    if (goalPos > paddle.getY() && goalPos < paddle.getY() + paddle.getHeight()) {
		paddle.stop();
	    }
	    else if (goalPos >= paddle.getY() + paddle.getHeight() / 5) {
		paddle.moveDown();
	    }
	    else if (goalPos <= paddle.getY() + paddle.getHeight() * 4 / 5) {
		paddle.moveUp();
	    }
	/*}
	else {
	    paddle.stop();
	}*/
	prevX = ball.getX();
	prevY = ball.getY();
    }
}
