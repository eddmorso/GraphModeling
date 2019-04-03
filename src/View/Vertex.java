package View;

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

        setSize(50,50);
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

        g.setColor(Color.black);
        g.fillOval(0, 0, 50, 50);

        String weight = String.valueOf(getWeight());
        String id = String.valueOf(getId());

        g.setColor(Color.white);
        g.drawString(weight, 22, 16);
        g.drawLine(0, 25, 50, 25);
        g.drawString(id, 22, 43);
    }

}
