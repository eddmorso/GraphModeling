package Viewer;

import Builder.Connection;
import Builder.Graph;
import Builder.Vertex;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        int width = (this.getWidth()) / 14;
        int height = (this.getHeight()) / 14;
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);

        g.setFont(font);
        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        g.setColor(Color.black);

        for (int i = 0; i <= 14; i++){
            for (int j = 0; j <= 14; j++){
                g.drawLine(i * width,j * height,i * width,j * height);
            }
        }
        List<Vertex> vertices = Graph.getInstance().getVertexList();
        List<Connection> connections = Graph.getInstance().getConnectionList();

        if (!connections.isEmpty()){
            for (Connection connection : connections){
                int initialX = connection.getStartVertex().getX() * width + width/2;
                int initialY = connection.getStartVertex().getY() * height + height/2;
                int endX = connection.getEndVertex().getX() * width + width/2;
                int endY = connection.getEndVertex().getY() * height + height/2;

                if (initialX == endX && initialY == endY){
                    g.setColor(Color.red);
                    g.drawOval(initialX - width, initialY - height, width, height);
                }
                g.setColor(Color.red);
                g.drawLine(initialX, initialY, endX, endY);
                g.setColor(Color.black);

                if (initialX < endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, initialY + (endY - initialY) / 2);
                    g.fillOval(endX - width/2, endY - height/2, 5,5);
                } else if (initialX < endX && initialY > endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, endY + (initialY - endY) / 2);
                    g.fillOval(endX - width/2, endY + height/2, 5,5);
                } else if (initialX > endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, initialY + (endY - initialY) / 2);
                    g.fillOval(endX + width/2, endY - height/2, 5,5);
                } else if (initialX > endX && initialY > endY) {
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, endY + (initialY - endY) / 2);
                    g.fillOval(endX + width/2, endY + height/2, 5,5);
                } else if (initialX == endX && initialY > endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX, endY + (initialY - endY) / 2);
                    g.fillOval(endX, endY + height/2, 5,5);
                } else if (initialX == endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX, initialY + (endY - initialY) / 2);
                    g.fillOval(endX, endY - (height/2 + 5), 5,5);
                } else if (initialX > endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, initialY);
                    g.fillOval(endX + width/2, endY, 5,5);
                } else if (initialX < endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, initialY);
                    g.fillOval(endX - (width/2 + 5), endY, 5,5);
                } else if (initialX == endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX - width, initialY - height);
                }
            }
        }
        if (!vertices.isEmpty()) {
            for (Vertex vertex : vertices) {
                g.setColor(Color.black);
                g.fillOval(vertex.getX() * width, vertex.getY() * height, width, height);

                g.setColor(Color.white);
                g.drawString(String.valueOf(vertex.getWeight()),
                        vertex.getX() * width + width/2 - 3, vertex.getY() * height + height/2);
                g.drawString(String.valueOf(vertex.getId()),
                        vertex.getX() * width + width/2 - 3, vertex.getY() * height + height - 5);
            }
        }
    }
}
