package Builder;

import java.io.Serializable;

public class Connection extends GraphElement implements Serializable {

    private Vertex startVertex, endVertex;
    private String connectionName;

    public Connection(int weight, Vertex startVertex, Vertex endVertex){
        super(weight);
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

    @Override
    public int hashCode() {
        return connectionName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Connection) {
            Connection connection = (Connection) obj;
            return getConnectionName().equals(connection.getConnectionName());
        }
        return false;
    }

    @Override
    public String toString() {
        return connectionName;
    }
}
