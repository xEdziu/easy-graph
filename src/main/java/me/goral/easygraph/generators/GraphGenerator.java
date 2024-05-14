package me.goral.easygraph.generators;

import me.goral.easygraph.classes.Graph;
import me.goral.easygraph.classes.Vertex;
import me.goral.easygraph.classes.AdjacencyMatrixGraph;
import me.goral.easygraph.classes.AdjacencyListGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    public static Graph<Integer, Integer> generateAdjacencyMatrixGraph(int numVertices, double density, boolean isDirected, Random random) {
        Graph<Integer, Integer> graph = new AdjacencyMatrixGraph<>(isDirected);
        generateGraph(graph, numVertices, density, random);
        return graph;
    }

    public static Graph<Integer, Integer> generateAdjacencyListGraph(int numVertices, double density, boolean isDirected, Random random) {
        Graph<Integer, Integer> graph = new AdjacencyListGraph<>(isDirected);
        generateGraph(graph, numVertices, density, random);
        return graph;
    }

    private static void generateGraph(Graph<Integer, Integer> graph, int numVertices, double density, Random random) {
        List<Vertex<Integer>> vertices = new ArrayList<>();

        // Adding vertices to the graph
        for (int i = 1; i <= numVertices; i++) {
            vertices.add(graph.insertVertex(i));
        }

        // Adding edges to the graph with random weights
        int maxEdges = (int) (density * numVertices * (numVertices - 1) / 2);
        int edgeCount = 0;

        for (int i = 0; i < numVertices - 1 && edgeCount < maxEdges; i++) {
            for (int j = i + 1; j < numVertices && edgeCount < maxEdges; j++) {
                int weight = 1 + random.nextInt(100); // Random weight between 1 and 100
                graph.insertEdge(vertices.get(i), vertices.get(j), weight);
                edgeCount++;
            }
        }
    }
}