package me.goral.easygraph.console;

import me.goral.easygraph.classes.*;
import me.goral.easygraph.algorithms.DijkstraAlgorithm;
import me.goral.easygraph.generators.GraphGenerator;
import me.goral.easygraph.generators.JsonLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphDemo {

    private static final String CSV_HEADER = "GraphType,Vertices,Density,Instance,ExecutionTimeNs\n";
    // Kody ANSI do ustalania kolorów tekstu
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

        int[] numbersOfVertices = null;
        double[] densities = null;
        Graph<Integer, Integer> matrixDemo = null;
        Graph<Integer, Integer> listDemo = null;
        int instancesPerConfiguration = 100;
        String outputFileName = "";
        boolean isDirected = false;
        Random random = new Random(1); // Use the same seed for both graphs

        if (args.length > 0) {
            if (args[0].equals("--tests")) {
                System.out.println("Preparing tests. Please wait for the results.");
                numbersOfVertices = new int[]{10, 50, 100, 500, 1000};
                densities = new double[]{0.25, 0.5, 0.75, 1.0};
                outputFileName = "graph_performance.csv";
            } else if (args[0].equals("--driver")) {
                System.out.println("Demo for the graph performance tests. Please wait for the results.");
                numbersOfVertices = new int[]{5, 10};
                densities = new double[]{0.5, 1};
                outputFileName = "demo.csv";
            }
        }

        List<String> csvLines = new ArrayList<>();

        // Adding CSV header
        csvLines.add(CSV_HEADER);

        for (int numVertices : numbersOfVertices) {
            for (double density : densities) {
                for (int i = 1; i <= instancesPerConfiguration; i++) {
                    // Generate graphs
                    Graph<Integer, Integer> matrixGraph = GraphGenerator.generateAdjacencyMatrixGraph(numVertices, density, isDirected, random);
                    Graph<Integer, Integer> listGraph = GraphGenerator.generateAdjacencyListGraph(numVertices, density, isDirected, random);

                    // Measure time and handle exceptions for matrix graph
                    long executionTimeMatrix = measureGraph(matrixGraph);
                    System.out.println(ANSI_GREEN + "Matrix Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeMatrix + " ns" + ANSI_RESET);

                    // Measure time and handle exceptions for list graph
                    long executionTimeList = measureGraph(listGraph);
                    System.out.println(ANSI_BLUE +"List Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeList + " ns" + ANSI_RESET);

                    // Save results to CSV
                    csvLines.add("Matrix," + numVertices + "," + density + "," + i + "," + executionTimeMatrix + "\n");
                    csvLines.add("List," + numVertices + "," + density + "," + i + "," + executionTimeList + "\n");

                    if (args[0].equals("--driver")) {
                        matrixDemo = matrixGraph;
                        listDemo = listGraph;
                    }
                }
            }
        }

        if (args[0].equals("--driver")) {
            Vertex<Integer> sourceMatrix = matrixDemo.vertices().iterator().next();
            Vertex<Integer> sourceList = listDemo.vertices().iterator().next();

            System.out.println("Matrix Graph:");
            matrixDemo.printGraph();
            System.out.println("Dijkstra distances for Matrix Graph:");
            printDijkstraDistances(matrixDemo, sourceMatrix);

            System.out.println("List Graph:");
            listDemo.printGraph();
            System.out.println("Dijkstra distances for List Graph:");
            printDijkstraDistances(listDemo, sourceList);
        }

        // Save results to CSV file
        try (FileWriter fileWriter = new FileWriter(outputFileName)) {
            for (String csvLine : csvLines) {
                fileWriter.append(csvLine);
            }
            System.out.println(ANSI_YELLOW + "CSV file was created successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println("Error in CsvFileWriter: " + e.getMessage());
        }
    }

    private static <V> long measureGraph(Graph<V, Integer> graph) {
        long startTime = System.nanoTime();
        try {
            Map<Vertex<V>, Vertex<V>> predecessors = new HashMap<>();
            DijkstraAlgorithm.dijkstraDistances(graph, graph.vertices().iterator().next(), predecessors);
        } catch (Exception e) {
            JsonLogger.logToJson("Error", "Failed to execute Dijkstra's algorithm", e.getMessage());
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static <V> void printDijkstraDistances(Graph<V, Integer> graph, Vertex<V> source) {
        Map<Vertex<V>, Vertex<V>> predecessors = new HashMap<>();
        Map<Vertex<V>, Integer> distances = DijkstraAlgorithm.dijkstraDistances(graph, source, predecessors);
        for (Map.Entry<Vertex<V>, Integer> entry : distances.entrySet()) {
            System.out.print("Distance from " + source.getElement() + " to " + entry.getKey().getElement() + " is " + entry.getValue());
            printPath(predecessors, source, entry.getKey());
            System.out.println();
        }
    }

    private static <V> void printPath(Map<Vertex<V>, Vertex<V>> predecessors, Vertex<V> source, Vertex<V> target) {
        if (!target.equals(source)) {
            printPath(predecessors, source, predecessors.get(target));
        }
        System.out.print(" -> " + target.getElement());
    }

}