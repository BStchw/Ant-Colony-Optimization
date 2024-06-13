package org.example;

public class Main {
    public static void main(String[] args) {

        Graph myGraph = new Graph();

        myGraph.readFromFile("src/main/java/org/example/input.txt");

        //myGraph.antColonyOptimlization("Warszawa");
        System.out.println("The shortest path by NN: " + myGraph.shortestPathByNN("Warszawa"));
    }
}