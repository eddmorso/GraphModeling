package Builder;

import java.io.Serializable;

public class Vertex extends GraphElement implements Comparable<Vertex>, Serializable {

    private static int counter;
    private int id, x, y;

    public Vertex(int weight, int x, int y){
        super(weight);
        id = counter;
        this.x = x;
        this.y = y;
        counter++;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void setCounter(int counter) {
        Vertex.counter = counter;
    }

    public static void setCounterToZero() {
        counter = 0;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(Vertex o) {
        if(getWeight() == o.getWeight()){
            return 0;
        }else if(getWeight() > o.getWeight()){
            return -1;
        }else
            return 1;
    }
}
