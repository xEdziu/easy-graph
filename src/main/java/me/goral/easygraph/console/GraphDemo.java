package me.goral.easygraph.console;

import me.goral.easygraph.classes.*;
import me.goral.easygraph.algorithms.DijkstraAlgorithm;
import me.goral.easygraph.generators.GraphGenerator;
import me.goral.easygraph.generators.JsonLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphDemo {

    private static final String CSV_HEADER = "GraphType,Vertices,Density,Instance,ExecutionTimeNs\n";
    // Kody ANSI do ustalania kolorów tekstu
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        int[] numbersOfVertices = {10, 50, 100, 500, 1000};
        double[] densities = {0.25, 0.5, 0.75, 1.0};
        int instancesPerConfiguration = 100;
        List<String> csvLines = new ArrayList<>();

        // Adding CSV header
        csvLines.add(CSV_HEADER);

        for (int numVertices : numbersOfVertices) {
            for (double density : densities) {
                for (int i = 1; i <= instancesPerConfiguration; i++) {
                    // Generate graphs
                    Graph<Integer, Integer> matrixGraph = GraphGenerator.generateAdjacencyMatrixGraph(numVertices, density);
                    Graph<Integer, Integer> listGraph = GraphGenerator.generateAdjacencyListGraph(numVertices, density);

                    // Measure time and handle exceptions for matrix graph
                    long executionTimeMatrix = measureGraph(matrixGraph);

                    // Measure time and handle exceptions for list graph
                    long executionTimeList = measureGraph(listGraph);

                    // Save results to CSV
                    csvLines.add("Matrix," + numVertices + "," + density + "," + i + "," + executionTimeMatrix + "\n");
                    csvLines.add("List," + numVertices + "," + density + "," + i + "," + executionTimeList + "\n");

                    // Print results
                    System.out.println(ANSI_GREEN + "Matrix Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeMatrix + " ns" + ANSI_RESET);
                    System.out.println(ANSI_BLUE +"List Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeList + " ns" + ANSI_RESET);
                }
            }
        }

        // Save results to CSV file
        try (FileWriter fileWriter = new FileWriter("graph_performance.csv")) {
            for (String csvLine : csvLines) {
                fileWriter.append(csvLine);
            }
            System.out.println(ANSI_YELLOW + "CSV file was created successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println("Error in CsvFileWriter: " + e.getMessage());
        }
    }

    private static long measureGraph(Graph<Integer, Integer> graph) {
        long startTime = System.nanoTime();
        try {
            DijkstraAlgorithm.dijkstraDistances(graph, graph.vertices().iterator().next());
        } catch (Exception e) {
            JsonLogger.logToJson("Error", "Failed to execute Dijkstra's algorithm", e.getMessage());
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

}


