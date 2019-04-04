package View;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Saver {
    private ArrayList<Vertex>  vertices;
    private ArrayList<Connection> connections;
    private int counter;

    public Saver(){
        vertices = Frame.vertexArrayList;
        connections = Frame.connectionArrayList;
        counter = Vertex.counter;
    }

    public void save(File file){
        try{
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(vertices);
            objectOutputStream.writeObject(connections);
            objectOutputStream.writeObject(counter);

            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }
    }
}
