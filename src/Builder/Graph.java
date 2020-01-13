package Builder;

import Exceptions.UIException;
import Viewer.Frame;
import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Graph implements Serializable {
    private List<Vertex> vertexList;
    private List<Connection> connectionList;
    private Map<Vertex, List<Vertex>> graphMap;
    private static Graph graph;

    private Graph() {
        vertexList = new ArrayList<>();
        connectionList = new ArrayList<>();
        graphMap = new HashMap<>();
    }

    public static Graph getInstance() {
        if (graph == null) {
            graph = new Graph();
        }
        return graph;
    }

    public int addVertex(String x, String y, String weight) {
        Vertex vertex;
        String numX;
        String numY;
        String numW;

        if (!x.equals("") && !y.equals("") && !weight.equals("")) {
            char [] xChars = x.toCharArray();
            char [] yChars = y.toCharArray();
            char [] characters2 = weight.toCharArray();

            if (xChars.length <= 2 && yChars.length <= 2) {
                numX = Frame.checkCharacters(xChars);
                numY = Frame.checkCharacters(yChars);
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

    private boolean hasVertex(String x, String y) {
        for (Vertex v : vertexList) {
            if (v.getX() == Integer.valueOf(x) &&
                    v.getY() == Integer.valueOf(y)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVertex(int id) {
        for (Vertex v : vertexList) {
            if(v.getId() == id){
                return true;
            }
        }
        return false;
    }

    private boolean hasConnection(Connection connection) {
        for(Connection c : connectionList){
            if(c.equals(connection)) return true;
        }
        return false;
    }

    private boolean hasConnection(String name) {
        for (Connection c : connectionList) {
            if (c.getConnectionName().equals(name)) return true;
        }
        return false;
    }

    public Vertex getVertex(int id) {
        if (hasVertex(id)) {
            for (Vertex vertex : vertexList) {
                if(vertex.getId() == id) return vertex;
            }
        }
        return null;
    }

    public Connection getConnection(String name) {
        if (hasConnection(name)) {
            for(Connection c : connectionList){
                if(c.getConnectionName().equals(name)) return c;
            }
        }
        return null;
    }

    public Connection getConnection(Vertex startVertex, Vertex endVertex) {
        for(Connection c : connectionList){
            if(c.getStartVertex().equals(startVertex) && c.getEndVertex().equals(endVertex)){
                return c;
            }
        }
        throw new RuntimeException(startVertex.getId() + "/" + endVertex.getId() + " Connection doesn't exist");
    }

    private void addToGraph(Vertex startVertex, Vertex endVertex) {
        if (graphMap.containsKey(startVertex) && !graphMap.get(startVertex).isEmpty()) {
            List<Vertex> connectingVertex = graphMap.get(startVertex);
            if (!connectingVertex.contains(endVertex)) {
                connectingVertex.add(endVertex);
            }
        } else if (graphMap.containsKey(startVertex) && graphMap.get(startVertex).isEmpty()) {
            graphMap.get(startVertex).add(endVertex);
        } else if (!graphMap.containsKey(startVertex)) {
            List<Vertex> connectingVertex = new ArrayList<>();
            connectingVertex.add(endVertex);
            graphMap.put(startVertex, connectingVertex);
        }
        if (!graphMap.containsKey(endVertex)) {
            List<Vertex> connectingVertex = new ArrayList<>();
            graphMap.put(endVertex, connectingVertex);
        }
    }

    private List<List<Vertex>> findRoutes(Vertex start, Vertex finish) {
        if (!hasVertex(start.getId()) && !hasVertex(finish.getId())) {
            throw new RuntimeException("Vertex do not exist");
        }
        List<List<Vertex>> routes = new ArrayList<>();
        boolean[] isVisited = new boolean[vertexList.size()];
        List<Vertex> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(start);

        //Call recursive utility
        printAllPathsUtil(start, finish, isVisited, pathList, routes);

        return routes;
    }

    private void printAllPathsUtil(Vertex start, Vertex end, boolean[] isVisited,
                                   List<Vertex> localPathList, List<List<Vertex>> routes) {
        if (!start.hasConnections() || !end.hasConnections()) {
            return;
        }
        // Mark the current node
        isVisited[start.getId()] = true;

        if (start.equals(end)){
            List<Vertex> route = new ArrayList<>(localPathList);
            routes.add(route);
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Vertex vertex : graphMap.get(start)){
            if (!isVisited[vertex.getId()]){
                // store current node
                // in path[]
                localPathList.add(vertex);
                printAllPathsUtil(vertex, end, isVisited, localPathList, routes);
                // remove current node
                // in path[]
                localPathList.remove(vertex);
            }
        }
        // Mark the current node
        isVisited[start.getId()] = false;
    }

    private boolean hasLooping(Vertex startVertex, Vertex endVertex){
        if (!startVertex.hasConnections() || !endVertex.hasConnections()) return false;
        List<List<Vertex>> routes = findRoutes(endVertex, startVertex);

        return routes.isEmpty();
    }

    public List<Vertex> sortByVertexWeight() {
        if(!vertexList.isEmpty()){
            long startTime = System.nanoTime();
            List<Vertex> sorted = new ArrayList<>(vertexList);
            long timeRes = System.nanoTime() - startTime;

            Collections.sort(sorted);
            System.out.println(timeRes);

            return sorted;
        }else
            throw new UIException("Nothing to sort");
    }

    public Map<Vertex, Integer> sortByCriticalRoute() {
        if (!vertexList.isEmpty() && !connectionList.isEmpty()) {
            long startTime = System.nanoTime();
            List<Vertex> endsOfGraph = findEndsOfGraph();
            List<List<Vertex>> routes;
            Map<Vertex, Integer> results = new HashMap<>();
            int weightSum = 0;
            List<Integer> weights = new ArrayList<>();

            for(Vertex start : vertexList){
                for(Vertex end : endsOfGraph){
                    if(start.equals(end)) continue;
                    routes = findRoutes(start, end);

                    if (!routes.isEmpty()) {
                        for (List<Vertex> route : routes) {
                            for (Vertex vertex : route) {
                                weightSum += vertex.getWeight();
                            }
                            weights.add(weightSum);
                            weightSum = 0;
                        }
                    }
                }
                int max = -1;
                for (int weight : weights) {
                    if (max < weight) {
                        max = weight;
                    }
                }
                if (max == -1) {
                    results.put(start, start.getWeight());
                    weights.clear();
                } else
                results.put(start, max);
                weights.clear();
            }
            //sorting map
            Map<Vertex, Integer> sorted = results
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));

            long timeRes = System.nanoTime() - startTime;
            System.out.println(timeRes);

            return sorted;
        }else{
            throw new UIException("Nothing to sort");
        }
    }

    private ArrayList<Vertex> findEndsOfGraph() {
        ArrayList<Vertex> endsList = new ArrayList<>();

        for(Vertex vertex : graphMap.keySet()){
            if(graphMap.get(vertex).isEmpty()){
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

    public void saveGraph(File file) throws Exception {
        int counter = Vertex.getCounter();
        FileOutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(vertexList);
        objectOutputStream.writeObject(connectionList);
        objectOutputStream.writeObject(graphMap);
        objectOutputStream.writeObject(counter);

        objectOutputStream.close();
    }

    public void openGraph(File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        ArrayList<Vertex> vertices = (ArrayList<Vertex>) objectInputStream.readObject();
        ArrayList<Connection> connections = (ArrayList<Connection>) objectInputStream.readObject();
        HashMap<Vertex, List<Vertex>> graph = (HashMap<Vertex, List<Vertex>>) objectInputStream.readObject();
        int counter = (int) objectInputStream.readObject();

        vertexList = vertices;
        connectionList = connections;
        this.graphMap = graph;
        Vertex.setCounter(counter);
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void clearAll() {
        vertexList.clear();
        connectionList.clear();
        graphMap.clear();
    }
}