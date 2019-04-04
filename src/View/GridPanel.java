package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPanel extends JPanel {
    public int width, height;

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(), this.getHeight());

        g.setColor(Color.black);

        width = (this.getWidth())/14;
        height = (this.getHeight())/14;

        for(int i = 0; i <= 14; i++){
            for(int j = 0; j <= 14; j++){
                g.drawLine(i* width,j* height,i* width,j* height);
            }
        }
        ArrayList<Vertex> vertices = Frame.vertexArrayList;
        if(!vertices.isEmpty()) {
            for (int i = 0; i < vertices.size(); i++) {
                Vertex vertex = vertices.get(i);
                g.setColor(Color.black);
                g.fillOval(vertex.getX(), vertex.getY(), width, height);

                g.setColor(Color.green);
                g.drawString(String.valueOf(vertex.getWeight()), vertex.getX() + width/2 - 3, vertex.getY() + height/2);
                g.drawString(String.valueOf(vertex.getId()), vertex.getX() + width/2 - 3, vertex.getY() + height - 5);
            }
        }
    }
}
