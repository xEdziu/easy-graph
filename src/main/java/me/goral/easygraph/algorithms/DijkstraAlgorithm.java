package me.goral.easygraph.algorithms;

import me.goral.easygraph.classes.*;

import java.util.*;

public class DijkstraAlgorithm {

    public static <V> Map<Vertex<V>, Integer> dijkstraDistances(Graph<V, Integer> graph, Vertex<V> source) {
        Map<Vertex<V>, Integer> distances = new HashMap<>();
        PriorityQueue<Vertex<V>> queue = new PriorityQueue<>(Comparator.comparing(v -> distances.getOrDefault(v, Integer.MAX_VALUE)));

        // Initialize distances for all vertices
        graph.vertices().forEach(v -> {
            distances.put(v, v.equals(source) ? 0 : Integer.MAX_VALUE);
            queue.add(v);
        });

        while (!queue.isEmpty()) {
            Vertex<V> u = queue.remove(); // get vertex with the smallest distance

            // Relax edges outgoing from u
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<V> z = graph.opposite(u, e);
                int weight = e.getElement();
                int distanceThroughU = distances.get(u) + weight;

                if (distanceThroughU < distances.get(z)) {
                    queue.remove(z); // Delete z from the queue to update its distance
                    distances.put(z, distanceThroughU); // Update the distance
                    queue.add(z); // Add z back to the queue with the updated distance
                }
            }
        }

        return distances;
    }
}
