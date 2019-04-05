package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPanel extends JPanel {
    public int width, height;

    @Override
    protected void paintComponent(Graphics g) {
        width = (this.getWidth())/14;
        height = (this.getHeight())/14;

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        g.setFont(font);

        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(), this.getHeight());

        g.setColor(Color.black);

        for(int i = 0; i <= 14; i++){
            for(int j = 0; j <= 14; j++){
                g.drawLine(i* width,j* height,i* width,j* height);
            }
        }

        ArrayList<Vertex> vertices = Frame.vertexArrayList;
        ArrayList<Connection> connections = Frame.connectionArrayList;

        if(!connections.isEmpty()){
            for(int i = 0; i < connections.size(); i++){
                Connection connection = connections.get(i);

                int initialX = connection.getStartVertex().getX() + width/2;
                int initialY = connection.getStartVertex().getY() + height/2;
                int endX = connection.getEndVertex().getX() + width/2;
                int endY = connection.getEndVertex().getY() + height/2;

                if(initialX == endX && initialY == endY){
                    g.setColor(Color.red);
                    g.drawOval(initialX - width, initialY - height, width, height);
                }

                g.setColor(Color.red);
                g.drawLine(initialX, initialY, endX, endY);

                g.setColor(Color.black);
                if(initialX < endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, initialY + (endY - initialY) / 2);
                }else if(initialX < endX && initialY > endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, endY + (initialY - endY) / 2);
                }else if(initialX > endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, initialY + (endY - initialY) / 2);
                }else if(initialX > endX && initialY > endY) {
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, endY + (initialY - endY) / 2);
                }else if(initialX == endX && initialY > endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX, endY + (initialY - endY) / 2);
                }else if(initialX == endX && initialY < endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX, initialY + (endY - initialY) / 2);
                }else if(initialX > endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), endX + (initialX - endX) / 2, initialY);
                }else if(initialX < endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX + (endX - initialX) / 2, initialY);
                }else if(initialX == endX && initialY == endY){
                    g.drawString(String.valueOf(connection.getWeight()), initialX - width, initialY - height);
                }
            }
        }

        if(!vertices.isEmpty()) {
            for (int i = 0; i < vertices.size(); i++) {
                Vertex vertex = vertices.get(i);
                g.setColor(Color.black);
                g.fillOval(vertex.getX(), vertex.getY(), width, height);

                g.setColor(Color.white);
                g.drawString(String.valueOf(vertex.getWeight()), vertex.getX() + width/2 - 3, vertex.getY() + height/2);
                g.drawString(String.valueOf(vertex.getId()), vertex.getX() + width/2 - 3, vertex.getY() + height - 5);
            }
        }
    }
}
