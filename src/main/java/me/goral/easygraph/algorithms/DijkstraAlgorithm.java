package me.goral.easygraph.algorithms;

import me.goral.easygraph.classes.*;

import java.util.*;

public class DijkstraAlgorithm {

    public static <V> Map<Vertex<V>, Integer> dijkstraDistances(Graph<V, Integer> graph, Vertex<V> source) {
        Map<Vertex<V>, Integer> distances = new HashMap<>();
        Map<Vertex<V>, Boolean> relaxed = new HashMap<>();
        PriorityQueue<Vertex<V>> queue = new PriorityQueue<>(Comparator.comparing(v -> distances.getOrDefault(v, Integer.MAX_VALUE)));

        // Initialize distances for all vertices
        graph.vertices().forEach(v -> {
            distances.put(v, v.equals(source) ? 0 : Integer.MAX_VALUE);
            relaxed.put(v, false); // nowe
            queue.add(v);
        });

        while (!queue.isEmpty()) {
            Vertex<V> u = queue.remove(); // get vertex with the smallest distance
            if (relaxed.get(u)) { // nowe
                continue;
            }
            relaxed.put(u, true); // nowe

            // Relax edges outgoing from u
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<V> z = graph.opposite(u, e);
                if (relaxed.get(z)) { // nowe
                    continue;
                }
                int weight = e.getElement();
                int distanceThroughU = distances.get(u) + weight;

                if (distanceThroughU < distances.get(z)) {
                    distances.put(z, distanceThroughU); // Update the distance
                    queue.add(z); // Add z back to the queue with the updated distance
                }
            }
        }
        return distances;
    }
}
