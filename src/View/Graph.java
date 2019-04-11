package View;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph implements Serializable {

    private List<Vertex> vertexList;
    private List<Connection> connectionList;
    private Map<Vertex, List<Vertex>> graph;

    public Graph(){
        vertexList = new ArrayList<>();
        connectionList = new ArrayList<>();
        graph = new HashMap<>();
    }

    public String addConnection(int weight, int startId, int endId){
        Connection connection = new Connection(weight, getVertex(startId), getVertex(endId));
        if(!hasConnection(connection)){
            connectionList.add(connection);
            return connection.getConnectionName();
        }else{
            JOptionPane.showMessageDialog(null, "Connection is already exists");
            return "";
        }
    }

    public int addVertex(String x, String y, String weight){
        Vertex vertex;

        String numX;
        String numY;
        String numW;

        if(!x.equals("") && !y.equals("") && !weight.equals("")){

            char characters [] = x.toCharArray();
            char characters1 [] = y.toCharArray();
            char characters2 [] = weight.toCharArray();

            if(characters.length <= 2 && characters1.length <= 2) {
                numX = Frame.checkCharacters(characters);
                numY = Frame.checkCharacters(characters1);
                numW = Frame.checkCharacters(characters2);
                if(numW.equals("")){
                    return -1;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Unexpected axis range");
                return -1;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Empty field");
            return -1;
        }
        if(!numX.equals("") && Integer.valueOf(numX) < 14 && !numY.equals("") && Integer.valueOf(numY) < 14){
            if(!hasVertex(numX, numY)) {
                int xInt = Integer.valueOf(x);
                int yInt = Integer.valueOf(y);
                int weightInt = Integer.valueOf(weight);
                vertex = new Vertex(weightInt, xInt, yInt);
                vertexList.add(vertex);
            }else{
                JOptionPane.showMessageDialog(null, "This Spot is already taken");
                return -1;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Unexpected axis range");
            return -1;
        }
        return vertex.getId();
    }

    public boolean hasVertex(String x, String y){
        for(Vertex v : vertexList){
            if(v.getX() == Integer.valueOf(x) &&
                    v.getY() == Integer.valueOf(y)){
                return true;
            }
        }
        return false;
    }

    public boolean hasVertex(int id){
        for(Vertex v : vertexList){
            if(v.getId() == id){
                return true;
            }
        }
        return false;
    }

    public boolean hasConnection(Connection connection){
        for(Connection c : connectionList){
            if(c.equals(connection)) return true;
        }
        return false;
    }

    public boolean hasConnection(String name){
        for(Connection c : connectionList){
            if(c.getConnectionName().equals(name)) return true;
        }
        return false;
    }

    public Vertex getVertex(int id){
        if(hasVertex(id)){
            for(Vertex vertex : vertexList){
                if(vertex.getId() == id) return vertex;
            }
        }
        return null;
    }

    public Connection getConnection(String name){
        if(hasConnection(name)){
            for(Connection c : connectionList){
                if(c.getConnectionName().equals(name)) return c;
            }
        }
        return null;
    }

//    public void removeVertex(int id){
//        if(hasVertex(id)){
//            Vertex vertex = getVertex(id);
//            vertexList.remove(vertex);
//        }
//    }
//
//    public void removeConnection(String name){
//        if(hasConnection(name)){
//            Connection connection = getConnection(name);
//            connectionList.remove(connection);
//        }
//    }

    public void saveGraph(File file){
        int counter = Vertex.getCounter();
        try{
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(vertexList);
            objectOutputStream.writeObject(connectionList);
            objectOutputStream.writeObject(graph);
            objectOutputStream.writeObject(counter);

            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }
    }

    public void openGraph(File file){
        ArrayList<Vertex> vertices = null;
        ArrayList<Connection> connections = null;
        HashMap<Vertex, List<Vertex>> graph = null;
        int counter = 0;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            vertices = (ArrayList<Vertex>) objectInputStream.readObject();
            connections = (ArrayList<Connection>) objectInputStream.readObject();
            graph = (HashMap<Vertex, List<Vertex>>) objectInputStream.readObject();
            counter = (int) objectInputStream.readObject();

        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }

        vertexList = vertices;
        connectionList = connections;
        this.graph = graph;
        Vertex.setCounter(counter);
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void clearAll(){
        vertexList.clear();
        connectionList.clear();
        graph.clear();
    }
}
