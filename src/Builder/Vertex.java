package Builder;

import java.io.Serializable;

public class Vertex implements Serializable {
    private static int counter;
    private int id, weight, x, y;

    public Vertex(int weight, int x, int y){
        id = counter;
        this.weight = weight;
        this.x = x;
        this.y = y;
        counter++;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
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
}
