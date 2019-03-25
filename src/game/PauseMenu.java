package game;

import java.awt.*;
import java.util.ArrayList;

public class PauseMenu
{
    private int selectItem;
    private ArrayList underlined;

    public PauseMenu() {
        selectItem = 0;
        underlined = new ArrayList();
        underlined.add(Font.BOLD);
        underlined.add(Font.PLAIN);
        underlined.add(Font.PLAIN);
        underlined.add(Font.PLAIN);
    }

    public void showPause(final Graphics g, int windowWidth, int windowHeight) {
        g.setColor(new Color(0, 0, 0, 255 / 2));
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.white);
        centerString(g, "2 Players", new Rectangle(windowWidth, windowHeight), 200, new Font("Symbol", (int) underlined.get(0), 60));
        centerString(g, "1 Player", new Rectangle(windowWidth, windowHeight), 300, new Font("Symbol", (int) underlined.get(1), 60));
        centerString(g, "Continue", new Rectangle(windowWidth, windowHeight), 400, new Font("Symbol", (int) underlined.get(2), 60));
        centerString(g, "Exit", new Rectangle(windowWidth, windowHeight), 500, new Font("Symbol", (int) underlined.get(3), 60));
    }

    public void centerString(Graphics g, String text, Rectangle rect, int y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text))/2;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void selectUp() {
	underlined.set(selectItem, Font.PLAIN);
	selectItem = (selectItem - 1) % 4;
	underlined.set(selectItem, Font.BOLD);
    }

    public void selectDown() {
	underlined.set(selectItem, Font.PLAIN);
	selectItem = (selectItem + 1) % 4;
	underlined.set(selectItem, Font.BOLD);
    }

    public int select() {
        return selectItem;
    }
}
