package View;

import Model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class MyPanel extends JPanel {
    private ArrayList<Vertex> vertexes;
    private int x, y, X, Y;
    private boolean isVertex = true;

    public MyPanel(){
        vertexes = new ArrayList<>();
        addMouseListener(new MyMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(isVertex) {
            g.setColor(Color.black);
            g.fillOval(x - 25, y - 25, 50, 50);
            if (!vertexes.isEmpty()) {
                g.setColor(Color.white);
                String weight = String.valueOf(vertexes.get(vertexes.size() - 1).getWeight());
                String id = String.valueOf(vertexes.get(vertexes.size() - 1).getId());
                g.drawString(weight, x - 3, y + 16);
                g.drawLine(x - 15, y, x + 15, y);
                g.drawString(id, x - 3, y - 8);
            }
        }else{
            g.setColor(Color.black);
            g.drawLine(x,y,X,Y);
        }
    }

    class MyMouseListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            for(int i = 0; i < vertexes.size(); i++){
                int xR = vertexes.get(i).getX();
                int yR = vertexes.get(i).getY();

                boolean xRange = xR >= e.getX() - 25 && xR <= e.getX() + 25;
                boolean yRange = yR >= e.getY() - 25 && yR <= e.getY() + 25;

                if(xRange && yRange){
                    x = xR;
                    y = yR;
                    System.out.println("pressed on the vertex");
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            isVertex = true;

            int weigh = (int) (Math.random() * 10);
            x = e.getX();
            y = e.getY();
            vertexes.add(new Vertex(weigh, x, y));
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            for(int i = 0; i < vertexes.size(); i++){
                int x = vertexes.get(i).getX();
                int y = vertexes.get(i).getY();

                boolean xRange = x >= e.getX() - 25 && x <= e.getX() + 25;
                boolean yRange = y >= e.getY() - 25 && y <= e.getY() + 25;
                if(xRange && yRange){
                    isVertex = false;
                    X = x;
                    Y = y;
                    System.out.println("released on vertex");
                    repaint();
                }
            }
        }
    }
}
