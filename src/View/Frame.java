package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Frame{

    private JFrame jFrame;
    private MyPanel background;
    private JButton drawButton, stopDrawingButton, moveButton, clearButton;
    private boolean isButtonPressed = false;
    private ArrayList<Vertex> vertexArrayList;
    private ArrayList<Connection> connectionArrayList;

    public void init(){

        vertexArrayList = new ArrayList<Vertex>();
        connectionArrayList = new ArrayList<Connection>();

        MouseDrawingListener mouseDrawingListener = new MouseDrawingListener();

        jFrame = new JFrame("Graph editor");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640,500);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        fileMenu.add(save);
        fileMenu.add(open);
        jMenuBar.add(fileMenu);
        jFrame.setJMenuBar(jMenuBar);

        background = new MyPanel();
        background.setLayout(null);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        background.setBackground(Color.white);

        Box buttonBox = new Box(BoxLayout.X_AXIS);

        drawButton = new JButton("Add new Component");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isButtonPressed) {
                    background.addMouseListener(mouseDrawingListener);
                    isButtonPressed = true;
                }
            }
        });
        stopDrawingButton = new JButton("Stop drawing");
        stopDrawingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isButtonPressed){
                    background.removeMouseListener(mouseDrawingListener);
                    isButtonPressed = false;
                }
            }
        });
        moveButton = new JButton("Move objects");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isButtonPressed){
                    isButtonPressed = false;
                    background.removeMouseListener(mouseDrawingListener);
                }else{
                    new MouseMovementsListener(background.getComponents());
                }
            }
        });
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                background.setClear(true);
                for(Component component : background.getComponents()) {
                    background.remove(component);
                }
                background.repaint();
                connectionArrayList.clear();
                vertexArrayList.clear();
            }
        });
        buttonBox.add(drawButton);
        buttonBox.add(stopDrawingButton);
        buttonBox.add(moveButton);
        buttonBox.add(clearButton);

        jFrame.getContentPane().add(BorderLayout.SOUTH, buttonBox);
        jFrame.getContentPane().add(BorderLayout.CENTER, background);
        jFrame.setVisible(true);
    }

    public class MouseMovementsListener implements MouseListener, MouseMotionListener {

        private int x,y;
        private Component component;

        public MouseMovementsListener(Component...components){
            for(Component component : components){
                component.addMouseListener(this);
                component.addMouseMotionListener(this);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
//            e.getComponent().setLocation((e.getX() + e.getComponent().getX()) - x,(e.getY() + e.getComponent().getY()) - y);
//            System.out.println(e.getComponent().getX() + " " + e.getComponent().getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getComponent().getX();
            y = e.getComponent().getY();
            component = e.getComponent();
            System.out.println(component.getX() + " " + component.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            component.setLocation(e.getX(), e.getY());
            background.repaint();
            System.out.println(component.getX() + " " + component.getY());
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    public class MouseDrawingListener implements MouseListener{

        int initialX, initialY, endX, endY;
        Vertex startVertex, endVertex;

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX() - 25;
            int y = e.getY() - 25;

            int weight = (int) (Math.random() * 10);
            Vertex vertex = new Vertex(weight, x, y);
            vertexArrayList.add(vertex);
            vertex.setLocation(x,y);

            System.out.println(x + " " + y);

            background.add(vertex);
            background.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(!vertexArrayList.isEmpty()) {
                for (Vertex vertex : vertexArrayList) {
                    if ((e.getX() >= vertex.getX() - 40 && e.getX() <= vertex.getX() + 40) &&
                            (e.getY() >= vertex.getY() - 40 && e.getY() <= vertex.getY() + 40)) {
                        initialX = e.getX();
                        initialY = e.getY();
                        startVertex = vertex;
                        return;
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!vertexArrayList.isEmpty()) {
                int weight = (int) (Math.random() * 10);
                for (Vertex vertex : vertexArrayList) {
                    if ((e.getX() >= vertex.getX() - 40 && e.getX() <= vertex.getX() + 40) &&
                            (e.getY() >= vertex.getY() - 40 && e.getY() <= vertex.getY() + 40)) {
                        endX = e.getX();
                        endY = e.getY();
                        endVertex = vertex;

                        Connection connection = new Connection(weight, startVertex, endVertex);
                        connectionArrayList.add(connection);
                        background.setAble(true, startVertex.getX(), startVertex.getY(), endVertex.getX(),endVertex.getY(), weight);
                        background.repaint();
                        return;
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
