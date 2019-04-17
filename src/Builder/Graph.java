package Builder;

import View.Frame;

import java.io.*;
import java.net.ConnectException;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toMap;

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
                if(!hasLooping(getVertex(startId), getVertex(endId))) {

                    connectionList.add(connection);

                    addToGraph(getVertex(startId), getVertex(endId));

                    getVertex(startId).setHasConnections(true);
                    getVertex(endId).setHasConnections(true);

                    return connection.getConnectionName();
                }else{
                    throw new UIException("Looping connections are not allowed");
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
        if(graph.keySet().contains(startVertex) && !graph.get(startVertex).isEmpty()){
            List<Vertex> connectingVertex = graph.get(startVertex);
            if(!connectingVertex.contains(endVertex)){
                connectingVertex.add(endVertex);
            }
        }else if(graph.keySet().contains(startVertex) && graph.get(startVertex).isEmpty()){
            graph.get(startVertex).add(endVertex);
        }else if(!graph.keySet().contains(startVertex)){
            List<Vertex> connectingVertex = new ArrayList<>();
            connectingVertex.add(endVertex);
            graph.put(startVertex, connectingVertex);
        }
        if(!graph.keySet().contains(endVertex)){
            List<Vertex> connectingVertex = new ArrayList<>();
            graph.put(endVertex, connectingVertex);
        }
    }

    public List<Connection> findRoute(Vertex startVertex, Vertex endVertex) {

        if(!hasVertex(startVertex.getId()) && !hasVertex(endVertex.getId())){
            throw new RuntimeException("Vertex do not exist");
        }else if(startVertex.hasConnections() && endVertex.hasConnections()) {

            List<Connection> route = new ArrayList<>();
            List<Vertex> reachableVertex = new ArrayList<>();
            Map<Vertex, Vertex> previousStations = new HashMap<>();

            List<Vertex> neighbors = graph.get(startVertex);
            for (int i = 0; i < neighbors.size(); i++) {
                Vertex vertex = neighbors.get(i);
                if (vertex.equals(endVertex)) {
                    route.add(getConnection(startVertex, vertex));
                    return route;
                } else {
                    reachableVertex.add(vertex);
                    previousStations.put(vertex, startVertex);
                }
            }

            List<Vertex> nextStations = new ArrayList<>(neighbors);
            Vertex currentVertex = startVertex;

            searchLoop:
            for (int i = 1; i < vertexList.size(); i++) {
                List<Vertex> tmpNextVertexes = new ArrayList<>();
                for (int j = 0; j < nextStations.size(); j++) {
                    Vertex vertex = nextStations.get(j);
                    reachableVertex.add(vertex);
                    currentVertex = vertex;
                    List<Vertex> currentNeighbors = graph.get(currentVertex);
                    for (int k = 0; k < currentNeighbors.size(); k++) {
                        Vertex neighbor = currentNeighbors.get(k);
                        if (neighbor.equals(endVertex)) {
                            reachableVertex.add(neighbor);
                            previousStations.put(neighbor, currentVertex);
                            break searchLoop;
                        } else if (!reachableVertex.contains(neighbor)) {
                            reachableVertex.add(neighbor);
                            tmpNextVertexes.add(neighbor);
                            previousStations.put(neighbor, currentVertex);
                        }
                    }
                }
                nextStations = tmpNextVertexes;
            }


            boolean keepLooping = true;
            Vertex keyVertex = endVertex;
            Vertex vertex;

            while (keepLooping) {
                vertex = previousStations.get(keyVertex);
                if(vertex == null){
                    break;
                }
                route.add(0, getConnection(vertex, keyVertex));
                if (startVertex.equals(vertex)) {
                    keepLooping = false;
                }
                keyVertex = vertex;
            }
            return route;
        }else {
            return null;
        }
    }

    //not working at all !!! FIX
    public boolean hasLooping(Vertex startVertex, Vertex endVertex){
        List<Connection> route = findRoute(endVertex, startVertex);
        System.out.println(route);
        if(route == null || route.isEmpty()){
           return false;
        }
        return true;
    }

    public void sortByVertexWeight(){
        if(!vertexList.isEmpty()){
            Collections.sort(vertexList);
            for(Vertex vertex : vertexList){
                System.out.print(vertex.getId() + "(" + vertex.getWeight() + ") ");
            }
        }else
            throw new UIException("Nothing to sort");
    }

    public void sortByCriticalRoute(){

//        List<Vertex> endsOfGraph = findEndsOfGraph();
//        System.out.println(endsOfGraph);
//        List<Connection> route = null;
//        Map<Vertex, Integer> results = new HashMap<>();
//
//        for(Vertex startVertex : vertexList){
//
//            ArrayList<Integer> critical = new ArrayList<>();
//
//            for(Vertex endVertex : endsOfGraph){
//
//                if(startVertex.equals(endVertex)) continue;
//
//                route = findRoute(startVertex, endVertex);
//                int weightCounter = 0;
//
//                for(int i = 0; i < route.size(); i++){
//                    weightCounter += route.get(i).getStartVertex().getWeight();
//                }
//                weightCounter += route.get(route.size() - 1).getEndVertex().getWeight();
//
//                critical.add(weightCounter);
//            }
//
//            if(critical.isEmpty()) continue;
//
//            int max = critical.get(0);
//            for(int i = 1; i < critical.size(); i++){
//                if(max < critical.get(i)){
//                    max = critical.get(i);
//                }
//            }
//            results.put(startVertex, max);
//        }
//        //sorting map
//        Map<Vertex, Integer> sorted = results
//                .entrySet()
//                .stream()
//                .sorted(comparingByValue())
//                .collect(
//                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
//                                LinkedHashMap::new));
//
//        System.out.println(sorted);
    }

    public ArrayList<Vertex> findEndsOfGraph(){

        ArrayList<Vertex> endsList = new ArrayList<>();

        for(Vertex vertex : graph.keySet()){
            if(graph.get(vertex).isEmpty()){
                endsList.add(vertex);
            }
        }
        return endsList;
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
