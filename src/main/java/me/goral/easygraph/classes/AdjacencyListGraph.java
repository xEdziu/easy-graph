package me.goral.easygraph.classes;

import me.goral.easygraph.generators.JsonLogger;

import java.util.*;

public class AdjacencyListGraph<V, E> extends Graph<V, E> {
    private final Map<Vertex<V>, List<Edge<E>>> adjacencyList;
    private final List<Vertex<V>> vertices;
    private final List<Edge<E>> edges;

    // Kody ANSI do ustalania kolorów tekstu
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";


    public AdjacencyListGraph(boolean isDirected) {
        super(isDirected);
        adjacencyList = new HashMap<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) {
        return adjacencyList.get(u).stream().anyMatch(e -> {
            Vertex<V>[] endpoints = endVertices(e);
            return endpoints[0].equals(v) || endpoints[1].equals(v);
        });
    }

    @Override
    public Optional<Edge<E>> getEdge(Vertex<V> u, Vertex<V> v) {
        return adjacencyList.get(u).stream()
                .filter(e -> {
                    Vertex<V>[] endpoints = endVertices(e);
                    return endpoints[0].equals(v) || endpoints[1].equals(v);
                })
                .findFirst();
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        for (Vertex<V> vertex : adjacencyList.keySet()) {
            if (adjacencyList.get(vertex).contains(e)) {
                for (Vertex<V> other : adjacencyList.keySet()) {
                    if (!other.equals(vertex) && adjacencyList.get(other).contains(e)) {
                        return new Vertex[] {vertex, other};
                    }
                }
            }
        }
        throw new IllegalArgumentException("Edge not in graph.");
    }


    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        Vertex<V>[] endpoints = endVertices(e);
        if (endpoints[0] != null && endpoints[0].equals(v)) {
            return endpoints[1];
        } else if (endpoints[1] != null && endpoints[1].equals(v)) {
            return endpoints[0];
        }
        // Logowanie, gdy nie znajdzie wierzchołka przeciwnego
        System.out.println("Failed to find opposite for vertex: " + v + " in edge: " + e + " - AdjacencyListGraph.opposite()");
        String data = "Edge: " + e + ", Vertex: " + v +
                ", Opposite vertex not found - AdjacencyListGraph.opposite()";
        JsonLogger.logToJson("Fail", "Edge not incident to vertex", data);
        throw new IllegalArgumentException("Edge not incident to vertex.");
    }



    @Override
    public int outDegree(Vertex<V> v) {
        return adjacencyList.get(v).size();
    }

    @Override
    public int inDegree(Vertex<V> v) {
        int count = 0;
        for (List<Edge<E>> edges : adjacencyList.values()) {
            for (Edge<E> e : edges) {
                Vertex<V>[] endpoints = endVertices(e);
                if (endpoints[1].equals(v)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        return new ArrayList<>(adjacencyList.get(v));
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        List<Edge<E>> incoming = new ArrayList<>();
        adjacencyList.forEach((vertex, edgesList) -> edgesList.forEach(e -> {
            Vertex<V>[] endpoints = endVertices(e);
            if (endpoints[1].equals(v)) {
                incoming.add(e);
            }
        }));
        return incoming;
    }

    @Override
    public Vertex<V> insertVertex(V x) {
        Vertex<V> newVertex = new Vertex<>(x);
        vertices.add(newVertex);
        adjacencyList.put(newVertex, new ArrayList<>());
        return newVertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E x) {
        Edge<E> newEdge = new Edge<>(x);
        adjacencyList.get(u).add(newEdge);
        if (!isDirected()) {
            adjacencyList.get(v).add(newEdge);
        }
        edges.add(newEdge);
        return newEdge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        adjacencyList.remove(v);
        vertices.remove(v);
        // Remove all edges associated with v
        adjacencyList.values().forEach(list -> list.removeIf(e -> {
            Vertex<V>[] endpoints = endVertices(e);
            return endpoints[0].equals(v) || endpoints[1].equals(v);
        }));
        edges.removeIf(e -> {
            Vertex<V>[] endpoints = endVertices(e);
            return endpoints[0].equals(v) || endpoints[1].equals(v);
        });
    }

    @Override
    public void removeEdge(Edge<E> e) {
        edges.remove(e);
        adjacencyList.forEach((k, v) -> v.remove(e));
    }
}
