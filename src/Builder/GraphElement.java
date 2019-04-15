package Builder;

import java.io.Serializable;

public abstract class GraphElement implements Serializable {

    private int weight;

    public GraphElement(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
