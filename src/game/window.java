package game;

import javax.swing.*;
import java.awt.*;

public class window
{
    private static void createAndShowWindow() {
	Game game = new Game();

        JFrame frame = new JFrame("Pong");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(game, BorderLayout.CENTER);
	frame.pack();
	frame.setResizable(false);
	frame.setVisible(true);
	game.start();
    }

    public static void main(String[] args) {
	createAndShowWindow();
    }
}