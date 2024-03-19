package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    ArrayList<Node> nodes = new ArrayList<>();

    public Integer shortestPathByNN(String nodeName) {
        Integer counter = 0;
        Node node = getNode(nodeName);

        while(isAllVisited() == false) {
            node.visited = true;
            System.out.println(node.nodeName);
            int minValue = Integer.MAX_VALUE;
            String minKey = null;

            for (String klucz : node.neighbours.keySet()) {

                Integer value = node.neighbours.get(klucz);

                if (minValue > value && !getNode(klucz).visited) {
                    minValue = value;
                    minKey = klucz;
                }
            }
            if(isAllVisited() == false) {
                node = getNode(minKey);
                counter = counter + minValue;
            }




        }
        return counter;
    }

    public void setAllNodesFeromons()  {
        for(Node node: nodes)    {
            node.feromons = new HashMap<>(node.neighbours);
            node.setAllFeromons();
        }
    }

    public boolean isAllVisited()   {
        for(Node node: nodes)   {
            if(node.visited == false)   {
                return false;
            }
        }
        return true;
    }

    public void readFromFile(String filePath)   {
        try (FileReader fileReader = new FileReader(filePath)) {
            // Tworzymy obiekt BufferedReader, który odczyta tekst z pliku
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Zmienna do przechowywania odczytanego wiersza
            String line;

            // Odczytujemy plik linia po linii, dopóki nie natrafimy na koniec pliku (null)
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");

                Node n1 = new Node(parts[0]);
                if(containsSame(n1) == false)    {
                    nodes.add(n1);
                }

                Node n2 = new Node(parts[1]);
                if(containsSame(n2) == false)    {
                    nodes.add(n2);
                }

                Integer dist = Integer.parseInt(parts[2]);

                getNode(parts[0]).addNeighbour(parts[1], dist);
                getNode(parts[1]).addNeighbour(parts[0], dist);
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean containsSame(Node node)   {
        for(int i=0; i<nodes.size(); i++)   {
            if(node.nodeName.equals(nodes.get(i).nodeName))  {
                return true;
            }
        }
        return false;
    }

    public Node getNode(String nodeName)    {
        for(int i=0; i<nodes.size(); i++)   {
            if(nodeName.equals(nodes.get(i).nodeName))  {
                return nodes.get(i);
            }
        }
        return null;
    }

    public void printGraph()  {
        for(Node node: nodes)   {
            node.printNodeAndNeighbours();
        }
    }

    public ArrayList<String> getListOfNodeNames()    {
        ArrayList<String> namesList = new ArrayList<>();

        for(Node node: nodes)   {
            namesList.add(node.nodeName);
        }
        return namesList;
    }

    public static String getRandomCity(HashMap<String, Integer> probabilities) {
        // Oblicz sumę prawdopodobieństw
        int totalProbability = 0;
        for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
            int probability = entry.getValue();
            totalProbability = totalProbability+probability;
        }

        // Wylosuj liczbę z przedziału od 0 do sumy prawdopodobieństw
        Random rand = new Random();
        int randomNumber = rand.nextInt(totalProbability);

        // Znajdź klucz, którego prawdopodobieństwo przekracza wylosowaną liczbę
        int currentSum = 0;
        for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
            currentSum += entry.getValue();
            if (currentSum > randomNumber) {
                return entry.getKey();
            }
        }

        // Jeśli coś poszło nie tak, zwróć null
        return null;
    }

    public int getDistance(ArrayList<String> route)    {
        int distance = 0;


        for(int i=0; i<route.size()-1; i++) {
            Node node1 = getNode(route.get(i));
            distance += node1.neighbours.get(route.get(i + 1));
        }

        return distance;
    }

    public int IncreaseFeromons(ArrayList<String> route)    {
        int distance = 0;


        for(int i=0; i<route.size()-1; i++) {
            Node node = getNode(route.get(i));

            int value = node.feromons.get(route.get(i + 1));
            value = value + 5;
            node.feromons.put(route.get(i + 1), value);
        }

        return distance;
    }

    public void setAllVisitedAsFalse()  {
        for(Node node: nodes)   {
            node.visited=false;
        }
    }

    public void antColonyOptimlization(String firstNodeName)    {
        int n = 100;        //number of ants

        int minDistance = Integer.MAX_VALUE;

        for(int i=0; i<10000000; i++) {
            System.out.println(i);
            setAllNodesFeromons();

            ArrayList<String>[] routes = new ArrayList[n];

            //for every ant:
            for (int j = 0; j < n; j++) {
                routes[j] = new ArrayList<>();
                Node curentNode = getNode(firstNodeName);
                setAllVisitedAsFalse();

                routes[j].add(firstNodeName);
                curentNode.visited = true;
                while (isAllVisited() == false) {
                    String nextNodeName = getRandomCity(curentNode.feromons);

                    Node nextNode = getNode(nextNodeName);

                    if (nextNode.visited == false) {
                        curentNode = nextNode;
                        routes[j].add(nextNodeName);
                        curentNode.visited = true;
                    }
                }

                routes[j].add(firstNodeName);

                if (minDistance > getDistance(routes[j])) {
                    minDistance = getDistance(routes[j]);
                }

            }
        }
        System.out.println("Minimalmy dystans to: " + minDistance);
    }

    
}
