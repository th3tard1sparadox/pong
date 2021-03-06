package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Game extends JComponent
{
    private static int WINDOW_WIDTH = 1000;
    private static int WINDOW_HEIGHT = 700;
    private static int BALL_SIZE = 6;
    private static int PADDLE_DISTANCE = 70;
    private static int PADDLE_WIDTH = 10;
    private static int PADDLE_HEIGHT = 100;

    private BufferedImage image;
    private Graphics imgG;
    private Ball ball;
    private Paddle rightPaddle;
    private Paddle leftPaddle;
    private Ai ai1;
    private Ai ai2;
    private PauseMenu pauseMenu;
    private boolean pause;
    private boolean singlePlayer;

    public Game() {
        ball = new Ball(WINDOW_WIDTH/2 - BALL_SIZE/2, WINDOW_HEIGHT/2 - BALL_SIZE/2, BALL_SIZE);
        rightPaddle = new Paddle(WINDOW_WIDTH - PADDLE_DISTANCE, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        leftPaddle = new Paddle(PADDLE_DISTANCE, WINDOW_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, -1);
        image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        imgG = image.getGraphics();
        setupKeyInput();
        ai1 = new Ai(ball, leftPaddle);
        ai2 = new Ai(ball, rightPaddle);
        pauseMenu = new PauseMenu();
        pause = true;
    }

    public void rightString(Graphics g, String text, Rectangle rect, int y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text));
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);

        imgG.setColor(Color.black);
        imgG.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        imgG.setColor(ball.getColor());
        imgG.fillRect((int) ball.getX(), (int) ball.getY(), ball.getSize(), ball.getSize());
        imgG.setColor(rightPaddle.getColor());
        imgG.fillRect((int) rightPaddle.getX(), (int) rightPaddle.getY(), rightPaddle.getWidth(), rightPaddle.getHeight());
        imgG.setColor(leftPaddle.getColor());
        imgG.fillRect((int) leftPaddle.getX(), (int) leftPaddle.getY(), leftPaddle.getWidth(), leftPaddle.getHeight());
        rightString(imgG, leftPaddle.getScore() + " ", new Rectangle(WINDOW_WIDTH/2, WINDOW_HEIGHT), 40, new Font("Symbol", Font.PLAIN, 30));
        imgG.setFont(new Font("Symbol", Font.PLAIN, 30));
        imgG.drawString(" " + rightPaddle.getScore(), WINDOW_WIDTH/2, 40);
        if (pause) {
            pauseMenu.showPause(imgG, WINDOW_WIDTH, WINDOW_HEIGHT);
        }
    }

    public void start() {
        while(true) {
            update(0.016f);
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(float dTime) {
        if (!pause) {
            ball.update(dTime, rightPaddle, leftPaddle, WINDOW_HEIGHT, WINDOW_WIDTH);
            if (singlePlayer) {
                ai1.updateHard();
            }
            rightPaddle.update(dTime, WINDOW_HEIGHT);
            leftPaddle.update(dTime, WINDOW_HEIGHT);
            if (ball.isLost()) {
                if (ball.getX() < WINDOW_WIDTH / 2) {
                    rightPaddle.setScore(rightPaddle.getScore() + 1);
                } else {
                    leftPaddle.setScore(leftPaddle.getScore() + 1);
                }
                ball.respawn(WINDOW_WIDTH / 2 - BALL_SIZE / 2, WINDOW_HEIGHT / 2 - BALL_SIZE / 2);
            }
        }
    }

    public void setupKeyInput() {
        addKeyListener(new KeyListener() {
           @Override public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == keyEvent.VK_UP) {
                    if (!pause) {
                        rightPaddle.moveUp();
                    }
                    else {
                        pauseMenu.selectUp();
                    }
                }
                else if (key == keyEvent.VK_DOWN) {
                    if (!pause) {
                        rightPaddle.moveDown();
                    }
                    else {
                        pauseMenu.selectDown();
                    }
                }

                if (key == keyEvent.VK_W) {
                    if (!pause) {
                        leftPaddle.moveUp();
                    }
                }
                else if (key == keyEvent.VK_S) {
                    if (!pause) {
                        leftPaddle.moveDown();
                    }
                }

                if (key == keyEvent.VK_ENTER) {
                    if (pause) {
                        switch (pauseMenu.select()) {
                            case 0:
                                singlePlayer = false;
                                pause = false;
                                break;
                            case 1:
                                singlePlayer = true;
                                pause = false;
                                break;
                            case 2:
                                pause = false;
                                break;
                            case 3:
                                System.exit(0);

                        }
                    }
                }

                if (key == keyEvent.VK_P) {
                    if (!pause) {
                        pause = true;
                    }
                }
            }

            @Override public void keyReleased(final KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == keyEvent.VK_UP || key == keyEvent.VK_DOWN) {
                    rightPaddle.stop();
                }

                if (key == keyEvent.VK_W || key == keyEvent.VK_S) {
                    leftPaddle.stop();
                }
            }

            @Override public void keyTyped(final KeyEvent keyEvent) {}
        });
        setFocusable(true);
        requestFocus();
    }
}
