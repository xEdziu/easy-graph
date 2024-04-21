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

    public static Graph<Integer, Integer> generateAdjacencyMatrixGraph(int numVertices, double density) {
        Graph<Integer, Integer> graph = new AdjacencyMatrixGraph<>(false);
        generateGraph(graph, numVertices, density);
        return graph;
    }

    public static Graph<Integer, Integer> generateAdjacencyListGraph(int numVertices, double density) {
        Graph<Integer, Integer> graph = new AdjacencyListGraph<>(false);
        generateGraph(graph, numVertices, density);
        return graph;
    }

    private static void generateGraph(Graph<Integer, Integer> graph, int numVertices, double density) {
        List<Vertex<Integer>> vertices = new ArrayList<>();

        // Adding vertices to the graph
        for (int i = 0; i < numVertices; i++) {
            vertices.add(graph.insertVertex(i));
        }

        // Adding edges to the graph with random weights
        int maxEdges = (int) (density * numVertices * (numVertices - 1) / 2);
        int edgeCount = 0;

        while (edgeCount < maxEdges) {
            int uIndex = random.nextInt(numVertices);
            int vIndex = random.nextInt(numVertices);
            if (uIndex == vIndex) continue; // No loops

            Vertex<Integer> u = vertices.get(uIndex);
            Vertex<Integer> v = vertices.get(vIndex);

            // We add an edge only if it doesn't exist
            if (graph.getEdge(u, v).isEmpty()) {
                int weight = random.nextInt(100) + 1; // Weight between 1 and 100
                graph.insertEdge(u, v, weight);
                edgeCount++;
            }
        }
    }
}
