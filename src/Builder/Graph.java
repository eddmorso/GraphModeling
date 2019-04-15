package Builder;

import View.Frame;

import java.io.*;
import java.util.*;

public class Graph implements Serializable {

    private List<Vertex> vertexList;
    private List<Connection> connectionList;
    private Map<Vertex, List<Vertex>> graph;

    public Graph() {
        vertexList = new ArrayList<>();
        connectionList = new ArrayList<>();
        graph = new HashMap<>();
    }

    public int addVertex(String x, String y, String weight) {
        Vertex vertex;

        String numX;
        String numY;
        String numW;

        if (!x.equals("") && !y.equals("") && !weight.equals("")) {

            char characters[] = x.toCharArray();
            char characters1[] = y.toCharArray();
            char characters2[] = weight.toCharArray();

            if (characters.length <= 2 && characters1.length <= 2) {
                numX = Frame.checkCharacters(characters);
                numY = Frame.checkCharacters(characters1);
                numW = Frame.checkCharacters(characters2);
                if (numW.equals("") || numX.equals("") || numY.equals("")) {
                    throw new UIException("Unexpected token");
                }
            } else {
                throw new UIException("Unexpected axis range");
            }
        } else {
            throw new UIException("Empty field");
        }
        if (Integer.valueOf(numX) < 14 && Integer.valueOf(numY) < 14) {
            if (!hasVertex(numX, numY)) {
                int xInt = Integer.valueOf(x);
                int yInt = Integer.valueOf(y);
                int weightInt = Integer.valueOf(weight);
                vertex = new Vertex(weightInt, xInt, yInt);
                vertexList.add(vertex);
            } else {
                throw new UIException("This Spot is already taken");
            }
        } else {
            throw new UIException("Unexpected axis range");
        }
        return vertex.getId();
    }

    public String addConnection(int weight, int startId, int endId) {
        if (hasVertex(startId) && hasVertex(endId)) {
            Connection connection = new Connection(weight, getVertex(startId), getVertex(endId));
            if (!hasConnection(connection)) {
                if (!hasLooping(getVertex(startId), getVertex(endId))) {
                    connectionList.add(connection);
                    addToGraph(vertexList.get(startId), vertexList.get(endId));
                    return connection.getConnectionName();
                }else{
                    throw new UIException("Looping connection");
                }
            } else {
                throw new UIException("Connection already exists");
            }
        } else {
            throw new UIException("Vertex doesn't exist");
        }
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

    public Connection getConnection(Vertex startVertex, Vertex endVertex){
        for(Connection c : connectionList){
            if(c.getStartVertex().equals(startVertex) && c.getEndVertex().equals(endVertex)){
                return c;
            }
        }
        return null;
    }

    public void addToGraph(Vertex startVertex, Vertex endVertex){
        if(graph.keySet().contains(startVertex)){
            List<Vertex> connectingVertex = graph.get(startVertex);
            if(!connectingVertex.contains(endVertex)){
                connectingVertex.add(endVertex);
            }
        }else{
            List<Vertex> connectingVertex = new ArrayList<>();
            connectingVertex.add(endVertex);
            graph.put(startVertex, connectingVertex);
        }
    }

    //not suitable here code, redo!!! ___CRUCIAL___
    public boolean hasLooping(Vertex startVertex, Vertex endVertex){

        List route = new LinkedList();
        List reachableStations = new LinkedList();
        Map previousStations = new HashMap();

        List neighbors = (List) graph.get(endVertex);
        if(neighbors != null) {
            for (Iterator iterator = neighbors.iterator(); iterator.hasNext(); ) {
                Vertex vertex = (Vertex) iterator.next();
                if (vertex.equals(startVertex)) {
                    return true;
                } else {
                    reachableStations.add(vertex);
                    previousStations.put(vertex, endVertex);
                }
            }
        }else{
            return false;
        }
        List nextStations = new LinkedList();
        nextStations.addAll(neighbors);
        Vertex currentVertex = endVertex;

        searchLoop:
        for(int i = 1; i < vertexList.size(); i++){
            List tmpNextStations = new LinkedList();

            for(Iterator j = nextStations.iterator(); j.hasNext(); ){
                Vertex vertex = (Vertex) j.next();
                reachableStations.add(vertex);
                currentVertex = vertex;
                List currentNeighbors = (List)graph.get(currentVertex);

                for(Iterator k = currentNeighbors.iterator(); k.hasNext(); ){
                    Vertex neighbor = (Vertex) k.next();

                    if(neighbor.equals(startVertex)){
                        reachableStations.add(neighbor);
                        previousStations.put(neighbor, currentVertex);
                        break searchLoop;
                    }else if(!reachableStations.contains(neighbor)){
                        reachableStations.add(neighbor);
                        tmpNextStations.add(neighbor);
                        previousStations.put(neighbor, currentVertex);
                    }
                }
            }
            nextStations = tmpNextStations;
        }

        boolean keepLooping = true;
        Vertex keyVertex = startVertex;
        Vertex vertex;

        while(keepLooping){
            vertex = (Vertex) previousStations.get(keyVertex);
            route.add(0, getConnection(vertex, keyVertex));
            if(endVertex.equals(vertex)){
                keepLooping = false;
            }
            keyVertex = vertex;
        }
        if(route.contains(null)){
            return false;
        }
        System.out.println(route);
        return true;
    }

    public void sortByVertexWeight(){
        if(!vertexList.isEmpty()){
            Collections.sort(vertexList);
            System.out.println(vertexList);
        }else
            throw new UIException("Nothing to sort");
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

    public void saveGraph(File file) throws Exception{
        int counter = Vertex.getCounter();
        FileOutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(vertexList);
        objectOutputStream.writeObject(connectionList);
        objectOutputStream.writeObject(graph);
        objectOutputStream.writeObject(counter);

        objectOutputStream.close();
    }

    public void openGraph(File file) throws Exception{

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        ArrayList<Vertex> vertices = (ArrayList<Vertex>) objectInputStream.readObject();
        ArrayList<Connection> connections = (ArrayList<Connection>) objectInputStream.readObject();
        HashMap<Vertex, List<Vertex>> graph = (HashMap<Vertex, List<Vertex>>) objectInputStream.readObject();
        int counter = (int) objectInputStream.readObject();

        vertexList = vertices;
        connectionList = connections;
        this.graph = graph;
        Vertex.setCounter(counter);

        System.out.println(graph);
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
