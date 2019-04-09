package View;

import jdk.nashorn.internal.scripts.JO;
import org.omg.PortableInterceptor.INACTIVE;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;

public class Frame implements Serializable {

    private JFrame jFrame;
    static ArrayList<Vertex> vertexArrayList;
    static ArrayList<Connection> connectionArrayList;

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
        JMenuItem openItem = new JMenuItem("Open");
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

                String textField = setXField.getText();
                String textField1 = setYField.getText();
                String textField2 = setWeightField.getText();
                String numX = "";
                String numY = "";
                String numW = "";
                if(!textField.equals("") && !textField1.equals("") && !textField2.equals("")){
                    char characters [] = textField.toCharArray();
                    char characters1 [] = textField1.toCharArray();
                    char characters2 [] = textField2.toCharArray();
                    if(characters.length <= 2 && characters1.length <= 2) {
                        numX = checkCharacters(characters);
                        numY = checkCharacters(characters1);
                        numW = checkCharacters(characters2);
                        if(numW.equals("")){
                            return;
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Unexpected axis range");
                        return;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Empty field");
                    return;
                }
                if(!numX.equals("") && Integer.valueOf(numX) < 14 && !numY.equals("") && Integer.valueOf(numY) < 14){
                    boolean able = true;
                    for(Vertex vertex : vertexArrayList){
                        if(vertex.getX() == Integer.valueOf(numX) * gridPanel.width &&
                                vertex.getY() == Integer.valueOf(numY) * gridPanel.height){
                            able = false;
                        }
                    }
                    if(able) {
                        int x = Integer.valueOf(setXField.getText()) * gridPanel.width;
                        int y = Integer.valueOf(setYField.getText()) * gridPanel.height;
                        int weight = Integer.valueOf(setWeightField.getText());
                        Vertex vertex = new Vertex(weight, x, y);
                        vertexArrayList.add(vertex);
                        jComboBoxVertex.addItem(vertex.getId());
                        setXField.setText("");
                        setYField.setText("");
                        setWeightField.setText("");
                        gridPanel.repaint();
                    }else{
                        JOptionPane.showMessageDialog(null, "This Spot is already taken");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Unexpected axis range");
                }
            }
            public String checkCharacters(char [] arr){
                String s = "";
                for(int i = 0; i < arr.length; i++) {
                    if (arr[i] >= 48 && arr[i] <=57) {
                        s += (arr[i]);
                    }else{
                        JOptionPane.showMessageDialog(null, "Unexpected token");
                        return s;
                    }
                }
                return s;
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

                            for(Connection con : connectionArrayList){
                                if(con.equals(connection)){
                                    JOptionPane.showMessageDialog(null, "Connection already exists");
                                    return;
                                }
                            }
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
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vertexArrayList.clear();
                connectionArrayList.clear();

                jComboBoxVertex.removeAllItems();
                jComboBoxConnection.removeAllItems();

                Vertex.counter = 0;

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

                Saver saver = new Saver();

                File file = jFileChooser.getSelectedFile();
                String fileName = file.getAbsolutePath();

                if(fileName.endsWith(".ser")){
                    saver.save(file);
                }else {
                    fileName += ".ser";
                    saver.save(new File(fileName));
                }
            }
        });
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(jFrame);

                Opener.open(jFileChooser.getSelectedFile());

                jComboBoxVertex.removeAllItems();
                jComboBoxConnection.removeAllItems();

                jComboBoxVertex.addItem("none");
                jComboBoxConnection.addItem("none");

                for(int i = 0; i < vertexArrayList.size(); i++){
                    jComboBoxVertex.addItem(vertexArrayList.get(i).getId());
                }

                for(int i = 0; i < connectionArrayList.size(); i++){
                    String connectionName = connectionArrayList.get(i).getStartVertex().getId() + "/" + connectionArrayList.get(i).getEndVertex().getId();
                    jComboBoxConnection.addItem(connectionName);
                }

                gridPanel.repaint();
            }
        });
        editVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!editVertexWeightField.getText().equals("") && !jComboBoxVertex.getSelectedItem().equals("none")){
                    String s = "";
                    char arr [] = editVertexWeightField.getText().toCharArray();
                    for(int i = 0; i < arr.length; i++){
                        if(arr[i] >= 48 && arr[i] <= 57){
                            s += arr[i];
                        }
                    }
                    if(!s.equals("")){
                        int weight = Integer.valueOf(s);
                        int id = (int) jComboBoxVertex.getSelectedItem();

                        for(Vertex vertex : vertexArrayList){
                            if(vertex.getId() == id){
                                vertex.setWeight(weight);
                                editVertexWeightField.setText("");
                                gridPanel.repaint();
                                break;
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Unexpected token");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Pick the weight and vertex");
                }
            }
        });
        editConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jComboBoxConnection.getSelectedItem().equals("none") && !editConnectionWeightField.getText().equals("")){
                    String s = "";
                    char arr [] = editConnectionWeightField.getText().toCharArray();
                    for(int i = 0; i < arr.length; i++){
                        if(arr[i] >= 48 && arr[i] <= 57){
                            s += arr[i];
                        }
                    }
                    if(!s.equals("")){
                        int weight = Integer.valueOf(s);
                        String [] sides = jComboBoxConnection.getSelectedItem().toString().split("/");

                        for(Connection connection : connectionArrayList){
                            if(connection.getStartVertex().getId() == Integer.valueOf(sides[0]) &&
                                    connection.getEndVertex().getId() == Integer.valueOf(sides[1])){

                                connection.setWeight(weight);
                                editConnectionWeightField.setText("");
                                gridPanel.repaint();
                                break;
                            }
                        }
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

                int vertexId = -1;
                String connectionName = "";

                if(!jComboBoxVertex.getSelectedItem().equals("none")){

                    vertexId = (int) jComboBoxVertex.getSelectedItem();

                    for(Vertex vertex : vertexArrayList){
                        if(vertex.getId() == vertexId){
                            ArrayList<Connection> removeList = new ArrayList<>();
                            for(Connection connection : connectionArrayList){
                                if(connection.getStartVertex().equals(vertex) || connection.getEndVertex().equals(vertex)){
                                    String conName = connection.getStartVertex().getId() + "/" + connection.getEndVertex().getId();
                                    removeList.add(connection);
                                    jComboBoxConnection.removeItem(conName);
                                }
                            }
                            jComboBoxVertex.removeItem(vertexId);
                            vertexArrayList.remove(vertex);
                            connectionArrayList.removeAll(removeList);
                            break;
                        }
                    }
                }
                if(!jComboBoxConnection.getSelectedItem().equals("none")){

                    connectionName += jComboBoxConnection.getSelectedItem();

                    String [] sides = connectionName.split("/");

                    int startVertexId = Integer.valueOf(sides[0]);
                    int endVertexId = Integer.valueOf(sides[1]);

                    for(Connection connection : connectionArrayList){
                        if(connection.getStartVertex().getId() == startVertexId && connection.getEndVertex().getId() == endVertexId){
                            jComboBoxConnection.removeItem(connectionName);
                            connectionArrayList.remove(connection);
                            break;
                        }
                    }
                }
                gridPanel.repaint();
            }
        });

        jFrame.setVisible(true);
    }
}
