package me.goral.easygraph.algorithms;

import me.goral.easygraph.classes.*;

import java.util.*;

public class DijkstraAlgorithm {

    public static <V> Map<Vertex<V>, Integer> dijkstraDistances(Graph<V, Integer> graph, Vertex<V> source) {
        Map<Vertex<V>, Integer> distances = new HashMap<>();
        Set<Vertex<V>> relaxed = new HashSet<>();
        TreeSet<Vertex<V>> queue = new TreeSet<>(Comparator.comparing(v -> distances.getOrDefault(v, Integer.MAX_VALUE)));

        // Initialize distances for all vertices
        graph.vertices().forEach(v -> {
            distances.put(v, v.equals(source) ? 0 : Integer.MAX_VALUE);
            queue.add(v);
        });

        while (!queue.isEmpty()) {
            Vertex<V> u = queue.pollFirst(); // get vertex with the smallest distance
            if (relaxed.contains(u)) {
                continue;
            }
            relaxed.add(u);

            // Relax edges outgoing from u
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<V> z = graph.opposite(u, e);
                if (relaxed.contains(z)) {
                    continue;
                }
                int weight = e.getElement();
                int distanceThroughU = distances.get(u) + weight;

                if (distanceThroughU < distances.get(z)) {
                    queue.remove(z); // Remove z from the queue before updating the distance
                    distances.put(z, distanceThroughU); // Update the distance
                    queue.add(z); // Add z back to the queue with the updated distance
                }
            }
        }
        return distances;
    }
}