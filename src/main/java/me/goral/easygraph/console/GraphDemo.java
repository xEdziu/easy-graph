package me.goral.easygraph.console;

import me.goral.easygraph.classes.*;
import me.goral.easygraph.algorithms.DijkstraAlgorithm;
import me.goral.easygraph.generators.GraphGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphDemo {

    private static final String CSV_HEADER = "GraphType,Vertices,Density,Instance,ExecutionTime\n";

    public static void main(String[] args) {
        int[] numbersOfVertices = {10, 50, 100, 500, 1000};
        double[] densities = {0.25, 0.5, 0.75, 1.0};
        int instancesPerConfiguration = 100;
        List<String> csvLines = new ArrayList<>();

        // Adding CSV header
        csvLines.add(CSV_HEADER);

        for (int numVertices : numbersOfVertices) {
            for (double density : densities) {
                for (int i = 0; i < instancesPerConfiguration; i++) {
                    // Generate graphs
                    Graph<Integer, Integer> matrixGraph = GraphGenerator.generateAdjacencyMatrixGraph(numVertices, density);
                    Graph<Integer, Integer> listGraph = GraphGenerator.generateAdjacencyListGraph(numVertices, density);

                    // Measure time for matrix graph
                    long startTimeMatrix = System.nanoTime();
                    DijkstraAlgorithm.dijkstraDistances(matrixGraph, matrixGraph.vertices().iterator().next());
                    long endTimeMatrix = System.nanoTime();
                    long executionTimeMatrix = endTimeMatrix - startTimeMatrix;
                    csvLines.add("Matrix," + numVertices + "," + density + "," + i + "," + executionTimeMatrix);

                    // Measure time for list graph
                    long startTimeList = System.nanoTime();
                    DijkstraAlgorithm.dijkstraDistances(listGraph, listGraph.vertices().iterator().next());
                    long endTimeList = System.nanoTime();
                    long executionTimeList = endTimeList - startTimeList;
                    csvLines.add("List," + numVertices + "," + density + "," + i + "," + executionTimeList);

                    // Print results
                    System.out.println("Matrix Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeMatrix + " ns");
                    System.out.println("List Graph - Vertices: " + numVertices + ", Density: " + density + ", Instance: " + i + ", Execution Time: " + executionTimeList + " ns");
                }
            }
        }

        // Save results to CSV file
        try (FileWriter fileWriter = new FileWriter("graph_performance.csv")) {
            for (String csvLine : csvLines) {
                fileWriter.append(csvLine);
            }
            System.out.println("CSV file was created successfully.");
        } catch (IOException e) {
            System.out.println("Error in CsvFileWriter.");
            e.printStackTrace();
        }
    }
}

