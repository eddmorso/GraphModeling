package Builder;

import View.Frame;

import java.io.*;
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
        }

        Vertex start = startVertex;
        Vertex end = endVertex;

        List route = new LinkedList();
        List reachableStations = new LinkedList();
        Map previousStations = new HashMap();

        List<Vertex> neighbors = graph.get(start);
        for(Vertex station : neighbors){
            if(station.equals(end)){
                route.add(getConnection(start, end));
                return route;
            }else{
                reachableStations.add(station);
                previousStations.put(station, start);
            }
        }

        List nextStaions = new LinkedList();
        nextStaions.addAll(neighbors);
        Vertex currentStation = start;

        searchLoop:
        for(int i = 1; i < vertexList.size(); i++){
            List tmpNextStations = new LinkedList();

            for(Iterator j = nextStaions.iterator(); j.hasNext(); ){
                Vertex station = (Vertex) j.next();
                reachableStations.add(station);
                currentStation = station;
                List currentNeighbors = (List)graph.get(currentStation);

                for(Iterator k = currentNeighbors.iterator(); k.hasNext(); ){
                    Vertex neighbor = (Vertex) k.next();

                    if(neighbor.equals(end)){
                        reachableStations.add(neighbor);
                        previousStations.put(neighbor, currentStation);
                        break searchLoop;
                    }else if(!reachableStations.contains(neighbor)){
                        reachableStations.add(neighbor);
                        tmpNextStations.add(neighbor);
                        previousStations.put(neighbor, currentStation);
                    }
                }
            }
            nextStaions = tmpNextStations;
        }

        boolean keepLooping = true;
        Vertex keyStation = end;
        Vertex station;

        while(keepLooping){
            station = (Vertex) previousStations.get(keyStation);
            route.add(0, getConnection(station, keyStation));
            if(start.equals(station)){
                keepLooping = false;
            }
            keyStation = station;
        }
        return route;
    }

    //not working at all !!! FIX
    public boolean hasLooping(Vertex startVertex, Vertex endVertex){

        List<Vertex> endsOfGraph = findEndsOfGraph();
        List<Vertex> connectedVertexes = new ArrayList<>();

        for(Vertex vertex : graph.keySet()){
            if(graph.get(vertex).size() != 0){
                connectedVertexes.add(vertex);
            }
        }
        if(endsOfGraph.contains(startVertex) && connectedVertexes.contains(endVertex)){
            return true;
        }
        return false;
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

        List<Vertex> endsOfGraph = findEndsOfGraph();
        System.out.println(endsOfGraph);
        List<Connection> route = null;
        Map<Vertex, Integer> results = new HashMap<>();

        for(Vertex startVertex : vertexList){

            ArrayList<Integer> critical = new ArrayList<>();

            for(Vertex endVertex : endsOfGraph){

                if(startVertex.equals(endVertex)) continue;

                route = findRoute(startVertex, endVertex);
                int weightCounter = 0;

                for(int i = 0; i < route.size(); i++){
                    weightCounter += route.get(i).getStartVertex().getWeight();
                }
                weightCounter += route.get(route.size() - 1).getEndVertex().getWeight();

                critical.add(weightCounter);
            }

            if(critical.isEmpty()) continue;

            int max = critical.get(0);
            for(int i = 1; i < critical.size(); i++){
                if(max < critical.get(i)){
                    max = critical.get(i);
                }
            }
            results.put(startVertex, max);
        }
        //sorting map
        Map<Vertex, Integer> sorted = results
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        System.out.println(sorted);
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
