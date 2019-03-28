package Model;

public class Connection {
    private int weight;
    private Vertex startVertex, endVertex;

    public Connection(int weight, Vertex startVertex, Vertex endVertex){
        this.weight = weight;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
