package me.goral.easygraph.classes;

import java.util.*;

public class AdjacencyListGraph<V, E> extends Graph<V, E> {
    private final Map<Vertex<V>, Map<Vertex<V>, Edge<E>>> adjacencyMap;
    private final Map<Edge<E>, Vertex<V>[]> edgeVertices;
    private final List<Vertex<V>> vertices;
    private final List<Edge<E>> edges;

    public AdjacencyListGraph(boolean isDirected) {
        super(isDirected);
        adjacencyMap = new LinkedHashMap<>();
        edgeVertices = new HashMap<>();
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
    public Vertex<V>[] endVertices(Edge<E> e) {
        if (!edgeVertices.containsKey(e)) {
            throw new IllegalArgumentException("Edge not found in the graph.");
        }
        return edgeVertices.get(e);
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        Vertex<V>[] endpoints = endVertices(e);
        if (endpoints[0].equals(v)) {
            return endpoints[1];
        } else if (endpoints[1].equals(v)) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("Vertex is not incident to edge.");
        }
    }

    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) {
        return adjacencyMap.containsKey(u) && adjacencyMap.get(u).containsKey(v);
    }

    @Override
    public Optional<Edge<E>> getEdge(Vertex<V> u, Vertex<V> v) {
        if (adjacencyMap.containsKey(u) && adjacencyMap.get(u).containsKey(v)) {
            return Optional.of(adjacencyMap.get(u).get(v));
        }
        return Optional.empty();
    }


    @Override
    public int outDegree(Vertex<V> v) {
        return adjacencyMap.getOrDefault(v, Collections.emptyMap()).size();
    }

    @Override
    public int inDegree(Vertex<V> v) {
        int count = 0;
        for (Map<Vertex<V>, Edge<E>> map : adjacencyMap.values()) {
            if (map.containsKey(v)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        return new ArrayList<>(adjacencyMap.getOrDefault(v, Collections.emptyMap()).values());
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        Vertex<V> newVertex = new Vertex<>(element);
        vertices.add(newVertex);
        adjacencyMap.put(newVertex, new LinkedHashMap<>());  // Use LinkedHashMap to maintain order
        return newVertex;
    }


    @Override
    public void insertEdge(Vertex<V> u, Vertex<V> v, E element) {
        Edge<E> newEdge = new Edge<>(element);
        adjacencyMap.computeIfAbsent(u, k -> new LinkedHashMap<>()).put(v, newEdge);
        edgeVertices.put(newEdge, new Vertex[]{u, v});

        if (!isDirected()) {
            adjacencyMap.computeIfAbsent(v, k -> new LinkedHashMap<>()).put(u, newEdge);
        }
        edges.add(newEdge);
    }


    @Override
    public void removeVertex(Vertex<V> v) {
        // Remove all edges incident to v
        new ArrayList<>(edgeVertices.entrySet()).forEach(entry -> {
            if (Arrays.asList(entry.getValue()).contains(v)) {
                removeEdge(entry.getKey());
            }
        });
        adjacencyMap.remove(v);
        vertices.remove(v);
    }

    @Override
    public void removeEdge(Edge<E> e) {
        Vertex<V>[] endpoints = endVertices(e);
        adjacencyMap.get(endpoints[0]).remove(endpoints[1]);
        if (!isDirected()) {
            adjacencyMap.get(endpoints[1]).remove(endpoints[0]);
        }
        edgeVertices.remove(e);
        edges.remove(e);
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        List<Edge<E>> result = new ArrayList<>();
        for (Edge<E> e : edges) {
            Vertex<V>[] endpoints = endVertices(e);
            if (endpoints[1].equals(v)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public void printGraph() {
        for (Vertex<V> vertex : vertices) {
            System.out.print(vertex.getElement() + " -> ");
            adjacencyMap.get(vertex).forEach((adjVertex, edge) -> System.out.print(adjVertex.getElement() + " "));
            System.out.println();
        }
    }

}

