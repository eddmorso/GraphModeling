package View;
import Model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Frame extends JFrame{
    private MyPanel panel;

    public Frame() {
        super("Graph editor");
        panel = new MyPanel();
        init();
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

    private void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setVisible(true);

        getContentPane().add(panel);
    }
}
