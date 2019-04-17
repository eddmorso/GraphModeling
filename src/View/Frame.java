package View;

import Builder.Connection;
import Builder.Graph;
import Builder.UIException;
import Builder.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Frame {

    private JFrame jFrame;
    static Graph graph;

    public void init(){
        graph = new Graph();

        jFrame = new JFrame("Graph modeling");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(900,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu sortMenu = new JMenu("Sort");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        JMenuItem sortVertexWeightItem = new JMenuItem("Sort Vertex Weight");
        JMenuItem sortCriticalRouteItem = new JMenuItem("Sort Critical Route");
        sortMenu.add(sortVertexWeightItem);
        sortMenu.add(sortCriticalRouteItem);
        jMenuBar.add(fileMenu);
        jMenuBar.add(sortMenu);
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
        jComboBoxVertex.addItem("none");

        JComboBox jComboBoxConnection = new JComboBox();
        jComboBoxConnection.addItem("none");

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

        JButton editConnectionButton = new JButton("edit connection");
        editConnectionButton.setMaximumSize(new Dimension(200,25));

        JButton deleteButton = new JButton("delete");
        deleteButton.setMaximumSize(new Dimension(200,25));

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
                try {
                    int id = graph.addVertex(setXField.getText(), setYField.getText(), setWeightField.getText());
                    jComboBoxVertex.addItem(id);
                    setXField.setText("");
                    setYField.setText("");
                    setWeightField.setText("");
                    gridPanel.repaint();
                }catch (UIException ex){
                    JOptionPane.showMessageDialog(null, ex);
                    ex.printStackTrace();
                }
            }});
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

                if(!graph.getVertexList().isEmpty()) {
                    for (Vertex vertex : graph.getVertexList()) {
                        startVertexComboBox.addItem(vertex.getId());
                        endVertexComboBox.addItem(vertex.getId());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Add some vertexes");
                    return;
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
                        if(!weightField.getText().equals("")){
                            try{
                            String connectionName =
                                    graph.addConnection(Integer.valueOf(weightField.getText()), (int) startVertexComboBox.getSelectedItem(), (int) endVertexComboBox.getSelectedItem());
                                jComboBoxConnection.addItem(connectionName);
                                gridPanel.repaint();
                                connectionFrame.dispose();
                            }catch (UIException ex){
                                JOptionPane.showMessageDialog(null, ex);
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                connectionFrame.setVisible(true);
            }
        });
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.clearAll();

                jComboBoxVertex.removeAllItems();
                jComboBoxConnection.removeAllItems();

                Vertex.setCounterToZero();

                jComboBoxVertex.addItem("none");
                jComboBoxConnection.addItem("none");

                gridPanel.repaint();
            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(jFrame);

                File file = jFileChooser.getSelectedFile();
                String fileName = file.getAbsolutePath();

                if(fileName.endsWith(".ser")){
                    try {
                        graph.saveGraph(file);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Something went wrong");
                        ex.printStackTrace();
                    }
                }else {
                    fileName += ".ser";
                    try {
                        graph.saveGraph(new File(fileName));
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Something went wrong");
                        ex.printStackTrace();
                    }
                }
            }
        });
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(jFrame);

                try {
                    graph.openGraph(jFileChooser.getSelectedFile());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Something went wrong");
                    ex.printStackTrace();
                    return;
                }

                jComboBoxVertex.removeAllItems();
                jComboBoxConnection.removeAllItems();

                jComboBoxVertex.addItem("none");
                jComboBoxConnection.addItem("none");

                for(Vertex v : graph.getVertexList()){
                    jComboBoxVertex.addItem(v.getId());
                }

                for(Connection c : graph.getConnectionList()){
                    jComboBoxConnection.addItem(c.getConnectionName());
                }

                gridPanel.repaint();
            }
        });
        sortVertexWeightItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    graph.sortByVertexWeight();
                }catch (Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        sortCriticalRouteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    graph.sortByCriticalRoute();
                }catch (Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        editVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!editVertexWeightField.getText().equals("") && !jComboBoxVertex.getSelectedItem().equals("none")){
                    String s = checkCharacters(editVertexWeightField.getText().toCharArray());

                    if(!s.equals("")){
                        int weight = Integer.valueOf(s);
                        int id = (int) jComboBoxVertex.getSelectedItem();

                        Vertex vertex = graph.getVertex(id);
                        vertex.setWeight(weight);
                        editVertexWeightField.setText("");
                        gridPanel.repaint();
                    }else
                        JOptionPane.showMessageDialog(null, "Unexpected token");
                }else
                    JOptionPane.showMessageDialog(null, "Pick the weight and vertex");
            }
        });
        editConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jComboBoxConnection.getSelectedItem().equals("none") && !editConnectionWeightField.getText().equals("")){
                    String s = checkCharacters(editConnectionWeightField.getText().toCharArray());
                    if(!s.equals("")){
                        int weight = Integer.valueOf(s);
                        Connection connection = graph.getConnection(jComboBoxConnection.getSelectedItem().toString());

                        connection.setWeight(weight);
                        editConnectionWeightField.setText("");
                        gridPanel.repaint();
                    }else{
                        JOptionPane.showMessageDialog(null, "Unexpected token");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Pick the weight and connection");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String connectionName = "";
                int vertexId;
                if(!jComboBoxVertex.getSelectedItem().equals("none")){

                    vertexId = (int) jComboBoxVertex.getSelectedItem();

                    for(Vertex vertex : graph.getVertexList()){
                        if(vertex.getId() == vertexId){
                            ArrayList<Connection> removeList = new ArrayList<>();
                            for(Connection connection : graph.getConnectionList()){
                                if(connection.getStartVertex().equals(vertex) || connection.getEndVertex().equals(vertex)){
                                    String conName = connection.getStartVertex().getId() + "/" + connection.getEndVertex().getId();
                                    removeList.add(connection);
                                    jComboBoxConnection.removeItem(conName);
                                }
                            }
                            jComboBoxVertex.removeItem(vertexId);
                            graph.getVertexList().remove(vertex);
                            graph.getConnectionList().removeAll(removeList);
                            break;
                        }
                    }
                }
                if(!jComboBoxConnection.getSelectedItem().equals("none")){

                    connectionName += jComboBoxConnection.getSelectedItem();

                    String [] sides = connectionName.split("/");

                    int startVertexId = Integer.valueOf(sides[0]);
                    int endVertexId = Integer.valueOf(sides[1]);

                    for(Connection connection : graph.getConnectionList()){
                        if(connection.getStartVertex().getId() == startVertexId && connection.getEndVertex().getId() == endVertexId){
                            jComboBoxConnection.removeItem(connectionName);
                            graph.getConnectionList().remove(connection);
                            break;
                        }
                    }
                }
                gridPanel.repaint();
            }
        });

        jFrame.setVisible(true);
    }

    public static String checkCharacters(char [] arr){
        String s = "";
        for(int i = 0; i < arr.length; i++) {
            if (arr[i] >= 48 && arr[i] <=57) {
                s += (arr[i]);
            }else{
                return s;
            }
        }
        return s;
    }
}
