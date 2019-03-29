package View;

import javax.swing.*;
import java.awt.*;

public class Circle extends JComponent {
    private int x, y;
    private Color color;
    private Vertex vertex;

    public Circle(Vertex vertex, Color color, int x, int y){
        this.vertex = vertex;
        this.color = color;
        this.x = x;
        this.y = y;
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval(x, y,60,60);
        graphics.drawString(String.valueOf(vertex.getWeight()), x, y);
    }
}
