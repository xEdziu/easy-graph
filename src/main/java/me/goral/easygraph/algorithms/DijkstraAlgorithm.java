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

//        // W algorytmie Dijkstry, po inicjalizacji odległości dla wszystkich wierzchołków
//        graph.vertices().forEach(v -> {
//            System.out.println("Post-initialization: Vertex " + v.getElement() + " with distance " + distances.get(v));
//        });

        while (!queue.isEmpty()) {
            Vertex<V> u = queue.pollFirst(); // get vertex with the smallest distance
//            System.out.println("Processing vertex " + u.getElement() + " with current distance " + distances.get(u));
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
//                    System.out.println("Relaxing edge from " + u.getElement() + " to " + z.getElement() + " with new distance " + distanceThroughU);
                    queue.remove(z); // Remove z from the queue before updating the distance
                    distances.put(z, distanceThroughU); // Update the distance
                    queue.add(z); // Add z back to the queue with the updated distance
                }
            }
        }
        return distances;
    }
}