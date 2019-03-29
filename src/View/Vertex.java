package View;

import View.MouseMovementsListener;

import javax.swing.*;
import java.awt.*;

public class Vertex extends JPanel {

    private static int counter;
    private int id, weight, x, y;

    public Vertex(int weight, int x, int y){
        id = counter;
        this.weight = weight;
        this.x = x;
        this.y = y;
        counter++;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(60,60);
        setBackground(Color.green);

        g.setColor(Color.black);
        g.fillOval(x - 25, y - 25, 50, 50);

        String weight = String.valueOf(getWeight());
        String id = String.valueOf(getId());

        g.setColor(Color.white);
        g.drawString(weight, x - 3, y + 16);
        g.drawLine(x - 15, y, x + 15, y);
        g.drawString(id, x - 3, y - 8);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(60,60);
    }
}
