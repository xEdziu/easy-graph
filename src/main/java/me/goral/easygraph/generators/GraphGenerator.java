package me.goral.easygraph.generators;

import me.goral.easygraph.classes.Graph;
import me.goral.easygraph.classes.Vertex;
import me.goral.easygraph.classes.AdjacencyMatrixGraph;
import me.goral.easygraph.classes.AdjacencyListGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    private static final Random random = new Random();

    public static Graph<Integer, Integer> generateAdjacencyMatrixGraph(int numVertices, double density, boolean isDirected) {
        Graph<Integer, Integer> graph = new AdjacencyMatrixGraph<>(isDirected);
        generateGraph(graph, numVertices, density);
        return graph;
    }

    public static Graph<Integer, Integer> generateAdjacencyListGraph(int numVertices, double density, boolean isDirected) {
        Graph<Integer, Integer> graph = new AdjacencyListGraph<>(isDirected);
        generateGraph(graph, numVertices, density);
        return graph;
    }

    private static void generateGraph(Graph<Integer, Integer> graph, int numVertices, double density) {
    List<Vertex<Integer>> vertices = new ArrayList<>();

    // Adding vertices to the graph
    for (int i = 1; i <= numVertices; i++) {
        vertices.add(graph.insertVertex(i));
    }

    // Adding edges to the graph with random weights
    int maxEdges = (int) (density * numVertices * (numVertices - 1) / 2);
    int edgeCount = 0;

    while (edgeCount < maxEdges) {
        int uIndex = random.nextInt(numVertices);
        int vIndex = random.nextInt(numVertices);

        // No loops or multiple edges
        if (uIndex == vIndex || !graph.getEdge(vertices.get(uIndex), vertices.get(vIndex)).isEmpty()) {
            continue;
        }

        int weight = random.nextInt(100) + 1; // Weight between 1 and 100
        graph.insertEdge(vertices.get(uIndex), vertices.get(vIndex), weight);
        edgeCount++;
    }
}
}
