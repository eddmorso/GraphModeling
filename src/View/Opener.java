package View;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Opener {

    public static void open(File file){
        ArrayList<Vertex> vertices = null;
        ArrayList<Connection> connections = null;
        int counter = 0;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            vertices = (ArrayList<Vertex>) objectInputStream.readObject();
            connections = (ArrayList<Connection>) objectInputStream.readObject();
            counter = (int) objectInputStream.readObject();

        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }

        Frame.vertexArrayList = vertices;
        Frame.connectionArrayList = connections;
        Vertex.counter = counter;
    }
}
