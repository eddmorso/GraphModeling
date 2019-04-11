package View;

import java.io.Serializable;

public class Connection implements Serializable {
    private int weight;
    private Vertex startVertex, endVertex;
    private String connectionName;

    public Connection(int weight, Vertex startVertex, Vertex endVertex){
        this.weight = weight;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        connectionName = startVertex.getId() + "/" + endVertex.getId();
    }

    public String getConnectionName() {
        return connectionName;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Connection) {
            Connection connection = (Connection) obj;
            return connection.getStartVertex().equals((startVertex)) && connection.getEndVertex().equals(endVertex);
        }
        return false;
    }
}
