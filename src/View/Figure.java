package View;

import javax.swing.*;
import java.awt.*;

public class Figure extends JComponent {
    private Object type;
    private Color color;
    private String weight;
    private int intCase; //if inCase equals 0 - draw circle, if 1 - draw arrow
    private int x,y;

    public Figure(Object type, Color color, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
        setOpaque(false);

        if(this.type instanceof Vertex){
            intCase = 0;
            Vertex vertex = (Vertex) type;
            weight = String.valueOf(vertex.getWeight());
        }else if(this.type instanceof Connection){
            intCase = 1;
            Connection connection = (Connection) type;
            weight = String.valueOf(connection.getWeight());
        }else{
            throw new RuntimeException("Wrong drawing object");
        }
    }
    public void paintComponent(Graphics graphics){
        graphics.setColor(color);
        switch (intCase){
            case 0: graphics.drawOval(x,y,50,50);
            break;
            case 1: graphics.drawLine(x,y,30,30);
            break;
        }
        graphics.setColor(Color.BLACK);
        graphics.drawString(weight, 50,50);

    }
}
