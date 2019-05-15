package Builder;

import View.Frame;

import java.io.*;
import java.util.*;

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
        throw new RuntimeException(startVertex.getId() + "/" + endVertex.getId() + " Connection doesn't exist");
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

    public List<List<Vertex>> findRoutes(Vertex start, Vertex finish) {

        if(!hasVertex(start.getId()) && !hasVertex(finish.getId())){
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

        // Mark the current node
        isVisited[start.getId()] = true;

        if (start.equals(end)){
            List<Vertex> route = new ArrayList<>(localPathList);
            routes.add(route);
            System.out.println(routes);
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Vertex vertex : graph.get(start)){

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

    public boolean hasLooping(Vertex startVertex, Vertex endVertex){

        if(!startVertex.hasConnections() || !endVertex.hasConnections()) return false;

        List<List<Vertex>> routes = findRoutes(endVertex, startVertex);
        if(routes.isEmpty()){
           return false;
        }
        return true;
    }

    public List<Vertex> sortByVertexWeight(){

        if(!vertexList.isEmpty()){

            List<Vertex> sorted = new ArrayList<>(vertexList);
            Collections.sort(sorted);

            return sorted;
        }else
            throw new UIException("Nothing to sort");
    }

    public Map<Vertex, Integer> sortByCriticalRoute(){

        if(!vertexList.isEmpty() && !connectionList.isEmpty()) {

            List<Vertex> endsOfGraph = findEndsOfGraph();
            List<List<Vertex>> routes = null;
            Map<Vertex, Integer> results = new HashMap<>();
            int weightSum = 0;
            List<Integer> weights = new ArrayList<>();

            for(Vertex start : vertexList){
                for(Vertex end : endsOfGraph){

                    if(start.equals(end)) continue;

                    routes = findRoutes(start, end);
                    if(!routes.isEmpty()){
                        for(List<Vertex> route : routes){
                            for(Vertex vertex : route){
                                weightSum += vertex.getWeight();
                            }
                            weights.add(weightSum);
                            weightSum = 0;
                        }
                        int max = 0;
                        for(int weight : weights){
                            if(max < weight){
                                max = weight;
                            }
                        }
                        results.put(start, max);
                        weights.clear();
                    }
                }
            }
            //sorting map
            Map<Vertex, Integer> sorted = results
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            return sorted;
        }else{
            throw new UIException("Nothing to sort");
        }
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
