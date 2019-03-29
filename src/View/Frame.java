package View;
import Model.Vertex;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Frame extends JFrame{
    private JPanel background = new JPanel();

    public Frame() {
        super("Graph editor");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setVisible(true);
        setLocationRelativeTo(null);

        background.setSize(getSize());
        background.setBackground(Color.white);
        background.setLayout(null);
        background.setLocation(0,0);
        background.addMouseListener(new MouseClickedListener());

        add(BorderLayout.CENTER, background);
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        file.add(open);
        file.add(save);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pressed open");
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pressed save");
            }
        });

        return file;
    }

    public class MouseClickedListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

            int weight = (int) (Math.random() * 10);
            Vertex vertex = new Vertex(weight, e.getX(), e.getY());
            vertex.setSize(60,60);
            vertex.setBackground(Color.BLACK);
            vertex.setLocation(e.getX(), e.getY());
            vertex.setPreferredSize(new Dimension(60,60));
            background.add(vertex);
            background.revalidate();
            background.repaint();
            System.out.println("vertex created and added");

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
