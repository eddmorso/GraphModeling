package Builder;

import java.io.Serializable;

public class Vertex extends GraphElement implements Serializable {

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
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex){
            Vertex vertex = (Vertex) obj;
            return vertex.getId() == getId() && vertex.getX() == getX() && vertex.getY() == getY();
        }
        return false;
    }
}
