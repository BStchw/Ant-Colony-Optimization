package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Graph myGraph = new Graph();

        myGraph.readFromFile("src/main/java/org/example/input.txt");

        //myGraph.printGraph();

        //System.out.println(myGraph.shortestPathByNN("Warszawa"));

        //ArrayList<String> names = myGraph.getListOfNodeNames();

        myGraph.antColonyOptimlization("Warszawa");
    }
}