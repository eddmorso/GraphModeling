package Builder;

import java.io.Serializable;

public abstract class GraphElement implements Comparable<GraphElement>, Serializable {

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

    @Override
    public int compareTo(GraphElement o) {
        if(getWeight() == o.getWeight()){
            return 0;
        }else if(getWeight() > o.getWeight()){
            return -1;
        }else
            return 1;
    }
}
