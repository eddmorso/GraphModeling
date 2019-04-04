package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Frame implements Serializable {

    private JFrame jFrame;
    static ArrayList<Vertex> vertexArrayList;
    static ArrayList<Connection> connectionArrayList;
    private boolean clean = false;

    public ArrayList<Vertex> getVertexArrayList() {
        return vertexArrayList;
    }

    public ArrayList<Connection> getConnectionArrayList() {
        return connectionArrayList;
    }

    public void init(){
        vertexArrayList = new ArrayList<Vertex>();
        connectionArrayList = new ArrayList<Connection>();

        jFrame = new JFrame("Graph modeling");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(900,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        jMenuBar.add(fileMenu);
        jFrame.setJMenuBar(jMenuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel insidePanel = new JPanel(new BorderLayout());

        GridPanel gridPanel = new GridPanel();

        insidePanel.add(BorderLayout.CENTER, gridPanel);
        mainPanel.add(BorderLayout.CENTER, insidePanel);

        Box yAxisName = new Box(BoxLayout.Y_AXIS);

        for(int i = 0; i <= 13; i++){
            yAxisName.add(new Label(String.valueOf(i)));
        }

        yAxisName.setBorder(BorderFactory.createEmptyBorder(10,0,-5,0));

        Box xAxisName = new Box(BoxLayout.X_AXIS);

        for(int i = 0; i <= 13; i++){
            xAxisName.add(new Label(String.valueOf(i)));
        }

        xAxisName.setBorder(BorderFactory.createEmptyBorder(0,30,0,-30));

        insidePanel.add(BorderLayout.WEST, yAxisName);
        insidePanel.add(BorderLayout.NORTH, xAxisName);


        JLabel xLabel = new JLabel("X: ");
        JTextField setXField = new JTextField();

        JLabel yLabel = new JLabel("Y: ");
        JTextField setYField = new JTextField();

        JLabel weightLabel = new JLabel("weight: ");
        JTextField setWeightField = new JTextField();

        JComboBox jComboBoxVertex = new JComboBox();
        for(int i = 0; i < vertexArrayList.size(); i++){
            jComboBoxVertex.addItem(vertexArrayList.get(i).getId());
        }

        JComboBox jComboBoxConnection = new JComboBox();
        for(int i = 0; i < connectionArrayList.size(); i++){
            jComboBoxConnection.addItem(connectionArrayList.get(i));
        }

        Box configButtonBox = new Box(BoxLayout.Y_AXIS);

        JButton vertexButton = new JButton("add vertex");
        vertexButton.setMaximumSize(new Dimension(200,25));


        JButton connectionButton = new JButton("add connection");
        connectionButton.setMaximumSize(new Dimension(200,25));

        configButtonBox.add(vertexButton);
        configButtonBox.add(Box.createRigidArea(new Dimension(0,5)));
        configButtonBox.add(connectionButton);

        configButtonBox.setBorder(BorderFactory.createEmptyBorder(0,0,50,10));

        Box editButtonBox = new Box(BoxLayout.Y_AXIS);

        JButton editVertexButton = new JButton("edit vertex");
        editVertexButton.setMaximumSize(new Dimension(200,25));
        editVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton editConnectionButton = new JButton("edit connection");
        editConnectionButton.setMaximumSize(new Dimension(200,25));
        editConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton deleteButton = new JButton("delete");
        deleteButton.setMaximumSize(new Dimension(200,25));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        editButtonBox.add(editVertexButton);
        editButtonBox.add(Box.createRigidArea(new Dimension(0,5)));
        editButtonBox.add(editConnectionButton);
        editButtonBox.add(Box.createRigidArea(new Dimension(0,5)));
        editButtonBox.add(deleteButton);

        editButtonBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel vertexLabel = new JLabel("vertex: ");

        JLabel connectionLabel = new JLabel("connection: ");

        JLabel editVertexWeightLabel = new JLabel("vertex weight: ");

        JTextField editVertexWeightField = new JTextField(3);

        JLabel editConnectionWeightLabel = new JLabel("connection weight: ");

        JTextField editConnectionWeightField = new JTextField(3);

        Box configLabelBox = new Box(BoxLayout.Y_AXIS);

        configLabelBox.add(xLabel);
        configLabelBox.add(Box.createRigidArea(new Dimension(0,10)));
        configLabelBox.add(yLabel);
        configLabelBox.add(Box.createRigidArea(new Dimension(0,10)));
        configLabelBox.add(weightLabel);

        configLabelBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box configFieldBox = new Box(BoxLayout.Y_AXIS);

        configFieldBox.add(setXField);
        configFieldBox.add(Box.createRigidArea(new Dimension(0,5)));
        configFieldBox.add(setYField);
        configFieldBox.add(Box.createRigidArea(new Dimension(0,5)));
        configFieldBox.add(setWeightField);

        configFieldBox.setMaximumSize(new Dimension(150,100));
        configFieldBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,0));

        Box configBox = new Box(BoxLayout.X_AXIS);

        JLabel configLabel = new JLabel("Make");
        configLabel.setBorder(BorderFactory.createEmptyBorder(10,110,10,0));

        configBox.add(configLabelBox);
        configBox.add(Box.createRigidArea(new Dimension(50,0)));
        configBox.add(configFieldBox);
        configBox.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        Box editLabelBox = new Box(BoxLayout.Y_AXIS);

        editLabelBox.add(vertexLabel);
        editLabelBox.add(Box.createRigidArea(new Dimension(0,10)));
        editLabelBox.add(connectionLabel);
        editLabelBox.add(Box.createRigidArea(new Dimension(0,10)));
        editLabelBox.add(editVertexWeightLabel);
        editLabelBox.add(Box.createRigidArea(new Dimension(0,10)));
        editLabelBox.add(editConnectionWeightLabel);

        editLabelBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box editFieldBox = new Box(BoxLayout.Y_AXIS);

        editFieldBox.add(jComboBoxVertex);
        editFieldBox.add(jComboBoxConnection);
        editFieldBox.add(editVertexWeightField);
        editFieldBox.add(editConnectionWeightField);

        editFieldBox.setMaximumSize(new Dimension(150,120));
        editFieldBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box editBox = new Box(BoxLayout.X_AXIS);

        JLabel editLabel = new JLabel("Edit");
        editLabel.setBorder(BorderFactory.createEmptyBorder(10,110,10,0));

        editBox.add(editLabelBox);
        editBox.add(editFieldBox);

        Box mainBox = new Box(BoxLayout.Y_AXIS);

        mainBox.add(configLabel);
        mainBox.add(configBox);
        mainBox.add(configButtonBox);
        mainBox.add(editLabel);
        mainBox.add(editBox);
        mainBox.add(editButtonBox);

        mainPanel.add(BorderLayout.EAST, mainBox);
        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        vertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!setXField.getText().equals("")){
                    int x = Integer.valueOf(setXField.getText()) * gridPanel.width;
                    int y = Integer.valueOf(setYField.getText()) * gridPanel.height;
                    int weight = Integer.valueOf(setWeightField.getText());
                    Vertex vertex = new Vertex(weight, x, y);
                    vertexArrayList.add(vertex);
                    jComboBoxVertex.addItem(vertex.getId());
                    gridPanel.repaint();
                }
            }
        });

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vertexArrayList.clear();
                connectionArrayList.clear();
                jComboBoxVertex.removeAllItems();
                jComboBoxConnection.removeAllItems();
                gridPanel.repaint();
            }
        });

        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame connectionFrame = new JFrame("Set connection");
                connectionFrame.setSize(400,150);
                connectionFrame.setLocationRelativeTo(null);

                JPanel jPanel = new JPanel(new BorderLayout());

                JLabel weightLabel = new JLabel("Weight:  ");
                JTextField weightField = new JTextField();

                JButton okayButton = new JButton("Okay");
                connectionFrame.getContentPane().add(BorderLayout.SOUTH, okayButton);

                JLabel startVertexLabel = new JLabel("Start Vertex:  ");
                JComboBox startVertexComboBox = new JComboBox();

                JLabel endVertexLabel = new JLabel("End Vertex: ");
                JComboBox endVertexComboBox = new JComboBox();

                for(Vertex vertex : vertexArrayList){
                    startVertexComboBox.addItem(vertex.getId());
                    endVertexComboBox.addItem(vertex.getId());
                }

                Box box = new Box(BoxLayout.X_AXIS);

                box.add(startVertexLabel);
                box.add(startVertexComboBox);
                box.add(endVertexLabel);
                box.add(endVertexComboBox);

                Box centerBox = new Box(BoxLayout.Y_AXIS);

                Box mediumBox = new Box(BoxLayout.X_AXIS);

                mediumBox.add(weightLabel);
                mediumBox.add(weightField);

                centerBox.add(Box.createRigidArea(new Dimension(0,18)));
                centerBox.add(mediumBox);
                centerBox.add(Box.createRigidArea(new Dimension(0,18)));

                jPanel.add(BorderLayout.CENTER, centerBox);
                jPanel.add(BorderLayout.NORTH, box);

                connectionFrame.getContentPane().add(BorderLayout.CENTER, jPanel);

                okayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!weightField.getText().equals("") && !vertexArrayList.isEmpty()){

                            Vertex startVertex = null, endVertex = null;

                            for(Vertex vertex : vertexArrayList){
                                if((Integer)startVertexComboBox.getSelectedItem() == vertex.getId()){
                                    startVertex = vertex;
                                }
                                if((Integer)endVertexComboBox.getSelectedItem() == vertex.getId()){
                                    endVertex = vertex;
                                }
                            }
                            int weight = Integer.valueOf(weightField.getText());
                            Connection connection = new Connection(weight, startVertex, endVertex);
                            connectionArrayList.add(connection);
                            String connectionName = connection.getStartVertex().getId() + "/" + connection.getEndVertex().getId();
                            jComboBoxConnection.addItem(connectionName);
                            gridPanel.repaint();
                            connectionFrame.dispose();
                        }
                    }
                });

                connectionFrame.setVisible(true);
            }
        });

        jFrame.setVisible(true);
    }
}
