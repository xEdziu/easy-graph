package me.goral.easygraph.console;

import me.goral.easygraph.algorithms.DijkstraAlgorithm;
import me.goral.easygraph.classes.AdjacencyListGraph;
import me.goral.easygraph.classes.AdjacencyMatrixGraph;
import me.goral.easygraph.classes.Graph;
import me.goral.easygraph.classes.Vertex;

import java.util.Map;

public class GraphTest {
    public static void main(String[] args) {
        Graph<Integer, Integer> matrixGraph = new AdjacencyMatrixGraph<>(false);
        Graph<Integer, Integer> listGraph = new AdjacencyListGraph<>(false);

        Vertex<Integer>[] vertices = new Vertex[5];
        for (int i = 0; i < 5; i++) {
            vertices[i] = matrixGraph.insertVertex(i + 1);
            listGraph.insertVertex(vertices[i].getElement());
        }

        matrixGraph.insertEdge(vertices[0], vertices[1], 10);
        matrixGraph.insertEdge(vertices[0], vertices[2], 20);
        matrixGraph.insertEdge(vertices[1], vertices[2], 5);
        matrixGraph.insertEdge(vertices[2], vertices[3], 2);
        matrixGraph.insertEdge(vertices[3], vertices[4], 1);
        matrixGraph.insertEdge(vertices[4], vertices[1], 4);

        listGraph.insertEdge(vertices[0], vertices[1], 10);
        listGraph.insertEdge(vertices[0], vertices[2], 20);
        listGraph.insertEdge(vertices[1], vertices[2], 5);
        listGraph.insertEdge(vertices[2], vertices[3], 2);
        listGraph.insertEdge(vertices[3], vertices[4], 1);
        listGraph.insertEdge(vertices[4], vertices[1], 4);

        System.out.println("MATRIX");
        matrixGraph.printGraph();

        System.out.println("LIST");
        listGraph.printGraph();

        System.out.println("MATRIX");
        printDijkstraDistances(matrixGraph, vertices[0]);
        System.out.println("LIST");
        printDijkstraDistances(listGraph, vertices[0]);
    }

    private static <V> void printDijkstraDistances(Graph<V, Integer> graph, Vertex<V> source) {
        Map<Vertex<V>, Integer> distances = DijkstraAlgorithm.dijkstraDistances(graph, source);
        for (Map.Entry<Vertex<V>, Integer> entry : distances.entrySet()) {
            System.out.println("Distance from " + source.getElement() + " to " + entry.getKey().getElement() + " is " + entry.getValue());
        }
    }
}
