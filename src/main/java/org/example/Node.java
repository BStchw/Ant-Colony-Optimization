package org.example;

import java.util.HashMap;
import java.util.Map;

public class Node {
    String nodeName;
    HashMap<String, Integer> neighbours = new HashMap<>();
    HashMap<String, Integer> feromons;


    Boolean visited = false;


    public Node(String nodeName){
        this.nodeName = nodeName;
    }
    public void addNeighbour(String name, Integer dist) {
        neighbours.put(name, dist);
    }

    public void printNodeAndNeighbours()    {
        System.out.println(nodeName);

        for (Map.Entry<String, Integer> entry: neighbours.entrySet()) {
            String klucz = entry.getKey();
            Integer wartość = entry.getValue();
            System.out.print("Wierzchołek: " + klucz + ", Dystans: " + wartość + ".     ");
            System.out.println("");
        }
    }

    public void setAllFeromons()    {
        for(String key: feromons.keySet())  {
            feromons.put(key, 10);
        }
    }
}
